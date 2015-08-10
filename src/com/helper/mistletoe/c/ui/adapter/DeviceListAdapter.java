package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Result_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.TimeTool_Utils;

@SuppressLint({ "InflateParams", "ViewHolder" })
public class DeviceListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;
	private static List<Result_Bean> list = null;
	private User_Bean user;
	private Integer device_id;

	public DeviceListAdapter(Context context, List<Result_Bean> list) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		user = new User_Bean();
		try {
			user.readData(context);
			device_id = user.getDevice_id();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list == null) {
			DeviceListAdapter.list = new ArrayList<Result_Bean>();
		} else {
			DeviceListAdapter.list = list;
		}
	}

	// 当ListView数据发生变化时,调用此方法来更新ListView
	// @param list
	public void updateListView(List<Result_Bean> list) {
		DeviceListAdapter.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder viewHolder = null;
		final Result_Bean mContent = list.get(position);
		// 初始化ViewHolder
		viewHolder = new viewHolder();
		// 把控件保存到ViewHolder中
		convertView = inflater.inflate(R.layout.device_list_item, null);
		viewHolder.ivPic = (ImageView) convertView.findViewById(R.id.device_list_imageView_pic);
		viewHolder.tv_name = (TextView) convertView.findViewById(R.id.device_list_textView_name);
		viewHolder.tv_time = (TextView) convertView.findViewById(R.id.device_list_textView_time);
		viewHolder.tv_kick = (TextView) convertView.findViewById(R.id.device_list_textView_kick);
		try {
			viewHolder.tv_name.setText(mContent.getHardware());
			viewHolder.tv_time.setText(TimeTool_Utils.fromateTimeShow(mContent.getAccess_token_valid() * 1000, "yyyy/MM/dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (mContent.getId() == device_id) {
			viewHolder.tv_kick.setText("本机");
		} else {
			if (mContent.getStatus() == 2) {
				viewHolder.tv_kick.setText("取消授权");
				viewHolder.tv_kick.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 发出踢出设备指令
						Instruction_Utils.sendInstrustion(context, Instruction_Utils.KICK_DEVICE, mContent.getId());
					}
				});
			} else if (mContent.getStatus() == 5) {
				viewHolder.tv_kick.setText("已踢出");
			}
		}
		convertView.setTag(viewHolder);
		return convertView;
	}

	final class viewHolder {
		ImageView ivPic;
		TextView tv_name;
		TextView tv_time;
		TextView tv_kick;
	}
}
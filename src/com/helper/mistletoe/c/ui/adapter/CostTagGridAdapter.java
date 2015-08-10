package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.helper.mistletoe.R;

@SuppressLint({ "DefaultLocale", "InflateParams" })
public class CostTagGridAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private static Context context;
	private Integer selectIndex=-1;
	private static ArrayList<String> list = null;

	@SuppressWarnings("static-access")
	public CostTagGridAdapter(Context context, ArrayList<String> tagNames) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		if (tagNames == null) {
			this.list = new ArrayList<String>();
		} else {
			this.list = tagNames;
		}
	}

	// 当ListView数据发生变化时,调用此方法来更新ListView
	// @param list
	public void updateListView(ArrayList<String> list) {
		CostTagGridAdapter.list = list;
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
		final String mContent = list.get(position);
		if (convertView == null) {
			// 初始化ViewHolder
			viewHolder = new viewHolder();
			// 把控件保存到ViewHolder中
			convertView = inflater.inflate(R.layout.schedule_cost_tag_item, null);
			viewHolder.name = (TextView) convertView.findViewById(R.id.schedule_cost_tag_item_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (viewHolder) convertView.getTag();
		}
		try {
			viewHolder.name.setText(mContent);
			if(position == selectIndex){
				if (mContent.equals("+")) {
					viewHolder.name.setBackgroundResource(R.color.main_color);
					viewHolder.name.setTextSize(16);
					viewHolder.name.setTextColor(Color.WHITE);
					viewHolder.name.setText("添加");
					viewHolder.name.setGravity(Gravity.CENTER_HORIZONTAL);
				}else {
				viewHolder.name.setBackgroundColor(Color.parseColor("#17a5ca"));
				viewHolder.name.setTextColor(Color.WHITE);
				}
			}else{
				if (mContent.equals("+")) {
					viewHolder.name.setBackgroundResource(R.color.main_color);
					viewHolder.name.setTextSize(16);
					viewHolder.name.setTextColor(Color.WHITE);
					viewHolder.name.setText("添加");
					viewHolder.name.setGravity(Gravity.CENTER_HORIZONTAL);
				}else {
					viewHolder.name.setBackgroundResource(R.drawable.cost_tag_text_back);	
					viewHolder.name.setTextColor(Color.parseColor("#757575"));
				}
				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	final class viewHolder {
		TextView name;
	}
	public void setSelectIndex(Integer i) {
		selectIndex=i;
	}
}
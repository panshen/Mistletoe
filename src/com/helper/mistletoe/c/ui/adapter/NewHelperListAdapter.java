package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.v.MyTextViewButton;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

@SuppressLint("InflateParams")
public class NewHelperListAdapter extends BaseAdapter {
	private static ArrayList<Helpers_Sub_Bean> list = null;
	private static Context mContext;

	public NewHelperListAdapter(Context mContext, ArrayList<Helpers_Sub_Bean> list) {
		NewHelperListAdapter.mContext = mContext;
		if (list == null) {
			NewHelperListAdapter.list = new ArrayList<Helpers_Sub_Bean>();
		} else {
			NewHelperListAdapter.list = list;
		}
	}

	// 当ListView数据发生变化时,调用此方法来更新ListView
	// @param list
	public void updateListView(ArrayList<Helpers_Sub_Bean> list) {
		NewHelperListAdapter.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Helpers_Sub_Bean getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final Helpers_Sub_Bean mContent = list.get(position);

		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.new_helper_list_item, null);
			viewHolder.tvName = (TextView) view.findViewById(R.id.new_helper_list_item_textView_name);
			viewHolder.tvAccount = (TextView) view.findViewById(R.id.new_helper_list_item_textView_label);
			viewHolder.head = (SnaImageViewV2) view.findViewById(R.id.new_helper_list_item_imageView_head);
			viewHolder.mybtAddRight = (MyTextViewButton) view.findViewById(R.id.new_helper_list_item_myButton_right);
			viewHolder.mybtAddRight.setVisibility(MyTextViewButton.GONE);
			viewHolder.line = (ImageView) view.findViewById(R.id.new_helper_list_item_dividingLine);
			viewHolder.line.setVisibility(ImageView.GONE);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
			viewHolder.head.setImageResource(R.drawable.default_head);
		}
		viewHolder.tvName.setText(mContent.getHelper_name());
		viewHolder.head.setImageForShow(mContent.getHelper_photo(), SnaBitmap.NET_SMALL);
		if (mContent.getHelper_msg() == null) {
			viewHolder.tvAccount.setVisibility(TextView.GONE);
		} else {
			if (mContent.getHelper_msg().length() <= 0) {
				viewHolder.tvAccount.setVisibility(TextView.GONE);
			} else {
				viewHolder.tvAccount.setVisibility(TextView.VISIBLE);
				viewHolder.tvAccount.setText(mContent.getHelper_msg());
			}
		}
		switch (mContent.getHelper_status()) {
		case 1:// 等待对方验证，不能点击
			viewHolder.line.setVisibility(ImageView.GONE);
			viewHolder.mybtAddRight.setVisibility(MyTextViewButton.VISIBLE);
			viewHolder.mybtAddRight.setText("待验证");
			viewHolder.mybtAddRight.setTextColor(Color.parseColor("#d3d3d3"));
			break;
		case 3:// 新帮手，点击后变为旧帮手，即3--》9
			viewHolder.line.setVisibility(ImageView.GONE);
			viewHolder.mybtAddRight.setVisibility(MyTextViewButton.VISIBLE);
			viewHolder.mybtAddRight.setText("已添加");
			viewHolder.mybtAddRight.setTextColor(Color.parseColor("#d3d3d3"));
			break;
		case 5:// 需要自己确认，点击接受，变为3，点击拒绝，变为6
			viewHolder.line.setVisibility(ImageView.VISIBLE);
			viewHolder.mybtAddRight.setVisibility(MyTextViewButton.VISIBLE);
			viewHolder.mybtAddRight.setText("接受");
			viewHolder.mybtAddRight.setTextColor(Color.parseColor("#259b24"));
			viewHolder.mybtAddRight.setIndex(position);
			viewHolder.mybtAddRight.setOnClickListener(MyFiveRightButtonOnClickListener.getInstance());
			break;
		default:
			break;
		}
		return view;
	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView tvName;
		TextView tvAccount;
		ImageView line;
		SnaImageViewV2 head;
		MyTextViewButton mybtAddRight;
	}

//	// 用单例模式构造一个Listener,新帮手，点击后变为旧帮手，即3--》9
//	private static class MyThreeButtonOnClickListener implements OnClickListener {
//
//		private static MyThreeButtonOnClickListener instance = null;
//
//		private MyThreeButtonOnClickListener() {
//		}
//
//		public static MyThreeButtonOnClickListener getInstance() {
//			if (instance == null)
//				instance = new MyThreeButtonOnClickListener();
//			return instance;
//		}
//
//		@Override
//		public void onClick(View view) {
//			int index = ((MyTextViewButton) view).getIndex();
//			// 发送移除新帮手列表，变为旧帮手
//			Instruction_Utils.sendInstrustion(mContext, Instruction_Utils.TURN_TO_OLD_HELPER, list.get(index).getHelper_id());
//		}
//	}

	// 用单例模式构造一个Listener 需要自己确认
	private static class MyFiveRightButtonOnClickListener implements OnClickListener {

		private static MyFiveRightButtonOnClickListener instance = null;

		private MyFiveRightButtonOnClickListener() {
		}

		public static MyFiveRightButtonOnClickListener getInstance() {
			if (instance == null)
				instance = new MyFiveRightButtonOnClickListener();
			return instance;
		}

		@Override
		public void onClick(View view) {
			int index = ((MyTextViewButton) view).getIndex();
			// 发送同意添加的指令，变为新帮手
			Instruction_Utils.sendInstrustion(mContext, Instruction_Utils.AGREE_TO_ADD, list.get(index).getHelper_id());
		}
	}
}
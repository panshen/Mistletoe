package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Cache_Bean;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class FeedBackAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private static List<Cache_Bean> list = null;

	@SuppressWarnings("static-access")
	public FeedBackAdapter(Context context, List<Cache_Bean> list) {
		super();
		this.inflater = LayoutInflater.from(context);
		if (list == null) {
			this.list = new ArrayList<Cache_Bean>();
		} else {
			this.list = list;
		}
	}

	// 当ListView数据发生变化时,调用此方法来更新ListView
	// @param list
	@SuppressWarnings("static-access")
	public void updateListView(List<Cache_Bean> list) {
		this.list = list;
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
		Cache_Bean mContent = list.get(position);
		if (convertView == null) {
			// 初始化ViewHolder
			viewHolder = new viewHolder();
			// 把控件保存到ViewHolder中
			convertView = inflater.inflate(R.layout.feedback_item, null);
			viewHolder.tvLeft_reply = (TextView) convertView.findViewById(R.id.feedBack_item_textView_reply);
			viewHolder.tvLeft_reply.setVisibility(TextView.GONE);
			viewHolder.tvRight_feedBack = (TextView) convertView.findViewById(R.id.feedBack_item_textView_feedBack);
			viewHolder.tvRight_feedBack.setVisibility(TextView.GONE);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (viewHolder) convertView.getTag();
		}
		if (mContent.getFeedBack_stype().equals("reply")) {
			viewHolder.tvLeft_reply.setText(mContent.getFeedBack());
			viewHolder.tvRight_feedBack.setVisibility(TextView.GONE);
			viewHolder.tvLeft_reply.setVisibility(TextView.VISIBLE);
		} else if (mContent.getFeedBack_stype().equals("feedBack")) {
			viewHolder.tvRight_feedBack.setText(mContent.getFeedBack());
			viewHolder.tvLeft_reply.setVisibility(TextView.GONE);
			viewHolder.tvRight_feedBack.setVisibility(TextView.VISIBLE);
		}

		return convertView;
	}

	final class viewHolder {
		TextView tvLeft_reply;
		TextView tvRight_feedBack;
	}
}
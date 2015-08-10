package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Public_Bean;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

@SuppressLint("InflateParams")
public class RecommendHelperListAdapter extends BaseAdapter {
	private ArrayList<Public_Bean> list = null;
	private Context mContext;

	public RecommendHelperListAdapter(Context mContext, ArrayList<Public_Bean> list) {
		this.mContext = mContext;
		if (list == null) {
			this.list = new ArrayList<Public_Bean>();
		} else {
			this.list = list;
		}
	}

	// 当ListView数据发生变化时,调用此方法来更新ListView
	// @param list
	public void updateListView(ArrayList<Public_Bean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Public_Bean getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		Public_Bean mContent = list.get(position);

		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.recommend_helper_list_item, null);
			viewHolder.ivHead = (SnaImageViewV2) view.findViewById(R.id.recommend_helper_list_item_imageView_head);
			viewHolder.tvName = (TextView) view.findViewById(R.id.recommend_helper_list_item_textView_name);
			viewHolder.tvLabel = (TextView) view.findViewById(R.id.recommend_helper_list_item_textView_label);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		if (mContent.getHelper_photo() != null) {
			if (!mContent.getHelper_photo().isEmpty()) {
				viewHolder.ivHead.setImageForShow(mContent.getHelper_photo(), SnaBitmap.NET_SMALL);
			}
		}
		viewHolder.tvName.setText(mContent.getHelper_name());
		viewHolder.tvLabel.setText(mContent.getHelper_sign());
		return view;
	}

	class ViewHolder {
		SnaImageViewV2 ivHead;
		TextView tvName;
		TextView tvLabel;
	}
}
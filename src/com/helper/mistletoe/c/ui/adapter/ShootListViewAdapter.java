package com.helper.mistletoe.c.ui.adapter;

import java.util.List;

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
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShootListViewAdapter extends BaseAdapter {

	Context con;
	List<Public_Bean> list;
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	public ShootListViewAdapter(Context con, List<Public_Bean> list) {
		super();
		this.con = con;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		Public_Bean mContent = list.get(position);

		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(con).inflate(R.layout.recommend_helper_list_item, null);
			viewHolder.ivHead = (SnaImageViewV2) view.findViewById(R.id.recommend_helper_list_item_imageView_head);
			viewHolder.tvName = (TextView) view.findViewById(R.id.recommend_helper_list_item_textView_name);
			viewHolder.tvLabel = (TextView) view.findViewById(R.id.recommend_helper_list_item_textView_label);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.ivHead.setImageForShow(mContent.getHelper_photo(), SnaBitmap.NET_SMALL);
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

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
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

@SuppressLint({ "DefaultLocale", "InflateParams" })
public class PosterFragmentAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private static Context context;
	private static ArrayList<Target_Bean> list = null;

	@SuppressWarnings("static-access")
	public PosterFragmentAdapter(Context context, ArrayList<Target_Bean> list) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		if (list == null) {
			this.list = new ArrayList<Target_Bean>();
		} else {
			this.list = list;
		}
	}

	// 当ListView数据发生变化时,调用此方法来更新ListView
	// @param list
	public void updateListView(ArrayList<Target_Bean> list) {
		PosterFragmentAdapter.list = list;
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
		final Target_Bean mContent = list.get(position);
		if (convertView == null) {
			// 初始化ViewHolder
			viewHolder = new viewHolder();
			// 把控件保存到ViewHolder中
			convertView = inflater.inflate(R.layout.poster_fragment_item, null);
			viewHolder.name = (TextView) convertView.findViewById(R.id.poster_fragment_item_name);
			viewHolder.pic = (SnaImageViewV2) convertView.findViewById(R.id.poster_fragment_item_imageView_head_f);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (viewHolder) convertView.getTag();
		}
		try {
			String name = mContent.getSummary();
			viewHolder.name.setText(name);
			viewHolder.pic.setImageForShow(mContent.getShowImageId(), SnaBitmap.NET_SMALL);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	final class viewHolder {
		TextView name;
		SnaImageViewV2 pic;
	}
}
package com.helper.mistletoe.v;

import java.util.ArrayList;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HorizontalListViewAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Helpers_Sub_Bean> list;
	private LayoutInflater mInflater;
	Bitmap iconBitmap;


	public HorizontalListViewAdapter(Context applicationContext, ArrayList<Helpers_Sub_Bean> selectedList) {
		this.mContext = applicationContext;
		if (selectedList == null) {
			this.list = new ArrayList<Helpers_Sub_Bean>();
		} else {
			this.list = selectedList;
		}
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);// LayoutInflater.from(mContext);
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
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		
		final Helpers_Sub_Bean mContent = list.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.horizontal_list_item, null);
			holder.mImage = (SnaImageViewV2) convertView.findViewById(R.id.horizontal_list_item_snaImageView_head);
			holder.name=(TextView) convertView.findViewById(R.id.horizontal_list_item_textView_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mImage.setImageForShow(mContent.getHelper_photo(), SnaBitmap.NET_SMALL);
		holder.name.setText(mContent.getHelper_name());
		return convertView;
	}

	private static class ViewHolder {
		private SnaImageViewV2 mImage;
		private TextView name;
	}
}
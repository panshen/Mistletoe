
package com.helper.mistletoe.v.viewflow;

import java.util.ArrayList;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.fragment.PosterFragment;
import com.helper.mistletoe.c.ui.Target_Details_Activity;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
/*
 * 数据装载类
 */
public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	public static ArrayList<Integer> ids;
	public static ArrayList<Target_Bean> mTargetList;
	public ImageAdapter(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public ImageAdapter(Context context, ArrayList<Target_Bean> targetList) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mTargetList=targetList;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ids =new ArrayList<Integer>();
		for (int i = 0; i < targetList.size(); i++) {
				ids.add(targetList.get(i).getShowImageId());
		}
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;   //返回很大的值使得getView中的position不断增大来实现循环。
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.poster_head_image_item, null);
		}
		((SnaImageViewV2) convertView.findViewById(R.id.imgView)).setImageForShow(ids.get(position%ids.size()), SnaBitmap.NET_NORMAL);;
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Target_Details_Activity.openPage_Market((Activity)mContext, mTargetList.get(position%ids.size()).getTarget_id(), 0);
			}
		});
		return convertView;
	}
	}

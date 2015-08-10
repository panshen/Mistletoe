package com.helper.mistletoe.v;

import java.util.ArrayList;

import com.helper.mistletoe.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HorizontalListViewForTagAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> list;
	private LayoutInflater mInflater;
	private Integer selectIndex=-1;
	Bitmap iconBitmap;

	public HorizontalListViewForTagAdapter(Context context, ArrayList<String> tagNames) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		if (tagNames == null) {
			this.list = new ArrayList<String>();
		} else {
			this.list = tagNames;
		}
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);// LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public String getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		
		final String mContent = list.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.taghorizontal_list_item, null);
			holder.name=(TextView) convertView.findViewById(R.id.taghorizontal_list_item_textView_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(mContent);
		if(position == selectIndex){
			holder.name.setBackgroundColor(Color.parseColor("#17a5ca"));
			holder.name.setTextColor(Color.WHITE);
		}else{
			holder.name.setBackgroundColor(Color.WHITE);
			holder.name.setTextColor(Color.parseColor("#535353"));
		}
		return convertView;
	}

	private static class ViewHolder {
		private TextView name;
	}
	public void setSelectIndex(Integer i) {
		selectIndex=i;
	}
}
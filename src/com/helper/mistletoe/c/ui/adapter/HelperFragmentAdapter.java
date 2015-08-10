package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

@SuppressLint({ "DefaultLocale", "InflateParams" })
public class HelperFragmentAdapter extends BaseAdapter implements SectionIndexer {
	private LayoutInflater inflater;
	private static Context context;
	private static ArrayList<Helpers_Sub_Bean> list = null;

	@SuppressWarnings("static-access")
	public HelperFragmentAdapter(Context context, ArrayList<Helpers_Sub_Bean> list) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		if (list == null) {
			this.list = new ArrayList<Helpers_Sub_Bean>();
		} else {
			this.list = list;
		}
	}

	// 当ListView数据发生变化时,调用此方法来更新ListView
	// @param list
	public void updateListView(ArrayList<Helpers_Sub_Bean> list) {
		HelperFragmentAdapter.list = list;
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
		final Helpers_Sub_Bean mContent = list.get(position);
		if (convertView == null) {
			// 初始化ViewHolder
			viewHolder = new viewHolder();
			// 把控件保存到ViewHolder中
			convertView = inflater.inflate(R.layout.helper_fragment_item, null);
			viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.helper_fragment_item_catalog);
			viewHolder.personalComment = (TextView) convertView.findViewById(R.id.helper_fragment_item_personalComment);
			viewHolder.name = (TextView) convertView.findViewById(R.id.helper_fragment_item_helperName);
			viewHolder.pic = (SnaImageViewV2) convertView.findViewById(R.id.helper_fragment_item_imageView_head);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (viewHolder) convertView.getTag();
		}
		if (mContent.getHelper_account_type() != -11111) {
			// 根据position获取分类的首字母的Char ascii值
			int section = getSectionForPosition(position);
			// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
			if (position == getPositionForSection(section)) {
				viewHolder.tvLetter.setVisibility(View.VISIBLE);
				viewHolder.tvLetter.setText(getAlpha(mContent.getHelper_name_pinyin()));
			} else {
				viewHolder.tvLetter.setVisibility(View.GONE);
			}
		} else {
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		try {
			viewHolder.name.setText(mContent.getHelper_name());
			//方法一：两者可以共存，且不会出现锯齿
//			viewHolder.name.getPaint().setStrikeThruText(true);//删除线
//			viewHolder.name.getPaint().setUnderlineText(true);//下划线
			//方法二：不可共存，后者将覆盖前者，会出现锯齿
//			viewHolder.name.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//删除线
//			viewHolder.name.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);//消除锯齿
//			viewHolder.name.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
			//方法二的共存方法：
//			viewHolder.name.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);//删除线+消除锯齿
//			viewHolder.name.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.UNDERLINE_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);//删除线+下划线+消除锯齿
			viewHolder.personalComment.setText(mContent.getHelper_sign());
			viewHolder.pic.setImageForShow(mContent.getHelper_photo(), SnaBitmap.NET_SMALL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	final class viewHolder {
		TextView tvLetter;
		TextView personalComment;
		TextView name;
		SnaImageViewV2 pic;
		ImageView line;
	}

	// 根据ListView的当前位置获取分类的首字母的Char ascii值
	@Override
	public int getSectionForPosition(int position) {
		int i = -1;
		String s = list.get(position).getHelper_name_pinyin();
		try {
			if (s.length() > 0) {
				i = list.get(position).getHelper_name_pinyin().substring(0, 1).toUpperCase().charAt(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	// 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	@Override
	public int getPositionForSection(int section) {
		try {
			for (int i = 0; i < getCount(); i++) {
				String s = list.get(i).getHelper_name_pinyin();
				if (s.length() > 0) {
					String sortStr = list.get(i).getHelper_name_pinyin().substring(0, 1).toUpperCase();
					char firstChar = sortStr.toUpperCase().charAt(0);
					if (firstChar == section) {
						return i;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}
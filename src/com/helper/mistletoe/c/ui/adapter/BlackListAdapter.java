package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.v.MyTextViewButton;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

@SuppressLint("DefaultLocale")
public class BlackListAdapter extends BaseAdapter implements SectionIndexer {
	private static ArrayList<Helpers_Sub_Bean> list = null;
	private static Context mContext;

	@SuppressWarnings("static-access")
	public BlackListAdapter(Context mContext, ArrayList<Helpers_Sub_Bean> list) {
		this.mContext = mContext;
		if (list == null) {
			this.list = new ArrayList<Helpers_Sub_Bean>();
		} else {
			this.list = list;
		}
	}

	// 当ListView数据发生变化时,调用此方法来更新ListView
	// @param list
	@SuppressWarnings("static-access")
	public void updateListView(ArrayList<Helpers_Sub_Bean> list) {
		this.list = list;
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final Helpers_Sub_Bean mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.black_helper_list_item, null);

			viewHolder.tvLetter = (TextView) view.findViewById(R.id.black_helper_list_item_catalog);
			viewHolder.siHead = (SnaImageViewV2) view.findViewById(R.id.black_helper_list_item_imageView_head);
			viewHolder.tvName = (TextView) view.findViewById(R.id.black_helper_list_item_textView_name);
			viewHolder.tvSign = (TextView) view.findViewById(R.id.black_helper_list_item_textView_sign);
			viewHolder.mybtRemoveBlack = (MyTextViewButton) view.findViewById(R.id.black_helper_list_item_myButton_remove);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(getAlpha(mContent.getHelper_name_pinyin().substring(0, 1)));
		} else {
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		viewHolder.tvName.setText(mContent.getHelper_name());
		viewHolder.tvSign.setText(mContent.getHelper_sign());
		if (mContent.getHelper_photo() != null) {
			if (!mContent.getHelper_photo().isEmpty()) {
				viewHolder.siHead.setImageForShow(Integer.valueOf(mContent.getHelper_photo()), SnaBitmap.NET_SMALL);
			} else {
				viewHolder.siHead.setBackgroundResource(R.drawable.default_head);
			}
		}
		viewHolder.mybtRemoveBlack.setIndex(position);
		viewHolder.mybtRemoveBlack.setOnClickListener(MyOnClickListener.getInstance());
		return view;
	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView tvName;
		TextView tvSign;
		SnaImageViewV2 siHead;
		MyTextViewButton mybtRemoveBlack;
	}

	// 用单例模式构造一个Listener
	private static class MyOnClickListener implements OnClickListener {

		private static MyOnClickListener instance = null;

		private MyOnClickListener() {
		}

		public static MyOnClickListener getInstance() {
			if (instance == null)
				instance = new MyOnClickListener();
			return instance;
		}

		@Override
		public void onClick(View view) {
			int index = ((MyTextViewButton) view).getIndex();
			// 发送解除黑名单指令
			Instruction_Utils.sendInstrustion(mContext, Instruction_Utils.REMOVE_BLACKLIST, list.get(index).getHelper_id());
		}
	}

	// 根据ListView的当前位置获取分类的首字母的Char ascii值

	@Override
	public int getSectionForPosition(int position) {
		return list.get(position).getHelper_name_pinyin().substring(0, 1).toUpperCase().charAt(0);
	}

	// 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getHelper_name_pinyin().substring(0, 1).toUpperCase();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
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
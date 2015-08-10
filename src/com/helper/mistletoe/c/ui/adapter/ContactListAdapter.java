package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.v.MyTextViewButton;
import com.helper.mistletoe.v.PhotoHead_TextView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class ContactListAdapter extends BaseAdapter implements SectionIndexer {
	private static List<Helpers_Sub_Bean> list = null;
	private static Context mContext;

	public ContactListAdapter(Context mContext, List<Helpers_Sub_Bean> list) {
		ContactListAdapter.mContext = mContext;
		if (list == null) {
			ContactListAdapter.list = new ArrayList<Helpers_Sub_Bean>();
		} else {
			ContactListAdapter.list = list;
		}
	}

	// 当ListView数据发生变化时,调用此方法来更新ListView
	// @param list
	public void updateListView(List<Helpers_Sub_Bean> list) {
		ContactListAdapter.list = list;
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
			view = LayoutInflater.from(mContext).inflate(R.layout.contact_helper_list_item, null);
			viewHolder.tvName = (TextView) view.findViewById(R.id.contact_helper_list_tiem_textView_name);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.contact_helper_list_tiem_catalog);
			viewHolder.tvAccount = (TextView) view.findViewById(R.id.contact_helper_list_tiem_textView_account);
			viewHolder.mybtAdd = (MyTextViewButton) view.findViewById(R.id.contact_helper_list_tiem_myButton_add);
			viewHolder.head = (PhotoHead_TextView) view.findViewById(R.id.contact_helper_list_tiem_photoHeadTextView);
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
		viewHolder.head.setBack(mContent);
		viewHolder.tvName.setText(mContent.getHelper_name().toString());
		viewHolder.tvAccount.setText(mContent.getHelper_account().toString());
		viewHolder.mybtAdd.setIndex(position);
		viewHolder.mybtAdd.setOnClickListener(MyOnClickListener.getInstance());
		return view;
	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView tvName;
		TextView tvAccount;
		MyTextViewButton mybtAdd;
		PhotoHead_TextView head;
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
			Helpers_Sub_Bean helper = list.get(index);
			if (helper.getHelper_account_type() == 0) {// 0为手机号
				Uri smsToUri = Uri.parse("smsto:" + helper.getHelper_account());
				Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
				intent.putExtra("sms_body", "我正在使用海豹ReadyGooo进行目标社交，你也快来试试吧！http://www.readygooo.com/readygooo/");
				mContext.startActivity(intent);
			} else if (helper.getHelper_account_type() == 1) {// 1为邮箱
				// 必须明确使用mailto前缀来修饰邮件地址,如果使用
				// intent.putExtra(Intent.EXTRA_EMAIL, email)，结果将匹配不到任何应用
				Uri uri = Uri.parse("mailto:" + helper.getHelper_account());
				String[] email = { helper.getHelper_account() };
				Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
				intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
				intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分"); // 主题
				intent.putExtra(Intent.EXTRA_TEXT, "我正在使用海豹ReadyGooo进行目标社交，你也快来试试吧！http://www.readygooo.com/readygooo/"); // 正文
				mContext.startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
			}
		}
	}

	// 根据ListView的当前位置获取分类的首字母的Char ascii值

	@Override
	public int getSectionForPosition(int position) {
		return list.get(position).getHelper_name_pinyin().substring(0, 1).charAt(0);
	}

	// 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getHelper_name_pinyin().substring(0, 1);
			char firstChar = sortStr.charAt(0);
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
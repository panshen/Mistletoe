//package com.helper.mistletoe.c.ui;
//
//import java.util.ArrayList;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.BaseAdapter;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.SectionIndexer;
//import android.widget.TextView;
//import com.helper.mistletoe.R;
//import com.helper.mistletoe.m.db.HelperManager;
//import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
//import com.helper.mistletoe.util.ThreadPoolUtils_db;
//import com.helper.mistletoe.v.ClearEditText;
//import com.helper.mistletoe.v.MyCheckBox;
//import com.helper.mistletoe.v.PhotoHead_TextView;
//import com.helper.mistletoe.v.SideBar;
//import com.helper.mistletoe.v.SideBar.OnTouchingLetterChangedListener;
//
//@SuppressLint("InflateParams")
//public class SelectCantactsActivity extends Activity {
//	private ListView select_lv;
//	private RelativeLayout back;
//	private ClearEditText mClearEditText;
//	private SideBar sideBar;
//	private TextView dialog, modification_tv, cancel_tv;
//	private static Context context;
//	private SelectCantactsListAdapter adapter;
//	private ArrayList<Helpers_Sub_Bean> SourceDateList;
//	private ReadContactsFromDBTask readContactsFromDBTask;
//	private static ArrayList<Integer> seleted_helperId,oldSeleted_helperId;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.select_cantacts_list);
//		context = this;
//		seleted_helperId = getMembers();
//		oldSeleted_helperId=getMembers();
//		initViews();
//	}
//
//	private ArrayList<Integer> getMembers() {
//		ArrayList<Integer> seletedHelperId = new ArrayList<Integer>();
//		seletedHelperId=getIntent().getIntegerArrayListExtra("seleted_helperId");
//		return seletedHelperId;
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		setData();
//		setlistener();
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//	}
//
//	private void initViews() {
//		back = (RelativeLayout) findViewById(R.id.select_cantacts_list_relativeLayout_back);
//		mClearEditText = (ClearEditText) findViewById(R.id.select_cantacts_list_filter_edit);
//		sideBar = (SideBar) findViewById(R.id.select_cantacts_list_relativeLayout_sidrbar);
//		select_lv = (ListView) findViewById(R.id.select_cantacts_list_relativeLayout_listview);
//		modification_tv = (TextView) findViewById(R.id.select_cantacts_list_textView_modification);
//		cancel_tv = (TextView) findViewById(R.id.select_cantacts_list_textView_cancel);
//		dialog = (TextView) findViewById(R.id.select_cantacts_list_relativeLayout_textview_dialog);
//		sideBar.setVisibility(SideBar.VISIBLE);
//		sideBar.setTextView(dialog);
//	}
//
//	private void setData() {
//		readContactsFromDBTask = new ReadContactsFromDBTask();
//		readContactsFromDBTask.executeOnExecutor(ThreadPoolUtils_db.threadPool);
//	}
//
//	class ReadContactsFromDBTask extends
//			AsyncTask<String, Integer, ArrayList<Helpers_Sub_Bean>> {
//
//		@Override
//		protected ArrayList<Helpers_Sub_Bean> doInBackground(String... params) {
//			ArrayList<Helpers_Sub_Bean> ShowDataList = new ArrayList<Helpers_Sub_Bean>();
//			HelperManager helperManager = new HelperManager();
//			ShowDataList = helperManager.ReadHelperFromDBByStatus(context,
//					new int[] { 4 });
//			return ShowDataList;
//		}
//
//		@Override
//		protected void onPostExecute(ArrayList<Helpers_Sub_Bean> result) {
//			super.onPostExecute(result);
//			display(result);
//		}
//	}
//
//	public void display(ArrayList<Helpers_Sub_Bean> SourceDateList) {
//		this.SourceDateList = SourceDateList;
//		adapter = new SelectCantactsListAdapter(context, SourceDateList);
//		select_lv.setAdapter(adapter);
//	}
//
//	private void setlistener() {
//		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
//
//			@Override
//			public void onTouchingLetterChanged(String s) {
//				if (null != adapter) {
//					int position = adapter.getPositionForSection(s.charAt(0));
//					if (position != -1) {
//						select_lv.setSelection(position);
//					}
//				}
//
//			}
//		});
//		back.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.putIntegerArrayListExtra("memberId", oldSeleted_helperId);
//				setResult(1, intent);
//				finish();
//			}
//		});
//		modification_tv.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Log.v("seleted_helperId",
//						"前----->" + seleted_helperId.toString());
//				// 传递
//				Intent intent = new Intent();
//				intent.putIntegerArrayListExtra("memberId", seleted_helperId);
//				setResult(1, intent);
//				finish();
//			}
//		});
//		cancel_tv.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.putIntegerArrayListExtra("memberId", oldSeleted_helperId);
//				setResult(1, intent);
//				finish();
//			}
//		});
//		mClearEditText.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//				filterData(s.toString());
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//			}
//		});
//	}
//
//	private void filterData(String filterStr) {
//		ArrayList<Helpers_Sub_Bean> filterDateList = new ArrayList<Helpers_Sub_Bean>();
//
//		if (TextUtils.isEmpty(filterStr)) {
//			filterDateList = SourceDateList;
//		} else {
//			filterDateList.clear();
//			for (Helpers_Sub_Bean sortModel : SourceDateList) {
//				String name = sortModel.getHelper_name();
//				String account = sortModel.getHelper_account();
//				if (name.indexOf(filterStr.toString()) != -1
//						|| sortModel.getHelper_name_pinyin().startsWith(
//								filterStr.toString())
//						|| name.startsWith(filterStr.toString())
//						|| account.startsWith(filterStr.toString())
//						|| account.equalsIgnoreCase(filterStr.toString())) {
//					filterDateList.add(sortModel);
//				}
//			}
//		}
//		adapter.updateListView(filterDateList);
//	}
//
//	static class SelectCantactsListAdapter extends BaseAdapter implements
//			SectionIndexer {
//		private static ArrayList<Helpers_Sub_Bean> list = null;
//		private Context mContext;
//
//		public SelectCantactsListAdapter(Context mContext,
//				ArrayList<Helpers_Sub_Bean> list) {
//			this.mContext = mContext;
//			if (list == null) {
//				SelectCantactsListAdapter.list = new ArrayList<Helpers_Sub_Bean>();
//			} else {
//				SelectCantactsListAdapter.list = list;
//			}
//		}
//
//		// 当ListView数据发生变化时,调用此方法来更新ListView
//		// @param list
//		public void updateListView(ArrayList<Helpers_Sub_Bean> list) {
//			SelectCantactsListAdapter.list = list;
//			notifyDataSetChanged();
//		}
//
//		@Override
//		public int getCount() {
//			return SelectCantactsListAdapter.list.size();
//		}
//
//		@Override
//		public Helpers_Sub_Bean getItem(int position) {
//			return list.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView(final int position, View view, ViewGroup arg2) {
//			ViewHolder viewHolder = null;
//			final Helpers_Sub_Bean mContent = list.get(position);
//			if (view == null) {
//				viewHolder = new ViewHolder();
//				view = LayoutInflater.from(mContext).inflate(
//						R.layout.select_cantacts_list_item, null);
//				viewHolder.tvName = (TextView) view
//						.findViewById(R.id.select_cantacts_list_item_textView_name);
//				viewHolder.tvLetter = (TextView) view
//						.findViewById(R.id.select_cantacts_list_item_textView_catalog);
//				viewHolder.tvAccount = (TextView) view
//						.findViewById(R.id.select_cantacts_list_item_textView_account);
//				viewHolder.head = (PhotoHead_TextView) view
//						.findViewById(R.id.select_cantacts_list_item_photoHeadTextView);
//				viewHolder.myCb = (MyCheckBox) view
//						.findViewById(R.id.select_cantacts_list_item_checkBox);
//				view.setTag(viewHolder);
//			} else {
//				viewHolder = (ViewHolder) view.getTag();
//			}
//			// 根据position获取分类的首字母的Char ascii值
//			int section = getSectionForPosition(position);
//			// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
//			if (position == getPositionForSection(section)) {
//				viewHolder.tvLetter.setVisibility(View.VISIBLE);
//				viewHolder.tvLetter.setText(getAlpha(mContent.getHelper_name_pinyin().substring(0, 1)));
//			} else {
//				viewHolder.tvLetter.setVisibility(View.GONE);
//			}
//			viewHolder.head.setBack(mContent);
//			viewHolder.tvName.setText(mContent.getHelper_name());
//			viewHolder.tvAccount.setText(mContent.getHelper_account());
//
//			viewHolder.myCb.setIndex(position);
//			viewHolder.myCb.setOnCheckedChangeListener(MyOnClickListener
//					.getInstance());
//			if (seleted_helperId.contains(mContent.getHelper_id())) {
//				viewHolder.myCb.setChecked(true);
//			} else {
//				viewHolder.myCb.setChecked(false);
//			}
//			return view;
//		}
//
//		class ViewHolder {
//			TextView tvLetter;
//			TextView tvName;
//			TextView tvAccount;
//			PhotoHead_TextView head;
//			MyCheckBox myCb;
//		}
//
//		// 用单例模式构造一个Listener
//		static class MyOnClickListener implements OnCheckedChangeListener {
//
//			private static MyOnClickListener instance = null;
//
//			private MyOnClickListener() {
//			}
//
//			public static MyOnClickListener getInstance() {
//				if (instance == null)
//					instance = new MyOnClickListener();
//				return instance;
//			}
//
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView,
//					boolean isChecked) {
//				// TODO Auto-generated method stub
//				int index = ((MyCheckBox) buttonView).getIndex();
//				Integer id = list.get(index).getHelper_id();
//				if (isChecked) { // 被选中
//					if (!seleted_helperId.contains(id)) {
//						seleted_helperId.add(id);
//					}
//				} else {// 被取消
//					seleted_helperId.remove(id);
//				}
//			}
//		}
//
//		// 根据ListView的当前位置获取分类的首字母的Char ascii值
//		@Override
//		public int getSectionForPosition(int position) {
//			int i = -1;
//			String s = list.get(position).getHelper_name_pinyin();
//			try {
//				if (s.length() > 0) {
//					i = list.get(position).getHelper_name_pinyin()
//							.substring(0, 1).toUpperCase().charAt(0);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return i;
//		}
//
//		// 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
//		@Override
//		public int getPositionForSection(int section) {
//			try {
//				for (int i = 0; i < getCount(); i++) {
//					String s = list.get(i).getHelper_name_pinyin();
//					if (s.length() > 0) {
//						String sortStr = list.get(i).getHelper_name_pinyin()
//								.substring(0, 1).toUpperCase();
//						char firstChar = sortStr.toUpperCase().charAt(0);
//						if (firstChar == section) {
//							return i;
//						}
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return -1;
//		}
//
//		/**
//		 * 提取英文的首字母，非英文字母用#代替。
//		 * 
//		 * @param str
//		 * @return
//		 */
//		private String getAlpha(String str) {
//			String sortStr = str.trim().substring(0, 1).toUpperCase();
//			// 正则表达式，判断首字母是否是英文字母
//			if (sortStr.matches("[A-Z]")) {
//				return sortStr;
//			} else {
//				return "#";
//			}
//		}
//
//		@Override
//		public Object[] getSections() {
//			return null;
//		}
//	}
// }

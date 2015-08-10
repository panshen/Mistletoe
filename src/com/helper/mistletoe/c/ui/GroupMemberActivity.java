package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import com.helper.mistletoe.R;
import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.db.GroupManager;
import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.ThreadPoolUtils_db;
import com.helper.mistletoe.util.Tool_Utils;
import com.helper.mistletoe.v.ClearEditText;
import com.helper.mistletoe.v.MyCheckBox;
import com.helper.mistletoe.v.PhotoHead_TextView;
import com.helper.mistletoe.v.SideBar;
import com.helper.mistletoe.v.SideBar.OnTouchingLetterChangedListener;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;

@SuppressLint("InflateParams")
public class GroupMemberActivity extends PrivateBasicActivity {
	private ListView select_lv;
	private RelativeLayout back, menu;
	private ClearEditText mClearEditText;
	private SideBar sideBar;
	private LinearLayout modification_linearLayout;
	private TextView dialog, modification_tv, cancel_tv, title;
	private static Context context;
	private SelectGroupMemberListAdapter adapter;
	private ArrayList<Helpers_Sub_Bean> SourceDateList;
	private ReadGroupMemberFromDBTask readGroupMemberFromDBTask;
	private SelectGroupMemberTask selectGroupMemberTask;
	private static ArrayList<Integer> seleted;
	private Helpers_Sub_Bean helper;
	private static Boolean show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.group_member_list);
		context = this;
		show = false;
		helper = getIntent().getParcelableExtra("group");
		// 发送同步单个group的Member的指令
		Instruction_Utils.sendInstrustion(context.getApplicationContext(), Instruction_Utils.SYNCHRONOUS_GROUP_MEMBER, helper.getHelper_id());
		initViews();
		CreateMenu(menu);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setData();
		setlistener();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void dealWithRadio(Intent intent) {
		super.dealWithRadio(intent);
		String action = intent.getAction();
		if (action.equals(MessageConstants.REFRESH_GROUP)) {
			int id = intent.getIntExtra("group_id", -1);
			if (id == helper.getHelper_id()) {
				Log.v("广播", "子类中" + id);
				setData();
			}
		}
	}

	private void initViews() {
		back = (RelativeLayout) findViewById(R.id.group_member_list_relativeLayout_back);
		menu = (RelativeLayout) findViewById(R.id.group_member_list_title_relativeLayout_menu);
		mClearEditText = (ClearEditText) findViewById(R.id.group_member_list_filter_edit);
		sideBar = (SideBar) findViewById(R.id.group_member_list_relativeLayout_sidrbar);
		select_lv = (ListView) findViewById(R.id.group_member_list_relativeLayout_listview);
		modification_linearLayout = (LinearLayout) findViewById(R.id.group_member_list_linearLayout_modification);
		modification_tv = (TextView) findViewById(R.id.group_member_list_textView_modification);
		cancel_tv = (TextView) findViewById(R.id.group_member_list_textView_cancel);
		dialog = (TextView) findViewById(R.id.group_member_list_relativeLayout_textview_dialog);
		title = (TextView) findViewById(R.id.group_member_list_textview_title);
		title.setText(helper.getHelper_name());
		sideBar.setVisibility(SideBar.VISIBLE);
		sideBar.setTextView(dialog);
		seleted = new ArrayList<Integer>();
	}

	private void setData() {
		modification_linearLayout.setVisibility(LinearLayout.GONE);
		readGroupMemberFromDBTask = new ReadGroupMemberFromDBTask();
		readGroupMemberFromDBTask.executeOnExecutor(ThreadPoolUtils_db.threadPool, helper.getHelper_id());
	}

	private class ReadGroupMemberFromDBTask extends AsyncTask<Integer, Integer, ArrayList<Helpers_Sub_Bean>> {

		@Override
		protected ArrayList<Helpers_Sub_Bean> doInBackground(Integer... params) {
			Integer[] group_id = new Integer[] { params[0] };

			ArrayList<Helpers_Sub_Bean> helperList = new ArrayList<Helpers_Sub_Bean>();
			GroupManager groupManager = new GroupManager();
			helperList = groupManager.ReadGroupMemberListFromDBByGroupId(context, group_id);
			seleted.clear();
			if (helperList.size() > 0) {
				for (int i = 0; i < helperList.size(); i++) {
					seleted.add(helperList.get(i).getHelper_id());
				}
			}
			return helperList;
		}

		@Override
		protected void onPostExecute(ArrayList<Helpers_Sub_Bean> result) {
			super.onPostExecute(result);
			if (result == null) {
				Tool_Utils.showInfo(context, "该组没有成员");
			} else {
				display(result);
			}

		}
	}

	private void selectGroupMemberData() {
		modification_linearLayout.setVisibility(LinearLayout.VISIBLE);
		selectGroupMemberTask = new SelectGroupMemberTask();
		selectGroupMemberTask.executeOnExecutor(ThreadPoolUtils_db.threadPool);
	}

	public class SelectGroupMemberTask extends AsyncTask<String, Integer, ArrayList<Helpers_Sub_Bean>> {

		@Override
		protected ArrayList<Helpers_Sub_Bean> doInBackground(String... params) {
			ArrayList<Helpers_Sub_Bean> ListHelper = new ArrayList<Helpers_Sub_Bean>();
			HelperManager helperManager = new HelperManager();
			ListHelper = helperManager.ReadHelperFromDBByStatus(context, new int[] { 2, 3, 9 });
			return ListHelper;
		}

		@Override
		protected void onPostExecute(ArrayList<Helpers_Sub_Bean> result) {
			super.onPostExecute(result);
			display(result);
		}
	}

	public void display(ArrayList<Helpers_Sub_Bean> SourceDateList) {
		this.SourceDateList = SourceDateList;
		adapter = new SelectGroupMemberListAdapter(context, SourceDateList);
		select_lv.setAdapter(adapter);
	}

	private void setlistener() {
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				if (null != adapter) {
					int position = adapter.getPositionForSection(s.charAt(0));
					if (position != -1) {
						select_lv.setSelection(position);
					}
				}

			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		select_lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if (show) {
					show = false;
					setData();
				} else {
					show = true;
					selectGroupMemberData();
				}
				return false;
			}
		});
		modification_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Group_Bean group_Bean = new Group_Bean();
				group_Bean.setGroup_id(helper.getHelper_id());
				String json = MyJsonWriter.getJsonDataForGroup(seleted);
				group_Bean.setGroup_memberIds(json);
				// 发送修改组成员的指令
				Instruction_Utils.sendInstrustion(context.getApplicationContext(), Instruction_Utils.UPDATE_GROUP_MEMBER, group_Bean);
				modification_linearLayout.setVisibility(LinearLayout.GONE);
				show = false;
				setData();
			}
		});
		cancel_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				modification_linearLayout.setVisibility(LinearLayout.GONE);
				show = false;
				setData();
			}
		});
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private void CreateMenu(RelativeLayout menu) {
		try {
			final PopupMenu popup = new PopupMenu(context, menu);
			popup.getMenuInflater().inflate(R.menu.group_member_menu, popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {
					switch (item.getItemId()) {
					case R.id.updateInformation:// 修改组信息
						if (show) {
							show = false;
							setData();
						}
						Intent intent_group = new Intent(context, GroupUpdateDialogActivity.class);
						intent_group.putExtra("group", helper);
						context.startActivity(intent_group);
						break;
					case R.id.updateMember:// 修改组成员
						if (show) {
							show = false;
							setData();
						} else {
							show = true;
							selectGroupMemberData();
						}
						break;

					default:
						break;
					}
					return true;
				}
			});
			menu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					popup.show();
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void filterData(String filterStr) {
		ArrayList<Helpers_Sub_Bean> filterDateList = new ArrayList<Helpers_Sub_Bean>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (Helpers_Sub_Bean sortModel : SourceDateList) {
				String name = sortModel.getHelper_name();
				String account = sortModel.getHelper_account();
				if (name.indexOf(filterStr.toString()) != -1 || sortModel.getHelper_name_pinyin().startsWith(filterStr.toString()) || name.startsWith(filterStr.toString())
						|| account.startsWith(filterStr.toString()) || account.equalsIgnoreCase(filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}
		adapter.updateListView(filterDateList);
	}

	static class SelectGroupMemberListAdapter extends BaseAdapter implements SectionIndexer {
		private static ArrayList<Helpers_Sub_Bean> list = null;
		private Context mContext;

		public SelectGroupMemberListAdapter(Context mContext, ArrayList<Helpers_Sub_Bean> list) {
			this.mContext = mContext;
			if (list == null) {
				SelectGroupMemberListAdapter.list = new ArrayList<Helpers_Sub_Bean>();
			} else {
				SelectGroupMemberListAdapter.list = list;
			}
		}

		// 当ListView数据发生变化时,调用此方法来更新ListView
		// @param list
		public void updateListView(ArrayList<Helpers_Sub_Bean> list) {
			SelectGroupMemberListAdapter.list = list;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return SelectGroupMemberListAdapter.list.size();
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
				view = LayoutInflater.from(mContext).inflate(R.layout.group_member_list_item, null);
				viewHolder.tvName = (TextView) view.findViewById(R.id.group_member_list_tiem_textView_name);
				viewHolder.tvLetter = (TextView) view.findViewById(R.id.group_member_list_tiem_catalog);
				viewHolder.tvAccount = (TextView) view.findViewById(R.id.group_member_list_tiem_textView_account);
				viewHolder.head = (SnaImageViewV2) view.findViewById(R.id.group_member_list_tiem_head);
				viewHolder.myCb = (MyCheckBox) view.findViewById(R.id.group_member_list_tiem_checkBox);
				viewHolder.cb = (RelativeLayout) view.findViewById(R.id.group_member_list_tiem_relativeLayout_checkBox);
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
			viewHolder.head.setImageForShow(mContent.getHelper_photo(), SnaBitmap.NET_SMALL);
			viewHolder.tvName.setText(mContent.getHelper_name());
			viewHolder.tvAccount.setText(mContent.getHelper_account());
			if (show) {
				viewHolder.cb.setVisibility(RelativeLayout.VISIBLE);
			} else {
				viewHolder.cb.setVisibility(RelativeLayout.GONE);
			}
			viewHolder.myCb.setIndex(position);
			viewHolder.myCb.setOnCheckedChangeListener(MyOnClickListener.getInstance());
			if (seleted.contains(mContent.getHelper_id())) {
				viewHolder.myCb.setChecked(true);
			} else {
				viewHolder.myCb.setChecked(false);
			}
			return view;
		}

		class ViewHolder {
			TextView tvLetter;
			TextView tvName;
			TextView tvAccount;
			SnaImageViewV2 head;
			MyCheckBox myCb;
			RelativeLayout cb;
		}

		// 用单例模式构造一个Listener
		static class MyOnClickListener implements OnCheckedChangeListener {

			private static MyOnClickListener instance = null;

			private MyOnClickListener() {
			}

			public static MyOnClickListener getInstance() {
				if (instance == null)
					instance = new MyOnClickListener();
				return instance;
			}

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				int index = ((MyCheckBox) buttonView).getIndex();
				Integer id = list.get(index).getHelper_id();
				if (isChecked) { // 被选中
					if (!seleted.contains(id)) {
						seleted.add(id);
					}
				} else {// 被取消
					seleted.remove(id);
				}
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
}

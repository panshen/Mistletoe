package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.ThreadPoolUtils_db;
import com.helper.mistletoe.util.Tool_Utils;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.v.ClearEditText;
import com.helper.mistletoe.v.HorizontalListView;
import com.helper.mistletoe.v.HorizontalListViewAdapter;
import com.helper.mistletoe.v.MyCheckBox;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class SelectScheduleMember_newActivity extends Activity {
	private ListView All_lv;
	private RelativeLayout back,sure;
	private TextView tv;
	private static HorizontalListView hListView;
	private static HorizontalListViewAdapter hListViewAdapter;
	private ClearEditText mClearEditText;
	private static Context context;
	private SelectGroupMemberListAdapter adapter;
	private ArrayList<Helpers_Sub_Bean> All_list;
	private static ArrayList<Helpers_Sub_Bean> Selected_List;
	private static ArrayList<Integer> Selected_List_ID=new ArrayList<Integer>();
	private ReadHelperAndGroupFromDBTask readHelperAndGroupFromDBTask;
	private ArrayList<Integer> schedule_helperId;
	private static Integer targetId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select_schedule_member_list);
		context = this;
		schedule_helperId = getMembers();
		initViews();
	}

	// 获取传递过来的members
	private ArrayList<Integer> getMembers() {
		schedule_helperId = new ArrayList<Integer>();// 确保seleted_helperId永不为null
		schedule_helperId = getIntent().getIntegerArrayListExtra("helper_id");
		targetId=getIntent().getIntExtra("targetId", -1);
		return schedule_helperId;
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

	private void initViews() {
		mClearEditText = (ClearEditText) findViewById(R.id.select_schedule_member_list_filter_edit);
		All_lv = (ListView) findViewById(R.id.select_schedule_member_list_listview);
		back = (RelativeLayout) findViewById(R.id.select_schedule_member_list_relativeLayout_back);
		sure = (RelativeLayout) findViewById(R.id.select_schedule_member_list_relativeLayout_sure);
		tv=(TextView) findViewById(R.id.select_schedule_member_list_textView);
		hListView=(HorizontalListView) findViewById(R.id.select_schedule_member_list_horizontalListView);
		hListView.setVisibility(HorizontalListView.GONE);
		Selected_List=new ArrayList<Helpers_Sub_Bean>();
		hListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(),Selected_List);
		hListView.setAdapter(hListViewAdapter);
	}

	private void setData() {
		readHelperAndGroupFromDBTask = new ReadHelperAndGroupFromDBTask();
		readHelperAndGroupFromDBTask.executeOnExecutor(ThreadPoolUtils_db.threadPool);
	}

	class ReadHelperAndGroupFromDBTask extends AsyncTask<String, Integer, ArrayList<Helpers_Sub_Bean>> {

		@Override
		protected ArrayList<Helpers_Sub_Bean> doInBackground(String... params) {
			ArrayList<Helpers_Sub_Bean> ShowDataList = new ArrayList<Helpers_Sub_Bean>();
			Integer[] helperId = null;
			if (schedule_helperId == null) {
				schedule_helperId = new ArrayList<Integer>();
			}
			if (schedule_helperId.size() > 0) {
				User_Bean user = new User_Bean();
				try {
					user.readData(getApplicationContext());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (schedule_helperId.contains(user.getUser_id())) {
					schedule_helperId.remove(user.getUser_id());
				}
				helperId = new Integer[schedule_helperId.size()];
				for (int i = 0; i < schedule_helperId.size(); i++) {
					helperId[i] = schedule_helperId.get(i);
				}
			}
			HelperManager helperManager = new HelperManager();
			ShowDataList = helperManager.ReadGroupMemberList(context, helperId);
			return ShowDataList;
		}

		@Override
		protected void onPostExecute(ArrayList<Helpers_Sub_Bean> result) {
			super.onPostExecute(result);
			display(result);
		}
	}

	public void display(ArrayList<Helpers_Sub_Bean> SourceDateList) {
		this.All_list = SourceDateList;
		adapter = new SelectGroupMemberListAdapter(context, SourceDateList);
		All_lv.setAdapter(adapter);
	}

	private void setlistener() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// setResult(1, intent);
				finish();
			}
		});
		sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Selected_List_ID.clear();
				if (Selected_List.size()>0) {
					for (int i = 0; i < Selected_List.size(); i++) {
						Selected_List_ID.add(Selected_List.get(i).getHelper_id());
					}
				}
				String content=tv.getText().toString();
				if (content.trim().equals("")) {
					Tool_Utils.showInfo(context, "你还没有说话!");
				}else{
					Schedule_Bean.sendSchedule_Text(context, targetId, Transformation_Util.ArrayList$Integer2JSONArray(Selected_List_ID), content);
					finish();	
				}
			}
		});
		hListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Selected_List.remove(parent.getAdapter().getItem(position));
			if (Selected_List.size()>0) {
				hListView.setVisibility(HorizontalListView.VISIBLE);
				hListViewAdapter.notifyDataSetChanged();
			}else {
				hListView.setVisibility(HorizontalListView.GONE);
			}
			adapter.notifyDataSetChanged();
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

	private void filterData(String filterStr) {
		ArrayList<Helpers_Sub_Bean> filterDateList = new ArrayList<Helpers_Sub_Bean>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = All_list;
		} else {
			filterDateList.clear();
			for (Helpers_Sub_Bean sortModel : All_list) {
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
				view = LayoutInflater.from(mContext).inflate(R.layout.select_group_member_list_item, null);
				viewHolder.tvName = (TextView) view.findViewById(R.id.select_group_member_list_tiem_textView_name);
				viewHolder.tvLetter = (TextView) view.findViewById(R.id.select_group_member_list_tiem_catalog);
				viewHolder.tvAccount = (TextView) view.findViewById(R.id.select_group_member_list_tiem_textView_account);
				viewHolder.head = (SnaImageViewV2) view.findViewById(R.id.select_group_member_list_tiem_head);
				viewHolder.myCb = (MyCheckBox) view.findViewById(R.id.select_group_member_list_tiem_checkBox);
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
			viewHolder.tvName.setText(mContent.getHelper_name().toString());
			if (mContent.getHelper_status() == 3 || mContent.getHelper_status() == 9) {
				viewHolder.tvAccount.setText(mContent.getHelper_sign());
			} else {
				viewHolder.tvAccount.setText(mContent.getHelper_account());
			}
			viewHolder.myCb.setIndex(position);
			viewHolder.myCb.setOnCheckedChangeListener(MyOnClickListener.getInstance());
			if (Selected_List.contains(mContent)) {
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
				if (isChecked) { // 被选中
					if (!Selected_List.contains(list.get(index))) {
						Selected_List.add(list.get(index));
					}
				} else {// 被取消
					Selected_List.remove(list.get(index));
				}
				if (Selected_List.size()>0) {
					hListView.setVisibility(HorizontalListView.VISIBLE);
					hListViewAdapter.notifyDataSetChanged();
				}else {
					hListView.setVisibility(HorizontalListView.GONE);
				}
			}
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
}

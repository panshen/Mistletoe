package com.helper.mistletoe.c.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.BlackHelperActivity;
import com.helper.mistletoe.c.ui.ContactHelperActivity;
import com.helper.mistletoe.c.ui.GroupListDialogActivity;
import com.helper.mistletoe.c.ui.GroupMemberActivity;
import com.helper.mistletoe.c.ui.GroupUpdateDialogActivity;
import com.helper.mistletoe.c.ui.HelperDetailsActivity;
import com.helper.mistletoe.c.ui.NewHelperActivity;
import com.helper.mistletoe.c.ui.RecommendHelperActivity;
import com.helper.mistletoe.c.ui.SelectGroupMemberActivity;
import com.helper.mistletoe.c.ui.Target_Create_Activity;
import com.helper.mistletoe.c.ui.TaskCreateDialogActivity;
import com.helper.mistletoe.c.ui.adapter.HelperFragmentAdapter;
import com.helper.mistletoe.m.db.GroupManager;
import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.pojo.AppNote_Bean;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.ThreadPoolUtils_db;
import com.helper.mistletoe.v.SideBar;
import com.helper.mistletoe.v.SideBar.OnTouchingLetterChangedListener;
import com.helper.mistletoe.v.redpoint.ReadPoint;

@SuppressLint("InflateParams")
public class HelperFragment extends BaseFragment implements
		OnItemClickListener, OnItemLongClickListener {
	private ListView helper_lv;
	private SideBar sideBar;
	private TextView dialog;
	private HelperFragmentAdapter adapter;
	private ArrayList<Helpers_Sub_Bean> helperList;
	private ReadHelperAndGroupFromDBTask readDataTask;
	private ReadNewHelperFromDBTask readNewHelperCountTask;
	private IntentFilter myIntentFilter;
	private int HeaderSize = 3;
	ReadPoint readPoint;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.helper_fragment, null);
		// 发送同步helper与group指令
		Instruction_Utils.sendInstrustion(
				getActivity().getApplicationContext(),
				Instruction_Utils.SYNCHRONOUS_HELPER_AND_GROUP);
		setUpView(v);
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(MessageConstants.REFRESH_HELPER);
		myIntentFilter.addAction(MessageConstants.REFRESH_GROUP);
		// 注册广播
		getActivity().registerReceiver(IntentReceicer, myIntentFilter);
		setData();
		setListener();
		getNewHelperCount();
	}

	@Override
	public void onPause() {
		super.onPause();
		// 注销广播
		getActivity().unregisterReceiver(IntentReceicer);
	}

	public void setData() {
		readDataTask = new ReadHelperAndGroupFromDBTask();
		readDataTask.executeOnExecutor(ThreadPoolUtils_db.threadPool);
	}

	class ReadHelperAndGroupFromDBTask extends
			AsyncTask<String, Integer, ArrayList<Helpers_Sub_Bean>> {

		@Override
		protected ArrayList<Helpers_Sub_Bean> doInBackground(String... params) {
			ArrayList<Helpers_Sub_Bean> ShowDataList = new ArrayList<Helpers_Sub_Bean>();
			HelperManager helperManager = new HelperManager();
			ShowDataList = helperManager
					.ReadHelperAndGroupDataFromDB(getActivity()
							.getApplicationContext());
			return ShowDataList;
		}

		@Override
		protected void onPostExecute(ArrayList<Helpers_Sub_Bean> result) {
			super.onPostExecute(result);
			display(result);
		}
	}

	public void display(ArrayList<Helpers_Sub_Bean> helperLis) {
		helperList.clear();
		helperList.addAll(helperLis);
		if (adapter == null) {
			adapter = new HelperFragmentAdapter(getActivity(), helperList);
			helper_lv.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
	}

	private void setListener() {
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				if (null != adapter) {
					int position = adapter.getPositionForSection(s.charAt(0));
					if (position != -1) {
						helper_lv.setSelection(position + HeaderSize);
					}
				}
			}
		});
	}

	// 长按组后的操作，即操作组
	protected void operationGroup(final Helpers_Sub_Bean helper) {
		AlertDialog dlg = new AlertDialog.Builder(getActivity())
				.setTitle("选项")
				.setItems(R.array.OperationGroupLongClick,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case 0:// 创建目标
									GroupManager groupManager = new GroupManager();
									Integer[] memberIds = groupManager
											.getGroupMemberIdByGroupId(
													getActivity(),
													new Integer[] { helper
															.getHelper_id() });
									ArrayList<Integer> seleted_helperId = new ArrayList<Integer>();
									if (memberIds != null) {
										if (memberIds.length > 0) {
											for (int i = 0; i < memberIds.length; i++) {
												if (!seleted_helperId
														.contains(memberIds[i])
														&& memberIds[i] != -1) {
													seleted_helperId
															.add(memberIds[i]);
												}
											}

										}
									}
									Intent intent_target = new Intent(
											getActivity(),
											Target_Create_Activity.class);
									intent_target.putIntegerArrayListExtra(
											"memberId", seleted_helperId);
									getActivity().startActivityForResult(
											intent_target, 6);
									break;
								case 1:// 修改组信息
									Intent intent_group = new Intent(
											getActivity(),
											GroupUpdateDialogActivity.class);
									intent_group.putExtra("group", helper);
									getActivity().startActivityForResult(
											intent_group, 7);
									break;
								case 2:// 删除组
										// 发送删除组的指令
									Instruction_Utils.sendInstrustion(
											getActivity()
													.getApplicationContext(),
											Instruction_Utils.DELETE_GROUP,
											helper.getHelper_id());
								}
							}
						})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).create();
		dlg.show();
	}

	// 长按帮手后的操作，即操作帮手
	protected void operationHelper(final Helpers_Sub_Bean helper) {
		AlertDialog dlg = new AlertDialog.Builder(getActivity())
				.setTitle("选项")
				.setItems(R.array.OperationHelperLongClick,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case 0:// 创建目标
									ArrayList<Integer> seleted_helperId = new ArrayList<Integer>();
									if (!seleted_helperId.contains(helper
											.getHelper_id())
											&& helper.getHelper_id() != -1) {
										seleted_helperId.add(helper
												.getHelper_id());
									}
									Intent intent_target = new Intent(
											getActivity()
													.getApplicationContext(),
											Target_Create_Activity.class);
									intent_target.putIntegerArrayListExtra(
											"memberId", seleted_helperId);
									getActivity().startActivity(intent_target);
									break;
								case 1:// 加入组
									Intent intent_group = new Intent(
											getActivity()
													.getApplicationContext(),
											GroupListDialogActivity.class);
									intent_group.putExtra("helper_id",
											helper.getHelper_id());
									getActivity().startActivity(intent_group);
									break;
								case 2:// 加入黑名单
										// 发送拉黑指令
									Instruction_Utils.sendInstrustion(
											getActivity()
													.getApplicationContext(),
											Instruction_Utils.PULL_BACK, helper
													.getHelper_id());
									break;
								}
							}
						})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).create();
		dlg.show();
	}

	private void setUpView(View v) {
		dialog = (TextView) v
				.findViewById(R.id.helper_fragment_textview_dialog);
		sideBar = (SideBar) v.findViewById(R.id.helper_fragment_sidrbar);
		sideBar.setVisibility(SideBar.VISIBLE);
		sideBar.setTextView(dialog);
		helper_lv = (ListView) v.findViewById(R.id.helper_fragment_listview);
		initializeHeader();
		helperList = new ArrayList<Helpers_Sub_Bean>();
		adapter = new HelperFragmentAdapter(getActivity(), helperList);
		helper_lv.setAdapter(adapter);
		helper_lv.requestFocusFromTouch();
		helper_lv.setOnItemClickListener(this);
		helper_lv.setOnItemLongClickListener(this);
	}

	@Override
	public void onSearch(String filterStr) {
		ArrayList<Helpers_Sub_Bean> filterDateList = new ArrayList<Helpers_Sub_Bean>();
		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = helperList;
		} else {
			filterDateList.clear();
			for (Helpers_Sub_Bean sortModel : helperList) {
				String name = sortModel.getHelper_name();
				if (name.indexOf(filterStr.toString()) != -1
						|| sortModel.getHelper_name_pinyin().startsWith(
								filterStr.toString())
						|| name.equalsIgnoreCase(filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}
		adapter.updateListView(filterDateList);
		helper_lv.setSelectionFromTop(HeaderSize, 0);
	}

	@Override
	public void onAdd() {
		Intent intent = new Intent(getActivity(), NewHelperActivity.class);
		startActivityForResult(intent, 10);

	}

	@Override
	public void onMenu(RelativeLayout menu) {
		try {
			final PopupMenu popup = new PopupMenu(getActivity()
					.getApplicationContext(), menu);
			popup.getMenuInflater().inflate(R.menu.helper_fragment_menu,
					popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {
					switch (item.getItemId()) {
					case R.id.create_newGroup:// 创建新群组
						Intent intent_selectGroupMember = new Intent(
								getActivity().getApplicationContext(),
								SelectGroupMemberActivity.class);
						getActivity().startActivity(intent_selectGroupMember);
						break;
					case R.id.my_blackList:// 我的黑名单
						Intent intent_blackHelper = new Intent(getActivity()
								.getApplicationContext(),
								BlackHelperActivity.class);
						getActivity().startActivity(intent_blackHelper);
						break;

					default:
						break;
					}
					return true;
				}
			});
			popup.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private BroadcastReceiver IntentReceicer = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(MessageConstants.REFRESH_GROUP)
					|| action.equals(MessageConstants.REFRESH_HELPER)) {
				setData();
			}
		}
	};

	// 初始化Listview的head
	private void initializeHeader() {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View convertView_NewHelper = inflater.inflate(
				R.layout.helper_fragment_item_group, null);
		ImageView iv_newHelper = (ImageView) convertView_NewHelper
				.findViewById(R.id.helperfragment_item_group_imageView);
		iv_newHelper.setImageResource(R.drawable.new_helper_round);
		TextView tv_newHelper = (TextView) convertView_NewHelper
				.findViewById(R.id.helperfragment_item_group_textView);
		readPoint = (ReadPoint) convertView_NewHelper
				.findViewById(R.id.helperfragment_item_group_readPoint);
		try {
			readPoint.setNumber(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tv_newHelper.setText("新帮手");
		helper_lv.addHeaderView(convertView_NewHelper);
		View convertView_Recommend = inflater.inflate(
				R.layout.helper_fragment_item_group, null);
		ImageView iv_recommend = (ImageView) convertView_Recommend
				.findViewById(R.id.helperfragment_item_group_imageView);
		iv_recommend.setImageResource(R.drawable.recommend_helper_round);
		TextView tv_recommend = (TextView) convertView_Recommend
				.findViewById(R.id.helperfragment_item_group_textView);
		tv_recommend.setText("推荐帮手");
		helper_lv.addHeaderView(convertView_Recommend);
		View convertView_Contact = inflater.inflate(
				R.layout.helper_fragment_item_group, null);
		ImageView iv_contact = (ImageView) convertView_Contact
				.findViewById(R.id.helperfragment_item_group_imageView);
		iv_contact.setImageResource(R.drawable.contact_helper_round);
		TextView tv_contact = (TextView) convertView_Contact
				.findViewById(R.id.helperfragment_item_group_textView);
		tv_contact.setText("通讯录");
		helper_lv.addHeaderView(convertView_Contact);
	}

	public void getNewHelperCount() {
		readNewHelperCountTask = new ReadNewHelperFromDBTask();
		readNewHelperCountTask.executeOnExecutor(ThreadPoolUtils_db.threadPool);
	}

	public class ReadNewHelperFromDBTask extends
			AsyncTask<String, Integer, ArrayList<Helpers_Sub_Bean>> {

		@Override
		protected ArrayList<Helpers_Sub_Bean> doInBackground(String... params) {
			ArrayList<Helpers_Sub_Bean> ListHelper = new ArrayList<Helpers_Sub_Bean>();
			HelperManager helperManager = new HelperManager();
			ListHelper = helperManager.ReadHelperFromDBByStatus(
					getApplicationContext(), new int[] { 1, 3, 5 });
			return ListHelper;
		}

		@Override
		protected void onPostExecute(ArrayList<Helpers_Sub_Bean> result) {
			super.onPostExecute(result);
			int count = AppNote_Bean.readNewHelperNumber(
					getApplicationContext(), 0);
			try {
				readPoint.setNumber(result.size() - count);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:// 新帮手
		case 1:// 推荐帮手
		case 2:// 通讯录
			break;
		default:
			Helpers_Sub_Bean helper = (Helpers_Sub_Bean) parent.getAdapter()
					.getItem(position);
			if (helper.getHelper_account_type() == -11111) {
				operationGroup(helper);
			} else {
				operationHelper(helper);
			}
			adapter.notifyDataSetChanged();
			break;
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:// 新帮手
			Intent intent_newHelper = new Intent(getActivity()
					.getApplicationContext(), NewHelperActivity.class);
			startActivityForResult(intent_newHelper, 1);
			break;
		case 1:// 推荐帮手
			Intent intent_recommendHelper = new Intent(getActivity()
					.getApplicationContext(), RecommendHelperActivity.class);
			startActivityForResult(intent_recommendHelper, 2);
			break;
		case 2:// 通讯录
			Intent intent_contactHelper = new Intent(getActivity()
					.getApplicationContext(), ContactHelperActivity.class);
			startActivityForResult(intent_contactHelper, 3);
			break;
		default:
			Helpers_Sub_Bean helper = (Helpers_Sub_Bean) parent.getAdapter()
					.getItem(position);
			if (helper.getHelper_account_type() == -11111) {// 组
				Intent intent_groupMember = new Intent(getActivity()
						.getApplicationContext(), GroupMemberActivity.class);
				intent_groupMember.putExtra("group", helper);
				startActivityForResult(intent_groupMember, 4);
			} else {// helper
				Intent intent_helperDetails = new Intent(getActivity()
						.getApplicationContext(), HelperDetailsActivity.class);
				intent_helperDetails.putExtra("helper", helper);
				startActivityForResult(intent_helperDetails, 5);
			}
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:// 新帮手返回
			getNewHelperCount();
			break;

		default:
			break;
		}
	}
}
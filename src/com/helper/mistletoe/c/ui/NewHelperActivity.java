package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.adapter.NewHelperListAdapter;
import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.net.request.Get_Helper_By_Account_NetRequest;
import com.helper.mistletoe.m.pojo.AppNote_Bean;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.ThreadPoolUtils_Net;
import com.helper.mistletoe.util.ThreadPoolUtils_db;
import com.helper.mistletoe.util.Tool_Utils;
import com.helper.mistletoe.v.ClearEditText;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class NewHelperActivity extends PrivateBasicActivity implements OnClickListener, OnItemLongClickListener {
	private ListView new_lv;
	private ArrayList<Helpers_Sub_Bean> helperList;
	private RelativeLayout back;
	private LinearLayout search, newHelper, contact;
	private SnaImageViewV2 searchHead;
	private TextView searchName, searchSign, searchAddButton;
	private ClearEditText mClearEditText;
	private NewHelperListAdapter adapter;
	private ReadNewHelperFromDBTask readNewHelperTask;
	private GetHelperByAccountTask getHelperByAccountTask;
	private Context context;
	private Get_Helper_By_Account_NetRequest netRequest;
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_helper_list);
		context = NewHelperActivity.this;
		initViews();

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
		count = AppNote_Bean.readNewHelperNumber(getApplicationContext());
		back = (RelativeLayout) findViewById(R.id.new_helper_list_button_back);
		back.setOnClickListener(this);
		contact = (LinearLayout) findViewById(R.id.new_helper_list_relativelayout_contacts);
		contact.setOnClickListener(this);
		search = (LinearLayout) findViewById(R.id.new_helper_list_linearLayout_serach);
		search.setVisibility(LinearLayout.GONE);
		newHelper = (LinearLayout) findViewById(R.id.new_helper_list_linearLayout_newHelper);
		newHelper.setVisibility(LinearLayout.GONE);
		searchHead = (SnaImageViewV2) findViewById(R.id.new_helper_list_imageView_serach_head);
		searchName = (TextView) findViewById(R.id.new_helper_list_textView_serach_name);
		searchSign = (TextView) findViewById(R.id.new_helper_list_textView_serach_sign);
		searchAddButton = (TextView) findViewById(R.id.new_helper_list_textView_serach_add);
		mClearEditText = (ClearEditText) findViewById(R.id.new_helper_list_filter_edit);
		new_lv = (ListView) findViewById(R.id.new_helper_list_listview);
		helperList = new ArrayList<Helpers_Sub_Bean>();
		adapter = new NewHelperListAdapter(context, helperList);
		new_lv.setAdapter(adapter);
		new_lv.setOnItemLongClickListener(this);
	}

	private void setData() {
		readNewHelperTask = new ReadNewHelperFromDBTask();
		readNewHelperTask.executeOnExecutor(ThreadPoolUtils_db.threadPool);
	}

	public class ReadNewHelperFromDBTask extends AsyncTask<String, Integer, ArrayList<Helpers_Sub_Bean>> {

		@Override
		protected ArrayList<Helpers_Sub_Bean> doInBackground(String... params) {
			ArrayList<Helpers_Sub_Bean> ListHelper = new ArrayList<Helpers_Sub_Bean>();
			HelperManager helperManager = new HelperManager();
			ListHelper = helperManager.ReadHelperFromDBByStatus(getApplicationContext(), new int[] { 1, 3, 5 });
			return ListHelper;
		}

		@Override
		protected void onPostExecute(ArrayList<Helpers_Sub_Bean> result) {
			super.onPostExecute(result);
			// 隐藏newHelper布局
			newHelper.setVisibility(LinearLayout.GONE);
			if (result != null) {
				if (result.size() > 0) {
					newHelper.setVisibility(LinearLayout.VISIBLE);
					display(result);
				}
			}
		}
	}

	public void display(ArrayList<Helpers_Sub_Bean> SourceDateList) {
		count = SourceDateList.size();
		helperList.clear();
		helperList.addAll(SourceDateList);
		if (adapter == null) {
			adapter = new NewHelperListAdapter(getApplicationContext(), helperList);
			new_lv.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
	}

	private void setlistener() {
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// 隐藏search布局
				search.setVisibility(LinearLayout.GONE);
				if (s != null) {//
					if (s.length() > 0) {
						search.setVisibility(LinearLayout.VISIBLE);
						findHelperByAccount(s.toString());
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	// 长按帮手后的操作，即操作帮手
	protected void operationHelper(final Helpers_Sub_Bean helper) {
		AlertDialog dlg = new AlertDialog.Builder(NewHelperActivity.this).setTitle("选项").setItems(R.array.NewHelperLongClick, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0://
					count--;
					if (helper.getHelper_status() == 1) {
						Instruction_Utils.sendInstrustion(NewHelperActivity.this.getApplicationContext(), Instruction_Utils.TURN_TO_FOUR, helper.getHelper_id());
					} else if (helper.getHelper_status() == 3) {
						// 发送变为旧帮手
						Instruction_Utils.sendInstrustion(NewHelperActivity.this.getApplicationContext(), Instruction_Utils.TURN_TO_OLD_HELPER, helper.getHelper_id());
					} else if (helper.getHelper_status() == 5) {
						Instruction_Utils.sendInstrustion(NewHelperActivity.this.getApplicationContext(), Instruction_Utils.TURN_TO_FOUR, helper.getHelper_id());
					}

					break;
				}
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		}).create();
		dlg.show();
	}

	protected void findHelperByAccount(String account) {
		getHelperByAccountTask = new GetHelperByAccountTask();
		getHelperByAccountTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, account);

	}

	public class GetHelperByAccountTask extends AsyncTask<String, Integer, Helpers_Sub_Bean> {
		String account = null;

		@Override
		protected Helpers_Sub_Bean doInBackground(String... params) {
			account = params[0];
			Helpers_Sub_Bean Helper_service = new Helpers_Sub_Bean();
			try {
				netRequest = new Get_Helper_By_Account_NetRequest(getApplicationContext());
				Helper_service = netRequest.doFindHelperByAccount(account);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return Helper_service;
		}

		@Override
		protected void onPostExecute(Helpers_Sub_Bean result) {
			super.onPostExecute(result);
			if (result != null) {
				// 如果搜索到则显示搜索到的结果
				displaySearchResult(result);
			} else {
				displaySearch(account);
			}
		}
	}

	public void displaySearchResult(final Helpers_Sub_Bean result) {
		searchName.setText(result.getHelper_name());
		searchSign.setText(result.getHelper_sign());
		searchHead.setImageForShow(result.getHelper_photo(), SnaBitmap.NET_SMALL);
		// 添加search到的helper
		searchAddButton.setText(R.string.new_helper_list_textView_serach_add);
		searchAddButton.setTextColor(Color.parseColor("#4a66ad"));
		searchAddButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (result.getHelper_verification() == 0) {
					// 发送添加帮手的指令
					Instruction_Utils.sendInstrustion(context, Instruction_Utils.INVITE_HELPER, result);
				} else {
					Intent intent_add = new Intent(getApplicationContext(), AddHelperDialogActivity.class);
					intent_add.putExtra("helper", result);
					context.startActivity(intent_add);
				}

			}
		});
	}

	public void displaySearch(final String account) {
		// XXX（需要系统自动识别短信接收者姓名），我正在使用罗盘ReadyGooo进行目标社交，你也快来试试吧！http（下载地址，暨官网）
		searchName.setText(account);
		searchSign.setText("他还不是helper，邀请他加入吧!");
		searchAddButton.setText(R.string.new_helper_list_textView_serach_invite);
		searchAddButton.setTextColor(Color.parseColor("#259b24"));
		searchAddButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Tool_Utils.isMobileNO(account)) {// 手机号
					Uri smsToUri = Uri.parse("smsto:" + account);
					Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
					intent.putExtra("sms_body", "我正在使用海豹ReadyGooo进行目标社交，你也快来试试吧！http://www.readygooo.com/readygooo/");
					context.startActivity(intent);
				} else if (Tool_Utils.isEmail(account)) {// 邮箱
					// 必须明确使用mailto前缀来修饰邮件地址,如果使用
					// intent.putExtra(Intent.EXTRA_EMAIL, email)，结果将匹配不到任何应用
					Uri uri = Uri.parse("mailto:" + account);
					String[] email = { account };
					Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
					intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
					intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分"); // 主题
					intent.putExtra(Intent.EXTRA_TEXT, "我正在使用海豹ReadyGooo进行目标社交，你也快来试试吧！http://www.readygooo.com/readygooo/"); // 正文
					context.startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
				} else {
					Tool_Utils.showInfo(context, "请输入手机号或邮箱");

				}

			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AppNote_Bean.writeNewHelperNumber(getApplicationContext(), count);
			finish();
		}
		return false;
	}

	@Override
	public void dealWithRadio(Intent intent) {
		// TODO Auto-generated method stub
		super.dealWithRadio(intent);
		String action = intent.getAction();
		if (action.equals(MessageConstants.REFRESH_HELPER)) {
			setData();
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Helpers_Sub_Bean helper = (Helpers_Sub_Bean) parent.getAdapter().getItem(position);
		operationHelper(helper);
		// adapter.notifyDataSetChanged();
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.new_helper_list_button_back:// 返回键
			AppNote_Bean.writeNewHelperNumber(getApplicationContext(), count);
			finish();
			break;
		case R.id.new_helper_list_relativelayout_contacts:// 进入通讯录列表
			Intent intent = new Intent(getApplicationContext(), ContactHelperActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

}

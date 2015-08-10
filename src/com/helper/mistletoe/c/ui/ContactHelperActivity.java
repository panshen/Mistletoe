package com.helper.mistletoe.c.ui;

import java.util.ArrayList;
import java.util.List;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.adapter.ContactListAdapter;
import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.ThreadPoolUtils_db;
import com.helper.mistletoe.v.ClearEditText;
import com.helper.mistletoe.v.SideBar;
import com.helper.mistletoe.v.SideBar.OnTouchingLetterChangedListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.RelativeLayout;

public class ContactHelperActivity extends PrivateBasicActivity {
	private ListView All_lv;
	private RelativeLayout back;
	private ClearEditText mClearEditText;
	private SideBar sideBar;
	private TextView dialog;
	public TextView alert;
	private ContactListAdapter adapter;
	private List<Helpers_Sub_Bean> SourceDateList;
	private ReadContactFromDBTask readContactTask;
	private int view_position = -1;
	private int top = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.contact_helper_list);
		// 上传通讯录指令
		Instruction_Utils.sendInstrustion(this.getApplicationContext(), Instruction_Utils.UPLOAD_ADDRESSBOOK);
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mClearEditText.clearComposingText();
		setData();
		setlistener();
	}

	@Override
	protected void onPause() {
		super.onPause();
		memoryListviewPosition();
	}

	private void initViews() {
		back = (RelativeLayout) findViewById(R.id.contact_helper_list_relativeLayout_back);
		mClearEditText = (ClearEditText) findViewById(R.id.contact_helper_list_filter_edit);
		sideBar = (SideBar) findViewById(R.id.contact_helper_list_sidrbar);
		All_lv = (ListView) findViewById(R.id.contact_helper_list_listview);
		dialog = (TextView) findViewById(R.id.contact_helper_list_textview_dialog);
		alert = (TextView) findViewById(R.id.contact_helper_list_textView_alert);
		alert.setVisibility(TextView.GONE);
		alert.setText(R.string.contact_helper_list_alert_text);
		sideBar.setVisibility(SideBar.VISIBLE);
		sideBar.setTextView(dialog);
	}

	private void setData() {
		alert.setVisibility(TextView.VISIBLE);
		readContactTask = new ReadContactFromDBTask();
		readContactTask.executeOnExecutor(ThreadPoolUtils_db.threadPool);
	}

	public void display(List<Helpers_Sub_Bean> SourceDateList) {
		this.SourceDateList = SourceDateList;
		if (SourceDateList.size() > 0) {
			alert.setVisibility(TextView.GONE);
		}
		adapter = new ContactListAdapter(ContactHelperActivity.this, SourceDateList);
		All_lv.setAdapter(adapter);
		if (view_position != -1) {
			All_lv.setSelectionFromTop(view_position, top);
		}
	}

	private void setlistener() {
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				if (null != adapter) {
					int position = adapter.getPositionForSection(s.charAt(0));
					if (position != -1) {
						All_lv.setSelection(position);
					}
				}

			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				setResult(1, intent);
				onDestroy();
				finish();
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
		List<Helpers_Sub_Bean> filterDateList = new ArrayList<Helpers_Sub_Bean>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (Helpers_Sub_Bean sortModel : SourceDateList) {
				String name = sortModel.getHelper_name();
				String number = sortModel.getHelper_tel();
				if (name.indexOf(filterStr.toString()) != -1 || sortModel.getHelper_name_pinyin().startsWith(filterStr.toString()) || number.startsWith(filterStr.toString())
						|| name.equalsIgnoreCase(filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}
		adapter.updateListView(filterDateList);
	}

	private class ReadContactFromDBTask extends AsyncTask<String, Integer, ArrayList<Helpers_Sub_Bean>> {

		@Override
		protected ArrayList<Helpers_Sub_Bean> doInBackground(String... params) {
			ArrayList<Helpers_Sub_Bean> ListHelper = new ArrayList<Helpers_Sub_Bean>();
			HelperManager helperManager = new HelperManager();
			ListHelper = helperManager.ReadHelperFromDBByStatus(getApplicationContext(), new int[] { 4 });
			return ListHelper;
		}

		@Override
		protected void onPostExecute(ArrayList<Helpers_Sub_Bean> result) {
			super.onPostExecute(result);
			display(result);
		}
	}

	// 记住当前位置
	protected void memoryListviewPosition() {
		view_position = All_lv.getFirstVisiblePosition();
		View v = All_lv.getChildAt(0);
		top = (v == null) ? 0 : v.getTop();
	}

	@Override
	public void dealWithRadio(Intent intent) {
		super.dealWithRadio(intent);
		String action = intent.getAction();
		if (action.equals(MessageConstants.REFRESH_HELPER)) {
			setData();
		}
	}
}

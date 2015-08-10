package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.db.GroupManager;
import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.ThreadPoolUtils_db;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class GroupListDialogActivity extends Activity {
	private RadioGroup radioGroup;
	private Button yes, no;
	private Integer seleted_Id;
	private Group_Bean group_bean;
	private Context context;
	private ReadGroupFromDBTask readGroupFromDBTask;
	private ArrayList<Helpers_Sub_Bean> showDataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.group_list_dialog);
		seleted_Id = getIntent().getIntExtra("helper_id", -1);
		context = this;
		initView();
		setData();
		setlistener();
	}

	private void setData() {
		readGroupFromDBTask = new ReadGroupFromDBTask();
		readGroupFromDBTask.executeOnExecutor(ThreadPoolUtils_db.threadPool);
	}

	private void setlistener() {
		// 确定
		yes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				group_bean.setGroup_member_id(seleted_Id);
				// 发送修改group的member的指令
				Instruction_Utils.sendInstrustion(getApplicationContext(), Instruction_Utils.ADD_HELPER_TO_GROUP, group_bean);
				finish();
			}
		});
		// 取消
		no.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
				for (int i = 0; i < showDataList.size(); i++) {
					if (radioGroup.getChildAt(i).getId() == checkedId) {
						group_bean.setGroup_name(showDataList.get(i).getHelper_name());
						group_bean.setGroup_describe(showDataList.get(i).getHelper_sign());
						group_bean.setGroup_id(showDataList.get(i).getHelper_id());
						group_bean.setGroup_status(showDataList.get(i).getHelper_status());
					}
				}

			}
		});
	}

	class ReadGroupFromDBTask extends AsyncTask<String, Integer, ArrayList<Helpers_Sub_Bean>> {

		@Override
		protected ArrayList<Helpers_Sub_Bean> doInBackground(String... params) {
			ArrayList<Helpers_Sub_Bean> ShowDataList = new ArrayList<Helpers_Sub_Bean>();
			GroupManager groupManager = new GroupManager();
			ShowDataList = groupManager.getShowDataListByStatus(getApplicationContext());
			return ShowDataList;
		}

		@Override
		protected void onPostExecute(ArrayList<Helpers_Sub_Bean> result) {
			super.onPostExecute(result);
			display(result);
		}
	}

	private void initView() {
		radioGroup = (RadioGroup) findViewById(R.id.group_list_dialog_radioGroup);
		yes = (Button) findViewById(R.id.group_list_dialog_button_yes);
		no = (Button) findViewById(R.id.group_list_dialog_button_no);
		showDataList = new ArrayList<Helpers_Sub_Bean>();
		group_bean = new Group_Bean();
	}

	@SuppressLint("ResourceAsColor")
	public void display(ArrayList<Helpers_Sub_Bean> ShowDataList) {
		if (ShowDataList != null) {
			this.showDataList = ShowDataList;
			if (ShowDataList.size() > 0) {
				for (int i = 0; i < ShowDataList.size(); i++) {
					RadioButton rb = new RadioButton(context);
					rb.setText(ShowDataList.get(i).getHelper_name());
					rb.setTextColor(R.color.blue);
					rb.setTextSize(25);
					rb.setPadding(20, 10, 0, 10);
					radioGroup.addView(rb);
				}
				radioGroup.check(radioGroup.getChildAt(0).getId());
			}

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

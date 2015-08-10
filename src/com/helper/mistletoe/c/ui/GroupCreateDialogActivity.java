package com.helper.mistletoe.c.ui;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class GroupCreateDialogActivity extends PrivateBasicActivity {
	private TextView create;
	private EditText group_name, group_describe;
	private Group_Bean group_bean;
	private Context context;
	private String members;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.create_group_dialog);
		members = getIntent().getStringExtra("members");
		context = this;
		initView();

	}

	@Override
	protected void onResume() {
		super.onResume();
		setlistener();
	}

	private void setlistener() {
		create.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				group_bean = new Group_Bean();
				group_bean.setGroup_name(group_name.getText().toString());
				group_bean.setGroup_describe(group_describe.getText().toString());
				group_bean.setGroup_memberIds(members);
				// 发送创建group的指令
				Instruction_Utils.sendInstrustion(getApplicationContext(), Instruction_Utils.CREATE_GROUP, group_bean);
			}
		});
	}

	private void initView() {
		create = (TextView) findViewById(R.id.creategroup_dialog_textview_create);
		group_name = (EditText) findViewById(R.id.creategroup_dialog_editText_name);
		group_describe = (EditText) findViewById(R.id.creategroup_dialog_editText_describe);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void dealWithRadio(Intent intent) {
		super.dealWithRadio(intent);
		String action = intent.getAction();
		if (action.equals(MessageConstants.REFRESH_GROUP)) {
			Intent intent_group = new Intent(getApplicationContext(), MainFragmentActivity.class);
			intent_group.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent_group.putExtra("flag", 0);
			context.startActivity(intent_group);
		}
	}
}

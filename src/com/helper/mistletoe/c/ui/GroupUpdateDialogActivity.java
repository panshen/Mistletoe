package com.helper.mistletoe.c.ui;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.Instruction_Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class GroupUpdateDialogActivity extends Activity {
	private TextView update;
	private EditText group_name, group_describe;
	private Helpers_Sub_Bean helper;
	private Group_Bean group_bean;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.updategroup_dialog);
		helper = getIntent().getParcelableExtra("group");
		context = this;
		initView();
		setlistener();
	}

	private void setlistener() {
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				group_bean = captureDate();
				// 发送修改group信息的指令
				Instruction_Utils.sendInstrustion(context.getApplicationContext(), Instruction_Utils.UPDATE_GROUP_INFORMATION, group_bean);
				finish();
			}
		});
	}

	private Group_Bean captureDate() {
		String name = group_name.getText().toString();
		String describe = group_describe.getText().toString();
		if (!name.isEmpty()) {
			group_bean.setGroup_name(name);
		}
		if (!describe.isEmpty()) {
			group_bean.setGroup_describe(describe);
		}
		return group_bean;
	};

	private void initView() {
		group_bean = new Group_Bean();
		update = (TextView) findViewById(R.id.updategroup_dialog_textview_update);
		group_name = (EditText) findViewById(R.id.updategroup_dialog_editText_name);
		group_name.setText(helper.getHelper_name().toString());
		group_describe = (EditText) findViewById(R.id.updategroup_dialog_editText_describe);
		group_describe.setText(helper.getHelper_sign().toString());
		group_bean.setGroup_id(helper.getHelper_id());
		group_bean.setGroup_name(helper.getHelper_name());
		group_bean.setGroup_describe(helper.getHelper_sign());

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

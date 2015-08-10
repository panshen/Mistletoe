package com.helper.mistletoe.c.ui;

import com.helper.mistletoe.R;
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

public class AddHelperDialogActivity extends Activity {
	private TextView update, cancel;
	private EditText msg;
	private Context context;
	private Helpers_Sub_Bean helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_helper_dialog);
		helper = getIntent().getParcelableExtra("helper");
		context = AddHelperDialogActivity.this;
		initView();
		setlistener();
	}

	private void setlistener() {
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String helprt_msg = msg.getText().toString();
				helper.setHelper_msg(helprt_msg);
				// 发送
				Instruction_Utils.sendInstrustion(context.getApplicationContext(), Instruction_Utils.INVITE_HELPER, helper);
				finish();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initView() {
		update = (TextView) findViewById(R.id.add_helper_dialog_textview_yes);
		cancel = (TextView) findViewById(R.id.add_helper_dialog_textview_cancel);
		msg = (EditText) findViewById(R.id.add_helper_dialog_editText_msg);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

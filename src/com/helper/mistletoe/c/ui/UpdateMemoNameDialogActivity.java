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

public class UpdateMemoNameDialogActivity extends Activity {
	private TextView update, cancel;
	private EditText memo_name;
	private Context context;
	private Helpers_Sub_Bean helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.update_memo_name_dialog);
		helper = getIntent().getParcelableExtra("helper");
		context = this;
		initView();
		setlistener();
	}

	private void setlistener() {
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String memo = memo_name.getText().toString();
				helper.setHelper_memo_name(memo);
				// 发送修改HelperMemoName的指令
				Instruction_Utils.sendInstrustion(context, Instruction_Utils.UPDATE_HELPER_MEMONAME, helper);
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
		update = (TextView) findViewById(R.id.update_memoName_dialog_textview_update);
		cancel = (TextView) findViewById(R.id.update_memoName_dialog_textview_cancel);
		memo_name = (EditText) findViewById(R.id.update_memoName_dialog_editText_memoName);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

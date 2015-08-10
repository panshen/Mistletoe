package com.helper.mistletoe.c.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.util.Instruction_Utils;

public class UploadAddressBookActivity extends Activity {

	private Button upload, skip;
	private TextView explain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.upload_addressbook);
		initView();
		setListener();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	protected void initView() {
		upload = (Button) findViewById(R.id.upload_addressbook_button_upload);
		skip = (Button) findViewById(R.id.upload_addressbook_button_skip);
		explain = (TextView) findViewById(R.id.upload_addressbook_textView_explain);
	}

	protected void setListener() {
		upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO
				// 上传通讯录指令
				Instruction_Utils.sendInstrustion(getApplicationContext(), Instruction_Utils.UPLOAD_ADDRESSBOOK);
				Intent intent = new Intent(UploadAddressBookActivity.this, MainFragmentActivity.class);
				startActivity(intent);
				finish();
			}
		});
		skip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO
				Intent intent = new Intent(UploadAddressBookActivity.this, MainFragmentActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}

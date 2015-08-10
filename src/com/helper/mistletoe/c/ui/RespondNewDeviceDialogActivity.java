package com.helper.mistletoe.c.ui;

import com.helper.mistletoe.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class RespondNewDeviceDialogActivity extends Activity {
	private TextView cancel, content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.respond_new_device_dialog);
		initView();
		setlistener();
	}

	private void setlistener() {
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RespondNewDeviceDialogActivity.this.finish();
			}
		});
	}

	private void initView() {
		String msg = getIntent().getStringExtra("content");
		cancel = (TextView) findViewById(R.id.respond_new_device_dialog_textView_cancel);
		content = (TextView) findViewById(R.id.respond_new_device_dialog_textView_content);
		content.setText(msg);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		}
		if (keyCode == KeyEvent.KEYCODE_HOME) {
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

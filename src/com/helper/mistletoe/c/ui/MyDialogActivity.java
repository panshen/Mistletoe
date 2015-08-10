package com.helper.mistletoe.c.ui;

import com.helper.mistletoe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class MyDialogActivity extends Activity {
	private TextView ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.my_dialog);
		initView();
		setlistener();
	}

	private void setlistener() {
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MyDialogActivity.this, Login_Activity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				MyDialogActivity.this.finish();
			}
		});
	}

	private void initView() {
		ok = (TextView) findViewById(R.id.my_dialog_textView_ok);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// MyJpushApplication.getInstance().exit();
		}
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			// MyJpushApplication.getInstance().exit();
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

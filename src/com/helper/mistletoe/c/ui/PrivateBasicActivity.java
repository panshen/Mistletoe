package com.helper.mistletoe.c.ui;

import com.helper.mistletoe.util.MessageConstants;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class PrivateBasicActivity extends Activity implements ReceiverInterface ,OnClickListener{
	private IntentFilter myIntentFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 注册广播
		regiserRadio(MessageConstants.REFRESHS_PROJECTION);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 注销广播
		destroyRadio();
	}

	@Override
	public void destroyRadio() {
		// 注销广播
		getApplicationContext().unregisterReceiver(IntentReceicer);
	}

	@Override
	public void dealWithRadio(Intent intent) {
		// TODO Auto-generated method stub
//		Log.v("广播", "父类中" + intent.getIntExtra("group_id", -1));
	}

	@Override
	public void regiserRadio(String[] actions) {
		myIntentFilter = new IntentFilter();
		for (int i = 0; i < actions.length; i++) {
			myIntentFilter.addAction(actions[i]);
		}
		// 注册广播
		getApplicationContext().registerReceiver(IntentReceicer, myIntentFilter);
	}

	private BroadcastReceiver IntentReceicer = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			dealWithRadio(intent);
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}

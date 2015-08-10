package com.helper.mistletoe.c.ui;

import java.util.ArrayList;
import java.util.List;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.adapter.DeviceListAdapter;
import com.helper.mistletoe.m.net.request.Find_Device_NetRequest;
import com.helper.mistletoe.m.pojo.Result_Bean;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.ThreadPoolUtils_Net;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class DeviceActivity extends PrivateBasicActivity {
	private ListView device_lv;
	private RelativeLayout back;
	private DeviceListAdapter adapter;
	private FindDeviceTask findDeviceTask;
	private Find_Device_NetRequest netRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.device_list);
		initViews();

	}

	@Override
	protected void onResume() {
		super.onResume();
		setData();
		setlistener();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private void initViews() {
		back = (RelativeLayout) findViewById(R.id.device_list_relativeLayout_back);
		device_lv = (ListView) findViewById(R.id.device_list_listView_device);

	}

	private void setData() {
		findDeviceTask = new FindDeviceTask();
		findDeviceTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);

	}

	private void display(List<Result_Bean> SourceDateList) {
		adapter = new DeviceListAdapter(this, SourceDateList);
		device_lv.setAdapter(adapter);
	}

	private void setlistener() {
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private class FindDeviceTask extends AsyncTask<String, Integer, ArrayList<Result_Bean>> {

		@Override
		protected ArrayList<Result_Bean> doInBackground(String... params) {
			ArrayList<Result_Bean> List_service = new ArrayList<Result_Bean>();
			try {
				netRequest = new Find_Device_NetRequest(getApplicationContext());
				List_service = netRequest.doFindDevice();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return List_service;
		}

		@Override
		protected void onPostExecute(ArrayList<Result_Bean> result) {
			super.onPostExecute(result);
			display(result);
		}
	}

	@Override
	public void dealWithRadio(Intent intent) {
		// TODO Auto-generated method stub
		super.dealWithRadio(intent);
		String action = intent.getAction();
		Log.v("广播", "zi类中" + intent.getIntExtra("group_id", -1));
		if (action.equals(MessageConstants.REFRESH_DEVICE)) {
			setData();
		}
	}
}

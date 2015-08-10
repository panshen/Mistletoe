package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.adapter.BlackListAdapter;
import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.ThreadPoolUtils_db;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class BlackHelperActivity extends PrivateBasicActivity {
	private ListView black_lv;
	private RelativeLayout back;
	private BlackListAdapter adapter;
	private ReadBlackFromDBTask readBlackHelperTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.black_helper_list);
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
		back = (RelativeLayout) findViewById(R.id.black_list_button_back);
		black_lv = (ListView) findViewById(R.id.black_list_listview);
	}

	private void setData() {
		readBlackHelperTask = new ReadBlackFromDBTask();
		readBlackHelperTask.executeOnExecutor(ThreadPoolUtils_db.threadPool);
	}

	private class ReadBlackFromDBTask extends AsyncTask<String, Integer, ArrayList<Helpers_Sub_Bean>> {

		@Override
		protected ArrayList<Helpers_Sub_Bean> doInBackground(String... params) {
			ArrayList<Helpers_Sub_Bean> ListHelper = new ArrayList<Helpers_Sub_Bean>();
			HelperManager helperManager = new HelperManager();
			ListHelper = helperManager.ReadHelperFromDBByStatus(getApplicationContext(), new int[] { 2 });
			return ListHelper;
		}

		@Override
		protected void onPostExecute(ArrayList<Helpers_Sub_Bean> result) {
			super.onPostExecute(result);
			display(result);
		}
	}

	private void display(ArrayList<Helpers_Sub_Bean> SourceDateList) {
		adapter = new BlackListAdapter(this, SourceDateList);
		black_lv.setAdapter(adapter);
	}

	private void setlistener() {
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void dealWithRadio(Intent intent) {
		super.dealWithRadio(intent);
		String action = intent.getAction();
		if (action.equals(MessageConstants.REFRESH_HELPER)) {
			setData();
		}
	}
}

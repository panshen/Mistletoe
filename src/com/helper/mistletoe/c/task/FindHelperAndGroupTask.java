package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.util.MessageConstants;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class FindHelperAndGroupTask extends AsyncTask<String, Integer, Boolean> {

	private Context context;
	private Boolean result = false;

	public FindHelperAndGroupTask(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		HelperManager helperManager = new HelperManager();
		result = helperManager.FindHelperAndGroupFromNetByLastUpdateTime(context);
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {// ture为需要更新，false不需要更新
			Intent intent = new Intent(); // Itent就是我们要发送的内容
			intent.setAction(MessageConstants.REFRESH_HELPER); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
			intent.setAction(MessageConstants.REFRESH_GROUP); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
			// 发送广播
			context.sendBroadcast(intent);
		}
	}
}

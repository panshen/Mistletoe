package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.util.MessageConstants;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class FindHelperTask extends AsyncTask<String, Integer, Boolean> {

	private Context context;

	public FindHelperTask(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		Boolean result = false;
		HelperManager helperManager = new HelperManager();
		result = helperManager.FindHelperFromNetByLastUpdataTime(context);
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {// 非空时，有增量
			Intent intent = new Intent(); // Itent就是我们要发送的内容
			intent.setAction(MessageConstants.REFRESH_HELPER); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
			// 发送广播
			context.sendBroadcast(intent);
		}
	}
}

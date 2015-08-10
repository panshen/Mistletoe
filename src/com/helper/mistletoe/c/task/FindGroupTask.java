package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.db.GroupManager;
import com.helper.mistletoe.util.MessageConstants;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class FindGroupTask extends AsyncTask<String, Integer, Boolean> {

	private Context context;
	private Boolean result = false;

	public FindGroupTask(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		GroupManager groupManager = new GroupManager();
		result = groupManager.FindGroupListFromNetByLastUpdateTime(context);
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {// 非空时，有增量
			Intent intent = new Intent(); // Itent就是我们要发送的内容
			intent.setAction(MessageConstants.REFRESH_GROUP); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
			// 发送广播
			context.sendBroadcast(intent);
		}
	}
}

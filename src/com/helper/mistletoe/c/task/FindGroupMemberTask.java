package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.db.GroupManager;
import com.helper.mistletoe.util.MessageConstants;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class FindGroupMemberTask extends AsyncTask<Integer, Integer, Boolean> {

	private Context context;
	private Boolean result = false;
	private Integer group_id;

	public FindGroupMemberTask(Context context) {
		this.context = context.getApplicationContext();
	}

	@Override
	protected Boolean doInBackground(Integer... params) {
		group_id = params[0];
		GroupManager groupManager = new GroupManager();
		result = groupManager.FindGroupMemberFromNetByGroupId(context, group_id);
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {// ture为需要更新，false不需要更新
			Intent intent = new Intent(); // Itent就是我们要发送的内容
			intent.setAction(MessageConstants.REFRESH_GROUP); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
			intent.putExtra("group_id", group_id);
			// 发送广播
			context.sendBroadcast(intent);
		}
	}
}

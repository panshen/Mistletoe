package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Get_User_NetRequest;
import com.helper.mistletoe.util.MessageConstants;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class GetUserByIdTask extends AsyncTask<String, Integer, Boolean> {

	private Context context;

	public GetUserByIdTask(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		Boolean result = false;
		try {
			Get_User_NetRequest netRequest = new Get_User_NetRequest(context);
			result = netRequest.doGetUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		Log.v("上下文", "获取User" + context);
		if (result) {// ture为需要更新，false不需要更新
			Intent intent = new Intent(); // Itent就是我们要发送的内容
			intent.setAction(MessageConstants.REFRESH_USER); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
			// 发送广播
			context.sendBroadcast(intent);
		}
	}
}

package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Kick_Device_NetRequest;
import com.helper.mistletoe.util.MessageConstants;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class KickDeviceTask extends AsyncTask<Integer, Integer, Boolean> {

	private Context context;
	private Kick_Device_NetRequest netRequest;
	private Integer device_id;
	private Boolean result = false;

	public KickDeviceTask(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(Integer... params) {
		device_id = params[0];
		try {
			netRequest = new Kick_Device_NetRequest(context);
			result = netRequest.doKickDecive(device_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {// ture为需要更新，false不需要更新
			Intent intent = new Intent(); // Itent就是我们要发送的内容
			intent.setAction(MessageConstants.REFRESH_DEVICE); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
			// 发送广播
			context.sendBroadcast(intent);
		}
	}
}

package com.helper.mistletoe.c.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.helper.mistletoe.m.net.request.Update_Public_Helper_NetRequest;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.MessageConstants;

public class UpdatePublicHelperTask extends AsyncTask<String, Integer, Boolean> {

	private Context context;
	private String status;
	private User_Bean user;

	public UpdatePublicHelperTask(Context context) {
		this.context = context;
		user = new User_Bean();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		Boolean result = false;
		status = params[0];
		try {
			Update_Public_Helper_NetRequest netRequest = new Update_Public_Helper_NetRequest(context);
			result = netRequest.doUpdatePublicHelper("", status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {
			try {
				user.readData(context);
				if (status.equals("1")) {
					user.setLoc_OpenMe(true);
				} else if (status.equals("2")) {
					user.setLoc_OpenMe(false);
				}
				user.writeData(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Intent intent = new Intent(); // Itent就是我们要发送的内容
		intent.setAction(MessageConstants.REFRESH_USER); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
		// 发送广播
		context.sendBroadcast(intent);

	}
}

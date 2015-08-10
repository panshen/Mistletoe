package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Update_User_NetRequest;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.Tool_Utils;

import android.content.Context;
import android.os.AsyncTask;

public class UpdateUserTask extends AsyncTask<String, Integer, Boolean> {

	private Context context;
	private Update_User_NetRequest netRequest;
	private User_Bean user;

	public UpdateUserTask(Context context, User_Bean user) {
		this.context = context;
		this.user = user;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		Boolean result = false;
		try {
			netRequest = new Update_User_NetRequest(context);
			result = netRequest.doUpdate(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {
			Instruction_Utils.sendInstrustion(context, Instruction_Utils.SYNCHRONOUS_USER);
			Tool_Utils.showInfo(context, "更新个人信息成功！");
		} else {
			Tool_Utils.showInfo(context, "更新个人信息失败，请在网络状态良好的情况下，重新操作！");
		}

	}
}

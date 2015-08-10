package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Delete_Group_NetRequest;
import com.helper.mistletoe.util.Instruction_Utils;

import android.content.Context;
import android.os.AsyncTask;

public class DeleteGroupTask extends AsyncTask<Integer, Integer, Boolean> {

	private Context context;
	private Delete_Group_NetRequest netRequest;
	private Boolean result = false;
	private Integer group_id = -1;

	public DeleteGroupTask(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(Integer... params) {
		group_id = params[0];
		try {
			netRequest = new Delete_Group_NetRequest(context);
			result = netRequest.doDeleteGroup(group_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {// ture为需要更新，false不需要更新
			// 发送同步group的指令
			Instruction_Utils.sendInstrustion(context, Instruction_Utils.SYNCHRONOUS_GROUP);
		}
	}
}

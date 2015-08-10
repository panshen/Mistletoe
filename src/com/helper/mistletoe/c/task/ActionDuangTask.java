package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Action_Duang_NetRequest;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.Tool_Utils;

import android.content.Context;
import android.os.AsyncTask;

public class ActionDuangTask extends AsyncTask<Integer, Integer, Boolean> {

	private Context context;
	private Action_Duang_NetRequest netRequest;
	private Integer helper_id;
	private Boolean result = false;

	public ActionDuangTask(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(Integer... params) {
		helper_id = params[0];
		try {
			netRequest = new Action_Duang_NetRequest(context);
			result = netRequest.doActionDuang(helper_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {// ture为需要更新，false不需要更新
			// 发送同步单个Helper指令
			Instruction_Utils.sendInstrustion(context, Instruction_Utils.SYNCHRONOUS_HELPER_BY_ID, helper_id);
		} else {
			// 如果不是，则不可以duang，并提示用户，只有加为好友才可以duang
			Tool_Utils.showInfo(context, "只有加为好友才可以duang!");
		}
	}
}

package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Update_Relationship_NetRequest;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.Tool_Utils;

import android.content.Context;
import android.os.AsyncTask;

public class UpdateBlackToHelperTask extends AsyncTask<Integer, Integer, Boolean> {

	private Context context;
	private String memo_name;

	public UpdateBlackToHelperTask(Context mContext) {
		this.context = mContext;
	}

	@Override
	protected Boolean doInBackground(Integer... params) {
		Boolean result = false;
		Integer id = params[0];
		try {
			Update_Relationship_NetRequest netRequest = new Update_Relationship_NetRequest(context);
			result = netRequest.doUpdateRelationship(id, 9, memo_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {// 修改成功
			Instruction_Utils.sendInstrustion(context, Instruction_Utils.SYNCHRONOUS_HELPER);
			Tool_Utils.showInfo(context, "解除阻止成功");
		} else {
			Tool_Utils.showInfo(context, "解除阻止失败，请在网络状态良好的情况下，重新操作！");
		}
	}
}

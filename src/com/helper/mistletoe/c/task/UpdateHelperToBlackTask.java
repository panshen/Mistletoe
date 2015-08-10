package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Update_Relationship_NetRequest;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.Tool_Utils;

import android.content.Context;
import android.os.AsyncTask;

public class UpdateHelperToBlackTask extends AsyncTask<Integer, Integer, Boolean> {

	private Context context;
	private Integer id;
	private String memo_name;
	private Update_Relationship_NetRequest netRequest;

	public UpdateHelperToBlackTask(Context mContext) {
		this.context = mContext;
	}

	@Override
	protected Boolean doInBackground(Integer... params) {
		Boolean result = false;
		id = params[0];
		try {
			netRequest = new Update_Relationship_NetRequest(context);
			result = netRequest.doUpdateRelationship(id, 2, memo_name);
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
			Tool_Utils.showInfo(context, "加入黑名单成功，可在黑名单中查看！");
		}else {
			Tool_Utils.showInfo(context, "加入黑名单失败，请在网络状态良好的情况下，重新操作！");	
		}
	}

}

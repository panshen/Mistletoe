package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Update_Relationship_NetRequest;
import com.helper.mistletoe.util.Instruction_Utils;
import android.content.Context;
import android.os.AsyncTask;

public class UpdateRequestToRefuseHelperTask extends AsyncTask<Integer, Integer, Boolean> {

	private Context context;
	private Integer id;
	private String memo_name;
	private Update_Relationship_NetRequest netRequest;

	public UpdateRequestToRefuseHelperTask(Context mContext) {
		this.context = mContext;
	}

	@Override
	protected Boolean doInBackground(Integer... params) {
		Boolean result = false;
		id = params[0];
		try {
			netRequest = new Update_Relationship_NetRequest(context);
			result = netRequest.doUpdateRelationship(id, 6, memo_name);
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
		}
	}
}

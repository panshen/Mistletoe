package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Update_Relationship_NetRequest;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.Instruction_Utils;

import android.content.Context;
import android.os.AsyncTask;

public class UpdateHelperMemoNameTask extends AsyncTask<Integer, Integer, Boolean> {

	private Context context;
	private Update_Relationship_NetRequest netRequest;
	private Helpers_Sub_Bean helper;

	public UpdateHelperMemoNameTask(Context mContext, Helpers_Sub_Bean helper) {
		this.context = mContext;
		this.helper = helper;
	}

	@Override
	protected Boolean doInBackground(Integer... params) {
		Boolean result = false;
		try {
			netRequest = new Update_Relationship_NetRequest(context);
			result = netRequest.doUpdateRelationship(helper.getHelper_id(), helper.getHelper_status(), helper.getHelper_memo_name());
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

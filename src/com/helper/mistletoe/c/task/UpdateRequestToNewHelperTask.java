package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Update_Relationship_NetRequest;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class UpdateRequestToNewHelperTask extends AsyncTask<Integer, Integer, Boolean> {

	private Context context;
	private Integer id;
	private String memo_name;
	private Update_Relationship_NetRequest netRequest;

	public UpdateRequestToNewHelperTask(Context mContext) {
		this.context = mContext;
	}

	@Override
	protected Boolean doInBackground(Integer... params) {
		Boolean result = false;
		id = params[0];
		try {
			netRequest = new Update_Relationship_NetRequest(context);
			result = netRequest.doUpdateRelationship(id, 3, memo_name);
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

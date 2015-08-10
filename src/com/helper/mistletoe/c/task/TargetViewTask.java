package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Target_View_NetRequest;

import android.content.Context;
import android.os.AsyncTask;

public class TargetViewTask extends AsyncTask<Integer, Integer, Boolean> {

	private Context context;
	private Target_View_NetRequest netRequest;
	private Boolean result = false;
	private Integer target_id = -1;

	public TargetViewTask() {
	}
	public TargetViewTask(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(Integer... params) {
		target_id = params[0];
		netRequest = new Target_View_NetRequest(context);
		try {
		result=netRequest.doTargetView(target_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
	}
}

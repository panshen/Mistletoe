package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.db.CostTagManager;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.util.MessageConstants;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class FindCostTagsByTargetIdTask extends
		AsyncTask<Integer, Integer,  Boolean> {

	private Context context;
	private Integer target_id;
	private Boolean result;

	public FindCostTagsByTargetIdTask(Context context, Integer target_id) {
		this.context = context;
		this.target_id = target_id;
	}

	@Override
	protected  Boolean doInBackground(Integer... params) {
		try {
			CostTagManager costTagManager=new CostTagManager();
			result=costTagManager.UpdateCostTagByID(context, target_id);
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {//
			AirLock_Work.syncScheduleTag();
			Intent intent = new Intent(); // Itent就是我们要发送的内容
			intent.setAction(MessageConstants.REFRESH_COSTTAG); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
			intent.putExtra("target_id", target_id);
			// 发送广播
			context.sendBroadcast(intent);
		}
	}
}

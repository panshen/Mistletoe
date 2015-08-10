package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.net.request.Get_Helper_By_Id_NetRequest;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.MessageConstants;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class GetHelperByIdTask extends AsyncTask<Integer, Integer, Helpers_Sub_Bean> {

	private Context context;
	private Get_Helper_By_Id_NetRequest netRequest;
	private Integer helper_id;

	public GetHelperByIdTask(Context context) {
		this.context = context;
	}

	@Override
	protected Helpers_Sub_Bean doInBackground(Integer... params) {
		helper_id = params[0];
		Helpers_Sub_Bean Helper_service = new Helpers_Sub_Bean();
		try {
			netRequest = new Get_Helper_By_Id_NetRequest(context);
			Helper_service = netRequest.doGetHelperById(helper_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Helper_service;
	}

	@Override
	protected void onPostExecute(Helpers_Sub_Bean result) {
		super.onPostExecute(result);
		if (result != null) {
			// 如果不为null，说明获取成功
			// 先更新数据库
			HelperManager helperManager = new HelperManager();
			helperManager.UpdateHelperToDBById(context, result);
			// 再通知界面更新
			Intent intent = new Intent(); // Itent就是我们要发送的内容
			intent.setAction(MessageConstants.REFRESH_HELPER); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
			// 发送广播
			context.sendBroadcast(intent);
		}
	}
}

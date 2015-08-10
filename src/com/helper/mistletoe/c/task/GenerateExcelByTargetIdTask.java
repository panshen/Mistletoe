package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Generate_Excel_By_Id_NetRequest;
import com.helper.mistletoe.m.pojo.NetResult_Bean;
import com.helper.mistletoe.util.Tool_Utils;

import android.content.Context;
import android.os.AsyncTask;

public class GenerateExcelByTargetIdTask extends
		AsyncTask<Integer, Integer, NetResult_Bean> {

	private Context context;
	private Generate_Excel_By_Id_NetRequest netRequest;
	private Integer target_id;
	private String email;
	private NetResult_Bean result;

	public GenerateExcelByTargetIdTask(Context context, Integer target_id,
			String email) {
		this.context = context;
		this.target_id = target_id;
		this.email = email;
	}

	@Override
	protected NetResult_Bean doInBackground(Integer... params) {
		try {
			netRequest = new Generate_Excel_By_Id_NetRequest(context);
			result = netRequest.doGenerateExcelByTargetId(target_id, email);
		} catch (Exception e) {
			result = new NetResult_Bean();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(NetResult_Bean result) {
		super.onPostExecute(result);
		if (result != null) {
			if (result.getResult().equals("0")) // 请求成功
				Tool_Utils.showInfo(context, "成功");
			else
				Tool_Utils.showInfo(context, "失败");
		}
	}
}

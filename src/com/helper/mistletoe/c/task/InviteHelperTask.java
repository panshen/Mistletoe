package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.net.request.Add_Helper_By_Id_NetRequest;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.Tool_Utils;

import android.content.Context;
import android.os.AsyncTask;

public class InviteHelperTask extends AsyncTask<String, Integer, Boolean> {

	private Context context;
	private Add_Helper_By_Id_NetRequest netRequest;
	private Helpers_Sub_Bean helper;
	private Boolean result = false;

	public InviteHelperTask(Context mContext, Helpers_Sub_Bean helper) {
		this.context = mContext;
		this.helper = helper;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		try {
			netRequest = new Add_Helper_By_Id_NetRequest(context);
			result = netRequest.doAddHelperById(helper.getHelper_msg(), helper.getHelper_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result) {// ture为需要更新，false不需要更新
			Tool_Utils.showInfo(context, "添加成功！");
			// 发送同步helper指令
			Instruction_Utils.sendInstrustion(context, Instruction_Utils.SYNCHRONOUS_HELPER);
		} else {
			Helpers_Sub_Bean helper = new Helpers_Sub_Bean();
			HelperManager helperManager = new HelperManager();
			helper = helperManager.ReadHelperFromDBById(context, helper.getHelper_id());
			if (helper != null) {
				if (helper.getHelper_name() == null) {
					Tool_Utils.showInfo(context, "添加失败！请检查网络状态！");
				} else {
					Tool_Utils.showInfo(context, "您们已经是好友不需要添加！");
				}
			}
		}
	}
}

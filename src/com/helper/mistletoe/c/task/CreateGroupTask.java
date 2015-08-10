package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Create_Group_NetRequest;
import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.Tool_Utils;

import android.content.Context;
import android.os.AsyncTask;

public class CreateGroupTask extends AsyncTask<String, Integer, Boolean> {

	private Context context;
	private Create_Group_NetRequest netRequest;
	private Group_Bean group;
	private Boolean result = false;

	public CreateGroupTask(Context context, Group_Bean group) {
		this.context = context;
		this.group = group;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		try {
			netRequest = new Create_Group_NetRequest(context);
			result = netRequest.doCreateGroup(group);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {// ture为需要更新，false不需要更新
			Instruction_Utils.sendInstrustion(context, Instruction_Utils.SYNCHRONOUS_GROUP);
			Tool_Utils.showInfo(context, "创建成功");
		} else {
			Tool_Utils.showInfo(context, "创建失败，请在网络状态良好的情况下，重新操作！");
		}
	}
}

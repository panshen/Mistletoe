package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Update_Group_Members_NetRequest;
import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import android.content.Context;
import android.os.AsyncTask;

public class UpdataGroupMemberTask extends AsyncTask<String, Integer, Boolean> {

	private Context context;
	private Update_Group_Members_NetRequest netRequest;
	private Group_Bean group;
	private Boolean result = false;

	public UpdataGroupMemberTask(Context context, Group_Bean group) {
		this.context = context;
		this.group = group;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		try {
			netRequest = new Update_Group_Members_NetRequest(context);
			result = netRequest.doUpdateGroupMembers(group);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {// ture为需要更新，false不需要更新
			// 发送同步单个group的组成员指令
			Instruction_Utils.sendInstrustion(context, Instruction_Utils.SYNCHRONOUS_GROUP_MEMBER, group.getGroup_id());
		}
	}
}

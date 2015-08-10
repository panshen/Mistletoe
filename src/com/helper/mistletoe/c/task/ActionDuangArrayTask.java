package com.helper.mistletoe.c.task;

import java.util.ArrayList;

import com.helper.mistletoe.m.db.TargetManager;
import com.helper.mistletoe.m.net.request.Action_Duang_Array_NetRequest;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.Tool_Utils;

import android.content.Context;
import android.os.AsyncTask;

public class ActionDuangArrayTask extends AsyncTask<Integer, Integer, Boolean> {

	private Context context;
	private Action_Duang_Array_NetRequest netRequest;
	private Integer target_id;
	private ArrayList<Integer> memnerIds;
	private Boolean result = false;

	public ActionDuangArrayTask(Context context) {
		this.context = context;
		memnerIds = new ArrayList<Integer>();
	}

	@Override
	protected Boolean doInBackground(Integer... params) {
		target_id = params[0];
		memnerIds.clear();
		try {
			TargetManager dbWorkOj = TargetManager.getInstance(context.getApplicationContext());
			Target_Bean target = dbWorkOj.getTarget(target_id, "");
			target.readTargetMember_Local(context.getApplicationContext());
			User_Bean user = new User_Bean();
			user.readData(context.getApplicationContext());
			ArrayList<TargetMember_Bean> members = target.getLoc_TargetMember().getTargetMemberList();
			for (int i = 0; i < members.size(); i++) {
				if (members.get(i).getHelper_id() == user.getUser_id()) {

				} else {
					memnerIds.add(members.get(i).getHelper_id());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			netRequest = new Action_Duang_Array_NetRequest(context);
			result = netRequest.doActionDuang(memnerIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {// ture为需要更新，false不需要更新
			// 发送同步单个Helper指令
			// Instruction_Utils.sendInstrustion(context,
			// Instruction_Utils.SYNCHRONOUS_HELPER_BY_ID, helper_id);
			Tool_Utils.showInfo(context, "duang成功!");
		} else {
			// 如果不是，则不可以duang，并提示用户，只有加为好友才可以duang
			Tool_Utils.showInfo(context, "只有加为好友才可以duang!");
		}
	}
}

package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Add_Cost_Tags_By_Id_NetRequest;
import com.helper.mistletoe.m.pojo.NetResult_Bean;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.Tool_Utils;

import android.content.Context;
import android.os.AsyncTask;

public class AddCostTagsByTargetIdTask extends
		AsyncTask<Integer, Integer, NetResult_Bean> {

	private Context context;
	private Add_Cost_Tags_By_Id_NetRequest netRequest;
	private Integer target_id;
	private String tag;
	private NetResult_Bean result;

	public AddCostTagsByTargetIdTask(Context context, Integer target_id,
			String tag) {
		this.context = context;
		this.target_id = target_id;
		this.tag = tag;
	}

	@Override
	protected NetResult_Bean doInBackground(Integer... params) {
		try {
			netRequest = new Add_Cost_Tags_By_Id_NetRequest(context);
			result = netRequest.doAddCostTagsByTargetId(target_id, tag);
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
			if (result.getResult().equals("0")){ // 请求成功
				Tool_Utils.showInfo(context, "添加成功");
				//发送费用标签的指令
				 Instruction_Utils.sendInstrustion(context, Instruction_Utils.SYNCHRONOUS_COSTTAG,target_id);
				AirLock_Work.syncScheduleTag();
			}else{
				Tool_Utils.showInfo(context, "添加失败");
			}
		}
	}
}

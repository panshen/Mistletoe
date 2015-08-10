package com.helper.mistletoe.m.net.request;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.Custom_Enum.SyncStatus;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.TaskList_Bean;
import com.helper.mistletoe.m.pojo.Task_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Task_GetList_NetRequest extends Template_NetRequest {
	public Task_GetList_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_TASK_GETBYTARGET);
	}

	public TaskList_Bean doRequest(Target_Bean target) {
		TaskList_Bean result = new TaskList_Bean();
		try {
			// 请求参数
			// 获取data
			String data = fromateData(target);
			// 连接
			openConnection(data);
			// 返回结果
			String tResc = "";
			if (getResultData().getResult().equals("0")) {
				tResc = getResultData().getLoc_data();
				// 访问成功
				Gson gson = new Gson();
				ArrayList<Task_Bean> tTempList = new ArrayList<Task_Bean>();
				tTempList = gson.fromJson(tResc, new TypeToken<ArrayList<Task_Bean>>() {
				}.getType());
				result.setList(tTempList);
				for (Task_Bean i : result.getList()) {
					i.setSyncStatus(SyncStatus.Synced);
				}
			} else {
				tResc = getResultData().getResult_msg();
				// 访问失败
			}
		} catch (Exception e) {
			result = new TaskList_Bean();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	private String fromateData(Target_Bean target) {
		String result = "";
		try {
			JSONObject jData = new JSONObject();

			jData.put("target_id", (int) target.getTarget_id());

			result = jData.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public TaskList_Bean doRequest(int targetId) {
		Target_Bean target = new Target_Bean();
		target.setTarget_id(targetId);
		return doRequest(target);
	}

}
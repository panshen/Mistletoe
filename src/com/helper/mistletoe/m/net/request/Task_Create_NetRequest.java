package com.helper.mistletoe.m.net.request;

import org.json.JSONObject;

import android.content.Context;

import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Task_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Task_Create_NetRequest extends Template_NetRequest {
	public Task_Create_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_TASK_CREATE);
	}

	public boolean doRequest(Task_Bean task) {
		boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data = fromateData(task);
			// 连接
			openConnection(data);
			// 返回结果
			String tResc = "";
			if (getResultData().getResult().equals("0")) {
				tResc = getResultData().getLoc_data();
				// 访问成功
				result = true;
			} else {
				tResc = getResultData().getResult_msg();
				// 访问失败
			}
		} catch (Exception e) {
			result = false;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	private String fromateData(Task_Bean task) {
		String result = "";
		try {
			JSONObject jData = new JSONObject();

			jData.put("target_id", (int) task.getLoc_TargetId());
			jData.put("client_ref_id", (String) task.getClient_ref_id());
			jData.put("description", (String) task.getDescription());
			if (task.owner_id != null) {
				if ((int) task.getOwner_id()>0) {
					jData.put("owner_id", (int) task.getOwner_id());
				}
				}
			if (task.weights != null) {
				if ( (int) task.getWeights()>0) {
					jData.put("weights", (int) task.getWeights());
				}
			}
			if (task.begin_time!=null) {
				if ( (long) task.getBegin_time()>0) {
					jData.put("begin_time", (long) task.getBegin_time());
				}
			}
			if (task.end_time!=null) {
				if ((long) task.getEnd_time()>0) {
					jData.put("end_time", (long) task.getEnd_time());
				}
			}
			result = jData.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

}
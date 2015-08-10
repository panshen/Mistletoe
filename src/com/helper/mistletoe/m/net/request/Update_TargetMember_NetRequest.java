package com.helper.mistletoe.m.net.request;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Update_TargetMember_NetRequest extends Template_NetRequest {
	public Update_TargetMember_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_TARGETMEMBER_UPDATE);
	}

	public boolean doUploadGroupMembers(int targetId, String members) throws Exception {
		boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data = fromateData(targetId, new JSONArray(members));
			// 连接
			openConnection(data);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				String t_resc = getResultData().getLoc_data();
				// 访问成功
				result = true;
			} else {
				String t_resc = getResultData().getResult_msg();
				// 访问失败
				result = false;
			}
		} catch (Exception e) {
			result = false;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String fromateData(int targetId, JSONArray members) throws Exception {
		String result = null;
		try {
			JSONObject jData = new JSONObject();

			jData.put("target_id", targetId);
			jData.put("members", members);

			result = jData.toString();
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
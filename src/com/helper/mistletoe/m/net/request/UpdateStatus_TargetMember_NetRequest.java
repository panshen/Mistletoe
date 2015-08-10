package com.helper.mistletoe.m.net.request;

import org.json.JSONObject;

import android.content.Context;

import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class UpdateStatus_TargetMember_NetRequest extends Template_NetRequest {
	public UpdateStatus_TargetMember_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_TARGETMEMBER_UPDATESTATUS);
	}

	public boolean doUploadGroupMembers(int targetId, int requestType, Integer memberId) {
		boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data = fromateData(targetId, requestType, memberId);
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
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	private String fromateData(int targetId, int requestType, Integer memberId) {
		String result = null;
		try {
			JSONObject jData = new JSONObject();

			jData.put("target_id", targetId);
			jData.put("status_req", requestType);
			if (memberId != null) {
				jData.put("member_id", memberId);
			}

			result = jData.toString();
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
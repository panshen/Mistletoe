package com.helper.mistletoe.m.net.request;

import org.json.JSONObject;

import android.content.Context;

import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class UpdateSettings_TargetMember_NetRequest extends Template_NetRequest {
	public UpdateSettings_TargetMember_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_TARGETMEMBER_UPDATESETTINGS);
	}

	public boolean doUploadGroupMembers(int targetId, String target_stick, String target_flag, String accept_push_msg) throws Exception {
		boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data = fromateData(targetId, target_stick, target_flag, accept_push_msg);
			// 连接
			openConnection(data);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				String t_resc = getResultData().getLoc_data();
				// 访问成功
				JSONObject t_Json = new JSONObject(t_resc);
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

	private String fromateData(int targetId, String target_stick, String target_flag, String accept_push_msg) throws Exception {
		String result = null;
		try {
			JSONObject jData = new JSONObject();

			jData.put("target_id", targetId);
			if (target_stick != null) {
				jData.put("target_stick", target_stick);
			}
			if (target_flag != null) {
				jData.put("target_flag", target_flag);
			}
			if (accept_push_msg != null) {
				jData.put("accept_push_msg", accept_push_msg);
			}

			result = jData.toString();
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
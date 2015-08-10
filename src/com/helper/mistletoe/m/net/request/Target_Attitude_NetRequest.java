package com.helper.mistletoe.m.net.request;

import org.json.JSONObject;

import android.content.Context;

import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Target_Attitude_NetRequest extends Template_NetRequest {
	public Target_Attitude_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_TARGET_ATTITUDE);
	}

	public int doTargetAttitude(Target_Bean target) {
		int result = -1;
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

				// TODO 这里需要对结果进行处理
				result = Transformation_Util.String2int(tResc);
			} else {
				// 访问失败
				tResc = getResultData().getResult_msg();

				result = -1;
			}
		} catch (Exception e) {
			result = -1;
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

	public int doTargetAttitude(int targetId) {
		int result = -1;
		try {
			Target_Bean tTarget = new Target_Bean();
			tTarget.setTarget_id(targetId);
			result = doTargetAttitude(tTarget);
		} catch (Exception e) {
			result = -1;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
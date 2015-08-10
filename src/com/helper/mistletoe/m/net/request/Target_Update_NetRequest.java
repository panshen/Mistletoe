package com.helper.mistletoe.m.net.request;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class Target_Update_NetRequest extends Template_NetRequest {
	public Target_Update_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_TARGET_UPDATE);
	}

	public boolean updateTarget(Target_Bean target) {
		boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data = fromateData(target);
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
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	private String fromateData(Target_Bean target) {
		String result = "";
		try {
			JSONObject jData = new JSONObject();

			if (target.target_id != null) {
				jData.put("target_id", target.getTarget_id());
			}
			if (target.summary != null) {
				jData.put("summary", target.getSummary());
			}
			if (target.view_flag != null) {
				jData.put("view_flag", target.getView_flag());
			}
			if (target != null) {
				jData.put("eta_time", target.getEta_time());
			}
			if (target.description != null) {
				jData.put("description", target.getDescription());
			}
			if (target.target_type != null) {
				jData.put("target_type", (int) target.getTarget_type().toInt());
			}
			if (target.head_pics != null) {
				jData.put("head_pics", (JSONArray) target.getHead_pics_JSONArray());
			}

			result = jData.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
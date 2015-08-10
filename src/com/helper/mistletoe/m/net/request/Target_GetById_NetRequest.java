package com.helper.mistletoe.m.net.request;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Target_GetById_NetRequest extends Template_NetRequest {
	public Target_GetById_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_TARGET_GETBYID);
	}

	public ArrayList<Target_Bean> doUploadGroupMembers(String ids) throws Exception {
		ArrayList<Target_Bean> result = null;
		try {
			// 请求参数
			// 获取data
			String data = fromateData(new JSONArray(ids));
			// 连接
			openConnection(data);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				String t_resc = getResultData().getLoc_data();
				// 访问成功
				result = new ArrayList<Target_Bean>();
				Gson gson = new Gson();
				JSONObject t_resJson = new JSONObject(t_resc);
				JSONArray t_Ids = new JSONArray(ids);
				for (int i = 0; i < t_Ids.length(); i++) {
					JSONObject t_subTarget = t_resJson.getJSONObject(t_Ids.getString(i));
					Target_Bean tTempTarget = gson.fromJson(t_subTarget.toString(), Target_Bean.class);
					tTempTarget.last_updated_time = null;
					result.add(tTempTarget);
				}
			} else {
				String t_resc = getResultData().getResult_msg();
				// 访问失败
				result = null;
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String fromateData(JSONArray ids) throws Exception {
		String result = null;
		try {
			JSONObject jData = new JSONObject();

			if ((ids != null) && (ids.length() > 0)) {
				jData.put("target_id", ids);
			}

			result = jData.toString();
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
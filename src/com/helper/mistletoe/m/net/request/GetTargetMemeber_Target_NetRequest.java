package com.helper.mistletoe.m.net.request;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class GetTargetMemeber_Target_NetRequest extends Template_NetRequest {
	/**
	 * 历史目标
	 */
	public final static int TARGET_GET_COUNT = 1000;

	public GetTargetMemeber_Target_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_TARGETMEMBER_FINDMEMBERS);
	}

	public ArrayList<TargetMember_Bean> getList(int target_id) throws Exception {
		ArrayList<TargetMember_Bean> result = null;
		try {
			// 请求参数
			// 获取data
			String data = fromateData(target_id);
			// 连接
			openConnection(data);
			// 返回结果
			String res = "";
			if (getResultData().getResult().equals("0")) {
				res = getResultData().getLoc_data();
				JSONArray jsonArray = new JSONArray(res);
				Gson gson = new Gson();
				result = new ArrayList<TargetMember_Bean>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject tjson = jsonArray.getJSONObject(i);
					result.add(gson.fromJson(tjson.toString(), TargetMember_Bean.class));
				}
			} else {
				res = getResultData().getResult_msg();
				result = null;
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String fromateData(int target_id) throws Exception {
		String result = "";
		try {
			JSONObject jData = new JSONObject();

			jData.put("target_id", target_id);

			result = jData.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
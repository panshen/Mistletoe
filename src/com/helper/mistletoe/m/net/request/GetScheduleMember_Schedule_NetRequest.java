package com.helper.mistletoe.m.net.request;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class GetScheduleMember_Schedule_NetRequest extends Template_NetRequest {
	public GetScheduleMember_Schedule_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_GETBYNOTEID_TARGETNOTERECEIVER);
	}

	public ArrayList<Schedule_Bean> createTarget(ArrayList<Schedule_Bean> schedules) throws Exception {
		ArrayList<Schedule_Bean> result = null;
		try {
			// 请求参数
			JSONArray t_JSArray = new JSONArray();
			for (int i = 0; i < schedules.size(); i++) {
				t_JSArray.put(i, schedules.get(i).getId());
			}
			// 获取data
			String data = fromateData(t_JSArray);
			// 连接
			openConnection(data);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				String t_resc = getResultData().getLoc_data();
				result = schedules;
				JSONObject t_JsonO = new JSONObject(t_resc);
				for (int i = 0; i < result.size(); i++) {
					String t_sRec = t_JsonO.getString("" + result.get(i).getId());
					result.get(i).getLoc_Member().clear();
					result.get(i).getLoc_Member().addAll(openJson(t_sRec));
				}
			} else {
				String t_resc = getResultData().getResult_msg();
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String fromateData(JSONArray schedules) throws Exception {
		String result = "";
		try {
			JSONObject jData = new JSONObject();

			if (schedules != null) {
				jData.put("note_id", schedules);
			}

			result = jData.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private ArrayList<TargetMember_Bean> openJson(String json) throws Exception {
		ArrayList<TargetMember_Bean> result = null;
		try {
			JSONArray jsonArra = new JSONArray(json);
			Gson gson = new Gson();
			result = new ArrayList<TargetMember_Bean>();
			for (int i = 0; i < jsonArra.length(); i++) {
				TargetMember_Bean t_me = new TargetMember_Bean();
				t_me = gson.fromJson(jsonArra.get(i).toString(), TargetMember_Bean.class);
				result.add(t_me);
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
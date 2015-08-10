package com.helper.mistletoe.m.net.request;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.helper.mistletoe.m.db.TargetManager;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class Target_GetList_NetRequest extends Template_NetRequest {
	/**
	 * 历史目标
	 */
	public final static int TARGET_GET_COUNT = 10000;

	public Target_GetList_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_TARGET_GETBYSTATUS);
	}

	public ArrayList<Target_Bean> getList() {
		ArrayList<Target_Bean> result = null;
		try {
			// 请求参数
			long last_update_time = TargetManager.getInstance(context).getLastUpdateTime();
			// 获取data
			String data = fromateData(last_update_time);
			// 连接
			openConnection(data);
			// 返回结果
			result = new ArrayList<Target_Bean>();
			String tResc = "";
			if (getResultData().getResult().equals("0")) {
				tResc = getResultData().getLoc_data();
				// 访问成功
				if (!tResc.equals("")) {
					Gson gson = new Gson();
					JSONArray jsonArray = new JSONArray(tResc);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject tjson = jsonArray.getJSONObject(i);
						Target_Bean tTempTarget = gson.fromJson(tjson.toString(), Target_Bean.class);
						tTempTarget.getEta_time();
						result.add(tTempTarget);
					}
				}
			} else {
				tResc = getResultData().getResult_msg();
				// 访问失败
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	private String fromateData(long last_update_time) {
		String result = "";
		try {
			JSONObject jData = new JSONObject();

			jData.put("last_updated_time", last_update_time);
			jData.put("count", TARGET_GET_COUNT);

			result = jData.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
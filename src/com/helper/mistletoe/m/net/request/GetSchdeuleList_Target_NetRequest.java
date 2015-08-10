package com.helper.mistletoe.m.net.request;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.helper.mistletoe.m.db.SchdeuleManager;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class GetSchdeuleList_Target_NetRequest extends Template_NetRequest {
	/**
	 * 历史目标
	 */
	public final static int TARGET_GET_COUNT = 80;

	public GetSchdeuleList_Target_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_NOTE_GET);
	}

	public ArrayList<Schedule_Bean> getList(int target_id) throws Exception {
		ArrayList<Schedule_Bean> result = new ArrayList<Schedule_Bean>();
		try {
			// 请求参数
			long last_update_time = SchdeuleManager.getInstance(context).getLastUpdateTime(target_id);
			// 获取data
			String data = fromateData(target_id, last_update_time);
			// 连接
			openConnection(data);
			// 返回结果
			String tResc = "";
			if (getResultData().getResult().equals("0")) {
				tResc = getResultData().getLoc_data();
				// 访问成功
				Gson gson = new Gson();
				JSONArray jsonArray = new JSONArray(tResc);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject tjson = jsonArray.getJSONObject(i);
					Integer tId = null;
					// 先把owner_id取出来
					if ((tjson.has("owner_id")) && (!tjson.isNull("owner_id"))) {
						tId = tjson.getInt("owner_id");
						tjson.remove("owner_id");
					}
					// 映射为JavaBean
					Schedule_Bean tTempSchedule = gson.fromJson(tjson.toString(), Schedule_Bean.class);
					// 如果有owner_id，保存起来
					if (tId != null) {
						tTempSchedule.getOwner_id().add(tId);
					}
					// 保存receivers
					if ((tjson.has("receivers")) && (!tjson.isNull("receivers"))) {
						tTempSchedule.setLoc_ReceiverString(tjson.getString("receivers"));
					}
					result.add(tTempSchedule);
				}
			} else {
				tResc = getResultData().getResult_msg();
				// 访问失败
			}
		} catch (Exception e) {
			result = new ArrayList<Schedule_Bean>();
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String fromateData(int target_id, long last_updated_time) throws Exception {
		String result = "";
		try {
			JSONObject jData = new JSONObject();

			jData.put("target_id", target_id);
			jData.put("last_updated_time", last_updated_time);
			jData.put("count", TARGET_GET_COUNT);

			result = jData.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
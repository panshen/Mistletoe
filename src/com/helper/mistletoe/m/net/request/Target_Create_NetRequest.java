package com.helper.mistletoe.m.net.request;


import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class Target_Create_NetRequest extends Template_NetRequest {
	public Target_Create_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_TARGET_CREATE);
	}

	public Target_Bean createTarget(Target_Bean target, JSONArray members) throws Exception {
		Target_Bean result = null;
		try {
			// 请求参数
			// 获取data
			String data = fromateData(target, members);
			// 连接
			openConnection(data);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				String t_resc = getResultData().getLoc_data();
				Gson gson = new Gson();
				result = gson.fromJson(t_resc, Target_Bean.class);
				result.setLoc_TargetTag(target.getLoc_TargetTag());
				AirLock_Work.syncTask(result.getTarget_id());
				AirLock_Work.syncSchedule(context, result.getTarget_id());
			} else {
				String t_resc = getResultData().getResult_msg();
				result = target;
				result.setTarget_id(-2);
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String fromateData(Target_Bean target, JSONArray members) throws Exception {
		String result = "";
		try {
			JSONObject jData = new JSONObject();

			jData.put("client_ref_id", target.getLoc_TargetTag());
			if (target.summary != null) {
				jData.put("summary", target.getSummary());
			}
			jData.put("members", members);
			jData.put("view_flag", target.getView_flag());
			jData.put("target_flag", target.getTarget_flag());
			jData.put("eta_time", (Long) target.getEta_time());
			if (target.budget != null) {
				jData.put("budget", target.getBudget());
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
			JSONArray a=new JSONArray();
			for (int i = 0; i < target.getTasks().size(); i++) {
				JSONObject b = new JSONObject();
				b.put("description", target.getTasks().get(i).getDescription());
				if (target.getTasks().get(i).weights!=null) {
					if ((target.getTasks().get(i).getWeights()>0)){
						b.put("weights",  target.getTasks().get(i).getWeights());		
					}
				}
				if (target.getTasks().get(i).owner_id!=null) {
					if ((target.getTasks().get(i).getOwner_id()>0)) {
						b.put("owner_id",  target.getTasks().get(i).getOwner_id());		
					}
				}
				if (target.getTasks().get(i).begin_time!=null) {
					if ((target.getTasks().get(i).getBegin_time()>0)) {
						b.put("begin_time",  target.getTasks().get(i).getBegin_time());		
					}
				}
				if (target.getTasks().get(i).end_time!=null) {
					if ((target.getTasks().get(i).getEnd_time()>0)) {
						b.put("end_time",  target.getTasks().get(i).getEnd_time());		
					}
				}
				a.put(b);
			}
			jData.put("task", a);
			result = jData.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
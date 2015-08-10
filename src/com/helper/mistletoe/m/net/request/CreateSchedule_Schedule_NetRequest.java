package com.helper.mistletoe.m.net.request;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleType;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class CreateSchedule_Schedule_NetRequest extends Template_NetRequest {
	public CreateSchedule_Schedule_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_NOTE_CREATE);
	}

	public Schedule_Bean createTarget(Schedule_Bean target) throws Exception {
		Schedule_Bean result = null;
		try {
			// 请求参数
			JSONArray t_Mem = new JSONArray();
			t_Mem = new JSONArray(target.getLoc_MembersString());
			// 获取data
			String data = fromateData(target, t_Mem);
			// 连接
			openConnection(data);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				String t_ire = getResultData().getLoc_data();
				JSONObject jsonObject = new JSONObject(t_ire);
				result = target;
				result.setId(jsonObject.getInt("note_id"));
			} else {
				String t_ire = getResultData().getResult_msg();
				result = target;
				result.setId(-2);
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String fromateData(Schedule_Bean target, JSONArray receivers) throws Exception {
		String result = "";
		try {
			JSONObject jData = new JSONObject();

			if (target.loc_ItemTag != null) {
				jData.put("client_ref_id", target.getLoc_ItemTag());
			}
			if (target.loc_TargetId != null) {
				jData.put("target_id", target.getLoc_TargetId());
			}
			if (target.note_type != null) {
				jData.put("note_type", target.getNote_type().toInt());
			}
			if ((receivers != null) && (receivers.length() > 0)) {
				jData.put("view_flag", 2);
			} else {
				jData.put("view_flag", 1);
			}
			if (target.content != null) {
				String t_conten = target.getContent();
				if (t_conten.equals("")) {
					t_conten += " ";
				}
				jData.put("content", t_conten);
			}
			if ((receivers != null) && (receivers.length() > 0)) {
				jData.put("receivers", receivers);
			}
			if ((target.file_id != null) && (!target.file_id.trim().equals(""))) {
				jData.put("file_id", target.getFile_id());
			}
			if (target.reminder_time != null) {
				jData.put("reminder_time", target.getReminder_time());
			}
			if (target.review_rank != null) {
				jData.put("review_rank", target.getReview_rank());
			}
			if (target.gps_latitude != null) {
				jData.put("gps_latitude", target.getGps_latitude());
			}
			if (target.gps_longitude != null) {
				jData.put("gps_longitude", target.getGps_longitude());
			}
			if (target.cost != null) {
				jData.put("cost", (float) target.getCost());
			}
			if (target.getNote_type() == ScheduleType.CostApply) {
				jData.put("cost_type", (String) target.getCost_type());
			}
			if (target.cost_desc != null) {
				jData.put("cost_desc", (String) target.getCost_desc());
			}
			if (target.transaction_time != null) {
				jData.put("transaction_time", (long) target.getTransaction_time());
			}
			if (target.balance != null) {
				jData.put("balance", (float) target.getBalance());
			}
			if (target.owner_id != null) {
				jData.put("owner_id", (JSONArray) target.getOwner_id_JSONArray());
			}
			if (target.tag_id != null) {
				jData.put("tag_id", (int) target.getTag_id());
			}

			result = jData.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}

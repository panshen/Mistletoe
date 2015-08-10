package com.helper.mistletoe.m.work.be;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.helper.mistletoe.m.db.impl.AirLock_DB;
import com.helper.mistletoe.m.net.request.TargetNoteReceiver_GetByNoteId_NetRequest;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.FactoryWork_v3;

public class GetScheduleReadingState_Target_Mode extends FactoryWork_v3 {
	private Schedule_Bean schedule;

	public GetScheduleReadingState_Target_Mode(Schedule_Bean schedule) {
		try {
			this.schedule = schedule;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			TargetNoteReceiver_GetByNoteId_NetRequest tNtRequest = new TargetNoteReceiver_GetByNoteId_NetRequest(
					getContext());
			JSONArray tNoteIds = new JSONArray();
			if ((schedule.id != null) && (((int) schedule.getId()) > 0)) {
				tNoteIds.put((int) schedule.getId());
			}
			String tRes = "";
			if (tNoteIds.length() > 0) {
				tRes = tNtRequest.getList(tNoteIds.toString());
			}
			// 如果网络访问成功处理结果
			if (!tRes.equals("")) {
				ArrayList<TargetMember_Bean> tMemberList = new ArrayList<TargetMember_Bean>();
				JSONObject tResJson = new JSONObject(tRes);
				if (tResJson.has("" + schedule.getId())) {
					JSONArray tMemberReadState = new JSONArray(
							tResJson.getString("" + schedule.getId()));
					// 遍历读取人员
					for (int i = 0; i < tMemberReadState.length(); i++) {
						JSONObject tSubJson = tMemberReadState.getJSONObject(i);
						TargetMember_Bean tSubMember = new TargetMember_Bean();
						tSubMember.copyData(AirLock_DB.select_helper(
								getContext(), tSubJson.getInt("user_id")));
						tSubMember.setRead_time(tSubJson.getLong("read_time"));
						tMemberList.add(tSubMember);
					}
					schedule.getLoc_Member().clear();
					schedule.getLoc_Member().addAll(tMemberList);
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public Schedule_Bean getSchedule() {
		if (schedule == null) {
			schedule = new Schedule_Bean();
		}
		return schedule;
	}

}
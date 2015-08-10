package com.helper.mistletoe.m.work.be.cloud;

import java.util.ArrayList;

import org.json.JSONArray;

import com.helper.mistletoe.m.db.TargetManager;
import com.helper.mistletoe.m.net.request.Target_GetById_NetRequest;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.ServiceWork_v3;

public class SyncTargetById_Target_Mode extends ServiceWork_v3 {
	private String targetIds;

	public SyncTargetById_Target_Mode(String targetIds) {
		try {
			this.targetIds = targetIds;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			ArrayList<Target_Bean> targetList = new ArrayList<Target_Bean>();
			Target_GetById_NetRequest netRequest = new Target_GetById_NetRequest(
					getContext());
			targetList = netRequest.doUploadGroupMembers(targetIds);
			// 保存Target
			TargetManager.getInstance(getContext()).writeTargetList(targetList);
			// 通知UI刷新
			JSONArray t_JsonArray = new JSONArray(targetIds);
			for (int i = 0; i < t_JsonArray.length(); i++) {
				Broadcast_Sender.targetChanged(getContext());
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
package com.helper.mistletoe.m.work.be.cloud;

import java.util.ArrayList;

import com.helper.mistletoe.m.db.TargetManager;
import com.helper.mistletoe.m.net.request.Target_GetList_NetRequest;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.ServiceWork_v3;

public class SyncTargetList_Target_Mode extends ServiceWork_v3 {

	public SyncTargetList_Target_Mode() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			int listLength = 0;
			boolean haveMore = true;
			ArrayList<Target_Bean> targetList = new ArrayList<Target_Bean>();
			Target_GetList_NetRequest netRequest = new Target_GetList_NetRequest(getContext());
			while (haveMore) {
				targetList = netRequest.getList();
				// 保存Target
				TargetManager.getInstance(getContext()).writeTargetList(targetList);
				listLength = targetList.size();
				haveMore = listLength >= Target_GetList_NetRequest.TARGET_GET_COUNT;
			}

			// TODO 清理掉多余的目标
			TargetManager.getInstance(getContext()).removeSameTarget();

			Broadcast_Sender.targetChanged(getContext());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
package com.helper.mistletoe.m.work.be.cloud;

import java.util.ArrayList;

import com.helper.mistletoe.m.db.SchdeuleManager;
import com.helper.mistletoe.m.net.request.GetSchdeuleList_Target_NetRequest;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.TestClass_Util;
import com.helper.mistletoe.util.prcomode.v3.ServiceWork_v3;
import com.helper.mistletoe.util.sysconst.MainConst;

public class SyncSchedule_Target_Mode extends ServiceWork_v3 {
	private int targetId;

	public SyncSchedule_Target_Mode(int targetId) {
		try {
			this.targetId = targetId;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			int listLength = 0;
			boolean haveMore = true;
			ArrayList<Schedule_Bean> targetList = new ArrayList<Schedule_Bean>();
			GetSchdeuleList_Target_NetRequest netRequest = new GetSchdeuleList_Target_NetRequest(getContext());
			while (haveMore) {
				targetList = netRequest.getList(targetId);
				// 保存Target
				SchdeuleManager.getInstance(getContext()).writeSchdeuleList(targetList, targetId);
				listLength = targetList.size();
				haveMore = listLength >= GetSchdeuleList_Target_NetRequest.TARGET_GET_COUNT;
				// 通知UI刷新
				Broadcast_Sender.scheduleChanged(getContext(), "" + targetId);
			}

			// TODO 清理掉多余的目标
			SchdeuleManager.getInstance(getContext()).removeSameSchedule();

			// 通知UI刷新
			Broadcast_Sender.scheduleChanged(getContext(), "" + targetId);

			if (!MainConst.DEVS_BUSINESS_MODEL) {
				// TODO 调用测试函数
				// TestClass_Util.startTest(getContext());
				TestClass_Util.copyDatabase(getContext());
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}

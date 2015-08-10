package com.helper.mistletoe.m.work.be.cloud;

import com.helper.mistletoe.m.net.request.HighLight_Schedule_NetRequest;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.util.prcomode.v3.ServiceWork_v3;

public class TopSchedule_Mode extends ServiceWork_v3 {
	private String target_id;
	private Integer note_id;
	private int mHighLight;

	public TopSchedule_Mode(String target_id, Integer note_id, int highLight) {
		super();
		try {
			this.target_id = target_id;
			this.note_id = note_id;
			this.mHighLight = highLight;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			// 发起网络请求
			HighLight_Schedule_NetRequest ntRObj = new HighLight_Schedule_NetRequest(
					getContext());
			ntRObj.createTarget(target_id, "" + note_id, "" + mHighLight);
			// 刷新进度更新列表
			AirLock_Work.syncSchedule(getContext(),
					Transformation_Util.String2int(target_id, -1));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
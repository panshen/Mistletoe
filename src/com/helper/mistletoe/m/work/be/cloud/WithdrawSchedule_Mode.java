package com.helper.mistletoe.m.work.be.cloud;

import com.helper.mistletoe.m.net.request.Withdraw_Schedule_NetRequest;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.util.prcomode.v3.ServiceWork_v3;

public class WithdrawSchedule_Mode extends ServiceWork_v3 {
	private String target_id;
	private String note_id;

	public WithdrawSchedule_Mode(String target_id, String note_id) {
		super();
		try {
			this.target_id = target_id;
			this.note_id = note_id;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			// 发起网络请求
			Withdraw_Schedule_NetRequest ntRObj = new Withdraw_Schedule_NetRequest(getContext());
			ntRObj.createTarget(note_id);

			Broadcast_Sender.scheduleChanged_Cloud(getContext(), Transformation_Util.String2int(target_id));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
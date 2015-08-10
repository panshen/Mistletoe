package com.helper.mistletoe.m.work.be.cloud;

import com.helper.mistletoe.m.db.TargetManager;
import com.helper.mistletoe.m.net.request.Target_Attitude_NetRequest;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.ServiceWork_v3;

public class Target_Attitude_Mode extends ServiceWork_v3 {
	private Target_Bean mTarget;// 目标

	public Target_Attitude_Mode(Target_Bean target) {
		super();
		try {
			this.mTarget = target;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			// 发送到网络
			Target_Attitude_NetRequest netWorkOj = new Target_Attitude_NetRequest(getContext());
			int tAttitude = netWorkOj.doTargetAttitude(mTarget);
			if (tAttitude > 0) {
				TargetManager.getInstance(getContext()).updateAttitude(mTarget, tAttitude);
			}

			Broadcast_Sender.scheduleChanged_Cloud(getContext(), -1);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
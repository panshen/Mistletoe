package com.helper.mistletoe.m.work.be.cloud;

import com.helper.mistletoe.m.net.request.UpdateSettings_TargetMember_NetRequest;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.ServiceWork_v3;

public class TargetMember_UpdateSettings_Mode extends ServiceWork_v3 {
	private int targetId;
	private String target_stick;
	private String target_flag;
	private String accept_push_msg;

	public TargetMember_UpdateSettings_Mode(int targetId, String target_stick, String target_flag, String accept_push_msg) {
		super();
		try {
			this.targetId = targetId;
			this.target_stick = target_stick;
			this.target_flag = target_flag;
			this.accept_push_msg = accept_push_msg;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			UpdateSettings_TargetMember_NetRequest netWorkOj = new UpdateSettings_TargetMember_NetRequest(getContext());
			boolean t_res = netWorkOj.doUploadGroupMembers(targetId, target_stick, target_flag, accept_push_msg);
			if (t_res) {
				new SyncTargetList_Target_Mode().publishWork(getContext());
				Broadcast_Sender.targetChanged(getContext());
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}
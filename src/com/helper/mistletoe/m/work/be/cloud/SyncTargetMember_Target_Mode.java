package com.helper.mistletoe.m.work.be.cloud;

import java.util.ArrayList;

import com.helper.mistletoe.m.db.TargetMemberManager;
import com.helper.mistletoe.m.net.request.GetTargetMemeber_Target_NetRequest;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.ServiceWork_v3;

public class SyncTargetMember_Target_Mode extends ServiceWork_v3 {
	private int mTargetId;

	public SyncTargetMember_Target_Mode(int targetId) {
		try {
			this.mTargetId = targetId;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			GetTargetMemeber_Target_NetRequest netRequest = new GetTargetMemeber_Target_NetRequest(getContext());
			ArrayList<TargetMember_Bean> member = netRequest.getList(mTargetId);
			if (member != null) {
				// 转入数据库，就完事了。。
				TargetMemberManager.getInstance(getContext()).writeTargetMemberList(mTargetId, member);
			}
			// 通知UI刷新
			Broadcast_Sender.targetMemeberChanged(getApplicationContext(), mTargetId);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}

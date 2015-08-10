package com.helper.mistletoe.m.work.be.cloud;

import java.util.ArrayList;

import com.helper.mistletoe.m.db.TargetMemberManager;
import com.helper.mistletoe.m.net.request.GetTargetMemeber_Target_NetRequest;
import com.helper.mistletoe.m.net.request.Update_TargetMember_NetRequest;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.ServiceWork_v3;

public class TargetMember_Update_Mode extends ServiceWork_v3 {
	private int targetId;
	private String targetMemeber;

	public TargetMember_Update_Mode(int targetId, String targetMember) {
		super();
		try {
			this.targetId = targetId;
			this.targetMemeber = targetMember;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			Update_TargetMember_NetRequest netWorkOj = new Update_TargetMember_NetRequest(
					getContext());
			boolean t_res = netWorkOj.doUploadGroupMembers(targetId,
					targetMemeber);

			GetTargetMemeber_Target_NetRequest netRequest = new GetTargetMemeber_Target_NetRequest(
					getContext());
			ArrayList<TargetMember_Bean> member = netRequest.getList(targetId);
			if (member != null) {
				// 转入数据库，就完事了。。
				TargetMemberManager.getInstance(getContext())
						.writeTargetMemberList(targetId, member);
			}

			if (t_res) {
				Broadcast_Sender.targetMemeberChanged(getContext(), ""
						+ targetId);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
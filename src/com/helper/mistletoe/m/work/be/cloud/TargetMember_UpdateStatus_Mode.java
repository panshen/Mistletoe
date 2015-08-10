package com.helper.mistletoe.m.work.be.cloud;

import java.util.ArrayList;

import com.helper.mistletoe.m.db.TargetMemberManager;
import com.helper.mistletoe.m.net.request.GetTargetMemeber_Target_NetRequest;
import com.helper.mistletoe.m.net.request.UpdateStatus_TargetMember_NetRequest;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.UpdateStatusRequestNumber;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.ServiceWork_v3;

public class TargetMember_UpdateStatus_Mode extends ServiceWork_v3 {
	private int targetId;
	private UpdateStatusRequestNumber requestType;
	private Integer memberId;

	public TargetMember_UpdateStatus_Mode(int targetId,
			UpdateStatusRequestNumber requestType, Integer memberId) {
		super();
		try {
			this.targetId = targetId;
			this.requestType = requestType;
			this.memberId = memberId;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			UpdateStatus_TargetMember_NetRequest netWorkOj = new UpdateStatus_TargetMember_NetRequest(
					getContext());
			boolean t_res = netWorkOj.doUploadGroupMembers(targetId,
					requestType.toInt(), memberId);

			GetTargetMemeber_Target_NetRequest netRequest = new GetTargetMemeber_Target_NetRequest(
					getContext());
			ArrayList<TargetMember_Bean> member = netRequest.getList(targetId);
			if (member != null) {
				// 转入数据库，就完事了。。
				TargetMemberManager.getInstance(getContext())
						.writeTargetMemberList(targetId, member);
			}

			if (t_res) {
				new SyncTargetList_Target_Mode().publishWork(getContext());
				Broadcast_Sender.targetMemeberChanged(getContext(), ""
						+ targetId);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
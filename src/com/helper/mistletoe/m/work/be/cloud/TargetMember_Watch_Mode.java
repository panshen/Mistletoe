package com.helper.mistletoe.m.work.be.cloud;

import org.json.JSONArray;

import com.helper.mistletoe.m.net.request.TargetMember_Watch_NetRequest;
import com.helper.mistletoe.m.net.request.TargetMember_Watch_NetRequest.OperatingState;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.ServiceWork_v3;

public class TargetMember_Watch_Mode extends ServiceWork_v3 {
	private Target_Bean mTarget;// 目标
	private JSONArray mMembers;// 目标
	private OperatingState mState;// 目标

	public TargetMember_Watch_Mode(Target_Bean target, JSONArray members, OperatingState state) {
		super();
		try {
			this.mTarget = target;
			this.mMembers = members;
			this.mState = state;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			// 发送到网络
			TargetMember_Watch_NetRequest netWorkOj = new TargetMember_Watch_NetRequest(getContext());
			netWorkOj.getList(mTarget, mMembers, mState);

			Broadcast_Sender.scheduleChanged_Cloud(getContext(), mTarget.getTarget_id());
			Broadcast_Sender.targetMemeberChanged_Cloud(getContext(), mTarget.getTarget_id());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
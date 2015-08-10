package com.helper.mistletoe.m.work.be;

import com.helper.mistletoe.m.db.TargetManager;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.ServiceWork_v3;

public class Target_ClearUnreadNoteNumber_Mode extends ServiceWork_v3 {
	public int mTargetId;
	public String mTargetTag;

	public Target_ClearUnreadNoteNumber_Mode(int targetId, String targetTag) {
		try {
			this.mTargetId = targetId;
			this.mTargetTag = targetTag;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			TargetManager.getInstance(getApplicationContext()).clearTargetUnreadNumber(mTargetId, mTargetTag);

			Broadcast_Sender.targetChanged(getApplicationContext());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
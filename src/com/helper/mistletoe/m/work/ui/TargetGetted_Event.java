package com.helper.mistletoe.m.work.ui;

import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.work.base.WorkMainThread_Event;
import com.helper.mistletoe.util.ExceptionHandle;

public class TargetGetted_Event extends WorkMainThread_Event {
	private Target_Bean mTarget;

	public TargetGetted_Event(Target_Bean target) {
		try {
			setTarget(target);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public Target_Bean getTarget() {
		if (mTarget == null) {
			mTarget = new Target_Bean();
		}
		return mTarget;
	}

	public void setTarget(Target_Bean target) {
		this.mTarget = target;
	}

}
package com.helper.mistletoe.m.work.base;

import com.helper.mistletoe.m.work.base.inter.WorkAsync;
import com.helper.mistletoe.m.work.base.inter.WorkMainThread;

import de.greenrobot.event.EventBus;

public abstract class WorkEvent_Base {
	public EventBus getEventBus() {
		return EventBus.getDefault();
	}

	public void post(WorkAsync event) {
		getEventBus().post(event);
	}

	public void post(WorkMainThread event) {
		getEventBus().post(event);
	}
}

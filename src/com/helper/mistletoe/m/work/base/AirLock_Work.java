package com.helper.mistletoe.m.work.base;

import android.content.Context;

import com.helper.mistletoe.m.work.be.cloud.SyncScheduleTag_Event;
import com.helper.mistletoe.m.work.be.cloud.SyncSchedule_Target_Mode;
import com.helper.mistletoe.m.work.be.cloud.SyncTargetList_Target_Mode;
import com.helper.mistletoe.m.work.be.cloud.SyncTargetMember_Target_Mode;
import com.helper.mistletoe.m.work.be.cloud.SyncTask_Event;
import com.helper.mistletoe.util.ExceptionHandle;

import de.greenrobot.event.EventBus;

public class AirLock_Work {
	public static void syncTarget(Context context) {
		try {
			new SyncTargetList_Target_Mode().publishWork(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static void syncTargetMember(Context context, int targetId) {
		try {
			new SyncTargetMember_Target_Mode(targetId).publishWork(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static void syncSchedule(Context context, int targetId) {
		try {
			new SyncSchedule_Target_Mode(targetId).publishWork(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
	public static void syncScheduleTag() {
		try {
			EventBus.getDefault().post(new SyncScheduleTag_Event());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static void syncTask(int targetId) {
		try {
			EventBus.getDefault().post(new SyncTask_Event(targetId));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
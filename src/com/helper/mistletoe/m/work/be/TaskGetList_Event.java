package com.helper.mistletoe.m.work.be;

import android.content.Context;

import com.helper.mistletoe.m.db.TaskManager;
import com.helper.mistletoe.m.pojo.TaskList_Bean;
import com.helper.mistletoe.m.work.base.WorkAsync_Event;
import com.helper.mistletoe.m.work.ui.TaskGetted_Event;
import com.helper.mistletoe.util.ExceptionHandle;

import de.greenrobot.event.EventBus;

public class TaskGetList_Event extends WorkAsync_Event {
	private int mTargetId;

	public TaskGetList_Event(int targetId) {
		try {
			setTargetId(targetId);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork(Context context) {
		super.doWork(context);
		try {
			// 从数据库中查询
			TaskList_Bean tTaskList = TaskManager.getInstance(getContext()).readTaskList(getTargetId());
			// 发布结果
			post(new TaskGetted_Event(tTaskList));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public int getTargetId() {
		return mTargetId;
	}

	public void setTargetId(int targetId) {
		this.mTargetId = targetId;
	}

	public static void getAll(int targetId) {
		eventPoster(new TaskGetList_Event(targetId));
	}

	private static void eventPoster(TaskGetList_Event event) {
		EventBus.getDefault().post(event);
	}

}
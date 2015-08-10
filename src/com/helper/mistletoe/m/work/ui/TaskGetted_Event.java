package com.helper.mistletoe.m.work.ui;

import com.helper.mistletoe.m.pojo.TaskList_Bean;
import com.helper.mistletoe.m.work.base.WorkMainThread_Event;
import com.helper.mistletoe.util.ExceptionHandle;

public class TaskGetted_Event extends WorkMainThread_Event {
	private TaskList_Bean mTaskList;

	public TaskGetted_Event(TaskList_Bean taskList) {
		try {
			setTaskList(taskList);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public TaskList_Bean getTaskList() {
		if (mTaskList == null) {
			mTaskList = new TaskList_Bean();
		}
		return mTaskList;
	}

	public void setTaskList(TaskList_Bean taskList) {
		this.mTaskList = taskList;
	}

}
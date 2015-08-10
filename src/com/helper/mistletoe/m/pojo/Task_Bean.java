package com.helper.mistletoe.m.pojo;

import com.helper.mistletoe.m.pojo.Task_Enum.TaskStatus;
import com.helper.mistletoe.m.work.be.cloud.TaskCreate_Event;
import com.helper.mistletoe.m.work.be.cloud.TaskUpdate_Event;
import com.helper.mistletoe.util.ExceptionHandle;

import de.greenrobot.event.EventBus;

public class Task_Bean extends Task_Pojo {
	public void updateWeights(int weights) {
		updateTask(getTask_id(), getLoc_TargetId(), weights, null, null,null,null);
	}

	public void updateStatus(TaskStatus status) {
		updateTask(getTask_id(), getLoc_TargetId(), null, status, null,null,null);
	}

	public void updateOwner(int owner_id) {
		updateTask(getTask_id(), getLoc_TargetId(), null, null, owner_id,null,null);
	}

	public static void createTask(int targetId, String description, Integer weights, Integer owner_id,Long begin_time,Long end_time) {
		try {
			Task_Bean tTask = new Task_Bean();
			tTask.loc_TargetId = targetId;
			tTask.description = description;
			tTask.weights = weights;
			tTask.owner_id = owner_id;
			tTask.begin_time=begin_time;
			tTask.end_time=end_time;
			EventBus.getDefault().post(new TaskCreate_Event(tTask));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static void updateTask(int task_id, int targetId, Integer weights, TaskStatus status, Integer owner_id,Long begin_time,Long end_time) {
		try {
			Task_Bean tTask = new Task_Bean();
			tTask.task_id = task_id;
			tTask.weights = weights;
			tTask.loc_TargetId = targetId;
			if (status != null) {
				tTask.setStatus(status);
			}
			tTask.owner_id = owner_id;
			tTask.begin_time=begin_time;
			tTask.end_time=end_time;
			EventBus.getDefault().post(new TaskUpdate_Event(tTask));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}
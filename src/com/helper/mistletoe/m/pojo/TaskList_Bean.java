package com.helper.mistletoe.m.pojo;

import com.helper.mistletoe.m.pojo.Task_Enum.TaskStatus;
import com.helper.mistletoe.m.work.be.TaskGetList_Event;
import com.helper.mistletoe.util.ExceptionHandle;

import de.greenrobot.event.EventBus;

public class TaskList_Bean extends TaskList_Pojo {
	/**
	 * 计算出这个Task列表中总的权重是多少
	 * 
	 * @return 总的权重
	 */
	public int getAllWeight() {
		int result = 0;
		try {
			for (Task_Bean i : getList()) {
				if (i.getStatus() != TaskStatus.Delete) {
					result += i.getWeights();
				}
			}
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 计算出这个列表中已经完成的任务权重是多少
	 * 
	 * @return 已经完成的任务权重
	 */
	public int getCompleteWeight() {
		int result = 0;
		try {
			for (Task_Bean i : getList()) {
				if (i.getStatus() == TaskStatus.Complete) {
					result += i.getWeights();
				}
			}
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static void getTaskList(int targetId) {
		EventBus.getDefault().post(new TaskGetList_Event(targetId));
	}
}
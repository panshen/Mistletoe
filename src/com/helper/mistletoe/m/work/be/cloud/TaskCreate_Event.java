package com.helper.mistletoe.m.work.be.cloud;

import android.content.Context;

import com.helper.mistletoe.m.net.request.Task_Create_NetRequest;
import com.helper.mistletoe.m.pojo.Task_Bean;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.m.work.base.WorkAsync_Event;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * 由张久瑞在2015年5月4日创建<br/>
 * 创建或者重新发送进度更新
 * 
 * @author 张久瑞
 */
public class TaskCreate_Event extends WorkAsync_Event {
	private Task_Bean mTask;// 进度更新JavaBean

	public TaskCreate_Event(Task_Bean task) {
		super();
		try {
			setTask(task);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork(Context context) {
		super.doWork(context);
		try {
			// 生成本地Id
			getTask().setClient_ref_id(java.util.UUID.randomUUID().toString());
			// 发送到服务器
			Task_Create_NetRequest tNetRequest = new Task_Create_NetRequest(getContext());
			boolean tRes = tNetRequest.doRequest(getTask());

			if (tRes) {
				AirLock_Work.syncTask(mTask.getLoc_TargetId());
				AirLock_Work.syncSchedule(getContext(), mTask.getLoc_TargetId());
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public Task_Bean getTask() {
		if (mTask == null) {
			mTask = new Task_Bean();
		}
		return mTask;
	}

	public void setTask(Task_Bean task) {
		this.mTask = task;
	}

}

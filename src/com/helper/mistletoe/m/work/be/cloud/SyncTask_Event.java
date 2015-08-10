package com.helper.mistletoe.m.work.be.cloud;

import android.content.Context;

import com.helper.mistletoe.m.db.TaskManager;
import com.helper.mistletoe.m.net.request.Task_GetList_NetRequest;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.TaskList_Bean;
import com.helper.mistletoe.m.work.base.WorkAsync_Event;
import com.helper.mistletoe.m.work.ui.TaskOnChange_Event;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * 由张久瑞在2015年5月4日创建<br/>
 * 创建或者重新发送进度更新
 * 
 * @author 张久瑞
 */
public class SyncTask_Event extends WorkAsync_Event {
	private Target_Bean mTarget;

	public SyncTask_Event(Target_Bean target) {
		super();
		try {
			setTarget(target);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public SyncTask_Event(int targetId) {
		super();
		try {
			Target_Bean tTarget = new Target_Bean();
			tTarget.setTarget_id(targetId);
			setTarget(tTarget);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork(Context context) {
		super.doWork(context);
		try {
			// 从网络获取Tag列表
			Task_GetList_NetRequest tNetWork = new Task_GetList_NetRequest(getContext());
			TaskList_Bean tTagList = tNetWork.doRequest(getTarget().getTarget_id());
			// 转入数据库中
			TaskManager.getInstance(getContext()).writeTaskList(getTarget().getTarget_id(), tTagList);
			// 发送广播提示Task有更新
			post(new TaskOnChange_Event());
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

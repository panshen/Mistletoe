package com.helper.mistletoe.m.work.be;

import android.content.Context;

import com.helper.mistletoe.m.db.TargetManager;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.work.base.WorkAsync_Event;
import com.helper.mistletoe.m.work.ui.TargetGetted_Event;
import com.helper.mistletoe.util.ExceptionHandle;

public class TargetGet_Event extends WorkAsync_Event {
	private int targetId;
	private String targetTag;

	public TargetGet_Event(int targetId, String targetTag) {
		try {
			setTargetId(targetId);
			setTargetTag(targetTag);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork(Context context) {
		super.doWork(context);
		try {
			// 查找
			Target_Bean target = new Target_Bean();
			TargetManager dbWorkOj = TargetManager.getInstance(getContext());
			target = dbWorkOj.getTarget(getTargetId(), getTargetTag());
			target.readTargetMember_Local(getApplicationContext());
			// 发布
			post(new TargetGetted_Event(target));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public String getTargetTag() {
		if (targetTag == null) {
			targetTag = "";
		}
		return targetTag;
	}

	public void setTargetTag(String targetTag) {
		this.targetTag = targetTag;
	}

}
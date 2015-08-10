package com.helper.mistletoe.m.work.be;

import com.helper.mistletoe.m.db.TargetManager;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.FactoryWork_v3;

public class GetTarget_Target_Mode extends FactoryWork_v3 {
	private int targetId;
	private String targetTag;
	private transient Target_Bean target;// 结果：Target列表

	public GetTarget_Target_Mode(int targetId, String targetTag) {
		try {
			this.targetId = targetId;
			this.targetTag = targetTag;
			this.target = new Target_Bean();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			TargetManager dbWorkOj = TargetManager.getInstance(getContext());
			target = dbWorkOj.getTarget(targetId, targetTag);
			target.readTargetMember_Local(getApplicationContext());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public Target_Bean getTarget() {
		if (target == null) {
			target = new Target_Bean();
		}
		return target;
	}

}
package com.helper.mistletoe.m.work.be.cloud;

import java.util.ArrayList;

import com.helper.mistletoe.m.net.request.Target_Update_NetRequest;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.ServiceWork_v3;

public class Target_Update_Mode extends ServiceWork_v3 {
	private Target_Bean mTarget;// 目标
	private String mTargetCover;// 目标封面
	private ArrayList<Integer> mTargetCoverId;// 目标封面

	public Target_Update_Mode(Target_Bean target, String filePath, ArrayList<Integer> fileId) {
		super();
		try {
			this.mTarget = target;
			this.mTargetCover = filePath;
			this.mTargetCoverId = fileId;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			// 发送文件
			if ((mTargetCoverId != null) && (mTargetCoverId.size() > 0)) {
				mTarget.setHead_pics(mTargetCoverId);
			} else {
				mTarget.uploadCover(getContext(), mTargetCover);
			}
			// 发送到网络
			Target_Update_NetRequest netWorkOj = new Target_Update_NetRequest(getContext());
			boolean t_res = netWorkOj.updateTarget(mTarget);

			Broadcast_Sender.scheduleChanged_Cloud(getContext(), -1);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
package com.helper.mistletoe.m.work.ui;

import java.util.ArrayList;

import android.content.Context;

import com.helper.mistletoe.m.db.TargetMemberManager;
import com.helper.mistletoe.m.net.request.GetTargetMemeber_Target_NetRequest;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.work.base.BaseWork_Mode;
import com.helper.mistletoe.m.work.base.inter.WorkCallBack_Mode;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * <p>
 * Created by 张久瑞 on 2015年3月13日.
 * </p>
 * 
 * @author 张久瑞
 * @version 1.0
 */
public class SyncTargetMember_Target_Mode extends BaseWork_Mode {
	private boolean response_isSyncOver;
	private int targetId;

	public SyncTargetMember_Target_Mode(WorkCallBack_Mode workCallBack, Context context, int targetId) {
		super(workCallBack, context);
		try {
			this.targetId = targetId;
			this.response_isSyncOver = false;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String result = "";
		try {
			GetTargetMemeber_Target_NetRequest netRequest = new GetTargetMemeber_Target_NetRequest(context);
			ArrayList<TargetMember_Bean> member = netRequest.getList(targetId);
			if (member != null) {
				// 转入数据库，就完事了。。
				TargetMemberManager.getInstance(context).writeTargetMemberList(targetId, member);
				response_isSyncOver = true;
			} else {
				response_isSyncOver = false;
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public boolean isResponse_isSyncOver() {
		return response_isSyncOver;
	}

}
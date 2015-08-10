package com.helper.mistletoe.m.work.ui;

import android.content.Context;

import com.helper.mistletoe.m.db.TargetManager;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.m.pojo.Target_Bean;
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
public class GetTargetMemberList_Target_Mode extends BaseWork_Mode {
	private Target_Bean response_target;

	public GetTargetMemberList_Target_Mode(WorkCallBack_Mode workCallBack, Context context, Target_Bean target) {
		super(workCallBack, context);
		try {
			this.response_target = target;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String result = "";
		try {
			response_target.readTargetMember_Local(context);
			// 如果自己不在成员列表中，把目标修改为市场目标
			if (response_target.getStatus_server() == MemberStatus.Watch) {
				if (response_target.getLoc_TargetMember().hasEffectiveContent()) {
					TargetMember_Bean tMember = response_target.getLoc_TargetMember().getMe(context);
					if ((tMember == null) || (tMember.getMember_id() < 1)) {
						TargetManager.getInstance(context).changeToMarketTarget(response_target);
					}
				}
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public Target_Bean getResponse_target() {
		if (response_target == null) {
			response_target = new Target_Bean();
		}
		return response_target;
	}

}
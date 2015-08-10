package com.helper.mistletoe.m.work.be.cloud;

import org.json.JSONArray;

import android.util.Log;

import com.helper.mistletoe.m.db.TargetManager;
import com.helper.mistletoe.m.net.request.Target_Create_NetRequest;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.sp.User_sp;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.ServiceWork_v3;

/**
 * 由张久瑞在2015年5月15日创建<br/>
 * 创建或者重新发送目标
 * 
 * @author 张久瑞
 */
public class Target_CreateTarget_Mode extends ServiceWork_v3 {
	private Target_Bean mTarget;// 目标
	private String mTargetCover;// 目标封面

	public Target_CreateTarget_Mode(Target_Bean target, String filePath) {
		super();
		try {
			this.mTarget = target;
			this.mTargetCover = filePath;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			// 如果是第一次发送就补充信息
			if (((int) mTarget.getTarget_id()) != -2) {
				long t_Time = TargetManager.getInstance(getContext()).getLastUpdateTime();
				// creator_id
				User_Bean user = new User_sp(getContext()).read();
				mTarget.setCreator_id(Integer.valueOf(user.getUser_id()));
				// target_stick
				mTarget.setTarget_stick(0);
				// status
				mTarget.setStatus_server(MemberStatus.Discussing);
				// create_time
				mTarget.setCreate_time(t_Time + 1);
				// last_updated_time
				mTarget.setLast_updated_time(t_Time);
				// loc_TargetTag
				String tag = java.util.UUID.randomUUID().toString();
				mTarget.setLoc_TargetTag(tag);
			}
			// target_id
			mTarget.setTarget_id(-1);
			saveToDBAndSendBroadcast();
			// 发送文件
			mTarget.uploadCover(getContext(), mTargetCover);
			// 发送到网络
			Target_Create_NetRequest fewfwef = new Target_Create_NetRequest(getContext());
			boolean tIfCreateOk = ((int) fewfwef.createTarget(mTarget, new JSONArray(mTarget.getLoc_MembersString())).getTarget_id()) != -2;
			if (tIfCreateOk) {
				Broadcast_Sender.scheduleChanged_Cloud(getApplicationContext(), -1);
			} else {
				mTarget.setTarget_id(-2);
				saveToDBAndSendBroadcast();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void saveToDBAndSendBroadcast() {
		try {
			TargetManager.getInstance(getApplicationContext()).writeTarget(mTarget);
			Broadcast_Sender.scheduleChanged_Cloud(getContext(), -1);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}

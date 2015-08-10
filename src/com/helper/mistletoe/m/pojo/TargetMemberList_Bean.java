package com.helper.mistletoe.m.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.content.Context;

import com.helper.mistletoe.m.db.TargetMemberManager;
import com.helper.mistletoe.m.db.impl.AirLock_DB;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.util.ExceptionHandle;

public class TargetMemberList_Bean extends TargetMemberList_Pojo {
	@SuppressLint("UseSparseArrays")
	public void readTargetMember_Local(Context context, int targetId) {
		try {
			// 从TargetMember表中查出数据
			// 因为TargetMember_Bean没有设定Hash，所以用HashMap去一下重复。
			ArrayList<TargetMember_Bean> members = TargetMemberManager.getInstance(context).readTargetMemberList(targetId);
			HashMap<Integer, TargetMember_Bean> tHashMap = new HashMap<Integer, TargetMember_Bean>();
			for (Iterator<TargetMember_Bean> iterator = members.iterator(); iterator.hasNext();) {
				TargetMember_Bean temp = iterator.next();
				if (temp.getMember_id() > 0) {
					tHashMap.put(temp.getMember_id(), temp);
				}
			}
			members.clear();
			members.addAll(tHashMap.values());
			init(members);
			// 从Helper表中查出数据
			// 从Member列表中查出Id列表
			ArrayList<Integer> memberIds = new ArrayList<Integer>();
			for (Iterator<TargetMember_Bean> iterator = getTargetMemberList().iterator(); iterator.hasNext();) {
				TargetMember_Bean tTemp = iterator.next();
				memberIds.add(tTemp.getMember_id());
			}
			// 使用已经查出的Id列表查询帮手
			HashMap<Integer, Helpers_Sub_Bean> helpers = AirLock_DB.select_helper(context, memberIds);
			for (Iterator<TargetMember_Bean> iterator = getTargetMemberList().iterator(); iterator.hasNext();) {
				TargetMember_Bean tTemp = iterator.next();
				long lastUpdateTime = tTemp.getHelper_update_time();
				tTemp.copyData((Helpers_Sub_Bean) helpers.get((int) tTemp.getMember_id()));
				// 在copyData时，会把帮手的更新时间覆盖了Member的更新时间，所以把之前保存的值还原
				tTemp.setHelper_update_time(lastUpdateTime);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public int getCanNumberOfCommunication() {
		int result = 0;
		try {
			// 这个数组中的状态可以沟通
			ArrayList<MemberStatus> tCanCommunication = new ArrayList<MemberStatus>();
			tCanCommunication.addAll(Arrays.asList(new MemberStatus[] { MemberStatus.Discussing, MemberStatus.Signed,
					MemberStatus.HelperApplyClose, MemberStatus.OwnerApplyClose, MemberStatus.Watch }));
			// 查找可沟通的人数
			for (Iterator<TargetMember_Bean> iterator = getTargetMemberList().iterator(); iterator.hasNext();) {
				TargetMember_Bean targetMember_Bean = iterator.next();
				if (tCanCommunication.contains(targetMember_Bean.getMember_status())) {
					result++;
				}
			}
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public TargetMember_Bean getMe(Context context) {
		TargetMember_Bean result = new TargetMember_Bean();
		try {
			User_Bean tUser = new User_Bean();
			tUser.readData(context);
			for (TargetMember_Bean i : getTargetMemberList()) {
				if (i.getMember_id() == tUser.getUser_id()) {
					result = i;
					break;
				}
			}
		} catch (Exception e) {
			result = new TargetMember_Bean();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public boolean whetherICare(Context context) {
		boolean result = false;
		try {
			TargetMember_Bean me = getMe(context);
			if (me.getMember_status() == MemberStatus.Watch) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			result = false;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public boolean hasEffectiveContent() {
		boolean result = false;
		try {
			for (TargetMember_Bean i : getTargetMemberList()) {
				if (i.getMember_id() > 0) {
					result = true;
					break;
				}
			}
		} catch (Exception e) {
			result = false;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
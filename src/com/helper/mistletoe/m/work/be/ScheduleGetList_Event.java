package com.helper.mistletoe.m.work.be;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;

import com.helper.mistletoe.m.db.SchdeuleManager;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleHighlightFlag;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleRevokeStatus;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleType;
import com.helper.mistletoe.m.work.base.WorkAsync_Event;
import com.helper.mistletoe.m.work.ui.ScheduleListGetted_Event;
import com.helper.mistletoe.util.ExceptionHandle;

import de.greenrobot.event.EventBus;

public class ScheduleGetList_Event extends WorkAsync_Event {
	private Integer mTargetId;
	private ScheduleHighlightFlag mHighlightFlag;
	private ScheduleType mScheduleType;
	private String mSearchKey;
	boolean mOnlySynced;
	private Integer mMemberId;
	private ScheduleRevokeStatus mStatus;
	private Integer mTagId;
	private Integer mLimit;
	private Integer mOffset;

	public ScheduleGetList_Event(Integer targetId, ScheduleHighlightFlag highlightFlag, ScheduleType scheduleType, String searchKey,
			boolean onlySynced, Integer memberId, ScheduleRevokeStatus status, Integer tagId, Integer limit, Integer offset) {
		try {
			this.mTargetId = targetId;
			this.mHighlightFlag = highlightFlag;
			this.mScheduleType = scheduleType;
			this.mSearchKey = searchKey;
			this.mOnlySynced = onlySynced;
			this.mMemberId = memberId;
			this.mStatus = status;
			this.mTagId = tagId;
			this.mLimit = limit;
			this.mOffset = offset;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork(Context context) {
		super.doWork(context);
		try {
			int tSchdeuleCount = 0;
			ArrayList<Schedule_Bean> tSchdeuleList = new ArrayList<Schedule_Bean>();
			// 从数据库中查询
			SchdeuleManager dbWorkOj = SchdeuleManager.getInstance(getContext());
			if ((mTagId != null) && (mTagId < 1)) {
				mTagId = null;
			}
			tSchdeuleCount = dbWorkOj.getSchdeuleCount(mTargetId, mHighlightFlag, mScheduleType, mSearchKey, mOnlySynced, mMemberId,
					mStatus, mTagId);
			tSchdeuleList = dbWorkOj.readSchdeuleList(mTargetId, mHighlightFlag, mScheduleType, mSearchKey, mOnlySynced, mMemberId,
					mStatus, mTagId, mLimit, mOffset);
			// 发布出去
			if ((mScheduleType == ScheduleType.CostApply) && (mMemberId != null) && (mMemberId > 0)) {
				for (Iterator<Schedule_Bean> iterator = tSchdeuleList.iterator(); iterator.hasNext();) {
					Schedule_Bean temp = iterator.next();
					if (temp.getOwner_id_int() != mMemberId) {
						iterator.remove();
					}
				}
			}
			post(new ScheduleListGetted_Event(tSchdeuleCount, tSchdeuleList));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static void getAll(int targetId) {
		eventPoster(new ScheduleGetList_Event(targetId, null, null, null, false, null, null, null, null, null));
	}

	public static void getHighLight(int targetId) {
		eventPoster(new ScheduleGetList_Event(targetId, ScheduleHighlightFlag.Yes, null, null, true, null, ScheduleRevokeStatus.Normal,
				null, null, null));
	}

	public static void getSearch(int targetId, String searchKey) {
		eventPoster(new ScheduleGetList_Event(targetId, null, null, searchKey, false, null, ScheduleRevokeStatus.Normal, null, null, null));
	}

	public static void getCostApply(int targetId, Integer memberId, Integer tagId) {
		eventPoster(new ScheduleGetList_Event(targetId, null, ScheduleType.CostApply, null, true, memberId, ScheduleRevokeStatus.Normal,
				tagId, null, null));
	}

	private static void eventPoster(ScheduleGetList_Event event) {
		EventBus.getDefault().post(event);
	}

}
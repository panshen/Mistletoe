package com.helper.mistletoe.m.work.ui;

import java.util.ArrayList;

import android.content.Context;

import com.helper.mistletoe.m.db.SchdeuleManager;
import com.helper.mistletoe.m.net.request.GetSchdeuleList_Target_NetRequest;
import com.helper.mistletoe.m.net.request.Target_GetList_NetRequest;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
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
public class SyncSchdeule_Target_Mode extends BaseWork_Mode {
	private int targetId;

	public SyncSchdeule_Target_Mode(WorkCallBack_Mode workCallBack, Context context, int targetId) {
		super(workCallBack, context);
		try {
			this.targetId = targetId;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String result = "";
		try {
			int listLength = 0;
			boolean haveMore = true;
			ArrayList<Schedule_Bean> targetList = new ArrayList<Schedule_Bean>();
			GetSchdeuleList_Target_NetRequest netRequest = new GetSchdeuleList_Target_NetRequest(context);
			while (haveMore) {
				targetList = netRequest.getList(targetId);
				// 保存Target
				SchdeuleManager.getInstance(context).writeSchdeuleList(targetList, targetId);
				listLength = targetList.size();
				haveMore = listLength >= Target_GetList_NetRequest.TARGET_GET_COUNT;
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

}
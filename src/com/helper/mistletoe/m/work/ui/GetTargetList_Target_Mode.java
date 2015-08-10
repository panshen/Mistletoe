package com.helper.mistletoe.m.work.ui;

import java.util.ArrayList;

import android.content.Context;

import com.helper.mistletoe.m.db.TargetManager;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRunningState;
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
public class GetTargetList_Target_Mode extends BaseWork_Mode {
	private TargetRunningState status;
	private ArrayList<Target_Bean> response_data;// 结果：Target列表
	private long response_TargetCount;// 结果：Target列表

	public GetTargetList_Target_Mode(WorkCallBack_Mode workCallBack, Context context, TargetRunningState status) {
		super(workCallBack, context);
		try {
			this.status = status;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String result = "";
		try {
			TargetManager dbWorkOj = TargetManager.getInstance(context);
			response_data = dbWorkOj.readTargetList(status);
			response_TargetCount = dbWorkOj.getTargetCount(status);
			if (response_data != null) {
				for (Target_Bean a : response_data) {
					a.readTargetMember_Local(context);
				}
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public ArrayList<Target_Bean> getResponse_data() {
		if (response_data == null) {
			response_data = new ArrayList<Target_Bean>();
		}
		return response_data;
	}

	public long getResponse_TargetCount() {
		return response_TargetCount;
	}

}
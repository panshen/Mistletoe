package com.helper.mistletoe.m.work.be;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.helper.mistletoe.m.db.TargetManager;
import com.helper.mistletoe.m.db.impl.AirLock_DB;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRunningState;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.FactoryWork_v3;

public class GetTargetList_Target_Mode extends FactoryWork_v3 {
	private TargetRunningState status;
	private int color;
	private String searchKey;
	private ArrayList<Target_Bean> targetList;
	private long targetCount;

	public GetTargetList_Target_Mode(TargetRunningState status, int color, String searchKey) {
		try {
			this.status = status;
			this.color = color;
			this.searchKey = searchKey;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			TargetManager dbWorkOj = TargetManager.getInstance(getContext());
			targetList = dbWorkOj.readTargetList(status, color, searchKey);
			if (targetList != null) {
				Gson tGson = new Gson();
				for (Target_Bean a : targetList) {
					String tHelperData = tGson.toJson(AirLock_DB.select_helper(
							getContext(), a.getCreator_id()));
					a.getLoc_TargetMember()
							.setOwner(
									tGson.fromJson(tHelperData,
											TargetMember_Bean.class));
				}
			}
			targetCount = targetList.size();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public ArrayList<Target_Bean> getTargetList() {
		if (targetList == null) {
			targetList = new ArrayList<Target_Bean>();
		}
		return targetList;
	}

	public long getTargetCount() {
		return targetCount;
	}

	public int getColor() {
		return color;
	}

}

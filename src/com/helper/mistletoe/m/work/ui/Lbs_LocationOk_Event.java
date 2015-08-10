package com.helper.mistletoe.m.work.ui;

import com.baidu.location.BDLocation;
import com.helper.mistletoe.m.work.base.WorkMainThread_Event;
import com.helper.mistletoe.util.ExceptionHandle;

public class Lbs_LocationOk_Event extends WorkMainThread_Event {
	private BDLocation mLocation;// 定位结果

	public Lbs_LocationOk_Event(BDLocation location) {
		try {
			setLocation(location);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public BDLocation getLocation() {
		if (mLocation == null) {
			mLocation = new BDLocation();
		}
		return mLocation;
	}

	public void setLocation(BDLocation location) {
		this.mLocation = location;
	}

}
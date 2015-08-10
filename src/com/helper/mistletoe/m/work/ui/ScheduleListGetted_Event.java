package com.helper.mistletoe.m.work.ui;

import java.util.ArrayList;

import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.work.base.WorkMainThread_Event;
import com.helper.mistletoe.util.ExceptionHandle;

public class ScheduleListGetted_Event extends WorkMainThread_Event {
	private Integer mSchdeuleCount;
	private ArrayList<Schedule_Bean> mSchdeuleList;

	public ScheduleListGetted_Event(int schdeuleCount, ArrayList<Schedule_Bean> schdeuleList) {
		try {
			setSchdeuleCount(schdeuleCount);
			setSchdeuleList(schdeuleList);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public int getSchdeuleCount() {
		if (mSchdeuleCount == null) {
			mSchdeuleCount = 0;
		}
		return mSchdeuleCount;
	}

	public void setSchdeuleCount(int schdeuleCount) {
		this.mSchdeuleCount = schdeuleCount;
	}

	public ArrayList<Schedule_Bean> getSchdeuleList() {
		if (mSchdeuleList == null) {
			mSchdeuleList = new ArrayList<Schedule_Bean>();
		}
		return mSchdeuleList;
	}

	public void setSchdeuleList(ArrayList<Schedule_Bean> schdeuleList) {
		this.mSchdeuleList = schdeuleList;
	}

}
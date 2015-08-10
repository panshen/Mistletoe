package com.helper.mistletoe.m.pojo;

import java.util.ArrayList;

import com.helper.mistletoe.m.sjb.core.SimpleBabelJavaBean;

public class TaskList_Pojo extends SimpleBabelJavaBean {
	public ArrayList<Task_Bean> mList;

	// TODO 以下为Get Set函数

	public ArrayList<Task_Bean> getList() {
		if (mList == null) {
			mList = new ArrayList<Task_Bean>();
		}
		return mList;
	}

	public void setList(ArrayList<Task_Bean> list) {
		this.mList = list;
	}

}
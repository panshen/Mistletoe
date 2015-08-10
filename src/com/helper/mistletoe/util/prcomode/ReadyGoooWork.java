package com.helper.mistletoe.util.prcomode;

import android.content.Context;

public interface ReadyGoooWork {
	// 执行
	public void doWork(Context context);

	// 这个工作需不需要通知领导
	public boolean hasResponse();

	public String packToJson();
}
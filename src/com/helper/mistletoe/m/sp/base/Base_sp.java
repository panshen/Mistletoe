package com.helper.mistletoe.m.sp.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.helper.mistletoe.util.ExceptionHandle;

/**
 * Created by stemp1234 on 15/3/11.
 */
public abstract class Base_sp<T> {
	private Context context;
	private SharedPreferences sharedPreferences;
	private String spName;

	public Base_sp(Context context, String spName) {
		try {
			this.context = context;
			this.spName = spName;
			this.sharedPreferences = null;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public SharedPreferences getSP() {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(spName, Activity.MODE_PRIVATE);
		}
		return sharedPreferences;
	}

	public abstract T read() throws Exception;

	public abstract void read(T bean) throws Exception;

	public abstract void write(T bean) throws Exception;
}
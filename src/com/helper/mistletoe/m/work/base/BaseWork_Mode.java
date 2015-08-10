package com.helper.mistletoe.m.work.base;

import android.content.Context;
import android.os.AsyncTask;

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
public abstract class BaseWork_Mode extends AsyncTask<String, Integer, String> {
	protected WorkCallBack_Mode workCallBack;// 回调函数
	protected Context context;// 设备上下文

	public BaseWork_Mode(WorkCallBack_Mode workCallBack, Context context) {
		super();
		try {
			this.context = context;
			this.workCallBack = workCallBack;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String result = "";
		try {
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		try {
			workCallBack.WorkCallBack();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}
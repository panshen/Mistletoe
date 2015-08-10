package com.helper.mistletoe.m.work.base;

import java.lang.ref.SoftReference;

import android.content.Context;

import com.helper.mistletoe.MistletoeApplication;
import com.helper.mistletoe.m.work.base.inter.WorkAsync;
import com.helper.mistletoe.util.ExceptionHandle;

public abstract class WorkAsync_Event extends WorkEvent_Base implements WorkAsync {
	private SoftReference<Context> mContext;

	@Override
	public void doWork(Context context) {
		try {
			setContext(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public Context getContext() {
		Context result = null;
		try {
			result = mContext.get();
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	protected void setContext(Context context) {
		this.mContext = new SoftReference<Context>(context);
	}

	protected Context getApplicationContext() {
		return getContext().getApplicationContext();
	}

	protected MistletoeApplication getApplication() {
		return MistletoeApplication.getInstance(getContext());
	}
}

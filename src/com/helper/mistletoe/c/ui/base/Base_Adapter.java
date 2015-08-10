package com.helper.mistletoe.c.ui.base;

import java.lang.ref.SoftReference;

import org.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.BaseAdapter;

import com.helper.mistletoe.m.pojo.AppNote_Bean;
import com.helper.mistletoe.m.sp.AppNote_sp;
import com.helper.mistletoe.util.Dialog_Util;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.ReadyGoooFactory;
import com.helper.mistletoe.v.ViewHolder;

import de.greenrobot.event.EventBus;

public abstract class Base_Adapter extends BaseAdapter implements UIInterface, PageCache {
	protected SoftReference<Context> mContext;
	protected ViewHolder mHolder;
	private SoftReference<UIToast> mToast;
	protected ReadyGoooFactory workFactory;

	public Base_Adapter(Context context, ReadyGoooFactory woFactory) {
		super();
		try {
			this.mContext = new SoftReference<Context>(context);
			this.mHolder = new ViewHolder();
			this.workFactory = woFactory;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public ReadyGoooFactory getWorkFactory() {
		return workFactory;
	}

	// TODO Toast

	public void showToast(CharSequence text, int duration) {
		try {
			getToastComponent().showToast(text, duration);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void showToast(CharSequence text) {
		try {
			getToastComponent().showToast(text);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public UIToast getToastComponent() {
		if ((mToast == null) || (mToast.get() == null)) {
			mToast = new SoftReference<UIToast>(new UIToast(getContext()));
		}
		return mToast.get();
	}

	// TODO 页面缓存

	@Override
	public JSONObject readData() {
		JSONObject result = new JSONObject();
		try {
			AppNote_sp tAsp = new AppNote_sp(getApplicationContext());
			AppNote_Bean tAppNote = tAsp.read();
			result = tAppNote.readPageData(getClass().getName());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public void saveData(JSONObject data) {
		try {
			AppNote_sp tAsp = new AppNote_sp(getApplicationContext());
			AppNote_Bean tAppNote = tAsp.read();
			tAppNote.savePageData(getClass().getName(), data);
			tAsp.write(tAppNote);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	// TODO Dialog

	protected void showDialog(String message, DialogInterface.OnClickListener positiveListener) {
		Dialog_Util.showDialog(getContext(), message, positiveListener);
	}

	// TODO EventBus
	protected EventBus getEventBus() {
		return EventBus.getDefault();
	}

	// TODO 通用

	protected Context getContext() {
		Context result = null;
		try {
			result = mContext.get();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	protected Context getApplicationContext() {
		return getContext().getApplicationContext();
	}

	public ViewHolder getHolder() {
		if (mHolder == null) {
			mHolder = new ViewHolder();
		}
		return mHolder;
	}

}
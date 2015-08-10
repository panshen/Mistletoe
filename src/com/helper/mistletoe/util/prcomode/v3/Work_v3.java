package com.helper.mistletoe.util.prcomode.v3;

import java.lang.ref.SoftReference;

import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.ReadyGoooWork;
import com.helper.mistletoe.util.sysconst.Broadcast_Const;

public abstract class Work_v3 implements ReadyGoooWork {
	protected transient SoftReference<Context> context;

	@Override
	public void doWork(Context context) {
		try {
			this.context = new SoftReference<Context>(context);
			doWork();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public abstract void doWork();

	@Override
	public boolean hasResponse() {
		return false;
	}

	protected Context getContext() {
		Context result = null;
		try {
			if ((context != null) && (context.get() != null)) {
				result = context.get();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	protected Context getApplicationContext() {
		Context result = null;
		try {
			if (getContext() != null) {
				result = getContext().getApplicationContext();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public String packToJson() {
		String result = "";
		try {
			JSONObject tStringWork = new JSONObject();
			tStringWork.put(Broadcast_Const.BC_MODE_CLASS, this.getClass()
					.getName());
			tStringWork.put(Broadcast_Const.BC_MODE_DATA, new JSONObject(
					new Gson().toJson(this)));
			tStringWork.put(Broadcast_Const.BC_MODE_STATUS,
					Broadcast_Const.BC_TAG_WORK);
			result = tStringWork.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

}
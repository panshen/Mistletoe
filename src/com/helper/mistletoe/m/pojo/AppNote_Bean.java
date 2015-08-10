package com.helper.mistletoe.m.pojo;

import java.util.Iterator;

import org.json.JSONObject;

import android.content.Context;

import com.helper.mistletoe.m.sp.AppNote_sp;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * Created by 张久瑞 on 15/3/4.
 */
public class AppNote_Bean extends AppNote_Pojo {
	public void readData(AppNote_sp usp) throws Exception {
		try {
			usp.read(this);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void readData(Context context) throws Exception {
		try {
			AppNote_sp usp = new AppNote_sp(context);
			readData(usp);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void writeData(AppNote_sp usp) throws Exception {
		try {
			usp.write(this);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void writeData(Context context) throws Exception {
		try {
			AppNote_sp usp = new AppNote_sp(context);
			writeData(usp);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public JSONObject readPageData(String className) {
		JSONObject result = new JSONObject();
		try {
			result = getPageCache().getJSONObject(className);
		} catch (Exception e) {
			result = new JSONObject();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public void savePageData(String className, JSONObject data) {
		try {
			JSONObject tThisPageNote = readPageData(className);
			for (@SuppressWarnings("unchecked")
			Iterator<String> iterator = data.keys(); iterator.hasNext();) {
				String tTempKey = iterator.next();
				tThisPageNote.put(tTempKey, data.get(tTempKey));
			}
			JSONObject tApplicationNote = getPageCache();
			tApplicationNote.put(className, tThisPageNote);
			setPageCache(tApplicationNote);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static int readNewHelperNumber(Context context) {
		return readNewHelperNumber(context, 0);
	}

	public static int readNewHelperNumber(Context context, int defaultValue) {
		int result = defaultValue;
		try {
			AppNote_Bean tNote = new AppNote_Bean();
			tNote.readData(context);
			if (tNote.newHelperNumber != null) {
				result = tNote.getNewHelperNumber();
			}
		} catch (Exception e) {
			result = defaultValue;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static void writeNewHelperNumber(Context context, int newHelperNumber) {
		try {
			AppNote_Bean tNote = new AppNote_Bean();
			tNote.setNewHelperNumber(newHelperNumber);
			tNote.writeData(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}
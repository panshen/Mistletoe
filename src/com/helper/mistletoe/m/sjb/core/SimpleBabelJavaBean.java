package com.helper.mistletoe.m.sjb.core;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.helper.mistletoe.m.sjb.abst.BabelJavaBean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Prism_Util;

public abstract class SimpleBabelJavaBean implements BabelJavaBean {
	@Override
	public String getFingerprint() {
		return "";
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	@Override
	public void copyData(Object o) {
		try {
			Gson tGson = new Gson();
			String tDataString = tGson.toJson(o);
			JSONObject tData = new JSONObject(tDataString);
			Prism_Util.depthCopyData(tData, this);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}
package com.helper.mistletoe.m.pojo;

import org.json.JSONObject;

import com.helper.mistletoe.util.ExceptionHandle;

/**
 * Created by 张久瑞 on 15/3/4.
 */
public class AppNote_Pojo {
	public String lastAppVersion;// 程序上次使用时的版本号
	public String pageCache;// 页面缓存
	public Integer newHelperNumber;// 新帮手数量

	// TODO 以下为Get Set函数
	public String getLastAppVersion() {
		if (lastAppVersion == null) {
			lastAppVersion = "0";
		}
		return lastAppVersion;
	}

	public void setLastAppVersion(String lastAppVersion) {
		this.lastAppVersion = lastAppVersion;
	}

	public JSONObject getPageCache() {
		JSONObject result = new JSONObject();
		try {
			if (pageCache == null) {
				result = new JSONObject();
				pageCache = result.toString();
			}
			result = new JSONObject(pageCache);
		} catch (Exception e) {
			result = new JSONObject();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public void setPageCache(JSONObject pageCache) {
		if (pageCache != null) {
			this.pageCache = pageCache.toString();
		}
	}

	public int getNewHelperNumber() {
		if (newHelperNumber == null) {
			newHelperNumber = 0;
		}
		return newHelperNumber;
	}

	public void setNewHelperNumber(int newHelperNumber) {
		this.newHelperNumber = newHelperNumber;
	}

}
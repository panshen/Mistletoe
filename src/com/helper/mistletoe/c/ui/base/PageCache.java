package com.helper.mistletoe.c.ui.base;

import org.json.JSONObject;

public interface PageCache {
	public void saveData(JSONObject data);

	public JSONObject readData();
}
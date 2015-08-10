package com.helper.mistletoe.m.net.request.base;

import android.content.Context;

import com.helper.mistletoe.util.CustomInfo;
import com.helper.mistletoe.util.ExceptionHandle;

public abstract class Template_includeInfo_NetRequest extends Template_NetRequest {
	protected String platform;
	protected String os;
	protected String hardware;
	protected String app_version;
	protected String device_token;

	protected Template_includeInfo_NetRequest(Context context, String requestType, String url) {
		super(context, requestType, url);
	}

	protected Template_includeInfo_NetRequest(Context context, String requestType, String url, String file_type) {
		super(context, requestType, url, file_type);
	}

	@Override
	protected void init(Context context, String requestType, String url, String file_type) {
		super.init(context, requestType, url, file_type);
		try {
			platform = CustomInfo.getPlatform();
			os = CustomInfo.getOs();
			hardware = CustomInfo.getHardware();
			app_version = CustomInfo.getApp_version(context);
			device_token = CustomInfo.getDevice_token(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
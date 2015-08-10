package com.helper.mistletoe.util;

public class MessageConstants {
	public static final String[] REFRESHS_PROJECTION = new String[] { MessageConstants.REFRESH, MessageConstants.REFRESH_USER,
			MessageConstants.REFRESH_HELPER, MessageConstants.REFRESH_GROUP, MessageConstants.REFRESH_DEVICE,MessageConstants.UPGRADED_VERSION,MessageConstants.REFRESH_COSTTAG};
	// 刷新
	public static final String REFRESH = "com.helper.mistletoe.refresh.interface";
	// 刷新user
	public static final String REFRESH_USER = "com.helper.mistletoe.refresh.interface.user";
	// 刷新helper
	public static final String REFRESH_HELPER = "com.helper.mistletoe.refresh.interface.helper";
	// 刷新group
	public static final String REFRESH_GROUP = "com.helper.mistletoe.refresh.interface.group";
	// 刷新device
	public static final String REFRESH_DEVICE = "com.helper.mistletoe.refresh.interface.device";
	// 刷新cost
	public static final String REFRESH_COST = "com.helper.mistletoe.refresh.interface.cost";
	//更新版本
	public static final String UPGRADED_VERSION = "com.helper.mistletoe.upgraded.version";
	// 刷新costTag
	public static final String REFRESH_COSTTAG = "com.helper.mistletoe.refresh.interface.costTag";
}

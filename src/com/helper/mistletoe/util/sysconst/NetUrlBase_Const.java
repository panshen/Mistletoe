package com.helper.mistletoe.util.sysconst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public interface NetUrlBase_Const {
	/**
	 * 网址
	 */
//	 public final static String NET_BASE_HOSTFILE = "file.readygooo.com:8080";
//	 public final static String NET_BASE_HOST = "api.readygooo.com:8080";

	public final static String NET_BASE_HOSTFILE = "182.92.189.166:8080";
	public final static String NET_BASE_HOST = "182.92.189.166:8080";

	/**
	 * 协议
	 */
	public final static String NET_BASE_PROTOCOL = "http";
	// public final static String NET_BASE_PROTOCOL = "https";

	/**
	 * URL
	 */
	public final static String NET_BASE_URL = NET_BASE_PROTOCOL + "://"
			+ NET_BASE_HOST + "/index.php?r=";
	public final static String NET_BASE_URLFILE = NET_BASE_PROTOCOL + "://"
			+ NET_BASE_HOSTFILE + "/index.php?r=";
}

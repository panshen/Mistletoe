package com.helper.mistletoe.util.sysconst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public interface NetUrl_Target_Const extends NetUrlBase_Const {
	/**
	 * 获取Target
	 */
	public final static String NET_TARGET_GETBYSTATUS = NET_BASE_URL + "target/get-by-status";
	public final static String NET_TARGET_CREATE = NET_BASE_URL + "target/create";
	public final static String NET_TARGET_UPDATE = NET_BASE_URL + "target/update";
	public final static String NET_TARGET_GETBYID = NET_BASE_URL + "target/get-by-id";
	/**
	 * 获取公共目标 http://localhost:8080/index.php?r=target/get-market-target
	 */
	public final static String NET_TARGET_GETMARKETTARGET = NET_BASE_URL + "target/get-market-target";
	/**
	 * 目标点击记录 http://localhost:8080/index.php?r=target/view
	 */
	public final static String NET_TARGET_VIEW = NET_BASE_URL + "target/view";
	/**
	 * 目标点赞 http://localhost:8080/index.php?r=target/attitude
	 */
	public final static String NET_TARGET_ATTITUDE = NET_BASE_URL + "target/attitude";
}
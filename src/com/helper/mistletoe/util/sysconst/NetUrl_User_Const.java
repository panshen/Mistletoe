package com.helper.mistletoe.util.sysconst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public interface NetUrl_User_Const extends NetUrlBase_Const {
	/**
	 * 获取验证码
	 */
	public final static String NET_USER_REGISTER = NET_BASE_URL + "user/register";
	/**
	 * 用户登录
	 */
	public final static String NET_USER_LOGIN = NET_BASE_URL + "user/login";
	/**
	 * 修改用户信息
	 */
	public final static String NET_USER_UPDATE = NET_BASE_URL + "user/update";
	/**
	 * 获得帮手详情根据ID(包括自己): http://localhost:8080/index.php?r=user/get
	 */
	public final static String NET_USER_GET = NET_BASE_URL + "user/get";
	/**
	 * 获得帮手详情根据account(包括自己):
	 * http://api.readygooo.com:8080/index.php?r=user/find-by-account
	 */
	public final static String NET_USER_FINDBYACCOUNT = NET_BASE_URL + "user/find-by-account";
}
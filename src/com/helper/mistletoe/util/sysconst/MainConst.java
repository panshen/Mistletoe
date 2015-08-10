package com.helper.mistletoe.util.sysconst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public interface MainConst extends NetUrl_Const {
	/**
	 * 当为true时为商业模式，此时异常不抛出，日志不打印。关闭所有开发痕迹。
	 */
	public final static boolean DEVS_BUSINESS_MODEL = false;

	/**
	 * true时打印日志。<br/>
	 * false不打印日志。
	 */
	public final static boolean DEVS_PRINT_LOG = true;
	/**
	 * true时抛出异常
	 */
	public final static boolean DEVS_THROW_ALL_EXCEPTION = true;

	/**
	 * app_token
	 */
	public final static String APP_APP_TOKEN = "readyGo1408.app_token@bj-china";
	/**
	 * MD5加密盐
	 */
	public final static String APP_MD5_TOKEN = "readyGo1408.md5@bj-china";
	/**
	 * 硬件平台，安卓平台
	 */
	public final static String APP_PLATFORM = "2";
}
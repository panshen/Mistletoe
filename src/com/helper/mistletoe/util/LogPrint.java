package com.helper.mistletoe.util;

import android.util.Log;

import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class LogPrint {
	/**
	 * 日志的前缀，程序名
	 */
	public static String LOG_TAG_APP = "Misletoe";
	/**
	 * 普通日志
	 */
	public static String LOG_TAG_SIMPLE = "Simple";
	/**
	 * 单元测试日志
	 */
	public static String LOG_TAG_JUNIT = "jUnit4";

	/**
	 * 在日志里打印异常，日志级别为Error
	 *
	 * @param exc
	 *            要打印的异常
	 */
	public static void printException_E(Exception exc) {
		try {
			if (ifPrintLog()) {
				String tag = getLogTag(LOG_TAG_SIMPLE);
				String msg = " " + exc.getMessage();
				Log.e(tag, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印日志，日志级别为Verbose
	 *
	 * @param tag
	 *            标签
	 * @param msg
	 *            消息
	 */
	public static void printString_V(String tag, String msg) {
		try {
			if (ifPrintLog()) {
				Log.v(getLogTag(LOG_TAG_SIMPLE) + tag, " " + msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印测试日志，日志级别为Verbose
	 *
	 * @param tag
	 *            标签
	 * @param msg
	 *            消息
	 */
	public static void unit_PrintString_V(boolean tag, String msg) {
		try {
			if (tag) {
				Log.v(getLogTag(LOG_TAG_JUNIT) + "Pass", " " + msg);
			} else {
				Log.v(getLogTag(LOG_TAG_JUNIT) + "Failed", " " + msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得日志的标签前缀
	 *
	 * @param type
	 *            日志的类型，测试日志还是普通日志
	 * @return 日志前缀
	 */
	private static String getLogTag(String type) {
		String result = "";
		try {
			result = LOG_TAG_APP + "_" + type + "：";
		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}
		return result;
	}

	/**
	 * 判断是否打印日志<br/>
	 * 如果商业模式（DEVS_BUSINESS_MODEL = true），则不打印。<br/>
	 * 如果不打印日志（DEVS_BUSINESS_MODEL = false），则不打印。
	 *
	 * @return true表示日志可以打印，false日志不打印。
	 */
	private static boolean ifPrintLog() {
		boolean result = false;
		try {
			result = true;
			if (MainConst.DEVS_BUSINESS_MODEL) {
				result = false;
			}
			if (!MainConst.DEVS_PRINT_LOG) {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
}

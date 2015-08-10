package com.helper.mistletoe.util;

import java.util.Calendar;
import java.util.Locale;

public class TimeTool_Utils {
	/**
	 * 获得当前时间，UNIX时间戳<br/>
	 * 如果异常则返回0L
	 * 
	 * @return 当前时间的UNIX时间戳 @ 未知异常
	 */
	public static long getNowTime() {
		long result = 0L;
		try {
			Calendar calendar = Calendar.getInstance();
			result = calendar.getTime().getTime();
		} catch (Exception e) {
			result = 0L;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 获得当前时间，按照给定的格式格式化<br/>
	 * 如果异常则返回""
	 * 
	 * @param fromateType
	 *            格式化格式
	 * @return 当前时间 @ 未知异常
	 */
	public static String getNowTime(String fromateType) {
		String result = "";
		try {
			long time = TimeTool_Utils.getNowTime();
			if (time != 0L) {
				result = fromateTimeShow(time, fromateType);
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 格式化时间<br/>
	 * 如果异常则返回""
	 * 
	 * @param time
	 *            时间，UNIX时间戳格式
	 * @param fType
	 *            格式
	 * @return 格式化后的时间 @ 未知异常
	 */
	public static String fromateTimeShow(long time, String fType) {
		String result = "";
		try {
			java.util.Date nowDate = new java.util.Date(time);
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(fType, Locale.getDefault());
			result = format.format(nowDate);
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 判断一个UNIX时间戳是否是今天以前的时间<br/>
	 * 如果异常则返回true
	 * 
	 * @param time
	 *            要判断的时间
	 * @return true表示是今天以前的时间，false表示不是 @ 未知异常
	 */
	public static boolean ifBeforeToday(long time) {
		boolean result = true;
		try {
			long now = getTodayTime(0, 0, 0);
			if ((now - time) > 0) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			result = true;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 根据今天的时间，时、分、秒来获得一个UNIX时间戳<br/>
	 * 如果异常则返回0L
	 * 
	 * @param hours
	 *            时
	 * @param minute
	 *            分
	 * @param seconds
	 *            秒
	 * @return UNIX时间戳 @ 未知异常
	 */
	public static long getTodayTime(int hours, int minute, int seconds) {
		long result = 0L;
		try {
			Calendar nowC = Calendar.getInstance();
			nowC.set(nowC.get(Calendar.YEAR), nowC.get(Calendar.MONTH), nowC.get(Calendar.DAY_OF_MONTH), hours, minute, seconds);
			result = nowC.getTime().getTime();
		} catch (Exception e) {
			result = 0L;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

}
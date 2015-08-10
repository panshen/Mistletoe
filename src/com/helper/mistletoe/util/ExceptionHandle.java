package com.helper.mistletoe.util;

import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class ExceptionHandle {
	/**
	 * 判断是否抛出异常<br/>
	 * 如果商业模式（DEVS_BUSINESS_MODEL = true），则不抛出。<br/>
	 * 如果不抛出异常（DEVS_THROW_ALL_EXCEPTION = false），则不抛出。
	 *
	 * @return true表示抛出异常，false异常不抛出。
	 */
	private static boolean ifThrowException() throws Exception {
		boolean result = false;
		try {
			result = true;
			if (MainConst.DEVS_BUSINESS_MODEL) {
				result = false;
			}

			if (!MainConst.DEVS_THROW_ALL_EXCEPTION) {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	/**
	 * 处理一些不感兴趣的异常，如果设定里设定抛出异常，那就会抛出。否则异常被忽略掉。<br/>
	 * 但是日志里仍然会打印异常
	 *
	 * @param exc
	 *            不感兴趣的异常
	 */
	public static void unInterestException(Exception exc) throws Exception {
		LogPrint.printException_E(exc);
		if (ifThrowException()) {
			exc.printStackTrace();
		} else {
			throw exc;
		}
	}

	/**
	 * 处理一些不感兴趣的异常，异常被忽略掉。<br/>
	 * 但是日志里仍然会打印异常
	 *
	 * @param exc
	 *            不感兴趣的异常
	 */
	public static void ignoreException(Exception exc) {
		try {
			LogPrint.printException_E(exc);
			exc.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

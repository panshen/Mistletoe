package com.helper.mistletoe.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import com.helper.mistletoe.util.sysconst.MainConst;
import com.tencent.android.tpush.XGPushConfig;

/**
 * 获得系统的通用参数
 * 
 * @author 张久瑞
 * @version 1.0
 *
 */
public class CustomInfo {

	/**
	 * 获取硬件平台，在这个App里，当然是Android平台了。。<br/>
	 * 例如：0
	 * 
	 * @return 硬件平台编号
	 */
	public static String getPlatform() {
		String result = "";
		try {
			result = MainConst.APP_PLATFORM;
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取系统版本号<br/>
	 * 例如：Android 4.4.4
	 * 
	 * @return 系统版本号
	 */
	public static String getOs() {
		String result = "";
		try {
			result = "Android " + android.os.Build.VERSION.RELEASE;
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取手机型号<br/>
	 * 例如：motorola XT1031
	 * 
	 * @return 手机型号
	 */
	public static String getHardware() {
		String result = "";
		try {
			result = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取手机版本名<br/>
	 * 例如：1.2.4
	 * 
	 * @param context
	 *            设备上下文
	 * @return 手机版本名
	 */
	public static String getApp_version(Context context) {
		String result = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			result = pi.versionName;
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取手机版本号<br/>
	 * 例如：1
	 * 
	 * @param context
	 *            设备上下文
	 * @return 手机版本号
	 */
	public static String getApp_versionCode(Context context) {
		String result = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			result = String.valueOf(pi.versionCode);
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取设备识别码<br/>
	 * 例如：A000002CDC85C1
	 * 
	 * @param context
	 *            设备上下文
	 * @return 设备识别码DeviceToken
	 */
	public static String getDevice_token(Context context) {
		String result = "";
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			result = tm.getDeviceId();
			if (result == null) {
				String ANDROID_ID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
				if (ANDROID_ID == null) {
					// result=
				} else {
					result = ANDROID_ID;
				}
			}
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取应用程序的Token，用于安全验证
	 * 
	 * @return AppToken
	 */
	public static String getApp_Token() {
		String result = "";
		try {
			result = MainConst.APP_APP_TOKEN;
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取应用程序的Md5Token，用于安全验证
	 * 
	 * @return Md5Token
	 */
	public static String getMd5_Token() {
		String result = "";
		try {
			result = MainConst.APP_MD5_TOKEN;
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 用于推送的Toke
	 * 
	 * @return PushToken
	 */
	public static String getPushToken(Context context) {
		String result = "";
		try {
			result = XGPushConfig.getToken(context);
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}
}
package com.helper.mistletoe.m.work.base;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.LogPrint;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.util.sysconst.Broadcast_Const;

public class Broadcast_Sender {
	/**
	 * 目标有变化
	 * 
	 * @param context
	 *            设备上下文
	 */
	public static void targetChanged(Context context) {
		try {
			JSONObject json = new JSONObject();
			sendBr_UI(context, Broadcast_Const.BC_TAG_UI_DATACHANGED_TARGET, json);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 目标成员变化
	 * 
	 * @param context
	 *            设备上下文
	 * @param targetId
	 *            目标Id
	 */
	public static void targetMemeberChanged(Context context, int targetId) {
		try {
			JSONObject json = new JSONObject();
			json.put("targetId", targetId);
			sendBr_UI(context, Broadcast_Const.BC_TAG_UI_DATACHANGED_TARGETMEMBER, json);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 目标成员变化
	 * 
	 * @param context
	 *            设备上下文
	 * @param targetId
	 *            目标Id
	 */
	public static void targetMemeberChanged(Context context, String targetId) {
		try {
			int tTargetId = Integer.valueOf(targetId);
			targetMemeberChanged(context, tTargetId);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 进度更新有变化
	 * 
	 * @param context
	 *            设备上下文
	 * @param targetId
	 *            目标Id
	 */
	public static void scheduleChanged(Context context, int targetId) {
		try {
			JSONObject json = new JSONObject();
			json.put("targetId", targetId);
			sendBr_UI(context, Broadcast_Const.BC_TAG_UI_DATACHANGED_SCHEDULE, json);
			LogPrint.printString_V("Test Task", "广播：进度更新变化");
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 进度更新有变化
	 * 
	 * @param context
	 *            设备上下文
	 * @param targetId
	 *            目标Id
	 */
	public static void scheduleChanged(Context context, String targetId) {
		try {
			int tTargetId = Transformation_Util.String2int(targetId);
			scheduleChanged(context, tTargetId);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 云端目标有变化
	 * 
	 * @param context
	 *            设备上下文
	 */
	public static void targetChanged_Cloud(Context context) {
		try {
			LogPrint.printString_V("PushMassege", "目标变化");
			JSONObject json = new JSONObject();
			sendBr_UI(context, Broadcast_Const.BC_TAG_CLOUD_CHANGED_TARGET, json);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 云端目标成员有变化
	 * 
	 * @param context
	 *            设备上下文
	 * @param targetId
	 *            目标Id
	 */
	public static void targetMemeberChanged_Cloud(Context context, int targetId) {
		try {
			LogPrint.printString_V("PushMassege", "目标变化成员变化" + targetId);
			JSONObject json = new JSONObject();
			json.put("targetId", targetId);
			sendBr_UI(context, Broadcast_Const.BC_TAG_CLOUD_CHANGED_TARGETMEMBER, json);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 云端进度更新有变化
	 * 
	 * @param context
	 *            设备上下文
	 * @param targetId
	 *            目标Id
	 */
	public static void scheduleChanged_Cloud(Context context, int targetId) {
		try {
			LogPrint.printString_V("PushMassege", "进度更新变化" + targetId);
			JSONObject json = new JSONObject();
			json.put("targetId", targetId);
			sendBr_UI(context, Broadcast_Const.BC_TAG_CLOUD_CHANGED_SCHEDULE, json);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private static void sendBr(Context context, String data) {
		try {
			Intent intent = new Intent();
			intent.setAction(Broadcast_Const.BC_TAG_UI_DATACHANGED);
			intent.putExtra("com.helper.mistletoe.bbc.data", data);
			context.sendBroadcast(intent);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private static void sendBr_UI(Context context, String tag, JSONObject data) {
		try {
			JSONObject json = new JSONObject();
			json.put(Broadcast_Const.BC_MODE_DATA, data);
			json.put(Broadcast_Const.BC_MODE_CLASS, "");
			json.put(Broadcast_Const.BC_MODE_STATUS, tag);
			sendBr(context, json.toString());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
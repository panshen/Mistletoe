package com.helper.mistletoe.util.sysconst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public interface Broadcast_Const {
	public final static String BC_MODE_CLASS = "workClass";
	public final static String BC_MODE_DATA = "workData";
	public final static String BC_MODE_STATUS = "xxxxintentStatus";

	/**
	 * 罗盘内部广播
	 */
	public final static String BC_TAG_BASE = "com.helper.mistletoe.bbc";
	/**
	 * 工厂模式Work
	 */
	public final static String BC_TAG_WORK = BC_TAG_BASE + ".work";
	/**
	 * UI数据刷新
	 */
	public final static String BC_TAG_UI_DATACHANGED = BC_TAG_BASE + ".ui.dataChanged";
	/**
	 * 云端有新数据
	 */
	public final static String BC_TAG_CLOUD_CHANGED = BC_TAG_BASE + ".cloud.Changed";

	/**
	 * 目标变化
	 */
	public final static String BC_TAG_UI_DATACHANGED_TARGET = BC_TAG_UI_DATACHANGED + ".target";
	/**
	 * 目标成员变化
	 */
	public final static String BC_TAG_UI_DATACHANGED_TARGETMEMBER = BC_TAG_UI_DATACHANGED + ".targetMember";
	/**
	 * 进度更新变化
	 */
	public final static String BC_TAG_UI_DATACHANGED_SCHEDULE = BC_TAG_UI_DATACHANGED + ".schedule";

	/**
	 * 云端有新目标
	 */
	public final static String BC_TAG_CLOUD_CHANGED_TARGET = BC_TAG_CLOUD_CHANGED + ".target";
	/**
	 * 云端有目标成员变化
	 */
	public final static String BC_TAG_CLOUD_CHANGED_TARGETMEMBER = BC_TAG_CLOUD_CHANGED + ".targetMember";
	/**
	 * 云端有新进度更新
	 */
	public final static String BC_TAG_CLOUD_CHANGED_SCHEDULE = BC_TAG_CLOUD_CHANGED + ".schedule";
}
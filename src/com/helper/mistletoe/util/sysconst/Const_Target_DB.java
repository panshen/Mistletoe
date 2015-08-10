package com.helper.mistletoe.util.sysconst;

/**
 * 数据库常量
 */
public interface Const_Target_DB {
	public final String TABLE_TARGETS = "Target";
	public final String TABLE_TARGETS_KEY = "_id";
	public final String TABLE_TARGETS_TARGETID = "target_id";// 目标ID
	public final String TABLE_TARGETS_CREATORID = "creator_id";// 目标创建者ID
	public final String TABLE_TARGETS_SUMMARY = "summary";// 目标名称
	public final String TABLE_TARGETS_DESCRIPTION = "description";// 目标详情描述
	public final String TABLE_TARGETS_STATUS = "status";// 状态
	public final String TABLE_TARGETS_CREATETIME = "create_time";// 目标创建时间
	public final String TABLE_TARGETS_STARTTIME = "start_time";// 目标开始时间
	public final String TABLE_TARGETS_ETATIME = "eta_time";// 目标估计完成时间
	public final String TABLE_TARGETS_DUETIME = "due_time";// 目标截至时间
	public final String TABLE_TARGETS_FINALTIME = "final_time";// 目标实际完成时间
	public final String TABLE_TARGETS_BUDGET = "budget";// 目标预算
	public final String TABLE_TARGETS_COST = "cost";// 目标已经发生的费用
	public final String TABLE_TARGETS_VIEWFLAG = "view_flag";// 目标可视状态
	public final String TABLE_TARGETS_NOTECOUNT = "note_count";// 目标备注数量
	public final String TABLE_TARGETS_LASTUPDATETIME = "last_update_time";// 目标最后一次更新时间
	public final String TABLE_TARGETS_UNREADNOTENUMBER = "unread_note_number";// 目标中未读的备注信息数
	public final String TABLE_TARGETS_UNREADHELPERCHANGENUMBER = "unread_helper_change_number";// 目标中未读的帮手变化数
	public final String TABLE_TARGETS_LASTNOTE = "last_note";// 目标的最后一个备注
	public final String TABLE_TARGETS_TARGETFLAG = "target_flag";// 目标的颜色标签
	public final String TABLE_TARGETS_TARGETSTICK = "target_stick";// 目标置顶
	public final String TABLE_TARGETS_TARGETSTICKTIME = "target_stick_time";// 置顶时间
	public final String TABLE_TARGETS_MEMBERS = "members";
	public final String TABLE_TARGETS_LOCTARGETTAG = "loc_TargetTag";
	public final String TABLE_TARGETS_ACCEPTPUSHMSG = "accept_push_msg";
	public final String TABLE_TARGETS_STATUSSERVER = "status_server";
	public final String TABLE_TARGETS_SYNCSTATUSLOC = "SyncStatus_Loc";// 同步状态
	public final String TABLE_TARGETS_TARGETTYPE = "target_type";// 通用=1，系统=2，市场=3，置顶=4（空默认为通用）
	public final String TABLE_TARGETS_HEADPICS = "head_pics";// 目标图片数组，默认为空
	public final String TABLE_TARGETS_AVATARFILEID = "avatar_file_id";// 目标创建者的头像
	public final String TABLE_TARGETS_RECORETYPELOC = "recordType_Loc";// 存在数据库中的目标类型
	public final String TABLE_TARGETS_ATTITUDE = "attitude";// 被点赞的次数
}
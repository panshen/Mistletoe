package com.helper.mistletoe.util.sysconst;

/**
 * 数据库常量
 */
public interface Const_TargetMember_DB {
	public final String TABLE_TARGETSMEMBER = "TargetMember";
	public final String TABLE_TARGETSMEMBER_KEY = "_id";
	public final String TABLE_TARGETSMEMBER_FK_TARGETID = "fk_TargetId";// 目标ID
	public final String TABLE_TARGETSMEMBER_MEMBERID = "member_id";// 目标名称
	public final String TABLE_TARGETSMEMBER_MEMBERROLE = "member_role";// 目标详情描述
	public final String TABLE_TARGETSMEMBER_MEMBERSTATUS = "member_status";// 状态
	public final String TABLE_TARGETSMEMBER_LASTUPDATETIME = "last_updated_time";// 目标创建时间
	public final String TABLE_TARGETSMEMBER_MEMBERAVATARFILEID = "member_avatar_file_id";// 帮手的头像Id
	public final String TABLE_TARGETSMEMBER_MEMBERNAME = "member_name";// 帮手的名字
	public final String TABLE_TARGETSMEMBER_INWAITINGSTATUS = "in_waiting_status";// 帮手状态修改是不是已经在等待回复状态
	public final String TABLE_TARGETSMEMBER_SECONDESCOUNTDOWN = "seconds_countdown";// 帮手状态修改剩余时间
	public final String TABLE_TARGETSMEMBER_SYNCSTATUSLOC = "SyncStatus_Loc";// 同步状态
	public final String TABLE_TARGETSMEMBER_RECORETYPELOC = "recordType_Loc";// 存在数据库中的目标类型
}
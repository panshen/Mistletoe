package com.helper.mistletoe.util.sysconst;

/**
 * 数据库常量
 */
public interface Const_Schedule_DB {
	public final String TABLE_SCHEDULE = "Schdeule";
	public final String TABLE_SCHEDULE_KEY = "_id";
	public final String TABLE_SCHEDULE_ID = "id";// 目标ID
	public final String TABLE_SCHEDULE_CREATORID = "creator_id";// 目标创建者ID
	public final String TABLE_SCHEDULE_NOTETYPE = "note_type";// 目标名称
	public final String TABLE_SCHEDULE_HIGHLIGHTFLAG = "highlight_flag";// 目标详情描述
	public final String TABLE_SCHEDULE_CREATETIME = "create_time";// 状态
	public final String TABLE_SCHEDULE_CONTENT = "content";// 目标创建时间
	public final String TABLE_SCHEDULE_LASTUPDATETIME = "last_updated_time";// 目标开始时间
	public final String TABLE_SCHEDULE_READPERCENTAGE = "read_percentage";// 目标估计完成时间
	public final String TABLE_SCHEDULE_ITEMTAG = "loc_ItemTag";// 目标估计完成时间
	public final String TABLE_SCHEDULE_LOCTARGETID = "loc_TargetId";
	public final String TABLE_SCHEDULE_FILEID = "file_id";
	public final String TABLE_SCHEDULE_REMINDERTIME = "reminder_time";
	public final String TABLE_SCHEDULE_REVIEWRANK = "review_rank";
	public final String TABLE_SCHEDULE_GPSLATITUDE = "gps_latitude";
	public final String TABLE_SCHEDULE_GPSLONGITUDE = "gps_longitude";
	public final String TABLE_SCHEDULE_COST = "cost";
	public final String TABLE_SCHEDULE_LOCFILEPATH = "loc_FilePath";
	public final String TABLE_SCHEDULE_STATUS = "status";
	public final String TABLE_SCHEDULE_LOCMEMBERSSTRING = "loc_MembersString";
	public final String TABLE_SCHEDULE_LOCRECEIVERSTRING = "loc_ReceiverString";
	public final String TABLE_SCHEDULE_VIEWFLAG = "view_flag";
	public final String TABLE_SCHEDULE_COSTTYPE = "cost_type";// 费用类型
	public final String TABLE_SCHEDULE_COSTDESC = "cost_desc";// 费用描述
	public final String TABLE_SCHEDULE_TRANSACTIONTIME = "transaction_time";// 交易时间
	public final String TABLE_SCHEDULE_BALANCE = "balance";// 余额
	public final String TABLE_SCHEDULE_CREATORNAME = "creator_name";// 创建者的名字
	public final String TABLE_SCHEDULE_CREATORAVATARFILEID = "creator_avatar_file_id";// 创建者的头像
	public final String TABLE_SCHEDULE_SYNCSTATUSLOC = "SyncStatus_Loc";// 同步状态
	public final String TABLE_SCHEDULE_RECORETYPELOC = "recordType_Loc";// 存在数据库中的目标类型
	public final String TABLE_SCHEDULE_OWNERID = "owner_id";// 帮谁记得费用ID
	public final String TABLE_SCHEDULE_TAGID = "tag_id";// 费用的类型，服务器预置的几种类型
	public final String TABLE_SCHEDULE_OWNER = "owner";// 帮谁记得费用
}
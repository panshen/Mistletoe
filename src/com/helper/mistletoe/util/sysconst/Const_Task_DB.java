package com.helper.mistletoe.util.sysconst;

/**
 * 数据库常量
 */
public interface Const_Task_DB {
	public final String TABLE_TASK = "Task";
	public final String TABLE_TASK_KEY = "_id";

	public final String TABLE_TASK_SYNCSTATUSLOC = "SyncStatus_Loc";// 同步状态
	public final String TABLE_TASK_TASKID = "task_id";// TaskId
	public final String TABLE_TASK_CREATERID = "creator_id";// 创建者Id
	public final String TABLE_TASK_WEIGHTS = "weights";// 权重
	public final String TABLE_TASK_OWNERID = "owner_id";// 分配给的人Id
	public final String TABLE_TASK_OWNER = "owner";// 分配给的人名字
	public final String TABLE_TASK_STATUS = "status";// Task的状态
	public final String TABLE_TASK_CLIENTREFID = "client_ref_id";// 本地Tag
	public final String TABLE_TASK_DESCRIPTION = "description";// 描述
	public final String TABLE_TASK_LOCTARGETID = "loc_TargetId";// 对应的目标Id
	//添加
	public final String TABLE_TASK_BEGIN_TIME ="begin_time";
	public final String TABLE_TASK_END_TIME ="end_time" ;
}
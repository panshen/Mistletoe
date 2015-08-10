package com.helper.mistletoe.m.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.helper.mistletoe.m.pojo.Custom_Enum.SyncStatus;
import com.helper.mistletoe.m.pojo.Task_Bean;
import com.helper.mistletoe.m.pojo.Task_Enum.TaskStatus;
import com.helper.mistletoe.util.Const_DB;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * <p>
 * Created by 张久瑞 on 2015年3月25日.
 * </p>
 * 
 * @author 张久瑞
 * @version 1.0
 */
public abstract class Task_DBService extends Custom_DBService<Task_Bean> {

	public Task_DBService(Context context) {
		super(context, Const_DB.TABLE_TASK, Const_DB.TABLE_TASK_KEY);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected Task_Bean readData(Cursor c) {
		Task_Bean result = null;
		try {
			result = new Task_Bean();
			int p = 0;
			if ((p = c.getColumnIndex(Const_DB.TABLE_TASK_KEY)) > -1) {
				result.setPrimaryKey(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TASK_SYNCSTATUSLOC)) > -1) {
				result.setSyncStatus(SyncStatus.valueOf(c.getInt(p)));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TASK_TASKID)) > -1) {
				result.setTask_id(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TASK_CREATERID)) > -1) {
				result.setCreator_id(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TASK_WEIGHTS)) > -1) {
				result.setWeights(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TASK_OWNERID)) > -1) {
				result.setOwner_id(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TASK_OWNER)) > -1) {
				result.setOwner(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TASK_STATUS)) > -1) {
				result.setStatus(TaskStatus.valueOf(c.getInt(p)));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TASK_CLIENTREFID)) > -1) {
				result.setClient_ref_id(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TASK_DESCRIPTION)) > -1) {
				result.setDescription(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TASK_LOCTARGETID)) > -1) {
				result.setLoc_TargetId(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TASK_BEGIN_TIME)) > -1) {
				result.setBegin_time(c.getLong(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TASK_END_TIME)) > -1) {
				result.setEnd_time(c.getLong(p));
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	protected void writeData(Task_Bean data) {
		try {
			boolean contentIsSafe = true;
			if (data == null) {
				contentIsSafe = false;
			}
			if (contentIsSafe) {
				ContentValues val = new ContentValues();

				if (data.SyncStatus_Loc != null) {
					val.put(Const_DB.TABLE_TASK_SYNCSTATUSLOC, data.getSyncStatus().toInt());
				}
				if (data.task_id != null) {
					val.put(Const_DB.TABLE_TASK_TASKID, data.getTask_id());
				}
				if (data.creator_id != null) {
					val.put(Const_DB.TABLE_TASK_CREATERID, data.getCreator_id());
				}
				if (data.weights != null) {
					val.put(Const_DB.TABLE_TASK_WEIGHTS, data.getWeights());
				}
				if (data.owner_id != null) {
					val.put(Const_DB.TABLE_TASK_OWNERID, data.getOwner_id());
				}
				if (data.owner != null) {
					val.put(Const_DB.TABLE_TASK_OWNER, data.getOwner());
				}
				if (data.status != null) {
					val.put(Const_DB.TABLE_TASK_STATUS, data.getStatus().toInt());
				}
				if (data.client_ref_id != null) {
					val.put(Const_DB.TABLE_TASK_CLIENTREFID, data.getClient_ref_id());
				}
				if (data.description != null) {
					val.put(Const_DB.TABLE_TASK_DESCRIPTION, data.getDescription());
				}
				if (data.loc_TargetId != null) {
					val.put(Const_DB.TABLE_TASK_LOCTARGETID, data.getLoc_TargetId());
				}
				if (data.begin_time != null) {
					val.put(Const_DB.TABLE_TASK_BEGIN_TIME, data.getBegin_time());
				}
				if (data.end_time != null) {
					val.put(Const_DB.TABLE_TASK_END_TIME, data.getEnd_time());
				}

				String where = Const_DB.TABLE_TASK_TASKID + " is '" + data.getTask_id() + "'";
				writeData(val, where);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static String getCreateSql() {
		String result = "";
		try {
			result += "create table " + Const_DB.TABLE_TASK + " (";
			result += getAddTableSQL(Const_DB.TABLE_TASK_KEY, "integer primary key autoincrement") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TASK_SYNCSTATUSLOC, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TASK_TASKID, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TASK_CREATERID, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TASK_WEIGHTS, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TASK_OWNERID, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TASK_OWNER, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TASK_STATUS, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TASK_CLIENTREFID, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TASK_LOCTARGETID, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TASK_BEGIN_TIME, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TASK_END_TIME, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TASK_DESCRIPTION, "text") + ");";
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public void dbUpdate_V7(SQLiteDatabase db) {
		super.dbUpdate_V7(db);
		try {
			db.execSQL(getCreateSql());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
	@Override
	public void dbUpdate_V9(SQLiteDatabase db) {
		super.dbUpdate_V9(db);
		String sql = "";

		sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_TASK_BEGIN_TIME + " " + "text" + ";";
		db.execSQL(sql);

		sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_TASK_END_TIME + " " + "text" + ";";
		db.execSQL(sql);
	}
}
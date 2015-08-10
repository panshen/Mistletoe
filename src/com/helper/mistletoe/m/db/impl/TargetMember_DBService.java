package com.helper.mistletoe.m.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.helper.mistletoe.m.pojo.Custom_Enum.SyncStatus;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberRole;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.m.pojo.TargetMember_Pojo;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRecordType;
import com.helper.mistletoe.util.Const_DB;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Transformation_Util;

/**
 * <p>
 * Created by 张久瑞 on 2015年3月25日.
 * </p>
 * 
 * @author 张久瑞
 * @version 1.0
 */
public class TargetMember_DBService extends Custom_DBService<TargetMember_Bean> {

	public TargetMember_DBService(Context context) {
		super(context, Const_DB.TABLE_TARGETSMEMBER, Const_DB.TABLE_TARGETSMEMBER_KEY);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected TargetMember_Bean readData(Cursor c) {
		TargetMember_Bean result = null;
		try {
			result = new TargetMember_Bean();
			int p = 0;
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETSMEMBER_FK_TARGETID)) > -1) {
				result.setFk_TargetId(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETSMEMBER_MEMBERID)) > -1) {
				result.setMember_id(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETSMEMBER_MEMBERROLE)) > -1) {
				result.setMember_role(MemberRole.valueOf(c.getInt(p), MemberRole.Helper));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETSMEMBER_MEMBERSTATUS)) > -1) {
				result.setMember_status(MemberStatus.valueOf(c.getInt(p), MemberStatus.Discussing));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETSMEMBER_LASTUPDATETIME)) > -1) {
				result.setHelper_update_time(c.getLong(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETSMEMBER_MEMBERAVATARFILEID)) > -1) {
				result.setMember_avatar_file_id(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETSMEMBER_MEMBERNAME)) > -1) {
				result.setMember_name(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETSMEMBER_INWAITINGSTATUS)) > -1) {
				result.setIn_waiting_status(Transformation_Util.Sring2boolean(c.getString(p)));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETSMEMBER_SECONDESCOUNTDOWN)) > -1) {
				result.setSeconds_countdown(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETSMEMBER_SYNCSTATUSLOC)) > -1) {
				result.setSyncStatus(SyncStatus.valueOf(c.getInt(p)));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETSMEMBER_KEY)) > -1) {
				result.setPrimaryKey(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETSMEMBER_RECORETYPELOC)) > -1) {
				result.setRecordType_Loc(TargetRecordType.valueOf((int) c.getInt(p)));
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	protected void writeData(TargetMember_Bean data) {
		try {
			ContentValues val = new ContentValues();
			val.put(Const_DB.TABLE_TARGETSMEMBER_FK_TARGETID, data.getFk_TargetId());
			val.put(Const_DB.TABLE_TARGETSMEMBER_MEMBERID, data.getMember_id());
			val.put(Const_DB.TABLE_TARGETSMEMBER_MEMBERROLE, data.getMember_role().toInt());
			val.put(Const_DB.TABLE_TARGETSMEMBER_MEMBERSTATUS, data.getMember_status().toInt());
			val.put(Const_DB.TABLE_TARGETSMEMBER_LASTUPDATETIME, data.getHelper_update_time());
			if (data.member_avatar_file_id != null) {
				val.put(Const_DB.TABLE_TARGETSMEMBER_MEMBERAVATARFILEID, data.getMember_avatar_file_id());
			}
			if (data.member_name != null) {
				val.put(Const_DB.TABLE_TARGETSMEMBER_MEMBERNAME, data.getMember_name());
			}
			if (data.in_waiting_status != null) {
				val.put(Const_DB.TABLE_TARGETSMEMBER_INWAITINGSTATUS,
						(String) String.valueOf(((TargetMember_Pojo) data).getIn_waiting_status()));
			}
			if (data.seconds_countdown != null) {
				val.put(Const_DB.TABLE_TARGETSMEMBER_SECONDESCOUNTDOWN, (int) data.getSeconds_countdown());
			}
			if (data.SyncStatus_Loc != null) {
				val.put(Const_DB.TABLE_TARGETSMEMBER_SYNCSTATUSLOC, (int) data.getSyncStatus().toInt());
			}
			if (data.recordType_Loc != null) {
				val.put(Const_DB.TABLE_TARGETSMEMBER_RECORETYPELOC, (int) data.getRecordType_Loc().toInt());
			}

			String where = "(" + Const_DB.TABLE_TARGETSMEMBER_FK_TARGETID + " is '" + data.getFk_TargetId() + "') and ("
					+ Const_DB.TABLE_TARGETSMEMBER_MEMBERID + " is '" + data.getMember_id() + "')";
			writeData(val, where);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static String getCreateSql() {
		String result = "";
		try {
			result += "create table " + Const_DB.TABLE_TARGETSMEMBER + " (";
			result += getAddTableSQL(Const_DB.TABLE_TARGETSMEMBER_KEY, "integer primary key autoincrement") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETSMEMBER_FK_TARGETID, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETSMEMBER_MEMBERID, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETSMEMBER_MEMBERROLE, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETSMEMBER_MEMBERSTATUS, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETSMEMBER_LASTUPDATETIME, "integer") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETSMEMBER_MEMBERAVATARFILEID, "integer") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETSMEMBER_MEMBERNAME, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETSMEMBER_INWAITINGSTATUS, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETSMEMBER_SECONDESCOUNTDOWN, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETSMEMBER_SYNCSTATUSLOC, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETSMEMBER_RECORETYPELOC, "text") + ");";
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public void dbUpdate_V3(SQLiteDatabase db) {
		super.dbUpdate_V3(db);
		try {
			String sql = "";

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_TARGETSMEMBER_INWAITINGSTATUS + " " + "text" + ";";
			db.execSQL(sql);

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_TARGETSMEMBER_SECONDESCOUNTDOWN + " " + "text" + ";";
			db.execSQL(sql);

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_TARGETSMEMBER_SYNCSTATUSLOC + " " + "text" + ";";
			db.execSQL(sql);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void dbUpdate_V5(SQLiteDatabase db) {
		super.dbUpdate_V5(db);
		try {
			String sql = "";

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_TARGETSMEMBER_RECORETYPELOC + " " + "text" + ";";
			db.execSQL(sql);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
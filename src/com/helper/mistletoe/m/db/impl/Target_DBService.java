package com.helper.mistletoe.m.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.helper.mistletoe.m.pojo.Custom_Enum.SyncStatus;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRecordType;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRunningState;
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
public abstract class Target_DBService extends Custom_DBService<Target_Bean> {

	public Target_DBService(Context context) {
		super(context, Const_DB.TABLE_TARGETS, Const_DB.TABLE_TARGETS_KEY);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected Target_Bean readData(Cursor c) {
		Target_Bean result = null;
		try {
			result = new Target_Bean();
			int p = 0;
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_KEY)) > -1) {
				result.setPrimaryKey(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_TARGETID)) > -1) {
				result.setTarget_id(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_CREATORID)) > -1) {
				result.setCreator_id(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_SUMMARY)) > -1) {
				result.setSummary(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_DESCRIPTION)) > -1) {
				result.setDescription(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_STATUS)) > -1) {
				result.setStatus(TargetRunningState.valueOf(c.getInt(p)));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_CREATETIME)) > -1) {
				result.setCreate_time(c.getLong(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_STARTTIME)) > -1) {
				result.setStart_time(c.getLong(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_ETATIME)) > -1) {
				result.setEta_time(c.getLong(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_DUETIME)) > -1) {
				result.setDue_time(c.getLong(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_FINALTIME)) > -1) {
				result.setFinal_time(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_BUDGET)) > -1) {
				result.setBudget(c.getDouble(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_COST)) > -1) {
				result.setCost(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_VIEWFLAG)) > -1) {
				result.setView_flag(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_NOTECOUNT)) > -1) {
				result.setNote_count(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_LASTUPDATETIME)) > -1) {
				result.setLast_updated_time(c.getLong(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_UNREADNOTENUMBER)) > -1) {
				result.setUnread_note_number(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_UNREADHELPERCHANGENUMBER)) > -1) {
				result.setUnread_helper_change_number(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_LASTNOTE)) > -1) {
				result.setLast_note(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_TARGETFLAG)) > -1) {
				result.setTarget_flag(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_TARGETSTICK)) > -1) {
				result.setTarget_stick(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_TARGETSTICKTIME)) > -1) {
				result.setTarget_stick_time(c.getLong(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_LOCTARGETTAG)) > -1) {
				result.setLoc_TargetTag(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_ACCEPTPUSHMSG)) > -1) {
				result.setAccept_push_msg(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_STATUSSERVER)) > -1) {
				int tValue = c.getInt(p);
				if (tValue > 0) {
					result.setStatus_server(MemberStatus.valueOf((int) tValue, MemberStatus.Discussing));
				}
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_SYNCSTATUSLOC)) > -1) {
				result.setSyncStatus(SyncStatus.valueOf((int) c.getInt(p)));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_TARGETTYPE)) > -1) {
				result.setTarget_type(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_HEADPICS)) > -1) {
				result.setHead_pics((String) c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_AVATARFILEID)) > -1) {
				result.setAvatar_file_id((int) c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_RECORETYPELOC)) > -1) {
				result.setRecordType_Loc(TargetRecordType.valueOf((int) c.getInt(p)));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_TARGETS_ATTITUDE)) > -1) {
				result.setAttitude((int) c.getInt(p));
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	protected void writeData(Target_Bean data) {
		try {
			boolean contentIsSafe = true;
			if ((data == null) || (!data.isContentSecurity())) {
				contentIsSafe = false;
			}
			if (contentIsSafe) {
				ContentValues val = new ContentValues();

				if (data.target_id != null) {
					val.put(Const_DB.TABLE_TARGETS_TARGETID, data.getTarget_id());
				}
				if (data.creator_id != null) {
					val.put(Const_DB.TABLE_TARGETS_CREATORID, data.getCreator_id());
				}
				if (data.summary != null) {
					val.put(Const_DB.TABLE_TARGETS_SUMMARY, data.getSummary());
				}
				if (data.description != null) {
					val.put(Const_DB.TABLE_TARGETS_DESCRIPTION, data.getDescription());
				}
				if (data.status != null) {
					val.put(Const_DB.TABLE_TARGETS_STATUS, data.getStatus().toInt());
				}
				if (data.create_time != null) {
					val.put(Const_DB.TABLE_TARGETS_CREATETIME, data.getCreate_time());
				}
				if (data.start_time != null) {
					val.put(Const_DB.TABLE_TARGETS_STARTTIME, data.getStart_time());
				}
				if (data.eta_time != null) {
					val.put(Const_DB.TABLE_TARGETS_ETATIME, data.getEta_time());
				}
				if (data.due_time != null) {
					val.put(Const_DB.TABLE_TARGETS_DUETIME, data.getDue_time());
				}
				if (data.final_time != null) {
					val.put(Const_DB.TABLE_TARGETS_FINALTIME, data.getFinal_time());
				}
				if (data.budget != null) {
					val.put(Const_DB.TABLE_TARGETS_BUDGET, data.getBudget());
				}
				if (data.cost != null) {
					val.put(Const_DB.TABLE_TARGETS_COST, data.getCost());
				}
				if (data.view_flag != null) {
					val.put(Const_DB.TABLE_TARGETS_VIEWFLAG, data.getView_flag());
				}
				if (data.note_count != null) {
					val.put(Const_DB.TABLE_TARGETS_NOTECOUNT, data.getNote_count());
				}
				if (data.last_updated_time != null) {
					val.put(Const_DB.TABLE_TARGETS_LASTUPDATETIME, data.getLast_updated_time());
				}
				if (data.unread_note_number != null) {
					val.put(Const_DB.TABLE_TARGETS_UNREADNOTENUMBER, data.getUnread_note_number());
				}
				if (data.unread_helper_change_number != null) {
					val.put(Const_DB.TABLE_TARGETS_UNREADHELPERCHANGENUMBER, data.getUnread_helper_change_number());
				}
				if (data.last_note != null) {
					val.put(Const_DB.TABLE_TARGETS_LASTNOTE, data.getLast_note());
				}
				if (data.target_flag != null) {
					val.put(Const_DB.TABLE_TARGETS_TARGETFLAG, data.getTarget_flag());
				}
				if (data.target_stick != null) {
					val.put(Const_DB.TABLE_TARGETS_TARGETSTICK, data.getTarget_stick());
				}
				if (data.target_stick_time != null) {
					val.put(Const_DB.TABLE_TARGETS_TARGETSTICKTIME, data.getTarget_stick_time());
				}
				if (data.loc_TargetTag != null) {
					val.put(Const_DB.TABLE_TARGETS_LOCTARGETTAG, data.loc_TargetTag);
				}
				if (data.accept_push_msg != null) {
					val.put(Const_DB.TABLE_TARGETS_ACCEPTPUSHMSG, data.getAccept_push_msg());
				}
				if (data.status_server != null) {
					val.put(Const_DB.TABLE_TARGETS_STATUSSERVER, data.getStatus_server().toInt());
				}
				if (data.SyncStatus_Loc != null) {
					val.put(Const_DB.TABLE_TARGETS_SYNCSTATUSLOC, data.getSyncStatus().toInt());
				}
				if (data.target_type != null) {
					val.put(Const_DB.TABLE_TARGETS_TARGETTYPE, (int) data.getTarget_type().toInt());
				}
				if (data.head_pics != null) {
					val.put(Const_DB.TABLE_TARGETS_HEADPICS, (String) data.getHead_pics_String());
				}
				if (data.avatar_file_id != null) {
					val.put(Const_DB.TABLE_TARGETS_AVATARFILEID, (int) data.getAvatar_file_id());
				}
				if (data.recordType_Loc != null) {
					val.put(Const_DB.TABLE_TARGETS_RECORETYPELOC, (int) data.getRecordType_Loc().toInt());
				}
				if (data.attitude != null) {
					val.put(Const_DB.TABLE_TARGETS_ATTITUDE, (int) data.getAttitude());
				}

				writeData(val, data.getTarget_id(), data.getLoc_TargetTag());
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static String getCreateSql() {
		String result = "";
		try {
			result += "create table Target (";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_KEY, "integer primary key autoincrement") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_TARGETID, "integer not null default '-1'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_CREATORID, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_SUMMARY, "text not null default ''") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_DESCRIPTION, "text not null default ''") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_STATUS, "integer not null default '1'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_CREATETIME, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_STARTTIME, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_ETATIME, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_DUETIME, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_FINALTIME, "text not null default ''") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_BUDGET, "not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_COST, "text not null default ''") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_VIEWFLAG, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_NOTECOUNT, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_LASTUPDATETIME, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_UNREADNOTENUMBER, "integer") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_UNREADHELPERCHANGENUMBER, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_LASTNOTE, "text not null default ''") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_TARGETFLAG, "integer not null default '1'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_TARGETSTICK, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_TARGETSTICKTIME, "integer not null default '0'") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_LOCTARGETTAG, "text not null default ''") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_ACCEPTPUSHMSG, "text not null default ''") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_STATUSSERVER, "text not null default ''") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_SYNCSTATUSLOC, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_TARGETTYPE, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_HEADPICS, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_AVATARFILEID, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_RECORETYPELOC, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_TARGETS_ATTITUDE, "text") + ");";
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	protected void writeData(ContentValues data, int targetId, String targetTag) {
		Cursor c = null;
		try {
			Target_Bean t_Bean = getTarget(targetId, targetTag);
			if (t_Bean == null) {
				t_Bean = new Target_Bean();
			}
			String t_whwere = Const_DB.TABLE_TARGETS_KEY + " is " + t_Bean.getPrimaryKey();
			writeData(data, t_whwere);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}

	public abstract Target_Bean getTarget(int targetId, String TargetTag);

	@Override
	public void dbUpdate_V3(SQLiteDatabase db) {
		super.dbUpdate_V3(db);
		try {
			String sql = "";

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_TARGETS_SYNCSTATUSLOC + " " + "text" + ";";
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

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_TARGETS_TARGETTYPE + " " + "text" + ";";
			db.execSQL(sql);

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_TARGETS_HEADPICS + " " + "text" + ";";
			db.execSQL(sql);

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_TARGETS_AVATARFILEID + " " + "text" + ";";
			db.execSQL(sql);

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_TARGETS_RECORETYPELOC + " " + "text" + ";";
			db.execSQL(sql);

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_TARGETS_ATTITUDE + " " + "text" + ";";
			db.execSQL(sql);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
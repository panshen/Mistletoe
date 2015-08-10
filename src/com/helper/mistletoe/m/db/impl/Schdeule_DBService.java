package com.helper.mistletoe.m.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.helper.mistletoe.m.pojo.Custom_Enum.SyncStatus;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleHighlightFlag;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleRevokeStatus;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleType;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleViewFlag;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRecordType;
import com.helper.mistletoe.util.Const_DB;
import com.helper.mistletoe.util.ExceptionHandle;

public abstract class Schdeule_DBService extends CustomV2_DBService<Schedule_Bean> {

	public Schdeule_DBService(Context context) {
		super(context, Const_DB.TABLE_SCHEDULE, Const_DB.TABLE_SCHEDULE_KEY);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected Schedule_Bean readData(Cursor c, Schedule_Bean data) {
		Schedule_Bean result = null;
		try {
			result = data;
			int p = 0;
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_KEY)) > -1) {
				result.setPrimaryKey(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_ID)) > -1) {
				result.setId(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_CREATORID)) > -1) {
				result.setCreator_id(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_NOTETYPE)) > -1) {
				result.setNote_type(ScheduleType.valueOf(c.getInt(p)));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_HIGHLIGHTFLAG)) > -1) {
				result.setHighlight_flag(ScheduleHighlightFlag.valueOf(c.getInt(p)));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_CREATETIME)) > -1) {
				result.setCreate_time(c.getLong(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_CONTENT)) > -1) {
				result.setContent(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_LASTUPDATETIME)) > -1) {
				result.setLast_updated_time(c.getLong(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_READPERCENTAGE)) > -1) {
				result.setRead_percentage(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_ITEMTAG)) > -1) {
				result.setLoc_ItemTag(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_LOCTARGETID)) > -1) {
				result.setLoc_TargetId(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_FILEID)) > -1) {
				result.setFile_id(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_REMINDERTIME)) > -1) {
				result.setReminder_time(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_REVIEWRANK)) > -1) {
				result.setReview_rank(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_GPSLATITUDE)) > -1) {
				result.setGps_latitude(c.getDouble(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_GPSLONGITUDE)) > -1) {
				result.setGps_longitude(c.getDouble(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_COST)) > -1) {
				result.setCost(c.getFloat(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_LOCFILEPATH)) > -1) {
				result.setLoc_FilePath(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_STATUS)) > -1) {
				result.setStatus(ScheduleRevokeStatus.valueOf((int) c.getInt(p)));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_LOCMEMBERSSTRING)) > -1) {
				result.setLoc_MembersString(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_VIEWFLAG)) > -1) {
				result.setView_flag(ScheduleViewFlag.valueOf(c.getInt(p)));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_LOCRECEIVERSTRING)) > -1) {
				result.setLoc_ReceiverString(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_COSTTYPE)) > -1) {
				result.setCost_type(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_COSTDESC)) > -1) {
				result.setCost_desc(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_TRANSACTIONTIME)) > -1) {
				result.setTransaction_time(c.getLong(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_BALANCE)) > -1) {
				result.setBalance(c.getFloat(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_CREATORNAME)) > -1) {
				result.setCreator_name(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_CREATORAVATARFILEID)) > -1) {
				result.setCreator_avatar_file_id(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_SYNCSTATUSLOC)) > -1) {
				result.setSyncStatus(SyncStatus.valueOf(c.getInt(p)));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_RECORETYPELOC)) > -1) {
				result.setRecordType_Loc(TargetRecordType.valueOf(c.getInt(p)));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_OWNERID)) > -1) {
				result.setOwner_id(c.getString(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_TAGID)) > -1) {
				result.setTag_id(c.getInt(p));
			}
			if ((p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_OWNER)) > -1) {
				result.setOwner((String) c.getString(p));
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	protected Schedule_Bean readData(Cursor c) {
		Schedule_Bean result = null;
		try {
			result = readData(c, new Schedule_Bean());
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	protected ContentValues writeContentValues(Schedule_Bean data) {
		ContentValues result = null;
		try {
			result = new ContentValues();
			if (data.id != null) {
				result.put(Const_DB.TABLE_SCHEDULE_ID, data.getId());
			}
			if (data.creator_id != null) {
				result.put(Const_DB.TABLE_SCHEDULE_CREATORID, data.getCreator_id());
			}
			if (data.note_type != null) {
				result.put(Const_DB.TABLE_SCHEDULE_NOTETYPE, data.getNote_type().toInt());
			}
			if (data.highlight_flag != null) {
				result.put(Const_DB.TABLE_SCHEDULE_HIGHLIGHTFLAG, (int) data.getHighlight_flag().toInt());
			}
			if (data.create_time != null) {
				result.put(Const_DB.TABLE_SCHEDULE_CREATETIME, data.getCreate_time());
			}
			if (data.content != null) {
				result.put(Const_DB.TABLE_SCHEDULE_CONTENT, data.getContent());
			}
			if (data.last_updated_time != null) {
				result.put(Const_DB.TABLE_SCHEDULE_LASTUPDATETIME, data.getLast_updated_time());
			}
			if (data.read_percentage != null) {
				result.put(Const_DB.TABLE_SCHEDULE_READPERCENTAGE, data.getRead_percentage());
			}
			if (data.loc_ItemTag != null) {
				result.put(Const_DB.TABLE_SCHEDULE_ITEMTAG, data.getLoc_ItemTag());
			}
			if (data.loc_TargetId != null) {
				result.put(Const_DB.TABLE_SCHEDULE_LOCTARGETID, data.getLoc_TargetId());
			}
			if (data.file_id != null) {
				result.put(Const_DB.TABLE_SCHEDULE_FILEID, data.getFile_id());
			}
			if (data.reminder_time != null) {
				result.put(Const_DB.TABLE_SCHEDULE_REMINDERTIME, data.getReminder_time());
			}
			if (data.review_rank != null) {
				result.put(Const_DB.TABLE_SCHEDULE_REVIEWRANK, data.getReview_rank());
			}
			if (data.gps_latitude != null) {
				result.put(Const_DB.TABLE_SCHEDULE_GPSLATITUDE, (Double) data.getGps_latitude());
			}
			if (data.gps_longitude != null) {
				result.put(Const_DB.TABLE_SCHEDULE_GPSLONGITUDE, (Double) data.getGps_longitude());
			}
			if (data.cost != null) {
				result.put(Const_DB.TABLE_SCHEDULE_COST, (float) data.getCost());
			}
			if (data.loc_FilePath != null) {
				result.put(Const_DB.TABLE_SCHEDULE_LOCFILEPATH, data.getLoc_FilePath());
			}
			if (data.status != null) {
				result.put(Const_DB.TABLE_SCHEDULE_STATUS, (int) data.getStatus().toInt());
			}
			if (data.loc_MembersString != null) {
				result.put(Const_DB.TABLE_SCHEDULE_LOCMEMBERSSTRING, data.getLoc_MembersString());
			}
			if (data.view_flag != null) {
				result.put(Const_DB.TABLE_SCHEDULE_VIEWFLAG, (int) data.getView_flag().toInt());
			}
			if (data.loc_ReceiverString != null) {
				result.put(Const_DB.TABLE_SCHEDULE_LOCRECEIVERSTRING, data.getLoc_ReceiverString());
			}
			if (data.cost_type != null) {
				result.put(Const_DB.TABLE_SCHEDULE_COSTTYPE, data.getCost_type());
			}
			if (data.cost_desc != null) {
				result.put(Const_DB.TABLE_SCHEDULE_COSTDESC, data.getCost_desc());
			}
			if (data.transaction_time != null) {
				result.put(Const_DB.TABLE_SCHEDULE_TRANSACTIONTIME, data.getTransaction_time());
			}
			if (data.balance != null) {
				result.put(Const_DB.TABLE_SCHEDULE_BALANCE, data.getBalance());
			}
			if (data.create_time != null) {
				result.put(Const_DB.TABLE_SCHEDULE_CREATORNAME, data.getCreator_name());
			}
			if (data.creator_avatar_file_id != null) {
				result.put(Const_DB.TABLE_SCHEDULE_CREATORAVATARFILEID, data.getCreator_avatar_file_id());
			}
			if (data.SyncStatus_Loc != null) {
				result.put(Const_DB.TABLE_SCHEDULE_SYNCSTATUSLOC, (int) data.getSyncStatus().toInt());
			}
			if (data.recordType_Loc != null) {
				result.put(Const_DB.TABLE_SCHEDULE_RECORETYPELOC, (int) data.getRecordType_Loc().toInt());
			}
			if (data.owner_id != null) {
				result.put(Const_DB.TABLE_SCHEDULE_OWNERID, (String) data.getOwner_id_String());
			}
			if (data.tag_id != null) {
				result.put(Const_DB.TABLE_SCHEDULE_TAGID, (int) data.getTag_id());
			}
			if (data.owner != null) {
				result.put(Const_DB.TABLE_SCHEDULE_OWNER, (String) data.getOwner());
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public Schedule_Bean selectData(Schedule_Bean data) {
		Schedule_Bean result = null;
		Cursor c = null;
		try {
			result = data;
			String t_where = "";
			String t_id = "" + result.getId();
			String t_tag = result.getLoc_ItemTag();

			if ((!t_id.equals("")) || (!t_tag.equals(""))) {
				if (Integer.valueOf(t_id) > 0) {
					// 如果存在服务器ID，则判断两条服务器ID相同，或者本地ID相同并且不存在服务器ID的条目
					if (!t_tag.equals("")) {
						t_where = "(" + Const_DB.TABLE_SCHEDULE_ID + " is '" + t_id + "') or ((" + Const_DB.TABLE_SCHEDULE_ITEMTAG
								+ " is '" + t_tag + "') and (" + Const_DB.TABLE_SCHEDULE_ID + " <= '0'))";
					} else {
						t_where = Const_DB.TABLE_SCHEDULE_ID + " is '" + t_id + "'";
					}
				} else {
					// 此种情况只对比TargetTag
					t_where = Const_DB.TABLE_SCHEDULE_ITEMTAG + " is '" + t_tag + "'";
				}
				c = getRDB().query(getTableName(), null, t_where, null, null, null, null);
				if (c.getCount() > 0) {
					c.moveToFirst();
					result.setPrimaryKey(c.getInt(c.getColumnIndex(Const_DB.TABLE_SCHEDULE_KEY)));
				}
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	public static String getCreateSql() {
		String result = "";
		try {
			result += "create table " + Const_DB.TABLE_SCHEDULE + " (";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_KEY, "integer primary key autoincrement") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_ID, "integer") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_CREATORID, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_NOTETYPE, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_HIGHLIGHTFLAG, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_CREATETIME, "integer") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_CONTENT, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_LASTUPDATETIME, "integer") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_READPERCENTAGE, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_ITEMTAG, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_LOCTARGETID, "integer") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_FILEID, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_REMINDERTIME, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_REVIEWRANK, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_GPSLATITUDE, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_GPSLONGITUDE, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_COST, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_LOCFILEPATH, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_STATUS, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_LOCMEMBERSSTRING, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_LOCRECEIVERSTRING, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_VIEWFLAG, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_COSTTYPE, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_COSTDESC, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_TRANSACTIONTIME, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_BALANCE, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_CREATORNAME, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_CREATORAVATARFILEID, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_SYNCSTATUSLOC, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_RECORETYPELOC, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_OWNERID, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_TAGID, "text") + ",";
			result += getAddTableSQL(Const_DB.TABLE_SCHEDULE_OWNER, "text") + ");";
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public void dbUpdate_V1(SQLiteDatabase db) {
		super.dbUpdate_V1(db);
		try {
			String sql = "";

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_SCHEDULE_LOCRECEIVERSTRING + " " + "text" + ";";
			db.execSQL(sql);

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_SCHEDULE_VIEWFLAG + " " + "text" + ";";
			db.execSQL(sql);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void dbUpdate_V2(SQLiteDatabase db) {
		super.dbUpdate_V2(db);
		try {
			String sql = "";

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_SCHEDULE_CREATORNAME + " " + "text" + ";";
			db.execSQL(sql);

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_SCHEDULE_CREATORAVATARFILEID + " " + "text" + ";";
			db.execSQL(sql);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void dbUpdate_V3(SQLiteDatabase db) {
		super.dbUpdate_V3(db);
		try {
			String sql = "";

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_SCHEDULE_COSTTYPE + " " + "text" + ";";
			db.execSQL(sql);

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_SCHEDULE_COSTDESC + " " + "text" + ";";
			db.execSQL(sql);

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_SCHEDULE_TRANSACTIONTIME + " " + "text" + ";";
			db.execSQL(sql);

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_SCHEDULE_BALANCE + " " + "text" + ";";
			db.execSQL(sql);

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_SCHEDULE_SYNCSTATUSLOC + " " + "text" + ";";
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

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_SCHEDULE_RECORETYPELOC + " " + "text" + ";";
			db.execSQL(sql);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void dbUpdate_V6(SQLiteDatabase db) {
		super.dbUpdate_V6(db);
		try {
			String sql = "";

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_SCHEDULE_OWNERID + " " + "text" + ";";
			db.execSQL(sql);

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_SCHEDULE_TAGID + " " + "text" + ";";
			db.execSQL(sql);

			sql = "ALTER TABLE " + getTableName() + " ADD " + Const_DB.TABLE_SCHEDULE_OWNER + " " + "text" + ";";
			db.execSQL(sql);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
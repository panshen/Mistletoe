package com.helper.mistletoe.m.db;

import java.util.ArrayList;

import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.Const_DB;
import com.helper.mistletoe.util.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HelperServiceImpl implements HelperService {
	public DatabaseReadyGo db_Helper = null;

	public HelperServiceImpl(Context context) {
		db_Helper = new DatabaseReadyGo(context);
	}

	/**
	 * 批量插入
	 */
	@Override
	public boolean addHelperList(ArrayList<ContentValues> list) {
		boolean ret = false;
		SQLiteDatabase database = null;
		try {
			database = db_Helper.getWritableDatabase();
			database.beginTransactionNonExclusive();
			for (ContentValues v : list) {
				database.insert(Const_DB.DATABASE_TABLE_HELPERS, null, v);
			}
			database.setTransactionSuccessful();
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.endTransaction();
				database.close();
			}
		}
		return ret;
	}

	/**
	 * 批量更新
	 */
	@Override
	public boolean updateHelperListInformationById(ArrayList<ContentValues> list) {
		boolean ret = false;
		SQLiteDatabase database = null;
		try {
			database = db_Helper.getWritableDatabase();
			database.beginTransactionNonExclusive();
			for (ContentValues v : list) {
				database.update(Const_DB.DATABASE_TABLE_HELPERS, v, Const_DB.DATABASE_TABLE_HELPERS_ID + " is " + "'" + v.get(Const_DB.DATABASE_TABLE_HELPERS_ID) + "'", null);
			}
			database.setTransactionSuccessful();
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.endTransaction();
				database.close();
			}
		}
		return ret;
	}

	/**
	 * 需要考虑同时取多个status
	 */
	@Override
	public ArrayList<Helpers_Sub_Bean> getHelperListByStatus(int[] status) {
		ArrayList<Helpers_Sub_Bean> list = new ArrayList<Helpers_Sub_Bean>();
		SQLiteDatabase database = null;
		final int statusCount = status.length - 1;
		final StringBuilder sb_status = new StringBuilder("");
		String whereClause = null;
		if (statusCount >= 0) {
			if (statusCount > 0) {
				for (int i = 0; i < statusCount; i++) {
					sb_status.append(status[i]).append(",");
				}
			}
			sb_status.append(status[statusCount]);
			whereClause = Const_DB.DATABASE_TABLE_HELPERS_STATUS + " IN (" + sb_status + ")";
		}
		Cursor cursor = null;
		try {
			database = db_Helper.getReadableDatabase();
			cursor = database.query(Const_DB.DATABASE_TABLE_HELPERS, Const_DB.HELPERS_PROJECTION, whereClause, null, null, null, Const_DB.DATABASE_TABLE_HELPERS_NAMEPINYIN);
			if (cursor.getColumnCount() > 0) {
				while (cursor.moveToNext()) {
					Helpers_Sub_Bean helpers_Sub_Bean = new Helpers_Sub_Bean();
					helpers_Sub_Bean = DBUtil.getHelperByCursor(cursor);
					list.add(helpers_Sub_Bean);
				}
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return list;
	}

	@Override
	public Helpers_Sub_Bean getHelperById(String id) {
		Helpers_Sub_Bean helpers_Sub_Bean = new Helpers_Sub_Bean();
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {
			database = db_Helper.getReadableDatabase();
			cursor = database.query(Const_DB.DATABASE_TABLE_HELPERS, Const_DB.HELPERS_PROJECTION, Const_DB.DATABASE_TABLE_HELPERS_ID + " is '" + id + "'", null, null, null, null);
			if (cursor.getColumnCount() > 0) {
				while (cursor.moveToNext()) {
					helpers_Sub_Bean = DBUtil.getHelperByCursor(cursor);
				}
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return helpers_Sub_Bean;
	}

	@Override
	public boolean updateHelperStatusById(String id, String status) {
		boolean flag = false;
		SQLiteDatabase database = null;
		ContentValues values = new ContentValues();
		values.put(Const_DB.DATABASE_TABLE_HELPERS_STATUS, status);
		int count = 0;
		try {
			database = db_Helper.getWritableDatabase();
			count = database.update(Const_DB.DATABASE_TABLE_HELPERS, values, Const_DB.DATABASE_TABLE_HELPERS_ID + " is " + "'" + id + "'", null);
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	@Override
	public long getLastUpdatedTime() {
		SQLiteDatabase database = null;
		Cursor cursor = null;
		long time = 0L;
		try {
			database = db_Helper.getReadableDatabase();
			cursor = database.query(Const_DB.DATABASE_TABLE_HELPERS, Const_DB.HELPERS_PROJECTION, null, null, null, null, Const_DB.DATABASE_TABLE_HELPERS_UPDATETIME + " " + "desc");
			if (cursor.getColumnCount() > 0) {
				if (cursor.moveToFirst()) {
					Helpers_Sub_Bean helpers_Sub_Bean = new Helpers_Sub_Bean();
					helpers_Sub_Bean = DBUtil.getHelperByCursor(cursor);
					time = helpers_Sub_Bean.getHelper_update_time();
				}
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return time;
	}

	@Override
	public ArrayList<Helpers_Sub_Bean> getHelperListByMemberId(Integer[] memberId) {
		ArrayList<Helpers_Sub_Bean> list = new ArrayList<Helpers_Sub_Bean>();
		SQLiteDatabase database = null;
		final int memberIdCount = memberId.length - 1;
		final StringBuilder sb_status = new StringBuilder("");
		String whereClause = null;
		if (memberIdCount >= 0) {
			if (memberIdCount > 0) {
				for (int i = 0; i < memberIdCount; i++) {
					sb_status.append(memberId[i]).append(",");
				}
			}
			sb_status.append(memberId[memberIdCount]);
			whereClause = Const_DB.DATABASE_TABLE_HELPERS_ID + " IN (" + sb_status + ")";
		}
		Cursor cursor = null;
		try {
			database = db_Helper.getReadableDatabase();
			cursor = database.query(Const_DB.DATABASE_TABLE_HELPERS, Const_DB.HELPERS_PROJECTION, whereClause, null, null, null, Const_DB.DATABASE_TABLE_HELPERS_NAMEPINYIN);
			if (cursor.getColumnCount() > 0) {
				while (cursor.moveToNext()) {
					Helpers_Sub_Bean helpers_Sub_Bean = new Helpers_Sub_Bean();
					helpers_Sub_Bean = DBUtil.getHelperByCursor(cursor);
					list.add(helpers_Sub_Bean);
				}
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return list;
	}

	@Override
	public Helpers_Sub_Bean getHelperById(Integer helper_id) {
		Helpers_Sub_Bean helpers_Sub_Bean = new Helpers_Sub_Bean();
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {
			database = db_Helper.getReadableDatabase();
			cursor = database.query(Const_DB.DATABASE_TABLE_HELPERS, Const_DB.HELPERS_PROJECTION, Const_DB.DATABASE_TABLE_HELPERS_ID + " is " + "'" + helper_id + "'", null, null, null,
					Const_DB.DATABASE_TABLE_HELPERS_NAMEPINYIN);
			if (cursor.getColumnCount() > 0) {
				while (cursor.moveToNext()) {
					helpers_Sub_Bean = DBUtil.getHelperByCursor(cursor);
				}
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return helpers_Sub_Bean;
	}

	@Override
	public Boolean updateHelperById(ContentValues initialValues) {
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = db_Helper.getWritableDatabase();
			count = database.update(Const_DB.DATABASE_TABLE_HELPERS, initialValues, Const_DB.DATABASE_TABLE_HELPERS_ID + " is " + "'" + initialValues.get(Const_DB.DATABASE_TABLE_HELPERS_ID) + "'",
					null);
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;

	}

	@Override
	public Boolean addHelperById(ContentValues initialValues) {
		boolean flag = false;
		SQLiteDatabase database = null;
		long count = 0;
		try {
			database = db_Helper.getWritableDatabase();
			count = database.insert(Const_DB.DATABASE_TABLE_HELPERS, null, initialValues);
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

}

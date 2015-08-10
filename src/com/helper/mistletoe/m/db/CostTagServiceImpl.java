package com.helper.mistletoe.m.db;

import java.util.ArrayList;

import com.helper.mistletoe.m.pojo.NoteTag_Pojo;
import com.helper.mistletoe.util.Const_DB;
import com.helper.mistletoe.util.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CostTagServiceImpl implements CostTagService {
	public DatabaseReadyGo db_Helper = null;

	public CostTagServiceImpl(Context context) {
		db_Helper = new DatabaseReadyGo(context);
	}
	/**
	 * 批量插入
	 */
	@Override
	public boolean addTagList(ArrayList<ContentValues> list) {
		// TODO Auto-generated method stub
		boolean ret = false;
		SQLiteDatabase database = null;
		try {
			database = db_Helper.getWritableDatabase();
			database.beginTransactionNonExclusive();
			for (ContentValues v : list) {
				database.insert(Const_DB.DATABASE_TABLE_COSTTAG, null, v);
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
	 * 需要考虑同时取每个目标独有的和系统的
	 */
	@Override
	public ArrayList<NoteTag_Pojo> getTagListByTargetId(Integer[] targetId) {
		ArrayList<NoteTag_Pojo> list = new ArrayList<NoteTag_Pojo>();
		SQLiteDatabase database = null;
		final int statusCount = targetId.length - 1;
		final StringBuilder sb_status = new StringBuilder("");
		String whereClause = null;
		if (statusCount >= 0) {
			if (statusCount > 0) {
				for (int i = 0; i < statusCount; i++) {
					sb_status.append(targetId[i]).append(",");
				}
			}
			sb_status.append(targetId[statusCount]);
			whereClause = Const_DB.DATABASE_TABLE_COSTTAG_TARGETID + " IN (" + sb_status + ")";
		}
		Cursor cursor = null;
		try {
			database = db_Helper.getReadableDatabase();
			cursor = database.query(Const_DB.DATABASE_TABLE_COSTTAG, Const_DB.COSTTAG_PROJECTION, whereClause, null, null, null, Const_DB.DATABASE_TABLE_COSTTAG_ID+ " " + "desc");
			if (cursor.getColumnCount() > 0) {
				while (cursor.moveToNext()) {
					NoteTag_Pojo CostTag = new NoteTag_Pojo();
					CostTag = DBUtil.getCostTagByCursor(cursor);
					list.add(CostTag);
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
	public NoteTag_Pojo getTagById(Integer id) {
		// TODO Auto-generated method stub
		NoteTag_Pojo costTag = new NoteTag_Pojo();
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {
			database = db_Helper.getReadableDatabase();
			cursor = database.query(Const_DB.DATABASE_TABLE_COSTTAG, Const_DB.COSTTAG_PROJECTION, Const_DB.DATABASE_TABLE_COSTTAG_ID+ " is " + "'" +id + "'", null, null, null,
					null);
			if (cursor.getColumnCount() > 0) {
				while (cursor.moveToNext()) {
					costTag = DBUtil.getCostTagByCursor(cursor);
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
		return costTag;
	}

	@Override
	public Boolean addTagById(ContentValues initialValues) {
		boolean flag = false;
		SQLiteDatabase database = null;
		long count = 0;
		try {
			database = db_Helper.getWritableDatabase();
			count = database.insert(Const_DB.DATABASE_CREATE_COSTTAG, null, initialValues);
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
	public Boolean deletedTagByTargetId(Integer targetId) {
		// TODO Auto-generated method stub
		return null;
	}
//
//	@Override
//	public Boolean updateHelperById(ContentValues initialValues) {
//		boolean flag = false;
//		SQLiteDatabase database = null;
//		int count = 0;
//		try {
//			database = db_Helper.getWritableDatabase();
//			count = database.update(Const_DB.DATABASE_TABLE_HELPERS, initialValues, Const_DB.DATABASE_TABLE_HELPERS_ID + " is " + "'" + initialValues.get(Const_DB.DATABASE_TABLE_HELPERS_ID) + "'",
//					null);
//			flag = (count > 0 ? true : false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (database != null) {
//				database.close();
//			}
//		}
//		return flag;
//
//	}
	@Override
	public ArrayList<NoteTag_Pojo> getTagList() {
		ArrayList<NoteTag_Pojo> list = new ArrayList<NoteTag_Pojo>();
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {
			database = db_Helper.getReadableDatabase();
			cursor = database.query(Const_DB.DATABASE_TABLE_COSTTAG, Const_DB.COSTTAG_PROJECTION, null, null, null, null, Const_DB.DATABASE_TABLE_COSTTAG_ID+ " " + "desc");
			if (cursor.getColumnCount() > 0) {
				while (cursor.moveToNext()) {
					NoteTag_Pojo CostTag = new NoteTag_Pojo();
					CostTag = DBUtil.getCostTagByCursor(cursor);
					list.add(CostTag);
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
}

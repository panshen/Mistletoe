package com.helper.mistletoe.m.db;

import java.util.ArrayList;

import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.Const_DB;
import com.helper.mistletoe.util.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GroupServiceImpl implements GroupService {
	public DatabaseReadyGo db_Helper = null;

	public GroupServiceImpl(Context context) {
		db_Helper = new DatabaseReadyGo(context.getApplicationContext());
	}

	/**
	 * 批量插入
	 */
	@Override
	public boolean addGroupList(ArrayList<ContentValues> list) {
		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			database = db_Helper.getWritableDatabase();
			database.beginTransactionNonExclusive();
			for (ContentValues v : list) {
				database.insert(Const_DB.DATABASE_TABLE_GROUPS, null, v);
			}
			database.setTransactionSuccessful();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.endTransaction();
				database.close();
			}
		}
		return flag;
	}

	/**
	 * 批量更新
	 */
	@Override
	public boolean updateGroupList(ArrayList<ContentValues> list) {
		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			database = db_Helper.getWritableDatabase();
			database.beginTransactionNonExclusive();
			for (ContentValues v : list) {
				database.update(Const_DB.DATABASE_TABLE_GROUPS, v, Const_DB.DATABASE_TABLE_GROUPS_ID + " is " + "'" + v.get(Const_DB.DATABASE_TABLE_GROUPS_ID) + "'", null);
			}
			database.setTransactionSuccessful();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.endTransaction();
				database.close();
			}
		}
		return flag;
	}

	/**
	 * 根据group_statu查询group_list
	 */
	@Override
	public ArrayList<Group_Bean> getgroupList(int[] status) {
		ArrayList<Group_Bean> list = new ArrayList<Group_Bean>();
		SQLiteDatabase database = null;
		Cursor cursor = null;
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
			whereClause = Const_DB.DATABASE_TABLE_GROUPS_STATUS + " IN (" + sb_status + ")";
		}
		try {
			database = db_Helper.getReadableDatabase();
			cursor = database.query(Const_DB.DATABASE_TABLE_GROUPS, Const_DB.GROUPS_PROJECTION, whereClause, null, Const_DB.DATABASE_TABLE_GROUPS_ID, null, Const_DB.DATABASE_TABLE_GROUPS_CREATETIME);
			if (cursor.getColumnCount() > 0) {
				while (cursor.moveToNext()) {
					Group_Bean group_Bean = new Group_Bean();
					group_Bean = DBUtil.getGroupByCursor(cursor);
					list.add(group_Bean);
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

	/**
	 * 根据group_statu查询helper_list,即将group暂时以helper提出，以便于在helperFragment中显示
	 */
	@Override
	public ArrayList<Helpers_Sub_Bean> getShowDataList(Integer[] status) {
		ArrayList<Helpers_Sub_Bean> list = new ArrayList<Helpers_Sub_Bean>();
		SQLiteDatabase database = null;
		Cursor cursor = null;
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
			whereClause = Const_DB.DATABASE_TABLE_GROUPS_STATUS + " IN (" + sb_status + ")";
		}
		try {
			database = db_Helper.getReadableDatabase();
			cursor = database.query(Const_DB.DATABASE_TABLE_GROUPS, Const_DB.GROUPS_PROJECTION, whereClause, null, Const_DB.DATABASE_TABLE_GROUPS_ID, null, Const_DB.DATABASE_TABLE_GROUPS_CREATETIME);
			if (cursor.getColumnCount() > 0) {
				while (cursor.moveToNext()) {
					Helpers_Sub_Bean helper = new Helpers_Sub_Bean();
					helper = DBUtil.getshowDataFromGroupByCursor(cursor);
					list.add(helper);
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

	/**
	 * 根据group_id删除组
	 */
	@Override
	public boolean deleteGroupById(Integer group_id) {
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = db_Helper.getWritableDatabase();
			count = database.delete(Const_DB.DATABASE_TABLE_GROUPS, Const_DB.DATABASE_TABLE_GROUPS_ID + " is " + "'" + group_id + "'", null);
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

	/**
	 * 根据group_id查找group_list
	 */
	@Override
	public ArrayList<Group_Bean> getGroupMemberByGroupId(Integer[] group_id) {
		ArrayList<Group_Bean> list = new ArrayList<Group_Bean>();
		SQLiteDatabase database = null;
		Cursor cursor = null;
		final int group_idCount = group_id.length - 1;
		final StringBuilder sb_group_id = new StringBuilder("");
		String whereClause = null;
		if (group_idCount >= 0) {
			if (group_idCount > 0) {
				for (int i = 0; i < group_idCount; i++) {
					sb_group_id.append(group_id[i]).append(",");
				}
			}
			sb_group_id.append(group_id[group_idCount]);
			whereClause = Const_DB.DATABASE_TABLE_GROUPS_ID + " IN (" + sb_group_id + ")";
		}

		try {
			database = db_Helper.getReadableDatabase();
			cursor = database.query(Const_DB.DATABASE_TABLE_GROUPS, Const_DB.GROUPS_PROJECTION, whereClause, null, null, null, Const_DB.DATABASE_TABLE_GROUPS_CREATETIME);
			if (cursor.getColumnCount() > 0) {
				while (cursor.moveToNext()) {
					Group_Bean group = new Group_Bean();
					group = DBUtil.getGroupByCursor(cursor);
					list.add(group);
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

	/**
	 * 获取group的last_updatedTime
	 */
	@Override
	public long getLastUpdatedTime() {
		long last_updata_time = 0L;
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {
			database = db_Helper.getReadableDatabase();
			cursor = database.query(Const_DB.DATABASE_TABLE_GROUPS, Const_DB.GROUPS_PROJECTION, null, null, Const_DB.DATABASE_TABLE_GROUPS_ID, null, Const_DB.DATABASE_TABLE_GROUPS_LASTUPDATETIME
					+ " " + "desc");
			if (cursor.getColumnCount() > 0) {
				if (cursor.moveToFirst()) {
					Group_Bean group_Bean = new Group_Bean();
					group_Bean = DBUtil.getGroupByCursor(cursor);
					last_updata_time = group_Bean.getGroup_last_update_time();
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
		return last_updata_time;
	}
}

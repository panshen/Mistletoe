package com.helper.mistletoe.m.db.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.helper.mistletoe.m.db.DBManager;
import com.helper.mistletoe.m.db.DatabaseReadyGo;
import com.helper.mistletoe.util.Array_Util;
import com.helper.mistletoe.util.ExceptionHandle;

public abstract class Custom_DBService<T> extends DBManager {
	protected String tableName;
	protected String tableKeyName;
	protected Context context;

	protected Custom_DBService(Context context, String tableName, String tableKeyName) {
		super();
		try {
			this.tableName = tableName;
			this.tableKeyName = tableKeyName;
			this.context = context;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void clearTable() {
		try {
			getWDB().execSQL("DELETE FROM " + getTableName());
			getWDB().execSQL("update sqlite_sequence set seq=0 where name='" + getTableName() + "'");
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected ArrayList<T> readDataList(Cursor c) {
		ArrayList<T> result = null;
		try {
			result = new ArrayList<T>();
			if (c.getCount() > 0) {
				c.moveToFirst();
				do {
					result.add(readData(c));
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	protected abstract T readData(Cursor c);

	protected void writeDataList(ArrayList<T> dataList) {
		try {
			for (T t : dataList) {
				writeData(t);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected abstract void writeData(T data);

	protected int writeData(ContentValues data, String where) {
		int result;
		Cursor c = null;
		try {
			result = 0;
			c = getRDB().query(getTableName(), new String[] { getTableKeyName() }, where, null, null, null, null);
			if (c.getCount() > 0) {
				result += getWDB().update(getTableName(), data, where, null);
			} else {
				long a = getWDB().insert(getTableName(), null, data);
				if (a >= 0) {
					result++;
				}
			}
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
				getRDB().close();
			}
		}
		return result;
	}

	private SQLiteOpenHelper getDBHelper() {
		SQLiteOpenHelper result = null;
		try {
			result = new DatabaseReadyGo(context);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	protected SQLiteDatabase getWDB() {
		SQLiteDatabase result = null;
		try {
			result = getDBHelper().getWritableDatabase();
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	protected SQLiteDatabase getRDB() {
		SQLiteDatabase result = null;
		try {
			result = getDBHelper().getReadableDatabase();
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public String getTableName() {
		String result = null;
		try {
			if (tableName == null) {
				tableName = "";
			}
			result = tableName;
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public String getTableKeyName() {
		String result = null;
		try {
			if (tableKeyName == null) {
				tableKeyName = "";
			}
			result = tableKeyName;
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	protected static String getAddTableSQL(String lolumName, String modification) {
		String result = "";
		try {
			result = lolumName + " " + modification;
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	protected static <U> String arrayToSelectIn(List<U> status) {
		return Array_Util.anArrayOfJoiningTogetherIntoAString(status, "(", ")", "'", "'", ",");
	}

	protected static String mosaicSelect(List<String> select, String relationship) {
		return Array_Util.anArrayOfJoiningTogetherIntoAString(select, "", "", "(", ")", " " + relationship + " ");
	}
}
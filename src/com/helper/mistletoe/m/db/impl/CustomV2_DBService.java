package com.helper.mistletoe.m.db.impl;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.helper.mistletoe.m.db.DatabaseReadyGo;
import com.helper.mistletoe.m.sjb.abst.DataBase_Bjb;
import com.helper.mistletoe.util.ExceptionHandle;

public abstract class CustomV2_DBService<T extends DataBase_Bjb> extends Custom_DBService<T> {

	protected CustomV2_DBService(Context context, String tableName, String tableKeyName) {
		super(context, tableName, tableKeyName);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void writeDataList(ArrayList<T> dataList) {
		try {
			if (dataList == null) {
				dataList = new ArrayList<T>();
			}
			dataList.remove(null);
			for (T bean : dataList) {
				bean = selectData(bean);
			}

			String tableName = getTableName();
			ContentValues cValues = null;
			String whereClause = "_id is ?";

			SQLiteDatabase db = new DatabaseReadyGo(context).getWritableDatabase();
			for (T bean : dataList) {
				cValues = writeContentValues(bean);
				if (bean.getPrimaryKey() > 0) {
					// 修改
					String[] whereArgs = new String[] { String.valueOf(bean.getPrimaryKey()) };
					db.update(tableName, cValues, whereClause, whereArgs);
				} else {
					// 插入
					db.insert(tableName, null, cValues);
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void writeData(T data) {
		try {
			ArrayList<T> dataList = new ArrayList<T>();
			dataList.add(data);
			writeDataList(dataList);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected abstract T readData(Cursor c);

	protected abstract T readData(Cursor c, T data);

	protected abstract ContentValues writeContentValues(T data);

	protected abstract T selectData(T data);
}
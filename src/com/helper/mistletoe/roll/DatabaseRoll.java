package com.helper.mistletoe.roll;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseRoll extends SQLiteOpenHelper {
	private static final String TAG = "DatabaseContacts";
	private static final String DATABASE_NAME = "roll";
	private static final int DATABASE_VERSION = 1;
	// DATABASE_CREATE常量包括创建titles表的SQL语句。
	// user表的语句
	private static final String DATABASE_CREATE_ROLL = "create table roll(_id integer primary key autoincrement," + "firstDice integer not null default -1,"
			+ "secondDice integer not null default -1," + "thirdDice integer not null default -1," + "fourthDice integer not null default -1," + "fifthDice integer not null default -1,"
			+ "sixthDice integer not null default -1," + "diceTime text not null default'');";

	public DatabaseRoll(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// onCreate 数据表
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_ROLL);// 创建user表
	}

	//
	public void onDeleted(SQLiteDatabase db) {
		db.execSQL("DROP DATABASE" + DATABASE_NAME);
	}

	// onUpgrade 升级数据库
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(TAG, "Upgrading database from version" + oldVersion + "to" + newVersion + ",which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS title");

	}

}

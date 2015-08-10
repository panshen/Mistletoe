package com.helper.mistletoe.m.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.helper.mistletoe.m.db.impl.Schdeule_DBService;
import com.helper.mistletoe.m.db.impl.TargetMember_DBService;
import com.helper.mistletoe.m.db.impl.Target_DBService;
import com.helper.mistletoe.m.db.impl.Task_DBService;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.sp.User_sp;
import com.helper.mistletoe.util.Const_DB;
import com.helper.mistletoe.util.ExceptionHandle;

public class DatabaseReadyGo extends SQLiteOpenHelper {
	private Context mContext;

	public DatabaseReadyGo(Context context) {
		super(context.getApplicationContext(), getDatabaseName(context.getApplicationContext()), null, Const_DB.DB_VERSION);
		this.mContext = context.getApplicationContext();
	}

	// onCreate 数据库
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Const_DB.DATABASE_CREATE_HELPERS);// 创建Helpers表
		db.execSQL(Const_DB.DATABASE_CREATE_GROUPS);// 创建Groups表
		db.execSQL(Const_DB.DATABASE_CREATE_COSTTAG);// 创建CostTag表
		db.execSQL(Target_DBService.getCreateSql());// 创建target表
		db.execSQL(TargetMember_DBService.getCreateSql());// 创建target表
		db.execSQL(Schdeule_DBService.getCreateSql());// 创建target表
		db.execSQL(Task_DBService.getCreateSql());// 创建Task表
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			TargetManager.getInstance(getContext()).dbUpdate(db, oldVersion, newVersion);
			TargetMemberManager.getInstance(getContext()).dbUpdate(db, oldVersion, newVersion);
			SchdeuleManager.getInstance(getContext()).dbUpdate(db, oldVersion, newVersion);
			TaskManager.getInstance(getContext()).dbUpdate(db, oldVersion, newVersion);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static String getDatabaseName(Context context) {
		String result = "";
		try {
			User_Bean user = new User_sp(context).read();
			result = Const_DB.DB_NAME + "_" + user.getUser_id();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	protected Context getContext() {
		return mContext;
	}

	protected Context getApplicationContext() {
		return getContext().getApplicationContext();
	}
}
package com.helper.mistletoe.m.db;

import android.database.sqlite.SQLiteDatabase;

import com.helper.mistletoe.util.Const_DB;
import com.helper.mistletoe.util.ExceptionHandle;

public class DBManager {

	private static DBManager instance = null;

	// 单例模式
	public static DBManager getInstance() {
		if (null == instance) {
			synchronized (DBManager.class) {
				if (instance == null) {
					instance = new DBManager();
				}
			}
		}
		return instance;
	}

	public void dbUpdate(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			if (oldVersion >= 1) {
				dbUpdate_V1(db);
			}
			if (oldVersion >= 2) {
				dbUpdate_V2(db);
			}
			if (oldVersion >= 3) {
				dbUpdate_V3(db);
			}
			if (oldVersion >= 4) {
				dbUpdate_V4(db);
			}
			if (oldVersion >= 5) {
				dbUpdate_V5(db);
			}
			if (oldVersion >= 6) {
				dbUpdate_V6(db);
			}
			if (oldVersion >= 7) {
				dbUpdate_V7(db);
			}
			if (oldVersion >= 8) {
				dbUpdate_V8(db);
			}
			if (oldVersion>=9) {
				dbUpdate_V9(db);
			}
			if (oldVersion>=10) {
				dbUpdate_V10(db);
			}
			if (oldVersion>=11) {
				dbUpdate_V11(db);
			}
			if (oldVersion>=12) {
				dbUpdate_V12(db);
			}
			
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void dbUpdate_V1(SQLiteDatabase db) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void dbUpdate_V2(SQLiteDatabase db) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void dbUpdate_V3(SQLiteDatabase db) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void dbUpdate_V4(SQLiteDatabase db) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void dbUpdate_V5(SQLiteDatabase db) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void dbUpdate_V6(SQLiteDatabase db) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void dbUpdate_V7(SQLiteDatabase db) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void dbUpdate_V8(SQLiteDatabase db) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
	public void dbUpdate_V9(SQLiteDatabase db) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
	private void dbUpdate_V10(SQLiteDatabase db) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		
	}
	private void dbUpdate_V11(SQLiteDatabase db) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		
	}
	private void dbUpdate_V12(SQLiteDatabase db) {
		try {
		db.execSQL(Const_DB.DATABASE_CREATE_COSTTAG);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		
	}
}
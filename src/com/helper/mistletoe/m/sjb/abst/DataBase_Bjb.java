package com.helper.mistletoe.m.sjb.abst;

import com.helper.mistletoe.m.pojo.Custom_Enum.SyncStatus;

public interface DataBase_Bjb extends BabelJavaBean {
	public String getTableName();

	public String getPrimaryKeyName();

	public void setPrimaryKey(int primaryKey);

	public int getPrimaryKey();

	public SyncStatus getSyncStatus();

	public void setSyncStatus(SyncStatus syncStatus_Loc);
}
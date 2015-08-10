package com.helper.mistletoe.m.sjb.core;

import com.helper.mistletoe.m.pojo.Custom_Enum.SyncStatus;
import com.helper.mistletoe.m.sjb.abst.DataBase_Bjb;

public abstract class SimpleDataBase extends SimpleBabelJavaBean implements DataBase_Bjb {
	public Integer DBKey_Loc;
	public Integer SyncStatus_Loc;

	@Override
	public String getFingerprint() {
		return "" + getPrimaryKey();
	}

	@Override
	public void setPrimaryKey(int primaryKey) {
		DBKey_Loc = primaryKey;
	}

	@Override
	public int getPrimaryKey() {
		if (DBKey_Loc == null) {
			DBKey_Loc = -1;
		}
		return DBKey_Loc;
	}

	@Override
	public SyncStatus getSyncStatus() {
		if (SyncStatus_Loc == null) {
			SyncStatus_Loc = SyncStatus.Local.toInt();
		}
		return SyncStatus.valueOf(SyncStatus_Loc);
	}

	@Override
	public void setSyncStatus(SyncStatus syncStatus_Loc) {
		SyncStatus_Loc = syncStatus_Loc.toInt();
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		try {
			if (getClass().isInstance(o)) {
				int oHashCode = o.hashCode();
				if (oHashCode == this.hashCode()) {
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int hashCode() {
		int result = 0;
		try {
			result = ("" + getPrimaryKey()).hashCode() + 36;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
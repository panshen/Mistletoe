package com.helper.mistletoe.m.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.helper.mistletoe.m.db.impl.TargetMember_DBService;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRecordType;
import com.helper.mistletoe.util.Const_DB;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * <p>
 * Created by 张久瑞 on 2015年3月25日.
 * </p>
 * 
 * @author 张久瑞
 * @version 1.0
 */
public class TargetMemberManager extends TargetMember_DBService {
	protected volatile static TargetMemberManager instance;

	private TargetMemberManager(Context context) {
		super(context);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void writeTargetMemberList(int targetId, ArrayList<TargetMember_Bean> targetList) {
		try {
			for (TargetMember_Bean b : targetList) {
				b.setFk_TargetId(targetId);
				b.setRecordType_Loc(TargetRecordType.Tradition);
			}
			String whereClause = Const_DB.TABLE_TARGETSMEMBER_FK_TARGETID + " is '" + targetId + "'";
			try {
				getWDB().delete(getTableName(), whereClause, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			writeDataList(targetList);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		} finally {
			getRDB().close();
		}
	}

	public ArrayList<TargetMember_Bean> readTargetMemberList(int targetId) {
		ArrayList<TargetMember_Bean> result = null;
		Cursor c = null;
		try {
			result = new ArrayList<TargetMember_Bean>();
			String selection = Const_DB.TABLE_TARGETSMEMBER_FK_TARGETID + " is '" + targetId + "'";
			c = getRDB().query(getTableName(), null, selection, null, null, null, null);
			result = readDataList(c);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
				getRDB().close();
			}
		}
		return result;
	}

	public ArrayList<TargetMember_Bean> readTargetMemberList_ByStatus(int targetId, int member_status) {
		ArrayList<TargetMember_Bean> result = null;
		Cursor c = null;
		try {
			result = new ArrayList<TargetMember_Bean>();
			String selection = "((" + Const_DB.TABLE_TARGETSMEMBER_FK_TARGETID + " is '" + targetId + "') and ("
					+ Const_DB.TABLE_TARGETSMEMBER_MEMBERROLE + " is '" + member_status + "'))";
			c = getRDB().query(getTableName(), null, selection, null, null, null, null);
			result = readDataList(c);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
				getRDB().close();
			}
		}
		return result;
	}

	public TargetMember_Bean readTargetMember(int targetId, int member_status) {
		TargetMember_Bean result = null;
		Cursor c = null;
		try {
			result = new TargetMember_Bean();
			String selection = "((" + Const_DB.TABLE_TARGETSMEMBER_FK_TARGETID + " is '" + targetId + "') and ("
					+ Const_DB.TABLE_TARGETSMEMBER_MEMBERID + " is '" + member_status + "'))";
			c = getRDB().query(getTableName(), null, selection, null, null, null, null);
			if (c.getCount() > 0) {
				c.moveToFirst();
				result = readData(c);
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
				getRDB().close();
			}
		}
		return result;
	}

	public TargetMember_Bean readTargetMember_ByStatus(int targetId, int memberId) {
		TargetMember_Bean result = null;
		Cursor c = null;
		try {
			result = new TargetMember_Bean();
			String selection = "((" + Const_DB.TABLE_TARGETSMEMBER_FK_TARGETID + " is '" + targetId + "') and ("
					+ Const_DB.TABLE_TARGETSMEMBER_MEMBERROLE + " is '" + memberId + "'))";
			c = getRDB().query(getTableName(), null, selection, null, null, null, null);
			if (c.getCount() > 0) {
				c.moveToFirst();
				result = readData(c);
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
				getRDB().close();
			}
		}
		return result;
	}

	// 单例模式
	public static TargetMemberManager getInstance(Context context) {
		if (instance == null) {
			synchronized (TargetMemberManager.class) {
				if (instance == null) {
					instance = new TargetMemberManager(context);
				}
			}
		}
		return instance;
	}
}
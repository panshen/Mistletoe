package com.helper.mistletoe.m.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.helper.mistletoe.m.db.impl.Target_DBService;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRecordType;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRunningState;
import com.helper.mistletoe.util.Array_Util;
import com.helper.mistletoe.util.Const_DB;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Transformation_Util;

/**
 * <p>
 * Created by 张久瑞 on 2015年3月25日.
 * </p>
 * 
 * @author 张久瑞
 * @version 1.0
 */
public class TargetManager extends Target_DBService {
	protected volatile static TargetManager instance;

	private TargetManager(Context context) {
		super(context);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void writeTargetList(ArrayList<Target_Bean> targetList) {
		try {
			for (Target_Bean i : targetList) {
				i.setRecordType_Loc(TargetRecordType.Tradition);
			}
			writeDataList(targetList);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void writeTargetList_Market(ArrayList<Target_Bean> targetList) {
		try {
			for (Target_Bean i : targetList) {
				i.setRecordType_Loc(TargetRecordType.Market);
			}
			writeDataList(targetList);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void writeTarget(Target_Bean target) {
		try {
			target.setRecordType_Loc(TargetRecordType.Tradition);
			writeData(target);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void clearTargetUnreadNumber(Target_Bean target) {
		try {
			clearTargetUnreadNumber(target.getTarget_id(), target.getLoc_TargetTag());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void clearTargetUnreadNumber(int targetId, String targetTag) {
		try {
			ContentValues val = new ContentValues();

			val.put(Const_DB.TABLE_TARGETS_UNREADNOTENUMBER, 0);

			writeData(val, (int) targetId, (String) targetTag);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void changeToMarketTarget(Target_Bean target) {
		try {
			changeToMarketTarget(target.getTarget_id(), target.getLoc_TargetTag());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void changeToMarketTarget(int targetId, String targetTag) {
		try {
			ContentValues val = new ContentValues();

			val.put(Const_DB.TABLE_TARGETS_RECORETYPELOC, (int) TargetRecordType.Market.toInt());
			val.put(Const_DB.TABLE_TARGETS_STATUSSERVER, (int) -1);

			writeData(val, (int) targetId, (String) targetTag);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void updateAttitude(Target_Bean target, int attitude) {
		try {
			updateAttitude(target.getTarget_id(), target.getLoc_TargetTag(), attitude);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void updateAttitude(int targetId, String targetTag, int attitude) {
		try {
			ContentValues val = new ContentValues();

			val.put(Const_DB.TABLE_TARGETS_ATTITUDE, (int) attitude);

			writeData(val, (int) targetId, (String) targetTag);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public long getLastUpdateTime() {
		long result = 0L;
		Cursor c = null;
		try {
			String[] columns = new String[] { Const_DB.TABLE_TARGETS_LASTUPDATETIME };
			String orderBy = Const_DB.TABLE_TARGETS_LASTUPDATETIME + " DESC";
			c = getRDB().query(getTableName(), columns, null, null, null, null, orderBy, "1");
			if (c.moveToFirst()) {
				Target_Bean ttt = readData(c);
				result = Long.valueOf(ttt.getLast_updated_time());
			}
		} catch (Exception e) {
			result = 0L;
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	public ArrayList<Target_Bean> readTargetList(TargetRunningState status) {
		ArrayList<Target_Bean> result = null;
		Cursor c = null;
		try {
			result = readTargetList(status, -1, null);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	public ArrayList<Target_Bean> readTargetList(TargetRunningState status, int color) {
		ArrayList<Target_Bean> result = null;
		Cursor c = null;
		try {
			result = readTargetList(status, color, null);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	public ArrayList<Target_Bean> readTargetList(TargetRunningState status, int color, String searchKey) {
		ArrayList<Target_Bean> result = null;
		Cursor c = null;
		try {
			result = new ArrayList<Target_Bean>();
			// 查询参数：where
			String selection = "";
			ArrayList<String> t_SelectionSub = new ArrayList<String>();
			if (status != null) {
				ArrayList<Integer> tIdList = new ArrayList<Integer>();
				for (MemberStatus i : Transformation_Util.TargetRunningState2MemberStatus(status)) {
					// 找到这个status（目标运行状态）对应的server_status（成员状态），放入list中
					tIdList.add(i.toInt());
				}
				t_SelectionSub.add(Const_DB.TABLE_TARGETS_STATUSSERVER + " in " + arrayToSelectIn(tIdList));
			}
			if (color > 0) {
				t_SelectionSub.add(Const_DB.TABLE_TARGETS_TARGETFLAG + " is '" + color + "'");
			}
			if ((searchKey != null) && (!searchKey.equals(""))) {
				t_SelectionSub.add(Const_DB.TABLE_TARGETS_SUMMARY + " like '%" + searchKey + "%'");
			}
			selection = mosaicSelect(t_SelectionSub, "and");
			// 查询参数：order by
			String orderBy = Const_DB.TABLE_TARGETS_TARGETSTICK + " DESC, " + Const_DB.TABLE_TARGETS_LASTUPDATETIME + " DESC, "
					+ Const_DB.TABLE_TARGETS_TARGETID + " DESC";
			// 查询
			c = getRDB().query(getTableName(), null, selection, null, null, null, orderBy);
			// 读取
			if (c.moveToFirst()) {
				result.addAll(readDataList(c));
			}
			// 去除脏数据
			Array_Util.removeArrayListInvalidData(result, new Target_Bean());
			if (result.size() < 0) {
				result = null;
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	public long getTargetCount(TargetRunningState status) {
		long result = 0L;
		Cursor c = null;
		try {
			// 查询参数：where
			String selection = "";
			if (status != null) {
				ArrayList<Integer> tIdList = new ArrayList<Integer>();
				for (MemberStatus i : Transformation_Util.TargetRunningState2MemberStatus(status)) {
					// 找到这个status（目标运行状态）对应的server_status（成员状态），放入list中
					tIdList.add(i.toInt());
				}
				selection = Const_DB.TABLE_TARGETS_STATUSSERVER + " in " + arrayToSelectIn(tIdList);
			}
			// 查询
			c = getRDB().query(getTableName(), new String[] { Const_DB.TABLE_TARGETS_KEY }, selection, null, null, null, null);
			// 读取
			result = c.getCount();
		} catch (Exception e) {
			result = 0L;
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	@Override
	public Target_Bean getTarget(int targetId, String TargetTag) {
		Target_Bean result = null;
		Cursor c = null;
		try {
			String t_where = "";
			if (Integer.valueOf(targetId) > 0) {
				// 如果存在服务器ID，则判断两条服务器ID相同，或者本地ID相同并且不存在服务器ID的条目
				t_where = "(" + Const_DB.TABLE_TARGETS_TARGETID + " is '" + targetId + "') or (" + Const_DB.TABLE_TARGETS_LOCTARGETTAG
						+ " is '" + TargetTag + "' and " + Const_DB.TABLE_TARGETS_TARGETID + " <= '0')";
			} else {
				// 此种情况只对比TargetTag
				t_where = "(" + Const_DB.TABLE_TARGETS_LOCTARGETTAG + " is '" + TargetTag + "')";
			}
			c = getRDB().query(getTableName(), null, t_where, null, null, null, null);
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
			}
		}
		return result;
	}

	public void removeSameTarget() {
		Cursor c = null;
		try {
			HashMap<Integer, HashSet<Integer>> tIdMap = new HashMap<Integer, HashSet<Integer>>();
			int p = 0;
			SQLiteDatabase tDb = getWDB();
			c = tDb.query(getTableName(), new String[] { Const_DB.TABLE_TARGETS_TARGETID, Const_DB.TABLE_TARGETS_KEY }, null, null, null,
					null, null);
			if (c.moveToFirst()) {
				do {
					int tTargetId = -1;
					p = c.getColumnIndex(Const_DB.TABLE_TARGETS_TARGETID);
					if (p > -1) {
						tTargetId = c.getInt(p);
					}

					int tKey = -1;
					p = c.getColumnIndex(Const_DB.TABLE_TARGETS_KEY);
					if (p > -1) {
						tKey = c.getInt(p);
					}

					if ((tKey > -1) && (tTargetId > 0)) {
						HashSet<Integer> tList = tIdMap.get(tTargetId);
						if (tList == null) {
							tList = new HashSet<Integer>();
						}
						tList.add(tKey);
						tIdMap.put(tTargetId, tList);
					}
				} while (c.moveToNext());
				for (Iterator<HashSet<Integer>> iterator = tIdMap.values().iterator(); iterator.hasNext();) {
					HashSet<Integer> temp = iterator.next();
					if (temp.size() > 1) {
						Integer[] tArray = temp.toArray(new Integer[] {});
						for (int ii = 1; ii < temp.size(); ii++) {
							tDb.delete(getTableName(), Const_DB.TABLE_TARGETS_KEY + " is " + tArray[ii], null);
						}
					}
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}

	// 单例模式
	public static TargetManager getInstance(Context context) {
		if (instance == null) {
			synchronized (TargetManager.class) {
				if (instance == null) {
					instance = new TargetManager(context);
				}
			}
		}
		return instance;
	}
}

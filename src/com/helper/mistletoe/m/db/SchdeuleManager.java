package com.helper.mistletoe.m.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteProgram;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import com.helper.mistletoe.m.db.impl.Schdeule_DBService;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleHighlightFlag;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleRevokeStatus;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleType;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRecordType;
import com.helper.mistletoe.util.Array_Util;
import com.helper.mistletoe.util.Const_DB;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.LogPrint;
import com.helper.mistletoe.util.Prism_Util;

public class SchdeuleManager extends Schdeule_DBService {
	protected volatile static SchdeuleManager instance;

	private SchdeuleManager(Context context) {
		super(context);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void writeSchdeuleList(ArrayList<Schedule_Bean> targetList) {
		try {
			HashSet<Schedule_Bean> t_List = new HashSet<Schedule_Bean>(targetList);
			ArrayList<Schedule_Bean> t_LastList = new ArrayList<Schedule_Bean>(t_List);
			for (Schedule_Bean i : t_LastList) {
				i.setRecordType_Loc(TargetRecordType.Tradition);
			}
			writeDataList(t_LastList);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void writeSchdeuleList(ArrayList<Schedule_Bean> targetList, Integer targetId) {
		try {
			for (Schedule_Bean i : targetList) {
				i.setLoc_TargetId(targetId);
				i.setRecordType_Loc(TargetRecordType.Tradition);
			}
			writeDataList(targetList);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void writeSchdeule(Schedule_Bean target) {
		try {
			writeData(target);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void writeSchdeule(Schedule_Bean target, Integer targetId) {
		try {
			target.setLoc_TargetId(targetId);
			writeData(target);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public long getLastUpdateTime(int targetId) {
		long result = 0L;
		Cursor c = null;
		try {
			String[] columns = new String[] { Const_DB.TABLE_SCHEDULE_LASTUPDATETIME };
			String selection = Const_DB.TABLE_SCHEDULE_LOCTARGETID + " is " + targetId;
			String orderBy = Const_DB.TABLE_SCHEDULE_LASTUPDATETIME + " DESC";
			c = getRDB().query(getTableName(), columns, selection, null, null, null, orderBy, "1");
			if (c.moveToFirst()) {
				Schedule_Bean ttt = readData(c);
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

	public Schedule_Bean readSchdeule(int scheduleId, String scheduleTag) {
		Schedule_Bean result = new Schedule_Bean();
		Cursor c = null;
		try {
			result.setId(scheduleId);
			result.setLoc_ItemTag(scheduleTag);
			result = selectData(result);

			String t_where = Const_DB.TABLE_SCHEDULE_KEY + " is " + result.getPrimaryKey();
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

	public int getSchdeuleCount(Integer targetId, ScheduleHighlightFlag highlightFlag, ScheduleType scheduleType, String searchKey,
			boolean onlySynced, Integer memberId, ScheduleRevokeStatus status, Integer tagId) {
		int result = 0;
		Cursor c = null;
		try {
			// 来值安检
			if (targetId != null) {
				// 查询参数：where
				String selection = "";
				ArrayList<String> t_SelectionSub = new ArrayList<String>();
				if (!targetId.equals("")) {
					t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_LOCTARGETID + " is '" + targetId + "'");
				}
				if (highlightFlag != null) {
					t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_HIGHLIGHTFLAG + " is '" + highlightFlag.toInt() + "'");
				}
				if ((searchKey != null) && (!searchKey.equals(""))) {
					t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_CONTENT + " like '%" + searchKey + "%'");
				}
				if (scheduleType != null) {
					t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_NOTETYPE + " is '" + scheduleType.toInt() + "'");
				}
				if (onlySynced) {
					t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_ID + " > '" + 0 + "'");
				}
				if (memberId != null) {
					if ((scheduleType != null) && (scheduleType == ScheduleType.CostApply)) {
						t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_OWNERID + " like '%" + memberId + "%'");
					} else {
						t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_CREATORID + " is '" + memberId + "'");
					}
				}
				if (status != null) {
					t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_STATUS + " is '" + status.toInt() + "'");
				}
				if (tagId != null) {
					t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_TAGID + " is '" + tagId + "'");
				}
				selection = mosaicSelect(t_SelectionSub, "and");
				// 查询参数：order by
				String orderBy = Const_DB.TABLE_SCHEDULE_CREATETIME + " DESC," + Const_DB.TABLE_SCHEDULE_ID + " DESC";
				// 查询
				c = getRDB().query(getTableName(), null, selection, null, null, null, orderBy);
				// 读取
				if (c.moveToFirst()) {
					result = c.getCount();
				}
			}
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	/**
	 * 从数据库中查询进度更新列表
	 * 
	 * @param targetId
	 *            目标Id
	 * @param highlightFlag
	 *            置顶标记
	 * @param scheduleType
	 *            进度更新类型
	 * @param searchKey
	 *            搜索关键字
	 * @param onlySynced
	 *            true表示只查找已经成功发出的
	 * @param memberId
	 *            与谁有关的Id
	 * @param status
	 *            发送状态
	 * @param tagId
	 *            费用归类
	 * @param limit
	 *            数量限制
	 * @param offset
	 *            跳过
	 * @return 查询到的列表
	 */
	public ArrayList<Schedule_Bean> readSchdeuleList(Integer targetId, ScheduleHighlightFlag highlightFlag, ScheduleType scheduleType,
			String searchKey, boolean onlySynced, Integer memberId, ScheduleRevokeStatus status, Integer tagId, Integer limit,
			Integer offset) {
		ArrayList<Schedule_Bean> result = null;
		Cursor c = null;
		try {
			// 来值安检
			result = new ArrayList<Schedule_Bean>();
			if (targetId != null) {
				// 查询参数：where
				String selection = "";
				ArrayList<String> t_SelectionSub = new ArrayList<String>();
				if (!targetId.equals("")) {
					t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_LOCTARGETID + " is '" + targetId + "'");
				}
				if (highlightFlag != null) {
					t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_HIGHLIGHTFLAG + " is '" + highlightFlag.toInt() + "'");
				}
				if ((searchKey != null) && (!searchKey.equals(""))) {
					t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_CONTENT + " like '%" + searchKey + "%'");
				}
				if (scheduleType != null) {
					t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_NOTETYPE + " is '" + scheduleType.toInt() + "'");
				}
				if (onlySynced) {
					t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_ID + " > '" + 0 + "'");
				}
				if (memberId != null) {
					if ((scheduleType != null) && (scheduleType == ScheduleType.CostApply)) {
						t_SelectionSub.add("(" + Const_DB.TABLE_SCHEDULE_CREATORID + " is '" + memberId + "'" + ") or ("
								+ Const_DB.TABLE_SCHEDULE_OWNERID + " like '%" + memberId + "%'" + ")");
					} else {
						t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_CREATORID + " is '" + memberId + "'");
					}
				}
				if (status != null) {
					t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_STATUS + " is '" + status.toInt() + "'");
				}
				if (tagId != null) {
					t_SelectionSub.add(Const_DB.TABLE_SCHEDULE_TAGID + " is '" + tagId + "'");
				}
				selection = mosaicSelect(t_SelectionSub, "and");
				// 查询参数：order by
				String orderBy = Const_DB.TABLE_SCHEDULE_CREATETIME + " DESC," + Const_DB.TABLE_SCHEDULE_ID + " DESC";
				// 查询参数：limit
				String tLimit = null;
				if ((limit != null) && (offset != null)) {
					tLimit = offset + " , " + limit;
				}
				// 查询
				c = getRDB().query(getTableName(), null, selection, null, null, null, orderBy, tLimit);
				{
					SQLiteQuery t_mQuery = (SQLiteQuery) Prism_Util.getField(c, "mQuery");
					String t_mSql = (String) Prism_Util.getField(t_mQuery, "mSql", SQLiteProgram.class.getName());
					LogPrint.printString_V("DB Log", "本次进度更新查询 TargetId: " + targetId + " HighLightFlag: " + highlightFlag + " 语句为："
							+ t_mSql);
					Log.v("cost", "本次进度更新查询,语句为：" + t_mSql);
				}
				// 读取
				if (c.moveToFirst()) {
					result.addAll(readDataList(c));
				}
				// 去除脏数据
				Array_Util.removeArrayListInvalidData(result, new Schedule_Bean());
				if (result.size() < 0) {
					result = null;
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	public void removeSameSchedule() {
		Cursor c = null;
		try {
			HashMap<Integer, HashSet<Integer>> tIdMap = new HashMap<Integer, HashSet<Integer>>();
			int p = 0;
			SQLiteDatabase tDb = getWDB();
			c = tDb.query(getTableName(), new String[] { Const_DB.TABLE_SCHEDULE_ID, Const_DB.TABLE_SCHEDULE_KEY }, null, null, null, null,
					null);
			if (c.moveToFirst()) {
				do {
					int tTargetId = -1;
					p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_ID);
					if (p > -1) {
						tTargetId = c.getInt(p);
					}

					int tKey = -1;
					p = c.getColumnIndex(Const_DB.TABLE_SCHEDULE_KEY);
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
							tDb.delete(getTableName(), Const_DB.TABLE_SCHEDULE_KEY + " is " + tArray[ii], null);
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
	public static SchdeuleManager getInstance(Context context) {
		if (instance == null) {
			synchronized (SchdeuleManager.class) {
				if (instance == null) {
					instance = new SchdeuleManager(context);
				}
			}
		}
		return instance;
	}

}

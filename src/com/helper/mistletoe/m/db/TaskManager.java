package com.helper.mistletoe.m.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.helper.mistletoe.m.db.impl.Task_DBService;
import com.helper.mistletoe.m.pojo.TaskList_Bean;
import com.helper.mistletoe.m.pojo.Task_Bean;
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
public class TaskManager extends Task_DBService {
	protected volatile static TaskManager instance;

	private TaskManager(Context context) {
		super(context);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void writeTaskList(int targetId, TaskList_Bean taskList) {
		try {
			for (Task_Bean b : taskList.getList()) {
				b.setLoc_TargetId(targetId);
			}
			String whereClause = Const_DB.TABLE_TASK_LOCTARGETID + " is '" + targetId + "'";
			try {
				getWDB().delete(getTableName(), whereClause, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			writeDataList(taskList.getList());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public TaskList_Bean readTaskList(int targetId) {
		TaskList_Bean result = new TaskList_Bean();
		ArrayList<Task_Bean> taskList=new ArrayList<Task_Bean>();
		TaskList_Bean resultTemp=new TaskList_Bean();
		Cursor c = null;
		try {
			String selection = Const_DB.TABLE_TASK_LOCTARGETID + " is '" + targetId + "'";
			c = getRDB().query(getTableName(), null, selection, null, null, null, Const_DB.TABLE_TASK_BEGIN_TIME + " " + "asc,"
					+Const_DB.TABLE_TASK_TASKID);
			resultTemp.setList(readDataList(c));
				for (int i = 0; i <resultTemp.getList().size(); i++) {
					Task_Bean task=new Task_Bean();
					task=resultTemp.getList().get(i);
					resultTemp.getList().remove(i);
					if (resultTemp.getList().contains(task)) {
						resultTemp.getList().add(i, task);
					}else {
						resultTemp.getList().add(i, task);
						taskList.add(task);
					}
				}
//         	// 去重
//			HashMap<Integer, Task_Bean> tHashMap = new HashMap<Integer, Task_Bean>();
//			for (Iterator<Task_Bean> iterator = result.getList().iterator(); iterator.hasNext();) {
//				Task_Bean temp = iterator.next();
//				if (temp.getTask_id() > 0) {
//					tHashMap.put(temp.getTask_id(), temp);
//				}
//			}
			result.getList().clear();
			result.getList().addAll(taskList);
		} catch (Exception e) {
			result = new TaskList_Bean();
			ExceptionHandle.ignoreException(e);
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	public void removeSameTask() {
		Cursor c = null;
		try {
			HashMap<Integer, HashSet<Integer>> tIdMap = new HashMap<Integer, HashSet<Integer>>();
			int p = 0;
			SQLiteDatabase tDb = getWDB();
			c = tDb.query(getTableName(), new String[] { Const_DB.TABLE_TASK_TASKID, Const_DB.TABLE_TASK_KEY }, null, null, null, null,
					null);
			if (c.moveToFirst()) {
				do {
					int tTargetId = -1;
					p = c.getColumnIndex(Const_DB.TABLE_TASK_TASKID);
					if (p > -1) {
						tTargetId = c.getInt(p);
					}

					int tKey = -1;
					p = c.getColumnIndex(Const_DB.TABLE_TASK_KEY);
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
							tDb.delete(getTableName(), Const_DB.TABLE_TASK_KEY + " is " + tArray[ii], null);
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
	public static TaskManager getInstance(Context context) {
		if (instance == null) {
			synchronized (TaskManager.class) {
				if (instance == null) {
					instance = new TaskManager(context);
				}
			}
		}
		return instance;
	}
}

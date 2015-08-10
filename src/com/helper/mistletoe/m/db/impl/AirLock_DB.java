package com.helper.mistletoe.m.db.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.content.Context;

import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.ExceptionHandle;

@SuppressLint("UseSparseArrays")
public class AirLock_DB {

	public static ArrayList<Helpers_Sub_Bean> select_helperList(Context context, Integer[] helperId) {
		ArrayList<Helpers_Sub_Bean> result = new ArrayList<Helpers_Sub_Bean>();
		try {
			result.addAll(select_helper(context, helperId).values());
		} catch (Exception e) {
			result = new ArrayList<Helpers_Sub_Bean>();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static ArrayList<Helpers_Sub_Bean> select_helperList(Context context, int[] helperId) {
		ArrayList<Helpers_Sub_Bean> result = new ArrayList<Helpers_Sub_Bean>();
		try {
			result.addAll(select_helper(context, helperId).values());
		} catch (Exception e) {
			result = new ArrayList<Helpers_Sub_Bean>();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static ArrayList<Helpers_Sub_Bean> select_helperList(Context context, ArrayList<Integer> helperId) {
		ArrayList<Helpers_Sub_Bean> result = new ArrayList<Helpers_Sub_Bean>();
		try {
			result.addAll(select_helper(context, helperId).values());
		} catch (Exception e) {
			result = new ArrayList<Helpers_Sub_Bean>();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static Helpers_Sub_Bean select_helper(Context context, int helperId) {
		Helpers_Sub_Bean result = new Helpers_Sub_Bean();
		try {
			// 转换为ArrayList
			ArrayList<Integer> tHelperIds = new ArrayList<Integer>();
			tHelperIds.add(helperId);
			// 查询
			HashMap<Integer, Helpers_Sub_Bean> tHelperList = new HashMap<Integer, Helpers_Sub_Bean>();
			tHelperList = select_helper(context, tHelperIds);
			for (Iterator<Helpers_Sub_Bean> iterator = tHelperList.values().iterator(); iterator.hasNext();) {
				Helpers_Sub_Bean tHelper = iterator.next();
				if (tHelper != null) {
					result = tHelper;
				}
			}
		} catch (Exception e) {
			result = new Helpers_Sub_Bean();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static HashMap<Integer, Helpers_Sub_Bean> select_helper(Context context, Integer[] helperId) {
		HashMap<Integer, Helpers_Sub_Bean> result = new HashMap<Integer, Helpers_Sub_Bean>();
		try {
			// 转换为ArrayList
			ArrayList<Integer> tHelperIds = new ArrayList<Integer>();
			tHelperIds.addAll(Arrays.asList(helperId));
			// 查询
			result = select_helper(context, tHelperIds);
		} catch (Exception e) {
			result = new HashMap<Integer, Helpers_Sub_Bean>();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static HashMap<Integer, Helpers_Sub_Bean> select_helper(Context context, int[] helperId) {
		HashMap<Integer, Helpers_Sub_Bean> result = new HashMap<Integer, Helpers_Sub_Bean>();
		try {
			// 转换为ArrayList
			ArrayList<Integer> tHelperIds = new ArrayList<Integer>();
			for (int i = 0; i < helperId.length; i++) {
				tHelperIds.add(helperId[i]);
			}
			// 查询
			result = select_helper(context, tHelperIds);
		} catch (Exception e) {
			result = new HashMap<Integer, Helpers_Sub_Bean>();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static HashMap<Integer, Helpers_Sub_Bean> select_helper(Context context, ArrayList<Integer> helperIds) {
		HashMap<Integer, Helpers_Sub_Bean> result = new HashMap<Integer, Helpers_Sub_Bean>();
		try {
			boolean isDataSafe = true;
			// 数据安检
			if (context == null) {
				isDataSafe = false;
			}
			if (helperIds == null) {
				isDataSafe = false;
			} else {
				ArrayList<Integer> tJustForRemove = new ArrayList<Integer>();
				tJustForRemove.add(null);
				helperIds.removeAll(tJustForRemove);
			}
			if (helperIds.size() < 1) {
				isDataSafe = false;
			}
			// 如果数据安全开始执行
			if (isDataSafe) {
				ArrayList<Helpers_Sub_Bean> tHelperList;
				// 从数据库中查出帮手
				Integer[] tTempId = new Integer[] {};
				tTempId = helperIds.<Integer> toArray(tTempId);
				tHelperList = new HelperManager().ReadGroupMemberListFromDB(context, tTempId);
				// 移除脏数据
				ArrayList<Helpers_Sub_Bean> tJustForRemove = new ArrayList<Helpers_Sub_Bean>();
				tJustForRemove.add(null);
				tHelperList.removeAll(tJustForRemove);
				// 如果Id列表中包含User，把User放入帮手中
				User_Bean user = new User_Bean();
				user.readData(context);
				user.setName("我");
				if (helperIds.contains((int) user.getUser_id())) {
					Helpers_Sub_Bean tHelper = new Helpers_Sub_Bean();
					user.transformDataTo(tHelper);
					tHelperList.add(tHelper);
				}
				// 格式化结果
				for (Iterator<Helpers_Sub_Bean> iterator = tHelperList.iterator(); iterator.hasNext();) {
					Helpers_Sub_Bean tHelper = iterator.next();
					int tHelperId = tHelper.getHelper_id();
					result.put(tHelperId, tHelper);
				}
			}
		} catch (Exception e) {
			result = new HashMap<Integer, Helpers_Sub_Bean>();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
package com.helper.mistletoe.m.db;

import java.util.ArrayList;

import com.helper.mistletoe.m.net.request.Find_GroupMember_NetRequest;
import com.helper.mistletoe.m.net.request.Find_Group_NetRequest;
import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.DBUtil;

import android.content.ContentValues;
import android.content.Context;

public class GroupManager extends DBManager {
	private static Find_Group_NetRequest group_netRequest;
	private Find_GroupMember_NetRequest netRequest;
	private static GroupManager instance = null;

	// 单例模式
	public static GroupManager getInstance() {
		if (null == instance) {
			synchronized (GroupManager.class) {
				if (instance == null) {
					instance = new GroupManager();
				}
			}
		}
		return instance;
	}

	// 批量插入group
	private void AddGroupListToDB(Context context, ArrayList<Group_Bean> groupList) {

		ArrayList<ContentValues> valuesList = new ArrayList<ContentValues>();
		GroupService groupService = new GroupServiceImpl(context.getApplicationContext());
		for (int i = 0; i < groupList.size(); i++) {
			ContentValues initialValues = new ContentValues();
			initialValues = DBUtil.CreateValues(groupList.get(i));
			valuesList.add(initialValues);
		}
		groupService.addGroupList(valuesList);

	}

	// 批量更新group
	private void UpdateGroupListToDB(Context context, ArrayList<Group_Bean> groupList) {
		ArrayList<ContentValues> valuesList = new ArrayList<ContentValues>();
		GroupService groupService = new GroupServiceImpl(context);
		for (int i = 0; i < groupList.size(); i++) {
			ContentValues initialValues = new ContentValues();
			initialValues = DBUtil.CreateValuesForUpdataGroup(groupList.get(i));// 不包括member_id
			valuesList.add(initialValues);
		}
		groupService.updateGroupList(valuesList);

	}

	// 获取last_updateTime
	public long getLastUpdateTime(Context context) {
		long last_updated_time = 0L;
		GroupService groupService = new GroupServiceImpl(context);
		last_updated_time = groupService.getLastUpdatedTime();
		return last_updated_time;
	}

	/**
	 * 根据status数组查找group
	 * 
	 * @param context
	 * @param status
	 * @return
	 */
	private ArrayList<Group_Bean> ReadDataFromDB(Context context, int[] status) {
		ArrayList<Group_Bean> groupList = new ArrayList<Group_Bean>();
		GroupService groupService = new GroupServiceImpl(context);
		groupList = groupService.getgroupList(status);
		return groupList;
	}

	// 根据last_updated_time从后台获取group并更新本地数据
	public Boolean FindGroupListFromNetByLastUpdateTime(Context context) {
		Boolean result = false;
		try {
			// 根据last_updated_time从后台获取helper并更新本地数据
			ArrayList<Group_Bean> groupList_service = new ArrayList<Group_Bean>();
			ArrayList<Group_Bean> groupList_db = new ArrayList<Group_Bean>();
			ArrayList<Group_Bean> groupList_Temp = new ArrayList<Group_Bean>();
			long group_last_updated_time = getLastUpdateTime(context);
			group_netRequest = new Find_Group_NetRequest(context);
			groupList_service = group_netRequest.doFindGroup(group_last_updated_time);
			if (groupList_service.size() > 0) {// 非空时，有增量
				result = true;// 有数据更新，需要刷新界面，置成true
				groupList_db = ReadDataFromDB(context, new int[] { 0, 1 });// group_status:0为删除，1为有效
				groupList_Temp.addAll(groupList_service);
				groupList_service.removeAll(groupList_db);
				groupList_Temp.removeAll(groupList_service);
				AddGroupListToDB(context, groupList_service);
				UpdateGroupListToDB(context, groupList_Temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 根据group_id获取groupList
	public ArrayList<Group_Bean> getGroupMemberByGroupId(Context context, Integer[] group_id) {
		ArrayList<Group_Bean> groupList = new ArrayList<Group_Bean>();
		GroupService groupService = new GroupServiceImpl(context);
		groupList = groupService.getGroupMemberByGroupId(group_id);
		return groupList;
	}

	// 根据group_id获取groupMember的ID[]
	public Integer[] getGroupMemberIdByGroupId(Context context, Integer[] group_id) {
		Integer[] memberId = null;
		ArrayList<Group_Bean> groupList = new ArrayList<Group_Bean>();
		ArrayList<Group_Bean> groupList_temp = new ArrayList<Group_Bean>();
		groupList_temp = getGroupMemberByGroupId(context, group_id);
		int len = groupList_temp.size();
		for (int i = 0; i < len; i++) {
			if (groupList_temp.get(i).getGroup_member_id() != -1) {
				groupList.add(groupList_temp.get(i));
			}
		}
		memberId = new Integer[groupList.size()];
		for (int i = 0; i < groupList.size(); i++) {
			memberId[i] = groupList.get(i).getGroup_member_id();
		}
		return memberId;
	}

	// 根据group_id获取group并更新数据库
	public Boolean FindGroupMemberFromNetByGroupId(Context context, Integer group_id) {
		Boolean result = false;
		try {
			ArrayList<Group_Bean> groupList = new ArrayList<Group_Bean>();
			netRequest = new Find_GroupMember_NetRequest(context);
			groupList = netRequest.doFindGroupById(group_id);

			if (groupList.size() > 0) {
				result = true;
				DeleteGroupToDBByGroupId(context, group_id); // 先删除
				AddGroupListToDB(context, groupList); // 后添加
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 根据group_id删除group
	public void DeleteGroupToDBByGroupId(Context context, Integer group_id) {
		GroupService groupService = new GroupServiceImpl(context);
		groupService.deleteGroupById(group_id);
	}

	// 根据group_id获取groupMember的HelperList
	public ArrayList<Helpers_Sub_Bean> ReadGroupMemberListFromDBByGroupId(Context context, Integer[] group_id) {
		ArrayList<Helpers_Sub_Bean> helperList = new ArrayList<Helpers_Sub_Bean>();
		Integer[] memberId = getGroupMemberIdByGroupId(context, group_id);
		HelperManager helperManager = new HelperManager();
		helperList = helperManager.ReadGroupMemberListFromDB(context, memberId);
		return helperList;
	}

	// 根据status获取groupList以便在界面上显示
	public ArrayList<Helpers_Sub_Bean> getShowDataListByStatus(Context context) {
		ArrayList<Helpers_Sub_Bean> ShowDataList = new ArrayList<Helpers_Sub_Bean>();
		GroupService groupService = new GroupServiceImpl(context);
		ShowDataList = groupService.getShowDataList(new Integer[] { 1 });
		return ShowDataList;
	}
}

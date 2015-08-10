package com.helper.mistletoe.m.db;

import java.util.ArrayList;

import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;

import android.content.ContentValues;

/**
 * @author 组数据库服务接口
 */
public interface GroupService {
	/**
	 * 添加groupList到数据库
	 * 
	 * @param values
	 * @return
	 * */
	public boolean addGroupList(ArrayList<ContentValues> list);

	/**
	 * 删除组
	 * 
	 * @param group_id
	 * @return
	 */
	public boolean deleteGroupById(Integer group_id);

	/**
	 * 更新groupList到数据库
	 * 
	 * @param values
	 * @return
	 * */
	public boolean updateGroupList(ArrayList<ContentValues> list);

	/**
	 * 获取groupList 在helper界面展示的所有组
	 * 
	 * @return
	 */
	public ArrayList<Group_Bean> getgroupList(int[] status);

	/**
	 * 获取组成员 需要关联helper表
	 * 
	 * @param group_id
	 * @return
	 */
	public ArrayList<Group_Bean> getGroupMemberByGroupId(Integer[] group_id);

	/**
	 * 获取ShowDataList 在helper界面展示的所有组
	 * 
	 * @param values
	 * @return
	 * */
	public ArrayList<Helpers_Sub_Bean> getShowDataList(Integer[] status);

	/**
	 * 获取最新更新时间
	 * 
	 * @return
	 */
	public long getLastUpdatedTime();

}

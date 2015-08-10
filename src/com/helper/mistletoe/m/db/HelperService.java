package com.helper.mistletoe.m.db;

import java.util.ArrayList;

import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;

import android.content.ContentValues;

/**
 * @author 帮手数据库服务接口
 */
public interface HelperService {
	/**
	 * 添加helperList到数据库
	 * 
	 * @param values
	 * @return
	 * */
	public boolean addHelperList(ArrayList<ContentValues> list);

	/**
	 * 更新helperList信息到数据库
	 * 
	 * @param values
	 *            信息内容
	 * @return
	 */
	public boolean updateHelperListInformationById(ArrayList<ContentValues> list);

	/**
	 * 获取helperList
	 * 
	 * @param is
	 *            ,帮手，黑名单,通讯录，新帮手，待验证帮手等
	 * @return
	 */
	public ArrayList<Helpers_Sub_Bean> getHelperListByStatus(int[] is);

	/**
	 * 获取Helper
	 * 
	 * @param id
	 * @return
	 */
	public Helpers_Sub_Bean getHelperById(String id);

	/**
	 * 更新Helper的status
	 * 
	 * @param id
	 * @return
	 */
	public boolean updateHelperStatusById(String id, String status);

	/**
	 * 获取Helper的最新更新时间
	 * 
	 * @return
	 */
	public long getLastUpdatedTime();

	/**
	 * 获取HelperList
	 * 
	 * @param id
	 * @return
	 */
	public ArrayList<Helpers_Sub_Bean> getHelperListByMemberId(Integer[] memberId);

	/**
	 * 获取Helper
	 * 
	 * @param helper_id
	 * @return
	 */
	public Helpers_Sub_Bean getHelperById(Integer helper_id);

	/**
	 * 更新Helper通过Id
	 * 
	 * @param
	 * @return
	 */
	public Boolean updateHelperById(ContentValues initialValues);

	/**
	 * 添加Helper通过Id
	 * 
	 * @param
	 * @return
	 */
	public Boolean addHelperById(ContentValues initialValues);
}

package com.helper.mistletoe.m.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import com.helper.mistletoe.m.net.request.Find_Helper_NetRequest;
import com.helper.mistletoe.m.net.request.Get_Helper_By_Id_NetRequest;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.DBUtil;

public class HelperManager extends DBManager {
	private static HelperManager instance = null;

	// 单例模式
	public static HelperManager getInstance() {
		if (null == instance) {
			synchronized (HelperManager.class) {
				if (instance == null) {
					instance = new HelperManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 把获取到的helper添加到本地数据库中，所有属性
	 * 
	 * @param helperList
	 */
	public void AddHelperListToDB(Context context, ArrayList<Helpers_Sub_Bean> helperList) {
		ArrayList<ContentValues> valuesList = new ArrayList<ContentValues>();
		HelperService helperService = new HelperServiceImpl(context);
		for (int i = 0; i < helperList.size(); i++) {
			ContentValues initialValues = new ContentValues();
			initialValues = DBUtil.CreateValues(helperList.get(i));
			valuesList.add(initialValues);
		}
		helperService.addHelperList(valuesList);
	}

	/**
	 * 根据status数组查找helper
	 * 
	 * @param context
	 * @param status
	 * @return
	 */
	public ArrayList<Helpers_Sub_Bean> ReadHelperFromDBByStatus(Context context, int[] status) {
		ArrayList<Helpers_Sub_Bean> helperList = new ArrayList<Helpers_Sub_Bean>();
		HelperService helperService = new HelperServiceImpl(context);
		helperList = helperService.getHelperListByStatus(status);
		return helperList;
	}

	/**
	 * 更新helperList
	 * 
	 * @param context
	 * @param helperList
	 */
	public void UpdateHelperListToDB(Context context, ArrayList<Helpers_Sub_Bean> helperList) {
		ArrayList<ContentValues> valuesList = new ArrayList<ContentValues>();
		HelperService helperService = new HelperServiceImpl(context);
		for (int i = 0; i < helperList.size(); i++) {
			ContentValues initialValues = new ContentValues();
			initialValues = DBUtil.CreateValuesForUpdateHelperList(helperList.get(i));
			valuesList.add(initialValues);
		}
		helperService.updateHelperListInformationById(valuesList);

	}

	public long GetLastUpdatedTimeFromDB(Context context) {
		long last_updated_time = 0L;
		HelperService helperService = new HelperServiceImpl(context);
		last_updated_time = helperService.getLastUpdatedTime();
		return last_updated_time;
	}

	/**
	 * 获取组与帮手，在helperFragment中显示
	 * 
	 * @param context
	 * @return
	 */
	public ArrayList<Helpers_Sub_Bean> ReadHelperAndGroupDataFromDB(Context context) {
		ArrayList<Helpers_Sub_Bean> ShowDataList = new ArrayList<Helpers_Sub_Bean>();
		GroupManager groupManager = new GroupManager();
		ShowDataList = groupManager.getShowDataListByStatus(context);
		HelperService helperService = new HelperServiceImpl(context);
		ShowDataList.addAll(helperService.getHelperListByStatus(new int[] { 3, 9 }));
		return ShowDataList;
	}

	// 根据last_updated_time从后台获取helper并更新本地数据
	public Boolean FindHelperFromNetByLastUpdataTime(Context context) {
		Boolean result = false;
		try {
			ArrayList<Helpers_Sub_Bean> helperList_service = new ArrayList<Helpers_Sub_Bean>();
			ArrayList<Helpers_Sub_Bean> helperList_db = new ArrayList<Helpers_Sub_Bean>();
			ArrayList<Helpers_Sub_Bean> helperList_Temp = new ArrayList<Helpers_Sub_Bean>();
			// 获取helper的last_updated_time
			long last_updated_time = GetLastUpdatedTimeFromDB(context);
			Find_Helper_NetRequest helper_netRequest = new Find_Helper_NetRequest(context);
			helperList_service = helper_netRequest.doFindHelper(last_updated_time);
			if (helperList_service.size() > 0) {// 非空时，有增量
				result = true;// 有数据更新，需要刷新界面，置成true
				helperList_db = ReadHelperFromDBByStatus(context, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });
				helperList_Temp.addAll(helperList_service);
				helperList_service.removeAll(helperList_db);
				helperList_Temp.removeAll(helperList_service);
				AddHelperListToDB(context, helperList_service);
				UpdateHelperListToDB(context, helperList_Temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 根据last_updated_time从后台获取helper和group并更新本地数据
	public Boolean FindHelperAndGroupFromNetByLastUpdateTime(Context context) {
		Boolean result = false;
		// 根据last_updated_time从后台获取helper并更新本地数据
		Boolean helperResult = FindHelperFromNetByLastUpdataTime(context);
		GroupManager groupManager = new GroupManager();
		// 根据last_updated_time从后台获取group并更新本地数据
		Boolean groupResult = groupManager.FindGroupListFromNetByLastUpdateTime(context);
		if (helperResult || groupResult) {
			result = true;
		}
		return result;
	}

	// 根据memberID[],获取helperList
	public ArrayList<Helpers_Sub_Bean> ReadGroupMemberListFromDB(Context context, Integer[] memberId) {
		ArrayList<Helpers_Sub_Bean> helperList = new ArrayList<Helpers_Sub_Bean>();
		if (memberId.length > 0) {
			HelperService helperService = new HelperServiceImpl(context);
			helperList = helperService.getHelperListByMemberId(memberId);
		}
		return helperList;
	}

	public ArrayList<Helpers_Sub_Bean> ReadHelperAndGroupDataForSeceteFromDB(Context context) {
		ArrayList<Helpers_Sub_Bean> ShowDataList = new ArrayList<Helpers_Sub_Bean>();
		GroupService groupService = new GroupServiceImpl(context);
		ShowDataList = groupService.getShowDataList(new Integer[] { 1 });
		HelperService helperService = new HelperServiceImpl(context);
		ShowDataList.addAll(helperService.getHelperListByStatus(new int[] { 3, 9 }));
		return ShowDataList;
	}

	// 根据HelperId,获取helper
	public Helpers_Sub_Bean ReadHelperFromDBById(Context context, Integer helper_id) {

		Helpers_Sub_Bean helpers_Sub_Bean = new Helpers_Sub_Bean();
		HelperService helperService = new HelperServiceImpl(context);
		helpers_Sub_Bean = helperService.getHelperById(helper_id);
		if (helpers_Sub_Bean.getHelper_id() == -1) {
			Get_Helper_By_Id_NetRequest netRequest = new Get_Helper_By_Id_NetRequest(context);
			try {
				helpers_Sub_Bean = netRequest.doGetHelperById(helper_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return helpers_Sub_Bean;
	}

	public void UpdateHelperToDBById(Context context, Helpers_Sub_Bean result) {
		HelperService helperService = new HelperServiceImpl(context);
		ContentValues initialValues = new ContentValues();
		initialValues = DBUtil.CreateValuesForUpdateHelper(result);
		Boolean isSucceed = helperService.updateHelperById(initialValues);
		if (!isSucceed) {// 如果更新失败，认为数据库不存在此项数据，添加到数据库中
			helperService.addHelperById(initialValues);
		}

	}

	public ArrayList<Helpers_Sub_Bean> ReadGroupMemberList(Context context, Integer[] helperId) {
		ArrayList<Helpers_Sub_Bean> helperList = new ArrayList<Helpers_Sub_Bean>();
		for (int i = 0; i < helperId.length; i++) {
			Helpers_Sub_Bean helper = new Helpers_Sub_Bean();
			helper = ReadHelperFromDBById(context, helperId[i]);
			helperList.add(helper);
		}
		return helperList;
	}

	public Integer Identification(Context context, Integer helperId) {
		Integer ID = -1;
		try {
			User_Bean user = new User_Bean();
			user.readData(context);
			Helpers_Sub_Bean helpers_Sub_Bean = new Helpers_Sub_Bean();
			HelperService helperService = new HelperServiceImpl(context);
			helpers_Sub_Bean = helperService.getHelperById(helperId);
			if (helperId == user.getUser_id()) {
				ID = 1;// 自己
			} else if (helpers_Sub_Bean.getHelper_id() == -1) {
				ID = 3;// 不是帮手
			} else {
				ID = 2;// 帮手
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ID;
	}
}

package com.helper.mistletoe.m.db;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;

import com.helper.mistletoe.m.net.request.Get_CostTag_NetRequest;
import com.helper.mistletoe.m.net.request.Get_Helper_By_Id_NetRequest;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.NoteTag_Pojo;
import com.helper.mistletoe.util.DBUtil;

public class CostTagManager extends DBManager {
	private static CostTagManager instance = null;

	// 单例模式
	public static CostTagManager getInstance() {
		if (null == instance) {
			synchronized (CostTagManager.class) {
				if (instance == null) {
					instance = new CostTagManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 添加到本地数据库中，所有属性
	 * 
	 * @param costTagList
	 */
	public void AddCostTagListToDB(Context context, ArrayList<NoteTag_Pojo> costTagList) {
		ArrayList<ContentValues> valuesList = new ArrayList<ContentValues>();
		CostTagService costTagService = new CostTagServiceImpl(context);
		for (int i = 0; i < costTagList.size(); i++) {
			ContentValues initialValues = new ContentValues();
			initialValues = DBUtil.CreateValues(costTagList.get(i));
			valuesList.add(initialValues);
		}
		costTagService.addTagList(valuesList);
	}

	/**
	 * 根据targetId数组查找Tag
	 * 
	 * @param context
	 * @param targetId
	 * @return
	 */
	public ArrayList<NoteTag_Pojo> ReadCostTagFromDBByTargetId(Context context, Integer[] targetId) {
		ArrayList<NoteTag_Pojo> costTagList = new ArrayList<NoteTag_Pojo>();
		CostTagService costTagService = new CostTagServiceImpl(context);
		costTagList = costTagService.getTagListByTargetId(targetId);
		return costTagList;
	}
	/**
	 * 查找所有Tag
	 * 
	 * @param context
	 * @param targetId
	 * @return
	 */
	public ArrayList<NoteTag_Pojo> ReadCostTagFromDB(Context context) {
		ArrayList<NoteTag_Pojo> costTagList = new ArrayList<NoteTag_Pojo>();
		CostTagService costTagService = new CostTagServiceImpl(context);
		costTagList = costTagService.getTagList();
		return costTagList;
	}
	// 根据id更新本地数据
	public Boolean UpdateCostTagByID(Context context,Integer targetId) {
		Boolean result = false;
		try {
			ArrayList<NoteTag_Pojo> costTagList_service = new ArrayList<NoteTag_Pojo>();
			ArrayList<NoteTag_Pojo> costTagList_temp = new ArrayList<NoteTag_Pojo>();
			ArrayList<NoteTag_Pojo> costTagList_db = new ArrayList<NoteTag_Pojo>();
			Get_CostTag_NetRequest netRequest = new Get_CostTag_NetRequest(context);
			costTagList_service = netRequest.doRequest(targetId);
			if (targetId>0) {
				costTagList_db = ReadCostTagFromDBByTargetId(context, new Integer[] {targetId,0});	
			}else {
				costTagList_db = ReadCostTagFromDBByTargetId(context, new Integer[] {0});
			}
			if (costTagList_service.size() > 0) {// 非空时，有增量
				result = true;// 有数据更新，需要刷新界面，置成true
				HashMap<Integer, NoteTag_Pojo> dbMap=new HashMap<Integer, NoteTag_Pojo>();
				for (int i = 0; i < costTagList_db.size(); i++) {
					NoteTag_Pojo tag =	costTagList_db.get(i);
					Integer tagId   =costTagList_db.get(i).getId();
					dbMap.put(tagId, tag);
				}
				for (int i = 0; i < costTagList_service.size(); i++) {
					NoteTag_Pojo tag =	costTagList_service.get(i);
					Integer tagId   =costTagList_service.get(i).getId();
					if (!dbMap.containsKey(tagId)) {
						costTagList_temp.add(tag);
					}
				}
				AddCostTagListToDB(context, costTagList_temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
}

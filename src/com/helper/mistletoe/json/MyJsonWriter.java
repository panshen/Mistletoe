package com.helper.mistletoe.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;

public class MyJsonWriter {

	// 上传通讯录
	public static String getJsonDataForUploadContact(
			ArrayList<Helpers_Sub_Bean> helperList) {
		// {
		// "contacts": [
		// {
		// "name": "mjy",
		// "type": 0,
		// "info": "18500540178"
		// }
		// ]
		// }
		String data_upload_contact = null;
		try {
			JSONObject jsData = new JSONObject();
			JSONArray contacts = new JSONArray();
			for (int i = 0; i < helperList.size(); i++) {
				Helpers_Sub_Bean helper = helperList.get(i);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("name", helper.getHelper_readygo_name());
				jsonObject.put("type", helper.getHelper_account_type());
				jsonObject.put("info", helper.getHelper_account());
				contacts.put(jsonObject);
			}
			jsData.put("contacts", contacts);
			data_upload_contact = jsData.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_upload_contact;
	}

	// 修改user
	public static String getJsonDataForUpdatePublicHelper(User_Bean user) {
		// {
		// "name": "",// 姓名
		// "gender": 0, //性别 未知/男/女
		// "country": "", //国家
		// "city" : "", //城市
		// "company": "", //公司
		// "title": "", //职务
		// "address": "", //地址
		// "zip": "", //邮编
		// "mobile": "", //手机
		// "tel": "", //座机
		// "fax":, //传真
		// "webpage": "", //主页
		// "email": "", //邮件
		// "sign": "", //签名
		// "avatar_file_id":0, //头像图片id
		// "memo":"" //备注
		// "helper_verification": 1, //加帮手是否需要验证
		// "accept_push_msg": 1 //是否接收推送消息
		// }
		String data_updata_user = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", user.getName());
			jsonObject.put("gender", user.getGender());
			jsonObject.put("country", user.getCountry());
			jsonObject.put("city", user.getCity());
			jsonObject.put("company", user.company);
			jsonObject.put("title", user.getTitle());
			jsonObject.put("address", user.getAddress());
			jsonObject.put("zip", user.getZip());
			jsonObject.put("mobile", user.getMobile());
			jsonObject.put("tel", user.getTel());
			jsonObject.put("fax", user.getFax());
			jsonObject.put("webpage", user.getWebpage());
			jsonObject.put("email", user.getEmail());
			jsonObject.put("sign", user.getSign());
			if (user.getAvatar_file_id() > 0) {
				jsonObject.put("avatar_file_id", user.getAvatar_file_id());
			}
			jsonObject.put("memo", user.getMemo());
			jsonObject.put("helper_verification",
					user.getLoc_AddHelperVerification());
			jsonObject.put("accept_push_msg", user.getLoc_AcceptPush());
			data_updata_user = jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return data_updata_user;
	}

	// 获取helperList
	public static String getJsonDataForFindHelper(Long last_updated_time) {
		String data_helper_find = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("count", 10000);
			jsonObject.put("last_updated_time", last_updated_time);
			data_helper_find = jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return data_helper_find;
	}

	// ---------------------------------------------------->
	public static String getJsonDataForAddHelperById(String msg, Integer id) {
		// {
		// "msg": "我是刘玉海",
		// "dst_id": "3",
		// }
		String data_addHelper_byId = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("msg", msg);
			jsonObject.put("dst_id", id);
			data_addHelper_byId = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_addHelper_byId;
	}

	public static String getJsonDataForFindPublicHelper(int start_id) {
		// {
		// "start_id": 0,
		// "count": 2
		// }
		String data_find_publicHelper = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("start_id", start_id);
			jsonObject.put("count", 50);
			data_find_publicHelper = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_find_publicHelper;
	}

	public static String getJsonDataForGetUser(String id) {
		// {"user_id":1}
		String data_get_user = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("user_id", id);
			data_get_user = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_get_user;
	}

	public static String getJsonDataForFindPublicHelperDesc(Integer id) {
		// {"user_id":1}
		String data_find_public_helper_desc = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("user_id", id);
			data_find_public_helper_desc = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_find_public_helper_desc;
	}

	public static String getJsonDataForFindPublicHelperByKeyword(String keyword) {
		// {"key_word":1}
		String data_find_public_helper_by_keyword = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("key_word", keyword);
			data_find_public_helper_by_keyword = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_find_public_helper_by_keyword;
	}

	public static String getJsonDataForUpdateRelationship(Integer helper_id,
			Integer status, String memo_name) {
		// { "dst_id":"3","status":"2", "memo_name":"xiaoluo"}
		String data_update_relationship = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("dst_id", helper_id);
			jsonObject.put("status", status);
			jsonObject.put("memo_name", memo_name);
			data_update_relationship = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_update_relationship;
	}

	public static String getJsonDataForFindHelperByAccount(String account) {
		// {"account": "18611697407"}
		String data_find_helper_byAccount = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("account", account);
			data_find_helper_byAccount = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_find_helper_byAccount;
	}

	public static String getJsonDataForCreateGroup(Group_Bean group) {
		// {
		// "name": "我是刘玉海",
		// "memo": "我是刘玉海",
		// "members": [3,4],
		// }
		String data_addHelper_byId = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", group.getGroup_name());
			jsonObject.put("memo", group.getGroup_describe());
			jsonObject
					.put("members", new JSONArray(group.getGroup_memberIds()));
			data_addHelper_byId = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_addHelper_byId;
	}

	public static String getJsonDataForUpdateGroupMembers(Group_Bean group) {
		// {"grp_id":"1", "members":[1,3]}
		String data_update_group_members = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("grp_id", group.getGroup_id());
			jsonObject
					.put("members", new JSONArray(group.getGroup_memberIds()));
			data_update_group_members = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_update_group_members;
	}

	public static String getJsonDataForUpdateGroup(Group_Bean group) {
		// {"grp_id":"7", "name":"readygo","memo":"公司"}
		String data_update_group = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("grp_id", group.getGroup_id());
			jsonObject.put("name", group.getGroup_name());
			jsonObject.put("memo", group.getGroup_describe());
			data_update_group = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_update_group;
	}

	public static String getJsonDataForDeleteGroup(Integer group_id) {
		// {"grp_id":"66"}
		String data_delete_group = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("grp_id", group_id);
			data_delete_group = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_delete_group;
	}

	public static String getJsonDataForFindGroup(Long last_updated_time) {
		// {"last_updated_time":"0", "count":"10"}
		String data_find_group = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("last_updated_time", last_updated_time);
			jsonObject.put("count", 100);
			data_find_group = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_find_group;
	}

	public static String getJsonDataForFindGroupById(Integer group_id) {
		// {"grp_id":"1"}
		String data_find_group_byId = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("grp_id", group_id);
			data_find_group_byId = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_find_group_byId;
	}

	public static String getJsonDataForUpdatePublicHelper(String memo,
			String status) {
		// {"memo":"我是您的帮手","status": 1}
		// “memo”：“您的好帮手”,//自荐时的备注，可以为空
		// “status”: 1,//自荐为公众帮手
		// "status": 2,//取消公共帮手
		String data_update_public_helper = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("memo", memo);
			jsonObject.put("status", status);
			data_update_public_helper = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_update_public_helper;
	}

	public static String getJsonDataForAddFeedback(String word) {
		// {"feedback":"very good"}
		String data_add_feedback = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("feedback", word);
			data_add_feedback = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_add_feedback;
	}

	// 踢出设备
	public static String getJsonDataForKickDecive(Integer id) {
		// {"device_id":1}
		String data_kick_decive = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("device_id", id);
			data_kick_decive = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_kick_decive;
	}

	public static String getJsonDataForGetLastVersion(String platform) {
		// {"platform":1}
		String data_get_last_version = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("platform", platform);
			data_get_last_version = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_get_last_version;
	}

	public static String getJsonDataForCreateTarget(String[] strings) {
		// TODO Auto-generated method stub
		JSONArray members = new JSONArray();
		for (String a : strings) {
			members.put(Integer.valueOf(a));
		}
		return members.toString();
	}

	public static String getJsonDataForGroup(List<Integer> seleted) {
		// TODO Auto-generated method stub
		JSONArray members = new JSONArray();
		for (Integer a : seleted) {
			members.put(a);
		}
		return members.toString();
	}

	public static String getJsonDataForActionDuang(Integer id) {
		String data_action_duang = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("dst_user_id", id);
			data_action_duang = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_action_duang;
	}

	public static String getJsonDataForActionDuang(ArrayList<Integer> ids) {
		String data_action_duang = null;
		try {
			JSONObject jsonObject = new JSONObject();
			// jsonObject.put("dst_user_ids", id);
			String s = getJsonDataForGroup(ids);
			jsonObject.put("dst_user_ids", new JSONArray(s));
			data_action_duang = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_action_duang;
	}

	public static String getJsonDataForGetHelperById(Integer id) {
		// {"user_id":1}
		String data_get_helper = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("user_id", id);
			data_get_helper = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_get_helper;
	}

	public static String getJsonDataForFindPublicTarget(int page_index, int page_size, String keyword) {
		//int page_index,int page_size,String keyword
		String data_find_public_target = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("page_index", page_index);
			jsonObject.put("page_size", page_size);
			jsonObject.put("keyword", keyword);
			data_find_public_target = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_find_public_target;
	}

	public static String getJsonDataForTagetView(Integer target_id) {
		String data_target_view = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("target_id", target_id);
			data_target_view = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_target_view;
	}

	public static String getJsonDataForTagetAttitude(Integer target_id) {
		String data_target_attitude = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("target_id", target_id);
			data_target_attitude = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_target_attitude;
	}

	public static String getJsonDataForGenerateExcelByTargetId(Integer target_Id, String email) {
		String data_generateExcel_byTargetId = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("target_id", target_Id);
			jsonObject.put("email", email);
			data_generateExcel_byTargetId = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_generateExcel_byTargetId;
	}
	public static String getJsonDataForTargetShareByTargetId(Integer target_Id) {
		String shareTarget_byTargetId = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("target_id", target_Id);
			shareTarget_byTargetId = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return shareTarget_byTargetId;
	}

	public static String getJsonDataForAddCostTagByTargetId(Integer target_Id, String tag) {
		String data_addCostTag_byTargetId = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("target_id", target_Id);
			jsonObject.put("tag", tag);
			data_addCostTag_byTargetId = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data_addCostTag_byTargetId;
	}
}

package com.helper.mistletoe.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.NoteTag_Pojo;
import com.helper.mistletoe.m.pojo.Public_Bean;
import com.helper.mistletoe.m.pojo.Result_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.Template_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;

public class MyJsonReader {

	// 解析通过getuser获取的user
	public static User_Bean getJsonDataForGetUser(String res, User_Bean user) {
		User_Bean user_json = new User_Bean();
		Gson gson = new Gson();
		user_json = gson.fromJson(res, User_Bean.class);// 解析出的user，
		// 为防止丢失原有数据
		user.setName(user_json.getName());
		user.setPy_name(user_json.getPy_name());
		user.setGender(user_json.getGender());
		user.setCountry(user_json.getCountry());
		user.setCity(user_json.getCity());
		user.setCompany(user_json.getCompany());
		user.setTitle(user_json.getTitle());
		user.setAddress(user_json.getAddress());
		user.setZip(user_json.getZip());
		user.setMobile(user_json.getMobile());
		user.setTel(user_json.getTel());
		user.setFax(user_json.getFax());
		user.setWebpage(user_json.getWebpage());
		user.setEmail(user_json.getEmail());
		user.setSign(user_json.getSign());
		user.setAvatar_file_id(user_json.getAvatar_file_id());
		user.setMemo(user_json.getMemo());
		user.setLoc_AddHelperVerification(user_json.getLoc_AddHelperVerification());
		user.setLoc_AcceptPush(user_json.getLoc_AcceptPush());
		user.setLoc_Target_count(user_json.getLoc_Target_count());
		user.setLoc_Helper_count(user_json.getLoc_Helper_count());
		user.setLoc_OpenMe(user_json.getLoc_OpenMe());
		user.setLoc_coin_count(user_json.getLoc_coin_count());
		return user;
	}

	// 解析通过FindHelper获取的helper
	public static ArrayList<Helpers_Sub_Bean> getJsonDataForFindHelper(String res) {
		ArrayList<Helpers_Sub_Bean> helper_list = new ArrayList<Helpers_Sub_Bean>();
		Gson gson = new Gson();
		try {
			JSONArray jsonArray = new JSONArray(res);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				Helpers_Sub_Bean helper_bean = new Helpers_Sub_Bean();
				helper_bean = gson.fromJson(json.toString(), Helpers_Sub_Bean.class);// 解析出的helper，
				helper_list.add(helper_bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return helper_list;
	}

	// 解析通过FindGroup获取的group
	public static ArrayList<Group_Bean> getJsonDataForFindGroup(String res) {
		ArrayList<Group_Bean> group_list = new ArrayList<Group_Bean>();
		Gson gson = new Gson();
		try {
			JSONArray jsonArray = new JSONArray(res);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				Group_Bean group_bean = new Group_Bean();
				group_bean = gson.fromJson(json.toString(), Group_Bean.class);
				group_list.add(group_bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return group_list;
	}

	// 解析通过FindGroupById获取的group
	public static ArrayList<Group_Bean> getJsonDataForFindGroupById(String res) {
		ArrayList<Group_Bean> group_list = new ArrayList<Group_Bean>();
		try {
			JSONObject jsonObject = new JSONObject(res);
			JSONArray jsonArray = new JSONArray(jsonObject.getString("members"));
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				Group_Bean group = new Group_Bean();
				group.setGroup_id(jsonObject.getInt("grp_id"));
				group.setGroup_name(jsonObject.getString("name"));
				group.setGroup_describe(jsonObject.getString("memo"));
				group.setGroup_status(jsonObject.getInt("status"));
				group.setGroup_create_time(jsonObject.getLong("create_time"));
				group.setGroup_last_update_time(jsonObject.getLong("last_updated_time"));
				group.setGroup_member_id(json.getInt("user_id"));
				group_list.add(group);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return group_list;
	}

	// 解析通过FindPublicHelper获取的public_bean
	public static ArrayList<Public_Bean> getJsonDataForFindPublicHelper(String res) {
		ArrayList<Public_Bean> public_list = new ArrayList<Public_Bean>();
		Gson gson = new Gson();
		try {
			JSONArray jsonArray = new JSONArray(res);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				Public_Bean public_Bean = new Public_Bean();
				public_Bean = gson.fromJson(json.toString(), Public_Bean.class);
				public_list.add(public_Bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return public_list;
	}

	// 解析通过FindPublicHelperDesc获取的public_bean
	public static Public_Bean getJsonDataForFindPublicHelperDesc(String res) {
		// TODO Auto-generated method stub
		Public_Bean public_Bean = new Public_Bean();
		Gson gson = new Gson();
		public_Bean = gson.fromJson(res, Public_Bean.class);
		return public_Bean;
	}

	// 解析通过FindPublicHelperByKeyword获取的public_bean
	public static ArrayList<Public_Bean> getJsonDataForFindPublicHelperByKeyword(String res) {
		ArrayList<Public_Bean> public_list = new ArrayList<Public_Bean>();
		Gson gson = new Gson();
		try {
			JSONArray jsonArray = new JSONArray(res);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				Public_Bean public_Bean = new Public_Bean();
				public_Bean = gson.fromJson(json.toString(), Public_Bean.class);
				public_list.add(public_Bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return public_list;
	}

	// // /------------------------------------------

	// 解析通过FindHelperByAccounte获取的helper
	public static Helpers_Sub_Bean getJsonDataForFindHelperByAccount(String res) {
		Helpers_Sub_Bean helpers_Sub_Bean = new Helpers_Sub_Bean();
		Gson gson = new Gson();
		helpers_Sub_Bean = gson.fromJson(res, Helpers_Sub_Bean.class);// 解析出的helper，
		return helpers_Sub_Bean;
	}

	// 解析通过FindDevice获取的Device
	public static ArrayList<Result_Bean> getJsonDataForFindDevice(String res) {
		ArrayList<Result_Bean> device_list = new ArrayList<Result_Bean>();
		try {
			JSONArray jsonArray = new JSONArray(res);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				Result_Bean device = new Result_Bean();
				Gson gson = new Gson();
				device = gson.fromJson(json.toString(), Result_Bean.class);// 解析出的device，
				if (device.getStatus() == 2 || device.getStatus() == 5) {
					device_list.add(device);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return device_list;
	}

	public static Result_Bean getJsonDataForGetLastVersion(String res) {
		Result_Bean result_Bean=new Result_Bean();
		try {
			JSONObject json=new JSONObject(res);
			Gson gson = new Gson();
			result_Bean = gson.fromJson(json.toString(), Result_Bean.class);// 解析出的result_Bean，
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result_Bean;
	}

	// 解析通过FindHelperByAccounte获取的helper
	public static Helpers_Sub_Bean getJsonDataForGetHelperById(String res) {
		Helpers_Sub_Bean helpers_Sub_Bean = new Helpers_Sub_Bean();
		Gson gson = new Gson();
		helpers_Sub_Bean = gson.fromJson(res, Helpers_Sub_Bean.class);// 解析出的helper，
		return helpers_Sub_Bean;
	}

	public static ArrayList<Target_Bean> getJsonDataForFindPublicTarget(String res) {
		ArrayList<Target_Bean> public_list = new ArrayList<Target_Bean>();
		Gson gson = new Gson();
		try {
			JSONArray jsonArray = new JSONArray(res);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				Target_Bean target_Bean = new Target_Bean();
				target_Bean = gson.fromJson(json.toString(), Target_Bean.class);
				public_list.add(target_Bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return public_list;
	}

	public static ArrayList<Template_Bean> getJsonDataForFindTargetTemlate(String res) {
		ArrayList<Template_Bean> Templates =new ArrayList<Template_Bean>();
		try {
			JSONArray jsonArray = new JSONArray(res);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				Template_Bean template_Bean = new Template_Bean();
				if (json.has("task")) {
				JSONArray s = new JSONArray(json.getString("task"));
				template_Bean.setTemplate_task(s.toString());
				}
				if (json.has("name")) {
				template_Bean.setTemplate_name(json.getString("name"));
				}
				if (json.has("pic")) {
					template_Bean.setTemplate_pic(json.getString("pic"));	
				}
				
				Templates.add(template_Bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Templates;
	}

	public static ArrayList<NoteTag_Pojo> getJsonDataForGetCostTag(String tResc) {
		ArrayList<NoteTag_Pojo> tag_list = new ArrayList<NoteTag_Pojo>();
		Gson gson = new Gson();
		try {
			JSONArray jsonArray = new JSONArray(tResc);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				NoteTag_Pojo tag = new NoteTag_Pojo();
				tag = gson.fromJson(json.toString(), NoteTag_Pojo.class);
				tag_list.add(tag);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tag_list;
	}
}

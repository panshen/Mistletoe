package com.helper.mistletoe.m.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.sp.base.Base_sp;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * Created by stemp1234 on 15/3/11.
 */
public class User_sp extends Base_sp<User_Bean> {

	public User_sp(Context context) {
		super(context, "user_sp");
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void read(User_Bean bean) throws Exception {
		try {
			// 安全检查
			if (bean == null) {
				bean = new User_Bean();
			}

			if (getSP().contains("user_id")) {
				bean.setUser_id(getSP().getInt("user_id", -1));
			}
			if (getSP().contains("name")) {
				bean.setName(getSP().getString("name", ""));
			}
			if (getSP().contains("gender")) {
				bean.setGender(getSP().getInt("gender", -1));
			}
			if (getSP().contains("country")) {
				bean.setCountry(getSP().getString("country", ""));
			}
			if (getSP().contains("city")) {
				bean.setCity(getSP().getString("city", ""));
			}
			if (getSP().contains("company")) {
				bean.setCompany(getSP().getString("company", ""));
			}
			if (getSP().contains("title")) {
				bean.setTitle(getSP().getString("title", ""));
			}
			if (getSP().contains("address")) {
				bean.setAddress(getSP().getString("address", ""));
			}
			if (getSP().contains("zip")) {
				bean.setZip(getSP().getString("zip", ""));
			}
			if (getSP().contains("mobile")) {
				bean.setMobile(getSP().getString("mobile", ""));
			}
			if (getSP().contains("tel")) {
				bean.setTel(getSP().getString("tel", ""));
			}
			if (getSP().contains("fax")) {
				bean.setFax(getSP().getString("fax", ""));
			}
			if (getSP().contains("webpage")) {
				bean.setWebpage(getSP().getString("webpage", ""));
			}
			if (getSP().contains("email")) {
				bean.setEmail(getSP().getString("email", ""));
			}
			if (getSP().contains("sign")) {
				bean.setSign(getSP().getString("sign", ""));
			}
			if (getSP().contains("avatar_file_id")) {
				bean.setAvatar_file_id(getSP().getInt("avatar_file_id", -1));
			}
			if (getSP().contains("memo")) {
				bean.setMemo(getSP().getString("memo", ""));
			}
			if (getSP().contains("access_token")) {
				bean.setAccess_token(getSP().getString("access_token", ""));
			}
			if (getSP().contains("loc_Account")) {
				bean.setLoc_Account(getSP().getString("loc_Account", ""));
			}
			if (getSP().contains("loc_AccountType")) {
				bean.setLoc_AccountType(getSP().getInt("loc_AccountType", -1));
			}
			if (getSP().contains("loc_OpenMe")) {
				bean.setLoc_OpenMe(getSP().getBoolean("loc_OpenMe", false));
			}
			if (getSP().contains("loc_AcceptPush")) {
				bean.setLoc_AcceptPush(getSP().getInt("loc_AcceptPush", 1));
			}
			if (getSP().contains("loc_AddHelperVerification")) {
				bean.setLoc_AddHelperVerification(getSP().getInt("loc_AddHelperVerification", 0));
			}
			if (getSP().contains("loc_Target_count")) {
				bean.setLoc_Target_count(getSP().getInt("loc_Target_count", 0));
			}
			if (getSP().contains("loc_Helper_count")) {
				bean.setLoc_Helper_count(getSP().getInt("loc_Helper_count", 0));
			}
			if (getSP().contains("loc_coin_count")) {
				bean.setLoc_coin_count(getSP().getInt("loc_coin_count", 0));
			}
			if (getSP().contains("device_id")) {
				bean.setDevice_id(getSP().getInt("device_id", -1));
			}
			if (getSP().contains("verification")) {
				bean.setVerification(getSP().getString("verification", ""));
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public User_Bean read() throws Exception {
		User_Bean result = null;
		try {
			result = new User_Bean();
			read(result);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	@Override
	public void write(User_Bean bean) throws Exception {
		try {
			// 实例化getSP().Editor对象（第二步）
			SharedPreferences.Editor editor = getSP().edit();
			// 用putString的方法保存数据
			editor.putInt("user_id", bean.getUser_id());
			editor.putString("name", bean.getName());
			editor.putInt("gender", bean.getGender());
			editor.putString("country", bean.getCountry());
			editor.putString("city", bean.getCity());
			editor.putString("company", bean.getCompany());
			editor.putString("title", bean.getTitle());
			editor.putString("address", bean.getAddress());
			editor.putString("zip", bean.getZip());
			editor.putString("mobile", bean.getMobile());
			editor.putString("tel", bean.getTel());
			editor.putString("fax", bean.getFax());
			editor.putString("webpage", bean.getWebpage());
			editor.putString("email", bean.getEmail());
			editor.putString("sign", bean.getSign());
			editor.putInt("avatar_file_id", bean.getAvatar_file_id());
			editor.putString("memo", bean.getMemo());
			editor.putString("access_token", bean.getAccess_token());
			editor.putString("loc_Account", bean.getLoc_Account());
			editor.putInt("loc_AccountType", bean.getLoc_AccountType());
			editor.putBoolean("loc_OpenMe", bean.getLoc_OpenMe());
			editor.putInt("loc_AcceptPush", bean.getLoc_AcceptPush());
			editor.putInt("loc_AddHelperVerification", bean.getLoc_AddHelperVerification());
			editor.putInt("loc_Target_count", bean.getLoc_Target_count());
			editor.putInt("loc_Helper_count", bean.getLoc_Helper_count());
			editor.putInt("loc_coin_count", bean.getLoc_coin_count());
			editor.putInt("device_id", bean.getDevice_id());
			editor.putString("verification", bean.getVerification());
			// 提交当前数据
			editor.commit();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

}
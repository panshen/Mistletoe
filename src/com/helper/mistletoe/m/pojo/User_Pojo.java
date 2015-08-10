package com.helper.mistletoe.m.pojo;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.helper.mistletoe.m.sjb.core.SimpleSharePreference;
import com.helper.mistletoe.util.ExceptionHandle;

class User_Pojo extends SimpleSharePreference {
	public Integer user_id;// 用户id
	public String name;// 姓名
	public String py_name;// 姓名拼音
	public Integer gender;// 性别 未知/男/女
	public String country;// 国家
	public String city;// 城市
	public String company;// 公司
	public String title;// 职务
	public String address;// 地址
	public String zip;// 邮编
	public String mobile;// 手机
	public String tel;// 座机
	public String fax;// 传真
	public String webpage;// 主页
	public String email;// 邮件
	public String sign;// 签名
	public Integer avatar_file_id;// 头像图片id
	public String memo;// 备注
	public String access_token;// access_token
	public String loc_Account;// Account
	public Integer loc_AccountType;// AccountType
	@SerializedName("is_public_helper")
	public Boolean loc_OpenMe;// 是否为罗盘公共帮手
	@SerializedName("accept_push_msg")
	public Integer loc_AcceptPush;// 是否接受新消息提醒
	@SerializedName("helper_verification")
	public Integer loc_AddHelperVerification;// 添加好友时是否需要验证
	@SerializedName("target_count")
	public Integer loc_Target_count;// 目标总数
	@SerializedName("helper_count")
	public Integer loc_Helper_count;// 帮手总数
	@SerializedName("coin_balance")
	public Integer loc_coin_count;// 罗盘币数量
	public Integer device_id;// 设备Id
	public String verification;// 验证码

	@Override
	public String getFingerprint() {
		String result = "";
		try {
			result = "" + getUser_id();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public String getSharePreferenceName() {
		return "user_sp";
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	// TODO 以下为Get Set函数
	public Integer getUser_id() {
		if (user_id == null) {
			user_id = 0;
		}
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		if (name == null) {
			name = "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPy_name() {
		if (py_name == null) {
			py_name = "";
		}
		return py_name;
	}

	public void setPy_name(String py_name) {
		this.py_name = py_name;
	}

	public Integer getGender() {
		if (gender == null) {
			gender = 0;
		}
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getCountry() {
		if (country == null) {
			country = "";
		}
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		if (city == null) {
			city = "";
		}
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompany() {
		if (company == null) {
			company = "";
		}
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTitle() {
		if (title == null) {
			title = "";
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		if (address == null) {
			address = "";
		}
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		if (zip == null) {
			zip = "";
		}
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getMobile() {
		if (mobile == null) {
			mobile = "";
		}
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		if (tel == null) {
			tel = "";
		}
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		if (fax == null) {
			fax = "";
		}
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebpage() {
		if (webpage == null) {
			webpage = "";
		}
		return webpage;
	}

	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}

	public String getEmail() {
		if (email == null) {
			email = "";
		}
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSign() {
		if (sign == null) {
			sign = "";
		}
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getAvatar_file_id() {
		if (avatar_file_id == null) {
			avatar_file_id = 0;
		}
		return avatar_file_id;
	}

	public void setAvatar_file_id(Integer avatar_file_id) {
		this.avatar_file_id = avatar_file_id;
	}

	public String getMemo() {
		if (memo == null) {
			memo = "";
		}
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAccess_token() {
		if (access_token == null) {
			access_token = "";
		}
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getLoc_Account() {
		if (loc_Account == null) {
			loc_Account = "";
		}
		return loc_Account;
	}

	public void setLoc_Account(String loc_Account) {
		this.loc_Account = loc_Account;
	}

	public Integer getLoc_AccountType() {
		if (loc_AccountType == null) {
			loc_AccountType = 0;
		}
		return loc_AccountType;
	}

	public void setLoc_AccountType(Integer loc_AccountType) {
		this.loc_AccountType = loc_AccountType;
	}

	public Boolean getLoc_OpenMe() {
		if (loc_OpenMe == null) {
			loc_OpenMe = false;
		}
		return loc_OpenMe;
	}

	public void setLoc_OpenMe(Boolean loc_OpenMe) {
		this.loc_OpenMe = loc_OpenMe;
	}

	public Integer getLoc_AcceptPush() {
		if (loc_AcceptPush == null) {
			loc_AcceptPush = 0;
		}
		return loc_AcceptPush;
	}

	public void setLoc_AcceptPush(Integer loc_AcceptPush) {
		this.loc_AcceptPush = loc_AcceptPush;
	}

	public Integer getLoc_AddHelperVerification() {
		if (loc_AddHelperVerification == null) {
			loc_AddHelperVerification = 0;
		}
		return loc_AddHelperVerification;
	}

	public void setLoc_AddHelperVerification(Integer loc_AddHelperVerification) {
		this.loc_AddHelperVerification = loc_AddHelperVerification;
	}

	public Integer getLoc_Target_count() {
		if (loc_Target_count == null) {
			loc_Target_count = 0;
		}
		return loc_Target_count;
	}

	public void setLoc_Target_count(Integer loc_Target_count) {
		this.loc_Target_count = loc_Target_count;
	}

	public Integer getLoc_Helper_count() {
		if (loc_Helper_count == null) {
			loc_Helper_count = 0;
		}
		return loc_Helper_count;
	}

	public void setLoc_Helper_count(Integer loc_Helper_count) {
		this.loc_Helper_count = loc_Helper_count;
	}

	public Integer getLoc_coin_count() {
		if (loc_coin_count == null) {
			loc_coin_count = 0;
		}
		return loc_coin_count;
	}

	public void setLoc_coin_count(Integer loc_coin_count) {
		this.loc_coin_count = loc_coin_count;
	}

	public Integer getDevice_id() {
		if (device_id == null) {
			device_id = 0;
		}
		return device_id;
	}

	public void setDevice_id(Integer device_id) {
		this.device_id = device_id;
	}

	public String getVerification() {
		if (verification == null) {
			verification = "";
		}
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

}
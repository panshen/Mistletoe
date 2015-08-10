package com.helper.mistletoe.m.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.helper.mistletoe.util.ExceptionHandle;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class Login_User_Bean implements Parcelable {
	private NetResult_Bean loc_NetRes;
	private String helper_verification;
	private String hardware;
	private String app_version;
	private String access_token_valid_for;
	private String device_token;
	private String user_id;
	private String access_token;
	private String account_type;
	private String os;
	private String platform;
	private String accept_push_msg;
	private String account;
	private String memo;
	private String tel;
	private String city;
	private String title;
	private String name;
	private String gender;
	private String zip;
	private String fax;
	private String country;
	private String sign;
	private String email;
	private String webpage;
	private String address;
	private String company;
	private String avatar_file_id;
	private String mobile;
	private String device_id;

	// TODO 以下是特有的函数
	public void transformDataTo(User_Bean bean) throws Exception {
		try {
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	// TODO 以上是特有的函数

	private Login_User_Bean(Parcel in) {
		try {
			ClassLoader loader = this.getClass().getClassLoader();
			loc_NetRes = in.readParcelable(loader);
			helper_verification = in.readString();
			hardware = in.readString();
			app_version = in.readString();
			access_token_valid_for = in.readString();
			device_token = in.readString();
			user_id = in.readString();
			access_token = in.readString();
			account_type = in.readString();
			os = in.readString();
			platform = in.readString();
			accept_push_msg = in.readString();
			account = in.readString();
			memo = in.readString();
			tel = in.readString();
			city = in.readString();
			title = in.readString();
			name = in.readString();
			gender = in.readString();
			zip = in.readString();
			fax = in.readString();
			country = in.readString();
			sign = in.readString();
			email = in.readString();
			webpage = in.readString();
			address = in.readString();
			company = in.readString();
			avatar_file_id = in.readString();
			mobile = in.readString();
			device_id = in.readString();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		try {
			dest.writeParcelable(loc_NetRes, PARCELABLE_WRITE_RETURN_VALUE);
			dest.writeString(helper_verification);
			dest.writeString(hardware);
			dest.writeString(app_version);
			dest.writeString(access_token_valid_for);
			dest.writeString(device_token);
			dest.writeString(user_id);
			dest.writeString(access_token);
			dest.writeString(account_type);
			dest.writeString(os);
			dest.writeString(platform);
			dest.writeString(accept_push_msg);
			dest.writeString(account);
			dest.writeString(memo);
			dest.writeString(tel);
			dest.writeString(city);
			dest.writeString(title);
			dest.writeString(name);
			dest.writeString(gender);
			dest.writeString(zip);
			dest.writeString(fax);
			dest.writeString(country);
			dest.writeString(sign);
			dest.writeString(email);
			dest.writeString(webpage);
			dest.writeString(address);
			dest.writeString(company);
			dest.writeString(avatar_file_id);
			dest.writeString(mobile);
			dest.writeString(device_id);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public Login_User_Bean() {
		try {
			this.loc_NetRes = getLoc_NetRes();
			this.helper_verification = getHelper_verification();
			this.hardware = getHardware();
			this.app_version = getApp_version();
			this.access_token_valid_for = getAccess_token_valid_for();
			this.device_token = getDevice_token();
			this.user_id = getUser_id();
			this.access_token = getAccess_token();
			this.account_type = getAccount_type();
			this.os = getOs();
			this.platform = getPlatform();
			this.accept_push_msg = getAccept_push_msg();
			this.account = getAccount();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static final Creator<Login_User_Bean> CREATOR = new Creator<Login_User_Bean>() {
		@Override
		public Login_User_Bean createFromParcel(Parcel in) {
			return new Login_User_Bean(in);
		}

		@Override
		public Login_User_Bean[] newArray(int size) {
			return new Login_User_Bean[size];
		}
	};

	public static Creator<Login_User_Bean> getCreator() {
		return CREATOR;
	}

	@Override
	public int describeContents() {
		return CONTENTS_FILE_DESCRIPTOR;
	}

	// TODO 以下为Get Set函数
	public NetResult_Bean getLoc_NetRes() {
		if (loc_NetRes == null) {
			loc_NetRes = new NetResult_Bean();
		}
		return loc_NetRes;
	}

	public void setLoc_NetRes(NetResult_Bean loc_NetRes) {
		this.loc_NetRes = loc_NetRes;
	}

	public String getHelper_verification() {
		if (helper_verification == null) {
			helper_verification = "";
		}
		return helper_verification;
	}

	public void setHelper_verification(String helper_verification) {
		this.helper_verification = helper_verification;
	}

	public String getHardware() {
		if (hardware == null) {
			hardware = "";
		}
		return hardware;
	}

	public void setHardware(String hardware) {
		this.hardware = hardware;
	}

	public String getApp_version() {
		if (app_version == null) {
			app_version = "";
		}
		return app_version;
	}

	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}

	public String getAccess_token_valid_for() {
		if (access_token_valid_for == null) {
			access_token_valid_for = "";
		}
		return access_token_valid_for;
	}

	public void setAccess_token_valid_for(String access_token_valid_for) {
		this.access_token_valid_for = access_token_valid_for;
	}

	public String getDevice_token() {
		if (device_token == null) {
			device_token = "";
		}
		return device_token;
	}

	public void setDevice_token(String device_token) {
		this.device_token = device_token;
	}

	public String getUser_id() {
		if (user_id == null) {
			user_id = "";
		}
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public String getAccount_type() {
		if (account_type == null) {
			account_type = "";
		}
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getOs() {
		if (os == null) {
			os = "";
		}
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getPlatform() {
		if (platform == null) {
			platform = "";
		}
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAccept_push_msg() {
		if (accept_push_msg == null) {
			accept_push_msg = "";
		}
		return accept_push_msg;
	}

	public void setAccept_push_msg(String accept_push_msg) {
		this.accept_push_msg = accept_push_msg;
	}

	public String getAccount() {
		if (account == null) {
			account = "";
		}
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public String getTel() {
		if (tel == null) {
			tel = "";
		}
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public String getTitle() {
		if (title == null) {
			title = "";
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getGender() {
		if (gender == null) {
			gender = "";
		}
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getFax() {
		if (fax == null) {
			fax = "";
		}
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	public String getSign() {
		if (sign == null) {
			sign = "";
		}
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
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

	public String getWebpage() {
		if (webpage == null) {
			webpage = "";
		}
		return webpage;
	}

	public void setWebpage(String webpage) {
		this.webpage = webpage;
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

	public String getCompany() {
		if (company == null) {
			company = "";
		}
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAvatar_file_id() {
		if (avatar_file_id == null) {
			avatar_file_id = "";
		}
		return avatar_file_id;
	}

	public void setAvatar_file_id(String avatar_file_id) {
		this.avatar_file_id = avatar_file_id;
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

	public String getDevice_id() {
		if (device_id == null) {
			device_id = "";
		}
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
}
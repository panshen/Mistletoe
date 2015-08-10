package com.helper.mistletoe.m.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.helper.mistletoe.util.ExceptionHandle;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class Update_User_Bean implements Parcelable {
	private NetResult_Bean loc_NetRes;
	private String user_id;
	private String name;
	private String gender;
	private String country;
	private String city;
	private String company;
	private String title;
	private String address;
	private String zip;
	private String fax;
	private String webpage;
	private String tel;
	private String mobile;
	private String email;
	private String sign;
	private String avatar_file_id;
	private String memo;
	private String accept_push_msg;

	// TODO 以下是特有的函数
	// TODO 以上是特有的函数

	private Update_User_Bean(Parcel in) {
		try {
			ClassLoader loader = this.getClass().getClassLoader();
			loc_NetRes = in.readParcelable(loader);
			user_id = in.readString();
			accept_push_msg = in.readString();
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
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		try {
			dest.writeParcelable(loc_NetRes, PARCELABLE_WRITE_RETURN_VALUE);
			dest.writeString(user_id);
			dest.writeString(accept_push_msg);
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
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public Update_User_Bean() {
		try {
			this.loc_NetRes = getLoc_NetRes();
			this.user_id = getUser_id();
			this.accept_push_msg = getAccept_push_msg();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static final Creator<Update_User_Bean> CREATOR = new Creator<Update_User_Bean>() {
		@Override
		public Update_User_Bean createFromParcel(Parcel in) {
			return new Update_User_Bean(in);
		}

		@Override
		public Update_User_Bean[] newArray(int size) {
			return new Update_User_Bean[size];
		}
	};

	public static Creator<Update_User_Bean> getCreator() {
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

	public String getUser_id() {
		if (user_id == null) {
			user_id = "";
		}
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
}
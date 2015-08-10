package com.helper.mistletoe.m.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.helper.mistletoe.util.ExceptionHandle;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class Register_User_Bean implements Parcelable {
	private NetResult_Bean loc_NetRes;
	private String os;
	private String platform;
	private String hardware;
	private String device_token;
	private String app_version;
	private String account;
	private String hint;
	private String user_id;
	private String account_type;
	private Integer device_id;

	// TODO 以下是特有的函数
	// TODO 以上是特有的函数

	private Register_User_Bean(Parcel in) {
		try {
			ClassLoader loader = this.getClass().getClassLoader();
			loc_NetRes = in.readParcelable(loader);
			os = in.readString();
			platform = in.readString();
			hardware = in.readString();
			device_token = in.readString();
			app_version = in.readString();
			account = in.readString();
			hint = in.readString();
			account_type = in.readString();
			device_id = in.readInt();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		try {
			dest.writeParcelable(loc_NetRes, PARCELABLE_WRITE_RETURN_VALUE);
			dest.writeString(os);
			dest.writeString(platform);
			dest.writeString(hardware);
			dest.writeString(device_token);
			dest.writeString(app_version);
			dest.writeString(account);
			dest.writeString(hint);
			dest.writeString(account_type);
			dest.writeInt(device_id);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public Register_User_Bean() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static final Creator<Register_User_Bean> CREATOR = new Creator<Register_User_Bean>() {
		@Override
		public Register_User_Bean createFromParcel(Parcel in) {
			return new Register_User_Bean(in);
		}

		@Override
		public Register_User_Bean[] newArray(int size) {
			return new Register_User_Bean[size];
		}
	};

	public static Creator<Register_User_Bean> getCreator() {
		return CREATOR;
	}

	@Override
	public int describeContents() {
		return CONTENTS_FILE_DESCRIPTOR;
	}

	// TODO 以下为Get Set函数

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

	public String getHardware() {
		if (hardware == null) {
			hardware = "";
		}
		return hardware;
	}

	public void setHardware(String hardware) {
		this.hardware = hardware;
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

	public String getApp_version() {
		if (app_version == null) {
			app_version = "";
		}
		return app_version;
	}

	public void setApp_version(String app_version) {
		this.app_version = app_version;
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

	public String getHint() {
		if (hint == null) {
			hint = "";
		}
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
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

	public String getAccount_type() {
		if (account_type == null) {
			account_type = "";
		}
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public NetResult_Bean getLoc_NetRes() {
		if (loc_NetRes == null) {
			loc_NetRes = new NetResult_Bean();
		}
		return loc_NetRes;
	}

	public void setLoc_NetRes(NetResult_Bean loc_NetRes) {
		this.loc_NetRes = loc_NetRes;
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

}
package com.helper.mistletoe.m.pojo;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

public class Result_Bean implements Parcelable {

	String result_msg;// 返回解释
	Integer id;// 设备ID
	Integer platform;// 平台
	String os;// 版本
	String hardware;// 设备名
	@SerializedName("version")
	String app_version;// app版本号
	Integer status;// 注册但没有登录
	String url;//app 下载地址
	Long access_token_valid;// 允许使用的时间戳
	Long last_login_time;// 最后一次登录时间
	

	private Result_Bean(Parcel in) {
		result_msg = in.readString();
		id = in.readInt();
		platform = in.readInt();
		os = in.readString();
		hardware = in.readString();
		app_version = in.readString();
		status = in.readInt();
		url = in.readString();
		access_token_valid = in.readLong();
		last_login_time = in.readLong();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(result_msg);
		dest.writeInt(id);
		dest.writeInt(platform);
		dest.writeString(os);
		dest.writeString(hardware);
		dest.writeString(app_version);
		dest.writeInt(status);
		dest.writeString(url);
		dest.writeLong(access_token_valid);
		dest.writeLong(last_login_time);
	}

	public Result_Bean() {
		this.result_msg = "";
		this.id = -1;
		this.platform = -1;
		this.os = "";
		this.hardware = "";
		this.app_version = "";
		this.status = -1;
		this.url = "";
		this.access_token_valid = 0L;
		this.last_login_time = 0L;
	}

	public String getResult_msg() {
		return result_msg;
	}

	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getHardware() {
		return hardware;
	}

	public void setHardware(String hardware) {
		this.hardware = hardware;
	}

	public String getApp_version() {
		return app_version;
	}

	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}

	public String getUrl() {
		if (url==null) {
			url="";
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getAccess_token_valid() {
		return access_token_valid;
	}

	public void setAccess_token_valid(Long access_token_valid) {
		this.access_token_valid = access_token_valid;
	}

	public Long getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(Long last_login_time) {
		this.last_login_time = last_login_time;
	}

	@Override
	public String toString() {
		return "Result_Bean [result_msg=" + result_msg + ", id=" + id + ", platform=" + platform + ", os=" + os + ", hardware=" + hardware + ", app_version=" + app_version + ", status=" + status
				+ ", url=" + url + ", access_token_valid=" + access_token_valid + ", last_login_time=" + last_login_time + "]";
	}


	public static final Parcelable.Creator<Result_Bean> CREATOR = new Parcelable.Creator<Result_Bean>() {
		@Override
		public Result_Bean createFromParcel(Parcel in) {
			return new Result_Bean(in);
		}

		@Override
		public Result_Bean[] newArray(int size) {
			return new Result_Bean[size];
		}
	};

	public static Parcelable.Creator<Result_Bean> getCreator() {
		return CREATOR;
	}

	@Override
	public int describeContents() {
		return CONTENTS_FILE_DESCRIPTOR;
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		try {
			if (o instanceof Result_Bean) {
				int oHashCode = ((Result_Bean) o).hashCode();
				if (oHashCode == this.hashCode()) {
					result = true;
				}
			}
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int hashCode() {
		int result = 0;
		try {
			result = id.hashCode() + 36;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Result_Bean clone() throws CloneNotSupportedException {
		Result_Bean o = null;
		try {
			o = new Result_Bean();
			o.result_msg = (String) result_msg;
			o.id = (Integer) id;
			o.platform = (Integer) platform;
			o.os = (String) os;
			o.hardware = (String) hardware;
			o.app_version = (String) app_version;
			o.status = (Integer) status;
			o.url = (String) url;
			o.access_token_valid = (Long) access_token_valid;
			o.last_login_time = (Long) last_login_time;
		} catch (Exception e) {
			CloneNotSupportedException e1 = new CloneNotSupportedException();
			throw e1;
		}
		return o;
	}
}
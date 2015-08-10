package com.helper.mistletoe.m.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.helper.mistletoe.util.ExceptionHandle;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class NetResult_Bean implements Parcelable {
	/**
	 * 服务器返回的结果
	 */
	private String result;
	/**
	 * 结果的解释
	 */
	private String result_msg;
	/**
	 * 返回的Json数据
	 */
	private String loc_data;

	// TODO 以下是特有的函数
	// TODO 以上是特有的函数

	private NetResult_Bean(Parcel in) {
		try {
			result = in.readString();
			result_msg = in.readString();
			loc_data = in.readString();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		try {
			dest.writeString(result);
			dest.writeString(result_msg);
			dest.writeString(loc_data);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public NetResult_Bean() {
		try {
			this.result = getResult();
			this.result_msg = getResult_msg();
			this.loc_data = getLoc_data();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static final Creator<NetResult_Bean> CREATOR = new Creator<NetResult_Bean>() {
		@Override
		public NetResult_Bean createFromParcel(Parcel in) {
			return new NetResult_Bean(in);
		}

		@Override
		public NetResult_Bean[] newArray(int size) {
			return new NetResult_Bean[size];
		}
	};

	public static Creator<NetResult_Bean> getCreator() {
		return CREATOR;
	}

	@Override
	public int describeContents() {
		return CONTENTS_FILE_DESCRIPTOR;
	}

	// TODO 以下为Get Set函数
	public String getResult() {
		if (result == null) {
			result = "";
		}
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult_msg() {
		if (result_msg == null) {
			result_msg = "";
		}
		return result_msg;
	}

	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}

	public String getLoc_data() {
		if (loc_data == null) {
			loc_data = "";
		}
		return loc_data;
	}

	public void setLoc_data(String loc_data) {
		this.loc_data = loc_data;
	}

}
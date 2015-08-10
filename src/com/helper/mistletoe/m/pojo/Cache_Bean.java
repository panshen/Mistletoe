package com.helper.mistletoe.m.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Cache_Bean implements Parcelable {

	String feedBack;// 意见
	String feedBack_stype;// 类型

	private Cache_Bean(Parcel in) {
		feedBack = in.readString();
		feedBack_stype = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(feedBack);
		dest.writeString(feedBack_stype);
	}

	public Cache_Bean() {
		this.feedBack = "";
		this.feedBack_stype = "";
	}

	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	public String getFeedBack_stype() {
		return feedBack_stype;
	}

	public void setFeedBack_stype(String feedBack_stype) {
		this.feedBack_stype = feedBack_stype;
	}

	@Override
	public String toString() {
		return "Cache_Bean [feedBack=" + feedBack + ", feedBack_stype=" + feedBack_stype + "]";
	}

	public static final Parcelable.Creator<Cache_Bean> CREATOR = new Parcelable.Creator<Cache_Bean>() {
		@Override
		public Cache_Bean createFromParcel(Parcel in) {
			return new Cache_Bean(in);
		}

		@Override
		public Cache_Bean[] newArray(int size) {
			return new Cache_Bean[size];
		}
	};

	public static Parcelable.Creator<Cache_Bean> getCreator() {
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
			if (o instanceof Cache_Bean) {
				int oHashCode = ((Cache_Bean) o).hashCode();
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
}
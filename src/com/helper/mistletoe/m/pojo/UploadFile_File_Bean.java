package com.helper.mistletoe.m.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.helper.mistletoe.util.ExceptionHandle;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class UploadFile_File_Bean implements Parcelable {
	private NetResult_Bean loc_NetRes;
	private String file_id;

	// TODO 以下是特有的函数
	// TODO 以上是特有的函数

	private UploadFile_File_Bean(Parcel in) {
		try {
			ClassLoader loader = this.getClass().getClassLoader();
			loc_NetRes = in.readParcelable(loader);
			file_id = in.readString();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		try {
			dest.writeParcelable(loc_NetRes, PARCELABLE_WRITE_RETURN_VALUE);
			dest.writeString(file_id);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public UploadFile_File_Bean() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static final Creator<UploadFile_File_Bean> CREATOR = new Creator<UploadFile_File_Bean>() {
		@Override
		public UploadFile_File_Bean createFromParcel(Parcel in) {
			return new UploadFile_File_Bean(in);
		}

		@Override
		public UploadFile_File_Bean[] newArray(int size) {
			return new UploadFile_File_Bean[size];
		}
	};

	public static Creator<UploadFile_File_Bean> getCreator() {
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

	public String getFile_id() {
		if (file_id == null) {
			file_id = "";
		}
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

}
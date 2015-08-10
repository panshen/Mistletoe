package com.helper.mistletoe.m.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Public_Bean extends Helpers_Sub_Bean {

	@SerializedName("id")
	Integer public_id;// 帮手的id

	public Public_Bean(Integer helper_id, String helper_account, Integer helper_account_type, String helper_name, String helper_memo_name, String helper_readygo_name, String helper_name_pinyin,
			String helper_memo_name_pinyin, String helper_readygo_name_pinyin, String helper_tel, String helper_mobile, Integer helper_gender, String helper_zip, String helper_fax,
			String helper_email, String helper_company, String helper_title, String helper_country, String helper_city, String helper_address, String helper_webpage, String helper_photo,
			String helper_sign, Integer helper_status, Integer helper_verification, Integer helper_count, Integer helper_target_count, Integer helper_coin_count, Integer helper_view_count,
			String helper_memo, String helper_msg, Long helper_become_public_time, Long helper_update_time, Long helper_start_time, Integer public_id) {
		super(helper_id, helper_account, helper_account_type, helper_name, helper_memo_name, helper_readygo_name, helper_name_pinyin, helper_memo_name_pinyin, helper_readygo_name_pinyin, helper_tel,
				helper_mobile, helper_gender, helper_zip, helper_fax, helper_email, helper_company, helper_title, helper_country, helper_city, helper_address, helper_webpage, helper_photo,
				helper_sign, helper_status, helper_verification, helper_count, helper_target_count, helper_coin_count, helper_view_count, helper_memo, helper_msg, helper_become_public_time,
				helper_update_time, helper_start_time);
		this.public_id = public_id;
	}

	private Public_Bean(Parcel in) {
		super(in);
		public_id = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeInt(public_id);
	}

	public Public_Bean() {
		this.public_id = -1;// 帮手的id
	}

	public Integer getPublic_id() {
		return public_id;
	}

	public void setPublic_id(Integer public_id) {
		this.public_id = public_id;
	}

	@Override
	public String toString() {
		return "Public_Bean [public_id=" + public_id + "]";
	}

	public static final Parcelable.Creator<Public_Bean> CREATOR = new Parcelable.Creator<Public_Bean>() {
		@Override
		public Public_Bean createFromParcel(Parcel in) {
			return new Public_Bean(in);
		}

		@Override
		public Public_Bean[] newArray(int size) {
			return new Public_Bean[size];
		}
	};


	@Override
	public int describeContents() {
		return CONTENTS_FILE_DESCRIPTOR;
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		try {
			if (o instanceof Public_Bean) {
				int oHashCode = ((Public_Bean) o).hashCode();
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
			result = public_id.hashCode() + 36;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Public_Bean clone() throws CloneNotSupportedException {
		Public_Bean o = null;
		try {
			o = new Public_Bean();
			o.public_id = (Integer) public_id;

		} catch (Exception e) {
			CloneNotSupportedException e1 = new CloneNotSupportedException();
			throw e1;
		}
		return o;
	}
}
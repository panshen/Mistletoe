package com.helper.mistletoe.m.pojo;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

public class Helpers_Sub_Bean implements Parcelable {

	@SerializedName("user_id")
	Integer helper_id;// 帮手的id
	@SerializedName("account")
	String helper_account;// 帮手的账号
	@SerializedName("account_type")
	Integer helper_account_type;// 账号类型，0为手机，1为邮箱
	String helper_name;// 显示姓名,不在保存而是拿去的时候，再判断是取memo还是readygo
	@SerializedName("memo_name")
	String helper_memo_name;// 备注名
	@SerializedName("name")
	String helper_readygo_name;// 账号名
	String helper_name_pinyin;// 帮手显示名字的拼音，备注名优先于账号名
	@SerializedName("py_memo_name")
	String helper_memo_name_pinyin;// 备注名拼音
	@SerializedName("py_name")
	String helper_readygo_name_pinyin;// 账号名拼音
	String helper_tel;// 帮手的座机号
	String helper_mobile;// 帮手的手机号
	Integer helper_gender;// 帮手性别
	String helper_zip;// 帮手的邮编
	String helper_fax;// 帮手的传真
	@SerializedName("email")
	String helper_email;// 电子邮件
	String helper_company;// 公司
	String helper_title;// 职务
	String helper_country;// 国家
	String helper_city;// 城市
	String helper_address;// 地址
	@SerializedName("webpage")
	String helper_webpage;// 主页地址
	@SerializedName("avatar_file_id")
	String helper_photo;// 头像
	@SerializedName("sign")
	String helper_sign;// 个性签名
	@SerializedName("status")
	Integer helper_status;// 关系状态
	Integer helper_verification;// 用于添加帮手时是否需要验证信息
	Integer helper_count;// 用户帮手总数
	@SerializedName("target_count")
	Integer helper_target_count;// 用户目标总数
	@SerializedName("coin_balance")
	Integer helper_coin_count;// 罗盘币的数量
	@SerializedName("view_count")
	Integer helper_view_count;// 展示次数
	@SerializedName("memo")
	String helper_memo;// 自荐的备注标签
	@SerializedName("msg")
	String helper_msg;// 邀请时信息
	Long helper_become_public_time;// 升级为公众帮手的时间
	@SerializedName("last_updated_time")
	Long helper_update_time;// 更新时间
	Long helper_start_time;// 公共帮手的最后更新时间

	public Helpers_Sub_Bean(Integer helper_id, String helper_account, Integer helper_account_type, String helper_name, String helper_memo_name, String helper_readygo_name, String helper_name_pinyin,
			String helper_memo_name_pinyin, String helper_readygo_name_pinyin, String helper_tel, String helper_mobile, Integer helper_gender, String helper_zip, String helper_fax,
			String helper_email, String helper_company, String helper_title, String helper_country, String helper_city, String helper_address, String helper_webpage, String helper_photo,
			String helper_sign, Integer helper_status, Integer helper_verification, Integer helper_count, Integer helper_target_count, Integer helper_coin_count, Integer helper_view_count,
			String helper_memo, String helper_msg, Long helper_become_public_time, Long helper_update_time, Long helper_start_time) {
		super();
		this.helper_id = helper_id;
		this.helper_account = helper_account;
		this.helper_account_type = helper_account_type;
		this.helper_name = helper_name;
		this.helper_memo_name = helper_memo_name;
		this.helper_readygo_name = helper_readygo_name;
		this.helper_name_pinyin = helper_name_pinyin;
		this.helper_memo_name_pinyin = helper_memo_name_pinyin;
		this.helper_readygo_name_pinyin = helper_readygo_name_pinyin;
		this.helper_tel = helper_tel;
		this.helper_mobile = helper_mobile;
		this.helper_gender = helper_gender;
		this.helper_zip = helper_zip;
		this.helper_fax = helper_fax;
		this.helper_email = helper_email;
		this.helper_company = helper_company;
		this.helper_title = helper_title;
		this.helper_country = helper_country;
		this.helper_city = helper_city;
		this.helper_address = helper_address;
		this.helper_webpage = helper_webpage;
		this.helper_photo = helper_photo;
		this.helper_sign = helper_sign;
		this.helper_status = helper_status;
		this.helper_verification = helper_verification;
		this.helper_count = helper_count;
		this.helper_target_count = helper_target_count;
		this.helper_coin_count = helper_coin_count;
		this.helper_view_count = helper_view_count;
		this.helper_memo = helper_memo;
		this.helper_msg = helper_msg;
		this.helper_become_public_time = helper_become_public_time;
		this.helper_update_time = helper_update_time;
		this.helper_start_time = helper_start_time;
	}

	protected Helpers_Sub_Bean(Parcel in) {
		helper_id = in.readInt();
		helper_account = in.readString();
		helper_account_type = in.readInt();
		helper_name = in.readString();
		helper_memo_name = in.readString();
		helper_readygo_name = in.readString();
		helper_name_pinyin = in.readString();
		helper_memo_name_pinyin = in.readString();
		helper_readygo_name_pinyin = in.readString();
		helper_tel = in.readString();
		helper_mobile = in.readString();
		helper_gender = in.readInt();
		helper_zip = in.readString();
		helper_fax = in.readString();
		helper_email = in.readString();
		helper_company = in.readString();
		helper_title = in.readString();
		helper_country = in.readString();
		helper_city = in.readString();
		helper_address = in.readString();
		helper_webpage = in.readString();
		helper_photo = in.readString();
		helper_sign = in.readString();
		helper_status = in.readInt();
		helper_verification = in.readInt();
		helper_count = in.readInt();
		helper_target_count = in.readInt();
		helper_coin_count = in.readInt();
		helper_view_count = in.readInt();
		helper_memo = in.readString();
		helper_msg = in.readString();
		helper_become_public_time = in.readLong();
		helper_update_time = in.readLong();
		helper_start_time = in.readLong();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(helper_id);
		dest.writeString(helper_account);
		dest.writeInt(helper_account_type);
		dest.writeString(helper_name);
		dest.writeString(helper_memo_name);
		dest.writeString(helper_readygo_name);
		dest.writeString(helper_name_pinyin);
		dest.writeString(helper_memo_name_pinyin);
		dest.writeString(helper_readygo_name_pinyin);
		dest.writeString(helper_tel);
		dest.writeString(helper_mobile);
		dest.writeInt(helper_gender);
		dest.writeString(helper_zip);
		dest.writeString(helper_fax);
		dest.writeString(helper_email);
		dest.writeString(helper_company);
		dest.writeString(helper_title);
		dest.writeString(helper_country);
		dest.writeString(helper_city);
		dest.writeString(helper_address);
		dest.writeString(helper_webpage);
		dest.writeString(helper_photo);
		dest.writeString(helper_sign);
		dest.writeInt(helper_status);
		dest.writeInt(helper_verification);
		dest.writeInt(helper_count);
		dest.writeInt(helper_target_count);
		dest.writeInt(helper_coin_count);
		dest.writeInt(helper_view_count);
		dest.writeString(helper_memo);
		dest.writeString(helper_msg);
		dest.writeLong(helper_become_public_time);
		dest.writeLong(helper_update_time);
		dest.writeLong(helper_start_time);
	}

	public Helpers_Sub_Bean() {
		this.helper_id = -1;// 帮手的id
		this.helper_account = "";// 帮手的账号
		this.helper_account_type = -1;// 账号类型，0为手机，1为邮箱
		this.helper_name = "";// 显示名
		this.helper_memo_name = "";// 备注名
		this.helper_readygo_name = "";// 账号名
		this.helper_name_pinyin = "";// 帮手显示名字的拼音，备注名优先于账号名
		this.helper_memo_name_pinyin = "";// 备注名拼音
		this.helper_readygo_name_pinyin = "";// 账号名拼音
		this.helper_tel = "";// 帮手的座机号
		this.helper_mobile = "";// 帮手的手机号
		this.helper_gender = -1;// 帮手性别
		this.helper_zip = "";// 帮手的邮编
		this.helper_fax = "";// 帮手的传真
		this.helper_email = "";// 电子邮件
		this.helper_company = "";// 公司
		this.helper_title = "";// 职务
		this.helper_country = "";// 国家
		this.helper_city = "";// 城市
		this.helper_address = "";// 地址
		this.helper_webpage = "";// 主页地址
		this.helper_photo = "";// 头像
		this.helper_sign = "";// 个性签名
		this.helper_status = -1;// 关系状态
		this.helper_verification = 0;// 用于添加帮手时是否需要验证信息，yes or no；
		this.helper_count = 0;// 用户帮手总数
		this.helper_target_count = 0;// 用户目标总数
		this.helper_coin_count = 0;// 罗盘币数
		this.helper_view_count = 0;// 展示次数
		this.helper_memo = "";// 自荐的备注标签
		this.helper_msg = "";// 邀请时信息
		this.helper_become_public_time = 0L;// 升级为公众帮手的时间
		this.helper_update_time = 0L;// 更新时间
		this.helper_start_time = 0L;// 更新时间
	}

	public Integer getHelper_id() {
		return helper_id;
	}

	public void setHelper_id(Integer helper_id) {
		this.helper_id = helper_id;
	}

	public String getHelper_account() {
		return helper_account;
	}

	public void setHelper_account(String helper_account) {
		this.helper_account = helper_account;
	}

	public Integer getHelper_account_type() {
		return helper_account_type;
	}

	public void setHelper_account_type(Integer helper_account_type) {
		this.helper_account_type = helper_account_type;
	}

	public String getHelper_name() {
		String helper_name = null;
		if (helper_memo_name == null || helper_memo_name.equals("")) {
			helper_name = helper_readygo_name;
		} else {
			helper_name = helper_memo_name;
		}
		if (helper_name==null) {
			helper_name="未设置姓名";
		}else if (helper_name.isEmpty()) {
			helper_name="未设置姓名";
		}
		return helper_name;
	}

	public void setHelper_name(String helper_name) {
		this.helper_name = helper_name;
	}

	public String getHelper_memo_name() {
		return helper_memo_name;
	}

	public void setHelper_memo_name(String helper_memo_name) {
		this.helper_memo_name = helper_memo_name;
	}

	public String getHelper_readygo_name() {
		return helper_readygo_name;
	}

	public void setHelper_readygo_name(String helper_readygo_name) {
		this.helper_readygo_name = helper_readygo_name;
	}

	public String getHelper_name_pinyin() {
		String helper_name_pinyin = null;
		if (helper_memo_name_pinyin == null || helper_memo_name_pinyin.equals("")) {
			helper_name_pinyin = helper_readygo_name_pinyin;
		} else {
			helper_name_pinyin = helper_memo_name_pinyin;
		}
		
		if (helper_name_pinyin == null || helper_name_pinyin.equals("")) {
			helper_name_pinyin = "Yonghuweimingming";
		}
		return helper_name_pinyin;
	}

	public void setHelper_name_pinyin(String helper_name_pinyin) {
		this.helper_name_pinyin = helper_name_pinyin;
	}

	public String getHelper_memo_name_pinyin() {
		return helper_memo_name_pinyin;
	}

	public void setHelper_memo_name_pinyin(String helper_memo_name_pinyin) {
		this.helper_memo_name_pinyin = helper_memo_name_pinyin;
	}

	public String getHelper_readygo_name_pinyin() {
		return helper_readygo_name_pinyin;
	}

	public void setHelper_readygo_name_pinyin(String helper_readygo_name_pinyin) {
		this.helper_readygo_name_pinyin = helper_readygo_name_pinyin;
	}

	public String getHelper_tel() {
		return helper_tel;
	}

	public void setHelper_tel(String helper_tel) {
		this.helper_tel = helper_tel;
	}

	public String getHelper_mobile() {
		return helper_mobile;
	}

	public void setHelper_mobile(String helper_mobile) {
		this.helper_mobile = helper_mobile;
	}

	public Integer getHelper_gender() {
		return helper_gender;
	}

	public void setHelper_gender(Integer helper_gender) {
		this.helper_gender = helper_gender;
	}

	public String getHelper_zip() {
		return helper_zip;
	}

	public void setHelper_zip(String helper_zip) {
		this.helper_zip = helper_zip;
	}

	public String getHelper_fax() {
		return helper_fax;
	}

	public void setHelper_fax(String helper_fax) {
		this.helper_fax = helper_fax;
	}

	public String getHelper_email() {
		return helper_email;
	}

	public void setHelper_email(String helper_email) {
		this.helper_email = helper_email;
	}

	public String getHelper_company() {
		return helper_company;
	}

	public void setHelper_company(String helper_company) {
		this.helper_company = helper_company;
	}

	public String getHelper_title() {
		return helper_title;
	}

	public void setHelper_title(String helper_title) {
		this.helper_title = helper_title;
	}

	public String getHelper_country() {
		return helper_country;
	}

	public void setHelper_country(String helper_country) {
		this.helper_country = helper_country;
	}

	public String getHelper_city() {
		return helper_city;
	}

	public void setHelper_city(String helper_city) {
		this.helper_city = helper_city;
	}

	public String getHelper_address() {
		return helper_address;
	}

	public void setHelper_address(String helper_address) {
		this.helper_address = helper_address;
	}

	public String getHelper_webpage() {
		return helper_webpage;
	}

	public void setHelper_webpage(String helper_webpage) {
		this.helper_webpage = helper_webpage;
	}

	public String getHelper_photo() {
		return helper_photo;
	}

	public void setHelper_photo(String helper_photo) {
		this.helper_photo = helper_photo;
	}

	public String getHelper_sign() {
		if (helper_sign == null) {
			helper_sign = "未设置个性签名";
		}else if (helper_sign.isEmpty()) {
			helper_sign = "未设置个性签名";
		}
		return helper_sign;
	}

	public void setHelper_sign(String helper_sign) {
		if (helper_sign == null) {
			helper_sign = "未设置个性签名";
		}else if (helper_sign.isEmpty()) {
			helper_sign = "未设置个性签名";
		}
		this.helper_sign = helper_sign;
	}

	public Integer getHelper_status() {
		return helper_status;
	}

	public void setHelper_status(Integer helper_status) {
		this.helper_status = helper_status;
	}

	public Integer getHelper_verification() {
		return helper_verification;
	}

	public void setHelper_verification(Integer helper_verification) {
		this.helper_verification = helper_verification;
	}

	public Integer getHelper_count() {
		return helper_count;
	}

	public void setHelper_count(Integer helper_count) {
		this.helper_count = helper_count;
	}

	public Integer getHelper_target_count() {
		return helper_target_count;
	}

	public void setHelper_target_count(Integer helper_target_count) {
		this.helper_target_count = helper_target_count;
	}

	public Integer getHelper_coin_count() {
		return helper_coin_count;
	}

	public void setHelper_coin_count(Integer helper_coin_count) {
		this.helper_coin_count = helper_coin_count;
	}

	public Integer getHelper_view_count() {
		return helper_view_count;
	}

	public void setHelper_view_count(Integer helper_view_count) {
		this.helper_view_count = helper_view_count;
	}

	public String getHelper_memo() {
		return helper_memo;
	}

	public void setHelper_memo(String helper_memo) {
		this.helper_memo = helper_memo;
	}

	public String getHelper_msg() {
		return helper_msg;
	}

	public void setHelper_msg(String helper_msg) {
		this.helper_msg = helper_msg;
	}

	public Long getHelper_become_public_time() {
		return helper_become_public_time;
	}

	public void setHelper_become_public_time(Long helper_become_public_time) {
		this.helper_become_public_time = helper_become_public_time;
	}

	public Long getHelper_update_time() {
		return helper_update_time;
	}

	public void setHelper_update_time(Long helper_update_time) {
		this.helper_update_time = helper_update_time;
	}

	public Long getHelper_start_time() {
		return helper_start_time;
	}

	public void setHelper_start_time(Long helper_start_time) {
		this.helper_start_time = helper_start_time;
	}

	@Override
	public String toString() {
		return "Helpers_Sub_Bean [helper_id=" + helper_id + ", helper_account=" + helper_account + ", helper_account_type=" + helper_account_type + ", helper_name=" + helper_name
				+ ", helper_memo_name=" + helper_memo_name + ", helper_readygo_name=" + helper_readygo_name + ", helper_name_pinyin=" + helper_name_pinyin + ", helper_memo_name_pinyin="
				+ helper_memo_name_pinyin + ", helper_readygo_name_pinyin=" + helper_readygo_name_pinyin + ", helper_tel=" + helper_tel + ", helper_mobile=" + helper_mobile + ", helper_gender="
				+ helper_gender + ", helper_zip=" + helper_zip + ", helper_fax=" + helper_fax + ", helper_email=" + helper_email + ", helper_company=" + helper_company + ", helper_title="
				+ helper_title + ", helper_country=" + helper_country + ", helper_city=" + helper_city + ", helper_address=" + helper_address + ", helper_webpage=" + helper_webpage
				+ ", helper_photo=" + helper_photo + ", helper_sign=" + helper_sign + ", helper_status=" + helper_status + ", helper_verification=" + helper_verification + ", helper_count="
				+ helper_count + ", helper_target_count=" + helper_target_count + ", helper_coin_count=" + helper_coin_count + ", helper_view_count=" + helper_view_count + ", helper_memo="
				+ helper_memo + ", helper_msg=" + helper_msg + ", helper_become_public_time=" + helper_become_public_time + ", helper_update_time=" + helper_update_time + ", helper_start_time="
				+ helper_start_time + "]";
	}

	public static final Parcelable.Creator<Helpers_Sub_Bean> CREATOR = new Parcelable.Creator<Helpers_Sub_Bean>() {
		@Override
		public Helpers_Sub_Bean createFromParcel(Parcel in) {
			return new Helpers_Sub_Bean(in);
		}

		@Override
		public Helpers_Sub_Bean[] newArray(int size) {
			return new Helpers_Sub_Bean[size];
		}
	};

	public static Parcelable.Creator<Helpers_Sub_Bean> getCreator() {
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
			if (o instanceof Helpers_Sub_Bean) {
				int oHashCode = ((Helpers_Sub_Bean) o).hashCode();
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
			result = helper_id.hashCode() + 36;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Helpers_Sub_Bean clone() throws CloneNotSupportedException {
		Helpers_Sub_Bean o = null;
		try {
			o = new Helpers_Sub_Bean();
			o.helper_id = (Integer) helper_id;
			o.helper_account = (String) helper_account;
			o.helper_account_type = (Integer) helper_account_type;
			o.helper_name = (String) helper_name;
			o.helper_memo_name = (String) helper_memo_name;
			o.helper_readygo_name = (String) helper_readygo_name;
			o.helper_name_pinyin = (String) helper_name_pinyin;
			o.helper_memo_name_pinyin = (String) helper_memo_name_pinyin;
			o.helper_readygo_name_pinyin = (String) helper_readygo_name_pinyin;
			o.helper_tel = (String) helper_tel;
			o.helper_mobile = (String) helper_mobile;
			o.helper_gender = (Integer) helper_gender;
			o.helper_zip = (String) helper_zip;
			o.helper_fax = (String) helper_fax;
			o.helper_email = (String) helper_email;
			o.helper_company = (String) helper_company;
			o.helper_title = (String) helper_title;
			o.helper_country = (String) helper_country;
			o.helper_city = (String) helper_city;
			o.helper_address = (String) helper_address;
			o.helper_webpage = (String) helper_webpage;
			o.helper_photo = (String) helper_photo;
			o.helper_sign = (String) helper_sign;
			o.helper_status = (Integer) helper_status;
			o.helper_verification = (Integer) helper_verification;
			o.helper_count = (Integer) helper_count;
			o.helper_target_count = (Integer) helper_target_count;
			o.helper_coin_count = (Integer) helper_coin_count;
			o.helper_view_count = (Integer) helper_view_count;
			o.helper_memo = (String) helper_memo;
			o.helper_msg = (String) helper_msg;
			o.helper_become_public_time = (Long) helper_become_public_time;
			o.helper_update_time = (Long) helper_update_time;
			o.helper_start_time = (Long) helper_start_time;
		} catch (Exception e) {
			CloneNotSupportedException e1 = new CloneNotSupportedException();
			throw e1;
		}
		return o;
	}
}
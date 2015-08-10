package com.helper.mistletoe.m.pojo;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

public class Group_Bean implements Parcelable {

	@SerializedName("grp_id")
	Integer group_id;// 组id
	@SerializedName("name")
	String group_name;// 组名
	@SerializedName("members")
	Integer group_member_id;// 组成员ID
	String group_memberIds;// 组成员数组，此成员变量不放入数据库，只作为携带载体
	@SerializedName("memo")
	String group_describe;// 组描述\
	@SerializedName("status")
	Integer group_status;// 组状态
	@SerializedName("create_time")
	Long group_create_time;// 组创建时间
	@SerializedName("last_updated_time")
	Long group_last_update_time;// 组更新时间

	private Group_Bean(Parcel in) {
		group_id = in.readInt();
		group_name = in.readString();
		group_member_id = in.readInt();
		group_memberIds = in.readString();
		group_describe = in.readString();
		group_status = in.readInt();
		group_create_time = in.readLong();
		group_last_update_time = in.readLong();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(group_id);
		dest.writeString(group_name);
		dest.writeInt(group_member_id);
		dest.writeString(group_memberIds);
		dest.writeString(group_describe);
		dest.writeInt(group_status);
		dest.writeLong(group_create_time);
		dest.writeLong(group_last_update_time);
	}

	public Group_Bean() {
		this.group_id = -1;
		this.group_name = "";
		this.group_member_id = -1;
		this.group_memberIds = "";
		this.group_describe = "";
		this.group_status = -1;
		this.group_create_time = 0L;
		this.group_last_update_time = 0L;
	}

	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public String getGroup_name() {
		if (group_name==null) {
			group_name="未设置组描述";
		}else if (group_name.isEmpty()) {
			group_name="未设置组描述";
		}
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public Integer getGroup_member_id() {
		return group_member_id;
	}

	public void setGroup_member_id(Integer group_member_id) {
		this.group_member_id = group_member_id;
	}

	public String getGroup_memberIds() {
		return group_memberIds;
	}

	public void setGroup_memberIds(String group_memberIds) {
		this.group_memberIds = group_memberIds;
	}

	public String getGroup_describe() {
		if (group_describe==null) {
			group_describe="未设置组描述";
		}else if (group_describe.isEmpty()) {
			group_describe="未设置组描述";
		}
		return group_describe;
	}

	public void setGroup_describe(String group_describe) {
		this.group_describe = group_describe;
	}

	public Integer getGroup_status() {
		return group_status;
	}

	public void setGroup_status(Integer group_status) {
		this.group_status = group_status;
	}

	public Long getGroup_create_time() {
		return group_create_time;
	}

	public void setGroup_create_time(Long group_create_time) {
		this.group_create_time = group_create_time;
	}

	public Long getGroup_last_update_time() {
		return group_last_update_time;
	}

	public void setGroup_last_update_time(Long group_last_update_time) {
		this.group_last_update_time = group_last_update_time;
	}

	@Override
	public String toString() {
		return "Group_Bean [group_id=" + group_id + ", group_name=" + group_name + ", group_member_id=" + group_member_id + ", group_memberIds=" + group_memberIds + ", group_describe="
				+ group_describe + ", group_status=" + group_status + ", group_create_time=" + group_create_time + ", group_last_update_time=" + group_last_update_time + "]";
	}

	public static final Parcelable.Creator<Group_Bean> CREATOR = new Parcelable.Creator<Group_Bean>() {
		@Override
		public Group_Bean createFromParcel(Parcel in) {
			return new Group_Bean(in);
		}

		@Override
		public Group_Bean[] newArray(int size) {
			return new Group_Bean[size];
		}
	};

	public static Parcelable.Creator<Group_Bean> getCreator() {
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
			if (o instanceof Group_Bean) {
				int oHashCode = ((Group_Bean) o).hashCode();
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
			result = group_id.hashCode() + 36;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Group_Bean clone() throws CloneNotSupportedException {
		Group_Bean o = null;
		try {
			o = new Group_Bean();
			o.group_id = (Integer) group_id;
			o.group_name = (String) group_name;
			o.group_member_id = (Integer) group_member_id;
			o.group_memberIds = (String) group_memberIds;
			o.group_describe = (String) group_describe;
			o.group_status = (Integer) group_status;
			o.group_create_time = (Long) group_create_time;
			o.group_last_update_time = (Long) group_last_update_time;
		} catch (Exception e) {
			CloneNotSupportedException e1 = new CloneNotSupportedException();
			throw e1;
		}
		return o;
	}
}
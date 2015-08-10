package com.helper.mistletoe.m.pojo;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

public class Template_Bean implements Parcelable {

	@SerializedName("name")
	String template_name;// 模板名称
	@SerializedName("task")
	String template_task;// 模板task
	@SerializedName("pic")
	String template_pic;// 模板图标

	protected Template_Bean(Parcel in) {
		template_name = in.readString();
		template_task = in.readString();
		template_pic = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(template_name);
		dest.writeString(template_task);
		dest.writeString(template_pic);
	}

	public Template_Bean() {
		this.template_name = "";// 模板名称
		this.template_task = "";// 模板task
		this.template_pic = "";// 模板图标
	}

	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}

	public String getTemplate_task() {
		return template_task;
	}

	public void setTemplate_task(String template_task) {
		this.template_task = template_task;
	}

	public String getTemplate_pic() {
		return template_pic;
	}

	public void setTemplate_pic(String template_pic) {
		this.template_pic = template_pic;
	}

	@Override
	public String toString() {
		return "Template_Bean [template_name=" + template_name + ", template_task=" + template_task + ", template_pic=" + template_pic + "]";
	}

	public static final Parcelable.Creator<Template_Bean> CREATOR = new Parcelable.Creator<Template_Bean>() {
		@Override
		public Template_Bean createFromParcel(Parcel in) {
			return new Template_Bean(in);
		}

		@Override
		public Template_Bean[] newArray(int size) {
			return new Template_Bean[size];
		}
	};

	public static Parcelable.Creator<Template_Bean> getCreator() {
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
			if (o instanceof Template_Bean) {
				int oHashCode = ((Template_Bean) o).hashCode();
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
			result = template_name.hashCode() + 36;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Template_Bean clone() throws CloneNotSupportedException {
		Template_Bean o = null;
		try {
			o = new Template_Bean();
			o.template_name = (String) template_name;
		} catch (Exception e) {
			CloneNotSupportedException e1 = new CloneNotSupportedException();
			throw e1;
		}
		return o;
	}
}
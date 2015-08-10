package com.helper.mistletoe.m.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 张久瑞 on 15/3/4.
 */
public class NoteTag_Pojo {
	@SerializedName("target_id")
	public Integer targetId;//目标Id
	public Integer id;// 编号
	public String tag;// 名称

	// TODO 以下为Get Set函数
	public int getId() {
		if (id == null) {
			id = 0;
		}
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		if (tag == null) {
			tag = "";
		}
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getTarggetId() {
		if (targetId==null) {
			targetId=0;
		}
		return targetId;
	}
	public void setTarggetId(Integer targgetId) {
		this.targetId = targgetId;
	}

	@Override
	public String toString() {
		return "NoteTag_Pojo [targetId=" + targetId + ", id=" + id + ", tag=" + tag + "]";
	}
	
}
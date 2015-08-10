package com.helper.mistletoe.m.pojo;

import com.helper.mistletoe.m.pojo.Task_Enum.TaskStatus;
import com.helper.mistletoe.m.sjb.core.SimpleDataBase;
import com.helper.mistletoe.util.Const_DB;

public class Task_Pojo extends SimpleDataBase {
	public Integer task_id;// TaskId
	public Integer creator_id;// 创建者Id
	public Integer weights;// 权重
	public Integer owner_id;// 分配给的人Id
	public String owner;// 分配给的人名字
	public Integer status;// Task的状态
	public String client_ref_id;// 本地Tag
	public String description;// 描述
	public Integer loc_TargetId;// 对应的目标Id
	public Long begin_time;// 任务开始时间
	public Long end_time;// 任务完成时间

	@Override
	public String getTableName() {
		return Const_DB.TABLE_TASK;
	}

	@Override
	public String getPrimaryKeyName() {
		return Const_DB.TABLE_TASK_KEY;
	}

	// TODO 以下为Get Set函数

	public int getTask_id() {
		if (task_id == null) {
			task_id = 0;
		}
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public int getCreator_id() {
		if (creator_id == null) {
			creator_id = 0;
		}
		return creator_id;
	}

	public void setCreator_id(int creator_id) {
		this.creator_id = creator_id;
	}

	public int getWeights() {
		if (weights == null) {
			weights = 0;
		}
		return weights;
	}

	public void setWeights(int weights) {
		this.weights = weights;
	}

	public int getOwner_id() {
		if (owner_id == null) {
			owner_id = 0;
		}
		return owner_id;
	}

	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}

	public String getOwner() {
		if (owner == null) {
			owner = "";
		}
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public TaskStatus getStatus() {
		if (status == null) {
			status = TaskStatus.Draft.toInt();
		}
		return TaskStatus.valueOf(status);
	}

	public void setStatus(TaskStatus status) {
		this.status = status.toInt();
	}

	public String getClient_ref_id() {
		if (client_ref_id == null) {
			client_ref_id = "";
		}
		return client_ref_id;
	}

	public void setClient_ref_id(String client_ref_id) {
		this.client_ref_id = client_ref_id;
	}

	public String getDescription() {
		if (description == null) {
			description = "";
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLoc_TargetId() {
		if (loc_TargetId == null) {
			loc_TargetId = 0;
		}
		return loc_TargetId;
	}

	public void setLoc_TargetId(int loc_TargetId) {
		this.loc_TargetId = loc_TargetId;
	}

	public Long getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(Long begin_time) {
		this.begin_time = begin_time;
	}

	public Long getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Long end_time) {
		this.end_time = end_time;
	}

}
package com.helper.mistletoe.m.pojo;

import java.util.ArrayList;

import org.json.JSONArray;

import com.google.gson.annotations.SerializedName;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRecordType;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRunningState;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetType;
import com.helper.mistletoe.m.sjb.core.SimpleDataBase;
import com.helper.mistletoe.util.Const_DB;
import com.helper.mistletoe.util.Transformation_Util;

class Target_Pojo extends SimpleDataBase {
	/**
	 * 置顶
	 */
	public final static int TARGET_STICK_STICK = 1;
	/**
	 * 取消置顶
	 */
	public final static int TARGET_STICK_UNSTICK = 0;
	/**
	 * 接受通知
	 */
	public final static int TARGET_ACCEPTPUSH_ACCEPT = 1;
	/**
	 * 不接受通知
	 */
	public final static int TARGET_ACCEPTPUSH_UNACCEPT = 0;
	/**
	 * 隔离模式
	 */
	public final static int TARGET_VIEWFLAG_ISOLATION = 0;
	/**
	 * 公开模式
	 */
	public final static int TARGET_VIEWFLAG_OPEN = 1;

	public Integer target_id;// 目标ID
	public Integer creator_id;// 目标创建者ID
	public String summary;// 目标名称
	public String description;// 目标详细描述
	@SerializedName("status_client")
	public Integer status;// 用于检索的状态
	public Integer status_server;// 目标状态
	public Long create_time;// 目标创建时间
	public Long start_time;// 目标开始时间
	public Long eta_time;// 目标估计完成时间
	public Long due_time;// 目标截至时间
	public String final_time;// 目标实际完成时间
	public Double budget;// 目标预算
	public String cost;// 目标已经发生的费用
	public Integer view_flag;// 目标可视状态
	public Integer note_count;// 目标备注数量
	public Long last_updated_time;// 目标最后一次更新时间
	public Integer unread_note_number;// 目标中未读的备注信息数
	public Integer unread_helper_change_number;// 目标中未读的帮手变化数
	public String last_note;// 目标的最后一个备注
	public Integer target_flag;// 目标的颜色标签
	public Integer target_stick;// 目标置顶
	public Long target_stick_time;// 置顶时间
	@SerializedName("client_ref_id")
	public String loc_TargetTag;
	public TargetMemberList_Bean loc_TargetMember;
	public ArrayList<Task_Bean> tasks;
	public String loc_TasksString;
	public Integer accept_push_msg;
	public String loc_MembersString;
	public String target_type; // 通用=1，系统=2，市场=3，置顶=4（空默认为通用）
	public ArrayList<Integer> head_pics; // 目标图片数组，默认为空
	public String avatar_file_id;// 目标创建者的头像
	public TargetRecordType recordType_Loc;// 存在数据库中的目标类型
	public String attitude;// 被点赞的次数

	@Override
	public String getTableName() {
		return Const_DB.TABLE_TARGETS;
	}

	@Override
	public String getPrimaryKeyName() {
		return Const_DB.TABLE_TARGETS_KEY;
	}

	// TODO 以下为Get Set函数
	public int getTarget_id() {
		if (target_id == null) {
			target_id = 0;
		}
		return target_id;
	}

	public void setTarget_id(int target_id) {
		this.target_id = target_id;
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

	public String getSummary() {
		if (summary == null) {
			summary = "";
		}
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public TargetRunningState getStatus() {
		if (status == null) {
			status = TargetRunningState.Running.toInt();
		}
		return TargetRunningState.valueOf(status);
	}

	public void setStatus(TargetRunningState status) {
		this.status = status.toInt();
	}

	public long getCreate_time() {
		if (create_time == null) {
			create_time = 0L;
		}
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	public Long getStart_time() {
		if (start_time == null) {
			start_time = 0L;
		}
		return start_time;
	}

	public void setStart_time(Long start_time) {
		this.start_time = start_time;
	}

	public Long getEta_time() {
		if ((eta_time == null) || (eta_time < 1L)) {
			eta_time = 0L;
		}
		return eta_time;
	}

	public void setEta_time(Long eta_time) {
		this.eta_time = eta_time;
	}

	public Long getDue_time() {
		if (due_time == null) {
			due_time = 0L;
		}
		return due_time;
	}

	public void setDue_time(Long due_time) {
		this.due_time = due_time;
	}

	public String getFinal_time() {
		if (final_time == null) {
			final_time = "";
		}
		return final_time;
	}

	public void setFinal_time(String final_time) {
		this.final_time = final_time;
	}

	public Double getBudget() {
		if (budget == null) {
			budget = 0.0;
		}
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public String getCost() {
		if (cost == null) {
			cost = "";
		}
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public int getView_flag() {
		if (view_flag == null) {
			view_flag = 0;
		}
		return view_flag;
	}

	public void setView_flag(int view_flag) {
		this.view_flag = view_flag;
	}

	public Integer getNote_count() {
		if (note_count == null) {
			note_count = 0;
		}
		return note_count;
	}

	public void setNote_count(Integer note_count) {
		this.note_count = note_count;
	}

	public Long getLast_updated_time() {
		if (last_updated_time == null) {
			last_updated_time = 0L;
		}
		return last_updated_time;
	}

	public void setLast_updated_time(Long last_updated_time) {
		this.last_updated_time = last_updated_time;
	}

	public Integer getUnread_note_number() {
		if (unread_note_number == null) {
			unread_note_number = 0;
		}
		return unread_note_number;
	}

	public void setUnread_note_number(Integer unread_note_number) {
		this.unread_note_number = unread_note_number;
	}

	public Integer getUnread_helper_change_number() {
		if (unread_helper_change_number == null) {
			unread_helper_change_number = 0;
		}
		return unread_helper_change_number;
	}

	public void setUnread_helper_change_number(Integer unread_helper_change_number) {
		this.unread_helper_change_number = unread_helper_change_number;
	}

	public String getLast_note() {
		if (last_note == null) {
			last_note = "";
		}
		return last_note;
	}

	public void setLast_note(String last_note) {
		this.last_note = last_note;
	}

	public Integer getTarget_flag() {
		if (target_flag == null) {
			target_flag = 0;
		}
		return target_flag;
	}

	public void setTarget_flag(Integer target_flag) {
		this.target_flag = target_flag;
	}

	public Integer getTarget_stick() {
		if (target_stick == null) {
			target_stick = 0;
		}
		return target_stick;
	}

	public void setTarget_stick(Integer target_stick) {
		this.target_stick = target_stick;
	}

	public Long getTarget_stick_time() {
		if (target_stick_time == null) {
			target_stick_time = 0L;
		}
		return target_stick_time;
	}

	public void setTarget_stick_time(Long target_stick_time) {
		this.target_stick_time = target_stick_time;
	}

	public String getLoc_TargetTag() {
		if (loc_TargetTag == null) {
			loc_TargetTag = "";
		}
		return loc_TargetTag;
	}

	public void setLoc_TargetTag(String loc_TargetTag) {
		this.loc_TargetTag = loc_TargetTag;
	}

	public Integer getAccept_push_msg() {
		if (accept_push_msg == null) {
			accept_push_msg = 1;
		}
		return accept_push_msg;
	}

	public void setAccept_push_msg(Integer accept_push_msg) {
		this.accept_push_msg = accept_push_msg;
	}

	public MemberStatus getStatus_server() {
		if (status_server == null) {
			status_server = MemberStatus.Discussing.toInt();
		}
		return MemberStatus.valueOf((int) status_server, MemberStatus.Discussing);
	}

	public void setStatus_server(MemberStatus status_server) {
		this.status_server = status_server.toInt();
	}

	public String getLoc_MembersString() {
		if (loc_MembersString == null) {
			loc_MembersString = "";
		}
		return loc_MembersString;
	}

	public void setLoc_MembersString(String loc_MembersString) {
		this.loc_MembersString = loc_MembersString;
	}

	public TargetMemberList_Bean getLoc_TargetMember() {
		if (loc_TargetMember == null) {
			loc_TargetMember = new TargetMemberList_Bean();
		}
		return loc_TargetMember;
	}

	public void setLoc_TargetMember(TargetMemberList_Bean loc_TargetMember) {
		this.loc_TargetMember = loc_TargetMember;
	}

	public TargetType getTarget_type() {
		if (target_type == null) {
			target_type = "" + TargetType.General.toInt();
		}
		return TargetType.valueOf(Transformation_Util.String2int(target_type, TargetType.General.toInt()));
	}

	public void setTarget_type(TargetType target_type) {
		this.target_type = "" + target_type.toInt();
	}

	public void setTarget_type(int target_type) {
		setTarget_type(TargetType.valueOf(target_type));
	}

	public ArrayList<Integer> getHead_pics() {
		if (head_pics == null) {
			head_pics = new ArrayList<Integer>();
		}
		return head_pics;
	}

	public ArrayList<Task_Bean> getTasks() {
		if (tasks == null) {
			tasks = new ArrayList<Task_Bean>();
		}
		return tasks;
	}

	public void setTasks(ArrayList<Task_Bean> tasks) {
		this.tasks = tasks;
	}

	public JSONArray getHead_pics_JSONArray() {
		return Transformation_Util.ArrayList$Integer2JSONArray(getHead_pics());
	}

	public String getHead_pics_String() {
		return Transformation_Util.ArrayList$Integer2String(getHead_pics());
	}

	public void setHead_pics(ArrayList<Integer> head_pics) {
		this.head_pics = head_pics;
	}

	public void setHead_pics(String head_pics) {
		setHead_pics(Transformation_Util.String2ArrayList$Integer(head_pics));
	}

	public int getAvatar_file_id() {
		if (avatar_file_id == null) {
			avatar_file_id = String.valueOf((int) -1);
		}
		return Transformation_Util.String2int(avatar_file_id, -1);
	}

	public void setAvatar_file_id(int avatar_file_id) {
		this.avatar_file_id = "" + avatar_file_id;
	}

	public TargetRecordType getRecordType_Loc() {
		if (recordType_Loc == null) {
			recordType_Loc = TargetRecordType.Tradition;
		}
		return recordType_Loc;
	}

	public void setRecordType_Loc(TargetRecordType recordType_Loc) {
		this.recordType_Loc = recordType_Loc;
	}

	public int getAttitude() {
		if (attitude == null) {
			attitude = "" + 0;
		}
		return Transformation_Util.String2int(attitude);
	}

	public void setAttitude(int attitude) {
		this.attitude = "" + attitude;
	}

}
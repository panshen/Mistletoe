package com.helper.mistletoe.m.pojo;

import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberRole;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRecordType;
import com.helper.mistletoe.util.Const_DB;
import com.helper.mistletoe.util.Transformation_Util;

public class TargetMember_Pojo extends Helpers_Sub_Compatible {
	public int fk_TargetId;// 与之关联的TargetId
	public int member_id;// 帮手的ID
	public String member_role;// 成员角色，创建者，帮手
	public String member_status;// 成员状态
	public Long read_time;// 这个参数仅用于进度更新阅读状态：阅读时间
	public Integer member_avatar_file_id;// 帮手的头像Id
	public String member_name;// 帮手的名字
	public Boolean in_waiting_status;// 帮手状态修改是不是已经在等待回复状态
	public Integer seconds_countdown;// 帮手状态修改剩余时间
	public TargetRecordType recordType_Loc;// 存在数据库中的目标类型

	@Override
	public String getTableName() {
		return Const_DB.TABLE_TARGETSMEMBER;
	}

	@Override
	public String getPrimaryKeyName() {
		return Const_DB.TABLE_TARGETSMEMBER_KEY;
	}

	// TODO 以下为Get Set函数
	public int getFk_TargetId() {
		return fk_TargetId;
	}

	public void setFk_TargetId(int fk_TargetId) {
		this.fk_TargetId = fk_TargetId;
	}

	public int getMember_id() {
		if (member_id < 1) {
			member_id = getHelper_id();
		}
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public MemberRole getMember_role() {
		return MemberRole.valueOf(Transformation_Util.String2int(member_role, -1), MemberRole.Helper);
	}

	public void setMember_role(MemberRole member_role) {
		this.member_role = String.valueOf(member_role.toInt());
	}

	public MemberStatus getMember_status() {
		return MemberStatus.valueOf(Transformation_Util.String2int(member_status, -1), MemberStatus.Discussing);
	}

	public void setMember_status(MemberStatus member_status) {
		this.member_status = String.valueOf(member_status.toInt());
	}

	public Long getRead_time() {
		if (read_time == null) {
			read_time = 0L;
		}
		return read_time;
	}

	public void setRead_time(Long read_time) {
		this.read_time = read_time;
	}

	public int getMember_avatar_file_id() {
		if (member_avatar_file_id == null) {
			member_avatar_file_id = 0;
		}
		return member_avatar_file_id;
	}

	public void setMember_avatar_file_id(int member_avatar_file_id) {
		this.member_avatar_file_id = member_avatar_file_id;
	}

	public String getMember_name() {
		if (member_name == null) {
			member_name = "";
		}
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	@Deprecated
	public boolean getIn_waiting_status() {
		if (in_waiting_status == null) {
			in_waiting_status = false;
		}
		return in_waiting_status;
	}

	@Deprecated
	public void setIn_waiting_status(boolean in_waiting_status) {
		this.in_waiting_status = in_waiting_status;
	}

	public int getSeconds_countdown() {
		if (seconds_countdown == null) {
			seconds_countdown = 0;
		}
		return seconds_countdown;
	}

	public void setSeconds_countdown(int seconds_countdown) {
		this.seconds_countdown = seconds_countdown;
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

}
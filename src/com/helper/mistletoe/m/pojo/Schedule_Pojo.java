package com.helper.mistletoe.m.pojo;

import java.util.ArrayList;

import org.json.JSONArray;

import com.google.gson.annotations.SerializedName;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleHighlightFlag;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleRevokeStatus;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleType;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleViewFlag;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRecordType;
import com.helper.mistletoe.m.sjb.core.SimpleDataBase;
import com.helper.mistletoe.util.Const_DB;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Transformation_Util;

class Schedule_Pojo extends SimpleDataBase {
	public Integer id;// 备注id
	public String creator_id;// 创建人id
	public String note_type;// 备注类型
	public String highlight_flag;// 是否置顶
	public Long create_time;// 创建时间
	public String content;// 备注内容
	public Long last_updated_time;// 最后更新时间
	public String read_percentage;// 阅读人数占应该阅读人数的比
	public String file_id;// 文件Id
	public String reminder_time;// 提醒时间
	public String review_rank;
	public Double gps_latitude;
	public Double gps_longitude;
	public Float cost;
	public String status;// 是否被撤回
	@SerializedName("client_ref_id")
	public String loc_ItemTag;
	public Integer loc_TargetId;
	public TargetMember_Bean loc_Creater;
	public ArrayList<TargetMember_Bean> loc_Sendto;
	public ArrayList<TargetMember_Bean> loc_Member;
	public String loc_FilePath;
	public String loc_MembersString;
	public String loc_ReceiverString;
	public String view_flag;// 公开还是私聊
	public String cost_type;// 费用类型
	public String cost_desc;// 费用描述
	public String transaction_time;// 交易时间
	public Float balance;// 余额
	public String creator_name;// 创建者的名字
	public Integer creator_avatar_file_id;// 创建者的头像
	public TargetRecordType recordType_Loc;// 存在数据库中的目标类型
	public ArrayList<Integer> owner_id;// 帮谁记得费用ID
	public Integer tag_id;// 费用的类型，服务器预置的几种类型
	public String owner;// 帮谁记得费用
	public TargetMember_Bean loc_Owner;// 这个进度更新的Owner的JavaBean

	@Override
	public String getTableName() {
		return Const_DB.TABLE_SCHEDULE;
	}

	@Override
	public String getPrimaryKeyName() {
		return Const_DB.TABLE_TARGETS_KEY;
	}

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

	public String getCreator_id() {
		if (creator_id == null) {
			creator_id = "";
		}
		return creator_id;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}

	public ScheduleType getNote_type() {
		if (note_type == null) {
			note_type = "" + ScheduleType.Unknown.toInt();
		}
		return ScheduleType.valueOf(Transformation_Util.String2int(note_type));
	}

	public void setNote_type(ScheduleType note_type) {
		this.note_type = "" + note_type.toInt();
	}

	public ScheduleHighlightFlag getHighlight_flag() {
		if (highlight_flag == null) {
			highlight_flag = "" + ScheduleHighlightFlag.No.toInt();
		}
		return ScheduleHighlightFlag.valueOf((int) Transformation_Util.String2int(highlight_flag));
	}

	public void setHighlight_flag(ScheduleHighlightFlag highlight_flag) {
		this.highlight_flag = "" + highlight_flag.toInt();
	}

	public Long getCreate_time() {
		if (create_time == null) {
			create_time = 0L;
		}
		return create_time;
	}

	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}

	public String getContent() {
		if (content == null) {
			content = "";
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getRead_percentage() {
		if (read_percentage == null) {
			read_percentage = "";
		}
		return read_percentage;
	}

	public void setRead_percentage(String read_percentage) {
		this.read_percentage = read_percentage;
	}

	public String getLoc_ItemTag() {
		if (loc_ItemTag == null) {
			loc_ItemTag = "";
		}
		return loc_ItemTag;
	}

	public void setLoc_ItemTag(String loc_ItemTag) {
		this.loc_ItemTag = loc_ItemTag;
	}

	public Integer getLoc_TargetId() {
		if (loc_TargetId == null) {
			loc_TargetId = 0;
		}
		return loc_TargetId;
	}

	public void setLoc_TargetId(Integer loc_TargetId) {
		this.loc_TargetId = loc_TargetId;
	}

	public TargetMember_Bean getLoc_Creater() {
		if (loc_Creater == null) {
			loc_Creater = new TargetMember_Bean();
		}
		return loc_Creater;
	}

	public void setLoc_Creater(TargetMember_Bean loc_Creater) {
		this.loc_Creater = loc_Creater;
	}

	public ArrayList<TargetMember_Bean> getLoc_Sendto() {
		if (loc_Sendto == null) {
			loc_Sendto = new ArrayList<TargetMember_Bean>();
		}
		return loc_Sendto;
	}

	public void setLoc_Sendto(ArrayList<TargetMember_Bean> loc_Sendto) {
		this.loc_Sendto = loc_Sendto;
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

	public String getReminder_time() {
		if (reminder_time == null) {
			reminder_time = "";
		}
		return reminder_time;
	}

	public void setReminder_time(String reminder_time) {
		this.reminder_time = reminder_time;
	}

	public String getReview_rank() {
		if (review_rank == null) {
			review_rank = "";
		}
		return review_rank;
	}

	public void setReview_rank(String review_rank) {
		this.review_rank = review_rank;
	}

	public Double getGps_latitude() {
		if (gps_latitude == null) {
			gps_latitude = 0.0D;
		}
		return gps_latitude;
	}

	public void setGps_latitude(Double gps_latitude) {
		this.gps_latitude = gps_latitude;
	}

	public Double getGps_longitude() {
		if (gps_longitude == null) {
			gps_longitude = 0.0D;
		}
		return gps_longitude;
	}

	public void setGps_longitude(Double gps_longitude) {
		this.gps_longitude = gps_longitude;
	}

	public float getCost() {
		if (cost == null) {
			cost = 0.0F;
		}
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public ArrayList<TargetMember_Bean> getLoc_Member() {
		if (loc_Member == null) {
			loc_Member = new ArrayList<TargetMember_Bean>();
		}
		return loc_Member;
	}

	public void setLoc_Member(ArrayList<TargetMember_Bean> loc_Member) {
		this.loc_Member = loc_Member;
	}

	public String getLoc_FilePath() {
		if (loc_FilePath == null) {
			loc_FilePath = "";
		}
		return loc_FilePath;
	}

	public void setLoc_FilePath(String loc_FilePath) {
		this.loc_FilePath = loc_FilePath;
	}

	public ScheduleRevokeStatus getStatus() {
		if (status == null) {
			status = "" + ScheduleRevokeStatus.Normal.toInt();
		}
		return ScheduleRevokeStatus.valueOf((int) Transformation_Util.String2int(status));
	}

	public void setStatus(ScheduleRevokeStatus status) {
		this.status = "" + status.toInt();
	}

	public String getLoc_MembersString() {
		if (loc_MembersString == null) {
			loc_MembersString = new JSONArray().toString();
		}
		return loc_MembersString;
	}

	public void setLoc_MembersString(String loc_MembersString) {
		this.loc_MembersString = loc_MembersString;
	}

	public ScheduleViewFlag getView_flag() {
		if (view_flag == null) {
			view_flag = "" + ScheduleViewFlag.Public.toInt();
		}
		return ScheduleViewFlag.valueOf(Transformation_Util.String2int(view_flag));
	}

	public void setView_flag(ScheduleViewFlag view_flag) {
		this.view_flag = "" + view_flag.toInt();
	}

	public String getLoc_ReceiverString() {
		if (loc_ReceiverString == null) {
			loc_ReceiverString = "";
		}
		return loc_ReceiverString;
	}

	public void setLoc_ReceiverString(String loc_ReceiverString) {
		this.loc_ReceiverString = loc_ReceiverString;
	}

	@Deprecated
	public String getCost_type() {
		if (cost_type == null) {
			cost_type = "";
		}
		return cost_type;
	}

	public void setCost_type(String cost_type) {
		this.cost_type = cost_type;
	}

	public String getCost_desc() {
		if (cost_desc == null) {
			cost_desc = "";
		}
		return cost_desc;
	}

	public void setCost_desc(String cost_desc) {
		this.cost_desc = cost_desc;
	}

	public long getTransaction_time() {
		if (transaction_time == null) {
			transaction_time = String.valueOf(0L);
		}
		return Transformation_Util.Sring2long(transaction_time);
	}

	public void setTransaction_time(long transaction_time) {
		this.transaction_time = String.valueOf(transaction_time);
	}

	public float getBalance() {
		if (balance == null) {
			balance = 0.0F;
		}
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public String getCreator_name() {
		if (creator_name == null) {
			creator_name = "";
		}
		return creator_name;
	}

	public void setCreator_name(String creator_name) {
		this.creator_name = creator_name;
	}

	public int getCreator_avatar_file_id() {
		if (creator_avatar_file_id == null) {
			creator_avatar_file_id = 0;
		}
		return creator_avatar_file_id;
	}

	public void setCreator_avatar_file_id(int creator_avatar_file_id) {
		this.creator_avatar_file_id = creator_avatar_file_id;
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

	/**
	 * 取出一个Owner_id，如果Owner_id数组中有多个，只会取出第一个。
	 * 
	 * @return Owner_id数组中的一个id
	 */
	public int getOwner_id_int() {
		int result = 0;
		try {
			for (Integer i : getOwner_id()) {
				if ((i != null) && (i > 0)) {
					result = i;
					break;
				}
			}
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 把Owner_id数组序列化为String用来保存
	 * 
	 * @return 序列化之后的数组
	 */
	public String getOwner_id_String() {
		return getOwner_id_JSONArray().toString();
	}

	/**
	 * 把Owner_id转换为JSONArray
	 * 
	 * @return JSONArray数组
	 */
	public JSONArray getOwner_id_JSONArray() {
		return Transformation_Util.ArrayList$Integer2JSONArray(getOwner_id());
	}

	public ArrayList<Integer> getOwner_id() {
		if (owner_id == null) {
			owner_id = new ArrayList<Integer>();
		}
		return owner_id;
	}

	/**
	 * 从序列化的Owner_id数组中还原
	 * 
	 * @param owner_id
	 *            序列化的Owner_id数组
	 */
	public void setOwner_id(String owner_id) {
		try {
			ArrayList<Integer> tList = Transformation_Util.String2ArrayList$Integer(owner_id);
			if ((tList == null) || (tList.size() < 1)) {
				tList.add(Transformation_Util.String2int(getCreator_id()));
			}
			setOwner_id(tList);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setOwner_id(ArrayList<Integer> owner_id) {
		this.owner_id = owner_id;
	}

	public int getTag_id() {
		if (tag_id == null) {
			tag_id = 0;
		}
		return tag_id;
	}

	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
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

	public TargetMember_Bean getLoc_Owner() {
		if (loc_Owner == null) {
			loc_Owner = new TargetMember_Bean();
		}
		return loc_Owner;
	}

	public void setLoc_Owner(TargetMember_Bean loc_Owner) {
		this.loc_Owner = loc_Owner;
	}

}
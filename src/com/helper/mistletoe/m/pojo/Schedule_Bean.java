package com.helper.mistletoe.m.pojo;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.helper.mistletoe.m.db.TargetMemberManager;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleHighlightFlag;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleRevokeStatus;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleType;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleViewFlag;
import com.helper.mistletoe.m.work.be.cloud.CreateSchedule_Event;
import com.helper.mistletoe.m.work.be.cloud.TopSchedule_Mode;
import com.helper.mistletoe.m.work.be.cloud.WithdrawSchedule_Mode;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.SnaBitmap.ImageSize;
import com.helper.mistletoe.v.snaimageview.v2.SnaBitmapV2;

import de.greenrobot.event.EventBus;

public class Schedule_Bean extends Schedule_Pojo {
	public void foundCreater(int targetId, Context context) {
		try {
			TargetMember_Bean t_Member = TargetMemberManager.getInstance(context).readTargetMember(targetId,
					Integer.valueOf(getCreator_id()));
			if (t_Member != null) {
				setLoc_Creater(t_Member);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void foundReciver() {
		try {
			if (getView_flag() == ScheduleViewFlag.Private) {
				JSONArray tReceiverList = new JSONArray(getLoc_ReceiverString());
				Gson tGson = new Gson();
				getLoc_Sendto().clear();
				for (int i = 0; i < tReceiverList.length(); i++) {
					JSONObject tObject = new JSONObject(tReceiverList.getString(i));
					tObject.put("member_name", tObject.getString("user_name"));
					tObject.put("member_avatar_file_id", tObject.getString("user_avatar_file_id"));
					tObject.put("member_id", tObject.getString("user_id"));
					TargetMember_Bean tMember = tGson.fromJson(tObject.toString(), TargetMember_Bean.class);
					if (((int) tMember.getHelper_id()) != (Transformation_Util.String2int(getCreator_id(), -1))) {
						getLoc_Sendto().add(tMember);
					}
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void sendSchedule(Context context) {
		try {
			EventBus.getDefault().post(new CreateSchedule_Event(this));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void topSchedule(Context context) {
		try {
			// 置顶进度更新
			ScheduleHighlightFlag tFlag = ScheduleHighlightFlag.No;
			if (getHighlight_flag() == ScheduleHighlightFlag.Yes) {
				tFlag = ScheduleHighlightFlag.No;
			} else {
				tFlag = ScheduleHighlightFlag.Yes;
			}
			TopSchedule_Mode mode = new TopSchedule_Mode("" + getLoc_TargetId(), getId(), tFlag.toInt());
			mode.publishWork(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void withdrawSchedule(Context context) {
		try {
			// 取消进度更新
			WithdrawSchedule_Mode mode = new WithdrawSchedule_Mode("" + getLoc_TargetId(), "" + getId());
			mode.publishWork(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public SnaBitmap getContentImage() {
		return getContentImage(ImageSize.Middle.toInt());
	}

	public SnaBitmap getContentImage(int sz) {
		SnaBitmap result = null;
		try {
			result = new SnaBitmapV2();
			if (getId() > 0) {
				result.setPath(getFile_id(), sz);
			} else {
				result.setPath(new File(getLoc_FilePath()));
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public String getShowName() {
		String result = "[未命名]";
		try {
			// 如果是普通进度更新，就取创建者的姓名，如果是费用类型进度更新有OwnerID，就取Owner的姓名
			if (getNote_type() == ScheduleType.CostApply) {
				result = getShowName_Owner();
				if (Transformation_Util.String2int(getCreator_id()) != getOwner_id_int()) {
					result += "（" + getShowName_Creater() + "代记）";
				}
			} else {
				result = getShowName_Creater();
			}
		} catch (Exception e) {
			result = "[未命名]";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public String getShowName_Creater() {
		String result = "[未命名]";
		try {
			result = getLoc_Creater().getShowName();
			if ((result.equals("")) || (result.equals("[未命名]"))) {
				result = getCreator_name();
			}
		} catch (Exception e) {
			result = "[未命名]";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public String getShowName_Owner() {
		String result = "[未命名]";
		try {
			result = getLoc_Owner().getShowName();
			if ((result.equals("")) || (result.equals("[未命名]"))) {
				result = getOwner();
			}
		} catch (Exception e) {
			result = "[未命名]";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public SnaBitmap getShowSnaBitmap() {
		return getShowSnaBitmap(ImageSize.Small);
	}

	public SnaBitmap getShowSnaBitmap(ImageSize sz) {
		SnaBitmap result = null;
		try {
			boolean contentIsSafe = true;
			if (sz == null) {
				contentIsSafe = false;
			}
			if (contentIsSafe) {
				result = new SnaBitmapV2();
				int tImageId = 0;
				// 如果是普通进度更新，就取创建者的头像，如果是费用类型进度更新有OwnerID，就取Owner的头像
				if ((getNote_type() == ScheduleType.CostApply) && (getOwner_id_int() > 0)) {
					// 取出Owner头像
					tImageId = getLoc_Owner().getShowImageId();
				} else {
					tImageId = getLoc_Creater().getShowImageId();
				}
				// 如果没有取到结果
				if (tImageId < 1) {
					tImageId = getCreator_avatar_file_id();
				}
				result.setPath(tImageId, sz.toInt());
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public void copiedToTheClipboard(Context context) {
		try {
			ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			clipboardManager.setPrimaryClip(ClipData.newPlainText(null, getContent()));
			if (clipboardManager.hasPrimaryClip()) {
				clipboardManager.getPrimaryClip().getItemAt(0).getText();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public TargetMember_Bean getFirstSendTo() {
		TargetMember_Bean result = new TargetMember_Bean();
		try {
			for (Iterator<TargetMember_Bean> iterator = getLoc_Sendto().iterator(); iterator.hasNext();) {
				result = iterator.next();
				if (result != null) {
					break;
				}
			}
		} catch (Exception e) {
			result = new TargetMember_Bean();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public String getSendToNames() {
		String result = "";
		try {
			result = "";
			for (Iterator<TargetMember_Bean> iterator = getLoc_Sendto().iterator(); iterator.hasNext();) {
				TargetMember_Bean temp = iterator.next();
				if (!result.trim().equals("")) {
					result += "，";
				}
				result += temp.getShowName();
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public boolean sendTimeLessThan5Minutes() {
		boolean result = false;
		try {
			long tSendTime = getCreate_time() * 1000;
			long tNowTiem = TimeTool_Utils.getNowTime();
			if ((tNowTiem - tSendTime) <= 5 * 60 * 1000) {
				result = true;
			}
		} catch (Exception e) {
			result = false;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public ScheduleType getScheduleType_IncludeRevoke() {
		ScheduleType result = ScheduleType.Unknown;
		try {
			ScheduleRevokeStatus tStatus = getStatus();
			if (tStatus == ScheduleRevokeStatus.Revoke) {
				result = ScheduleType.Revoke;
			} else {
				result = getNote_type();
			}
		} catch (Exception e) {
			result = ScheduleType.Unknown;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public String getCost_type(NoteTagList_Bean noteTagList) {
		String result = "";
		try {
			String tNewTag = noteTagList.getTagName(getTag_id());
			if (tNewTag.equals("")) {
				result = getCost_type();
			} else {
				result = tNewTag;
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 发送一个费用管理格式进度更新
	 * 
	 * @param context
	 *            设备上下文
	 * @param targetId
	 *            目标Id
	 * @param recivers
	 *            私聊接收者
	 * @param cost
	 *            费用
	 * @param cost_type
	 *            费用类型
	 * @param cost_desc
	 *            费用描述
	 * @param transaction_time
	 *            交易时间
	 * @param balance
	 *            余额
	 * @param owner_id
	 *            这笔费用替谁记的
	 * @param tag_id
	 *            费用的分类
	 */
	public static void sendSchedule_CostApply(Context context, int targetId, JSONArray recivers, float cost, String cost_type,
			String cost_desc, long transaction_time, float balance, ArrayList<Integer> owner_id, Integer tag_id) {
		try {
			Schedule_Bean tSchedule = sendSchedule_InternalGetBean(targetId, ScheduleType.CostApply, recivers);

			tSchedule.setCost(cost);
			tSchedule.setCost_type(cost_type);
			tSchedule.setCost_desc(cost_desc);
			tSchedule.setTransaction_time(transaction_time);
			tSchedule.setBalance(balance);
			tSchedule.setOwner_id((ArrayList<Integer>) owner_id);
			if (tag_id != null) {
				tSchedule.setTag_id((int) tag_id);
			}

			EventBus.getDefault().post(new CreateSchedule_Event(tSchedule));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 发送一个文字格式进度更新
	 * 
	 * @param context
	 *            设备上下文
	 * @param targetId
	 *            目标Id
	 * @param recivers
	 *            私聊接收者
	 * @param content
	 *            内容
	 */
	public static void sendSchedule_Text(Context context, int targetId, JSONArray recivers, String content) {
		try {
			Schedule_Bean tSchedule = sendSchedule_InternalGetBean(targetId, ScheduleType.Text, recivers);

			tSchedule.setContent(content);

			if ((content != null) && (!content.trim().equals(""))) {
				EventBus.getDefault().post(new CreateSchedule_Event(tSchedule));
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 发送一个提醒格式进度更新
	 * 
	 * @param context
	 *            设备上下文
	 * @param targetId
	 *            目标Id
	 * @param recivers
	 *            私聊接收者
	 * @param content
	 *            内容
	 * @param reminderTime
	 *            提醒时间
	 */
	public static void sendSchedule_Remind(Context context, int targetId, JSONArray recivers, String content, long reminderTime) {
		try {
			Schedule_Bean tSchedule = sendSchedule_InternalGetBean(targetId, ScheduleType.Remind, recivers);

			tSchedule.setContent(content);
			tSchedule.setReminder_time("" + reminderTime);

			EventBus.getDefault().post(new CreateSchedule_Event(tSchedule));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 发送一个图片格式进度更新
	 * 
	 * @param context
	 *            设备上下文
	 * @param targetId
	 *            目标Id
	 * @param recivers
	 *            私聊接收者
	 * @param content
	 *            内容
	 * @param file
	 *            文件
	 */
	public static void sendSchedule_Image(Context context, int targetId, JSONArray recivers, String content, File file) {
		try {
			Schedule_Bean tSchedule = sendSchedule_InternalGetBean(targetId, ScheduleType.Image, recivers);

			tSchedule.setContent(content);
			tSchedule.setLoc_FilePath(file.getAbsolutePath());

			EventBus.getDefault().post(new CreateSchedule_Event(tSchedule));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static void sendSchedule_Location(Context context, int targetId, JSONArray recivers, String content, Double gps_latitude,
			Double gps_longitude) {
		try {
			Schedule_Bean tSchedule = sendSchedule_InternalGetBean(targetId, ScheduleType.GPS, recivers);

			tSchedule.setContent(content);
			tSchedule.setGps_latitude(gps_latitude);
			tSchedule.setGps_longitude(gps_longitude);

			EventBus.getDefault().post(new CreateSchedule_Event(tSchedule));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private static Schedule_Bean sendSchedule_InternalGetBean(int targetId, ScheduleType type, JSONArray recivers) {
		Schedule_Bean result = null;
		try {
			result = new Schedule_Bean();
			result.setLoc_TargetId(targetId);
			result.setId(-1);
			result.setNote_type(type);
			if (recivers != null) {
				result.setLoc_MembersString(recivers.toString());
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public void setLocation(BDLocation location) {
		try {
			setGps_latitude(location.getLatitude());
			setGps_longitude(location.getLongitude());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public BDLocation getLocation() {
		BDLocation result = new BDLocation();
		try {
			result.setLatitude(getGps_latitude());
			result.setLongitude(getGps_longitude());
		} catch (Exception e) {
			result = new BDLocation();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

}
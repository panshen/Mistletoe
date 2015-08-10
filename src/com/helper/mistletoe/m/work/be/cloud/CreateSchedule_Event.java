package com.helper.mistletoe.m.work.be.cloud;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;

import com.helper.mistletoe.m.db.SchdeuleManager;
import com.helper.mistletoe.m.net.request.CreateSchedule_Schedule_NetRequest;
import com.helper.mistletoe.m.net.request.Image_Upload_NetRequest;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleHighlightFlag;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleRevokeStatus;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleType;
import com.helper.mistletoe.m.pojo.UploadFile_File_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.m.work.base.WorkAsync_Event;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * 由张久瑞在2015年5月4日创建<br/>
 * 创建或者重新发送进度更新
 * 
 * @author 张久瑞
 */
public class CreateSchedule_Event extends WorkAsync_Event {
	private Schedule_Bean mSchedule;// 进度更新JavaBean

	public CreateSchedule_Event(Schedule_Bean schedule) {
		super();
		try {
			setSchedule(schedule);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork(Context context) {
		super.doWork(context);
		try {
			// 补充参数
			if (getSchedule().getId() != -2) {
				// 补充参数
				long t_Time = SchdeuleManager.getInstance(getContext()).getLastUpdateTime(getSchedule().getLoc_TargetId());
				User_Bean user = new User_Bean();
				user.readData(getContext());
				// client_ref_id
				getSchedule().setLoc_ItemTag(java.util.UUID.randomUUID().toString());
				getSchedule().setLast_updated_time(t_Time);
				getSchedule().setCreator_id("" + user.getUser_id());
				getSchedule().setCreate_time(t_Time + 1);
				getSchedule().setHighlight_flag(ScheduleHighlightFlag.No);
				getSchedule().setStatus(ScheduleRevokeStatus.Normal);
			}
			getSchedule().setId(-1);
			// 保存一次
			saveToDBAndSendBroadcast();
			// 发送文件
			if (getSchedule().getFile_id().equals("")) {
				boolean tIfSendFile = true;
				File t_file = new File(getSchedule().getLoc_FilePath());
				if (!t_file.isFile()) {
					tIfSendFile = false;
				}
				{
					ArrayList<ScheduleType> tTypeList = new ArrayList<ScheduleType>();
					tTypeList.addAll(Arrays.asList(new ScheduleType[] { ScheduleType.Image, ScheduleType.File }));
					if (!tTypeList.contains(getSchedule().getNote_type())) {
						tIfSendFile = false;
					}
				}
				if (tIfSendFile) {
					Image_Upload_NetRequest uploadImageRequest = new Image_Upload_NetRequest(getContext());
					UploadFile_File_Bean resbean = uploadImageRequest.doUpload(t_file);
					if (!resbean.getFile_id().equals("")) {
						getSchedule().setFile_id(resbean.getFile_id());
					} else {
						getSchedule().file_id = null;
					}
				} else {
					getSchedule().setFile_id("");
					getSchedule().loc_FilePath = null;
				}
			}
			// 保存一次
			saveToDBAndSendBroadcast();
			// 发送进度更新
			if (getSchedule().file_id != null) {
				// 把Target发送到网络
				CreateSchedule_Schedule_NetRequest fewfwef = new CreateSchedule_Schedule_NetRequest(getContext());
				Schedule_Bean t_target_Bean = fewfwef.createTarget(getSchedule());
				t_target_Bean.setLoc_ItemTag(getSchedule().getLoc_ItemTag());
				SchdeuleManager.getInstance(getContext()).writeSchdeule(t_target_Bean);
			} else {
				getSchedule().setId(-2);
			}
			// 保存一次
			saveToDBAndSendBroadcast();
			AirLock_Work.syncSchedule(getContext(), getSchedule().getLoc_TargetId());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void saveToDBAndSendBroadcast() {
		try {
			SchdeuleManager.getInstance(getContext()).writeSchdeule(getSchedule());// 把进度更新保存到数据库
			Broadcast_Sender.scheduleChanged_Cloud(getContext(), getSchedule().getLoc_TargetId());// 发送广播，本地进度更新变化
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private Schedule_Bean getSchedule() {
		if (mSchedule == null) {
			mSchedule = new Schedule_Bean();
		}
		return mSchedule;
	}

	private void setSchedule(Schedule_Bean schedule) {
		this.mSchedule = schedule;
	}

}

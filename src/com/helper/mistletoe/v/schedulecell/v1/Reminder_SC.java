package com.helper.mistletoe.v.schedulecell.v1;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.util.Transformation_Util;

public class Reminder_SC extends Standard_SC {
	protected TextView mContent;
	protected TextView mReminderTime;

	public Reminder_SC(Context context) {
		super(context);
	}

	@Override
	protected void initView(View view) {
		super.initView(view);
		try {
			mContent = (TextView) view.findViewById(R.id.sci_remindContent);
			mReminderTime = (TextView) view.findViewById(R.id.sci_remindTime);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void updateShow(Target_Bean target, Schedule_Bean schedule) {
		super.updateShow(target, schedule);
		try {
			String tReminderTime = "";
			String tContent = "";
			tReminderTime += TimeTool_Utils.fromateTimeShow(Transformation_Util.Sring2long(schedule.getReminder_time(), 0) * 1000,
					"yyyy-MM-dd HH:mm:ss");
			tContent += schedule.getContent();
			mReminderTime.setText(tReminderTime);
			mContent.setText(tContent);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setLinseaner(Target_Bean target, Schedule_Bean schedule) {
		super.setLinseaner(target, schedule);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.schedule_item_remind_layout;
	}
}
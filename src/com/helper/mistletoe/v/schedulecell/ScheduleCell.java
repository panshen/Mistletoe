package com.helper.mistletoe.v.schedulecell;

import android.view.View;

import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;

public interface ScheduleCell {
	public View findView();

	public void updateShow(Target_Bean target, Schedule_Bean schedule);

	public void setLinseaner(Target_Bean target, Schedule_Bean schedule);

	public void setChoosed(boolean choosed);

	public void setChoosed(boolean choosed, boolean showWithdraw, boolean showCopy, boolean showTop);
}
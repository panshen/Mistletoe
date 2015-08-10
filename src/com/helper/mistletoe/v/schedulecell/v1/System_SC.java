package com.helper.mistletoe.v.schedulecell.v1;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;

public class System_SC extends Simple_SC {
	public System_SC(Context context) {
		super(context);
	}

	protected TextView mContent;

	@Override
	protected void initView(View view) {
		super.initView(view);
		try {
			mContent = (TextView) view.findViewById(R.id.sci_systemText);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void updateShow(Target_Bean target, Schedule_Bean schedule) {
		super.updateShow(target, schedule);
		try {
			mContent.setText(schedule.getContent());
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
	public void setChoosed(boolean choosed, boolean showWithdraw, boolean showCopy, boolean showTop) {
	}

	
	@Override
	protected int getLayoutId() {
		return R.layout.schedule_item_system_layout;
	}
}
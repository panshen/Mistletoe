package com.helper.mistletoe.v.schedulecell.v1;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;

public class Revoke_SC extends Simple_SC {
	protected TextView mContent;

	public Revoke_SC(Context context) {
		super(context);
	}

	@Override
	protected void initView(View view) {
		super.initView(view);
		try {
			mContent = (TextView) view.findViewById(R.id.sci_revokeContent);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void updateShow(Target_Bean target, Schedule_Bean schedule) {
		super.updateShow(target, schedule);
		try {
			String tContent = "";
			tContent += schedule.getLoc_Creater().getShowName();
			tContent += " 撤回了一条消息";
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
	public void setChoosed(boolean choosed, boolean showWithdraw, boolean showCopy, boolean showTop) {
	}

	@Override
	protected int getLayoutId() {
		return R.layout.schedule_item_revoke_layout;
	}
}
package com.helper.mistletoe.v.schedulecell.v1;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class Image_SC extends Standard_SC {
	protected TextView mContent;
	protected SnaImageViewV2 mImage;

	public Image_SC(Context context) {
		super(context);
	}

	@Override
	public View findView() {
		View result = super.findView();
		try {
			mImage.setDefaultImageForShow(R.drawable.pictures_can_not_show);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	protected void initView(View view) {
		super.initView(view);
		try {
			mContent = (TextView) view.findViewById(R.id.sci_imageContent);
			mImage = (SnaImageViewV2) view.findViewById(R.id.sci_imageImage);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void updateShow(Target_Bean target, Schedule_Bean schedule) {
		super.updateShow(target, schedule);
		try {
			mContent.setText(schedule.getContent());
			mImage.setImageForShow(schedule.getContentImage());
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
		return R.layout.schedule_item_image_layout;
	}
}
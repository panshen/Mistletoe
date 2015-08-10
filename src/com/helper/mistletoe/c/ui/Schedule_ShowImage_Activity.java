package com.helper.mistletoe.c.ui;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.base.Base_Activity;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.work.be.GetSchedule_Target_Mode;
import com.helper.mistletoe.util.DisplayImage;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.ReadyGoooWork;
import com.helper.mistletoe.v.snaimageview.SnaBitmap.ImageSize;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class Schedule_ShowImage_Activity extends Base_Activity {
	private SnaImageViewV2 vBigImage;
	private ProgressBar vLoadProgress;

	private PhotoViewAttacher mAttacher;
	private int m_ScheduleId;
	private String m_ScheduleTag;
	protected Schedule_Bean m_Schedule;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.schedule_showimage_layout);
			initView();
			setListener();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView() {
		try {
			// 初始化控件
			vBigImage = (SnaImageViewV2) findViewById(R.id.siml_bigImagefweffewafweafweafawf);
			vLoadProgress = (ProgressBar) findViewById(R.id.siml_loadingImageProgress);
			// 初始化成员变量
			m_Schedule = new Schedule_Bean();
			mAttacher = new PhotoViewAttacher(vBigImage);
			// 从上一个页面获得参数
			getDataFromBundle();
			// 其他初始化工作
			vBigImage.setDefaultImageForShow(-1);
			mAttacher.setZoomable(true);// 设置图片可以缩放
			mAttacher.setMinimumScale(0.3F);// 设置最小缩放比例
			mAttacher.setMaximumScale(5.0F);// 设置最大缩放比例
			mAttacher.update();
			new GetSchedule_Target_Mode(m_ScheduleId, m_ScheduleTag).publishWork(getWorkFactory());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setListener() {
		try {
			mAttacher.setOnViewTapListener(new OnViewTapListener() {
				@Override
				public void onViewTap(View view, float x, float y) {
					try {
						finish();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void getDataFromBundle() {
		try {
			Bundle bundle = getIntent().getExtras();
			if (bundle.containsKey("ScheduleId")) {
				m_ScheduleId = bundle.getInt("ScheduleId");
			}
			if (bundle.containsKey("ScheduleTag")) {
				m_ScheduleTag = bundle.getString("ScheduleTag");
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onWorkOk(ReadyGoooWork work) {
		super.onWorkOk(work);
		try {
			if (work instanceof GetSchedule_Target_Mode) {
				GetSchedule_Target_Mode t_Work = (GetSchedule_Target_Mode) work;
				// 结果处理
				m_Schedule = t_Work.getSchedule();
				DisplayImage.loadImageUseAUIL_Ex(getContext(), m_Schedule.getContentImage(ImageSize.Normal.toInt()), mAttacher, R.drawable.pictures_can_not_show, vLoadProgress);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}

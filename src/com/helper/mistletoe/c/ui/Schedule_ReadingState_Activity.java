package com.helper.mistletoe.c.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.adapter.Schedule_ReadingState_Adapter;
import com.helper.mistletoe.c.ui.base.Base_Activity;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.work.be.GetScheduleReadingState_Target_Mode;
import com.helper.mistletoe.m.work.be.GetTarget_Target_Mode;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.ReadyGoooWork;

public class Schedule_ReadingState_Activity extends Base_Activity {
	private ImageView vBack;
	private ListView vHelperList;

	private int mTargetId;
	private String mTargetTag;
	private int mScheduleId;
	private String mScheduleTag;
	private Schedule_Bean mSchedule;
	private Schedule_ReadingState_Adapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.schedule_readingstate_layout);
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

	@Override
	protected void onResume() {
		super.onResume();
		try {
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView() throws Exception {
		try {
			// 初始化控件
			vBack = (ImageView) findViewById(R.id.searchViddddddddew_main_back);
			vHelperList = (ListView) findViewById(R.id.helperList);
			// 初始化成员变量
			mTargetId = -1;
			mTargetTag = "";
			mScheduleId = -1;
			mScheduleTag = "";
			mSchedule = new Schedule_Bean();
			mAdapter = new Schedule_ReadingState_Adapter(getContext(),
					getWorkFactory(), mSchedule);
			// 其他初始化工作
			getDataFromBundle();
			mSchedule.setId(mScheduleId);
			mSchedule.setLoc_ItemTag(mScheduleTag);
			new GetTarget_Target_Mode(mTargetId, mTargetTag)
					.publishWork(getWorkFactory());
			vHelperList.setAdapter(mAdapter);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void setData() throws Exception {
		try {
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void setListener() throws Exception {
		try {
			vBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						finish();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public void onWorkOk(ReadyGoooWork work) {
		super.onWorkOk(work);
		try {
			if (work instanceof GetTarget_Target_Mode) {
				GetTarget_Target_Mode t_Work = (GetTarget_Target_Mode) work;
				// 取出结果
				// 更新显示
				new GetScheduleReadingState_Target_Mode(mSchedule)
						.publishWork(getWorkFactory());
			} else if (work instanceof GetScheduleReadingState_Target_Mode) {
				GetScheduleReadingState_Target_Mode t_Work = (GetScheduleReadingState_Target_Mode) work;
				// 取出结果
				mSchedule = t_Work.getSchedule();
				// 更新显示
				mAdapter.setData_Pr(mSchedule.getLoc_Member());
				setData();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void getDataFromBundle() throws Exception {
		try {
			Bundle bundle = getIntent().getExtras();
			mTargetId = bundle.getInt("TargetId", -1);
			mTargetTag = bundle.getString("TargetTag", "");
			mScheduleId = bundle.getInt("ScheduleId", -1);
			mScheduleTag = bundle.getString("ScheduleTag", "");
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}
}
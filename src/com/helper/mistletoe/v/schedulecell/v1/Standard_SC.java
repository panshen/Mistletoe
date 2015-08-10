package com.helper.mistletoe.v.schedulecell.v1;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.Schedule_ReadingState_Activity;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleHighlightFlag;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleViewFlag;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public abstract class Standard_SC extends Simple_SC {
	protected SnaImageViewV2 mHead;
	protected TextView mName;
	protected TextView mSendTo;
	protected TextView mSendToArrow;
	protected TextView mTime;
	protected TextView mReadingState;
	protected LinearLayout mSendingStateArea;
	protected ImageView mSending_Failed;
	protected ProgressBar mSending_Sending;
	protected LinearLayout mTopState;
	protected LinearLayout mContent;

	public Standard_SC(Context context) {
		super(context);
	}

	@Override
	public View findView() {
		View result = super.findView();
		try {
			mHead.setDefaultImageForShow(R.drawable.default_head);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	protected void initView(View view) {
		super.initView(view);
		try {
			mHead = (SnaImageViewV2) view.findViewById(R.id.sistandard_Head);
			mName = (TextView) view.findViewById(R.id.sistandard_CreaterName);
			mSendTo = (TextView) view.findViewById(R.id.sistandard_SendTo);
			mSendToArrow = (TextView) view.findViewById(R.id.sistandard_SendToState);
			mTime = (TextView) view.findViewById(R.id.sistandard_Time);
			mReadingState = (TextView) view.findViewById(R.id.sistandard_ReadingState);
			mSendingStateArea = (LinearLayout) view.findViewById(R.id.sistandard_SendStateArea);
			mSending_Failed = (ImageView) view.findViewById(R.id.sistandard_FailedState);
			mSending_Sending = (ProgressBar) view.findViewById(R.id.sistandard_SendingState);
			mTopState = (LinearLayout) view.findViewById(R.id.sistandard_TopFlag);
			mContent = (LinearLayout) view.findViewById(R.id.sistandard_RCenterArea);
			// 其他初始化工作
			mSendToArrow.setText("@");
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void updateShow(Target_Bean target, Schedule_Bean schedule) {
		super.updateShow(target, schedule);
		try {
			// 显示头像
			mHead.setImageForShow(schedule.getShowSnaBitmap());
			// 显示发送状态
			uds_SendingState(target, schedule);
			// 姓名
			mName.setText(schedule.getShowName());
			// 私密
			uds_SendTo(target, schedule);
			// 时间
			String tCreateTime = TimeTool_Utils.fromateTimeShow(schedule.getCreate_time() * 1000, "yy-MM-dd HH:mm");
			mTime.setText(tCreateTime);
			// 置顶
			mTopState.setVisibility(LinearLayout.INVISIBLE);
			if (schedule.getHighlight_flag() == ScheduleHighlightFlag.Yes) {
				mTopState.setVisibility(LinearLayout.VISIBLE);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setLinseaner(Target_Bean target, final Schedule_Bean schedule) {
		super.setLinseaner(target, schedule);
		try {
			mReadingState.setOnClickListener(new OnClickListener() {
				private Schedule_Bean mSchedule = schedule;

				@Override
				public void onClick(View v) {
					try {
						showToast("打开阅读状态");
						Intent tIntent = new Intent(mContext, Schedule_ReadingState_Activity.class);
						tIntent.putExtra("TargetId", (int) mSchedule.getLoc_TargetId());
						tIntent.putExtra("TargetTag", (String) "");
						tIntent.putExtra("ScheduleId", (int) mSchedule.getId());
						tIntent.putExtra("ScheduleTag", (String) "");
						mContext.startActivity(tIntent);
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			mSending_Failed.setOnClickListener(new OnClickListener() {
				private Schedule_Bean mSchedule = schedule;

				@Override
				public void onClick(View v) {
					try {
						showToast("正在重发");
						mSchedule.sendSchedule(mContext);
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void uds_SendingState(Target_Bean target, Schedule_Bean schedule) {
		try {
			mSendingStateArea.setVisibility(LinearLayout.GONE);
			mSending_Failed.setVisibility(LinearLayout.INVISIBLE);
			mSending_Sending.setVisibility(LinearLayout.VISIBLE);
			if (schedule.getId() == -1) {
				// 正在发送
				mSendingStateArea.setVisibility(LinearLayout.VISIBLE);
			} else if (schedule.getId() == -2) {
				// 发送失败
				mSendingStateArea.setVisibility(LinearLayout.VISIBLE);
				mSending_Failed.setVisibility(LinearLayout.VISIBLE);
				mSending_Sending.setVisibility(LinearLayout.GONE);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void uds_SendTo(Target_Bean target, Schedule_Bean schedule) {
		try {
			String tSendTo = "";
			// 这里给获取接受者的名字
			ScheduleViewFlag tViewFlag = schedule.getView_flag();
			if (tViewFlag == ScheduleViewFlag.Private) {
				schedule.foundReciver();
				tSendTo = schedule.getSendToNames();
			}
			// 更新显示
			mSendTo.setVisibility(TextView.INVISIBLE);
			mSendToArrow.setVisibility(TextView.INVISIBLE);
			if ((tSendTo != null) && (!tSendTo.trim().equals(""))) {
				mSendTo.setVisibility(TextView.VISIBLE);
				mSendToArrow.setVisibility(TextView.VISIBLE);
				mSendTo.setText(tSendTo);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}

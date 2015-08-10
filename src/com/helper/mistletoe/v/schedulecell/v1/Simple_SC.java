package com.helper.mistletoe.v.schedulecell.v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.base.UIInterface;
import com.helper.mistletoe.c.ui.base.UIToast;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.v.schedulecell.ScheduleCell;

public abstract class Simple_SC implements ScheduleCell, UIInterface {
	protected LinearLayout mBody;
	protected LinearLayout mExpandFunction;
	protected TextView mEF_Withdraw;
	protected TextView mEF_Copy;
	protected TextView mEF_Top;

	protected Context mContext;
	protected UIToast mToast;

	public Simple_SC(Context context) {
		try {
			this.mContext = context;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public View findView() {
		View result = null;
		try {
			result = LayoutInflater.from(getContext()).inflate(getLayoutId(),
					null);
			initView(result);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	protected void initView(View view) {
		try {
			mBody = (LinearLayout) view.findViewById(R.id.sisimple_Body);
			mExpandFunction = (LinearLayout) view
					.findViewById(R.id.sisimple_MoreFunciton);
			mEF_Withdraw = (TextView) view.findViewById(R.id.sisimple_Withdraw);
			mEF_Top = (TextView) view.findViewById(R.id.sisimple_Top);
			mEF_Copy = (TextView) view.findViewById(R.id.sisimple_Copy);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void updateShow(Target_Bean target, Schedule_Bean schedule) {
		try {
			setLinseaner(target, schedule);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setLinseaner(Target_Bean target, final Schedule_Bean schedule) {
		try {
			mEF_Withdraw.setOnClickListener(new OnClickListener() {
				private Schedule_Bean mSchedule = schedule;

				@Override
				public void onClick(View v) {
					try {
						showToast("正在撤回");
						mSchedule.withdrawSchedule(mContext);
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			mEF_Copy.setOnClickListener(new OnClickListener() {
				private Schedule_Bean mSchedule = schedule;

				@Override
				public void onClick(View v) {
					try {
						showToast("内容已经复制到剪贴板");
						mSchedule.copiedToTheClipboard(mContext);
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			mEF_Top.setOnClickListener(new OnClickListener() {
				private Schedule_Bean mSchedule = schedule;

				@Override
				public void onClick(View v) {
					try {
						showToast("正在提交");
						mSchedule.topSchedule(mContext);
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setChoosed(boolean choosed) {
		setChoosed(choosed, false, true, false);
	}

	@Override
	public void setChoosed(boolean choosed, boolean showWithdraw,
			boolean showCopy, boolean showTop) {
		try {
			mExpandFunction.setVisibility(LinearLayout.GONE);
			mEF_Copy.setVisibility(TextView.VISIBLE);
			mEF_Top.setVisibility(TextView.VISIBLE);
			mEF_Withdraw.setVisibility(TextView.VISIBLE);
			if (choosed) {
				mExpandFunction.setVisibility(LinearLayout.VISIBLE);
			}
			if (!showCopy) {
				mEF_Copy.setVisibility(TextView.INVISIBLE);
			}
			if (!showWithdraw) {
				mEF_Withdraw.setVisibility(TextView.INVISIBLE);
			}
			if (!showTop) {
				mEF_Top.setVisibility(TextView.INVISIBLE);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected abstract int getLayoutId();

	// TODO Toast

	public void showToast(CharSequence text, int duration) {
		try {
			getToastComponent().showToast(text, duration);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void showToast(CharSequence text) {
		try {
			getToastComponent().showToast(text);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public UIToast getToastComponent() {
		if (mToast == null) {
			mToast = new UIToast(getApplicationContext());
		}
		return mToast;
	}

	// TODO 通用

	protected Context getContext() {
		return mContext;
	}

	protected Context getApplicationContext() {
		return getContext().getApplicationContext();
	}

}
package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.helper.mistletoe.c.ui.base.Base_Adapter;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleType;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.Array_Util;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.util.prcomode.ReadyGoooFactory;
import com.helper.mistletoe.v.schedulecell.ScheduleCell;
import com.helper.mistletoe.v.schedulecell.v1.CostApply_SC;
import com.helper.mistletoe.v.schedulecell.v1.Image_SC;
import com.helper.mistletoe.v.schedulecell.v1.Location_SC;
import com.helper.mistletoe.v.schedulecell.v1.Reminder_SC;
import com.helper.mistletoe.v.schedulecell.v1.Revoke_SC;
import com.helper.mistletoe.v.schedulecell.v1.System_SC;
import com.helper.mistletoe.v.schedulecell.v1.Text_SC;
import com.helper.mistletoe.v.schedulecell.v1.Unknown_SC;

public class Schedule_Adapter extends Base_Adapter {
	public String mPoition;
	private ArrayList<Schedule_Bean> mData;
	private SparseArray<TargetMember_Bean> mMemberPools;
	private Target_Bean mTarget;
	private boolean mIfShowFuction;
	private boolean mCanTip;
	private int mUserId;

	public Schedule_Adapter(Context context, ReadyGoooFactory woFactory, ArrayList<Schedule_Bean> data) {
		super(context, woFactory);
		try {
			this.mPoition = null;
			this.mData = data;
			this.mIfShowFuction = false;
			this.mCanTip = false;
			this.mUserId = -1;

			User_Bean user_Bean = new User_Bean();
			user_Bean.readData(getContext());
			mUserId = (int) user_Bean.getUser_id();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public int getCount() {
		return getData_Pr().size();
	}

	@Override
	public Schedule_Bean getItem(int position) {
		Schedule_Bean result = new Schedule_Bean();
		try {
			result = getData_Pr().get(position);
			if (result == null) {
				result = new Schedule_Bean();
			}
		} catch (Exception e) {
			result = new Schedule_Bean();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public long getItemId(int position) {
		long result = 0L;
		try {
			result = (long) (getItem(position).getPrimaryKey());
		} catch (Exception e) {
			result = 0L;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public int getItemViewType(int position) {
		int result = 0;
		try {
			result = getItem(position).getScheduleType_IncludeRevoke().toInt();
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public int getViewTypeCount() {
		int result = 0;
		try {
			result = ScheduleType.values().length;
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			ScheduleType tType = ScheduleType.valueOf(getItemViewType(position));
			ScheduleCell tShowView = null;
			switch (tType) {
			case System:
				if (convertView == null) {
					getHolder().system_SC00 = new System_SC(getContext());
					convertView = getHolder().system_SC00.findView();
					convertView.setTag((System_SC) getHolder().system_SC00);
				} else {
					getHolder().system_SC00 = (System_SC) convertView.getTag();
				}
				tShowView = getHolder().system_SC00;
				break;
			case Text:
				if (convertView == null) {
					getHolder().text_SC00 = new Text_SC(getContext());
					convertView = getHolder().text_SC00.findView();
					convertView.setTag((Text_SC) getHolder().text_SC00);
				} else {
					getHolder().text_SC00 = (Text_SC) convertView.getTag();
				}
				tShowView = getHolder().text_SC00;
				break;
			case Image:
				if (convertView == null) {
					getHolder().image_SC00 = new Image_SC(getContext());
					convertView = getHolder().image_SC00.findView();
					convertView.setTag((Image_SC) getHolder().image_SC00);
				} else {
					getHolder().image_SC00 = (Image_SC) convertView.getTag();
				}
				tShowView = getHolder().image_SC00;
				break;
			case Remind:
				if (convertView == null) {
					getHolder().reminder_SC00 = new Reminder_SC(getContext());
					convertView = getHolder().reminder_SC00.findView();
					convertView.setTag((Reminder_SC) getHolder().reminder_SC00);
				} else {
					getHolder().reminder_SC00 = (Reminder_SC) convertView.getTag();
				}
				tShowView = getHolder().reminder_SC00;
				break;
			case Revoke:
				if (convertView == null) {
					getHolder().revoke_SC00 = new Revoke_SC(getContext());
					convertView = getHolder().revoke_SC00.findView();
					convertView.setTag((Revoke_SC) getHolder().revoke_SC00);
				} else {
					getHolder().revoke_SC00 = (Revoke_SC) convertView.getTag();
				}
				tShowView = getHolder().revoke_SC00;
				break;
			case CostApply:
				if (convertView == null) {
					getHolder().costApply_SC00 = new CostApply_SC(getContext());
					convertView = getHolder().costApply_SC00.findView();
					convertView.setTag((CostApply_SC) getHolder().costApply_SC00);
				} else {
					getHolder().costApply_SC00 = (CostApply_SC) convertView.getTag();
				}
				tShowView = getHolder().costApply_SC00;
				break;
			case GPS:
				if (convertView == null) {
					getHolder().location_SC00 = new Location_SC(getContext());
					convertView = getHolder().location_SC00.findView();
					convertView.setTag((Location_SC) getHolder().location_SC00);
				} else {
					getHolder().location_SC00 = (Location_SC) convertView.getTag();
				}
				tShowView = getHolder().location_SC00;
				break;
			default:
				if (convertView == null) {
					getHolder().unknown_SC00 = new Unknown_SC(getContext());
					convertView = getHolder().unknown_SC00.findView();
					convertView.setTag((Unknown_SC) getHolder().unknown_SC00);
				} else {
					getHolder().unknown_SC00 = (Unknown_SC) convertView.getTag();
				}
				tShowView = getHolder().unknown_SC00;
				break;
			}
			updateShow(position, tShowView, convertView);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return convertView;
	}

	private void updateShow(int position, ScheduleCell showView, View convertView) {
		try {
			Schedule_Bean bean = getItem(position);
			bean.setLoc_Creater(getScheduleMember(Transformation_Util.String2int(bean.getCreator_id(), -1)));
			bean.setLoc_Owner(getScheduleMember(bean.getOwner_id_int()));
			showView.updateShow(getTarget(), bean);
			// 选中了第几条
			boolean t_CanShow = true;
			boolean showWithdraw = true;
			boolean showCopy = true;
			if (!mIfShowFuction) {
				// 设置不显示
				t_CanShow = false;
			}
			if ((mPoition == null) || (Transformation_Util.String2int(mPoition, -1) != getItemId(position))) {
				// 此条未被选中不显示
				t_CanShow = false;
			}
			if (t_CanShow) {
				if (bean.getContent().trim().equals("")) {
					showCopy = false;
				}
				if (Transformation_Util.String2int(bean.getCreator_id(), -1) != mUserId) {
					showWithdraw = false;
				}
				if (!bean.sendTimeLessThan5Minutes()) {
					showWithdraw = false;
				}
			}
			showView.setChoosed(t_CanShow, showWithdraw, showCopy, mCanTip);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setData_Pr(ArrayList<Schedule_Bean> data) {
		try {
			this.mData = data;
			notifyDataSetChanged();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public ArrayList<Schedule_Bean> getData_Pr() {
		if (mData == null) {
			mData = new ArrayList<Schedule_Bean>();
		}
		return mData;
	}

	public void setmIfShowFuction(boolean mIfShowFuction) {
		this.mIfShowFuction = mIfShowFuction;
	}

	public void setCanTip(boolean CanTip) {
		this.mCanTip = CanTip;
	}

	public void setMemberPool(ArrayList<TargetMember_Bean> data) {
		try {
			getMemberPool().clear();

			boolean contentIsSafe = true;
			if (data == null) {
				contentIsSafe = false;
			}
			if (contentIsSafe) {
				Array_Util.removeArrayListInvalidData(data, new TargetMember_Bean());
			}
			if (data.size() < 0) {
				contentIsSafe = false;
			}
			if (contentIsSafe) {
				for (Iterator<TargetMember_Bean> iterator = data.iterator(); iterator.hasNext();) {
					TargetMember_Bean tTemp = iterator.next();
					getMemberPool().put((int) tTemp.getMember_id(), tTemp);
				}
			}

			notifyDataSetChanged();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private SparseArray<TargetMember_Bean> getMemberPool() {
		if (mMemberPools == null) {
			mMemberPools = new SparseArray<TargetMember_Bean>();
		}
		return mMemberPools;
	}

	private TargetMember_Bean getScheduleMember(int helperId) {
		TargetMember_Bean result = null;
		try {
			result = getMemberPool().get(helperId);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public Target_Bean getTarget() {
		if (mTarget == null) {
			mTarget = new Target_Bean();
		}
		return mTarget;
	}

	public void setTarget(Target_Bean Target) {
		this.mTarget = Target;
		notifyDataSetChanged();
	}

}

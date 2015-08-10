package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.HelperDetailsActivity;
import com.helper.mistletoe.c.ui.base.Base_Adapter;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.util.prcomode.ReadyGoooFactory;
import com.helper.mistletoe.v.ViewHolder;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class Schedule_ReadingState_Adapter extends Base_Adapter {
	public String m_poition;
	protected ArrayList<TargetMember_Bean> data;
	protected Schedule_Bean mSchedule;

	public Schedule_ReadingState_Adapter(Context context, ReadyGoooFactory woFactory, Schedule_Bean schedule) {
		super(context, woFactory);
		try {
			this.m_poition = null;
			this.mSchedule = schedule;
			setData_Pr(schedule.getLoc_Member());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public int getCount() {
		int result = 0;
		try {
			result = getData_Pr().size();
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public TargetMember_Bean getItem(int position) {
		TargetMember_Bean result = null;
		try {
			result = getData_Pr().get(position);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public long getItemId(int position) {
		long result = 0L;
		try {
			result = (long) hashCode();
		} catch (Exception e) {
			result = 0L;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			if (convertView == null) {
				mHolder = new ViewHolder();
				// 把控件保存到ViewHolder中
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.schedule_readingstate_item_layout, null);

				mHolder.snaImageView00 = (SnaImageViewV2) convertView.findViewById(R.id.srsil_Head);

				mHolder.textView00 = (TextView) convertView.findViewById(R.id.srsil_Name);
				mHolder.textView01 = (TextView) convertView.findViewById(R.id.srsil_ReadTime);

				// 保存ViewHolder
				convertView.setTag(mHolder);
			} else {
				// 取出holder
				mHolder = (ViewHolder) convertView.getTag();
			}
			updateShow(position, convertView);
			setListener(position);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return convertView;
	}

	protected void updateShow(int position, View convertView) throws Exception {
		try {
			TargetMember_Bean bean = getItem(position);

			// 头像
			mHolder.snaImageView00.setImageForShow(bean.getHelper_photo(), SnaBitmap.NET_SMALL);
			// 姓名
			mHolder.textView00.setText(bean.getHelper_name());
			// 状态
			if (((long) bean.getRead_time()) > 0) {
				String tTimeString = TimeTool_Utils.fromateTimeShow(bean.getRead_time() * 1000L, "yy-MM-dd HH:mm");
				mHolder.textView01.setText(tTimeString);
			} else {
				mHolder.textView01.setText("还未阅读");
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void setListener(final int position) throws Exception {
		try {
			mHolder.snaImageView00.setOnClickListener(new OnClickListener() {
				private final Helpers_Sub_Bean helper = getItem(position);

				@Override
				public void onClick(View v) {
					Intent intent_helperDetails = new Intent(getContext(), HelperDetailsActivity.class);
					intent_helperDetails.putExtra("helper", helper);
					getContext().startActivity(intent_helperDetails);
				}
			});
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void setData_Pr(ArrayList<TargetMember_Bean> data) throws Exception {
		try {
			if (data == null) {
				data = new ArrayList<TargetMember_Bean>();
			}
			getData_Pr().clear();
			getData_Pr().addAll(data);
			notifyDataSetChanged();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public ArrayList<TargetMember_Bean> getData_Pr() throws Exception {
		ArrayList<TargetMember_Bean> result = null;
		try {
			if (data == null) {
				data = new ArrayList<TargetMember_Bean>();
			}
			result = data;
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

}
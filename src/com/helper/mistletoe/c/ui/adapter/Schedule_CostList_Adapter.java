package com.helper.mistletoe.c.ui.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.Schedule_CostList_Activity;
import com.helper.mistletoe.c.ui.base.Base_Adapter;
import com.helper.mistletoe.m.pojo.NoteTagList_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.work.be.ScheduleGetList_Event;
import com.helper.mistletoe.util.Array_Util;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.util.prcomode.ReadyGoooFactory;
import com.helper.mistletoe.v.MyRelativeLayout;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

@SuppressLint("InflateParams")
public class Schedule_CostList_Adapter extends Base_Adapter {
	private ArrayList<Schedule_Bean> mData;
	private SparseArray<TargetMember_Bean> mMemberPools;
	private static Integer selectedTagId;

	public Schedule_CostList_Adapter(Context context,
			ReadyGoooFactory woFactory, ArrayList<Schedule_Bean> list) {
		super(context, woFactory);
		try {
			setData(list);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public int getCount() {
		return getData().size();
	}

	@Override
	public Schedule_Bean getItem(int position) {
		return getData().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		Schedule_Bean mContent = mData.get(position);
		// 设置目标创建者，Owner
		mContent.setLoc_Creater(getScheduleMember(Transformation_Util
				.String2int(mContent.getCreator_id(), -1)));
		mContent.setLoc_Owner(getScheduleMember(mContent.getOwner_id_int()));
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(getContext()).inflate(
					R.layout.schedule_costlist_item_layout, null);
			viewHolder.tvName = (TextView) view
					.findViewById(R.id.schedule_cost_list_item_item_textView_name);
			viewHolder.tvType = (TextView) view
					.findViewById(R.id.schedule_cost_list_item_textView_cost_type);
			viewHolder.tvCost = (TextView) view
					.findViewById(R.id.schedule_cost_list_item_money);
			viewHolder.tvTime = (TextView) view
					.findViewById(R.id.schedule_cost_list_item_time);
			viewHolder.layout = (MyRelativeLayout) view
					.findViewById(R.id.schedule_cost_list_item_myRelativeLayout);
			viewHolder.head = (SnaImageViewV2) view
					.findViewById(R.id.schedule_cost_list_item_imageView_head);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		User_Bean user = new User_Bean();
		try {
			user.readData(getContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 显示头像
		viewHolder.tvName.setText(mContent.getShowName());
		// 显示姓名
		viewHolder.head.setImageForShow(mContent.getShowSnaBitmap());
		viewHolder.tvType.setText(mContent.getCost_type(NoteTagList_Bean
				.getInstance(getContext())) + "    " + mContent.cost_desc);

		DecimalFormat cost = new DecimalFormat();
		String style = "0.00";
		cost.applyPattern(style);
		String tCost = cost.format(mContent.getCost());
		if (mContent.getCost() >= 0) {
			tCost = "+" + tCost;
			viewHolder.tvCost.setTextColor(Color.GREEN);
		} else {
			viewHolder.tvCost.setTextColor(Color.RED);
		}
		viewHolder.tvCost.setText(tCost);
		// 时间
		long Transaction_time = mContent.getTransaction_time() * 1000;
		String transaction_time_String = "";
		if (Transaction_time > 0) {
			transaction_time_String = TimeTool_Utils.fromateTimeShow(
					Transaction_time, "yy-MM-dd");
		}
		viewHolder.tvTime.setText(transaction_time_String);
		viewHolder.layout.setIndex(position);
		viewHolder.layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					int index = ((MyRelativeLayout) v).getIndex();
					int clickedId = mData.get(index).getOwner_id_int();
					// 再通知界面更新
					if (getContext() instanceof Schedule_CostList_Activity) {
						Schedule_CostList_Activity tAc = (Schedule_CostList_Activity) getContext();
						if ((tAc.slectedId != null)
								&& (tAc.slectedId == clickedId)) {
							tAc.slectedId = null;
						} else {
							tAc.slectedId = clickedId;
						}
						ScheduleGetList_Event.getCostApply(tAc.mTargetId,
								tAc.slectedId, selectedTagId);
					}
				} catch (Exception e) {
					ExceptionHandle.ignoreException(e);
				}
			}
		});
		return view;
	}

	final static class ViewHolder {
		TextView tvName;
		TextView tvType;
		TextView tvCost;
		TextView tvTime;
		SnaImageViewV2 head;
		MyRelativeLayout layout;
	}

	public ArrayList<Schedule_Bean> getData() {
		if (mData == null) {
			mData = new ArrayList<Schedule_Bean>();
		}
		return mData;
	}

	public void setData(ArrayList<Schedule_Bean> data) {
		this.mData = data;
		notifyDataSetChanged();
	}

	public void setMemberPool(ArrayList<TargetMember_Bean> data) {
		try {
			getMemberPool().clear();
			boolean contentIsSafe = true;
			if (data == null) {
				contentIsSafe = false;
			}
			if (contentIsSafe) {
				Array_Util.removeArrayListInvalidData(data,
						new TargetMember_Bean());
			}
			if (data.size() < 0) {
				contentIsSafe = false;
			}
			if (contentIsSafe) {
				for (Iterator<TargetMember_Bean> iterator = data.iterator(); iterator
						.hasNext();) {
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

	public void setSelectedTagId(int i) {
		selectedTagId = i;
	}
}
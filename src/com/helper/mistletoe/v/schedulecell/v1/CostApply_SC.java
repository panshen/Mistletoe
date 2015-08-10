package com.helper.mistletoe.v.schedulecell.v1;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.NoteTagList_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;

public class CostApply_SC extends Standard_SC {
	protected TextView mContent;
	protected TextView mCostType;

	public CostApply_SC(Context context) {
		super(context);
	}

	@Override
	protected void initView(View view) {
		super.initView(view);
		try {
			mContent = (TextView) view.findViewById(R.id.sci_costApplyCost);
			mCostType = (TextView) view.findViewById(R.id.sci_costApplyType);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void updateShow(Target_Bean target, Schedule_Bean schedule) {
		super.updateShow(target, schedule);
		try {
			DecimalFormat tDf = new DecimalFormat();
			String style = "0.00";

			tDf.applyPattern(style);
			String tCost = tDf.format(schedule.getCost());
			if (schedule.getCost() >= 0) {
				tCost = "+" + tCost;
				mContent.setTextColor(Color.GREEN);
			} else {
				mContent.setTextColor(Color.RED);
			}

			mContent.setText(tCost);
			mCostType.setText(""
					+ schedule.getCost_type(NoteTagList_Bean
							.getInstance(getContext())) + "    "
					+ schedule.cost_desc);
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
		return R.layout.schedule_item_scostapply_layout;
	}
}

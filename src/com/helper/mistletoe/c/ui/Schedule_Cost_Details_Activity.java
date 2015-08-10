package com.helper.mistletoe.c.ui;

import java.text.DecimalFormat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.base.Base_Activity;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.util.prcomode.ReadyGoooWork;

public class Schedule_Cost_Details_Activity extends Base_Activity {
	private ImageView back;
	private TextView summary, money, type, describe, symbol, date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.schedule_cost_details_layout);
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
			back = (ImageView) findViewById(R.id.schedule_cost_details_layout_back);
			summary = (TextView) findViewById(R.id.schedule_cost_details_layout_textView_summary);
			symbol = (TextView) findViewById(R.id.schedule_cost_details_layout_textView_symbol);
			date = (TextView) findViewById(R.id.schedule_cost_details_layout_textView_date);
			money = (TextView) findViewById(R.id.schedule_cost_details_layout_textView_money);
			type = (TextView) findViewById(R.id.schedule_cost_details_layout_textView_type);
			describe = (TextView) findViewById(R.id.schedule_cost_details_layout_textView_describe);
			// 初始化成员变量
			// 其他初始化工作
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void setData() throws Exception {
		try {
			summary.setText(getIntent().getExtras().getString("summary"));
			DecimalFormat cost = new DecimalFormat();
			String style = "0.00";
			cost.applyPattern(style);
			float c = getIntent().getExtras().getFloat("cost");
			String tCost = cost.format(c);
			if (c >= 0) {
				tCost = "+" + tCost;
				symbol.setTextColor(Color.rgb(104, 198, 136));
				symbol.setText("预算");
				money.setTextColor(Color.rgb(104, 198, 136));
			} else {
				symbol.setTextColor(Color.rgb(237, 107, 125));
				symbol.setText("支出");
				money.setTextColor(Color.rgb(237, 107, 125));
			}
			money.setText(tCost);
			type.setText(getIntent().getExtras().getString("cost_type"));
			date.setText(TimeTool_Utils.fromateTimeShow(getIntent().getExtras().getLong("transaction_time") * 1000, "yyyy年MM月dd日HH时mm分"));
			describe.setText(getIntent().getExtras().getString("cost_describe"));
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void setListener() throws Exception {
		try {
			back.setOnClickListener(new OnClickListener() {

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
			// if (work instanceof GetTarget_Target_Mode) {
			// GetTarget_Target_Mode t_Work = (GetTarget_Target_Mode) work;
			// // 取出结果
			// // 更新显示
			// } else if (work instanceof GetScheduleReadingState_Target_Mode) {
			// GetScheduleReadingState_Target_Mode t_Work =
			// (GetScheduleReadingState_Target_Mode) work;
			// // 取出结果
			// }
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}

package com.helper.mistletoe.v.colorswitch;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.UnitTrans_Utils;

/**
 * 这是带有红点的ImageView
 * 
 * @author 张久瑞
 * @version 1.0
 *
 */
public class ColorSwitch_LinearLayout extends LinearLayout {
	// 页面控件
	private LinearLayout white;
	private LinearLayout red;
	private LinearLayout yellow;
	private LinearLayout green;
	private LinearLayout purple;
	private TextView white_def;
	private TextView red_def;
	private TextView yellow_def;
	private TextView green_def;
	private TextView purple_def;
	private TextView white_cled;
	private TextView red_cled;
	private TextView yellow_cled;
	private TextView green_cled;
	private TextView purple_cled;
	// 成员变量
	private LayoutInflater inflater;
	private int selicked;

	public ColorSwitch_LinearLayout(Context context) {
		super(context);
		try {
			initView(context);
			setData();
			setlistener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ColorSwitch_LinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		try {
			initView(context);
			setData();
			setlistener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initView(Context context) throws Exception {
		try {
			Initialization(context);
			getFatherContent();
			// 初始化成员变量
			selicked = TargetColor.WHITE;
			// 设置各个空间的颜色
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				uds_SetBackground();
			} else {
				uds_SetBackground_PI16();
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@SuppressWarnings("deprecation")
	private void uds_SetBackground() throws Exception {
		try {
			// 设置各个空间的颜色
			white_def.setBackgroundDrawable(getColorForShow(TargetColor.getTargetColorId(TargetColor.WHITE)));
			white_cled.setBackgroundDrawable(getColorForShow(TargetColor.getTargetColorId(TargetColor.WHITE)));
			red_def.setBackgroundDrawable(getColorForShow(TargetColor.getTargetColorId(TargetColor.RED)));
			red_cled.setBackgroundDrawable(getColorForShow(TargetColor.getTargetColorId(TargetColor.RED)));
			yellow_def.setBackgroundDrawable(getColorForShow(TargetColor.getTargetColorId(TargetColor.YELLOW)));
			yellow_cled.setBackgroundDrawable(getColorForShow(TargetColor.getTargetColorId(TargetColor.YELLOW)));
			green_def.setBackgroundDrawable(getColorForShow(TargetColor.getTargetColorId(TargetColor.GREEN)));
			green_cled.setBackgroundDrawable(getColorForShow(TargetColor.getTargetColorId(TargetColor.GREEN)));
			purple_def.setBackgroundDrawable(getColorForShow(TargetColor.getTargetColorId(TargetColor.PURPLE)));
			purple_cled.setBackgroundDrawable(getColorForShow(TargetColor.getTargetColorId(TargetColor.PURPLE)));
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void uds_SetBackground_PI16() throws Exception {
		try {
			// 设置各个空间的颜色
			white_def.setBackground(getColorForShow(TargetColor.getTargetColorId(TargetColor.WHITE)));
			white_cled.setBackground(getColorForShow(TargetColor.getTargetColorId(TargetColor.WHITE)));
			red_def.setBackground(getColorForShow(TargetColor.getTargetColorId(TargetColor.RED)));
			red_cled.setBackground(getColorForShow(TargetColor.getTargetColorId(TargetColor.RED)));
			yellow_def.setBackground(getColorForShow(TargetColor.getTargetColorId(TargetColor.YELLOW)));
			yellow_cled.setBackground(getColorForShow(TargetColor.getTargetColorId(TargetColor.YELLOW)));
			green_def.setBackground(getColorForShow(TargetColor.getTargetColorId(TargetColor.GREEN)));
			green_cled.setBackground(getColorForShow(TargetColor.getTargetColorId(TargetColor.GREEN)));
			purple_def.setBackground(getColorForShow(TargetColor.getTargetColorId(TargetColor.PURPLE)));
			purple_cled.setBackground(getColorForShow(TargetColor.getTargetColorId(TargetColor.PURPLE)));
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void Initialization(Context context) throws Exception {
		try {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(R.layout.base_linearlayout_colorswitch_layout, this);

			white = (LinearLayout) findViewById(R.id.main_color_white_Linear);
			red = (LinearLayout) findViewById(R.id.main_color_darkorange_Linear);
			yellow = (LinearLayout) findViewById(R.id.main_color_mediumslateblue_Linear);
			green = (LinearLayout) findViewById(R.id.main_color_olivedrab_Linear);
			purple = (LinearLayout) findViewById(R.id.main_color_firebrick_Linear);
			white_def = (TextView) findViewById(R.id.main_color_white);
			red_def = (TextView) findViewById(R.id.main_color_darkorange);
			yellow_def = (TextView) findViewById(R.id.main_color_mediumslateblue);
			green_def = (TextView) findViewById(R.id.main_color_olivedrab);
			purple_def = (TextView) findViewById(R.id.main_color_firebrick);
			white_cled = (TextView) findViewById(R.id.main_color_white_big);
			red_cled = (TextView) findViewById(R.id.main_color_darkorange_big);
			yellow_cled = (TextView) findViewById(R.id.main_color_mediumslateblue_big);
			green_cled = (TextView) findViewById(R.id.main_color_olivedrab_big);
			purple_cled = (TextView) findViewById(R.id.main_color_firebrick_big);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void getFatherContent() throws Exception {
		try {
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void setData() throws Exception {
		try {
			sveryOneDefault();
			selickedChanged(getColor());
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}

	}

	private void setlistener() throws Exception {
		white.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					selicked = TargetColor.WHITE;
					setData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		red.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					selicked = TargetColor.RED;
					setData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		yellow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					selicked = TargetColor.YELLOW;
					setData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		green.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					selicked = TargetColor.GREEN;
					setData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		purple.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					selicked = TargetColor.PURPLE;
					setData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 把每一个颜色都设置为默认
	 * 
	 * @throws Exception
	 *             未知异常
	 */
	private void sveryOneDefault() throws Exception {
		try {
			white_setDefault();
			red_setDefault();
			yellow_setDefault();
			green_setDefault();
			purple_setDefault();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	/**
	 * 把某个颜色突出
	 * 
	 * @param color
	 *            颜色
	 * @throws Exception
	 *             未知异常
	 */
	private void selickedChanged(int color) throws Exception {
		try {
			if (color == TargetColor.RED) {
				red_setclickedshwo();
			} else if (color == TargetColor.PURPLE) {
				purple_setclickedshwo();
			} else if (color == TargetColor.YELLOW) {
				yellow_setclickedshwo();
			} else if (color == TargetColor.GREEN) {
				green_setclickedshwo();
			} else if (color == TargetColor.WHITE) {
				white_setclickedshwo();
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void white_setDefault() throws Exception {
		try {
			white.setVisibility(LinearLayout.VISIBLE);
			white_cled.setVisibility(TextView.GONE);
			white_def.setVisibility(TextView.VISIBLE);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void red_setDefault() throws Exception {
		try {
			red.setVisibility(LinearLayout.VISIBLE);
			red_cled.setVisibility(TextView.GONE);
			red_def.setVisibility(TextView.VISIBLE);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void yellow_setDefault() throws Exception {
		try {
			yellow.setVisibility(LinearLayout.VISIBLE);
			yellow_cled.setVisibility(TextView.GONE);
			yellow_def.setVisibility(TextView.VISIBLE);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void green_setDefault() throws Exception {
		try {
			green.setVisibility(LinearLayout.VISIBLE);
			green_cled.setVisibility(TextView.GONE);
			green_def.setVisibility(TextView.VISIBLE);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void purple_setDefault() throws Exception {
		try {
			purple.setVisibility(LinearLayout.VISIBLE);
			purple_cled.setVisibility(TextView.GONE);
			purple_def.setVisibility(TextView.VISIBLE);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void white_setclickedshwo() throws Exception {
		try {
			white.setVisibility(LinearLayout.VISIBLE);
			white_cled.setVisibility(TextView.VISIBLE);
			white_def.setVisibility(TextView.GONE);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void red_setclickedshwo() throws Exception {
		try {
			red.setVisibility(LinearLayout.VISIBLE);
			red_cled.setVisibility(TextView.VISIBLE);
			red_def.setVisibility(TextView.GONE);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void yellow_setclickedshwo() throws Exception {
		try {
			yellow.setVisibility(LinearLayout.VISIBLE);
			yellow_cled.setVisibility(TextView.VISIBLE);
			yellow_def.setVisibility(TextView.GONE);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void green_setclickedshwo() throws Exception {
		try {
			green.setVisibility(LinearLayout.VISIBLE);
			green_cled.setVisibility(TextView.VISIBLE);
			green_def.setVisibility(TextView.GONE);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void purple_setclickedshwo() throws Exception {
		try {
			purple.setVisibility(LinearLayout.VISIBLE);
			purple_cled.setVisibility(TextView.VISIBLE);
			purple_def.setVisibility(TextView.GONE);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public int getColor() throws Exception {
		int result = selicked;
		try {
			result = TargetColor.WHITE;
			if (selicked == TargetColor.RED) {
				result = TargetColor.RED;
			}
			if (selicked == TargetColor.GREEN) {
				result = TargetColor.GREEN;
			}
			if (selicked == TargetColor.YELLOW) {
				result = TargetColor.YELLOW;
			}
			if (selicked == TargetColor.PURPLE) {
				result = TargetColor.PURPLE;
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	public void setColor(int color) throws Exception {
		try {
			selicked = color;
			setData();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private GradientDrawable getColorForShow(int color, GradientDrawable drawable) throws Exception {
		GradientDrawable result = null;
		try {
			int t_OriginalColor = getResources().getColor(color);
			int t_Color = Color.rgb(Color.red(t_OriginalColor), Color.green(t_OriginalColor), Color.blue(t_OriginalColor));
			int t_StrokeColor = Color.argb(200, Color.red(t_OriginalColor), Color.green(t_OriginalColor), Color.blue(t_OriginalColor));
			if (color == R.color.colorSwitch_white) {
				t_Color = Color.rgb(96, 96, 96);
			}
			result = drawable;
			result.setColor(t_StrokeColor);
			result.setCornerRadius(UnitTrans_Utils.dpToPx(getContext(), 4));
			result.setStroke((int) UnitTrans_Utils.dpToPx(getContext(), 1), t_Color);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private GradientDrawable getColorForShow(int color) throws Exception {
		GradientDrawable result = null;
		try {
			result = getColorForShow(color, new GradientDrawable());
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

}
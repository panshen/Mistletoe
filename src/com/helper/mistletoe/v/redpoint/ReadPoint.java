package com.helper.mistletoe.v.redpoint;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * @see com.helper.ReadPoint_inter.c.view.itfe.ReadPoint
 * @author 张久瑞
 * @version 1.0
 */
public class ReadPoint extends LinearLayout implements ReadPoint_Plus, CustomLoad {
	private LinearLayout vBody;
	private LinearLayout vWithNumber_Lin;
	private LinearLayout vOnlyPoint_Lin;
	private TextView vWithNumber;

	private LayoutInflater inflater;
	private int unreadNumber;
	private int maxNumber;
	private int minNumber;
	private String showType;
	private ReadPointFilter filter;

	public ReadPoint(Context context, AttributeSet attrs) {
		super(context, attrs);
		try {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(R.layout.redpoint_readpoint_layout, this);
			setView();
			setData();
			setListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setView() throws Exception {
		try {
			// 初始化页面
			this.vBody = (LinearLayout) findViewById(R.id.rdp_body);
			this.vWithNumber_Lin = (LinearLayout) findViewById(R.id.rdp_readPointLinear);
			this.vOnlyPoint_Lin = (LinearLayout) findViewById(R.id.rdp_readPointSmallLinear);
			this.vWithNumber = (TextView) findViewById(R.id.rdp_readPoint);
			// 初始化成员变量
			this.unreadNumber = 0;
			this.maxNumber = 99;
			this.minNumber = 0;
			this.showType = SHOWTYPE_NUMBER;
			this.filter = new ReadPointFilter() {

				@Override
				public String setFilter(int number) throws Exception {
					String result = "";
					try {
						if (number == 0) {
							result = ReadPoint_Plus.SHOWTYPE_HIDE;
						} else if (number == 1) {
							result = ReadPoint_Plus.SHOWTYPE_POINT;
						}
					} catch (Exception e) {
						result = "";
						ExceptionHandle.unInterestException(e);
					}
					return result;
				}
			};
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public void setData() throws Exception {
		try {
			// 设置第一次显示
			// 更新显示
			updateShow();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void updateShow() throws Exception {
		try {
			// 首先判断是哪种显示模式
			String temp = useFilter(getNumberChecked());
			if (temp.equals(SHOWTYPE_NUMBER)) {
				// 数字模式
				// 显示数字
				showNumberPoint();
			} else if (temp.equals(SHOWTYPE_POINT)) {
				// 红点模式
				// 显示红点
				showReadPoint();
			} else if (temp.equals(SHOWTYPE_HIDE)) {
				// 隐藏模式
				hidePoint();
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void showNumberPoint() throws Exception {
		try {
			// 显示主框架
			this.vBody.setVisibility(LinearLayout.VISIBLE);
			// 隐藏红点框架
			this.vOnlyPoint_Lin.setVisibility(LinearLayout.GONE);
			// 显示数字框架
			this.vWithNumber_Lin.setVisibility(LinearLayout.VISIBLE);
			// 显示数字内容
			this.vWithNumber.setVisibility(TextView.VISIBLE);
			// 设置数字
			this.vWithNumber.setText(getNumberShow());
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void showReadPoint() throws Exception {
		try {
			// 显示主框架
			this.vBody.setVisibility(LinearLayout.VISIBLE);
			// 隐藏数字框架
			this.vWithNumber_Lin.setVisibility(LinearLayout.GONE);
			// 显示红点框架
			this.vOnlyPoint_Lin.setVisibility(LinearLayout.VISIBLE);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void hidePoint() throws Exception {
		try {
			// 显示主框架
			this.vBody.setVisibility(LinearLayout.VISIBLE);
			// 隐藏数字框架
			this.vWithNumber_Lin.setVisibility(LinearLayout.GONE);
			// 隐藏红点框架
			this.vOnlyPoint_Lin.setVisibility(LinearLayout.GONE);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public void setListener() throws Exception {
		try {
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public void setNumber(int number) throws Exception {
		try {
			// 保存数字
			this.unreadNumber = number;
			// 修改之后应该更新显示
			updateShow();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void setNumber(String number) throws Exception {
		try {
			setNumber(Integer.valueOf(number));
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public int getNumber() throws Exception {
		int result = minNumber;
		try {
			// 取出保存的数字
			result = this.unreadNumber;
		} catch (Exception e) {
			result = minNumber;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	@Override
	public void setShowType(String showType) throws Exception {
		try {
			// 检查传入的参数是否符合标准
			if (checkTypeIsLegal(showType)) {
				// 如果符合保存类型
				this.showType = showType;
				// 修改之后应该更新显示
				updateShow();
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public void setMaxNumber(int maxNumber) throws Exception {
		try {
			// 检查这个值是否与最小值冲突
			if (maxNumber < minNumber) {
				// 如果不冲突则保存
				this.maxNumber = maxNumber;
				// 修改之后应该更新显示
				updateShow();
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public void setMinNumber(int minNumber) throws Exception {
		try {
			// 检查这个值与最大值是否冲突
			if (minNumber > maxNumber) {
				// 如果不冲突保存
				this.minNumber = minNumber;
				// 修改之后应该更新显示
				updateShow();
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public void cleanNumber() throws Exception {
		try {
			// 把当前的数量设置为最小值
			setNumber(minNumber);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public int numberAdd(int number) throws Exception {
		int result = unreadNumber;
		try {
			// 保存结果
			setNumber(unreadNumber + number);
			result = unreadNumber;
		} catch (Exception e) {
			result = unreadNumber;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	@Override
	public int numberDetract(int number) throws Exception {
		int result = unreadNumber;
		try {
			// 保存结果
			setNumber(unreadNumber + number);
			result = unreadNumber;
		} catch (Exception e) {
			result = unreadNumber;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private int getNumberChecked() throws Exception {
		int result = getNumber();
		try {
			// 判断未读数量是否大于等于最小值
			if (result < minNumber) {
				result = minNumber;
			}
			// 判断未读数量是否小于等于最大值
			if (result > maxNumber) {
				result = maxNumber;
			}
		} catch (Exception e) {
			result = getNumber();
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String getNumberShow() throws Exception {
		String result = "";
		try {
			result = String.valueOf(getNumberChecked());
			if (this.unreadNumber > this.maxNumber) {
				result += "+";
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private boolean checkTypeIsLegal(String type) throws Exception {
		boolean result = false;
		try {
			// 判断显示类型是否合法
			if (type.equals(SHOWTYPE_NUMBER)) {
				result = true;
			} else if (type.equals(SHOWTYPE_NUMBER)) {
				result = true;
			} else if (type.equals(SHOWTYPE_HIDE)) {
				result = true;
			}
		} catch (Exception e) {
			result = false;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String useFilter(int number) throws Exception {
		String result = "";
		try {
			if (filter != null) {
				result = filter.setFilter(number);
				if (!checkTypeIsLegal(result)) {
					result = this.showType;
				}
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	@Override
	public void setFilter(ReadPointFilter filter) throws Exception {
		try {
			this.filter = filter;
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

}
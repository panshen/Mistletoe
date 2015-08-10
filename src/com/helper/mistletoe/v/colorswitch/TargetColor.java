package com.helper.mistletoe.v.colorswitch;

import android.util.SparseArray;
import android.util.SparseIntArray;

import com.helper.mistletoe.R;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * 由张久瑞在2015年4月29日创建<br/>
 * 这个类规定了目标的颜色
 * 
 * @author 张久瑞
 */
public class TargetColor {
	/**
	 * 白
	 */
	public final static int WHITE = 1;
	/**
	 * 红
	 */
	public final static int RED = 2;
	/**
	 * 黄
	 */
	public final static int YELLOW = 3;
	/**
	 * 绿
	 */
	public final static int GREEN = 4;
	/**
	 * 紫
	 */
	public final static int PURPLE = 5;

	/**
	 * 这个函数获取一个颜色的Id
	 * 
	 * @param color
	 *            颜色代码，在Target中应当是Target_Flag属性
	 * @return 颜色的资源Id
	 * @throws Exception
	 *             未知异常
	 */
	public static int getTargetColorId(Integer color) throws Exception {
		int result = 0;
		try {
			result = getColorMap().get(color, 0);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	/**
	 * 这个函数获取一个颜色的名字
	 * 
	 * @param color
	 *            颜色代码，在Target中应当是Target_Flag属性
	 * @return 颜色的名字
	 * @throws Exception
	 *             未知异常
	 */
	public static String getTargetColorName(Integer color) throws Exception {
		String result = "";
		try {
			result = getColorNameMap().get(color);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	/**
	 * 获取颜色编号和颜色资源Id的映射关系
	 * 
	 * @return 未知异常
	 */
	public static SparseIntArray getColorMap() {
		SparseIntArray result = null;
		try {
			result = new SparseIntArray();
			result.put(WHITE, R.color.colorSwitch_white);
			result.put(RED, R.color.colorSwitch_red);
			result.put(YELLOW, R.color.colorSwitch_yellow);
			result.put(GREEN, R.color.colorSwitch_green);
			result.put(PURPLE, R.color.colorSwitch_purple);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 获取颜色编号和颜色名的映射关系
	 * 
	 * @return 未知异常
	 */
	public static SparseArray<String> getColorNameMap() {
		SparseArray<String> result = null;
		try {
			result = new SparseArray<String>();
			result.put(WHITE, "白");
			result.put(RED, "红");
			result.put(YELLOW, "黄");
			result.put(GREEN, "绿");
			result.put(PURPLE, "紫");
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
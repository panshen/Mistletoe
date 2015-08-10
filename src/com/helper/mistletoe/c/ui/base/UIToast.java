package com.helper.mistletoe.c.ui.base;

import android.content.Context;
import android.widget.Toast;

import com.helper.mistletoe.util.ExceptionHandle;

public class UIToast {
	private Context context;
	private Toast toast;

	public UIToast(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 显示Toast
	 * 
	 * @param text
	 *            要显示的内容
	 * @param duration
	 *            显示的时间长短
	 * @throws Exception
	 *             未知异常
	 */
	public void showToast(CharSequence text, int duration) throws Exception {
		try {
			if (toast == null) {
				toast = Toast.makeText(context, text, duration);
			} else {
				toast.setText(text);
				toast.setDuration(duration);
			}
			toast.show();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	/**
	 * 显示Toast
	 * 
	 * @param text
	 *            要显示的内容
	 * @throws Exception
	 *             未知异常
	 */
	public void showToast(CharSequence text) throws Exception {
		try {
			showToast(text, Toast.LENGTH_SHORT);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}
}
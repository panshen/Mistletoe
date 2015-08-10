package com.helper.mistletoe.util;

import android.content.Context;

public class UnitTrans_Utils {
	public static float dpToPx(Context context, float dp) {
		float result = 0f;
		try {
			final float scale = context.getResources().getDisplayMetrics().density;
			result = (float) (dp * scale + 0.5f);
		} catch (Exception e) {
			result = 0f;
			e.printStackTrace();
		}
		return result;
	}

	public static float spToPx(Context context, float sp) {
		float result = 0f;
		try {
			final float scale = context.getResources().getDisplayMetrics().density;
			result = (float) (sp * scale + 0.5f);
		} catch (Exception e) {
			result = 0f;
			e.printStackTrace();
		}
		return result;
	}
}
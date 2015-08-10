package com.helper.mistletoe.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

public class ColorText_Util {
	public static SpannableStringBuilder append(SpannableStringBuilder builder, String text, ForegroundColorSpan color) {
		SpannableStringBuilder result = builder;
		try {
			int tStart = builder.length();
			int tEnd = tStart + text.length();
			result.append(text);
			if (color != null) {
				result.setSpan(color, tStart, tEnd, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			}
		} catch (Exception e) {
			result = builder;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static SpannableStringBuilder append(SpannableStringBuilder builder, String text) {
		return append(builder, text, null);
	}

	public static SpannableStringBuilder append(SpannableStringBuilder builder, String text, int color) {
		return append(builder, text, new ForegroundColorSpan(color));
	}

}
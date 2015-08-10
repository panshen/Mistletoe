package com.helper.mistletoe.util;

import java.lang.ref.SoftReference;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.helper.mistletoe.m.work.base.Broadcast_Sender;

public class Dialog_Util {
	public static void showDialog(Context context, String message, OnClickListener positiveListener) {
		try {
			// 转换为软引用
			final SoftReference<Context> sContext = new SoftReference<Context>(context);
			final SoftReference<OnClickListener> sPositiveListener = new SoftReference<OnClickListener>(positiveListener);

			AlertDialog dialog = buildDialog(sContext.get(), message, "提示", "确认", sPositiveListener.get(), "取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							try {
								dialog.dismiss();
								Broadcast_Sender.targetChanged(sContext.get());
							} catch (Exception e) {
								ExceptionHandle.ignoreException(e);
							}
						}
					});
			dialog.show();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static AlertDialog buildDialog(Context context, String message, String title, String positiveText,
			OnClickListener positiveListener, String negativeText, OnClickListener negativeListener) {
		AlertDialog result = null;
		try {
			// 转换为软引用
			SoftReference<Context> sContext = new SoftReference<Context>(context);
			SoftReference<OnClickListener> sPositiveListener = new SoftReference<OnClickListener>(positiveListener);
			SoftReference<OnClickListener> sNegativeListener = new SoftReference<OnClickListener>(negativeListener);

			Builder builder = new Builder(sContext.get());
			if (message != null) {
				builder.setMessage(message);
			}
			if (title != null) {
				builder.setTitle(title);
			}
			if (positiveText != null) {
				builder.setPositiveButton(positiveText, sPositiveListener.get());
			}
			if (negativeText != null) {
				builder.setNegativeButton(negativeText, sNegativeListener.get());
			}
			result = builder.create();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
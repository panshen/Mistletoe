package com.helper.mistletoe.zxing;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * ������
 * 
 * @author LJC
 *
 */
public class Untilly {
	/**
	 * ����ͼƬ
	 * 
	 * @param icon
	 * @param h
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap icon, int h) {
		// ����ͼƬ
		Matrix m = new Matrix();
		float sx = (float) 2 * h / icon.getWidth();
		float sy = (float) 2 * h / icon.getHeight();
		m.setScale(sx, sy);
		// ���¹���һ��2h*2h��ͼƬ
		Bitmap bitmap = Bitmap.createBitmap(icon, 0, 0, icon.getWidth(), icon.getHeight(), m, false);
		return getRoundedCornerBitmap(bitmap);
	}

	/**
	 * 圆角
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = 15;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 
	 * @param fileName
	 * @return byte[]
	 */
	public static byte[] readFile(String fileName) {
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		byte[] data = null;
		try {
			fis = new FileInputStream(fileName);
			byte[] buffer = new byte[8 * 1024];
			int readSize = -1;
			baos = new ByteArrayOutputStream();
			while ((readSize = fis.read(buffer)) != -1) {
				baos.write(buffer, 0, readSize);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;

	}

	public static boolean writeToSdcard(byte[] data, String path, String fileName) {
		FileOutputStream fos = null;
		try {
			File filePath = new File(path);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}

			File file = new File(path + fileName);
			if (file.exists()) {
				file.delete();
			}
			fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
			return true;

		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

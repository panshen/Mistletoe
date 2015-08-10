package com.helper.mistletoe.util;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

/**
 * 图片工具
 * 
 * @author 张久瑞
 * @version 1.0
 *
 */
public class ImageTool_Utils {
	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 *            图片信息
	 * @param reqWidth
	 *            压缩期望：宽
	 * @param reqHeight
	 *            压缩期望：高
	 * @return 压缩比 @ 未知异常
	 */
	public static int getZoomBitmapSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		int result = 0;
		try {
			// Raw height and width of image
			final int height = options.outHeight;
			final int width = options.outWidth;
			result = 1;

			if (height > reqHeight || width > reqWidth) {

				// Calculate ratios of height and width to requested height and
				// width
				final int heightRatio = Math.round((float) height
						/ (float) reqHeight);
				final int widthRatio = Math.round((float) width
						/ (float) reqWidth);

				// Choose the smallest ratio as inSampleSize value, this will
				// guarantee
				// a final image with both dimensions larger than or equal to
				// the
				// requested height and width.
				result = heightRatio < widthRatio ? heightRatio : widthRatio;
			}

			options = null;
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static int getPictureDegree(String path) {
		int result = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				result = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				result = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				result = 270;
				break;
			}
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 压缩一张图片，返回文件地址
	 * 
	 * @param filePath
	 *            想要压缩的图片
	 * @return 压缩结果 @ 未知异常
	 */
	public static String getZoomBitmapFile(Context conte, String filePath) {
		String result = null;
		Bitmap mSaveBitmap = null;
		Bitmap mOriginalBitmap = null;
		try {
			// 获得当前时间
			String ntL = String.valueOf(TimeTool_Utils.getNowTime());
			// 生成一个临时文件
			result = FolderTool_Utils.getAFE_ImageZoom() + "/zoom" + ntL
					+ ".jpg";
			File fSaveFile = new File(result);// 生成要保存的文件
			// 创建新文件
			FolderTool_Utils.makeFileNew(result);
			FileOutputStream fSaveFileOS = new FileOutputStream(fSaveFile);// 建立要保存的文件的输出流
			// 压缩图片
			{
				BitmapFactory.Options options;
				options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(filePath, options);

				// Calculate inSampleSize
				options.inSampleSize = getZoomBitmapSize(options, 1280, 1280);

				// Decode bitmap with inSampleSize set
				options.inJustDecodeBounds = false;
				mOriginalBitmap = BitmapFactory.decodeFile(filePath, options);

				if (mOriginalBitmap != null) {
					Matrix m = new Matrix();
					m.postRotate(getPictureDegree(filePath));
					mSaveBitmap = Bitmap.createBitmap(mOriginalBitmap, 0, 0,
							mOriginalBitmap.getWidth(),
							mOriginalBitmap.getHeight(), m, true);
				}
			}
			// 保存bitmap
			mSaveBitmap.compress(Bitmap.CompressFormat.JPEG, 40, fSaveFileOS);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		} finally {
			if (mOriginalBitmap != null) {
				if (!mOriginalBitmap.isRecycled()) {
					mOriginalBitmap.recycle();
				}
			}
			if (mSaveBitmap != null) {
				if (!mSaveBitmap.isRecycled()) {
					mSaveBitmap.recycle();
				}
			}
		}
		return result;
	}
}
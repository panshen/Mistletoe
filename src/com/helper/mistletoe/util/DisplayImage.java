package com.helper.mistletoe.util;

import java.lang.ref.SoftReference;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.helper.mistletoe.MistletoeApplication;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 * 图片工具
 * 
 * @author 张久瑞
 * @version 1.0
 *
 */
public class DisplayImage {
	public static void loadImageUseAUIL(Context context, SnaBitmap uri, ImageView imageView) {
		loadImageUseAUIL(context, uri, imageView, null);
	}

	public static void loadImageUseAUIL(Context context, SnaBitmap uri, ImageView imageView, DisplayImageOptions options) {
		loadImageUseAUIL(context, uri, imageView, options, null, null);
	}

	public static void loadImageUseAUIL(Context context, SnaBitmap uri, ImageView imageView, DisplayImageOptions options,
			ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
		try {
			if (options == null) {
				options = getDisplayImageOptions();
			}
			MistletoeApplication.getInstance(context).getAUIL().cancelDisplayTask(imageView);
			MistletoeApplication.getInstance(context).getAUIL()
					.displayImage(uri.getAUILShowPath(context), imageView, options, listener, progressListener);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadImageUseAUIL_Ex(Context context, SnaBitmap uri, ImageView imageView, int defaultImage) {
		loadImageUseAUIL(context, uri, imageView, getDisplayImageOptions(defaultImage));
	}

	public static void loadImageUseAUIL_Ex(Context context, SnaBitmap uri, PhotoViewAttacher attacher, int defaultImage,
			ProgressBar progressBar) {
		try {
			final SoftReference<PhotoViewAttacher> tAttacher = new SoftReference<PhotoViewAttacher>(attacher);
			final SoftReference<ProgressBar> tProgressBar = new SoftReference<ProgressBar>(progressBar);
			ImageLoadingListener listener = new ImageLoadingListener() {
				final private SoftReference<PhotoViewAttacher> mAttacher = tAttacher;
				final private SoftReference<ProgressBar> mProgressBar = tProgressBar;

				@Override
				public void onLoadingStarted(String imageUri, View view) {
					try {
						if (mProgressBar.get() != null) {
							mProgressBar.get().setVisibility(ProgressBar.VISIBLE);
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					try {
						if (mAttacher.get() != null) {
							mAttacher.get().update();
						}
						if (mProgressBar.get() != null) {
							mProgressBar.get().setVisibility(ProgressBar.GONE);
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					try {
						if (mAttacher.get() != null) {
							mAttacher.get().update();
						}
						if (mProgressBar.get() != null) {
							mProgressBar.get().setVisibility(ProgressBar.GONE);
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					try {
						if (mAttacher.get() != null) {
							mAttacher.get().update();
						}
						if (mProgressBar.get() != null) {
							mProgressBar.get().setVisibility(ProgressBar.GONE);
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			};
			ImageLoadingProgressListener progressListener = new ImageLoadingProgressListener() {
				final private SoftReference<ProgressBar> mProgressBar = tProgressBar;

				@Override
				public void onProgressUpdate(String imageUri, View view, int current, int total) {
					try {
						int tProgress = (int) (((float) current / (float) total) * 100F);
						if (mProgressBar.get() != null) {
							mProgressBar.get().setProgress(tProgress);
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			};
			loadImageUseAUIL(context, uri, attacher.getImageView(), getDisplayImageOptions(defaultImage), listener, progressListener);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化AndroidUniversalImageLoader图片框架
	 * 
	 * @param context
	 *            设备上下文
	 * @return AUIL实例
	 */
	public static ImageLoader initAndroidUniversalImageLoader(Context context) {
		ImageLoader result = null;
		try {
			// 显示选项
			DisplayImageOptions options = getDisplayImageOptions();
			if ((context != null) && (options != null)) {
				// 初始化选项
				ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).memoryCache(new WeakMemoryCache())
						.threadPriority(Thread.NORM_PRIORITY - 3).diskCacheFileNameGenerator(new Md5FileNameGenerator())
						// 50M
						.diskCacheSize(50 * 1024 * 1024).defaultDisplayImageOptions(options).build();
				// 初始化，并且把显示选项设置为默认
				result = ImageLoader.getInstance();
				if (!result.isInited()) {
					result.init(config);
				}
				// 释放所有引用
				config = null;
			}
			// 释放所有引用
			options = null;
			context = null;
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 获得图片显示选项
	 * 
	 * @return 显示选项
	 */
	public static DisplayImageOptions getDisplayImageOptions() {
		return getDisplayImageOptions(null, null);
	}

	/**
	 * 获得图片显示选项
	 * 
	 * @param defaultImage
	 *            默认图片
	 * @return 显示选项
	 */
	public static DisplayImageOptions getDisplayImageOptions(Integer defaultImage) {
		return getDisplayImageOptions(defaultImage, null);
	}

	/**
	 * 获得图片显示选项
	 * 
	 * @param defaultImage
	 *            默认图片
	 * @param imageScaleType
	 *            图片缩放方式
	 * @return 显示选项
	 */
	public static DisplayImageOptions getDisplayImageOptions(Integer defaultImage, ImageScaleType imageScaleType) {
		DisplayImageOptions result = null;
		try {
			com.nostra13.universalimageloader.core.DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
					.resetViewBeforeLoading(false).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(false).cacheInMemory(true)
					.cacheOnDisk(true);
			if (defaultImage != null) {
				builder = builder.showImageOnFail((int) defaultImage).showImageForEmptyUri((int) defaultImage);
			}
			if (imageScaleType != null) {
				builder = builder.imageScaleType(imageScaleType);
			}
			result = builder.build();
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 从Uri获得一个File地址。<br/>
	 * 从系统图库返回的是图片Uri，使用这个方法可以获得File地址
	 * 
	 * @param context
	 *            设备上下文
	 * @param uri
	 *            Uri地址
	 * @return File地址
	 */
	public static String getFileFromUri(Context context, Uri uri) {
		String result = null;
		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(uri, new String[] { MediaStore.Images.Media.DATA }, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			result = cursor.getString(column_index);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return result;
	}

}

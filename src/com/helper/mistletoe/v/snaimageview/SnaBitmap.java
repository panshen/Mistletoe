package com.helper.mistletoe.v.snaimageview;

import java.io.File;

import android.content.Context;
import android.net.Uri;
import android.util.SparseArray;

import com.helper.mistletoe.util.Array_Util;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.SnaEnum;

public interface SnaBitmap {
	/**
	 * 没有图片
	 */
	public final static int PATH_TYPE_NULL = 0;
	/**
	 * Android媒体库Uri
	 */
	public final static int PATH_TYPE_URI = 1;
	/**
	 * 文件
	 */
	public final static int PATH_TYPE_FILE = 2;
	/**
	 * 罗盘图片Id
	 */
	public final static int PATH_TYPE_ID = 3;
	/**
	 * 资源文件Id
	 */
	public final static int PATH_TYPE_RESOURCES = 4;

	/**
	 * 罗盘云端图片：小图片
	 */
	public final static int NET_SMALL = 0;
	/**
	 * 罗盘云端图片：中图
	 */
	public final static int NET_MIDDLE = 1;
	/**
	 * 罗盘云端图片：大图片
	 */
	public final static int NET_BIG = 2;
	/**
	 * 罗盘云端图片：原图
	 */
	public final static int NET_NORMAL = 3;

	/**
	 * 获得用于在AUIL上显示的图片路径
	 * 
	 * @param context
	 *            设备上下文
	 * @return 路径
	 */
	public String getAUILShowPath(Context context);

	/**
	 * 获取图片的类型
	 * 
	 * @return
	 */
	public int getType();

	/**
	 * 将图片设置为空
	 */
	public void setPath_Null();

	/**
	 * 设置Uri路径
	 * 
	 * @param uri
	 *            Uri
	 */
	public void setPath(Uri uri);

	/**
	 * 获取Uri路径
	 * 
	 * @return Uri路径
	 */
	public Uri getPath_Uri();

	/**
	 * 设置文件路径
	 * 
	 * @param file
	 *            文件
	 */
	public void setPath(File file);

	/**
	 * 获取文件路径
	 * 
	 * @return 文件
	 */
	public File getPath_File();

	/**
	 * 设置罗盘Id
	 * 
	 * @param imageId
	 *            罗盘文件Id
	 * @param sz
	 *            文件尺寸
	 */
	public void setPath(String imageId, int sz);

	/**
	 * 设置罗盘Id
	 * 
	 * @param imageId
	 *            罗盘文件Id
	 * @param sz
	 *            文件尺寸
	 */
	public void setPath(int imageId, int sz);

	/**
	 * 获取文件罗盘Id
	 * 
	 * @return 罗盘文件Id
	 */
	public int getPath_Id();

	/**
	 * 获取文件尺寸
	 * 
	 * @return 尺寸
	 */
	public int getPath_Id_sz();

	/**
	 * 设置资源文件地址
	 * 
	 * @param Resources
	 *            资源文件地址
	 */
	public void setPath(int Resources);

	/**
	 * 获取资源文件地址
	 * 
	 * @return 资源文件地址
	 */
	public int getPath_Resources();

	/**
	 * 内容是否无效地址
	 * 
	 * @return true表示内容有效，false表示内容无效
	 */
	public boolean isContentEffective();

	/**
	 * 由张久瑞在2015年5月25日创建<br/>
	 * 图片路径类型
	 * 
	 * @author 张久瑞
	 */
	public enum PathType implements SnaEnum {
		Null(0, "没有图片"), Uri(1, "Android媒体库Uri"), File(2, "文件"), Id(3, "罗盘图片Id"), Resources(4, "资源文件Id");

		private int mInt;
		private String mDescription;

		private PathType(int _int, String _Description) {
			this.mInt = _int;
			this.mDescription = _Description;
		}

		@Override
		public String getDescription() {
			return this.mDescription;
		}

		@Override
		public int toInt() {
			return this.mInt;
		}

		public static PathType valueOf(int value, PathType defaultValue) {
			PathType result = defaultValue;
			try {
				SparseArray<PathType> valueMap = Array_Util.getEnumValueMap(PathType.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static PathType valueOf(int value) {
			return valueOf(value, Null);
		}
	}

	/**
	 * 由张久瑞在2015年5月25日创建<br/>
	 * 图片大小
	 * 
	 * @author 张久瑞
	 */
	public enum ImageSize implements SnaEnum {
		Small(0, "小图片"), Middle(1, "中图"), Big(2, "大图片"), Normal(3, "原图");

		private int mInt;
		private String mDescription;

		private ImageSize(int _int, String _Description) {
			this.mInt = _int;
			this.mDescription = _Description;
		}

		@Override
		public String getDescription() {
			return this.mDescription;
		}

		@Override
		public int toInt() {
			return this.mInt;
		}

		public static ImageSize valueOf(int value, ImageSize defaultValue) {
			ImageSize result = defaultValue;
			try {
				SparseArray<ImageSize> valueMap = Array_Util.getEnumValueMap(ImageSize.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static ImageSize valueOf(int value) {
			return valueOf(value, Small);
		}
	}
}
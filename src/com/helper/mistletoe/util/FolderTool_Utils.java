package com.helper.mistletoe.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.helper.mistletoe.util.sysconst.ConstFile_Util;

/**
 * 文件工具
 * 
 * @author 张久瑞
 * @version 1.0
 *
 */
public class FolderTool_Utils {
	/**
	 * 获得程序内存卡文件夹
	 * 
	 * @return 路径
	 */
	public static String getAppFolderExternal() {
		String result = null;
		try {
			result = Environment.getExternalStorageDirectory().getAbsolutePath() + ConstFile_Util.AFE_APPFOLDER;
			makeFolderCanWrite(result);
			// 生成nomedia
			File tFile = new File(result);
			tFile = new File(tFile.getAbsolutePath() + "/.nomedia");
			if (!tFile.exists()) {
				tFile.createNewFile();
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 照相储存的位置
	 *
	 * @return 路径
	 */
	public static String getAFE_ImageCamera() {
		String result = null;
		try {
			result = getAppFolderExternal() + ConstFile_Util.AFE_IMAGE_CAMERA;
			makeFolderCanWrite(result);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 压缩后的图片储存的位置
	 * 
	 * @return 路径
	 */
	public static String getAFE_ImageZoom() {
		String result = null;
		try {
			result = getAppFolderExternal() + ConstFile_Util.AFE_IMAGE_ZOOM;
			makeFolderCanWrite(result);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 上传图片时储存的位置
	 * 
	 * @return 路径
	 */
	public static String getAFE_UploadHead() {
		String result = null;
		try {
			result = getAppFolderExternal() + ConstFile_Util.AFE_IMAGE_UPLOADHEAD;
			makeFolderCanWrite(result);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 获得程序内存卡文件夹
	 * 
	 * @return 路径
	 */
	public static String getAppFolder(Context context) {
		String result = null;
		try {
			result = context.getFilesDir().getAbsolutePath();
			makeFolderCanWrite(result);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 照相储存的位置
	 *
	 * @return 路径
	 */
	public static String getAF_ImageCamera(Context context) {
		String result = null;
		try {
			result = getAppFolder(context) + ConstFile_Util.AF_IMAGE_CAMERA;
			FolderTool_Utils.makeFolderCanWrite(result);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 压缩后的图片储存的位置
	 * 
	 * @return 路径
	 */
	public static String getAF_ImageZoom(Context context) {
		String result = null;
		try {
			result = getAppFolder(context) + ConstFile_Util.AF_IMAGE_ZOOM;
			FolderTool_Utils.makeFolderCanWrite(result);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 使一个文件变为新文件
	 * 
	 * @param filePath
	 *            文件路径
	 */
	public static void makeFileNew(String filePath) {
		try {
			File f = new File(filePath);
			// 首先判断父文件夹是否存在
			makeFolderCanWrite(f.getParent());
			// 然后判断这个文件是否存在
			if (f.exists()) {
				// 如果存在删除
				f.delete();
			}
			// 新建文件
			f.createNewFile();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 如果一个文件夹不存在，则建立
	 * 
	 * @param filePath
	 *            文件路径
	 */
	public static void makeFolderCanWrite(String filePath) {
		try {
			File f = new File(filePath);
			// 判断这个文件是否存在
			if (f.exists()) {
			} else {
				// 如果不存在新建
				f.mkdirs();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 清空一个文件夹（不包括子文件夹）
	 * 
	 * @param filePath
	 *            文件路径
	 */
	public static void makeFolderEmpty(String filePath) {
		try {
			File f = new File(filePath);
			// 判断这个文件是否存在
			if (f.exists()) {
				File[] childFile = f.listFiles();
				if ((childFile != null) && (childFile.length > 0)) {
					for (File temp : childFile) {
						if ((temp.exists()) && (temp.isFile())) {
							temp.delete();
						}
					}
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
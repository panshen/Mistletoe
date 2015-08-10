package com.helper.mistletoe.util;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;

import com.helper.mistletoe.m.pojo.Schedule_Bean;

public class TestClass_Util {
	private final static String FOLDER = "/Sna";
	private final static String DATABASE = "databases";

	public static void startTest(Context context) {
		try {
			Schedule_Bean.sendSchedule_Location(context, 437, null, "这是地理位置的测试", 116.229494D, 40.054693D);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static void copyDatabase(Context context) {
		try {
			mirrorAllFile(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private static void mirrorAllFile(Context context) {
		try {
			mirrorFile(context, (String) null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void mirrorDataBase(Context context) {
		try {
			mirrorFile(context, (String) DATABASE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void mirrorFile(Context context, String folder) {
		try {
			ArrayList<String> tList = new ArrayList<String>();
			tList.add(folder);
			mirrorFile(context, tList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void mirrorFile(Context context, ArrayList<String> folder) {
		try {
			boolean isContentSafe = true;
			if ((getAefFolder(context) == null) || (getAefFolder(context).equals(""))) {
				isContentSafe = false;
			}
			if (folder == null) {
				isContentSafe = false;
			}
			if (isContentSafe) {
				for (String i : folder) {
					String tFrom = getAFolder(context);
					if (i != null) {
						tFrom += "/" + i;
					}
					String tTo = getAefFolder(context);
					if (i != null) {
						tTo += "/" + i;
					}
					FileTool_Utils.copyFolder(tFrom, tTo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getAFolder(Context context) {
		String result = null;
		try {
			result = context.getFilesDir().getParentFile().getAbsolutePath();
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		return result;
	}

	private static String getAefFolder(Context context) {
		String result = null;
		try {
			boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
			if (sdCardExist) {
				result = Environment.getExternalStorageDirectory().getAbsolutePath();// 获取跟目录
				result += FOLDER + "/" + new File(getAFolder(context)).getName();
			}
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		return result;
	}

}
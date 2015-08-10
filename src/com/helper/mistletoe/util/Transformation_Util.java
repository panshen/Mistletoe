package com.helper.mistletoe.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.util.SparseArray;

import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.m.pojo.Target_Enum;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRunningState;

public class Transformation_Util {
	/**
	 * 将String转换为int
	 * 
	 * @param source
	 *            源String
	 * @return 结果，如果失败返回0
	 */
	public static int String2int(String source) {
		return String2int(source, 0);
	}

	/**
	 * 将String转换为int
	 * 
	 * @param source
	 *            源String
	 * @param defaultValue
	 *            默认值，转换失败时返回默认值
	 * @return 结果
	 */
	public static int String2int(String source, int defaultValue) {
		int result = defaultValue;
		try {
			boolean isDataSafe = true;
			// 内容安全检查
			if ((source == null) || (getOnlyNumber(source).trim().equals(""))) {
				isDataSafe = false;
			}
			// 内容安全才执行
			if (isDataSafe) {
				result = Integer.valueOf(getOnlyNumber(source));
			}
		} catch (Exception e) {
			result = defaultValue;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static long Sring2long(String source) {
		return Sring2long(source, 0L);
	}

	public static long Sring2long(String source, long defaultValue) {
		long result = defaultValue;
		try {
			boolean isDataSafe = true;
			// 内容安全检查
			if ((source == null) || (getOnlyNumber(source).trim().equals(""))) {
				isDataSafe = false;
			}
			// 内容安全才执行
			if (isDataSafe) {
				result = Long.valueOf(getOnlyNumber(source));
			}
		} catch (Exception e) {
			result = defaultValue;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static float Sring2float(String source) {
		return Sring2float(source, 0.0F);
	}

	public static float Sring2float(String source, float defaultValue) {
		float result = defaultValue;
		try {
			boolean isDataSafe = true;
			// 内容安全检查
			if ((source == null) || (source.trim().equals(""))) {
				isDataSafe = false;
			}
			// 内容安全才执行
			if (isDataSafe) {
				result = Long.valueOf(source);
			}
		} catch (Exception e) {
			result = defaultValue;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static boolean Sring2boolean(String source) {
		return Sring2boolean(source, false);
	}

	public static boolean Sring2boolean(String source, boolean defaultValue) {
		boolean result = defaultValue;
		try {
			boolean isDataSafe = true;
			// 内容安全检查
			if ((source == null) || (source.trim().equals(""))) {
				isDataSafe = false;
			}
			// 内容安全才执行
			if (isDataSafe) {
				result = Boolean.valueOf(source);
			}
		} catch (Exception e) {
			result = defaultValue;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static TargetRunningState MemberStatus2TargetRunningState(MemberStatus source) {
		return MemberStatus2TargetRunningState(source, TargetRunningState.Running);
	}

	public static TargetRunningState MemberStatus2TargetRunningState(MemberStatus source, TargetRunningState defaultValue) {
		TargetRunningState result = defaultValue;
		try {
			boolean isDataSafe = true;
			// 内容安全检查
			if (source == null) {
				isDataSafe = false;
			}
			// 内容安全才执行
			if (isDataSafe) {
				HashMap<TargetRunningState, ArrayList<MemberStatus>> tMap = Target_Enum.getStatusMap();
				for (Iterator<TargetRunningState> iterator = tMap.keySet().iterator(); iterator.hasNext();) {
					TargetRunningState tTemp = iterator.next();
					if (tMap.get(tTemp).contains(source)) {
						result = tTemp;
					}
				}
			}
		} catch (Exception e) {
			result = defaultValue;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static ArrayList<MemberStatus> TargetRunningState2MemberStatus(TargetRunningState source) {
		ArrayList<MemberStatus> defaultValue = new ArrayList<MemberStatus>();
		try {
			defaultValue.addAll(Arrays.asList(new MemberStatus[] { MemberStatus.Discussing }));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return TargetRunningState2MemberStatus(source, defaultValue);
	}

	public static ArrayList<MemberStatus> TargetRunningState2MemberStatus(TargetRunningState source, ArrayList<MemberStatus> defaultValue) {
		ArrayList<MemberStatus> result = defaultValue;
		try {
			boolean isDataSafe = true;
			// 内容安全检查
			if (source == null) {
				isDataSafe = false;
			}
			// 内容安全才执行
			if (isDataSafe) {
				result = Target_Enum.getStatusMap().get(source);
			}
		} catch (Exception e) {
			result = defaultValue;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@SuppressLint("UseSparseArrays")
	public static <T> HashMap<Integer, T> SparseArray2HashMap(SparseArray<T> source) {
		HashMap<Integer, T> result = new HashMap<Integer, T>();
		try {
			boolean isDataSafe = true;
			// 内容安全检查
			if (source == null) {
				isDataSafe = false;
			}
			// 内容安全才执行
			if (isDataSafe) {
				for (int i = 0; i <= source.size(); i++) {
					T temp = source.get(i);
					if (temp != null) {
						result.put(i, temp);
					}
				}
			}
		} catch (Exception e) {
			result = new HashMap<Integer, T>();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static ArrayList<Integer> JSONArray2ArrayList$Integer(JSONArray source) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		try {
			boolean isDataSafe = true;
			// 内容安全检查
			if (source == null) {
				isDataSafe = false;
			}
			// 内容安全才执行
			if (isDataSafe) {
				for (int i = 0; i < source.length(); i++) {
					Integer temp = source.getInt(i);
					if (temp != null) {
						result.add(temp);
					}
				}
			}
		} catch (Exception e) {
			result = new ArrayList<Integer>();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static JSONArray ArrayList$Integer2JSONArray(ArrayList<Integer> source) {
		JSONArray result = new JSONArray();
		try {
			boolean isDataSafe = true;
			// 内容安全检查
			if (source == null) {
				isDataSafe = false;
			}
			// 内容安全才执行
			if (isDataSafe) {
				result = new JSONArray(source);
			}
		} catch (Exception e) {
			result = new JSONArray();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static ArrayList<Integer> String2ArrayList$Integer(String source) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		try {
			boolean isDataSafe = true;
			// 内容安全检查
			if (source == null) {
				isDataSafe = false;
			}
			// 内容安全才执行
			if (isDataSafe) {
				JSONArray tArray = new JSONArray();
				try {
					tArray = new JSONArray(source);
				} catch (Exception e) {
					ExceptionHandle.ignoreException(e);
				}
				result = JSONArray2ArrayList$Integer(tArray);
			}
		} catch (Exception e) {
			result = new ArrayList<Integer>();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static String ArrayList$Integer2String(ArrayList<Integer> source) {
		String result = new JSONArray().toString();
		try {
			boolean isDataSafe = true;
			// 内容安全检查
			if (source == null) {
				isDataSafe = false;
			}
			// 内容安全才执行
			if (isDataSafe) {
				JSONArray tArray = new JSONArray();
				try {
					tArray = new JSONArray(source);
				} catch (Exception e) {
					ExceptionHandle.ignoreException(e);
				}
				result = tArray.toString();
			}
		} catch (Exception e) {
			result = new JSONArray().toString();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 从一个字符串中提取出纯数字
	 * 
	 * @param source
	 *            源字符串
	 * @return 结果
	 */
	private static String getOnlyNumber(String source) {
		String result = "";// 默认值""
		try {
			boolean isDataSafe = true;
			// 内容安全检查
			if ((source == null) || (source.trim().equals(""))) {
				isDataSafe = false;// 如果源字符串为null，或者源字符串没有有意义字符，数据就不安全
			}
			// 内容安全才执行
			if (isDataSafe) {
				result = Pattern.compile("[^0-9]").matcher(source).replaceAll("").trim();
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
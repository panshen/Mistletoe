package com.helper.mistletoe.util;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;

public class Array_Util {
	/**
	 * 把字符串拼接成一个数组<br/>
	 * 示例：<br/>
	 * array={1,2,3};<br/>
	 * Array_Util.anArrayOfJoiningTogetherIntoAString(array,"(",")","\"","\"",
	 * ",");<br/>
	 * 结果为("1","2","3")
	 * 
	 * @param array
	 *            来源数组
	 * @param startChar
	 *            整个字符串开头
	 * @param endChar
	 *            整个字符串结尾
	 * @param leftString
	 *            左边字符
	 * @param rightString
	 *            右边字符
	 * @param linkString
	 *            中间的链接符号
	 * @return 未知异常
	 */
	public static <T_arrayToSelectIn> String anArrayOfJoiningTogetherIntoAString(List<T_arrayToSelectIn> array, String startChar, String endChar, String leftString, String rightString,
			String linkString) {
		String result = "";
		try {
			// 来值安检
			startChar = (startChar == null) ? "" : startChar;
			endChar = (endChar == null) ? "" : endChar;
			leftString = (leftString == null) ? "" : leftString;
			rightString = (rightString == null) ? "" : rightString;
			linkString = (linkString == null) ? "" : linkString;
			// 开始业务处理
			if ((array != null) && (array.size() > 0)) {
				for (T_arrayToSelectIn a : array) {
					if (result.equals("")) {
						result += startChar;
					} else {
						result += linkString;
					}
					result += leftString + a + rightString;
				}
				result += endChar;
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static <T> void removeArrayListInvalidData(ArrayList<T> list, T t) {
		try {
			boolean contentIsSafe = true;
			if ((list == null) || (list.size() < 1)) {
				contentIsSafe = false;
			}
			if (contentIsSafe) {
				ArrayList<T> tJustForRemove = new ArrayList<T>();
				tJustForRemove.add(null);
				tJustForRemove.add(t);
				list.removeAll(tJustForRemove);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static <T extends SnaEnum> SparseArray<T> getEnumValueMap(T[] t) {
		SparseArray<T> result = new SparseArray<T>();
		try {
			boolean contentIsSafe = true;
			if (t.length < 1) {
				contentIsSafe = false;
			}
			if (contentIsSafe) {
				for (T i : t) {
					result.put(i.toInt(), i);
				}
			}
		} catch (Exception e) {
			result = new SparseArray<T>();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
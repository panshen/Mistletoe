package com.helper.mistletoe.util;

import java.lang.reflect.Field;
import java.util.HashSet;

import org.json.JSONObject;

import com.google.gson.Gson;

public class Prism_Util {
	/**
	 * 获取一个类成员变量
	 * 
	 * @param o
	 *            类对象
	 * @param fieldName
	 *            变量名
	 * @return 这个成员变量
	 */
	public static Object getField(Object o, String fieldName) {
		return getField(o, fieldName, o.getClass().getName());
	}

	/**
	 * 获取一个类成员变量
	 * 
	 * @param o
	 *            类对象
	 * @param fieldName
	 *            变量名
	 * @return 这个成员变量
	 */
	public static Object getField(Object o, String fieldName, String domain) {
		Object result = null;
		try {
			Field t_field = Class.forName(domain).getDeclaredField(fieldName);
			t_field.setAccessible(true);
			result = t_field.get(o);
			t_field.setAccessible(false);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 透视一个对象的成员变量列表，无法获得它的父类的成员
	 * 
	 * @param o
	 *            要透视的对象
	 * @return 成员列表
	 */
	public static Field[] getFieldsList(Object o) {
		return getFieldsList(o, o.getClass().getName());
	}

	/**
	 * 透视一个对象的成员变量列表，无法获得它的父类的成员
	 * 
	 * @param o
	 *            要透视的对象
	 * @param className
	 *            类名
	 * @return 成员列表
	 */
	public static Field[] getFieldsList(Object o, String className) {
		Field[] result = null;
		try {
			result = Class.forName(className).getDeclaredFields();
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 获得父类列表
	 * 
	 * @param tTrans
	 *            要获得内容的类
	 * @return 未知异常
	 */
	public static Class[] getSuperClassList(Class tTrans) {
		Class[] result = null;
		try {
			HashSet<Class> tSuperClassList = new HashSet<Class>();
			while (tTrans != null) {
				tSuperClassList.add(tTrans);
				tTrans = tTrans.getSuperclass();
			}
			result = new Class[tSuperClassList.size()];
			result = tSuperClassList.toArray(result);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 从另外一个Object复制内容，这两个Object必须是在指定的域中
	 * 
	 * @param domain
	 *            指定的域
	 * @param from
	 *            数据来源
	 * @param to
	 *            目标类
	 */
	public static void copyData(Class domain, Object from, Object to) {
		try {
			boolean dataIsSafe = true;
			if (!(domain.isInstance(to) && domain.isInstance(from))) {
				dataIsSafe = false;
			}
			if (dataIsSafe) {
				// 获得这个类的成员列表
				Field[] tField = domain.getDeclaredFields();
				// 挨个赋值
				for (Field tI : tField) {
					tI.setAccessible(true);
					tI.set(to, tI.get(from));
					tI.setAccessible(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void copyData(Class domain, JSONObject from, Object to) {
		try {
			Gson tGson = new Gson();
			Object tFrom = tGson.fromJson(from.toString(), domain);
			copyData(domain, tFrom, to);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void depthCopyData(Object from, Object to) {
		try {
			Class[] tSuperClassList = getSuperClassList(to.getClass());
			for (Class tDomain : tSuperClassList) {
				copyData(tDomain, from, to);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void depthCopyData(JSONObject from, Object to) {
		try {
			Gson tGson = new Gson();
			Object tFrom = tGson.fromJson(from.toString(), to.getClass());
			depthCopyData(tFrom, to);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
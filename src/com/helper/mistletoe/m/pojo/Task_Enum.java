package com.helper.mistletoe.m.pojo;

import android.util.SparseArray;

import com.helper.mistletoe.util.Array_Util;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.SnaEnum;

public class Task_Enum {
	/**
	 * Task的状态
	 * 
	 * @author 张久瑞
	 */
	public enum TaskStatus implements SnaEnum {
		/**
		 * 草稿
		 */
		Draft(0, "草稿"),
		/**
		 * 完成
		 */
		Complete(1, "完成"),
		/**
		 * 作废
		 */
		Delete(2, "作废");

		private int mInt;
		private String mDescription;

		private TaskStatus(int _int, String _Description) {
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

		public static TaskStatus valueOf(int value, TaskStatus defaultValue) {
			TaskStatus result = defaultValue;
			try {
				SparseArray<TaskStatus> valueMap = Array_Util.getEnumValueMap(TaskStatus.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static TaskStatus valueOf(int value) {
			return valueOf(value, Delete);
		}
	}
}
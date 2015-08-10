package com.helper.mistletoe.m.pojo;

import android.util.SparseArray;

import com.helper.mistletoe.util.Array_Util;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.SnaEnum;

public class Schedule_Enum {
	/**
	 * 进度更新类型
	 * 
	 * @author 张久瑞
	 */
	public enum ScheduleType implements SnaEnum {
		Revoke(-2, "撤销"), Unknown(-1, "未知"), System(0, "系统"), Text(1, "文本"), Sound(2, "语音"), Image(3, "图片"), File(4, "文件"), Remind(5, "提醒"), CostApply(
				6, "费用申请"), CostConfirm(7, "费用确认"), Assessment(8, "评估"), GPS(9, "GPS");

		private int mInt;
		private String mDescription;

		private ScheduleType(int _int, String _Description) {
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

		public static ScheduleType valueOf(int value, ScheduleType defaultValue) {
			ScheduleType result = defaultValue;
			try {
				SparseArray<ScheduleType> valueMap = Array_Util.getEnumValueMap(ScheduleType.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static ScheduleType valueOf(int value) {
			return valueOf(value, Unknown);
		}
	}

	public enum ScheduleRevokeStatus implements SnaEnum {
		Normal(1, "普通"), Revoke(2, "被撤销");

		private int mInt;
		private String mDescription;

		private ScheduleRevokeStatus(int _int, String _Description) {
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

		public static ScheduleRevokeStatus valueOf(int value, ScheduleRevokeStatus defaultValue) {
			ScheduleRevokeStatus result = defaultValue;
			try {
				SparseArray<ScheduleRevokeStatus> valueMap = Array_Util.getEnumValueMap(ScheduleRevokeStatus.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static ScheduleRevokeStatus valueOf(int value) {
			return valueOf(value, Normal);
		}
	}

	public enum ScheduleHighlightFlag implements SnaEnum {
		No(0, "不重要"), Yes(1, "重要进度");

		private int mInt;
		private String mDescription;

		private ScheduleHighlightFlag(int _int, String _Description) {
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

		public static ScheduleHighlightFlag valueOf(int value, ScheduleHighlightFlag defaultValue) {
			ScheduleHighlightFlag result = defaultValue;
			try {
				SparseArray<ScheduleHighlightFlag> valueMap = Array_Util.getEnumValueMap(ScheduleHighlightFlag.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static ScheduleHighlightFlag valueOf(int value) {
			return valueOf(value, No);
		}
	}

	public enum ScheduleViewFlag implements SnaEnum {
		Public(1, "公开"), Private(2, "私聊");

		private int mInt;
		private String mDescription;

		private ScheduleViewFlag(int _int, String _Description) {
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

		public static ScheduleViewFlag valueOf(int value, ScheduleViewFlag defaultValue) {
			ScheduleViewFlag result = defaultValue;
			try {
				SparseArray<ScheduleViewFlag> valueMap = Array_Util.getEnumValueMap(ScheduleViewFlag.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static ScheduleViewFlag valueOf(int value) {
			return valueOf(value, Public);
		}
	}
}
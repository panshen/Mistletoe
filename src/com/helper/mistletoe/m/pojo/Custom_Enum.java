package com.helper.mistletoe.m.pojo;

import android.util.SparseArray;

import com.helper.mistletoe.util.Array_Util;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.SnaEnum;

public class Custom_Enum {
	/**
	 * 内容与服务器同步状态
	 * 
	 * @author 张久瑞
	 */
	public enum SyncStatus implements SnaEnum {
		Local(1, "本地内容"), Syncing(1, "正在同步"), SyncFailure(2, "同步失败"), Synced(3, "已同步");

		private int mInt;
		private String mDescription;

		private SyncStatus(int _int, String _Description) {
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

		public static SyncStatus valueOf(int value, SyncStatus defaultValue) {
			SyncStatus result = defaultValue;
			try {
				SparseArray<SyncStatus> valueMap = Array_Util.getEnumValueMap(SyncStatus.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static SyncStatus valueOf(int value) {
			return valueOf(value, Local);
		}
	}

}
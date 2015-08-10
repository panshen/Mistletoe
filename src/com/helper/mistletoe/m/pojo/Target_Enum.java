package com.helper.mistletoe.m.pojo;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.util.SparseArray;

import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.util.Array_Util;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.SnaEnum;

public class Target_Enum {
	private static SoftReference<HashMap<TargetRunningState, ArrayList<MemberStatus>>> mMap;

	public static HashMap<TargetRunningState, ArrayList<MemberStatus>> getStatusMap() {
		try {
			if ((mMap == null) || (mMap.get() == null)) {
				HashMap<TargetRunningState, ArrayList<MemberStatus>> tMap = new HashMap<TargetRunningState, ArrayList<MemberStatus>>();
				// Running状态映射
				ArrayList<MemberStatus> tList = new ArrayList<MemberStatus>();
				tList.addAll(Arrays.asList(new MemberStatus[] { MemberStatus.Discussing, MemberStatus.Signed,
						MemberStatus.HelperApplyClose, MemberStatus.OwnerApplyClose, MemberStatus.Watch }));
				tMap.put(TargetRunningState.Running, tList);
				// History状态映射
				tList = new ArrayList<MemberStatus>();
				tList.addAll(Arrays.asList(new MemberStatus[] { MemberStatus.Closed }));
				tMap.put(TargetRunningState.History, tList);
				// Deleted状态映射
				tList = new ArrayList<MemberStatus>();
				tList.addAll(Arrays.asList(new MemberStatus[] { MemberStatus.Deleted }));
				tMap.put(TargetRunningState.Deleted, tList);
				// Erased状态映射
				tList = new ArrayList<MemberStatus>();
				tList.addAll(Arrays.asList(new MemberStatus[] { MemberStatus.Erased, MemberStatus.Black, MemberStatus.UnWatch }));
				tMap.put(TargetRunningState.Erased, tList);
				// 将状态映射加入到map中
				mMap = new SoftReference<HashMap<TargetRunningState, ArrayList<MemberStatus>>>(tMap);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return mMap.get();
	}

	/**
	 * 目标运行状态
	 * 
	 * @author 张久瑞
	 */
	public enum TargetRunningState implements SnaEnum {
		Running(1, "正在进行"), History(2, "历史目标"), Deleted(3, "删除目标"), Erased(4, "彻底删除");

		private int mInt;
		private String mDescription;

		private TargetRunningState(int _int, String _Description) {
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

		public static TargetRunningState valueOf(int value, TargetRunningState defaultValue) {
			TargetRunningState result = defaultValue;
			try {
				SparseArray<TargetRunningState> valueMap = Array_Util.getEnumValueMap(TargetRunningState.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static TargetRunningState valueOf(int value) {
			return valueOf(value, Running);
		}
	}

	/**
	 * 目标类型
	 * 
	 * @author 张久瑞
	 */
	public enum TargetType implements SnaEnum {
		General(1, "通用"), System(2, "系统"), Market(3, "市场"), Top(4, "置顶");

		private int mInt;
		private String mDescription;

		private TargetType(int _int, String _Description) {
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

		public static TargetType valueOf(int value, TargetType defaultValue) {
			TargetType result = defaultValue;
			try {
				SparseArray<TargetType> valueMap = Array_Util.getEnumValueMap(TargetType.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static TargetType valueOf(int value) {
			return valueOf(value, General);
		}
	}

	/**
	 * 存在数据库中的目标类型
	 * 
	 * @author 张久瑞
	 */
	public enum TargetRecordType implements SnaEnum {
		/**
		 * 普通目标
		 */
		Tradition(1, "普通目标"),
		/**
		 * 市场里的目标
		 */
		Market(2, "市场里的目标");

		private int mInt;
		private String mDescription;

		private TargetRecordType(int _int, String _Description) {
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

		public static TargetRecordType valueOf(int value, TargetRecordType defaultValue) {
			TargetRecordType result = defaultValue;
			try {
				SparseArray<TargetRecordType> valueMap = Array_Util.getEnumValueMap(TargetRecordType.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static TargetRecordType valueOf(int value) {
			return valueOf(value, Tradition);
		}
	}
}
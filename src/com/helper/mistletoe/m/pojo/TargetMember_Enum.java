package com.helper.mistletoe.m.pojo;

import android.util.SparseArray;

import com.helper.mistletoe.util.Array_Util;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.SnaEnum;

public class TargetMember_Enum {
	/**
	 * 目标成员角色
	 * 
	 * @author 张久瑞
	 */
	public enum MemberRole implements SnaEnum {
		Owner(1, "创建者"), Admin(2, "管理员"), Helper(3, "助手"), Observer(4, "观察者");

		private int mInt;
		private String mDescription;

		private MemberRole(int _int, String _Description) {
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

		public static MemberRole valueOf(int value, MemberRole defaultValue) {
			MemberRole result = defaultValue;
			try {
				SparseArray<MemberRole> valueMap = Array_Util.getEnumValueMap(MemberRole.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static MemberRole valueOf(int value) {
			return valueOf(value, Helper);
		}
	}

	/**
	 * 目标成员状态
	 * 
	 * @author 张久瑞
	 */
	public enum MemberStatus implements SnaEnum {
		Discussing(1, "协商"), Signed(2, "签约"), HelperApplyClose(3, "帮手申请关闭"), OwnerApplyClose(4, "owner申请关闭"), Closed(5, "关闭"), Deleted(6,
				"删除"), Erased(7, "清除"), Watch(8, "关注"), Black(9, "禁止关注"), UnWatch(10, "未关注");

		private int mInt;
		private String mDescription;

		private MemberStatus(int _int, String _Description) {
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

		public static MemberStatus valueOf(int value, MemberStatus defaultValue) {
			MemberStatus result = defaultValue;
			try {
				SparseArray<MemberStatus> valueMap = Array_Util.getEnumValueMap(MemberStatus.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static MemberStatus valueOf(int value) {
			return valueOf(value, Discussing);
		}
	}

	/**
	 * 
	 * 
	 * @author 张久瑞
	 * 
	 */
	public enum UpdateStatusRequestNumber implements SnaEnum {
		/**
		 * 未知
		 */
		UnKnown(0, "未知", ""),
		/**
		 * 协商状态，帮手同意目标
		 */
		HELPER_AGREE(1, "协商状态，帮手同意目标", "确定加入目标吗"),
		/**
		 * 协商状态，帮手拒绝目标
		 */
		HELPER_REJECT(2, "协商状态，帮手拒绝目标", "确定拒绝目标吗"),
		/**
		 * 签约状态，目标创建者对选定帮手执行关闭操作
		 */
		OWNER_CLOSE_MEMBER(3, "签约状态，目标创建者对选定帮手执行关闭操作", "确定要将该帮手请出目标吗"),
		/**
		 * 签约状态，目标创建者申请关闭目标
		 */
		OWNER_CLOSE_TARGET(4, "签约状态，目标创建者申请关闭目标", "确定要关闭目标吗，所有的帮手都会被请出"),
		/**
		 * 签约状态，帮手申请关闭目标
		 */
		HELPER_CLOSE_TARGET(5, "签约状态，帮手申请关闭目标", "确定要从该目标中退出吗"),
		/**
		 * 签约状态，目标创建者同意帮手的关闭申请
		 */
		OWNER_AGREE_CLOSE(6, "签约状态，目标创建者同意帮手的关闭申请", "点击确定，允许帮手离开"),
		/**
		 * 签约状态，目标创建者拒绝帮手的关闭申请
		 */
		OWNER_REJECT_CLOSE(7, "签约状态，目标创建者拒绝帮手的关闭申请", "点击确定，拒绝帮手离开"),
		/**
		 * owner申请关闭状态，帮手同意关闭申请
		 */
		HELPER_AGREE_CLOSE(8, "owner申请关闭状态，帮手同意关闭申请", "点击确定，你将从目标中离开"),
		/**
		 * owner申请关闭状态，帮手拒绝关闭申请
		 */
		HELPER_REJECT_CLOSE(9, "owner申请关闭状态，帮手拒绝关闭申请", "点击确定，你继续留在目标中"),
		/**
		 * 关闭状态，删除目标
		 */
		DELETE_TARGET(10, "关闭状态，删除目标", "目标会被移动到[删除目标]"),
		/**
		 * 删除状态，清除目标
		 */
		ERASE_TARGET(11, "删除状态，清除目标", "目标会被彻底删除"),
		/**
		 * 删除状态，恢复目标
		 */
		RESTORE_TARGET(12, "删除状态，恢复目标", "目标会被移动到[归档目标]");

		private int mInt;
		private String mDescription;
		private String mOperationRemind;

		private UpdateStatusRequestNumber(int _int, String _Description, String _OperationRemind) {
			this.mInt = _int;
			this.mDescription = _Description;
			this.mOperationRemind = _OperationRemind;
		}

		@Override
		public String getDescription() {
			return this.mDescription;
		}

		public String getOperationRemind() {
			return this.mOperationRemind;
		}

		@Override
		public int toInt() {
			return this.mInt;
		}

		public static UpdateStatusRequestNumber valueOf(int value, UpdateStatusRequestNumber defaultValue) {
			UpdateStatusRequestNumber result = defaultValue;
			try {
				SparseArray<UpdateStatusRequestNumber> valueMap = Array_Util.getEnumValueMap(UpdateStatusRequestNumber.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static UpdateStatusRequestNumber valueOf(int value) {
			return valueOf(value, UnKnown);
		}
	}
}
package com.helper.mistletoe.util;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import com.helper.mistletoe.c.service.ContacterSyncService;
import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;

public class Instruction_Utils {
	/**
	 * @param context
	 * @param instruction
	 * @exception 只有指令
	 *                ，不携带其他数据 instruction: UPLOAD_ADDRESSBOOK 发送上传通讯录指令
	 *                SYNCHRONOUS_HELPER_AND_GROUP 发送同步helper与group指令
	 *                SYNCHRONOUS_HELPER 发送同步helper指令 SYNCHRONOUS_GROUP
	 *                发送同步group的指令 SYNCHRONOUS_USER 发送同步User的指令 CHECK_VERSION
	 *                发送检查版本指令
	 */
	public static void sendInstrustion(Context context, Integer instruction) {
		Intent intent = new Intent(context, ContacterSyncService.class);
		intent.putExtra("instruction", instruction);
		context.startService(intent);
	}

	/**
	 * 
	 * @param context
	 * @param instruction
	 * @param keyWord
	 * @exception 除了指令以外
	 *                ，还有一个string，这个string可能是，group_id，helper_id，status等单个字符串
	 *                instruction: RESPOND_DUANG 发送回应duang的指令 UPDATE_USER_PUBLIC
	 *                发送变更公共帮手状态指令
	 * 
	 * 
	 */
	public static void sendInstrustion(Context context, Integer instruction, String keyWord) {
		Intent intent = new Intent(context, ContacterSyncService.class);
		intent.putExtra("instruction", instruction);
		intent.putExtra("public_status", keyWord);
		intent.putExtra("s", keyWord);
		intent.putExtra("source", keyWord);
		context.startService(intent);

	}

	/**
	 * @param context
	 * @param instruction
	 * @param id
	 * @exception 除了指令以外
	 *                ，还有一个Integer，这个Integer可能是，group_id，helper_id，status等
	 *                instruction: SYNCHRONOUS_HELPER_BY_ID 发送同步单个Helper的指令
	 *                PULL_BACK 发送拉黑Helper的指令 ACTION_DUANG 发送duang Helper的指令
	 *                DELETE_GROUP 发送删除组的指令 SYNCHRONOUS_GROUP_MEMBER
	 *                发送同步单个group的Member的指令 REMOVE_BLACKLIST 发送解除黑名单指令
	 *                TURN_TO_OLD_HELPER 发送移除新帮手列表，变为旧帮手 AGREE_TO_ADD
	 *                发送同意添加的指令，变为新帮手 REFUSE_TO_ADD 发送拒绝添加的指令，变为6 KICK_DEVICE
	 *                发送踢出设备的指令
	 */
	public static void sendInstrustion(Context context, Integer instruction, Integer id) {
		Intent intent = new Intent(context.getApplicationContext(), ContacterSyncService.class);
		intent.putExtra("instruction", instruction);
		intent.putExtra("group_id", id);
		intent.putExtra("helper_id", id);
		intent.putExtra("target_id", id);
		intent.putExtra("device_id", id);
		context.startService(intent);
	}

	/**
	 * @param context
	 * @param instruction
	 * @param user
	 * @exception 除了指令以外
	 *                ，还有一个user对象
	 */
	public static void sendInstrustion(Context context, Integer instruction, User_Bean user) {
		Intent intent = new Intent(context, ContacterSyncService.class);
		intent.putExtra("instruction", instruction);
		intent.putExtra("user", new Gson().toJson(user));
		context.startService(intent);
	}

	/**
	 * 
	 * 
	 * @param context
	 * @param instruction
	 * @param helper
	 * @exception 除了指令以外
	 *                ，还有一个helper对象 instruction: UPDATE_HELPER_MEMONAME
	 *                发送修改HelperMemoName的指令 INVITE_HELPER 发送添加帮手的指令
	 */
	public static void sendInstrustion(Context context, Integer instruction, Helpers_Sub_Bean helper) {
		Intent intent = new Intent(context, ContacterSyncService.class);
		intent.putExtra("instruction", instruction);
		intent.putExtra("helper", helper);
		context.startService(intent);

	}

	/**
	 * @param context
	 * @param instruction
	 * @param group_bean
	 * @exception 除了指令以外
	 *                ，还有一个group对象 instruction: GREATE_GROUP 发送创建group的指令
	 *                UPDATE_GROUP_INFORMATION 发送修改group信息的指令
	 *                UPDATE_GROUP_MEMBER 发送修改group成员的指令 ADD_HELPER_TO_GROUP
	 *                发送修改group的member的指令
	 */
	public static void sendInstrustion(Context context, Integer instruction, Group_Bean group_bean) {
		Intent intent = new Intent(context, ContacterSyncService.class);
		intent.putExtra("instruction", instruction);
		intent.putExtra("group", group_bean);
		context.startService(intent);
	}

	/**
	 * 1000~~~1999 IS other
	 */
	public static final int UPLOAD_ADDRESSBOOK = 1000;// 上传通讯录指令
	public static final int ACTION_DUANG = 1001;// duang Helper的指令
	public static final int ACTION_DUANG_ARRAY = 1002;// duang Helpers的指令
	public static final int KICK_DEVICE = 1003;// 踢出设备的指令
	public static final int CHECK_VERSION = 1004;// 检查版本指令
	public static final int SYNCHRONOUS_CONSTANTS = 1005;//常量的同步指令
	public static final int SYNCHRONOUS_COSTTAG = 1006;//费用标签的同步指令
	/**
	 * 2000~~~2999 IS user
	 */
	public static final int SYNCHRONOUS_USER = 2000;// 同步user指令
	public static final int UPDATE_USER = 2001;// 更新user指令
	public static final int UPDATE_USER_PUBLIC = 2002;// 变更公共帮手状态指令
	/**
	 * 3000~~~3999 IS helper
	 */
	public static final int SYNCHRONOUS_HELPER = 3000;// 同步helper指令
	public static final int SYNCHRONOUS_HELPER_BY_ID = 3001;// 同步单个Helper的指令
	public static final int SYNCHRONOUS_HELPER_AND_GROUP = 3002;// 同步helper与group指令
	public static final int PULL_BACK = 3003;// 拉黑Helper的指令
	public static final int REMOVE_BLACKLIST = 3004;// 解除黑名单指令
	public static final int TURN_TO_OLD_HELPER = 3005;// 移除新帮手列表，变为旧帮手
	public static final int AGREE_TO_ADD = 3006;// 同意添加的指令，变为新帮手
	public static final int REFUSE_TO_ADD = 3007;// 拒绝添加的指令，变为6
	public static final int UPDATE_HELPER_MEMONAME = 3008;// 修改HelperMemoName的指令
	public static final int INVITE_HELPER = 3009;// 添加帮手的指令
	public static final int ADD_HELPER_TO_GROUP = 3010;// 修改group的member的指令
	public static final int TURN_TO_FOUR = 3011;//删除新帮手界面的数据，变为4，既不同意也不拒绝
	/**
	 * 4000~~~4999 IS group
	 */
	public static final int CREATE_GROUP = 4000;// 创建group的指令
	public static final int SYNCHRONOUS_GROUP = 4001;// 同步group指令
	public static final int DELETE_GROUP = 4002;// 删除组的指令
	public static final int SYNCHRONOUS_GROUP_MEMBER = 4003;// 同步单个group的Member的指令
	public static final int UPDATE_GROUP_INFORMATION = 4004;// 修改group信息的指令
	public static final int UPDATE_GROUP_MEMBER = 4005;// 修改group成员的指令
}

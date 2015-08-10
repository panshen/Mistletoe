package com.helper.mistletoe.util;

import com.helper.mistletoe.util.sysconst.Const_Schedule_DB;
import com.helper.mistletoe.util.sysconst.Const_TargetMember_DB;
import com.helper.mistletoe.util.sysconst.Const_Target_DB;
import com.helper.mistletoe.util.sysconst.Const_Task_DB;

/**
 * 数据库常量
 */
public interface Const_DB extends Const_Target_DB, Const_TargetMember_DB, Const_Schedule_DB, Const_Task_DB {
	/**
	 * 数据库名字
	 */
	public final String DB_NAME = "readyGo";
	/**
	 * 数据库的版本
	 */
	public final int DB_VERSION = 14;
	/**
	 * helper表
	 */
	public static final String DATABASE_TABLE_HELPERS = "Helpers";// 表名
	public static final String DATABASE_TABLE_HELPERS_KEY = "_id";// 主键
	public static final String DATABASE_TABLE_HELPERS_ID = "helper_id";// 帮手的id
	public static final String DATABASE_TABLE_HELPERS_ACCOUNT = "helper_account";// 帮手的账号
	public static final String DATABASE_TABLE_HELPERS_ACCOUNTTYPE = "helper_account_type";// 账号类型，0为手机，1为邮箱,-1为组
	public static final String DATABASE_TABLE_HELPERS_NAME = "helper_name";// 显示名
	public static final String DATABASE_TABLE_HELPERS_MEMONAME = "helper_memo_name";// 备注名
	public static final String DATABASE_TABLE_HELPERS_READYGONAME = "helper_readygo_name";// 账号名
	public static final String DATABASE_TABLE_HELPERS_NAMEPINYIN = "helper_name_pinyin";// 帮手显示名字的拼音，备注名优先于账号名
	public static final String DATABASE_TABLE_HELPERS_MEMONAMEPINYIN = "helper_memo_name_pinyin";// 帮手备注名字的拼音
	public static final String DATABASE_TABLE_HELPERS_READYGONAMEPINYIN = "helper_readygo_name_pinyin";// 帮手账号名字的拼音
	public static final String DATABASE_TABLE_HELPERS_TEL = "helper_tel";// 帮手的座机号
	public static final String DATABASE_TABLE_HELPERS_MOBILE = "helper_mobile";// 帮手的手机号
	public static final String DATABASE_TABLE_HELPERS_GENDER = "helper_gender";// 帮手性别
	public static final String DATABASE_TABLE_HELPERS_ZIP = "helper_zip";// 帮手的邮编
	public static final String DATABASE_TABLE_HELPERS_FAX = "helper_fax";// 帮手的传真
	public static final String DATABASE_TABLE_HELPERS_EMAIL = "helper_email";// 电子邮箱
	public static final String DATABASE_TABLE_HELPERS_COMPANY = "helper_company";// 公司
	public static final String DATABASE_TABLE_HELPERS_TITLE = "helper_title";// 职务
	public static final String DATABASE_TABLE_HELPERS_COUNTRY = "helper_country";// 国家
	public static final String DATABASE_TABLE_HELPERS_CITY = "helper_city";// 城市
	public static final String DATABASE_TABLE_HELPERS_ADDRESS = "helper_address";// 地址
	public static final String DATABASE_TABLE_HELPERS_WEBPAGE = "helper_webpage";// 主页地址
	public static final String DATABASE_TABLE_HELPERS_PHOTO = "helper_photo";// 头像
	public static final String DATABASE_TABLE_HELPERS_SIGN = "helper_sign";// 个性签名
	public static final String DATABASE_TABLE_HELPERS_STATUS = "helper_status";// 关系状态״
	public static final String DATABASE_TABLE_HELPERS_VERIFICATION = "helper_verification";// 用于添加帮手时是否需要验证信息
	public static final String DATABASE_TABLE_HELPERS_HELPERCOUNT = "helper_count";// 用户帮手总数״
	public static final String DATABASE_TABLE_HELPERS_TARGETCOUNT = "helper_target_count";// 用户目标总数
	public static final String DATABASE_TABLE_HELPERS_VIEWCOUNT = "helper_view_count";// 展示次数
	public static final String DATABASE_TABLE_HELPERS_COINCOUNT = "helper_coin_count";// 罗盘币的数量
	public static final String DATABASE_TABLE_HELPERS_MEMO = "helper_memo";// 自荐的备注标签״
	public static final String DATABASE_TABLE_HELPERS_MSG = "helper_msg";// 邀请时信息״
	public static final String DATABASE_TABLE_HELPERS_BECOMEPUBLICTIME = "helper_become_public_time";// 升级为公众帮手的时间
	public static final String DATABASE_TABLE_HELPERS_UPDATETIME = "helper_update_time";// 更新时间
	public static final String DATABASE_TABLE_HELPERS_STARTTIME = "helper_start_time";// 公共帮手的最后更新时间

	// 获取Helpers表
	public static final String[] HELPERS_PROJECTION = new String[] { DATABASE_TABLE_HELPERS_KEY, DATABASE_TABLE_HELPERS_ID,
			DATABASE_TABLE_HELPERS_ACCOUNT, DATABASE_TABLE_HELPERS_ACCOUNTTYPE, DATABASE_TABLE_HELPERS_NAME,
			DATABASE_TABLE_HELPERS_MEMONAME, DATABASE_TABLE_HELPERS_READYGONAME, DATABASE_TABLE_HELPERS_NAMEPINYIN,
			DATABASE_TABLE_HELPERS_MEMONAMEPINYIN, DATABASE_TABLE_HELPERS_READYGONAMEPINYIN, DATABASE_TABLE_HELPERS_TEL,
			DATABASE_TABLE_HELPERS_MOBILE, DATABASE_TABLE_HELPERS_GENDER, DATABASE_TABLE_HELPERS_ZIP, DATABASE_TABLE_HELPERS_FAX,
			DATABASE_TABLE_HELPERS_EMAIL, DATABASE_TABLE_HELPERS_COMPANY, DATABASE_TABLE_HELPERS_TITLE, DATABASE_TABLE_HELPERS_COUNTRY,
			DATABASE_TABLE_HELPERS_CITY, DATABASE_TABLE_HELPERS_ADDRESS, DATABASE_TABLE_HELPERS_WEBPAGE, DATABASE_TABLE_HELPERS_PHOTO,
			DATABASE_TABLE_HELPERS_SIGN, DATABASE_TABLE_HELPERS_STATUS, DATABASE_TABLE_HELPERS_VERIFICATION,
			DATABASE_TABLE_HELPERS_HELPERCOUNT, DATABASE_TABLE_HELPERS_TARGETCOUNT, DATABASE_TABLE_HELPERS_VIEWCOUNT,
			DATABASE_TABLE_HELPERS_COINCOUNT, DATABASE_TABLE_HELPERS_MEMO, DATABASE_TABLE_HELPERS_MSG,
			DATABASE_TABLE_HELPERS_BECOMEPUBLICTIME, DATABASE_TABLE_HELPERS_UPDATETIME, DATABASE_TABLE_HELPERS_STARTTIME };
	// 创建Helpers表的语句
	public static final String DATABASE_CREATE_HELPERS = "create table Helpers (_id integer primary key autoincrement,"
			+ "helper_id integer unique," + "helper_account text," + "helper_account_type integer," + "helper_name text,"
			+ "helper_memo_name text," + "helper_readygo_name text," + "helper_name_pinyin text," + "helper_memo_name_pinyin text,"
			+ "helper_readygo_name_pinyin text," + "helper_tel text," + "helper_mobile text," + "helper_gender integer,"
			+ "helper_zip text," + "helper_fax text," + "helper_email text," + "helper_company text," + "helper_title text,"
			+ "helper_country text," + "helper_city text," + "helper_address text," + "helper_webpage text," + "helper_photo text,"
			+ "helper_sign text," + "helper_status integer," + "helper_verification integer," + "helper_count integer,"
			+ "helper_target_count integer," + "helper_view_count integer," + "helper_coin_count integer," + "helper_memo text,"
			+ "helper_msg text," + "helper_become_public_time text," + "helper_update_time text," + "helper_start_time text);";
	/**
	 * group表
	 */
	public static final String DATABASE_TABLE_GROUPS = "Groups";// 表名
	public static final String DATABASE_TABLE_GROUPS_KEY = "_id";// 主键
	public static final String DATABASE_TABLE_GROUPS_ID = "group_id";// 组id
	public static final String DATABASE_TABLE_GROUPS_NAME = "group_name";// 组名
	public static final String DATABASE_TABLE_GROUPS_MEMBERID = "group_member_id";// 组成员ID
	public static final String DATABASE_TABLE_GROUPS_DESCRIBE = "group_describe";// 组描述
	public static final String DATABASE_TABLE_GROUPS_STATUS = "group_status";// 组状态
	public static final String DATABASE_TABLE_GROUPS_CREATETIME = "group_create_time";// 组创建时间
	public static final String DATABASE_TABLE_GROUPS_LASTUPDATETIME = "group_last_update_time";// 组更新时间
	// 获取Groups表
	public static final String[] GROUPS_PROJECTION = new String[] { DATABASE_TABLE_GROUPS_KEY, DATABASE_TABLE_GROUPS_ID,
			DATABASE_TABLE_GROUPS_NAME, DATABASE_TABLE_GROUPS_MEMBERID, DATABASE_TABLE_GROUPS_DESCRIBE, DATABASE_TABLE_GROUPS_STATUS,
			DATABASE_TABLE_GROUPS_CREATETIME, DATABASE_TABLE_GROUPS_LASTUPDATETIME };
	// 创建Groups表的语句
	public static final String DATABASE_CREATE_GROUPS = "create table Groups (_id integer primary key autoincrement,"
			+ "group_id integer default -1," + "group_name text," + "group_member_id integer default -1," + "group_describe text,"
			+ "group_status integer default -1," + "group_create_time text," + "group_last_update_time text);";
	/**
	 * UpdateLog表
	 */
	public final String DATABASE_TABLE_UPDATELOGS_NAME = "UpdateLog";
	public final String DATABASE_TABLE_UPDATELOGS_KEY = "_id";
	public final String DATABASE_TABLE_UPDATELOGS_LOGNAME = "logname";// 日志的名字
	public final String DATABASE_TABLE_UPDATELOGS_TIME = "time";// 创建日志的时间
	public final String DATABASE_TABLE_UPDATELOGS_TYPE = "type";// 日志的类型
	public final String DATABASE_TABLE_UPDATELOGS_IFSUCCESS = "ifsuccess";// 该条日志记录的内容是否是成功内容
	public final String DATABASE_TABLE_UPDATELOGS_MARK = "mark";// 备注
	public final String DATABASE_TABLE_UPDATELOGS_TIME_START = "time_start";// 日志开始的时间
	public final String DATABASE_TABLE_UPDATELOGS_REPORTWORD = "reportword";// 报告给用户的文字
	public final String DATABASE_TABLE_UPDATELOGS_REPORT = "report";// 报告，系统字符
	/**
	 * CostTag表
	 */
	public static final String DATABASE_TABLE_COSTTAG = "CostTag";// 表名
	public static final String DATABASE_TABLE_COSTTAG_KEY = "_id";// 主键
	public static final String DATABASE_TABLE_COSTTAG_ID = "tag_id";// 组id
	public static final String DATABASE_TABLE_COSTTAG_NAME = "tag_name";// 组名
	public static final String DATABASE_TABLE_COSTTAG_TARGETID = "target_id";// 组成员ID
	// 获取CostTag表
	public static final String[] COSTTAG_PROJECTION = new String[] { DATABASE_TABLE_COSTTAG_KEY, DATABASE_TABLE_COSTTAG_ID,
		DATABASE_TABLE_COSTTAG_NAME, DATABASE_TABLE_COSTTAG_TARGETID};
	// 创建CostTag表的语句
	public static final String DATABASE_CREATE_COSTTAG = "create table CostTag (_id integer primary key autoincrement,"
			+ "tag_id integer unique default 0," + "tag_name text," + "target_id integer default 0);";
}
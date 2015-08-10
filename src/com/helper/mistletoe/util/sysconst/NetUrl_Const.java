package com.helper.mistletoe.util.sysconst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public interface NetUrl_Const extends NetUrl_Note_Const, NetUrl_TargetMember_Const, NetUrl_Target_Const, NetUrl_User_Const,
		NetUrl_Task_Const {
	/**
	 * 上传通讯录http://localhost:8080/index.php?r=user-contact/upload
	 */
	public final static String NET_UPLOAD_USER_CONTACT = NET_BASE_URL + "user-contact/upload";
	/**
	 * 获取帮手http://localhost:8080/index.php?r=helper/find
	 */
	public final static String NET_FIND_HELPER = NET_BASE_URL + "helper/find";
	/**
	 * 修改和帮手的关系http://localhost:8080/index.php?r=helper/update-relationship
	 */
	public final static String NET_UPDATE_RELATIONSHIP = NET_BASE_URL + "helper/update-relationship";
	/**
	 * 添加帮手，通过account(TEL或email) http://localhost:8080/index.php?r=helper/add
	 */
	public final static String NET_ADD_HELPER = NET_BASE_URL + "helper/add";
	/**
	 * 获得推荐帮手详情根据ID(包括自己):
	 * http://localhost:8080/index.php?r=helper/find-public-helper-desc
	 */
	public final static String NET_FIND_PUBLIC_HELPER_DESC = NET_BASE_URL + "helper/find-public-helper-desc";
	/**
	 * 获得推荐帮手详情根据关键字(包括自己):
	 * http://localhost:8080/index.php?r=helper/find-public-helpers-by-keyword
	 */
	public final static String NET_FIND_PUBLIC_HELPER_BY_KEYWORD = NET_BASE_URL + "helper/find-public-helpers-by-keyword";
	/**
	 * 添加帮手(网络搜索帮手) http://localhost:8080/index.php?r=helper/add-by-id
	 */
	public final static String NET_ADD_HELPER_BY_ID = NET_BASE_URL + "helper/add-by-id";
	/**
	 * 获得公众帮手 http://localhost:8080/index.php?r=helper/find-public-helpers
	 */
	public final static String NET_FIND_PUBLIC_HELPERS = NET_BASE_URL + "helper/find-public-helpers";
	/**
	 * 自荐为公众帮手或取消 http://localhost:8080/index.php?r=helper/update-public-helper
	 */
	public final static String NET_UPDATE_PUBLIC_HELPER = NET_BASE_URL + "helper/update-public-helper";
	/**
	 * 创建组 http://localhost:8080/index.php?r=group/create
	 */
	public final static String NET_GREATE_GROUP = NET_BASE_URL + "group/create";
	/**
	 * 更新组 http://localhost:8080/index.php?r=group/update
	 */
	public final static String NET_UPDATE_GROUP = NET_BASE_URL + "group/update";
	/**
	 * 删除组http://localhost:8080/index.php?r=group/delete
	 */
	public final static String NET_DELETE_GROUP = NET_BASE_URL + "group/delete";
	/**
	 * 获得组信息http://localhost:8080/index.php?r=group/find
	 */
	public final static String NET_FIND_GROUP = NET_BASE_URL + "group/find";
	/**
	 * 获得某一组信息http://localhost:8080/index.php?r=group/find-members-by-grp
	 */
	public final static String NET_FIND_GROUP_BY_ID = NET_BASE_URL + "group/find-members-by-grp";
	/**
	 * 添加和删除组成员 http://localhost:8080/index.php?r=group/update-grp-members
	 */
	public final static String NET_UPDATE_GRP_MEMBERS = NET_BASE_URL + "group/update-grp-members";

	/**
	 * 用户反馈 http://localhost:8080/index.php?r=user-feedback/add
	 */
	public final static String NET_USER_ADD_FEEDBACK = NET_BASE_URL + "user-feedback/add";
	/**
	 * 查找用户名下的所有设备 http://localhost:8080/index.php?r=user-device/find
	 */
	public final static String NET_USER_FIND_DEVICE = NET_BASE_URL + "user-device/find";
	/**
	 * 强制指定设备退出 http://localhost:8080/index.php?r=user-device/kick
	 */
	public final static String NET_USER_KICK_DEVICE = NET_BASE_URL + "user-device/kick";
	/**
	 * 检查最新版本 http://localhost:8080/index.php?r=version/get-last-release-version
	 */
	public final static String NET_GET_LAST_VERSION = NET_BASE_URL + "version/get-last-release-version";
	/**
	 * 锤子http://localhost:8080/index.php?r=user-action/duang
	 */
	public final static String NET_ACTION_DUANG = NET_BASE_URL + "user-action/duang";
	/**
	 * 锤子多人http://localhost:8080/index.php?r=user-action/multiple-duang
	 */
	public final static String NET_ACTION_DUANG_ARRAY = NET_BASE_URL + "user-action/multiple-duang";
	/**
	 * 上传文件
	 */
	public final static String NET_UPLOADFILE_FILE = NET_BASE_URLFILE + "file/upload-file";
	/**
	 * 下载文件
	 */
	public final static String NET_DOWNLOAD_FILE = NET_BASE_URLFILE + "file/download-file";
	/**
	 * 获取目标进度更新接受者
	 */
	public final static String NET_GETBYNOTEID_TARGETNOTERECEIVER = NET_BASE_URL + "target-note-receiver/get-by-note-id";
	/**
	 * 获取目标模板
	 */
	public final static String NET_FIND_TARGET_TEMPLATE = NET_BASE_URL + "target/get-template";
	/**
	 *生成Excel
	 */
	public final static String NET_GENERATE_EXCEL = NET_BASE_URL + "target/summary";
	/**
	 *目标分享
	 */
	public final static String NET_SHARE_TARGET = NET_BASE_URL + "target/share";
	/**
	 *添加费用标签
	 */
	public final static String NET_ADD_TAG = NET_BASE_URL + "note/add-tag";
	// 官方网址的地址transmit
	public final String NET_TRANSMIT_INFO = "http://www.4helper.com/m/";
	// 官方网址的地址introduce
	public final String NET_INTRODUCE = "http://www.4helper.com/";
	// 官方网址的地址 instructions
	public final String NET_INSTRUCTIONS = "http://www.4helper.com/m/services.html";
	// 官方网址的地址 about
	public final String NET_ABOUT = "http://www.readygooo.com/readygooo/";
	// 隐私协议的地址clause
	public final String NET_CLAUSE = "http://www.4helper.com/m/contact.html";
}
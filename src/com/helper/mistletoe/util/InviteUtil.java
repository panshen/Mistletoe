package com.helper.mistletoe.util;

import android.content.Intent;
import android.net.Uri;

public class InviteUtil {
	public void InviteBySMS(String phoneNumber, String message) {
		/**
		 * 调起系统发短信功能
		 * 
		 * @param phoneNumber
		 *            发送短信的接收号码
		 * @param message
		 *            短信内容
		 */
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
		intent.putExtra("sms_body", message);
		startActivity(intent);
	}

	public void InviteByEmail() {
		Intent data = new Intent(Intent.ACTION_SENDTO);
		// data.setData(Uri.parse("355955396@qq.com"));
		// data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
		// data.putExtra(Intent.EXTRA_TEXT, "这是内容");
		// startActivity(data);
		// String[] email = {"355955396@qq.com"}; // 需要注意，email必须以数组形式传入
		// Intent intent = new Intent(Intent.ACTION_SEND);
		// intent.setType("message/rfc822"); // 设置邮件格式
		// intent.putExtra(Intent.EXTRA_EMAIL, email); // 接收人
		// intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
		// intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分"); // 主题
		// intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分"); // 正文
		// startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
		// //kh
		// 必须明确使用mailto前缀来修饰邮件地址,如果使用
		// intent.putExtra(Intent.EXTRA_EMAIL, email)，结果将匹配不到任何应用
		Uri uri = Uri.parse("mailto:355955396@qq.com");
		String[] email = { "355955396@qq.com" };
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
		intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分"); // 主题
		intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分"); // 正文
		startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
	}

	private void startActivity(Intent createChooser) {
		// TODO Auto-generated method stub

	}
}

package com.helper.mistletoe.m.work.ui;

import android.content.Context;

import com.google.gson.Gson;
import com.helper.mistletoe.m.net.request.Login_User_NetRequest;
import com.helper.mistletoe.m.pojo.Login_User_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.sp.User_sp;
import com.helper.mistletoe.m.work.base.BaseWork_Mode;
import com.helper.mistletoe.m.work.base.inter.WorkCallBack_Mode;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * <p>
 * Created by 张久瑞 on 2015年3月13日.
 * </p>
 * 
 * @author 张久瑞
 * @version 1.0
 */
public class Login_Login_Mode extends BaseWork_Mode {
	private String account_type;// 输入：account_type
	private String account;// 输入：account
	private int deviceId;// 输入：设备Id
	private String verification;// 输入：验证码
	private Login_User_Bean response;// 结果：服务器返回的结果

	public Login_Login_Mode(WorkCallBack_Mode workCallBack, Context context, String account_type, String account, String verification, int deviceId) {
		super(workCallBack, context);
		try {
			this.account_type = account_type;
			this.account = account;
			this.verification = verification;
			this.deviceId = deviceId;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String result = "";
		try {
			Login_User_NetRequest request = new Login_User_NetRequest(context);
			response = request.doLogin(account_type, account, verification, deviceId);
			// 如登录成功，保存User
			if (!response.getAccess_token().equals("")) {
				Gson gson = new Gson();
				User_sp usp = new User_sp(context);
				User_Bean user = usp.read();
				user = gson.fromJson(response.getLoc_NetRes().getLoc_data(), User_Bean.class);
				user.setLoc_Account(account);
				user.setVerification(verification);
				user.setLoc_AccountType(Integer.valueOf(account_type));
				usp.write(user);
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public Login_User_Bean getResponse() {
		if (response == null) {
			response = new Login_User_Bean();
		}
		return response;
	}

}
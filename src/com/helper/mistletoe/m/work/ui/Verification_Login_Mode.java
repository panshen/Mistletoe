package com.helper.mistletoe.m.work.ui;

import android.content.Context;

import com.helper.mistletoe.m.net.request.Register_User_NetRequest;
import com.helper.mistletoe.m.pojo.Register_User_Bean;
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
public class Verification_Login_Mode extends BaseWork_Mode {
	private String account_type;// 输入：account_type
	private String account;// 输入：account
	private Register_User_Bean response;// 结果：服务器返回的结果

	public Verification_Login_Mode(WorkCallBack_Mode workCallBack, Context context, String account_type, String account) {
		super(workCallBack, context);
		try {
			this.account_type = account_type;
			this.account = account;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String result = "";
		try {
			Register_User_NetRequest request = new Register_User_NetRequest(context);
			response = request.doRegister(account_type, account);
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public Register_User_Bean getResponse() {
		if (response == null) {
			response = new Register_User_Bean();
		}
		return response;
	}

}
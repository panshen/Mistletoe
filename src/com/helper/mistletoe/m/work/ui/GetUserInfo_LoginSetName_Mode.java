package com.helper.mistletoe.m.work.ui;

import android.content.Context;

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
public class GetUserInfo_LoginSetName_Mode extends BaseWork_Mode {
	private User_Bean response;// 结果：服务器返回的结果

	public GetUserInfo_LoginSetName_Mode(WorkCallBack_Mode workCallBack, Context context) {
		super(workCallBack, context);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String result = "";
		try {
			User_sp usp = new User_sp(context);
			response = usp.read();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public User_Bean getResponse() {
		if (response == null) {
			response = new User_Bean();
		}
		return response;
	}

}
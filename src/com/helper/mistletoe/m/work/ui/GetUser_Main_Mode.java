package com.helper.mistletoe.m.work.ui;

import android.content.Context;

import com.helper.mistletoe.m.pojo.AppNote_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.sp.AppNote_sp;
import com.helper.mistletoe.m.sp.User_sp;
import com.helper.mistletoe.m.work.base.BaseWork_Mode;
import com.helper.mistletoe.m.work.base.inter.WorkCallBack_Mode;
import com.helper.mistletoe.util.CustomInfo;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * <p>
 * Created by 张久瑞 on 2015年3月13日.
 * </p>
 * 
 * @author 张久瑞
 * @version 1.0
 */
public class GetUser_Main_Mode extends BaseWork_Mode {
	private boolean response_isLoggined;// 结果：用户是否有效
	private boolean response_isFirstUse;// 结果：用户是否更新后第一次打开App

	public GetUser_Main_Mode(WorkCallBack_Mode workCallBack, Context context) {
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
			// 获取User
			User_sp usp = new User_sp(context);
			User_Bean user = usp.read();
			// 判断User是否有效
			response_isLoggined = user.isEffective();

			// 获取App笔记
			AppNote_sp ssp = new AppNote_sp(context);
			AppNote_Bean setting = ssp.read();
			// 判断是否更新后第一次打开App
			if (Integer.valueOf((CustomInfo.getApp_versionCode(context))) > (Integer.valueOf(setting.getLastAppVersion()))) {
				response_isFirstUse = true;
			} else {
				response_isFirstUse = false;
			}
			// 把新的版本号保存到App笔记中
			setting.setLastAppVersion(CustomInfo.getApp_versionCode(context));
			ssp.write(setting);
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public boolean isResponse_isLoggined() {
		return response_isLoggined;
	}

	public boolean isResponse_isFirstUse() {
		return response_isFirstUse;
	}

}
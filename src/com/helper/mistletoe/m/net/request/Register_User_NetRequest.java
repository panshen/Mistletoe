package com.helper.mistletoe.m.net.request;

import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Register_User_Bean;
import com.helper.mistletoe.util.CustomInfo;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class Register_User_NetRequest extends Template_NetRequest {
	public Register_User_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_USER_REGISTER);
	}

	public Register_User_Bean doRegister(String account_type, String account) throws Exception {
		Register_User_Bean result = null;
		try {
			// 请求参数
			// 获取data
			String data = fromateData(account_type, account);
			// 连接
			openConnection(data);
			// 返回结果
			Gson tGson = new Gson();
			result = new Register_User_Bean();
			if (getResultData().getResult().equals("0")) {
				String t_resc = getResultData().getLoc_data();
				// 访问成功
				if (!t_resc.equals("")) {
					result = tGson.fromJson(t_resc, Register_User_Bean.class);
				}
				result.setLoc_NetRes(getResultData());
			} else {
				String t_resc = getResultData().getResult_msg();
				// 访问失败
				result.setLoc_NetRes(getResultData());
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String fromateData(String account_type, String account) throws Exception {
		String result = null;
		try {
			JSONObject jData = new JSONObject();

			jData.put("account_type", account_type);
			jData.put("account", account);
			jData.put("platform", CustomInfo.getPlatform());
			jData.put("os", CustomInfo.getOs());
			jData.put("hardware", CustomInfo.getHardware());
			jData.put("app_version", CustomInfo.getApp_version(context));
			jData.put("device_token", CustomInfo.getDevice_token(context));

			result = jData.toString();
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
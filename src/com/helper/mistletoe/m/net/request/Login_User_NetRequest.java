package com.helper.mistletoe.m.net.request;

import java.security.MessageDigest;

import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.Login_User_Bean;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.CustomInfo;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class Login_User_NetRequest extends Template_NetRequest {
	public Login_User_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_USER_LOGIN);
	}

	public Login_User_Bean doLogin(String account_type, String account, String verification, int deviceId) throws Exception {
		Login_User_Bean result = null;
		try {
			// 请求参数
			String verification_code = "";
			// 计算验证码
			verification += CustomInfo.getMd5_Token();
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(verification.getBytes());
			byte[] dddddd = messageDigest.digest();
			StringBuffer strBuffer = new StringBuffer(dddddd.length * 2);
			for (int i = 0; i < dddddd.length; i++) {
				strBuffer.append(Character.forDigit((dddddd[i] & 240) >> 4, 16));
				strBuffer.append(Character.forDigit(dddddd[i] & 15, 16));
			}
			verification_code = new String(strBuffer.toString());
			// 获取data
			String data = fromateData(account_type, account, verification_code, deviceId);
			// 连接
			openConnection(data);
			// 返回结果
			Gson gson = new Gson();
			result = gson.fromJson(getResultData().getLoc_data(), Login_User_Bean.class);
			if (result == null) {
				result = new Login_User_Bean();
			}
			result.setLoc_NetRes(getResultData());
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String fromateData(String account_type, String account, String verification_code, int deviceId) throws Exception {
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
			jData.put("verification_code", verification_code);
			jData.put("device_id", deviceId);
			jData.put("push_token", CustomInfo.getPushToken(context));

			result = jData.toString();
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
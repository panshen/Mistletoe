package com.helper.mistletoe.m.net.request.base;

import java.io.File;

import org.json.JSONObject;

import android.content.Context;

import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.NetResult_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.LogPrint;

/**
 * Created by stemp1234 on 15/3/4.
 */
public abstract class Template_NetRequest {
	protected Context context;
	private NetRequest_Bean requestData;
	private NetResult_Bean resultData;

	protected Template_NetRequest(Context context, String requestType, String url) {
		super();
		try {
			init(context, requestType, url, null);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected Template_NetRequest(Context context, String requestType, String url, String file_type) {
		super();
		try {
			init(context, requestType, url, file_type);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void init(Context context, String requestType, String url, String file_type) {
		try {
			this.context = context;
			requestData = new NetRequest_Bean();
			requestData.setSys_RequestType(requestType);
			requestData.setSys_RequestUrl(url);
			requestData.setFile_type(file_type);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void openConnection(String data) throws Exception {
		try {
			//签名
			requestData.acquisitionLinkKey(context);
			requestData.setData(data);
			resultData = AirLock_NetRequest.openConnection(requestData);
			printClientLog();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void openConnection(String data, File file_content) throws Exception {
		try {
			requestData.setFile_content(file_content);
			openConnection(data);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void printClientLog() throws Exception {
		try {
			JSONObject t_RequestSafe = new JSONObject();
			t_RequestSafe.put("signature", getRequestData().getSignature());
			t_RequestSafe.put("timestamp", getRequestData().getTimestamp());
			t_RequestSafe.put("user_id", getRequestData().getUser_id());
			t_RequestSafe.put("access_token", getRequestData().getAccess_token());
			JSONObject t_ResultDefault = new JSONObject();
			t_ResultDefault.put("result", getResultData().getResult());
			t_ResultDefault.put("result_msg", getResultData().getResult_msg());

			String clientLog = "";
			clientLog += "请求参数_api\n" + getRequestData().getSys_RequestUrl();
			clientLog += "\n请求参数_安全验证\n" + t_RequestSafe.toString();
			clientLog += "\n请求参数_data\n" + getRequestData().getData();
			clientLog += "\n结果参数_通用返回\n" + t_ResultDefault.toString();
			clientLog += "\n结果参数_data\n" + getResultData().getLoc_data();
			clientLog += "\n------------------------------------------------";
			LogPrint.printString_V("NetRequest&Result", clientLog);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected NetResult_Bean getResultData() {
		if (resultData == null) {
			resultData = new NetResult_Bean();
		}
		return resultData;
	}

	protected NetRequest_Bean getRequestData() {
		if (requestData == null) {
			requestData = new NetRequest_Bean();
		}
		return requestData;
	}
}
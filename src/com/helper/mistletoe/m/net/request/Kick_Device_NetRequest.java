package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Kick_Device_NetRequest extends Template_NetRequest {
	public Kick_Device_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_USER_KICK_DEVICE);
	}

	public Boolean doKickDecive(Integer Id) throws Exception {
		/**
		 * {"device_id":1}
		 */
		Boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data_kick_decive = MyJsonWriter.getJsonDataForKickDecive(Id);
			// 连接
			openConnection(data_kick_decive);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				result = true;
			} else {
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
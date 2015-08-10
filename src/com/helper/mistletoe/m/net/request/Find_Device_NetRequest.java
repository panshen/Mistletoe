package com.helper.mistletoe.m.net.request;

import java.util.ArrayList;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonReader;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Result_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Find_Device_NetRequest extends Template_NetRequest {
	public Find_Device_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_USER_FIND_DEVICE);
	}

	public ArrayList<Result_Bean> doFindDevice() throws Exception {
		/**
		 * {}
		 */
		ArrayList<Result_Bean> result = null;
		try {
			// 请求参数
			// 获取data
			String data_find_device = "";
			// 连接
			openConnection(data_find_device);
			// 返回结果
			String res = "";
			if (getResultData().getResult().equals("0")) {
				res = getResultData().getLoc_data();
			} else {
				res = getResultData().getResult_msg();
			}
			result = MyJsonReader.getJsonDataForFindDevice(res);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
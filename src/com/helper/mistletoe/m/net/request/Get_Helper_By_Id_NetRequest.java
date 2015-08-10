package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonReader;
import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Get_Helper_By_Id_NetRequest extends Template_NetRequest {
	public Get_Helper_By_Id_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_USER_GET);
	}

	public Helpers_Sub_Bean doGetHelperById(Integer Id) throws Exception {
		/**
		 * {"user_id":1}
		 */
		Helpers_Sub_Bean helper = null;
		try {
			// 请求参数
			// 获取data
			String data_get_user = MyJsonWriter.getJsonDataForGetHelperById(Id);
			// 连接
			openConnection(data_get_user);
			// 返回结果
			String res = "";
			if (getResultData().getResult().equals("0")) {
				res = getResultData().getLoc_data();
			}
			if (!res.isEmpty()) {
				helper = MyJsonReader.getJsonDataForGetHelperById(res);
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return helper;
	}
}
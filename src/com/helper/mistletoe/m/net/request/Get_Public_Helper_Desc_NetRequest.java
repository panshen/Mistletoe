package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonReader;
import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Public_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Get_Public_Helper_Desc_NetRequest extends Template_NetRequest {
	public Get_Public_Helper_Desc_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_FIND_PUBLIC_HELPER_DESC);
	}

	public Public_Bean doGetPublicDesc(Integer Id) throws Exception {
		/**
		 * {"user_id":1}
		 */
		Public_Bean public_service = new Public_Bean();
		try {
			// 请求参数
			// 获取data
			String data_find_public_helper_desc = MyJsonWriter.getJsonDataForFindPublicHelperDesc(Id);
			// 连接
			openConnection(data_find_public_helper_desc);
			// 返回结果
			String res = "";
			if (getResultData().getResult().equals("0")) {
				res = getResultData().getLoc_data();
			}
			if (res.length() > 0) {
				public_service = MyJsonReader.getJsonDataForFindPublicHelperDesc(res);
			}

		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return public_service;
	}
}
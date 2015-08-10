package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonReader;
import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Get_Helper_By_Account_NetRequest extends Template_NetRequest {
	public Get_Helper_By_Account_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_USER_FINDBYACCOUNT);
	}

	public Helpers_Sub_Bean doFindHelperByAccount(String account) throws Exception {
		/**
		 * {"account": "18611697407"}
		 */
		Helpers_Sub_Bean helper = null;
		try {
			// 请求参数
			// 获取data
			String data_find_helper_byAccount = MyJsonWriter.getJsonDataForFindHelperByAccount(account);
			// 连接
			openConnection(data_find_helper_byAccount);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				String res = getResultData().getLoc_data();
				if (res.length() > 0) {
					helper = MyJsonReader.getJsonDataForFindHelperByAccount(res);
				}
			}

		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return helper;
	}
}
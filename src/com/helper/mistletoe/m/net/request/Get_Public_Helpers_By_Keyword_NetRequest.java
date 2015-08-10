package com.helper.mistletoe.m.net.request;

import java.util.ArrayList;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonReader;
import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Public_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Get_Public_Helpers_By_Keyword_NetRequest extends Template_NetRequest {
	public Get_Public_Helpers_By_Keyword_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_FIND_PUBLIC_HELPER_BY_KEYWORD);
	}

	public ArrayList<Public_Bean> doFindPublicHelperByKeyword(String keyword) throws Exception {
		/**
		 * 
		 {"key_word":1}
		 */
		ArrayList<Public_Bean> publicHelperList = new ArrayList<Public_Bean>();
		try {
			// 请求参数
			// 获取data
			String data_find_public_helper_by_keyword = MyJsonWriter.getJsonDataForFindPublicHelperByKeyword(keyword);
			// 连接
			openConnection(data_find_public_helper_by_keyword);
			// 返回结果
			String res = "";
			if (getResultData().getResult().equals("0")) {
				res = getResultData().getLoc_data();
			}
			publicHelperList = MyJsonReader.getJsonDataForFindPublicHelperByKeyword(res);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return publicHelperList;
	}
}
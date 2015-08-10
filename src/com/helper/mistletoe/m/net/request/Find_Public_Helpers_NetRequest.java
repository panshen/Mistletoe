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

public class Find_Public_Helpers_NetRequest extends Template_NetRequest {
	public Find_Public_Helpers_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_FIND_PUBLIC_HELPERS);
	}

	public ArrayList<Public_Bean> doFindPublicHelper(int start_id) throws Exception {
		/**
		 * 
		 {"start_id":0,"count": 2}
		 */
		ArrayList<Public_Bean> public_List = new ArrayList<Public_Bean>();
		try {
			// 请求参数
			// 获取data
			String data_find_publicHelper = MyJsonWriter.getJsonDataForFindPublicHelper(start_id);
			// 连接
			openConnection(data_find_publicHelper);
			// 返回结果
			String res = "";
			if (getResultData().getResult().equals("0")) {
				res = getResultData().getLoc_data();
			}
			if (res.length() > 0) {
				public_List = MyJsonReader.getJsonDataForFindPublicHelper(res);
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return public_List;
	}
}
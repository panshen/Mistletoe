package com.helper.mistletoe.m.net.request;

import java.util.ArrayList;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonReader;
import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_includeInfo_NetRequest;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Find_Helper_NetRequest extends Template_includeInfo_NetRequest {
	public Find_Helper_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_FIND_HELPER);
	}

	public ArrayList<Helpers_Sub_Bean> doFindHelper(Long last_updated_time) throws Exception {
		ArrayList<Helpers_Sub_Bean> HelperList = new ArrayList<Helpers_Sub_Bean>();
		try {
			// 请求参数
			// 获取data
			String data_find_helper = MyJsonWriter.getJsonDataForFindHelper(last_updated_time);
			// 连接
			openConnection(data_find_helper);
			// 返回结果
			String res = null;
			if (getResultData().getResult().equals("0")) {
				res = getResultData().getLoc_data();
				if (res.length() > 0) {
					HelperList = MyJsonReader.getJsonDataForFindHelper(res);
				}
			} else {
				res = getResultData().getResult_msg();
			}
		} catch (Exception e) {
			HelperList = null;
			ExceptionHandle.unInterestException(e);
		}
		return HelperList;
	}
}
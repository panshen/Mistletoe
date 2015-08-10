package com.helper.mistletoe.m.net.request;

import android.content.Context;
import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_includeInfo_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Add_Helper_By_Id_NetRequest extends Template_includeInfo_NetRequest {
	public Add_Helper_By_Id_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_ADD_HELPER_BY_ID);
	}

	public Boolean doAddHelperById(String msg, Integer integer) throws Exception {
		/**
		 * 
		 { "msg": "我是刘玉海", "dst_id": "3" }
		 */
		Boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data_addHelper_byId = MyJsonWriter.getJsonDataForAddHelperById(msg, integer);
			// 连接
			openConnection(data_addHelper_byId);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				result = true;
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
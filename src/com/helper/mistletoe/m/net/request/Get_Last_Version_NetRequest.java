package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonReader;
import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_includeInfo_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Result_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Get_Last_Version_NetRequest extends Template_includeInfo_NetRequest {
	public Get_Last_Version_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_GET_LAST_VERSION);
	}

	public Result_Bean doGetLastVersion() throws Exception {
		/**
		 * {"platform":2}
		 */
		Result_Bean result_Bean = new Result_Bean();
		try {
			// 请求参数
			// 获取data
			String data_get_last_version = MyJsonWriter.getJsonDataForGetLastVersion(platform);
			// 连接
			openConnection(data_get_last_version);
			// 返回结果
			String res = "";
			if (getResultData().getResult().equals("0")) {
				res = getResultData().getLoc_data();
			} else {
				res = getResultData().getResult_msg();
			}
			 result_Bean = MyJsonReader.getJsonDataForGetLastVersion(res);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return result_Bean;
	}
}
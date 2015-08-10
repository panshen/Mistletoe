package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class Update_Public_Helper_NetRequest extends Template_NetRequest {
	public Update_Public_Helper_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_UPDATE_PUBLIC_HELPER);
	}

	public Boolean doUpdatePublicHelper(String memo, String status) throws Exception {
		/**
		 * {"memo":"我是您的帮手","status": 1} “memo”：“您的好帮手”,//自荐时的备注，可以为空 “status”:
		 * 1,//自荐为公众帮手 "status": 2,//取消公共帮手
		 */
		Boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data_update_public_helper = MyJsonWriter.getJsonDataForUpdatePublicHelper(memo, status);
			// 连接
			openConnection(data_update_public_helper);
			// 返回结果
			// String res = "";
			if (getResultData().getResult().equals("0")) {
				result = true;
				// res = getResultData().getLoc_data();
			} else {
				// res = getResultData().getResult_msg();
			}
			// result = MyJsonReader.getJsonDataForUpdatePublicHelper(res);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
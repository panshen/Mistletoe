package com.helper.mistletoe.m.net.request;


import android.content.Context;
import android.util.Log;

import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Find_Target_Template_NetRequest extends Template_NetRequest {
	public Find_Target_Template_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_FIND_TARGET_TEMPLATE);
	}

	public String doFindTargetTemplate() throws Exception {
		/**
		 * {}
		 */
		String result = null;
		try {
			// 请求参数
			// 获取data
			String data_find_target_template = "";
			// 连接
			openConnection(data_find_target_template);
			// 返回结果
			String res = "";
			if (getResultData().getResult().equals("0")) {
				res = getResultData().getLoc_data();
			} else {
				res = getResultData().getResult_msg();
			}
			result = res;
			Log.v("TemplateS", "wangluoTemplateSres" + res.toString());
			Log.v("TemplateS", "wangluoTemplateS" + result.toString());
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
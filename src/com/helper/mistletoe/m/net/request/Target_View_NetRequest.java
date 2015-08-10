package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Target_View_NetRequest extends Template_NetRequest {
	public Target_View_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_TARGET_VIEW);
	}

	public Boolean doTargetView(Integer target_id) throws Exception {
		/**
		 * {"	target_id：	112//目标id"}
		 */
		Boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data_target_view = MyJsonWriter.getJsonDataForTagetView(target_id);
			// 连接
			openConnection(data_target_view);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				result = true;
			} else {
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
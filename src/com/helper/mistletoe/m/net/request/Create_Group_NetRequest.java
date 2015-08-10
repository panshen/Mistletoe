package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_includeInfo_NetRequest;
import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Create_Group_NetRequest extends Template_includeInfo_NetRequest {
	public Create_Group_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_GREATE_GROUP);
	}

	public Boolean doCreateGroup(Group_Bean group) throws Exception {
		/**
		 * { "name": "我是刘玉海", "memo":"公司","members": [3,4], }
		 */
		Boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data_create_group = MyJsonWriter.getJsonDataForCreateGroup(group);
			// 连接
			openConnection(data_create_group);
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
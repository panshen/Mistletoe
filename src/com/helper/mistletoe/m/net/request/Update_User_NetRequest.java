package com.helper.mistletoe.m.net.request;

import android.content.Context;
import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Update_User_NetRequest extends Template_NetRequest {
	public Update_User_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST,
				MainConst.NET_USER_UPDATE);
	}

	public Boolean doUpdate(User_Bean user) throws Exception {
		Boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data = MyJsonWriter.getJsonDataForUpdatePublicHelper(user);
			// 连接
			openConnection(data);
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

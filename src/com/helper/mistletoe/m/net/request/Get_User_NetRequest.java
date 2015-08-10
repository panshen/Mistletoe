package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonReader;
import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Get_User_NetRequest extends Template_NetRequest {
	public Get_User_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_USER_GET);
	}

	public Boolean doGetUser() throws Exception {
		/**
		 * {"user_id":1}
		 */
		Boolean result = false;
		User_Bean user = new User_Bean();
		try {
			// 请求参数
			user.readData(context);
			// 获取data
			String data_get_user = MyJsonWriter.getJsonDataForGetUser("" + user.getUser_id());
			// 连接
			openConnection(data_get_user);
			// 返回结果
			String res = "";
			if (getResultData().getResult().equals("0")) {
				result = true;
				res = getResultData().getLoc_data();
			} else {
				res = getResultData().getResult_msg();
			}
			user = MyJsonReader.getJsonDataForGetUser(res, user);
			user.writeData(context);
		} catch (Exception e) {
			result = false;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
package com.helper.mistletoe.m.net.request;

import java.util.ArrayList;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class UploadContact_User_NetRequest extends Template_NetRequest {
	public UploadContact_User_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_UPLOAD_USER_CONTACT);
	}

	public Boolean doUploadContact(ArrayList<Helpers_Sub_Bean> helperList) throws Exception {
		/**
		 * 上传通信录后不在将返回的数据添加到数据库 { "contacts": [ { "type": 0, "user_id": 3,
		 * "info": "18500540178", "name": "mjy" } ] }
		 */
		Boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data_upload_contact = MyJsonWriter.getJsonDataForUploadContact(helperList);
			// 连接
			openConnection(data_upload_contact);
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
package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Delete_Group_NetRequest extends Template_NetRequest {
	public Delete_Group_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_DELETE_GROUP);
	}

	public Boolean doDeleteGroup(Integer group_id) throws Exception {
		/**
		 * {"grp_id":"66"}
		 */
		Boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data_delete_group = MyJsonWriter.getJsonDataForDeleteGroup(group_id);
			// 连接
			openConnection(data_delete_group);
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
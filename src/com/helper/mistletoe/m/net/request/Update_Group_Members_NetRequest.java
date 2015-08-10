package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Update_Group_Members_NetRequest extends Template_NetRequest {
	public Update_Group_Members_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_UPDATE_GRP_MEMBERS);
	}

	public Boolean doUpdateGroupMembers(Group_Bean group) throws Exception {
		/**
		 * {"grp_id":"1", "members":[1,3]}
		 */
		Boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data_update_group_members = MyJsonWriter.getJsonDataForUpdateGroupMembers(group);
			// 连接
			openConnection(data_update_group_members);
			// 返回结果
			// String res = "";
			if (getResultData().getResult().equals("0")) {
				result = true;
				// res = getResultData().getLoc_data();
			} else {
				// res = getResultData().getResult_msg();
			}
			// result = MyJsonReader.getJsonDataForUpdateGroupMembers(res);
		} catch (Exception e) {
			// result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
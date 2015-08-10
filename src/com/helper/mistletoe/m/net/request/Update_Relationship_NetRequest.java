package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Tool_Utils;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Update_Relationship_NetRequest extends Template_NetRequest {
	public Update_Relationship_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_UPDATE_RELATIONSHIP);
	}

	public Boolean doUpdateRelationship(Integer helper_id, Integer status, String memo_name) throws Exception {
		/**
		 * { "dst_id":"3","status":"2", "memo_name":"xiaoluo"}
		 */
		Boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data_update_relationship = MyJsonWriter.getJsonDataForUpdateRelationship(helper_id, status, memo_name);
			// 连接
			openConnection(data_update_relationship);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				result = true;
			}else if (getResultData().getResult().equals("2008")) {
				Tool_Utils.showInfo(context, "你们之间还存在已签约的目标，不可以加入黑名单!");
			} else {
				Tool_Utils.showInfo(context, "加入黑名单失败，请在网络状态良好的情况下，重新操作！");
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Action_Duang_NetRequest extends Template_NetRequest {
	public Action_Duang_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_ACTION_DUANG);
	}

	public Boolean doActionDuang(Integer Id) throws Exception {
		/**
		 * {"dst_user_id": 10001} { "result": 0, "result_msg": "Success.",
		 * "data": { "src_user_id": 10000, "dst_user_id": 10001,
		 * "src_user_coin_balance": 198, "dst_user_coin_balance": 202,
		 * "action_time": 1429976546, "action_desc": "您Duang了对方一下！" } }
		 */
		Boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data_action_duang = MyJsonWriter.getJsonDataForActionDuang(Id);
			// 连接
			openConnection(data_action_duang);
			// 返回结果
			// String res = "";
			if (getResultData().getResult().equals("0")) {
				result = true;
				// res = getResultData().getLoc_data();
			}
			// result = MyJsonReader.getJsonDataForActionDuang(res);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
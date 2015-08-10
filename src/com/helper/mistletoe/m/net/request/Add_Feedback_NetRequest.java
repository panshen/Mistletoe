package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.NetResult_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Add_Feedback_NetRequest extends Template_NetRequest {
	public Add_Feedback_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_USER_ADD_FEEDBACK);
	}

	public NetResult_Bean doAddFeedback(String word) throws Exception {
		/**
		 * {"feedback":"very good"}
		 */
		NetResult_Bean result = null;
		try {
			// 请求参数
			// 获取data
			String data_add_feedback = MyJsonWriter.getJsonDataForAddFeedback(word);
			// 连接
			openConnection(data_add_feedback);
			// 返回结果
			// String res = "";
			// // if (getResultData().getResult().equals("0")) {
			// res = getResultData().getLoc_data();
			// } else {
			result = getResultData();
			// }
			// result = MyJsonReader.getJsonDataForAddFeedback(res);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
package com.helper.mistletoe.m.net.request;

import org.json.JSONObject;

import android.content.Context;

import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class Withdraw_Schedule_NetRequest extends Template_NetRequest {

	public Withdraw_Schedule_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_NOTE_WITHDRAW);
	}

	public String createTarget(String note_id) throws Exception {
		String result = null;
		try {
			// 请求参数
			// 获取data
			String data = fromateData(note_id);
			// 连接
			openConnection(data);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				result = getResultData().getLoc_data();
				JSONObject jsonObject = new JSONObject(result);
				result = jsonObject.getString("explanation");
			} else {
				result = "";
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String fromateData(String note_id) throws Exception {
		String result = "";
		try {
			JSONObject jData = new JSONObject();

			if (note_id != null) {
				jData.put("note_id", note_id);
			}

			result = jData.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
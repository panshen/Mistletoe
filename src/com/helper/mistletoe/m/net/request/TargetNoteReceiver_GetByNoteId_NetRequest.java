package com.helper.mistletoe.m.net.request;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class TargetNoteReceiver_GetByNoteId_NetRequest extends Template_NetRequest {

	public TargetNoteReceiver_GetByNoteId_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_GETBYNOTEID_TARGETNOTERECEIVER);
	}

	public String getList(String noteId) throws Exception {
		String result = null;
		try {
			// 请求参数
			JSONArray tNoteId = new JSONArray(noteId);
			// 获取data
			String data = fromateData(tNoteId);
			// 连接
			openConnection(data);
			// 返回结果
			result = "";
			if (getResultData().getResult().equals("0")) {
				String t_resc = getResultData().getLoc_data();
				result = t_resc;
			} else {
				String t_resc = getResultData().getResult_msg();
				result = "";
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String fromateData(JSONArray noteId) throws Exception {
		String result = "";
		try {
			JSONObject jData = new JSONObject();

			jData.put("note_id", noteId);

			result = jData.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
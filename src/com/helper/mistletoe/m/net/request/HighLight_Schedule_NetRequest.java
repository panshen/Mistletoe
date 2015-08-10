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
public class HighLight_Schedule_NetRequest extends Template_NetRequest {

	public HighLight_Schedule_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_NOTE_HIGHLIGHT);
	}

	public String createTarget(String target_id, String note_id, String highLight) throws Exception {
		String result = null;
		try {
			// 请求参数
			// 获取data
			String data = fromateData(target_id, note_id, highLight);
			// 连接
			openConnection(data);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				result = getResultData().getLoc_data();
				JSONObject jsonObject = new JSONObject(result);
				result = jsonObject.getString("ret");
			} else {
				result = "";
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String fromateData(String target_id, String note_id, String highLight) throws Exception {
		String result = "";
		try {
			JSONObject jData = new JSONObject();

			if (target_id != null) {
				jData.put("target_id", target_id);
			}
			if (note_id != null) {
				jData.put("note_id", note_id);
			}
			if (note_id != null) {
				jData.put("highlight_flag", highLight);
			}

			result = jData.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
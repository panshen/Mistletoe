package com.helper.mistletoe.m.net.request;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.NoteTagList_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Schedule_GetTag_NetRequest extends Template_NetRequest {
	public Schedule_GetTag_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_NOTE_GETTAG);
	}

	public NoteTagList_Bean doRequest(int targetId) {
		NoteTagList_Bean result = new NoteTagList_Bean();
		try {
			// 请求参数
			// 获取data
			String data = fromateData(targetId);
			// 连接
			openConnection(data);
			// 返回结果
			String tResc = "";
			if (getResultData().getResult().equals("0")) {
				tResc = getResultData().getLoc_data();
				// 访问成功
				result.setTags(tResc);
			} else {
				tResc = getResultData().getResult_msg();
				// 访问失败
			}
		} catch (Exception e) {
			result = new NoteTagList_Bean();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	private String fromateData(int targetId) {
		String result = "";
		try {
			JSONObject jData = new JSONObject();
			if (targetId > 0) {
				jData.put("target_id", targetId);
			}
			result = jData.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

}
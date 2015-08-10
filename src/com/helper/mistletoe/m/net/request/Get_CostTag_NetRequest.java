package com.helper.mistletoe.m.net.request;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonReader;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.NoteTag_Pojo;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Get_CostTag_NetRequest extends Template_NetRequest {
	public Get_CostTag_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_NOTE_GETTAG);
	}

	public ArrayList<NoteTag_Pojo> doRequest(int targetId) {
		ArrayList<NoteTag_Pojo> result = new ArrayList<NoteTag_Pojo>();
		try {
			// 请求参数
			// 获取data
			String data = fromateData(targetId);
			// 连接
			openConnection(data);
			// 返回结果
			String tResc = "";
			if (getResultData().getResult().equals("0")) {
				// 访问成功
				tResc = getResultData().getLoc_data();
				if (tResc.length() > 0) {
					result = MyJsonReader.getJsonDataForGetCostTag(tResc);
				}
			} else {
				tResc = getResultData().getResult_msg();
				// 访问失败
			}
		} catch (Exception e) {
			result = new ArrayList<NoteTag_Pojo>();
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
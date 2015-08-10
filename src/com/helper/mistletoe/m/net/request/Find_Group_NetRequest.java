package com.helper.mistletoe.m.net.request;

import java.util.ArrayList;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonReader;
import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Find_Group_NetRequest extends Template_NetRequest {
	public Find_Group_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_FIND_GROUP);
	}

	public ArrayList<Group_Bean> doFindGroup(Long last_updated_time) throws Exception {
		/**
		 * 
		 {"last_updated_time":"0", "count":"10"}
		 */
		ArrayList<Group_Bean> groupList = new ArrayList<Group_Bean>();
		try {
			// 请求参数
			// 获取data
			String data_find_group = MyJsonWriter.getJsonDataForFindGroup(last_updated_time);
			// 连接
			openConnection(data_find_group);
			// 返回结果
			String res = null;
			if (getResultData().getResult().equals("0")) {
				res = getResultData().getLoc_data();
				if (res.length() > 0) {
					groupList = MyJsonReader.getJsonDataForFindGroup(res);
				}
			} else {
				res = getResultData().getResult_msg();
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return groupList;
	}
}
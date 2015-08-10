package com.helper.mistletoe.m.net.request;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.helper.mistletoe.json.MyJsonReader;
import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Find_Public_Target_NetRequest extends Template_NetRequest {
	public Find_Public_Target_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_TARGET_GETMARKETTARGET);
	}

	public ArrayList<Target_Bean> doFindPublicTarget(int page_index,int page_size,String keyword) throws Exception {
		/**
		 * 
		 “page_size”: 50,//int，分页大小
	“page_index”：0,//int,第几页，从0开始为第一页。
	“keyword”："装修",//string，查询字符串，匹配target summary

		 */
		ArrayList<Target_Bean> public_List = new ArrayList<Target_Bean>();
		try {
			// 请求参数
			// 获取data
			String data_find_publicHelper = MyJsonWriter.getJsonDataForFindPublicTarget(page_index,page_size,keyword);
			// 连接
			openConnection(data_find_publicHelper);
			// 返回结果
			String res = "";
			if (getResultData().getResult().equals("0")) {
				res = getResultData().getLoc_data();
				Log.v("public_list","显示====》"+res.toString());
			}
			if (res.length() > 0) {
				public_List = MyJsonReader.getJsonDataForFindPublicTarget(res);
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return public_List;
	}
}
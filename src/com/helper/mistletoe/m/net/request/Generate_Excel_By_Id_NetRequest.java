package com.helper.mistletoe.m.net.request;

import android.content.Context;

import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.base.Template_includeInfo_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.NetResult_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

public class Generate_Excel_By_Id_NetRequest extends Template_includeInfo_NetRequest {
	public Generate_Excel_By_Id_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_GENERATE_EXCEL);
	}

	public NetResult_Bean doGenerateExcelByTargetId(Integer target_Id,String email) throws Exception {
		NetResult_Bean result = null;
		try {
			// 请求参数
			// 获取data
			String data_generateExcel_byTargetId = MyJsonWriter.getJsonDataForGenerateExcelByTargetId(target_Id, email);
			// 连接
			openConnection(data_generateExcel_byTargetId);
			// 返回结果
			result=getResultData();
		} catch (Exception e) {
			result=new NetResult_Bean();
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
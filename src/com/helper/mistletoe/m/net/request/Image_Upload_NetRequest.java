package com.helper.mistletoe.m.net.request;

import java.io.File;

import android.content.Context;

import com.google.gson.Gson;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.UploadFile_File_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class Image_Upload_NetRequest extends Template_NetRequest {
	public Image_Upload_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_UPLOADFILE_FILE, NetRequest_Bean.FILE_TYPE_IMAGE);
	}

	public UploadFile_File_Bean doUpload(File file_content) throws Exception {
		UploadFile_File_Bean result = null;
		try {
			// 请求参数
			// 获取data
			String data = fromateData();
			// 连接
			openConnection(data, file_content);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				String t_ire = getResultData().getLoc_data();
				Gson gson = new Gson();
				result = new UploadFile_File_Bean();
				result = gson.fromJson(t_ire, UploadFile_File_Bean.class);
				result.setLoc_NetRes(getResultData());
			} else {
				String t_ire = getResultData().getResult_msg();
				result = new UploadFile_File_Bean();
				result.setLoc_NetRes(getResultData());
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String fromateData() throws Exception {
		String result = "";
		try {
		} catch (Exception e) {
			result = "";
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
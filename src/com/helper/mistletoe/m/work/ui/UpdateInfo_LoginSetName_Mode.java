package com.helper.mistletoe.m.work.ui;

import java.io.File;

import android.content.Context;

import com.helper.mistletoe.m.net.request.Image_Upload_NetRequest;
import com.helper.mistletoe.m.net.request.UpdateUser_User_NetRequest;
import com.helper.mistletoe.m.pojo.Update_User_Bean;
import com.helper.mistletoe.m.pojo.UploadFile_File_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.work.base.BaseWork_Mode;
import com.helper.mistletoe.m.work.base.inter.WorkCallBack_Mode;
import com.helper.mistletoe.util.DisplayImage;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;

/**
 * <p>
 * Created by 张久瑞 on 2015年3月13日.
 * </p>
 * 
 * @author 张久瑞
 * @version 1.0
 */
public class UpdateInfo_LoginSetName_Mode extends BaseWork_Mode {
	private SnaBitmap head;// 输入：头像
	private String name;// 输入：名字
	private Update_User_Bean response;// 结果：服务器返回的结果

	public UpdateInfo_LoginSetName_Mode(WorkCallBack_Mode workCallBack, Context context, String name, SnaBitmap head) {
		super(workCallBack, context);
		try {
			this.name = name;
			this.head = head;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String result = "";
		try {
			// 新生成一个User
			User_Bean user = new User_Bean();
			user.setName(name);
			// 先上传图片
			File uploadFile = null;
			if (head.getType() == SnaBitmap.PATH_TYPE_FILE) {
				uploadFile = head.getPath_File();
			}
			if (head.getType() == SnaBitmap.PATH_TYPE_URI) {
				uploadFile = new File(DisplayImage.getFileFromUri(context, head.getPath_Uri()));
			}
			if (uploadFile != null) {
				Image_Upload_NetRequest uploadImageRequest = new Image_Upload_NetRequest(context);
				UploadFile_File_Bean resbean = uploadImageRequest.doUpload(uploadFile);
				if (!resbean.getFile_id().equals("")) {
					user.setAvatar_file_id(Transformation_Util.String2int(resbean.getFile_id(), -1));
				}
			}
			// 把修改上传到服务器
			UpdateUser_User_NetRequest userNetRequest = new UpdateUser_User_NetRequest(context);
			response = userNetRequest.doUpdate(user);
			// 处理结果
			if (response.getLoc_NetRes().getResult().equals("0")) {
				User_Bean userForSave = new User_Bean();
				userForSave.readData(context);
				userForSave.setName(user.getName());
				userForSave.setAvatar_file_id(user.getAvatar_file_id());
				userForSave.writeData(context);
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public Update_User_Bean getResponse() {
		if (response == null) {
			response = new Update_User_Bean();
		}
		return response;
	}

}
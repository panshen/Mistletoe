package com.helper.mistletoe.m.net.request;

import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Update_User_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class UpdateUser_User_NetRequest extends Template_NetRequest {
	public UpdateUser_User_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_USER_UPDATE);
	}

	public Update_User_Bean doUpdate(User_Bean user) throws Exception {
		Update_User_Bean result = null;
		try {
			// 请求参数
			// 获取data
			String data = fromateData(user);
			// 连接
			openConnection(data);
			// 返回结果
			if (getResultData().getResult().equals("0")) {
				String t_ire = getResultData().getLoc_data();
				Gson gson = new Gson();
				result = new Update_User_Bean();
				result = gson.fromJson(t_ire, Update_User_Bean.class);
				result.setLoc_NetRes(getResultData());
			} else {
				String t_ire = getResultData().getResult_msg();
				result = new Update_User_Bean();
				result.setLoc_NetRes(getResultData());
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String fromateData(User_Bean user) throws Exception {
		String result = null;
		try {
			JSONObject jData = new JSONObject();

			if (user.name != null) {
				jData.put("name", user.getName());
			}
			if (user.gender != null) {
				jData.put("gender", user.getGender());
			}
			if (user.country != null) {
				jData.put("country", user.getCountry());
			}
			if (user.city != null) {
				jData.put("city", user.getCity());
			}
			if (user.company != null) {
				jData.put("company", user.getCompany());
			}
			if (user.title != null) {
				jData.put("title", user.getTitle());
			}
			if (user.address != null) {
				jData.put("address", user.getAddress());
			}
			if (user.zip != null) {
				jData.put("zip", user.getZip());
			}
			if (user.mobile != null) {
				jData.put("mobile", user.getMobile());
			}
			if (user.tel != null) {
				jData.put("tel", user.getTitle());
			}
			if (user.fax != null) {
				jData.put("fax", user.getFax());
			}
			if (user.webpage != null) {
				jData.put("webpage", user.getWebpage());
			}
			if (user.email != null) {
				jData.put("email", user.getEmail());
			}
			if (user.sign != null) {
				jData.put("sign", user.getSign());
			}
			if (user.avatar_file_id != null) {
				jData.put("avatar_file_id", user.getAvatar_file_id());
			}
			if (user.memo != null) {
				jData.put("memo", user.getMemo());
			}

			result = jData.toString();
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
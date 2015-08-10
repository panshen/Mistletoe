package com.helper.mistletoe.c.task;

import java.util.ArrayList;

import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.net.request.UploadContact_User_NetRequest;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.util.ContactUtil;
import com.helper.mistletoe.util.Instruction_Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UploadContactTask extends AsyncTask<String, Integer, Boolean> {

	private Context context;

	public UploadContactTask(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		Boolean result = false;
		ArrayList<Helpers_Sub_Bean> ListHelper_contact = new ArrayList<Helpers_Sub_Bean>();
		ArrayList<Helpers_Sub_Bean> ListHelper_db = new ArrayList<Helpers_Sub_Bean>();
		try {
			HelperManager helperManager = new HelperManager();
			ListHelper_db = helperManager.ReadHelperFromDBByStatus(context, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });
			ListHelper_contact = ContactUtil.getContacts(context);
			ListHelper_contact.removeAll(ListHelper_db);// 去除本地数据库存在的部分
			if (ListHelper_contact.size() > 0) {// 有增量时，才会上传
				UploadContact_User_NetRequest uploadContact_User_NetRequest = new UploadContact_User_NetRequest(context);
				result = uploadContact_User_NetRequest.doUploadContact(ListHelper_contact);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {// 上传通讯录成功，获取helper，进行更新本地数据
			Log.v("上下文", "上传通讯录" + context);
			// 发送同步helper的指令
			Instruction_Utils.sendInstrustion(context, Instruction_Utils.SYNCHRONOUS_HELPER);
		}
	}
}

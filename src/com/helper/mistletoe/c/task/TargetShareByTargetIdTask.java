package com.helper.mistletoe.c.task;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.helper.mistletoe.m.net.request.Target_ShareTarget_By_Id_NetRequest;
import com.helper.mistletoe.m.pojo.NetResult_Bean;
import com.helper.mistletoe.util.Tool_Utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.ClipboardManager;

public class TargetShareByTargetIdTask extends
		AsyncTask<Integer, Integer, NetResult_Bean> {

	private Context context;
	private Target_ShareTarget_By_Id_NetRequest netRequest;
	private Integer target_id;
	private NetResult_Bean result;
	private ClipboardManager cp;
	private String mTargetTitle ;
	public TargetShareByTargetIdTask(Context context, Integer target_id,String title) {
		this.context = context;
		this.target_id = target_id;
		cp = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		this.mTargetTitle = title;
	}

	@Override
	protected NetResult_Bean doInBackground(Integer... params) {
		try {
			netRequest = new Target_ShareTarget_By_Id_NetRequest(context);
			result = netRequest.doGenerateExcelByTargetId(target_id);
		} catch (Exception e) {
			result = new NetResult_Bean();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(NetResult_Bean result) {
		super.onPostExecute(result);
		if (result != null) {
			
			JSONTokener js = new JSONTokener(result.getLoc_data());
			JSONObject jsonobj = null;
			try {
				jsonobj = (JSONObject) js.nextValue();

				// 网址
				String str = jsonobj.get("url").toString();
				cp.setText(str);
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(Intent.EXTRA_TEXT, "我使用海报创建了目标:"+mTargetTitle+",期待你的加入!" + "\n" + str);
				i.putExtra(Intent.EXTRA_SUBJECT, "海豹");
				context.startActivity(i);
				Tool_Utils.showInfo(context, "目标地址已经复制到粘贴板");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

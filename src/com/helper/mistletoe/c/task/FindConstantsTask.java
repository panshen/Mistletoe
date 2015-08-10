package com.helper.mistletoe.c.task;


import com.helper.mistletoe.m.net.request.Find_Target_Template_NetRequest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

public class FindConstantsTask extends AsyncTask<String, Integer,String> {

	private Context context;
	private Find_Target_Template_NetRequest netRequest;
	public FindConstantsTask(Context context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		String result_Bean = null;
		try {
			netRequest = new Find_Target_Template_NetRequest(context);
			result_Bean = netRequest.doFindTargetTemplate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_Bean;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result != null) {
			// 将模板保存到本地
//			Log.v("TemplateS", "保存TemplateS" + result.toString());
			// 获取SharedPreferences对象
			SharedPreferences sp = context.getSharedPreferences("TemplateConstants", context.MODE_PRIVATE);
			// 存入数据
			Editor editor = sp.edit();
			editor.putString("TemplateS", result.toString());
			editor.commit();
		}
	}
}

package com.helper.mistletoe.c.ui;

import java.util.ArrayList;
import java.util.List;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.adapter.FeedBackAdapter;
import com.helper.mistletoe.m.net.request.Add_Feedback_NetRequest;
import com.helper.mistletoe.m.pojo.Cache_Bean;
import com.helper.mistletoe.m.pojo.NetResult_Bean;
import com.helper.mistletoe.m.sp.CacheXml;
import com.helper.mistletoe.util.ThreadPoolUtils_Net;
import com.helper.mistletoe.util.Tool_Utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class FeedBackActivity extends Activity {
	private ListView feedBack_lv;
	private RelativeLayout back;
	private EditText word;
	private ImageView send;
	private FeedBackAdapter adapter;
	private List<Cache_Bean> cache_list;
	private SendFeedBackTask sendFeedBackTask;
	private Context context;
	private CacheXml cacheXml;
	private Cache_Bean cache;
	private Add_Feedback_NetRequest netRequest;
	private NetResult_Bean msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.feedback);
		context = this;
		initViews();

	}

	@Override
	protected void onResume() {
		super.onResume();
		setData();
		setlistener();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private void initViews() {
		back = (RelativeLayout) findViewById(R.id.feedback_button_back);
		feedBack_lv = (ListView) findViewById(R.id.feedback_listView_feedback);
		word = (EditText) findViewById(R.id.feedback_editText_word);
		send = (ImageView) findViewById(R.id.feedback_imageView_send);
		cacheXml = new CacheXml(context);
		cache = new Cache_Bean();
		cache_list = new ArrayList<Cache_Bean>();

	}

	private void setData() {
		cache = cacheXml.read();
		word.setText(cache.getFeedBack());
	}

	public void display(List<Cache_Bean> cache_list) {
		adapter = new FeedBackAdapter(context, cache_list);
		feedBack_lv.setAdapter(adapter);
	}

	private void setlistener() {
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cache = new Cache_Bean();
				cache.setFeedBack(word.getText().toString());
				cache.setFeedBack_stype("feedBack");
				if (cache.getFeedBack().isEmpty()) {
					Tool_Utils.showInfo(context, "请填写您的意见，谢谢！");
				} else {
					sendFeedBack(cache.getFeedBack());
					cache_list.add(cache);
				}
				word.setText("");
				display(cache_list);
			}
		});
	}

	protected void sendFeedBack(String FeedBack) {
		sendFeedBackTask = new SendFeedBackTask();
		sendFeedBackTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, FeedBack);
	}

	public class SendFeedBackTask extends AsyncTask<String, Integer, NetResult_Bean> {

		@Override
		protected NetResult_Bean doInBackground(String... params) {
			try {
				String word = params[0];
				netRequest = new Add_Feedback_NetRequest(context);
				msg = netRequest.doAddFeedback(word);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return msg;
		}

		@Override
		protected void onPostExecute(NetResult_Bean result) {
			super.onPostExecute(result);
			cache = new Cache_Bean();
			if (result == null) {
				cache.setFeedBack("上传失败，请检查网络状态");
			} else if (result.getResult().equals("-1")) {
				cache.setFeedBack("上传失败，请检查网络状态");
			} else if (result.getResult().equals("0")) {
				cache.setFeedBack("非常感谢!小罗会尽快给你回复的!!");
				cacheXml.write();
			} else {
				cache.setFeedBack(result.getResult_msg());
			}
			cache.setFeedBack_stype("reply");
			cache_list.add(cache);
			display(cache_list);
		}
	}

}

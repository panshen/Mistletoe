package com.helper.mistletoe.c.ui;

import com.helper.mistletoe.R;
import com.helper.mistletoe.util.sysconst.NetUrl_Const;

import android.net.http.SslError;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class ClauseActivity extends Activity {
	private WebView webview;
	private RelativeLayout back;
	private String Url_clause = NetUrl_Const.NET_CLAUSE;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.introduce);
		setUpView();
		setListener();
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setSupportZoom(true);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.loadUrl(Url_clause);
		webview.setWebViewClient(new HelloWebViewClient() {
			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				// handler.process();//����֤��
				handler.proceed();
			}
		});
	}

	private void setListener() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onDestroy();
				finish();
			}
		});

	}

	private void setUpView() {
		webview = (WebView) findViewById(R.id.webview_introduce);
		back = (RelativeLayout) findViewById(R.id.button_introduce_back);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack(); // goBack()��ʾ����WebView����һҳ��
			return true;
		}
		if ((keyCode == KeyEvent.KEYCODE_BACK) && !webview.canGoBack()) {
			ClauseActivity.this.finish();
			return true;
		}
		return false;
	}

	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		webview = null;
	}
}

package com.helper.mistletoe.m.net.request.base;

import com.helper.mistletoe.util.ExceptionHandle;
import com.loopj.android.http.SyncHttpClient;

/**
 * <p>
 * Created by 张久瑞 on 2015年3月16日.
 * </p>
 * 使用getInstance()获得这个类的实例，使用getClient()获得Client对象
 * 
 * @author 张久瑞
 * @version 1.0
 */
public class HttpClient_NetRequest {
	private volatile static HttpClient_NetRequest instance;
	/**
	 * 用于Http访问的Client对象
	 */
	private SyncHttpClient client_Http;
	/**
	 * 用于Https访问的Client对象
	 */
	private SyncHttpClient client_Https;

	private HttpClient_NetRequest() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 返回网络连接对象
	 *
	 * @param clientType
	 *            网络连接的类型Http还是Https
	 * @return 网络连接对象
	 * @throws Exception
	 *             未知异常
	 */
	protected SyncHttpClient getClient(String clientType) throws Exception {
		SyncHttpClient result = null;
		try {
			if (clientType.equals("http")) {
				result = getClient_Http();
			}
			if (clientType.equals("https")) {
				result = getClient_Https();
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private SyncHttpClient getClient_Http() {
		if (client_Http == null) {
			this.client_Http = HttpClientStaticFuction_NetRequest.httpClient();
			this.client_Http.setTimeout(180);
		}
		return client_Http;
	}

	private SyncHttpClient getClient_Https() {
		if (client_Https == null) {
			this.client_Https = HttpClientStaticFuction_NetRequest.httpsClient_IgnoreCertificate();
			this.client_Https.setTimeout(180);
		}
		return client_Https;
	}

	protected static HttpClient_NetRequest getInstance() throws Exception {
		try {
			if (instance == null) {
				synchronized (HttpClient_NetRequest.class) {
					if (instance == null) {
						instance = new HttpClient_NetRequest();
					}
				}
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return instance;
	}
}
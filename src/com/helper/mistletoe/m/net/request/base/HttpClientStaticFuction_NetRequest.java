package com.helper.mistletoe.m.net.request.base;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;

import com.helper.mistletoe.util.ExceptionHandle;
import com.loopj.android.http.MySSLSocketFactory;
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
public class HttpClientStaticFuction_NetRequest {
	/**
	 * 获得一个Http连接
	 *
	 * @return AsyncHttpClient对象
	 * @throws Exception
	 *             未知异常
	 */
	protected static SyncHttpClient httpClient() {
		SyncHttpClient result = null;
		try {
			result = new SyncHttpClient();
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 获得一个Https连接，会忽略证书验证<br/>
	 * 代码来自<a href="http://blog.csdn.net/fzping22/article/details/38402723">
	 * fzping22的专栏</a>
	 *
	 * @return AsyncHttpClient对象
	 * @throws Exception
	 *             未知异常
	 */
	protected static SyncHttpClient httpsClient_IgnoreCertificate() {
		SyncHttpClient result = null;
		try {
			SSLSocketFactory sf = MySSLSocketFactory.getFixedSocketFactory();
			// 从android-async-http包中取得SSLSocketFactory的对象。静态方法getFixedSocketFactory中已经设置允许所以服务器证书
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("https", sf, 443));
			// 设置SchemeRegistry为httpclient提供http和https选项。。这里未设置http
			result = new SyncHttpClient(schReg);
			// 从android-async-http中取得httpclient对象
			result.setSSLSocketFactory(sf);
			// 设置SSLSocketFactory
			// 其余与post与get方法一直。
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
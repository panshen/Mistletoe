package com.helper.mistletoe.m.net.request.base;

import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.NetResult_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.exc.UnLogginException;
import com.loopj.android.http.SyncHttpClient;

/**
 * <p>
 * Created by 张久瑞 on 2015年3月16日.
 * </p>
 * <p>
 * 网络请求模块入口
 * </p>
 * 
 * @author 张久瑞
 * @version 1.0
 */
public class AirLock_NetRequest {

	/**
	 * 请求网络
	 * 
	 * @param requestData
	 *            请求参数
	 * @return 请求结果
	 * @throws Exception
	 *             未知异常
	 */
	public static NetResult_Bean openConnection(NetRequest_Bean requestData) throws UnLogginException, Exception {
		NetResult_Bean result = null;
		try {
			Custom_NetRequest base_NetRequest = new Custom_NetRequest();
			result = base_NetRequest.openConnection(requestData);
		} catch (UnLogginException e) {
			result = null;
			throw e;
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	/**
	 * 获取一个同步的网络连接Client，不推荐
	 * 
	 * @param clientType
	 *            协议类型，Http或者Https
	 * @return Client对象
	 * @throws Exception
	 *             未知异常
	 */
	public static SyncHttpClient getClient(String clientType) throws Exception {
		SyncHttpClient result = null;
		try {
			result = HttpClient_NetRequest.getInstance().getClient(clientType);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
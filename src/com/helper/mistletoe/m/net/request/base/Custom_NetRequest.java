package com.helper.mistletoe.m.net.request.base;

import java.net.URL;

import org.apache.http.Header;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.NetResult_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.exc.UnLogginException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

/**
 * <p>
 * Created by 张久瑞 on 2015年3月16日.
 * </p>
 * 打开一个网络连接
 * 
 * @author 张久瑞
 * @version 1.0
 */
public class Custom_NetRequest {
	private NetRequest_Bean requestData;
	private NetResult_Bean resultData;

	protected NetResult_Bean openConnection(NetRequest_Bean requestData) throws UnLogginException, Exception {
		NetResult_Bean result = null;
		try {
			this.requestData = requestData;
			boolean canConnect = true;
			if (requestData.getAccess_token().equals("")) {
				canConnect = false;
				throw new UnLogginException("用户没有登录！");
			}
			if (canConnect) {
				openConnection();
			}
			result = resultData;
		} catch (UnLogginException e) {
			result = null;
			throw e;
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private String openConnection() throws Exception {
		String result = "";
		try {
			String requestType = requestData.getSys_RequestType();
			SyncHttpClient client = AirLock_NetRequest.getClient(new URL(requestData.getSys_RequestUrl()).getProtocol());
			String url = requestData.getSys_RequestUrl();
			RequestParams params = requestData.createRequestParams();
			ResponseHandlerInterface responseHandler = getResponseHandler();
			doNetWork(requestType, client, url, params, responseHandler);
		} catch (Exception e) {
			result = "";
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	private void doNetWork(String requestType, SyncHttpClient client, String url, RequestParams params, ResponseHandlerInterface responseHandler) throws Exception {
		try {
			if (requestType.equals(NetRequest_Bean.REQUEST_TYPE_POST)) {
				client.post(url, params, responseHandler);
			} else if (requestType.equals(NetRequest_Bean.REQUEST_TYPE_GET)) {
				client.get(url, params, responseHandler);
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected ResponseHandlerInterface getResponseHandler() throws Exception {
		ResponseHandlerInterface result = null;
		try {
			result = new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					try {
						String tResJson = new String(arg2);
						resultData = new Gson().fromJson(tResJson, NetResult_Bean.class);
						if (resultData == null) {
							resultData = new NetResult_Bean();
						}
						JSONObject jRes = new JSONObject(tResJson);
						if (jRes.has("data")) {
							resultData.setLoc_data(jRes.getString("data"));
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					try {
						resultData = new NetResult_Bean();
						resultData.setResult("-1");
						if (arg3 != null) {
							resultData.setResult_msg(arg3.toString());
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			};
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

}
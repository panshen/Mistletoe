package com.helper.mistletoe.c.task;

import com.helper.mistletoe.m.net.request.Get_Last_Version_NetRequest;
import com.helper.mistletoe.m.pojo.Result_Bean;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.Tool_Utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;

public class CheckVersionUpdateTask extends AsyncTask<String, Integer, Result_Bean> {

	private Context context;
	private Get_Last_Version_NetRequest netRequest;
	private String source;
	private boolean a;
	private String b;

	public CheckVersionUpdateTask(Context context) {
		this.context = context;
	}

	@Override
	protected Result_Bean doInBackground(String... params) {
		Result_Bean result_Bean = new Result_Bean();
		source=params[0];
		try {
			netRequest = new Get_Last_Version_NetRequest(context);
			result_Bean = netRequest.doGetLastVersion();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_Bean;
	}

	@Override
	protected void onPostExecute(Result_Bean result) {
		super.onPostExecute(result);
		if (result != null) {
			if (!Tool_Utils.isLastVerion(context,result.getApp_version())) {
				a=isConnected(context);
				if (a&&!result.getUrl().isEmpty()) {
					Intent intent = new Intent(); // Itent就是我们要发送的内容
					intent.setAction(MessageConstants.UPGRADED_VERSION); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
					intent.putExtra("source", source);
					intent.putExtra("url", result.getUrl());
					// 发送广播
					context.sendBroadcast(intent);	
				}else{
					Tool_Utils.showInfo(context, "最新版本为:" + result.getApp_version() + ",请转到WiFi状态下进行更新");
				}
			}else{
				if (source.equals("MySettingActivity")) {
					Tool_Utils.showInfo(context, "牛叉!你的版本已经最新版本!");
				}
			}
		} else {
			if (source.equals("MySettingActivity")) {
				Tool_Utils.showInfo(context, "牛叉!你的版本已经最新版本!");
			}
		}
	}
	 /**
     * 判断当前是否网络连接
     *
     * @param context
     * @return 状态码
     */
    public boolean isConnected(Context context) {
    	boolean c=false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting()) {
            switch (ni.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    c = true;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    switch (ni.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS: //联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: //电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: //移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager. NETWORK_TYPE_IDEN:
//                            stateCode = NetState.NET_2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: //电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
//                            stateCode = NetState.NET_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:
//                            stateCode = NetState.NET_4G;
                            break;
                        default:
//                            stateCode = NetState.NET_UNKNOWN;
                    }
                    break;
                default:
//                    stateCode = NetState.NET_UNKNOWN;
            }

        }
        return c;
    }

}

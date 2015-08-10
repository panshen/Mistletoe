package com.helper.mistletoe.util.observer.v1;

import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.observer.Watcher;
import com.helper.mistletoe.util.sysconst.Broadcast_Const;

/**
 * 由张久瑞在2015年4月18日创建<br/>
 * 广播接收器，用于注册观察者
 * 
 * @author 张久瑞
 */
public class ReadyGoooReceiver extends BroadcastReceiver {
	private Watcher observer;

	public ReadyGoooReceiver(Watcher observer) {
		super();
		try {
			this.observer = observer;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			// 判断各个对象是否安全
			boolean tIsWorkCanDo = true;
			if (observer == null) {
				tIsWorkCanDo = false;
			}
			if (intent == null) {
				tIsWorkCanDo = false;
			}
			// 执行任务
			if (tIsWorkCanDo) {
				Bundle tBundle = intent.getExtras();
				if (tBundle == null) {
					tBundle = new Bundle();
				}
				// 取出所有的数据
				String tAddData = tBundle.getString("com.helper.mistletoe.bbc.data");
				JSONObject tAddData_Json = new JSONObject(tAddData);
				// 取出每个键
				String tStatus = "";
				String tClassName = "";
				String tResData = "";
				if (tAddData_Json.has(Broadcast_Const.BC_MODE_DATA)) {
					tResData = tAddData_Json.getString(Broadcast_Const.BC_MODE_DATA);
				}
				if (tAddData_Json.has(Broadcast_Const.BC_MODE_CLASS)) {
					tClassName = tAddData_Json.getString(Broadcast_Const.BC_MODE_CLASS);
				}
				if (tAddData_Json.has(Broadcast_Const.BC_MODE_STATUS)) {
					tStatus = tAddData_Json.getString(Broadcast_Const.BC_MODE_STATUS);
				}
				if (tStatus.equals(Broadcast_Const.BC_TAG_WORK)) {
					observer.getWorkFactory().publishWork(tAddData);
				} else {
					observer.doWorkReceiver(tStatus, tClassName, tResData);
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void register(Context context) {
		try {
			// 判断各个对象是否安全
			boolean tIsWorkCanDo = true;
			if (observer == null) {
				tIsWorkCanDo = false;
			}
			if (context == null) {
				tIsWorkCanDo = false;
			}
			// 执行任务
			if (tIsWorkCanDo) {
				IntentFilter tFilter = new IntentFilter();
				for (String i : observer.getFilter()) {
					tFilter.addAction(i);
				}
				context.registerReceiver(this, tFilter);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void unRegister(Context context) {
		try {
			// 判断各个对象是否安全
			boolean tIsWorkCanDo = true;
			if (context == null) {
				tIsWorkCanDo = false;
			}
			// 执行任务
			if (tIsWorkCanDo) {
				context.unregisterReceiver(this);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
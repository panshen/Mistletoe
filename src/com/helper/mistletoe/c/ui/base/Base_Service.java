package com.helper.mistletoe.c.ui.base;

import java.util.HashSet;

import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;

import com.helper.mistletoe.m.pojo.AppNote_Bean;
import com.helper.mistletoe.m.sp.AppNote_sp;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.util.Dialog_Util;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.util.observer.Watcher;
import com.helper.mistletoe.util.observer.v1.ReadyGoooReceiver;
import com.helper.mistletoe.util.prcomode.Consumer;
import com.helper.mistletoe.util.prcomode.ReadyGoooFactory;
import com.helper.mistletoe.util.prcomode.ReadyGoooWork;
import com.helper.mistletoe.util.prcomode.v3.FactoryHandler_v3;
import com.helper.mistletoe.util.prcomode.v3.Factory_v3;
import com.helper.mistletoe.util.sysconst.Broadcast_Const;

import de.greenrobot.event.EventBus;

/**
 * <p>
 * Created by 张久瑞 on 2015年3月13日.
 * </p>
 * 
 * @author 张久瑞
 * @version 1.0
 */
public abstract class Base_Service extends Service implements Watcher, Consumer, PageCache {
	protected ReadyGoooFactory workFactory;
	protected ReadyGoooReceiver mBroadcastReceiver;

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			getBroadcastReceiver().register(getApplicationContext());
			registerEventBus();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			getBroadcastReceiver().register(getApplicationContext());
			unRegisterEventBus();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	// TODO 生产/消费模型

	@Override
	public void onWorkOk(ReadyGoooWork work) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	// TODO 观察者

	@Override
	public void doWorkReceiver(String status, String className, String data) {
		try {
			if (status.equals(Broadcast_Const.BC_TAG_UI_DATACHANGED_TARGET)) {
				onTargetChangedReceiver();
			} else if (status.equals(Broadcast_Const.BC_TAG_UI_DATACHANGED_TARGETMEMBER)) {
				String tId = new JSONObject(data).getString("targetId");
				onTargetMemeberChangedReceiver(tId);
			} else if (status.equals(Broadcast_Const.BC_TAG_UI_DATACHANGED_SCHEDULE)) {
				String tId = new JSONObject(data).getString("targetId");
				onScheduleChangedReceiver(tId);
			} else if (status.equals(Broadcast_Const.BC_TAG_CLOUD_CHANGED_TARGET)) {
				onTargetCloudChangedReceiver();
			} else if (status.equals(Broadcast_Const.BC_TAG_CLOUD_CHANGED_TARGETMEMBER)) {
				String tId = new JSONObject(data).getString("targetId");
				onTargetMemeberCloudChangedReceiver(tId);
			} else if (status.equals(Broadcast_Const.BC_TAG_CLOUD_CHANGED_SCHEDULE)) {
				String tId = new JSONObject(data).getString("targetId");
				onScheduleCloudChangedReceiver(tId);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void onTargetChangedReceiver() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void onTargetMemeberChangedReceiver(String id) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void onScheduleChangedReceiver(String id) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void onTargetCloudChangedReceiver() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void onTargetMemeberCloudChangedReceiver(String id) {
		try {
//			AirLock_Work.syncTargetMember(getContext(), Transformation_Util.String2int(id));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void onScheduleCloudChangedReceiver(String id) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public ReadyGoooFactory getWorkFactory() {
		if (workFactory == null) {
			workFactory = new Factory_v3(getApplicationContext(), new FactoryHandler_v3(), this);
		}
		return workFactory;
	}

	@Override
	public HashSet<String> getFilter() {
		HashSet<String> result = null;
		try {
			result = new HashSet<String>();
			getFilter(result);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public void getFilter(HashSet<String> filter) {
		try {
			filter.add(Broadcast_Const.BC_TAG_UI_DATACHANGED);
			filter.add(Broadcast_Const.BC_TAG_WORK);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public ReadyGoooReceiver getBroadcastReceiver() {
		if (mBroadcastReceiver == null) {
			mBroadcastReceiver = new ReadyGoooReceiver(this);
		}
		return mBroadcastReceiver;
	}

	// TODO 页面缓存

	@Override
	public JSONObject readData() {
		JSONObject result = new JSONObject();
		try {
			AppNote_sp tAsp = new AppNote_sp(getApplicationContext());
			AppNote_Bean tAppNote = tAsp.read();
			result = tAppNote.readPageData(getClass().getName());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public void saveData(JSONObject data) {
		try {
			AppNote_sp tAsp = new AppNote_sp(getApplicationContext());
			AppNote_Bean tAppNote = tAsp.read();
			tAppNote.savePageData(getClass().getName(), data);
			tAsp.write(tAppNote);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	// TODO Dialog

	protected void showDialog(String message, DialogInterface.OnClickListener positiveListener) {
		Dialog_Util.showDialog(getContext(), message, positiveListener);
	}

	// TODO EventBus
	protected void registerEventBus() {
		try {
			EventBus.getDefault().register(this);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void unRegisterEventBus() {
		try {
			EventBus.getDefault().unregister(this);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected EventBus getEventBus() {
		return EventBus.getDefault();
	}

	// TODO 通用

	protected Context getContext() {
		return this;
	}

}
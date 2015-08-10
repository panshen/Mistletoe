package com.helper.mistletoe.c.ui.base;

import java.lang.ref.SoftReference;
import java.util.HashSet;

import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.helper.mistletoe.c.ui.ReceiverInterface;
import com.helper.mistletoe.m.pojo.AppNote_Bean;
import com.helper.mistletoe.m.sp.AppNote_sp;
import com.helper.mistletoe.util.Dialog_Util;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.MessageConstants;
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
public abstract class Base_Activity extends FragmentActivity implements UIInterface, Watcher, Consumer, PageCache, ReceiverInterface {
	private SoftReference<UIToast> mToast;
	protected ReadyGoooFactory workFactory;
	protected ReadyGoooReceiver mBroadcastReceiver;
	private IntentFilter myIntentFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			// 注册广播
			regiserRadio(MessageConstants.REFRESHS_PROJECTION);
			getBroadcastReceiver().register(getApplicationContext());
			registerEventBus();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {
			getBroadcastReceiver().unRegister(getApplicationContext());
			unRegisterEventBus();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 注销广播
		destroyRadio();
	}

	@Override
	public void destroyRadio() {
		// 注销广播
		getApplicationContext().unregisterReceiver(IntentReceicer);
	}

	@Override
	public void dealWithRadio(Intent intent) {
		// TODO Auto-generated method stub
		// Log.v("广播", "父类中" + intent.getIntExtra("group_id", -1));
	}

	@Override
	public void regiserRadio(String[] actions) {
		myIntentFilter = new IntentFilter();
		for (int i = 0; i < actions.length; i++) {
			myIntentFilter.addAction(actions[i]);
		}
		// 注册广播
		getApplicationContext().registerReceiver(IntentReceicer, myIntentFilter);
	}

	private BroadcastReceiver IntentReceicer = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			dealWithRadio(intent);
		}
	};

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

	// TODO Toast

	public void showToast(CharSequence text, int duration) {
		try {
			getToastComponent().showToast(text, duration);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void showToast(CharSequence text) {
		try {
			getToastComponent().showToast(text);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public UIToast getToastComponent() {
		if ((mToast == null) || (mToast.get() == null)) {
			mToast = new SoftReference<UIToast>(new UIToast(getContext()));
		}
		return mToast.get();
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

	public interface ChangeMarketPosition {
		public void changeMarketPosition(int targetId, int position);
	}

}
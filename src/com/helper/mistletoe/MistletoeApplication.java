package com.helper.mistletoe;

import java.lang.ref.SoftReference;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.helper.mistletoe.c.ui.MainFragmentActivity;
import com.helper.mistletoe.c.ui.Target_Details_Activity;
import com.helper.mistletoe.util.DisplayImage;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Instruction_Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

public class MistletoeApplication extends Application {
	/**
	 * 张久瑞2015年5月14日添加，Application由Android系统维护，请不要重新New一个对象
	 */
	@Deprecated
	private static MistletoeApplication instance;// TODO 请删除这个变量
	public static String TOKEN = "null";
	public static int mDiceCount;
	public static int targetInterfaceFlags;
	public static int targetId;
	public static String targetTag;
	public static int noteCount = 0;
	public static int duangCount = 0;
	private SoftReference<ImageLoader> mAUIL;

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
			SDKInitializer.initialize(getApplicationContext());
			initAUIL();// 初始化AUIL图片显示组件，这个函数不能抛出异常，不然会影响后面的语句执行
//			synchronousConstants();//同步常量数据
			getToken();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

//	private void synchronousConstants() {
//		// 发送同步系统费用标签常量的指令
//	  Instruction_Utils.sendInstrustion(getApplicationContext(), Instruction_Utils.SYNCHRONOUS_COSTTAG);
//	}

	public void getToken() {
		XGPushManager.registerPush(this);
		// 开启logcat输出，方便debug，发布时请关闭
		XGPushConfig.enableDebug(this, true);
		XGPushManager.registerPush(this, new XGIOperateCallback() {

			@Override
			public void onSuccess(Object arg0, int arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					TOKEN = arg0.toString() + "";
				}
				Log.e("TPush", "注册成功，设备token为：" + TOKEN);
				return;
			}

			@Override
			public void onFail(Object arg0, int arg1, String arg2) {
				// TODO Auto-generated method stub
				TOKEN = "null";
				Log.e("TPush", "注册失败，错误码：" + arg1 + ",错误信息：" + arg2);
			}
		});
	}

	public void pushNotifications(Context context, Intent intent) {
		try {
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context.getApplicationContext());
			mBuilder.setContentTitle("海豹")
			// 设置通知栏标题
					.setContentText(intent.getCharSequenceExtra("content")).setNumber(intent.getIntExtra("count", 1))
					// 设置通知集合的数量
					.setWhen(System.currentTimeMillis())
					// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
					.setDefaults(Notification.DEFAULT_LIGHTS).setDefaults(Notification.DEFAULT_SOUND).setSmallIcon(R.drawable.ic_launcher);// 设置通知小ICON
			// 用mNotificationManager的notify方法通知用户生成标题栏消息通知
			Intent intents = new Intent(context.getApplicationContext(), Target_Details_Activity.class);
			intents.putExtra("source", 1);
			intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intents, 0);
			mBuilder.setContentIntent(pendingIntent);
			Notification notify = mBuilder.build();
			notify.flags |= Notification.FLAG_AUTO_CANCEL;// 设置这个标志当用户单击面板就可以让通知将自动取消
			notify.flags |= Notification.PRIORITY_HIGH;// 设置该通知优先级
			mNotificationManager.notify(0, notify);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void pushDuangNotifications(Context context, Intent intent) {
		try {
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
			mBuilder.setContentTitle("海豹")// 设置通知栏标题
					.setContentText(intent.getStringExtra("content"))// /<span
					.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
					.setSmallIcon(R.drawable.ic_launcher);// 设置通知小ICON
			// 用mNotificationManager的notify方法通知用户生成标题栏消息通知
			Intent intents = new Intent(context.getApplicationContext(), MainFragmentActivity.class);
			intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intents, 0);
			mBuilder.setContentIntent(pendingIntent);
			Notification notify = mBuilder.build();
			notify.defaults = Notification.DEFAULT_LIGHTS;
			notify.defaults = Notification.DEFAULT_VIBRATE;
			notify.sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.coin);
			notify.flags |= Notification.FLAG_AUTO_CANCEL;// 设置这个标志当用户单击面板就可以让通知将自动取消
			notify.flags |= Notification.PRIORITY_HIGH;// 设置该通知优先级
			mNotificationManager.notify(intent.getIntExtra("count", 1), notify);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 初始化AUIL图片显示组件
	 */
	private void initAUIL() {
		try {
			mAUIL = new SoftReference<ImageLoader>(DisplayImage.initAndroidUniversalImageLoader(getApplicationContext()));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public ImageLoader getAUIL() {
		if ((mAUIL == null) || (mAUIL.get() == null) || (!mAUIL.get().isInited())) {
			initAUIL();
		}
		return mAUIL.get();
	}

	public int getDiceCount() {
		if (mDiceCount == 0) {
			mDiceCount = 6;
		}
		return mDiceCount;
	}

	public void setDiceCount(int diceCount) {
		this.mDiceCount = diceCount;
	}

	public static MistletoeApplication getInstance(Context context) {
		MistletoeApplication result = null;
		try {
			if (context.getApplicationContext() instanceof MistletoeApplication) {
				result = (MistletoeApplication) context.getApplicationContext();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 张久瑞2015年5月14日添加，Application由Android系统维护，请不要重新New一个对象
	 */
	@Deprecated
	public synchronized static MistletoeApplication getInstance() {
		// TODO 请删除这个函数
		if (null == instance) {
			instance = new MistletoeApplication();
		}
		return instance;
	}

}
package com.helper.mistletoe.roll;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.util.Log;

public class ShakeListener implements SensorEventListener {
	String TAG = "ShakeListener";
	private static final int SPEED_SHRESHOLD = 3000;
	private static final int UPTATE_INTERVAL_TIME = 70;
	private static MyCount mc;
	public static  String shakingStaus = "Stop";
	private SensorManager sensorManager;
	private Sensor sensor;
	private OnShakeListener onShakeListener;
	private Context mContext;
	private float lastX;
	private float lastY;
	private float lastZ;
	private long lastUpdateTime;

	public ShakeListener(Context c) {
		mContext = c;
		mc = new MyCount(1000, 100);
		start();
	}

	public void start() {
		sensorManager = (SensorManager) mContext
				.getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager != null) {
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		if (sensor != null) {
			sensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_GAME);
		}
	}

	public void stop() {
		sensorManager.unregisterListener(this);
	}

	public void setOnShakeListener(OnShakeListener listener) {
		onShakeListener = listener;
	}

	public void onSensorChanged(SensorEvent event) {
		long currentUpdateTime = System.currentTimeMillis();
		long timeInterval = currentUpdateTime - lastUpdateTime;
		if (timeInterval < UPTATE_INTERVAL_TIME)
			return;
		lastUpdateTime = currentUpdateTime;
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		float deltaX = x - lastX;
		float deltaY = y - lastY;
		float deltaZ = z - lastZ;
		lastX = x;
		lastY = y;
		lastZ = z;
		double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
				* deltaZ)
				/ timeInterval * 10000;
		if (speed >= SPEED_SHRESHOLD) {
			mc.cancel();
			mc.start();
			Log.v("shake", "晃动中。。。");
			shakingStaus = "Shaking";
			onShakeListener.onShake();
		} else {

		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public interface OnShakeListener {
		public void onShake();
	}

	/* 定义一个倒计时的内部类 */
	class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			// tv.setText("finish");
			Log.v("shake", "停止晃动");
			shakingStaus = "Stop";
			onShakeListener.onShake();
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// tv.setText("请等待30秒(" + millisUntilFinished / 1000 + ")...");
		}
	}
}

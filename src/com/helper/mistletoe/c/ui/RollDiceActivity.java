package com.helper.mistletoe.c.ui;

import com.helper.mistletoe.MistletoeApplication;
import com.helper.mistletoe.R;
import com.helper.mistletoe.roll.Dices;
import com.helper.mistletoe.roll.ShakeListener;
import com.helper.mistletoe.roll.ShakeListener.OnShakeListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class RollDiceActivity extends Activity {
	private RelativeLayout back, result_list;
	private TextView count_dice;
	private SeekBar seekBar_dice;
	Vibrator vibrator;
	Dices dices;
	private ShakeListener mShakeListener;
	public static boolean isForeground = true;
	PowerManager pm;
	PowerManager.WakeLock mWakeLock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.roll_dice);
		initView();
		setlistener();
	}

	private void setlistener() {
		// TODO Auto-generated method stub
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isForeground = false;
				mShakeListener.stop();
				finish();
			}
		});
		result_list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(RollDiceActivity.this, RollListActivity.class);
				startActivity(intent);
			}
		});
		seekBar_dice.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				int p = seekBar.getProgress();
				if (p == 0) {
					MistletoeApplication.getInstance().setDiceCount(1);
				} else {
					MistletoeApplication.getInstance().setDiceCount(p / 20 + 1);
				}

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				int c = 1;
				if (progress == 0) {
					c = 1;
				} else {
					c = progress / 20 + 1;
				}
				count_dice.setText("" + c);
			}
		});
		mShakeListener.setOnShakeListener(new OnShakeListener() {

			@Override
			public void onShake() {
				// TODO Auto-generated method stub

				int num = MistletoeApplication.getInstance().getDiceCount();
				dices.update(num);
			}
		});
	}

	private void initView() {
		back = (RelativeLayout) findViewById(R.id.button_roll_dice_back);
		result_list = (RelativeLayout) findViewById(R.id.button_roll_dice_list);
		count_dice = (TextView) findViewById(R.id.roll_dice_textView_count);
		seekBar_dice = (SeekBar) findViewById(R.id.roll_dice_seekBar);
		int postion = MistletoeApplication.getInstance().getDiceCount();
		seekBar_dice.setProgress((postion - 1) * 20);
		count_dice.setText("" + postion);
		dices = (Dices) findViewById(R.id.roll_dice_dices);
		mShakeListener = dices.getmShakeListener();
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		// mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,
		// "My Tag");
		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// in onResume() call
		mWakeLock.acquire();
		isForeground = true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// in onPause() call
		mWakeLock.release();
		isForeground = false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isForeground = false;
		dices.mp = null;
		dices = null;
		mShakeListener.stop();

	}

	// 按back按钮就退出程序
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		isForeground = false;
		mShakeListener.stop();
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isForeground = false;
			mShakeListener.stop();
			finish();
		}
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			isForeground = false;
			mShakeListener.stop();
			finish();
		}
		return false;
	}

}

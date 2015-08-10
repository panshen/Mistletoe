package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.v.AlwaysMarqueeTextView;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class RespondRemindDialogActivity extends Activity {
	private SnaImageViewV2 head;
	private AlwaysMarqueeTextView content;
	private User_Bean user_bean;
	private Context context;
	SoundPool soundPool;
	int sound_id;
	ArrayList<Integer> sounds = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.respond_remind_dialog);
		context = this;
		initUnlockSound2();
		initView();
		setlistener();

	}

	private void initUnlockSound2() {
		soundPool = new SoundPool(5, AudioManager.STREAM_SYSTEM, 1);
		sound_id = soundPool.load(context, R.raw.coin, 1);
	}

	/*
	 * @ soundID 音效池中的ID
	 * 
	 * @ leftVolume 左声道 ：0.0-1.0
	 * 
	 * @ rightVolume 右声道 ：0.0-1.0
	 * 
	 * @ priority 优先权 ： 0 表示最低权限；
	 * 
	 * @ loop : 循环 0 == 不循环 -1==永远循环 other==循环指定次数
	 * 
	 * @ rate 比率 ：playback 录音重放 rate ： 0.5-2.0
	 */
	private void playUnlockSound2() {
		soundPool.stop(sound_id);
		sound_id = soundPool.play(sound_id, 1.0f, 1.0f, 1, 100, 1.0f);
	}

	private void setlistener() {
		head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				soundPool.stop(sound_id);
				RespondRemindDialogActivity.this.finish();
			}
		});
	}

	private void initView() {
		head = (SnaImageViewV2) findViewById(R.id.respond_remind_dialog_snaImage_head);
		content = (AlwaysMarqueeTextView) findViewById(R.id.respond_remind_dialog_textview);
		String msg = getIntent().getStringExtra("content");
		Integer user_id = getIntent().getIntExtra("user_id", -1);
		HelperManager helperManager = new HelperManager();
		Helpers_Sub_Bean helper = helperManager.ReadHelperFromDBById(context, user_id);
		if (helper.getHelper_photo() == null) {
			helper.setHelper_photo("" + user_bean.getAvatar_file_id());
		} else {
			if (helper.getHelper_photo().length() <= 0) {
				helper.setHelper_photo("" + user_bean.getAvatar_file_id());
			}
		}
		head.setImageForShow(helper.getHelper_photo(), SnaBitmap.NET_NORMAL);
		content.setText(msg);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				playUnlockSound2();
				ObjectAnimator animator = tada(head);
				animator.setRepeatCount(ValueAnimator.INFINITE);
				animator.start();
			}
		}, 2000);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			soundPool.stop(sound_id);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		soundPool.release();
	}

	public static ObjectAnimator tada(View view) {
		return tada(view, 1f);
	}

	public static ObjectAnimator tada(View view, float shakeFactor) {

		PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(View.SCALE_X, Keyframe.ofFloat(0f, 1f), Keyframe.ofFloat(.1f, .9f), Keyframe.ofFloat(.2f, .9f), Keyframe.ofFloat(.3f, 1.1f),
				Keyframe.ofFloat(.4f, 1.1f), Keyframe.ofFloat(.5f, 1.1f), Keyframe.ofFloat(.6f, 1.1f), Keyframe.ofFloat(.7f, 1.1f), Keyframe.ofFloat(.8f, 1.1f), Keyframe.ofFloat(.9f, 1.1f),
				Keyframe.ofFloat(1f, 1f));

		PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y, Keyframe.ofFloat(0f, 1f), Keyframe.ofFloat(.1f, .9f), Keyframe.ofFloat(.2f, .9f), Keyframe.ofFloat(.3f, 1.1f),
				Keyframe.ofFloat(.4f, 1.1f), Keyframe.ofFloat(.5f, 1.1f), Keyframe.ofFloat(.6f, 1.1f), Keyframe.ofFloat(.7f, 1.1f), Keyframe.ofFloat(.8f, 1.1f), Keyframe.ofFloat(.9f, 1.1f),
				Keyframe.ofFloat(1f, 1f));

		PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION, Keyframe.ofFloat(0f, 0f), Keyframe.ofFloat(.1f, -3f * shakeFactor), Keyframe.ofFloat(.2f, -3f * shakeFactor),
				Keyframe.ofFloat(.3f, 3f * shakeFactor), Keyframe.ofFloat(.4f, -3f * shakeFactor), Keyframe.ofFloat(.5f, 3f * shakeFactor), Keyframe.ofFloat(.6f, -3f * shakeFactor),
				Keyframe.ofFloat(.7f, 3f * shakeFactor), Keyframe.ofFloat(.8f, -3f * shakeFactor), Keyframe.ofFloat(.9f, 3f * shakeFactor), Keyframe.ofFloat(1f, 0));

		return ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY, pvhRotate).setDuration(1000);
	}
}

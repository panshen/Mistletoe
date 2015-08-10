package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.net.request.Get_Public_Helper_Desc_NetRequest;
import com.helper.mistletoe.m.pojo.Public_Bean;
import com.helper.mistletoe.roll.ShakeListener;
import com.helper.mistletoe.roll.ShakeListener.OnShakeListener;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.ThreadPoolUtils_Net;
import com.helper.mistletoe.util.Tool_Utils;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class PublicDetailsActivity extends PrivateBasicActivity {
	private TextView momeName, readygoooName, sign, target_cout, helper_count, view_cout;
	private SnaImageViewV2 photo;
	private RelativeLayout back;
	private ProgressBar progressBa;
	private ImageButton addHelper;
	private ImageView hammer;
	private AnimationDrawable animationDrawable = null;
	private ShakeListener mShakeListener;
	private String shakingDialog = "inexistence";
	private Public_Bean public_Bean;
	private int Back_resultCode = -1;
	String picBitmip;
	MyRunnable myRunnable;
	Handler handler;
	SoundPool soundPool;
	int sound_id;
	Integer ID;

	private Context context;
	private GetPublicDescByIdTask getPublicDescByIdTask;
	private Get_Public_Helper_Desc_NetRequest get_Public_Helper_Desc_NetRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.public_details);
		context = this;
		public_Bean = getIntent().getParcelableExtra("public_Bean");
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mShakeListener.start();
		initUnlockSound2();
		setData();
		setlistener();
	}

	private void initUnlockSound2() {
		soundPool = new SoundPool(5, AudioManager.STREAM_SYSTEM, 1);
		sound_id = soundPool.load(context, R.raw.coin, 1);
	}

	private void playUnlockSound2() {
		soundPool.play(sound_id, 1.0f, 1.0f, 1, 0, 1.0f);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mShakeListener.stop();
	}

	private void setData() {
		disPlay();
		getPublicDescByIdTask = new GetPublicDescByIdTask();
		getPublicDescByIdTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, public_Bean.getHelper_id());
	}

	public class GetPublicDescByIdTask extends AsyncTask<Integer, Integer, Public_Bean> {
		private Integer user_id;

		@Override
		protected Public_Bean doInBackground(Integer... params) {

			Public_Bean public_service = new Public_Bean();
			user_id = params[0];
			try {
				get_Public_Helper_Desc_NetRequest = new Get_Public_Helper_Desc_NetRequest(context);
				public_service = get_Public_Helper_Desc_NetRequest.doGetPublicDesc(user_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return public_service;
		}

		@Override
		protected void onPostExecute(Public_Bean result) {
			super.onPostExecute(result);
			if (result != null) {
				target_cout.setText(result.getHelper_target_count().toString());
				helper_count.setText(result.getHelper_count().toString());
				view_cout.setText(result.getHelper_view_count().toString());
			}
		}
	}

	// 数据展示
	private void disPlay() {
		// TODO Auto-generated method stub
		momeName.setText(public_Bean.getHelper_memo_name());
		readygoooName.setText(public_Bean.getHelper_readygo_name());
		sign.setText(public_Bean.getHelper_sign());
		// target_cout.setText(public_Bean.getHelper_target_count().toString());
		// helper_count.setText(public_Bean.getHelper_count().toString());
		try {
			photo.setImageForShow(public_Bean.getHelper_photo(), SnaBitmap.NET_SMALL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void dialog() {
		shakingDialog = "existing";
		AlertDialog.Builder builder = new Builder(PublicDetailsActivity.this);
		builder.setMessage("亲，你要duang" + public_Bean.getHelper_name() + "吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				shakingDialog = "inexistence";
				duang();
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				shakingDialog = "inexistence";
			}
		});

		builder.create().show();
	}

	private void setlistener() {
		mShakeListener.setOnShakeListener(new OnShakeListener() {

			@Override
			public void onShake() {
				// TODO Auto-generated method stub
				if (mShakeListener.shakingStaus.equals("Stop")) {
					if (shakingDialog.equals("inexistence")) {
						dialog();
					}
				}
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				setResult(Back_resultCode, intent);
				PublicDetailsActivity.this.finish();
			}
		});
		photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_imager = new Intent(context, ImageShower.class);
				intent_imager.putExtra("helper_photo", public_Bean.getHelper_photo());
				startActivityForResult(intent_imager, 3);
			}
		});
		addHelper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ID == 2) {// 帮手, 创建目标
					ArrayList<Integer> seleted_helperId = new ArrayList<Integer>();
					if (!seleted_helperId.contains(public_Bean.getHelper_id()) && public_Bean.getHelper_id() != -1) {
						seleted_helperId.add(public_Bean.getHelper_id());
					}
					Intent intent_target = new Intent(context, Target_Create_Activity.class);
					intent_target.putIntegerArrayListExtra("memberId", seleted_helperId);
					context.startActivity(intent_target);
				} else if (ID == 3) {// 非帮手，加帮手
					// 先判断是否需要验证
					if (public_Bean.getHelper_verification() == 0) {
						// 发送
						Instruction_Utils.sendInstrustion(context, Instruction_Utils.INVITE_HELPER, public_Bean);
					} else {
						Intent intent_add = new Intent(context, AddHelperDialogActivity.class);
						intent_add.putExtra("helper", public_Bean);
						context.startActivity(intent_add);
					}
				}
			}
		});
	}

	private Integer isWho(Integer helper_id) {
		Integer ID = -1;
		HelperManager helperManager = new HelperManager();
		ID = helperManager.Identification(getApplicationContext(), helper_id);
		return ID;
	}

	private void initView() {
		momeName = (TextView) findViewById(R.id.public_detail_textView_momeName);
		readygoooName = (TextView) findViewById(R.id.public_detail_textView_readygoooName);
		sign = (TextView) findViewById(R.id.public_detail_textView_sign);
		target_cout = (TextView) findViewById(R.id.public_detail_textView_targetCount);
		helper_count = (TextView) findViewById(R.id.public_detail_textView_helperCount);
		view_cout = (TextView) findViewById(R.id.public_detail_textView_viewCount);
		back = (RelativeLayout) findViewById(R.id.public_detail_button_back);
		progressBa = (ProgressBar) findViewById(R.id.public_detail_progressb);
		photo = (SnaImageViewV2) findViewById(R.id.public_detail_snaImage_head);
		addHelper = (ImageButton) findViewById(R.id.public_detail_imageButton_addHelper);
		progressBa.setVisibility(ProgressBar.GONE);
		mShakeListener = new ShakeListener(getApplicationContext());
		hammer = (ImageView) findViewById(R.id.public_detail_imageView_hammer);
		hammer.setBackgroundResource(R.drawable.animation_white);
		ID = isWho(public_Bean.getHelper_id());
		if (ID == 1) {// 自己
			addHelper.setVisibility(ImageButton.GONE);
		} else if (ID == 2) {// 帮手
			addHelper.setVisibility(ImageButton.VISIBLE);
			addHelper.setBackgroundResource(R.drawable.create_target_targetfragment);
		} else if (ID == 3) {// 非帮手
			addHelper.setVisibility(ImageButton.VISIBLE);
			addHelper.setBackgroundResource(R.drawable.add_helper_helperfragment);
		}
		handler = new Handler();
		myRunnable = new MyRunnable();
		/** 通过ImageView对象拿到背景显示的AnimationDrawable **/
		animationDrawable = (AnimationDrawable) hammer.getBackground();
		/** 开始播放动画 **/
		hammer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				/** 播放动画 **/
				duang();
			}
		});
	}

	private void duang() {
		if (!animationDrawable.isRunning()) {
			animationDrawable.start();
			// TODO Auto-generated method stub
			if (ID == 1) {
				Tool_Utils.showInfo(context, "你可看清楚了！！这是你自己！！！！");
			} else {
				// 先到数据库中查找，判断是否好友
				Instruction_Utils.sendInstrustion(context, Instruction_Utils.ACTION_DUANG, public_Bean.getHelper_id());
			}
			Animation animation = AnimationUtils.loadAnimation(context, R.anim.myanim);
			photo.startAnimation(animation);
			playUnlockSound2();
		}
		handler.postDelayed(myRunnable, 1000);
	}

	class MyRunnable implements Runnable {

		@Override
		public void run() {
			animationDrawable.stop();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void dealWithRadio(Intent intent) {
		super.dealWithRadio(intent);
		String action = intent.getAction();
		if (action.equals(MessageConstants.REFRESH_HELPER)) {
			setData();
		}
	}
}

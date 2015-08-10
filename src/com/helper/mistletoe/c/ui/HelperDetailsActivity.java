package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.roll.ShakeListener;
import com.helper.mistletoe.roll.ShakeListener.OnShakeListener;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.ThreadPoolUtils_db;
import com.helper.mistletoe.util.Tool_Utils;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class HelperDetailsActivity extends PrivateBasicActivity {
	private TextView momeName, readygoooName, personalComment, target_cout,
			helper_count, cion_count;
	private SnaImageViewV2 photo, head;
	private RelativeLayout back, menu;
	private LinearLayout head_a;
	private ImageButton createTarget;
	private ImageView hammer;
	private AnimationDrawable animationDrawable = null;

	private ShakeListener mShakeListener;
	private String shakingDialog = "inexistence";
	private Helpers_Sub_Bean helper;
	private ReadHelperByIdFromDBTask readHelperByIdFromDBTask;
	String picBitmip;
	MyRunnable myRunnable;
	Handler handler;
	SoundPool soundPool;
	int sound_id;
	Integer ID;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.helper_details);
		context = this;
		helper = getIntent().getParcelableExtra("helper");
		// 发送同步单个Helper指令
		Instruction_Utils.sendInstrustion(context.getApplicationContext(),
				Instruction_Utils.SYNCHRONOUS_HELPER_BY_ID,
				helper.getHelper_id());
		initView();
		CreateMenu(menu);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mShakeListener.start();
		initUnlockSound2();
		disPlay(helper);
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
		readHelperByIdFromDBTask = new ReadHelperByIdFromDBTask();
		readHelperByIdFromDBTask.executeOnExecutor(
				ThreadPoolUtils_db.threadPool, helper.getHelper_id());
	}

	private Integer isWho(Integer helper_id) {
		Integer ID = -1;
		HelperManager helperManager = new HelperManager();
		ID = helperManager.Identification(getApplicationContext(), helper_id);
		return ID;
	}

	private class ReadHelperByIdFromDBTask extends
			AsyncTask<Integer, Integer, Helpers_Sub_Bean> {

		@Override
		protected Helpers_Sub_Bean doInBackground(Integer... params) {
			Integer helper_id = params[0];
			Helpers_Sub_Bean Helper_db = new Helpers_Sub_Bean();
			HelperManager helperManager = new HelperManager();
			Helper_db = helperManager.ReadHelperFromDBById(context, helper_id);

			return Helper_db;
		}

		@Override
		protected void onPostExecute(Helpers_Sub_Bean result) {
			super.onPostExecute(result);
			if (result != null) {
				disPlay(result);
			}
		}
	}

	// 数据展示
	private void disPlay(Helpers_Sub_Bean helper) {
		momeName.setText(helper.getHelper_memo_name());
		readygoooName.setText(helper.getHelper_readygo_name());
		personalComment.setText(helper.getHelper_sign());
		target_cout.setText(helper.getHelper_target_count().toString());
		helper_count.setText(helper.getHelper_count().toString());
		cion_count.setText(helper.getHelper_coin_count().toString());
		photo.setImageForShow(helper.getHelper_photo(), SnaBitmap.NET_SMALL);
		head.setImageForShow(helper.getHelper_photo(), SnaBitmap.NET_SMALL);
		//applyBlur();
	}

	private void applyBlur() {
		head.getViewTreeObserver().addOnPreDrawListener(
				new ViewTreeObserver.OnPreDrawListener() {

					@Override
					public boolean onPreDraw() {
						head.getViewTreeObserver()
								.removeOnPreDrawListener(this);
						head.buildDrawingCache();
						Bitmap bmp = head.getDrawingCache();
						blur(bmp, head_a);
						return true;
					}
				});
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	private void blur(Bitmap bkg, View view) {
		try {
			float radius = 25; // 0<radius<=25

			Bitmap overlay = Bitmap.createBitmap(
					(int) (view.getMeasuredWidth()),
					(int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(overlay);
			canvas.translate(-view.getLeft(), -view.getTop());
			canvas.drawBitmap(bkg, 0, 0, null);
			RenderScript rs = RenderScript.create(HelperDetailsActivity.this);
			Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
			ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs,
					overlayAlloc.getElement());
			blur.setInput(overlayAlloc);
			blur.setRadius(radius);
			blur.forEach(overlayAlloc);
			overlayAlloc.copyTo(overlay);
			view.setBackground(new BitmapDrawable(getResources(), overlay));
			rs.destroy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void dialog() {
		shakingDialog = "existing";
		AlertDialog.Builder builder = new Builder(HelperDetailsActivity.this);
		builder.setMessage("亲，你要duang" + helper.getHelper_name() + "吗？");
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
				if (ShakeListener.shakingStaus.equals("Stop")) {
					if (shakingDialog.equals("inexistence")) {
						dialog();
					}
				}
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HelperDetailsActivity.this.finish();
			}
		});
		photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_imager = new Intent(context, ImageShower.class);
				intent_imager.putExtra("helper_photo", helper.getHelper_photo());
				startActivityForResult(intent_imager, 3);
			}
		});
		createTarget.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ID == 2) {// 帮手, 创建目标
					ArrayList<Integer> seleted_helperId = new ArrayList<Integer>();
					if (!seleted_helperId.contains(helper.getHelper_id())
							&& helper.getHelper_id() != -1) {
						seleted_helperId.add(helper.getHelper_id());
					}
					Intent intent_target = new Intent(context,
							Target_Create_Activity.class);
					intent_target.putIntegerArrayListExtra("memberId",
							seleted_helperId);
					context.startActivity(intent_target);
				} else if (ID == 3) {// 非帮手，加帮手
					// 先判断是否需要验证
					if (helper.getHelper_verification() == 0) {
						// 发送
						Instruction_Utils.sendInstrustion(context,
								Instruction_Utils.INVITE_HELPER, helper);
					} else {
						Intent intent_add = new Intent(context,
								AddHelperDialogActivity.class);
						intent_add.putExtra("helper", helper);
						context.startActivity(intent_add);
					}
				}
			}
		});
	}

	private void CreateMenu(RelativeLayout menu) {
		try {
			final PopupMenu popup = new PopupMenu(context, menu);
			popup.getMenuInflater().inflate(R.menu.helper_detail_menu,
					popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {
					switch (item.getItemId()) {
					case R.id.sign:// 修改备注
						Intent intent_update = new Intent(context,
								UpdateMemoNameDialogActivity.class);
						intent_update.putExtra("helper", helper);
						context.startActivity(intent_update);
						break;
					case R.id.toBlack:// 加入到黑名单
						// 发送拉黑Helper的指令
						Instruction_Utils.sendInstrustion(
								context.getApplicationContext(),
								Instruction_Utils.PULL_BACK,
								helper.getHelper_id());
						break;

					default:
						break;
					}
					return true;
				}
			});
			menu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					popup.show();
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void initView() {
		momeName = (TextView) findViewById(R.id.helper_detail_textView_momeName);
		readygoooName = (TextView) findViewById(R.id.helper_detail_textView_readygoooName);
		personalComment = (TextView) findViewById(R.id.helper_detail_textView_sign);
		target_cout = (TextView) findViewById(R.id.helper_detail_textView_targetCount);
		helper_count = (TextView) findViewById(R.id.helper_detail_textView_helperCount);
		cion_count = (TextView) findViewById(R.id.helper_detail_textView_coinCount);
		back = (RelativeLayout) findViewById(R.id.helper_detail_button_back);
		menu = (RelativeLayout) findViewById(R.id.helper_detail_imageButton_menu);
		head = (SnaImageViewV2) findViewById(R.id.helper_detail_head);
		head_a = (LinearLayout) findViewById(R.id.helper_detail_linearLayout_head);
		photo = (SnaImageViewV2) findViewById(R.id.helper_detail_snaImage_head);
		createTarget = (ImageButton) findViewById(R.id.helper_detail_imageButton_createTarget);
		mShakeListener = new ShakeListener(getApplicationContext());
		hammer = (ImageView) findViewById(R.id.helper_detail_imageView_hammer);
		ID = isWho(helper.getHelper_id());
		if (ID == 1) {// 自己
			createTarget.setVisibility(ImageButton.GONE);
		} else if (ID == 2) {// 帮手
			createTarget.setVisibility(ImageButton.VISIBLE);
			createTarget
					.setBackgroundResource(R.drawable.create_target_targetfragment);
		} else if (ID == 3) {// 非帮手
			createTarget.setVisibility(ImageButton.VISIBLE);
			createTarget
					.setBackgroundResource(R.drawable.add_helper_helperfragment);
		}
		try {
			hammer.setBackgroundResource(R.drawable.animation_white);
		} catch (OutOfMemoryError e) {
			Tool_Utils.showInfo(context, "图片太大，让我休息一会！");
			hammer.setBackgroundResource(0);// 别忘了把背景设为null，避免onDraw刷新背景时候出现used
											// arecycled bitmap错误
			BitmapDrawable bd = (BitmapDrawable) hammer.getBackground();
			if (bd != null) {
				bd.setCallback(null);
				bd.getBitmap().recycle();
			}
			hammer.setBackgroundResource(R.drawable.animation_white);
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
			if (ID == 1) {
				Tool_Utils.showInfo(context, "你可看清楚了！！这是你自己！！！！");
			} else {
				// 先到数据库中查找，判断是否好友
				Instruction_Utils.sendInstrustion(
						context.getApplicationContext(),
						Instruction_Utils.ACTION_DUANG, helper.getHelper_id());
			}
			// 发送duang Helper的指令
			// Instruction_Utils.sendInstrustion(context, "ActionDuang",
			// helper.getHelper_id());
			Animation animation = AnimationUtils.loadAnimation(context,
					R.anim.myanim);
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
		hammer.setBackgroundResource(0);
		hammer = null;
		System.gc();
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

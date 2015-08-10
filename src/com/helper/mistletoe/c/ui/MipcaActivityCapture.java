package com.helper.mistletoe.c.ui;

import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.helper.mistletoe.R;
import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.net.request.Get_Helper_By_Id_NetRequest;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.ThreadPoolUtils_Net;
import com.helper.mistletoe.util.Tool_Utils;
import com.helper.mistletoe.zxing.camera.CameraManager;
import com.helper.mistletoe.zxing.decoding.CaptureActivityHandler;
import com.helper.mistletoe.zxing.decoding.InactivityTimer;
import com.helper.mistletoe.zxing.view.ViewfinderView;

public class MipcaActivityCapture extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private Context context;
	// private GetHelperByIdTask getHelperByIdTask;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private TextView name_tv, phoneNumber_tv, email_tv, alert_tv, title_tv, company_tv, address_tv;
	private LinearLayout photo, lamp, myCode, name_Llyt, phoneNumber_Llyt, email_Llyt, title_Llyt, company_Llyt, address_Llyt;
	private Button cancel, ok, back;
	private ImageView head_pic;
	private RelativeLayout captrue, business_card;
	private SurfaceView surfaceView;
	private Helpers_Sub_Bean helper;
	private String number;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_capture);
		context = this;
		initView();
		setlistener();
	}

	private void setlistener() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MipcaActivityCapture.this.finish();
			}
		});
		// cancel.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// captrue.setVisibility(RelativeLayout.VISIBLE);
		// business_card.setVisibility(RelativeLayout.GONE);
		// if (handler != null) {
		// handler.quitSynchronously();
		// handler = null;
		// }
		// inactivityTimer = new InactivityTimer(MipcaActivityCapture.this);
		// onResume();
		// }
		// });
		// ok.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// boolean a = alert_tv.getText().equals("确认信息，点击确定，添加成帮手");
		// if (a) {
		// // ContactUtil.i
		// // Tool_Utils.insert(context, helper);
		// } else {
		// // Tool_Utils.update(MipcaActivityCapture.this,
		// // user_past,number);
		// }
		//
		// MipcaActivityCapture.this.finish();
		// }
		// });
		// photo.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Tool_Utils.showInfo(context, "此功能尚未开发，敬请期待！");
		// }
		// });
		// lamp.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Tool_Utils.showInfo(context, "此功能尚未开发，敬请期待！");
		// }
		// });
		// myCode.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent();
		// intent.setClass(MipcaActivityCapture.this,
		// PersonalActivity.class);
		// startActivityForResult(intent, 3);
		// MipcaActivityCapture.this.onDestroy();
		//
		// }
		// });

	}

	private void initView() {
		photo = (LinearLayout) findViewById(R.id.layout_main_end_photo);
		lamp = (LinearLayout) findViewById(R.id.layout_main_end_lamp);
		myCode = (LinearLayout) findViewById(R.id.layout_main_end_myCode);
		name_Llyt = (LinearLayout) findViewById(R.id.linearLayout_business_card_name);
		phoneNumber_Llyt = (LinearLayout) findViewById(R.id.linearLayout_business_card_phoneNumber);
		email_Llyt = (LinearLayout) findViewById(R.id.linearLayout_business_card_email);
		title_Llyt = (LinearLayout) findViewById(R.id.linearLayout_business_card_title);
		company_Llyt = (LinearLayout) findViewById(R.id.linearLayout_business_card_company);
		address_Llyt = (LinearLayout) findViewById(R.id.linearLayout_business_card_address);
		captrue = (RelativeLayout) findViewById(R.id.relativeLayout_capture);
		business_card = (RelativeLayout) findViewById(R.id.relativeLayout_business_card);
		business_card.setVisibility(RelativeLayout.GONE);
		name_tv = (TextView) findViewById(R.id.textView_business_card_name);
		phoneNumber_tv = (TextView) findViewById(R.id.textView_business_card_phoneNumber);
		email_tv = (TextView) findViewById(R.id.textView_business_card_email);
		title_tv = (TextView) findViewById(R.id.textView_business_card_title);
		company_tv = (TextView) findViewById(R.id.textView_business_card_company);
		address_tv = (TextView) findViewById(R.id.textView_business_card_address);
		alert_tv = (TextView) findViewById(R.id.textView_capture_context);
		head_pic = (ImageView) findViewById(R.id.imageView_business_card_head);
		cancel = (Button) findViewById(R.id.button_capture_cancel);
		ok = (Button) findViewById(R.id.button_capture_sure);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		back = (Button) findViewById(R.id.button_back);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		Log.v("saommiao", "ffß" + result.getText());
		// http://www.4helper.com/m/
		if (result.getText().contains("http://")) {
			String w = result.getText().substring(result.getText().indexOf("/") + 2, result.getText().lastIndexOf("/") - 2);
			if (w.equals("www.4helper.com")) {
				String s = result.getText().substring(result.getText().lastIndexOf("/") + 1);
				if (s.equals("")) {
					Toast.makeText(MipcaActivityCapture.this, "Scan failed!", Toast.LENGTH_SHORT).show();
				} else {
					business_card.setVisibility(RelativeLayout.VISIBLE);
					captrue.setVisibility(RelativeLayout.GONE);
					inactivityTimer.shutdown();
					Helpers_Sub_Bean helper_temp = new Helpers_Sub_Bean();
					User_Bean user_temp = new User_Bean();
					try {
						user_temp.readData(context);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					helper_temp.setHelper_id(Integer.valueOf(s));
					helper_temp.setHelper_msg(user_temp.getName() + "通过扫描二维码，加你好友！");
					// Toast.makeText(context, "5秒后可再次扫描",
					// Toast.LENGTH_SHORT).show();
					// 发送添加好友的指令
					Instruction_Utils.sendInstrustion(context.getApplicationContext(), Instruction_Utils.INVITE_HELPER, helper);

					// try {
					// Thread.sleep(5000);
					// } catch (InterruptedException e) {
					// e.printStackTrace();
					// }
					captrue.setVisibility(RelativeLayout.VISIBLE);
					business_card.setVisibility(RelativeLayout.GONE);
					if (handler != null) {
						handler.quitSynchronously();
						handler = null;
					}
					inactivityTimer = new InactivityTimer(MipcaActivityCapture.this);
					onResume();
					// getHelperByIdTask=new GetHelperByIdTask(context);
					// getHelperByIdTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool,Integer.valueOf(s));
				}
			} else {
				Intent intent = new Intent(MipcaActivityCapture.this, ZxingOtherActivity.class);
				intent.putExtra("url", w);
				startActivityForResult(intent, 5);
			}
		} else {

			Toast.makeText(context, "此类二维码不识别!", Toast.LENGTH_SHORT).show();
			// try {
			// Thread.sleep(5000);
			//
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			if (handler != null) {
				handler.quitSynchronously();
				handler = null;
			}
			inactivityTimer = new InactivityTimer(MipcaActivityCapture.this);
			onResume();
		}
	}

	//
	// public class GetHelperByIdTask extends
	// AsyncTask<Integer, Integer, Helpers_Sub_Bean> {
	//
	// private Context context;
	// private Get_Helper_By_Id_NetRequest netRequest;
	// private Integer helper_id;
	//
	// public GetHelperByIdTask(Context context) {
	// this.context = context;
	// }
	//
	// @Override
	// protected Helpers_Sub_Bean doInBackground(Integer... params) {
	// helper_id = params[0];
	// Helpers_Sub_Bean Helper_service = new Helpers_Sub_Bean();
	// try {
	// netRequest = new Get_Helper_By_Id_NetRequest(context);
	// Helper_service = netRequest.doGetHelperById(helper_id);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return Helper_service;
	// }
	//
	// @Override
	// protected void onPostExecute(Helpers_Sub_Bean result) {
	// super.onPostExecute(result);
	// if (result != null) {
	// Log.v("huoqudaode", "ss"+result.toString());
	// // disPlay(result);
	// }
	// }
	// }
	//
	// public void disPlay(Helpers_Sub_Bean helper) {
	// name_tv.setText(helper.getHelper_name());
	// phoneNumber_tv.setText(helper.getHelper_account());
	// if (helper.getHelper_email().equals("")) {
	// email_Llyt.setVisibility(LinearLayout.GONE);
	// } else {
	// email_Llyt.setVisibility(LinearLayout.VISIBLE);
	// email_tv.setText(helper.getHelper_email());
	// }
	// if (helper.getHelper_address().equals("")) {
	// address_Llyt.setVisibility(LinearLayout.GONE);
	// } else {
	// address_Llyt.setVisibility(LinearLayout.VISIBLE);
	// address_tv.setText(helper.getHelper_address());
	// }
	// if (helper.getHelper_company().equals("")) {
	// company_Llyt.setVisibility(LinearLayout.GONE);
	// } else {
	// company_Llyt.setVisibility(LinearLayout.VISIBLE);
	// company_tv.setText(helper.getHelper_company());
	// }
	// if (helper.getHelper_title().equals("")) {
	// title_Llyt.setVisibility(LinearLayout.GONE);
	// } else {
	// title_Llyt.setVisibility(LinearLayout.VISIBLE);
	// title_tv.setText(helper.getHelper_title());
	// }
	// ContentResolver resolver = MipcaActivityCapture.this
	// .getContentResolver();
	// Cursor cursor = resolver.query(
	// Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
	// Uri.encode(helper.getHelper_account())),
	// new String[] { PhoneLookup.NUMBER },
	// PhoneLookup.NORMALIZED_NUMBER + " like '%"
	// + helper.getHelper_account() + "'", null,
	// PhoneLookup.NUMBER);
	// if ((cursor != null) && (cursor.getCount() > 0)) {
	// cursor.moveToFirst();
	// number = cursor
	// .getString(cursor.getColumnIndex(PhoneLookup.NUMBER));
	// }
	// if (cursor.getCount() > 0) {
	// alert_tv.setText("您的通讯录已经存在这个联系人，点击确定，进行更新");
	// } else {
	// alert_tv.setText("确认信息，点击确定，添加成帮手");
	// }
	// cursor.close();
	// // getImageofHelper_Task = new GetImageofHelper_Task(
	// // MipcaActivityCapture.this, head_pic);
	// // try {
	// // user_past.setPicId(getImageofHelper_Task
	// // .executeOnExecutor(ThreadPoolUtils.threadPool,
	// // GetImageofHelper_Task.FLAG_BIG,
	// // user_past.getPhoneNumber(), "helper").get()
	// // .getFileName());
	// // } catch (InterruptedException e) {
	// // e.printStackTrace();
	// // } catch (ExecutionException e) {
	// // e.printStackTrace();
	// // }
	// }

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

}
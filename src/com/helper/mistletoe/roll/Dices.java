package com.helper.mistletoe.roll;

import java.util.Random;

import com.helper.mistletoe.MistletoeApplication;
import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.RollDiceActivity;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class Dices extends SurfaceView implements Callback, Runnable {
	public static int NUM = 6;
	public static int screenW;
	public static int screenH;
	public static int picWidth = 115;
	public static int picHeight = 148;

	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	private Move move;
	private Bitmap dice_result;
	private Result result;
	private Context context;
	public MediaPlayer mp;
	private Dice[] dice;
	private Random random = new Random();
	public static int shujunum;
	private ShakeListener mShakeListener;

	public Dices(Context context) {
		super(context);
		init(context);
	}

	public Dices(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void init(Context context) {
		this.context = context;
		dice = new Dice[Dices.NUM];
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(40);
		paint.setTextAlign(Align.CENTER);
		setFocusable(true);
		Dice.count = 0;
		shujunum = 0;
		mShakeListener = new ShakeListener(context);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		Dice.count = 0;
		screenW = this.getWidth();
		screenH = this.getHeight();
		flag = true;
		th = new Thread(this);
		th.start();
		if (mp != null) {
			mp.stop();
		}
	}

	@Override
	public void run() {
		th = Thread.currentThread();
		Dice.count = 0;
		init();
		move = new Move(dice);
		mp = MediaPlayer.create(context, R.raw.sound_dice);
		while (flag) {
			long start = System.currentTimeMillis();

			myRolling();
			move.start();
			if (null != mp) {
				if (!mp.isPlaying()) {
					mp.start();
				}
				long end = System.currentTimeMillis();
				try {
					if (end - start < 20) {
						Thread.sleep(20 - (end - start));
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				shake();
			}
		}
	}

	private void shuju() {
		// TODO Auto-generated method stub
		shujunum++;
		RollService service = new RollServiceImpl(context);
		ContentValues values = new ContentValues();
		int[] FaceValue = { -1, -1, -1, -1, -1, -1 };
		if (result != null) {
			int DicesNum = MistletoeApplication.getInstance().getDiceCount();
			for (int i = 0; i < DicesNum; i++) {
				FaceValue[i] = result.getFaceValue()[i];
			}
			values.put("firstDice", FaceValue[0]);
			values.put("secondDice", FaceValue[1]);
			values.put("thirdDice", FaceValue[2]);
			values.put("fourthDice", FaceValue[3]);
			values.put("fifthDice", FaceValue[4]);
			values.put("sixthDice", FaceValue[5]);
			values.put("diceTime", System.currentTimeMillis());
			service.addFaceValues(values);
		}
	}

	public void shake() {
		// TODO Auto-generated method stub
		for (int i = 0; i < Dices.NUM; i++) {
			if (!dice[i].isState()) {
				continue;
			}
			return;
		}
		if (mp.isPlaying()) {
			mp.stop();
		} else if (move.isOk()) {
			// mShakeListener.stop();
			while (!mp.isPlaying() && RollDiceActivity.isForeground) {
				mShakeListener.start();
			}
		}
	}

	public void update(int num) {
		// TODO Auto-generated method stub
		if (move != null && RollDiceActivity.isForeground) {
			shujunum = 0;
			move.init();
			mp = MediaPlayer.create(context, R.raw.sound_dice);
			move.start();
			mp.start();

		}

	}

	private void init() {
		Dice.count = 0;
		for (int i = 0; i < Dices.NUM; i++) {
			dice[i] = new Dice(screenW, screenH, BitmapFactory.decodeResource(getResources(), Face.face[i]));
			for (int j = 0; j < Dice.count; j++) {
				if (dice[i].isValid(dice[j]) || i == j) {
					continue;
				}
				i--;
				Dice.count--;
				break;
			}
		}
	}

	private void myRolling() {
		// TODO Auto-generated method stub
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {

				canvas.drawColor(0xff007800);// 背景色
				int DicesNum = MistletoeApplication.getInstance().getDiceCount();
				for (int i = 0; i < Dices.NUM; i++) {
					if (dice[i].isState()) {
						if (i < DicesNum) {
							rolling(canvas, i);
						}
					} else {
						if (i < DicesNum) {
							sitting(canvas, i);
						}
					}
				}
				if (!mp.isPlaying()) {
					result = new Result(dice);
					for (int i = 0; i < DicesNum; i++) {
						canvas.drawBitmap(createBitmap(i), i * dice_result.getWidth(), screenH - dice_result.getWidth(), paint);
					}
					if (shujunum == 0) {
						shuju();
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (canvas != null) {
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}

	private Bitmap createBitmap(int i) {
		if (dice_result != null && !dice_result.isRecycled()) {
			dice_result.recycle();
			dice_result = null;
		}
		dice_result = BitmapFactory.decodeResource(getResources(), Face.value[result.getFaceValue()[i]]);
		return dice_result;
	}

	public void rolling(Canvas canvas, int i) {
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), Face.rollFace[random.nextInt(9)]), dice[i].getLeft(), dice[i].getTop(), paint);
	}

	public void sitting(Canvas canvas, int i) {
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), Face.face[dice[i].getFace().getFaceValue()]), dice[i].getLeft(), dice[i].getTop(), paint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public ShakeListener getmShakeListener() {
		return mShakeListener;
	}

	public void setmShakeListener(ShakeListener mShakeListener) {
		this.mShakeListener = mShakeListener;
	}

}

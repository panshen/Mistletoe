package com.helper.mistletoe.c.ui;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.sp.User_sp;
import com.helper.mistletoe.util.sysconst.NetUrl_Const;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;
import com.helper.mistletoe.zxing.Untilly;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ZxingDialogActivity extends Activity {
	// 前景色
	int FOREGROUND_COLOR = 0xff000000;
	// 背景色
	int BACKGROUND_COLOR = 0xffffffff;
	// 图片宽度的一般
	private static final int IMAGE_HALFWIDTH = 40;
	// 插入到二维码里面的图片对象
	private Bitmap mIcon;
	// 需要插图图片的大小 这里设定为2 * IMAGE_HALFWIDTH
	int[] mPixels = new int[2 * IMAGE_HALFWIDTH * 2 * IMAGE_HALFWIDTH];
	private TextView name, comment;
	private SnaImageViewV2 head;
	private ImageView code;
	private User_Bean user_bean;
	private Context context;
	MyRunnable myRunnable;
	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zxing_dialog);
		context = this;
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setData();
	}

	private void setData() {
		try {
			user_bean = new User_sp(context).read();
			name.setText(user_bean.getName().toString());
			comment.setText(user_bean.getSign().toString());
			head.setImageForShow(user_bean.getAvatar_file_id(), SnaBitmap.NET_SMALL);
			creatCode(user_bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initView() {
		name = (TextView) findViewById(R.id.zxing_dialog_textView_name);
		comment = (TextView) findViewById(R.id.zxing_dialog_textView_comment);
		head = (SnaImageViewV2) findViewById(R.id.zxing_dialog_imageView_head);
		code = (ImageView) findViewById(R.id.zxing_dialog_imageView_code);
	}

	/**
	 * 生成二维码
	 */
	@SuppressLint("ShowToast")
	@SuppressWarnings("deprecation")
	public void creatCode(User_Bean user) {
		mIcon = null;
		try {
			DiskCache diskCache = ImageLoader.getInstance().getDiskCache();
			String url = head.getImageForShow().getAUILShowPath(getApplicationContext());
			File ima = diskCache.get(url);
			if (ima == null) {
				mIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
				handler = new Handler();
				myRunnable = new MyRunnable();
				handler.postDelayed(myRunnable, 3000);
			} else {
				mIcon = BitmapFactory.decodeFile(ima.getPath());
			}
			mIcon = BitmapFactory.decodeFile(ima.getPath());
			String transmit_info = NetUrl_Const.NET_TRANSMIT_INFO + user.getUser_id();
			if (mIcon == null) {
				mIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
			}
			Bitmap bitmapCode = createBitmap(new String(transmit_info.toString().getBytes(), "UTF-8"), mIcon);
			code.setImageBitmap(bitmapCode);
		} catch (UnsupportedEncodingException e) {
			Toast.makeText(this, "出错！", 3000).show();
		} catch (WriterException e) {
			Toast.makeText(this, "出错！", 3000).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class MyRunnable implements Runnable {

		@Override
		public void run() {
			setData();
		}

	}

	/**
	 * 生成二维码 中间插入小图片
	 * 
	 * @param str
	 *            内容
	 * @return Bitmap
	 * @throws WriterException
	 */
	public Bitmap createBitmap(String str, Bitmap icon) throws WriterException {
		// 缩放一个(2*IMAGE_HALFWIDTH)*(2*IMAGE_HALFWIDTH)的图片
		icon = Untilly.zoomBitmap(icon, IMAGE_HALFWIDTH);
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, 1);
		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 400, 400, hints);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 二维矩阵转为一维像素数组,也就是一直横着排了
		int halfW = width / 2;
		int halfH = height / 2;
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH && y > halfH - IMAGE_HALFWIDTH && y < halfH + IMAGE_HALFWIDTH) {
					pixels[y * width + x] = icon.getPixel(x - halfW + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
				} else {
					if (matrix.get(x, y)) {
						pixels[y * width + x] = FOREGROUND_COLOR;
					} else { // 无信息设置像素点为白色
						pixels[y * width + x] = BACKGROUND_COLOR;
					}
				}

			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		// 通过像素数组生成bitmap
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

		return bitmap;
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

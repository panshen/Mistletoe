package com.helper.mistletoe.c.ui;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.sysconst.NetUrl_Const;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;
import com.helper.mistletoe.zxing.Untilly;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PersonalActivity extends PrivateBasicActivity {
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

	private RelativeLayout back;
	private LinearLayout edit_info;
	private SnaImageViewV2 head;
	private ImageView img_code;
	private ImageButton edit;
	private TextView name, comment, mobile, email, company, title, address;
	private User_Bean user_bean;
	private Context context;
	private IntentFilter myIntentFilter;
	MyRunnable myRunnable;
	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal);
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setData();
		setlistener();
	}

	private void setlistener() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonalActivity.this, PersonalEditActivity.class);
				startActivityForResult(intent, 2);
			}
		});
		
		edit_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonalActivity.this, PortraitActivity.class);
				startActivityForResult(intent, 2);
			}
		});
	}

	private void setData() {
		try {
			user_bean = new User_sp(context).read();
			disPlay(user_bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void disPlay(User_Bean user_bean) {
		try {
			name.setText(user_bean.getName());
			comment.setText(user_bean.getSign());
			mobile.setText(user_bean.getMobile());
			email.setText(user_bean.getEmail());
			company.setText(user_bean.getCompany());
			title.setText(user_bean.getTitle());
			address.setText(user_bean.getAddress());
			head.setImageForShow(user_bean.getAvatar_file_id(), SnaBitmap.NET_SMALL);
			creatCode(user_bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initView() {
		context = this;
		back = (RelativeLayout) findViewById(R.id.personal_relativeLayout_back);
		edit_info = (LinearLayout) findViewById(R.id.personal_linearLayout_pic);
		head = (SnaImageViewV2) findViewById(R.id.personal_imageView_head);
		edit = (ImageButton) findViewById(R.id.personal_ImageButton_edit);
		name = (TextView) findViewById(R.id.personal_textView_name);
		comment = (TextView) findViewById(R.id.personal_textView_comment);
		mobile = (TextView) findViewById(R.id.personal_textView_mobile);
		email = (TextView) findViewById(R.id.personal_textView_email);
		company = (TextView) findViewById(R.id.personal_textView_company);
		title = (TextView) findViewById(R.id.personal_textView_title);
		address = (TextView) findViewById(R.id.personal_textView_address);
		img_code = (ImageView) findViewById(R.id.personal_imageView_code);
	}

	/**
	 * 生成二维码
	 */
	@SuppressLint("ShowToast")
	public void creatCode(User_Bean user) {
		mIcon = null;
		try {
			DiskCache diskCache = ImageLoader.getInstance().getDiskCache();
			String url = head.getImageForShow().getAUILShowPath(context);
			@SuppressWarnings("deprecation")
			File ima = diskCache.get(url);
			if (ima == null) {
				mIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
				handler = new Handler();
				myRunnable = new MyRunnable();
				handler.postDelayed(myRunnable, 3000);
			} else {
				mIcon = BitmapFactory.decodeFile(ima.getPath());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			if (mIcon == null) {
				mIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
			}
			String transmit_info = NetUrl_Const.NET_TRANSMIT_INFO + user.getUser_id();
			Bitmap bitmapCode = createBitmap(new String(transmit_info.toString().getBytes(), "UTF-8"), mIcon);
			img_code.setImageBitmap(bitmapCode);
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

	@Override
	public void dealWithRadio(Intent intent) {
		super.dealWithRadio(intent);
		String action = intent.getAction();
		if (action.equals(MessageConstants.REFRESH_USER)) {
			setData();
		}
	}
}

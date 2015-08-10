package com.helper.mistletoe.c.ui;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;

import com.helper.mistletoe.R;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class ImageShower extends Activity {
	private SnaImageViewV2 pic_big;
	private PhotoViewAttacher mAttacher;
	private String helper_photo;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.image_shower);
		helper_photo = getIntent().getExtras().getString("helper_photo");
		initView();
		setData();
	}

	public void setData() {
		try {
			pic_big.setImageForShow(helper_photo, SnaBitmap.NET_NORMAL);
			mAttacher.setZoomable(true);// 设置图片可以缩放
			mAttacher.setMinimumScale(0.3F);// 设置最小缩放比例
			mAttacher.setMaximumScale(5.0F);// 设置最大缩放比例
			mAttacher.setScale(1.0F);
			mAttacher.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initView() {
		pic_big = (SnaImageViewV2) findViewById(R.id.imageView_pic_big);
		mAttacher = new PhotoViewAttacher(pic_big);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Intent intent = new Intent();
		setResult(1, intent);
		onDestroy();
		finish();
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			setResult(1, intent);
			onDestroy();
			finish();
		}
		return true;
	}
}

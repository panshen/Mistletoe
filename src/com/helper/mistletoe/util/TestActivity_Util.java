package com.helper.mistletoe.util;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.base.Base_Activity;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.SnaImageView;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class TestActivity_Util extends Base_Activity {
	private SnaImageView vImageView;
	private Button vStartShow;

	private CountDownTimer mTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.test_layout);
			initView();
			setListener();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void initView() {
		try {
			vImageView = (SnaImageViewV2) findViewById(R.id.test_MainImage);
			vStartShow = (Button) findViewById(R.id.test_StartShowImage);

			mTimer = new CountDownTimer(100 * 1000, 1 * 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
					try {
						vImageView.setImageForShow(
								70 + ((int) (millisUntilFinished / 1000)),
								SnaBitmap.NET_MIDDLE);
						vStartShow.setText("正在刷新（" + millisUntilFinished / 1000
								+ "）");
						vStartShow.setClickable(false);
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}

				@Override
				public void onFinish() {
					try {
						vStartShow.setText("开始显示");
						vStartShow.setClickable(true);
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			};

			vImageView.setDefaultImageForShow(R.drawable.about);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setListener() {
		try {
			vStartShow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						mTimer.start();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setData() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}
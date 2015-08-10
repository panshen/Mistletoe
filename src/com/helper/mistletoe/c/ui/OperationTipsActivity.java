package com.helper.mistletoe.c.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.base.Base_Activity;
import com.helper.mistletoe.m.work.base.inter.WorkCallBack_Mode;
import com.helper.mistletoe.m.work.ui.GetUser_Main_Mode;
import com.helper.mistletoe.util.ExceptionHandle;

public class OperationTipsActivity extends Base_Activity {
	private ImageView vTipImage;
	private TextView vTipText;
	private GetUser_Main_Mode work_GetUser;
	private int mShowImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.operation_tips_layout);
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
	protected void onPause() {
		super.onPause();
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setData() {
		try {
			vTipImage.setImageResource(mShowImage);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView() {
		try {
			vTipImage = (ImageView) findViewById(R.id.opti_tipImage);
			vTipText = (TextView) findViewById(R.id.opti_tipText);
			work_GetUser = null;
			mShowImage = -1;
			getDataFromBundle();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setListener() {
		try {
			vTipImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						finish();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vTipText.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent tIntent = new Intent();
						tIntent.putExtra("neverTip", (boolean) true);
						setResult(772 + 0, tIntent);
						finish();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void getDataFromBundle() {
		try {
			Bundle bundle = getIntent().getExtras();
			if (bundle == null) {
				bundle = new Bundle();
			}

			if (bundle.containsKey("imageResources")) {
				mShowImage = bundle.getInt("imageResources", -1);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
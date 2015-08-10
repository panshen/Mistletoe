package com.helper.mistletoe.v.welcome;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.util.ExceptionHandle;

public class WelcomePage extends LinearLayout {
	private ViewPager vImagePager;
	private ProgressDot vImageProgress;
	private TextView vStartWork;
	private LinearLayout vSkipWork;

	private Context context;
	private LayoutInflater inflater;
	private ImagePageAdapter adapter;
	private OnWelcomePageOK onWelcomePageOK;

	public WelcomePage(Context context, AttributeSet attrs) {
		super(context, attrs);
		try {
			this.context = context;
			this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.inflater.inflate(R.layout.welcompage_layout, this);
			setView();
			setData();
			setListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setView() throws Exception {
		try {
			vImagePager = (ViewPager) findViewById(R.id.zWelcomeImage);
			vImageProgress = (ProgressDot) findViewById(R.id.zWelcomeImageProgress);
			vStartWork = (TextView) findViewById(R.id.zStartWorkInLuoPan);
			vSkipWork = (LinearLayout) findViewById(R.id.zSkipWelcomePage);

			adapter = null;
			this.onWelcomePageOK = null;
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void setData() throws Exception {
		try {
			updateShow(0);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void setListener() throws Exception {
		vImagePager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				try {
					updateShow(arg0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		vSkipWork.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					onWelcomePageOK.onPageOk();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		vStartWork.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					onWelcomePageOK.onPageOk();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setOnWelcomePageOKListener(OnWelcomePageOK onWelcomePageOK) throws Exception {
		try {
			this.onWelcomePageOK = onWelcomePageOK;
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void updateShow(int pageNumber) throws Exception {
		try {
			if (adapter == null) {
				ArrayList<String> data = new ArrayList<String>();
				{
					data.add("drawable://" + R.drawable.welcompage_welcome1);
					data.add("drawable://" + R.drawable.welcompage_welcome2);
					data.add("drawable://" + R.drawable.welcompage_welcome3);
				}
				adapter = new ImagePageAdapter(context, data);
				vImagePager.setAdapter(adapter);
			}

			vImageProgress.selectDot(pageNumber);

			if (pageNumber == 2) {
				vStartWork.setVisibility(TextView.VISIBLE);
			} else {
				vStartWork.setVisibility(TextView.INVISIBLE);
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

}
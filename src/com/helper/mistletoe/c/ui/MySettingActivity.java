package com.helper.mistletoe.c.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.CustomInfo;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;

public class MySettingActivity extends PrivateBasicActivity implements OnCheckedChangeListener {
	private TextView current_version;
	// private TextView dataSize;
	// private RelativeLayout memory;
	private RelativeLayout back, device, version, feedBack, clause, about,introduce;
	private Switch msg, verification, public_helper;
	private Context context;
	private User_Bean user;
	private UpdateManager mUpdateManager; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_mysetting);
			initView();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		setData();
		setListener();

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	// 数据展示
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressLint("NewApi")
	private void setData() {
		try {
			user.readData(context);
			if (user.getLoc_AcceptPush() == 0) {// 是否接收推送消息,1需要，0不需要
				msg.setChecked(false);
				msg.setThumbDrawable(getResources().getDrawable(R.drawable.switchback));
			} else {// 默认为需要
				msg.setThumbDrawable(getResources().getDrawable(R.drawable.switchbackselected));
				msg.setChecked(true);
			}
			
			if (user.getLoc_AddHelperVerification() == 1) {// 加帮手是否需要验证,1需要，0不需要
				verification.setThumbDrawable(getResources().getDrawable(R.drawable.switchbackselected));
				verification.setChecked(true);
			} else {// 默认为不需要
				verification.setThumbDrawable(getResources().getDrawable(R.drawable.switchback));
				verification.setChecked(false);
			}
			
			if (user.getLoc_OpenMe()) {// 自荐为公众帮手,true为是，false为不是
				public_helper.setThumbDrawable(getResources().getDrawable(R.drawable.switchbackselected));
				public_helper.setChecked(true);
			} else {// 默认为不是
				public_helper.setThumbDrawable(getResources().getDrawable(R.drawable.switchback));
				public_helper.setChecked(false);
			}
			// dataSize.setText(user.get);
			current_version.setText("当前版本:" + CustomInfo.getApp_version(context).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setListener() {
		// 返回上一界面
		back.setOnClickListener(this);
		// 设备管理
		device.setOnClickListener(this);
		// 检查更新
		version.setOnClickListener(this);
		// 使用介绍
		introduce.setOnClickListener(this);
		// 缓存
		// memory.setOnClickListener(this);
		// 意见反馈
		feedBack.setOnClickListener(this);
		// 隐私条款
		clause.setOnClickListener(this);
		// 关于
		about.setOnClickListener(this);
		// 接受消息提醒
		msg.setOnCheckedChangeListener(this);
		// 添加好友验证
		verification.setOnCheckedChangeListener(this);
		// 公共帮手
		public_helper.setOnCheckedChangeListener(this);
	}

	private void initView() {
		context = this;
		user = new User_Bean();
		// dataSize = (TextView)
		// findViewById(R.id.activity_mysetting_textview_datasize);
		current_version = (TextView) findViewById(R.id.activity_mysetting_textView_version);
		back = (RelativeLayout) findViewById(R.id.activity_mysetting_relativeLayout_back);
		device = (RelativeLayout) findViewById(R.id.activity_mysetting_relativeLayout_device);
		version = (RelativeLayout) findViewById(R.id.activity_mysetting_relativeLayout_version);
		introduce = (RelativeLayout) findViewById(R.id.activity_mysetting_relativeLayout_introduce);
		// memory = (RelativeLayout)
		// findViewById(R.id.activity_mysetting_relativeLayout_memory);
		feedBack = (RelativeLayout) findViewById(R.id.activity_mysetting_relativeLayout_feedBack);
		clause = (RelativeLayout) findViewById(R.id.activity_mysetting_relativeLayout_clause);
		about = (RelativeLayout) findViewById(R.id.activity_mysetting_relativeLayout_about);
		about.setOnClickListener(this);
		msg = (Switch) findViewById(R.id.activity_mysetting_switch_accept_push_msg);
		verification = (Switch) findViewById(R.id.activity_mysetting_switch_helper_verification);
		public_helper = (Switch) findViewById(R.id.activity_mysetting_switch_public_helper);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			setResult(1, intent);
			finish();
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.activity_mysetting_relativeLayout_back:// 返回上一界面
			MySettingActivity.this.finish();
			break;
		case R.id.activity_mysetting_relativeLayout_device:// 设备管理
			Intent intent_device = new Intent(MySettingActivity.this, DeviceActivity.class);
			startActivity(intent_device);
			break;
		case R.id.activity_mysetting_relativeLayout_version:// 检查更新
			// 发送检查版本更新指令
			Instruction_Utils.sendInstrustion(context.getApplicationContext(), Instruction_Utils.CHECK_VERSION,"MySettingActivity");
			break;
		case R.id.activity_mysetting_relativeLayout_introduce:// 使用介绍
			Intent intent_introduce = new Intent(MySettingActivity.this, ClauseActivity.class);
			startActivity(intent_introduce);
			break;
		// case R.id.activity_mysetting_relativeLayout_memory://缓存
		// break;
		case R.id.activity_mysetting_relativeLayout_feedBack:// 意见反馈
			Intent intent_feedBack = new Intent(MySettingActivity.this, FeedBackActivity.class);
			startActivity(intent_feedBack);
			break;
		case R.id.activity_mysetting_relativeLayout_clause:// 隐私条款
			Intent intent_clause = new Intent(MySettingActivity.this, ClauseActivity.class);
			startActivity(intent_clause);
			break;
		case R.id.activity_mysetting_relativeLayout_about:// 关于
			Intent intent_about = new Intent(MySettingActivity.this, IntroduceActivity.class);
			startActivity(intent_about);
			break;
		default:
			break;
		}

	}

	@SuppressLint("NewApi")
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.activity_mysetting_switch_accept_push_msg:// 接受消息提醒
			if (isChecked) {
				msg.setThumbDrawable(getResources().getDrawable(R.drawable.switchbackselected));
				user.setLoc_AcceptPush(1);
			} else {
				msg.setThumbDrawable(getResources().getDrawable(R.drawable.switchback));
				user.setLoc_AcceptPush(0);
			}
			// 发送变更user的指令
			Instruction_Utils.sendInstrustion(context.getApplicationContext(), Instruction_Utils.UPDATE_USER, user);
			break;
		case R.id.activity_mysetting_switch_helper_verification:// 添加好友验证
			if (isChecked) {
				verification.setThumbDrawable(getResources().getDrawable(R.drawable.switchbackselected));
				user.setLoc_AddHelperVerification(1);
			} else {
				verification.setThumbDrawable(getResources().getDrawable(R.drawable.switchback));
				user.setLoc_AddHelperVerification(0);
			}
			// 发送变更user的指令令
			Instruction_Utils.sendInstrustion(context.getApplicationContext(), Instruction_Utils.UPDATE_USER, user);
			break;
		case R.id.activity_mysetting_switch_public_helper:// 公共帮手
			String public_status = null;
			if (isChecked) {
				public_helper.setThumbDrawable(getResources().getDrawable(R.drawable.switchbackselected));
				public_status = "1";
			} else {
				public_helper.setThumbDrawable(getResources().getDrawable(R.drawable.switchback));
				public_status = "2";
			}
			// 发送变更公共帮手状态指令
			Instruction_Utils.sendInstrustion(context.getApplicationContext(), Instruction_Utils.UPDATE_USER_PUBLIC, public_status);
			break;

		default:
			break;
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
		if (action.equals(MessageConstants.REFRESH_USER)) {
			setData();
		}else if (action.equals(MessageConstants.UPGRADED_VERSION)) {
			if (intent.getStringExtra("source").equals("MySettingActivity")) {
				Update(intent.getStringExtra("url"));
			}
		}
	}
	private void Update(String url) {
		//这里来检测版本是否需要更新 
        mUpdateManager = new UpdateManager(MySettingActivity.this);  
        mUpdateManager.checkUpdateInfo(url);
	}
}

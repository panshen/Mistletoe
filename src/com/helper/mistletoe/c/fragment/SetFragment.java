package com.helper.mistletoe.c.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.MipcaActivityCapture;
import com.helper.mistletoe.c.ui.MySettingActivity;
import com.helper.mistletoe.c.ui.PersonalActivity;
import com.helper.mistletoe.c.ui.RollDiceActivity;
import com.helper.mistletoe.c.ui.ZxingDialogActivity;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

@SuppressLint("InflateParams")
public class SetFragment extends BaseFragment implements OnClickListener {
	private LinearLayout personal, richScan, shake, setting;
	// private LinearLayout health;
	private ImageView code;
	private SnaImageViewV2 head;
	private TextView name, comment, targetCount, helperCount, coinCount;
	private User_Bean user_bean;
	private IntentFilter myIntentFilter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.setting_frangment, null);
		// 发出同步user的指令
		Instruction_Utils.sendInstrustion(getActivity().getApplicationContext(), Instruction_Utils.SYNCHRONOUS_USER);
		setUpView(v);
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(MessageConstants.REFRESH_USER);
		// 注册广播
		getActivity().registerReceiver(IntentReceicer, myIntentFilter);
		setData();
	}

	private void setUpView(View v) {
		personal = (LinearLayout) v.findViewById(R.id.setting_frangment_linearLayout_personal);
		richScan = (LinearLayout) v.findViewById(R.id.setting_frangment_linearLayout_richScan);
		shake = (LinearLayout) v.findViewById(R.id.setting_frangment_linearLayout_shake);
		setting = (LinearLayout) v.findViewById(R.id.setting_frangment_linearLayout_setting);
		// health = (LinearLayout) v
		// .findViewById(R.id.setting_frangment_linearLayout_health);
		head = (SnaImageViewV2) v.findViewById(R.id.setting_frangment_imageView_personal_head);
		code = (ImageView) v.findViewById(R.id.setting_frangment_imageView_code);
		name = (TextView) v.findViewById(R.id.setting_frangment_textView_personal_name);
		comment = (TextView) v.findViewById(R.id.setting_frangment_textView_personal_comment);
		targetCount = (TextView) v.findViewById(R.id.setting_frangment_textView_targetCount);
		helperCount = (TextView) v.findViewById(R.id.setting_frangment_textView_helperCount);
		coinCount = (TextView) v.findViewById(R.id.setting_frangment_textView_coinCount);
		user_bean = new User_Bean();
		personal.setOnClickListener(this);
		richScan.setOnClickListener(this);
		shake.setOnClickListener(this);
		setting.setOnClickListener(this);
	  //health.setOnClickListener(this);
		code.setOnClickListener(this);
	}

	private void setData() {
		try {
			user_bean.readData(getActivity());
			name.setText(user_bean.getName());
			comment.setText(user_bean.getSign());
			if (user_bean.getSign() != null) {
				if (user_bean.getSign().length() <= 0) {
					comment.setText("未设置个性签名");
				}
			}
			targetCount.setText(user_bean.getLoc_Target_count().toString());
			helperCount.setText(user_bean.getLoc_Helper_count().toString());
			coinCount.setText(user_bean.getLoc_coin_count().toString());
			head.setImageForShow(user_bean.getAvatar_file_id(), SnaBitmap.NET_SMALL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_frangment_linearLayout_personal:// 进入个人信息界面
			Intent intent_personal = new Intent(getActivity(), PersonalActivity.class);
			startActivity(intent_personal);
			break;
		case R.id.setting_frangment_linearLayout_richScan:// 进入扫一扫界面
			Intent intent_richScan = new Intent(getActivity(), MipcaActivityCapture.class);
			startActivity(intent_richScan);
			break;
		case R.id.setting_frangment_linearLayout_shake:// 进入摇一摇界面
			Intent intent_shake = new Intent(getActivity(), RollDiceActivity.class);
			startActivity(intent_shake);
			break;
		case R.id.setting_frangment_linearLayout_setting:// 进入设置界面
			Intent intent_setting = new Intent(getActivity(), MySettingActivity.class);
			startActivity(intent_setting);
			break;
		// case R.id.setting_frangment_linearLayout_health:// 进入健康指数界面
		// Intent intent_health = new Intent(getActivity(),
		// HealthIndexActivity.class);
		// startActivity(intent_health);
		// break;
		case R.id.setting_frangment_imageView_code:// 打开二维码dialog界面
			Intent intent_code = new Intent(getActivity(), ZxingDialogActivity.class);
			startActivity(intent_code);
			break;

		default:
			break;
		}
	}
	@Override
	public void onPause() {
		super.onPause();
		// 注销广播
		getActivity().unregisterReceiver(IntentReceicer);
	}

	@Override
	public void onSearch(String filterStr) {

	}

	@Override
	public void onAdd() {

	}

	@Override
	public void onMenu(RelativeLayout menu) {

	}

	private BroadcastReceiver IntentReceicer = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(MessageConstants.REFRESH_USER)) {
				setData();
			}
		}
	};

}

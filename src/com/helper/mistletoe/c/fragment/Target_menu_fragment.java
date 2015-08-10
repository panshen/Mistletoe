package com.helper.mistletoe.c.fragment;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetType;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Target_menu_fragment extends DialogFragment {
	private Button toTop;
	private Button publish;
	private Button mute;
	public static String DIALOG_FRAGMENT_DATA = "com.helper.mistletoe.c.fragment.Target_menu_fragment";
	private Target_Bean tb;
	private boolean canpublicsh = true;

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.target_dialog_menu, null);
		
		toTop = (Button) v.findViewById(R.id.toTop);
		publish = (Button) v.findViewById(R.id.publish);
		mute = (Button) v.findViewById(R.id.mute);
		tb = (Target_Bean) getArguments().getSerializable(DIALOG_FRAGMENT_DATA);
//		ObjectAnimator.ofFloat(toTop, "alpha", 0.1f,1.0f).setDuration(1000).start();
//		ObjectAnimator.ofFloat(publish, "alpha", 0.1f,1.0f).setDuration(1000).start();
//		ObjectAnimator.ofFloat(mute, "alpha", 0.1f,1.0f).setDuration(1000).start();
		TargetType canChangeTo = tb.canChangeToTargetType(getActivity());
		if (canChangeTo != null) {
			if (canChangeTo == TargetType.Market) {
				publish.setText("发布到海报");
			} else if (canChangeTo == TargetType.General) {
				publish.setText("从海报撤回");
			}
		} else {
			publish.setTextColor(Color.parseColor("#a5a5a5"));
			publish.setClickable(false);
			publish.setBackgroundColor(Color.parseColor("#ffffff"));
			canpublicsh = false;
		}
		
		if (tb.getTarget_stick() == Target_Bean.TARGET_STICK_STICK) {
			toTop.setText("取消置顶");
		} else {
			toTop.setText("置顶该目标");
		}

		if (tb.getAccept_push_msg() == Target_Bean.TARGET_ACCEPTPUSH_ACCEPT) {
			mute.setText("静音");
		} else {
			mute.setText("取消静音");
		}
		
		toTop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tb.topTarget(getActivity());
				getDialog().dismiss();
			}
		});

		publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tb.entryMarket(getActivity());
				if (canpublicsh) {
					getDialog().dismiss();
				}
			}
		});

		mute.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tb.muteTarget(getActivity());
				getDialog().dismiss();
			}
		});
		AlertDialog.Builder ad = 	new AlertDialog.Builder(getActivity()).setView(v);
		return ad.show();
	}

	public static Target_menu_fragment getFragmet(Target_Bean id) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(DIALOG_FRAGMENT_DATA, id);
		Target_menu_fragment sdf = new Target_menu_fragment();
		sdf.setArguments(bundle);
		return sdf;
	}

}

package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.adapter.TemplateListAdapter;
import com.helper.mistletoe.c.ui.adapter.TemplateListAdapter.ICallback;
import com.helper.mistletoe.m.pojo.Template_Bean;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class TemplateListDialogActivity extends Activity implements ICallback {
	private Context context;
	private RelativeLayout p;
	private ArrayList<Template_Bean> templateS;
	private ListView templateList;
	private Template_Bean defaultTemplate;
	private TemplateListAdapter templateAdapter;
	public static String CLOSE_THIS_ACTIVITY = "com.helper.mistletoe.c.ui.TemplateListDialogActivity.CLOSE_ACTIVITY";
	private mBroad mb;
	public Activity thisactivity;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.template_list_dialog);
		TemplateListDialogActivity.this.setFinishOnTouchOutside(true);
		thisactivity = this;
		defaultTemplate = new Template_Bean();
		defaultTemplate.setTemplate_name("常规目标");
		// 初始化模板集合
		intent = getIntent();
		if (intent.getParcelableArrayListExtra("TemplateS") == null) {
			templateS = new ArrayList<Template_Bean>();
		} else {
			templateS = intent.getParcelableArrayListExtra("TemplateS");
		}
		templateS.add(defaultTemplate);
		mb = new mBroad();
		context = TemplateListDialogActivity.this;
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter inF = new IntentFilter(CLOSE_THIS_ACTIVITY);
		registerReceiver(mb, inF);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mb);
	}

	class mBroad extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			thisactivity.finish();
		}
	}

	private void initView() {
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.list_scale_alpha_anim);
		LayoutAnimationController controller = new LayoutAnimationController(
				anim);
		controller.setOrder(LayoutAnimationController.ORDER_REVERSE);
		p = (RelativeLayout) findViewById(R.id.template_list_relativeLayout);
		templateList = (ListView) findViewById(R.id.template_list_listView);
		templateAdapter = new TemplateListAdapter(context, templateS);
		templateList.setAdapter(templateAdapter);
		templateList.setLayoutAnimation(controller);
		p.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void finishss() {
		TemplateListDialogActivity.this.finish();
	}
}

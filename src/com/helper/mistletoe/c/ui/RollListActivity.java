package com.helper.mistletoe.c.ui;

import java.util.List;

import com.helper.mistletoe.R;
import com.helper.mistletoe.roll.Dice_Bean;
import com.helper.mistletoe.roll.Dices;
import com.helper.mistletoe.roll.ListofRollAdapter;
import com.helper.mistletoe.roll.RollService;
import com.helper.mistletoe.roll.RollServiceImpl;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.RelativeLayout;

public class RollListActivity extends Activity {
	Context mContext = null;
	private ListView roll_lv;
	String result;
	private RelativeLayout back;
	private ProgressBar progressBar;
	private TextView clear;
	private List<Dice_Bean> dice_faces;
	private ListofRollAdapter roll_adapter;
	RollService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mContext = this;
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.roll_list);
		initViews();
		setData();
		setlistener();
	}

	private void setlistener() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dices.shujunum = 0;
				RollListActivity.this.finish();
			}
		});
		clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				service = new RollServiceImpl(RollListActivity.this);
				service.deleteFaceValues();
				dice_faces = service.getFaceValues();
				roll_adapter.updateListView(dice_faces);
			}
		});

	}

	private void setData() {
		service = new RollServiceImpl(mContext);
		dice_faces = service.getFaceValues();
		roll_adapter = new ListofRollAdapter(mContext, dice_faces);
		roll_lv.setAdapter(roll_adapter);
	}

	private void initViews() {
		back = (RelativeLayout) findViewById(R.id.roll_list_button_back);
		progressBar = (ProgressBar) findViewById(R.id.roll_list_progressb);
		progressBar.setVisibility(ProgressBar.GONE);
		roll_lv = (ListView) findViewById(R.id.roll_list_listView);
		clear = (TextView) findViewById(R.id.roll_list_textview_clear);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		roll_adapter = null;
	}
}

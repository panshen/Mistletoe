package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.TargetMemberList_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.work.be.TargetGet_Event;
import com.helper.mistletoe.m.work.ui.TargetGetted_Event;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.wheel.WheelView;
import com.helper.mistletoe.wheel.adapters.AbstractWheelTextAdapter;

import de.greenrobot.event.EventBus;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HoloWheelActivity extends PrivateBasicActivity {
	private Button sure;
	private static int targetId = -1;
	private Adapter NameAdaper;
	private WheelView cities;
	private ArrayList<String> OwnerNames = new ArrayList<String>();
	private ArrayList<Integer> OwnerId = new ArrayList<Integer>();
	private ArrayList<Integer> selectedId = new ArrayList<Integer>();
	private User_Bean user;
	private Integer selecteditem = -1;
	private String Name;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.wheel_dialog);
		EventBus.getDefault().register(this);
		targetId = getIntent().getIntExtra("targetId", -1);
		cities = (WheelView) findViewById(R.id.wheelView);
		sure = (Button) findViewById(R.id.wheel_button);
		sure.setOnClickListener(this);
		user=new User_Bean();
		try {
			user.readData(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventBus.getDefault().post(new TargetGet_Event(targetId, ""));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.wheel_button:
			selecteditem = cities.getCurrentItem();
			returnOwnerId();
			finish();
			break;
		default:
			break;
		}
	}

	private void returnOwnerId() {
		// TODO Auto-generated method stub
		switch (OwnerId.get(selecteditem)) {
		case -1:
			selectedId.clear();
			selectedId.addAll(OwnerId);
			selectedId.remove(OwnerId.get(selecteditem));
			break;
		default:
			selectedId.clear();
			selectedId.add(OwnerId.get(selecteditem));
			break;
		}
		Name = OwnerNames.get(selecteditem);
		Intent i = new Intent(HoloWheelActivity.this, Schedule_Cost_Activity.class);
		i.putIntegerArrayListExtra("OwnerId", selectedId);
		i.putExtra("OwnerName", Name);
		setResult(0, i);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		selecteditem = cities.getCurrentItem();
		returnOwnerId();
		EventBus.getDefault().unregister(this);
	}

	/**
	 * Adapter
	 */
	private class Adapter extends AbstractWheelTextAdapter {
		ArrayList<String> OwnerNames;

		/**
		 * Constructor
		 */
		protected Adapter(Context context, ArrayList<String> OwnerNames) {
			super(context, R.layout.city_holo_layout, NO_RESOURCE);
			if (OwnerNames == null) {
				this.OwnerNames = new ArrayList<String>();
			} else {
				this.OwnerNames = OwnerNames;
			}
			setItemTextResource(R.id.city_name);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return OwnerNames.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			return OwnerNames.get(index);
		}
	}

	public void onEventMainThread(TargetGetted_Event event) {
		try {
			// 获取到结果
			TargetMemberList_Bean tNoteTagList = event.getTarget().getLoc_TargetMember();
			OwnerNames.add("全员AA");
			OwnerId.add(-1);
			OwnerNames.add("本人");
			OwnerId.add(user.getUser_id());
			for (TargetMember_Bean i : tNoteTagList.getTargetMemberList()) {
				if (i.getMember_id() != user.getUser_id()) {
					OwnerNames.add(i.getShowName());
					OwnerId.add(i.getMember_id());
				}
			}
			cities.setVisibleItems(5); // Number of items
			cities.setWheelBackground(R.drawable.wheel_bg_holo);
			cities.setWheelForeground(R.drawable.wheel_val_holo);
			cities.setShadowColor(0xFF000000, 0x88000000, 0x00000000);
			NameAdaper = new Adapter(this, OwnerNames);
			cities.setViewAdapter(NameAdaper);
			cities.setCurrentItem(0);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}

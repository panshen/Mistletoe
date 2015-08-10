package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.TargetMemberList_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.Task_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.work.be.TargetGet_Event;
import com.helper.mistletoe.m.work.ui.TargetGetted_Event;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.ThreadPoolUtils_db;
import com.helper.mistletoe.util.Tool_Utils;
import com.helper.mistletoe.v.DateTimePickDialogUtil;
import com.helper.mistletoe.wheel.WheelView;
import com.helper.mistletoe.wheel.adapters.AbstractWheelTextAdapter;

import de.greenrobot.event.EventBus;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskCreateDialogActivity extends PrivateBasicActivity {
	private EditText content;
	private WheelView assighed;
	private static int weight=1;
	private static int targetId = -1;
	private Adapter NameAdaper;
	private ArrayList<String> OwnerNames = new ArrayList<String>();
	private ArrayList<Integer> OwnerId = new ArrayList<Integer>();
	private Integer selectedId =  -1;
	private User_Bean user;
	private Integer selecteditem = -1;
	private String source;
	private int[] mTargetMembers;
	private Integer[] memberIds;
	private GetTargetMemberTask GetMemberTask;
	private TextView cancel,send,weight_one,weight_two,weight_three,start_time,end_time;
	private ImageView weight_iv;
	public Long startDateTimeForLong=null;
	public Long endDateTimeForLong=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.create_task_dialog);
		EventBus.getDefault().register(this);
		source=getIntent().getStringExtra("taskSource");
		targetId = getIntent().getIntExtra("targetId", -1);
		if (getIntent().getExtras().containsKey("mTargetMembers")) {
			mTargetMembers=getIntent().getIntArrayExtra("mTargetMembers");
			memberIds=new Integer[mTargetMembers.length];
			for (int i = 0; i < mTargetMembers.length; i++) {
				memberIds[i]=mTargetMembers[i];
			}
		}
		initView();
		user=new User_Bean();
		try {
			user.readData(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (targetId>0) {
			EventBus.getDefault().post(new TargetGet_Event(targetId, ""));
		}else {
			GetMemberTask=new GetTargetMemberTask();
			GetMemberTask.executeOnExecutor(ThreadPoolUtils_db.threadPool);
		}
		
	}
	public class GetTargetMemberTask extends AsyncTask<String, Integer, ArrayList<Helpers_Sub_Bean>> {

		@Override
		protected ArrayList<Helpers_Sub_Bean> doInBackground(String... params) {
			ArrayList<Helpers_Sub_Bean> ListHelper = new ArrayList<Helpers_Sub_Bean>();
			HelperManager helperManager = new HelperManager();
			ListHelper = helperManager.ReadGroupMemberListFromDB(TaskCreateDialogActivity.this.getApplicationContext(), memberIds);
			return ListHelper;
		}

		@Override
		protected void onPostExecute(ArrayList<Helpers_Sub_Bean> result) {
			super.onPostExecute(result);
			distribution(result);
		}
	}

	void distribution(ArrayList<Helpers_Sub_Bean> result){
		OwnerNames.add("暂不分配");
		OwnerId.add(-1);
		OwnerNames.add("本人");
		OwnerId.add(user.getUser_id());
		for (int i = 0; i < result.size(); i++) {
			Helpers_Sub_Bean helper=result.get(i);
			if (helper.getHelper_id() != user.getUser_id()) {
				OwnerNames.add(helper.getHelper_name());
				OwnerId.add(helper.getHelper_id());
			}
		}
		assighed.setVisibleItems(5); // Number of items
//		assighed.setWheelBackground(R.drawable.wheel_bg_holo);
//		assighed.setWheelForeground(R.drawable.wheel_val_holo);
//		assighed.setShadowColor(0xFF000000, 0x88000000, 0x00000000);
		NameAdaper = new Adapter(this, OwnerNames);
		assighed.setViewAdapter(NameAdaper);
		assighed.setCurrentItem(selecteditem);
	};
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.create_task_dialog_textview_send:
			 returnOwnerId();
			 startDateTimeForLong=DateTimePickDialogUtil.getTime(start_time.getText().toString());
			 endDateTimeForLong =DateTimePickDialogUtil.getTime(end_time.getText().toString());
			boolean can=isCanSend();
			 if (can) {
				 if (source!=null) {
					 if (source.equals("INFORMATION")) {
							Task_Bean.createTask(targetId, content.getText().toString(), weight, selectedId,startDateTimeForLong,endDateTimeForLong);
						}else if (source.equals("CREATE")) {
							Intent task_create=new Intent();
							task_create.putExtra("taskOwnerId", selectedId);
							task_create.putExtra("taskContent", content.getText().toString());
							task_create.putExtra("taskWeights", weight);	
							task_create.putExtra("taskBeginTime", startDateTimeForLong);
							task_create.putExtra("taskEndTime", endDateTimeForLong);
							setResult(1, task_create);
						}
				}else {
					Task_Bean.createTask(targetId, content.getText().toString(), weight, selectedId,startDateTimeForLong,endDateTimeForLong);
				}
				finish();
			}
			break;
		case R.id.create_task_dialog_textview_cancel:
			finish();
			break;
		case R.id.create_task_dialog_textview_startTime:
				DateTimePickDialogUtil startDateTimePicKDialog = new DateTimePickDialogUtil(TaskCreateDialogActivity.this, start_time.getText().toString());
				startDateTimePicKDialog.dateTimePicKDialog(start_time);
			break;
		case R.id.create_task_dialog_textview_endTime:
				DateTimePickDialogUtil endDateTimePicKDialog = new DateTimePickDialogUtil(TaskCreateDialogActivity.this, end_time.getText().toString());
				endDateTimePicKDialog.dateTimePicKDialog(end_time);
			break;
		case R.id.create_task_dialog_textview_weight_one:
				initialWeightText();
				weight_one.setTextColor(Color.parseColor("#cc222222"));
				weight_one.setTextSize(18);
				weight_iv.setImageResource(R.drawable.weight_one);
				weight = 1;
			break;
		case R.id.create_task_dialog_textview_weight_two:
				initialWeightText();
				weight_two.setTextColor(Color.parseColor("#cc222222"));
				weight_two.setTextSize(18);
				weight_iv.setImageResource(R.drawable.weight_two);
				weight = 2;
			break;
		case R.id.create_task_dialog_textview_weight_three:
				initialWeightText();
				weight_three.setTextColor(Color.parseColor("#cc222222"));
				weight_three.setTextSize(18);
				weight_iv.setImageResource(R.drawable.weight_three);
				weight = 3;
			break;
		default:
			break;
		}

	}
	private boolean isCanSend() {
		// TODO Auto-generated method stub
		boolean a=true;
		if (endDateTimeForLong!=null&&startDateTimeForLong!=null&&endDateTimeForLong<startDateTimeForLong) {
			a=false;
			Tool_Utils.showInfo(TaskCreateDialogActivity.this, "结束时间不可早于开始时间");
		}else if (content.getText().toString()==null||content.getText().toString().equals("")||content.getText().toString().length()<0) {
			a=false;
			Tool_Utils.showInfo(TaskCreateDialogActivity.this, "任务内容为空");
		}
		return a;
	}
	private void initView() {
		content = (EditText) findViewById(R.id.create_task_dialog_editText_content);
		assighed = (WheelView) findViewById(R.id.create_task_dialog_wheelView);
		weight_one=(TextView) findViewById(R.id.create_task_dialog_textview_weight_one);
		weight_one.setOnClickListener(this);
		weight_two =(TextView) findViewById(R.id.create_task_dialog_textview_weight_two);
		weight_two.setOnClickListener(this);
		weight_three = (TextView) findViewById(R.id.create_task_dialog_textview_weight_three);
		weight_three.setOnClickListener(this);
		weight_iv=(ImageView) findViewById(R.id.create_task_dialog_imageView_weight);
		initialWeightText();
		if (weight == 2) {
			weight_two.setTextSize(18);
			weight_iv.setImageResource(R.drawable.weight_two);
			weight_two.setTextColor(Color.parseColor("#cc222222"));
		} else if (weight == 3) {
			weight_three.setTextSize(18);
			weight_iv.setImageResource(R.drawable.weight_three);
			weight_three.setTextColor(Color.parseColor("#cc222222"));
		} else {
			weight_one.setTextSize(18);
			weight_iv.setImageResource(R.drawable.weight_one);
			weight_one.setTextColor(Color.parseColor("#cc222222"));

		}
		start_time=(TextView) findViewById(R.id.create_task_dialog_textview_startTime);
		start_time.setOnClickListener(this);
		end_time=(TextView) findViewById(R.id.create_task_dialog_textview_endTime);
		end_time.setOnClickListener(this);
		cancel = (TextView) findViewById(R.id.create_task_dialog_textview_cancel);
		cancel.setOnClickListener(this);
		send = (TextView) findViewById(R.id.create_task_dialog_textview_send);
		send.setOnClickListener(this);
	}
	private void initialWeightText() {
		weight_one.setText("1");
		weight_one.setTextColor(Color.parseColor("#a9a9a9"));
		weight_one.setTextSize(12);
		weight_two.setText("2");
		weight_two.setTextColor(Color.parseColor("#a9a9a9"));
		weight_two.setTextSize(12);
		weight_three.setText("3");
		weight_three.setTextColor(Color.parseColor("#a9a9a9"));
		weight_three.setTextSize(12);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
	private void returnOwnerId() {
		selecteditem =assighed.getCurrentItem();
		switch (OwnerId.get(selecteditem)) {
		case -1:
			selectedId=null;
			break;
		default:
			selectedId=OwnerId.get(selecteditem);
			break;
		}
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
			
			OwnerNames.add("暂不分配");
			OwnerId.add(-1);
			OwnerNames.add("本人");
			OwnerId.add(user.getUser_id());
			for (TargetMember_Bean i : tNoteTagList.getTargetMemberList()) {
				if (i.getMember_id() != user.getUser_id()) {
					OwnerNames.add(i.getShowName());
					OwnerId.add(i.getMember_id());
				}
			}
			assighed.setVisibleItems(5); // Number of items
//			assighed.setWheelBackground(R.drawable.wheel_bg_holo);
//			assighed.setWheelForeground(R.drawable.wheel_val_holo);
//			assighed.setShadowColor(0xFF000000, 0x88000000, 0x00000000);
			NameAdaper = new Adapter(this, OwnerNames);
			assighed.setViewAdapter(NameAdaper);
			assighed.setCurrentItem(0);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}

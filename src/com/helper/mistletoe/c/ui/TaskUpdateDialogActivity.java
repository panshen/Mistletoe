package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.TargetMemberList_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.Task_Bean;
import com.helper.mistletoe.m.pojo.Task_Enum.TaskStatus;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.work.be.TargetGet_Event;
import com.helper.mistletoe.m.work.ui.TargetGetted_Event;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.ThreadPoolUtils_db;
import com.helper.mistletoe.util.TimeTool_Utils;
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

public class TaskUpdateDialogActivity extends PrivateBasicActivity {
	private boolean isEditable = false;
	private TextView cancel, send;
	private TextView deleted;
	private TextView content_tv;
	private EditText content_et;
	private TextView startTime, endTime;
	private TextView state;
	private TextView weight_one, weight_two, weight_three;
	private ImageView weight_iv;
	private TextView owner;
	private WheelView assighed;
	private Adapter NameAdaper;
	private ArrayList<String> OwnerNames = new ArrayList<String>();
	private ArrayList<Integer> OwnerId = new ArrayList<Integer>();
	private Integer selectedId = -1;
	private User_Bean user;
	private Integer selecteditem = 0;
	private Task_Bean task_Bean;
	private String source;
	private boolean isEdited = true;
	private int position;
	private int[] mTargetMembers;
	private Integer[] memberIds;
	private static int weight;
	private GetTargetMemberTask GetMemberTask;
	private Long startDateTimeForLong = null;
	private Long endDateTimeForLong = null;
	private String mTargetTag="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.update_task_dialog);
		EventBus.getDefault().register(this);
		task_Bean = getDataByIntent();
		if (source != null) {
			if (source.equals("CREATE")) {
				isEditable = true;
			}
		}
		weight = task_Bean.getWeights();
		initView();
		user = new User_Bean();
		try {
			user.readData(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (task_Bean.getLoc_TargetId() > 0) {
			EventBus.getDefault().post(new TargetGet_Event(task_Bean.getLoc_TargetId(), mTargetTag));
		} else {
			GetMemberTask = new GetTargetMemberTask();
			GetMemberTask.executeOnExecutor(ThreadPoolUtils_db.threadPool);
		}

	}

	private Task_Bean getDataByIntent() {
		if (task_Bean == null) {
			task_Bean = new Task_Bean();
		}
		source = getIntent().getStringExtra("taskSource");
		position = getIntent().getIntExtra("taskPosition", -1);
		if (getIntent().getExtras().containsKey("mTargetMembers")) {
			mTargetMembers = getIntent().getIntArrayExtra("mTargetMembers");
			memberIds = new Integer[mTargetMembers.length];
			for (int i = 0; i < mTargetMembers.length; i++) {
				memberIds[i] = mTargetMembers[i];
			}
		}
		if (getIntent().getExtras().containsKey("taskEdited")) {
			isEdited = getIntent().getBooleanExtra("taskEdited", true);
		}
		if (getIntent().getExtras().containsKey("targetTag")) {
			mTargetTag=getIntent().getStringExtra("targetTag");
		}
		task_Bean.setOwner(getIntent().getStringExtra("taskOwner"));
		task_Bean.setOwner_id(getIntent().getIntExtra("taskOwnerId", -1));
		task_Bean.setTask_id(getIntent().getIntExtra("taskId", 0));
		task_Bean.setStatus(TaskStatus.valueOf(getIntent().getIntExtra("taskStatus", TaskStatus.Draft.toInt())));
		task_Bean.setDescription(getIntent().getStringExtra("taskContent"));
		task_Bean.setLoc_TargetId(getIntent().getIntExtra("taskTargetId", -1));
		task_Bean.setWeights(getIntent().getIntExtra("taskWeights", 1));
		task_Bean.setBegin_time(getIntent().getLongExtra("taskBeginTime", -1));
		task_Bean.setEnd_time(getIntent().getLongExtra("taskEndTime", -1));
		if (task_Bean.getBegin_time() > 0) {
			startDateTimeForLong = task_Bean.getBegin_time();
		}
		if (task_Bean.getEnd_time() > 0) {
			endDateTimeForLong = task_Bean.getEnd_time();
		}
		return task_Bean;
	}

	private void initView() {
		content_tv = (TextView) findViewById(R.id.update_task_dialog_textView_content);
		content_tv.setText(task_Bean.getDescription());
		content_et = (EditText) findViewById(R.id.update_task_dialog_editText_content);
		content_et.setText(task_Bean.getDescription());
		startTime = (TextView) findViewById(R.id.update_task_dialog_textview_startTime);
		startTime.setOnClickListener(this);
		endTime = (TextView) findViewById(R.id.update_task_dialog_textview_endTime);
		endTime.setOnClickListener(this);
		state = (TextView) findViewById(R.id.update_task_dialog_textview_state);
		weight_one = (TextView) findViewById(R.id.update_task_dialog_textview_weight_one);
		weight_one.setOnClickListener(this);
		weight_two = (TextView) findViewById(R.id.update_task_dialog_textview_weight_two);
		weight_two.setOnClickListener(this);
		weight_three = (TextView) findViewById(R.id.update_task_dialog_textview_weight_three);
		weight_three.setOnClickListener(this);
		weight_iv = (ImageView) findViewById(R.id.update_task_dialog_imageView_weight);
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
		owner = (TextView) findViewById(R.id.update_task_dialog_textview_owner);
		if (task_Bean.getOwner_id() > 0) {
			owner.setText(task_Bean.getOwner());
		} else {
			owner.setText("暂未分配");
		}
		assighed = (WheelView) findViewById(R.id.update_task_dialog_wheelView);
		cancel = (TextView) findViewById(R.id.update_task_dialog_textview_cancel);
		cancel.setOnClickListener(this);
		send = (TextView) findViewById(R.id.update_task_dialog_textview_send);
		send.setOnClickListener(this);
		deleted = (TextView) findViewById(R.id.update_task_dialog_textView_delete);
		if (isEdited) {
			cancel.setVisibility(TextView.VISIBLE);
			deleted.setVisibility(TextView.VISIBLE);
		} else {
			cancel.setVisibility(TextView.GONE);
			deleted.setVisibility(TextView.GONE);
		}
		if (source.equals("INFORMATION")) {
			if (task_Bean.getStatus() == TaskStatus.Delete) {
				deleted.setText("恢复");
			} else {
				deleted.setText("作废");
			}
		} else if (source.equals("CREATE")) {
			deleted.setText("删除");
		}
		deleted.setOnClickListener(this);
		if (startDateTimeForLong == null || startDateTimeForLong < 1L) {
			startTime.setText("开始时间");
		} else {
			startTime.setText(TimeTool_Utils.fromateTimeShow(startDateTimeForLong * 1000, "yyyy年MM月dd日 HH:mm"));
		}
		if (endDateTimeForLong == null || endDateTimeForLong < 1L) {
			endTime.setText("结束时间");
		} else {
			endTime.setText(TimeTool_Utils.fromateTimeShow(endDateTimeForLong * 1000, "yyyy年MM月dd日 HH:mm"));
		}
		int states = DateTimePickDialogUtil.judgeTaskState(task_Bean);
		if (task_Bean.getStatus() == TaskStatus.Complete) {
			state.setText("已完成");
			state.setTextColor(Color.parseColor("#999999"));
		} else if (task_Bean.getStatus() == TaskStatus.Delete) {
			state.setText("已作废");
			state.setTextColor(Color.parseColor("#999999"));
		} else {
			if (states == 0) {
				state.setText("未进行");
				state.setTextColor(Color.parseColor("#999999"));
			} else if (states == 1) {
				state.setText("进行中");
				state.setTextColor(Color.parseColor("#33475f"));
			} else if (states == 2) {
				state.setText("已逾期");
				state.setTextColor(Color.parseColor("#eb4f38"));
			}
		}
		updateSurface();
	}

	private void updateSurface() {
		if (isEditable) {// 为可编辑
			if (source.equals("INFORMATION")) {
				content_et.setVisibility(TextView.GONE);
				content_tv.setVisibility(TextView.VISIBLE);
			} else {
				content_tv.setVisibility(TextView.GONE);
				content_et.setVisibility(TextView.VISIBLE);
			}
			owner.setVisibility(TextView.GONE);
			assighed.setVisibility(WheelView.VISIBLE);
			cancel.setText("取消");
			send.setText("确定修改");
		} else {
			content_et.setVisibility(TextView.GONE);
			assighed.setVisibility(WheelView.GONE);
			content_tv.setVisibility(TextView.VISIBLE);
			owner.setVisibility(TextView.VISIBLE);
			cancel.setText("编辑");
			send.setText("返回");
		}
	}

	public class GetTargetMemberTask extends AsyncTask<String, Integer, ArrayList<Helpers_Sub_Bean>> {
		@Override
		protected ArrayList<Helpers_Sub_Bean> doInBackground(String... params) {
			ArrayList<Helpers_Sub_Bean> ListHelper = new ArrayList<Helpers_Sub_Bean>();
			HelperManager helperManager = new HelperManager();
			ListHelper = helperManager.ReadGroupMemberListFromDB(TaskUpdateDialogActivity.this.getApplicationContext(), memberIds);
			return ListHelper;
		}

		@Override
		protected void onPostExecute(ArrayList<Helpers_Sub_Bean> result) {
			super.onPostExecute(result);
			distribution(result);
		}
	}

	void distribution(ArrayList<Helpers_Sub_Bean> result) {
		OwnerNames.add("暂不分配");
		OwnerId.add(-1);
		OwnerNames.add("本人");
		OwnerId.add(user.getUser_id());
		for (int i = 0; i < result.size(); i++) {
			Helpers_Sub_Bean helper = result.get(i);
			if (helper.getHelper_id() != user.getUser_id()) {
				OwnerNames.add(helper.getHelper_name());
				OwnerId.add(helper.getHelper_id());
			}
		}
		for (int i = 0; i < OwnerId.size(); i++) {
			if (OwnerId.get(i) == task_Bean.getOwner_id()) {
				selecteditem = i;
			}
		}
		assighed.setVisibleItems(5); // Number of items
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
		case R.id.update_task_dialog_textview_send:
			if (isEditable) {
				returnOwnerId();
				startDateTimeForLong = DateTimePickDialogUtil.getTime(startTime.getText().toString());
				endDateTimeForLong = DateTimePickDialogUtil.getTime(endTime.getText().toString());
				boolean can = isCanSend();
				if (can) {
					if (source.equals("INFORMATION")) {
						Task_Bean.updateTask(task_Bean.getTask_id(), task_Bean.getLoc_TargetId(), weight, task_Bean.getStatus(), selectedId, startDateTimeForLong, endDateTimeForLong);
					} else if (source.equals("CREATE")) {
						Intent task_update = new Intent();
						task_update.putExtra("taskPosition", position);
						task_update.putExtra("isDeleted", false);
						task_update.putExtra("taskOwnerId", selectedId);
						task_update.putExtra("taskWeights", weight);
						task_update.putExtra("taskStatus", task_Bean.getStatus().toInt());
						task_update.putExtra("taskContent", content_et.getText().toString());
						task_update.putExtra("taskBeginTime", startDateTimeForLong);
						task_update.putExtra("taskEndTime", endDateTimeForLong);
						setResult(1, task_update);
					}
					finish();
				}
			} else {
				finish();
			}

			break;
		case R.id.update_task_dialog_textView_delete:
			if (source.equals("INFORMATION")) {
				if (task_Bean.getStatus() == TaskStatus.Delete) {
					Task_Bean.updateTask(task_Bean.getTask_id(), task_Bean.getLoc_TargetId(), task_Bean.getWeights(), TaskStatus.Draft, task_Bean.getOwner_id(), startDateTimeForLong,
							endDateTimeForLong);
				} else {
					Task_Bean.updateTask(task_Bean.getTask_id(), task_Bean.getLoc_TargetId(), task_Bean.getWeights(), TaskStatus.Delete, task_Bean.getOwner_id(), startDateTimeForLong,
							endDateTimeForLong);
				}
			} else if (source.equals("CREATE")) {
				Intent task_update = new Intent();
				task_update.putExtra("taskPosition", position);
				task_update.putExtra("isDeleted", true);
				setResult(1, task_update);
			}
			finish();
			break;
		case R.id.update_task_dialog_textview_cancel:
			if (isEditable) {
				finish();
			} else {
				isEditable = true;
				updateSurface();
			}
			break;
		case R.id.update_task_dialog_textview_startTime:
			if (isEditable) {
				DateTimePickDialogUtil startDateTimePicKDialog = new DateTimePickDialogUtil(TaskUpdateDialogActivity.this, startTime.getText().toString());
				startDateTimePicKDialog.dateTimePicKDialog(startTime);
			} else {
				Tool_Utils.showInfo(TaskUpdateDialogActivity.this, "现在为非可编辑状态");
			}
			break;
		case R.id.update_task_dialog_textview_endTime:
			if (isEditable) {
				DateTimePickDialogUtil endDateTimePicKDialog = new DateTimePickDialogUtil(TaskUpdateDialogActivity.this, endTime.getText().toString());
				endDateTimePicKDialog.dateTimePicKDialog(endTime);
			} else {
				Tool_Utils.showInfo(TaskUpdateDialogActivity.this, "现在为非可编辑状态");
			}
			break;
		case R.id.update_task_dialog_textview_weight_one:
			if (isEditable) {
				initialWeightText();
				weight_one.setTextColor(Color.parseColor("#cc222222"));
				weight_one.setTextSize(18);
				weight_iv.setImageResource(R.drawable.weight_one);
				weight = 1;
			} else {
				Tool_Utils.showInfo(TaskUpdateDialogActivity.this, "现在为非可编辑状态");
			}

			break;
		case R.id.update_task_dialog_textview_weight_two:
			if (isEditable) {
				initialWeightText();
				weight_two.setTextColor(Color.parseColor("#cc222222"));
				weight_two.setTextSize(18);
				weight_iv.setImageResource(R.drawable.weight_two);
				weight = 2;
			} else {
				Tool_Utils.showInfo(TaskUpdateDialogActivity.this, "现在为非可编辑状态");
			}
			break;
		case R.id.update_task_dialog_textview_weight_three:
			if (isEditable) {
				initialWeightText();
				weight_three.setTextColor(Color.parseColor("#cc222222"));
				weight_three.setTextSize(18);
				weight_iv.setImageResource(R.drawable.weight_three);
				weight = 3;
			} else {
				Tool_Utils.showInfo(TaskUpdateDialogActivity.this, "现在为非可编辑状态");
			}
			break;
		default:
			break;
		}
	}

	private boolean isCanSend() {
		boolean a = true;
		if (endDateTimeForLong != null && startDateTimeForLong != null && endDateTimeForLong < startDateTimeForLong) {
			a = false;
			Tool_Utils.showInfo(TaskUpdateDialogActivity.this, "结束时间不可早于开始时间");
		} else if (content_et.getText().toString() == null || content_et.getText().toString().equals("") || content_et.getText().toString().length() < 0) {
			a = false;
			Tool_Utils.showInfo(TaskUpdateDialogActivity.this, "任务内容为空");
		}
		return a;
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
		selecteditem = assighed.getCurrentItem();
		switch (OwnerId.get(selecteditem)) {
		case -1:
			selectedId = null;
			break;
		default:
			selectedId = OwnerId.get(selecteditem);
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
			for (int i = 0; i < tNoteTagList.getTargetMemberList().size(); i++) {
				TargetMember_Bean targetMember_Bean = tNoteTagList.getTargetMemberList().get(i);
				if (targetMember_Bean.getMember_id() != user.getUser_id()) {
					OwnerNames.add(targetMember_Bean.getShowName());
					OwnerId.add(targetMember_Bean.getMember_id());
				}
			}
			for (int i = 0; i < OwnerId.size(); i++) {
				if (OwnerId.get(i) == task_Bean.getOwner_id()) {
					selecteditem = i;
				}
			}
			assighed.setVisibleItems(5); // Number of items
			NameAdaper = new Adapter(this, OwnerNames);
			assighed.setViewAdapter(NameAdaper);
			assighed.setCurrentItem(selecteditem);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}

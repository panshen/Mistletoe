package com.helper.mistletoe.c.ui;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.fragment.AddCostTags_Dialog_Fragment;
import com.helper.mistletoe.c.ui.adapter.CostTagGridAdapter;
import com.helper.mistletoe.c.ui.base.Base_Activity;
import com.helper.mistletoe.m.db.CostTagManager;
import com.helper.mistletoe.m.pojo.NoteTag_Pojo;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.ThreadPoolUtils_db;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.util.Tool_Utils;
import com.helper.mistletoe.v.GridViewForScrollView;

public class Schedule_Cost_Activity extends Base_Activity {
	private ImageView back;
	private TextView expenditure, income, symbol, send, date, time, ower;
	private EditText money, describe;
	private Calendar calendar;
	// private HorizontalListView TagListView;
	// private HorizontalListViewForTagAdapter TagAdapter;
	private GridViewForScrollView TagGridView;
	private CostTagGridAdapter TagGridAdapter;
	private static int targetId = -1;
	private ArrayList<String> TagNames = new ArrayList<String>();
	private ArrayList<Integer> TagIds = new ArrayList<Integer>();
	private Integer selectedTagId = 8;
	private String selectedTagName = "其他";
	private ArrayList<Integer> OwnerId = new ArrayList<Integer>();
//	private TextView add_Tag;
	private User_Bean user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.schedule_cost_layout);
			targetId = getIntent().getIntExtra("targetId", -1);
			// 发送费用标签的指令
			Instruction_Utils.sendInstrustion(getApplicationContext(), Instruction_Utils.SYNCHRONOUS_COSTTAG, targetId);
			initView();
			setListener();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			ReadCostTagFromDBTask s = new ReadCostTagFromDBTask();
			s.executeOnExecutor(ThreadPoolUtils_db.threadPool, targetId);
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	protected void initView() {
		try {
			// 初始化控件
			back = (ImageView) findViewById(R.id.schedule_cost_layout_back);
			expenditure = (TextView) findViewById(R.id.schedule_cost_layout_textView_expenditure);
			income = (TextView) findViewById(R.id.schedule_cost_layout_textView_income);
			symbol = (TextView) findViewById(R.id.schedule_cost_layout_textView_symbol);
			date = (TextView) findViewById(R.id.schedule_cost_layout_textView_date);
			time = (TextView) findViewById(R.id.schedule_cost_layout_textView_time);
			ower = (TextView) findViewById(R.id.schedule_cost_layout_textView_owner);
			send = (TextView) findViewById(R.id.schedule_cost_layout_textView_send);
			money = (EditText) findViewById(R.id.schedule_cost_layout_editText_money);
//			add_Tag = (TextView) findViewById(R.id.schedule_cost_layout_addTag);
			// type = (EditText)
			// findViewById(R.id.schedule_cost_layout_editText_type);
			// TagListView = (HorizontalListView)
			// findViewById(R.id.schedule_cost_layout_horizontalListView);
			// TagAdapter = new HorizontalListViewForTagAdapter(this, TagNames);
			// TagListView.setAdapter(TagAdapter);
			// TagAdapter.setSelectIndex(0);
			TagGridView = (GridViewForScrollView) findViewById(R.id.schedule_cost_layout_gridView);
			TagGridView.setNumColumns(4);
			TagGridAdapter = new CostTagGridAdapter(this, TagNames);
			TagGridView.setAdapter(TagGridAdapter);
			TagGridAdapter.setSelectIndex(0);
			describe = (EditText) findViewById(R.id.schedule_cost_layout_editText_describe);
			// 初始化成员变量
			expenditure.setTextColor(Color.rgb(237, 107, 125));
			income.setTextColor(Color.GRAY);
			symbol.setText("-");
			symbol.setTextColor(Color.rgb(237, 107, 125));
			calendar = Calendar.getInstance();
			date.setText(TimeTool_Utils.fromateTimeShow(calendar.getTime().getTime(), "yyyy年MM月dd日E"));
			time.setText(TimeTool_Utils.fromateTimeShow(calendar.getTime().getTime(), "HH:mm"));
			user = new User_Bean();
			user.readData(getApplicationContext());
			ower.setText("本人");
			OwnerId.clear();
			OwnerId.add(user.getUser_id());
			// 其他初始化工作
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setData() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setListener() {
		try {
			back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						finish();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
//			add_Tag.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					FragmentManager fg = getSupportFragmentManager();
//					AddCostTags_Dialog_Fragment sg = AddCostTags_Dialog_Fragment.getFragmet(String.valueOf(targetId));
//					sg.show(fg, "");
//				}
//			});
			expenditure.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					expenditure.setTextColor(Color.rgb(237, 107, 125));
					income.setTextColor(Color.GRAY);
					symbol.setText("-");
					symbol.setTextColor(Color.rgb(237, 107, 125));
				}
			});
			income.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					expenditure.setTextColor(Color.GRAY);
					income.setTextColor(Color.rgb(104, 198, 136));
					symbol.setText("+");
					symbol.setTextColor(Color.rgb(104, 198, 136));
				}
			});
			date.setOnClickListener(new OnClickListener() {
				private OnDateSetListener mDateSetListener = new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
						try {
							// 保存时间
							calendar.set(Calendar.YEAR, arg1);
							calendar.set(Calendar.MONTH, arg2);
							calendar.set(Calendar.DATE, arg3);
							// 更新显示
							date.setText(TimeTool_Utils.fromateTimeShow(calendar.getTime().getTime(), "yyyy年MM月dd日E"));
							time.setText(TimeTool_Utils.fromateTimeShow(calendar.getTime().getTime(), "HH:mm"));
						} catch (Exception e) {
							ExceptionHandle.ignoreException(e);
						}
					}

				};

				@Override
				public void onClick(View v) {
					try {
						// 弹出日期对话框
						int year = calendar.get(Calendar.YEAR);
						int monthOfYear = calendar.get(Calendar.MONTH);
						int dayOfMonth = calendar.get(Calendar.DATE);
						Dialog datelg = new DatePickerDialog(Schedule_Cost_Activity.this, mDateSetListener, year, monthOfYear, dayOfMonth);
						datelg.show();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			time.setOnClickListener(new OnClickListener() {
				private OnTimeSetListener mDateSetListener = new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						try {
							// 保存时间
							calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
							calendar.set(Calendar.MINUTE, minute);
							// 更新显示
							date.setText(TimeTool_Utils.fromateTimeShow(calendar.getTime().getTime(), "yyyy年MM月dd日E"));
							time.setText(TimeTool_Utils.fromateTimeShow(calendar.getTime().getTime(), "HH:mm"));
						} catch (Exception e) {
							ExceptionHandle.ignoreException(e);
						}
					}

				};

				@Override
				public void onClick(View v) {
					try {
						// 弹出日期对话框
						int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
						int minute = calendar.get(Calendar.MINUTE);
						boolean is24HourView = false;
						Dialog datelg = new TimePickerDialog(Schedule_Cost_Activity.this, mDateSetListener, hourOfDay, minute, is24HourView);
						datelg.show();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			ower.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(Schedule_Cost_Activity.this, HoloWheelActivity.class);
					i.putExtra("targetId", targetId);
					startActivityForResult(i, 1);
				}
			});
			send.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!checkEdit()) {
						return;
					} else {
						float cost = Float.parseFloat(money.getText().toString());
						if (symbol.getText().toString().equals("-")) {
							cost = 0 - cost;
						}
						String cost_desc = describe.getText().toString();
						long transaction_time = calendar.getTime().getTime() / 1000;
						Schedule_Bean.sendSchedule_CostApply(getApplicationContext(), targetId, null, cost / OwnerId.size(), selectedTagName, cost_desc, transaction_time, 0, OwnerId, selectedTagId);
						finish();
					}

				}
			});
			// TagListView.setOnItemClickListener(new OnItemClickListener() {
			//
			// @Override
			// public void onItemClick(AdapterView<?> parent, View view, int
			// position, long id) {
			// TagAdapter.setSelectIndex(position);
			// selectedTagId = TagIds.get(position);
			// selectedTagName = TagNames.get(position);
			// TagAdapter.notifyDataSetChanged();
			// }
			// });

			TagGridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					if (TagIds.get(position) == 0) {
						FragmentManager fg = getSupportFragmentManager();
						AddCostTags_Dialog_Fragment sg = AddCostTags_Dialog_Fragment.getFragmet(String.valueOf(targetId));
						sg.show(fg, "");
					} else {
						TagGridAdapter.setSelectIndex(position);
						selectedTagId = TagIds.get(position);
						selectedTagName = TagNames.get(position);
						TagGridAdapter.notifyDataSetChanged();
					}
				}

			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private boolean checkEdit() {
		if (money.getText().toString().trim().equals("")) {
			Tool_Utils.showInfo(Schedule_Cost_Activity.this, "金额不能为空");
		} else if (selectedTagId < 1) {
			Tool_Utils.showInfo(Schedule_Cost_Activity.this, "未选择费用标签");
		} else {
			return true;
		}
		return false;
	}

	// public void onGetTagList() {
	// try {
	// // 获取到结果
	// NoteTagList_Bean tNoteTagList =
	// NoteTagList_Bean.getInstance(Schedule_Cost_Activity.this);
	// for (int i = 0; i <= tNoteTagList.getTags().size(); i++) {
	// NoteTag_Bean temp = tNoteTagList.getTags().get(i);
	// if (temp != null) {
	// TagNames.add(temp.getTag());
	// TagIds.add(temp.getId());
	// }
	// }
	// TagAdapter.notifyDataSetChanged();
	// } catch (Exception e) {
	// ExceptionHandle.ignoreException(e);
	// }
	// }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if (data != null) {
				OwnerId.clear();
				OwnerId = data.getIntegerArrayListExtra("OwnerId");
				ower.setText(data.getStringExtra("OwnerName"));
			} else {
				OwnerId.clear();
				OwnerId.add(user.getUser_id());
				ower.setText("本人");
			}

			break;

		default:
			break;
		}
	}

	@Override
	public void dealWithRadio(Intent intent) {
		super.dealWithRadio(intent);
		String action = intent.getAction();
		if (action.equals(MessageConstants.REFRESH_COSTTAG)) {
			AirLock_Work.syncScheduleTag();
			Integer id = intent.getIntExtra("target_id", 0);
			if (id == targetId) {
				ReadCostTagFromDBTask s = new ReadCostTagFromDBTask();
				s.executeOnExecutor(ThreadPoolUtils_db.threadPool, id);
			}
		}
	}

	class ReadCostTagFromDBTask extends AsyncTask<Integer, Integer, ArrayList<NoteTag_Pojo>> {

		@Override
		protected ArrayList<NoteTag_Pojo> doInBackground(Integer... params) {
			Integer target_id = params[0];
			ArrayList<NoteTag_Pojo> ListCostTag = new ArrayList<NoteTag_Pojo>();
			CostTagManager costTagManager = new CostTagManager();
			if (target_id > 0) {
				ListCostTag = costTagManager.ReadCostTagFromDBByTargetId(getApplicationContext(), new Integer[] { target_id, 0 });
			} else {
				ListCostTag = costTagManager.ReadCostTagFromDBByTargetId(getApplicationContext(), new Integer[] { 0 });
			}
			return ListCostTag;
		}

		@Override
		protected void onPostExecute(ArrayList<NoteTag_Pojo> result) {
			super.onPostExecute(result);
			if (result==null) {
				result=new ArrayList<NoteTag_Pojo>();
			}
			    display(result);
		}
	}

	public void display(ArrayList<NoteTag_Pojo> SourceDateList) {
		TagNames.clear();
		TagIds.clear();
		for (int i = 0; i < SourceDateList.size(); i++) {
			NoteTag_Pojo temp = SourceDateList.get(i);
			if (temp != null) {
				if (!TagNames.contains(temp.getTag())) {
					TagNames.add(temp.getTag());
				}
				if (!TagIds.contains(temp.getId())) {
					TagIds.add(temp.getId());
				}
			}
		}
		TagNames.add("+");
		TagIds.add(0);
		// TagAdapter.notifyDataSetChanged();
		// TagAdapter.setSelectIndex(0);
		TagGridAdapter.notifyDataSetChanged();
		TagGridAdapter.setSelectIndex(0);
		selectedTagId = TagIds.get(0);
		selectedTagName = TagNames.get(0);

	}
}

package com.helper.mistletoe.c.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.fragment.SendEmail_Dialog_Fragment;
import com.helper.mistletoe.c.ui.adapter.NewHelperListAdapter;
import com.helper.mistletoe.c.ui.adapter.Schedule_CostList_Adapter;
import com.helper.mistletoe.c.ui.base.Base_Activity;
import com.helper.mistletoe.m.db.CostTagManager;
import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.NoteTagList_Bean;
import com.helper.mistletoe.m.pojo.NoteTag_Bean;
import com.helper.mistletoe.m.pojo.NoteTag_Pojo;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRunningState;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.m.work.be.ScheduleGetList_Event;
import com.helper.mistletoe.m.work.be.GetTarget_Target_Mode;
import com.helper.mistletoe.m.work.ui.ScheduleListGetted_Event;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.ThreadPoolUtils_Net;
import com.helper.mistletoe.util.ThreadPoolUtils_db;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.util.prcomode.ReadyGoooWork;
import com.helper.mistletoe.v.HorizontalListView;
import com.helper.mistletoe.v.HorizontalListViewForTagAdapter;

public class Schedule_CostList_Activity extends Base_Activity {
	private ImageView back, sendemail;
	private ListView costList;
	private TextView add, allMoney, incomeMoney, expenditureMoney, all,
			expenditure, income, title;

	public int mTargetId;// 目标的Id
	public Integer slectedId;
	private String mTargetTag;// 目标的标签
	private Target_Bean mTarget;// 使用[目标Id+标签]可以唯一的确定一个目标
	private Schedule_CostList_Adapter costListAdapter;
	private int targetId = 0;
	private String status;
	private ArrayList<Schedule_Bean> all_data = new ArrayList<Schedule_Bean>();
	private ArrayList<Schedule_Bean> expenditure_data = new ArrayList<Schedule_Bean>();
	private ArrayList<Schedule_Bean> income_data = new ArrayList<Schedule_Bean>();
	private float all_cost, expenditure_cost, income_cost;
	private ArrayList<String> TagNames = new ArrayList<String>();
	private ArrayList<Integer> TagIds = new ArrayList<Integer>();
	private Integer selectedTagId = -1;
	private HorizontalListView TagListView;
	private HorizontalListViewForTagAdapter TagAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.schedule_costlist_layout);
			initView();
			setListener();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			ReadCostTagFromDBTask s=new ReadCostTagFromDBTask();
			s.executeOnExecutor(ThreadPoolUtils_db.threadPool, mTargetId);
			ScheduleGetList_Event.getCostApply(mTargetId, slectedId,
					selectedTagId);
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {
			// 注销广播
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView() {
		try {
			// 初始化控件
			back = (ImageView) findViewById(R.id.schedule_costlist_layout_back);
			sendemail = (ImageView) findViewById(R.id.sendemailbutton);
			title = (TextView) findViewById(R.id.schedule_costlist_layout_title);
			add = (TextView) findViewById(R.id.schedule_costlist_layout_imageView_add);
			costList = (ListView) findViewById(R.id.schedule_costlist_layout_listView);
			allMoney = (TextView) findViewById(R.id.schedule_costlist_layout_textView_money);
			expenditureMoney = (TextView) findViewById(R.id.schedule_costlist_layout_textView_expenditure_money);
			incomeMoney = (TextView) findViewById(R.id.schedule_costlist_layout_textView_income_money);
			all = (TextView) findViewById(R.id.schedule_costlist_layout_textView_all);
			expenditure = (TextView) findViewById(R.id.schedule_costlist_layout_textView_expenditure);
			income = (TextView) findViewById(R.id.schedule_costlist_layout_textView_income);
			TagListView = (HorizontalListView) findViewById(R.id.schedule_costlist_layout_horizontalListView);
			TagAdapter = new HorizontalListViewForTagAdapter(this, TagNames);
			TagListView.setAdapter(TagAdapter);
			TagAdapter.setSelectIndex(0);
			// 初始化成员变量
			mTarget = new Target_Bean();
			mTargetId = -1;
			mTargetTag = "";
			slectedId = null;
			status = "all";
			// 其他初始化工作
			initColor();
			all.setBackgroundColor(Color.WHITE);
			all.setTextColor(Color.BLACK);
			sendemail.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					FragmentManager fg = getSupportFragmentManager();
					SendEmail_Dialog_Fragment sg = SendEmail_Dialog_Fragment
							.getFragmet(String.valueOf(mTargetId));
					sg.show(fg, "");
				}
			});
			getDataFromBundle();
			if (targetId == 1) {
				add.setVisibility(TextView.VISIBLE);
			} else {
				add.setVisibility(TextView.GONE);
			}
			new GetTarget_Target_Mode(mTargetId, mTargetTag)
					.publishWork(getWorkFactory());// 开启异步线程获取Target，获取结果在onWorkOk里
			ScheduleGetList_Event.getCostApply(mTargetId, slectedId, null);
//			onGetTagList();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void initColor() {
		all.setBackgroundColor(Color.rgb(229, 212, 122));// 黄色
		all.setTextColor(Color.WHITE);
		expenditure.setBackgroundColor(Color.rgb(237, 107, 125));// 红色
		expenditure.setTextColor(Color.WHITE);
		income.setBackgroundColor(Color.rgb(104, 198, 136));// 绿色
		income.setTextColor(Color.WHITE);
	}

	public void setData() {
		try {
			all.setText("全部(" + all_data.size() + ")");
			all_cost = 0.0f;
			expenditure_cost = 0.0f;
			income_cost = 0.0f;
			income_data = new ArrayList<Schedule_Bean>();
			expenditure_data = new ArrayList<Schedule_Bean>();
			for (int i = 0; i < all_data.size(); i++) {
				if (all_data.get(i).cost >= 0) {
					income_data.add(all_data.get(i));
					income_cost = income_cost + all_data.get(i).cost;
				} else {
					expenditure_data.add(all_data.get(i));
					expenditure_cost = expenditure_cost + all_data.get(i).cost;
				}
			}
			all_cost = expenditure_cost + income_cost;
			expenditure.setText("支出(" + expenditure_data.size() + ")");
			income.setText("预算(" + income_data.size() + ")");
			display(allMoney, all_cost);
			display(expenditureMoney, expenditure_cost);
			display(incomeMoney, income_cost);
			if (status.equals("all")) {
				costListAdapter = new Schedule_CostList_Adapter(getContext(),
						getWorkFactory(), all_data);
				costListAdapter.setMemberPool(mTarget.getLoc_TargetMember()
						.getTargetMemberList());
				costList.setAdapter(costListAdapter);
			} else if (status.equals("expenditure")) {
				costListAdapter = new Schedule_CostList_Adapter(getContext(),
						getWorkFactory(), expenditure_data);
				costListAdapter.setMemberPool(mTarget.getLoc_TargetMember()
						.getTargetMemberList());
				costList.setAdapter(costListAdapter);
			} else if (status.equals("income")) {
				costListAdapter = new Schedule_CostList_Adapter(getContext(),
						getWorkFactory(), income_data);
				costListAdapter.setMemberPool(mTarget.getLoc_TargetMember()
						.getTargetMemberList());
				costList.setAdapter(costListAdapter);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void display(TextView view, float data) {
		DecimalFormat cost = new DecimalFormat();
		String style = "0.00";
		cost.applyPattern(style);
		String tCost = cost.format(data);
		if (data >= 0) {
			tCost = "+" + tCost;
			view.setTextColor(Color.rgb(104, 198, 136));
		} else {
			view.setTextColor(Color.rgb(237, 107, 125));
		}
		view.setText(tCost);
	};

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
			add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(getApplicationContext(),
								Schedule_Cost_Activity.class);
						intent.putExtra("targetId", mTargetId);
						startActivityForResult(intent, 1);
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			all.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						initColor();
						all.setBackgroundColor(Color.WHITE);
						all.setTextColor(Color.BLACK);
						status = "all";
						setData();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			expenditure.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						initColor();
						expenditure.setBackgroundColor(Color.WHITE);
						expenditure.setTextColor(Color.BLACK);
						status = "expenditure";
						setData();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			income.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						initColor();
						income.setBackgroundColor(Color.WHITE);
						income.setTextColor(Color.BLACK);
						status = "income";
						setData();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			costList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Schedule_Bean data = (Schedule_Bean) parent.getAdapter()
							.getItem(position);
					Intent intent = new Intent(getApplicationContext(),
							Schedule_Cost_Details_Activity.class);
					Bundle b = new Bundle();
					b.putString("summary", mTarget.getSummary());
					b.putInt("targetId", mTargetId);
					b.putFloat("cost", data.getCost());
					b.putString("cost_type", data.getCost_type(NoteTagList_Bean.getInstance(Schedule_CostList_Activity.this)));
					b.putString("cost_describe", data.getCost_desc());
					b.putLong("transaction_time", data.getTransaction_time());
					intent.putExtras(b);
					startActivity(intent);
				}
			});
			TagListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					TagAdapter.setSelectIndex(position);
					selectedTagId = TagIds.get(position);
					costListAdapter.setSelectedTagId(selectedTagId);
					TagAdapter.notifyDataSetChanged();
					ScheduleGetList_Event.getCostApply(mTargetId, slectedId,
							selectedTagId);
				}
			});

		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onWorkOk(ReadyGoooWork work) {
		super.onWorkOk(work);
		try {
			if (work instanceof GetTarget_Target_Mode) {
				GetTarget_Target_Mode tWork = (GetTarget_Target_Mode) work;
				// 取出结果
				mTarget = tWork.getTarget();
				// 更新显示
				title.setText(mTarget.getSummary());
				costListAdapter.setMemberPool(mTarget.getLoc_TargetMember()
						.getTargetMemberList());
				// mTarget.getLoc_TargetMember().getMe(getApplicationContext())
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onScheduleChangedReceiver(String id) {
		super.onScheduleChangedReceiver(id);
		try {
			int tLocalId = mTargetId;// 这个Activity显示的目标Id
			int tCloudId = (int) Transformation_Util.String2int(id);// 有变化的目标Id
			if (tLocalId == tCloudId) {
				// 如果Id相同，就刷新
				ScheduleGetList_Event.getCostApply(mTargetId, slectedId, null);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onScheduleCloudChangedReceiver(String id) {
		super.onScheduleCloudChangedReceiver(id);
		try {
			int tLocalId = mTargetId;// 这个Activity显示的目标Id
			int tCloudId = (int) Integer.valueOf(id);// 有变化的目标Id
			if (tLocalId == tCloudId) {
				// 如果Id相同，就刷新
				AirLock_Work.syncSchedule(getApplicationContext(), tLocalId);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void getDataFromBundle() {
		try {
			Bundle bundle = getIntent().getExtras();
			if (bundle == null) {
				bundle = new Bundle();
			}
			mTargetId = bundle.getInt("targetId", 0);
			mTargetTag = bundle.getString("targetTag", "");
			targetId = bundle.getInt("targetStatus",
					TargetRunningState.Running.toInt());
			//发送费用标签的指令
			 Instruction_Utils.sendInstrustion(getApplicationContext(), Instruction_Utils.SYNCHRONOUS_COSTTAG,mTargetId);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:// 点击add按钮的值
			try {
				setData();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	public void onEventMainThread(ScheduleListGetted_Event event) {
		try {
			// 获取到结果
			ArrayList<Schedule_Bean> tNoteTagList = event.getSchdeuleList();
			// 刷新结果
			all_data = new ArrayList<Schedule_Bean>();
			all_data.addAll(tNoteTagList);
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

//	public void onGetTagList() {
//		try {
//			// 获取到结果
//			NoteTagList_Bean tNoteTagList = NoteTagList_Bean.getInstance(Schedule_CostList_Activity.this);
//			TagNames.add("全部");
//			TagIds.add(0);
//			for (int i = 0; i <= tNoteTagList.getTags().size(); i++) {
//				NoteTag_Bean temp = tNoteTagList.getTags().get(i);
//				if (temp != null) {
//					TagNames.add(temp.getTag());
//					TagIds.add(temp.getId());
//				}
//			}
//			TagAdapter.notifyDataSetChanged();
//		} catch (Exception e) {
//			ExceptionHandle.ignoreException(e);
//		}
//	}
	@Override
	public void dealWithRadio(Intent intent) {
		super.dealWithRadio(intent);
		String action = intent.getAction();
		if (action.equals(MessageConstants.REFRESH_COSTTAG)) {
			AirLock_Work.syncScheduleTag();
			Integer id=intent.getIntExtra("target_id", 0);
			if (id==mTargetId) {
				ReadCostTagFromDBTask s=new ReadCostTagFromDBTask();
				s.executeOnExecutor(ThreadPoolUtils_db.threadPool, id);
			}	
		}
	}
	class ReadCostTagFromDBTask extends AsyncTask<Integer, Integer, ArrayList<NoteTag_Pojo>> {

		@Override
		protected ArrayList<NoteTag_Pojo> doInBackground(Integer... params) {
			Integer target_id = params[0];
			 ArrayList<NoteTag_Pojo> ListCostTag= new ArrayList<NoteTag_Pojo>();
			CostTagManager costTagManager = new CostTagManager();
			if (target_id>0) {
				ListCostTag=costTagManager.ReadCostTagFromDBByTargetId(getApplicationContext(), new Integer[] {target_id,0});	
			}else {
				ListCostTag=costTagManager.ReadCostTagFromDBByTargetId(getApplicationContext(), new Integer[] {0});
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
		TagNames.add("全部");
		TagIds.clear();
		TagIds.add(0);
		for (int i = 0; i <SourceDateList.size(); i++) {
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
		TagAdapter.notifyDataSetChanged();
		TagAdapter.setSelectIndex(0);
	}
}

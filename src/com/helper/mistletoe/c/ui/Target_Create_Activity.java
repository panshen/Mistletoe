package com.helper.mistletoe.c.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.adapter.TaskListAdapter;
import com.helper.mistletoe.c.ui.base.Base_Activity;
import com.helper.mistletoe.m.db.impl.AirLock_DB;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.TaskList_Bean;
import com.helper.mistletoe.m.pojo.Task_Bean;
import com.helper.mistletoe.m.pojo.Template_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.pojo.Task_Enum.TaskStatus;
import com.helper.mistletoe.m.work.be.GetTarget_Target_Mode;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.FolderTool_Utils;
import com.helper.mistletoe.util.LogPrint;
import com.helper.mistletoe.util.Prism_Util;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.util.prcomode.ReadyGoooWork;
import com.helper.mistletoe.v.ListViewForScrollView;
import com.helper.mistletoe.v.choosehelper.ChooseHelper;
import com.helper.mistletoe.v.colorswitch.ColorSwitch_LinearLayout;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.SnaBitmap.ImageSize;
import com.helper.mistletoe.v.snaimageview.SnaBitmap.PathType;
import com.helper.mistletoe.v.snaimageview.v2.SnaBitmapV2;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class Target_Create_Activity extends Base_Activity {
	private Switch vTargetIfPermanent;// 是否永久目标
	private Switch vTargetIfOpen;// 是否开放目标
	private LinearLayout vCancel;// 取消按钮
	private LinearLayout vOk;// 确定按钮
	private EditText vTargetTitle;// 目标标题
	private TextView vTargetEndTimeText;// 目标结束时间的显示文字
	private EditText vTargetRemarks;// 目标备注
	private LinearLayout vTargetEndTime_Lin;// 目标结束时间的外框
	private ColorSwitch_LinearLayout vSelectColor;// 颜色选择
	private ChooseHelper vSelectHelper;// 帮手选择
	private SnaImageViewV2 vTargetCover;// 目标封面
	private LinearLayout vSelectColor_Lin;// 颜色选择外框
	private ListViewForScrollView taskList;// task 列表
	private Button addTask;
	private TaskListAdapter taskAdapter;
	private TaskList_Bean mTaskList;
	private ArrayList<Task_Bean> tasks;
	private Template_Bean template = new Template_Bean();

	private Calendar mTargetEndTime;// 目标结束时间
	private Target_Bean mTarget;// 目标
	private int[] mTargetMembers;// 目标成员
	private int mTargetId;// 目标Id
	private String mTargetTag;// 目标Tag
	private SnaBitmap mTargetCoverFile;// 目标Tag
	private File mUploadFile;
	private String operationType = "";
	private LinearLayout addTask_linearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.target_create_layout);
			initView();
			setListener();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {
			// 保存数据
			String tTitle = vTargetTitle.getText().toString();
			String tRemarks = vTargetRemarks.getText().toString();
			JSONObject tJson = new JSONObject();
			tJson.put("title", tTitle);
			tJson.put("remarks", tRemarks);
			saveData(tJson);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setData() {
		try {
			// 显示日期
			if (vTargetIfPermanent.isChecked()) {
				// 永久
				vTargetEndTimeText.setText("永不过期");
			} else {
				// 限时
				vTargetEndTimeText.setText(TimeTool_Utils.fromateTimeShow(mTargetEndTime.getTime().getTime(), "yyyy年MM月dd日"));
			}
			// 显示封面
			vTargetCover.setImageForShow(mTargetCoverFile);
			// 显示成员
			uds_MemberShow();
			// 显示任务进度条
			if (mTaskList.getList().size() < 1) {// 没有任务
				taskList.setVisibility(ListViewForScrollView.GONE);
			} else {
				taskList.setVisibility(ListViewForScrollView.VISIBLE);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView() {
		try {
			// 初始化控件
			vTargetTitle = (EditText) findViewById(R.id.main_targetName);
			vTargetEndTimeText = (TextView) findViewById(R.id.main_showEndTime);
			vTargetRemarks = (EditText) findViewById(R.id.main_Tag);
			vCancel = (LinearLayout) findViewById(R.id.targetcreate_bt_deleted_linear);
			vOk = (LinearLayout) findViewById(R.id.targetcreate_bt_add_linear);
			vTargetEndTime_Lin = (LinearLayout) findViewById(R.id.main_showEndTimeLinear);
			vSelectColor = (ColorSwitch_LinearLayout) findViewById(R.id.main_Color);
			vTargetIfPermanent = (Switch) findViewById(R.id.main_PermanentTarget);
			vTargetIfOpen = (Switch) findViewById(R.id.main_OpenTarget);
			vSelectHelper = (ChooseHelper) findViewById(R.id.main_progressBarwssssssss);
			vTargetCover = (SnaImageViewV2) findViewById(R.id.main_Cover);
			vSelectColor_Lin = (LinearLayout) findViewById(R.id.main_SwitchColor_Lin);
			taskList = (ListViewForScrollView) findViewById(R.id.target_create_layout_task_list_listview);
			addTask_linearLayout = (LinearLayout) findViewById(R.id.target_create_layout_linearLout_addTask);
			addTask = (Button) findViewById(R.id.target_create_layout_addTask);
			// 初始化成员变量
			mTargetEndTime = Calendar.getInstance();
			mTarget = new Target_Bean();
			mTaskList = new TaskList_Bean();
			tasks = new ArrayList<Task_Bean>();
			mTargetMembers = new int[] {};
			mTargetId = -1;
			mTargetTag = "";
			mTargetCoverFile = new SnaBitmapV2();
			// 其他初始化工作
			// 加载缓存数据
			loadCached();
			// 从上一个页面获得参数
			getDataFromBundle();
			// 设置创建者
			User_Bean tUser = new User_Bean();
			tUser.readData(getApplicationContext());
			TargetMember_Bean tCreater = new TargetMember_Bean();
			Prism_Util.depthCopyData(new JSONObject(new Gson().toJson(tUser)), tCreater);
			vSelectHelper.setCreater(tCreater);
			// 从数据库中读取Target
			if (mTargetId > 0) {
				GetTarget_Target_Mode t_WorkOj = new GetTarget_Target_Mode(mTargetId, mTargetTag);
				// 发布任务
				t_WorkOj.publishWork(getWorkFactory());
			}
			// 显示操作提示页
			JSONObject tCache = readData();
			boolean tNeverTip = false;
			if (tCache.has("neverTip")) {
				tNeverTip = tCache.getBoolean("neverTip");
			}
			if (!tNeverTip) {
				//opac_OperationTips();
			}
			// 设置默认封面
			uds_CoverHight();
			vTargetCover.setDefaultImageForShow(R.drawable.add_cover);
			// 初始时间修改
			mTargetEndTime.setTimeInMillis(mTargetEndTime.getTime().getTime() + (7 * 24 * 60 * 60 * 1000));
			//显示传递数据
			if (operationType.equals("Update")) {
				addTask_linearLayout.setVisibility(LinearLayout.GONE);
			} else {
				addTask_linearLayout.setVisibility(LinearLayout.VISIBLE);
			}
			if (operationType.equals("Cloning")) {
				vTargetTitle.setText(mTarget.getSummary());
				if (mTarget.getView_flag()==0) {//0为隔离模式
					vTargetIfOpen.setChecked(true);
				}else {
					vTargetIfOpen.setChecked(false);
				}
				vSelectColor.setColor(mTarget.getTarget_flag());
				vTargetCover.setImageForShow(mTarget.getShowImageId(), ImageSize.Normal.toInt());
				vTargetRemarks.setText(mTarget.getDescription());
			}
			taskAdapter = new TaskListAdapter(Target_Create_Activity.this, mTaskList.getList());
			taskList.setAdapter(taskAdapter);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void loadCached() {
		// 读出数据
		try {
			String tTitle = "";
			String tRemarks = "";
			JSONObject tJson = readData();
			if (tJson.has("title")) {
				tTitle = tJson.getString("title");
			}
			if (tJson.has("remarks")) {
				tRemarks = tJson.getString("remarks");
			}
			vTargetTitle.setText(tTitle);
			vTargetRemarks.setText(tRemarks);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	protected void setListener() {
		try {
			vSelectHelper.setOnChooseHelperClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						opac_TargetMember();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vTargetIfPermanent.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					try {
						setData();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						finish();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vOk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						if (vTargetIfOpen.isChecked()) {
							// m_Target.setView_flag(0);// 隔离
							showDialog_CreateTik();
						} else {
							// m_Target.setView_flag(1);// 公开
							task_CreateTarget();
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vTargetEndTime_Lin.setOnClickListener(new OnClickListener() {
				private OnDateSetListener mDateSetListener = new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
						try {
							// 设置后的时间
							mTargetEndTime.set(arg1, arg2, arg3);
							if (mTargetEndTime.getTime().getTime() < TimeTool_Utils.getNowTime()) {
								mTargetEndTime = Calendar.getInstance();
							}
							setData();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				};

				@Override
				public void onClick(View v) {
					try {
						// 弹出日期对话框
						Dialog datelg = new DatePickerDialog(Target_Create_Activity.this, mDateSetListener, mTargetEndTime.get(Calendar.YEAR), mTargetEndTime.get(Calendar.MONTH), mTargetEndTime
								.get(Calendar.DATE));
						if (!vTargetIfPermanent.isChecked()) {
							datelg.show();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			vTargetCover.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(Intent.ACTION_PICK, null);
						intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
						startActivityForResult(intent, 777 + 1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		taskList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Task_Bean task_Bean = (Task_Bean) parent.getAdapter().getItem(position);
				Intent task_intent = new Intent(getApplicationContext(), TaskUpdateDialogActivity.class);
				task_intent.putExtra("taskSource", "CREATE");
				task_intent.putExtra("taskOwner", task_Bean.getOwner());
				task_intent.putExtra("taskOwnerId", task_Bean.getOwner_id());
				task_intent.putExtra("taskPosition", position);
				task_intent.putExtra("taskId", task_Bean.getTask_id());
				task_intent.putExtra("taskStatus", task_Bean.getStatus().toInt());
				task_intent.putExtra("taskContent", task_Bean.getDescription());
				task_intent.putExtra("taskTargetId", task_Bean.getLoc_TargetId());
				task_intent.putExtra("taskWeights", task_Bean.getWeights());
				task_intent.putExtra("taskBeginTime", task_Bean.getBegin_time());
				task_intent.putExtra("taskEndTime", task_Bean.getEnd_time());
				task_intent.putExtra("mTargetMembers", mTargetMembers);
				startActivityForResult(task_intent, 5);
			}
		});
		addTask.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), TaskCreateDialogActivity.class);
				intent.putExtra("taskSource", "CREATE");
				intent.putExtra("targetId", mTargetId);
				intent.putExtra("mTargetMembers", mTargetMembers);
				startActivityForResult(intent, 6);
			}
		});
	}

	protected void getDataFromBundle() {
		try {
			Bundle bundle = getIntent().getExtras();
			if (bundle == null) {
				bundle = new Bundle();
			}

			if (bundle.containsKey("memberId")) {
				ArrayList<Integer> s = getIntent().getIntegerArrayListExtra("memberId");
				mTargetMembers = new int[s.size()];
				for (int tI = 0; tI < s.size(); tI++) {
					getMembers()[tI] = s.get(tI);
				}
			}
			if (bundle.containsKey("targetId")) {
				mTargetId = bundle.getInt("targetId", 0);
			}
			if (bundle.containsKey("targetTag")) {
				mTargetTag = bundle.getString("targetTag", "");
			}
			if (bundle.containsKey("type")) {
				operationType = bundle.getString("type", "Create");
			}
			// 传递template选中的模板
			if (bundle.containsKey("Template")) {
				template = bundle.getParcelable("Template");
			}
			if (bundle.containsKey("data")) {
				// 解析cloning传递来的Target
				String target_date = bundle.getString("data");
				Gson gson = new Gson();
				JSONObject json = new JSONObject(target_date);
				mTarget = gson.fromJson(json.toString(), Target_Bean.class);
				JSONArray task_data = new JSONArray(json.getString("task"));
				for (int i = 0; i < task_data.length(); i++) {
					JSONObject j = task_data.getJSONObject(i);
					Task_Bean task = new Task_Bean();
					Gson gsons = new Gson();
					task = gsons.fromJson(j.toString(), Task_Bean.class);
					tasks.add(task);
				}
			}
			// 解析出模板中含的task
			try {
				JSONArray jsonArray = new JSONArray(template.getTemplate_task());
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject json = jsonArray.getJSONObject(i);
					Task_Bean task = new Task_Bean();
					Gson gson = new Gson();
					task = gson.fromJson(json.toString(), Task_Bean.class);
					tasks.add(task);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			mTaskList.setList(tasks);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onWorkOk(ReadyGoooWork work) {
		super.onWorkOk(work);
		try {
			if (work instanceof GetTarget_Target_Mode) {
				GetTarget_Target_Mode t_Work = (GetTarget_Target_Mode) work;
				// 修改本页的Target
				mTarget = t_Work.getTarget();
				// 封面
				vTargetCover.setImageForShow(mTarget.getShowImageId(), ImageSize.Normal.toInt());
				// 目标名
				vTargetTitle.setText(mTarget.getSummary());
				// 备注
				vTargetRemarks.setText(mTarget.getDescription());
				// 目标结束时间
				long tEndTime = mTarget.getEta_time();
				if (tEndTime < 1L) {
					tEndTime = Calendar.getInstance().getTime().getTime();
				} else {
					tEndTime *= 1000L;
				}
				mTargetEndTime.setTimeInMillis(tEndTime);
				// 已选择的帮手
				int[] tMemberIds = new int[mTarget.getLoc_TargetMember().getHelper().size()];
				for (int tI = 0; tI < tMemberIds.length; tI++) {
					tMemberIds[tI] = mTarget.getLoc_TargetMember().getHelper().get(tI).getMember_id();
				}
				if (tMemberIds.length > 0) {
					mTargetMembers = tMemberIds;
				}
				// 显示是隔离模式还是
				if (((int) mTarget.getView_flag()) == Target_Bean.TARGET_VIEWFLAG_ISOLATION) {
					vTargetIfOpen.setChecked(true);
				} else {
					vTargetIfOpen.setChecked(false);
				}
				// 显示是否永久目标
				if (((long) mTarget.getEta_time()) < 1L) {
					vTargetIfPermanent.setChecked(true);
				} else {
					vTargetIfPermanent.setChecked(false);
				}
				// 如果是修改，去掉一些不应该显示的控件
				if (((int) mTarget.getTarget_id()) > 0) {
					vSelectHelper.setVisibility(ChooseHelper.GONE);
					vSelectColor_Lin.setVisibility(ColorSwitch_LinearLayout.GONE);
				}
				// 更新显示
				setData();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			switch (requestCode) {
			case 5:// 修改
				switch (resultCode) {
				case 1:
					int position = data.getIntExtra("taskPosition", -1);
					boolean isDeleted = data.getBooleanExtra("isDeleted", false);
					if (isDeleted) {
						if (position >= 0) {
							tasks.remove(position);
							mTaskList.setList(tasks);
							taskAdapter.notifyDataSetChanged();
						}
					} else {
						if (position >= 0) {
							tasks.get(position).setOwner_id(data.getIntExtra("taskOwnerId", 0));
							tasks.get(position).setWeights(data.getIntExtra("taskWeights", 1));
							tasks.get(position).setDescription(data.getStringExtra("taskContent"));
							tasks.get(position).setStatus(TaskStatus.valueOf(getIntent().getIntExtra("taskStatus", TaskStatus.Draft.toInt())));
							if (data.getLongExtra("taskBeginTime", 0) > 0) {
								tasks.get(position).setBegin_time((data.getLongExtra("taskBeginTime", 0)));
							} else {
								tasks.get(position).setBegin_time(null);
							}
							if (data.getLongExtra("taskEndTime", 0) > 0) {
								tasks.get(position).setEnd_time((data.getLongExtra("taskEndTime", 0)));
							} else {
								tasks.get(position).setEnd_time(null);
							}
							mTaskList.setList(tasks);
							taskAdapter.notifyDataSetChanged();
						}
					}
					break;

				default:
					break;
				}
				break;
			case 6:// 添加
				switch (resultCode) {
				case 1:
					Task_Bean tempTask = new Task_Bean();
					tempTask.setWeights(data.getIntExtra("taskWeights", 1));
					tempTask.setDescription(data.getStringExtra("taskContent"));
					tempTask.setOwner_id(data.getIntExtra("taskOwnerId", -1));
					if (data.getLongExtra("taskBeginTime", -1) > 0) {
						tempTask.setBegin_time((data.getLongExtra("taskBeginTime", -1)));
					} else {
						tempTask.setBegin_time(null);
					}
					if (data.getLongExtra("taskEndTime", -1) > 0) {
						tempTask.setEnd_time((data.getLongExtra("taskEndTime", -1)));
					} else {
						tempTask.setEnd_time(null);
					}
					tasks.add(tempTask);
					mTaskList.setList(tasks);
					taskAdapter.notifyDataSetChanged();
					break;

				default:
					break;
				}
				break;
			case 777 + 1: {
				switch (resultCode) {
				case Activity.RESULT_OK:
					Intent intent = new Intent("com.android.camera.action.CROP");
					intent.setDataAndType(data.getData(), "image/*");
					// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
					intent.putExtra("crop", "true");
					// aspectX aspectY 是宽高的比例
					intent.putExtra("aspectX", 250);
					intent.putExtra("aspectY", 154);
					// outputX outputY 是裁剪图片宽高
					intent.putExtra("outputX", 250);
					intent.putExtra("outputY", 154);
					intent.putExtra("return-data", false);
					mUploadFile = new File(FolderTool_Utils.getAFE_ImageZoom() + "/CaiJian" + TimeTool_Utils.getNowTime() + ".jpg");// 以时间秒为文件名
					mUploadFile.createNewFile();
					intent.putExtra("output", Uri.fromFile(mUploadFile)); // 专入目标文件
					intent.putExtra("outputFormat", "JPEG");// 输入文件格式
					startActivityForResult(intent, 777 + 2);
					break;
				}
			}
				break;
			case 777 + 2: {
				switch (resultCode) {
				case Activity.RESULT_OK:
					mTargetCoverFile.setPath(mUploadFile);
					setData();
					break;
				}
			}
				break;
			case 892 + 0: {
				switch (resultCode) {
				case 1:
					ArrayList<Integer> s = data.getIntegerArrayListExtra("memberId");
					mTargetMembers = new int[s.size()];
					for (int tI = 0; tI < s.size(); tI++) {
						getMembers()[tI] = s.get(tI);
					}
					LogPrint.printString_V("UI Log", new Gson().toJson(getMembers()));
					break;
				}
			}
				break;
			case 892 + 1: {
				switch (resultCode) {
				case 772 + 0:
					boolean tNeverTip = false;
					if (data != null) {
						tNeverTip = data.getBooleanExtra("neverTip", false);
					}
					if (tNeverTip) {
						JSONObject tJsonObject = new JSONObject();
						tJsonObject.put("neverTip", (boolean) tNeverTip);
						saveData(tJsonObject);
					}
					break;
				}
			}
				break;
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void task_CreateTarget() {
		try {
			// summary
			mTarget.setSummary(vTargetTitle.getText().toString());
			// view_flag
			if (vTargetIfOpen.isChecked()) {
				mTarget.setView_flag(0);// 隔离
			} else {
				mTarget.setView_flag(1);// 公开
			}
			// target_flag
			mTarget.setTarget_flag(vSelectColor.getColor());
			// eta_time
			if (vTargetIfPermanent.isChecked()) {
				mTarget.setEta_time(0L);
			} else {
				mTarget.setEta_time(mTargetEndTime.getTime().getTime() / 1000);
			}
			// description
			mTarget.setDescription(vTargetRemarks.getText().toString());
			// 目标成员
			JSONArray tMembers = new JSONArray();
			for (int tI : getMembers()) {
				tMembers.put(tI);
			}
			mTarget.setTasks(tasks);
			mTarget.setLoc_MembersString(tMembers.toString());
			// 目标封面
			String tCover = "";
			if (PathType.valueOf(mTargetCoverFile.getType()) == PathType.File) {
				tCover = mTargetCoverFile.getPath_File().getAbsolutePath();
			} else {
				tCover = null;
			}

			boolean workCanCom = true;
			if (mTarget.getSummary().trim().equals("")) {
				workCanCom = false;
				showToast("要创建目标，首先你得写个标题");
			}
			if (workCanCom) {
				if (mTarget.getTarget_id() > 0) {
					mTarget.updateCloud(getContext(), tCover);
				} else {
					mTarget.createTarget_Cloud(getContext(), tCover);
				}
				vTargetTitle.setText("");
				vTargetRemarks.setText("");
				finish();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void opac_TargetMember() {
		try {
			Intent intent = new Intent(this, SelectTargetMemberActivity.class);
			intent.putExtra("targetId", (int) mTargetId);
			intent.putExtra("targetTag", (String) mTargetTag);
			intent.putExtra("sna.std.private.Members", (String) new Gson().toJson(getMembers()));
			startActivityForResult(intent, 892 + 0);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * @deprecated
	 * */
	private void opac_OperationTips() {
		try {
			Intent intent = new Intent(this, OperationTipsActivity.class);
			//intent.putExtra("imageResources", (int) R.drawable.operationtips_createtarget);
			startActivityForResult(intent, 892 + 1);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void uds_MemberShow() {
		try {
			if (getMembers().length > 0) {
				// 从数据库中读取帮手信息
				ArrayList<Helpers_Sub_Bean> tMembers = AirLock_DB.select_helperList(this.getApplicationContext(), getMembers());
				ArrayList<TargetMember_Bean> tMemberList = new ArrayList<TargetMember_Bean>();
				for (Helpers_Sub_Bean i : tMembers) {
					TargetMember_Bean temp = new TargetMember_Bean();
					temp.copyData(i);
					tMemberList.add(temp);
				}
				// 显示给控件
				vSelectHelper.setHelper(tMemberList);
			} else {
				vSelectHelper.setHelper(new ArrayList<TargetMember_Bean>());
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public int[] getMembers() {
		if (mTargetMembers == null) {
			mTargetMembers = new int[] {};
		}
		return mTargetMembers;
	}

	protected void showDialog_CreateTik() {
		try {
			String tShowText = "非公开模式的目标中，参与目标的人相互之间不能看见对方的备注，只有目标创建人可以看到所有的备注。适用于任务分配，信息发布等场景。";
			showDialog(tShowText, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						dialog.dismiss();
						task_CreateTarget();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void uds_CoverHight() {
		try {
			WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
			int tWidth = wm.getDefaultDisplay().getWidth();
			LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) vTargetCover.getLayoutParams();
			// 取控件aaa当前的布局参数
			linearParams.height = (int) (tWidth * 0.618F); // 当控件的高强制设成365象素
			vTargetCover.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件aaa
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
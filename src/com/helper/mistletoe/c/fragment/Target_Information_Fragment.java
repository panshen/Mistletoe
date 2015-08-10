package com.helper.mistletoe.c.fragment;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.fragment.base.TargetDetailFragment;
import com.helper.mistletoe.c.task.TargetShareByTargetIdTask;
import com.helper.mistletoe.c.ui.HelperDetailsActivity;
import com.helper.mistletoe.c.ui.Schedule_CostList_Activity;
import com.helper.mistletoe.c.ui.Target_Create_Activity;
import com.helper.mistletoe.c.ui.Target_Details_Activity;
import com.helper.mistletoe.c.ui.TaskCreateDialogActivity;
import com.helper.mistletoe.c.ui.Target_Details_Activity.DetailViewType;
import com.helper.mistletoe.c.ui.Target_Details_Activity.TargetMarketList;
import com.helper.mistletoe.c.ui.Target_TargetMember_Activity;
import com.helper.mistletoe.c.ui.TaskUpdateDialogActivity;
import com.helper.mistletoe.c.ui.adapter.TaskListAdapter;
import com.helper.mistletoe.c.ui.base.Base_Activity.ChangeMarketPosition;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.UpdateStatusRequestNumber;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRunningState;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetType;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.TaskList_Bean;
import com.helper.mistletoe.m.pojo.Task_Bean;
import com.helper.mistletoe.m.pojo.Task_Enum.TaskStatus;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.m.work.base.inter.WorkCallBack_Mode;
import com.helper.mistletoe.m.work.be.ScheduleGetList_Event;
import com.helper.mistletoe.m.work.be.TargetGet_Event;
import com.helper.mistletoe.m.work.ui.GetTargetMemberList_Target_Mode;
import com.helper.mistletoe.m.work.ui.ScheduleListGetted_Event;
import com.helper.mistletoe.m.work.ui.TargetGetted_Event;
import com.helper.mistletoe.m.work.ui.TaskGetted_Event;
import com.helper.mistletoe.m.work.ui.TaskOnChange_Event;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.FolderTool_Utils;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.util.Tool_Utils;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.util.prcomode.ReadyGoooWork;
import com.helper.mistletoe.v.ListViewForScrollView;
import com.helper.mistletoe.v.choosehelper.ChooseHelper;
import com.helper.mistletoe.v.colorswitch.ColorSwitch_LinearLayout;
import com.helper.mistletoe.v.snaimageview.SnaBitmap.ImageSize;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class Target_Information_Fragment extends TargetDetailFragment implements
		ChangeMarketPosition {
	private String DEBUG_TAG = "com.helper.mistletoe.c.fragment.Target_Information_Fragment";
	private ScrollView scrollView;
	private ArrayList<Schedule_Bean> all_data = new ArrayList<Schedule_Bean>();
	private ArrayList<Schedule_Bean> expenditure_data = new ArrayList<Schedule_Bean>();
	private ArrayList<Schedule_Bean> income_data = new ArrayList<Schedule_Bean>();
	private float expenditure_cost, income_cost;
	private TableRow tableLayoutCost, tableLayoutWeight;
	private Button addTask;
	private TextView cost_up, cost_down;
	private TextView cost_detail;// 费用详情
	private TextView vTime_endTime;// 结束时间
	private TextView vTime_createTime;// 创建时间
	private TextView vTargetRemarks;// 目标备注
	private ColorSwitch_LinearLayout vSwitchColor;// 颜色选择
	private ChooseHelper vSwitchHelper;// 帮手选择
	private ProgressBar vCostUpProgress, vCostDownProgress;// 费用进度条
	private ProgressBar vDateProgress;// 日期进度条
	private ProgressBar vWeightProgress;// 权重进度条
	private TextView weight;
	private ListViewForScrollView taskList;// task 列表
	private TaskListAdapter taskAdapter;
	private LinearLayout vFriendlyTarget_Lin;// 同意拒绝按钮的外框
	private Button vRefuseButton;// 拒绝按钮
	private Button vAgreeButton;// 同意按钮
	private LinearLayout vMarketTarget_Lin;// 目标市场目标按钮的外框
	private ImageButton vLastButton;// 上一个目标
	private Button vJoinButton;// 关注目标
	private ImageButton vNextButton;// 下一个目标
	private SnaImageViewV2 vTargetCover;// 目标封面
	private TextView vTargetTypeTip;// 目标类型提示
	private ImageView vAppreciate;// 赞
	private TextView vAppreciateNumber;// 赞的数量
	private RelativeLayout vTargetCover_Lin;// 目标封面的外框
	private LinearLayout vAppreciate_Lin;// 点赞的外框

	private int mTargetId;// 目标Id
	private String mTargetTag;// 目标Tag
	private Target_Bean mTarget;// 目标
	private int mMarketPosition;// 这条记录的位置
	private DetailViewType mViewType;// 这条记录的类型
	private File mUploadFile;
	private TaskList_Bean mTaskList;
	private GetTargetMemberList_Target_Mode work_GetTargetMember;// Mode：获取TargetMember
	public static boolean isEdited = true;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.target_information_layout, null);
		try {
			initView(v);
			setListener();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return v;
	}

	@Override
	public void onResume() {
		try {
			setData();
			if (getActivity() instanceof Target_Details_Activity) {
				Target_Details_Activity fActivity = (Target_Details_Activity) (getActivity());
				fActivity.setTitleTag();
				fActivity.setMenu();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		super.onResume();
	}

	@Override
	public void onPause() {
		try {
			// 如果修改了颜色，把改动上传到服务器
			if (mTarget.getTarget_flag() != vSwitchColor.getColor()) {
				mTarget.updateSettingsCloud(getActivity()
						.getApplicationContext(), null,
						"" + vSwitchColor.getColor(), null);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		super.onPause();
	}

	protected void setData() {
		try {
			if (mTarget != null) {
				if (mTarget.getStatus() == TargetRunningState.Running) {
					isEdited = true;
				} else {
					isEdited = false;
				}
				// 显示时间进度条
				long startTime = Long.valueOf(mTarget.getCreate_time()) * 1000;
				long endTime = Long.valueOf(mTarget.getEta_time()) * 1000;
				if (endTime < 1L) {// 永久目标
					vDateProgress.setProgress(0);
					vTime_endTime.setText("永久目标");
					if (startTime > TimeTool_Utils.getNowTime()) {// 开始时间大于当前时间
						vTime_createTime.setText("此目标尚未开始");
					} else {// 开始时间小于当前时间
						vTime_createTime.setText("目标开始于:"
								+ TimeTool_Utils.fromateTimeShow(startTime,
										"yyyy-MM-dd"));
					}
				} else {// 非永久目标
					if (startTime > TimeTool_Utils.getNowTime()) {// 开始时间大于当前时间
						vDateProgress.setProgress(0);
						vTime_createTime.setText("此目标尚未开始");
						vTime_endTime.setText(TimeTool_Utils.fromateTimeShow(
								endTime, "yyyy-MM-dd"));
					} else if (startTime <= TimeTool_Utils.getNowTime()
							&& endTime >= TimeTool_Utils.getNowTime()) {// 开始时间小于当前时间，且
																		// 结束时间大于当前时间
						long tSum = endTime - startTime;
						long tPasted = TimeTool_Utils.getNowTime() - startTime;
						int tProgress = (int) ((tPasted * 100) / (tSum));
						vDateProgress.setProgress((int) tProgress);
						vTime_endTime.setText(TimeTool_Utils.fromateTimeShow(
								endTime, "yyyy-MM-dd"));
						vTime_createTime.setText("今日:"
								+ TimeTool_Utils.fromateTimeShow(
										TimeTool_Utils.getNowTime(),
										"yyyy-MM-dd"));
					} else {// 结束时间小于当前时间
						long tSum = TimeTool_Utils.getNowTime() - startTime;
						long tPasted = TimeTool_Utils.getNowTime() - endTime;
						long days = tPasted / (1000 * 60 * 60 * 24);
						long hours = (tPasted - days * (1000 * 60 * 60 * 24))
								/ (1000 * 60 * 60);
						// long minutes = (tPasted-days*(1000 * 60 * 60 *
						// 24)-hours*(1000* 60 * 60))/(1000* 60);
						int tProgress = (int) ((tPasted * 100) / (tSum));
						vDateProgress.setProgress((int) tProgress);
						vTime_createTime.setText("原定:"
								+ TimeTool_Utils.fromateTimeShow(endTime,
										"yyyy-MM-dd"));
						vTime_endTime.setText("逾期:" + days + "天" + hours + "时");
					}
				}
				vTargetRemarks.setText(mTarget.getDescription());
				vSwitchColor.setColor(mTarget.getTarget_flag());
				// 显示任务进度条
				if (mTaskList.getList().size() < 1) {// 没有任务
					tableLayoutWeight.setVisibility(TableLayout.GONE);
					taskList.setVisibility(ListViewForScrollView.GONE);
				} else {
					if (mTaskList.getAllWeight() < 1) {// 有任务但是全部被删除
						tableLayoutWeight.setVisibility(TableLayout.GONE);
						taskList.setVisibility(ListViewForScrollView.VISIBLE);
					} else {
						tableLayoutWeight.setVisibility(TableLayout.VISIBLE);
						taskList.setVisibility(ListViewForScrollView.VISIBLE);
						int taskProgress = (int) (mTaskList.getCompleteWeight() * 100.0D / mTaskList
								.getAllWeight());
						vWeightProgress.setProgress((int) (taskProgress));
						weight.setText("任务已完成：" + taskProgress + "%");
					}
				}
				// 设置是否显示同意拒绝
				uds_FloatButton();
				// 设置创建者
				vSwitchHelper.setCreater(mTarget.getLoc_TargetMember()
						.getOwner());
				// 设置接受者
				HashSet<TargetMember_Bean> tReciver = new HashSet<TargetMember_Bean>();
				{
					tReciver.addAll(mTarget.getLoc_TargetMember().getHelper());
					for (Iterator<TargetMember_Bean> iterator = tReciver
							.iterator(); iterator.hasNext();) {
						TargetMember_Bean temp = iterator.next();
						if (temp.getMember_status() == MemberStatus.UnWatch) {
							iterator.remove();
						}
					}
				}
				vSwitchHelper.getHelper().clear();
				vSwitchHelper.setHelper(tReciver);
				// 显示封面
				vTargetCover.setImageForShow(mTarget.getShowImageId(),
						ImageSize.Normal.toInt());
				// 显示赞的数量
				vAppreciateNumber.setText("" + (int) mTarget.getAttitude());
				// 显示类型提示文字
				if (mTarget.getView_flag() == Target_Bean.TARGET_VIEWFLAG_OPEN) {
					vTargetTypeTip.setText("此目标为公开讨论型目标");
				} else {
					vTargetTypeTip.setText("");
				}
				taskAdapter.notifyDataSetChanged();
			}
			setFatherTitle();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView(View v) {
		try {
			// 初始化控件
			scrollView = (ScrollView) v
					.findViewById(R.id.target_information_scrollView);
			scrollView.smoothScrollTo(0, 0);
			tableLayoutCost = (TableRow) v
					.findViewById(R.id.target_information_layout_tableRow_cost);
			tableLayoutWeight = (TableRow) v
					.findViewById(R.id.target_information_layout_tableRow_weight);
			cost_detail = (TextView) v
					.findViewById(R.id.target_information_layout_textview_cost_details);
			cost_up = (TextView) v
					.findViewById(R.id.target_information_layout_textview_cost_up);
			cost_down = (TextView) v
					.findViewById(R.id.target_information_layout_textview_cost_down);
			vTime_endTime = (TextView) v
					.findViewById(R.id.target_information_layout_textview_time);
			vTime_createTime = (TextView) v
					.findViewById(R.id.target_information_layout_textview_time_down);
			vTargetRemarks = (TextView) v.findViewById(R.id.main_TargetContext);
			vSwitchColor = (ColorSwitch_LinearLayout) v
					.findViewById(R.id.main_Color);
			vSwitchHelper = (ChooseHelper) v
					.findViewById(R.id.main_progressBarwssssssss);
			vRefuseButton = (Button) v.findViewById(R.id.main_TargetRefuse);
			vAgreeButton = (Button) v.findViewById(R.id.main_TargetAgree);
			vCostUpProgress = (ProgressBar) v
					.findViewById(R.id.target_information_layout_progressBar_cost_up);
			vCostDownProgress = (ProgressBar) v
					.findViewById(R.id.target_information_layout_progressBar_cost_down);
			vDateProgress = (ProgressBar) v
					.findViewById(R.id.target_information_layout_progressBar_time);
			vWeightProgress = (ProgressBar) v
					.findViewById(R.id.target_information_layout_progressBar_weight);
			weight = (TextView) v
					.findViewById(R.id.target_information_layout_textview_weight);
			vFriendlyTarget_Lin = (LinearLayout) v
					.findViewById(R.id.main_FriendlyTarget_Lin);
			vMarketTarget_Lin = (LinearLayout) v
					.findViewById(R.id.main_MarketTarget_Lin);
			vLastButton = (ImageButton) v.findViewById(R.id.main_TargetLast);
			vJoinButton = (Button) v.findViewById(R.id.main_TargetJoin);
			vNextButton = (ImageButton) v.findViewById(R.id.main_TargetNext);
			vTargetCover = (SnaImageViewV2) v.findViewById(R.id.main_Cover);
			vTargetTypeTip = (TextView) v.findViewById(R.id.main_TargetTypeTip);
			vAppreciate = (ImageView) v.findViewById(R.id.main_Appreciate);
			vAppreciateNumber = (TextView) v
					.findViewById(R.id.main_AppreciateNumber);
			vTargetCover_Lin = (RelativeLayout) v
					.findViewById(R.id.main_Cover_Lin);
			vAppreciate_Lin = (LinearLayout) v
					.findViewById(R.id.main_Appreciate_Lin);
			taskList = (ListViewForScrollView) v
					.findViewById(R.id.task_list_listview);
			addTask = (Button) v.findViewById(R.id.target_information_addTask);
			// 初始化成员变量
			mTarget = new Target_Bean();
			mTaskList = new TaskList_Bean();
			// 从上一个页面获得参数
			getDataFromBundle();
			// 其他初始化工作
			initView_Other();
			ScheduleGetList_Event.getCostApply(mTargetId, null, null);
			taskAdapter = new TaskListAdapter(getActivity(),
					mTaskList.getList());
			taskList.setAdapter(taskAdapter);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView_Other() {
		try {
			task_GetTarget();
			AirLock_Work.syncTargetMember(getContext(), mTargetId);
			AirLock_Work.syncTask(mTargetId);
			TaskList_Bean.getTaskList(mTargetId);
			// 更改封面区域大小
			uds_CoverHight();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setListener() {
		try {
			vAgreeButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						if (mTarget.getStatus_server() == MemberStatus.Discussing) {
							mTarget.updateStatusCloud(getActivity(),
									UpdateStatusRequestNumber.HELPER_AGREE,
									null);
						} else if (mTarget.getStatus_server() == MemberStatus.OwnerApplyClose) {
							mTarget.updateStatusCloud(
									getActivity(),
									UpdateStatusRequestNumber.HELPER_AGREE_CLOSE,
									null);
						}
						uds_FloatButton_HideAll();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vRefuseButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						if (mTarget.getStatus_server() == MemberStatus.Discussing) {
							mTarget.updateStatusCloud(getActivity(),
									UpdateStatusRequestNumber.HELPER_REJECT,
									null);
						} else if (mTarget.getStatus_server() == MemberStatus.OwnerApplyClose) {
							mTarget.updateStatusCloud(
									getActivity(),
									UpdateStatusRequestNumber.HELPER_REJECT_CLOSE,
									null);
						}
						uds_FloatButton_HideAll();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vSwitchHelper.setOnChooseHelperClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						opac_TargetMember();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vSwitchHelper.setOnCreaterClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent_helperDetails = new Intent(getContext(),
								HelperDetailsActivity.class);
						intent_helperDetails.putExtra("helper", mTarget
								.getLoc_TargetMember().getOwner());
						getContext().startActivity(intent_helperDetails);
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vAppreciate_Lin.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						if (vAppreciate.getVisibility() == ImageView.VISIBLE) {
							vAppreciate.setVisibility(ImageView.INVISIBLE);
							mTarget.attitudeThis(getContext());
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vTargetCover.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						if (mTarget.userIsCreater(getContext())) {
							Intent intent = new Intent(Intent.ACTION_PICK, null);
							intent.setDataAndType(
									MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
									"image/*");
							startActivityForResult(intent, 777 + 1);
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vJoinButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						String tOperationType = vJoinButton.getText()
								.toString();
						if (tOperationType.equals("关注目标")) {
							mTarget.watchThis(getContext());
						} else if (tOperationType.equals("取消关注")) {
							mTarget.unWatchThis(getContext());
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vLastButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						TargetMarketList tList = new PosterFragment();
						Target_Bean target = tList
								.getRandomTarget(mMarketPosition - 1);
						if (target != null) {
							if (getContext() instanceof ChangeMarketPosition) {
								ChangeMarketPosition temp = (ChangeMarketPosition) getContext();
								temp.changeMarketPosition(
										target.getTarget_id(),
										mMarketPosition - 1);
							}
						} else {
							showToast("已经到头了");
							vLastButton.setVisibility(ImageButton.INVISIBLE);
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vNextButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						TargetMarketList tList = new PosterFragment();
						Target_Bean target = tList
								.getRandomTarget(mMarketPosition + 1);
						if (target != null) {
							if (getContext() instanceof ChangeMarketPosition) {
								ChangeMarketPosition temp = (ChangeMarketPosition) getContext();
								temp.changeMarketPosition(
										target.getTarget_id(),
										mMarketPosition + 1);
							}
						} else {
							showToast("已经到头了");
							vNextButton.setVisibility(ImageButton.INVISIBLE);
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			cost_detail.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent_cost_detail = new Intent(getActivity()
							.getApplicationContext(),
							Schedule_CostList_Activity.class);
					intent_cost_detail.putExtra("targetId", mTargetId);
					intent_cost_detail.putExtra("targetTag", mTargetTag);
					intent_cost_detail.putExtra("summary", mTarget.getSummary());
					startActivity(intent_cost_detail);
				}
			});
			taskList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Task_Bean task_Bean = (Task_Bean) parent.getAdapter()
							.getItem(position);
					Intent task_intent = new Intent(getActivity()
							.getApplicationContext(),
							TaskUpdateDialogActivity.class);
					task_intent.putExtra("taskSource", "INFORMATION");
					task_intent.putExtra("taskOwner", task_Bean.getOwner());
					task_intent.putExtra("taskOwnerId", task_Bean.getOwner_id());
					task_intent.putExtra("taskId", task_Bean.getTask_id());
					task_intent.putExtra("taskStatus", task_Bean.getStatus()
							.toInt());
					task_intent.putExtra("taskContent",
							task_Bean.getDescription());
					task_intent.putExtra("taskTargetId",
							task_Bean.getLoc_TargetId());
					task_intent.putExtra("taskWeights", task_Bean.getWeights());
					task_intent.putExtra("taskBeginTime",
							task_Bean.getBegin_time());
					task_intent.putExtra("taskEndTime", task_Bean.getEnd_time());
					task_intent.putExtra("targetTag", mTargetTag);

					if (mTarget.getStatus() == TargetRunningState.Running) {
						task_intent.putExtra("taskEdited", true);
					} else {
						task_intent.putExtra("taskEdited", false);
					}
					startActivityForResult(task_intent, 5);
				}
			});
			addTask.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mTarget.getStatus() == TargetRunningState.Running) {
						Intent intent = new Intent(getActivity()
								.getApplicationContext(),
								TaskCreateDialogActivity.class);
						intent.putExtra("taskSource", "INFORMATION");
						intent.putExtra("targetId", mTargetId);
						startActivityForResult(intent, 777 + 11);
					} else {
						Tool_Utils.showInfo(getActivity(), "目标当期状态不可添加任务");
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onTargetChangedReceiver() {
		super.onTargetChangedReceiver();
		try {
			task_GetTarget();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onTargetMemeberChangedReceiver(String id) {
		super.onTargetMemeberChangedReceiver(id);
		try {
			if (Transformation_Util.String2int(id) == mTargetId) {
				task_GetTargetMember();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onScheduleCloudChangedReceiver(String id) {
		super.onScheduleCloudChangedReceiver(id);
		try {
			new com.helper.mistletoe.m.work.be.cloud.SyncTargetList_Target_Mode()
					.publishWork(getApplicationContext());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onTargetMemeberCloudChangedReceiver(String id) {
		super.onTargetMemeberCloudChangedReceiver(id);
		try {
			if (Transformation_Util.String2int(id) == mTargetId) {
				AirLock_Work.syncTargetMember(getContext(), mTargetId);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onWorkOk(ReadyGoooWork work) {
		super.onWorkOk(work);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void getDataFromBundle() {
		try {
			Bundle bundle = getArguments();
			mTargetId = bundle.getInt("targetId", -1);
			mTargetTag = bundle.getString("targetTag", "");
			mMarketPosition = bundle.getInt("position", -1);
			mViewType = DetailViewType
					.valueOf(bundle.getInt("targetRecordType",
							DetailViewType.TraditionTarget.toInt()));

		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onSearch(String filterStr) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public String getTitleString() {
		String result = "";
		try {
			if (mTarget != null) {
				result = mTarget.getSummary();
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	private void setFatherTitle() {
		try {
			if (getActivity() instanceof Target_Details_Activity) {
				Target_Details_Activity fActivity = (Target_Details_Activity) (getActivity());
				fActivity.setTitleTag(mTarget.getSummary(), "进度沟通（"
						+ mTarget.getLoc_TargetMember()
								.getCanNumberOfCommunication() + "人）");
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setMenu(final ImageView menu) {
		try {
			menu.setVisibility(ImageView.VISIBLE);
			menu.setImageResource(R.drawable.menu_press);
			final PopupMenu popup = new PopupMenu(getActivity()
					.getApplicationContext(), menu);
			popup.getMenuInflater().inflate(R.menu.target_detail_menu,
					popup.getMenu());
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				public boolean onMenuItemClick(MenuItem item) {
					try {
						if (item.getItemId() == R.id.special_topic) {
							Intent tIntent = new Intent(getActivity()
									.getApplicationContext(),
									Target_Create_Activity.class);
							tIntent.putExtra("targetId", (int) mTargetId);
							tIntent.putExtra("targetTag", (String) mTargetTag);
							tIntent.putExtra("type", "Update");
							startActivity(tIntent);
							getActivity().finish();
						}
						if (item.getItemId() == R.id.elite) {
							UpdateStatusRequestNumber mRequestId = UpdateStatusRequestNumber.UnKnown;
							if (mTarget.userIsCreater(getActivity()
									.getApplicationContext())) {
								mRequestId = UpdateStatusRequestNumber.OWNER_CLOSE_TARGET;
							} else {
								mRequestId = UpdateStatusRequestNumber.HELPER_CLOSE_TARGET;
							}
							mTarget.updateStatusCloud(getActivity(),
									mRequestId, null);
						}
						if (item.getItemId() == R.id.share) {

							new TargetShareByTargetIdTask(mActivity, mTargetId,getTitleString()).execute();
							
						}
						if (item.getItemId() == R.id.cloning) {
							Intent tIntent = new Intent(getActivity()
									.getApplicationContext(),
									Target_Create_Activity.class);
							// 打包数据
							JSONObject jData = new JSONObject();
							if (mTarget.summary != null) {
								jData.put("summary", mTarget.getSummary());
							}
							jData.put("view_flag", mTarget.getView_flag());
							jData.put("target_flag", mTarget.getTarget_flag());
							if (mTarget.description != null) {
								jData.put("description",
										mTarget.getDescription());
							}
							if (mTarget.head_pics != null) {
								jData.put("head_pics", (JSONArray) mTarget
										.getHead_pics_JSONArray());
							}

							JSONArray a = new JSONArray();
							for (int i = 0; i < mTaskList.getList().size(); i++) {
								if (mTaskList.getList().get(i).getStatus() != TaskStatus.Delete) {// 过滤已作废的task
									JSONObject b = new JSONObject();
									b.put("description", mTaskList.getList()
											.get(i).getDescription());
									if (mTaskList.getList().get(i).weights != null) {
										if ((mTaskList.getList().get(i)
												.getWeights() > 0)) {
											b.put("weights", mTaskList
													.getList().get(i)
													.getWeights());
										} else {
											b.put("weights", 1);
										}
									}
									a.put(b);
								}
							}
							jData.put("task", a);
							tIntent.putExtra("type", "Cloning");
							tIntent.putExtra("data", jData.toString());
							startActivity(tIntent);
							getActivity().finish();
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
					return true;
				}
			});
			menu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						// if (mTarget.getStatus() ==
						// TargetRunningState.Running) {
						popup.show();
						// }
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			{
				TargetMember_Bean tMe = mTarget.getLoc_TargetMember().getMe(
						getContext());
				if (tMe.getMember_id() < 1) {
					tMe = null;
				}
				if ((tMe == null)
						|| (tMe.getMember_status() == MemberStatus.UnWatch)) {
					popup.getMenu().getItem(0).setEnabled(false);
				}
			}
			if (!mTarget.userIsCreater(getContext())) {// 非创建者
				popup.getMenu().getItem(1).setTitle("申请退出");
			} else {
				popup.getMenu().getItem(1).setTitle("申请关闭");
			}
			if (mTarget.getStatus() == TargetRunningState.Running) {
				if (mTarget.getTarget_type() == TargetType.Market
						|| mTarget.getTarget_type() == TargetType.System) {
					popup.getMenu().getItem(1).setEnabled(false);
				} else {
					// 帮手的状态为签约
					if (mTarget.getStatus_server() == MemberStatus.Signed) {
						popup.getMenu().getItem(1).setEnabled(true);
					} else {
						popup.getMenu().getItem(1).setEnabled(false);
					}
				}
			} else {
				popup.getMenu().getItem(0).setEnabled(false);
				popup.getMenu().getItem(1).setEnabled(false);
			}

		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void task_GetTarget() {
		try {
			getEventBus().post(new TargetGet_Event(mTargetId, mTargetTag));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void task_GetTarget_Cbk(Target_Bean target) {
		try {
			if ((target.target_id != null) && (target.getTarget_id() > 0)) {
				mTarget = target;
				if (mTarget.getStatus() == TargetRunningState.Running) {
					isEdited = true;
				} else {
					isEdited = false;
				}
			}
			setData();
			task_GetTargetMember();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void task_GetTargetMember() {
		try {
			boolean workCanCom = true;
			if (workCanCom) {
				work_GetTargetMember = new GetTargetMemberList_Target_Mode(
						new WorkCallBack_Mode() {

							@Override
							public void WorkCallBack() {
								try {
									task_GetTargetMember_Cbk();
								} catch (Exception e) {
									ExceptionHandle.ignoreException(e);
								}
							}
						}, getActivity().getApplicationContext(), mTarget);
				work_GetTargetMember.execute("");
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void task_GetTargetMember_Cbk() {
		try {
			// 取出结果
			Target_Bean tTarget = work_GetTargetMember.getResponse_target();
			if ((tTarget.target_id != null) && (tTarget.getTarget_id() > 0)) {
				mTarget = tTarget;
			}
			// 更新显示
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void uds_FloatButton() {
		try {
			uds_FloatButton_HideAll();
			if ((mViewType == DetailViewType.MarketTarget)
					|| (mTarget.getTarget_type() == TargetType.Market)) {
				// 是海报墙上的按钮
				uds_FloatButtonMarket();
			} else {
				uds_FloatButtonLocal();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void uds_FloatButtonMarket() {
		try {
			// 左边按钮
			if ((mMarketPosition > 0)
					&& (mViewType == DetailViewType.MarketTarget)) {
				vLastButton.setVisibility(Button.VISIBLE);
			} else {
				vLastButton.setVisibility(Button.INVISIBLE);
			}
			// 中间按钮
			if (mTarget.userIsCreater(getContext())) {
				vJoinButton.setVisibility(Button.INVISIBLE);
			} else {
				vJoinButton.setVisibility(Button.VISIBLE);
			}
			if (mTarget.getLoc_TargetMember().whetherICare(getContext())) {
				vJoinButton.setText("取消关注");
			} else {
				vJoinButton.setText("关注目标");
			}
			// 右边按钮
			if (mViewType == DetailViewType.MarketTarget) {
				vNextButton.setVisibility(Button.VISIBLE);
			} else {
				vNextButton.setVisibility(Button.INVISIBLE);
			}
			if ((vLastButton.getVisibility() == Button.VISIBLE)
					|| (vJoinButton.getVisibility() == Button.VISIBLE)
					|| (vNextButton.getVisibility() == Button.VISIBLE)) {
				vMarketTarget_Lin.setVisibility(LinearLayout.VISIBLE);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void uds_FloatButtonLocal() {
		try {
			boolean tNeedAgree = mTarget.needAgree();
			boolean tNeedRefuse = mTarget.needRefuse();
			Log.i(DEBUG_TAG, String.valueOf(tNeedAgree));
			if (tNeedAgree || tNeedRefuse) {
				vFriendlyTarget_Lin.setVisibility(LinearLayout.VISIBLE);
				// 控制同意按钮的显示
				if (tNeedAgree) {
					vAgreeButton.setVisibility(Button.VISIBLE);
				} else {
					vAgreeButton.setVisibility(Button.INVISIBLE);
				}
				// 控制拒绝按钮的显示
				if (tNeedRefuse) {
					vRefuseButton.setVisibility(Button.VISIBLE);
				} else {
					vRefuseButton.setVisibility(Button.INVISIBLE);
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void uds_FloatButton_HideAll() {
		try {
			// 重置本地目标按键显示
			vFriendlyTarget_Lin.setVisibility(LinearLayout.GONE);
			vAgreeButton.setVisibility(Button.VISIBLE);
			vRefuseButton.setVisibility(Button.VISIBLE);
			// 重置本地目标按键显示
			vMarketTarget_Lin.setVisibility(LinearLayout.GONE);
			vLastButton.setVisibility(Button.VISIBLE);
			vJoinButton.setText("关注目标");
			vNextButton.setVisibility(Button.VISIBLE);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setOnCostListClicked(ImageView vCostList) {
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			switch (requestCode) {
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
					mUploadFile = new File(FolderTool_Utils.getAFE_ImageZoom()
							+ "/CaiJian" + TimeTool_Utils.getNowTime() + ".jpg");// 以时间秒为文件名
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
					vTargetCover.setImageForShow(mUploadFile);
					mTarget.updateCloud(getContext(),
							mUploadFile.getAbsolutePath());
					showToast("正在上传，请稍等");
					break;
				}
			}
				break;
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void opac_TargetMember() {
		try {
			Intent intent = new Intent(getActivity(),
					Target_TargetMember_Activity.class);
			intent.putExtra("targetId", (int) mTargetId);
			intent.putExtra("targetTag", (String) mTargetTag);
			startActivityForResult(intent, 892);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void uds_CoverHight() {
		try {
			WindowManager wm = (WindowManager) getContext().getSystemService(
					Context.WINDOW_SERVICE);
			int tWidth = wm.getDefaultDisplay().getWidth();
			LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) vTargetCover_Lin
					.getLayoutParams();
			// 取控件aaa当前的布局参数
			linearParams.height = (int) (tWidth * 0.618F); // 当控件的高强制设成365象素
			vTargetCover_Lin.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件aaa
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void changeMarketPosition(int targetId, int position) {
		try {
			this.mTargetId = targetId;
			mTargetTag = "";
			mMarketPosition = position;
			mViewType = DetailViewType.MarketTarget;
			initView_Other();

			// 重新显示出点赞的按钮
			vAppreciate.setVisibility(ImageButton.VISIBLE);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void onEventMainThread(TargetGetted_Event event) {
		try {
			// 获取到结果
			// 处理结果
			task_GetTarget_Cbk(event.getTarget());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void onEventMainThread(TaskOnChange_Event event) {
		try {
			// 获取到结果
			// 处理结果
			TaskList_Bean.getTaskList(mTargetId);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void onEventMainThread(TaskGetted_Event event) {
		try {
			// 获取到结果
			mTaskList.getList().clear();
			mTaskList.getList().addAll(event.getTaskList().getList());
			// 处理结果
			if (mTaskList.getList().size() > 0) {
				setData();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void onEventMainThread(ScheduleListGetted_Event event) {
		try {
			// 获取到结果
			ArrayList<Schedule_Bean> tNoteTagList = event.getSchdeuleList();
			// 刷新结果
			all_data = new ArrayList<Schedule_Bean>();
			all_data.addAll(tNoteTagList);
			getCostData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void getCostData() {
		try {
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
			String income = display(income_cost);// 支出
			String expendditure = display(expenditure_cost);// 预算
			cost_up.setText("预算" + income);
			cost_up.setTextColor(Color.rgb(104, 198, 136));
			cost_down.setText("支出" + expendditure);
			cost_down.setTextColor(Color.rgb(237, 107, 125));
			if (income_cost == 0) {// 预算为零
				if (expenditure_cost == 0) {// 支出为零
					tableLayoutCost.setVisibility(TableRow.GONE);
				} else {// 支出不为零
					tableLayoutCost.setVisibility(TableRow.VISIBLE);
					vCostUpProgress.setProgress(0);
					vCostDownProgress.setProgress(100);
				}
			} else {// 预算大于零
				tableLayoutCost.setVisibility(TableRow.VISIBLE);
				if (expenditure_cost == 0) {// 支出为零
					vCostUpProgress.setProgress(100);
					vCostDownProgress.setProgress(0);
				} else {// 支出不为零
					if (Math.abs(income_cost) >= Math.abs(expenditure_cost)) {// 预算大于支出
						vCostUpProgress.setProgress(100);
						vCostDownProgress.setProgress((int) ((Math
								.abs(expenditure_cost * 100)) / Math
								.abs(income_cost)));
					} else {// 支出大于预算
						vCostUpProgress.setProgress((int) ((Math
								.abs(income_cost * 100)) / Math
								.abs(expenditure_cost)));
						vCostDownProgress.setProgress(100);
					}
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private String display(float data) {
		DecimalFormat cost = new DecimalFormat();
		String style = "0.00";
		cost.applyPattern(style);
		return cost.format(Math.abs(data));// 取绝对值
	};
}

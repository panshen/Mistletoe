package com.helper.mistletoe.c.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.fragment.base.TargetDetailFragment;
import com.helper.mistletoe.c.ui.LocationActivity;
import com.helper.mistletoe.c.ui.Schedule_CostList_Activity;
import com.helper.mistletoe.c.ui.Schedule_Cost_Activity;
import com.helper.mistletoe.c.ui.Schedule_Cost_Details_Activity;
import com.helper.mistletoe.c.ui.Schedule_ShowImage_Activity;
import com.helper.mistletoe.c.ui.SelectScheduleMember_newActivity;
import com.helper.mistletoe.c.ui.Target_Details_Activity;
import com.helper.mistletoe.c.ui.TaskCreateDialogActivity;
import com.helper.mistletoe.c.ui.adapter.Schedule_Adapter;
import com.helper.mistletoe.c.ui.base.Base_Activity.ChangeMarketPosition;
import com.helper.mistletoe.m.pojo.NoteTagList_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleRevokeStatus;
import com.helper.mistletoe.m.pojo.Schedule_Enum.ScheduleType;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRunningState;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.m.work.be.GetTarget_Target_Mode;
import com.helper.mistletoe.m.work.be.ScheduleGetList_Event;
import com.helper.mistletoe.m.work.ui.ScheduleListGetted_Event;
import com.helper.mistletoe.roll.ShakeListener;
import com.helper.mistletoe.roll.ShakeListener.OnShakeListener;
import com.helper.mistletoe.util.DisplayImage;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.FolderTool_Utils;
import com.helper.mistletoe.util.ImageTool_Utils;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.LogPrint;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.util.prcomode.ReadyGoooWork;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Schedule_Fragment extends TargetDetailFragment implements
		ChangeMarketPosition {
	private ListView vList;// 进度更新的List
	private ImageView vMoreFunctionXXXX;// 点击这个键显示/隐藏更多功能
	private EditText vEditSchedule;// 编辑进度更新
	private ImageView vSend;// 选择私聊帮手
	private ImageView vChooseHelper;// 发送进度更新
	private LinearLayout vFunction;// 更多功能的外框
	private LinearLayout vFunction_Fun;// 更多功能-菜单的外框
	private LinearLayout vFunction_Rem;// 更多功能-发送提醒的外框
	private ImageView vFunImage;// 发送图片
	private ImageView vFunCamera;// 发送从相机照的照片
	private ImageView vFunRem;// 发送提醒
	private ImageView vFunCost;// 发送费用
	private ImageView vFunTask;// 创建任务
	private ImageView vFunLocation;// 分享位置
	private TextView vRemDate;// 提醒-日期
	private TextView vRemTime;// 提醒-时间
	private TextView vRemSend;// 提醒-发送
	private TextView vRemCancel;// 提醒-取消
	private LinearLayout vEditArea;

	private ShakeListener mShakeListener;
	private String shakingDialog = "inexistence";
	private int m_targetId;
	private String m_targetTag;
	private Target_Bean mTarget;
	private Schedule_Adapter m_listAdapter;
	private Calendar m_Calendar;
	private File m_File;
	private JSONArray mReciverId;
	private boolean mShowHighLight;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.schedule_layout, null);
		try {
			initView(v);
			setListener();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return v;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		try {
			mShakeListener.start();
			task_GetSchedule();// 从数据库获取进度更新
			setData();
			// 读出数据
			String tText = "";
			JSONObject tJson = readData();
			if (tJson.has("editText")) {
				tText = tJson.getString("editText");
			}
			vEditSchedule.setText(tText);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		try {
			mShakeListener.stop();
			// 保存数据
			String tText = vEditSchedule.getText().toString();
			JSONObject tJson = new JSONObject();
			tJson.put("editText", tText);
			saveData(tJson);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setData() {
		try {
			if (mTarget.getStatus() == TargetRunningState.Running) {
				vEditArea.setVisibility(LinearLayout.VISIBLE);
			} else {
				vEditArea.setVisibility(LinearLayout.GONE);
			}
			setFatherTitle();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView(View v) {
		try {
			// 初始化控件
			vList = (ListView) v.findViewById(R.id.schdeule_List);
			vMoreFunctionXXXX = (ImageView) v
					.findViewById(R.id.schdeule_MoreFunction);
			vMoreFunctionXXXX.setImageResource(R.drawable.open_function);
			vSend = (ImageView) v.findViewById(R.id.schdeule_Send);
			vChooseHelper = (ImageView) v
					.findViewById(R.id.schdeule_ChoosHelper);
			vEditSchedule = (EditText) v
					.findViewById(R.id.schdeule_EditSchdeule);
			vFunction = (LinearLayout) v.findViewById(R.id.schdeule_Fuction);
			vFunction_Fun = (LinearLayout) v
					.findViewById(R.id.schdeule_Fuction_Button);
			vFunction_Rem = (LinearLayout) v
					.findViewById(R.id.schdeule_Fuction_Rem);
			vFunImage = (ImageView) v
					.findViewById(R.id.schdeule_Fuction_Button_Image);
			vFunCamera = (ImageView) v
					.findViewById(R.id.schdeule_Fuction_Button_Camera);
			vFunRem = (ImageView) v
					.findViewById(R.id.schdeule_Fuction_Button_Rem);
			vFunTask = (ImageView) v
					.findViewById(R.id.schdeule_Fuction_Button_Task);
			vFunLocation = (ImageView) v
					.findViewById(R.id.schdeule_Fuction_Button_Location);
			vRemDate = (TextView) v
					.findViewById(R.id.schdeule_Fuction_Rem_Date);
			vRemTime = (TextView) v
					.findViewById(R.id.schdeule_Fuction_Rem_Time);
			vRemSend = (TextView) v
					.findViewById(R.id.schdeule_Fuction_Rem_Send);
			vRemCancel = (TextView) v
					.findViewById(R.id.schdeule_Fuction_Rem_Cancel);
			vEditArea = (LinearLayout) v.findViewById(R.id.schdeule_editArea);
			vFunCost = (ImageView) v
					.findViewById(R.id.schdeule_Fuction_Button_Cost);
			// 初始化成员变量
			mShakeListener = new ShakeListener(getActivity());
			m_targetId = -1;
			mTarget = new Target_Bean();
			m_targetTag = "";
			m_listAdapter = new Schedule_Adapter(getActivity(),
					getWorkFactory(), null);
			m_Calendar = Calendar.getInstance();
			m_File = null;
			mShowHighLight = false;
			mReciverId = new JSONArray();
			// 从上一个页面获得参数
			getDataFromBundle();
			// 其他初始化工作
			initView_Other();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView_Other() {
		try {
			vList.setAdapter(m_listAdapter);
			uds_TimeChoose();
			AirLock_Work.syncSchedule(getApplicationContext(), m_targetId);// 同步进度更新
			new GetTarget_Target_Mode(m_targetId, m_targetTag)
					.publishWork(getWorkFactory());// 从数据库获取目标
			task_GetSchedule();// 从数据库获取进度更新
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void dialog() {
		shakingDialog = "existing";
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage("亲，你要duang所有目标成员吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				shakingDialog = "inexistence";
				Instruction_Utils.sendInstrustion(getActivity()
						.getApplicationContext(),
						Instruction_Utils.ACTION_DUANG_ARRAY, mTarget
								.getTarget_id());
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				shakingDialog = "inexistence";
			}
		});

		builder.create().show();
	}

	protected void setListener() {
		try {
			mShakeListener.setOnShakeListener(new OnShakeListener() {

				@Override
				public void onShake() {
					if (ShakeListener.shakingStaus.equals("Stop")) {
						if (shakingDialog.equals("inexistence")) {
							dialog();
						}
					}
				}
			});
			vChooseHelper.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						showToast("私聊选人");
						Intent intent = new Intent(getActivity(),
								SelectScheduleMember_newActivity.class);

						// 帮手Id列表
						ArrayList<Integer> mHelperId = new ArrayList<Integer>();
						for (TargetMember_Bean tI : mTarget
								.getLoc_TargetMember().getReceiverList()) {
							mHelperId.add((int) tI.getMember_id());
						}
						// 如果用户不是创建者，把自己加入
						if (!mTarget.userIsCreater(getApplicationContext())) {
							mHelperId.add((int) mTarget.getLoc_TargetMember()
									.getOwner().getMember_id());
						}
						intent.putIntegerArrayListExtra("helper_id", mHelperId);
						intent.putExtra("targetId", m_targetId);
						if ((mHelperId == null) || (mHelperId.size() > 0)) {
							startActivityForResult(intent, 103);
						} else {
							showToast("没有可选择的帮手");
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vFunImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(Intent.ACTION_PICK, null);
						intent.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								"image/*");
						startActivityForResult(intent, 777 + 1);
						type_Look();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vFunCamera.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						ImageLoader.getInstance().clearMemoryCache();
						Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						// 下面这句指定调用相机拍照后的照片存储的路径
						m_File = new File(FolderTool_Utils.getAFE_ImageCamera()
								+ "/cam" + TimeTool_Utils.getNowTime() + ".jpg");
						intent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(m_File));
						startActivityForResult(intent, 777 + 2);
						type_Look();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vFunCost.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(getActivity()
								.getApplicationContext(),
								Schedule_Cost_Activity.class);
						intent.putExtra("targetId", (int) m_targetId);
						startActivityForResult(intent, 777 + 11);
						type_Look();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vFunTask.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(getActivity()
								.getApplicationContext(),
								TaskCreateDialogActivity.class);
						intent.putExtra("taskSource", "INFORMATION");
						intent.putExtra("targetId", (int) m_targetId);
						startActivityForResult(intent, 777 + 11);
						type_Look();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vFunLocation.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent_location = new Intent();
						intent_location.setClass(getActivity(),
								LocationActivity.class);
						intent_location.putExtra("targetId", (int) m_targetId);
						getActivity().startActivity(intent_location);
						// showToast("正在定位，请稍候");
						// getEventBus().post(new Lbs_Location_Event());
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vSend.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						if (!vEditSchedule.getText().toString().equals("")) {
							task_CreateSchedule(ScheduleType.Text);
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vList.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					try {
						String t_ClickItemId = ""
								+ m_listAdapter.getItemId(position);
						if ((m_listAdapter.mPoition != null)
								&& (m_listAdapter.mPoition
										.equals(t_ClickItemId))) {
							m_listAdapter.mPoition = null;
						} else {
							m_listAdapter.mPoition = ""
									+ m_listAdapter.getItemId(position);
						}
						m_listAdapter.notifyDataSetChanged();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
					return true;
				}
			});
			vMoreFunctionXXXX.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						if (vFunction.getVisibility() == LinearLayout.VISIBLE) {
							vMoreFunctionXXXX
									.setImageResource(R.drawable.open_function);
							type_Look();
						} else {
							vMoreFunctionXXXX
									.setImageResource(R.drawable.close_function);
							type_MoreFuction();
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					try {
						Schedule_Bean data = m_listAdapter.getItem(position);

						if (vFunction_Rem.getVisibility() == LinearLayout.VISIBLE) {
							type_Look();
							type_MoreFuction_Rem();
						} else {
							type_Look();
						}

						if (data.getStatus() != ScheduleRevokeStatus.Revoke) {
							if (data.getNote_type() == ScheduleType.Image) {
								opac_ShowImage(data);
							} else if (data.getNote_type() == ScheduleType.CostApply) {
								Intent intent = new Intent(getActivity()
										.getApplicationContext(),
										Schedule_Cost_Details_Activity.class);
								Bundle b = new Bundle();
								b.putString("summary", mTarget.getSummary());
								b.putInt("targetId", (int) m_targetId);
								b.putFloat("cost", data.getCost());
								b.putString("cost_type", data
										.getCost_type(NoteTagList_Bean.getInstance(getContext())));
								b.putString("cost_describe",
										data.getCost_desc());
								b.putLong("transaction_time",
										data.getTransaction_time());
								intent.putExtras(b);
								startActivity(intent);
							}
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vEditSchedule.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					try {
						if (hasFocus == true) {
							if (vFunction_Rem.getVisibility() != LinearLayout.VISIBLE) {
								type_InPut();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			vEditSchedule.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (vFunction_Rem.getVisibility() != LinearLayout.VISIBLE) {
						type_InPut();
					}
				}
			});
			vFunRem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						vEditSchedule.setHint("请输入提醒内容!");
						type_MoreFuction_Rem();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			vRemCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						type_MoreFuction();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			vRemSend.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						type_Look();
						task_CreateSchedule(ScheduleType.Remind);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			vRemDate.setOnClickListener(new OnClickListener() {
				private OnDateSetListener mDateSetListener = new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker arg0, int arg1, int arg2,
							int arg3) {
						try {
							// 保存时间
							m_Calendar.set(Calendar.YEAR, arg1);
							m_Calendar.set(Calendar.MONTH, arg2);
							m_Calendar.set(Calendar.DATE, arg3);
							if (m_Calendar.getTime().getTime() < TimeTool_Utils
									.getNowTime()) {
								m_Calendar = Calendar.getInstance();
							}
							// 更新显示
							uds_TimeChoose();
						} catch (Exception e) {
							ExceptionHandle.ignoreException(e);
						}
					}

				};

				@Override
				public void onClick(View v) {
					try {
						if (vFunction_Rem.getVisibility() == LinearLayout.VISIBLE) {
							type_Look();
							type_MoreFuction_Rem();
						} else {
							type_Look();
						}
						// 弹出日期对话框
						int year = m_Calendar.get(Calendar.YEAR);
						int monthOfYear = m_Calendar.get(Calendar.MONTH);
						int dayOfMonth = m_Calendar.get(Calendar.DATE);
						Dialog datelg = new DatePickerDialog(getActivity(),
								mDateSetListener, year, monthOfYear, dayOfMonth);
						datelg.show();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vRemTime.setOnClickListener(new OnClickListener() {
				private OnTimeSetListener mDateSetListener = new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						try {
							// 保存时间
							m_Calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
							m_Calendar.set(Calendar.MINUTE, minute);
							if (m_Calendar.getTime().getTime() < TimeTool_Utils
									.getNowTime()) {
								m_Calendar = Calendar.getInstance();
							}
							// 更新显示
							uds_TimeChoose();
						} catch (Exception e) {
							ExceptionHandle.ignoreException(e);
						}
					}

				};

				@Override
				public void onClick(View v) {
					try {
						if (vFunction_Rem.getVisibility() == LinearLayout.VISIBLE) {
							type_Look();
							type_MoreFuction_Rem();
						} else {
							type_Look();
						}
						// 弹出日期对话框
						int hourOfDay = m_Calendar.get(Calendar.HOUR_OF_DAY);
						int minute = m_Calendar.get(Calendar.MINUTE);
						boolean is24HourView = false;
						Dialog datelg = new TimePickerDialog(getActivity(),
								mDateSetListener, hourOfDay, minute,
								is24HourView);
						datelg.show();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			switch (requestCode) {
			case 103: {
				switch (resultCode) {
				case 1:
					ArrayList<Integer> tChoosedHelper = data
							.getIntegerArrayListExtra("seleteds");
					// 把返回的Id保存下来
					mReciverId = new JSONArray();
					for (Integer i : tChoosedHelper) {
						if ((i != null) && (i > 0)) {
							mReciverId.put(i);
						}
					}
					// 显示出选择了几个人
					showToast("选择了" + mReciverId.length() + "个人");
					break;
				}
			}
				break;
			case 777 + 1: {
				switch (resultCode) {
				case Activity.RESULT_OK:
					m_File = new File(DisplayImage.getFileFromUri(getActivity()
							.getApplicationContext(), data.getData()));
					task_CreateSchedule(ScheduleType.Image);
					break;
				}
			}
				break;
			case 777 + 2: {
				switch (resultCode) {
				case Activity.RESULT_OK:
					task_CreateSchedule(ScheduleType.Image);
					break;
				}
			}
				break;
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void getDataFromBundle() {
		try {
			Bundle bundle = getArguments();
			m_targetId = bundle.getInt("targetId");
			m_targetTag = bundle.getString("targetTag");
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onSearch(String filterStr) {
		try {
			ScheduleGetList_Event.getSearch(m_targetId, filterStr);
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

	@Override
	public void setOnCostListClicked(ImageView vCostList) {
		try {
			Intent intent = new Intent(getActivity().getApplicationContext(),
					Schedule_CostList_Activity.class);
			intent.putExtra("targetId", (int) m_targetId);
			intent.putExtra("targetTag", (String) m_targetTag);
			intent.putExtra("summary", mTarget.getSummary());
			// TODO
			intent.putExtra("targetStatus", mTarget.getStatus().toInt());
			// Log.v("targetStatus", "传递前："+mTarget.getStatus());
			startActivityForResult(intent, 777 + 11);
			type_Look();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setMenu(ImageView menu) {
		try {
			menu.setVisibility(ImageView.VISIBLE);
			menu.setImageResource(R.drawable.title_star_hollow);
			menu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						mShowHighLight = !mShowHighLight;
						String tTos = "显示所有进度更新";
						if (mShowHighLight) {
							tTos = "显示置顶的更新";
							ScheduleGetList_Event.getHighLight(m_targetId);
						} else {
							tTos = "显示所有进度更新";
							ScheduleGetList_Event.getAll(m_targetId);
						}
						showToast(tTos);
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onScheduleChangedReceiver(String id) {
		super.onScheduleChangedReceiver(id);
		try {
			LogPrint.printString_V("Test Task", "接收：进度更新变化");
			int tLocalId = m_targetId;// 这个Activity显示的目标Id
			int tCloudId = (int) Transformation_Util.String2int(id);// 有变化的目标Id
			if (tLocalId == tCloudId) {
				// 如果Id相同，就刷新
				task_GetSchedule();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onTargetMemeberChangedReceiver(String id) {
		super.onTargetMemeberChangedReceiver(id);
		try {
			int tLocalId = m_targetId;// 这个Activity显示的目标Id
			int tCloudId = (int) Transformation_Util.String2int(id);// 有变化的目标Id
			if (tLocalId == tCloudId) {
				// 如果Id相同，就刷新
				new GetTarget_Target_Mode(m_targetId, m_targetTag)
						.publishWork(getWorkFactory());// 从数据库获取目标
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onScheduleCloudChangedReceiver(String id) {
		super.onScheduleCloudChangedReceiver(id);
		try {
			int tLocalId = m_targetId;// 这个Activity显示的目标Id
			int tCloudId = (int) Integer.valueOf(id);// 有变化的目标Id
			if (tLocalId == tCloudId) {
				// 如果Id相同，就刷新
				AirLock_Work.syncSchedule(getApplicationContext(), tLocalId);
			}
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
				task_GetTarget_Cbk(t_Work);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
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

	private void uds_TimeChoose() {
		try {
			vRemDate.setText(TimeTool_Utils.fromateTimeShow(m_Calendar
					.getTime().getTime(), "yyyy年MM月dd日E"));
			vRemTime.setText(TimeTool_Utils.fromateTimeShow(m_Calendar
					.getTime().getTime(), "HH:mm"));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void task_GetTarget_Cbk(GetTarget_Target_Mode task) {
		try {
			// 获取任务结果
			mTarget = task.getTarget();
			// 更新显示
			if (getActivity() instanceof Target_Details_Activity) {
				Target_Details_Activity fActivity = (Target_Details_Activity) (getActivity());
				fActivity.setTitleTag();
				fActivity.setMenu();
			}
			MemberStatus tMyStatus = mTarget.getLoc_TargetMember()
					.getMe(getApplicationContext()).getMember_status();
			if (tMyStatus != MemberStatus.UnWatch) {
				m_listAdapter.setCanTip(true);
			}
			if ((mTarget.userIsCreater(getActivity().getApplicationContext()))
					|| (((int) mTarget.getView_flag()) == Target_Bean.TARGET_VIEWFLAG_OPEN)) {
				vChooseHelper.setVisibility(ImageView.VISIBLE);
			} else {
				vChooseHelper.setVisibility(ImageView.GONE);
			}
			if (mTarget.getStatus() == TargetRunningState.Running) {
				m_listAdapter.setmIfShowFuction(true);
			} else {
				m_listAdapter.setmIfShowFuction(false);
			}
			// 把帮手放入Adapter
			m_listAdapter.setMemberPool(mTarget.getLoc_TargetMember()
					.getTargetMemberList());
			// 把Target放入Adapter
			m_listAdapter.setTarget(mTarget);
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void task_GetSchedule() {
		try {
			ScheduleGetList_Event.getAll(m_targetId);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void task_CreateSchedule(ScheduleType type) {
		try {
			// 目标Id
			int tTargetId = (int) m_targetId;
			// 目标接收者
			JSONArray tRecivers = mReciverId;
			// 按照类型发送
			switch (type) {
			case Text: {
				// 取出内容
				String content = vEditSchedule.getText().toString();
				vEditSchedule.setText("");
				JSONObject tJson = new JSONObject();
				tJson.put("editText", "");
				saveData(tJson);
				// 发送
				Schedule_Bean.sendSchedule_Text(getContext(), tTargetId,
						tRecivers, content);
			}
				break;
			case Image: {
				// 取出内容
				String content = vEditSchedule.getText().toString();
				vEditSchedule.setText("");
				JSONObject tJson = new JSONObject();
				tJson.put("editText", "");
				saveData(tJson);
				// 取出文件
				File file = new File(ImageTool_Utils.getZoomBitmapFile(
						getApplicationContext(), m_File.getPath()));
				// 发送
				Schedule_Bean.sendSchedule_Image(getContext(), tTargetId,
						tRecivers, content, file);
			}
				break;
			case Remind: {
				// 取出内容
				String content = vEditSchedule.getText().toString();
				vEditSchedule.setText("");
				JSONObject tJson = new JSONObject();
				tJson.put("editText", "");
				saveData(tJson);
				// 取出提醒时间
				long reminderTimef = m_Calendar.getTime().getTime() / 1000;
				// 发送
				Schedule_Bean.sendSchedule_Remind(getContext(), tTargetId,
						tRecivers, content, reminderTimef);
			}
				break;
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void type_Default() {
		try {
			// vMoreFunctionXXXX.setVisibility(ImageView.VISIBLE);
			vChooseHelper.setVisibility(ImageView.VISIBLE);
			vSend.setVisibility(ImageView.VISIBLE);
			vFunction.setVisibility(LinearLayout.GONE);
			vFunction_Fun.setVisibility(LinearLayout.VISIBLE);
			vFunction_Rem.setVisibility(LinearLayout.GONE);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void type_InPut() {
		try {
			type_Default();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void type_MoreFuction() {
		try {
			type_Default();
			hideKeyboard();
			vFunction.setVisibility(LinearLayout.VISIBLE);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void type_MoreFuction_Rem() {
		try {
			type_MoreFuction();
			hideKeyboard();
			// vMoreFunctionXXXX.setVisibility(ImageView.GONE);
			vChooseHelper.setVisibility(ImageView.GONE);
			vSend.setVisibility(ImageView.GONE);
			vFunction_Fun.setVisibility(LinearLayout.GONE);
			vFunction_Rem.setVisibility(LinearLayout.VISIBLE);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void type_Look() {
		try {
			type_Default();
			hideKeyboard();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void opac_ShowImage(Schedule_Bean data) {
		try {
			Intent intent = new Intent(getActivity(),
					Schedule_ShowImage_Activity.class);
			intent.putExtra("ScheduleId", (int) data.getId());
			intent.putExtra("ScheduleTag", (String) data.getLoc_ItemTag());
			startActivity(intent);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void onEventMainThread(ScheduleListGetted_Event event) {
		try {
			// 获取到结果
			ArrayList<Schedule_Bean> tNoteTagList = event.getSchdeuleList();
			// 刷新结果
			m_listAdapter.setData_Pr(tNoteTagList);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	// public void onEventMainThread(Lbs_LocationOk_Event event) {
	// try {
	// // 获取到结果
	// BDLocation location = event.getLocation();
	// // 刷新结果
	// Schedule_Bean.sendSchedule_Location(getContext(), m_targetId, null, "",
	// location.getLatitude(), location.getLongitude());
	// } catch (Exception e) {
	// ExceptionHandle.ignoreException(e);
	// }
	// }

	public void changeMarketPosition(int targetId, int position) {
		try {
			this.m_targetId = targetId;
			m_targetTag = "";
			initView_Other();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void hideKeyboard() {
		try {
			InputMethodManager imm = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
			if (isOpen) {
				View focusView = getActivity().getCurrentFocus();
				if (focusView != null) {
					((InputMethodManager) getActivity().getSystemService(
							Activity.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(
									focusView.getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);

		}
	}
}

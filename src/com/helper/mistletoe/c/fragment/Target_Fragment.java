package com.helper.mistletoe.c.fragment;

import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.Target_Create_Activity;
import com.helper.mistletoe.c.ui.Target_Details_Activity;
import com.helper.mistletoe.c.ui.Target_HistoryTarget_Activity;
import com.helper.mistletoe.c.ui.TemplateListDialogActivity;
import com.helper.mistletoe.c.ui.adapter.Target_Adapter;
import com.helper.mistletoe.json.MyJsonReader;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.Template_Bean;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRunningState;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.m.work.be.GetTargetList_Target_Mode;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.util.prcomode.ReadyGoooWork;
import com.helper.mistletoe.v.colorswitch.TargetColor;

/**
 * Target页面
 *
 * @author 张久瑞
 * @version 1.0
 *
 */
public class Target_Fragment extends BaseFragment {
	private TextView vTitle;// 控件：标题
	private ListView vList;// 控件：列表

	private Target_Adapter m_listAdapter;// 数据：用户是否第一次登陆
	private int m_Color;
	private ArrayList<Template_Bean> TemplateS;
	private static String TAG = "com.helper.mistletoe.c.fragment.Target_Fragment";
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.target_layout, null);
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
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		super.onDestroyView();
	}

	@Override
	public void onResume() {
		try {
			m_Color = -1;
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		super.onResume();
	}

	@Override
	public void onPause() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		super.onPause();
	}

	@Override
	public void onSearch(String filterStr) {
		try {
			task_UpdateTargetList(filterStr);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onAdd() {
		try {
			opac_CreateTarget();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onMenu(RelativeLayout menu) {
		try {
			opac_HistoryTarget();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setData() {
		try {
			task_UpdateTargetList();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView(View v) {
		try {
			// 初始化控件
			vTitle = (TextView) v.findViewById(R.id.tarf_title);
			vList = (ListView) v.findViewById(R.id.tarf_list);
			// 初始化成员变量
			m_listAdapter = new Target_Adapter(getActivity(), getWorkFactory(),
					null);
			m_Color = -1;
			// 其他初始化工作
			vList.setAdapter(m_listAdapter);
			task_UpdateTargetList();
			AirLock_Work.syncTarget(getContext());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void getTemplateS() {
		TemplateS = new ArrayList<Template_Bean>();
		// 1、获取Preferences
		SharedPreferences settings = getActivity().getSharedPreferences(
				"TemplateConstants", getActivity().MODE_PRIVATE);
		// 2、取出数据
		String ts = settings.getString("TemplateS", null);
		if (ts == null) {
			TemplateS = null;
		} else {
			TemplateS = MyJsonReader.getJsonDataForFindTargetTemlate(ts);
		}
	}

	protected void setListener() {
		try {
			vTitle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						m_Color = -1;
						setData();
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
						android.support.v4.app.FragmentManager fm = 	getFragmentManager();
						fm.beginTransaction().add(Target_menu_fragment.getFragmet(m_listAdapter.getItem(position)), "").commit();
//						String t_ClickItemId = ""+ m_listAdapter.getItemId(position);
//						Log.i(TAG, t_ClickItemId+"---mPoint"+m_listAdapter.m_poition);
//						if ((m_listAdapter.m_poition != null) && (m_listAdapter.m_poition.equals(t_ClickItemId)))
//							 m_listAdapter.m_poition = null;
//						 else
//							 m_listAdapter.m_poition = ""+ m_listAdapter.getItemId(position);
//						     m_listAdapter.notifyDataSetChanged();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
					return true;
				}
			});
			vList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					try {
						m_listAdapter.m_poition = null;
						Target_Bean mClickedTarget = m_listAdapter.getData_Pr()
								.get(position);
						if (mClickedTarget.getTarget_id() == -2) {
							// 把发送标志置为发送失败
							mClickedTarget.setTarget_id(-2);
							// 重发目标
							showToast("重新发送");
							mClickedTarget.createTarget_Cloud(getContext());
						} else if (mClickedTarget.getTarget_id() == -1) {
							if (((TimeTool_Utils.getNowTime() / 1000) - (mClickedTarget
									.getCreate_time())) > 120) {
								// 把发送标志置为发送失败
								mClickedTarget.setTarget_id(-2);
								// 重发目标
								showToast("重新发送");
								mClickedTarget.createTarget_Cloud(getContext());
							} else {
								showToast("这个目标正在发送中");
							}
						} else {
							opac_TargetDeatil(position);
						}
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
		try {
			AirLock_Work.syncTarget(getContext());
			task_UpdateTargetList();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onWorkOk(ReadyGoooWork work) {
		super.onWorkOk(work);
		try {
			if (work instanceof GetTargetList_Target_Mode) {
				GetTargetList_Target_Mode workObj = (GetTargetList_Target_Mode) work;
				// 获取任务执行结果
				ArrayList<Target_Bean> backBean = workObj.getTargetList();
				long efwfwefwffw = workObj.getTargetCount();
				m_Color = workObj.getColor();
				// 处理结果
				String t_TitleString = "";
				if (m_Color < 1) {
					t_TitleString = "全部";
					t_TitleString += "目标（" + efwfwefwffw + "）";
				} else {
					t_TitleString = TargetColor.getTargetColorName(m_Color)
							+ "色";
					t_TitleString += "目标（" + efwfwefwffw + "） 点击此栏还原";
				}
				vTitle.setText(t_TitleString);
				m_listAdapter.setData_Pr(backBean);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onTargetChangedReceiver() {
		super.onTargetChangedReceiver();
		try {
			task_UpdateTargetList();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onScheduleCloudChangedReceiver(String id) {
		super.onScheduleCloudChangedReceiver(id);
		try {
			AirLock_Work.syncTarget(getApplicationContext());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void task_UpdateTargetList() {
		try {
			task_UpdateTargetList(null);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void task_UpdateTargetList(String searchKey) {
		try {
			boolean workCanCom = true;
			if (workCanCom) {
				GetTargetList_Target_Mode workObj = new GetTargetList_Target_Mode(
						TargetRunningState.Running, m_Color, searchKey);
				workObj.publishWork(getWorkFactory());
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void opac_CreateTarget() {
		try {
			getTemplateS();
			if (TemplateS == null) {
				Intent it_create = new Intent(getActivity(),
						Target_Create_Activity.class);
				Template_Bean tempTemplate = new Template_Bean();
				it_create.putExtra("Template", tempTemplate);
				startActivityForResult(it_create, 0);
			} else {
				Intent it_template = new Intent(getActivity(),
						TemplateListDialogActivity.class);
				it_template.putParcelableArrayListExtra("TemplateS", TemplateS);
				startActivityForResult(it_template, 0);
			}

		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void opac_HistoryTarget() {
		try {
			Intent it = new Intent(getActivity(),
					Target_HistoryTarget_Activity.class);
			startActivityForResult(it, 0);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void opac_TargetDeatil(int position) {
		try {
			Intent it = new Intent(getActivity(), Target_Details_Activity.class);
			it.putExtra("targetId", m_listAdapter.getItem(position)
					.getTarget_id());
			it.putExtra("targetTag", m_listAdapter.getItem(position)
					.getLoc_TargetTag());
			it.putExtra(
					"toTargetInfo",
					(boolean) (m_listAdapter.getItem(position).needAgree() || m_listAdapter
							.getItem(position).needRefuse()));
			startActivityForResult(it, 0);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
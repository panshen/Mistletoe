package com.helper.mistletoe.c.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.fragment.base.TargetHistoryFragment;
import com.helper.mistletoe.c.ui.adapter.Target_DeletedTarget_Adapter;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRunningState;
import com.helper.mistletoe.m.work.be.GetTargetList_Target_Mode;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.ReadyGoooWork;

public class Target_DeletedTarget_Fragment extends TargetHistoryFragment {
	private ListView vList;// 控件：列表

	private Target_DeletedTarget_Adapter m_listAdapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.target_deletedtarget_layout, null);
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

	protected void setData() throws Exception {
		try {
			GetTargetList_Target_Mode workObj = new GetTargetList_Target_Mode(TargetRunningState.Deleted, -1, null);
			workObj.publishWork(getWorkFactory());
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public void onTargetChangedReceiver() {
		super.onTargetChangedReceiver();
		try {
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView(View v) throws Exception {
		try {
			// 初始化控件
			vList = (ListView) v.findViewById(R.id.tarf_list);
			// 初始化成员变量
			m_listAdapter = new Target_DeletedTarget_Adapter(getActivity(), null, null);
			// 从上一个页面获得参数
			// 其他初始化工作
			vList.setAdapter(m_listAdapter);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void setListener() throws Exception {
		try {
			vList.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					try {
						String t_ClickItemId = "" + m_listAdapter.getItemId(position);
						if ((m_listAdapter.m_poition != null) && (m_listAdapter.m_poition.equals(t_ClickItemId))) {
							m_listAdapter.m_poition = null;
						} else {
							m_listAdapter.m_poition = "" + m_listAdapter.getItemId(position);
						}
						m_listAdapter.notifyDataSetChanged();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
					return true;
				}
			});
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public void onWorkOk(ReadyGoooWork work) {
		super.onWorkOk(work);
		try {
			if (work instanceof GetTargetList_Target_Mode) {
				GetTargetList_Target_Mode workObj = (GetTargetList_Target_Mode) work;
				// 获取任务执行结果
				ArrayList<Target_Bean> backBean = workObj.getTargetList();
				// 处理结果
				m_listAdapter.setData_Pr(backBean);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onScheduleCloudChangedReceiver(String id) {
		super.onScheduleCloudChangedReceiver(id);
		try {
			new com.helper.mistletoe.m.work.be.cloud.SyncTargetList_Target_Mode().publishWork(getApplicationContext());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void getDataFromBundle() throws Exception {
		try {
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public void onSearch(String filterStr) {
		try {
			GetTargetList_Target_Mode workObj = new GetTargetList_Target_Mode(TargetRunningState.Deleted, -1, filterStr);
			workObj.publishWork(getWorkFactory());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setMenu(ImageView menu) {
		try {
			menu.setVisibility(ImageView.INVISIBLE);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
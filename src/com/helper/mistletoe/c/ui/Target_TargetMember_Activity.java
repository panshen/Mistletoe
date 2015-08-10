package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.adapter.Target_TargetMember_Adapter;
import com.helper.mistletoe.c.ui.base.Base_Activity;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRunningState;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.m.work.be.GetTarget_Target_Mode;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.LogPrint;
import com.helper.mistletoe.util.prcomode.ReadyGoooWork;

public class Target_TargetMember_Activity extends Base_Activity {
	private ImageView vBack;
	private ImageView vChangHelper;
	private ListView vHelperList;

	private int m_TargetId;
	private String m_TargetTag;
	private Target_Bean m_Target;
	private JSONArray m_TargetMember;
	private Target_TargetMember_Adapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.target_targetmember_layout);
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
	protected void initView() throws Exception {
		try {
			// 初始化控件
			vBack = (ImageView) findViewById(R.id.searchViddddddddew_main_back);
			vChangHelper = (ImageView) findViewById(R.id.changeHelper);
			vHelperList = (ListView) findViewById(R.id.helperList);
			// 初始化成员变量
			m_TargetId = 0;
			m_TargetTag = "";
			m_Target = new Target_Bean();
			m_TargetMember = new JSONArray();
			mAdapter = new Target_TargetMember_Adapter(getContext(), null, getWorkFactory(), m_Target);
			// 其他初始化工作
			getDataFromBundle();
			new GetTarget_Target_Mode(m_TargetId, m_TargetTag).publishWork(getWorkFactory());
			vHelperList.setAdapter(mAdapter);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void setData() throws Exception {
		try {
			uds_MemberShow();
			// 如果创建者是自己，删除掉按钮
			boolean tIsShowButton = true;
			if (!m_Target.userIsCreater(getApplicationContext())) {
				tIsShowButton = false;
			}
			if (!(m_Target.getStatus() == TargetRunningState.Running)) {
				tIsShowButton = false;
			}
			if (tIsShowButton) {
				vChangHelper.setVisibility(LinearLayout.VISIBLE);
			} else {
				vChangHelper.setVisibility(LinearLayout.GONE);
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void setListener() throws Exception {
		try {
			vBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
						finish();
				}
			});
			vChangHelper.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent t_Intent = new Intent(Target_TargetMember_Activity.this, SelectTargetMemberActivity.class);
						t_Intent.putExtra("sna.std.private.Members", m_TargetMember.toString());
						startActivityForResult(t_Intent, 888);
						LogPrint.printString_V("UI Log", m_TargetMember.toString());
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			Bundle bundle = new Bundle();
			// 获取内容
			if (data != null) {
				bundle = data.getExtras();
				if (bundle == null) {
					bundle = new Bundle();
				}
			}
			// 处理
			if (requestCode == 888) {
				if (resultCode == 1) {
					if (bundle.containsKey("memberId")) {
						ArrayList<Integer> t_Member = bundle.getIntegerArrayList("memberId");
						m_TargetMember = new JSONArray(new Gson().toJson(t_Member));
						m_Target.updateMembersCloud(getApplicationContext(), m_TargetMember.toString());
					}
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onWorkOk(ReadyGoooWork work) {
		super.onWorkOk(work);
		try {
			if (work instanceof GetTarget_Target_Mode) {
				GetTarget_Target_Mode t_Work = (GetTarget_Target_Mode) work;
				m_Target = t_Work.getTarget();
				mAdapter.setmTarget(m_Target);
				for (TargetMember_Bean t_A : m_Target.getLoc_TargetMember().getHelper()) {
					m_TargetMember.put((int) t_A.getHelper_id());
				}
				setData();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onTargetChangedReceiver() {
		super.onTargetChangedReceiver();
		try {
			new GetTarget_Target_Mode(m_TargetId, m_TargetTag).publishWork(getWorkFactory());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onScheduleCloudChangedReceiver(String id) {
		super.onScheduleCloudChangedReceiver(id);
		try {
			AirLock_Work.syncTarget(getApplicationContext());
			AirLock_Work.syncTargetMember(getApplicationContext(), m_TargetId);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void uds_MemberShow() {
		try {
			mAdapter.setData_Pr(m_Target.getLoc_TargetMember().getHelper());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onTargetMemeberChangedReceiver(String id) {
		super.onTargetMemeberChangedReceiver(id);
		try {
			new GetTarget_Target_Mode(m_TargetId, m_TargetTag).publishWork(getWorkFactory());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void getDataFromBundle() throws Exception {
		try {
			Bundle bundle = getIntent().getExtras();
			if (bundle.containsKey("targetId")) {
				m_TargetId = bundle.getInt("targetId");
			}
			if (bundle.containsKey("targetTag")) {
				m_TargetTag = bundle.getString("targetTag");
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}
}

package com.helper.mistletoe.c.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.task.TargetViewTask;
import com.helper.mistletoe.c.ui.MainFragmentActivity;
import com.helper.mistletoe.c.ui.Target_Create_Activity;
import com.helper.mistletoe.c.ui.Target_Details_Activity;
import com.helper.mistletoe.c.ui.Target_Details_Activity.TargetMarketList;
import com.helper.mistletoe.c.ui.adapter.PosterFragmentAdapter;
import com.helper.mistletoe.m.db.TargetManager;
import com.helper.mistletoe.m.net.request.Find_Public_Target_NetRequest;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetType;
import com.helper.mistletoe.util.ThreadPoolUtils_db;
import com.helper.mistletoe.util.Tool_Utils;
import com.helper.mistletoe.v.GridViewWithHeaderAndFooter;
import com.helper.mistletoe.v.refrenshellview.PullToRefreshBase;
import com.helper.mistletoe.v.refrenshellview.PullToRefreshBase.OnRefreshListener;
import com.helper.mistletoe.v.refrenshellview.PullToRefreshGridViewWithHeadAndFooter;
import com.helper.mistletoe.v.viewflow.CircleFlowIndicator;
import com.helper.mistletoe.v.viewflow.ImageAdapter;
import com.helper.mistletoe.v.viewflow.ViewFlow;

@SuppressLint({ "InflateParams", "RtlHardcoded" })
public class PosterFragment extends BaseFragment implements TargetMarketList {
	private ViewFlow viewFlow;
	private CircleFlowIndicator indic; // 页表指示器
	private GridViewWithHeaderAndFooter gridview;
	private PullToRefreshGridViewWithHeadAndFooter gridViewWithHeaderAndFooter;
	private PosterFragmentAdapter adapter;
	private static ArrayList<Target_Bean> targetList, targetListNormal;
	private ReadPublicTargetFromNetTask readPublicTargetTask;
	private TargetViewTask targetViewTask;
	private View poster_head;
	private int view_position = -1;
	private int top_count = -1;
	private int top = 0;
	private int pager = 0;
	private boolean isPlaying = false;
	private String keyWord;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.poster_fragment, null);
		setUpView(v);
		targetList = new ArrayList<Target_Bean>();
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		isPlaying = true;
		setData();
		setListener();
	}

	@Override
	public void onPause() {
		super.onPause();
		isPlaying = false;
		viewFlow.stopAutoFlowTimer();
		// 记住当前位置
		memoryListviewPosition();
	}

	public void setData() {
		readPublicTargetTask = new ReadPublicTargetFromNetTask();
		readPublicTargetTask.executeOnExecutor(ThreadPoolUtils_db.threadPool);
	}

	class ReadPublicTargetFromNetTask extends
			AsyncTask<String, Integer, ArrayList<Target_Bean>> {

		@Override
		protected ArrayList<Target_Bean> doInBackground(String... params) {
			ArrayList<Target_Bean> ShowDataList = new ArrayList<Target_Bean>();
			Find_Public_Target_NetRequest netRequest = new Find_Public_Target_NetRequest(
					getActivity());
			try {
				ShowDataList = netRequest
						.doFindPublicTarget(pager, 50, keyWord);
				// 装到数据库中
				TargetManager.getInstance(getContext()).writeTargetList_Market(
						ShowDataList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ShowDataList;
		}

		@Override
		protected void onPostExecute(ArrayList<Target_Bean> result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.size() > 0) {
					targetList.clear();
					targetList.addAll(result);
				} else {
					if (getActivity() != null
							&& MainFragmentActivity.currentFragmentIndex == 2) {
						Tool_Utils.showInfo(getActivity(), "已没有更多消息");
					}
				}
			} else {
				if (getActivity() != null
						&& MainFragmentActivity.currentFragmentIndex == 2) {
					Tool_Utils.showInfo(getActivity(), "已没有更多消息");
				}
			}
			display(targetList);
		}
	}

	public void display(ArrayList<Target_Bean> targetList) {
		ArrayList<Target_Bean> targetListTop = new ArrayList<Target_Bean>();
		targetListNormal.clear();
		for (int i = 0; i < targetList.size(); i++) {
			if (targetList.get(i).getTarget_type() == TargetType.Top) {
				targetListTop.add(targetList.get(i));
			} else {
				targetListNormal.add(targetList.get(i));
			}
		}

		// 如果最后一行不满则不显示
		// int remainder=targetListNormal.size()%3;
		// if (remainder!=0) {
		// for (int i = 0; i < remainder; i++) {
		// targetListNormal.remove(targetListNormal.size()-i-1);
		// }
		// }
		if (adapter == null) {
			adapter = new PosterFragmentAdapter(getActivity(), targetListNormal);
			gridview.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
		// if (view_position != -1) {
		// gridview.smoothScrollToPosition(view_position, top);
		// }
		gridview.removeHeaderView(poster_head);
		top_count = 0;
		if (targetListTop.size() > 0) {
			top_count = 3;
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			poster_head = inflater.inflate(R.layout.poster_head, null);
			viewFlow = (ViewFlow) poster_head.findViewById(R.id.viewflow);
			viewFlow.setNestedpParent((ViewGroup) viewFlow.getParent());
			indic = (CircleFlowIndicator) poster_head
					.findViewById(R.id.viewflowindic);
			gridview.addHeaderView(poster_head);
			// 为其绑定适配器
			viewFlow.setAdapter(new ImageAdapter(getActivity(), targetListTop),
					0); // 初始位置5
			viewFlow.setmSideBuffer(new ImageAdapter(getActivity()).ids.size()); // 实际图片张数，
																					// 我的ImageAdapter实际图片张数为3
			// viewFlow绑定页表指示器
			viewFlow.setFlowIndicator(indic);
			viewFlow.setSelection(0); // 设置初始位置
			viewFlow.startAutoFlowTimer(); // 启动自动播放
		}
	}

	private void targetByView(int target_id) {
		targetViewTask = new TargetViewTask();
		targetViewTask.executeOnExecutor(ThreadPoolUtils_db.threadPool,
				target_id);
	}

	private void setListener() {
		// 单击进入
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Target_Bean target = (Target_Bean) parent.getAdapter().getItem(
						position + top_count);
				targetByView(target.getTarget_id());
				Target_Details_Activity.openPage_Market(getActivity(),
						target.getTarget_id(), position + top_count);
			}
		});
	}

	// 记住当前位置
	protected void memoryListviewPosition() {
		view_position = gridview.getFirstVisiblePosition();
		View v = gridview.getChildAt(4);
		top = (v == null) ? 4 : v.getTop();
	}

	private void setUpView(View v) {
		keyWord = "";
		gridViewWithHeaderAndFooter = (PullToRefreshGridViewWithHeadAndFooter) v
				.findViewById(R.id.poster_fragment_gridview);
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		poster_head = inflater.inflate(R.layout.poster_head, null);
		viewFlow = (ViewFlow) poster_head.findViewById(R.id.viewflow);
		viewFlow.setNestedpParent((ViewGroup) viewFlow.getParent());
		indic = (CircleFlowIndicator) poster_head
				.findViewById(R.id.viewflowindic);
		gridViewWithHeaderAndFooter.setPullLoadEnabled(false);
		gridViewWithHeaderAndFooter.setScrollLoadEnabled(true);
		gridview = gridViewWithHeaderAndFooter.getRefreshableView();
		gridview.addHeaderView(poster_head);
		gridview.setNumColumns(3);
		targetList = new ArrayList<Target_Bean>();
		targetListNormal = new ArrayList<Target_Bean>();
		adapter = new PosterFragmentAdapter(getActivity(), targetListNormal);
		gridview.setAdapter(adapter);
		gridViewWithHeaderAndFooter
				.setOnRefreshListener(new OnRefreshListener<GridViewWithHeaderAndFooter>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<GridViewWithHeaderAndFooter> refreshView) {
						// TODO Auto-generated method stub
						memoryListviewPosition();
						pager = 0;
						targetList.clear();
						setData();
						gridViewWithHeaderAndFooter.onPullDownRefreshComplete();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<GridViewWithHeaderAndFooter> refreshView) {
						// TODO Auto-generated method stub
						memoryListviewPosition();
						pager++;
						setData();
						gridViewWithHeaderAndFooter.onPullUpRefreshComplete();
					}
				});
	}

	@Override
	public void onSearch(String filterStr) {
		keyWord = filterStr;
		targetList.clear();
		targetListNormal.clear();
		setData();
	}

	@Override
	public void onAdd() {
		Intent it = new Intent(getActivity(), Target_Create_Activity.class);
		startActivityForResult(it, 10);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public Target_Bean getRandomTarget(int position) {
		Target_Bean target_Bean = new Target_Bean();
		if (position == -1) {// 前面没有
			target_Bean = null;
		} else if (position < targetList.size()) {// 根据position获取targetList中的Target
			target_Bean = targetList.get(position);
		} else {// 当前获取的已经没有了，需要到后台加载
			target_Bean = null;
			// if (isLastTarget) {
			// target_Bean=null;
			// }else {
			// if (position-10>=0) {
			// target_Bean=targetList.get(position-10);
			// }else{
			// target_Bean=targetList.get(0);
			// }
			// pager++;
			// setData();
			// }
		}
		if (target_Bean != null) {
			targetByView(target_Bean.getTarget_id());
		}
		return target_Bean;
	}

	@Override
	public void onMenu(RelativeLayout menu) {
		// TODO Auto-generated method stub

	}

}

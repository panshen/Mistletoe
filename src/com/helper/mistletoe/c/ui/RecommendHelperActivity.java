package com.helper.mistletoe.c.ui;

import java.util.ArrayList;
import java.util.List;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.adapter.ShootListViewAdapter;
import com.helper.mistletoe.m.net.request.Find_Public_Helpers_NetRequest;
import com.helper.mistletoe.m.net.request.Get_Public_Helpers_By_Keyword_NetRequest;
import com.helper.mistletoe.m.pojo.Public_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.ThreadPoolUtils_Net;
import com.helper.mistletoe.v.ClearEditText;
import com.helper.mistletoe.v.refrenshellview.PullToRefreshBase;
import com.helper.mistletoe.v.refrenshellview.PullToRefreshBase.OnRefreshListener;
import com.helper.mistletoe.v.refrenshellview.PullToRefreshListView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecommendHelperActivity extends PrivateBasicActivity implements OnItemClickListener{
	private RelativeLayout back;
	private ClearEditText mClearEditText;
	private ProgressBar progressBar;
	private Button verification;
	private PullToRefreshListView listview;
	private ListView public_lv;
	private ShootListViewAdapter shootListViewAdapter;
	private TextView searchResults;
	private GetPublicHelperTask getPublicHelperTask;
	private GetPublicHelperByKeywordTask getPublicHelperByKeywordTask;
	private Find_Public_Helpers_NetRequest find_Public_Helpers_NetRequest;
	private Get_Public_Helpers_By_Keyword_NetRequest get_Public_Helpers_By_Keyword_NetRequest;
	private Public_Bean public_Bean;
	private List<Public_Bean> list;
	private int start_id;
	private User_Bean user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.recommend_helper_list);
		initViews();
		setlistener();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setPublicStatus();
		setPublicData();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		public_Bean = (Public_Bean) parent.getAdapter().getItem(position);
		Intent intent_publicDetails = new Intent(RecommendHelperActivity.this.getApplicationContext(), PublicDetailsActivity.class);
		intent_publicDetails.putExtra("public_Bean", public_Bean);
		startActivityForResult(intent_publicDetails, 2);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.recommend_helper_list_button_back:
			RecommendHelperActivity.this.finish();
			break;
		case R.id.recommend_helper_list_button_verification:
			if (user.getLoc_OpenMe()) {
				Instruction_Utils.sendInstrustion(RecommendHelperActivity.this, Instruction_Utils.UPDATE_USER_PUBLIC, "2");
			} else {
				Instruction_Utils.sendInstrustion(RecommendHelperActivity.this, Instruction_Utils.UPDATE_USER_PUBLIC, "1");
			}
			break;

		default:
			break;
		}
		
	}
	private void initViews() {
		start_id=0;
		back = (RelativeLayout) findViewById(R.id.recommend_helper_list_button_back);
		searchResults = (TextView) findViewById(R.id.recommend_helper_list_textView_searchResult);
		searchResults.setVisibility(TextView.GONE);
		verification = (Button) findViewById(R.id.recommend_helper_list_button_verification);
		mClearEditText = (ClearEditText) findViewById(R.id.recommend_helper_list_filter_edit);
		progressBar = (ProgressBar) findViewById(R.id.recommend_helper_list_progressb);
		listview = (PullToRefreshListView) findViewById(R.id.main_shoot_listview);
		listview.setPullLoadEnabled(false);
		listview.setScrollLoadEnabled(true);
		public_lv=listview.getRefreshableView();
		list = new ArrayList<Public_Bean>();
		shootListViewAdapter = new ShootListViewAdapter(RecommendHelperActivity.this, list);
		public_lv.setAdapter(shootListViewAdapter);
		listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				start_id=0;
				setPublicData();
				listview.onPullDownRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if (list.size()==0) {
					start_id=0;
				}else{
					start_id=list.get(list.size()-1).getPublic_id()+1;
				}
				setPublicData();
				listview.onPullUpRefreshComplete();
			}
		});
	}

	private void setlistener() {
		back.setOnClickListener(this);
		verification.setOnClickListener(this);
		public_lv.setOnItemClickListener(this);
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				searchResults.setVisibility(TextView.GONE);
				if (s != null) {
					if (s.length() > 0) {
						findHelperBykeyword(s.toString());
					} else {
						start_id=0;
						setPublicData();
					}

				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}
	private void setPublicStatus() {
		try {
			user = new User_Bean();
			user.readData(RecommendHelperActivity.this.getApplicationContext());
			if (user.getLoc_OpenMe()) {
				verification.setText("不再成为公共帮手");
			} else {
				verification.setText("我也要成为公共帮手");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	
	private void setPublicData() {
		progressBar.setVisibility(ProgressBar.VISIBLE);
		getPublicHelperTask = new GetPublicHelperTask();
		getPublicHelperTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, start_id);
	}

	public class GetPublicHelperTask extends AsyncTask<Integer, Integer, ArrayList<Public_Bean>> {
		@Override
		protected ArrayList<Public_Bean> doInBackground(Integer... params) {
			Integer state_id = params[0];
			ArrayList<Public_Bean> public_list_service = new ArrayList<Public_Bean>();
			try {
				find_Public_Helpers_NetRequest = new Find_Public_Helpers_NetRequest(RecommendHelperActivity.this.getApplicationContext());
				public_list_service = find_Public_Helpers_NetRequest.doFindPublicHelper(state_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return public_list_service;
		}

		@Override
		protected void onPostExecute(ArrayList<Public_Bean> result) {
			super.onPostExecute(result);
			if (result!=null) {
				if (result.size()>0) {
					if (start_id==0) {
						list.clear();	
					}
					list.addAll(result);
					shootListViewAdapter.notifyDataSetChanged();
				}else {
					Toast.makeText(RecommendHelperActivity.this, "已没有更多消息", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(RecommendHelperActivity.this, "已没有更多消息", Toast.LENGTH_SHORT).show();
			}
			progressBar.setVisibility(ProgressBar.GONE);
		}
	}

	

	protected void findHelperBykeyword(String string) {
		progressBar.setVisibility(ProgressBar.VISIBLE);
		getPublicHelperByKeywordTask = new GetPublicHelperByKeywordTask();
		getPublicHelperByKeywordTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, string);

	}

	public class GetPublicHelperByKeywordTask extends AsyncTask<String, Integer, ArrayList<Public_Bean>> {

		@Override
		protected ArrayList<Public_Bean> doInBackground(String... params) {
			String keyword = params[0];
			ArrayList<Public_Bean> listPublic_service = new ArrayList<Public_Bean>();
			try {
				get_Public_Helpers_By_Keyword_NetRequest = new Get_Public_Helpers_By_Keyword_NetRequest(RecommendHelperActivity.this.getApplicationContext());
				listPublic_service = get_Public_Helpers_By_Keyword_NetRequest.doFindPublicHelperByKeyword(keyword);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return listPublic_service;
		}

		@Override
		protected void onPostExecute(ArrayList<Public_Bean> result) {
			super.onPostExecute(result);
				if (result!=null) {
					list.clear();
					list.addAll(result);
					searchResults.setVisibility(TextView.GONE);
					shootListViewAdapter.notifyDataSetChanged();
				}else {
					searchResults.setVisibility(TextView.VISIBLE);
				}
			progressBar.setVisibility(ProgressBar.GONE);
		}
	}

	@Override
	public void dealWithRadio(Intent intent) {
		super.dealWithRadio(intent);
		String action = intent.getAction();
		if (action.equals(MessageConstants.REFRESH_USER)) {
			setPublicStatus();
		}
	}
}

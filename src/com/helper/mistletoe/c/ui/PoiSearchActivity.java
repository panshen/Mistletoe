package com.helper.mistletoe.c.ui;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.helper.mistletoe.R;
import com.helper.mistletoe.v.MyCheckBox;

/**
 * 演示poi搜索功能
 */
public class PoiSearchActivity extends Activity implements OnGetPoiSearchResultListener, OnGetSuggestionResultListener {

	private BDLocation location_final = new BDLocation();// 最终位置
	private PoiSearch mPoiSearch = null;
	private SuggestionSearch mSuggestionSearch = null;
	private ListView place_lv;
	private ArrayList<SuggestionInfo> suggestionInfo_all;
	private static SuggestionInfo selectSuggestionInfo = new SuggestionInfo();
	private static SelectPlaceListAdapter selectPlaceListAdapter;
	private Button back;
	/**
	 * 搜索关键字输入窗口
	 */
	private AutoCompleteTextView keyWorldsView = null;
	private ArrayAdapter<String> sugAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poisearch);
		place_lv = (ListView) findViewById(R.id.listView_place);
		back = (Button) findViewById(R.id.button_sure);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				back();
				finish();
			}
		});
		location_final = getIntent().getParcelableExtra("location");
		suggestionInfo_all = new ArrayList<SuggestionInfo>();
		selectPlaceListAdapter = new SelectPlaceListAdapter(PoiSearchActivity.this, suggestionInfo_all);

		place_lv.setAdapter(selectPlaceListAdapter);
		if (suggestionInfo_all.size() > 0) {
			place_lv.setVisibility(ListView.VISIBLE);
		} else {
			place_lv.setVisibility(ListView.GONE);
		}
		// 初始化搜索模块，注册搜索事件监听
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		mSuggestionSearch = SuggestionSearch.newInstance();
		mSuggestionSearch.setOnGetSuggestionResultListener(this);
		keyWorldsView = (AutoCompleteTextView) findViewById(R.id.searchkey);

		keyWorldsView.setFocusable(true);
		keyWorldsView.setFocusableInTouchMode(true);
		keyWorldsView.requestFocus();

		Timer timer = new Timer();
		timer.schedule(new TimerTask(){

			public void run()

			{

				InputMethodManager inputManager =

				(InputMethodManager) keyWorldsView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

				inputManager.showSoftInput(keyWorldsView, 0);

			}

		},100);
//		InputMethodManager inputManager = (InputMethodManager) keyWorldsView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//
//		inputManager.showSoftInput(keyWorldsView, 0);
		sugAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
		keyWorldsView.setAdapter(sugAdapter);

		/**
		 * 当输入关键字变化时，动态更新建议列表
		 */
		keyWorldsView.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				if (cs.length() <= 0) {
					return;
				}

				/**
				 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
				 */
				mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).city("").keyword(cs.toString()));
			}
		});

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mPoiSearch.destroy();
		mSuggestionSearch.destroy();
		super.onDestroy();
	}

	private void back() {
		try {
			Intent intent_poiSearch = new Intent();
			location_final.setLatitude(selectSuggestionInfo.pt.latitude);
			location_final.setLongitude(selectSuggestionInfo.pt.longitude);
			intent_poiSearch.putExtra("location", location_final);
			setResult(RESULT_OK, intent_poiSearch);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	/**
	 * 影响搜索按钮点击事件
	 * 
	 * @param v
	 */
	public void searchButtonProcess(View v) {
		String keyword = keyWorldsView.getText().toString();
		mPoiSearch.searchInCity((new PoiCitySearchOption()).city("").keyword(keyword).pageCapacity(10).pageNum(0));
	}

	public void onGetPoiResult(PoiResult result) {
		if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(PoiSearchActivity.this, "未找到结果", Toast.LENGTH_LONG).show();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			return;
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

			// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
			String strInfo = "在";
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			strInfo += "找到结果";
			Toast.makeText(PoiSearchActivity.this, strInfo, Toast.LENGTH_LONG).show();
		}
	}

	public void onGetPoiDetailResult(PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(PoiSearchActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(PoiSearchActivity.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onGetSuggestionResult(SuggestionResult res) {
		if (res == null || res.getAllSuggestions() == null) {
			return;
		}
		sugAdapter.clear();
		suggestionInfo_all.clear();
		for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
			if (info.key != null)
				sugAdapter.add(info.key);
			if (info.pt != null) {
				suggestionInfo_all.add(info);
			}

		}
		sugAdapter.notifyDataSetChanged();
		selectPlaceListAdapter.notifyDataSetChanged();
		place_lv.setAdapter(selectPlaceListAdapter);
		if (suggestionInfo_all.size() > 0) {
			place_lv.setVisibility(ListView.VISIBLE);
			selectSuggestionInfo = suggestionInfo_all.get(0);
		} else {
			place_lv.setVisibility(ListView.GONE);
		}
	}

	// private class MyPoiOverlay extends PoiOverlay {
	//
	// public MyPoiOverlay(BaiduMap baiduMap) {
	// super(baiduMap);
	// }
	//
	// @Override
	// public boolean onPoiClick(int index) {
	// super.onPoiClick(index);
	// PoiInfo poi = getPoiResult().getAllPoi().get(index);
	// // if (poi.hasCaterDetails) {
	// mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
	// .poiUid(poi.uid));
	// // }
	// return true;
	// }
	// }
	static class SelectPlaceListAdapter extends BaseAdapter {
		private static ArrayList<SuggestionInfo> list = null;
		private Context mContext;

		public SelectPlaceListAdapter(Context mContext, ArrayList<SuggestionInfo> list) {
			this.mContext = mContext;
			if (list == null) {
				SelectPlaceListAdapter.list = new ArrayList<SuggestionInfo>();
			} else {
				SelectPlaceListAdapter.list = list;

			}
		}

		// 当ListView数据发生变化时,调用此方法来更新ListView
		// @param list
		public void updateListView(ArrayList<SuggestionInfo> list) {
			SelectPlaceListAdapter.list = list;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return SelectPlaceListAdapter.list.size();
		}

		@Override
		public SuggestionInfo getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View view, ViewGroup arg2) {
			ViewHolder viewHolder = null;
			final SuggestionInfo mContent = list.get(position);
			if (view == null) {
				viewHolder = new ViewHolder();
				view = LayoutInflater.from(mContext).inflate(R.layout.place_list_item, null);
				viewHolder.tvName = (TextView) view.findViewById(R.id.place_list_item_textView_name);
				viewHolder.myCb = (MyCheckBox) view.findViewById(R.id.place_list_item_checkBox);
				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}
			viewHolder.tvName.setText(mContent.city + mContent.district + mContent.key);
			viewHolder.myCb.setIndex(position);
			viewHolder.myCb.setOnCheckedChangeListener(MyOnClickListener.getInstance());
			if (selectSuggestionInfo.equals(mContent)) {
				viewHolder.myCb.setChecked(true);
			} else {
				viewHolder.myCb.setChecked(false);
			}
			return view;
		}

		class ViewHolder {
			TextView tvName;
			MyCheckBox myCb;
		}

		// 用单例模式构造一个Listener
		static class MyOnClickListener implements OnCheckedChangeListener {

			private static MyOnClickListener instance = null;

			private MyOnClickListener() {
			}

			public static MyOnClickListener getInstance() {
				if (instance == null)
					instance = new MyOnClickListener();
				return instance;
			}

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				int index = ((MyCheckBox) buttonView).getIndex();
				SuggestionInfo info = list.get(index);
				if (isChecked) { // 被选中
					selectSuggestionInfo = info;
					selectPlaceListAdapter.notifyDataSetChanged();
				}
			}
		}
	}
}

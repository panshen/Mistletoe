package com.helper.mistletoe.c.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.work.base.inter.WorkAsync;
import com.helper.mistletoe.m.work.be.cloud.Lbs_Location_Event;
import com.helper.mistletoe.m.work.ui.Lbs_LocationOk_Event;
import com.helper.mistletoe.util.ExceptionHandle;

import de.greenrobot.event.EventBus;

/**
 *用来展示如何结合定位SDK实现定位，并使用MyLocationOverlay绘制定位位置 同时展示如何使用自定义图标绘制并点击时弹出泡泡
 * 
 */
public class LocationActivity extends Activity implements WorkAsync{
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BDLocation location_final;// 最终位置

	MapView mMapView;
	BaiduMap mBaiduMap;
	Button send,search;
	private int targetId;

	// UI相关
	OnCheckedChangeListener radioButtonListener;
//	Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_location);
		targetId=getIntent().getIntExtra("targetId", -1);
		//注册EventBus  
	    EventBus.getDefault().register(this); 
	    send=(Button) findViewById(R.id.button_send);
	    search=(Button) findViewById(R.id.button_search);
	    send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(new Lbs_Location_Event());
				finish();
			}
		});
	    search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent_poiSearch=new Intent();
				intent_poiSearch.setClass(LocationActivity.this, PoiSearchActivity.class);
				intent_poiSearch.putExtra("location", location_final);
				startActivityForResult(intent_poiSearch, 1);
			}
		});
//		requestLocButton = (Button) findViewById(R.id.button1);
//		mCurrentMode = LocationMode.NORMAL;
//		requestLocButton.setText("普通");
//		OnClickListener btnClickListener = new OnClickListener() {
//			public void onClick(View v) {
//				switch (mCurrentMode) {
//				case NORMAL:
//					requestLocButton.setText("跟随");
//					mCurrentMode = LocationMode.FOLLOWING;
//					mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
//					break;
//				case COMPASS:
//					requestLocButton.setText("普通");
//					mCurrentMode = LocationMode.NORMAL;
//					mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
//					break;
//				case FOLLOWING:
//					requestLocButton.setText("罗盘");
//					mCurrentMode = LocationMode.COMPASS;
//					mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
//					break;
//				}
//			}
//		};
//		requestLocButton.setOnClickListener(btnClickListener);
		mCurrentMode = LocationMode.FOLLOWING;
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mMapView.showZoomControls(false);
		mMapView.showScaleControl(false);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
		// 定义地图状态
		MapStatus mMapStatus = new MapStatus.Builder().zoom(15).build();
		// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
		// 改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		getLocationClient().setLocOption(option);
		getLocationClient().start();
	}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	switch (requestCode) {
	case 1:
		if (data!=null) {
			location_final=data.getExtras().getParcelable("location");
//			Log.v("location", "传递进："+location_final.getLatitude()+location_final.getLatitude());
			if (location_final==null|| mMapView == null) 
				return;
			  disPlay(location_final);
		}
		break;
	default:
		break;
	}
}
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			location_final=location;
			disPlay(location_final);
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}
	public void disPlay(BDLocation location) {
		// TODO Auto-generated method stub
		MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
		// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
		mBaiduMap.setMyLocationData(locData);
		if (isFirstLoc) {
			Log.v("location", "显示："+location.getLatitude()+location.getLatitude());
			isFirstLoc = false;
			LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
			mBaiduMap.animateMapStatus(u);
		}
	}
	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		//注册EventBus  
	    EventBus.getDefault().unregister(this); 
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}

	@Override
	public void doWork(Context context) {
		
	}

	public LocationClient getLocationClient() {
		return mLocClient;
	}

	public void setLocationClient(LocationClient locationClient) {
		this.mLocClient = locationClient;
	}

	public void onEventMainThread(Lbs_LocationOk_Event event) {
		try {
			// 获取到结果
//			BDLocation location = event.getLocation();
//			Log.v("location", "发送："+location_final.getLatitude()+location_final.getLatitude());
			// 刷新结果
			Schedule_Bean.sendSchedule_Location(LocationActivity.this, targetId, null, "", location_final.getLatitude(), location_final.getLongitude());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}

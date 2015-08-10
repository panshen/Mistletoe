package com.helper.mistletoe.v.schedulecell.v1;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.RoutePlanActivity;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;

public class Location_SC extends Standard_SC implements OnGetGeoCoderResultListener{
	protected MapView vMap;
	private TextView address;
	private BaiduMap mBaiduMap;
	private Context context;
	private GeoCoder mGeoCoder = null;

	public Location_SC(Context context) {
		super(context);
		this.context=context;
	}

	@Override
	public View findView() {
		View result = super.findView();
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	protected void initView(View view) {
		super.initView(view);
		try {
			vMap = (MapView) view.findViewById(R.id.sci_location);
			address=(TextView) view.findViewById(R.id.textView_location);
			mGeoCoder = GeoCoder.newInstance();
			mGeoCoder.setOnGetGeoCodeResultListener(this);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void updateShow(Target_Bean target, Schedule_Bean schedule) {
		super.updateShow(target, schedule);
		try {
			// 地图初始化
			mBaiduMap = vMap.getMap();
			final BDLocation tLocation = schedule.getLocation();

			vMap.showZoomControls(false);
			vMap.showScaleControl(false);
			
			// 普通地图
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			LatLng cenpt = new LatLng(tLocation.getLatitude(), tLocation.getLongitude());
			// 定义地图状态
			MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(15).build();
			// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
			MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
			// 改变地图状态
			mBaiduMap.setMapStatus(mMapStatusUpdate);
			// 构建Marker图标
			BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
			// 构建MarkerOption，用于在地图上添加Marker
			OverlayOptions option = new MarkerOptions().position(cenpt).icon(bitmap);
			// 在地图上添加Marker，并显示
			mBaiduMap.addOverlay(option);
			mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
            mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
				
				@Override
				public boolean onMapPoiClick(MapPoi arg0) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public void onMapClick(LatLng arg0) {
					// TODO Auto-generated method stub
					Intent intent_Location=new Intent();
					intent_Location.setClass(context, RoutePlanActivity.class);
					intent_Location.putExtra("latitude",tLocation.getLatitude());
					intent_Location.putExtra("longitude",tLocation.getLongitude());
					context.startActivity(intent_Location);
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setLinseaner(Target_Bean target, Schedule_Bean schedule) {
		super.setLinseaner(target, schedule);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.schedule_item_location_layout;
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(context, "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
			return;
		}
//		mBaiduMap.clear();
//		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
//				.icon(BitmapDescriptorFactory
//						.fromResource(R.drawable.icon_gcoding)));
//		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
//				.getLocation()));
		address.setText(result.getAddress());
//		Toast.makeText(context, result.getAddress(),
//				Toast.LENGTH_LONG).show();

	}
		
}
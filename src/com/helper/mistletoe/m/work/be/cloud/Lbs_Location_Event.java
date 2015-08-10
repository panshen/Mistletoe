package com.helper.mistletoe.m.work.be.cloud;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.helper.mistletoe.m.work.base.WorkAsync_Event;
import com.helper.mistletoe.m.work.ui.Lbs_LocationOk_Event;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * 由张久瑞在2015年5月4日创建<br/>
 * 创建或者重新发送进度更新
 * 
 * @author 张久瑞
 */
public class Lbs_Location_Event extends WorkAsync_Event {
	public LocationClient mLocationClient;// 百度定位
	public Lbs_Location_Event(){
		
	}
//	public Lbs_Location_Event(LocationClient locationClient) {
//		try {
//			setLocationClient(locationClient);
//		} catch (Exception e) {
//			ExceptionHandle.ignoreException(e);
//		}
//	}

	@Override
	public void doWork(Context context) {
		super.doWork(context);
		try {
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);// 打开gps
			option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
			option.setScanSpan(1000);// 设置发起定位请求的间隔时间为5000ms
			getLocationClient().setLocOption(option);
			getLocationClient().start();
//		    post(new Lbs_Location_Event(mLocationClient));	
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public LocationClient getLocationClient() {
		return mLocationClient;
	}

	public void setLocationClient(LocationClient locationClient) {
		this.mLocationClient = locationClient;
	}

}

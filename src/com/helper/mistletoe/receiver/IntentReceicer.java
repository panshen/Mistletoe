//package com.helper.mistletoe.receiver;
//
//import com.helper.mistletoe.m.db.BroadcastService;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//public class IntentReceicer extends BroadcastReceiver {
//	private BroadcastService rec;
//
//	public IntentReceicer(BroadcastService rec) {
//		super();
//		this.rec=rec;
//	}
//
//	@Override
//	public void onReceive(Context context, Intent intent) {
//		// TODO Auto-generated method stub
//	        String name = intent.getExtras().getString("name");  
//	        Log.i("Recevier1", "接收到:"+name);
//	        this.rec.refreshInterface(intent);
////	        //终止广播，在这里我们可以稍微处理，根据用户输入的号码可以实现短信防火墙。   
////	        abortBroadcast();
//	}    
// }

package com.helper.mistletoe.util.prcomode.v3;

import android.os.Handler;
import android.os.Message;

import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.ReadyGoooFactory;

public class FactoryHandler_v3 extends Handler {
	private ReadyGoooFactory factory;

	@Override
	public void handleMessage(Message msg) {
		try {
			switch (msg.what) {
			case 101:
				// 此时生产者刚刚完成一项工作
				factory.notifyConsumer();
				break;
			case 102:
				// 此时生产者完成了工作，并且没有找到新的任务
				factory.notifyProducer();
				break;
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setFactory(ReadyGoooFactory factory) {
		this.factory = factory;
	}
}
package com.helper.mistletoe.util.prcomode.v3;

import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.ReadyGoooFactory;

public abstract class FactoryWork_v3 extends Work_v3 {
	public void publishWork(ReadyGoooFactory factory) {
		try {
			factory.publishWork(this);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public boolean hasResponse() {
		return true;
	}
}
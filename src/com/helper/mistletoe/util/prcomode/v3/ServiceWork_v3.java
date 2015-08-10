package com.helper.mistletoe.util.prcomode.v3;

import android.content.Context;
import android.content.Intent;

import com.helper.mistletoe.c.service.ContacterSyncService;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.Broadcast_Const;

public abstract class ServiceWork_v3 extends Work_v3 {
	public void publishWork(Context context) {
		try {
			Intent intent = new Intent(context, ContacterSyncService.class);
			intent.putExtra("com.helper.mistletoe.bbc.data", this.packToJson());
			intent.putExtra(Broadcast_Const.BC_MODE_STATUS,
					Broadcast_Const.BC_TAG_WORK);
			context.startService(intent);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public boolean hasResponse() {
		return false;
	}
}
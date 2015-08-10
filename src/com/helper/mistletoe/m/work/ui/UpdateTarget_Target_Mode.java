package com.helper.mistletoe.m.work.ui;

import android.content.Context;

import com.helper.mistletoe.m.net.request.Target_Update_NetRequest;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.work.base.BaseWork_Mode;
import com.helper.mistletoe.m.work.base.inter.WorkCallBack_Mode;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * <p>
 * Created by 张久瑞 on 2015年3月13日.
 * </p>
 * 
 * @author 张久瑞
 * @version 1.0
 */
public class UpdateTarget_Target_Mode extends BaseWork_Mode {
	private Target_Bean target;// 结果：Target列表
	private String response_json;// 结果：Target列表

	public UpdateTarget_Target_Mode(WorkCallBack_Mode workCallBack, Context context, Target_Bean target) {
		super(workCallBack, context);
		try {
			this.target = target;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String result = "";
		try {
			Target_Update_NetRequest fewfwef = new Target_Update_NetRequest(context);
			// response_json = fewfwef.updateTarget(target);
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public String getResponse_json() {
		if (response_json == null) {
			response_json = "";
		}
		return response_json;
	}

}
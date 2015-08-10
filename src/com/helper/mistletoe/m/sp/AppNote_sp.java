package com.helper.mistletoe.m.sp;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

import com.helper.mistletoe.m.pojo.AppNote_Bean;
import com.helper.mistletoe.m.sp.base.Base_sp;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * Created by stemp1234 on 15/3/11.
 */
public class AppNote_sp extends Base_sp<AppNote_Bean> {

	public AppNote_sp(Context context) {
		super(context, "appnote_sp");
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void read(AppNote_Bean bean) throws Exception {
		try {
			// 安全检查
			if (bean == null) {
				bean = new AppNote_Bean();
			}

			if (getSP().contains("lastAppVersion")) {
				bean.setLastAppVersion(getSP().getString("lastAppVersion", "0"));
			}
			if (getSP().contains("pageCache")) {
				JSONObject tRes = new JSONObject();
				String defaultRes = tRes.toString();
				tRes = new JSONObject(getSP().getString("pageCache", defaultRes));
				bean.setPageCache(tRes);
			}
			if (getSP().contains("newHelperNumber")) {
				bean.setNewHelperNumber(getSP().getInt("newHelperNumber", 0));
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	public AppNote_Bean read() throws Exception {
		AppNote_Bean result = null;
		try {
			result = new AppNote_Bean();
			read(result);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	@Override
	public void write(AppNote_Bean bean) throws Exception {
		try {
			// 实例化getSP().Editor对象（第二步）
			SharedPreferences.Editor editor = getSP().edit();
			// 用putString的方法保存数据
			if (bean.lastAppVersion != null) {
				editor.putString("lastAppVersion", bean.getLastAppVersion());
			}
			if (bean.pageCache != null) {
				editor.putString("pageCache", bean.getPageCache().toString());
			}
			if (bean.newHelperNumber != null) {
				editor.putInt("newHelperNumber", bean.getNewHelperNumber());
			}
			// 提交当前数据
			editor.commit();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}
}
package com.helper.mistletoe.m.net.request;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.SparseArray;

import com.helper.mistletoe.m.net.request.base.Template_NetRequest;
import com.helper.mistletoe.m.pojo.NetRequest_Bean;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.Array_Util;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.SnaEnum;
import com.helper.mistletoe.util.sysconst.MainConst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class TargetMember_Watch_NetRequest extends Template_NetRequest {
	public TargetMember_Watch_NetRequest(Context context) {
		super(context, NetRequest_Bean.REQUEST_TYPE_POST, MainConst.NET_TARGETMEMBER_WATCH);
	}

	public boolean getList(Target_Bean target, JSONArray members, OperatingState state) {
		boolean result = false;
		try {
			// 请求参数
			// 获取data
			String data = fromateData(target, members, state);
			// 连接
			openConnection(data);
			// 返回结果
			String tResc = "";
			if (getResultData().getResult().equals("0")) {
				tResc = getResultData().getLoc_data();
				// 访问成功

				result = true;
			} else {
				// 访问失败
				tResc = getResultData().getResult_msg();

				result = false;
			}
		} catch (Exception e) {
			result = false;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	private String fromateData(Target_Bean target, JSONArray members, OperatingState state) {
		String result = "";
		try {
			JSONObject jData = new JSONObject();

			jData.put("target_id", (int) target.getTarget_id());
			jData.put("members", (JSONArray) members);
			jData.put("watch", (int) state.toInt());

			result = jData.toString();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 操作类型
	 * 
	 * @author 张久瑞
	 */
	public enum OperatingState implements SnaEnum {
		/**
		 * 加入
		 */
		Join(1, "加入"),
		/**
		 * 退出
		 */
		SignOut(2, "退出");

		private int mInt;
		private String mDescription;

		private OperatingState(int _int, String _Description) {
			this.mInt = _int;
			this.mDescription = _Description;
		}

		@Override
		public String getDescription() {
			return this.mDescription;
		}

		@Override
		public int toInt() {
			return this.mInt;
		}

		public static OperatingState valueOf(int value, OperatingState defaultValue) {
			OperatingState result = defaultValue;
			try {
				SparseArray<OperatingState> valueMap = Array_Util.getEnumValueMap(OperatingState.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static OperatingState valueOf(int value) {
			return valueOf(value, Join);
		}
	}
}
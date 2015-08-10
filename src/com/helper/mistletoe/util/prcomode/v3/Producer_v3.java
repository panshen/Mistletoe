package com.helper.mistletoe.util.prcomode.v3;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.Producer;
import com.helper.mistletoe.util.prcomode.ReadyGoooWork;
import com.helper.mistletoe.util.sysconst.Broadcast_Const;

public class Producer_v3 implements Producer, Runnable {
	private Context context;
	private AssemblyList_v3<String> m_workList;
	private AssemblyList_v3<ReadyGoooWork> m_responseList;
	private Handler handler;

	public Producer_v3(Context context, Handler handler, AssemblyList_v3<String> workList, AssemblyList_v3<ReadyGoooWork> responseList) {
		super();
		try {
			this.context = context;
			this.m_workList = workList;
			this.m_responseList = responseList;
			this.handler = handler;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void run() {
		try {
			String work = m_workList.getWork();
			while (work != null) {
				doWork(work);
				work = m_workList.getWork();
				handler.sendEmptyMessage(101);
			}
			handler.sendEmptyMessage(102);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork(ReadyGoooWork work) {
		try {
			work.doWork(context);
			if (work.hasResponse()) {
				m_responseList.addWork(work);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void doWork(String work) {
		try {
			// 取出内容
			JSONObject tAddData_Json = new JSONObject(work);
			String tClassName = "";
			String tResData = "";
			if (tAddData_Json.has(Broadcast_Const.BC_MODE_DATA)) {
				tResData = tAddData_Json.getString(Broadcast_Const.BC_MODE_DATA);
			}
			if (tAddData_Json.has(Broadcast_Const.BC_MODE_CLASS)) {
				tClassName = tAddData_Json.getString(Broadcast_Const.BC_MODE_CLASS);
			}
			// 执行任务
			ReadyGoooWork workInstance = (ReadyGoooWork) new Gson().fromJson(tResData, Class.forName(tClassName));
			doWork(workInstance);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected Context getContext() {
		return context;
	}

	protected Context getApplicationContext() {
		return getContext().getApplicationContext();
	}
}
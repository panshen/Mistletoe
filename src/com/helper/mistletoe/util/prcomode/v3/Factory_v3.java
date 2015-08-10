package com.helper.mistletoe.util.prcomode.v3;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.os.Handler;

import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.LogPrint;
import com.helper.mistletoe.util.prcomode.Consumer;
import com.helper.mistletoe.util.prcomode.ReadyGoooFactory;
import com.helper.mistletoe.util.prcomode.ReadyGoooWork;

public class Factory_v3 implements ReadyGoooFactory {
	private Context context;
	private int maxTask;
	private AssemblyList_v3<String> m_workList;
	private AssemblyList_v3<ReadyGoooWork> m_responseList;
	private Consumer leader;
	private ArrayList<Thread> robots;
	private Handler handler;

	public Factory_v3(Context context, FactoryHandler_v3 handler, Consumer leader) {
		super();
		try {
			this.context = context;
			this.maxTask = 3;
			this.m_workList = new AssemblyList_v3<String>();
			this.m_responseList = new AssemblyList_v3<ReadyGoooWork>();
			this.leader = leader;
			this.robots = new ArrayList<Thread>();
			this.handler = handler;

			handler.setFactory(this);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void publishWork(ReadyGoooWork work) {
		try {
			publishWork(work.packToJson());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void publishWork(String work) {
		try {
			m_workList.addWork(work);
			dispatchWorker();
			notifyProducer();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void notifyProducer() {
		try {
			if (m_workList.getSize() > 0) {
				if (robots.size() < getProducerMax()) {
					Thread t_Thread = new Thread(new Producer_v3(context, handler, m_workList, m_responseList));
					robots.add(t_Thread);
					t_Thread.start();
				}
			}
			LogPrint.printString_V("Production/Consumption Mode", "当前运行的线程数量" + robots.size());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void dispatchWorker() {
		try {
			LogPrint.printString_V("Production/Consumption Mode", "队列里有任务" + robots.size());
			for (Iterator<Thread> iterator = robots.iterator(); iterator.hasNext();) {
				Thread.State state = iterator.next().getState();
				if ((!state.equals(Thread.State.RUNNABLE)) && (!state.equals(Thread.State.RUNNABLE))) {
					iterator.remove();
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void notifyConsumer() {
		try {
			ReadyGoooWork workResponse = m_responseList.getWork();
			if (workResponse != null) {
				leader.onWorkOk(workResponse);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public int getProducerMax() {
		return maxTask;
	}

	@Override
	public void setProducerMax(int workerMax) {
		maxTask = workerMax;
	}

	protected Context getContext() {
		return context;
	}

	protected Context getApplicationContext() {
		return getContext().getApplicationContext();
	}

}
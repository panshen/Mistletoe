package com.helper.mistletoe.util.prcomode.v3;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.AssemblyList;

public class AssemblyList_v3<T> implements AssemblyList<T> {
	protected ConcurrentLinkedQueue<T> list;

	@Override
	public void addWork(T work) {
		try {
			// 在流水线中加入产品
			getList().add(work);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public T getWork() {
		T result = null;
		try {
			// 从流水线中取出产品
			result = getList().poll();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public int getSize() {
		int result = 0;
		try {
			// 从流水线中取出产品
			result = getList().size();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public ConcurrentLinkedQueue<T> getList() {
		if (list == null) {
			list = new ConcurrentLinkedQueue<T>();
		}
		return list;
	}
}
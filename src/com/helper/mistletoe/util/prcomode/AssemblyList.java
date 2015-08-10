package com.helper.mistletoe.util.prcomode;

public interface AssemblyList<T> {
	// 装入
	public void addWork(T work);

	// 取出
	public T getWork();
}
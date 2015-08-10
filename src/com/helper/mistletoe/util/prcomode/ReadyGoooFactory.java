package com.helper.mistletoe.util.prcomode;

public interface ReadyGoooFactory {
	// 发布工作
	public void publishWork(ReadyGoooWork work);

	// 发布工作
	public void publishWork(String work);

	// 通知临时工
	public void notifyProducer();

	// 通知领导
	public void notifyConsumer();

	// 读工人人数上限
	public int getProducerMax();

	// 写工人人数上限
	public void setProducerMax(int workerMax);
}
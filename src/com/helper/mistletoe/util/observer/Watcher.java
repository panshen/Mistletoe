package com.helper.mistletoe.util.observer;

import java.util.HashSet;

import com.helper.mistletoe.util.prcomode.ReadyGoooFactory;

/**
 * 由张久瑞在2015年4月18日创建<br/>
 * 作为一个组件存在Controller中，当广播、意图来时调用观察者，观察者解析，调用响应函数
 * 
 * @author 张久瑞
 */
public interface Watcher {
	/**
	 * 调用此函数去做一项工作，Flag为此事件的标记，Data为参数
	 * 
	 * @param flag
	 *            标志着不同的事件oF
	 * @param data
	 *            参数，可以为空
	 */
	public void doWorkReceiver(String status, String className, String data);

	/**
	 * 获得此观察者的兴趣列表
	 * 
	 * @return 兴趣列表，列表里包含了这个观察者感兴趣的Flag
	 */
	public HashSet<String> getFilter();

	public ReadyGoooFactory getWorkFactory();
}
package com.helper.mistletoe.v.redpoint;

/**
 * @see ReadPoint_inter
 * @author 张久瑞
 * @version 1.0
 */
public interface ReadPoint_Plus extends ReadPoint_inter {
	/**
	 * 清空未读消息数量，会置为最小的消息数值
	 * 
	 * @throws Exception
	 *             未知异常
	 */
	public void cleanNumber() throws Exception;

	/**
	 * 增加未读消息数量
	 * 
	 * @param number
	 *            要增加的消息数目
	 * @return 当前未读消息数量
	 * @throws Exception
	 *             未知异常
	 */
	public int numberAdd(int number) throws Exception;

	/**
	 * 减少未读消息数量
	 * 
	 * @param number
	 *            要减少的消息数目
	 * @return 当前未读消息数量
	 * @throws Exception
	 *             未知异常
	 */
	public int numberDetract(int number) throws Exception;
}
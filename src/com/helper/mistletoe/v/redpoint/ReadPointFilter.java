package com.helper.mistletoe.v.redpoint;

/**
 * @see ReadPoint_inter
 * @author 张久瑞
 * @version 1.0
 */
public interface ReadPointFilter {
	/**
	 * 可以设置某些特殊的数字不按照显示风格显示<br/>
	 * 例：输入0时返回hide
	 * 
	 * @param number
	 *            消息数目
	 * @return 这个数字如何显示
	 * @throws Exception
	 *             未知异常
	 */
	public String setFilter(int number) throws Exception;
}
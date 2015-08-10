package com.helper.mistletoe.v.redpoint;

/**
 * <p>
 * 红点控件<br/>
 * 这个控件里面的红点大小是固定的，子控件的对齐方式为"right|top"
 * <table border="0px" cellpadding="3px" cellspacing="0px" style="text-align: center;">
 * <tr>
 * <td ><font color="#F3C"></font></td>
 * <td ><font color="#F3C">小红点</font></td>
 * <td ><font color="#F3C">带数字的提示</font></td>
 * </tr>
 * <tr>
 * <td ><font color="#F3C">宽</font></td>
 * <td ><font color="#06F">10dp</font></td>
 * <td ><font color="#06F">10dp</font></td>
 * </tr>
 * <tr>
 * <td ><font color="#F3C">高</font></td>
 * <td ><font color="#06F">wrap_content</font></td>
 * <td ><font color="#06F">20dp</font></td>
 * </tr>
 * </table>
 * </p>
 * 
 * @author 张久瑞
 * @version 1.0
 */
public interface ReadPoint_inter {
	/**
	 * 显示方式：显示数字
	 */
	public static final String SHOWTYPE_NUMBER = "number";
	/**
	 * 显示方式：只显示红点
	 */
	public static final String SHOWTYPE_POINT = "point";
	/**
	 * 显示方式：什么都不显示^_^
	 */
	public static final String SHOWTYPE_HIDE = "hide";

	/**
	 * 设置未读消息数量
	 * 
	 * @param number
	 *            有多少条未读消息
	 * @throws Exception
	 *             未知异常
	 */
	public void setNumber(int number) throws Exception;

	/**
	 * 获得未读消息数量
	 * 
	 * @return 获得当前有几条未读
	 * @throws Exception
	 *             未知异常
	 */
	public int getNumber() throws Exception;

	/**
	 * 设置显示方式，可以有两种选择：<br/>
	 * 显示数字ReadPoint.SHOWTYPE_NUMBER<br/>
	 * 只显示红点ReadPoint.SHOWTYPE_POINT
	 * 
	 * @param showType
	 *            显示方式
	 * @throws Exception
	 *             未知异常
	 */
	public void setShowType(String showType) throws Exception;

	/**
	 * 设置显示的最大值，超过最大值后显示加号，默认最大值为99<br/>
	 * 例：最大值为99，设置101显示99+
	 * 
	 * @param maxNumber
	 *            最大可显示的消息数量
	 * @throws Exception
	 *             未知异常
	 */
	public void setMaxNumber(int maxNumber) throws Exception;

	/**
	 * 设置显示的最小值，小于最小值显示最小值，默认最小值为0<br/>
	 * 例：最小值为0，设置为-3时显示0
	 * 
	 * @param minNumber
	 *            最小可显示的消息数量
	 * @throws Exception
	 *             未知异常
	 */
	public void setMinNumber(int minNumber) throws Exception;

	/**
	 * 可以设置一个过滤器，使某些特殊的数字不按照显示风格显示<br/>
	 * 
	 * @param filter
	 *            过滤器
	 * @throws Exception
	 *             未知异常
	 */
	public void setFilter(ReadPointFilter filter) throws Exception;
}
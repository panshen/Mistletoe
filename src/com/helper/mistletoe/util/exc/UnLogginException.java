package com.helper.mistletoe.util.exc;

/**
 * <p>
 * Created by 张久瑞 on 2015年3月18日.
 * </p>
 * 
 * @author 张久瑞
 * @version 1.0
 */
public class UnLogginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnLogginException() {
		super();
	}

	public UnLogginException(String msg) {
		super(msg);
	}

	public UnLogginException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public UnLogginException(Throwable cause) {
		super(cause);
	}

}
package com.helper.mistletoe.util.sysconst;

/**
 * Created by stemp1234 on 15/3/4.
 */
public interface NetUrl_Note_Const extends NetUrlBase_Const {
	public final static String NET_NOTE_GET = NET_BASE_URL + "note/get";
	public final static String NET_NOTE_WITHDRAW = NET_BASE_URL + "note/withdraw";
	public final static String NET_NOTE_CREATE = NET_BASE_URL + "note/create";
	public final static String NET_NOTE_HIGHLIGHT = NET_BASE_URL + "note/highlight";
	/**
	 * 标签(获取note标签)
	 */
	public final static String NET_NOTE_GETTAG = NET_BASE_URL + "note/get-tag";
}
package com.helper.mistletoe.c.ui.base;

public interface UIInterface {
	public abstract void showToast(CharSequence text, int duration) throws Exception;

	public abstract void showToast(CharSequence text) throws Exception;
}
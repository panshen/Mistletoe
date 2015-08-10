package com.helper.mistletoe.m.pojo;

import android.content.Context;

import com.helper.mistletoe.m.sp.NoteTag_sp;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * Created by 张久瑞 on 15/3/4.
 */
public class NoteTagList_Bean extends NoteTagList_Pojo {
	public String getTagName(int tagId) {
		String result = "";
		try {
			NoteTag_Bean tTag = getTags().get(tagId);
			if (tTag != null) {
				result = tTag.getTag();
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public Integer getTagId(String tagName) {
		Integer result = null;
		try {
			for (int i = 0; i <= getTags().size(); i++) {
				NoteTag_Bean temp = getTags().get(i);
				if (temp.getTag().equals(tagName)) {
					result = temp.getId();
					break;
				}
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public static NoteTagList_Bean getInstance(Context context) {
		NoteTagList_Bean result = new NoteTagList_Bean();
		try {
			NoteTag_sp usp = new NoteTag_sp(context);
			result = usp.read();
		} catch (Exception e) {
			result = new NoteTagList_Bean();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
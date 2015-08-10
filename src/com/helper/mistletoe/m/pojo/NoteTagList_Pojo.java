package com.helper.mistletoe.m.pojo;

import java.util.ArrayList;

import org.json.JSONObject;

import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * Created by 张久瑞 on 15/3/4.
 */
public class NoteTagList_Pojo {
	public SparseArray<NoteTag_Bean> mTags;// Tag列表

	// TODO 以下为Get Set函数
	public String getTags_String() {
		String result = new JSONObject().toString();
		try {
			Gson tGson = new Gson();
			ArrayList<NoteTag_Bean> tList = new ArrayList<NoteTag_Bean>();
			for (int i = 0; i < getTags().size(); i++) {
				tList.add(getTags().valueAt(i));
			}
			result = tGson.toJson(tList);
		} catch (Exception e) {
			result = new JSONObject().toString();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public SparseArray<NoteTag_Bean> getTags() {
		if (mTags == null) {
			mTags = new SparseArray<NoteTag_Bean>();
		}
		return mTags;
	}

	public void setTags(String tags) {
		try {
			Gson tGson = new Gson();
			ArrayList<NoteTag_Bean> tList = tGson.fromJson(tags, new TypeToken<ArrayList<NoteTag_Bean>>() {
			}.getType());
			SparseArray<NoteTag_Bean> tTags = new SparseArray<NoteTag_Bean>(tList.size());
			for (NoteTag_Bean i : tList) {
				tTags.put(i.getId(), i);
			}
			setTags(tTags);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
	public void setTags(ArrayList<NoteTag_Pojo> tagList) {
		try {
			SparseArray<NoteTag_Bean> tTags = new SparseArray<NoteTag_Bean>(tagList.size());
			for (int i = 0; i < tagList.size(); i++) {
				NoteTag_Bean tag=new NoteTag_Bean();
				tag.setId(tagList.get(i).getId());
				tag.setTag(tagList.get(i).getTag());
				tag.setTarggetId(tagList.get(i).getTarggetId());
				tTags.put(tagList.get(i).getId(), tag);
			}
			setTags(tTags);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setTags(SparseArray<NoteTag_Bean> tags) {
		this.mTags = tags;
	}

}
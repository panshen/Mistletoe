package com.helper.mistletoe.m.sp;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import com.helper.mistletoe.m.pojo.NoteTagList_Bean;
import com.helper.mistletoe.m.sp.base.Base_sp;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * Created by stemp1234 on 15/3/11.
 */
public class NoteTag_sp extends Base_sp<NoteTagList_Bean> {
	public NoteTag_sp(Context context) {
		super(context, "notetag_sp");
	}
	@Override
	public void write(NoteTagList_Bean bean) {
		try {
			// 实例化getSP().Editor对象（第二步）
			SharedPreferences.Editor editor = getSP().edit();
			// 用putString的方法保存数据
			if (bean.mTags != null) {
				editor.putString("mTags", bean.getTags_String());
			}
			// 提交当前数据
			editor.commit();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public NoteTagList_Bean read() throws Exception {
		NoteTagList_Bean result = null;
		try {
			result = new NoteTagList_Bean();
			read(result);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public void read(NoteTagList_Bean bean) throws Exception {
		try {
			// 安全检查
			if (bean == null) {
				bean = new NoteTagList_Bean();
			}
			if (getSP().contains("mTags")) {
				bean.setTags(getSP().getString("mTags", new JSONObject().toString()));
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}
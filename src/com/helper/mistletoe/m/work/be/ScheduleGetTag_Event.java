package com.helper.mistletoe.m.work.be;

import android.content.Context;

import com.helper.mistletoe.m.pojo.NoteTagList_Bean;
import com.helper.mistletoe.m.work.base.WorkAsync_Event;
import com.helper.mistletoe.m.work.ui.ScheduleTagGetted_Event;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * 由张久瑞在2015年5月4日创建<br/>
 * 创建或者重新发送进度更新
 * 
 * @author 张久瑞
 */
public class ScheduleGetTag_Event extends WorkAsync_Event {

	@Override
	public void doWork(Context context) {
		super.doWork(context);
		try {
			// 从SP中取出Tag列表
			NoteTagList_Bean tNoteTagList = NoteTagList_Bean.getInstance(getContext());
			// 把结果发布出去
			post(new ScheduleTagGetted_Event(tNoteTagList));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}

package com.helper.mistletoe.m.work.ui;

import com.helper.mistletoe.m.pojo.NoteTagList_Bean;
import com.helper.mistletoe.m.work.base.WorkMainThread_Event;
import com.helper.mistletoe.util.ExceptionHandle;

public class ScheduleTagGetted_Event extends WorkMainThread_Event {
	private NoteTagList_Bean mNoteTagList;

	public ScheduleTagGetted_Event(NoteTagList_Bean noteTagList) {
		try {
			setNoteTagList(noteTagList);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public NoteTagList_Bean getNoteTagList() {
		if (mNoteTagList == null) {
			mNoteTagList = new NoteTagList_Bean();
		}
		return mNoteTagList;
	}

	public void setNoteTagList(NoteTagList_Bean noteTagList) {
		this.mNoteTagList = noteTagList;
	}

}
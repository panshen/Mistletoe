package com.helper.mistletoe.m.work.be.cloud;

import java.util.ArrayList;

import android.content.Context;

import com.helper.mistletoe.m.db.CostTagManager;
import com.helper.mistletoe.m.pojo.NoteTagList_Bean;
import com.helper.mistletoe.m.pojo.NoteTag_Pojo;
import com.helper.mistletoe.m.sp.NoteTag_sp;
import com.helper.mistletoe.m.work.base.WorkAsync_Event;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * 由张久瑞在2015年5月4日创建<br/>
 * 创建或者重新发送进度更新
 * 
 * @author 张久瑞
 */
public class SyncScheduleTag_Event extends WorkAsync_Event {

	@Override
	public void doWork(Context context) {
		super.doWork(context);
		try {
			// 从数据库获取Tag列表
			CostTagManager costManager=new CostTagManager();
			 ArrayList<NoteTag_Pojo> tagList= costManager.ReadCostTagFromDB(getContext());
			NoteTagList_Bean tTagList = new NoteTagList_Bean();
			tTagList.setTags(tagList);
			// 把Tag列表写入SP中
			if (tTagList.getTags().size() > 0) {
				NoteTag_sp tNsp = new NoteTag_sp(getContext());
				tNsp.write(tTagList);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}

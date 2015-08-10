package com.helper.mistletoe.m.db;

import java.util.ArrayList;
import com.helper.mistletoe.m.pojo.NoteTag_Pojo;
import android.content.ContentValues;

/**
 * @author 费用标签数据库服务接口
 */
public interface CostTagService {
	/**
	 * 添加tagList到数据库
	 * 
	 * @param values
	 * @return
	 * */
	public boolean addTagList(ArrayList<ContentValues> list);

	/**
	 * 获取tagList
	 * 
	 * @param is
	 *           
	 * @return
	 */
	public ArrayList<NoteTag_Pojo> getTagListByTargetId(Integer[] is);
	/**
	 * 获取tagList
	 * 
	 * @param is
	 *           
	 * @return
	 */
	public ArrayList<NoteTag_Pojo> getTagList();

	/**
	 * 获取tag
	 * 
	 * @param id
	 * @return
	 */
	public NoteTag_Pojo getTagById(Integer id);
	/**
	 * 添加Tag通过Id
	 * 
	 * @param
	 * @return
	 */
	public Boolean addTagById(ContentValues initialValues);
	/**
	 *删除tag
	 */
	public Boolean deletedTagByTargetId(Integer targetId);
}

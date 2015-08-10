package com.helper.mistletoe.roll;

import java.util.List;
import android.content.ContentValues;

/**
 * @author Hanzz 色子服务接口
 */
public interface RollService {

	/**
	 * 将摇色子的结果存到数据库中
	 * 
	 * @param values
	 * @return
	 */
	public boolean addFaceValues(ContentValues values);

	/**
	 * 删除数据库中所有数据
	 * 
	 * @return
	 */
	public boolean deleteFaceValues();

	/**
	 * 获取数据库中前1000条数据
	 * 
	 * @return
	 */
	public List<Dice_Bean> getFaceValues();
}

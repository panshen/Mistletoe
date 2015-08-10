package com.helper.mistletoe.roll;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author 列表数据导入，实现数据库中对表的具体操作
 */
public class RollServiceImpl implements RollService {

	private DatabaseRoll db_Roll = null;
	public static final String DATABASE_TABLE_ROLL = "roll";// 表名
	public static final String DATABASE_TABLE_ROLL_KEY = "_id";// 主键
	public static final String DATABASE_TABLE_ROLL_FIRSTDICE = "firstDice";
	public static final String DATABASE_TABLE_ROLL_SECONDDICE = "secondDice";
	public static final String DATABASE_TABLE_ROLL_THIRDDICE = "thirdDice";
	public static final String DATABASE_TABLE_ROLL_FOURTHDICE = "fourthDice";
	public static final String DATABASE_TABLE_ROLL_FIFTHDICE = "fifthDice";
	public static final String DATABASE_TABLE_ROLL_SIXTHDICE = "sixthDice";
	public static final String DATABASE_TABLE_ROLL_DICETIME = "diceTime";
	// 获取roll表字段
	private static final String[] ROLLS_PROJECTION = new String[] { DATABASE_TABLE_ROLL_KEY, DATABASE_TABLE_ROLL_FIRSTDICE, DATABASE_TABLE_ROLL_SECONDDICE, DATABASE_TABLE_ROLL_THIRDDICE,
			DATABASE_TABLE_ROLL_FOURTHDICE, DATABASE_TABLE_ROLL_FIFTHDICE, DATABASE_TABLE_ROLL_SIXTHDICE, DATABASE_TABLE_ROLL_DICETIME };

	public RollServiceImpl(Context context) {
		// TODO Auto-generated constructor stub
		db_Roll = new DatabaseRoll(context);
	}

	@Override
	public boolean addFaceValues(ContentValues values) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		long id = -1;
		try {
			database = db_Roll.getWritableDatabase();
			id = database.insert(DATABASE_TABLE_ROLL, null, values);
			flag = (id != -1 ? true : false);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	@Override
	public boolean deleteFaceValues() {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = db_Roll.getWritableDatabase();
			count = database.delete(DATABASE_TABLE_ROLL, null, null);
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	@Override
	public List<Dice_Bean> getFaceValues() {
		// TODO Auto-generated method stub
		List<Dice_Bean> list = new ArrayList<Dice_Bean>();
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {
			database = db_Roll.getReadableDatabase();
			cursor = database.query(DATABASE_TABLE_ROLL, ROLLS_PROJECTION, null, null, null, null, DATABASE_TABLE_ROLL_DICETIME + " " + "desc", "1000");
			if (cursor.getColumnCount() > 0) {
				while (cursor.moveToNext()) {
					Dice_Bean dice = new Dice_Bean();
					dice.setFirstDice(cursor.getInt(cursor.getColumnIndex(DATABASE_TABLE_ROLL_FIRSTDICE)));
					dice.setSecondDice(cursor.getInt(cursor.getColumnIndex(DATABASE_TABLE_ROLL_SECONDDICE)));
					dice.setThirdDice(cursor.getInt(cursor.getColumnIndex(DATABASE_TABLE_ROLL_THIRDDICE)));
					dice.setFourthDice(cursor.getInt(cursor.getColumnIndex(DATABASE_TABLE_ROLL_FOURTHDICE)));
					dice.setFifthDice(cursor.getInt(cursor.getColumnIndex(DATABASE_TABLE_ROLL_FIFTHDICE)));
					dice.setSixthDice(cursor.getInt(cursor.getColumnIndex(DATABASE_TABLE_ROLL_SIXTHDICE)));
					dice.setDiceTime(cursor.getLong(cursor.getColumnIndex(DATABASE_TABLE_ROLL_DICETIME)));
					list.add(dice);
				}
				cursor.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return list;
	}
}

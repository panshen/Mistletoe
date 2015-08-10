package com.helper.mistletoe.util;

import android.content.ContentValues;
import android.database.Cursor;

import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.NoteTag_Pojo;
import com.helper.mistletoe.m.pojo.Target_Bean;

public class DBUtil {
	/**
	 * 根据指针获取对应的helper
	 * 
	 * @param cursor
	 * @return
	 */
	public static Helpers_Sub_Bean getHelperByCursor(Cursor cursor) {
		Helpers_Sub_Bean helpers_Sub_Bean = new Helpers_Sub_Bean();
		helpers_Sub_Bean.setHelper_id(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_ID)));
		helpers_Sub_Bean.setHelper_account(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_ACCOUNT)));
		helpers_Sub_Bean.setHelper_account_type(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_ACCOUNTTYPE)));
		helpers_Sub_Bean.setHelper_name(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_NAME)));
		helpers_Sub_Bean.setHelper_memo_name(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_MEMONAME)));
		helpers_Sub_Bean.setHelper_readygo_name(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_READYGONAME)));
		helpers_Sub_Bean.setHelper_name_pinyin(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_NAMEPINYIN)));
		helpers_Sub_Bean.setHelper_memo_name_pinyin(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_MEMONAMEPINYIN)));
		helpers_Sub_Bean.setHelper_readygo_name_pinyin(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_READYGONAMEPINYIN)));
		helpers_Sub_Bean.setHelper_tel(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_TEL)));
		helpers_Sub_Bean.setHelper_mobile(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_MOBILE)));
		helpers_Sub_Bean.setHelper_gender(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_GENDER)));
		helpers_Sub_Bean.setHelper_zip(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_ZIP)));
		helpers_Sub_Bean.setHelper_fax(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_FAX)));
		helpers_Sub_Bean.setHelper_email(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_EMAIL)));
		helpers_Sub_Bean.setHelper_company(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_COMPANY)));
		helpers_Sub_Bean.setHelper_title(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_TITLE)));
		helpers_Sub_Bean.setHelper_country(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_COUNTRY)));
		helpers_Sub_Bean.setHelper_city(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_CITY)));
		helpers_Sub_Bean.setHelper_address(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_ADDRESS)));
		helpers_Sub_Bean.setHelper_webpage(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_WEBPAGE)));
		helpers_Sub_Bean.setHelper_photo(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_PHOTO)));
		helpers_Sub_Bean.setHelper_sign(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_SIGN)));
		helpers_Sub_Bean.setHelper_status(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_STATUS)));
		helpers_Sub_Bean.setHelper_verification(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_VERIFICATION)));
		helpers_Sub_Bean.setHelper_count(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_HELPERCOUNT)));
		helpers_Sub_Bean.setHelper_target_count(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_TARGETCOUNT)));
		helpers_Sub_Bean.setHelper_coin_count(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_COINCOUNT)));
		helpers_Sub_Bean.setHelper_view_count(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_VIEWCOUNT)));
		helpers_Sub_Bean.setHelper_memo(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_MEMO)));
		helpers_Sub_Bean.setHelper_msg(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_MSG)));
		helpers_Sub_Bean.setHelper_become_public_time(cursor.getLong(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_BECOMEPUBLICTIME)));
		helpers_Sub_Bean.setHelper_update_time(cursor.getLong(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_UPDATETIME)));
		helpers_Sub_Bean.setHelper_start_time(cursor.getLong(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_HELPERS_STARTTIME)));
		return helpers_Sub_Bean;
	}

	public static Group_Bean getGroupByCursor(Cursor cursor) {
		Group_Bean group_Bean = new Group_Bean();
		group_Bean.setGroup_id(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_GROUPS_ID)));
		group_Bean.setGroup_status(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_GROUPS_STATUS)));
		group_Bean.setGroup_name(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_GROUPS_NAME)));
		group_Bean.setGroup_member_id(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_GROUPS_MEMBERID)));
		group_Bean.setGroup_describe(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_GROUPS_DESCRIBE)));
		group_Bean.setGroup_create_time(cursor.getLong(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_GROUPS_CREATETIME)));
		group_Bean.setGroup_last_update_time(cursor.getLong(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_GROUPS_LASTUPDATETIME)));
		return group_Bean;
	}
	public static NoteTag_Pojo getCostTagByCursor(Cursor cursor) {
		NoteTag_Pojo costTag = new NoteTag_Pojo();
		costTag.setId(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_COSTTAG_ID)));
		costTag.setTag(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_COSTTAG_NAME)));
		costTag.setTarggetId(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_COSTTAG_TARGETID)));
		return costTag;
	}

	/**
	 * @param object
	 * @return
	 */
	public static ContentValues CreateValues(Object object) {
		ContentValues values = new ContentValues();
		if (object instanceof Helpers_Sub_Bean) {
			values.put(Const_DB.DATABASE_TABLE_HELPERS_ID, ((Helpers_Sub_Bean) object).getHelper_id());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_ACCOUNT, ((Helpers_Sub_Bean) object).getHelper_account());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_ACCOUNTTYPE, ((Helpers_Sub_Bean) object).getHelper_account_type());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_NAME, ((Helpers_Sub_Bean) object).getHelper_name());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_MEMONAME, ((Helpers_Sub_Bean) object).getHelper_memo_name());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_READYGONAME, ((Helpers_Sub_Bean) object).getHelper_readygo_name());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_NAMEPINYIN, ((Helpers_Sub_Bean) object).getHelper_name_pinyin().toUpperCase());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_MEMONAMEPINYIN, ((Helpers_Sub_Bean) object).getHelper_memo_name_pinyin());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_READYGONAMEPINYIN, ((Helpers_Sub_Bean) object).getHelper_readygo_name_pinyin());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_TEL, ((Helpers_Sub_Bean) object).getHelper_tel());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_MOBILE, ((Helpers_Sub_Bean) object).getHelper_mobile());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_GENDER, ((Helpers_Sub_Bean) object).getHelper_gender());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_ZIP, ((Helpers_Sub_Bean) object).getHelper_zip());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_FAX, ((Helpers_Sub_Bean) object).getHelper_fax());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_EMAIL, ((Helpers_Sub_Bean) object).getHelper_email());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_COMPANY, ((Helpers_Sub_Bean) object).getHelper_company());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_TITLE, ((Helpers_Sub_Bean) object).getHelper_title());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_COUNTRY, ((Helpers_Sub_Bean) object).getHelper_country());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_CITY, ((Helpers_Sub_Bean) object).getHelper_city());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_ADDRESS, ((Helpers_Sub_Bean) object).getHelper_address());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_WEBPAGE, ((Helpers_Sub_Bean) object).getHelper_webpage());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_PHOTO, ((Helpers_Sub_Bean) object).getHelper_photo());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_SIGN, ((Helpers_Sub_Bean) object).getHelper_sign());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_STATUS, ((Helpers_Sub_Bean) object).getHelper_status());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_VERIFICATION, ((Helpers_Sub_Bean) object).getHelper_verification());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_HELPERCOUNT, ((Helpers_Sub_Bean) object).getHelper_count());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_TARGETCOUNT, ((Helpers_Sub_Bean) object).getHelper_target_count());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_VIEWCOUNT, ((Helpers_Sub_Bean) object).getHelper_view_count());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_COINCOUNT, ((Helpers_Sub_Bean) object).getHelper_coin_count());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_MEMO, ((Helpers_Sub_Bean) object).getHelper_memo());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_MSG, ((Helpers_Sub_Bean) object).getHelper_msg());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_BECOMEPUBLICTIME, ((Helpers_Sub_Bean) object).getHelper_become_public_time());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_UPDATETIME, ((Helpers_Sub_Bean) object).getHelper_update_time());
			values.put(Const_DB.DATABASE_TABLE_HELPERS_STARTTIME, ((Helpers_Sub_Bean) object).getHelper_start_time());

		} else if (object instanceof Group_Bean) {

			values.put(Const_DB.DATABASE_TABLE_GROUPS_ID, ((Group_Bean) object).getGroup_id());
			values.put(Const_DB.DATABASE_TABLE_GROUPS_NAME, ((Group_Bean) object).getGroup_name());
			values.put(Const_DB.DATABASE_TABLE_GROUPS_MEMBERID, ((Group_Bean) object).getGroup_member_id());
			values.put(Const_DB.DATABASE_TABLE_GROUPS_DESCRIBE, ((Group_Bean) object).getGroup_describe());
			values.put(Const_DB.DATABASE_TABLE_GROUPS_STATUS, ((Group_Bean) object).getGroup_status());
			values.put(Const_DB.DATABASE_TABLE_GROUPS_CREATETIME, ((Group_Bean) object).getGroup_create_time());
			values.put(Const_DB.DATABASE_TABLE_GROUPS_LASTUPDATETIME, ((Group_Bean) object).getGroup_last_update_time());

		} else if (object instanceof Target_Bean) {

		} else if (object instanceof NoteTag_Pojo) {
			values.put(Const_DB.DATABASE_TABLE_COSTTAG_ID, ((NoteTag_Pojo) object).getId());
			values.put(Const_DB.DATABASE_TABLE_COSTTAG_NAME, ((NoteTag_Pojo) object).getTag());
			values.put(Const_DB.DATABASE_TABLE_COSTTAG_TARGETID,  ((NoteTag_Pojo) object).getTarggetId());
		}
		return values;
	}

	public static Helpers_Sub_Bean getshowDataFromGroupByCursor(Cursor cursor) {
		Helpers_Sub_Bean helper = new Helpers_Sub_Bean();
		helper.setHelper_id(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_GROUPS_ID)));
		helper.setHelper_sign(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_GROUPS_DESCRIBE)));
		helper.setHelper_memo_name(cursor.getString(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_GROUPS_NAME)));
		helper.setHelper_status(cursor.getInt(cursor.getColumnIndex(Const_DB.DATABASE_TABLE_GROUPS_STATUS)));
		helper.setHelper_account_type(-11111);
		return helper;
	}

	public static ContentValues CreateValuesForUpdateHelperList(Helpers_Sub_Bean helpers_Sub_Bean) {
		ContentValues values = new ContentValues();
		values.put(Const_DB.DATABASE_TABLE_HELPERS_ID, helpers_Sub_Bean.getHelper_id());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_ACCOUNT, helpers_Sub_Bean.getHelper_account());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_ACCOUNTTYPE, helpers_Sub_Bean.getHelper_account_type());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_NAME, helpers_Sub_Bean.getHelper_name());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_MEMONAME, helpers_Sub_Bean.getHelper_memo_name());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_READYGONAME, helpers_Sub_Bean.getHelper_readygo_name());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_NAMEPINYIN, helpers_Sub_Bean.getHelper_name_pinyin().toUpperCase());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_MEMONAMEPINYIN, helpers_Sub_Bean.getHelper_memo_name_pinyin());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_READYGONAMEPINYIN, helpers_Sub_Bean.getHelper_readygo_name_pinyin());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_WEBPAGE, helpers_Sub_Bean.getHelper_webpage());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_PHOTO, helpers_Sub_Bean.getHelper_photo());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_SIGN, helpers_Sub_Bean.getHelper_sign());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_STATUS, helpers_Sub_Bean.getHelper_status());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_MEMO, helpers_Sub_Bean.getHelper_memo());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_MSG, helpers_Sub_Bean.getHelper_msg());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_UPDATETIME, helpers_Sub_Bean.getHelper_update_time());
		return values;
	}

	public static ContentValues CreateValuesForUpdateHelper(Helpers_Sub_Bean helpers_Sub_Bean) {
		ContentValues values = new ContentValues();
		values.put(Const_DB.DATABASE_TABLE_HELPERS_ID, helpers_Sub_Bean.getHelper_id());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_READYGONAME, helpers_Sub_Bean.getHelper_readygo_name());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_READYGONAMEPINYIN, helpers_Sub_Bean.getHelper_readygo_name_pinyin());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_WEBPAGE, helpers_Sub_Bean.getHelper_webpage());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_PHOTO, helpers_Sub_Bean.getHelper_photo());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_SIGN, helpers_Sub_Bean.getHelper_sign());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_MEMO, helpers_Sub_Bean.getHelper_memo());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_COMPANY, helpers_Sub_Bean.getHelper_company());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_COUNTRY, helpers_Sub_Bean.getHelper_country());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_CITY, helpers_Sub_Bean.getHelper_city());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_TEL, helpers_Sub_Bean.getHelper_tel());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_FAX, helpers_Sub_Bean.getHelper_fax());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_ZIP, helpers_Sub_Bean.getHelper_zip());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_TITLE, helpers_Sub_Bean.getHelper_title());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_EMAIL, helpers_Sub_Bean.getHelper_email());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_ADDRESS, helpers_Sub_Bean.getHelper_address());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_MOBILE, helpers_Sub_Bean.getHelper_mobile());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_VERIFICATION, helpers_Sub_Bean.getHelper_verification());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_GENDER, helpers_Sub_Bean.getHelper_gender());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_HELPERCOUNT, helpers_Sub_Bean.getHelper_count());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_TARGETCOUNT, helpers_Sub_Bean.getHelper_target_count());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_COINCOUNT, helpers_Sub_Bean.getHelper_coin_count());
		values.put(Const_DB.DATABASE_TABLE_HELPERS_UPDATETIME, helpers_Sub_Bean.getHelper_update_time());
		return values;
	}

	public static ContentValues CreateValuesForUpdataGroup(Group_Bean group_Bean) {
		ContentValues values = new ContentValues();
		values.put(Const_DB.DATABASE_TABLE_GROUPS_ID, group_Bean.getGroup_id());
		values.put(Const_DB.DATABASE_TABLE_GROUPS_NAME, group_Bean.getGroup_name());
		values.put(Const_DB.DATABASE_TABLE_GROUPS_DESCRIBE, group_Bean.getGroup_describe());
		values.put(Const_DB.DATABASE_TABLE_GROUPS_STATUS, group_Bean.getGroup_status());
		values.put(Const_DB.DATABASE_TABLE_GROUPS_CREATETIME, group_Bean.getGroup_create_time());
		values.put(Const_DB.DATABASE_TABLE_GROUPS_LASTUPDATETIME, group_Bean.getGroup_last_update_time());

		return values;
	}

}
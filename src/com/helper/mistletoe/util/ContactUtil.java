package com.helper.mistletoe.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;

import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.Data;
import android.text.TextUtils;

public class ContactUtil {
	private static String mPath = Environment.getExternalStorageDirectory() + "/lidynast/pic/";
	private static String mFileName;

	// 获取手机通讯录的数据
	public static ArrayList<Helpers_Sub_Bean> getContacts(Context context) {
		ArrayList<Helpers_Sub_Bean> listHelper = new ArrayList<Helpers_Sub_Bean>();
		// 获得通讯录信息 ，URI是ContactsContract.Contacts.CONTENT_URI
		Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		while (cursor.moveToNext()) {
			// 获得通讯录中联系人的名字
			String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
			// 如果姓名为空，跳过这个循环，进行下个循环
			if (TextUtils.isEmpty(name))
				continue;
			// 获得通讯录中每个联系人的ID
			String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			// 查看给联系人是否有电话，返回结果是String类型，1表示有，0表是没有
			String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
			if (hasPhone.equalsIgnoreCase("1"))
				hasPhone = "true";
			else
				hasPhone = "false";
			// 如果有电话，根据联系人的ID查找到联系人的电话，电话可以是多个
			if (Boolean.parseBoolean(hasPhone)) {
				Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
						null, null);
				while (phones.moveToNext()) {
					String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					// 如果姓名与号码一致，跳过这个循环，进行下个循环
					if (name.equals(phoneNumber))
						continue;
					Helpers_Sub_Bean helper = new Helpers_Sub_Bean();
					helper.setHelper_account_type(0);
					helper.setHelper_readygo_name(name);
					helper.setHelper_account(purifyPhoneNumber(phoneNumber));
					listHelper.add(helper);
				}
				phones.close();
			}
			// 查找email地址，这里email也可以有多个
			Cursor emails = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null,
					null);
			while (emails.moveToNext()) {
				String emailAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				// 如果姓名与email一致，跳过这个循环，进行下个循环
				if (name.equals(emailAddress))
					continue;
				Helpers_Sub_Bean helper = new Helpers_Sub_Bean();
				helper.setHelper_account_type(1);
				helper.setHelper_readygo_name(name);
				helper.setHelper_account(emailAddress);
				listHelper.add(helper);
			}
			emails.close();
			// //获得联系人的地址
			// Cursor address = context.getContentResolver()
			// .query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
			// null,
			// ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID
			// + " = " + contactId, null, null);
			// while (address.moveToNext()) {
			// // These are all private class variables, don’t forget to create
			// // them.
			// String poBox = address
			// .getString(address
			// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
			// String street = address
			// .getString(address
			// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
			// String city = address
			// .getString(address
			// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
			// String state = address
			// .getString(address
			// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
			// String postalCode = address
			// .getString(address
			// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
			// String country = address
			// .getString(address
			// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
			// String type = address
			// .getString(address
			// .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
			// }
		}
		cursor.close();
		HashSet<Helpers_Sub_Bean> hash = new HashSet<Helpers_Sub_Bean>(listHelper);
		listHelper.clear();
		listHelper.addAll(hash);
		return listHelper;
	}

	// 向手机通讯录插入数据
	public static void insert(Context context, Helpers_Sub_Bean helper) {
		// 首先插入空值，再得到rawContactsId ，用于下面插值
		ContentValues values = new ContentValues();
		// insert a null value
		Uri rawContactUri = context.getContentResolver().insert(RawContacts.CONTENT_URI, values);
		long rawContactsId = ContentUris.parseId(rawContactUri);
		// 往刚才的空记录中插入姓名
		values.clear();
		values.put(StructuredName.RAW_CONTACT_ID, rawContactsId);
		values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
		values.put(StructuredName.DISPLAY_NAME, helper.getHelper_name());
		context.getContentResolver().insert(Data.CONTENT_URI, values);
		// // 插入firstName
		// values.clear();
		// values.put(StructuredName.RAW_CONTACT_ID, rawContactsId);
		// values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
		// values.put(StructuredName.FAMILY_NAME, user.getFirstName());
		// context.getContentResolver().insert(Data.CONTENT_URI, values);
		// // 插入lastName
		// values.clear();
		// values.put(StructuredName.RAW_CONTACT_ID, rawContactsId);
		// values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
		// values.put(StructuredName.GIVEN_NAME, user.getLastName());
		// context.getContentResolver().insert(Data.CONTENT_URI, values);
		// 插入公司
		values.clear();
		values.put(StructuredName.RAW_CONTACT_ID, rawContactsId);
		values.put(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
		values.put(Organization.COMPANY, helper.getHelper_company());
		values.put(Organization.TITLE, helper.getHelper_title());// 插入职务
		context.getContentResolver().insert(Data.CONTENT_URI, values);
		// 插入电话
		values.clear();
		values.put(Phone.RAW_CONTACT_ID, rawContactsId);
		values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		values.put(Phone.NUMBER, helper.getHelper_account());
		context.getContentResolver().insert(Data.CONTENT_URI, values);
		// 插入头像
		// mFileName = helper.getHelper_account() + ".png";
		// Bitmap bitmap = BitmapFactory.decodeFile(helper.get);
		// ByteArrayOutputStream by = new ByteArrayOutputStream();
		// bitmap.compress(Bitmap.CompressFormat.PNG, 100, by);
		// byte[] stream = by.toByteArray();
		// Untilly.writeToSdcard(stream, mPath, mFileName);
		// values.clear();
		// values.put(Phone.RAW_CONTACT_ID, rawContactsId);
		// values.put(Data.MIMETYPE, Photo.CONTENT_ITEM_TYPE);
		// values.put(Photo.PHOTO, stream);
		// context.getContentResolver().insert(Data.CONTENT_URI, values);
		// 往data表入Email数据
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactsId);
		values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
		values.put(Email.DATA, helper.getHelper_email());
		values.put(Email.TYPE, Email.TYPE_WORK);
		context.getContentResolver().insert(Data.CONTENT_URI, values);
		// 往data表入address数据
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactsId);
		values.put(Data.MIMETYPE, StructuredPostal.CONTENT_ITEM_TYPE);
		values.put(StructuredPostal.FORMATTED_ADDRESS, helper.getHelper_address());
		values.put(StructuredPostal.TYPE, StructuredPostal.TYPE_WORK);
		Uri c = context.getContentResolver().insert(Data.CONTENT_URI, values);
		if (null != c) {
			Tool_Utils.showInfo(context, "联系人添加成功!");
		} else {
			Tool_Utils.showInfo(context, "联系人添加失败!");
		}
	}

	// 向手机通讯录更新数据
	public static void update(Context context, Helpers_Sub_Bean helper) {
		/**
		 * ContentResolver resolver = MipcaActivityCapture.this
		 * .getContentResolver(); Cursor cursor =
		 * resolver.query(Phone.CONTENT_URI, null,
		 * ContactsContract.CommonDataKinds.Phone.NUMBER + " = " +
		 * user_past.getPhoneNumber(), null, null)
		 */
		Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.NUMBER + " like " + helper.getHelper_account(), null, null);
		// Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI,
		// null,
		// ContactsContract.CommonDataKinds.Phone.NUMBER + " like "
		// + user.getPhoneNumber(), null, null);
		// Cursor cursor = context.getContentResolver().query(Data.CONTENT_URI,
		// new String[] { Data.RAW_CONTACT_ID },
		// ContactsContract.Contacts.Data.DATA1 + "=?",
		// new String[] { user.getPhoneNumber() }, null);
		cursor.moveToFirst();
		String id = cursor.getString(cursor.getColumnIndex(Data.RAW_CONTACT_ID));
		cursor.close();
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		// 更新姓名
		ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(Data.RAW_CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + " = ?", new String[] { String.valueOf(id), StructuredName.CONTENT_ITEM_TYPE })
				.withValue(StructuredName.DISPLAY_NAME, helper.getHelper_name()).build());
		// // 更新头像
		// mFileName =helper.getHelper_account() + ".png";
		// Bitmap bitmap = BitmapFactory.decodeFile(helper.getHelper_account());
		// ByteArrayOutputStream by = new ByteArrayOutputStream();
		// bitmap.compress(Bitmap.CompressFormat.PNG, 100, by);
		// byte[] stream = by.toByteArray();
		// Untilly.writeToSdcard(stream, mPath, mFileName);
		// ops.add(ContentProviderOperation
		// .newUpdate(ContactsContract.Data.CONTENT_URI)
		// .withSelection(
		// Data.RAW_CONTACT_ID + "=?" + " AND "
		// + ContactsContract.Data.MIMETYPE + " = ?",
		// new String[] { String.valueOf(id),
		// Photo.CONTENT_ITEM_TYPE })
		// .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, stream)
		// .build());
		// 更新email
		ops.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(Data.RAW_CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + " = ?" + " AND " + Email.TYPE + "=?",
						new String[] { String.valueOf(id), Email.CONTENT_ITEM_TYPE, String.valueOf(Email.TYPE_WORK) }).withValue(Email.DATA, helper.getHelper_email()).build());

		// 更新公司
		ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(Data.RAW_CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + " = ?", new String[] { String.valueOf(id), Organization.CONTENT_ITEM_TYPE })
				.withValue(Organization.COMPANY, helper.getHelper_company()).build());
		// 更新职务
		ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(Data.RAW_CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + " = ?", new String[] { String.valueOf(id), Organization.CONTENT_ITEM_TYPE })
				.withValue(Organization.TITLE, helper.getHelper_title()).build());
		// 更新地址
		ops.add(ContentProviderOperation
				.newUpdate(ContactsContract.Data.CONTENT_URI)
				.withSelection(Data.RAW_CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + " = ?" + " AND " + StructuredPostal.TYPE + "=?",
						new String[] { String.valueOf(id), StructuredPostal.CONTENT_ITEM_TYPE, String.valueOf(StructuredPostal.TYPE_WORK) })
				.withValue(StructuredPostal.FORMATTED_ADDRESS, helper.getHelper_address()).build());
		try {
			ContentProviderResult[] c = context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
			if (null != c) {
				Tool_Utils.showInfo(context, "联系人更新成功!");
			} else {
				Tool_Utils.showInfo(context, "联系人更新失败!");
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /
		// public void testUpdate()throws Exception{
		// int id = 1;
		// // String phone = "999999";
		// Uri uri = Uri.parse("content://com.android.contacts/data");//
		// 对data表的所有数据操作
		// ContentResolver resolver = context.getContentResolver();
		// ContentValues values = new ContentValues();
		// values.put("data1", user.getPhoneNumber());
		// resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new
		// String[]{"vnd.android.cursor.item/phone_v2",id+""});
		// }
		// Log.i("传过来的值", "sdf" + user);
		// ContentValues values = new ContentValues();
		// // 更新Data.MIMETYPE为StructuredName.CONTENT_ITEM_TYPE
		// values.clear();
		// values.put(StructuredName.DISPLAY_NAME, user.getUserName());
		// // values.put(StructuredName.FAMILY_NAME, user.getFirstName());
		// // values.put(StructuredName.GIVEN_NAME, user.getLastName());
		// context.getContentResolver().update(
		// ContactsContract.Data.CONTENT_URI,
		// values,
		// "( " + Data.MIMETYPE + " is '"
		// + StructuredName.CONTENT_ITEM_TYPE + "' ) and ( "
		// + Data.RAW_CONTACT_ID + " is '" + user.getDb_id()
		// + "' )", new String[] { StructuredName.DISPLAY_NAME });
		// 更新Data.MIMETYPE为StructuredName.CONTENT_ITEM_TYPE
		// values.clear();
		// values.put(StructuredName.DISPLAY_NAME, user.getUserName());
		// values.put(StructuredName.FAMILY_NAME, user.getFirstName());
		// values.put(StructuredName.GIVEN_NAME, user.getLastName());
		// context.getContentResolver().update(
		// Data.CONTENT_URI,
		// values,
		// "(" + Data.MIMETYPE + "= '" + StructuredName.CONTENT_ITEM_TYPE
		// + "')and("
		// + ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = '"
		// + user.getDb_id() + "')", UPDATE_INFO_NAME);
		// 更新Data.MIMETYPE为Organization.CONTENT_ITEM_TYPE
		// values.clear();
		// values.put(Organization.COMPANY, user.getCompany());
		// values.put(Organization.TITLE, user.getTitle());
		// context.getContentResolver().update(
		// Data.CONTENT_URI,
		// values,
		// "(" + Data.MIMETYPE + "= '" + Organization.CONTENT_ITEM_TYPE
		// + "')and("
		// + ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = '"
		// + user.getDb_id()+ "')", UPDATE_INFO_COMPANY);
		// // 更新Data.MIMETYPE为Email.CONTENT_ITEM_TYPE
		// values.clear();
		// values.put(Email.DATA, user.getEmail());
		// context.getContentResolver().update(
		// Data.CONTENT_URI,
		// values,
		// "(" +Data.MIMETYPE + "=" + Email.CONTENT_ITEM_TYPE + ")and("
		// + ContactsContract.CommonDataKinds.Phone.NUMBER + " = "
		// + user.getPhoneNumber()+ ")", UPDATE_INFO_EMAIL);
		// // 更新Data.MIMETYPE为StructuredPostal.CONTENT_ITEM_TYPE
		// values.clear();
		// values.put(StructuredPostal.STREET, user.getAddress());
		// context.getContentResolver().update(
		// Data.CONTENT_URI,
		// values,
		// "(" +Data.MIMETYPE + "=" + StructuredPostal.CONTENT_ITEM_TYPE
		// + ")and("
		// + ContactsContract.CommonDataKinds.Phone.NUMBER + " = "
		// + user.getPhoneNumber()+ ")", UPDATE_INFO_ADDRESS);
	}

	public static String purifyPhoneNumber(String phoneNumber) {
		String result = "";
		if (phoneNumber == null) {
			return result;
		}
		try {
			String purifyPhoneNumber = getOnlyNumber(phoneNumber);
			if ((purifyPhoneNumber == null) || (purifyPhoneNumber.equals(""))) {
				return result;
			}
			if ((purifyPhoneNumber.indexOf("86") == 0) && purifyPhoneNumber.length() > 10) {
				phoneNumber = purifyPhoneNumber.substring(2);
			} else if ((phoneNumber.indexOf("+") == 0) && (phoneNumber.contains(" "))) {
				result = phoneNumber.substring(phoneNumber.indexOf(" ") + 1, phoneNumber.length());
			}
			result = Tool_Utils.getOnlyNumber(phoneNumber);
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}

	public static String getOnlyNumber(String s) {
		String result = "";
		if ((s == null) || (s.equals(""))) {
			return result;
		}
		try {
			result = Pattern.compile("[^0-9]").matcher(s).replaceAll("").trim();
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}
}

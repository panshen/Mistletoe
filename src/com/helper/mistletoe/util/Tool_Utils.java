package com.helper.mistletoe.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.zxing.maxicode.MaxiCodeReader;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.provider.ContactsContract;
import android.renderscript.Int2;
import android.util.Log;
import android.widget.Toast;

public class Tool_Utils {
	
	static Context mContext = null;
	
	public static Toast showToast(Context context, Toast toast, String messege) {
		Toast result = toast;
		try {
			if (toast == null) {
				toast = Toast.makeText(context, messege, Toast.LENGTH_SHORT);
			} else {
				toast.setText(messege);
			}
			toast.show();
			result = toast;
		} catch (Exception e) {
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

	public static String getAreaCode(String phoneNumber) {
		String result = "";
		if ((phoneNumber == null) || (phoneNumber.equals(""))) {
			return result;
		}
		try {
			String purifyPhoneNumber = getOnlyNumber(phoneNumber);
			if ((purifyPhoneNumber == null) || (purifyPhoneNumber.equals(""))) {
				return result;
			}
			if ((purifyPhoneNumber.indexOf("86") == 0) && purifyPhoneNumber.length() > 10) {
				result = "86";
			} else if ((phoneNumber.indexOf("+") == 0) && (phoneNumber.contains(" "))) {
				result = phoneNumber.substring(0, phoneNumber.indexOf(" "));
				result = Tool_Utils.getOnlyNumber(result);
			}
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
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

	public static void showInfo(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	// public static Bitmap getBitMapFromPicID(Context con, ContactUser_Bean be)
	// {
	// Bitmap result = null;
	// try {
	// if (be.getPhotoid() > 0) {
	// Uri uri = ContentUris.withAppendedId(
	// ContactsContract.Contacts.CONTENT_URI,
	// be.getContactid());
	// InputStream input = ContactsContract.Contacts
	// .openContactPhotoInputStream(con.getContentResolver(),
	// uri);
	// result = BitmapFactory.decodeStream(input);
	// } else {
	// result = null;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return result;
	// }

	public static String getBitMapFromPicID(Context con, String pnumber, Long picid, Long conId) {
		String result = "";
		try {
			pnumber = Tool_Utils.purifyPhoneNumber(pnumber);
			if (picid > 0) {
				Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, conId);
				InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(con.getContentResolver(), uri);
				Bitmap bitma = BitmapFactory.decodeStream(input);
				result = saveBitmap(con, pnumber, bitma);
			}
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}

	public static String saveBitmap(Context con, String pNumber, Bitmap bm) {
		String result = "";
		try {
			boolean isSave = false;
			File f = new File(con.getFilesDir(), pNumber + ".png");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			isSave = bm.compress(Bitmap.CompressFormat.PNG, 90, out);
			if (out != null) {
				out.flush();
				out.close();
			}
			if (isSave) {
				result = f.getPath();
			}
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 判断当前应用程序处于前台还是后台
	 */
	public static boolean isApplicationBroughtToBackground(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			for (RunningTaskInfo task : tasks) {
				if (task.getClass().getName().equals("com.helper.mistletoe.c.ui.MainFragmentActivity")) {
					return true;
				}
			}
		}
		return false;
	}

	// public static boolean isTopActivity(Activity activity){
	// String packageName = activity.getPackageName();
	// ActivityManager activityManager = (ActivityManager)
	// activity.getSystemService(Context.ACTIVITY_SERVICE);
	// List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
	// if(tasksInfo.size() > 0){
	// System.out.println("---------------包名-----------"+tasksInfo.get(0).topActivity.getPackageName());
	// //应用程序位于堆栈的顶层
	// if(packageName.equals(tasksInfo.get(0).topActivity.getPackageName())){
	// return true;
	// }
	// }
	// return false;
	// }
	public static Long insertCalendar(Context con, String title, String endtime, String de) {
		long result = 0L;
		try {
			// Uri
			Uri calanderURL = Uri.parse("content://com.android.calendar/calendars");
			Uri calanderEventURL = Uri.parse("content://com.android.calendar/events");
			Uri calanderRemiderURL = Uri.parse("content://com.android.calendar/reminders");

			String calId = "";
			Cursor userCursor = con.getContentResolver().query(calanderURL, null, null, null, null);
			if (userCursor.getCount() > 0) {
				userCursor.moveToFirst();
				calId = userCursor.getString(userCursor.getColumnIndex("_id"));
			} else {
				calId = "1";
			}

			ContentValues event = new ContentValues();
			event.put("title", title);
			event.put("description", de);
			event.put("calendar_id", calId);

			Calendar edtime = Calendar.getInstance();
			edtime.setTime(new Date(Long.parseLong(endtime) * 1000));
			edtime.set(edtime.get(Calendar.YEAR), edtime.get(Calendar.MONTH), edtime.get(Calendar.DAY_OF_MONTH), 8, 0, 0);
			long start = edtime.getTime().getTime();
			edtime.set(edtime.get(Calendar.YEAR), edtime.get(Calendar.MONTH), edtime.get(Calendar.DAY_OF_MONTH), 18, 0, 0);
			long end = edtime.getTime().getTime();
			event.put("dtstart", start);
			event.put("dtend", end);
			event.put("hasAlarm", 1);
			event.put("eventTimezone", TimeZone.getDefault().getID().toString());
			// event.put(Events.ALL_DAY, 1);
			Uri newEvent = con.getContentResolver().insert(calanderEventURL, event);
			long id = Long.parseLong(newEvent.getLastPathSegment());

			ContentValues values = new ContentValues();
			// values.put(Reminders.MINUTES, Reminders.METHOD_DEFAULT);
			values.put(Reminders.MINUTES, 20 * 60);
			values.put(Reminders.EVENT_ID, id);
			values.put(Reminders.METHOD, Reminders.METHOD_ALERT);
			ContentResolver cr1 = con.getContentResolver(); //
			cr1.insert(calanderRemiderURL, values);
			return id;
		} catch (Exception e) {
			result = 0L;
			e.printStackTrace();
		}
		return result;
	}

	@SuppressLint("NewApi")
	public static int deleteCalendar(Context context, String id) {
		int result = 0;
		try {
			ContentResolver cr = context.getContentResolver();
			ContentValues values = new ContentValues();
			Uri deleteUri = null;
			deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, Long.parseLong(id));
			result = context.getContentResolver().delete(deleteUri, null, null);
		} catch (NumberFormatException e) {
			result = 0;
			e.printStackTrace();
		}
		return result;
	}

	// 向手机通讯录插入数据
	// public static void insert(Context context, User_Bean user) {
	// // 首先插入空值，再得到rawContactsId ，用于下面插值
	// ContentValues values = new ContentValues();
	// // insert a null value
	// Uri rawContactUri = context.getContentResolver().insert(
	// RawContacts.CONTENT_URI, values);
	// long rawContactsId = ContentUris.parseId(rawContactUri);
	// // 往刚才的空记录中插入姓名
	// values.clear();
	// values.put(StructuredName.RAW_CONTACT_ID, rawContactsId);
	// values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
	// values.put(StructuredName.DISPLAY_NAME, user.getUserName());
	// context.getContentResolver().insert(Data.CONTENT_URI, values);
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
	// // 插入公司
	// values.clear();
	// values.put(StructuredName.RAW_CONTACT_ID, rawContactsId);
	// values.put(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
	// values.put(Organization.COMPANY, user.getCompany());
	// values.put(Organization.TITLE, user.getTitle());// 插入职务
	// context.getContentResolver().insert(Data.CONTENT_URI, values);
	// // 插入电话
	// values.clear();
	// values.put(Phone.RAW_CONTACT_ID, rawContactsId);
	// values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
	// values.put(Phone.NUMBER, user.getPhoneNumber());
	// context.getContentResolver().insert(Data.CONTENT_URI, values);
	// // 插入头像
	// mFileName = user.getPhoneNumber() + ".png";
	// Bitmap bitmap = BitmapFactory.decodeFile(user.getPicId());
	// ByteArrayOutputStream by = new ByteArrayOutputStream();
	// bitmap.compress(Bitmap.CompressFormat.PNG, 100, by);
	// byte[] stream = by.toByteArray();
	// Untilly.writeToSdcard(stream, mPath, mFileName);
	// values.clear();
	// values.put(Phone.RAW_CONTACT_ID, rawContactsId);
	// values.put(Data.MIMETYPE, Photo.CONTENT_ITEM_TYPE);
	// values.put(Photo.PHOTO, stream);
	// context.getContentResolver().insert(Data.CONTENT_URI, values);
	// // 往data表入Email数据
	// values.clear();
	// values.put(Data.RAW_CONTACT_ID, rawContactsId);
	// values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
	// values.put(Email.DATA, user.getEmail());
	// values.put(Email.TYPE, Email.TYPE_WORK);
	// context.getContentResolver().insert(Data.CONTENT_URI, values);
	// // 往data表入address数据
	// values.clear();
	// values.put(Data.RAW_CONTACT_ID, rawContactsId);
	// values.put(Data.MIMETYPE, StructuredPostal.CONTENT_ITEM_TYPE);
	// values.put(StructuredPostal.FORMATTED_ADDRESS, user.getAddress());
	// values.put(StructuredPostal.TYPE, StructuredPostal.TYPE_WORK);
	// Uri c = context.getContentResolver().insert(Data.CONTENT_URI, values);
	// if (null != c) {
	// Tool_Utils.showInfo(context, "联系人添加成功!");
	// } else {
	// Tool_Utils.showInfo(context, "联系人添加失败!");
	// }
	// }

	// 向手机通讯录更新数据
	// public static void update(Context context, User_Bean user, String number)
	// {
	// /**
	// * ContentResolver resolver = MipcaActivityCapture.this
	// * .getContentResolver(); Cursor cursor =
	// * resolver.query(Phone.CONTENT_URI, null,
	// * ContactsContract.CommonDataKinds.Phone.NUMBER + " = " +
	// * user_past.getPhoneNumber(), null, null)
	// */
	// Cursor cursor = context.getContentResolver().query(
	// Phone.CONTENT_URI,
	// null,
	// ContactsContract.CommonDataKinds.Phone.NUMBER + " like "
	// + number, null, null);
	// // Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI,
	// // null,
	// // ContactsContract.CommonDataKinds.Phone.NUMBER + " like "
	// // + user.getPhoneNumber(), null, null);
	// // Cursor cursor = context.getContentResolver().query(Data.CONTENT_URI,
	// // new String[] { Data.RAW_CONTACT_ID },
	// // ContactsContract.Contacts.Data.DATA1 + "=?",
	// // new String[] { user.getPhoneNumber() }, null);
	// cursor.moveToFirst();
	// String id = cursor
	// .getString(cursor.getColumnIndex(Data.RAW_CONTACT_ID));
	// cursor.close();
	// ArrayList<ContentProviderOperation> ops = new
	// ArrayList<ContentProviderOperation>();
	// // 更新姓名
	// ops.add(ContentProviderOperation
	// .newUpdate(ContactsContract.Data.CONTENT_URI)
	// .withSelection(
	// Data.RAW_CONTACT_ID + "=?" + " AND "
	// + ContactsContract.Data.MIMETYPE + " = ?",
	// new String[] { String.valueOf(id),
	// StructuredName.CONTENT_ITEM_TYPE })
	// .withValue(StructuredName.DISPLAY_NAME, user.getUserName())
	// .build());
	// // 更新头像
	// mFileName = user.getPhoneNumber() + ".png";
	// Bitmap bitmap = BitmapFactory.decodeFile(user.getPicId());
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
	// // 更新email
	// ops.add(ContentProviderOperation
	// .newUpdate(ContactsContract.Data.CONTENT_URI)
	// .withSelection(
	// Data.RAW_CONTACT_ID + "=?" + " AND "
	// + ContactsContract.Data.MIMETYPE + " = ?"
	// + " AND " + Email.TYPE + "=?",
	// new String[] { String.valueOf(id),
	// Email.CONTENT_ITEM_TYPE,
	// String.valueOf(Email.TYPE_WORK) })
	// .withValue(Email.DATA, user.getEmail()).build());
	//
	// // 更新公司
	// ops.add(ContentProviderOperation
	// .newUpdate(ContactsContract.Data.CONTENT_URI)
	// .withSelection(
	// Data.RAW_CONTACT_ID + "=?" + " AND "
	// + ContactsContract.Data.MIMETYPE + " = ?",
	// new String[] { String.valueOf(id),
	// Organization.CONTENT_ITEM_TYPE })
	// .withValue(Organization.COMPANY, user.getCompany()).build());
	// // 更新职务
	// ops.add(ContentProviderOperation
	// .newUpdate(ContactsContract.Data.CONTENT_URI)
	// .withSelection(
	// Data.RAW_CONTACT_ID + "=?" + " AND "
	// + ContactsContract.Data.MIMETYPE + " = ?",
	// new String[] { String.valueOf(id),
	// Organization.CONTENT_ITEM_TYPE })
	// .withValue(Organization.TITLE, user.getTitle()).build());
	// // 更新地址
	// ops.add(ContentProviderOperation
	// .newUpdate(ContactsContract.Data.CONTENT_URI)
	// .withSelection(
	// Data.RAW_CONTACT_ID + "=?" + " AND "
	// + ContactsContract.Data.MIMETYPE + " = ?"
	// + " AND " + StructuredPostal.TYPE + "=?",
	// new String[] { String.valueOf(id),
	// StructuredPostal.CONTENT_ITEM_TYPE,
	// String.valueOf(StructuredPostal.TYPE_WORK) })
	// .withValue(StructuredPostal.FORMATTED_ADDRESS,
	// user.getAddress()).build());
	// try {
	// ContentProviderResult[] c = context.getContentResolver()
	// .applyBatch(ContactsContract.AUTHORITY, ops);
	// if (null != c) {
	// Tool_Utils.showInfo(context, "联系人更新成功!");
	// } else {
	// Tool_Utils.showInfo(context, "联系人更新失败!");
	// }
	// } catch (RemoteException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (OperationApplicationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// // /
	// // public void testUpdate()throws Exception{
	// // int id = 1;
	// // // String phone = "999999";
	// // Uri uri = Uri.parse("content://com.android.contacts/data");//
	// // 对data表的所有数据操作
	// // ContentResolver resolver = context.getContentResolver();
	// // ContentValues values = new ContentValues();
	// // values.put("data1", user.getPhoneNumber());
	// // resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new
	// // String[]{"vnd.android.cursor.item/phone_v2",id+""});
	// // }
	// // Log.i("传过来的值", "sdf" + user);
	// // ContentValues values = new ContentValues();
	// // // 更新Data.MIMETYPE为StructuredName.CONTENT_ITEM_TYPE
	// // values.clear();
	// // values.put(StructuredName.DISPLAY_NAME, user.getUserName());
	// // // values.put(StructuredName.FAMILY_NAME, user.getFirstName());
	// // // values.put(StructuredName.GIVEN_NAME, user.getLastName());
	// // context.getContentResolver().update(
	// // ContactsContract.Data.CONTENT_URI,
	// // values,
	// // "( " + Data.MIMETYPE + " is '"
	// // + StructuredName.CONTENT_ITEM_TYPE + "' ) and ( "
	// // + Data.RAW_CONTACT_ID + " is '" + user.getDb_id()
	// // + "' )", new String[] { StructuredName.DISPLAY_NAME });
	// // 更新Data.MIMETYPE为StructuredName.CONTENT_ITEM_TYPE
	// // values.clear();
	// // values.put(StructuredName.DISPLAY_NAME, user.getUserName());
	// // values.put(StructuredName.FAMILY_NAME, user.getFirstName());
	// // values.put(StructuredName.GIVEN_NAME, user.getLastName());
	// // context.getContentResolver().update(
	// // Data.CONTENT_URI,
	// // values,
	// // "(" + Data.MIMETYPE + "= '" + StructuredName.CONTENT_ITEM_TYPE
	// // + "')and("
	// // + ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = '"
	// // + user.getDb_id() + "')", UPDATE_INFO_NAME);
	// // 更新Data.MIMETYPE为Organization.CONTENT_ITEM_TYPE
	// // values.clear();
	// // values.put(Organization.COMPANY, user.getCompany());
	// // values.put(Organization.TITLE, user.getTitle());
	// // context.getContentResolver().update(
	// // Data.CONTENT_URI,
	// // values,
	// // "(" + Data.MIMETYPE + "= '" + Organization.CONTENT_ITEM_TYPE
	// // + "')and("
	// // + ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = '"
	// // + user.getDb_id()+ "')", UPDATE_INFO_COMPANY);
	// // // 更新Data.MIMETYPE为Email.CONTENT_ITEM_TYPE
	// // values.clear();
	// // values.put(Email.DATA, user.getEmail());
	// // context.getContentResolver().update(
	// // Data.CONTENT_URI,
	// // values,
	// // "(" +Data.MIMETYPE + "=" + Email.CONTENT_ITEM_TYPE + ")and("
	// // + ContactsContract.CommonDataKinds.Phone.NUMBER + " = "
	// // + user.getPhoneNumber()+ ")", UPDATE_INFO_EMAIL);
	// // // 更新Data.MIMETYPE为StructuredPostal.CONTENT_ITEM_TYPE
	// // values.clear();
	// // values.put(StructuredPostal.STREET, user.getAddress());
	// // context.getContentResolver().update(
	// // Data.CONTENT_URI,
	// // values,
	// // "(" +Data.MIMETYPE + "=" + StructuredPostal.CONTENT_ITEM_TYPE
	// // + ")and("
	// // + ContactsContract.CommonDataKinds.Phone.NUMBER + " = "
	// // + user.getPhoneNumber()+ ")", UPDATE_INFO_ADDRESS);
	// }

	// public static List<SortEntity> filledData(
	// ArrayList<ContactUser_Bean> listUser) {
	// // TODO Auto-generated method stub
	// List<SortEntity> listContactName = new ArrayList<SortEntity>();
	// characterParser = new CharacterParser();
	// for (int i = 0; i < listUser.size(); i++) {
	// ContactUser_Bean user = listUser.get(i);
	// SortEntity sortEntity = new SortEntity();
	// String pinyin = characterParser.getSelling(user.getContactName());
	// String sortString = pinyin.substring(0, 1).toUpperCase();
	// if (sortString.matches("[A-Z]")) {
	// sortEntity.setSortLetters(sortString.toUpperCase());
	// } else {
	// sortEntity.setSortLetters("#");
	// }
	// sortEntity.setPhoneNumber(user.getPhoneNumber());
	// sortEntity.setContactPhoto(user.getContactPhoto());
	// sortEntity.setName(user.getContactName());
	// listContactName.add(sortEntity);
	// }
	// return listContactName;
	// }
	public static final boolean ping() {

		String result = null;

		try {

			String ip = "www.4helper.com";

			Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);

			// // ��ȡping�����ݣ��ɲ��ӡ�
			//
			// InputStream input = p.getInputStream();
			//
			// BufferedReader in = new BufferedReader(new
			// InputStreamReader(input));
			//
			// StringBuffer stringBuffer = new StringBuffer();
			//
			// String content = "";
			//
			// while ((content = in.readLine()) != null) {
			//
			// stringBuffer.append(content);
			//
			// }
			//
			// Log.i("TTT", "result content : " + stringBuffer.toString());

			// PING��״̬

			int status = p.waitFor();

			if (status == 0) {

				result = "successful~";

				return true;

			} else {

				result = "failed~ cannot reach the IP address";

			}

		} catch (IOException e) {

			result = "failed~ IOException";

		} catch (InterruptedException e) {

			result = "failed~ InterruptedException";

		} finally {

			Log.i("TTT", "result = " + result);

		}

		return false;

	}

	// 判断手机格式是否正确

	public static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern

		.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		Matcher m = p.matcher(mobiles);

		return m.matches();

	}

	// 判断email格式是否正确

	public static boolean isEmail(String email) {

		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

		Pattern p = Pattern.compile(str);

		Matcher m = p.matcher(email);

		return m.matches();

	}

	// 判断是否全是数字

	public static boolean isNumeric(String str) {

		Pattern pattern = Pattern.compile("[0-9]*");

		Matcher isNum = pattern.matcher(str);

		if (!isNum.matches()) {

			return false;

		}

		return true;

	}

	public static boolean isLastVerion(Context context, String last_version) {
		try {
			String current_version=CustomInfo.getApp_version(context);
			String[] last=last_version.split("\\.");
			int[] l=new int[last.length];
			for (int i = 0; i < last.length; i++) {
				l[i]=Integer.parseInt(last[i]);
			}
			String[] current=current_version.split("\\.");
			int[] c=new int[current.length];
			for (int i = 0; i < current.length; i++) {
				c[i]=Integer.parseInt(current[i]);	
			}
			int length;
			if (l.length>=c.length) {
				length=l.length;
			}else {
				length=c.length;
			}
			for (int i = 0; i < length; i++) {
				if (l.length>=i&&c.length>=i) {//i位两者都有
					if (c[i]<l[i]) {
						return false;
					}else if (c[i]>l[i]){
						return true;
					}
				}else if (c.length<i) {
					return false;
				}else if (l.length<i){
					return true;
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}

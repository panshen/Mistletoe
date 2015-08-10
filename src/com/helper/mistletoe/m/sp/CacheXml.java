package com.helper.mistletoe.m.sp;

import com.helper.mistletoe.m.pojo.Cache_Bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class CacheXml {
	private SharedPreferences sp;
	private Editor editor;
	private Cache_Bean cache;

	public CacheXml(Context context) {
		// 获取SharedPreferences对象
		sp = context.getSharedPreferences("CacheXml", context.MODE_PRIVATE);
	}

	// 写入
	public void write() {
		cache = new Cache_Bean();
		cache = read();
		// 存入数据
		editor = sp.edit();
		// editor.putString("User_password", user.getUser_password());
		// editor.putInt("User_first_id", 1);
		// editor.putString("User_first_name", user.getUser_first_name());
		// editor.putString("User_first_number", user.getUser_first_number());
		// editor.putInt("User_second_id", 2);
		// editor.putString("User_second_name", user.getUser_second_name());
		// editor.putString("User_second_number", user.getUser_second_number());
		// editor.putInt("User_third_id", 3);
		// editor.putString("User_third_name", user.getUser_third_name());
		// editor.putString("User_third_number", user.getUser_third_number());
		editor.putLong("User_start_time", 12);
		editor.putLong("User_end_time", 13);
		editor.commit();
	}

	// // 更新号码
	// public void updateTel(Tel_Sub_been tel) {
	// editor = sp.edit();
	// if (tel.getTel_id().endsWith("1")) {
	// editor.putString("User_first_name", tel.getTel_name());
	// editor.putString("User_first_number", tel.getTel_number());
	// } else if (tel.getTel_id().endsWith("2")) {
	// editor.putString("User_second_name", tel.getTel_name());
	// editor.putString("User_second_number", tel.getTel_number());
	// } else if (tel.getTel_id().endsWith("3")) {
	// editor.putString("User_third_name", tel.getTel_name());
	// editor.putString("User_third_number", tel.getTel_number());
	// }
	// editor.commit();
	//
	// }

	// 修改密码
	public void updatePassword(String newPassword) {
		editor = sp.edit();
		editor.putString("User_password", newPassword);
		editor.commit();
	}

	// 读取
	public Cache_Bean read() {
		cache = new Cache_Bean();
		// user.setUser_password(sp.getString("User_password", "123456"));
		// user.setUser_first_name(sp.getString("User_first_name", "爸爸"));
		// user.setUser_first_number(sp.getString("User_first_number",
		// "18653503680"));
		// user.setUser_second_name(sp.getString("User_second_name", "妈妈"));
		// user.setUser_second_number(sp.getString("User_second_number",
		// "18905353680"));
		// user.setUser_third_name(sp.getString("User_third_name", "奶奶"));
		// user.setUser_third_number(sp.getString("User_third_number",
		// "18653503681"));
		// user.setUser_start_time(sp.getLong("User_start_time", 12));
		// user.setUser_end_time(sp.getLong("User_end_time", 13));
		return cache;
	}
}

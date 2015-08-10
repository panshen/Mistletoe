package com.helper.mistletoe.v;

import java.util.Random;
import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

public class PhotoHead_TextView extends TextView {
	private static String[] colors = { "#F0E68C", "#FF00FF", "#CD5C5C", "#8A2BE2", "#9400D3", "#0000FF", "#DC143C", "#FF7F50", "#228B22", "#8FBC8F", "#5F9EA0", "#9932CC", "#BDB76B", "#FF8C00",
			"#7FFFD4", "#FF69B4", "#FFF8DC", "#90EE90", "#DAA520", "#7CFC00" };
	private String backColor;
	private String text;

	public PhotoHead_TextView(Context context) {
		super(context);
	}

	public PhotoHead_TextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setBack(Helpers_Sub_Bean helper) {
		if (helper.getHelper_photo() == null || helper.getHelper_photo().equals("")) {// 没有头像
			Random random = new Random();
			int randomInt = random.nextInt(20);
			backColor = colors[randomInt];
			String s = helper.getHelper_name();
			if (s.length() > 0) {
				text = s.substring(s.length() - 1, s.length()).toUpperCase();
			} else {
				text = "#";
			}
			setBackgroundColor(Color.parseColor(backColor));
			setText(text);
			setTextColor(Color.parseColor("#F8F8FF"));
			setTextSize(25);
		} else {
			setBackgroundResource(R.drawable.default_head);
			// setImageShow(user_bean.getAvatar_file_id(),
			// ImageInfo.NET_SMALL);
			// DiskCache diskCache = ImageLoader.getInstance().getDiskCache();
			// String url =
			// head.getImage().getShowPath(getApplicationContext());
			// File ima = diskCache.get(url);
			// mIcon = BitmapFactory.decodeFile(ima.getPath());
		}
	}
}

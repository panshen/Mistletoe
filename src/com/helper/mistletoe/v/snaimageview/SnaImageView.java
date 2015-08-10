package com.helper.mistletoe.v.snaimageview;

import java.io.File;

import android.net.Uri;

public interface SnaImageView {
	public SnaBitmap getImageForShow();

	public void setImageForShow(SnaBitmap mImageForShow);

	public int getDefaultImageForShow();

	public void setDefaultImageForShow(int mDefaultImageForShow);

	public void setImageForShow(String imageId, int sz);

	public void setImageForShow(int imageId, int sz);

	public void setImageForShow(File file);

	public void setImageForShow(Uri uri);
}
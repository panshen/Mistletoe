package com.helper.mistletoe.v.snaimageview.v2;

import java.io.File;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.util.DisplayImage;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;

public class SnaImageViewV2 extends ImageView implements com.helper.mistletoe.v.snaimageview.SnaImageView {
	private SnaBitmap mImageForShow;
	private SnaBitmap mDefaultImageForShow;
	private Context context;

	public SnaImageViewV2(Context context) {
		super(context);
		try {
			this.context = context;
			initView();
			setData();
			setListener();
			updateShow();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public SnaImageViewV2(Context context, AttributeSet attr) {
		super(context, attr);
		try {
			this.context = context;
			initView();
			setData();
			setListener();
			updateShow();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public SnaImageViewV2(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
		try {
			this.context = context;
			initView();
			setData();
			setListener();
			updateShow();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setData() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setListener() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void updateShow() {
		try {
			// 显示图片
			if (getImageForShow().isContentEffective()) {
				DisplayImage.loadImageUseAUIL_Ex(context, getImageForShow(), this, getDefaultImageForShow());
			} else {
				setImageResource(getDefaultImageForShow());
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public SnaBitmap getImageForShow() {
		if (mImageForShow == null) {
			mImageForShow = new com.helper.mistletoe.v.snaimageview.v2.SnaBitmapV2();
		}
		return mImageForShow;
	}

	@Override
	public void setImageForShow(SnaBitmap mImageForShow) {
		this.mImageForShow = mImageForShow;
		updateShow();
	}

	@Override
	public void setImageForShow(String imageId, int sz) {
		try {
			getImageForShow().setPath(imageId, sz);
			setImageForShow(getImageForShow());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setImageForShow(int imageId, int sz) {
		try {
			getImageForShow().setPath(imageId, sz);
			setImageForShow(getImageForShow());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setImageForShow(File file) {
		try {
			getImageForShow().setPath(file);
			setImageForShow(getImageForShow());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setImageForShow(Uri uri) {
		try {
			getImageForShow().setPath(uri);
			setImageForShow(getImageForShow());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public int getDefaultImageForShow() {
		if (mDefaultImageForShow == null) {
			mDefaultImageForShow = new com.helper.mistletoe.v.snaimageview.v2.SnaBitmapV2();
			mDefaultImageForShow.setPath(R.drawable.ic_launcher);
		}
		return mDefaultImageForShow.getPath_Resources();
	}

	@Override
	public void setDefaultImageForShow(int defaultImageForShow) {
		this.mDefaultImageForShow.setPath(defaultImageForShow);
		updateShow();
	}

	@Override
	@Deprecated
	public void setBackground(Drawable background) {
		// 这个方法被屏蔽掉，因为该控件不支持修改背景
	}

	@Override
	@Deprecated
	public void setBackgroundColor(int color) {
		// 这个方法被屏蔽掉，因为该控件不支持修改背景
	}

	@Override
	@Deprecated
	public void setBackgroundDrawable(Drawable background) {
		// 这个方法被屏蔽掉，因为该控件不支持修改背景
	}

	@Override
	@Deprecated
	public void setBackgroundResource(int resid) {
		// 这个方法被屏蔽掉，因为该控件不支持修改背景
	}

}
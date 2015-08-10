//package com.helper.mistletoe.v.snaimageview.v1;
//
//import java.io.File;
//
//import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;
//import android.content.Context;
//import android.net.Uri;
//import android.util.AttributeSet;
//
//import com.helper.mistletoe.util.ExceptionHandle;
//import com.helper.mistletoe.v.snaimage.SnaBitmap;
//import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;
//
//public class SnaImage extends SnaImageViewV2 {
//	public SnaImage(Context context) {
//		super(context);
//		try {
//		} catch (Exception e) {
//			ExceptionHandle.ignoreException(e);
//		}
//	}
//
//	public SnaImage(Context context, AttributeSet attr) {
//		super(context, attr);
//		try {
//		} catch (Exception e) {
//			ExceptionHandle.ignoreException(e);
//		}
//	}
//
//	public SnaImage(Context context, AttributeSet attr, int defStyle) {
//		super(context, attr, defStyle);
//		try {
//		} catch (Exception e) {
//			ExceptionHandle.ignoreException(e);
//		}
//	}
//
//	public void setImageShow(Uri uri) throws Exception {
//		setmImageForShow(uri);
//	}
//
//	public void setImageShow(SnaBitmap bitmap) throws Exception {
//		setmImageForShow(bitmap);
//	}
//
//	public void setImageShow(File file) throws Exception {
//		setmImageForShow(file);
//	}
//
//	public void setImageShow(String id, int sz) {
//		setmImageForShow(id, sz);
//	}
//
//	public void setImageShow(Integer id, int sz) {
//		setmImageForShow(id, sz);
//	}
//
//	public void setOnClickListener(OnViewTapListener l) {
//		setOnViewTapListener(l);
//	}
//
//	public ImagePath getImage() throws Exception {
//		return new ImagePath(getmImageForShow());
//	}
//
// }
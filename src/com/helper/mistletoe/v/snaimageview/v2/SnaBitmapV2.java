package com.helper.mistletoe.v.snaimageview.v2;

import java.io.File;

import android.content.Context;
import android.net.Uri;

import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.util.sysconst.MainConst;

public class SnaBitmapV2 implements com.helper.mistletoe.v.snaimageview.SnaBitmap {
	private int type;
	private Uri path_Uri;
	private File path_File;
	private int path_Id;
	private int path_Id_Sz;
	private int path_Resources;

	@Override
	public String getAUILShowPath(Context context) {
		String result = "";
		try {
			switch (getType()) {
			case PATH_TYPE_NULL:
				result = "";
				break;
			case PATH_TYPE_URI:
				result = getPath_Uri().toString();
				break;
			case PATH_TYPE_FILE:
				result = getShowPath_File();
				break;
			case PATH_TYPE_ID:
				result = getShowPath_Id(context);
				break;
			case PATH_TYPE_RESOURCES:
				result = "drawable://" + getPath_Resources();
				break;
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	private String getShowPath_File() {
		String result = "";
		try {
			result = "file://" + getPath_File().getPath();
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	private String getShowPath_Id(Context context) {
		String result = "";
		try {
			if (getPath_Id() > 0) {
				result += MainConst.NET_DOWNLOAD_FILE;
				result += "&id=" + getPath_Id();
				result += "&sz=" + getPath_Id_sz();
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public int getType() {
		int result = PATH_TYPE_NULL;
		try {
			result = type;
		} catch (Exception e) {
			result = PATH_TYPE_NULL;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public Uri getPath_Uri() {
		Uri result = null;
		try {
			result = this.path_Uri;
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public File getPath_File() {
		File result = new File("");
		try {
			result = this.path_File;
		} catch (Exception e) {
			result = new File("");
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public int getPath_Id() {
		int result = 0;
		try {
			result = this.path_Id;
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public int getPath_Id_sz() {
		int result = NET_SMALL;
		try {
			result = path_Id_Sz;
		} catch (Exception e) {
			result = NET_SMALL;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public void setPath_Null() {
		try {
			type = PATH_TYPE_NULL;
			path_Uri = null;
			path_File = null;
			path_Id = 0;
			path_Id_Sz = NET_SMALL;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setPath(Uri uri) {
		try {
			setPath_Null();
			type = PATH_TYPE_URI;
			this.path_Uri = uri;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setPath(File file) {
		try {
			setPath_Null();
			type = PATH_TYPE_FILE;
			this.path_File = file;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setPath(String imageId, int sz) {
		try {
			int mImageId = Transformation_Util.String2int(imageId, -1);
			setPath(mImageId, sz);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setPath(int imageId, int sz) {
		try {
			setPath_Null();
			type = PATH_TYPE_ID;
			this.path_Id = imageId;
			this.path_Id_Sz = sz;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void setPath(int Resources) {
		try {
			setPath_Null();
			type = PATH_TYPE_RESOURCES;
			this.path_Resources = Resources;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public int getPath_Resources() {
		int result = 0;
		try {
			result = path_Resources;
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public boolean isContentEffective() {
		boolean result = false;
		try {
			switch (getType()) {
			case PATH_TYPE_NULL:
				result = false;
				break;
			case PATH_TYPE_URI:
				result = true;
				break;
			case PATH_TYPE_FILE:
				result = true;
				break;
			case PATH_TYPE_ID:
				if (getPath_Id() > 0) {
					result = true;
				}
				break;
			case PATH_TYPE_RESOURCES:
				result = true;
				break;
			}
		} catch (Exception e) {
			result = false;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
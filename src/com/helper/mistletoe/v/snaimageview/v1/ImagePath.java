//package com.helper.mistletoe.v.snaimageview.v1;
//
//import java.io.File;
//
//import android.content.Context;
//import android.net.Uri;
//
//import com.helper.mistletoe.util.ExceptionHandle;
//import com.helper.mistletoe.util.sysconst.MainConst;
//import com.helper.mistletoe.v.snaimage.SnaBitmap;
//
//public class ImagePath {
//	private int type;
//	private Uri path_Uri;
//	private File path_File;
//	private String path_Id;
//	private int path_Id_Sz;
//
//	public ImagePath() {
//		try {
//		} catch (Exception e) {
//			ExceptionHandle.ignoreException(e);
//		}
//
//	}
//
//	public ImagePath(SnaBitmap snaBitmap) {
//		try {
//			switch (snaBitmap.getType()) {
//			case SnaBitmap.PATH_TYPE_NULL:
//				setPathNull();
//				break;
//			case SnaBitmap.PATH_TYPE_URI:
//				setPath(snaBitmap.getPath_Uri());
//				break;
//			case SnaBitmap.PATH_TYPE_FILE:
//				setPath(snaBitmap.getPath_File());
//				break;
//			case SnaBitmap.PATH_TYPE_ID:
//				setPath("" + snaBitmap.getPath_Id(), snaBitmap.getPath_Id_sz());
//				break;
//			}
//		} catch (Exception e) {
//			ExceptionHandle.ignoreException(e);
//		}
//	}
//
//	public void setPathNull() {
//		try {
//			type = ImageInfo.PATH_TYPE_NULL;
//			path_Uri = null;
//			path_File = null;
//			path_Id = "";
//			path_Id_Sz = ImageInfo.NET_SMALL;
//		} catch (Exception e) {
//			ExceptionHandle.ignoreException(e);
//		}
//	}
//
//	public void setPath(Uri uri) {
//		try {
//			setPathNull();
//			this.path_Uri = uri;
//			type = ImageInfo.PATH_TYPE_URI;
//		} catch (Exception e) {
//			ExceptionHandle.ignoreException(e);
//		}
//	}
//
//	public void setPath(File file) {
//		try {
//			setPathNull();
//			this.path_File = file;
//			type = ImageInfo.PATH_TYPE_FILE;
//		} catch (Exception e) {
//			ExceptionHandle.ignoreException(e);
//		}
//	}
//
//	public void setPath(String imageId, int sz) {
//		try {
//			setPathNull();
//			if (Integer.valueOf(imageId) > 0) {
//				this.path_Id = imageId;
//				this.path_Id_Sz = sz;
//				type = ImageInfo.PATH_TYPE_ID;
//			}
//		} catch (Exception e) {
//			ExceptionHandle.ignoreException(e);
//		}
//	}
//
//	public Uri getPathUri() {
//		Uri result = null;
//		try {
//			result = this.path_Uri;
//		} catch (Exception e) {
//			result = null;
//			ExceptionHandle.ignoreException(e);
//		}
//		return result;
//	}
//
//	public File getPathFile() {
//		File result = null;
//		try {
//			result = this.path_File;
//		} catch (Exception e) {
//			result = null;
//			ExceptionHandle.ignoreException(e);
//		}
//		return result;
//	}
//
//	public String getPathId() {
//		String result = null;
//		try {
//			result = this.path_Id;
//		} catch (Exception e) {
//			result = null;
//			ExceptionHandle.ignoreException(e);
//		}
//		return result;
//	}
//
//	public int getPathId_sz() {
//		if ((!(path_Id_Sz == ImageInfo.NET_NORMAL))
//				&& (!(path_Id_Sz == ImageInfo.NET_SMALL))) {
//			type = ImageInfo.NET_SMALL;
//		}
//		return path_Id_Sz;
//	}
//
//	public String getShowPath(Context context) {
//		String result = null;
//		try {
//			switch (getType()) {
//			case ImageInfo.PATH_TYPE_NULL:
//				result = "";
//				break;
//			case ImageInfo.PATH_TYPE_URI:
//				result = getPathUri().toString();
//				break;
//			case ImageInfo.PATH_TYPE_FILE:
//				result = getShowPath_File();
//				break;
//			case ImageInfo.PATH_TYPE_ID:
//				result = getShowPath_Id(context);
//				break;
//			}
//		} catch (Exception e) {
//			result = null;
//			ExceptionHandle.ignoreException(e);
//		}
//		return result;
//	}
//
//	public int getType() {
//		if ((!(type == ImageInfo.PATH_TYPE_NULL))
//				&& (!(type == ImageInfo.PATH_TYPE_URI))
//				&& (!(type == ImageInfo.PATH_TYPE_FILE))
//				&& (!(type == ImageInfo.PATH_TYPE_ID))) {
//			type = ImageInfo.PATH_TYPE_NULL;
//		}
//		return type;
//	}
//
//	private String getShowPath_File() {
//		String result = "";
//		try {
//			result = "file://" + getPathFile().getPath();
//		} catch (Exception e) {
//			result = "";
//			ExceptionHandle.ignoreException(e);
//		}
//		return result;
//	}
//
//	private String getShowPath_Id(Context context) {
//		String result = "";
//		try {
//			String url = MainConst.NET_DOWNLOAD_FILE;
//			result += url;
//			// {
//			// // 增加验证信息
//			// NetRequest_Bean request = new NetRequest_Bean();
//			// request.acquisitionLinkKey(context);
//			// result += "&signature=" + request.getSignature();
//			// result += "&timestamp=" + request.getTimestamp();
//			// result += "&user_id=" + request.getUser_id();
//			// result += "&access_token=" + request.getAccess_token();
//			// }
//			result += "&id=" + getPathId();
//			result += "&sz=" + getPathId_sz();
//		} catch (Exception e) {
//			result = "";
//			ExceptionHandle.ignoreException(e);
//		}
//		return result;
//	}
//
// }
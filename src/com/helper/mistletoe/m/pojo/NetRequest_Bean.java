package com.helper.mistletoe.m.pojo;

import java.io.File;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Calendar;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.helper.mistletoe.m.sp.User_sp;
import com.helper.mistletoe.util.CustomInfo;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.sysconst.MainConst;
import com.loopj.android.http.RequestParams;

/**
 * Created by stemp1234 on 15/3/4.
 */
public class NetRequest_Bean implements Parcelable {
	/**
	 * Post请求
	 */
	public final static String REQUEST_TYPE_POST = "post";
	/**
	 * Get请求
	 */
	public final static String REQUEST_TYPE_GET = "get";
	/**
	 * 不包括文件
	 */
	public final static String FILE_TYPE_NONE = "-1";
	/**
	 * 图片
	 */
	public final static String FILE_TYPE_IMAGE = "0";
	/**
	 * 语音
	 */
	public final static String FILE_TYPE_SOUND = "1";
	/**
	 * 文件
	 */
	public final static String FILE_TYPE_FILE = "2";

	private String signature;// 加密签名。signature结合了app_token参数和请求中的timestamp参数、access_token参数。
	private String timestamp;// 时间戳
	private String user_id;// 用户id
	private String access_token;// 用户访问凭证
	private String data;// json格式的请求内容(string)
	private String sys_RequestType;// 请求的方式Post或者Get
	private String sys_RequestUrl;// 要请求的Url
	private String file_type;// 文件类型 (图片=0,语音=1,文件=2)
	private File file_content;// 文件内容

	// TODO 以下是特有的函数
	/**
	 * 自动采集连接到服务器上的安全认证信息，请在连接服务器前使用此函数。
	 */
	public void acquisitionLinkKey(Context context) throws Exception {
		try {
			String tAccess_token = "";
			String tUser_id = "";
			String tTimestamp = "";
			String tSignature = "";
			String tApp_token = CustomInfo.getApp_Token();
			// 获取Access_token
			User_sp tempSp = new User_sp(context);
			User_Bean user_Bean = tempSp.read();
			tAccess_token = user_Bean.getAccess_token();
			if ((getSys_RequestUrl().equals(MainConst.NET_USER_LOGIN)) || (getSys_RequestUrl().equals(MainConst.NET_USER_REGISTER))) {
				tAccess_token = "default_token";
			}
			// 获取User_id
			tUser_id = "" + user_Bean.getUser_id();
			// 获取Timestamp
			tTimestamp = String.valueOf((Calendar.getInstance().getTime().getTime()) / 1000);
			// 获取Signature
			String[] in = { tApp_token, tTimestamp, tAccess_token };
			Arrays.sort(in);
			String tempin = "";
			for (String a : in) {
				tempin += a;
			}
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(tempin.getBytes());
			byte[] dddddd = messageDigest.digest();

			StringBuffer strBuffer = new StringBuffer(dddddd.length * 2);
			for (int i = 0; i < dddddd.length; i++) {
				strBuffer.append(Character.forDigit((dddddd[i] & 240) >> 4, 16));
				strBuffer.append(Character.forDigit(dddddd[i] & 15, 16));
			}

			tSignature = new String(strBuffer.toString());
			// 保存结果
			setAccess_token(tAccess_token);
			setUser_id(tUser_id);
			setTimestamp(tTimestamp);
			setSignature(tSignature);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	/**
	 * 把该JavaBean中的内容转换为RequestParams。
	 *
	 * @return 网络请求参数
	 */
	public RequestParams createRequestParams() throws Exception {
		RequestParams params = null;
		try {
			params = new RequestParams();
			params.put("signature", getSignature());
			params.put("timestamp", getTimestamp());
			params.put("user_id", getUser_id());
			params.put("access_token", getAccess_token());
			params.put("data", getData());
			if (!getFile_type().equals(FILE_TYPE_NONE)) {
				params.put("file_type", getFile_type());
				params.put("file_content", getFile_content());
			}
		} catch (Exception e) {
			params = null;
			ExceptionHandle.unInterestException(e);
		}
		return params;
	}

	// TODO 以上是特有的函数

	private NetRequest_Bean(Parcel in) {
		try {
			signature = in.readString();
			timestamp = in.readString();
			user_id = in.readString();
			access_token = in.readString();
			data = in.readString();
			sys_RequestType = in.readString();
			sys_RequestUrl = in.readString();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		try {
			dest.writeString(signature);
			dest.writeString(timestamp);
			dest.writeString(user_id);
			dest.writeString(access_token);
			dest.writeString(data);
			dest.writeString(sys_RequestType);
			dest.writeString(sys_RequestUrl);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public NetRequest_Bean() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static final Parcelable.Creator<NetRequest_Bean> CREATOR = new Parcelable.Creator<NetRequest_Bean>() {
		@Override
		public NetRequest_Bean createFromParcel(Parcel in) {
			return new NetRequest_Bean(in);
		}

		@Override
		public NetRequest_Bean[] newArray(int size) {
			return new NetRequest_Bean[size];
		}
	};

	public static Parcelable.Creator<NetRequest_Bean> getCreator() {
		return CREATOR;
	}

	@Override
	public int describeContents() {
		return CONTENTS_FILE_DESCRIPTOR;
	}

	// TODO 以下为Get Set函数
	public String getSignature() {
		if (signature == null) {
			signature = "";
		}
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		if (timestamp == null) {
			timestamp = "";
		}
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUser_id() {
		if (user_id == null) {
			user_id = "";
		}
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAccess_token() {
		if (access_token == null) {
			access_token = "";
		}
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getData() {
		if (data == null) {
			data = "";
		}
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 如果sys_RequestType既不是REQUEST_TYPE_GET也不是REQUEST_TYPE_POST，
	 * 会将它变为默认值REQUEST_TYPE_POST
	 *
	 * @return sys_RequestType的值
	 */
	public String getSys_RequestType() {
		if (sys_RequestType == null) {
			sys_RequestType = REQUEST_TYPE_POST;
		}
		if (!sys_RequestType.equals(REQUEST_TYPE_POST) && !sys_RequestType.equals(REQUEST_TYPE_GET)) {
			sys_RequestType = REQUEST_TYPE_POST;
		}
		return sys_RequestType;
	}

	public void setSys_RequestType(String sys_RequestType) {
		this.sys_RequestType = sys_RequestType;
	}

	public String getSys_RequestUrl() {
		if (sys_RequestUrl == null) {
			sys_RequestUrl = "";
		}
		return sys_RequestUrl;
	}

	public void setSys_RequestUrl(String sys_RequestUrl) {
		this.sys_RequestUrl = sys_RequestUrl;
	}

	public String getFile_type() {
		if (file_type == null) {
			file_type = FILE_TYPE_NONE;
		}
		if (!file_type.equals(FILE_TYPE_NONE) && !file_type.equals(FILE_TYPE_FILE) && !file_type.equals(FILE_TYPE_IMAGE) && !file_type.equals(FILE_TYPE_SOUND)) {
			file_type = FILE_TYPE_NONE;
		}
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public File getFile_content() {
		if (file_content == null) {
			file_content = null;
		}
		return file_content;
	}

	public void setFile_content(File file_content) {
		this.file_content = file_content;
	}

}
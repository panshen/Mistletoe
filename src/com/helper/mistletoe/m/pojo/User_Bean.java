package com.helper.mistletoe.m.pojo;

import android.content.Context;

import com.helper.mistletoe.m.sp.User_sp;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.SnaBitmap.ImageSize;
import com.helper.mistletoe.v.snaimageview.v2.SnaBitmapV2;

public class User_Bean extends User_Pojo {
	public void readData(User_sp usp) throws Exception {
		try {
			usp.read(this);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void readData(Context context) throws Exception {
		try {
			User_sp usp = new User_sp(context);
			readData(usp);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
			
		}
	}

	public void writeData(User_sp usp) throws Exception {
		try {
			usp.write(this);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void writeData(Context context) throws Exception {
		try {
			User_sp usp = new User_sp(context);
			writeData(usp);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void transformDataTo(Helpers_Sub_Bean bean) {
		try {
			bean.setHelper_tel(getTel());
			bean.setHelper_id(getUser_id());
			bean.setHelper_account(getLoc_Account());
			bean.setHelper_readygo_name(getName());
			bean.setHelper_gender(getGender());
			bean.setHelper_email(getEmail());
			bean.setHelper_company(getCompany());
			bean.setHelper_title(getTitle());
			bean.setHelper_address(getAddress());
			bean.setHelper_photo(getAvatar_file_id().toString());
			bean.setHelper_account_type(getLoc_AccountType());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 判断本地的User是否是个有效地User，有效为true，无效为false
	 */
	public boolean isEffective() {
		boolean result = false;
		try {
			result = true;
			if (getLoc_Account().trim().equals("")) {
				// 如果账号没有，认为用户无效
				result = false;
			}
			if (getLoc_AccountType() < 0) {
				// 如果账号类型小于零，认为用户无效
				result = false;
			}
			if (getVerification().trim().equals("")) {
				// 如果验证码没有，认为用户无效
				result = false;
			}
			if (getAccess_token().trim().equals("")) {
				// 如果Token没有，认为用户无效
				result = false;
			}
			if ((getName().trim().equals("")) || (getName().trim().equals("用户[" + getUser_id() + "]"))) {
				// 如果没有名字，认为用户无效
				result = false;
			}
			if (getDevice_id() < 0) {
				// 如果设备号没有，认为用户无效
				result = false;
			}
			if (getUser_id() < 0) {
				// 如果用户Id没有，认为用户无效
				result = false;
			}
		} catch (Exception e) {
			result = false;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public SnaBitmap getAvatar(ImageSize size) {
		SnaBitmap result = new SnaBitmapV2();
		try {
			result.setPath((int) getAvatar_file_id(), size.toInt());
		} catch (Exception e) {
			result = new SnaBitmapV2();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
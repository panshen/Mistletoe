package com.helper.mistletoe.m.pojo;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;

import com.helper.mistletoe.m.db.impl.AirLock_DB;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Prism_Util;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.v.snaimageview.SnaBitmap.ImageSize;
import com.helper.mistletoe.v.snaimageview.v2.SnaBitmapV2;

public class TargetMember_Bean extends TargetMember_Pojo {
	public void copyData(Helpers_Sub_Bean o) {
		try {
			Prism_Util.copyData(Helpers_Sub_Bean.class, o, this);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void foundHelper(Context context) throws Exception {
		try {
			boolean contentIsSafe = true;
			if (getMember_id() < 1) {
				contentIsSafe = false;
			}
			if (contentIsSafe) {
				Helpers_Sub_Bean tHelper = AirLock_DB.select_helper(context, getMember_id());
				Prism_Util.copyData(Helpers_Sub_Bean.class, tHelper, this);
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public String getMemberStatusIntroduction() throws Exception {
		String result = "";
		try {
			result = getMember_status().getDescription();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	public String getShowName() {
		String result = "[未命名]";
		try {
			if ((!getHelper_name().trim().equals("")) && (!getHelper_name().trim().equals("未设置姓名"))) {
				result = getHelper_name();
			} else if (!getMember_name().trim().equals("")) {
				result = getMember_name();
			}
		} catch (Exception e) {
			result = "[未命名]";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public int getShowImageId() {
		return getShowImageId(-1);
	}

	public int getShowImageId(int defaultId) {
		int result = defaultId;
		try {
			if (Transformation_Util.String2int(getHelper_photo(), -1) > 0) {
				result = Transformation_Util.String2int(getHelper_photo(), -1);
			} else if (getMember_avatar_file_id() > 0) {
				result = getMember_avatar_file_id();
			}
		} catch (Exception e) {
			result = defaultId;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public SnaBitmapV2 getShowSnaBitmap() {
		return getShowSnaBitmap(new SnaBitmapV2());
	}

	public SnaBitmapV2 getShowSnaBitmap(SnaBitmapV2 defaultImage) {
		SnaBitmapV2 result = defaultImage;
		try {
			int tImageId = getShowImageId(-1);
			if (tImageId > 0) {
				result = new SnaBitmapV2();
				result.setPath(tImageId, ImageSize.Small.toInt());
			}
		} catch (Exception e) {
			result = defaultImage;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public boolean canDelete() {
		boolean result = false;
		try {
			ArrayList<MemberStatus> tArrayStatus = new ArrayList<MemberStatus>();
			tArrayStatus.addAll(Arrays.asList(new MemberStatus[] { MemberStatus.Discussing, MemberStatus.Signed }));
			if (tArrayStatus.contains(getMember_status())) {
				result = true;
			}
		} catch (Exception e) {
			result = false;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public boolean getIn_waiting_status() {
		boolean result = false;
		try {
			ArrayList<MemberStatus> tArrayStatus = new ArrayList<MemberStatus>();
			tArrayStatus.addAll(Arrays.asList(new MemberStatus[] { MemberStatus.OwnerApplyClose, MemberStatus.HelperApplyClose }));
			if ((tArrayStatus.contains(getMember_status())) && (getSeconds_countdown() > 0)) {
				result = true;
			}
		} catch (Exception e) {
			result = false;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public boolean needOperation() {
		boolean result = false;
		try {
			ArrayList<MemberStatus> tArrayStatus = new ArrayList<MemberStatus>();
			tArrayStatus.addAll(Arrays.asList(new MemberStatus[] { MemberStatus.HelperApplyClose }));
			if (tArrayStatus.contains(getMember_status())) {
				result = true;
			}
		} catch (Exception e) {
			result = false;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public String showWaitingTimes() {
		String result = "Error";
		try {
			int tHours = 0;
			{
				tHours = getSeconds_countdown() / (60 * 60);
			}
			int tMinutes = 0;
			{
				int tSurplusTime = getSeconds_countdown() - (tHours * 60 * 60);
				tMinutes = tSurplusTime / 60;
			}
			result = tHours + "时" + tMinutes + "分";
		} catch (Exception e) {
			result = "Error";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}
}
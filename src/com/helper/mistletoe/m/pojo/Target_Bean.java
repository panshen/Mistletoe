package com.helper.mistletoe.m.pojo;

import java.io.File;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;

import android.content.Context;
import android.content.DialogInterface;

import com.helper.mistletoe.m.net.request.Image_Upload_NetRequest;
import com.helper.mistletoe.m.net.request.TargetMember_Watch_NetRequest.OperatingState;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.UpdateStatusRequestNumber;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRunningState;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetType;
import com.helper.mistletoe.m.work.be.cloud.SyncTargetList_Target_Mode;
import com.helper.mistletoe.m.work.be.cloud.TargetMember_UpdateSettings_Mode;
import com.helper.mistletoe.m.work.be.cloud.TargetMember_UpdateStatus_Mode;
import com.helper.mistletoe.m.work.be.cloud.TargetMember_Update_Mode;
import com.helper.mistletoe.m.work.be.cloud.TargetMember_Watch_Mode;
import com.helper.mistletoe.m.work.be.cloud.Target_Attitude_Mode;
import com.helper.mistletoe.m.work.be.cloud.Target_CreateTarget_Mode;
import com.helper.mistletoe.m.work.be.cloud.Target_Update_Mode;
import com.helper.mistletoe.util.Dialog_Util;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.SnaBitmap.ImageSize;
import com.helper.mistletoe.v.snaimageview.v2.SnaBitmapV2;

public class Target_Bean extends Target_Pojo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void readTargetMember_Local(Context context) {
		getLoc_TargetMember().readTargetMember_Local(context, getTarget_id());
	}

	public boolean userIsCreater(Context context) {
		boolean result = false;
		try {
			User_Bean user = new User_Bean();
			user.readData(context);
			int CreaterId = getCreator_id();
			int userid = user.getUser_id();
			result = (CreaterId == userid);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 目标修改<br/>
	 * 修改云端
	 * 
	 * @param context
	 *            设备上下文
	 */
	public void updateCloud(Context context) {
		updateCloud(context, null);
	}

	/**
	 * 目标修改<br/>
	 * 修改云端
	 * 
	 * @param context
	 *            设备上下文
	 */
	public void updateCloud(Context context, String filePath) {
		try {
			Target_Update_Mode mode = new Target_Update_Mode(this, filePath, null);
			mode.publishWork(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 目标成员修改<br/>
	 * 修改云端
	 * 
	 * @param context
	 *            设备上下文
	 */
	public void updateMembersCloud(Context context, String members) {
		try {
			TargetMember_Update_Mode mode = new TargetMember_Update_Mode(getTarget_id(), members);
			mode.publishWork(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 目标设置修改<br/>
	 * 修改云端
	 * 
	 * @param context
	 *            设备上下文
	 */
	public void updateSettingsCloud(Context context, String target_stick, String target_flag, String accept_push_msg) {
		try {
			TargetMember_UpdateSettings_Mode mode = new TargetMember_UpdateSettings_Mode(getTarget_id(), target_stick, target_flag,
					accept_push_msg);
			mode.publishWork(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 目标状态修改<br/>
	 * 修改云端
	 * 
	 * @param context
	 *            设备上下文
	 */
	public void updateStatusCloud(Context context, UpdateStatusRequestNumber requestType, Integer memberId) {
		try {
			final SoftReference<Context> tContext = new SoftReference<Context>(context);
			final SoftReference<UpdateStatusRequestNumber> tRequestType = new SoftReference<UpdateStatusRequestNumber>(requestType);
			final SoftReference<Integer> tMemberId = new SoftReference<Integer>(memberId);
			String tOperationRemind = tRequestType.get().getOperationRemind();
			// 如果有操作提示就弹出窗口，如果没有操作提示就直接修改
			if (!tOperationRemind.trim().equals("")) {
				Dialog_Util.showDialog(tContext.get(), tOperationRemind, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							new TargetMember_UpdateStatus_Mode(getTarget_id(), tRequestType.get(), tMemberId.get()).publishWork(tContext
									.get());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} else {
				new TargetMember_UpdateStatus_Mode(getTarget_id(), tRequestType.get(), tMemberId.get()).publishWork(tContext.get());
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void entryMarket(Context context) {
		try {
			final SoftReference<Context> tContext = new SoftReference<Context>(context);
			final SoftReference<TargetType> tRequestType = new SoftReference<TargetType>(canChangeToTargetType(tContext.get()));
			String tOperationRemind = "";

			if (tRequestType.get() == TargetType.Market) {
				tOperationRemind = "如果目标内没有签约的帮手，你的目标会进入到海报市场";
			} else if (tRequestType.get() == TargetType.General) {
				tOperationRemind = "你将从海报市场撤回目标";
			}
			// 如果有操作提示就弹出窗口，如果没有操作提示就直接修改
			if (!tOperationRemind.trim().equals("")) {
				Dialog_Util.showDialog(tContext.get(), tOperationRemind, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							setTarget_type(tRequestType.get());
							updateCloud(tContext.get());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} else {
				setTarget_type(tRequestType.get());
				updateCloud(tContext.get());
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 同步此Target
	 * 
	 * @param context
	 *            设备上下文
	 */
	public void sync(Context context) {
		try {
			SyncTargetList_Target_Mode workObj = new SyncTargetList_Target_Mode();
			workObj.publishWork(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 使目标静音<br/>
	 * 修改云端
	 * 
	 * @param context
	 *            设备上下文
	 */
	public void muteTarget(Context context) {
		try {
			String t_Accept = "0";
			if (getAccept_push_msg() == 0) {
				t_Accept = "1";
			}
			updateSettingsCloud(context, null, null, t_Accept);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 使目标置顶<br/>
	 * 修改云端
	 * 
	 * @param context
	 *            设备上下文
	 */
	public void topTarget(Context context) {
		try {
			String t_Stick = "1";
			if (getTarget_stick() == TARGET_STICK_STICK) {
				t_Stick = "2";
			}
			updateSettingsCloud(context, t_Stick, null, null);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 修改目标的颜色<br/>
	 * 修改云端
	 * 
	 * @param context
	 *            设备上下文
	 */
	public void changeColorTarget(Context context, String target_flag) {
		try {
			updateSettingsCloud(context, null, target_flag, null);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void createTarget_Cloud(Context context) {
		createTarget_Cloud(context, null);
	}

	public void createTarget_Cloud(Context context, String filePath) {
		try {
			// 格式化成员列表
			JSONArray tJsonArray = new JSONArray();
			try {
				tJsonArray = new JSONArray(getLoc_MembersString());
			} catch (Exception e) {
				ExceptionHandle.ignoreException(e);
			}
			setLoc_MembersString(tJsonArray.toString());
			// 重新发送
			new Target_CreateTarget_Mode(this, filePath).publishWork(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 获取目标的运行状态
	 * 
	 * @return 目标的运行状态
	 */
	@Override
	public TargetRunningState getStatus() {
		return Transformation_Util.MemberStatus2TargetRunningState(getStatus_server(), TargetRunningState.Running);
	}

	public boolean needAgree() {
		boolean result = false;
		try {
			ArrayList<MemberStatus> tList = new ArrayList<MemberStatus>();
			tList.addAll(Arrays.asList(new MemberStatus[] { MemberStatus.Discussing, MemberStatus.OwnerApplyClose }));
			if (tList.contains(getStatus_server())) {
				result = true;
			}
		} catch (Exception e) {
			result = false;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public boolean needRefuse() {
		boolean result = false;
		try {
			ArrayList<MemberStatus> tList = new ArrayList<MemberStatus>();
			tList.addAll(Arrays.asList(new MemberStatus[] { MemberStatus.Discussing, MemberStatus.OwnerApplyClose }));
			if (tList.contains(getStatus_server())) {
				result = true;
			}
		} catch (Exception e) {
			result = false;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public boolean isContentSecurity() {
		boolean result = true;
		try {
			if ((target_id != null) && (getTarget_id() < 1)) {
				boolean hasTitle = (summary != null) && (!getSummary().trim().equals(""));
				boolean hasCreater = (creator_id != null) && (getCreator_id() > 0);
				result = hasTitle && hasCreater;
			}
		} catch (Exception e) {
			result = true;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public void uploadCover(Context context, String filePath) {
		try {
			File tFile = new File(filePath);

			boolean isDataSafe = true;
			if ((filePath == null) || (filePath.trim().equals(""))) {
				isDataSafe = false;
			}
			if (isDataSafe) {
				if (!tFile.isFile()) {
					isDataSafe = false;
				}
			}
			if (isDataSafe) {
				// 如果file文件可以找到，上传，取回Id
				int tId = Transformation_Util.String2int(new Image_Upload_NetRequest(context).doUpload(tFile).getFile_id(), -1);
				if (tId > 0) {
					// 如果Id大于0，把Id赋给Target
					ArrayList<Integer> tArray = new ArrayList<Integer>();
					tArray.add(tId);
					setHead_pics(tArray);
				} else {
					head_pics = null;
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public int getShowImageId() {
		return getShowImageId(-1);
	}

	public int getShowImageId(int defaultId) {
		int result = defaultId;
		try {
			if (getHead_pics().size() > 0) {
				if (getHead_pics().get(0) > 0) {
					result = getHead_pics().get(0);
				}
			} else if (getLoc_TargetMember().getOwner().getShowImageId(-1) > 0) {
				result = getLoc_TargetMember().getOwner().getShowImageId(-1);
			} else if (getAvatar_file_id() > 0) {
				result = getAvatar_file_id();
			}
		} catch (Exception e) {
			result = defaultId;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public SnaBitmap getShowSnaBitmap() {
		return getShowSnaBitmap(new SnaBitmapV2());
	}

	public SnaBitmap getShowSnaBitmap(SnaBitmap defaultImage) {
		SnaBitmap result = defaultImage;
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

	public TargetType canChangeToTargetType(Context context) {
		TargetType result = null;
		try {
			if (userIsCreater(context)) {
				switch (getTarget_type()) {
				case General:
					result = TargetType.Market;
					break;
				case Market:
					result = TargetType.General;
					break;
				case Top:
					result = TargetType.General;
					break;
				default:
					result = null;
					break;
				}
			}
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public void attitudeThis(Context context) {
		try {
			new Target_Attitude_Mode(this).publishWork(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void watchThis(Context context) {
		try {
			User_Bean tUser = new User_Bean();
			tUser.readData(context);
			JSONArray members = new JSONArray();
			members.put((int) tUser.getUser_id());
			watchThis(context, members);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void watchThis(Context context, JSONArray members) {
		try {
			new TargetMember_Watch_Mode(this, members, OperatingState.Join).publishWork(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void unWatchThis(Context context) {
		try {
			User_Bean tUser = new User_Bean();
			tUser.readData(context);
			JSONArray members = new JSONArray();
			members.put((int) tUser.getUser_id());
			unWatchThis(context, members);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void unWatchThis(Context context, JSONArray members) {
		try {
			new TargetMember_Watch_Mode(this, members, OperatingState.SignOut).publishWork(context);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
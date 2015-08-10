package com.helper.mistletoe.c.service;

import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.CallLog;
import android.provider.ContactsContract;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.google.gson.Gson;
import com.helper.mistletoe.c.task.ActionDuangArrayTask;
import com.helper.mistletoe.c.task.ActionDuangTask;
import com.helper.mistletoe.c.task.CheckVersionUpdateTask;
import com.helper.mistletoe.c.task.CreateGroupTask;
import com.helper.mistletoe.c.task.DeleteGroupTask;
import com.helper.mistletoe.c.task.FindConstantsTask;
import com.helper.mistletoe.c.task.FindCostTagsByTargetIdTask;
import com.helper.mistletoe.c.task.FindGroupMemberTask;
import com.helper.mistletoe.c.task.FindGroupTask;
import com.helper.mistletoe.c.task.FindHelperAndGroupTask;
import com.helper.mistletoe.c.task.FindHelperTask;
import com.helper.mistletoe.c.task.GetHelperByIdTask;
import com.helper.mistletoe.c.task.GetUserByIdTask;
import com.helper.mistletoe.c.task.InviteHelperTask;
import com.helper.mistletoe.c.task.KickDeviceTask;
import com.helper.mistletoe.c.task.UpdataGroupInformationTask;
import com.helper.mistletoe.c.task.UpdataGroupMemberFromaHelperFragmentTask;
import com.helper.mistletoe.c.task.UpdataGroupMemberTask;
import com.helper.mistletoe.c.task.UpdateBlackToHelperTask;
import com.helper.mistletoe.c.task.UpdateHelperMemoNameTask;
import com.helper.mistletoe.c.task.UpdateHelperToBlackTask;
import com.helper.mistletoe.c.task.UpdateNewToHelperTask;
import com.helper.mistletoe.c.task.UpdatePublicHelperTask;
import com.helper.mistletoe.c.task.UpdateRequestToFourHelperTask;
import com.helper.mistletoe.c.task.UpdateRequestToNewHelperTask;
import com.helper.mistletoe.c.task.UpdateRequestToRefuseHelperTask;
import com.helper.mistletoe.c.task.UpdateUserTask;
import com.helper.mistletoe.c.task.UploadContactTask;
import com.helper.mistletoe.c.ui.base.Base_Service;
import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.work.base.inter.WorkAsync;
import com.helper.mistletoe.m.work.be.cloud.Lbs_Location_Event;
import com.helper.mistletoe.m.work.ui.Lbs_LocationOk_Event;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.ThreadPoolUtils_Net;
import com.helper.mistletoe.util.sysconst.Broadcast_Const;

public class ContacterSyncService extends Base_Service {
	private final static int ELAPSE_TIME = 3000;

	private Handler mHandler = null;
	public LocationClient mLocationClient;// 百度定位
	public MyLocationListener mMyLocationListener;// 百度定位监听

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, true, mCallLogObserver);
			getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, mObserver);

			new Thread(new Runnable() {
				public void run() {
					Looper.prepare();
					mHandler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {
							case 0:
								Instruction_Utils.sendInstrustion(getApplicationContext(), Instruction_Utils.UPLOAD_ADDRESSBOOK);
								break;
							default:
								break;
							}
						}
					};
					Looper.loop();
				}
			}).start();

			mLocationClient = new LocationClient(this.getApplicationContext());
			mMyLocationListener = new MyLocationListener();
			mLocationClient.registerLocationListener(mMyLocationListener);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			if (intent != null) {
				String action = intent.getStringExtra(Broadcast_Const.BC_MODE_STATUS);
				if (action != null) {
					if (action.equals(Broadcast_Const.BC_TAG_WORK)) {
						String tData = intent.getExtras().getString("com.helper.mistletoe.bbc.data");
						getWorkFactory().publishWork(tData);
					}
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		try {
			if (intent == null) {

			} else {
				Integer instruction = intent.getIntExtra("instruction", -1);
				switch (instruction) {
				case 1000:// UPLOAD_ADDRESSBOOK 上传通讯录指令
					UploadContactTask uploadContactTask = new UploadContactTask(getApplicationContext());
					uploadContactTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
					break;
				case 1001:// ACTION_DUANG duang Helper的指令
					Integer helper_id = intent.getIntExtra("helper_id", -1);
					ActionDuangTask actionDuangTask = new ActionDuangTask(getApplicationContext());
					actionDuangTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, helper_id);
					break;
				case 1002:// ACTION_DUANG_ARRAY duang Helpers的指令
					Integer target_id = intent.getIntExtra("target_id", -1);
					ActionDuangArrayTask actionDuangArrayTask = new ActionDuangArrayTask(getApplicationContext());
					actionDuangArrayTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, target_id);
					break;
				case 1003:// KICK_DEVICE 踢出设备的指令
					Integer device_id = intent.getIntExtra("device_id", -1);
					KickDeviceTask kickDeviceTask = new KickDeviceTask(getApplicationContext());
					kickDeviceTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, device_id);
					break;
				case 1004:// CHECK_VERSION 检查版本指令
					String source = intent.getStringExtra("source");
					CheckVersionUpdateTask checkVersionUpdateTask = new CheckVersionUpdateTask(getApplicationContext());
					checkVersionUpdateTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, source);
					break;
				case 1005:// SYNCHRONOUS_CONSTANTS 常量的同步指令
					FindConstantsTask findConstantsTask = new FindConstantsTask(getApplicationContext());
					findConstantsTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
					break;
				case 1006:// SYNCHRONOUS_COSTTAG 费用标签的同步指令
					Integer target_id_for_costTag = intent.getIntExtra("target_id", 0);
					FindCostTagsByTargetIdTask findCostTagTask = new FindCostTagsByTargetIdTask(getApplicationContext(),target_id_for_costTag);
					findCostTagTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
					break;
				case 2000:// SYNCHRONOUS_USER 同步user指令
					GetUserByIdTask getUserByIdTask = new GetUserByIdTask(getApplicationContext());
					getUserByIdTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
					break;
				case 2001:// UPDATE_USER 更新user指令
					String user_json = intent.getStringExtra("user");
					User_Bean user = new Gson().fromJson(user_json, User_Bean.class);
					UpdateUserTask updateUserTask = new UpdateUserTask(getApplicationContext(), user);
					updateUserTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
					break;
				case 2002:// UPDATE_USER_PUBLIC 变更公共帮手状态指令
					String public_status = intent.getStringExtra("public_status");
					UpdatePublicHelperTask updatePublicHelperTask = new UpdatePublicHelperTask(getApplicationContext());
					updatePublicHelperTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, public_status);
					break;
				case 3000:// SYNCHRONOUS_HELPER 同步helper指令
					FindHelperTask findHelperTask = new FindHelperTask(getApplicationContext());
					findHelperTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
					break;
				case 3001:// SYNCHRONOUS_HELPER_BY_ID 同步单个Helper的指令
					Integer helper_id_s = intent.getIntExtra("helper_id", -1);
					GetHelperByIdTask getHelperByIdTask = new GetHelperByIdTask(getApplicationContext());
					getHelperByIdTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, helper_id_s);
					break;
				case 3002:// SYNCHRONOUS_HELPER_AND_GROUP 同步helper与group指令
					FindHelperAndGroupTask findHelperAndGroupTask = new FindHelperAndGroupTask(getApplicationContext());
					findHelperAndGroupTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
					break;
				case 3003:// PULL_BACK 拉黑Helper的指令
					Integer helper_id_a = intent.getIntExtra("helper_id", -1);
					UpdateHelperToBlackTask updateHelperToBlackTask = new UpdateHelperToBlackTask(getApplicationContext());
					updateHelperToBlackTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, helper_id_a);
					break;
				case 3004:// REMOVE_BLACKLIST 解除黑名单指令
					Integer helper_id_d = intent.getIntExtra("helper_id", -1);
					UpdateBlackToHelperTask updateBlackToHelperTask = new UpdateBlackToHelperTask(getApplicationContext());
					updateBlackToHelperTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, helper_id_d);
					break;
				case 3005:// TURN_TO_OLD_HELPER 移除新帮手列表，变为旧帮手
					Integer helper_id_w = intent.getIntExtra("helper_id", -1);
					UpdateNewToHelperTask updateNewToHelperTask = new UpdateNewToHelperTask(getApplicationContext());
					updateNewToHelperTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, helper_id_w);
					break;
				case 3006:// AGREE_TO_ADD 同意添加的指令，变为新帮手
					Integer helper_id_t = intent.getIntExtra("helper_id", -1);
					UpdateRequestToNewHelperTask updateRequestToNewHelperTask = new UpdateRequestToNewHelperTask(getApplicationContext());
					updateRequestToNewHelperTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, helper_id_t);
					break;
				case 3007:// REFUSE_TO_ADD 拒绝添加的指令，变为6
					Integer helper_id_h = intent.getIntExtra("helper_id", -1);
					UpdateRequestToRefuseHelperTask updateRequestToRefuseHelperTask = new UpdateRequestToRefuseHelperTask(
							getApplicationContext());
					updateRequestToRefuseHelperTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, helper_id_h);
					break;
				case 3008:// UPDATE_HELPER_MEMONAME 修改HelperMemoName的指令
					Helpers_Sub_Bean helper = intent.getParcelableExtra("helper");
					UpdateHelperMemoNameTask updateHelperMemoNameTask = new UpdateHelperMemoNameTask(getApplicationContext(), helper);
					updateHelperMemoNameTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
					break;
				case 3009:// INVITE_HELPER 添加帮手的指令
					Helpers_Sub_Bean helper_a = intent.getParcelableExtra("helper");
					InviteHelperTask inviteHelperTask = new InviteHelperTask(getApplicationContext(), helper_a);
					inviteHelperTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
					break;
				case 3010:// ADD_HELPER_TO_GROUP 修改group的member的指令
					Group_Bean group_s = intent.getParcelableExtra("group");
					UpdataGroupMemberFromaHelperFragmentTask updataGroupMemberFromaHelperFragmentTask = new UpdataGroupMemberFromaHelperFragmentTask(
							getApplicationContext(), group_s);
					updataGroupMemberFromaHelperFragmentTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
					break;
				case 3011:// ADD_HELPER_TO_GROUP 修改group的member的指令
					Integer helper_id_f = intent.getIntExtra("helper_id", -1);
					UpdateRequestToFourHelperTask updateRequestTofourHelperTask = new UpdateRequestToFourHelperTask(getApplicationContext());
					updateRequestTofourHelperTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, helper_id_f);
					break;
				case 4000:// CREATE_GROUP 创建group的指令
					Group_Bean group = intent.getParcelableExtra("group");
					CreateGroupTask createGroupTask = new CreateGroupTask(getApplicationContext(), group);
					createGroupTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
					break;
				case 4001:// SYNCHRONOUS_GROUP 同步group指令
					FindGroupTask findGroupTask = new FindGroupTask(getApplicationContext());
					findGroupTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
					break;
				case 4002:// DELETE_GROUP 删除组的指令
					Integer group_id_P = intent.getIntExtra("group_id", -1);
					DeleteGroupTask deleteGroupTask = new DeleteGroupTask(getApplicationContext());
					deleteGroupTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, group_id_P);
					break;
				case 4003:// SYNCHRONOUS_GROUP_MEMBER 同步单个group的Member的指令
					Integer group_id = intent.getIntExtra("group_id", -1);
					FindGroupMemberTask findGroupMemberTask = new FindGroupMemberTask(getApplicationContext());
					findGroupMemberTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool, group_id);
					break;
				case 4004:// UPDATE_GROUP_INFORMATION 修改group信息的指令
					Group_Bean group_w = intent.getParcelableExtra("group");
					UpdataGroupInformationTask updataGroupInformationTask = new UpdataGroupInformationTask(getApplicationContext(), group_w);
					updataGroupInformationTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
					break;
				case 4005:// UPDATE_GROUP_MEMBER 修改group成员的指令
					Group_Bean group_d = intent.getParcelableExtra("group");
					UpdataGroupMemberTask updataGroupMemberTask = new UpdataGroupMemberTask(getApplicationContext(), group_d);
					updataGroupMemberTask.executeOnExecutor(ThreadPoolUtils_Net.threadPool);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	public ContentObserver mObserver = new ContentObserver(new Handler()) {

		@Override
		public void onChange(boolean selfChange) {

			mHandler.removeMessages(0);
			mHandler.sendEmptyMessageDelayed(0, ELAPSE_TIME);
		}

	};

	private ContentObserver mCallLogObserver = new ContentObserver(new Handler()) {
		@Override
		public void onChange(boolean selfChange) {
			mHandler.removeMessages(0);
		}

	};

	public void onEventAsync(WorkAsync event) {
		try {
			// 如果是定位任务，把定位对象传入
			if (event instanceof Lbs_Location_Event) {
				Lbs_Location_Event tEvent = (Lbs_Location_Event) event;
				tEvent.setLocationClient(mLocationClient);
			}
			// 执行任务
			event.doWork(getContext());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			try {
				mLocationClient.stop();
				getEventBus().post(new Lbs_LocationOk_Event(location));
			} catch (Exception e) {
				ExceptionHandle.ignoreException(e);
			}
		}

	}

}
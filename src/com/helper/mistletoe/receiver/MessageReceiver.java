package com.helper.mistletoe.receiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.helper.mistletoe.MistletoeApplication;
import com.helper.mistletoe.c.ui.MyDialogActivity;
import com.helper.mistletoe.c.ui.RespondNewDeviceDialogActivity;
import com.helper.mistletoe.c.ui.RespondRemindDialogActivity;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.work.base.Broadcast_Sender;
import com.helper.mistletoe.util.Instruction_Utils;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

public class MessageReceiver extends XGPushBaseReceiver {
	private Intent intent = new Intent(
			"com.helper.mistletoe.activity.UPDATE_LISTVIEW");
	public static final String LogTag = "TPushReceiver";

	private void show(Context context, String text) {
		// Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	// 通知展示
	@Override
	public void onNotifactionShowedResult(Context context,
			XGPushShowedResult notifiShowedRlt) {

		if (context == null || notifiShowedRlt == null) {
			return;
		}
		XGNotification notific = new XGNotification();
		notific.setMsg_id(notifiShowedRlt.getMsgId());
		notific.setTitle(notifiShowedRlt.getTitle());
		notific.setContent(notifiShowedRlt.getContent());
		// notificationActionType==1为Activity，2为url，3为intent
		notific.setNotificationActionType(notifiShowedRlt
				.getNotificationActionType());
		// Activity,url,intent都可以通过getActivity()获得
		notific.setActivity(notifiShowedRlt.getActivity());
		notific.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime()));
		NotificationService.getInstance(context).save(notific);
		context.sendBroadcast(intent);
		show(context, "您有1条新消息, " + "通知被展示 ， " + notifiShowedRlt.toString());
	}

	@Override
	public void onUnregisterResult(Context context, int errorCode) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "反注册成功";
		} else {
			text = "反注册失败" + errorCode;
		}
		Log.d(LogTag, text);
		show(context, text);

	}

	@Override
	public void onSetTagResult(Context context, int errorCode, String tagName) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "\"" + tagName + "\"设置成功";
		} else {
			text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
		}
		Log.d(LogTag, text);
		show(context, text);

	}

	@Override
	public void onDeleteTagResult(Context context, int errorCode, String tagName) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "\"" + tagName + "\"删除成功";
		} else {
			text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
		}
		Log.d(LogTag, text);
		show(context, text);

	}

	// 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
	@Override
	public void onNotifactionClickedResult(Context context,
			XGPushClickedResult message) {
		if (context == null || message == null) {
			return;
		}
		String text = "";
		if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
			// 通知在通知栏被点击啦。。。。。
			// APP自己处理点击的相关动作
			// 这个动作可以在activity的onResume也能监听，请看第3点相关内容
			text = "通知被打开 :" + message;
		} else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
			// 通知被清除啦。。。。
			// APP自己处理通知被清除后的相关动作
			text = "通知被清除 :" + message;
		}
		Toast.makeText(context, "广播接收到通知被点击:" + message.toString(),
				Toast.LENGTH_SHORT).show();
		// 获取自定义key-value
		String customContent = message.getCustomContent();
		if (customContent != null && customContent.length() != 0) {
			try {
				JSONObject obj = new JSONObject(customContent);
				// key1为前台配置的key
				if (!obj.isNull("key")) {
					String value = obj.getString("key");
					Log.d(LogTag, "get custom value:" + value);
				}
				// ...
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// APP自主处理的过程。。。
		Log.d(LogTag, text);
		show(context, text);
	}

	@Override
	public void onRegisterResult(Context context, int errorCode,
			XGPushRegisterResult message) {
		// TODO Auto-generated method stub
		if (context == null || message == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = message + "注册成功";
			// 在这里拿token
			// String token = message.getToken();
		} else {
			text = message + "注册失败，错误码：" + errorCode;
		}
		Log.d(LogTag, text);
		show(context, text);
	}

	// 消息透传
	@Override
	public void onTextMessage(Context context, XGPushTextMessage message) {
		User_Bean user = new User_Bean();
		try {
			user.readData(context);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String text = "收到消息:" + message.toString();
		Log.i("消息命令", "" + text);
		// String title = message.getTitle();
		String content = message.getContent();
		// 获取自定义key-value
		String customContent = message.getCustomContent();
		// Integer ring=message.ge
		if (customContent != null && customContent.length() != 0) {
			try {
				// action_code 指令
				// badge 红点
				// param 参数
				JSONObject obj = new JSONObject(customContent);
				// key1为前台配置的key
				// if (!obj.isNull("key")) {
				// String value = obj.getString("key");
				// Log.d(LogTag, "get custom value:" + value);
				// }
				Integer action_code = obj.getInt("action_code");

				switch (action_code) {
				case 1000:// 个人信息变化
					// 发出同步user的指令
					Instruction_Utils.sendInstrustion(context,
							Instruction_Utils.SYNCHRONOUS_USER);
					break;
				case 1001:// 被踢出
					Intent mIntent = new Intent(
							context.getApplicationContext(),
							MyDialogActivity.class);
					mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(mIntent);
					break;
				case 2000:// 帮手的状态变化：
					// 发送同步helper的指令
					Instruction_Utils.sendInstrustion(context,
							Instruction_Utils.SYNCHRONOUS_HELPER);
					break;
				case 3000:// group新建，组信息变化,member变化
					JSONObject param = obj.getJSONObject("param");
					Integer group_id = param.getInt("group_id");
					// 发送同步单个group的Member的指令
					Instruction_Utils.sendInstrustion(context,
							Instruction_Utils.SYNCHRONOUS_GROUP_MEMBER,
							group_id);
					break;
				case 3001:// 删除组
					JSONObject param_delete = obj.getJSONObject("param");
					Integer group_id_delete = param_delete.getInt("group_id");
					// 发送删除组的指令
					Instruction_Utils.sendInstrustion(context,
							Instruction_Utils.DELETE_GROUP, group_id_delete);
					break;
				case 4000:// 目标变化
					// JSONObject param_target = obj.getJSONObject("param");
					// Integer group_id_target =
					// param_target.getInt("target_id");
					// 发送xx的指令
					Broadcast_Sender.targetChanged_Cloud(context);
					break;
				case 4001:// 目标成员变化
					JSONObject param_targe = obj.getJSONObject("param");
					Integer group_id_targe = param_targe.getInt("target_id");
					// 发送xx的指令
					Broadcast_Sender.targetMemeberChanged_Cloud(context,
							group_id_targe);
					break;
				case 5000:// 目标沟通变化
					JSONObject param_targ = obj.getJSONObject("param");
					Integer group_id_targ = param_targ.getInt("target_id");
					String group_tag_targ = param_targ.getString("target_tag");
					Integer mute = obj.getInt("mute");
					// 发送xx的指令
					Broadcast_Sender.scheduleChanged_Cloud(context,
							group_id_targ);
					if (user.getLoc_AcceptPush() == 1) {// 是否接收推送消息,1需要，0不需要
						if (mute == 0) {// menu该目标是否接收推送消息,0需要，1不需要
							ActivityManager am = (ActivityManager) context
									.getSystemService(Context.ACTIVITY_SERVICE);
							ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
							if (cn.getClassName()
									.equals("com.helper.mistletoe.c.ui.Target_Details_Activity")) {
								if (group_id_targ == MistletoeApplication.targetInterfaceFlags) {
									break;
								}
							}
							MistletoeApplication.noteCount = MistletoeApplication.noteCount + 1;
							MistletoeApplication.targetId = group_id_targ;
							MistletoeApplication.targetTag = group_tag_targ;
							MistletoeApplication app = (MistletoeApplication) context
									.getApplicationContext();
							Intent intent = new Intent();
							intent.putExtra("content", content);
							intent.putExtra("count",MistletoeApplication.noteCount);
							app.pushNotifications(context, intent);
						}
					}
					break;
				case 6000:// duang
					MistletoeApplication.duangCount = MistletoeApplication.duangCount + 1;
					MistletoeApplication apps = (MistletoeApplication) context
							.getApplicationContext();
					Intent s = new Intent();
					s.putExtra("content", content);
					s.putExtra("count", MistletoeApplication.duangCount);
					apps.pushDuangNotifications(context, s);
					// 发出同步user的指令
					Instruction_Utils.sendInstrustion(
							context.getApplicationContext(),
							Instruction_Utils.SYNCHRONOUS_USER);
					break;
				case 6001:// 定时提醒
					JSONObject param_remind = obj.getJSONObject("param");
					Integer sender = param_remind.getInt("sender");
					Intent intent_remind = new Intent(
							context.getApplicationContext(),
							RespondRemindDialogActivity.class);
					intent_remind.putExtra("content", content);
					intent_remind.putExtra("user_id", sender);
					intent_remind.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent_remind);
					break;
				case 7000:// 新设备登录
					Intent intent_new_device = new Intent(
							context.getApplicationContext(),
							RespondNewDeviceDialogActivity.class);
					intent_new_device.putExtra("content", content);
					intent_new_device.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(intent_new_device);
					break;
				default:
					break;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}

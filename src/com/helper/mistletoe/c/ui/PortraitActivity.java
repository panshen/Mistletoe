package com.helper.mistletoe.c.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.base.Base_Activity;
import com.helper.mistletoe.m.pojo.Update_User_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.sp.User_sp;
import com.helper.mistletoe.m.work.base.inter.WorkCallBack_Mode;
import com.helper.mistletoe.m.work.ui.UpdateInfo_LoginSetName_Mode;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.FolderTool_Utils;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.util.UploadUtil;
import com.helper.mistletoe.util.UploadUtil.OnUploadProcessListener;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaBitmapV2;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class PortraitActivity extends Base_Activity implements OnUploadProcessListener {
	// 去上传文件
	protected static final int TO_UPLOAD_FILE = 1;
	// 上传文件响应
	protected static final int UPLOAD_FILE_DONE = 2;
	// 选择文件
	public static final int TO_SELECT_PHOTO = 3;
	// 上传初始化
	private static final int UPLOAD_INIT_PROCESS = 4;
	// 上传中
	private static final int UPLOAD_IN_PROCESS = 5;
	private static String requestURL = "webservicelogin/imgeupload";
	private boolean isBackClicked = false, isEditClicked = false;
	private SnaImageViewV2 head;
	private RelativeLayout back, edit;
	private ProgressDialog progressDialog;
	private String picPath = null;
	private File tempFile;
	private Bitmap photo = null;
	private User_Bean user_bean;
	private Context context;

	private UpdateInfo_LoginSetName_Mode work_UpdateInfo;// Mode：修改用户信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.portrait);
		context = this;
		initView();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setData();
		setlistener();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private void setData() {
		try {
			user_bean = new User_sp(context).read();
			if (head.getImageForShow().getType() == SnaBitmap.PATH_TYPE_NULL) {
				head.setImageForShow("" + user_bean.getAvatar_file_id(), SnaBitmap.NET_NORMAL);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setlistener() {
		if (isBackClicked == false) {
			back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					isBackClicked = true;
					PortraitActivity.this.finish();
					isBackClicked = false;
				}
			});
		}
		if (isEditClicked == false) {
			edit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					isEditClicked = true;
					ShowPickDialog();
					isEditClicked = false;
				}
			});
		}

	}

	protected void ShowPickDialog() {
		new AlertDialog.Builder(this).setTitle("设置头像...").setNegativeButton("相册", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, 1);

			}
		}).setPositiveButton("拍照", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// 下面这句指定调用相机拍照后的照片存储的路径
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "helperHead.jpg")));
				startActivityForResult(intent, 2);
			}
		}).show();
	}

	private void initView() {
		back = (RelativeLayout) findViewById(R.id.portrait_relativeLayout_back);
		edit = (RelativeLayout) findViewById(R.id.portrait_relativeLayout_edit);
		head = (SnaImageViewV2) findViewById(R.id.portrait_imageView_head);
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 如果是直接从相册获取
		case 1:
			switch (resultCode) {
			case RESULT_OK:
				if (data != null) {
					startPhotoZoom(data.getData());
				}
				break;
			}
			break;
		// 如果是调用相机拍照时
		case 2:
			switch (resultCode) {
			case RESULT_OK:
				File temp = new File(Environment.getExternalStorageDirectory() + "/helperHead.jpg");
				startPhotoZoom(Uri.fromFile(temp));
				break;
			}
			break;
		// 取得裁剪后的图片
		case 3:
			if (data != null) {
				try {
					picPath = tempFile.getAbsolutePath();
					File t_file = new File(picPath);
					head.setImageForShow(t_file);
					task_updateInfo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		intent.putExtra("return-data", true);
		tempFile = new File(FolderTool_Utils.getAFE_UploadHead() + "/CaiJian" + TimeTool_Utils.getNowTime() + ".jpg");// 以时间秒为文件名
		intent.putExtra("output", Uri.fromFile(tempFile)); // 专入目标文件
		intent.putExtra("outputFormat", "JPEG");// 输入文件格式
		startActivityForResult(intent, 3);
	}

	@Override
	public void onUploadDone(int responseCode, String message) {
		progressDialog.dismiss();
		Message msg = Message.obtain();
		msg.what = UPLOAD_FILE_DONE;
		msg.arg1 = responseCode;
		msg.obj = message;
		handler.sendMessage(msg);
	}

	private void toUploadFile() {
		progressDialog.setMessage("正在上传文件...");
		progressDialog.show();
		String fileKey = "img";
		UploadUtil uploadUtil = UploadUtil.getInstance();
		uploadUtil.setOnUploadProcessListener(this); // 设置监听器监听上传状态״̬
		Map<String, String> params = new HashMap<String, String>();
		// params.put("phoneNumber", user.getPhoneNumber());
		// params.put("sessionCode", user.getSessionCode());
		uploadUtil.uploadFile(picPath, fileKey, requestURL, params);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TO_UPLOAD_FILE:
				toUploadFile();
				break;
			case UPLOAD_FILE_DONE:
				// try {
				// // helperGetImage_Task = new HelperGetImage_Task(
				// // PortraitActivity.this, PortraitActivity.this);
				// // user.setPicId(helperGetImage_Task
				// // .executeOnExecutor(ThreadPoolUtils.threadPool,
				// // HelperGetImage_Task.FLAG_BIG,
				// // user.getPhoneNumber(), "user").get()
				// // .getFileName());
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// } catch (ExecutionException e) {
				// e.printStackTrace();
				// }
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	public void onUploadProcess(int uploadSize) {
		Message msg = Message.obtain();
		msg.what = UPLOAD_IN_PROCESS;
		msg.arg1 = uploadSize;
		handler.sendMessage(msg);
	}

	@Override
	public void initUpload(int fileSize) {
		Message msg = Message.obtain();
		msg.what = UPLOAD_INIT_PROCESS;
		msg.arg1 = fileSize;
		handler.sendMessage(msg);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (photo != null && !photo.isRecycled()) {
			photo.recycle();
			photo = null;
		}
		// getImageofHelper_Task=null;
		// helperGetImage_Task=null;
	}

	/**
	 * Mode：修改用户信息
	 * 
	 * @throws Exception
	 *             未知异常
	 */
	private void task_updateInfo() throws Exception {
		try {
			String t_Name = user_bean.getName();
			SnaBitmap t_Head = new SnaBitmapV2();
			t_Head.setPath(new File(tempFile.getPath()));

			boolean workCanCom = true;
			if (workCanCom) {
				work_UpdateInfo = new UpdateInfo_LoginSetName_Mode(new WorkCallBack_Mode() {

					@Override
					public void WorkCallBack() throws Exception {
						try {
							task_updateInfo_Cbk();
						} catch (Exception e) {
							ExceptionHandle.unInterestException(e);
						}
					}
				}, getApplicationContext(), t_Name, t_Head);
				work_UpdateInfo.execute("");
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void task_updateInfo_Cbk() throws Exception {
		try {
			// 获取任务执行结果
			Update_User_Bean t_Up = work_UpdateInfo.getResponse();

			user_bean = new User_sp(context).read();
			if (t_Up.getLoc_NetRes().getResult().equals("0")) {
				head.setImageForShow("" + user_bean.getAvatar_file_id(), SnaBitmap.NET_NORMAL);
			} else {
				head.setImageForShow(tempFile);
				showToast("头像修改失败");
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

}

package com.helper.mistletoe.c.ui;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.base.Base_Activity;
import com.helper.mistletoe.m.pojo.Update_User_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.work.base.inter.WorkCallBack_Mode;
import com.helper.mistletoe.m.work.ui.GetUserInfo_LoginSetName_Mode;
import com.helper.mistletoe.m.work.ui.UpdateInfo_LoginSetName_Mode;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.FolderTool_Utils;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

/**
 * Created by stemp1234 on 15/3/11.
 */
public class Login_InitInfo_Activity extends Base_Activity {
	private ImageView vBack;// 控件：后退按钮
	private EditText vInName;// 控件：用户名字
	private Button vUpdateInfo;// 控件：提交按键
	private SnaImageViewV2 vUserHead;// 控件：用户头像
	private ImageView vChooseImage;// 控件：选择一张照片

	private UpdateInfo_LoginSetName_Mode work_UpdateInfo;// Mode：修改用户信息
	private GetUserInfo_LoginSetName_Mode work_GetUserInfo;// Mode：获取用户信息
	private File mUploadFile;// 控件：选择一张照片

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.login_initinfo_layout);
			initView();
			setListener();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setData() throws Exception {
		try {
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void initView() throws Exception {
		try {
			vBack = (ImageView) findViewById(R.id.sn_back);
			vInName = (EditText) findViewById(R.id.sn_inName);
			vUpdateInfo = (Button) findViewById(R.id.sn_updateInfo);
			vUserHead = (SnaImageViewV2) findViewById(R.id.sn_userHead);
			vChooseImage = (ImageView) findViewById(R.id.sn_chooseImage);

			task_getUserInfo();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void setListener() throws Exception {
		try {
			vBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						finish();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vUpdateInfo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						task_updateInfo();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vChooseImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(Intent.ACTION_PICK, null);
						intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
						startActivityForResult(intent, 777 + 1);
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (requestCode == 777 + 1) {
				switch (resultCode) {
				case RESULT_OK:
					Intent intent = new Intent("com.android.camera.action.CROP");
					intent.setDataAndType(data.getData(), "image/*");
					// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
					intent.putExtra("crop", "true");
					// aspectX aspectY 是宽高的比例
					intent.putExtra("aspectX", 1);
					intent.putExtra("aspectY", 1);
					// outputX outputY 是裁剪图片宽高
					intent.putExtra("outputX", 1280);
					intent.putExtra("outputY", 1280);
					intent.putExtra("return-data", true);
					mUploadFile = new File(FolderTool_Utils.getAFE_ImageZoom() + "/CaiJian" + TimeTool_Utils.getNowTime() + ".jpg");// 以时间秒为文件名
					intent.putExtra("output", Uri.fromFile(mUploadFile)); // 专入目标文件
					intent.putExtra("outputFormat", "JPEG");// 输入文件格式
					startActivityForResult(intent, 777 + 2);
					break;
				}
			}
			if (requestCode == 777 + 2) {
				switch (resultCode) {
				case RESULT_OK:
					vUserHead.setImageForShow(mUploadFile);
					break;
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Mode：修改用户信息
	 * 
	 * @throws Exception
	 *             未知异常
	 */
	private void task_updateInfo() throws Exception {
		try {
			boolean isName=isHaveName();
			if (isName) {
				work_UpdateInfo = new UpdateInfo_LoginSetName_Mode(new WorkCallBack_Mode() {

					@Override
					public void WorkCallBack() throws Exception {
						try {
							task_updateInfo_Cbk();
						} catch (Exception e) {
							ExceptionHandle.unInterestException(e);
						}
					}
				}, getApplicationContext(), vInName.getText().toString(), vUserHead.getImageForShow());
				work_UpdateInfo.execute("");
				vUpdateInfo.setText("正在更新");
				vUpdateInfo.setClickable(false);
			}else {
				showToast("请输入你的名字");
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private boolean isHaveName() {
		// TODO Auto-generated method stub
		Boolean isHaveName=true;
		User_Bean user=new User_Bean();
		try {
			user.readData(getContext());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (vInName.getText().toString().trim().equals("")) {
			isHaveName=false;
		}else if (vInName.getText().toString().trim().contains(""+user.getUser_id())) {
			isHaveName=false;
		}else if (vInName.getText().toString().trim().equals("用户["+user.getUser_id()+"]")) {
			isHaveName=false;
		}
		return isHaveName;
	}

	private void task_updateInfo_Cbk() throws Exception {
		try {
			// 获取任务执行结果
			Update_User_Bean backBean = work_UpdateInfo.getResponse();
			// 处理结果
			vUpdateInfo.setText("更新信息");
			vUpdateInfo.setClickable(true);
			if (backBean.getLoc_NetRes().getResult().equals("0")) {
				// 进入主页
				opac_MainPage();
			} else {
				if (backBean.getLoc_NetRes().getResult().equals("-1")) {
					showToast("修改失败");
				} else {
					showToast(backBean.getLoc_NetRes().getResult_msg());
				}
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	/**
	 * Mode：获取用户信息
	 * 
	 * @throws Exception
	 *             未知异常
	 */
	private void task_getUserInfo() throws Exception {
		try {
			boolean workCanCom = true;
			if (workCanCom) {
				work_GetUserInfo = new GetUserInfo_LoginSetName_Mode(new WorkCallBack_Mode() {

					@Override
					public void WorkCallBack() throws Exception {
						try {
							task_getUserInfo_Cbk();
						} catch (Exception e) {
							ExceptionHandle.unInterestException(e);
						}
					}
				}, getApplicationContext());
				work_GetUserInfo.execute("");
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void task_getUserInfo_Cbk() throws Exception {
		try {
			// 获取任务执行结果
			User_Bean backBean = work_GetUserInfo.getResponse();
			if (backBean != null) {
				vUserHead.setImageForShow("" + backBean.getAvatar_file_id(), SnaBitmap.NET_NORMAL);
				boolean isName=isHaveName();
				if (isName) {
					vInName.setText(backBean.getName());
				}else {
					vInName.setHint("请输入你的名字");
				}
				
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	/**
	 * 跳转：到主页
	 * 
	 * @throws Exception
	 *             未知异常
	 */
	private void opac_MainPage() throws Exception {
		try {
			startActivity(new Intent(this, UploadAddressBookActivity.class));
			finish();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

}

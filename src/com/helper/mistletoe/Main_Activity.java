package com.helper.mistletoe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;

import com.helper.mistletoe.c.ui.Login_Activity;
import com.helper.mistletoe.c.ui.MainFragmentActivity;
import com.helper.mistletoe.c.ui.base.Base_Activity;
import com.helper.mistletoe.m.work.base.inter.WorkCallBack_Mode;
import com.helper.mistletoe.m.work.ui.GetUser_Main_Mode;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.v.welcome.OnWelcomePageOK;
import com.helper.mistletoe.v.welcome.WelcomePage;

public class Main_Activity extends Base_Activity {
	private ImageView vHomePage;// 控件：普通的进入页面
	private WelcomePage vWelcome;// 控件：每一次更新之后的欢迎页
	private GetUser_Main_Mode work_GetUser;// Mode：获取用户信息
	private boolean m_isLoggined;// 数据：用户是否第一次登陆
	private boolean m_isFirstUse;// 数据：用户是否更新后第一次使用程序
	private Message m;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.main_layout);
			initView();
			setListener();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
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
	}

	protected void setData() {
		try {
			// 设置成普通模式
			type_Normal();
			// 获取User信息
			task_GetUser();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView() {
		try {
			// 寻找控件
			vHomePage = (ImageView) findViewById(R.id.m_HomePage_wowejfowfw);
			vWelcome = (WelcomePage) findViewById(R.id.m_Welcome);
			// 初始化成员变量
			work_GetUser = null;
			m_isFirstUse = false;
			m_isLoggined = false;
			m = null;
			// 从上一个页面取参
			// 其他初始化工作
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setListener() {
		try {
			
			vWelcome.setOnWelcomePageOKListener(new OnWelcomePageOK() {
				@Override
				public void onPageOk() {
					try {
						// 判断用户是否有效
						if (m_isLoggined) {
							// 进入主页
							opac_MainPage();
						} else {
							// 进入登录页
							opac_Login();
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * Mode：获取用户信息
	 * 
	 * 
	 */
	private void task_GetUser() {
		try {
			boolean workCanCom = true;
			if (workCanCom) {
				work_GetUser = new GetUser_Main_Mode(new WorkCallBack_Mode() {

					@Override
					public void WorkCallBack() {
						try {
							task_GetUser_Cbk();
						} catch (Exception e) {
							ExceptionHandle.ignoreException(e);
						}
					}
				}, getApplicationContext());
				work_GetUser.execute("");
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void task_GetUser_Cbk() {
		try {
			// 获取任务执行结果
			m_isFirstUse = work_GetUser.isResponse_isFirstUse();
			m_isLoggined = work_GetUser.isResponse_isLoggined();
			// 判断是否更新后第一次打开
			if (m_isFirstUse) {
				// 显示欢迎页
				type_Welcome();
			} else {
				// 显示正常页面
				type_Normal();
				// 判断用户是否有效
				if (m_isLoggined) {
					// 进入主页
					opac_MainPage();
				} else {
					// 进入登录页
					opac_Login();
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 模式：更新后第一次打开App
	 */
	private void type_Welcome() {
		try {
			vHomePage.setVisibility(ImageView.GONE);
			vWelcome.setVisibility(WelcomePage.VISIBLE);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 模式：正常使用
	 */
	private void type_Normal() {
		try {
			vHomePage.setVisibility(ImageView.VISIBLE);
			vWelcome.setVisibility(WelcomePage.GONE);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 跳转：到主页
	 */
	private void opac_MainPage() {
		try {
			startActivity(new Intent(Main_Activity.this,
					MainFragmentActivity.class));
			finish();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 跳转：到登录页面
	 */
	private void opac_Login() {
		try {
			startActivity(new Intent(Main_Activity.this, Login_Activity.class));
			finish();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}

package com.helper.mistletoe.c.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.base.Base_Activity;
import com.helper.mistletoe.m.pojo.Login_User_Bean;
import com.helper.mistletoe.m.pojo.Register_User_Bean;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.m.work.base.inter.WorkCallBack_Mode;
import com.helper.mistletoe.m.work.ui.Login_Login_Mode;
import com.helper.mistletoe.m.work.ui.Verification_Login_Mode;
import com.helper.mistletoe.util.ExceptionHandle;

/**
 * Created by stemp1234 on 15/3/11.
 */
public class Login_Activity extends Base_Activity {
	public final static int ACCOUNT_TYPE_PHONENUMBER = 0;// Account_Type：phonenumber
	public final static int ACCOUNT_TYPE_EMAIL = 1;// Account_Type：email

	private Button vLogin;// 控件：登录按钮
	private Button vGetPassWord;// 控件：获取验证码按钮
	private EditText vInPassWord;// 控件：输入验证码
	private EditText vInAccount;// 控件：输入登录用户
	private TextView vShowMessage;// 控件：显示提示
	private TextView vReadServerClause;// 控件：服务条款
	private CheckBox vAgreeServerClause;// 控件：用户是否同意服务条款
	private TextView vTxtNowAccountType;// 控件：现在的登录方式
	private TextView vTxtAnotherAccountType;// 控件：换用另一种登录方式
	private TextView vAreaCode;// 控件：区号
	private ProgressBar vProgressBar;// 控件：进度条

	private Verification_Login_Mode work_Verification;// Mode：获取验证码
	private Login_Login_Mode work_Login;// Mode：登录
	private int m_accountType;// 数据：登录类型
	private String m_account_PhoneNumber;// 数据：用户名_电话号码模式
	private String m_account_eMail;// 数据：用户名_邮箱模式
	private String m_Verification_PhoneNumber;// 数据：验证码_电话号码模式
	private String m_Verification_eMail;// 数据：验证码_邮箱模式
	private String m_showMessage_PhoneNumber;// 数据：提示信息_电话号码模式
	private String m_showMessage_eMail;// 数据：提示信息_邮箱模式
	private int mDeviceId;// 数据：设备Id
	private boolean mCanGetPassword;// 数据：是否能获取验证码
	private Handler mHandler;// 数据：句柄

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.login_layout);
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

	protected void setData() {
		try {
			uds_ShowMessege();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView() {
		try {
			vLogin = (Button) findViewById(R.id.lg_login);
			vGetPassWord = (Button) findViewById(R.id.lg_btnGetPassWord);
			vInPassWord = (EditText) findViewById(R.id.lg_passWord);
			vInAccount = (EditText) findViewById(R.id.lg_inAccount);
			vShowMessage = (TextView) findViewById(R.id.lg_texHint);
			vReadServerClause = (TextView) findViewById(R.id.lg_Clause);
			vAgreeServerClause = (CheckBox) findViewById(R.id.lg_checkServerClause);
			vTxtNowAccountType = (TextView) findViewById(R.id.lg_texPhoneNumber);
			vTxtAnotherAccountType = (TextView) findViewById(R.id.lg_texEmail);
			vAreaCode = (TextView) findViewById(R.id.lg_AreaCode);
			vProgressBar = (ProgressBar) findViewById(R.id.lg_ProgressBar);

			work_Verification = null;
			work_Login = null;
			m_accountType = ACCOUNT_TYPE_PHONENUMBER;
			m_account_PhoneNumber = "";
			m_account_eMail = "";
			m_Verification_PhoneNumber = "";
			m_Verification_eMail = "";
			m_showMessage_eMail = "";
			m_showMessage_PhoneNumber = "";
			mDeviceId = -1;
			mCanGetPassword = true;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setListener() {
		try {
			vTxtAnotherAccountType
					.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							try {
								if (getd_AccountType() == ACCOUNT_TYPE_PHONENUMBER) {
									m_accountType = ACCOUNT_TYPE_EMAIL;
								} else if (getd_AccountType() == ACCOUNT_TYPE_EMAIL) {
									m_accountType = ACCOUNT_TYPE_PHONENUMBER;
								}
								uds_AccountType();
								type_UnInPut();
							} catch (Exception e) {
								ExceptionHandle.ignoreException(e);
							}
						}
					});
			vGetPassWord.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						if (mCanGetPassword) {
							task_getVerification();
							type_UnInPut();
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vLogin.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						task_doLogin();
						type_UnInPut();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			vInPassWord.addTextChangedListener(new TextWatcher() {
				@Override
				public void afterTextChanged(Editable s) {
					try {
						setd_Verification(vInPassWord.getText().toString());
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					try {
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					try {
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}

			});
			vInAccount.addTextChangedListener(new TextWatcher() {
				@Override
				public void afterTextChanged(Editable s) {
					try {
						setd_Account(vInAccount.getText().toString());
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					try {
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					try {
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
	 * Mode：获取验证码
	 * 
	 * 
	 */
	private void task_getVerification() {
		try {
			boolean workCanCom = true;
			if (getd_Account().equals("")) {
				workCanCom = false;
			}
			if ((work_Verification != null)
					&& (!work_Verification.getStatus().equals(Status.FINISHED))) {
				workCanCom = false;
			}
			if (workCanCom) {
				work_Verification = new Verification_Login_Mode(
						new WorkCallBack_Mode() {

							@Override
							public void WorkCallBack() {
								try {
									task_getVerification_Cbk();
								} catch (Exception e) {
									ExceptionHandle.ignoreException(e);
								}
							}
						}, getApplicationContext(),
						String.valueOf(getd_AccountType()), getd_Account());
				work_Verification.execute("");
				vProgressBar.setVisibility(ProgressBar.VISIBLE);
				uds_LockGetPassword();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void uds_LockGetPassword() {
		try {
			mCanGetPassword = false;
			new CountDownTimer(60 * 1000, 1 * 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
					try {
						vGetPassWord.setTextColor(getResources().getColor(
								R.color.grey));
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}

				@Override
				public void onFinish() {
					try {
						mCanGetPassword = true;
						vGetPassWord.setTextColor(getResources().getColor(
								R.color.temp_MistletoeMainColor));
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			}.start();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void task_getVerification_Cbk() {
		try {
			vProgressBar.setVisibility(ProgressBar.INVISIBLE);
			// 获取任务执行结果
			Register_User_Bean backBean = work_Verification.getResponse();
			if (backBean.getLoc_NetRes().getResult().equals("0")) {
				// 把信息提示给用户
				setd_showMessage(backBean.getHint());
				mDeviceId = backBean.getDevice_id();
			} else {
				// 把信息提示给用户
				setd_showMessage(backBean.getLoc_NetRes().getResult_msg());
			}
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * Mode：登录
	 * 
	 * 
	 */
	private void task_doLogin() {
		try {
			boolean workCanCom = true;
			if (getd_Account().equals("")) {
				workCanCom = false;
			}
			if (getd_Verification().equals("")) {
				workCanCom = false;
			}
			if (!vAgreeServerClause.isChecked()) {
				workCanCom = false;
			}
			if ((work_Login != null)
					&& (!work_Login.getStatus().equals(Status.FINISHED))) {
				workCanCom = false;
			}
			if (workCanCom) {
				work_Login = new Login_Login_Mode(new WorkCallBack_Mode() {

					@Override
					public void WorkCallBack() {
						try {
							task_doLogin_Cbk();
						} catch (Exception e) {
							ExceptionHandle.ignoreException(e);
						}
					}
				}, getApplicationContext(), String.valueOf(getd_AccountType()),
						getd_Account(), getd_Verification(), mDeviceId);
				work_Login.execute("");
				vProgressBar.setVisibility(ProgressBar.VISIBLE);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void task_doLogin_Cbk() {
		try {
			vProgressBar.setVisibility(ProgressBar.INVISIBLE);
			// 获取任务执行结果
			Login_User_Bean backBean = work_Login.getResponse();
			if (backBean.getLoc_NetRes().getResult().equals("0")) {
				User_Bean tUser = new User_Bean();
				tUser.readData(getApplicationContext());
				// 判断有没有用户名
				if (tUser.isEffective()) {
					// 进入主页
					opac_MainPage();
				} else {
					opac_SetName();
				}
			} else {
				setd_showMessage(backBean.getLoc_NetRes().getResult_msg());
			}
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 更新显示：提示信息
	 */
	private void uds_ShowMessege() {
		try {
			vShowMessage.setText(getd_showMessage());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 更新显示：输入模式，邮箱还是手机号
	 */
	private void uds_AccountType() {
		try {
			if (getd_AccountType() == ACCOUNT_TYPE_PHONENUMBER) {
				type_AccountType_PhoneNumber();
			}
			if (getd_AccountType() == ACCOUNT_TYPE_EMAIL) {
				type_AccountType_eMail();
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 模式：手机号
	 */
	private void type_AccountType_PhoneNumber() {

		try {
			vTxtNowAccountType.setText("手机号码验证");
			vTxtAnotherAccountType.setText("或邮箱验证");
			vInAccount.setHint("请输入您的手机号码");
			vInAccount.setInputType(InputType.TYPE_CLASS_PHONE);
			vAreaCode.setVisibility(TextView.VISIBLE);
			vInAccount.setText(getd_Account());
			vShowMessage.setText(getd_showMessage());
			vInPassWord.setText(getd_Verification());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 模式：邮箱
	 */
	private void type_AccountType_eMail() {

		try {
			vTxtNowAccountType.setText("邮箱验证");
			vTxtAnotherAccountType.setText("或手机号码验证");
			vInAccount.setHint("请输入您的邮箱");
			vInAccount
					.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			vAreaCode.setVisibility(TextView.GONE);
			vInAccount.setText(getd_Account());
			vShowMessage.setText(getd_showMessage());
			vInPassWord.setText(getd_Verification());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 模式：输入模式
	 */
	private void type_InPut() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 模式：非输入模式
	 */
	private void type_UnInPut() {
		try {
			hideKeyboard();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 内容Get：AccountType
	 */
	private int getd_AccountType() {
		int result = 0;
		try {
			if (m_accountType == ACCOUNT_TYPE_PHONENUMBER) {
				result = ACCOUNT_TYPE_PHONENUMBER;
			} else if (m_accountType == ACCOUNT_TYPE_EMAIL) {

				result = ACCOUNT_TYPE_EMAIL;
			} else {
				result = ACCOUNT_TYPE_PHONENUMBER;
			}
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 内容Set：AccountType
	 */
	private void setd_Account(String account) {
		try {
			if (getd_AccountType() == ACCOUNT_TYPE_PHONENUMBER) {
				this.m_account_PhoneNumber = account;
			}
			if (getd_AccountType() == ACCOUNT_TYPE_EMAIL) {
				this.m_account_eMail = account;
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 内容Get：Account
	 */
	private String getd_Account() {
		String result = "";
		try {
			if (getd_AccountType() == ACCOUNT_TYPE_PHONENUMBER) {
				result = m_account_PhoneNumber;
			}
			if (getd_AccountType() == ACCOUNT_TYPE_EMAIL) {
				result = m_account_eMail;
			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 内容Set：提示给用户的信息
	 */
	private void setd_showMessage(String account) {
		try {
			if (getd_AccountType() == ACCOUNT_TYPE_PHONENUMBER) {
				this.m_showMessage_PhoneNumber = account;
			}
			if (getd_AccountType() == ACCOUNT_TYPE_EMAIL) {
				this.m_showMessage_eMail = account;

			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 内容Get：提示给用户的信息
	 */
	private String getd_showMessage() {
		String result = "";
		try {
			if (getd_AccountType() == ACCOUNT_TYPE_PHONENUMBER) {
				result = m_showMessage_PhoneNumber;
			}
			if (getd_AccountType() == ACCOUNT_TYPE_EMAIL) {
				result = m_showMessage_eMail;

			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 内容Set：验证码
	 */
	private void setd_Verification(String verification) {
		try {
			if (getd_AccountType() == ACCOUNT_TYPE_PHONENUMBER) {
				this.m_Verification_PhoneNumber = verification;
			}
			if (getd_AccountType() == ACCOUNT_TYPE_EMAIL) {
				this.m_Verification_eMail = verification;

			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 内容Get：验证码
	 */
	private String getd_Verification() {
		String result = "";
		try {
			if (getd_AccountType() == ACCOUNT_TYPE_PHONENUMBER) {
				result = m_Verification_PhoneNumber;
			}
			if (getd_AccountType() == ACCOUNT_TYPE_EMAIL) {
				result = m_Verification_eMail;

			}
		} catch (Exception e) {
			result = "";
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 跳转：到主页
	 */
	private void opac_MainPage() {
		try {
			Intent i = new Intent(this, MainFragmentActivity.class);
			startActivity(i);
			finish();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 跳转：到补充信息（填写姓名，上传头像）页面
	 */
	private void opac_SetName() {
		try {
			startActivity(new Intent(this, Login_InitInfo_Activity.class));
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 隐藏屏幕键盘
	 */
	private void hideKeyboard() {
		try {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
			if (isOpen) {
				View focusView = this.getCurrentFocus();
				if (focusView != null) {
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(
									focusView.getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);

		}
	}

}

package com.helper.mistletoe.c.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.User_Bean;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.SnaImageView;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class PersonalEditActivity extends PrivateBasicActivity {

	private LinearLayout back, edit;
	private SnaImageView head;
	private TextView comment_tv, name_tv;
	private EditText name_et, comment_et, email_et, company_et, address_et, title_et, number_et;
	private User_Bean user;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_edit);
		initView();
		setData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setlistener();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private void setData() {
		try {
			user.readData(context);
			if (head.getImageForShow().getType() == SnaBitmap.PATH_TYPE_NULL) {
				head.setImageForShow(user.getAvatar_file_id(), SnaBitmap.NET_SMALL);
			}
			name_tv.setText(user.getName().toString());
			comment_tv.setText(user.getSign().toString());
			name_et.setText(user.getName().toString());
			comment_et.setText(user.getSign().toString());
			number_et.setText(user.getMobile().toString());
			email_et.setText(user.getEmail().toString());
			company_et.setText(user.getCompany().toString());
			title_et.setText(user.getTitle().toString());
			address_et.setText(user.getAddress().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private User_Bean captureData(User_Bean user) {
		try {
			user.setName(name_et.getText().toString());
			user.setSign(comment_et.getText().toString());
			user.setMobile(number_et.getText().toString());
			user.setEmail(email_et.getText().toString());
			user.setCompany(company_et.getText().toString());
			user.setTitle(title_et.getText().toString());
			user.setAddress(address_et.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	};

	private void setlistener() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PersonalEditActivity.this.finish();
			}
		});
		edit.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				User_Bean newUser = captureData(user);
				Instruction_Utils.sendInstrustion(context, Instruction_Utils.UPDATE_USER, newUser);
			}
		});
	}

	private void initView() {
		context = this;
		user = new User_Bean();
		back = (LinearLayout) findViewById(R.id.personal_edit_linearLayout_back);
		edit = (LinearLayout) findViewById(R.id.personal_edit_linearLayout_determine);
		head = (SnaImageViewV2) findViewById(R.id.personal_edit_imageView_head);
		name_tv = (TextView) findViewById(R.id.personal_edit_textView_name);
		comment_tv = (TextView) findViewById(R.id.personal_edit_textView_comment);
		name_et = (EditText) findViewById(R.id.personal_edit_editText_name);
		comment_et = (EditText) findViewById(R.id.personal_edit_editText_comment);
		number_et = (EditText) findViewById(R.id.personal_edit_editText_moilbe);
		email_et = (EditText) findViewById(R.id.personal_edit_editText_email);
		company_et = (EditText) findViewById(R.id.personal_edit_editText_company);
		title_et = (EditText) findViewById(R.id.personal_edit_editText_title);
		address_et = (EditText) findViewById(R.id.personal_edit_editText_address);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void dealWithRadio(Intent intent) {
		super.dealWithRadio(intent);
		String action = intent.getAction();
		if (action.equals(MessageConstants.REFRESH_USER)) {
			PersonalEditActivity.this.finish();
		}
	}
}

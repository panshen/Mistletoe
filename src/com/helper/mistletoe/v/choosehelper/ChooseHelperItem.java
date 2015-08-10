package com.helper.mistletoe.v.choosehelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.v.snaimageview.SnaImageView;

public class ChooseHelperItem extends LinearLayout {
	private SnaImageView vImage_Lin;
	private TextView vProgressBar_Lin;

	private Context context;
	private LayoutInflater inflater;
	private TargetMember_Bean helper;

	public ChooseHelperItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		try {
			this.context = context;
			this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.inflater.inflate(R.layout.choosehelper_item_detail, this);
			setView();
			setData();
			setListener();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void setView() throws Exception {
		try {
			vImage_Lin = (SnaImageView) findViewById(R.id.wefwfwfwfwefwfwefssssefsssee);
			vProgressBar_Lin = (TextView) findViewById(R.id.wefwfwfwfwefwfwefssssefxxeefeew);

			this.helper = new TargetMember_Bean();

			vImage_Lin.setDefaultImageForShow(R.drawable.default_head);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void setData() throws Exception {
		try {
			vImage_Lin.setImageForShow(getHelper().getShowSnaBitmap());
			vProgressBar_Lin.setText(getHelper().getShowName());
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void setListener() throws Exception {
		try {
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void setHelper(TargetMember_Bean helper) throws Exception {
		try {
			this.helper = helper;
			setData();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public TargetMember_Bean getHelper() throws Exception {
		TargetMember_Bean result = null;
		try {
			if (helper == null) {
				helper = new TargetMember_Bean();
			}
			result = helper;
		} catch (Exception e) {
			result = null;
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}
}
package com.helper.mistletoe.v.welcome;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.util.ExceptionHandle;

public class ProgressDot extends LinearLayout {
	private ArrayList<TextView> vDot;

	private LayoutInflater inflater;

	public ProgressDot(Context context, AttributeSet attrs) {
		super(context, attrs);
		try {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(R.layout.welcompage_progressdot_layout, this);
			setView();
			setData();
			setListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setView() throws Exception {
		try {
			vDot = new ArrayList<TextView>();
			TextView temp = (TextView) findViewById(R.id.zDot_01);
			vDot.add(temp);
			TextView temp1 = (TextView) findViewById(R.id.zDot_02);
			vDot.add(temp1);
			TextView temp2 = (TextView) findViewById(R.id.zDot_03);
			vDot.add(temp2);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void setData() throws Exception {
		try {
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void setListener() throws Exception {
		try {
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void selectDot(int dot) throws Exception {
		try {
			selectDot(false, false, false);
			setMode(dot, true);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void selectDot(boolean dot1, boolean dot2, boolean dot3) throws Exception {
		try {
			setMode(0, dot1);
			setMode(1, dot2);
			setMode(2, dot3);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void setMode(int dot, boolean select) throws Exception {
		try {
			if (select) {
				setMode_select(dot);
			} else {
				setMode_none(dot);
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void setMode_select(int dot) throws Exception {
		try {
			vDot.get(dot).setBackgroundResource(R.drawable.welcompage_spd_selectd);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	private void setMode_none(int dot) throws Exception {
		try {
			vDot.get(dot).setBackgroundResource(R.drawable.welcompage_spd_noselect);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

}
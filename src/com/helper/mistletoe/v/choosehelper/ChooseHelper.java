package com.helper.mistletoe.v.choosehelper;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.util.ExceptionHandle;

public class ChooseHelper extends LinearLayout {
	private LinearLayout vBaseBody;
	private ChooseHelperItem vCreater;
	private ChooseHelperItem vHelper01;
	private ChooseHelperItem vHelper02;
	private ChooseHelperItem vHelper03;

	private Context context;
	private LayoutInflater inflater;
	private TargetMember_Bean creater;
	private ArrayList<TargetMember_Bean> helper;

	public ChooseHelper(Context context, AttributeSet attrs) {
		super(context, attrs);
		try {
			this.context = context;
			this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.inflater.inflate(R.layout.choosehelper_schedule_detail, this);
			setView();
			setData();
			setListener();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void setView() {
		try {
			vBaseBody = (LinearLayout) findViewById(R.id.snaimage_bodyjjg);
			vCreater = (ChooseHelperItem) findViewById(R.id.snaimage_imagedccccdef);
			vHelper01 = (ChooseHelperItem) findViewById(R.id.snaimage_imagedcfwefwcccdef);
			vHelper02 = (ChooseHelperItem) findViewById(R.id.snaimage_imageddfwfeccccdef);
			vHelper03 = (ChooseHelperItem) findViewById(R.id.snaimage_imagedcfewfwcccdefddefewx);

			this.creater = new TargetMember_Bean();
			this.helper = new ArrayList<TargetMember_Bean>();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void setData() {
		try {
			uds_CreaterTitle();
			uds_HelperTitle();
			uds_Creater();
			uds_Helper();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void setListener() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setOnChooseHelperClickListener(OnClickListener onClickListener) {
		try {
			vBaseBody.setOnClickListener(onClickListener);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setOnCreaterClickListener(OnClickListener onClickListener) {
		try {
			vCreater.setOnClickListener(onClickListener);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void uds_CreaterTitle() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void uds_HelperTitle() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void uds_Creater() {
		try {
			vCreater.setHelper(creater);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void uds_Helper() {
		try {
			if (helper.size() > 0) {
				uds_HelperSingleItem(vHelper01, helper.get(0));
			} else {
				uds_HelperSingleItem(vHelper01, null);
			}
			if (helper.size() > 1) {
				uds_HelperSingleItem(vHelper02, helper.get(1));
			} else {
				uds_HelperSingleItem(vHelper02, null);
			}
			if (helper.size() > 2) {
				uds_HelperSingleItem(vHelper03, helper.get(2));
			} else {
				uds_HelperSingleItem(vHelper03, null);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void uds_HelperSingleItem(ChooseHelperItem helperView, TargetMember_Bean helper) {
		try {
			if (helper == null) {
				helperView.setVisibility(ChooseHelperItem.INVISIBLE);
			} else {
				helperView.setVisibility(ChooseHelperItem.VISIBLE);
				helperView.setHelper(helper);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	// 获取创建者
	public TargetMember_Bean getCreater() {
		TargetMember_Bean result = null;
		try {
			if (creater == null) {
				creater = new TargetMember_Bean();
			}
			result = creater;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	// 设置创建者
	public void setCreater(TargetMember_Bean helper) {
		try {
			creater = helper;
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	// 获取（全部）帮手
	public ArrayList<TargetMember_Bean> getHelper() {
		ArrayList<TargetMember_Bean> result = null;
		try {
			if (helper == null) {
				helper = new ArrayList<TargetMember_Bean>();
			}
			result = helper;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	// 增加（单个）帮手
	public void setHelper(TargetMember_Bean helper) {
		try {
			removeHelper();
			getHelper().add(helper);
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	// 增加（多个）帮手
	public void setHelper(Collection<TargetMember_Bean> helper) {
		try {
			removeHelper();
			getHelper().addAll(helper);
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	// 删除（单个）帮手
	public void removeHelper(TargetMember_Bean helperId) {
		try {
			getHelper().remove(helperId);
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	// 删除（多个）帮手
	public void removeHelper(ArrayList<TargetMember_Bean> helper) {
		try {
			getHelper().removeAll(helper);
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	// 删除（全部）帮手
	public void removeHelper() {
		try {
			getHelper().clear();
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * @deprecated
	 */
	public void setOnClickListener(OnClickListener onClickListener) {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}
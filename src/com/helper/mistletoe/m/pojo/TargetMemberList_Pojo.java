package com.helper.mistletoe.m.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.helper.mistletoe.m.sjb.core.SimpleBabelJavaBean;
import com.helper.mistletoe.util.Array_Util;
import com.helper.mistletoe.util.ExceptionHandle;

public class TargetMemberList_Pojo extends SimpleBabelJavaBean {
	public TargetMember_Bean mOwner;
	public ArrayList<TargetMember_Bean> mAdmin;
	public ArrayList<TargetMember_Bean> mHelper;
	public ArrayList<TargetMember_Bean> mObserver;

	/**
	 * 初始化列表元素
	 * 
	 * @param targetMembers
	 *            目标成员
	 */
	public void init(ArrayList<TargetMember_Bean> targetMembers) {
		try {
			clear();
			addAll(targetMembers);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 向列表中增加元素
	 * 
	 * @param targetMembers
	 *            目标成员
	 */
	private void add(TargetMember_Bean targetMembers) {
		try {
			ArrayList<TargetMember_Bean> tMembersList = new ArrayList<TargetMember_Bean>();
			tMembersList.add(targetMembers);
			addAll(tMembersList);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 向列表中增加元素
	 * 
	 * @param targetMembers
	 *            目标成员
	 */
	private void addAll(ArrayList<TargetMember_Bean> targetMembers) {
		try {
			boolean contentIsSafe = true;
			if (targetMembers == null) {
				contentIsSafe = false;
			}
			if (contentIsSafe) {
				Array_Util.removeArrayListInvalidData(targetMembers, new TargetMember_Bean());
				HashSet<TargetMember_Bean> tSet = new HashSet<TargetMember_Bean>();
				tSet.addAll(targetMembers);
				targetMembers.clear();
				targetMembers.addAll(tSet);
			}
			if (targetMembers.size() < 0) {
				contentIsSafe = false;
			}
			if (contentIsSafe) {
				for (Iterator<TargetMember_Bean> iterator = targetMembers.iterator(); iterator.hasNext();) {
					TargetMember_Bean tTempMember = iterator.next();
					switch (tTempMember.getMember_role()) {
					case Owner:
						setOwner(tTempMember);
						break;
					case Admin:
						getAdmin().add(tTempMember);
						break;
					case Helper:
						getHelper().add(tTempMember);
						break;
					case Observer:
						getObserver().add(tTempMember);
						break;
					default:
						break;
					}
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 取得目标所有的成员列表<br/>
	 * 该列表是个临时列表，向里面增加内容不会被保存
	 * 
	 * @return 所有成员列表，包括创建者
	 */
	public ArrayList<TargetMember_Bean> getTargetMemberList() {
		ArrayList<TargetMember_Bean> result = new ArrayList<TargetMember_Bean>();
		try {
			result.add(getOwner());
			result.addAll(getAdmin());
			result.addAll(getHelper());
			result.addAll(getObserver());
			Array_Util.removeArrayListInvalidData(result, new TargetMember_Bean());
		} catch (Exception e) {
			result = new ArrayList<TargetMember_Bean>();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	/**
	 * 取得目标的接收者列表<br/>
	 * 该列表是个临时列表，向里面增加内容不会被保存
	 * 
	 * @return 接收者列表，除了创建者以外的目标成员列表
	 */
	public ArrayList<TargetMember_Bean> getReceiverList() {
		ArrayList<TargetMember_Bean> result = new ArrayList<TargetMember_Bean>();
		try {
			result.addAll(getAdmin());
			result.addAll(getHelper());
			result.addAll(getObserver());
			Array_Util.removeArrayListInvalidData(result, new TargetMember_Bean());
		} catch (Exception e) {
			result = new ArrayList<TargetMember_Bean>();
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	public TargetMember_Bean getOwner() {
		if (mOwner == null) {
			mOwner = new TargetMember_Bean();
		}
		return mOwner;
	}

	public ArrayList<TargetMember_Bean> getAdmin() {
		if (mAdmin == null) {
			mAdmin = new ArrayList<TargetMember_Bean>();
		}
		return mAdmin;
	}

	public ArrayList<TargetMember_Bean> getHelper() {
		if (mHelper == null) {
			mHelper = new ArrayList<TargetMember_Bean>();
		}
		return mHelper;
	}

	public ArrayList<TargetMember_Bean> getObserver() {
		if (mObserver == null) {
			mObserver = new ArrayList<TargetMember_Bean>();
		}
		return mObserver;
	}

	public void setOwner(TargetMember_Bean mOwner) {
		this.mOwner = mOwner;
	}

	public void setAdmin(ArrayList<TargetMember_Bean> mAdmin) {
		try {
			getAdmin().addAll(mAdmin);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setHelper(ArrayList<TargetMember_Bean> mHelper) {
		try {
			getHelper().addAll(mHelper);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setObserver(ArrayList<TargetMember_Bean> mObserver) {
		try {
			getObserver().addAll(mObserver);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void clear() {
		mOwner = null;
		mAdmin = null;
		mHelper = null;
		mObserver = null;
	}

}

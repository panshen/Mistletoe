package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.helper.mistletoe.c.fragment.base.TargetHistoryFragment;
import com.helper.mistletoe.util.ExceptionHandle;

public class Target_HistoryTarget_Adapter extends FragmentPagerAdapter {
	private ArrayList<TargetHistoryFragment> list;
	private FragmentManager fm;

	public Target_HistoryTarget_Adapter(FragmentManager fm, ArrayList<TargetHistoryFragment> list) {
		super(fm);
		try {
			this.list = list;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public TargetHistoryFragment getItem(int arg0) {
		return getList().get(arg0);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return getList().size();
	}

	public void setFragments(ArrayList<TargetHistoryFragment> fragments) throws Exception {
		try {
			if (this.list != null) {
				FragmentTransaction ft = fm.beginTransaction();
				for (TargetHistoryFragment f : this.list) {
					ft.remove(f);
				}
				ft.commit();
				ft = null;
				fm.executePendingTransactions();
			}
			this.list = fragments;
			notifyDataSetChanged();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public List<TargetHistoryFragment> getList() {
		if (list == null) {
			list = new ArrayList<TargetHistoryFragment>();
		}
		return list;
	}
}
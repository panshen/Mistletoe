package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.helper.mistletoe.c.fragment.BaseFragment;

public class MainFragmentAdapter extends FragmentPagerAdapter {

	private ArrayList<BaseFragment> list;
	FragmentManager fm;

	public MainFragmentAdapter(FragmentManager fm, ArrayList<BaseFragment> list) {
		super(fm);
		setList(list);
	}

	private void setList(ArrayList<BaseFragment> list) {
		if (list != null) {
			this.list = list;
		} else {
			this.list = new ArrayList<BaseFragment>();
		}
	}

	@Override
	public BaseFragment getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	public void setFragments(ArrayList<BaseFragment> fragments) {
		if (this.list != null) {
			FragmentTransaction ft = fm.beginTransaction();
			for (BaseFragment f : this.list) {
				ft.remove(f);
			}
			ft.commit();
			ft = null;
			fm.executePendingTransactions();
		}
		this.list = fragments;
		notifyDataSetChanged();
	}
}
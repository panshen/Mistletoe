package com.helper.mistletoe.c.fragment;

import android.widget.RelativeLayout;

import com.helper.mistletoe.c.ui.base.AbsFragment;

public abstract class BaseFragment extends AbsFragment {

	public abstract void onSearch(String filterStr);

	public abstract void onAdd();

	public abstract void onMenu(RelativeLayout menu);

}
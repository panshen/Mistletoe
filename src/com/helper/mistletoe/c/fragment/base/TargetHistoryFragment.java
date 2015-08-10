package com.helper.mistletoe.c.fragment.base;

import com.helper.mistletoe.c.ui.base.AbsFragment;

import android.widget.ImageView;

public abstract class TargetHistoryFragment extends AbsFragment {
	public abstract void onSearch(String filterStr);

	public abstract void setMenu(ImageView menu);
}
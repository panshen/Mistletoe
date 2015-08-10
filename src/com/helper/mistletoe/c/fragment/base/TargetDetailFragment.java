package com.helper.mistletoe.c.fragment.base;

import com.helper.mistletoe.c.ui.base.AbsFragment;

import android.widget.ImageView;

public abstract class TargetDetailFragment extends AbsFragment {
	public abstract void onSearch(String filterStr);

	public abstract String getTitleString();

	public abstract void setMenu(ImageView menu);

	public abstract void setOnCostListClicked(ImageView vCostList);
}
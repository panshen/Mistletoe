package com.helper.mistletoe.v.schedulecell.v1;

import android.content.Context;

import com.helper.mistletoe.R;

public class Unknown_SC extends Simple_SC {
	public Unknown_SC(Context context) {
		super(context);
	}

	@Override
	public void setChoosed(boolean choosed, boolean showWithdraw, boolean showCopy, boolean showTop) {
	}

	@Override
	protected int getLayoutId() {
		return R.layout.schedule_item_unknown_layout;
	}
}
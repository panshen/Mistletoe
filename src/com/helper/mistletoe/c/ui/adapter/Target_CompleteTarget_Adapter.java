package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.helper.mistletoe.R;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.UpdateStatusRequestNumber;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.util.prcomode.ReadyGoooFactory;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;

public class Target_CompleteTarget_Adapter extends Target_Adapter {
	public Target_CompleteTarget_Adapter(Context context, ReadyGoooFactory workFactory, ArrayList<Target_Bean> data) {
		super(context, workFactory, data);
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void updateShow(int position, View convertView) {
		try {
			Target_Bean bean = getItem(position);

			// 用户头像
			mHolder.snaImageView00.setImageForShow(bean.getLoc_TargetMember().getOwner().getHelper_photo(), SnaBitmap.NET_SMALL);
			// 标题
			mHolder.textView00.setText(bean.getSummary());
			// 最后一个进度更新
			mHolder.textView01.setText(bean.getLast_note());
			// 最后更新时间
			String ttt_lastUpdateTime = TimeTool_Utils.fromateTimeShow(bean.getLast_updated_time() * 1000, "yy-MM-dd");
			mHolder.textView02.setText(ttt_lastUpdateTime);
			// 隐藏掉无用的按键
			mHolder.textView03.setVisibility(ImageView.GONE);
			mHolder.textView04.setVisibility(ImageView.GONE);
			mHolder.textView05.setVisibility(ImageView.VISIBLE);
			mHolder.textView05.setText("删除");
			// 选中了第几条
			LinearLayout lin = (LinearLayout) convertView.findViewById(R.id.trg_addButton);
			if ((m_poition != null) && (Transformation_Util.Sring2long(m_poition) == getItemId(position))) {
				lin.setVisibility(LinearLayout.VISIBLE);
			} else {
				lin.setVisibility(LinearLayout.GONE);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void setListener(final int position) {
		try {
			mHolder.textView05.setOnClickListener(new OnClickListener() {
				private Target_Bean bean = getItem(position);

				@Override
				public void onClick(View v) {
					try {
						bean.updateStatusCloud(getContext(), UpdateStatusRequestNumber.DELETE_TARGET, null);
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

}
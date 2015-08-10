package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.Target_Create_Activity;
import com.helper.mistletoe.c.ui.TemplateListDialogActivity;
import com.helper.mistletoe.m.pojo.Template_Bean;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

@SuppressLint({ "InflateParams", "ViewHolder" })
public class TemplateListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;
	private static ArrayList<Template_Bean> list = null;

	public TemplateListAdapter(Context context,
			ArrayList<Template_Bean> templateS) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		if (templateS == null) {
			TemplateListAdapter.list = new ArrayList<Template_Bean>();
		} else {
			TemplateListAdapter.list = templateS;
		}
	}

	// 当ListView数据发生变化时,调用此方法来更新ListView
	// @param list
	public void updateListView(ArrayList<Template_Bean> list) {
		TemplateListAdapter.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder viewHolder = null;
		final Template_Bean mContent = list.get(position);

		// 把控件保存到ViewHolder中
		if(convertView==null){
			viewHolder = new viewHolder();
			convertView = inflater.inflate(R.layout.template_list_item, null);
			viewHolder.templateNameTv = (TextView) convertView
					.findViewById(R.id.template_list_tiem_textView_name);
			viewHolder.pic = (SnaImageViewV2) convertView.findViewById(R.id.template_list_tiem_imageButton_template);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (viewHolder) convertView.getTag();
		}
		
		viewHolder.pic.setImageForShow(mContent.getTemplate_pic(),SnaBitmap.NET_NORMAL);
		viewHolder.templateNameTv.setText(mContent.getTemplate_name());
		
		// 初始化ViewHolder
		// 根据task name 来设置ImageButton
//		if (mContent.getTemplate_name().equals("销售目标")) {
//			viewHolder.templateBt
//					.setBackgroundResource(R.drawable.template_sell);
//		} else {
//			viewHolder.templateBt
//					.setBackgroundResource(R.drawable.template_default);
//		}
		
		viewHolder.pic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setAction(TemplateListDialogActivity.CLOSE_THIS_ACTIVITY);
				context.sendBroadcast(i);
				Intent it_create = new Intent(context,
						Target_Create_Activity.class);
				it_create.putExtra("Template", mContent);
				context.startActivity(it_create);
			}
		});
		return convertView;
	}

	final class viewHolder {
		ImageButton templateBt;
		TextView templateNameTv;
		SnaImageViewV2 pic;
	}

	public interface ICallback {

		public void finishss();
	}
}
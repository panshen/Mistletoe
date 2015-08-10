package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.fragment.Target_Information_Fragment;
import com.helper.mistletoe.m.db.HelperManager;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.Task_Bean;
import com.helper.mistletoe.m.pojo.Task_Enum.TaskStatus;
import com.helper.mistletoe.util.ThreadPoolUtils_db;
import com.helper.mistletoe.util.Tool_Utils;
import com.helper.mistletoe.v.DateTimePickDialogUtil;
import com.helper.mistletoe.v.MyCheckBox;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

@SuppressLint({ "InflateParams", "ViewHolder" })
public class TaskListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private static Context context;
	private static List<Task_Bean> list = null; 
	private ArrayList<Helpers_Sub_Bean> members=new ArrayList<Helpers_Sub_Bean>();

	public TaskListAdapter(Context context, List<Task_Bean> list) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		if (list == null) {
			TaskListAdapter.list = new ArrayList<Task_Bean>();
		} else {
			TaskListAdapter.list = list;
		}
	}

	// 当ListView数据发生变化时,调用此方法来更新ListView
	// @param list
	public void updateListView(List<Task_Bean> list) {
		TaskListAdapter.list = list;
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
		ViewHolder viewHolder = null;
		final Task_Bean mContent = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.task_list_item, null);
			viewHolder.taskName = (TextView) convertView.findViewById(R.id.task_list_item_textView_name);
			viewHolder.taskWeight = (ImageView) convertView.findViewById(R.id.task_list_item_imageView_weight);
			viewHolder.taskOwner = (SnaImageViewV2) convertView.findViewById(R.id.task_list_item_imageView_head);
			viewHolder.taskCb = (MyCheckBox) convertView.findViewById(R.id.task_list_item_checkBox);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		    viewHolder.taskOwner.setVisibility(SnaImageViewV2.GONE);
		if (mContent.getOwner_id() != 0) {
			Helpers_Sub_Bean bean=null;
			for (int i = 0; i < members.size(); i++) {
				if (mContent.getOwner_id()==members.get(i).getHelper_id()) {
					bean=members.get(i);
				}
			}
			if (bean!=null) {
				viewHolder.taskOwner.setVisibility(SnaImageViewV2.VISIBLE);
				viewHolder.taskOwner.setImageForShow(bean.getHelper_photo(), SnaBitmap.NET_SMALL);
			}else {
				GetHelperBeenTask GetMemberTask = new GetHelperBeenTask(viewHolder.taskOwner, mContent);
				GetMemberTask.executeOnExecutor(ThreadPoolUtils_db.threadPool);
			}
		} 
		viewHolder.taskCb.setIndex(position);
		int state = DateTimePickDialogUtil.judgeTaskState(mContent);
		   viewHolder.taskCb.setOnCheckedChangeListener(MyOnClickListener.getInstance());
		   viewHolder.taskCb.setEnabled(Target_Information_Fragment.isEdited);
		if (mContent.getStatus() == TaskStatus.Complete) {// 完成
			viewHolder.taskCb.setChecked(true);
			viewHolder.taskName.getPaint().setStrikeThruText(false);
			viewHolder.taskName.setTextColor(Color.parseColor("#a9a9a9"));
			viewHolder.taskName.setText(mContent.getDescription().toString()+"(已完成)");
			viewHolder.taskCb.setBackgroundResource(R.drawable.check_unproceed_press);
		} else if (mContent.getStatus() == TaskStatus.Delete) {//作废
			viewHolder.taskName.getPaint().setStrikeThruText(true);
			viewHolder.taskName.setTextColor(Color.parseColor("#a9a9a9"));
			viewHolder.taskName.setText(mContent.getDescription().toString()+"(已作废)");
			viewHolder.taskCb.setBackgroundResource(R.drawable.check_unproceed_press);
		} else if (mContent.getStatus() == TaskStatus.Draft) {// 草稿
			viewHolder.taskCb.setChecked(false);
			viewHolder.taskName.getPaint().setStrikeThruText(false);
			viewHolder.taskName.setTextColor(Color.parseColor("#cc222222"));
			if (state==0) {
				viewHolder.taskName.setText(mContent.getDescription().toString()+"(未进行)");
				viewHolder.taskCb.setBackgroundResource(R.drawable.check_unproceed_press);
			}else if (state==1) {
				viewHolder.taskName.setText(mContent.getDescription().toString()+"(进行中)");
				viewHolder.taskCb.setBackgroundResource(R.drawable.check_proceeding_press);
			}else if (state==2) {
				viewHolder.taskName.setText(mContent.getDescription().toString()+"(已逾期)");
				viewHolder.taskCb.setBackgroundResource(R.drawable.check_proceeded_press);
			}
		}
		if (mContent.getWeights() == 2) {
			viewHolder.taskWeight.setImageResource(R.drawable.weight_two);
		} else if (mContent.getWeights() == 3) {
			viewHolder.taskWeight.setImageResource(R.drawable.weight_three);
		} else {
			viewHolder.taskWeight.setImageResource(R.drawable.weight_one);
		}
		return convertView;
	}
    class GetHelperBeenTask extends AsyncTask<String, Integer, Helpers_Sub_Bean> {
		private SnaImageViewV2 taskOwner;
		private Task_Bean task_bean;

		public GetHelperBeenTask(SnaImageViewV2 taskOwner, Task_Bean mContent) {
			this.taskOwner = taskOwner;
			this.task_bean = mContent;
		}

		@Override
		protected Helpers_Sub_Bean doInBackground(String... params) {
			Helpers_Sub_Bean Helper = new Helpers_Sub_Bean();
			HelperManager helperManager = new HelperManager();
			Helper = helperManager.ReadHelperFromDBById(context, task_bean.getOwner_id());
			return Helper;
		}

		@Override
		protected void onPostExecute(Helpers_Sub_Bean result) {
			super.onPostExecute(result);
			if (result != null) {
				members.add(result);
				taskOwner.setVisibility(SnaImageViewV2.VISIBLE);
				taskOwner.setImageForShow(result.getHelper_photo(), SnaBitmap.NET_SMALL);
			}
		}
	}

	class ViewHolder {
		TextView taskName;
		ImageView taskWeight;
		SnaImageViewV2 taskOwner;
		MyCheckBox taskCb;
	}

	// 用单例模式构造一个Listener
	static class MyOnClickListener implements OnCheckedChangeListener {

		private static MyOnClickListener instance = null;

		private MyOnClickListener() {
		}

		public static MyOnClickListener getInstance() {
			if (instance == null)
				instance = new MyOnClickListener();
			return instance;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			int index = ((MyCheckBox) buttonView).getIndex();
			
				if (isChecked) { // 被选中
					if (list.get(index).getStatus() != TaskStatus.Complete) {
						list.get(index).updateStatus(TaskStatus.Complete);
					}
				} else {// 被取消
					if (list.get(index).getStatus() != TaskStatus.Draft) {
						list.get(index).updateStatus(TaskStatus.Draft);
					}
				}
			
		}
	}

}
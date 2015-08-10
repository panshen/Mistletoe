package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.HelperDetailsActivity;
import com.helper.mistletoe.c.ui.base.Base_Adapter;
import com.helper.mistletoe.m.pojo.Helpers_Sub_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.MemberStatus;
import com.helper.mistletoe.m.pojo.TargetMember_Enum.UpdateStatusRequestNumber;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.util.prcomode.ReadyGoooFactory;
import com.helper.mistletoe.v.ViewHolder;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class Target_TargetMember_Adapter extends Base_Adapter {
	public String m_poition;
	protected ArrayList<TargetMember_Bean> data;
	protected Target_Bean mTarget;

	public Target_TargetMember_Adapter(Context context, ArrayList<TargetMember_Bean> data, ReadyGoooFactory workFactory, Target_Bean target) {
		super(context, workFactory);
		try {
			this.m_poition = null;
			this.mTarget = target;
			setData_Pr(data);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public int getCount() {
		int result = 0;
		try {
			result = getData_Pr().size();
		} catch (Exception e) {
			result = 0;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public TargetMember_Bean getItem(int position) {
		TargetMember_Bean result = null;
		try {
			result = getData_Pr().get(position);
		} catch (Exception e) {
			result = null;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@Override
	public long getItemId(int position) {
		long result = 0L;
		try {
			result = (long) hashCode();
		} catch (Exception e) {
			result = 0L;
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			if (convertView == null) {
				mHolder = new ViewHolder();
				// 把控件保存到ViewHolder中
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.target_targetmember_item_layout, null);

				mHolder.linearLayout00 = (LinearLayout) convertView.findViewById(R.id.targetMember_theShortcutMenu);

				mHolder.snaImageView00 = (SnaImageViewV2) convertView.findViewById(R.id.targetMember_head);

				mHolder.imageView00 = (ImageView) convertView.findViewById(R.id.targetMember_dividingLine);

				mHolder.textView00 = (TextView) convertView.findViewById(R.id.targetMember_name);
				mHolder.textView01 = (TextView) convertView.findViewById(R.id.targetMember_status);
				mHolder.textView02 = (TextView) convertView.findViewById(R.id.targetMember_lastUpdateTime);
				mHolder.textView03 = (TextView) convertView.findViewById(R.id.targetMember_leftButton);
				mHolder.textView04 = (TextView) convertView.findViewById(R.id.targetMember_rightButton);

				mHolder.snaImageView00.setDefaultImageForShow(R.drawable.default_head);

				// 保存ViewHolder
				convertView.setTag(mHolder);
			} else {
				// 取出holder
				mHolder = (ViewHolder) convertView.getTag();
			}
			updateShow(position, convertView);
			setListener(position);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return convertView;
	}

	protected void updateShow(int position, View convertView) throws Exception {
		try {
			TargetMember_Bean bean = getItem(position);

			// 头像
			mHolder.snaImageView00.setImageForShow(bean.getShowImageId(), SnaBitmap.NET_SMALL);
			// 姓名
			mHolder.textView00.setText(bean.getShowName());
			// 状态
			mHolder.textView01.setText(bean.getMemberStatusIntroduction());
			// 时间
			mHolder.textView02.setText(TimeTool_Utils.fromateTimeShow(bean.getHelper_update_time() * 1000, "yy/MM/dd"));

			// 选中了第几条
			LinearLayout lin = (LinearLayout) convertView.findViewById(R.id.targetMember_theShortcutMenu);
			if ((m_poition != null) && (Integer.valueOf(m_poition) == getItemId(position))) {
				lin.setVisibility(LinearLayout.VISIBLE);
			} else {
				lin.setVisibility(LinearLayout.GONE);
			}

			uds_ButtonShow(bean);// 设置按键名
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void uds_ButtonShow(TargetMember_Bean data) throws Exception {
		try {
			// 初始化
			mHolder.textView04.setEnabled(false);
			mHolder.textView03.setVisibility(TextView.INVISIBLE);
			mHolder.textView04.setVisibility(TextView.INVISIBLE);
			mHolder.imageView00.setVisibility(TextView.INVISIBLE);
			/*
			 * 显示类型： 0.隐藏 1.同意/拒绝 2.删除 3.倒计时
			 */
			int tShowType = 0;
			// 判断显示类型
			{
				boolean tIsCreater = mTarget.userIsCreater(getContext());// 当前用户是否是创建者
				boolean tCanDelete = data.canDelete();// 这个帮手是否允许删除
				boolean tIsNeedOperation = data.needOperation();// 这个帮手是否需要操作
				boolean tIsWatiing = data.getIn_waiting_status();// 这个帮手的状态变化是不是在计时中
				if (tIsCreater && tIsNeedOperation) {
					tShowType = 1;// 需要操作的用户，创建者应当显示同意拒绝
				} else if (tIsWatiing) {
					tShowType = 3;// 正在改变状态的用户需要显示改变时间
				} else if (tIsCreater && tCanDelete && (!tIsWatiing)) {
					tShowType = 2;// 创建者对于可以删除的用户，如果用户不在冷却时间，应当显示删除按钮
				} else {
					tShowType = 0;// 其他用户隐藏按钮
				}
			}
			// 更新显示
			switch (tShowType) {
			case 1:
				mHolder.textView03.setText("同意");
				mHolder.textView04.setText("拒绝");
				mHolder.textView04.setEnabled(true);
				mHolder.textView03.setVisibility(TextView.VISIBLE);
				mHolder.textView04.setVisibility(TextView.VISIBLE);
				mHolder.imageView00.setVisibility(TextView.VISIBLE);
				break;
			case 2:
				mHolder.textView04.setText("删除");
				mHolder.textView04.setEnabled(true);
				mHolder.textView04.setVisibility(TextView.VISIBLE);
				break;
			case 3:
				String tCountdown = data.showWaitingTimes();
				mHolder.textView04.setText(tCountdown);
				mHolder.textView03.setVisibility(TextView.GONE);
				mHolder.textView04.setVisibility(TextView.VISIBLE);
				mHolder.imageView00.setVisibility(TextView.GONE);
				break;
			}
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void setListener(final int position) throws Exception {
		try {
			mHolder.snaImageView00.setOnClickListener(new OnClickListener() {
				private final Helpers_Sub_Bean helper = getItem(position);

				@Override
				public void onClick(View v) {
					Intent intent_helperDetails = new Intent(getContext(), HelperDetailsActivity.class);
					intent_helperDetails.putExtra("helper", helper);
					getContext().startActivity(intent_helperDetails);
				}
			});
			mHolder.textView03.setOnClickListener(new OnClickListener() {
				private final MemberStatus mStatus = getItem(position).getMember_status();
				private final int mHelperId = getItem(position).getMember_id();
				private final Target_Bean mTarget = Target_TargetMember_Adapter.this.mTarget;

				@Override
				public void onClick(View v) {
					try {
						switch (mStatus) {
						case HelperApplyClose:
							// 同意 6
							mTarget.updateStatusCloud(getContext(), UpdateStatusRequestNumber.OWNER_AGREE_CLOSE, mHelperId);
							break;
						default:
							// 没反应
							break;
						}
						showToast("正在操作");
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			mHolder.textView04.setOnClickListener(new OnClickListener() {
				private final MemberStatus mStatus = getItem(position).getMember_status();
				private final int mHelperId = getItem(position).getMember_id();
				private final Target_Bean mTarget = Target_TargetMember_Adapter.this.mTarget;

				@Override
				public void onClick(View v) {
					try {
						switch (mStatus) {
						case HelperApplyClose:
							// 拒绝 7
							mTarget.updateStatusCloud(getContext(), UpdateStatusRequestNumber.OWNER_REJECT_CLOSE, mHelperId);
							break;
						default:
							// 删除 3
							mTarget.updateStatusCloud(getContext(), UpdateStatusRequestNumber.OWNER_CLOSE_MEMBER, mHelperId);
							break;
						}
						showToast("正在操作");
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void setData_Pr(ArrayList<TargetMember_Bean> data) throws Exception {
		try {
			if (data == null) {
				data = new ArrayList<TargetMember_Bean>();
			}
			for (Iterator<TargetMember_Bean> iterator = data.iterator(); iterator.hasNext();) {
				TargetMember_Bean temp = iterator.next();
				if (temp.getMember_status() == MemberStatus.UnWatch) {
					iterator.remove();
				}
			}
			getData_Pr().clear();
			getData_Pr().addAll(data);
			notifyDataSetChanged();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public ArrayList<TargetMember_Bean> getData_Pr() throws Exception {
		ArrayList<TargetMember_Bean> result = null;
		try {
			if (data == null) {
				data = new ArrayList<TargetMember_Bean>();
			}
			result = data;
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
		return result;
	}

	public void setmTarget(Target_Bean mTarget) {
		this.mTarget = mTarget;
	}

}

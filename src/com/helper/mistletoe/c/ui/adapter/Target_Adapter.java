package com.helper.mistletoe.c.ui.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.ui.base.Base_Adapter;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetRunningState;
import com.helper.mistletoe.m.pojo.Target_Enum.TargetType;
import com.helper.mistletoe.m.work.be.GetTargetList_Target_Mode;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.TimeTool_Utils;
import com.helper.mistletoe.util.Tool_Utils;
import com.helper.mistletoe.util.Transformation_Util;
import com.helper.mistletoe.util.prcomode.ReadyGoooFactory;
import com.helper.mistletoe.v.ViewHolder;
import com.helper.mistletoe.v.colorswitch.TargetColor;
import com.helper.mistletoe.v.redpoint.ReadPoint;
import com.helper.mistletoe.v.snaimageview.SnaBitmap;
import com.helper.mistletoe.v.snaimageview.v2.SnaImageViewV2;

public class Target_Adapter extends Base_Adapter {
	public String m_poition;
	protected ArrayList<Target_Bean> data;

	protected ReadyGoooFactory workFactory;

	public Target_Adapter(Context context, ReadyGoooFactory workFactory, ArrayList<Target_Bean> data) {
		super(context, workFactory);
		try {
			this.m_poition = null;
			this.workFactory = workFactory;
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
	public Target_Bean getItem(int position) {
		Target_Bean result = null;
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
			result = (long) (getData_Pr().get(position).getPrimaryKey());
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
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.target_item_layout, null);
				mHolder.linearLayout00 = (LinearLayout) convertView.findViewById(R.id.trg_show);
				mHolder.linearLayout02 = (LinearLayout) convertView.findViewById(R.id.trg_addButton);
				mHolder.linearLayout03 = (LinearLayout) convertView.findViewById(R.id.trg_back);
				mHolder.snaImageView00 = (SnaImageViewV2) convertView.findViewById(R.id.trg_head);
				mHolder.imageView00 = (ImageView) convertView.findViewById(R.id.trg_color);
				mHolder.imageView01 = (ImageView) convertView.findViewById(R.id.trg_sendState);
				mHolder.textView00 = (TextView) convertView.findViewById(R.id.trg_title);
				mHolder.textView01 = (TextView) convertView.findViewById(R.id.trg_lastSchedule);
				mHolder.textView02 = (TextView) convertView.findViewById(R.id.trg_date);
				mHolder.textView03 = (TextView) convertView.findViewById(R.id.trg_Mute);
				mHolder.textView04 = (TextView) convertView.findViewById(R.id.trg_toTop);
				mHolder.textView05 = (TextView) convertView.findViewById(R.id.trg_delete);
				mHolder.readPoint00 = (ReadPoint) convertView.findViewById(R.id.trg_head_Rdp);
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

	protected void updateShow(int position, View convertView) {
		try {
			Target_Bean bean = getItem(position);

			// 目标颜色
			int t_ColorRes = TargetColor.getTargetColorId(bean.getTarget_flag());
			mHolder.imageView00.setImageResource(t_ColorRes);
			// 用户头像
			mHolder.snaImageView00.setImageForShow(bean.getShowImageId(-1), SnaBitmap.NET_SMALL);
			// 标题
			mHolder.textView00.setText(bean.getSummary());
			// 最后一个进度更新
			mHolder.textView01.setText(bean.getLast_note());
			// 最后更新时间
			long tEtaTime = bean.getLast_updated_time() * 1000;
			if (tEtaTime > 0) {
				String ttt_lastUpdateTime = TimeTool_Utils.fromateTimeShow(tEtaTime, "yy-MM-dd");
				mHolder.textView02.setText(ttt_lastUpdateTime);
			} else {
				mHolder.textView02.setText("");
			}
			// 背景颜色
			mHolder.linearLayout03.setBackgroundResource(R.color.white_t9t);
			if (bean.getTarget_stick() == Target_Bean.TARGET_STICK_STICK) {
				mHolder.linearLayout03.setBackgroundColor(Color.argb(255, 236, 236, 236));
			}
			// 选中了第几条
			LinearLayout lin = (LinearLayout) convertView.findViewById(R.id.trg_addButton);
			if ((m_poition != null) && (Transformation_Util.Sring2long(m_poition) == getItemId(position))) {
				lin.setVisibility(LinearLayout.VISIBLE);
				// 显示/隐藏市场操作
				uds_ExFloatIcon(bean, convertView);
				uds_MarketFloat(bean, convertView);
			} else {
				lin.setVisibility(LinearLayout.GONE);
			}

			// 红点颜色
			mHolder.readPoint00.setNumber(bean.getUnread_note_number());

			// 显示发送成功还是失败
			uds_AccordingToTheSendingState(bean, convertView);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void uds_AccordingToTheSendingState(Target_Bean bean, View convertView) {
		try {
			mHolder.imageView01.setVisibility(ImageView.INVISIBLE);
			mHolder.imageView01.setImageResource(R.drawable.create_failed);
			if (((int) bean.getTarget_id()) == -2) {
				mHolder.imageView01.setVisibility(ImageView.VISIBLE);
			} else if (((int) bean.getTarget_id()) == -1) {
				mHolder.imageView01.setVisibility(ImageView.VISIBLE);
				mHolder.imageView01.setImageResource(R.drawable.target_sending);
			}else if (((int) bean.getTarget_id()) >0 ) {
				if (bean.eta_time>0&&bean.eta_time*1000<TimeTool_Utils.getNowTime()) {
					mHolder.imageView01.setVisibility(ImageView.VISIBLE);
					mHolder.imageView01.setImageResource(R.drawable.target_expired);
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void uds_MarketFloat(Target_Bean bean, View convertView) {
		try {
			mHolder.textView05.setVisibility(LinearLayout.VISIBLE);
			TargetType canChangeTo = bean.canChangeToTargetType(getContext());
			if (canChangeTo != null) {
				if (canChangeTo == TargetType.Market) {
					mHolder.textView05.setText("发布");
				} else if (canChangeTo == TargetType.General) {
					mHolder.textView05.setText("撤回");
				}
			} else {
				mHolder.textView05.setVisibility(LinearLayout.INVISIBLE);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void uds_ExFloatIcon(Target_Bean bean, View convertView) {
		try {
			if (bean.getAccept_push_msg() == Target_Bean.TARGET_ACCEPTPUSH_ACCEPT) {
				mHolder.textView03.setText("静音");
			} else {
				mHolder.textView03.setText("取消静音");
			}
			if (bean.getTarget_stick() == Target_Bean.TARGET_STICK_STICK) {
				mHolder.textView04.setText("还原");
			} else {
				mHolder.textView04.setText("置顶");
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setListener(final int position) {
		try {
			mHolder.imageView00.setOnClickListener(new OnClickListener() {
				private Target_Bean bean = getItem(position);

				@Override
				public void onClick(View v) {
					try {
						if (workFactory != null) {
							GetTargetList_Target_Mode workObj = new GetTargetList_Target_Mode(TargetRunningState.Running, bean
									.getTarget_flag(), null);
							workObj.publishWork(getWorkFactory());
						}
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			
			mHolder.textView04.setOnClickListener(new OnClickListener() {
				private Target_Bean bean = getItem(position);

				@Override
				public void onClick(View v) {
					try {
						showToast("正在置顶");
						bean.topTarget(getContext());
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			
			mHolder.textView05.setOnClickListener(new OnClickListener() {
				private Target_Bean bean = getItem(position);

				@Override
				public void onClick(View v) {
					try {
						bean.entryMarket(getContext());
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			mHolder.textView03.setOnClickListener(new OnClickListener() {
				private Target_Bean bean = getItem(position);

				@Override
				public void onClick(View v) {
					try {
						showToast("正在静音");
						bean.muteTarget(getContext());
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setData_Pr(ArrayList<Target_Bean> data) {
		try {
			if (data == null) {
				data = new ArrayList<Target_Bean>();
			}
			getData_Pr().clear();
			getData_Pr().addAll(data);
			notifyDataSetChanged();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public ArrayList<Target_Bean> getData_Pr() {
		ArrayList<Target_Bean> result = null;
		try {
			if (data == null) {
				data = new ArrayList<Target_Bean>();
			}
			result = data;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
		return result;
	}

}

package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.helper.mistletoe.MistletoeApplication;
import com.helper.mistletoe.R;
import com.helper.mistletoe.c.fragment.Schedule_Fragment;
import com.helper.mistletoe.c.fragment.Target_Information_Fragment;
import com.helper.mistletoe.c.fragment.base.TargetDetailFragment;
import com.helper.mistletoe.c.ui.adapter.Target_Detailes_Adapter;
import com.helper.mistletoe.c.ui.base.Base_Activity.ChangeMarketPosition;
import com.helper.mistletoe.c.ui.base.Base_FragmentActivity;
import com.helper.mistletoe.m.db.TargetManager;
import com.helper.mistletoe.m.pojo.Target_Bean;
import com.helper.mistletoe.m.work.base.AirLock_Work;
import com.helper.mistletoe.m.work.be.Target_ClearUnreadNoteNumber_Mode;
import com.helper.mistletoe.util.Array_Util;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.SnaEnum;
import com.helper.mistletoe.util.Tool_Utils;

public class Target_Details_Activity extends Base_FragmentActivity implements
		OnClickListener, ChangeMarketPosition {
	private String DEBUG_TAG = "com.helper.mistletoe.c.fragment.Target_Information_activity";
	private ViewPager vp_main_fragment_viewpager;
	private SearchView searchView;
	private ImageView memu;
	private ImageView vCostList;
	private ImageView vBack;
	private TextView vTitle;
	private LinearLayout[] linearLayout = new LinearLayout[2];
	private TextView[] textview = new TextView[2];
	private ImageView line;

	private Target_Detailes_Adapter adapter;
	private int currentFragmentIndex;
	private ArrayList<TargetDetailFragment> fragments;
	private int targetId;
	private String targetTag;
	private int mMarketPosition;// 这条记录的位置
	private DetailViewType mViewType;// 这条记录的类型
	/**
	 * 屏幕的宽度
	 */
	private int screenWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.target_details_layout);
			initView();
			setListener();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			new Target_ClearUnreadNoteNumber_Mode(targetId, targetTag)
					.publishWork(getApplicationContext());
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			setData();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void setData() {
		try {
			vp_main_fragment_viewpager.setCurrentItem(currentFragmentIndex);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView() {
		try {
			// 初始化控件
			linearLayout[0] = (LinearLayout) findViewById(R.id.targetdeatil_layout_note);
			linearLayout[1] = (LinearLayout) findViewById(R.id.targetdeatil_layout_target);
			textview[0] = (TextView) findViewById(R.id.targetdeatil_layout_textview_note);
			textview[1] = (TextView) findViewById(R.id.targetdeatil_layout_textview_target);
			vp_main_fragment_viewpager = (ViewPager) findViewById(R.id.vp_main_fragment_viewpager);
			searchView = (SearchView) findViewById(R.id.searchView_main_fragment);
			memu = (ImageView) findViewById(R.id.imageButton_main_fragment_menufwefwfwfwfwefwf);
			vBack = (ImageView) findViewById(R.id.searchViddddddddew_main_back);
			vTitle = (TextView) findViewById(R.id.searchxxxxxView_main_title);
			vCostList = (ImageView) findViewById(R.id.td_CostList);
			// 初始化成员变量
			currentFragmentIndex = 1;
			fragments = new ArrayList<TargetDetailFragment>();
			// 从上一个页面获得参数
			getDataFromBundle();
			// 其他初始化工作
			setFragment();
			initTabLine(currentFragmentIndex);
			adapter = new Target_Detailes_Adapter(getSupportFragmentManager(),
					fragments);
			vp_main_fragment_viewpager.setAdapter(adapter);

			initView_Other();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void initView_Other() {
		try {
			// 从数据库中读取Target
			TargetManager dbWorkOj = TargetManager.getInstance(getContext());
			Target_Bean tTarget = dbWorkOj.getTarget(targetId, targetTag);
			tTarget.readTargetMember_Local(getApplicationContext());
			if ((tTarget.needAgree() || tTarget.needRefuse())
					|| (mViewType == DetailViewType.MarketTarget)) {
				currentFragmentIndex = 1;
			} else {
				currentFragmentIndex = 0;
			}

			// 其他初始化工作
			AirLock_Work.syncTargetMember(getApplicationContext(), targetId);// 同步目标成员
			setTitleTag();
			setMenu();
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 根据屏幕的宽度，初始化引导线的宽度
	 */
	private void initTabLine(int currentFragmentIndex) {
		line = (ImageView) findViewById(R.id.targetdeatil_layout_imageview_line);
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(outMetrics);
		screenWidth = outMetrics.widthPixels;
		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) line
				.getLayoutParams();
		lp.width = screenWidth / 2;
		lp.leftMargin = (int) ((currentFragmentIndex) * (screenWidth * 1.0 / 2));
		line.setLayoutParams(lp);
	}

	/**
	 * 重置颜色与控件
	 */
	protected void resetTextView(int position) {
		textview[0].setTextColor(getResources().getColor(R.color.normal_light));
		textview[1].setTextColor(getResources().getColor(R.color.normal_light));
		textview[position].setTextColor(getResources().getColor(
				R.color.high_light));
	}

	protected void setListener() {
		try {
			vp_main_fragment_viewpager
					.setOnPageChangeListener(new OnPageChangeListener() {

						@Override
						public void onPageSelected(int position) {
							try {
								currentFragmentIndex = position;
								setTitleTag();
								setMenu();
								setCost();
								// 重置颜色与控件
								resetTextView(position);

								if (position == 1) {
									searchView.setVisibility(SearchView.GONE);
								} else {
									searchView
											.setVisibility(SearchView.VISIBLE);
								}
							} catch (Exception e) {
								ExceptionHandle.ignoreException(e);
							}
						}

						@Override
						public void onPageScrolled(int position,
								float positionOffset, int positionOffsetPixels) {
							/**
							 * 利用position和currentIndex判断用户的操作是哪一页往哪一页滑动
							 * 然后改变根据positionOffset动态改变TabLine的leftMargin
							 */
							if (currentFragmentIndex == 0 && position == 0)// 0->1
							{
								LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) line
										.getLayoutParams();
								lp.leftMargin = (int) (positionOffset
										* (screenWidth * 1.0 / 2) + currentFragmentIndex
										* (screenWidth / 2));
								line.setLayoutParams(lp);

							} else if (currentFragmentIndex == 1
									&& position == 0) // 1->0
							{
								LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) line
										.getLayoutParams();
								lp.leftMargin = (int) (-(1 - positionOffset)
										* (screenWidth * 1.0 / 2) + currentFragmentIndex
										* (screenWidth / 2));
								line.setLayoutParams(lp);
							}
						}

						@Override
						public void onPageScrollStateChanged(int arg0) {
						}
					});
			vBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						if (!Tool_Utils
								.isApplicationBroughtToBackground(getApplicationContext())) {
							Intent intent = new Intent();
							intent.setClass(getApplicationContext(),
									MainFragmentActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
									| Intent.FLAG_ACTIVITY_CLEAR_TOP);
							getApplicationContext().startActivity(intent);
							MistletoeApplication.targetInterfaceFlags = -1;
						}
						finish();
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
			searchView.setOnQueryTextListener(new OnQueryTextListener() {
				@Override
				public boolean onQueryTextChange(String queryText) {
					try {
						fragments.get(currentFragmentIndex).onSearch(queryText);
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
					return true;
				}

				@Override
				public boolean onQueryTextSubmit(String queryText) {
					try {
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
					return true;
				}
			});

			for (LinearLayout b : linearLayout) {
				b.setOnClickListener(this);
			}

			vCostList.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						fragments.get(currentFragmentIndex)
								.setOnCostListClicked(vCostList);
					} catch (Exception e) {
						ExceptionHandle.ignoreException(e);
					}
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setTitleTag() {
		try {
			if ((fragments != null) && (fragments.size() > 0)) {
				vTitle.setText(fragments.get(currentFragmentIndex)
						.getTitleString());
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setTitleTag(String title, String leftTag) {
		try {
			vTitle.setText(title);
			textview[0].setText(leftTag);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void setFragment() {
		try {
			Bundle bundle = new Bundle();
			bundle.putInt("targetId", targetId);
			bundle.putString("targetTag", targetTag);
			bundle.putInt("position", mMarketPosition);
			bundle.putInt("targetRecordType", mViewType.toInt());

			Schedule_Fragment Schedulefragment = new Schedule_Fragment();
			Schedulefragment.setArguments(bundle);
			fragments.add(Schedulefragment);
			Target_Information_Fragment Targetfragment = new Target_Information_Fragment();
			Targetfragment.setArguments(bundle);
			fragments.add(Targetfragment);

		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setMenu() {
		try {
			fragments.get(currentFragmentIndex).setMenu(memu);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public void setCost() {
		try {
			if (currentFragmentIndex == 0) {
				vCostList.setVisibility(ImageView.VISIBLE);
			} else if (currentFragmentIndex == 1) {
				vCostList.setVisibility(ImageView.GONE);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.targetdeatil_layout_note:
			currentFragmentIndex = 0;
			break;
		case R.id.targetdeatil_layout_target:
			currentFragmentIndex = 1;
			break;
		}
		vp_main_fragment_viewpager.setCurrentItem(currentFragmentIndex);
	}

	public void changeMarketPosition(int targetId, int position) {
		try {
			if (targetId > 0) {
				this.targetId = targetId;
				targetTag = "";
				mMarketPosition = position;
				mViewType = DetailViewType.MarketTarget;
				initView_Other();
				// 调用子类的同步方法
				for (TargetDetailFragment i : fragments) {
					if (i instanceof ChangeMarketPosition) {
						ChangeMarketPosition temp = (ChangeMarketPosition) i;
						temp.changeMarketPosition(targetId, position);
					}
				}
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void getDataFromBundle() {
		try {
			Bundle bundle = getIntent().getExtras();
			int source = bundle.getInt("source", -1);
			if (source == 1) {
				MistletoeApplication.noteCount = 0;
				targetId = MistletoeApplication.targetId;
				targetTag = MistletoeApplication.targetTag;
			} else {
				targetId = bundle.getInt("targetId", -1);
			}

			mMarketPosition = bundle.getInt("position", -1);
			mViewType = DetailViewType
					.valueOf(bundle.getInt("targetRecordType",
							DetailViewType.TraditionTarget.toInt()));
			MistletoeApplication.targetInterfaceFlags = targetId;

			Log.i(DEBUG_TAG, "details _ activity :" + targetId + "--"
					+ targetTag + "--" + mMarketPosition);

		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public static void openPage_Market(Activity activity, int targetId,
			int position) {
		openPage(activity, 337 + 0, 2, targetId, "", position,
				DetailViewType.MarketTarget);
	}

	public static void openPage(Activity activity, int requestCode, int source,
			int targetId, String targetTag, int position,
			DetailViewType recordType) {
		try {
			Intent intent = new Intent(activity, Target_Details_Activity.class);

			intent.putExtra("source", source);
			intent.putExtra("targetId", targetId);
			intent.putExtra("targetTag", targetTag);
			intent.putExtra("position", position);
			intent.putExtra("targetRecordType", recordType.toInt());

			activity.startActivityForResult(intent, requestCode);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public interface TargetMarketList {
		/**
		 * @param position
		 *            目前正在阅读的Target的位置
		 * @return 目标
		 */
		public Target_Bean getRandomTarget(int position);
	}

	/**
	 * 目标详情展示的类型
	 * 
	 * @author 张久瑞
	 */
	public enum DetailViewType implements SnaEnum {
		/**
		 * 普通目标
		 */
		TraditionTarget(1, "普通目标"),
		/**
		 * 市场里的目标
		 */
		MarketTarget(2, "市场里的目标");

		private int mInt;
		private String mDescription;

		private DetailViewType(int _int, String _Description) {
			this.mInt = _int;
			this.mDescription = _Description;
		}

		@Override
		public String getDescription() {
			return this.mDescription;
		}

		@Override
		public int toInt() {
			return this.mInt;
		}

		public static DetailViewType valueOf(int value,
				DetailViewType defaultValue) {
			DetailViewType result = defaultValue;
			try {
				SparseArray<DetailViewType> valueMap = Array_Util
						.getEnumValueMap(DetailViewType.values());
				result = valueMap.get(value, defaultValue);
			} catch (Exception e) {
				result = defaultValue;
				ExceptionHandle.ignoreException(e);
			}
			return result;
		}

		public static DetailViewType valueOf(int value) {
			return valueOf(value, TraditionTarget);
		}
	}
}
package com.helper.mistletoe.c.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.fragment.Target_CompleteTarget_Fragment;
import com.helper.mistletoe.c.fragment.Target_DeletedTarget_Fragment;
import com.helper.mistletoe.c.fragment.base.TargetHistoryFragment;
import com.helper.mistletoe.c.ui.adapter.Target_HistoryTarget_Adapter;
import com.helper.mistletoe.util.ExceptionHandle;

public class Target_HistoryTarget_Activity extends FragmentActivity implements
		OnClickListener {
	private ViewPager vp_main_fragment_viewpager;
	private LinearLayout[] linearLayout = new LinearLayout[2];
	private TextView[] textview = new TextView[2];
	private ImageView line;
	private SearchView searchView;
	private ImageView memu;
	private ImageView vBack;
	private TextView vTitle;

	private Target_HistoryTarget_Adapter adapter;
	private int currentFragmentIndex;
	private ArrayList<TargetHistoryFragment> fragments;
	private String targetId;
	/**
	 * 屏幕的宽度
	 */
	private int screenWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.target_historytarget_layout);
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

	protected void setData() throws Exception {
		try {
			vp_main_fragment_viewpager.setAdapter(adapter);
			vp_main_fragment_viewpager.setCurrentItem(0);
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	protected void initView() throws Exception {
		try {
			linearLayout[0] = (LinearLayout) findViewById(R.id.target_historyac_layout_deleted);
			linearLayout[1] = (LinearLayout) findViewById(R.id.target_historyac_layout_history);
			textview[0] = (TextView) findViewById(R.id.target_historyac_layout_textview_deleted);
			textview[1] = (TextView) findViewById(R.id.target_historyac_layout_textview_history);
			// 初始化控件
			vp_main_fragment_viewpager = (ViewPager) findViewById(R.id.vp_main_fragment_viewpager);

			searchView = (SearchView) findViewById(R.id.searchView_main_fragment);
			memu = (ImageView) findViewById(R.id.imageButton_main_fragment_menufwefwfwfwfwefwf);
			vBack = (ImageView) findViewById(R.id.searchViddddddddew_main_back);
			vTitle = (TextView) findViewById(R.id.searchxxxxxView_main_title);
			// 初始化成员变量
			currentFragmentIndex = 0;
			fragments = new ArrayList<TargetHistoryFragment>();
			// 从上一个页面获得参数
			getDataFromBundle();
			initTabLine(currentFragmentIndex);
			// 其他初始化工作
			setFragment();
			adapter = new Target_HistoryTarget_Adapter(
					getSupportFragmentManager(), fragments);
			setTitleTag();
			setMenu();
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	/**
	 * 根据屏幕的宽度，初始化引导线的宽度
	 */
	private void initTabLine(int currentFragmentIndex) {
		line = (ImageView) findViewById(R.id.target_historyac_layout_imageview_line);
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

	protected void setListener() throws Exception {
		try {
			vp_main_fragment_viewpager
					.setOnPageChangeListener(new OnPageChangeListener() {

						@Override
						public void onPageSelected(int position) {
							try {
								currentFragmentIndex = position;
								setTitleTag();
								setMenu();
								// 重置颜色与控件
								resetTextView(position);
							} catch (Exception e) {
								ExceptionHandle.ignoreException(e);
							}
						}

						@Override
						public void onPageScrolled(int position,
								float positionOffset, int positionOffsetPixels) {
							/**
							 * 利用possition和currentIndex判断用户的操作是哪一页往哪一页滑动
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
							try {
							} catch (Exception e) {
								ExceptionHandle.ignoreException(e);
							}
						}
					});
			vBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
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
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void setMenu() {
		try {
			fragments.get(currentFragmentIndex).setMenu(memu);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	protected void getDataFromBundle() throws Exception {
		try {
		} catch (Exception e) {
			ExceptionHandle.unInterestException(e);
		}
	}

	public void setTitleTag() {
		try {
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	private void setFragment() {
		try {
			{
				Target_CompleteTarget_Fragment fragment = new Target_CompleteTarget_Fragment();
				fragments.add(fragment);
			}
			{
				Target_DeletedTarget_Fragment fragment = new Target_DeletedTarget_Fragment();
				fragments.add(fragment);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.target_historyac_layout_deleted:
			currentFragmentIndex = 0;
			break;
		case R.id.target_historyac_layout_history:
			currentFragmentIndex = 1;
			break;
		}
		vp_main_fragment_viewpager.setCurrentItem(currentFragmentIndex);
	}

}

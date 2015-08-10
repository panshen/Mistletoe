package com.helper.mistletoe.c.ui;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.fragment.BaseFragment;
import com.helper.mistletoe.c.fragment.HelperFragment;
import com.helper.mistletoe.c.fragment.PosterFragment;
import com.helper.mistletoe.c.fragment.SetFragment;
import com.helper.mistletoe.c.fragment.Target_Fragment;
import com.helper.mistletoe.c.ui.adapter.MainFragmentAdapter;
import com.helper.mistletoe.c.ui.base.Base_FragmentActivity;
import com.helper.mistletoe.json.MyJsonReader;
import com.helper.mistletoe.m.pojo.Template_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.Instruction_Utils;
import com.helper.mistletoe.util.MessageConstants;
import com.helper.mistletoe.util.Tool_Utils;

public class MainFragmentActivity extends Base_FragmentActivity implements
		OnClickListener {
	private IntentFilter myIntentFilter;
	private ViewPager main_fragment_viewpager;
	private ArrayList<BaseFragment> fragments;
	private LinearLayout[] linearLayout = new LinearLayout[4];
	private TextView[] textview = new TextView[4];
	private TextView logo;
	private SearchView searchView;
	private ImageButton add;
	private RelativeLayout menu;
	private ImageView menu_Icon;
	private ImageView line;
	private MainFragmentAdapter adapter;
	public static int currentFragmentIndex;
	@SuppressWarnings("unused")
	private LayoutInflater inflater;
	public static int flag = 0;
	private ArrayList<Template_Bean> TemplateS = new ArrayList<Template_Bean>();
	Intent i = null;
	/**
	 * 屏幕的宽度
	 */
	private int screenWidth;
	private UpdateManager mUpdateManager;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_fragment);
		i = getIntent();
		int id = i.getIntExtra("flag", 0);

		currentFragmentIndex = id;
		// 注册广播
		myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(MessageConstants.UPGRADED_VERSION);
		getApplicationContext()
				.registerReceiver(IntentReceicer, myIntentFilter);
		// 发送检查版本更新指令
		Instruction_Utils.sendInstrustion(
				MainFragmentActivity.this.getApplicationContext(),
				Instruction_Utils.CHECK_VERSION, "MainFragmentActivity");
		// 发送获取模板的指令
		Instruction_Utils.sendInstrustion(
				MainFragmentActivity.this.getApplicationContext(),
				Instruction_Utils.SYNCHRONOUS_CONSTANTS);
		//发送费用标签的指令
		 Instruction_Utils.sendInstrustion(getApplicationContext(), Instruction_Utils.SYNCHRONOUS_COSTTAG,0);
		setupViews();
		setListeners();
	}

	private void Update(String url) {
		// 这里来检测版本是否需要更新
		mUpdateManager = new UpdateManager(MainFragmentActivity.this);
		mUpdateManager.checkUpdateInfo(url);
	}

	private BroadcastReceiver IntentReceicer = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(MessageConstants.UPGRADED_VERSION)) {
				if (intent.getStringExtra("source").equals(
						"MainFragmentActivity")) {
					Update(intent.getStringExtra("url"));
				}
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Tool_Utils.showInfo(this, "再按一次退出程序");
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
		} else {
			finish();
			System.exit(0);
		}
	}

	private void setupViews() {
		try {
			inflater = getLayoutInflater();
			main_fragment_viewpager = (ViewPager) findViewById(R.id.main_fragment_viewpager);
			linearLayout[0] = (LinearLayout) findViewById(R.id.main_fragment_title_linearLayout_target);
			linearLayout[1] = (LinearLayout) findViewById(R.id.main_fragment_title_linearLayout_helper);
			linearLayout[2] = (LinearLayout) findViewById(R.id.main_fragment_title_linearLayout_poster);
			linearLayout[3] = (LinearLayout) findViewById(R.id.main_fragment_title_linearLayout_setting);
			textview[0] = (TextView) findViewById(R.id.main_fragment_title_textview_target);
			textview[1] = (TextView) findViewById(R.id.main_fragment_title_textview_helper);
			textview[2] = (TextView) findViewById(R.id.main_fragment_title_textview_poster);
			textview[3] = (TextView) findViewById(R.id.main_fragment_title_textview_setting);
			logo = (TextView) findViewById(R.id.main_fragment_title_textView_name);
			searchView = (SearchView) findViewById(R.id.main_fragment_title_searchView);
			add = (ImageButton) findViewById(R.id.main_fragment_imageButton_add);
			menu = (RelativeLayout) findViewById(R.id.main_fragment_title_relativeLayout_menu);
			menu_Icon = (ImageView) findViewById(R.id.main_fragment_title_imagetButton_menu);

			// ArrayList<Template_Bean> TemplateS = new
			// ArrayList<Template_Bean>();
			// // 1、获取Preferences
			// SharedPreferences settings = getSharedPreferences(
			// "TemplateConstants", getApplication().MODE_PRIVATE);
			// // 2、取出数据
			// String ts = settings.getString("TemplateS", null);
			// if (ts == null) {
			// TemplateS = null;
			// } else {
			// TemplateS = MyJsonReader.getJsonDataForFindTargetTemlate(ts);
			// }
			//
			// // FAB按钮
			//
			// Fab_Menu = (FloatingActionsMenu) findViewById(R.id.Fab_Menu);
			//
			// Fab_Menu.setOnFloatingActionsMenuUpdateListener(new
			// OnFloatingActionsMenuUpdateListener() {
			//
			//
			// @Override
			// public void onMenuExpanded() {
			//
			// }
			//
			// @Override
			// public void onMenuCollapsed() {
			//
			// }
			// });
			//
			// for(final Template_Bean temp : TemplateS){
			// FloatingActionButton fab_bt = new FloatingActionButton(
			// getApplicationContext());
			// fab_bt.setTitle("321");
			// fab_bt.setIconDrawable(getResources().getDrawable(R.drawable.ic_launcher));
			// fab_bt.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// Fab_Menu.collapse();
			// Intent it_create = new Intent(getApplicationContext(),
			// Target_Create_Activity.class);
			// it_create.putExtra("Template", temp);
			// startActivity(it_create);
			// }
			// });
			// Fab_Menu.addButton(fab_bt);
			// }

			initTabLine(currentFragmentIndex);
			fragments = createFragments();
			adapter = new MainFragmentAdapter(getSupportFragmentManager(),
					fragments);
			main_fragment_viewpager.setAdapter(adapter);
			main_fragment_viewpager.setCurrentItem(currentFragmentIndex);
			resetTextView(currentFragmentIndex);
			
			main_fragment_viewpager
					.setOnPageChangeListener(new OnPageChangeListener() {

						@Override
						public void onPageSelected(int position) {
							currentFragmentIndex = position;
							// 重置所有TextView的字体颜色与控件显示
							resetTextView(position);
							hidden(position);
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
										* (screenWidth * 1.0 / 4) + currentFragmentIndex
										* (screenWidth / 4));
								line.setLayoutParams(lp);

							} else if (currentFragmentIndex == 1
									&& position == 0) // 1->0
							{
								LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) line
										.getLayoutParams();
								lp.leftMargin = (int) (-(1 - positionOffset)
										* (screenWidth * 1.0 / 4) + currentFragmentIndex
										* (screenWidth / 4));
								line.setLayoutParams(lp);

							} else if (currentFragmentIndex == 1
									&& position == 1) // 1->2
							{
								LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) line
										.getLayoutParams();
								lp.leftMargin = (int) (positionOffset
										* (screenWidth * 1.0 / 4) + currentFragmentIndex
										* (screenWidth / 4));
								line.setLayoutParams(lp);
							} else if (currentFragmentIndex == 2
									&& position == 1) // 2->1
							{
								LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) line
										.getLayoutParams();
								lp.leftMargin = (int) (-(1 - positionOffset)
										* (screenWidth * 1.0 / 4) + currentFragmentIndex
										* (screenWidth / 4));
								line.setLayoutParams(lp);

							} else if (currentFragmentIndex == 2
									&& position == 2) // 2->3
							{
								LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) line
										.getLayoutParams();
								lp.leftMargin = (int) (positionOffset
										* (screenWidth * 1.0 / 4) + currentFragmentIndex
										* (screenWidth / 4));
								line.setLayoutParams(lp);
							} else if (currentFragmentIndex == 3
									&& position == 2) // 3->2
							{
								LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) line
										.getLayoutParams();
								lp.leftMargin = (int) (-(1 - positionOffset)
										* (screenWidth * 1.0 / 4) + currentFragmentIndex
										* (screenWidth / 4));
								line.setLayoutParams(lp);

							}
						}

						@Override
						public void onPageScrollStateChanged(int arg0) {

						}
					});
			searchView.setOnQueryTextListener(new OnQueryTextListener() {
				@Override
				public boolean onQueryTextChange(String queryText) {
					fragments.get(currentFragmentIndex).onSearch(queryText);
					return true;
				}

				@Override
				public boolean onQueryTextSubmit(String queryText) {
					if (searchView != null) {
						logo.setVisibility(TextView.GONE);
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						if (imm != null) {
							logo.setVisibility(TextView.VISIBLE);
							imm.hideSoftInputFromWindow(
									searchView.getWindowToken(), 0);
						}
						searchView.clearFocus();
					}
					return true;
				}
			});

			add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					fragments.get(currentFragmentIndex).onAdd();
					if (currentFragmentIndex == 0)
						add.startAnimation(AnimationUtils.loadAnimation(
								getApplicationContext(),
								R.anim.shake_half_second));
				}
			});

			menu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					fragments.get(currentFragmentIndex).onMenu(menu);
				}
			});
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	/**
	 * 重置颜色与控件
	 */
	protected void resetTextView(int position) {
		textview[0].setTextColor(getResources().getColor(R.color.normal_light));
		textview[1].setTextColor(getResources().getColor(R.color.normal_light));
		textview[2].setTextColor(getResources().getColor(R.color.normal_light));
		textview[3].setTextColor(getResources().getColor(R.color.normal_light));
		searchView.setVisibility(SearchView.VISIBLE);
		add.setVisibility(ImageButton.VISIBLE);
		menu.setVisibility(RelativeLayout.VISIBLE);
		textview[position].setTextColor(getResources().getColor(
				R.color.high_light));
	}

	/**
	 * 根据屏幕的宽度，初始化引导线的宽度
	 */
	private void initTabLine(int currentFragmentIndex) {
		line = (ImageView) findViewById(R.id.main_fragment_title_imageview_line);
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(outMetrics);
		screenWidth = outMetrics.widthPixels;
		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) line
				.getLayoutParams();
		lp.width = screenWidth / 4;
		lp.leftMargin = (int) ((currentFragmentIndex) * (screenWidth * 1.0 / 4));
		line.setLayoutParams(lp);
	}

	protected void hidden(int currentFragmentIndex) {
		if (currentFragmentIndex >= 0
				&& currentFragmentIndex <= textview.length - 1) {
			switch (currentFragmentIndex) {
			case 0:
				menu.setVisibility(RelativeLayout.VISIBLE);
				menu_Icon.setImageResource(R.drawable.file_white);
				add.setBackgroundResource(R.drawable.create_target_targetfragment);
				break;
			case 1:
				menu.setVisibility(RelativeLayout.VISIBLE);
				menu_Icon.setImageResource(R.drawable.menu_press);
				add.setBackgroundResource(R.drawable.add_helper_helperfragment);
				break;
			case 2:
				menu.setVisibility(RelativeLayout.GONE);
				add.setBackgroundResource(R.drawable.create_target_targetfragment);
				break;
			case 3:
				menu.setVisibility(RelativeLayout.GONE);
				searchView.setVisibility(SearchView.GONE);
				add.setVisibility(ImageButton.GONE);
				break;
			default:
				break;
			}
		}
	}

	private void setListeners() {
		for (LinearLayout b : linearLayout) {
			b.setOnClickListener(this);
		}
	}

	private ArrayList<BaseFragment> createFragments() {
		ArrayList<BaseFragment> list = new ArrayList<BaseFragment>();
		list.add(new Target_Fragment());
		list.add(new HelperFragment());
		list.add(new PosterFragment());
		list.add(new SetFragment());
		return list;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_fragment_title_linearLayout_target:
			currentFragmentIndex = 0;
			break;
		case R.id.main_fragment_title_linearLayout_helper:
			currentFragmentIndex = 1;
			break;
		case R.id.main_fragment_title_linearLayout_poster:
			currentFragmentIndex = 2;
			break;
		case R.id.main_fragment_title_linearLayout_setting:
			currentFragmentIndex = 3;
			break;
		}
		main_fragment_viewpager.setCurrentItem(currentFragmentIndex);
	}

	@Override
	protected void onResume() {
		try {
			super.onResume();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		try {
			super.onPause();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		try {
			// 注销广播
			getApplicationContext().unregisterReceiver(IntentReceicer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

	/**
	 * @deprecated 张久瑞2015/05/21日增加，显示操作提示页
	 */
	private void task_OpenOperationTips() {
		try {
			JSONObject tCache = readData();
			boolean tNeverTip = false;
			String tTipFragment = "";
			// 判断显示了哪个页面
			if (currentFragmentIndex == 0) {
				tTipFragment = "targetNeverTip";
			} else if (currentFragmentIndex == 1) {
				tTipFragment = "helperNeverTip";
			} else {
				tNeverTip = true;
			}
			// 取出保存的数据
			if ((!tTipFragment.equals("")) && (tCache.has(tTipFragment))) {
				tNeverTip = tCache.getBoolean(tTipFragment);
			}
			// 打开提示页
			if (!tNeverTip) {
				opac_OperationTips(currentFragmentIndex);
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// try {
		// if (requestCode == 892 + 1) {
		// if (resultCode == 772 + 0) {
		// boolean tNeverTip = false;
		// if (data != null) {
		// tNeverTip = data.getBooleanExtra("neverTip", false);
		// }
		// if (tNeverTip) {
		// JSONObject tJsonObject = new JSONObject();
		// tJsonObject.put("helperNeverTip", (boolean) tNeverTip);
		// saveData(tJsonObject);
		// }
		// }
		// } else if (requestCode == 892 + 2) {
		// if (resultCode == 772 + 0) {
		// boolean tNeverTip = false;
		// if (data != null) {
		// tNeverTip = data.getBooleanExtra("neverTip", false);
		// }
		// if (tNeverTip) {
		// JSONObject tJsonObject = new JSONObject();
		// tJsonObject.put("targetNeverTip", (boolean) tNeverTip);
		// saveData(tJsonObject);
		// }
		// }
		// }
		// } catch (Exception e) {
		// ExceptionHandle.ignoreException(e);
		// }

	}

	/**
	 * @deprecated
	 * 
	 *             张久瑞2015/05/21日增加，打开给用户的操作提示
	 */
	private void opac_OperationTips(int drable) {
		try {
			int tImageResources = -1;
			int tRequestCode = -1;
			// 判断打开的那个页面
//			if (drable == 0) {
//				tImageResources = R.drawable.operationtips_targetlist;
//				tRequestCode = 892 + 2;
//			} else if (drable == 1) {
//				tImageResources = R.drawable.operationtips_helperlist;
//				tRequestCode = 892 + 1;
//			}
			// 开启页面
			Intent intent = new Intent(getContext(),
					OperationTipsActivity.class);
			intent.putExtra("imageResources", tImageResources);
			startActivityForResult(intent, tRequestCode);
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}
}

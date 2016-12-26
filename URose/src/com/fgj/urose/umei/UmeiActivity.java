package com.fgj.urose.umei;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fgj.urose.R;
import com.fgj.urose.remote.sax.entity.Waterfalls;
import com.fgj.urose.remote.sax.ksoap.WaterfallService;
import com.fgj.urose.waterfall.ImageDownLoadAsyncTask;
import com.fgj.urose.waterfall.LazyScrollView;

public class UmeiActivity extends FragmentActivity {
	public final static int TAB_INDEX_TAB_1 = 0;
	public final static int TAB_INDEX_TAB_2 = 1;
	public final static int TAB_INDEX_TAB_3 = 2;
	public final static int TAB_INDEX_TAB_4 = 3;
	public final static int TAB_INDEX_TAB_5 = 4;
	public final static int TAB_COUNT = 5;

	private ViewPager mViewPager;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei);

		actionBar = getActionBar();
		actionBar.setDisplayShowCustomEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);

		setupTest1();
		setupTest2();
		setupTest3();
		setupTest4();
		setupTest5();

		mViewPager = (ViewPager) findViewById(R.id.pager);
		getFragmentManager();

		mViewPager.setAdapter(new TestViewPagerAdapter(
				getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(new TestPagerListener());
		mViewPager.setCurrentItem(TAB_INDEX_TAB_1);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}

	private void setupTest1() {
		Tab tab = actionBar.newTab();
		tab.setContentDescription("日韩");
		tab.setText("日韩");
		tab.setTabListener(mTabListener);
		actionBar.addTab(tab);
	}

	private void setupTest2() {
		Tab tab = actionBar.newTab();
		tab.setContentDescription("国内");
		tab.setText("国内");
		tab.setTabListener(mTabListener);
		actionBar.addTab(tab);
	}

	private void setupTest3() {
		Tab tab = actionBar.newTab();
		tab.setContentDescription("港台");
		tab.setText("港台");
		tab.setTabListener(mTabListener);
		actionBar.addTab(tab);
	}

	private void setupTest4() {
		Tab tab = actionBar.newTab();
		tab.setContentDescription("欧美");
		tab.setText("欧美");
		tab.setTabListener(mTabListener);
		actionBar.addTab(tab);
	}
	
	private void setupTest5() {
		Tab tab = actionBar.newTab();
		tab.setContentDescription("秀人模特");
		tab.setText("秀人模特");
		tab.setTabListener(mTabListener);
		actionBar.addTab(tab);
	}

	private final TabListener mTabListener = new TabListener() {
		private final static String TAG = "TabListener";

		@Override
		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
			Log.d(TAG, "onTabReselected");
		}

		@Override
		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			Log.d(TAG, "onTabSelected()");
			if (mViewPager != null)
				mViewPager.setCurrentItem(tab.getPosition());
		}

		@Override
		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
			Log.d(TAG, "onTabUnselected()");
		}
	};

	class TestPagerListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			getActionBar().selectTab(getActionBar().getTabAt(arg0));
		}
	}

	public class TestViewPagerAdapter extends FragmentPagerAdapter {
		public TestViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			String url = "http://www.umei.cc/p/gaoqing/rihan/1.htm";
			switch (arg0) {
			case 0:
				 url = "http://www.umei.cc/p/gaoqing/rihan/1.htm";
				break;
			case 1:
				 url = "http://www.umei.cc/p/gaoqing/cn/1.htm";
				 
				break;
			case 2:
				url = "http://www.umei.cc/p/gaoqing/gangtai/1.htm";
				break;
			case 3:
				url = "http://www.umei.cc/p/gaoqing/xiuren_VIP/1.htm";
				break;
			case 4:
				url = "http://www.umei.cc/p/gaoqing/oumei/1.htm";
				break;
			default:
				break;
			}
			UmeiFragment fragment = UmeiFragment.newInstance(url);
			return fragment;
			// switch (arg0) {
			// case TAB_INDEX_TAB_1:
			// Fragment fragment = new WaterfallFragment();
			// return fragment;
			//
			// case TAB_INDEX_TAB_2:
			// return new WaterfallFragment();
			//
			// case TAB_INDEX_TAB_3:
			// return new WaterfallFragment();
			//
			// case TAB_INDEX_TAB_4:
			// return new WaterfallFragment();
			// }

//			throw new IllegalStateException("No fragment at position " + arg0);
		}

		@Override
		public int getCount() {
			return TAB_COUNT;
		}
	}
}

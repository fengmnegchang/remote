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
	public final static int TAB_COUNT = 4;

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
		tab.setContentDescription("Tab 1");
		tab.setText("Tab 1");
		tab.setTabListener(mTabListener);
		actionBar.addTab(tab);
	}

	private void setupTest2() {
		Tab tab = actionBar.newTab();
		tab.setContentDescription("Tab 2");
		tab.setText("Tab 2");
		tab.setTabListener(mTabListener);
		actionBar.addTab(tab);
	}

	private void setupTest3() {
		Tab tab = actionBar.newTab();
		tab.setContentDescription("Tab 3");
		tab.setText("Tab 3");
		tab.setTabListener(mTabListener);
		actionBar.addTab(tab);
	}

	private void setupTest4() {
		Tab tab = actionBar.newTab();
		tab.setContentDescription("Tab 4");
		tab.setText("Tab 4");
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
			WaterfallFragment fragment = new WaterfallFragment();
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

	public static class WaterfallFragment extends Fragment implements
			LazyScrollView.OnScrollListener {

		/** Called when the activity is first created. */
		private LinearLayout layout01, layout02, layout03;// 3列

		// private List<String> image_filenames = new ArrayList<String>(); //

		private ImageDownLoadAsyncTask asyncTask;

		// private AssetManager assetManager;

		private int Image_width; 
		private int x, y; 
		private int current_page = 0; 
		private int count = 15; 
		private LazyScrollView lazyScrollView; 
		private LinearLayout progressbar; 
		private TextView loadtext; 
		private String tag = "WaterfallActivity";
		private WaterfallService waterfallService;
		private static Waterfalls falls;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.activity_waterfall,
					container, false);

			lazyScrollView = (LazyScrollView) view
					.findViewById(R.id.lazyscrollview);
			progressbar = (LinearLayout) view.findViewById(R.id.progressbar);
			loadtext = (TextView) view.findViewById(R.id.loadtext);
			lazyScrollView.getView();
			lazyScrollView.setOnScrollListener(this);
			layout01 = (LinearLayout) view.findViewById(R.id.layout01);
			layout02 = (LinearLayout) view.findViewById(R.id.layout02);
			layout03 = (LinearLayout) view.findViewById(R.id.layout03);

			AssetManager assetManager = getActivity().getAssets();
			Image_width = (getActivity().getWindowManager().getDefaultDisplay()
					.getWidth() - 4) / 3;
			// try {
			// StringBuilder content = new StringBuilder();
			// image_filenames = Arrays.asList(assetManager.list("waterfall"));
			// content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+"\n");
			// content.append("<waterfalls>"+"\n");
			// int i = 1;
			// for(String s:image_filenames){
			// Log.d(tag,s);
			// content.append("    <waterfall id=\""+i+"\">\n");
			// content.append("        <title>美女"+i+"</title>\n");
			// content.append("        <imageName>"+s+"</imageName>\n");
			// content.append("        <url>https://raw.githubusercontent.com/fengmnegchang/remote/master/URose/assets/waterfall/"+s+"</url>\n");
			// content.append("        <subName></subName>\n");
			// content.append("        <subUrl></subUrl>\n");
			// content.append("        <subAction></subAction>\n");
			// content.append("    </waterfall>\n");
			// i++;
			// }
			// content.append("</waterfalls>");
			// String path = Environment.getExternalStorageDirectory()
			// .getPath()
			// +"/"+"waterfalls.xml";
			// File fileName = new File(path);
			// FileOutputStream fileOutputStream = new
			// FileOutputStream(fileName);
			// fileOutputStream.write(content.toString().getBytes("utf-8"));
			// fileOutputStream.flush();
			// fileOutputStream.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }

			// for(Waterfall water : falls.getFalls()){
			// image_filenames.add(water.getUrl());
			// }
			addImage(current_page, count);
			return view;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// setContentView(R.layout.activity_waterfall);

			waterfallService = new WaterfallService(getActivity());
			falls = waterfallService.getUmeiWaters();

		}

		public void addBitMapToImage(String imagePath, int j, int i,
				String imgName) {
			ImageView imageView = getImageview();
			asyncTask = new ImageDownLoadAsyncTask(getActivity()
					.getApplicationContext(), imagePath, imageView,
					Image_width, imgName);
			asyncTask.setProgressbar(progressbar);
			asyncTask.setLoadtext(loadtext);
			asyncTask.execute(imagePath);

			imageView.setTag(i);
			if (j == 0) {
				layout01.addView(imageView);
			} else if (j == 1) {
				layout02.addView(imageView);
			} else if (j == 2) {
				layout03.addView(imageView);
			}

			imageView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					Toast.makeText(getActivity().getApplicationContext(),
							"您点击了" + v.getTag() + "个Item", Toast.LENGTH_SHORT)
							.show();
					return false;
				}
			});
//			imageView.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Toast.makeText(getActivity().getApplicationContext(),
//							"您点击了" + v.getTag() + "个Item", Toast.LENGTH_SHORT)
//							.show();
//
//				}
//			});
		}

		public ImageView getImageview() {
			ImageView imageView = new ImageView(getActivity()
					.getApplicationContext());
			LayoutParams layoutParams = new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			imageView.setLayoutParams(layoutParams);
			imageView.setPadding(2, 0, 2, 2);
			return imageView;
		}

		@Override
		public void onBottom() {
			addImage(++current_page, count);
		}

		private void addImage(int current_page, int count) {
			for (int x = current_page * count; x < (current_page + 1) * count
					&& x < falls.getSize(); x++) {
				addBitMapToImage(falls.getFalls().get(x).getUrl(), y, x, falls
						.getFalls().get(x).getImageName());
				y++;
				if (y >= 3)
					y = 0;
			}

		}

		@Override
		public void onTop() {
			Log.d(tag, "top");
		}

		@Override
		public void onScroll() {
			Log.d(tag, "scroll");
		}
	}

}

package com.fgj.urose;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fgj.urose.remote.sax.entity.MainOnline;
import com.fgj.urose.remote.sax.ksoap.MainAdvertiseService;
import com.fgj.urose.waterfall.WaterfallActivity;

public class MainActivity extends FragmentActivity implements
		OnPageChangeListener, OnClickListener {
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	GridView menuView;
	// int[] menus = new int[]{R.string.g1,R.string.g2,R.string.g3,
	// R.string.g4,R.string.g5,R.string.g6};
	// int[] icons = new int[]{R.drawable.g1,R.drawable.g2,R.drawable.g3,
	// R.drawable.g4,R.drawable.g5,R.drawable.g6};

	private ImageView[] dots;
	private int currentIndex;
	private MainAdvertiseService advertService;
	private static MainOnline online;
	private static Drawable drawable;
	private static AdvertAsyncTask taks;

	private static Bitmap bitmap;
	private static MenuAsyncTask menuTaks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		advertService = new MainAdvertiseService(MainActivity.this);
		online = advertService.getMainOnline();

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(this);

		menuView = (GridView) findViewById(R.id.grid_menu);
		menuView.setAdapter(new GridAapter(MainActivity.this));
		menuView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(),
						"您点击了" + position + "个Item", Toast.LENGTH_SHORT).show();
				com.fgj.urose.MainActivity.GridAapter.ViewHolder holder = (com.fgj.urose.MainActivity.GridAapter.ViewHolder) view.getTag();
				if (position == 0) {
					try {
						Class onwClass = Class.forName(holder.subUrl.getText().toString());
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, onwClass);
						startActivity(intent);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, WaterfallActivity.class);
					startActivity(intent);
				}
			}
		});

		initDots();
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		dots = new ImageView[online.getSize()];
		for (int i = 0; i < online.getSize(); i++) {
			// dots[i] = (ImageView) ll.getChildAt(i);
			ImageView img = new ImageView(getApplicationContext());
			img.setImageResource(R.drawable.dot);
			img.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			img.setPadding(15, 15, 15, 15);
			img.setClickable(true);
			dots[i] = img;

			dots[i].setEnabled(true);
			dots[i].setOnClickListener(this);
			dots[i].setTag(i);

			ll.addView(dots[i]);
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);
	}

	private class GridAapter extends BaseAdapter {
		Context context;

		public GridAapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			// return menus.length;
			return online.getMenuSize();
		}

		@Override
		public Object getItem(int position) {
			// return menus[position];
			return online.getMenus().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater
						.from(getApplicationContext());
				convertView = inflater.inflate(R.layout.item_grid_view, null);
				viewHolder = new ViewHolder();
				viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
				viewHolder.title = (TextView) convertView.findViewById(R.id.title);
				viewHolder.subUrl = (TextView) convertView.findViewById(R.id.subUrl);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			// viewHolder.img.setImageResource(icons[position]);
			// viewHolder.title.setText(menus[position]);
			menuTaks = new MenuAsyncTask(viewHolder.img, context, online
					.getMenus().get(position).getImageName());
			menuTaks.execute(online.getMenus().get(position).getUrl());
			viewHolder.title.setText(online.getMenus().get(position).getTitle());
			viewHolder.subUrl.setText(online.getMenus().get(position).getSubUrl());
			return convertView;
		}

		private class ViewHolder {
			ImageView img;
			TextView title;
			TextView subUrl;
		}

	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			if (online.getSize() > 0) {
				return online.getSize();
			}
			return 0;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}

	public static class DummySectionFragment extends Fragment {
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			RelativeLayout dummyImg = (RelativeLayout) rootView
					.findViewById(R.id.section_img);

			int position = getArguments().getInt(ARG_SECTION_NUMBER);
			// try {
			// InputStream inputStream =
			// getActivity().getAssets().open("main"+position+".png");
			// Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			// dummyImg.setImageBitmap(bitmap);
			// } catch (IOException e) {
			// e.printStackTrace();
			// dummyImg.setImageResource(R.drawable.ic_launcher);
			// }
			taks = new AdvertAsyncTask(dummyImg, getActivity()
					.getApplicationContext(), online.getAdverts()
					.get(position - 1).getImageName());
			taks.execute(online.getAdverts().get(position - 1).getUrl());

			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}

	/**
	 * 设置当前的引导页
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= online.getSize()) {
			return;
		}

		mViewPager.setCurrentItem(position);
	}

	/**
	 * 这只当前引导小点的选中
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > online.getSize() - 1
				|| currentIndex == positon) {
			return;
		}

		dots[positon].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = positon;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		setCurDot(arg0);

	}

	private static class AdvertAsyncTask extends
			AsyncTask<String, Integer, Drawable> {
		private RelativeLayout layout;
		Context context;
		String localResName;

		public AdvertAsyncTask(RelativeLayout layout, Context context,
				String localResName) {
			this.layout = layout;
			this.context = context;
			this.localResName = localResName;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			layout.setBackground(null);
		}

		@Override
		protected void onPostExecute(Drawable result) {
			super.onPostExecute(result);
			Message msg = new Message();
			if (result != null) {
				drawable = result;
				layout.setBackground(drawable);
				msg.what = 1;
			} else {
				msg.what = 0;
				int resId = context.getResources().getIdentifier(localResName,
						"drawable", context.getPackageName());
				layout.setBackgroundResource(resId);
			}
			handleMessage(msg);
		}

		protected void handleMessage(Message msg) {

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
		}

		@Override
		protected void onCancelled(Drawable result) {
			super.onCancelled(result);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Drawable doInBackground(String... params) {
			final Drawable dr;
			try {
				HttpClient hc = new DefaultHttpClient();
				HttpGet hg = new HttpGet(params[0]);
				HttpResponse hr = hc.execute(hg);
				// bm = BitmapFactory.decodeStream(hr.getEntity().getContent());
				dr = Drawable.createFromStream(hr.getEntity().getContent(), "");
			} catch (Exception e) {
				return null;
			}
			return dr;
		}
	}

	private static class MenuAsyncTask extends
			AsyncTask<String, Integer, Bitmap> {
		ImageView img;
		Context context;
		String localResName;

		public MenuAsyncTask(ImageView img, Context context, String localResName) {
			this.img = img;
			this.context = context;
			this.localResName = localResName;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			img.setImageBitmap(null);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			Message msg = new Message();
			if (result != null) {
				bitmap = result;
				img.setImageBitmap(bitmap);
				msg.what = 1;
			} else {
				msg.what = 0;
				int resId = context.getResources().getIdentifier(localResName,
						"drawable", context.getPackageName());
				img.setImageResource(resId);
			}
			handleMessage(msg);
		}

		protected void handleMessage(Message msg) {

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
		}

		@Override
		protected void onCancelled(Bitmap result) {
			super.onCancelled(result);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			final Bitmap bm;
			try {
				HttpClient hc = new DefaultHttpClient();
				HttpGet hg = new HttpGet(params[0]);
				HttpResponse hr = hc.execute(hg);
				bm = BitmapFactory.decodeStream(hr.getEntity().getContent());
				// menuDrawable =
				// Drawable.createFromStream(hr.getEntity().getContent(), "");
			} catch (Exception e) {
				return null;
			}
			return bm;
		}
	}
}

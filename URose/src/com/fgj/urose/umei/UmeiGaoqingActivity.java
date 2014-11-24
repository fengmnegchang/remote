package com.fgj.urose.umei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fgj.urose.R;
import com.fgj.urose.remote.sax.entity.WelcomePager;
import com.fgj.urose.remote.sax.ksoap.SelectPagerService;

public class UmeiGaoqingActivity extends FragmentActivity  implements OnPageChangeListener,OnClickListener{
 
	SectionsPagerAdapterNew mSectionsPagerAdapter;
 
	ViewPager mViewPager;
	
	boolean misScrolled = true;
	 
    private ImageView[] dots ;  
    private int currentIndex;  
    
    private SelectPagerService pagerService;
    private static WelcomePager welPager;
    private static Drawable drawable;
    private static WelPagerAsyncTask taks;
    
    private List<DummySectionFragment> fragments = Collections.synchronizedList(new ArrayList<DummySectionFragment>());
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_umei_gaoqing);
         
		pagerService = new SelectPagerService(UmeiGaoqingActivity.this);
		welPager = pagerService.getWelcomePager();
		
		for(int i=1;i<=welPager.getSize();i++){
			fragments.add(DummySectionFragment.newInstance(i));
		}
//		fragments.add(DummySectionFragment.newInstance(1));
//        fragments.add(DummySectionFragment.newInstance(2));
//        fragments.add(DummySectionFragment.newInstance(3));
        mSectionsPagerAdapter = new SectionsPagerAdapterNew(getSupportFragmentManager(),fragments); 
		 
//		mSectionsPagerAdapter = new SectionsPagerAdapterNew(
//				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		
        initDots();  
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		dots = new ImageView[welPager.getSize()];
		for (int i = 0; i < welPager.getSize(); i++) {
			ImageView img = new ImageView(getApplicationContext());
			img.setImageResource(R.drawable.dot);
			img.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
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

	 /**
     * A {@link SectionsPagerAdapterNew} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapterNew extends FragmentPagerAdapter {

        private List<DummySectionFragment> fragments;

        public SectionsPagerAdapterNew(FragmentManager fm) {
            super(fm);
        }

        public SectionsPagerAdapterNew(FragmentManager fm, List<DummySectionFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        
		@Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	Locale l = Locale.getDefault();
			return welPager.getPagers().get(position).getTitle().toUpperCase(l);
        }
    }
	 
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
//		private List<View> views;  
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

//		public SectionsPagerAdapter(FragmentManager fm,
//				List<View> views) {
//			super(fm);
//			this.views = views;
//		}

//	    @Override  
//	    public void destroyItem(View arg0, int arg1, Object arg2) {  
//	        ((ViewPager) arg0).removeView(views.get(arg1));       
//	    }  
	  
	    @Override  
	    public void finishUpdate(View arg0) {  
	          
	    }  
	    
		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

//		@Override
//		public Object instantiateItem(View arg0, int arg1) {
//
//			((ViewPager) arg0).addView(views.get(arg1), 0);
//
//			return views.get(arg1);
//		}

		@Override
		public int getCount() {
			if (welPager.getSize() > 0)  
	        {  
	            return welPager.getSize();  
	        } 
			return 0;
		}

//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			return (arg0 == arg1);
//		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			return welPager.getPagers().get(position).getTitle().toUpperCase(l);
//			switch (position) {
//			case 0:
//				return getString(R.string.title_section1).toUpperCase(l);
//			case 1:
//				return getString(R.string.title_section2).toUpperCase(l);
//			case 2:
//				return getString(R.string.title_section3).toUpperCase(l);
//			}
//			return null;
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
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	@SuppressLint("ValidFragment")
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		private int sectionNumber;
		
		public DummySectionFragment() {
		}
		
		public DummySectionFragment(int sectionNumber) {
	            this.sectionNumber = sectionNumber;
	        }
		
		public static DummySectionFragment newInstance(int sectionNumber) {
			DummySectionFragment fragment = new DummySectionFragment(sectionNumber);
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_welcome_dummy,
					container, false);
			RelativeLayout dummyImg = (RelativeLayout) rootView
					.findViewById(R.id.section_img);
			
			
//			int position = getArguments().getInt(ARG_SECTION_NUMBER);
			int position = sectionNumber;
//			int resId = getResources().getIdentifier("wel"+position, 
//					"drawable",
//					getActivity().getPackageName());
//			dummyImg.setBackgroundResource(resId);
			
			taks = new WelPagerAsyncTask(dummyImg,
					getActivity().getApplicationContext(),
					welPager.getPagers().get(position-1).getImageName()){
//				@Override
//				protected void handleMessage(Message msg) {
//					super.handleMessage(msg);
//					if(msg.what==1){
//						dummyImg.setBackground(drawable);
//					}
//				}
			};
			taks.execute(welPager.getPagers().get(position-1).getUrl());
			
//			dummyTextView.setText(Integer.toString(
//					getArguments().getInt(
//					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
	 /** 
     *设置当前的引导页  
     */  
    private void setCurView(int position)  
    {  
        if (position < 0 || position >= welPager.getSize()) {  
            return;  
        }  
  
        mViewPager.setCurrentItem(position);  
    }  
  
    /** 
     *这只当前引导小点的选中  
     */  
    private void setCurDot(int position)  
    {  
        if (position < 0 || position > welPager.getSize() - 1 || currentIndex == position) {  
            return;  
        }  
  
        dots[position].setEnabled(false);  
        dots[currentIndex].setEnabled(true);  
  
        currentIndex = position; 
//        if(position == 0){
//        	taks = new WelPagerAsyncTask();
//    		taks.execute(welPager.getPagers().get(position).getUrl());
//        }
    }  
    
	@Override
	public void onPageScrollStateChanged(int state) {
		switch (state) {
		case ViewPager.SCROLL_STATE_DRAGGING:
			misScrolled = false;
			break;
		case ViewPager.SCROLL_STATE_SETTLING:
			misScrolled = true;
			break;
		case ViewPager.SCROLL_STATE_IDLE:
			if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1 && !misScrolled) {
			}
			misScrolled = true;
			break;
		}
	}

	@Override
	public void onPageScrolled(int position, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		 setCurDot(position);  
	}

	@Override
	public void onClick(View v) {
		int position = (Integer)v.getTag();  
        setCurView(position);  
        setCurDot(position);  
	}
	
	
	private static class WelPagerAsyncTask extends AsyncTask<String,Integer,Drawable>{
		private RelativeLayout layout;
		private Context context;
		private String localResName;
		public WelPagerAsyncTask(RelativeLayout layout,Context context,String localResName){
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
			if(result != null) {
				drawable = result;
				layout.setBackground(drawable);
				msg.what = 1;
            }else {
            	msg.what = 0;
            	int resId = context.getResources().getIdentifier(localResName, 
    					"drawable",
    					context.getPackageName());
            	layout.setBackgroundResource(resId);
            }
			handleMessage(msg);
		}
		
		protected void handleMessage(Message msg){
			
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
//                bm = BitmapFactory.decodeStream(hr.getEntity().getContent());
                dr = Drawable.createFromStream(hr.getEntity().getContent(), "");
            } catch (Exception e) {
                return null;
            }
            return dr;
		}
		
	}

}

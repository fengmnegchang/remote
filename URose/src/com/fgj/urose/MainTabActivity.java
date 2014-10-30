package com.fgj.urose;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.fgj.urose.remote.sax.entity.MainTab;
import com.fgj.urose.remote.sax.entity.SubTab;
import com.fgj.urose.remote.sax.ksoap.SubTabService;

@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity implements OnCheckedChangeListener{
	
	TabHost mTabHost;
	RadioGroup mRadioGroup;
//	RadioButton radio1,radio2,radio3,radio4;
//	TabGestureListener mTabGestureListener;
//	GestureDetector mGestureDetector;
//	int mCurrentTabIndex = 0;
	
	SubTabService subTabService;
	MainTab mainTab;
	static Drawable radioBg;
	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.activity_main_tab);
		setContentView(R.layout.activity_sliding_menu);
		
		subTabService = new SubTabService(this);
		mainTab = subTabService.getTabs();
		
		mTabHost =  getTabHost();

		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		
		WindowManager wmx = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wmx.getDefaultDisplay().getMetrics(dm);
		
		for(SubTab tab :mainTab.getTabs()){
			TabSpec tab_main = mTabHost.newTabSpec(tab.getTitle());
			
			try {
				Class onwClass = Class.forName(tab.getSubUrl());
				tab_main.setContent(new Intent(this,  onwClass))
				.setIndicator(tab.getTitle());
				mTabHost.addTab(tab_main);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		   RadioButton radio = (RadioButton) LayoutInflater.from(this).
				   inflate(R.layout.radio_style, null);  
			radio.setId(tab.getId());
			radio.setText(tab.getTitle());
			radio.setBackgroundResource(R.drawable.selector_tab_main_bg);
			radio.setPadding(20, 5, 20, 5);
			
			mRadioGroup.addView(radio,new LayoutParams(dm.widthPixels/mainTab.getSize()
					,LayoutParams.WRAP_CONTENT));
			
			TabAsyncTask tabTask = new TabAsyncTask(radio,this);
			tabTask.execute(tab.getUrl());
		}
//		TabSpec tab_main1 = mTabHost.newTabSpec(getString(R.string.tab_main1));
//		TabSpec tab_main2 = mTabHost.newTabSpec(getString(R.string.tab_main2));
//		TabSpec tab_main3 = mTabHost.newTabSpec(getString(R.string.tab_main3));
//		TabSpec tab_main4 = mTabHost.newTabSpec(getString(R.string.tab_main4));

//		tab_main1.setContent(new Intent(this,  MainActivity.class))
//				.setIndicator(getString(R.string.tab_main1));
//		tab_main2.setContent(new Intent(this, WaterfallActivity.class))
//				.setIndicator(getString(R.string.tab_main2));
//		tab_main3.setContent(new Intent(this, WaterfallActivity.class))
//				.setIndicator(getString(R.string.tab_main3));
//		tab_main4.setContent(new Intent(this, WaterfallActivity.class))
//				.setIndicator(getString(R.string.tab_main4));
		 

//		mTabHost.addTab(tab_main1);
//		mTabHost.addTab(tab_main2);
//		mTabHost.addTab(tab_main3);
//		mTabHost.addTab(tab_main4);
		
//		mTabGestureListener = new TabGestureListener();
//        mGestureDetector = new GestureDetector(mTabGestureListener);
		
//		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
//		radio1 = (RadioButton) findViewById(R.id.radio1);
//		radio2 = (RadioButton) findViewById(R.id.radio2);
//		radio3 = (RadioButton) findViewById(R.id.radio3);
//		radio4 = (RadioButton) findViewById(R.id.radio4);
		
		mRadioGroup.setOnCheckedChangeListener(this);
		
//		mTabGestureListener.setCurrentIndex(1);
//		radio1.setChecked(true);
	}
	
//	@Override
//	public boolean dispatchTouchEvent(final MotionEvent event) {
//		if (mGestureDetector != null && mGestureDetector.onTouchEvent(event)) {
//			event.setAction(MotionEvent.ACTION_CANCEL);
//		}
//		return super.dispatchTouchEvent(event);
//	}
//	 
	
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		if (mGestureDetector != null && mGestureDetector.onTouchEvent(event)) {
//			return mGestureDetector.onTouchEvent(event);
//		}
//		return super.onTouchEvent(event);
//	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		for(SubTab tab :mainTab.getTabs()){
			if(checkedId == tab.getId()){
				mTabHost.setCurrentTabByTag(tab.getTitle());
				break;
			}
		}
//		switch (checkedId) {
//		case R.id.radio1:
//			mTabHost.setCurrentTabByTag(getString(R.string.tab_main1));
//			break;
//		case R.id.radio2:
//			mTabHost.setCurrentTabByTag(getString(R.string.tab_main2));
//			break;
//		case R.id.radio3:
//			mTabHost.setCurrentTabByTag(getString(R.string.tab_main3));
//			break;
//		case R.id.radio4:
//			mTabHost.setCurrentTabByTag(getString(R.string.tab_main4));
//			break;
//		default:
//			break;
//		}
	}
	
	private static class TabAsyncTask extends AsyncTask<String,Integer,Drawable>{
		RadioButton radio;
		Context context;
		public TabAsyncTask(RadioButton radio,Context context){
			this.radio = radio;
			this.context = context;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			radio.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
		}

		@Override
		protected void onPostExecute(Drawable result) {
			super.onPostExecute(result);
			Message msg = new Message();
			if(result != null) {
				radioBg = result;
				radio.setCompoundDrawablesRelativeWithIntrinsicBounds(null, radioBg, null, null);
				msg.what = 1;
            }else {
            	msg.what = 0;
            	Drawable drawable= context.getResources().getDrawable(R.drawable.icon); 
            	radio.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
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
			final Drawable menuDrawable;
			try {
				HttpClient hc = new DefaultHttpClient();
	            HttpGet hg = new HttpGet(params[0]); 
                HttpResponse hr = hc.execute(hg);
//                bm = BitmapFactory.decodeStream(hr.getEntity().getContent());
                menuDrawable = Drawable.createFromStream(hr.getEntity().getContent(), "");
            } catch (Exception e) {
                return null;
            }
            return menuDrawable;
		}
	}

//	private final class TabGestureListener extends SimpleOnGestureListener {
//        private static final int SWIPE_MIN_DISTANCE = 60;
//        private static final int SWIPE_THRESHOLD_VELOCITY = 100;
//
//        @Override
//        public boolean onFling(final MotionEvent e1, final MotionEvent e2, final float velocityX, final float velocityY) {
//            int slideDistanceX = (int) (e2.getX() - e1.getX());
//            int slideDistanceY = (int) (e2.getY() - e1.getY());
//            boolean isIgnoreFling = (slideDistanceY != 0 && Math.abs(slideDistanceX / slideDistanceY) <= 0.5);
//
//            if (isIgnoreFling || (Math.abs(velocityX) <= SWIPE_THRESHOLD_VELOCITY)) {
//                return false;
//            }
//            if ((e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) && (mCurrentTabIndex < 4)) {
//                mCurrentTabIndex++;
//                updateTabItemState(mCurrentTabIndex);
//                return true;
//            }
//            if ((e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) && (mCurrentTabIndex > 0)) {
//                mCurrentTabIndex--;
//                updateTabItemState(mCurrentTabIndex);
//                return true;
//            }
//            return false;
//        }
//
//        public void setCurrentIndex(final int index) {
//            mCurrentTabIndex = index;
//        }
//    }
	
//	private void updateTabItemState(final int index) {
//        switch (index) {
//            case 1:
//            	radio1.setChecked(true);
//            	radio2.setChecked(false);
//            	radio3.setChecked(false);
//            	radio4.setChecked(false);
//                break;
//            case 2:
//            	radio1.setChecked(false);
//            	radio2.setChecked(true);
//            	radio3.setChecked(false);
//            	radio4.setChecked(false);
//                break;
//            case 3:
//            	radio1.setChecked(false);
//            	radio2.setChecked(false);
//            	radio3.setChecked(true);
//            	radio4.setChecked(false);
//                break;
//            case 4:
//            	radio1.setChecked(false);
//            	radio2.setChecked(false);
//            	radio3.setChecked(false);
//            	radio4.setChecked(true);
//                break;
//            default:
//                break;
//        }
//    }
}

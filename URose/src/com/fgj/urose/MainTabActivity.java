package com.fgj.urose;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.fgj.urose.waterfall.WaterfallActivity;

@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity implements OnCheckedChangeListener{
	
	TabHost mTabHost;
	RadioGroup mRadioGroup;
	RadioButton radio1,radio2,radio3,radio4;
//	TabGestureListener mTabGestureListener;
//	GestureDetector mGestureDetector;
//	int mCurrentTabIndex = 0;
	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_tab);
		
		mTabHost =  getTabHost();

		TabSpec tab_main1 = mTabHost.newTabSpec(getString(R.string.tab_main1));
		TabSpec tab_main2 = mTabHost.newTabSpec(getString(R.string.tab_main2));
		TabSpec tab_main3 = mTabHost.newTabSpec(getString(R.string.tab_main3));
		TabSpec tab_main4 = mTabHost.newTabSpec(getString(R.string.tab_main4));

		tab_main1.setContent(new Intent(this,  MainActivity.class))
				.setIndicator(getString(R.string.tab_main1));
		tab_main2.setContent(new Intent(this, WaterfallActivity.class))
				.setIndicator(getString(R.string.tab_main2));
		tab_main3.setContent(new Intent(this, WaterfallActivity.class))
				.setIndicator(getString(R.string.tab_main3));
		tab_main4.setContent(new Intent(this, WaterfallActivity.class))
				.setIndicator(getString(R.string.tab_main4));
		 

		mTabHost.addTab(tab_main1);
		mTabHost.addTab(tab_main2);
		mTabHost.addTab(tab_main3);
		mTabHost.addTab(tab_main4);
		
//		mTabGestureListener = new TabGestureListener();
//        mGestureDetector = new GestureDetector(mTabGestureListener);
		
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		radio1 = (RadioButton) findViewById(R.id.radio1);
		radio2 = (RadioButton) findViewById(R.id.radio2);
		radio3 = (RadioButton) findViewById(R.id.radio3);
		radio4 = (RadioButton) findViewById(R.id.radio4);
		
		mRadioGroup.setOnCheckedChangeListener(this);
		
//		mTabGestureListener.setCurrentIndex(1);
		radio1.setChecked(true);
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
		switch (checkedId) {
		case R.id.radio1:
			mTabHost.setCurrentTabByTag(getString(R.string.tab_main1));
			break;
		case R.id.radio2:
			mTabHost.setCurrentTabByTag(getString(R.string.tab_main2));
			break;
		case R.id.radio3:
			mTabHost.setCurrentTabByTag(getString(R.string.tab_main3));
			break;
		case R.id.radio4:
			mTabHost.setCurrentTabByTag(getString(R.string.tab_main4));
			break;
		default:
			break;
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

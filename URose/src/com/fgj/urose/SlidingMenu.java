package com.fgj.urose;

import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView{
	private LinearLayout mWapper;
	private ViewGroup mContent,mMenu;
	private int mScreenWidth;
	private int mRightPadding = 200;
	private boolean first = false;
	private int mMenuWidth;
	

	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		WindowManager wma = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wma.getDefaultDisplay().getMetrics(dm);
		
		mScreenWidth = dm.widthPixels;
		
		mRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, 
				context.getResources().getDisplayMetrics());
	}
 
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(!first){
			mWapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWapper.getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(1);
			
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mRightPadding;
			mContent.getLayoutParams().width = mScreenWidth;
			first = true;
		}
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(ev.getAction() == MotionEvent.ACTION_UP){
			int scrollX = getScrollX();
			if(scrollX>= mMenuWidth/2){
				this.smoothScrollTo(mMenuWidth, 0);
			}else{
				this.smoothScrollTo(0, 0);
			}
		}
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if(changed){
			this.scrollTo(mMenuWidth, 0);
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l*1.0f/mMenuWidth;
		ViewHelper.setTranslationX(mMenu, mMenuWidth*scale*0.7f);
		
		float rightScale = 0.7f + 0.3f*scale;
		float leftScale = 1.0f - 0.3f*scale;
		float leftAlf = 0.6f + 0.4f*(1-scale);
		
		ViewHelper.setScaleX(mMenu, leftScale);
		ViewHelper.setScaleY(mMenu, leftScale);
		ViewHelper.setAlpha(mMenu, leftAlf);
		
		
		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight()/2);
		ViewHelper.setScaleX(mContent, rightScale);
		ViewHelper.setScaleY(mContent, rightScale);
	}

	
	
}

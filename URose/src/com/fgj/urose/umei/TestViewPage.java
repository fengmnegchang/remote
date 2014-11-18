package com.fgj.urose.umei;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TestViewPage extends ViewPager{  
    public TestViewPage(Context context) {  
        super(context);  
    }  
  
    public TestViewPage(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }

//    public boolean dispatchTouchEvent(MotionEvent ev)
//    {
//      boolean ret = super.dispatchTouchEvent(ev);
//      if(ret) 
//      {
//        requestDisallowInterceptTouchEvent(true);
//      }
//      return ret;
//    } 
} 
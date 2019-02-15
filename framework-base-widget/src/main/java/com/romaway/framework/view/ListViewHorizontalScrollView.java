package com.romaway.framework.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/*
 * 自定义的 滚动控件
 * 重载了 onScrollChanged（滚动条变化）,监听每次的变化通知给 观察(此变化的)观察者
 * 可使用 AddOnScrollChangedListener 来订阅本控件的 滚动条变化
 * */

 public class ListViewHorizontalScrollView extends HorizontalScrollView {

		
	    ScrollViewObserver mScrollViewObserver = new ScrollViewObserver();

	    public ListViewHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	    }

	    public ListViewHorizontalScrollView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }

	    public ListViewHorizontalScrollView(Context context) {
	        super(context);
	    }

	    float x1,y1,x2,y2;
	    @Override
	    public boolean onTouchEvent(MotionEvent event) {
	    	
	    	//继承了Activity的onTouchEvent方法，直接监听点击事件  
	        if(event.getAction() == MotionEvent.ACTION_DOWN) {  
	            //当手指按下的时候  
	            x1 = event.getX();  
	            y1 = event.getY();  
	        }  
	        if(event.getAction() == MotionEvent.ACTION_MOVE) {  
	            //当手指离开的时候  
	            x2 = event.getX();  
	            y2 = event.getY();  
	            if(y1 - y2 > 30) {  
	            	return true;
	               // Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();  
	            } else if(y2 - y1 > 30) {  
	            	return true;
	                //Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();  
	            } 
	        }  
	       return super.onTouchEvent(event);
	    }

	    
	/*@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (MotionEvent.ACTION_UP == ev.getAction()) {
			// 父view允许滑动
		} else {
			// 禁止父view滑动
			return true;
		}
		return false;

	}*/
	    
	    @Override
	    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
	        /*
	         * 当滚动条移动后，引发 滚动事件。通知给观察者，观察者会传达给其他的。
	         */
	        if (mScrollViewObserver != null  /*&& (l != oldl || t != oldt)*/ ) {
	            mScrollViewObserver.NotifyOnScrollChanged(l, t, oldl, oldt);
	        }
	        super.onScrollChanged(l, t, oldl, oldt);
	    }

	    /*
	     * 订阅 本控件 的 滚动条变化事件
	     */
	    public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
	        mScrollViewObserver.AddOnScrollChangedListener(listener);
	    }

	    /*
	     * 取消 订阅 本控件 的 滚动条变化事件
	     */
	    public void RemoveOnScrollChangedListener(OnScrollChangedListener listener) {
	        mScrollViewObserver.RemoveOnScrollChangedListener(listener);
	    }

	    /*
	     * 当发生了滚动事件时
	     */
	    public static interface OnScrollChangedListener {
	        public void onScrollChanged(int l, int t, int oldl, int oldt);
	    }

	    /*
	     * 观察者
	     */
	    public static class ScrollViewObserver {
	        List<OnScrollChangedListener> mList;

	        public ScrollViewObserver() {
	            super();
	            mList = new ArrayList<OnScrollChangedListener>();
	        }

	        public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
	            mList.add(listener);
	        }

	        public void RemoveOnScrollChangedListener(OnScrollChangedListener listener) {
	            mList.remove(listener);
	        }

	        public void NotifyOnScrollChanged(int l, int t, int oldl, int oldt) {
	            if (mList == null || mList.size() == 0) {
	                return;
	            }
	            for (int i = 0; i < mList.size(); i++) {
	                if (mList.get(i) != null) {
	                    //通知list中所有的订阅者滚动事件，调用OnScrollChangedListenerImp实现类的onScrollChanged
	                    mList.get(i).onScrollChanged(l, t, oldl, oldt);
	                }
	            }
	        }
	 }
  }
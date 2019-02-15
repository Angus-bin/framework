package com.romaway.framework.view.pagerindicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.romaway.commons.log.Logger;

public class RomaWrapContentViewPager extends ViewPager {

    public RomaWrapContentViewPager(Context context) {
        super(context);
    }

    public RomaWrapContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//    int height = 0;
//    //下面遍历所有child的高度
//    for (int i = 0; i < getChildCount(); i++) {
//      View child = getChildAt(i);
//      child.measure(widthMeasureSpec,
//          MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//      int h = child.getMeasuredHeight();
//      if (h > height) //采用最大的view的高度。
//        height = h;
//    }
//    Logger.d("tag", "onMeasure height: "+height);
//    heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
//        MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private float mDownX;
    private float mDownY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            super.onTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            Logger.e("RomaWrapContentViewPager-error", "IllegalArgumentException 错误被活捉了！");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                mDownX = ev.getX();
                mDownY = ev.getY();
            } else {
                // [Bug]修复在三星5.1.1版本上首页快捷菜单项点击不灵敏Bug;
                float distanceX = Math.abs(ev.getX() - mDownX);
                float distanceY = Math.abs(ev.getY() - mDownY);
                if (distanceX > distanceY && Math.abs(distanceX - distanceY) > 5) {
                    return true;
                } else {
                    mDownX = ev.getX();
                    mDownY = ev.getY();
                }
            }
            super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            Logger.e("RomaWrapContentViewPager-error", "IllegalArgumentException 错误被活捉了！");
            e.printStackTrace();
        }
        return false;
    }

//    @Override
//    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
//        if (v != this && v instanceof ViewPager) {
//            return true;
//        }
//        return super.canScroll(v, checkV, dx, x, y);
//    }
//
//    @Override
//    public boolean canScrollHorizontally(int direction) {
//
//        return super.canScrollHorizontally(direction);
//    }

    // 滑动距离及坐标 归还父控件焦点
    private float xDistance, yDistance, xLast, yLast,xDown, mLeft;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.d("touch", "ACTION_DOWN");
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                xDown = ev.getX();
                mLeft = ev.getX();// 解决与侧边栏滑动冲突
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                if (mLeft < 100 || xDistance < yDistance) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    if (getCurrentItem() == 0) {
                        if (curX < xDown) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        } else {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else if (getCurrentItem() == (getAdapter().getCount()-1)) {
                        if (curX > xDown) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        } else {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}

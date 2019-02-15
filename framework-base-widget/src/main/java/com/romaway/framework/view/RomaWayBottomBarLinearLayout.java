package com.romaway.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class RomaWayBottomBarLinearLayout extends LinearLayout{

    final int MAX_VERTICAL_COUNT = 1;
    private int MAX_HORIZONTAL_COUNT = 1;
    
    public RomaWayBottomBarLinearLayout(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public RomaWayBottomBarLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public RomaWayBottomBarLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        this.setOrientation(HORIZONTAL);
        //LayoutInflater.from(context).inflate(R.layout.abs__top_tab_bar_layout, this, true);
    }
    
    /**
     * 初始化子控件
     * 
     * @param viewArry
     */
    public void initChildView(RomaWayShortcutView[] viewArry) {

        MAX_HORIZONTAL_COUNT = viewArry.length;// 设置按钮的个数
        //wlpArr = new WsLayoutParams[MAX_VERTICAL_COUNT * MAX_HORIZONTAL_COUNT];
        mViewArry = viewArry;

        this.removeAllViews();
        for (int i = 0; i < viewArry.length; i++) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    /*ViewGroup.LayoutParams.WRAP_CONTENT*/90,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            viewArry[i].setLayoutParams(lp);
            viewArry[i].setOnClickListener(new MyOnClickListener(i));
            viewArry[i].setOnTouchListener(new MyOnClickListener(i));
            viewArry[i].setTitleColor(viewArry[i].getNormalColor());
            addView(viewArry[i]);
        }
        // 默认
        //setSelectedItem(0);
    }

    private RomaWayShortcutView[] mViewArry;
    private int mCurrentItem = -1;

    public void setSelectedItem(int itemIndex) {

        if (itemIndex < 0)
            return;
        else if (itemIndex >= mViewArry.length)
            itemIndex = mViewArry.length - 1;

        if(itemIndex >= 0){
        
            if(mCurrentItem >= 0){
                mViewArry[mCurrentItem].setTitleColor(mViewArry[mCurrentItem]
                        .getNormalColor());
                mViewArry[mCurrentItem].setImageDrawable(mViewArry[mCurrentItem]
                        .getNormalDrawable());
                mViewArry[mCurrentItem].setStatus(RomaWayShortcutView.STATUS_NORMAL);
            }
            
            mViewArry[itemIndex].setTitleColor(mViewArry[itemIndex]
                    .getPressedColor());
            mViewArry[itemIndex].setImageDrawable(mViewArry[itemIndex]
                    .getPressedDrawable());
            mViewArry[itemIndex].setStatus(RomaWayShortcutView.STATUS_SELECT_FOR_PRESSED);
            
            mCurrentItem = itemIndex;
        }
    }

    public int getCurrentItem(){
        return mCurrentItem;
    }
    
    class MyOnClickListener implements OnClickListener, OnTouchListener {
        private int mItem;

        public MyOnClickListener(int item) {
            this.mItem = item;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            if (mOnSwitchItemListener != null) {
                //setSelectedItem(mItem);
                mOnSwitchItemListener.onClickSwitchItem(v, mItem);
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            /*
             * if (event.getAction() == MotionEvent.ACTION_DOWN){
             * mViewArry[mItem].setTitleColor(mSelectedColor);
             * 
             * }else if(event.getAction() == MotionEvent.ACTION_UP){
             * mViewArry[mItem].setTitleColor(mNormalColor); }
             */
            return false;
        }
    }

    private OnSwitchItemListener mOnSwitchItemListener;

    public void setOnSwitchStockListener(
            OnSwitchItemListener onSwitchItemListener) {
        mOnSwitchItemListener = onSwitchItemListener;
    }

    public interface OnSwitchItemListener {
        public void onClickSwitchItem(View v, int itemIndex);
    }
}

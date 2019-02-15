package com.romaway.framework.view;

import com.android.basephone.widget.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class RomaWayBottomBarWorkspace extends ViewGroup {

	final int MAX_VERTICAL_COUNT = 1;
	private int MAX_HORIZONTAL_COUNT = 1;
	private Context mContext;

	public RomaWayBottomBarWorkspace(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub

	}

	public RomaWayBottomBarWorkspace(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public RomaWayBottomBarWorkspace(Context context, AttributeSet attrs,
									 int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	/**
	 * 初始化子控件
	 * 
	 * @param viewArry
	 */
	public void initChildView(RomaWayShortcutView[] viewArry) {

		if(viewArry == null || viewArry.length == 0)
			return;
		
		MAX_HORIZONTAL_COUNT = viewArry.length;// 设置按钮的个数
		wlpArr = new WsLayoutParams[MAX_VERTICAL_COUNT * MAX_HORIZONTAL_COUNT];
		mViewArry = viewArry;

		this.removeAllViews();
		for (int i = 0; i < viewArry.length; i++) {
			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			viewArry[i].setLayoutParams(lp);
			viewArry[i].setOnClickListener(new MyOnClickListener(i));
			viewArry[i].setOnTouchListener(new MyOnClickListener(i));
			viewArry[i].setTitleColor(viewArry[i].getNormalColor());
			viewArry[i].setBgColor(viewArry[i].getUnBgColor());
			addView(viewArry[i]);
		}
		// 默认
		//setSelectedItem(0);
	}

	private int mCurrentItem = -1;

	public void setSelectedItem(int itemIndex) {

		if (itemIndex < 0 || mViewArry == null){
			return;
		}else if (itemIndex >= mViewArry.length)
			itemIndex = mViewArry.length - 1;

		if(itemIndex >= 0){
		
    		if(mCurrentItem >= 0){
        		mViewArry[mCurrentItem].setTitleColor(mViewArry[mCurrentItem].getNormalColor());
//        		mViewArry[mCurrentItem].setImageDrawable(mViewArry[mCurrentItem].getNormalDrawable());
				mViewArry[mCurrentItem].setImageBitmap(mViewArry[mCurrentItem].getmNormalBitmap());
        		mViewArry[mCurrentItem].setStatus(RomaWayShortcutView.STATUS_NORMAL);
        		mViewArry[mCurrentItem].setBgColor(mViewArry[mCurrentItem].getUnBgColor());
    		}
    		
    		mViewArry[itemIndex].setStatus(RomaWayShortcutView.STATUS_SELECT_FOR_PRESSED);
    		mViewArry[itemIndex].setTitleColor(mViewArry[itemIndex].getPressedColor());
			if (mContext.getResources().getBoolean(R.bool.is_show_bottomBar_select_background)){
				mViewArry[itemIndex].setBgColor(mViewArry[itemIndex].getBgColor());
			} else {
				mViewArry[itemIndex].setBgColor(0x00000000);
			}
    		///mViewArry[itemIndex].setImageDrawable(mViewArry[itemIndex]
    		//		.getPressedDrawable());
//    		mViewArry[itemIndex].setPressedDrawable();
			mViewArry[itemIndex].setmPressedBitmap();
    		mCurrentItem = itemIndex;
		}
	}

	public int getCurrentItem(){
	    return mCurrentItem;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View view = getChildAt(i);
			WsLayoutParams lp = wlpArr[i];// (WsLayoutParams)
											// view.getLayoutParams();
			view.layout(lp.x, lp.y, lp.x + lp.width, lp.y + lp.height);
		}
	}

	WsLayoutParams[] wlpArr;
	private RomaWayShortcutView[] mViewArry;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO: currently ignoring padding
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

		int measuredChildHeight = 0;
		int measuredChildWidth = 0;

		if (widthSpecMode == MeasureSpec.UNSPECIFIED
				|| heightSpecMode == MeasureSpec.UNSPECIFIED) {
			throw new RuntimeException(
					"Dockbar cannot have UNSPECIFIED dimensions");
		}

		int cellWidth = widthSpecSize / MAX_HORIZONTAL_COUNT;
		int cellHeight = heightSpecSize / MAX_VERTICAL_COUNT;

		final int count = getChildCount();

		for (int i = 0; i < count; i++) {
			View childView = getChildAt(i);
			// 将计算的宽和高传递给子控件中
			childView.measure(View.MeasureSpec.makeMeasureSpec(cellWidth,
					View.MeasureSpec.AT_MOST), View.MeasureSpec
					.makeMeasureSpec(cellHeight, View.MeasureSpec.AT_MOST));

			measuredChildWidth = childView.getMeasuredWidth();
			measuredChildHeight = childView.getMeasuredHeight();
			WsLayoutParams lp = new WsLayoutParams();

			int cellx = i % MAX_HORIZONTAL_COUNT;
			int celly = i / MAX_HORIZONTAL_COUNT;

			lp.width = cellWidth;
			lp.height = measuredChildHeight;
			
			
			lp.x = cellx * lp.width;
			lp.y = heightSpecSize - measuredChildHeight;//celly * lp.height;//从底部开始进行布局

			wlpArr[i] = lp;
		}

		setMeasuredDimension(widthSpecSize, heightSpecSize);	
	
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

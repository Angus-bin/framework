package com.actionbarsherlock.internal.widget;

import com.android.basephone.widget.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public class ActionBarBottomView extends FrameLayout{

	public ActionBarBottomView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ActionBarBottomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SherlockActionBar,
                R.attr.ROMAactionBarStyle, 0);
		
		int backgroundColor = a.getColor(R.styleable.SherlockActionBar_bottomBarBackground, 
		        context.getResources().getColor(R.color.abs__action_bar_background_color));
		
		int mBottomBarResId = a.getResourceId(
                R.styleable.SherlockActionBar_bottomBarLayout, -1);
		
		a.recycle();
	    setBackgroundColor(backgroundColor);
		
		if(mBottomBarResId > 0){
	        LayoutInflater inflater = LayoutInflater.from(getContext());
	        addView(inflater.inflate(mBottomBarResId,this, false));
		}
	}

	public ActionBarBottomView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

}

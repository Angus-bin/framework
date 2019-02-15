package com.romaway.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class RomaWayBaseViewLayout extends LinearLayout{

	public RomaWayBaseViewLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public RomaWayBaseViewLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RomaWayBaseViewLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public void addView(com.romaway.framework.view.RomaWayBaseViewLoader oldLoader, com.romaway.framework.view.RomaWayBaseViewLoader newLoader, View child) {
		this.removeAllViews();   
		this.addView(child);
		
		
		if(oldLoader != null)
			oldLoader.onPause();
		
		newLoader.onResume();
	}
}

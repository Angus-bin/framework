package com.romaway.framework.view;

import com.android.basephone.widget.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class PreferenceItemNoneView extends PreferenceItemView{
	
	private View mDividerBottomView;
	private View mDividerTitleView;
	
	public PreferenceItemNoneView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public PreferenceItemNoneView(Context context, AttributeSet attrs) {
	    super(context, attrs);
		// TODO Auto-generated constructor stub
	    
	    mDividerBottomView = this.findViewById(R.id.divider_bottom);
        mDividerTitleView = this.findViewById(R.id.divider_title);
        mDividerBottomView.setVisibility(View.GONE);
        mDividerTitleView.setVisibility(View.GONE);
	}
	
}

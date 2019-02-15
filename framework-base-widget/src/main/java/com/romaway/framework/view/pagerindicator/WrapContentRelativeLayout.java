package com.romaway.framework.view.pagerindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class WrapContentRelativeLayout extends RelativeLayout{

	public WrapContentRelativeLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public WrapContentRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

	    int height = 0;
	    //下面遍历所有child的高度
	    for (int i = 0; i < getChildCount(); i++) {
	      View child = getChildAt(i);
	      child.measure(widthMeasureSpec,
	          MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	      int h = child.getMeasuredHeight();
	      if (h > height) //采用最大的view的高度。
	        height = h;
	    }
	    
	    heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
	        MeasureSpec.EXACTLY);

	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	  }

}

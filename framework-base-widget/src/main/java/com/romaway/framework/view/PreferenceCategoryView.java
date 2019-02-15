package com.romaway.framework.view;

import com.android.basephone.widget.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class PreferenceCategoryView extends FrameLayout{
	private Context mContext;
	private TextView mTitleView;
	//private View mDividerView;
	
	public PreferenceCategoryView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public PreferenceCategoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SherlockPreferrence, 0, 0);
        CharSequence titleStr = a.getText(R.styleable.SherlockPreferrence_titleText);
        a.recycle();
        
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.abs__preference_title_layout, this, true);
        
        mTitleView = (TextView) this.findViewById(R.id.title);
        mTitleView.setText(titleStr == null? "" : titleStr);
	}

	public PreferenceCategoryView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
	}
	
	public void setTitleText(String str){
		mTitleView.setText(str);
	}
	
	public void setTitleColor(int color){
		mTitleView.setTextColor(color);
	}
	
	/*public void setDividerColor(int color){
		mDividerView.setBackgroundColor(color);
	}*/
}

package com.romaway.framework.view;

import com.android.basephone.widget.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RomaWayListItemView extends FrameLayout{
	
	private LinearLayout mRoot;
	private View spaceView;
	private ImageView mLeftIconView;
	private TextView mTitleView;
	private TextView mSummaryView;
	
	public RomaWayListItemView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public RomaWayListItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public RomaWayListItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SherlockPreferrence, 0, 0);
		CharSequence titleText = a.getText(R.styleable.SherlockPreferrence_titleText);
		CharSequence summaryText = a.getText(R.styleable.SherlockPreferrence_summaryText);
        Drawable leftIcon = a.getDrawable(R.styleable.SherlockPreferrence_iconLeft);
        a.recycle();
        
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.abs__list_item_singe_line_layout, this, true);
		
		mRoot = (LinearLayout) this.findViewById(R.id.root);
		spaceView = this.findViewById(R.id.space);
		mLeftIconView = (ImageView) this.findViewById(R.id.left_icon);
		mTitleView = (TextView) this.findViewById(R.id.title);
		mSummaryView = (TextView) this.findViewById(R.id.summary);
		
		if(titleText == null)
			mTitleView.setVisibility(View.GONE);
		else
			mTitleView.setText(titleText);
		
		if(summaryText == null)
			mSummaryView.setVisibility(View.GONE);
		else
			mSummaryView.setText(summaryText);
		
		if(leftIcon != null)
			mLeftIconView.setImageDrawable(leftIcon);
	}
	
	public void setSpace(){
		spaceView.setVisibility(View.VISIBLE);
	}
	
	public void setBackgroundForAllKuang(){
		mRoot.setBackgroundResource(R.drawable.roma_edit_bg_bottom_line);
	}
	
	public void setLeftIconImageDrawable(Drawable drawable){
		mLeftIconView.setImageDrawable(drawable);
	}
	
	public void setTitleText(CharSequence text){
		if(text == null)
			mTitleView.setVisibility(View.GONE);
		else{
			mTitleView.setVisibility(View.VISIBLE);
			mTitleView.setText(text);
		}
	}
	
	/*public void setVisibilitySummary(int visibility){
		if(visibility == View.GONE)
			mTitleView.setTextSize(20);
		else
			mTitleView.setTextSize(19);
		
		mSummaryView.setVisibility(visibility);
	}*/
	
	public void setSummaryText(CharSequence text){
		if(text == null)
			mSummaryView.setVisibility(View.GONE);
		else{
			mSummaryView.setVisibility(View.VISIBLE);
			mSummaryView.setText(text);
		}
	}
	
}

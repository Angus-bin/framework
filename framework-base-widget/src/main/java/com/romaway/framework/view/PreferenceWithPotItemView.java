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
import android.widget.TextView;

public class PreferenceWithPotItemView extends FrameLayout{
	
	private ImageView mLeftIconView;
	private TextView mTitleView;
	private TextView mSummaryView;
	private TextView mRightTextView;
	private View mDividerBottomView;
	private View mDividerTitleView;
	private ImageView iv_pot;

	public PreferenceWithPotItemView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public PreferenceWithPotItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public PreferenceWithPotItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SherlockPreferrence, 0, 0);
		CharSequence titleText = a.getText(R.styleable.SherlockPreferrence_titleText);
		CharSequence summaryText = a.getText(R.styleable.SherlockPreferrence_summaryText);
        Drawable leftIcon = a.getDrawable(R.styleable.SherlockPreferrence_iconLeft);
        CharSequence leftIconVisible = a.getText(R.styleable.SherlockPreferrence_iconLeftVisible);
        CharSequence potVisible = a.getText(R.styleable.SherlockPreferrence_potVisible);
        a.recycle();
        
        LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.abs__preference_item_with_pot_layout, this, true);
		
		mLeftIconView = (ImageView) this.findViewById(R.id.left_icon);
		mTitleView = (TextView) this.findViewById(R.id.title);
		mSummaryView = (TextView) this.findViewById(R.id.summary);
		mRightTextView = (TextView) this.findViewById(R.id.right_textView);
		iv_pot = (ImageView) this.findViewById(R.id.iv_pot);
		mDividerBottomView = this.findViewById(R.id.divider_bottom);
		mDividerTitleView = this.findViewById(R.id.divider_title);
		mDividerBottomView.setVisibility(View.GONE);
		mDividerTitleView.setVisibility(View.VISIBLE);
		
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
		if(leftIconVisible!=null){
			String _leftIconVisible = leftIconVisible.toString();
			int visibile = _leftIconVisible.equals("gone")? View.GONE : (_leftIconVisible.equals("visible")?View.VISIBLE:View.INVISIBLE);
			mLeftIconView.setVisibility(visibile);
		}
		if(potVisible!=null){
			String _potVisible = potVisible.toString();
			int visibile = _potVisible.equals("gone")? View.GONE : (_potVisible.equals("visible")?View.VISIBLE:View.INVISIBLE);
			iv_pot.setVisibility(visibile);
		}
		setBottomDivider();
	}
	
	/**
	 * 设置圆点是否显示
	 * @param visible
	 */
	public void setPotVisible(int visible){
		iv_pot.setVisibility(visible);
	}

	public void setLeftIconImageDrawable(Drawable drawable){
		mLeftIconView.setImageDrawable(drawable);
	}
	public void setVisibilityLeftIcon(int visibility){
		mLeftIconView.setVisibility(visibility);
	}
	
	public void setTitleText(CharSequence text){
		if(text == null)
			mTitleView.setVisibility(View.GONE);
		else{
			mTitleView.setVisibility(View.VISIBLE);
			mTitleView.setText(text);
		}
	}
	
	public void setTitleTextSize(int size){
		mTitleView.setTextSize(size);
	}
	
	public void setTitleTextColor(int color){
		mTitleView.setTextColor(color);
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
	
	public void setRightText(CharSequence text){
		mRightTextView.setText(text);
	}
	
	public void setRightTextSize(int size){
		mRightTextView.setTextSize(size);
	}
	
	public void setVisibilityDividerBottom(int visibility){
		mDividerBottomView.setVisibility(visibility);
	}
	
	public void setColorDividerBottom(int color){
        mDividerBottomView.setBackgroundColor(color);
    }
	
	public void setVisibilityDividerTitle(int visibility){
		mDividerTitleView.setVisibility(visibility);
	}
	
	public void setBottomDivider(){
		mDividerBottomView = this.findViewById(R.id.divider_bottom);
		mDividerTitleView = this.findViewById(R.id.divider_title);
		mDividerBottomView.setVisibility(View.VISIBLE);
		mDividerTitleView.setVisibility(View.GONE);
	}
	
	public void setNormalDivider(){
		mDividerBottomView = this.findViewById(R.id.divider_bottom);
		mDividerTitleView = this.findViewById(R.id.divider_title);
		mDividerBottomView.setVisibility(View.GONE);
		mDividerTitleView.setVisibility(View.VISIBLE);
	}
}

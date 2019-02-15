package com.romaway.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.basephone.widget.R;
import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SVGView;

public class PreferenceItemView extends RelativeLayout{

	private View ll_preference_parent;
	private SVGView mLeftIconView;
	private TextView mTitleView;
	private TextView tv_unread_info_counts;
	private TextView mSummaryView;
	private TextView mRightTextView;
	private ImageView arrowRightView;
	private View mDividerBottomView;
	private View mDividerTitleView;
	
	public PreferenceItemView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public PreferenceItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SherlockPreferrence, 0, 0);
        CharSequence titleText = a.getText(R.styleable.SherlockPreferrence_titleText);
        CharSequence layoutType = a.getText(R.styleable.SherlockPreferrence_layoutType);
        CharSequence summaryText = a.getText(R.styleable.SherlockPreferrence_summaryText);
        //Drawable leftIcon = a.getDrawable(R.styleable.SherlockPreferrence_iconLeft);
        CharSequence leftIconVisible = a.getText(R.styleable.SherlockPreferrence_iconLeftVisible);
        a.recycle();
        
        LayoutInflater inflater = LayoutInflater.from(context);
		// 【兼容】多处共用此控件，通过ViewAttr动态替换控件布局:
		if(!TextUtils.isEmpty(layoutType) && context.getResources().getString(R.string.roma_preference_only_image_view).equals(layoutType)) {
			inflater.inflate(R.layout.abs__preference_image_item_layout, this, true);
		}else if(!TextUtils.isEmpty(layoutType) && context.getResources().getString(R.string.roma_preference_gray_text_view).equals(layoutType)){
			inflater.inflate(R.layout.abs__preference_gray_item_layout, this, true);
		}else {
			inflater.inflate(R.layout.abs__preference_item_layout, this, true);
		}

		ll_preference_parent = this.findViewById(R.id.ll_preference_parent);
        mLeftIconView = (SVGView) this.findViewById(R.id.left_icon);
        mTitleView = (TextView) this.findViewById(R.id.title);
        tv_unread_info_counts = (TextView) this.findViewById(R.id.tv_unread_info_counts);
        
        mSummaryView = (TextView) this.findViewById(R.id.summary);
        mRightTextView = (TextView) this.findViewById(R.id.right_textView);
        arrowRightView = (ImageView) this.findViewById(R.id.iv_arrow_right);

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
        
        //if(leftIcon != null)
        //    mLeftIconView.setImageDrawable(leftIcon);
        if(leftIconVisible!=null){
            String _leftIconVisible = leftIconVisible.toString();
            int visibile = _leftIconVisible.equals("gone")? View.GONE : (_leftIconVisible.equals("visible")?View.VISIBLE:View.INVISIBLE);
            mLeftIconView.setVisibility(visibile);
        }
        
        requestDisallowInterceptTouchEvent(true);
	}

	public PreferenceItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		
		
	}
	
	public void setLeftIconImageDrawable(SVGParserRenderer svgParserRenderer){
	    mLeftIconView.setSVGRenderer(svgParserRenderer, null);
		//mLeftIconView.setImageDrawable(drawable);
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
	public void setUnreadCounts(int counts){
		if(counts<=0)
			tv_unread_info_counts.setVisibility(View.GONE);
		else{
			tv_unread_info_counts.setVisibility(View.VISIBLE);
			tv_unread_info_counts.setText(counts+"");
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

	public String getRightText(){
		if (mRightTextView != null) {
			return mRightTextView.getText().toString();
		} else {
			return "";
		}
	}
	
	public void setRightText(CharSequence text){
		mRightTextView.setText(text);
		if (!TextUtils.isEmpty(text)) {
			mRightTextView.setCompoundDrawables(null, null, null, null);
		}
	}
	
	public void setRightTextColor(int color){
		mRightTextView.setTextColor(color);
	}
	
	public void setRightTextSize(int size){
		mRightTextView.setTextSize(size);
	}

	public TextView getRightTextView(){
		return mRightTextView;
	}

	public void setRightImageBitmap(Bitmap bitmap){
		arrowRightView.setImageBitmap(bitmap);
		Drawable nav_up=getResources().getDrawable(R.drawable.abs__roma_icon_arrow_right);
		nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
		mRightTextView.setCompoundDrawables(null, null, nav_up, null);
	}

	public void setVisibilityRightImageBitmap(int visibility){
//		arrowRightView.setVisibility(visibility);
		Drawable nav_up=getResources().getDrawable(R.drawable.abs__roma_icon_arrow_right);
		nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
		if (mRightTextView != null) {
			mRightTextView.setCompoundDrawables(null, null, nav_up, null);
		}
//		if (visibility == View.GONE) {
//			arrowRightView.clearAnimation();
//		}
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

	@Override
	public void setBackgroundColor(int color) {
		// ll_preference_parent.setBackgroundColor(color);
		super.setBackgroundColor(color);
	}
}

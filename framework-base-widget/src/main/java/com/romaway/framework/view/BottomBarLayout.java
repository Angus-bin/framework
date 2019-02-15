package com.romaway.framework.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class BottomBarLayout extends FrameLayout implements View.OnClickListener{
	
	private Context mContext;
	private LayoutInflater inflator;
	private int mCurrentItem = 0;
	
	private int[] itemIdArray;
	
	private OnSwitchItemListener mOnSwitchItemListener;
	public void setOnSwitchStockListener(OnSwitchItemListener onSwitchItemListener){
		mOnSwitchItemListener = onSwitchItemListener;
	}
	public interface OnSwitchItemListener{
		public void onClickSwitchItem(int itemIndex);
	}
	
	public BottomBarLayout(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public BottomBarLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public BottomBarLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		mContext = context;
		inflator = LayoutInflater.from(context);
		
	}
	
	public void init(int layoutResId, int[] itemViewId){
		itemIdArray = itemViewId;
		inflator.inflate(layoutResId, this, true);
		
		for(int i = 0; i < itemIdArray.length; i++){
			(findViewById(itemIdArray[i])).setOnClickListener(this);
		}
	}
	
	public void setTextCompoundDrawables(String[] text, int[] topResId){
		
		for(int i = 0; i < itemIdArray.length; i++){
			((TextView)(findViewById(itemIdArray[i]))).setBackgroundResource(Color.TRANSPARENT);
			((TextView)(findViewById(itemIdArray[i]))).setCompoundDrawablesWithIntrinsicBounds(0, topResId[i], 0, 0);
			((TextView)(findViewById(itemIdArray[i]))).setText(text[i]);
		}
	}
	
	public void setBackgroundResource(int[] resId){
		
		for(int i = 0; i < itemIdArray.length; i++){
			((TextView)(findViewById(itemIdArray[i]))).setBackgroundResource(resId[i]);
		}
	}
	public void setItemClick(int itemIndex){
		if(itemIndex < 0)
			return;
		else if(itemIndex >= itemIdArray.length)
			itemIndex = itemIdArray.length - 1;
		
		onClick(findViewById(itemIdArray[itemIndex]));
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
		int item =Integer.parseInt((String) view.getTag());
		
		(findViewById(itemIdArray[mCurrentItem])).setSelected(false);
		view.setSelected(true);
		mCurrentItem = item;
	
		if(mOnSwitchItemListener != null)
			mOnSwitchItemListener.onClickSwitchItem(item);
	}
	
	public void setSelectedItem(int itemIndex){
		
		if(itemIndex < 0)
			return;
		else if(itemIndex >= itemIdArray.length)
			itemIndex = itemIdArray.length - 1;
		
		View view = findViewById(itemIdArray[itemIndex]);
		int item =Integer.parseInt((String) view.getTag());
		
		(findViewById(itemIdArray[mCurrentItem])).setSelected(false);
		view.setSelected(true);
		
		mCurrentItem = item;
	}
	
	public int getCurrentItemIndex(){
		return mCurrentItem;
	}
	public void switchTitleIndicator(){
		
	}

}

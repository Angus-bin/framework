package com.romaway.framework.view;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.basephone.widget.R;
import com.zhy.autolayout.utils.AutoUtils;

public class RomaWaySwitchTowView extends LinearLayout implements
		View.OnClickListener {

	private TextView mLeftItemView;
	private TextView mRightItemView;
	private LinearLayout mParentView;
	private int currentSelectedItem = 0;
	private int[] mColorArray = new int[]{0xff000000, 0xff000000};
	private int[] mTextColorArray = new int[]{0xffffffff, 0xffffffff};
	private int mCorner = AutoUtils.getPercentWidthSize(30); // 圆角角度

	public RomaWaySwitchTowView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public RomaWaySwitchTowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(lp);
        LayoutInflater inflater = LayoutInflater.from(context);  
        inflater.inflate(R.layout.roma_switch_tow_layout, this, true);

        mParentView = (LinearLayout) this.findViewById(R.id.ll_tab_parent);
        mLeftItemView = (TextView) this.findViewById(R.id.left_text);
        mRightItemView = (TextView) this.findViewById(R.id.right_text);
        mLeftItemView.setOnClickListener(this);
        mRightItemView.setOnClickListener(this);
        //setSelectedItem(0);
	}

	/**
	 * 设置选项被选中
	 * @param item
	 * @param flag 标志是否产生点击事件监听器 true 为监听；false 不监听
	 */
	public void setSelectedItem(int item, boolean flag) {
		currentSelectedItem = item;
		mLeftItemView.setSelected(currentSelectedItem == 0 ? true : false);// 默认为左边
		mRightItemView.setSelected(currentSelectedItem == 1 ? true : false);
		setLeftColor(mColorArray[currentSelectedItem == 0 ? 0 : 1]);
		setLeftTextColor(mTextColorArray[currentSelectedItem == 0 ? 0 : 1]);
        setRightColor(mColorArray[currentSelectedItem == 0 ? 1 : 0]);
        setRightTextColor(mTextColorArray[currentSelectedItem == 0 ? 1 : 0]);
        
		if (mOnItemSwitchListener != null && flag)
            mOnItemSwitchListener.onClickItem(currentSelectedItem);
	}

	public void setLeftText(CharSequence text) {
		mLeftItemView.setText(text);
		if (text.toString().trim().length() >= 4) {
			mLeftItemView.setPadding(14, 0, 14, 0);
		}
	}

	public void setLeftTextColor(int color){
		mLeftItemView.setTextColor(color);
	}
	
	public void setLeftColor(int color) {
		ShapeDrawable mDrawable = new ShapeDrawable(new RoundRectShape(
				new float[] { mCorner, mCorner, 0, 0, 0, 0, mCorner, mCorner }, null, null));
		mDrawable.getPaint().setColor(color);
		mLeftItemView.setBackgroundDrawable(mDrawable);
	}

	public void setRightText(CharSequence text) {
		mRightItemView.setText(text);
		if (text.toString().trim().length() >= 4) {
			mRightItemView.setPadding(14, 0, 14, 0);
		}
	}
	
	public void setRightTextColor(int color){
		mRightItemView.setTextColor(color);
	}
	
	public void setRightColor(int color) {
		ShapeDrawable mDrawable = new ShapeDrawable(new RoundRectShape(
				new float[] { 0, 0, mCorner, mCorner, mCorner, mCorner, 0, 0 }, null, null));
		mDrawable.getPaint().setColor(color);
		mRightItemView.setBackgroundDrawable(mDrawable);
	}

	public void setSolidColor(int color) {
		ShapeDrawable mDrawable = new ShapeDrawable(new RoundRectShape(
				new float[] { mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner }, null, null));
		mDrawable.getPaint().setStrokeWidth(AutoUtils.getPercentWidthSize(2));
		mDrawable.getPaint().setColor(color);
		mParentView.setBackgroundDrawable(mDrawable);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		currentSelectedItem = 0;
		if (view.getId() == R.id.left_text) {
			mLeftItemView.setSelected(true);
			setLeftColor(mColorArray[0]);
			setLeftTextColor(mTextColorArray[0]);
			setRightColor(mColorArray[1]);
			setRightTextColor(mTextColorArray[1]);
			mRightItemView.setSelected(false);
			currentSelectedItem = 0;
		} else if (view.getId() == R.id.right_text) {// 点击理财选项
			mLeftItemView.setSelected(false);
			mRightItemView.setSelected(true);
			setLeftColor(mColorArray[1]);
			setLeftTextColor(mTextColorArray[1]);
			setRightColor(mColorArray[0]);
			setRightTextColor(mTextColorArray[0]);
			currentSelectedItem = 1;
		}

		if (mOnItemSwitchListener != null)
			mOnItemSwitchListener.onClickItem(currentSelectedItem);
	}
    /**
     * 
     * @param onClickItemSwitchListener
     * @param itemTextArray  选项文本
     * @param itemColorArray 选项颜色
     * @param solidColor 边框颜色
     */
	public void init(OnClickItemSwitchListener onClickItemSwitchListener,
			String[] itemTextArray, int[] itemColorArray, int[] textColorArray, int solidColor) {
		mOnItemSwitchListener = onClickItemSwitchListener;
		mColorArray = itemColorArray;
		mTextColorArray = textColorArray;
		setSolidColor(solidColor);
		for (int i = 0; i < itemTextArray.length; i++) {
			if (i == 0) {
				setLeftText(itemTextArray[0]);
				setLeftColor(itemColorArray[0]);
			} else if (i == 1) {
				setRightText(itemTextArray[1]);
				setRightColor(itemColorArray[1]);
			}
		}
	}

	OnClickItemSwitchListener mOnItemSwitchListener = null;

	public interface OnClickItemSwitchListener {
		/**
		 * 相应切换点击事件
		 * 
		 * @param selectedItem
		 */
		void onClickItem(int selectedItem);
	}
}

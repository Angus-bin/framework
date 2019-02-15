package com.romaway.framework.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.basephone.widget.R;
import com.romalibs.utils.DensityUtil;
import com.romaway.commons.log.Logger;
import com.zhy.autolayout.utils.AutoUtils;

public class TopTabBarTouGu2 extends FrameLayout{
	private Context mContext;
	private LinearLayout mContent;
	private IndicatorTouGuView indicatorView;
	private View topBottomline;
	private int tabTextColor = 0xffffffff;
	private int tabTextSelectedColor = 0xffff3434;
	private int index = 0;

	private boolean isShowRedPoint = false;

	public boolean IsScrollSliding;

	int mOldItem = 0;

	public TopTabBarTouGu2(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public TopTabBarTouGu2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		mContext = context;
        LayoutInflater.from(context).inflate(R.layout.abs__top_tab_bar_tougu_layout, this, true);
        
        mContent = (LinearLayout) this.findViewById(R.id.root);
        indicatorView = (IndicatorTouGuView) this.findViewById(R.id.abs__top_indicator_view);
        topBottomline=(View)this.findViewById(R.id.abs__top_bottomline);
        
        /*setItems(
                2, 
                new String[]{"�ҵ�����","��������"}); 
        updateUiShow(1);*/
	}

	public void setIndicatorColor(int color) {
		indicatorView.setTabColor(color);
	}

	public void setTabTextSelectedColor(int color) {
		this.tabTextSelectedColor = color;
	}
	
	public void setIsScrollSliding(boolean isScrollSliding) {
		IsScrollSliding = isScrollSliding;
	}

	/**
	 * 
	 * @param onClickItemSwitchListener
	 * @param itemNum �ܹ���ѡ�����
	 * @param titleArray ���鳤��ΪitemNum����ѡ�����
	 * @param defaultItemNum Ĭ����ʾ��һ��
	 */
	public void setInitItems(final OnClickItemSwitchListener onClickItemSwitchListener, int itemNum, String[] titleArray, int defaultItemNum){
		// 设置确切的滑块数量
		index = 0;
		mContent.removeAllViews();

		for (int i = 0; i < titleArray.length; i++) {
			if (!titleArray[i].equals("")) {
				index++;
			}
			indicatorView.setCursorCount(index);
		}
//		indicatorView.setCursorCount(itemNum);
		updateUiShow(defaultItemNum);
		for(int i = 0; i < itemNum; i++){
			RelativeLayout parentView = new RelativeLayout(mContext);
//			LinearLayout parentView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.abs_top_tougu_flag, null);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.weight = 1.0f;
			parentView.setLayoutParams(lp);
//			parentView.setOrientation(LinearLayout.VERTICAL);
			parentView.setGravity(Gravity.CENTER);
			parentView.setTag("" + i);
			TextPaint paint = new TextPaint();
			Rect rect = new Rect();
			paint.setTextSize(13);
			float a = paint.measureText("0最赚钱组合0");
			paint.getTextBounds(titleArray[i], 0, titleArray[i].length(), rect);
			float b = rect.width();
			TextView textView = (TextView)LayoutInflater.from(mContext).inflate(R.layout.abs__text_view3, null);
			textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					mContext.getResources().getDimensionPixelSize(R.dimen.abs__bottom_action_bar_default_height) * 3 / 4));
			LinearLayout tv_flag = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.abs__text_view_flag, null);
			textView.setTag("textview");
			textView.setText(titleArray[i]);
			tv_flag.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getContext(), 7)));
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv_flag.getLayoutParams();
//			params.addRule(RelativeLayout.LEFT_OF, R.id.TextView);
			params.addRule(RelativeLayout.CENTER_IN_PARENT);
//			params.leftMargin = 100;
//			Logger.d("文本长度", "文本长度 =   " + textView.getWidth());
//			params.setMargins(0, 0, AutoUtils.getPercentWidthSize(50), 0);
			tv_flag.setLayoutParams(params);
			tv_flag.setTag("tv_flag");
			if (textView.getText().equals("") || textView.getText().equals(null)) {
				textView.setWidth(0);
				continue;
			}
			textView.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					// TODO Auto-generated method stub
					ViewGroup parentView = (ViewGroup) view.getParent();
					String parentTag = (String) parentView.getTag();
					int selectedItem = Integer.parseInt(parentTag);
					if (selectedItem != mOldItem) {
						if (onClickItemSwitchListener != null) {
							onClickItemSwitchListener.onClickItemSwitch(selectedItem);
							updateUiShow(selectedItem);
						}
					}
				}
			});
			parentView.addView(textView);
			parentView.addView(tv_flag);

			mContent.addView(parentView); 
		}
		
		updateUiShow(defaultItemNum);
		if(onClickItemSwitchListener != null)
			onClickItemSwitchListener.onClickItemSwitch(defaultItemNum);
	}
	
	public void indicatorScrolled(float movexs){
	    indicatorView.cursorScrolled(movexs);
    }
	
	public void updateUiShow(int selectedItem){
		if(IsScrollSliding){
			indicatorView.setTagCursorIndex(selectedItem-1);
		}else {
			indicatorView.setTagCursorIndex(selectedItem);
		}
		mOldItem = selectedItem;
		
		//�޸���ʾ
		int rootChildCount = mContent.getChildCount();
		for(int i = 0; i < rootChildCount; i++){
			View parentView = (View) mContent.getChildAt(i);
			String tag = (String)parentView.getTag();
			
			if(tag.equals(""+selectedItem)){
				for(int j = 0; j < ((ViewGroup)parentView).getChildCount(); j++){
					View view = ((ViewGroup)parentView).getChildAt(j);
					String tag2 = (String) view.getTag();
					if(tag2.equals("textview")) {
						((TextView)view).setTextColor(tabTextSelectedColor);
					} else if (tag2.equals("tv_flag")) {
						view.setVisibility(View.GONE);
					} else{
						view.setVisibility(View.VISIBLE);

					}
				}
			}else{
				if(!tag.equals("divider")){
					for(int j = 0; j < ((ViewGroup)parentView).getChildCount(); j++){
						View view = ((ViewGroup)parentView).getChildAt(j);
						String tag2 = (String) view.getTag();
						if(tag2.equals("textview")){
							((TextView)view).setTextColor(tabTextColor);
						}else{
							view.setVisibility(View.INVISIBLE);
						}
							
					}
				}
			}
			if (isShowRedPoint) {
				updateUiShowRedPoint(1);
			}
		}
	}

	public void setIsShowRedPoint(boolean isShowRedPoint) {
		this.isShowRedPoint = isShowRedPoint;
	}

	public void updateUiShowRedPoint(int selectedItem) {
		int rootChildCount = mContent.getChildCount();
		for(int i = 0; i < rootChildCount; i++){
			View parentView = (View) mContent.getChildAt(i);
			String tag = (String)parentView.getTag();

			if(tag.equals(""+selectedItem)){
				for(int j = 0; j < ((ViewGroup)parentView).getChildCount(); j++){
					View view = ((ViewGroup)parentView).getChildAt(j);
					String tag2 = (String) view.getTag();
					if (tag2.equals("tv_flag")) {
						if (isShowRedPoint) {
							view.setVisibility(View.VISIBLE);
						} else {
							view.setVisibility(View.INVISIBLE);
						}
					}/* else{
						view.setVisibility(View.INVISIBLE);

					}*/
				}
			}else{
				if(!tag.equals("divider")){
					for(int j = 0; j < ((ViewGroup)parentView).getChildCount(); j++){
						View view = ((ViewGroup)parentView).getChildAt(j);
						String tag2 = (String) view.getTag();
						if(tag2.equals("tv_flag")){
//							((TextView)view).setTextColor(tabTextColor);
							view.setVisibility(View.GONE);
						}/*else{
							view.setVisibility(View.INVISIBLE);
						}*/

					}
				}
			}
		}
	}
	
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO: currently ignoring padding            
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    	int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize =  MeasureSpec.getSize(widthMeasureSpec);            
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize =  MeasureSpec.getSize(heightMeasureSpec);
        
        int maxMeasureHeight = 0;
        
        if (widthSpecMode == MeasureSpec.UNSPECIFIED || heightSpecMode == MeasureSpec.UNSPECIFIED) {
                throw new RuntimeException("Dockbar cannot have UNSPECIFIED dimensions");
        }
        
        final int count = getChildCount();
        
        //��ȡ�������߶�
        for (int i = 0; i < count; i++) {
        	View childView = getChildAt(i);
        	/*childView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
          	                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));*/
        	
        	maxMeasureHeight = Math.max(maxMeasureHeight, childView.getMeasuredHeight());
        }
        setMeasuredDimension(widthSpecSize, maxMeasureHeight); 
	}
	
	OnClickItemSwitchListener mOnItemSwitchListener = null;
	/* public void setOnItemSwitchListener(OnItemSwitchListener onItemSwitchListener){
		 mOnItemSwitchListener = onItemSwitchListener;
	 }*/
	 public interface OnClickItemSwitchListener{
		 void onClickItemSwitch(int selectedItem);
	 }
	 
	 public void setTextColor(int color){
		 this.tabTextColor = color;
	 }
	 public void setLineColor(int color){
		 this.topBottomline.setBackgroundColor(color);
	 }
}

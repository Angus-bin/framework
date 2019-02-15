package com.romaway.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.basephone.widget.R;

public class TopTabBarTouGu extends FrameLayout{
	private Context mContext;
	private LinearLayout mContent;
	private IndicatorTouGuView indicatorView;
	private View topBottomline;
	private int tabTextColor = 0xffffffff;
	private int tabTextSelectedColor = 0xffff3434;
	private int index = 0;

	public boolean IsScrollSliding;

	int mOldItem = 0;

	public TopTabBarTouGu(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public TopTabBarTouGu(Context context, AttributeSet attrs) {
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

	public void setIndicatorWidth(int width) {
		indicatorView.setIndicatorWidth(width);
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
			LinearLayout parentView = new LinearLayout(mContext);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.weight = 1.0f;
			parentView.setLayoutParams(lp);
			parentView.setOrientation(LinearLayout.VERTICAL);
			parentView.setGravity(Gravity.CENTER);
			parentView.setTag(""+i);
			
			TextView textView = (TextView)LayoutInflater.from(mContext).inflate(R.layout.abs__text_view3, null);
			textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					mContext.getResources().getDimensionPixelSize(R.dimen.abs__bottom_action_bar_default_height) * 3 / 4));
			textView.setTag("textview");
			textView.setText(titleArray[i]);
			if (textView.getText().equals("") || textView.getText().equals(null)) {
				textView.setWidth(0);
				continue;
			}
			textView.setOnClickListener(new OnClickListener(){
				public void onClick(View view) {
					// TODO Auto-generated method stub
					ViewGroup parentView = (ViewGroup) view.getParent();
					String parentTag = (String) parentView.getTag();
					int selectedItem = Integer.parseInt(parentTag);
					if(selectedItem != mOldItem){
						if(onClickItemSwitchListener != null){
							onClickItemSwitchListener.onClickItemSwitch(selectedItem);
							updateUiShow(selectedItem);
						}
					}
				}
			});
			parentView.addView(textView);
			
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
					if(tag2.equals("textview")){
						((TextView)view).setTextColor(tabTextSelectedColor); 
					}else{
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

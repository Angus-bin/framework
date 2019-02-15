package com.romaway.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.android.basephone.widget.R;
import com.romalibs.utils.DensityUtil;
import com.romaway.common.android.base.OriginalContext;
import com.zhy.autolayout.utils.AutoUtils;

public class IndicatorTouGuView extends View{

	private int itemWidth;
	private int indicatorWidth;
	private float tagStartX = 0;
	private float tempStartX = 0;

	private int cursorCount = 4;
	private int OFFSET = 50;
	private int tagCursorIndex = 0;

	/** 间隔*/
	private int space = 0;

	/** 设置菜单间距*/
	public void setSpace(int space){
		this.space = space;
	}

	private Paint paint;
	private int tabColor = 0xffff3434;

	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch(msg.what){
				case 0:
					if(tagStartX - tempStartX > 0){//右滑
						if(tagStartX - tempStartX <= OFFSET)
							tempStartX = tagStartX;
						else
							tempStartX += OFFSET;

						IndicatorTouGuView.this.invalidate();
					}else if(tagStartX - tempStartX < 0){//左滑

						if(tempStartX - tagStartX < OFFSET)
							tempStartX = tagStartX;
						else
							tempStartX -= OFFSET;
						IndicatorTouGuView.this.invalidate();
					}else{

					}

				break;
			}
		}
	};

	public IndicatorTouGuView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public IndicatorTouGuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		paint = new Paint();
		paint.setColor(tabColor);
		paint.setStyle(Paint.Style.FILL);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);// 去锯齿
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setStrokeWidth(1);

	}

	public IndicatorTouGuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
	}
	
	public void setTabColor(int color){
		this.tabColor = color;
		this.invalidate();
	}

	public void setCursorCount(int count){
		cursorCount = count;
	}
	
	public int getCursorCount() {
		return cursorCount;
	}

	public void setTagCursorIndex(int index){
		tagCursorIndex = index;
		tagStartX = getStartX(tagCursorIndex);
		
		mHandler.removeMessages(0);
		mHandler.sendEmptyMessage(0);
	}
	
	public void cursorScrolled(float movexs){
        tagStartX = getStartX(tagCursorIndex) + itemWidth * movexs;
        
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessage(0);
    }
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setColor(tabColor);
		//canvas.drawColor(bgColor);
		Rect rect = new Rect((int)tempStartX, 0, (int)tempStartX + indicatorWidth, this.getHeight());
		canvas.drawRect(rect, paint);
		//canvas.drawLine(tempStartX, 0, tempStartX + indicatorWidth, 0, paint);
		
		mHandler.sendEmptyMessage(0);
	}

	private int getStartX(int cursorIndex){
		
		return itemWidth * cursorIndex + (itemWidth - indicatorWidth)/ 2 + space * cursorIndex;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
		
		
		itemWidth = getMeasuredWidth() / cursorCount;
//		indicatorWidth = itemWidth / 2;
		// 顶部滑动栏线条长度:
//		indicatorWidth = itemWidth - AutoUtils.getPercentWidthSize(getResources().getInteger(R.integer.roma_indicator_tougu_view_line_padding));
		indicatorWidth = getIndicatorWidth() <= 0 ? DensityUtil.dip2px(OriginalContext.getContext(), 25f) : getIndicatorWidth();
		OFFSET = itemWidth / 2;
		//init
		tagStartX = getStartX(tagCursorIndex);
		tempStartX = tagStartX;
		
	}
	/**
     * Determines the width of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            result = getWidth();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    /**
     * Determines the height of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
        	//基础线之下的距离
            result = (int) paint.descent();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

	/**
	 * 设置顶部滑动栏线条长度
	 * @param indicatorWidth
	 */
	public void setIndicatorWidth(int indicatorWidth){
		this.indicatorWidth = indicatorWidth;
		invalidate();
	}

	public int getIndicatorWidth() {
		return indicatorWidth;
	}
}

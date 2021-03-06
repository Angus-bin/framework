/*
 * Copyright (C) 2011 Patrik Akerfeldt
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.romaway.framework.view.pagerindicator;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.basephone.widget.R;
import com.romaway.commons.log.Logger;

/**
 * Draws circles (one for each view). The current view position is filled and
 * others are only stroked.
 */
public class CirclePageIndicator extends View implements PageIndicator {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private float mRadius;
    private float mSpacingRatio;
    private final Paint mPaintStroke;
    private final Paint mPaintFill;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;
    private int mCurrentPage;
    private int mSnapPage;
    private int mCurrentOffset;
    private int mScrollState;
    private int mPageSize;
    private int mOrientation;
    private boolean mCentered;
    private boolean mSnap;

    private boolean IsScrollSliding;

    private boolean IsCircle;

    private int itemCount;

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public CirclePageIndicator(Context context) {
        this(context, null);
    }

    public CirclePageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0/*R.attr.circlePageIndicatorStyle*/);
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
	@SuppressLint("NewApi")
	public CirclePageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //Load defaults from resources
        final Resources res = getResources();
        final int defaultFillColor = res.getColor(R.color.default_circle_indicator_fill_color);
        final int defaultOrientation = res.getInteger(R.integer.default_circle_indicator_orientation);
        final int defaultStrokeColor = res.getColor(R.color.default_circle_indicator_stroke_color);
        final float defaultStrokeWidth = res.getDimension(R.dimen.default_circle_indicator_stroke_width);
        final float defaultRadius = res.getDimension(R.dimen.default_circle_indicator_radius);
        final boolean defaultCentered = res.getBoolean(R.bool.default_circle_indicator_centered);
        final boolean defaultSnap = res.getBoolean(R.bool.default_circle_indicator_snap);
        final int defaultSpacingRatio = res.getInteger(R.integer.default_circle_indicator_spacing_ratio);

        //Retrieve styles attributes
        //TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CirclePageIndicator, defStyle, R.style.Widget_CirclePageIndicator);

        mCentered = defaultCentered;//a.getBoolean(R.styleable.CirclePageIndicator_centered, defaultCentered);
        mOrientation = defaultOrientation;//a.getInt(R.styleable.CirclePageIndicator_orientation, defaultOrientation);
        mPaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintStroke.setStyle(Style.FILL);
        mPaintStroke.setColor(defaultStrokeColor/*a.getColor(R.styleable.CirclePageIndicator_strokeColor, defaultStrokeColor)*/);
        mPaintStroke.setStrokeWidth(defaultStrokeWidth/*a.getDimension(R.styleable.CirclePageIndicator_strokeWidth, defaultStrokeWidth)*/);
        mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFill.setStyle(Style.FILL);
        mPaintFill.setColor(defaultFillColor/*a.getColor(R.styleable.CirclePageIndicator_fillColor, defaultFillColor)*/);
        mRadius = defaultRadius;//a.getDimension(R.styleable.CirclePageIndicator_radius, defaultRadius);
        mSnap = defaultSnap;//a.getBoolean(R.styleable.CirclePageIndicator_snap, defaultSnap);
        mSpacingRatio = defaultSpacingRatio;

       // a.recycle();
    }

    public void setCentered(boolean centered) {
        mCentered = centered;
        invalidate();
    }

    public boolean isCentered() {
        return mCentered;
    }

    public void setFillColor(int fillColor) {
        mPaintFill.setColor(fillColor);
        invalidate();
    }

    public boolean isCircle() {
        return IsCircle;
    }

    public void setCircle(boolean circle) {
        IsCircle = circle;
    }

    public void setIsScrollSliding(boolean isScrollSliding) {
        IsScrollSliding = isScrollSliding;
    }

    public int getFillColor() {
        return mPaintFill.getColor();
    }

    public void setOrientation(int orientation) {
        switch (orientation) {
            case HORIZONTAL:
            case VERTICAL:
                mOrientation = orientation;
                updatePageSize();
                requestLayout();
                break;

            default:
                throw new IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.");
        }
    }

    public int getOrientation() {
        return mOrientation;
    }

    public void setStrokeColor(int strokeColor) {
        mPaintStroke.setColor(strokeColor);
        invalidate();
    }

    public int getStrokeColor() {
        return mPaintStroke.getColor();
    }

    public void setStrokeWidth(float strokeWidth) {
        mPaintStroke.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public float getStrokeWidth() {
        return mPaintStroke.getStrokeWidth();
    }

    public void setRadius(float radius) {
        mRadius = radius;
        invalidate();
    }

    public float getRadius() {
        return mRadius;
    }

    public void setSnap(boolean snap) {
        mSnap = snap;
        invalidate();
    }

    public boolean isSnap() {
        return mSnap;
    }

    public void setStrokeType(Style styleType) {
        mPaintStroke.setStyle(styleType);
        invalidate();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try {
            int longSize;
            int longPaddingBefore;
            int longPaddingAfter;
            int shortPaddingBefore;
            if (mOrientation == HORIZONTAL) {
                longSize = getWidth();
                longPaddingBefore = getPaddingLeft() + 10;
                longPaddingAfter = getPaddingRight() + 10;
                shortPaddingBefore = getPaddingTop() + 10;
            } else {
                longSize = getHeight();
                longPaddingBefore = getPaddingTop() + 10;
                longPaddingAfter = getPaddingBottom() + 10;
                shortPaddingBefore = getPaddingLeft() + 10;
            }

            int count = 0;
            if (mViewPager == null) {
                return;
            }
            if (mViewPager.getAdapter() == null)
                return;

            count = mViewPager.getAdapter().getCount();

            if (IsScrollSliding) {
                count = itemCount;
            }
            final float threeRadius = mRadius * mSpacingRatio;
            final float shortOffset = shortPaddingBefore + mRadius;
            float longOffset = 0/*longPaddingBefore + mRadius*/;
            if (mCentered) {
                longOffset = longPaddingBefore + mRadius;
            }
//            Logger.d("CirclePageIndicator1", "mRadius = " + mRadius);
//            Logger.d("CirclePageIndicator1", "mSpacingRatio = " + mSpacingRatio);
//            Logger.d("CirclePageIndicator1", "longSize = " + longSize);
//            Logger.d("CirclePageIndicator1", "longPaddingBefore = " + longPaddingBefore);
//            Logger.d("CirclePageIndicator1", "longPaddingAfter = " + longPaddingAfter);
//            Logger.d("CirclePageIndicator1", "shortPaddingBefore = " + shortPaddingBefore);
//            Logger.d("CirclePageIndicator1", "count = " + count);
//            Logger.d("CirclePageIndicator1", "threeRadius = " + threeRadius);
//            Logger.d("CirclePageIndicator1", "((count -1 ) * (threeRadius + 10)) = " + (((count - 1) * (threeRadius + 10))));
            if (mCentered) {
                longOffset += ((longSize - longPaddingBefore - longPaddingAfter) / 2.0f) - ((count * threeRadius) / 2.0f)/* - (longPaddingBefore /3.0f)*/;
            } else {
                longOffset += longSize - (((count - 1) * (threeRadius + 10))) - 20;
            }
//            Logger.d("CirclePageIndicator", "longOffset = " + longOffset);

            float dX;
            float dY;

            //Draw stroked circles
            for (int iLoop = 0; iLoop < count; iLoop++) {
                float drawLong;
                drawLong = longOffset + (iLoop * threeRadius);
//            if (iLoop == 0) {
//            } else {
//                Logger.d("CirclePageIndicator", "11");
//                drawLong = longOffset + (iLoop * threeRadius)/* + 10*/;
//            }
                if (mOrientation == HORIZONTAL) {
                    if (iLoop == 0) {
                        dX = drawLong;
                    } else {
                        dX = drawLong + iLoop * 4;
                    }
                    dY = shortOffset;
                } else {
                    dX = shortOffset;
                    dY = drawLong;
                }
//                Logger.d("CirclePageIndicator", "mOrientation = " + mOrientation);
//                Logger.d("CirclePageIndicator", "iLoop = " + iLoop);
//                Logger.d("CirclePageIndicator", "dX = " + dX);
//                Logger.d("CirclePageIndicator", "dY = " + dY);
//                Logger.d("CirclePageIndicator", "drawLong = " + drawLong);
                if (IsCircle) {
                    canvas.drawCircle(dX, dY, mRadius, mPaintStroke);
                } else {
                    canvas.drawRect(dX, dY, dX + 14, dY + 4, mPaintStroke);
                }
            }

            //Draw the filled circle according to the current scroll
            float cx = 0;
            if (IsScrollSliding) {
                mCurrentPage = (mCurrentPage % itemCount);
                cx = (mSnap ? mSnapPage : mCurrentPage) * threeRadius + (mCurrentPage == 0 ? 0 : mCurrentPage * 4);
            } else {
                cx = (mSnap ? mSnapPage : mCurrentPage) * threeRadius + (mCurrentPage == 0 ? 0 : mCurrentPage * 4);
            }
//            Logger.d("CirclePageIndicator", "mPageSize = " + mPageSize);
            if (!mSnap && (mPageSize != 0)) {
                cx += (mCurrentOffset * 1.0f / mPageSize) * threeRadius;
            }
//            Logger.d("CirclePageIndicator", "cx = " + cx);
            if (mOrientation == HORIZONTAL) {
                dX = longOffset + cx;
                dY = shortOffset;
            } else {
                dX = shortOffset;
                dY = longOffset + cx;
            }
//            Logger.d("CirclePageIndicator1", "mCurrentPage = " + mCurrentPage);
//            Logger.d("CirclePageIndicator1", "mCurrentOffset = " + mCurrentOffset);
//            Logger.d("CirclePageIndicator1", "mPageSize = " + mPageSize);
//            Logger.d("CirclePageIndicator1", "threeRadius = " + threeRadius);
//            Logger.d("CirclePageIndicator1", "cx = " + cx);
//            Logger.d("CirclePageIndicator1", "dX = " + dX);
//            Logger.d("CirclePageIndicator1", "dY = " + dY);
            if (IsCircle) {
                canvas.drawCircle(dX, dY, mRadius, mPaintFill);
            } else {
                canvas.drawRect(dX, dY, dX + 14, dY + 4, mPaintFill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int count = mViewPager.getAdapter().getCount();
            if (IsScrollSliding) {
                count = itemCount;
            }
            final int longSize = (mOrientation == HORIZONTAL) ? getWidth() : getHeight();
            final float halfLongSize = longSize / 2;
            final float halfCircleLongSize = (count * 3 * mRadius) / 2;
            final float pointerValue = (mOrientation == HORIZONTAL) ? event.getX() : event.getY();

            if ((mCurrentPage > 0) && (pointerValue < halfLongSize - halfCircleLongSize)) {
                setCurrentItem(mCurrentPage - 1);
                return true;
            } else if ((mCurrentPage < count - 1) && (pointerValue > halfLongSize + halfCircleLongSize)) {
                setCurrentItem(mCurrentPage + 1);
                return true;
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void setViewPager(ViewPager view) {
        if (view.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        mViewPager.setOnPageChangeListener(this);
        updatePageSize();
        invalidate();
    }

    private void updatePageSize() {
        if (mViewPager != null) {
            mPageSize = (mOrientation == HORIZONTAL) ? mViewPager.getWidth() : mViewPager.getHeight();
        }
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        if (!IsScrollSliding) {
            mViewPager.setCurrentItem(item);
        }
        mCurrentPage = item;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;

        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentPage = position;
        mCurrentOffset = positionOffsetPixels;
        updatePageSize();
        invalidate();

        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mSnap || mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            mCurrentPage = position;
            mSnapPage = position;
            invalidate();
        }

        if (mListener != null) {
            mListener.onPageSelected(position);
        }
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mOrientation == HORIZONTAL) {
            setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
        } else {
            setMeasuredDimension(measureShort(widthMeasureSpec), measureLong(heightMeasureSpec));
        }
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec
     *            A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureLong(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            //We were told how big to be
            result = specSize;
        } else {
            //Calculate the width according the views count
            int count = mViewPager.getAdapter().getCount();
            if (IsScrollSliding) {
                count = itemCount;
            }
            result = (int)(getPaddingLeft() + getPaddingRight()
                    + (count * 2 * mRadius) + (count - 1) * mRadius + 1);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec
     *            A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureShort(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            //We were told how big to be
            result = specSize;
        } else {
            //Measure the height
            result = (int)(2 * mRadius + getPaddingTop() + getPaddingBottom() + 1);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState)state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPage = savedState.currentPage;
        mSnapPage = savedState.currentPage;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPage = mCurrentPage;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPage;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPage = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPage);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}

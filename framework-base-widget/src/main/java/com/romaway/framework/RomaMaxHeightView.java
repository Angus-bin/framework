package com.romaway.framework;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.android.basephone.widget.R;

/**
 * 先判断是否设定了mMaxHeight，如果设定了mMaxHeight，则直接使用mMaxHeight的值，
 * 如果没有设定mMaxHeight，则判断是否设定了mMaxRatio，如果设定了mMaxRatio的值
 * 则使用此值与屏幕高度的乘积作为最高高度
 */
public class RomaMaxHeightView extends FrameLayout {

    private static final float DEFAULT_MAX_RATIO = 0.4f;
    private static final float DEFAULT_MAX_HEIGHT = -1f;

    private Context mContext;
    private float mMaxHeight = DEFAULT_MAX_HEIGHT;//优先级高
    private float mMaxRatio = DEFAULT_MAX_RATIO;//优先级低

    public RomaMaxHeightView(Context context) {
        this(context, null);
    }

    public RomaMaxHeightView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RomaMaxHeightView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ROMAMaxHeightView);

        final int count = a.getIndexCount();
        for (int i = 0; i < count; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.ROMAMaxHeightView_maxHeightRatio) {
                mMaxRatio = a.getFloat(attr, DEFAULT_MAX_RATIO);
            } else if (attr == R.styleable.ROMAMaxHeightView_maxHeightDimen) {
                mMaxHeight = a.getDimension(attr, DEFAULT_MAX_HEIGHT);
            }
        }
        a.recycle();

        if (mMaxHeight < 0) {
            mMaxHeight = mMaxRatio * (float) getScreenHeight(mContext);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

        Log.d("wang", "onMeasure heightSize is " + heightSize + " | mMaxHeight is " + mMaxHeight);

        if (heightMode == View.MeasureSpec.EXACTLY) {
            Log.d("wang", "heightMode == View.MeasureSpec.EXACTLY");
            heightSize = heightSize <= mMaxHeight ? heightSize : (int) mMaxHeight;
        }

        if (heightMode == View.MeasureSpec.UNSPECIFIED) {
            Log.d("wang", "heightMode == View.MeasureSpec.UNSPECIFIED");
            heightSize = heightSize <= mMaxHeight ? heightSize : (int) mMaxHeight;
        }
        if (heightMode == View.MeasureSpec.AT_MOST) {
            Log.d("wang", "heightMode == View.MeasureSpec.AT_MOST");
            heightSize = heightSize <= mMaxHeight ? heightSize : (int) mMaxHeight;
        }

        int maxHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightSize, heightMode);

        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
    }


    /**
     * 获取屏幕高度
     *
     * @param context
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕大小
     *
     * @param context
     * @param outDimension
     */
    public static void getScreenDimension(Context context, int[] outDimension) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        outDimension[0] = wm.getDefaultDisplay().getWidth();
        outDimension[1] = wm.getDefaultDisplay().getHeight();
    }
}

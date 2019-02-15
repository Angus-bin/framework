package com.romaway.framework.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.basephone.widget.R;
import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SVGView;

public class RomaWayShortcutView extends LinearLayout {
	private Context mContext;
	private LinearLayout llParent;
	private SVGView mSvgView;
	private TextView mTvTitle;
	private TextView tv_flag;
	private TextView tv_flag_for_tuisong;
	private static int llParentheight;
	private ImageView mImageView;
	
	public RomaWayShortcutView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public RomaWayShortcutView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public RomaWayShortcutView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
		this.setGravity(Gravity.CENTER);

		// 获取控件设置的Sytle属性:
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ROMAShortcutViewStyle, 0, 0);
		CharSequence layoutType = a.getText(R.styleable.ROMAShortcutViewStyle_shortcut_layoutType);
		a.recycle();

		LayoutInflater inflater = LayoutInflater.from(context);
		// 【兼容】多处共用此控件，通过ViewAttr动态替换控件布局:
		if(context.getResources().getString(R.string.roma_shortcut_layoutType).equals(layoutType))
			inflater.inflate(R.layout.abs__shortcut_button_layout2, this, true);
		else
			inflater.inflate(R.layout.abs__shortcut_button_layout, this, true);
		
		llParent = (LinearLayout) this.findViewById(R.id.ll_parent);
		
//		mSvgView = (SVGView) this.findViewById(R.id.siv_topicon);
		mTvTitle = (TextView) this.findViewById(R.id.tv_title);
		tv_flag = (TextView) this.findViewById(R.id.tv_flag);
		tv_flag_for_tuisong = (TextView) this.findViewById(R.id.tv_flag_for_tuisong);
		mImageView = (ImageView) this.findViewById(R.id.img_topicon);
	}

	public static int getLlParentheight() {
		return llParentheight;
	}

	public static void setLlParentheight(int Parentheight) {
		llParentheight = Parentheight;
	}

	/**
	 * 设置icon 与文本的布局方向，支持水平和垂直两个方向
	 */
	public void setOrientation(int orientation){
		if(orientation == HORIZONTAL){
			llParent.setOrientation(HORIZONTAL);
		}else{
			llParent.setOrientation(VERTICAL);
		}
	}
	
    int mNormalColor = 0xffffffff;
    int mSelectedColor = 0xff000000;
    int mSelectedBgColor=0xff000000;
    int mUnSelectedBgColor=0xff000000;
    /**
     * 初始化文本颜色 参数设置为0会默认颜色
     * @param normalColor
     * @param selectedColor
     */
    public void initTitleColor(int normalColor, int selectedColor, int selectedBgColor, int unSelectedBgColor){
        if(normalColor != 0)
            mNormalColor = normalColor | 0xff000000;
        if(selectedColor != 0)
            mSelectedColor = selectedColor | 0xff000000;
        if (selectedBgColor != 0) {
			mSelectedBgColor = selectedBgColor | 0xff000000;
		}
        if (unSelectedBgColor != 0) {
			mUnSelectedBgColor = unSelectedBgColor | 0xff000000;
		}
    }
    public int getNormalColor(){
        return mNormalColor;
    }
    public int getPressedColor(){
        return mSelectedColor;
    }
    public int getBgColor(){
    	return mSelectedBgColor;
    }
    public int getUnBgColor(){
    	return mUnSelectedBgColor;
    }
    
    /**
     * 设置红点的可见性
     * @param visibility
     */
    public void setRedPointVisibility(int visibility){
    	tv_flag.setVisibility(visibility);
    }
    /**
     * 设置红点的可见性
     * @param visibility
     */
    public void setRedPointVisibility1(int visibility){
    	tv_flag_for_tuisong.setVisibility(visibility);
    }

	/**
	 * 设置红点的数字
	 * @param num
	 */
	public void setRedPointNum(String num) {
		tv_flag_for_tuisong.setText(num);
	}
    
	private SVGParserRenderer mNormalDrawable;
	private SVGParserRenderer mPressedDrawable;

	private Bitmap mNormalBitmap;
	private Bitmap mPressedBitmap;
	//private SVGParserRenderer mCurrentDrawable;
	
	/**
	 * 初始化图片
	 * @param normalDrawable
	 * @param pressedDrawable 按下或者被选择的图片
	 */
	public void initDrawable(SVGParserRenderer normalDrawable, SVGParserRenderer pressedDrawable){
	    mNormalDrawable = normalDrawable;
	    mPressedDrawable = pressedDrawable;
	    
	    mSvgView.setSVGRenderer(mNormalDrawable, null);
        
	}

	/**
	 * 初始化图片
	 * @param normalBitmap
	 * @param pressedBitmap 按下或者被选择的图片
	 */
	public void initDrawable(Bitmap normalBitmap, Bitmap pressedBitmap){
		mNormalBitmap = normalBitmap;
		mPressedBitmap = pressedBitmap;

		mImageView.setImageBitmap(mNormalBitmap);
		mImageView.setVisibility(View.VISIBLE);
	}
	
	public SVGParserRenderer getNormalDrawable(){
	    return mNormalDrawable;
	}
	
	public void setNormalDrawable(SVGParserRenderer drawable){
	    mNormalDrawable = drawable;
	    if(getStatus() == STATUS_NORMAL){
	        mSvgView.setSVGRenderer(drawable, null);
	    }
	}

	public SVGParserRenderer getPressedDrawable(){
	    if(mPressedDrawable == null)
	        mPressedDrawable = mNormalDrawable;
	    
        return mPressedDrawable;
    }
	
	public void setPressedDrawable(){
        if(getStatus() == STATUS_SELECT_FOR_PRESSED){
            mSvgView.setSVGRenderer(mPressedDrawable, null);
        }
    }
	
	public void setPressedDrawable(SVGParserRenderer drawable){
	    mPressedDrawable = drawable;
	    if(getStatus() == STATUS_SELECT_FOR_PRESSED){
	        mSvgView.setSVGRenderer(drawable, null);
        }
    }

	public Bitmap getmNormalBitmap() {
		return mNormalBitmap;
	}

	public void setmNormalBitmap(Bitmap mNormalBitmap) {
		this.mNormalBitmap = mNormalBitmap;
		if(getStatus() == STATUS_NORMAL){
			mImageView.setImageBitmap(mNormalBitmap);
		}
	}

	public Bitmap getmPressedBitmap() {
		if(mNormalBitmap == null)
			mPressedBitmap = mNormalBitmap;

		return mPressedBitmap;
	}

	public void setmPressedBitmap() {
		if(getStatus() == STATUS_SELECT_FOR_PRESSED){
			mImageView.setImageBitmap(mPressedBitmap);
		}
	}

	public void setmPressedBitmap(Bitmap mPressedBitmap) {
		this.mPressedBitmap = mPressedBitmap;
		if(getStatus() == STATUS_SELECT_FOR_PRESSED){
			mImageView.setImageBitmap(mPressedBitmap);
		}
	}
	
	public void setImageDrawable(SVGParserRenderer drawable){
	    mSvgView.setSVGRenderer(drawable, null);
    }

	public void setImageBitmap(Bitmap mPressedBitmap){
		mImageView.setImageBitmap(mPressedBitmap);
	}
	
	public static final int STATUS_NORMAL = 0;
	public static final int STATUS_SELECT_FOR_PRESSED = 1;
	private int mStatus = STATUS_NORMAL;
	public void setStatus(int status){
	    mStatus = status;
	}
	public int getStatus(){
	    return mStatus;
	}
	
	/**
	 * 设置文本的内边距
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setTitlePadding(int left, int top, int right, int bottom){
	    mTvTitle.setPadding(left, top, right, bottom);
	}
	
	/**
	 * 设置文本
	 * @param text
	 */
	public void setTitle(String text){
	    mTvTitle.setText(text);
	}
	/**
     * 设置文本颜色
     * @param text
     */
    public void setTitleColor(int color){
        mTvTitle.setTextColor(color);
    }
    
    public void setBgColor(int color){
    	this.setBackgroundColor(color);
    }
	public void setUnBgColor(int unSelectedBgColor){
    	this.mUnSelectedBgColor = unSelectedBgColor;
    }
    
    public void setTextSize(int size){
    	mTvTitle.setTextSize(size);
    }
    
    /*@Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        View view = getChildAt(0);
        view.layout(lp.x, lp.y, lp.x + lp.width, lp.y + lp.height);
    }*/
    
    /*WsLayoutParams lp;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO: currently ignoring padding            
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize =  MeasureSpec.getSize(widthMeasureSpec);            
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize =  MeasureSpec.getSize(heightMeasureSpec);
        
        int measuredChildHeight = 0;
        int measuredChildWidth = 0;
        
       // if (widthSpecMode == MeasureSpec.UNSPECIFIED || heightSpecMode == MeasureSpec.UNSPECIFIED) {
        //        throw new RuntimeException("Dockbar cannot have UNSPECIFIED dimensions");
        //}
       
            View childView = getChildAt(0);
            //该种写法是不会将测量宽和高传递到子控件中的onMeasure函数，这样当进入子的onMeasure函数时，取到的宽和高都是0
            childView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            
            measuredChildWidth = childView.getMeasuredWidth();
            measuredChildHeight = childView.getMeasuredHeight();
            lp = new WsLayoutParams();
            
            lp.width = measuredChildWidth;
            lp.height = measuredChildHeight;
            
            lp.x =  (widthSpecSize - measuredChildWidth)/2;
            lp.y = 0;
                
        
        setMeasuredDimension(widthSpecSize, measuredChildHeight); 
 }*/
}

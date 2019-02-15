package com.romaway.android.phone.widget;

import roma.romaway.commons.android.theme.SkinManager;
import roma.romaway.commons.android.theme.svg.SVGManager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.romaway.android.phone.utils.DrawableUtils;
import com.romaway.android.phone.R;
import com.romaway.common.android.base.Res;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.trevorpage.tpsvg.SVGView;
import com.zhy.autolayout.utils.AutoUtils;

public class RomaDialog extends Dialog implements View.OnClickListener{
	
	private LinearLayout mDialogTitle;
	private TextView mTitleText;
	private FrameLayout mDialogCenter;
	private FrameLayout mDialogCenter2;
	private LinearLayout mBottomRootroot;
	protected Button mLeftButton;
	protected Button mRightButton;
	public Context mContext;
	
	private String titleStr = null;
	private OnClickButtonListener leftListenter = null;
	private OnClickButtonListener rightListenter = null;
	private View mCenterView = null;
	private View mCenterView2 = null;
	private View divider_top;
	private TextView bottom_text;
	/**两个按钮之间的空隙*/
	protected View v_view_space;
	protected View v_view_space_left;
	protected View v_view_space_right;
	protected View closeView;
	
	private ShapeDrawable mDrawable;
	protected int mCorner = AutoUtils.getPercentWidthSize(Res.getInteger(R.integer.roma_dialog_layout_btn_corner));
	private int paintRightBtnColor;
	private int paintRightBtnPressedColor;
	private int paintRightBtnFocusedColor;
	private int paintLeftBtnColor;
	private int paintLeftBtnPressedColor;
	private int paintLeftBtnFocusedColor;
	private float x1, x2, x3, x4, y1, y2, y3, y4;
	
	public RomaDialog(Context context, String title, int layoutId, OnClickButtonListener leftButton, OnClickButtonListener rightButton) {
		this(context, R.style.Theme_CustomDialog);
		titleStr = title;
		leftListenter = leftButton;
		rightListenter = rightButton;
		if(layoutId > 0)
			mCenterView = LayoutInflater.from(mContext).inflate(layoutId, null);

	}
	
	public RomaDialog(Context context, String title, String message, OnClickButtonListener leftButton, OnClickButtonListener rightButton) {
		this(context, R.style.Theme_CustomDialog);
		titleStr = title;
		leftListenter = leftButton;
		rightListenter = rightButton;
		TextView centerView = (TextView)LayoutInflater.from(mContext).inflate(R.layout.abs__text_view2, null);
		mCenterView = centerView;
		centerView.setText(message);
	}

	public RomaDialog(Context context, String layoutType, String title, String message, OnClickButtonListener leftButton, OnClickButtonListener rightButton) {
		this(context, R.style.Theme_CustomDialog);
		titleStr = title;
		leftListenter = leftButton;
		rightListenter = rightButton;

		mCenterView = LayoutInflater.from(mContext).inflate(R.layout.abs__scroll_text_view, null);
		((TextView)mCenterView.findViewById(R.id.message_view)).setText(message);
	}

	public RomaDialog(Context context, String layoutType, String title, String message, String subMessage, OnClickButtonListener leftButton, OnClickButtonListener rightButton) {
		this(context, R.style.Theme_CustomDialog);
		titleStr = title;
		leftListenter = leftButton;
		rightListenter = rightButton;

		mCenterView = LayoutInflater.from(mContext).inflate(R.layout.abs__scroll_text_view, null);
		((TextView)mCenterView.findViewById(R.id.message_view)).setText(message);
		Logger.d("RomaDialog", "KdsDialog2  " + subMessage);
		if (!StringUtils.isEmpty(subMessage)) {
			mCenterView2 = LayoutInflater.from(mContext).inflate(R.layout.abs__scroll_sub_text_view, null);
			((TextView)mCenterView2.findViewById(R.id.message_view)).setText(subMessage);
			((TextView)mCenterView2.findViewById(R.id.message_view)).setTextColor(0xff899198);
		}
	}

	public RomaDialog(Context context, String layoutType, String title, SpannableStringBuilder message, OnClickButtonListener leftButton, OnClickButtonListener rightButton, int gravity) {
		this(context, R.style.Theme_CustomDialog);
		titleStr = title;
		leftListenter = leftButton;
		rightListenter = rightButton;

		mCenterView = LayoutInflater.from(mContext).inflate(R.layout.abs__scroll_text_view, null);
		((TextView)mCenterView.findViewById(R.id.message_view)).setText(message);
		if(gravity >= 0)
			((TextView)mCenterView.findViewById(R.id.message_view)).setGravity(gravity);
	}

	public RomaDialog(Context context) {
		this(context, R.style.Theme_CustomDialog);
	//	super(context);
		// TODO Auto-generated constructor stub
	}

	public RomaDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

//	private String mLayoutType = LAYOUT_TYPE_DEFAULT;
	public final static String LAYOUT_TYPE_DEFAULT = "LAYOUT_TYPE_DEFAULT";
	public final static String LAYOUT_TYPE_LARGE = "LAYOUT_TYPE_LARGE";

	public boolean isCancelable = true;
	/**
	 * Sets whether this dialog is cancelable with the
	 * {@link KeyEvent#KEYCODE_BACK BACK} key.
	 */
	public void setCancelable(boolean flag) {
		super.setCancelable(flag);
		isCancelable = flag;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		Window window = getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.abs__dialog_layout);
		
		RelativeLayout root = (RelativeLayout) this.findViewById(R.id.root);
		// 对话框圆角背景:
		root.setBackgroundDrawable(DrawableUtils.getShapeDrawable(0xffffffff, Res.getInteger(R.integer.roma_dialog_layout_bg_corner)));

		mDialogTitle = (LinearLayout) this.findViewById(R.id.dialog_title);
		mTitleText = (TextView)this.findViewById(R.id.title_text);
		
		mDialogCenter = (FrameLayout)this.findViewById(R.id.dialog_center_info);
		mDialogCenter2 = (FrameLayout)this.findViewById(R.id.dialog_center_info2);
		if(mCenterView != null)
			mDialogCenter.addView(mCenterView);

		if (mCenterView2 != null) {
			mDialogCenter2.setVisibility(View.VISIBLE);
			mDialogCenter2.addView(mCenterView2);
		}
		mBottomRootroot = (LinearLayout)this.findViewById(R.id.bottom_rootroot);
		
		mLeftButton = (Button) this.findViewById(R.id.CancleButton);
		mRightButton = (Button) this.findViewById(R.id.SureButton);
		
		divider_top = this.findViewById(R.id.divider_top);
		bottom_text = (TextView) this.findViewById(R.id.bottom_text);
		
		v_view_space = this.findViewById(R.id.v_view_space);
		v_view_space_left = this.findViewById(R.id.v_view_space_left);
		v_view_space_right = this.findViewById(R.id.v_view_space_right);
		((SVGView)this.findViewById(R.id.svg_dialog_close)).setSVGRenderer(SVGManager.getSVGParserRenderer(mContext, "roma_svg_close", R.drawable.roma_svg_close), null);
		closeView = findViewById(R.id.fl_dialog_close);
		closeView.setOnClickListener(this);

		//此两行需放在最后
		setOnClickLeftButtonListener(leftListenter);
        setOnClickRightButtonListener(rightListenter);

		setPaintRightBtnColor(SkinManager.getColor("ConfirmBtnColor"));
		setPaintRightBtnPressedColor(SkinManager.getColor("ConfirmBtnPressedColor"));
		setPaintRightBtnFocusedColor(SkinManager.getColor("ConfirmBtnFocusedColor"));
		setPaintLeftBtnColor(SkinManager.getColor("CancleBtnColor"));
		setPaintLeftBtnPressedColor(SkinManager.getColor("CancleBtnPressedColor"));
        setPaintLeftBtnFocusedColor(SkinManager.getColor("CancleBtnFocusedColor"));
        
        if (mRightButton.isPressed()) {
        	setPaintRightBtnPressedColor(paintRightBtnPressedColor);
			mRightButton.setBackgroundDrawable(mDrawable);
		}else if (mRightButton.isFocused()) {
			setPaintRightBtnFocusedColor(paintRightBtnFocusedColor);
			mRightButton.setBackgroundDrawable(mDrawable);
		}else {
			setPaintRightBtnColor(paintRightBtnColor);
			mRightButton.setBackgroundDrawable(mDrawable);
		}
        if (mLeftButton.isPressed()) {
        	setPaintLeftBtnPressedColor(paintLeftBtnPressedColor);
        	mLeftButton.setBackgroundDrawable(mDrawable);
        }else if (mLeftButton.isFocused()) {
        	setPaintLeftBtnFocusedColor(paintLeftBtnFocusedColor);
        	mLeftButton.setBackgroundDrawable(mDrawable);
        }else {
        	setPaintLeftBtnColor(paintLeftBtnColor);
        	mLeftButton.setBackgroundDrawable(mDrawable);
        }
        // 取消按钮
        mLeftButton.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					setPaintLeftBtnFocusedColor(paintLeftBtnFocusedColor);
		        	mLeftButton.setBackgroundDrawable(mDrawable);
				}else {
					setPaintLeftBtnColor(paintLeftBtnColor);
		        	mLeftButton.setBackgroundDrawable(mDrawable);
				}
			}
		});
        mLeftButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					x1=event.getX();
					y1=event.getY();
					setPaintLeftBtnPressedColor(paintLeftBtnPressedColor);
		        	mLeftButton.setBackgroundDrawable(mDrawable);
					break;
				case MotionEvent.ACTION_MOVE:
					x2=event.getX();
					y2=event.getY();
					x3=Math.abs(x2-x1);
					y3=Math.abs(y2-y1);
					x4=Math.abs(mLeftButton.getWidth() - x1);
					y4=Math.abs(mLeftButton.getHeight() - y1);
					if (x3>x4 || y3>y4) {
						setPaintLeftBtnColor(paintLeftBtnColor);
			        	mLeftButton.setBackgroundDrawable(mDrawable);
					}
					break;
				case MotionEvent.ACTION_UP:
					setPaintLeftBtnColor(paintLeftBtnColor);
		        	mLeftButton.setBackgroundDrawable(mDrawable);
					break;
				case MotionEvent.ACTION_CANCEL:
					setPaintLeftBtnColor(paintLeftBtnColor);
		        	mLeftButton.setBackgroundDrawable(mDrawable);
					break;
				default:
					break;
				}
				return false;
			}
		});
        // 确定按钮
        mRightButton.setOnFocusChangeListener(new OnFocusChangeListener() {
        	
        	@Override
        	public void onFocusChange(View v, boolean hasFocus) {
        		// TODO Auto-generated method stub
        		if (hasFocus) {
        			setPaintRightBtnFocusedColor(paintRightBtnFocusedColor);
        			mRightButton.setBackgroundDrawable(mDrawable);
        		}else {
        			setPaintRightBtnColor(paintRightBtnColor);
        			mRightButton.setBackgroundDrawable(mDrawable);
        		}
        	}
        });
        mRightButton.setOnTouchListener(new OnTouchListener() {
        	
        	@Override
        	public boolean onTouch(View v, MotionEvent event) {
        		// TODO Auto-generated method stub
        		switch (event.getAction()) {
        		case MotionEvent.ACTION_DOWN:
        			x1=event.getX();
        			y1=event.getY();
        			setPaintRightBtnPressedColor(paintRightBtnPressedColor);
        			mRightButton.setBackgroundDrawable(mDrawable);
        			break;
        		case MotionEvent.ACTION_MOVE:
        			x2=event.getX();
        			y2=event.getY();
        			x3=Math.abs(x2-x1);
        			y3=Math.abs(y2-y1);
        			x4=Math.abs(mRightButton.getWidth() - x1);
        			y4=Math.abs(mRightButton.getHeight() - y1);
        			if (x3>x4 || y3>y4) {
        				setPaintRightBtnColor(paintRightBtnColor);
        				mRightButton.setBackgroundDrawable(mDrawable);
        			}
        			break;
        		case MotionEvent.ACTION_UP:
        			setPaintRightBtnColor(paintRightBtnColor);
        			mRightButton.setBackgroundDrawable(mDrawable);
        			break;
        		case MotionEvent.ACTION_CANCEL:
        			setPaintRightBtnColor(paintRightBtnColor);
        			mRightButton.setBackgroundDrawable(mDrawable);
        			break;
        			
        		default:
        			break;
        		}
        		return false;
        	}
        });
	}
	
	public View findViewByIdForCenter(int id){
		if(mCenterView != null)
			return mCenterView.findViewById(id);
		
		return null;
			
	}

	/**
	 * 设置顶部分割线可见性
	 * @param visibility
	 */
	public void setVisibilityForTopDivider(int visibility){
	    divider_top.setVisibility(visibility);
	}
	/**
	 * 设置底部文本
	 * @param text
	 */
	public void setBottomText(String text){
	    bottom_text.setVisibility(View.VISIBLE);
	    bottom_text.setText(text);
	}
	public void setOnClickLeftButtonListener(String text, OnClickButtonListener l){
		if(mLeftButton != null)
            mLeftButton.setText(text);
		setOnClickLeftButtonListener(l);
	}
	
	public void setOnClickRightButtonListener(String text, OnClickButtonListener l){
		if(mRightButton != null)
            mRightButton.setText(text);
		setOnClickRightButtonListener(l);
	}

	public void setLeftButtonText(String text){
		if(mLeftButton != null)
            mLeftButton.setText(text);
	}

	public void setRightButtonText(String text){
		if(mRightButton != null)
            mRightButton.setText(text);
	}
	
	public void setOnClickLeftButtonListener(OnClickButtonListener l){
		mOnClickLeftButtonListener = l;
		if(l != null){
			mLeftButton.setVisibility(View.VISIBLE);
			mLeftButton.setOnClickListener(this);
		}else{
			mLeftButton.setVisibility(View.GONE);
		}
		
		setSpace();
	}
	public void setOnClickRightButtonListener(OnClickButtonListener l){
		mOnClickRightButtonListener = l;
		if(l != null){
			mRightButton.setVisibility(View.VISIBLE);
			mRightButton.setOnClickListener(this);
		}else{
			mRightButton.setVisibility(View.GONE);
		}
		
		setSpace();
	}
	/**处理两个按钮之间的空隙问题*/
	protected void setSpace(){
	    if(mRightButton.getVisibility() ==  View.VISIBLE && 
	            mLeftButton.getVisibility() == View.VISIBLE)
	        v_view_space.setVisibility(View.VISIBLE);
	    else
	        v_view_space.setVisibility(View.GONE);
	}
	@Override
	public void setTitle(int titleId) {
		// TODO Auto-generated method stub
		//mTitleText.setText(titleId);
		titleStr = mContext.getResources().getString(titleId);
	}
	
	@Override
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		//mTitleText.setText(title);
		titleStr = title.toString();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		addCenterView(mCenterView);
		if(titleStr == null || !Res.getBoolean(R.bool.kconfigs_dialog_isShowTitleView)){
			mDialogTitle.setVisibility(View.GONE);
			mTitleText.setText("");
			((TextView)mCenterView.findViewById(R.id.message_view)).setTextSize(
					TypedValue.COMPLEX_UNIT_PX, Res.getDimen(R.dimen.roma_dialog_only_content_font_size));
		}else{
			mDialogTitle.setVisibility(View.VISIBLE);
			mTitleText.setText(titleStr);
		}
		
		setOnClickLeftButtonListener(leftListenter);
		setOnClickRightButtonListener(rightListenter);
	}

	public TextView getTitleText(){
		
		return mTitleText;
	}
	
	private void addCenterView(View child){
		if(mDialogCenter.getChildCount() > 0)
			mDialogCenter.removeAllViews();
		
		if(child != null)
			mDialogCenter.addView(child);
	}
	
	public void setCenterView(int layoutId){
		View view = LayoutInflater.from(mContext).inflate(layoutId, null);
		setCenterView(view);
	}
	public void setCenterView(View view){
		mCenterView = view;
	}
	
	public void setBackground(int topResid, int centerResid, int bottomResid){
		if(topResid > 0)
			mDialogTitle.setBackgroundResource(topResid);
		
		if(centerResid > 0)
			mDialogCenter.setBackgroundResource(centerResid);
		
		if(bottomResid > 0)
			mBottomRootroot.setBackgroundResource(bottomResid);
	}
	
	OnClickButtonListener mOnClickLeftButtonListener;
	OnClickButtonListener mOnClickRightButtonListener;
	public interface OnClickButtonListener{
		public void onClickButton(View view);
	}
	
	private boolean isDismissDialog = true;
	public void setOnClickDismissDialog(boolean flag){
		isDismissDialog = flag;
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		isDismissDialog = true;
		
		if(view.getId() == R.id.CancleButton){
			if(mOnClickLeftButtonListener != null)
				mOnClickLeftButtonListener.onClickButton(view);
			
		}else if(view.getId() == R.id.SureButton){
			if(mOnClickRightButtonListener != null)
				mOnClickRightButtonListener.onClickButton(view);
		}
		
		if(isDismissDialog)
			this.dismiss();
	}
	
	public void setPaintRightBtnColor(int color) {
		this.paintRightBtnColor = color;
		mDrawable = new ShapeDrawable(new RoundRectShape(new float[] { mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner }, null, null));
		mDrawable.getPaint().setColor(color);
		mRightButton.setBackgroundDrawable(mDrawable);
	}

	public void setPaintRightBtnPressedColor(int color) {
		this.paintRightBtnPressedColor = color;
		mDrawable = new ShapeDrawable(new RoundRectShape(new float[] { mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner }, null, null));
		mDrawable.getPaint().setColor(color);
	}

	public void setPaintRightBtnFocusedColor(int color) {
		this.paintRightBtnFocusedColor = color;
		mDrawable = new ShapeDrawable(new RoundRectShape(new float[] { mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner }, null, null));
		mDrawable.getPaint().setColor(color);
	}
	
	public void setPaintLeftBtnColor(int color) {
		this.paintLeftBtnColor = color;
		mDrawable = new ShapeDrawable(new RoundRectShape(new float[] { mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner }, null, null));
		mDrawable.getPaint().setColor(color);
	}

	public void setPaintLeftBtnPressedColor(int color) {
		this.paintLeftBtnPressedColor = color;
		mDrawable = new ShapeDrawable(new RoundRectShape(new float[] { mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner }, null, null));
		mDrawable.getPaint().setColor(color);
	}

	public void setPaintLeftBtnFocusedColor(int color) {
		this.paintLeftBtnFocusedColor = color;
		mDrawable = new ShapeDrawable(new RoundRectShape(new float[] { mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner, mCorner }, null, null));
		mDrawable.getPaint().setColor(color);
	}
	
}

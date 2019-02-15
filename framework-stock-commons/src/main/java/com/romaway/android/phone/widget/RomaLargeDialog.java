package com.romaway.android.phone.widget;

import roma.romaway.commons.android.theme.SkinManager;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.romaway.android.phone.R;

public class RomaLargeDialog extends Dialog implements View.OnClickListener{
	
	private LinearLayout mDialogTitle;
	private TextView mTitleText;
	private ScrollView mDialogCenter;
	private LinearLayout mBottomRootroot;
	private Button mLeftButton;
	private Button mRightButton;
	private Context mContext;
	
	private String titleStr = null;
	private RomaDialog.OnClickButtonListener leftListenter = null;
	private RomaDialog.OnClickButtonListener rightListenter = null;
	private View mCenterView = null;
	private View divider_top;
	private TextView bottom_text;
	/**两个按钮之间的空隙*/
	private View v_view_space;
	
	private ShapeDrawable mDrawable;
	private int mCorner = 6;
	private int paintRightBtnColor;
	private int paintRightBtnPressedColor;
	private int paintRightBtnFocusedColor;
	private int paintLeftBtnColor;
	private int paintLeftBtnPressedColor;
	private int paintLeftBtnFocusedColor;
	private float x1, x2, x3, x4, y1, y2, y3, y4;
	
	public RomaLargeDialog(Context context, String title, int layoutId, RomaDialog.OnClickButtonListener leftButton, RomaDialog.OnClickButtonListener rightButton) {
		this(context, R.style.Theme_CustomDialog);
		titleStr = title;
		leftListenter = leftButton;
		rightListenter = rightButton;
		if(layoutId > 0)
			mCenterView = LayoutInflater.from(mContext).inflate(layoutId, null);
		
	}
	
	public RomaLargeDialog(Context context, String title, String message, RomaDialog.OnClickButtonListener leftButton, RomaDialog.OnClickButtonListener rightButton) {
		this(context, R.style.Theme_CustomDialog);
		titleStr = title;
		leftListenter = leftButton;
		rightListenter = rightButton;
		TextView centerView = (TextView)LayoutInflater.from(mContext).inflate(R.layout.abs__text_view2, null);
		mCenterView = centerView;
		centerView.setText(message);
	}
	
	public RomaLargeDialog(Context context) {
		this(context, R.style.Theme_CustomDialog);
	//	super(context);
		// TODO Auto-generated constructor stub
	}

	public RomaLargeDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		Window window = getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.abs__large_dialog_layout);
		
		RelativeLayout root = (RelativeLayout) this.findViewById(R.id.root);
		ShapeDrawable mSd = new ShapeDrawable(
                new RoundRectShape(new float[] { 12, 12, 12, 12, 12, 12, 12, 12 }, null,
                null));
		mSd.getPaint().setColor(0xffffffff);
		root.setBackgroundDrawable(mSd);
		
		
		mDialogTitle = (LinearLayout) this.findViewById(R.id.dialog_title);
		mTitleText = (TextView)this.findViewById(R.id.title_text);
		
		mDialogCenter = (ScrollView)this.findViewById(R.id.largedialog_center_info);
		if(mCenterView != null)
			mDialogCenter.addView(mCenterView);
		mBottomRootroot = (LinearLayout)this.findViewById(R.id.bottom_rootroot);
		
		mLeftButton = (Button) this.findViewById(R.id.CancleButton);
		mRightButton = (Button) this.findViewById(R.id.SureButton);
		
		divider_top = this.findViewById(R.id.divider_top);
		bottom_text = (TextView) this.findViewById(R.id.bottom_text);
		
		v_view_space = this.findViewById(R.id.v_view_space);
		
		
		//此两行需放在最后
		setOnClickLeftButtonListener(leftListenter);
        setOnClickRightButtonListener(rightListenter);
        
        setPaintLeftBtnColor(SkinManager.getColor("CancleBtnColor"));
		setPaintLeftBtnPressedColor(SkinManager.getColor("CancleBtnPressedColor"));
		setPaintLeftBtnFocusedColor(SkinManager.getColor("CancleBtnFocusedColor"));
		setPaintRightBtnColor(SkinManager.getColor("BtnRegisterBgColor"));
		setPaintRightBtnFocusedColor(SkinManager.getColor("BtnRegisterUnBgColor"));
		setPaintRightBtnPressedColor(SkinManager.getColor("BtnRegisterUnBgColor"));
        
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
	public void setOnClickLeftButtonListener(String text, RomaDialog.OnClickButtonListener l){
		if(l != null)
            mLeftButton.setText(text);
		setOnClickLeftButtonListener(l);
	}
	
	public void setOnClickRightButtonListener(String text, RomaDialog.OnClickButtonListener l){
		if(l != null)
            mRightButton.setText(text);
		setOnClickRightButtonListener(l);
	}
	
	public void setOnClickLeftButtonListener(RomaDialog.OnClickButtonListener l){
		mOnClickLeftButtonListener = l;
		if(l != null){
			mLeftButton.setVisibility(View.VISIBLE);
			mLeftButton.setOnClickListener(this);
		}else{
			mLeftButton.setVisibility(View.GONE);
		}
		
		setSpace();
	}
	public void setOnClickRightButtonListener(RomaDialog.OnClickButtonListener l){
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
	private void setSpace(){
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
		if(titleStr == null){
			mDialogTitle.setVisibility(View.GONE);
			mTitleText.setText("");
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
	
	RomaDialog.OnClickButtonListener mOnClickLeftButtonListener;
	RomaDialog.OnClickButtonListener mOnClickRightButtonListener;
	
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

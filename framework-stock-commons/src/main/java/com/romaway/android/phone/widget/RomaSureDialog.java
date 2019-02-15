package com.romaway.android.phone.widget;

import roma.romaway.commons.android.theme.SkinManager;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.romaway.android.phone.R;

public class RomaSureDialog extends Dialog implements View.OnClickListener{
	
	private Button mLeftButton;
	private Button mRightButton;
	private Context mContext;
	private String mMessage;
	
	private OnClickButtonListener leftListenter = null;
	private OnClickButtonListener rightListenter = null;
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
	
	public RomaSureDialog(Context context, String message, OnClickButtonListener leftButton, OnClickButtonListener rightButton) {
		this(context, R.style.Theme_CustomDialog);
		leftListenter = leftButton;
		rightListenter = rightButton;
		mMessage = message;
	}
	
	public RomaSureDialog(Context context) {
		this(context, R.style.Theme_CustomDialog);
	//	super(context);
		// TODO Auto-generated constructor stub
	}

	public RomaSureDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		Window window = getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.abs__sure_dialog_layout);
		
		RelativeLayout root = (RelativeLayout) this.findViewById(R.id.root);
		ShapeDrawable mSd = new ShapeDrawable(
                new RoundRectShape(new float[] { 12, 12, 12, 12, 12, 12, 12, 12 }, null,
                null));
		mSd.getPaint().setColor(0xffffffff);
		root.setBackgroundDrawable(mSd);
		
		TextView message_view = (TextView) this.findViewById(R.id.tv_sure_message_view);
		if(mMessage != null) {
		    message_view.setText(mMessage);
		} else {
		    message_view.setVisibility(View.GONE);
		}
		
		mLeftButton = (Button) this.findViewById(R.id.CancleButton);
		mRightButton = (Button) this.findViewById(R.id.SureButton);
		
		v_view_space = this.findViewById(R.id.v_view_space);
		
		//此两行需放在最后
		setOnClickLeftButtonListener(leftListenter);
        setOnClickRightButtonListener(rightListenter);
        
        setPaintRightBtnColor(SkinManager.getColor("BtnRegisterBgColor"));
        setPaintRightBtnPressedColor(SkinManager.getColor("BtnRegisterUnBgColor"));
        setPaintRightBtnFocusedColor(SkinManager.getColor("BtnRegisterUnBgColor"));
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
	
	public void setOnClickLeftButtonListener(String text, OnClickButtonListener l){
		if(l != null)
            mLeftButton.setText(text);
		setOnClickLeftButtonListener(l);
	}
	
	public void setOnClickRightButtonListener(String text, OnClickButtonListener l){
		if(l != null)
            mRightButton.setText(text);
		setOnClickRightButtonListener(l);
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
	private void setSpace(){
	    if(mRightButton.getVisibility() ==  View.VISIBLE && 
	            mLeftButton.getVisibility() == View.VISIBLE)
	        v_view_space.setVisibility(View.VISIBLE);
	    else
	        v_view_space.setVisibility(View.GONE);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		
		setOnClickLeftButtonListener(leftListenter);
		setOnClickRightButtonListener(rightListenter);
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

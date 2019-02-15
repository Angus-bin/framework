package com.romaway.framework.view;

import com.android.basephone.widget.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditSpinner extends EditText implements PopMenu.OnItemClickListener{
	public static final int STATUS_EMPTY = 0;
	public static final int STATUS_SHOW_CLEAR = 1;
	private int mCurrentStatus = STATUS_SHOW_CLEAR;
	private final int clearButtonRightPadding = -2;
	
	private Bitmap cleanMap;
	private Bitmap showListMap;
	private int cleanMapStartX = 0;
	private int showListMapStartX = 0;
	
	private Context mContext = null;

	private int initPandingRight = 0;
	private boolean isMove = false;
	
	private OnClickClearListener mOnClickClearListener = null;
	public void setOnClickClearListener(OnClickClearListener onClickClearListener){
		mOnClickClearListener = onClickClearListener;
	}
	
	public interface  OnClickClearListener{
		
		 public void onClickClear(View view);
		 
		 public void onClickSpinner(View view);
		 
		 public void onClearChanged();
	}
	
	public EditSpinner(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public EditSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public EditSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context){
		setBackgroundColor(0xffffffff);
		mContext = context;
		
		cleanMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.abs__ic_clear_search_api_holo_light);
		showListMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.abs__ic_list_go);
		
		initPandingRight = getPaddingRight() - 8;
		setPadding(getPaddingLeft(), 
				getPaddingTop(), 
				cleanMap.getWidth() + initPandingRight + clearButtonRightPadding * 2 + showListMap.getWidth(), 
				getPaddingBottom());
	}
	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int x = getWidth() - initPandingRight;
		if(showListMap != null){
			showListMapStartX = x -= showListMap.getWidth();
			canvas.drawBitmap(showListMap, x, (getHeight() - showListMap.getHeight()) / 2, null);
		}
		
		cleanMapStartX = x -= (clearButtonRightPadding + cleanMap.getWidth()); 
		if(mCurrentStatus == STATUS_SHOW_CLEAR){  
			if(cleanMap != null)
				canvas.drawBitmap(cleanMap, x, 
						(getHeight() - cleanMap.getHeight()) / 2, null);
		}
	}
	
	PopMenu mPopMenu;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		float y = event.getY();
		
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				isMove = false;
			break;
			case MotionEvent.ACTION_MOVE:  
				isMove = true;
			break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				if(isMove == false){
					if(showListMap != null && 
							(x >= showListMapStartX && showListMapStartX <= showListMapStartX + showListMap.getWidth())){
						final String[] testArray = {"popmenu1","popmenu2","popmenu3","popmenu4","popmenu5"};
						//if(mPopMenu == null)
							mPopMenu = new PopMenu(mContext, getWidth(), 80);
						mPopMenu.setOnItemClickListener(this);
						mPopMenu.addItems(testArray);
						mPopMenu.showAsDropDown(this);
						
						if(mOnClickClearListener != null)
							mOnClickClearListener.onClickSpinner(this);
						
					}else if(mCurrentStatus == STATUS_SHOW_CLEAR && cleanMap != null &&
							(x >= cleanMapStartX && cleanMapStartX <= cleanMapStartX + cleanMap.getWidth())){
						//Toast.makeText(mContext, "锟斤拷锟斤拷锟斤拷荩锟�, Toast.LENGTH_SHORT).show(); 
						this.setText("");
						if(mOnClickClearListener != null)
							mOnClickClearListener.onClickClear(this);
					}
				}
				
				break;
		}
		return super.onTouchEvent(event);
	}

	public void setStatusChanged(int status){
		mCurrentStatus = status;
	}

	@Override
	public void onTextSelected(CharSequence text) {
		// TODO Auto-generated method stub
		this.setText(text);
		mPopMenu.dismiss();
		mPopMenu = null;
	}
}

package com.romalibs.widget;

import com.romaway.libs.R;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * 顶部窗体
 * @author wanlh
 *
 */
public class TopWindow {

	private WindowManager mWindowManager;
	private WindowManager.LayoutParams wmParams;
	private View topView;
	
	/**
	 * 创建窗体
	 */
	public void creatWindowView(Context context, int layoutId){
		if(mWindowManager == null)
			mWindowManager = (WindowManager)
				context.getSystemService(Context.WINDOW_SERVICE);
		if(topView == null)
			topView = LayoutInflater.from(context).inflate(layoutId,null);
	}
	
	/**
	 * 获取window布局属性
	 * @return
	 */
	private WindowManager.LayoutParams getLayoutParams(int x, int y) {
		if(wmParams == null) {
			DisplayMetrics dm = new DisplayMetrics();
	    	mWindowManager.getDefaultDisplay().getMetrics(dm);
	    	int screenWidth = dm.widthPixels; //屏幕宽
	    	int screenHeight = dm.heightPixels; //屏幕高
	    	
			wmParams = new WindowManager.LayoutParams();

			wmParams.type = android.view.WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;   //����window type
			wmParams.format = PixelFormat.RGBA_8888;   

			wmParams.flags = android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
	                              | android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
			
			wmParams.gravity = Gravity.CENTER|Gravity.TOP;   
			wmParams.x = x;  
			wmParams.y = y;
			
			wmParams.height = screenHeight / 14;
			wmParams.width = screenWidth;
			wmParams.windowAnimations = R.style.TopWindow;//动画
		}
		
		return wmParams;
	}
	
	private View anchor;
	public void setAnchorView(View anchor) {
		this.anchor = anchor;
		/*int[] location = new int[2];
		anchor.getLocationOnScreen(location);
		lx = location[0];
		ly = location[1];*/
	}
	
	public View getAnchorView() {
		return anchor;
	}
	
	private boolean isShow = false;
	private int lx = 0;
	private int ly = 0;
	public void showAtLocation(int x, int y) {
		if(mWindowManager != null && topView != null && !isShow) {
			try{
				mWindowManager.removeView(topView);
			}catch(Exception e) {
				
			}finally {
				lx = x;
				ly = y;
				isShow = true;
				mWindowManager.addView(topView, getLayoutParams(x, y));
			}
		}
	}

	public void show() {
		showAtLocation(lx,ly);
	}

	/**
	 * 关闭结束当前window
	 */
	public void dismiss() {
		if(mWindowManager != null && topView != null && isShow) {
			mWindowManager.removeView(topView);
			isShow = false;
		}
	}
	
	public boolean isShowing() {
		return isShow;
	}
	/**
	 * 获取顶层视图父视图
	 * @return
	 */
	public View getTopView() {
		return topView;	
	}
	
	/**
	 * 获取子视图
	 * @param id
	 * @return
	 */
	public View findViewById(int id) {
		if(topView != null)
			return topView.findViewById(id);
		return null;
	}
}

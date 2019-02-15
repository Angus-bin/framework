package com.romaway.framework.view;

import com.android.basephone.widget.R;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class SysInfo
{ 

	/** 列表离窗体的间隔高度 */
	public static int mListToWindowMarginTopHeight = 119;
	/** 列表左移动箭头 */
	public static PopupWindow mPWMoveArrowLeft;
	/** 列表右移动箭头 */
	public static PopupWindow mPWMoveArrowRight;

	/** 关闭列表左、右移动箭头 */
	public static void closePopupWindow()
	{
		closeLeftArrowPopupWindow();
		closeRightArrowPopupWindow();
	}

	public static void closeLeftArrowPopupWindow()
	{
		if (mPWMoveArrowLeft != null && mPWMoveArrowLeft.isShowing())
		{
			mPWMoveArrowLeft.dismiss();
		}
		mPWMoveArrowLeft = null;
	}

	public static void closeRightArrowPopupWindow()
	{
		if (mPWMoveArrowRight != null && mPWMoveArrowRight.isShowing())
		{
			mPWMoveArrowRight.dismiss();
		}
		mPWMoveArrowRight = null;
	}

	private static View mToastView;
	private static Toast mToastMsg;

	/** 自定义样式消息提示Toast */
	public static void showMessage(Activity activity, String msg)
	{
	    if(msg == null || msg.equals(""))
            return;
	    
		if (mToastView == null)
		{
			mToastView = LayoutInflater.from(activity).inflate(
			        R.layout.abs__toast_view, null);
		}
		if (mToastMsg == null)
		{
			// Toast.makeText(CA.getActivity(), msg, Toast.LENGTH_SHORT);
			mToastMsg = new Toast(activity);
			mToastMsg.setGravity(Gravity.CENTER, 0, 0);
		}
		TextView mTxtView = (TextView) mToastView
		        .findViewById(R.id.txt_toast_view_text);
		mTxtView.setText(msg);
		mToastMsg.setView(mToastView);
		if(!msg.equals(""))
		mToastMsg.show();
	}

	/** 自定义样式消息提示Toast */
	public static void showMessage(Context context, String msg)
	{
	    if(msg == null || msg.equals(""))
            return;
	    
		if (mToastView == null)
		{
			mToastView = LayoutInflater.from(context).inflate(
			        R.layout.abs__toast_view, null);
		}
		if (mToastMsg == null)
		{
			// Toast.makeText(CA.getActivity(), msg, Toast.LENGTH_SHORT);
			mToastMsg = new Toast(context);
			mToastMsg.setGravity(Gravity.CENTER, 0, 0);
		}
		TextView mTxtView = (TextView) mToastView
		        .findViewById(R.id.txt_toast_view_text);
		mTxtView.setText(msg);
		mToastMsg.setView(mToastView);
		if(!msg.equals(""))
		mToastMsg.show();
	}

}

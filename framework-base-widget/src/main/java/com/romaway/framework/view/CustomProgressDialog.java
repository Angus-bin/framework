package com.romaway.framework.view;

import com.android.basephone.widget.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomProgressDialog extends Dialog
{
	private static CustomProgressDialog customProgressDialog = null;
	private static LayoutParams lp;
	private static Window dialogWindow;

	private CustomProgressDialog(Context context, int theme)
	{
		super(context, theme);
	}

	/**
	 * 创建对话框
	 * 
	 * @param context
	 * @return
	 */
	public static CustomProgressDialog createDialog(Context context)
	{
		customProgressDialog = new CustomProgressDialog(context, R.style.abs__CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.abs__customprogressdialog);

		dialogWindow = customProgressDialog.getWindow();
		lp = dialogWindow.getAttributes();

		dialogWindow.setGravity(Gravity.CENTER);

		return customProgressDialog;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{// 开启动画
		if (customProgressDialog == null)
		{
			return;
		}

		ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
		AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
		animationDrawable.start();
	}

	@Override
	public void show()
	{
		super.show();
	}

	/**
	 * @param x
	 *            新位置X坐标
	 * @param y
	 *            新位置Y坐标
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param alpha
	 *            透明度
	 */
	public void show(int x, int y, int width, int height, float alpha)
	{
		lp.x = x; // 新位置X坐标
		lp.y = y; // 新位置Y坐标
		lp.width = width; // 宽度
		lp.height = height; // 高度
		lp.alpha = alpha; // 透明度

		// 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
		// dialog.onWindowAttributesChanged(lp);
		dialogWindow.setAttributes(lp);
		// return customProgressDialog;
		show();
	}

	/**
	 * 请求文字
	 * 
	 * @param strMessage
	 * @return
	 */
	public void setMessage(String strMessage)
	{
		TextView tv = (TextView) customProgressDialog.findViewById(R.id.tv_loadingmsg);
		if (tv != null)
		{
			tv.setText(strMessage);
		}
	}
}

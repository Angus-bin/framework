package com.romaway.framework.view;

import com.romaway.framework.view.CustomProgressDialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;

/**
 * 行情视图View加载器
 * @author wanlh
 *
 */
public abstract class RomaWayBaseViewLoader {
	
	protected Activity activity;
	
	public RomaWayBaseViewLoader(Activity activity){
		this.activity = activity;
	}
	
	public abstract void onRefreshSkin();
	
	public abstract void refresh();
	
	public abstract void autoRefresh();
	
	public View getContentView(){
	    return null;
	}
		
	protected CustomProgressDialog pd;

	public void showNetReqDialog(Context context)
	{
		this.showNetReqDialog("", context);
	}
	/**
	 * 显示带文本文字进度条
	 * @param context
	 * @param message
	 */
	public void showNetReqDialog(Context context, String message)
	{
		this.showNetReqDialog(message, context);
	}
	public void showNetReqDialog(String msg, Context context)
	{
		
		if (pd == null)
		{
			pd = CustomProgressDialog.createDialog(context);//new RomaWayProgressDialog(context);
			pd.setMessage(msg);
			pd.show();

		} else {
			pd.setMessage(msg);
			
			if (!pd.isShowing())
			{
				pd.show();
			}
		}
	}

	public void hideNetReqDialog()
	{
		if (pd != null)
		{
			pd.dismiss();
			pd = null;
		}
	}
	
	/**
	 * 设置监听请求对话框结束
	 * @param listener
	 */
	public void setOnNetReqDialogDismissListener(OnDismissListener listener){
		if(pd != null){
			pd.setOnDismissListener(listener);
		}
	}
	
	public void onResume(){
		
	}

	public void onPause() {
	}

	public void onDestroy() {
	}
}

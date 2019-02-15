package com.actionbarsherlock.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.ActionBarSherlock.OnActionModeFinishedListener;
import com.actionbarsherlock.ActionBarSherlock.OnActionModeStartedListener;
import com.actionbarsherlock.ActionBarSherlock.OnCreatePanelMenuListener;
import com.actionbarsherlock.ActionBarSherlock.OnMenuItemSelectedListener;
import com.actionbarsherlock.ActionBarSherlock.OnPreparePanelListener;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.romaway.framework.view.CustomProgressDialog;

public abstract class SherlockActivity extends Activity implements OnCreatePanelMenuListener, OnPreparePanelListener, OnMenuItemSelectedListener, OnActionModeStartedListener, OnActionModeFinishedListener {
    private ActionBarSherlock mSherlock;

    protected final ActionBarSherlock getSherlock() {
        if (mSherlock == null) {
            mSherlock = ActionBarSherlock.wrap(this, ActionBarSherlock.FLAG_DELEGATE);
        }
        return mSherlock;
    }


    ///////////////////////////////////////////////////////////////////////////
    // Action bar and mode
    ///////////////////////////////////////////////////////////////////////////

    public ActionBar getSupportActionBar() {
        return getSherlock().getActionBar();
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        return getSherlock().startActionMode(callback);
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {}

    @Override
    public void onActionModeFinished(ActionMode mode) {}


    ///////////////////////////////////////////////////////////////////////////
    // General lifecycle/callback dispatching
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getSherlock().dispatchConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getSherlock().dispatchPostResume();
    }

    @Override
    protected void onPause() {
        getSherlock().dispatchPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        getSherlock().dispatchStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        getSherlock().dispatchDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        getSherlock().dispatchPostCreate(savedInstanceState);
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        getSherlock().dispatchTitleChanged(title, color);
        super.onTitleChanged(title, color);
    }

    @Override
    public final boolean onMenuOpened(int featureId, android.view.Menu menu) {
        if (getSherlock().dispatchMenuOpened(featureId, menu)) {
            return true;
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onPanelClosed(int featureId, android.view.Menu menu) {
        getSherlock().dispatchPanelClosed(featureId, menu);
        super.onPanelClosed(featureId, menu);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK)
        {
            
            if(getSupportActionBar() != null && 
                    getSupportActionBar().getKeyboardVisibility() != View.GONE){
                getSupportActionBar().hideKeyboard();
                return true;
            }else
                backKeyCallBack();
                
            return true;
        }  
        if (getSherlock().dispatchKeyEvent(event)) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    /**
     * 监听返回键
     */
    public void backKeyCallBack(){
        this.finish();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSherlock().dispatchSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getSherlock().dispatchRestoreInstanceState(savedInstanceState);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Native menu handling
    ///////////////////////////////////////////////////////////////////////////

    public MenuInflater getSupportMenuInflater() {
        return getSherlock().getMenuInflater();
    }

    public void invalidateOptionsMenu() {
        getSherlock().dispatchInvalidateOptionsMenu();
    }

    public void supportInvalidateOptionsMenu() {
        invalidateOptionsMenu();
    }

    @Override
    public final boolean onCreateOptionsMenu(android.view.Menu menu) {
        return getSherlock().dispatchCreateOptionsMenu(menu);
    }

    @Override
    public final boolean onPrepareOptionsMenu(android.view.Menu menu) {
        return getSherlock().dispatchPrepareOptionsMenu(menu);
    }

    @Override
    public final boolean onOptionsItemSelected(android.view.MenuItem item) {
        return getSherlock().dispatchOptionsItemSelected(item);
    }

    @Override
    public void openOptionsMenu() {
        if (!getSherlock().dispatchOpenOptionsMenu()) {
            super.openOptionsMenu();
        }
    }

    @Override
    public void closeOptionsMenu() {
        if (!getSherlock().dispatchCloseOptionsMenu()) {
            super.closeOptionsMenu();
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // Sherlock menu handling
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_OPTIONS_PANEL) {
            return onCreateOptionsMenu(menu);
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (featureId == Window.FEATURE_OPTIONS_PANEL) {
            return onPrepareOptionsMenu(menu);
        }
        return false;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (featureId == Window.FEATURE_OPTIONS_PANEL) {
            return onOptionsItemSelected(item);
        }
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }


    ///////////////////////////////////////////////////////////////////////////
    // Content
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void addContentView(View view, LayoutParams params) {
        getSherlock().addContentView(view, params);
    }

    @Override
    public void setContentView(int layoutResId) {
        getSherlock().setContentView(layoutResId);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        getSherlock().setContentView(view, params);
    }

    @Override
    public void setContentView(View view) {
        getSherlock().setContentView(view);
    }

    public void requestWindowFeature(long featureId) {
        getSherlock().requestFeature((int)featureId);
    }

    @Override
    public View findViewById(int id) {
        getSherlock().ensureActionBar();
        return super.findViewById(id);
    }


    ///////////////////////////////////////////////////////////////////////////
    // Progress Indication
    ///////////////////////////////////////////////////////////////////////////

    public void setSupportProgress(int progress) {
        getSherlock().setProgress(progress);
    }

    public void setSupportProgressBarIndeterminate(boolean indeterminate) {
        getSherlock().setProgressBarIndeterminate(indeterminate);
    }

    public void setSupportProgressBarIndeterminateVisibility(boolean visible) {
        getSherlock().setProgressBarIndeterminateVisibility(visible);
    }

    public void setSupportProgressBarVisibility(boolean visible) {
        getSherlock().setProgressBarVisibility(visible);
    }

    public void setSupportSecondaryProgress(int secondaryProgress) {
        getSherlock().setSecondaryProgress(secondaryProgress);
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
			if(context != null){
				pd =  CustomProgressDialog.createDialog(context);
				pd.setMessage(msg);
				pd.show();
			}

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

	/**
	 * 显示对话框
	 * 
	 * @param title
	 *            对话框标题
	 * @param message
	 *            内容
	 * @param okButton
	 *            确定按钮文字,若为null,则表示不显示此按钮
	 * @param cancelButton
	 *            取消按钮文字，若为null,则表示不显示此按钮
	 * @param okOnClickListener
	 *            确定按钮响应事件，若为null,则默认为关闭Diolog
	 * @param cancelOnClickListener
	 *            取消按钮响应时间，若未null,则默认为关闭Diolog
	 */
	public void showDialog(String title, String message, String okButton,
	        String cancelButton,
	        DialogInterface.OnClickListener okOnClickListener,
	        DialogInterface.OnClickListener cancelOnClickListener)
	{

		Builder b = new AlertDialog.Builder(this);
		b.setTitle(title);
		b.setMessage(message);

		if (!TextUtils.isEmpty(okButton))
		{
			if (okOnClickListener == null)
			{
				b.setPositiveButton(okButton,
				        new DialogInterface.OnClickListener()
				        {

					        @Override
					        public void onClick(DialogInterface dialog,
					                int which)
					        {
						        dialog.dismiss();
					        }
				        });
			} else
			{
				b.setPositiveButton(okButton, okOnClickListener);
			}

		}

		if (!TextUtils.isEmpty(cancelButton))
		{
			if (cancelOnClickListener == null)
			{
				b.setNegativeButton(cancelButton,
				        new DialogInterface.OnClickListener()
				        {

					        @Override
					        public void onClick(DialogInterface dialog,
					                int which)
					        {
						        dialog.dismiss();
					        }
				        });
			} else
			{
				b.setNegativeButton(cancelButton, cancelOnClickListener);
			}
		}

		b.show();
	}
}

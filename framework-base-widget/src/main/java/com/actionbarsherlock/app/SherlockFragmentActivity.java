package com.actionbarsherlock.app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Watson;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.android.basephone.widget.R;
import com.trevorpage.tpsvg.SVGParserRenderer;

import static com.actionbarsherlock.ActionBarSherlock.OnActionModeFinishedListener;
import static com.actionbarsherlock.ActionBarSherlock.OnActionModeStartedListener;

/** @see {@link android.support.v4.app.Watson} */
public class SherlockFragmentActivity extends Watson implements OnActionModeStartedListener, OnActionModeFinishedListener {
    private static final String TAG = "SherlockFragmentActivity";

    private ActionBarSherlock mSherlock;
    private boolean mIgnoreNativeCreate = false;
    private boolean mIgnoreNativePrepare = false;
    private boolean mIgnoreNativeSelected = false;

    private final ActionBarSherlock getSherlock() {
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//为Activity添加默认的svg格式图片
		if (getSupportActionBar()!=null) {
			getSupportActionBar().setLeftSvgIcon(
					new SVGParserRenderer(this, R.drawable.abs__navigation_back));
		}
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
        if (getSherlock().dispatchKeyEvent(event)) {
            return true;
        }
        return super.dispatchKeyEvent(event);
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
        if (ActionBarSherlock.DEBUG) Log.d(TAG, "[getSupportMenuInflater]");

        return getSherlock().getMenuInflater();
    }

    public void invalidateOptionsMenu() {
        if (ActionBarSherlock.DEBUG) Log.d(TAG, "[invalidateOptionsMenu]");

        getSherlock().dispatchInvalidateOptionsMenu();
    }

    public void supportInvalidateOptionsMenu() {
        if (ActionBarSherlock.DEBUG) Log.d(TAG, "[supportInvalidateOptionsMenu]");

        invalidateOptionsMenu();
    }

    @Override
    public final boolean onCreatePanelMenu(int featureId, android.view.Menu menu) {
        if (ActionBarSherlock.DEBUG) Log.d(TAG, "[onCreatePanelMenu] featureId: " + featureId + ", menu: " + menu);

        if (featureId == Window.FEATURE_OPTIONS_PANEL && !mIgnoreNativeCreate) {
            mIgnoreNativeCreate = true;
            boolean result = getSherlock().dispatchCreateOptionsMenu(menu);
            mIgnoreNativeCreate = false;

            if (ActionBarSherlock.DEBUG) Log.d(TAG, "[onCreatePanelMenu] returning " + result);
            return result;
        }
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public final boolean onCreateOptionsMenu(android.view.Menu menu) {
        return true;
    }

    @Override
    public final boolean onPreparePanel(int featureId, View view, android.view.Menu menu) {
        if (ActionBarSherlock.DEBUG) Log.d(TAG, "[onPreparePanel] featureId: " + featureId + ", view: " + view + ", menu: " + menu);

        if (featureId == Window.FEATURE_OPTIONS_PANEL && !mIgnoreNativePrepare) {
            mIgnoreNativePrepare = true;
            boolean result = getSherlock().dispatchPrepareOptionsMenu(menu);
            mIgnoreNativePrepare = false;

            if (ActionBarSherlock.DEBUG) Log.d(TAG, "[onPreparePanel] returning " + result);
            return result;
        }
        return super.onPreparePanel(featureId, view, menu);
    }

    @Override
    public final boolean onPrepareOptionsMenu(android.view.Menu menu) {
        return true;
    }

    @Override
    public final boolean onMenuItemSelected(int featureId, android.view.MenuItem item) {
        if (ActionBarSherlock.DEBUG) Log.d(TAG, "[onMenuItemSelected] featureId: " + featureId + ", item: " + item.getTitle());

        if (featureId == Window.FEATURE_OPTIONS_PANEL && !mIgnoreNativeSelected) {
            mIgnoreNativeSelected = true;
            boolean result = getSherlock().dispatchOptionsItemSelected(item);
            mIgnoreNativeSelected = false;

            if (ActionBarSherlock.DEBUG) Log.d(TAG, "[onMenuItemSelected] returning " + result);
            return result;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public final boolean onOptionsItemSelected(android.view.MenuItem item) {
        return false;
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
    
    /*+ add by wanlaihuan 2014-06-21 通过Fragment onCreateOptionsMenu创建菜单*/
    Fragment mCurrentFragment = null;
    public void setSherlockFragment(Fragment fragment){
    	mCurrentFragment =  fragment;
    }
    
    public SherlockFragment getSherlockFragment(){
    	SherlockFragment sherlockFragment = null;
    	if(mCurrentFragment instanceof SherlockFragment){
    		sherlockFragment = (SherlockFragment)mCurrentFragment;
    	}
    	return sherlockFragment;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Sherlock menu handling
    ///////////////////////////////////////////////////////////////////////////

    public boolean onCreateOptionsMenu(Menu menu) {
        
    	SherlockFragment sherlockFragment = null;
    	if(currentPriority == MENU_PRIORITY_FRGMENT && 
    	        (sherlockFragment = getSherlockFragment()) != null){
    	  //先清空右边菜单
            this.getSupportActionBar().getActionBarView().setMenuDefaultLayout(-1);
    		sherlockFragment.onCreateOptionsMenu(menu);
    	}else
    	    getSupportActionBar().getActionBarView().
    	        setMenuDefaultLayout(R.layout.roma_common_actionbar_right_layout);
    	
        return true;
    }

    public static final int MENU_PRIORITY_FRGMENT = 0;
    public static final int MENU_PRIORITY_ACTIVITY = 1;
    private int currentPriority = MENU_PRIORITY_FRGMENT;
    /**
     * 设置右上角菜单优先级，默认是Fragment菜单优先级高于Activity
     * @param priority
     */
    public void setMenuPriority(int priority){
        currentPriority = priority;
    }
    /**
     * 获取右上角菜单优先级
     * @return
     */
    public int getMenuPriority(){
        return currentPriority;
    }
    
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

	public boolean onOptionsItemSelected(MenuItem item) {
		SherlockFragment sherlockFragment = null;
    	if((sherlockFragment = getSherlockFragment()) != null)
    		sherlockFragment.onOptionsItemSelected(item);
		
		return true;
	}
	
	/**用于监听Fragment 被启动*/
	public void onFragmentResumeListener(SherlockFragment fragment){
		
	}
	/*- add by wanlaihuan 2014-06-21 通过Fragment onCreateOptionsMenu创建菜单*/
}

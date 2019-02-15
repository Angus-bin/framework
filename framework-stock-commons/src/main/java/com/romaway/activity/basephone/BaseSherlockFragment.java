package com.romaway.activity.basephone;

import roma.romaway.commons.android.webkit.ReEventsController;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.internal.widget.ActionBarView.OnLoadMenuActionListener;
import com.actionbarsherlock.view.Menu;
import com.romaway.android.phone.KActivityMgr;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.R;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.android.phone.keyboardelf.Roma_SearchActivity;
import com.romaway.android.phone.keyboardelf.RomaSearchActivity;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.phone.ISubTabView;
import com.romaway.commons.log.Logger;

@SuppressLint("ValidFragment")
public class BaseSherlockFragment extends SherlockFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
	@SuppressLint("ValidFragment")
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		//initSkin();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//onAttach(mActivity);
	    
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	protected final ViewPostFlag postFlag = new ViewPostFlag();
	private View autoRefreshView = null;
	/**
	 * 启动自动刷新功能，关闭自动刷新功能，参数设置为null
	 * @param view
	 */
	public void postAutoRefresh(View view){
		autoRefreshView = view;
		if (SysConfigs.duringTradeTime() && view != null)
		{
			postFlag.addFlag(view, runHQZX);
			postFlag.allPost();
			
		} else
		{
			postFlag.removeCallbacks();
			postFlag.destory();
		}
	}
	
	private boolean autoRefresh24HFlag = false;
	/**
	 * 设置该标志一天24小时都会自动刷新
	 * @param falg24H
	 */
	public void setAutoRefresh24HFlag(boolean falg24H){
		autoRefresh24HFlag = falg24H;
	}

    public boolean isAutoRefresh24HFlag() {
        return autoRefresh24HFlag;
    }

    boolean isCanAutoRefresh = true;
	/**设置临时是否自动刷新*/
	public void setCanAutoRefresh(boolean isCanAutoRefresh){
		this.isCanAutoRefresh = isCanAutoRefresh;
	}
	
	private Runnable runHQZX = new Runnable()
	{
		@Override
		public void run()
		{
		    if (isCanAutoRefresh){
                currentRefreshIsAuto = true;
                if(autoRefreshView != null) 
                    autoRefreshView.removeCallbacks(runHQZX);
                autoRefresh();
            }else
                currentRefreshIsAuto = false;
		    
//		    if(autoRefreshView != null && Configs.getInstance().getHqRefreshTimes() > 0){
//		    	Logger.d("自动刷新", "开启-1");
//		        autoRefreshView.postDelayed(runHQZX, Configs.getInstance()
//			        .getHqRefreshTimes());
//		    }
		}
	};
	
	/**
     * 当前刷新类型是否是自动刷新的
     */
    public boolean currentRefreshIsAuto = false;
    /**
     * 获取当前刷新是否是自动刷新
     * @return
     */
    public boolean getCurrentRefreshIsAuto(){
        return currentRefreshIsAuto;
    }
    
    /**
     * 设置当前刷新标志
     * @param autoFlag
     */
    public void setCurrentRefreshIsAuto(boolean autoFlag){
        currentRefreshIsAuto = autoFlag;
    }
    
    public void refresh() {
        currentRefreshIsAuto = false;
        //先停掉自动刷新
        if(autoRefreshView != null)
            autoRefreshView.removeCallbacks(runHQZX);
    }
    public void autoRefresh() {
        currentRefreshIsAuto = true;
    }
    @Override
	public void onResume() {
        // [统一处理]优先统一置为false, 避免不需要该入口的页面显示优问入口:
        BaseSherlockFragmentActivity.isShowFloatWindow = false;
        BaseSherlockFragmentActivity.mView = null;
		super.onResume();
		//initSkin();
	}

    @Override
    public void onResumeInit() {
        super.onResumeInit();

        if (mActivity instanceof BaseSherlockFragmentActivity)
            ((BaseSherlockFragmentActivity)mActivity).showFloatWindow();
    }

    @Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
        BaseSherlockFragmentActivity.isShowFloatWindow = false;
		super.onPause();
		postFlag.removeCallbacks();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		postFlag.destory();
	}
	/**
	 * 启动键盘精灵
	 */
	public void showAddUserStockDialog()
	{
        KActivityMgr.switchWindow((ISubTabView)mActivity, RomaSearchActivity.class, false);
	}

	@Override
    public void onCreateOptionsMenu(Menu menu) {  
        //Nothing to see here.
        super.onCreateOptionsMenu(menu);
        
        mActivity.getSupportActionBar().setOnLoadMenuActionListener(new OnLoadMenuActionListener(){
            @Override
            public void onLoadMenuAction(View menuItemView) {
                // TODO Auto-generated method stub
                onMenuItemAction(menuItemView);
            }
        });
	}
	 
	private ReEventsController mReEventController;
    /**
     * 用于处理右上角的菜单布局
     */
    public void onMenuItemAction(View view){
        if(view.getId() == R.id.sb_refresh){
        	if(mReEventController == null)
        		mReEventController = new ReEventsController();
        	//300毫秒之内重复点击刷新是无效的
        	if(mReEventController.isRepeat(view, 500))
        		return;
        	
            refresh();
            
        }else if(view.getId() == R.id.sb_search){
            showAddUserStockDialog();
        }
    }
 
    /**
     * 开始刷新
     */
    public void showNetReqProgress(){
        mActionBar.showNetReqProgress();
    }
    
    /**
     * 停止刷新
     */
    public void hideNetReqProgress(){
        mActionBar.hideNetReqProgress();
        //从新开启自动刷新
	    if((RomaSysConfig.hqRefreshTimes > 0) &&
	        		(autoRefresh24HFlag || (SysConfigs.duringTradeTime() && autoRefreshView != null))){
	        autoRefreshView.postDelayed(runHQZX, RomaSysConfig.hqRefreshTimes);
	    }
    }
    
    @Override
    public void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
    		String key) {
    }
    
    /**
     * 用于回调上层界面处理界面更新问题
     * @param tag
     */
    public void onUpdateActivityUi(Object tag){
    	if(((BaseSherlockFragmentActivity)mActivity) != null)
    		((BaseSherlockFragmentActivity)mActivity).onUpdateActivityUi(tag);
    }
    
    
    /**
     * 直接触发弹出自定义键盘
     * @param defKeyboardXmlId  键盘xml布局的资源ID
     * @param editBottomY   检测输入框的位置，用于处理键盘遮挡问题
     */
    public void loadRomaKeyboard(int defKeyboardXmlId, int editBottomY,
            OnKeyboardActionListener listener,OnClickListener hideButtonListener){
        ((BaseSherlockFragmentActivity)mActivity).loadRomaKeyboard(
                defKeyboardXmlId, editBottomY, listener,hideButtonListener);
        
    }
    
    /**
     * 隐藏键盘
     */
    public void hideRomaKeyboard(){
        ((BaseSherlockFragmentActivity)mActivity).hideRomaKeyboard();
    }
    
    /**
     * 入栈处理
     * @param fragment
     */
    public void switchWebViewForStack(Fragment fragment){
        ((BaseSherlockFragmentActivity)mActivity).switchWebViewForStack(fragment);
    }
    
    /**
     * 不入栈处理
     * @param fragment
     */
    public void switchWebViewForStackNull(Fragment fragment){
        ((BaseSherlockFragmentActivity)mActivity).switchWebViewForStackNull(fragment);
    }
    
    public void finishWebView(){
        if(mActivity != null)
            ((BaseSherlockFragmentActivity)mActivity).finishWebView();
    }
    
    public void finishActivity(){
        ((BaseSherlockFragmentActivity)mActivity).finishActivity();
    }
    
    /**
     * 仅仅绑定输入框的键盘点击时才弹出键盘
     * @param editText
     * @param defKeyboardXmlId
     */
    public void bundingRomaKeyboard(EditText editText, int defKeyboardXmlId){
        ((BaseSherlockFragmentActivity)mActivity).
            bundingRomaKeyboard(editText, defKeyboardXmlId);
    }
    
    /**
     * 用于绑定输入框的键盘同时弹出键盘
     * @param editText
     * @param defKeyboardXmlId
     */
    public void bundingRomaKeyboardAndLoad(EditText editText, int defKeyboardXmlId){
        ((BaseSherlockFragmentActivity)mActivity).
            bundingRomaKeyboardAndLoad(editText, defKeyboardXmlId);
    }
    /**
     * 设置键盘隐藏按钮的可见性 
     * @param visibility
     */
    public void setRomaKeyboardHideButtonVisibility(int visibility){
        ((BaseSherlockFragmentActivity)mActivity).
        setRomaKeyboardHideButtonVisibility(visibility);
    }

    /**
     * 检测网络是否可用
     * @return
     */
    public static boolean isNetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

}

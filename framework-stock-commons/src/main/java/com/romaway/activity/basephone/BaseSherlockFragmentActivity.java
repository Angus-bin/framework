package com.romaway.activity.basephone;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.actionbarsherlock.internal.widget.ActionBarView.OnLoadMenuActionListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.romaway.android.phone.KActivityMgr;
import com.romaway.android.phone.R;
import com.romaway.android.phone.config.Configs;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.android.phone.keyboardelf.RomaSearchActivity;
import com.romaway.android.phone.timer.JYTimer;
import com.romaway.android.phone.timer.RZRQ_JYTimer;
import com.romaway.android.phone.widget.DialogFactory;
import com.romaway.android.phone.widget.RomaToast;
import com.romaway.android.phone.widget.dialog.OnClickDialogBtnListener;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.android.base.PermissionsUtils;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.common.android.phone.ISubTabView;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.romaway.framework.app.RomaWayFragmentActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import roma.romaway.commons.android.FloatWindow.FloatWindowManager;
import roma.romaway.commons.android.theme.SkinManager;

public class BaseSherlockFragmentActivity extends RomaWayFragmentActivity implements ISubTabView{

	public static boolean isShowFloatWindow = false;
	public static boolean isNoHideTransition = false;
	protected ISubTabView currentSubTabView;
	/**
	 * 来源Activity的modeID
	 */
	protected int from = 0;
	/**
	 * 目标Activity的modeID;
	 */
	protected int go = 0;

	/**
	 * 当前activity的modeID
	 */
	protected int modeID = 0;

	/**
	 * 当前活动界面的modeID
	 */
	protected static int currentActiveModeID = 0;

	private JYTimerBroadcastReceiver mJYTimerBroadcastReceiver;

	public static String module_id = "";
	public static String productID = "";
	public static View mView;
	public static String item_id = "";
	public static String content = "";

	public BaseSherlockFragmentActivity(){
		currentSubTabView = this;
		setJYTimerCurrent();
		setJYRZRQTimerCurrent();
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		//【bug】解决客户端转后台，太久时间没操作，会出现首页界面点击底部切换不了页面的问题 2015/12/31
		SharedPreferenceUtils.setPreference("system",
				"pressHomeTimeMillis", 0L);
		super.onCreate(arg0);
		//initSkin();
	}

	public ISubTabView getCurrentSubTabView()
	{
		return currentSubTabView;
	}

	protected void setJYTimerCurrent()
	{
		JYTimer.getInstance().setCurrentSubTabView(currentSubTabView);
	}

	protected void setJYRZRQTimerCurrent()
	{
		RZRQ_JYTimer.getInstance().setCurrentSubTabView(currentSubTabView);
	}

	@Override
	public boolean AddZixuan() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delZiXuan() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showOrHideAddStock(boolean showAdd, boolean hideAll) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCurrentSubView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fastTrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getFromID() {
		// TODO Auto-generated method stub
		return from;
	}

	@Override
	public int getModeID() {
		// TODO Auto-generated method stub
		return modeID;
	}

	public void setModeID(int modeID) {
		this.modeID = modeID;
	}

	@Override
	public boolean isEnableFastTrade() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void goBack() {
		// TODO Auto-generated method stub

	}

	@Override
	public Activity getCurrentAct() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	protected void onStart() {
		super.onStart();
		// [统一处理]优先统一置为false, 避免不需要该入口的页面显示客服入口:
		BaseSherlockFragmentActivity.isShowFloatWindow = false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);

		//注册交易超时广播监听器
		mJYTimerBroadcastReceiver = new JYTimerBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("ACTION_JY_TIMER_OUT");
		filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		this.registerReceiver(mJYTimerBroadcastReceiver, filter);

		initSkin();

		// ++【bug】 解决客户端转后台，太久时间没操作，会出现首页界面点击底部切换不了页面的问题 2015/12/31
		long tempHomeTime = SharedPreferenceUtils.getPreference("system", "pressHomeTimeMillis", 0L).longValue();
		if(tempHomeTime > 0){
			if(System.currentTimeMillis() > (tempHomeTime + 30 * 60 *1000)){
				SharedPreferenceUtils.setPreference("system",
						"pressHomeTimeMillis", 0L);
				Logger.d("后台拉起", "=========== finish init");
				this.finish();
//				KActivityMgr.switchWindow(this,
//						Res.getString(R.string.Package_Class_KdsInitActivity),
//						null, true);
				return;
			}
			SharedPreferenceUtils.setPreference("system",
					"pressHomeTimeMillis", 0L);
		}
		// --【bug】 解决客户端转后台，太久时间没操作，会出现首页界面点击底部切换不了页面的问题 2015/12/31

		showFloatWindow();
	}

	private static boolean isFirstCheckAppOps = true;

	public static boolean isFirstCheckAppOps() {
		return isFirstCheckAppOps;
	}

	public static void setIsFirstCheckAppOps(boolean isFirstCheckAppOps) {
		BaseSherlockFragmentActivity.isFirstCheckAppOps = isFirstCheckAppOps;
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (isShowFloatWindow) {
				Logger.e("TAG", PermissionsUtils.getAppOps(OriginalContext.getContext()) ? "已开启拥有悬浮框权限" : "当前应用未拥有悬浮窗显示权限, 将建议打开权限");
				if (!PermissionsUtils.getAppOps(OriginalContext.getContext()) && isFirstCheckAppOps) {
					isFirstCheckAppOps = false;
					Dialog dialog = DialogFactory.getIconDialog(getCurrentAct(),
							"当前应用未拥有\"显示悬浮窗\"权限, 将影响您的正常使用, 请问是否前往应用权限管理打开? ",
							DialogFactory.DIALOG_TYPE_QUESTION,
							null, new OnClickDialogBtnListener() {
								@Override
								public void onClickButton(View view) {
								}
							}, null, new OnClickDialogBtnListener() {
								@Override
								public void onClickButton(View view) {
									PermissionsUtils.openMiuiPermissionActivity(getCurrentAct());
								}
							});
					dialog.setCancelable(false);
					dialog.show();
				}

				FloatWindowManager.getInstance(OriginalContext.getContext()).showFloatWindow(OriginalContext.getContext(),
						R.layout.roma_float_window_small, R.id.small_window_img);
				FloatWindowManager.getInstance(OriginalContext.getContext()).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (!StringUtils.isEmpty(RomaSysConfig.getWechatCode())) {
							Bundle bundle = new Bundle();
							bundle.putString("module_id", module_id);
							bundle.putString("pro_id", productID);
							bundle.putString("content", content);
							KActivityMgr.switchWindow((ISubTabView) BaseSherlockFragmentActivity.this, "roma.romaway.homepage.android.guide.HomePageGuideActivityNew", bundle, false);
						}
					}
				});
			} else {
				FloatWindowManager.getInstance(OriginalContext.getContext()).hideFloatWindow();
			}
		}
	};

	public void showFloatWindow() {
		Logger.d("showFloatWindow", "mView = " + (mView != null));
		try {
			if (mView != null) {
				mView.removeCallbacks(runnable);
				mView.postDelayed(runnable, 3000);
			} else {
				if (isShowFloatWindow) {
					Logger.e("TAG", PermissionsUtils.getAppOps(OriginalContext.getContext()) ? "已开启拥有悬浮框权限" : "当前应用未拥有悬浮窗显示权限, 将建议打开权限");
					if (!PermissionsUtils.getAppOps(OriginalContext.getContext()) && isFirstCheckAppOps) {
						isFirstCheckAppOps = false;
						Dialog dialog = DialogFactory.getIconDialog(getCurrentAct(),
								"当前应用未拥有\"显示悬浮窗\"权限, 将影响您的正常使用, 请问是否前往应用权限管理打开? ",
								DialogFactory.DIALOG_TYPE_QUESTION,
								null, new OnClickDialogBtnListener() {
									@Override
									public void onClickButton(View view) {
									}
								}, null, new OnClickDialogBtnListener() {
									@Override
									public void onClickButton(View view) {
										PermissionsUtils.openMiuiPermissionActivity(getCurrentAct());
									}
								});
						dialog.setCancelable(false);
						dialog.show();
					}

					FloatWindowManager.getInstance(OriginalContext.getContext()).showFloatWindow(OriginalContext.getContext(),
							R.layout.roma_float_window_small, R.id.small_window_img);
					FloatWindowManager.getInstance(OriginalContext.getContext()).setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (!StringUtils.isEmpty(RomaSysConfig.getWechatCode())) {
								Bundle bundle = new Bundle();
								bundle.putString("module_id", module_id);
								bundle.putString("pro_id", productID);
								KActivityMgr.switchWindow((ISubTabView) BaseSherlockFragmentActivity.this, "roma.romaway.homepage.android.guide.HomePageGuideActivityNew", bundle, false);
							}
						}
					});
				} else {
					FloatWindowManager.getInstance(OriginalContext.getContext()).hideFloatWindow();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);

		postFlag.removeCallbacks();
		//注销广播
		unregisterReceiver(mJYTimerBroadcastReceiver);

		if (mView != null) {
			mView.removeCallbacks(runnable);
			mView = null;
		}
		isShowFloatWindow = false;
		hideFloatWindow();
	}

	public void hideFloatWindow() {
		FloatWindowManager.getInstance(OriginalContext.getContext()).hideFloatWindow();
		isNoHideTransition = false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		postFlag.destory();
	}

	private boolean autoRefresh24HFlag = false;
	public void setAutoRefresh24HFlag(boolean falg24H){
		autoRefresh24HFlag = falg24H;
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

	/**
	 * 根据市场Id判断是否属于港股市场(交易时间段):
	 * @param marketId		市场Id
     */
	public void handleHKStockConfigs(int marketId){
		switch (marketId){
			case ProtocolConstant.SE_GG:
			case ProtocolConstant.SE_SGT:
			case ProtocolConstant.SE_HGT:
			case ProtocolConstant.SE_BH:
				SysConfigs.isHkStock = true;
				break;
			default:
				SysConfigs.isHkStock = false;
				break;
		}
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
            
//            if(autoRefreshView != null && Configs.getInstance().getHqRefreshTimes() > 0)
//                autoRefreshView.postDelayed(runHQZX, Configs.getInstance()
//                    .getHqRefreshTimes());
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
    
    @Override
    public void refresh() {
        currentRefreshIsAuto = false;
        if(autoRefreshView != null)
            autoRefreshView.removeCallbacks(runHQZX);
    }
    public void autoRefresh() {
        currentRefreshIsAuto = true;
    }
    
	boolean isCanAutoRefresh = true;
	/**设置临时是否自动刷新*/
	public void setCanAutoRefresh(boolean isCanAutoRefresh){
		this.isCanAutoRefresh = isCanAutoRefresh;
	}
	
	/**
	 * 启动键盘精灵
	 */
	public void showAddUserStockDialog() {
//		if(Res.getBoolean(R.bool.kconfigs_isSupport_keyboard_v2_0))
//			KActivityMgr.switchWindow(currentSubTabView, Roma_SearchActivity.class,
//					false);
//		else
			KActivityMgr.switchWindow(currentSubTabView, RomaSearchActivity.class,
				false);
	}
	
	private class JYTimerBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			//交易超时回调方法
			if("ACTION_JY_TIMER_OUT".equals(intent.getAction()) && 
					intent.getStringExtra("AppPackageName").equals(context.getPackageName())){
					try{
						for(ISubTabView iSubTabView : ActivityStackMgr.jyHistoryWindows)
							((Activity)iSubTabView).finish();
					}catch(Exception e){
						
					}finally{
						ActivityStackMgr.jyHistoryWindows.clear();
					}
				jyTimerOutListener();
				
			}// ++【bug】 解决客户端转后台，太久时间没操作，会出现首页界面点击底部切换不了页面的问题 2015/12/31
			else if (intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
				String reason = intent.getStringExtra("reason");
				if (!"lock".equals(reason)) {
					Logger.d("后台拉起", "=========== home");
					SharedPreferenceUtils.setPreference("system", 
							"pressHomeTimeMillis", System.currentTimeMillis());
	            }
			} 
			// --【bug】 解决客户端转后台，太久时间没操作，会出现首页界面点击底部切换不了页面的问题 2015/12/31
		}
	}
	/**
	 * 监听交易超时函数
	 */
	public void jyTimerOutListener() {
		
	}
	
	/**
	 * 退出交易确认提示
	 */
	protected void loginOut(Context context)
	{
		ActivityStackMgr.jyHistoryWindows.add(currentSubTabView);
		Configs.updateLastTradeTime();
	}
	/**
	 * 退出交易
	 */
	protected void logout(){
		try{
			for(ISubTabView iSubTabView : ActivityStackMgr.jyHistoryWindows)
				((Activity)iSubTabView).finish();
			}catch(Exception e){
				
			}finally{
				ActivityStackMgr.jyHistoryWindows.clear();
			}
			JYTimer.getInstance().stop();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  
        //Nothing to see here.
    	super.onCreateOptionsMenu(menu);
    	if(this.getSherlockFragment() == null ||
    	        getMenuPriority() == MENU_PRIORITY_ACTIVITY){
        	getSupportActionBar().setOnLoadMenuActionListener(new OnLoadMenuActionListener(){
                @Override
                public void onLoadMenuAction(View menuItemView) {
                    // TODO Auto-generated method stub
                    onMenuItemAction(menuItemView);
                }
            });
    	}
        return true;
    }

    /**
     * 用于回调上层界面处理界面更新问题
     * @param tag
     */
    public void onUpdateActivityUi(Object tag){
        
    }
    
    /**
     * 用于处理自定义菜单布局
     * @param view
     */
    public void onMenuItemAction(View view) {
        if(view.getId() == R.id.sb_refresh){
            refresh();
            
        }else if(view.getId() == R.id.sb_search){
            showAddUserStockDialog();
        }
    }

    /**
     * 开始刷新
     */
    public void showNetReqProgress(){
    	if(getSupportActionBar() != null)
    		getSupportActionBar().showNetReqProgress();
    }
    
    /**
     * 停止刷新
     */
    public void hideNetReqProgress(){
    	if(getSupportActionBar() != null)
    		getSupportActionBar().hideNetReqProgress();
        
        //从新开启自动刷新
        if((RomaSysConfig.hqRefreshTimes > 0) &&
        		(autoRefresh24HFlag || (SysConfigs.duringTradeTime() && autoRefreshView != null)))
        autoRefreshView.postDelayed(runHQZX, RomaSysConfig.hqRefreshTimes);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Nothing to see here.
        int id = item.getItemId();
        if(id == android.R.id.home){
			// [个性需求]退出时直接关闭而不先关闭键盘精灵;
                //处理Activity没有加载FRAGMENT的情况
                if(getSherlockFragment() == null)
                    this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    }
    
   private  Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            
            switch(msg.what){
                case 0:
                    getSupportActionBar().loadShowKeyboard(msg.arg1,msg.arg2, listener, hideButtonListener);
                    break;
                    
                case 1://入栈跳转
                    switchFragmentForStack(webviewFragment);
                    break;
                case 2://不入栈跳转
                    switchFragmentForStackNull(webviewFragment);
                    break;
                case 3:
                    removePreviousFragment();
                    finish();
                    break;
                case 4://隐藏键盘
                	getSupportActionBar().hideKeyboard();
                	break;
            }
        }
    };
    
    private OnKeyboardActionListener listener;
    private OnClickListener hideButtonListener;
    /**
     * 直接触发弹出自定义键盘
     * @param defKeyboardXmlId  键盘xml布局的资源ID
     * @param editBottomY   检测输入框的位置，用于处理键盘遮挡问题
     */
    public void loadRomaKeyboard(int defKeyboardXmlId, int editBottomY,
            OnKeyboardActionListener listener,OnClickListener hideButtonListener){
        this.listener = listener;
        this.hideButtonListener = hideButtonListener;
        
        Message msg = Message.obtain();
        msg.arg1 = defKeyboardXmlId;
        msg.arg2 = editBottomY;
        msg.what = 0;
        handler.sendMessage(msg);
    }
    
    public void hideRomaKeyboard(){
    	Message msg = Message.obtain();
        msg.what = 4;
        handler.sendMessage(msg);
    }
    
    Fragment webviewFragment = null;
    public void switchWebViewForStack(Fragment fragment){
        
        webviewFragment = fragment;
        handler.sendEmptyMessage(1);
    }
 
    public void switchWebViewForStackNull(Fragment fragment){
        
        webviewFragment = fragment;
        handler.sendEmptyMessage(2);
    }
    
    public void finishWebView(){
        handler.sendEmptyMessage(3);
    }

    public void hideKeyboard(){
        handler.sendEmptyMessage(4);
    }
    /**
     * 仅仅绑定输入框的键盘点击时才弹出键盘
     * @param editText
     * @param defKeyboardXmlId
     */
    public void bundingRomaKeyboard(EditText editText, int defKeyboardXmlId){
        getSupportActionBar().bundingRomaKeyboard(editText, defKeyboardXmlId, null);
    }
    
    /**
     * 用于绑定输入框的键盘同时弹出键盘
     * @param editText
     * @param defKeyboardXmlId
     */
    public void bundingRomaKeyboardAndLoad(EditText editText, int defKeyboardXmlId){
        getSupportActionBar().bundingRomaKeyboardAndLoad(editText, defKeyboardXmlId, null);
    }
    
    /**
     * 设置键盘隐藏按钮的可见性 
     * @param visibility
     */
    public void setRomaKeyboardHideButtonVisibility(int visibility){
        getSupportActionBar().setRomaKeyboardHideButtonVisibility(visibility);
    }
    
    private void initSkin(){
        if(getSupportActionBar() != null){
            getSupportActionBar().setBackgroundColor(SkinManager.getColor("actionBarBackgoundColor"));
            getSupportActionBar().setBottomBarBackgroundColor(SkinManager.getColor("bottomBarBackgroundColor"));
        }
    }
    
    private Map<String, ActionBarTabSwitchMangger> mActionBarTabSwitchManggerMap = 
    		new HashMap<String, ActionBarTabSwitchMangger>();
    /**
     * 设置Tab切换对象
     * @param tag
	 * @param actionBarTabSwitchMangger
     */
    public void initActionBarTabSwitchMangger(String tag, ActionBarTabSwitchMangger actionBarTabSwitchMangger) {
    	mActionBarTabSwitchManggerMap.put(tag, actionBarTabSwitchMangger);
	}
	
    /**
	 * 获取TAB切换对象
	 * @return
	 */
//	public ActionBarTabSwitchMangger getActionBarTabSwitchMangger(
//			BaseSherlockFragment baseSherlockFragment){
//		
//		for (String key : mActionBarTabSwitchManggerMap.keySet()) {  
//			ActionBarTabSwitchMangger mangger = mActionBarTabSwitchManggerMap.get(key);
//			ActionBarTabConInfo[] actionBarTabConInfoArray = mangger.getActionBarTabConInfoArr();
//			for(int i = 0; i < actionBarTabConInfoArray.length; i++){
//				if(actionBarTabConInfoArray[i].baseSherlockFragment == baseSherlockFragment){
//					return mActionBarTabSwitchManggerMap.get(actionBarTabConInfoArray[i].groupFunKey);
//				}
//			}
//		}  
//		return null;
//	}
	
	/**
	 * 获取TAB切换对象
	 * @param funKey 某个页面的功能key
	 * @return
	 */
	public ActionBarTabSwitchMangger getActionBarTabSwitchMangger(String funKey){
		
		for (String key : mActionBarTabSwitchManggerMap.keySet()) {  
			ActionBarTabSwitchMangger mangger = mActionBarTabSwitchManggerMap.get(key);
			ActionBarTabConInfo[] actionBarTabConInfoArray = mangger.getActionBarTabConInfoArr();
			for(int i = 0; i < actionBarTabConInfoArray.length; i++){
				if(actionBarTabConInfoArray[i].funKey.equals(funKey)){
					return mActionBarTabSwitchManggerMap.get(actionBarTabConInfoArray[i].groupFunKey);
				}
			}
		}  
		return null;
	}
}

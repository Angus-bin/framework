package com.romaway.activity.basephone;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.timer.JYTimer;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.android.phone.KActivityMgr;
import com.romaway.android.phone.R;
import com.romaway.android.phone.config.Configs;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.android.phone.timer.RZRQ_JYTimer;
import com.romaway.common.android.phone.ISubTabView;
import com.romaway.commons.log.Logger;
import com.umeng.analytics.MobclickAgent;

public class BaseSherlockActivity extends SherlockActivity implements ISubTabView{
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
	
	protected ISubTabView currentSubTabView;
	
	private JYTimerBroadcastReceiver mJYTimerBroadcastReceiver;
	
	public BaseSherlockActivity(){
		currentSubTabView = this;
		setJYTimerCurrent();
		setJYRZRQTimerCurrent();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//【bug】解决客户端转后台，太久时间没操作，会出现首页界面点击底部切换不了页面的问题 2015/12/31
		SharedPreferenceUtils.setPreference("system",
				"pressHomeTimeMillis", 0L);
		super.onCreate(savedInstanceState);
		//initSkin();
		
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
		//initSkin();
		
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
	public void jyTimerOutListener(){
		
	}
	
	@Override
	public boolean AddZixuan() {
		// TODO Auto-generated method stub
		
		return false;
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
	
	private Runnable runHQZX = new Runnable()
	{
		@Override
		public void run()
		{
		    Logger.d("tag", "autoRefreshView.postDelayed:"+isCanAutoRefresh);
			if (isCanAutoRefresh){
			    currentRefreshIsAuto = true;
				autoRefresh();
			}else
			    currentRefreshIsAuto = false;
			
			if(RomaSysConfig.hqRefreshTimes > 0)
				autoRefreshView.postDelayed(runHQZX, RomaSysConfig.hqRefreshTimes);
		}
	};
		
	boolean isCanAutoRefresh = true;
	/**设置临时是否自动刷新*/
	public void setCanAutoRefresh(boolean isCanAutoRefresh){
		this.isCanAutoRefresh = isCanAutoRefresh;
	}
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
	}
	public void autoRefresh() {
	    currentRefreshIsAuto = true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
		postFlag.removeCallbacks();
		//注销广播
		unregisterReceiver(mJYTimerBroadcastReceiver);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		postFlag.destory();
	}
	
	/**
	 * 启动键盘精灵
	 */
	public void showAddUserStockDialog()
	{

		
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

	public void setModeID(int modeID) {
		this.modeID = modeID;
	}
	
	@Override
	public int getModeID() {
		// TODO Auto-generated method stub
		return modeID;
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
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    }
}

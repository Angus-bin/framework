package com.romaway.android.phone.timer;

import roma.romaway.commons.android.webkit.WebkitSherlockFragment;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.android.phone.ISubTabView;

/**
 * 交易超時定時器类
 * 
 * @author qinyn
 * 
 */
public class JYTimer
{
    
    public static WebkitSherlockFragment currentJyFragment = null;
	private static JYTimer instance = null;//new JYTimer();

	public static JYTimer getInstance()
	{
	    if(instance == null)
	        instance = new JYTimer();
	    
		return instance;
	}

	public JYTimer(){
	    
	}
	
	public void setOnTimerOutListener(OnTimerOutListener onTimerOutListener)
	{
		TimerRunner.getInstance().initTimerRunner(onTimerOutListener/*new JYTimerNetListener()*/);
	}

	/**
	 * 设置超时时间
	 */
	public void setOutTime(){
	    TimerRunner.getInstance().setDelayTime(RomaSysConfig.getJyOutTime());
	}
	
	/**
	 * 设置超时时间 单位 ms
	 * @param time
	 */
	public void setOutTime(int time){
        TimerRunner.getInstance().setDelayTime(time);
    }
	
	public void start(int time)
    {
        TimerRunner.getInstance().start(time);
    }
	
	public void start()
    {
        TimerRunner.getInstance().start();
    }
    
    public void stop()
    {
        TimerRunner.getInstance().stop();
    }
    public void reset()
    {
        TimerRunner.getInstance().reset();
    }
    
	private void timerStop(String msg)
	{
		try{
			Toast.makeText(OriginalContext.getContext(), msg, Toast.LENGTH_LONG)
	        .show();
			stop();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	protected ISubTabView currentSubTabView;

	public void setCurrentSubTabView(ISubTabView currentSubTabView)
	{
		this.currentSubTabView = currentSubTabView;
	}

	/** 退出交易 */
	private void jyQuit()
	{
		//TradeUserMgr.logout();
		/*if (KActivityMgr.isTrade(currentSubTabView.getModeID(),false))
		{
			System.out.println("\n------交易退出-----哈哈");
			KActivityMgr.onTradeLoginTimeOut();
		}else*/{//主要是用于2014新版移动证劵登录超时的处理
			//发送交易超时广播
			Intent intent = new Intent("ACTION_JY_TIMER_OUT");
			intent.putExtra("AppPackageName", ((Activity)currentSubTabView).getPackageName());
			((Activity)currentSubTabView).sendBroadcast(intent);
		}
		
	}
}

package com.romaway.android.phone.timer;

import roma.romaway.commons.android.webkit.WebkitSherlockFragment;

import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.common.android.phone.ISubTabView;

/**
 * 交易超時定時器类
 * 
 * @author qinyn
 * 
 */
public class RZRQ_JYTimer
{
    public static WebkitSherlockFragment currentRzrqFragment = null;
	private static RZRQ_JYTimer instance = null;//new JYTimer();

	public static RZRQ_JYTimer getInstance()
	{
	    if(instance == null)
	        instance = new RZRQ_JYTimer();
	    
		return instance;
	}

	public RZRQ_JYTimer(){
	    
	}
	
	public void setOnTimerOutListener(OnTimerOutListener onTimerOutListener)
	{
	    RZRQ_TimerRunner.getInstance().initTimerRunner(onTimerOutListener/*new JYTimerNetListener()*/);
	}

	/**
	 * 设置超时时间
	 */
	public void setOutTime(){
	    RZRQ_TimerRunner.getInstance().setDelayTime(RomaSysConfig.getJyOutTime());
	}
	
	/**
	 * 设置超时时间 单位 ms
	 * @param time
	 */
	public void setOutTime(int time){
	    RZRQ_TimerRunner.getInstance().setDelayTime(time);
    }
	
	public void start(int time)
    {
	    RZRQ_TimerRunner.getInstance().start(time);
    }
	
	public void start()
    {
	    RZRQ_TimerRunner.getInstance().start();
    }
    
    public void stop()
    {
        RZRQ_TimerRunner.getInstance().stop();
    }
    public void reset()
    {
        RZRQ_TimerRunner.getInstance().reset();
    }
    
	protected ISubTabView currentSubTabView;

	public void setCurrentSubTabView(ISubTabView currentSubTabView)
	{
		this.currentSubTabView = currentSubTabView;
	}

}

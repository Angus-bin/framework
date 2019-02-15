package com.romaway.android.phone.timer;

import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.commons.log.Logger;

import android.os.Handler;
import android.os.Message;

/**
 * 交易定时器开启类
 * 
 * @author qinyn
 * 
 */
public class RZRQ_TimerRunner
{
	private static RZRQ_TimerRunner instance = new RZRQ_TimerRunner();
	protected static int toStop = 1;
	protected static int toRun = 0;

	public static RZRQ_TimerRunner getInstance()
	{
		return instance;
	}

	// View view;
	private OnTimerOutListener listener;
	private Handler handler;

	public void initTimerRunner(final OnTimerOutListener listener)
	{
		this.listener = listener;
		if (handler == null)
		{
		    handler = new Handler(){

                @Override
                public void handleMessage(Message msg) {
                    // TODO Auto-generated method stub
                    super.handleMessage(msg);
                    
                    if(listener != null)
                        listener.onTime();
                }
		    };
		}
	}

	private static long delay = 3000;
	private Runnable run = new Runnable()
	{
		@Override
		public void run()
		{
		    handler.sendEmptyMessage(0);
		}

	};

	public void setDelayTime(int time)
	{
		delay = time;
		
		Logger.d("定时器", "定时时间："+delay);
	}

	public void stop()
	{
		if (handler != null)
		    handler.removeCallbacks(run);
	}

	public void start(int time)
    {
        if (handler != null && time > 0){
            handler.removeCallbacks(run);
            handler.postDelayed(run, time);
        }
    }
	
	public void start()
	{
	    setDelayTime(RomaSysConfig.getJyOutTime());
		if (handler != null && delay > 0){
		    handler.removeCallbacks(run);
		    handler.postDelayed(run, delay);
		}
	}
	
	public void reset()
    {
	    stop();
	    start();
    }

}

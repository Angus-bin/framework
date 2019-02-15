/**
 * 
 */
package com.romaway.common.protocol.service;

import android.os.Handler;
import android.os.Looper;

import com.romaway.common.net.ANetMsg;

/**
 * @author duminghui
 * 
 */
public class YTNetReceiveListener extends ANetReceiveListener
{

	public YTNetReceiveListener() {
		
	}

	@Override
	public final void onReceive(ANetMsg msg)
	{
		(new Handler(Looper.getMainLooper())).post(new RunnableUI(msg));
	}

	private class RunnableUI implements Runnable
	{
		private ANetMsg msg;

		private RunnableUI(ANetMsg msg)
		{
			this.msg = msg;
		}

		@Override
		public void run()
		{
			onReceive_sub(msg);
		}

	}
}

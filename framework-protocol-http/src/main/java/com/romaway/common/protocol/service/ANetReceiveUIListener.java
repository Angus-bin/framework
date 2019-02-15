/**
 * 
 */
package com.romaway.common.protocol.service;

import android.app.Activity;

import com.romaway.common.net.ANetMsg;

/**
 * @author duminghui
 * 
 */
public class ANetReceiveUIListener extends ANetReceiveListener
{

	private Activity activity;

	public ANetReceiveUIListener(Activity activity)
	{
		this.activity = activity;
	}

	@Override
	public final void onReceive(ANetMsg msg)
	{
	    if(activity != null)
	        activity.runOnUiThread(new RunnableUI(msg));
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

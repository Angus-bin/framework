/**
 * 
 */
package com.romaway.common.android.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.romaway.common.net.ANetMsg;
import com.romaway.common.net.sender.ANetMsgSender;

/**
 * @author duminghui
 * 
 */
public class BaseNetMsgSender extends ANetMsgSender
{
	private ExecutorService pool;

	public BaseNetMsgSender()
	{
		pool = Executors.newCachedThreadPool();
	}

	@Override
	protected boolean isValidMsg(ANetMsg msg)
	{
		return true;
	}

	@Override
	public void send(ANetMsg msg)
	{
		pool.execute(new MessengerRunnable(msg));

	}

	private class MessengerRunnable implements Runnable
	{
		private ANetMsg msg;

		private MessengerRunnable(ANetMsg msg)
		{
			this.msg = msg;
		}

		@Override
		public void run()
		{
			toSend(msg);
		}

	}

	@Override
	public void shutdown()
	{
		if (pool != null)
		{
			pool.shutdown();
		}
	}
}

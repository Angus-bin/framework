/**
 * 
 */
package com.romaway.common.net.timeout;

import com.romaway.common.net.sendingqueue.NetMsgSendingQueue;

/**
 * @author duminghui
 * 
 */
public class NetMsgTimeoutRunnable implements Runnable
{
	@Override
	public void run()
	{
		NetMsgSendingQueue.getInstance().timeouts();
	}

}

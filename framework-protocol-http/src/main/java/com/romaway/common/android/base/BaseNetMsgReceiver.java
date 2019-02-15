/**
 * 
 */
package com.romaway.common.android.base;

import com.romaway.common.net.ANetMsg;
import com.romaway.common.net.receiver.ANetMsgReceiver;

/**
 * @author duminghui
 * 
 */
public class BaseNetMsgReceiver extends ANetMsgReceiver
{
	// private class ReceiveRun implements Runnable
	// {
	// private ANetMsg msg;
	//
	// private ReceiveRun(ANetMsg msg)
	// {
	// this.msg = msg;
	// }
	//
	// @Override
	// public void run()
	// {
	// msg.onReceiveListener();
	// }
	// }

	@Override
	protected boolean isValidMsg(ANetMsg msg)
	{
		return true;
	}

	@Override
	protected void listenerOnReceive(ANetMsg msg)
	{
		msg.onReceiveListener();
		// new Handler(OriginalThreadHandlerMgr.getInstance().getHandler()
		// .getLooper()).post(new ReceiveRun(msg));
	}

}

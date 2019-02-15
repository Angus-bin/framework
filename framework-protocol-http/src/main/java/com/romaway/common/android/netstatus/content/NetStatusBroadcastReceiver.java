/**
 * 
 */
package com.romaway.common.android.netstatus.content;

import com.romaway.common.android.netstatus.NetStatus;
import com.romaway.commons.log.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Keep;
import android.util.Log;

/**
 * @author duminghui
 * 
 */
@Keep
public class NetStatusBroadcastReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		NetStatus.getInstance().readNetStatus(context);
		Logger.i("NetStatusBroadcastReceiver", "NetStatus Change");
	}

}

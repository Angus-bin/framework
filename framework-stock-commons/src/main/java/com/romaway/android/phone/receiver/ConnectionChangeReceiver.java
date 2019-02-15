package com.romaway.android.phone.receiver;

import com.romaway.common.android.base.Res;
import com.romaway.android.phone.R;
import com.romaway.common.android.base.ActivityUtils;
import com.romaway.commons.lang.StringUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 监听网络状态变化广播
 * 
 * @author qinyn
 * 
 */
public class ConnectionChangeReceiver extends BroadcastReceiver
{
	private ConnectivityManager connectivityManager;
	private NetworkInfo info;
	private static final String netACTION = "android.net.conn.CONNECTIVITY_CHANGE";

	@Override
	public void onReceive(Context context, Intent intent)
	{
		String action = intent.getAction();
		if (action.equals(netACTION))
		{
			connectivityManager = (ConnectivityManager) context
			        .getSystemService(Context.CONNECTIVITY_SERVICE);
			info = connectivityManager.getActiveNetworkInfo();
			if (info != null && info.isConnected())
			{
				showMessage(Res.getString(R.string.net_conn_success), context);
			} else
			{
				showMessage(Res.getString(R.string.net_conn_disconnected), context);
			}
		}
	}

	private void showMessage(String s, Context context)
	{
		if (ActivityUtils.isTopActivity(context) && !TextUtils.isEmpty(s))
			Toast.makeText(context, s, Toast.LENGTH_LONG).show();
	}

}

/**
 * 
 */
package com.romaway.android.phone.net;

import android.app.Activity;
import android.widget.Toast;

import com.romaway.android.phone.widget.RomaToast;
import com.romaway.common.android.base.Res;
import com.romaway.android.phone.R;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.protocol.service.ANetReceiveUIListener;
import com.romaway.common.net.ANetMsg;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

/**
 * 显示请求窗口的网络监听器
 * 
 * @author duminghui
 * 
 */
public class ProgressUINetReceiveListener extends ANetReceiveUIListener
{
	protected Activity activity;
	/**
	 * @param activity
	 */
	public ProgressUINetReceiveListener(Activity activity)
	{
		super(activity);
		this.activity = activity;
	}

	@Override
	protected void onReceive_sub(ANetMsg netMsg)
	{
		super.onReceive_sub(netMsg);
	}

	@Override
	protected void onSuccess(NetMsg msg, AProtocol ptl)
	{
//		Toast.makeText(OriginalContext.getContext(), "onSuccess",
//		        Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onServerError(NetMsg msg)
	{
		Logger.d("NetParser", String.format("onServerError:%s",msg.getServerMsg()));
		
		if (activity == null)
		{
			Toast.makeText(OriginalContext.getContext(), msg.getServerMsg(),
			        Toast.LENGTH_SHORT).show();
		} else
		{
			if (StringUtils.isEmpty(msg.getServerMsg()))
            {
				RomaToast.showMessage(activity, Res.getString(R.string.err_net_servererror));
            }else {
				
            	RomaToast.showMessage(activity, msg.getServerMsg());
			}
//			SysInfo.showMessage(activity, "您的网络不给力...");
		}
		
	}

	@Override
	protected void onParseError(NetMsg msg)
	{
		Logger.d("NetParser", String.format("onParseError:%s",msg.toString()));
		if (msg.isAutoRefresh())
			return;
		if (activity == null)
		{
			Toast.makeText(OriginalContext.getContext(), Res.getString(R.string.err_net_parse),
			        Toast.LENGTH_SHORT).show();
		} else
		{
			RomaToast.showMessage(activity, Res.getString(R.string.err_net_parse));
//			SysInfo.showMessage(activity, "您的网络不给力...");
		}
		
	}

	@Override
	protected void onConnError(NetMsg msg)
	{
		Logger.d("NetParser", String.format("onConnError:%s",msg.toString()));
		if (msg.isAutoRefresh())
			return;
		if(activity == null){
			Toast.makeText(OriginalContext.getContext(), Res.getString(R.string.err_net_conn),
			        Toast.LENGTH_SHORT).show();
		} else{
			RomaToast.showMessage(activity, Res.getString(R.string.err_net_conn));
		}
		
	}

	@Override
	protected void onNetError(NetMsg msg)
	{
		Logger.d("NetParser", String.format("onNetError:%s",msg.toString()));
//		Toast.makeText(OriginalContext.getContext(), "onNetError",
//		        Toast.LENGTH_SHORT).show();
		
		if (msg.isAutoRefresh())
			return;
		/*if (activity == null)
		{
			Toast.makeText(OriginalContext.getContext(),
			        Res.getString(R.string.err_net_unknown), Toast.LENGTH_SHORT).show();
		} else
		{
			// SysInfo.showMessage(activity, msg.getServerMsg());
			RomaToast.showMessage(activity, Res.getString(R.string.err_net_unknown));
		}*/
	}

	@Override
	protected void onSentTimeout(NetMsg msg)
	{
		Logger.d("NetParser", String.format("onSentTimeout:%s",msg.toString()));
		
		if (msg.isAutoRefresh())
			return;
		if(activity == null){
			Toast.makeText(OriginalContext.getContext(), Res.getString(R.string.err_net_timeout),
			        Toast.LENGTH_SHORT).show();
		} else{
			RomaToast.showMessage(activity, Res.getString(R.string.err_net_timeout));
		}
		
	}

}

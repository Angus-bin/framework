/**
 * 
 */
package com.romaway.android.phone.net;

import android.app.Activity;

import com.romaway.android.phone.widget.RomaToast;
import com.romaway.common.android.base.Res;
import com.romaway.android.phone.R;
import com.romaway.common.protocol.service.ANetReceiveUIListener;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.commons.log.Logger;

/**
 * 不显示请求窗口的网络监听器
 * 
 * @author duminghui
 * 
 */
public class UINetReceiveListener extends ANetReceiveUIListener
{
	protected Activity activity;

	/**
	 * @param activity
	 */
	public UINetReceiveListener(Activity activity)
	{
		super(activity);
		this.activity = activity;
	}

	@Override
	protected void onSuccess(NetMsg msg, AProtocol ptl)
	{
		// Toast.makeText(OriginalContext.getContext(), "onSuccess",
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onServerError(NetMsg msg)
	{
//		if (activity != null && activity.isFinishing()){
//            return;
//        }else{
		if (msg.isAutoRefresh())
			return;
		if (activity != null)
			RomaToast.showMessage(activity, msg.getServerMsg());
//        }
	}

	@Override
	protected void onParseError(NetMsg msg)
	{
//		if (activity != null && activity.isFinishing()){
//            return;
//        }else{
		if (msg.isAutoRefresh())
			return;
		if (activity != null)
			RomaToast.showMessage(activity, Res.getString(R.string.err_net_parse));
//        }
	}

	@Override
	protected void onConnError(NetMsg msg)
	{
//		if (activity != null && activity.isFinishing()){
//            return;
//        }else{
            if (msg.isAutoRefresh())
                return;
            if (activity != null) {
				if(Logger.getDebugMode())
					RomaToast.showMessage(activity, Res.getString(R.string.err_net_conn));
				else
					Logger.e("KdsInitActivity", Res.getString(R.string.err_net_conn));
			}
//        }
	}

	@Override
	protected void onNetError(NetMsg msg)
	{
//		if (activity != null && activity.isFinishing()){
//            return;
//        }else{
		if (msg.isAutoRefresh())
			return;
		if (activity != null)
			RomaToast.showMessage(activity, Res.getString(R.string.err_net_unknown));
//        }
	}

	@Override
	protected void onSentTimeout(NetMsg msg)
	{
//		if (activity != null && activity.isFinishing()){
//			return;
//		}else{
		if (msg.isAutoRefresh())
			return;
		if (activity != null) {
			if(Logger.getDebugMode())
				RomaToast.showMessage(activity, Res.getString(R.string.err_net_timeout));
			else
				Logger.e("UINetReceiveListener", Res.getString(R.string.err_net_timeout));
		}
//		}
	}

}

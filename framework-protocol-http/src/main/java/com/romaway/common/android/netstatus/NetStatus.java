/**
 * 
 */
package com.romaway.common.android.netstatus;


import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.Keep;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 网络状态工具
 * 
 * @author duminghui
 * 
 */
@Keep
public class NetStatus
{
	private static final NetStatus instance = new NetStatus();

	private volatile boolean isReadedStatus;
	private volatile boolean isConn;
	private volatile String proxyHost;
	private volatile int proxyPort;
	private volatile String localIp;

	private NetStatus()
	{

	}

	public final static NetStatus getInstance()
	{
		return instance;
	}

	public boolean isConn(Context context)
	{
		if (!isReadedStatus)
		{
			readNetStatus(context);
		}
		return isConn;
	}

	/**
	 * 检查手机联网信息
	 * @param context
	 */
	public void readNetStatus(Context context)
	{
		ConnectivityManager conManager = (ConnectivityManager) context
		        .getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取代表联网状态的NetWorkInfo对象
		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
		String apn = "";
		if (networkInfo != null && networkInfo.isConnected())
		{
			if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
			{
				proxyHost = "";
				proxyPort = 0;
				
				
				//读取ip地址
				//获取wifi服务  
				WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); 

				//读取wifi下ip地址信息
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();       
				int ipAddress = wifiInfo.getIpAddress();   
				localIp = intToIp(ipAddress);   
				

			} else
			{
				apn = networkInfo.getExtraInfo();
				if (apn != null && apn.length()>0 && (apn.toLowerCase().contains("3gwap")
						 || apn.toLowerCase().contains("cmnet")
						 || apn.toLowerCase().contains("cmwap")
						 || apn.toLowerCase().contains("3gnet")
						 || apn.toLowerCase().contains("uninet"))) 
				{
					proxyHost = "";
					proxyPort = 0;
				} else
				{
					proxyHost = android.net.Proxy.getDefaultHost();
					proxyPort = android.net.Proxy.getDefaultPort();
				}
				
				//读取ip地址
				try {
					for (Enumeration<NetworkInterface> en = NetworkInterface
							.getNetworkInterfaces(); en.hasMoreElements();) {
						NetworkInterface intf = en.nextElement();
						for (Enumeration<InetAddress> enumIpAddr = intf
								.getInetAddresses(); enumIpAddr
								.hasMoreElements();) {
							InetAddress inetAddress = enumIpAddr.nextElement();
							if (!inetAddress.isLoopbackAddress()) {
								localIp = inetAddress.getHostAddress()
										.toString();
							}
						}
					}
				} catch (SocketException ex) {
					Log.e("WifiPreference IpAddress", ex.toString());
					localIp = "";
				}
				

			}
			isConn = true;
		} else
		{
			isConn = false;
		}
		isReadedStatus = true;
		NetworkInfo wifiNetInfo = conManager
		        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobileNetInfo = conManager
		        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		String a = networkInfo != null ? networkInfo.isConnected() + ""
		        : "null";
		String m = mobileNetInfo != null ? mobileNetInfo.isConnected() + ""
		        : "null";
		String w = wifiNetInfo != null ? wifiNetInfo.isConnected() + ""
		        : "null";
		Logger.i("NetWorkStatusTool", String.format(
		        "NetStatus:A:%s,M:%s,W:%s|isConn:%s|apn:%s|h:%s,p:%s", a, m, w,
		        isConn, apn, proxyHost, proxyPort));
	}
	
	
	/**
	 * 显示网络信息，以协助客服/技术支持人员检查网络
	 * @param context
	 * @param title
	 * @param onClickListener
	 */
	public static void showCurrentAPN(Context context, String title,DialogInterface.OnClickListener onClickListener)
    {
       
        Builder b = new AlertDialog.Builder(context).setTitle(title)
                .setMessage(showCurrentAPN(context));
        //b.show();
       /* b.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int whichButton)
            {
                Intent mIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                m.pleaseKing().startActivityForResult(mIntent,
                        K.RESULTCODE_ACTION_WIRELESS_SETTINGS);
                if (isExit)
                {
                    dialog.dismiss();
                    m.close();
                }
            }
        });*/
        if (onClickListener == null)
        {
            b.setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    
                        dialog.dismiss();
                    
                }
            });
        }
        else
            b.setPositiveButton("确定", onClickListener); 
   
        b.show();
    }
	/**
	 * 获取手机ip地址
	 * @return
	 */
	public String getLocalIp(){
		if (StringUtils.isEmpty(localIp)){
			return "";
		}else{
			return localIp;
		}
	}
	/**
     * 读取当前网络设置信息
     * @param context
     * @return
     */
    public static String showCurrentAPN(Context context)
    {
        /*
        wifi: 是否可用
        APN:
        HOST:
        POST:
        */
        boolean netStatus = false;
        boolean wifiStatus = false;
        boolean mobiStatus = false;
        String cAPN = "";
        String cHOST = "";
        int cPORT = 0;
        ConnectivityManager conManager = (ConnectivityManager)context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取代表联网状态的NetWorkInfo对象
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        NetworkInfo wifiNetInfo = conManager
        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetInfo = conManager
        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (networkInfo == null || !networkInfo.isConnected())// 当前网络不可用
        {
         
            netStatus = false;
        } else
        {
            netStatus = networkInfo.isConnected();
            if (wifiNetInfo.isConnected())
            {
                wifiStatus = true;
                
            } else
            {
                
               cAPN  = networkInfo.getExtraInfo();
                
               cHOST = android.net.Proxy.getDefaultHost();
               cPORT = android.net.Proxy.getDefaultPort();
               
               mobiStatus = mobileNetInfo.isConnected();
               
               if (StringUtils.isEmpty(cHOST))
                   cHOST ="";
               
               if (StringUtils.isEmpty(cAPN))
               {
                   cAPN = "未启用";
               }
               else
               {
                   cAPN = String.format("%S,[%S]", cAPN,(mobiStatus ? "可用":"不可用"));
               }
               
            }

        }
        
        return String.format("您的手机网络设置信息如下:\n网络%s\nWIFI:%s\n接入点:%s\n代理服务器:%s\n端口:%s", 
                (netStatus ? "可用":"不可用"),(wifiStatus ? "有":"无"),cAPN,cHOST,cPORT);
        
    }

	public String getProxyHost()
	{
		return proxyHost;
	}

	public int getProxyPort()
	{
		return proxyPort;
	}
	
	private String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}
}

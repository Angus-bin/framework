package com.romaway.commons.utils;

import com.romaway.commons.lang.StringUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetUtils {
	/*
    * 检测网络是否可用
    * @return
    */
   public static boolean isNetworkConnected(Context context) {
       ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo ni = cm.getActiveNetworkInfo();
       return ni != null && ni.isConnectedOrConnecting();
   }

   /**
    * 带toast提示的网络是否连接
    * @param context
    * @return 返回网络是否连接
    */
   public static boolean isNetworkConnectedAndToast(Context context){
	   if(!isNetworkConnected(context)){
		   Toast.makeText(context, "请检查您的网络连接！", Toast.LENGTH_SHORT).show();
		   return false;
	   }
	   return true;
   }
   
  /**
   * 网络类型枚举类
   */
   enum NetType{
	   NETTYPE_WIFI, //WIFI网络
	   NETTYPE_CMWAP, //WAP网络
	   NETTYPE_CMNET //NET网络
   }
   
   /**
    * 获取当前网络类型
    * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
    */
   public static NetType getNetworkType(Context context) {
       NetType netType = NetType.NETTYPE_WIFI;
       ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
       if (networkInfo == null) {
           return netType;
       }        
       int nType = networkInfo.getType();
       if (nType == ConnectivityManager.TYPE_MOBILE) {
           String extraInfo = networkInfo.getExtraInfo();
           if(!StringUtils.isEmpty(extraInfo)){
               if (extraInfo.toLowerCase().equals("cmnet")) {
                   netType = NetType.NETTYPE_CMNET;
               } else {
                   netType = NetType.NETTYPE_CMWAP;
               }
           }
       } else if (nType == ConnectivityManager.TYPE_WIFI) {
           netType = NetType.NETTYPE_WIFI;
       }
       return netType;
   }
}

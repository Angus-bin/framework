package com.romaway.commons.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;

import com.romaway.commons.log.Logger;

public class SignatrueUtils {

	public static String getSignature(Context context) {
           try {
        	   PackageInfo packageInfo = 
        			   context.getPackageManager().getPackageInfo(
        					   context.getPackageName(), PackageManager.GET_SIGNATURES);
        	   Signature[] signatures = packageInfo.signatures;
        	   StringBuilder builder = new StringBuilder();
               for (Signature signature : signatures) {
                   builder.append(signature.toCharsString());
               }
               
               Logger.d("tag", MD5.getInstance().getMD5(builder.toString()));
               return MD5.getInstance().getMD5(builder.toString());
               
           } catch (NameNotFoundException e) {
               e.printStackTrace();
           }
           
           return null;
	 }
}

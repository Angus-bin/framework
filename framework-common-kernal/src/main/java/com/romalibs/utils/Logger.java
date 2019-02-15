package com.romalibs.utils;

public class Logger {

private static boolean DEBUG = true;
	private static String format(String level, String tag, String msg)
	{
		return String.format("[%s][%s]::%s", level, tag, msg);
	}
	
	public static void initDebug(boolean isDebug){
		DEBUG = isDebug;
	}
	
	public static boolean isDebug(){
		return DEBUG;   
	}
	
	public static void d(String TAG, String msg){
		if(DEBUG)
			System.out.println(format("DEBUG",TAG, msg)); 
	}
	
	public static void i(String TAG, String msg){
		System.out.println(format("INFO", TAG, msg));
	}
	
	public static void w(String TAG, String msg){
		System.out.println(format("WARN", TAG, msg));
	}
	
	public static void e(String TAG, String msg){
		System.out.println(format("ERROR",TAG, msg));
	}
}

package com.romaway.android.phone.cache;

import com.romaway.common.android.base.data.SharedPreferenceUtils;

public class CacheUtils
{
	private static final String pName = "PName_Minute_Cache";
	private static final String key = "Key_Server_Date";

	private static final String pName_historyDate = "PName_Minute_CachehistoryDate";
	private static final String key_historyDate = "Key_Server_DatehistoryDate";

	private static final String pName_historyTime = "PName_Minute_CachehistoryTime";
	private static final String key_historyTime = "Key_Server_DatehistoryTime";

	/** 存初始化时服务器下发日期 */
	public static final void saveServerDate(int date)
	{
		SharedPreferenceUtils.setPreference(pName, key, date);
	}

	/** 存历史数据服务器下发日期 */
	public static final void saveHistoryServerDate(int date)
	{
		SharedPreferenceUtils.setPreference(pName_historyDate, key_historyDate, date);
	}

	/** 存历史数据服务器下发时间 */
	public static final void saveHistoryServerTime(int date)
	{
		SharedPreferenceUtils.setPreference(pName_historyTime, key_historyTime, date);
	}

	/** 初始化时服务器下发日期 */
	public static final int getServerDate()
	{
		return SharedPreferenceUtils.getPreference(pName, key, 0);
	}

	/** 历史数据服务器下发日期 */
	public static final int getHistoryServerDate()
	{
		return SharedPreferenceUtils.getPreference(pName_historyDate, key_historyDate, 0);
	}

	/** 历史数据服务器下发时间 */
	public static final int getHistoryServerTime()
	{
		return SharedPreferenceUtils.getPreference(pName_historyTime, key_historyTime, 0);
	}

	/** 是否偶数 */
	public static boolean isEven(int v)
	{
		int b = v % 2;
		return b == 0;
	}

	/** 删除a中的最后一个元素 */
	public static int[] delArrayLastValue(int[] a)
	{
		int a_length = a.length;
		int[] f = new int[a_length - 1];
		System.arraycopy(a, 0, f, 0, a_length - 1);
		return f;
	}

	/** 将数组a，b拼接 */
	public static int[] splicingIntArray(int[] a, int[] b)
	{
		int a_length = a.length;
		int b_length = b.length;
		int[] f = new int[a_length + b_length];
		System.arraycopy(a, 0, f, 0, a_length);
		System.arraycopy(b, 0, f, a_length, b_length);
		return f;
	}
	
	public static int[] splicingIntArray(int[] a, int[] b,int pos)
	{
		int a_length = a.length;
		int b_length = b.length;
		
		if (pos>=0 && pos<a_length){
			a_length = pos;
		}
		int[] f = new int[a_length + b_length];
		System.arraycopy(a, 0, f, 0, a_length);
		System.arraycopy(b, 0, f, a_length, b_length);
		
		return f;
	}


	public static String[] splicingStringArray(String[] a, String[] b,int pos)
	{
		int a_length = a.length;
		int b_length = b.length;
		if (pos>=0 && pos<a_length){
			a_length = pos;
		}
		String[] f = new String[a_length + b_length];
		System.arraycopy(a, 0, f, 0, a_length);
		System.arraycopy(b, 0, f, a_length, b_length);
			
		return f;
	}

	public static byte[] splicingByteArray(byte[] a, byte[] b,int pos)
	{
		int a_length = a.length;
		int b_length = b.length;
		if (pos>=0 && pos<a_length){
			a_length = pos;
		}
		byte[] f = new byte[a_length + b_length];
		System.arraycopy(a, 0, f, 0, a_length);
		System.arraycopy(b, 0, f, a_length, b_length);
		
		return f;
	}
}

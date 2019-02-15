/**
 * 
 */
package com.romaway.commons.lang;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;

/**
 * @author duminghui
 * 
 */
public class StringUtils
{
	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the
	 * String. That functionality is available in isBlank().
	 * </p>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty(String str)
	{
		return str == null || str.length() == 0;
	}

	// Equals
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Compares two Strings, returning <code>true</code> if they are equal.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case sensitive.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.equals(null, null)   = true
	 * StringUtils.equals(null, "abc")  = false
	 * StringUtils.equals("abc", null)  = false
	 * StringUtils.equals("abc", "abc") = true
	 * StringUtils.equals("abc", "ABC") = false
	 * </pre>
	 * 
	 * @see java.lang.String#equals(Object)
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return <code>true</code> if the Strings are equal, case sensitive, or
	 *         both <code>null</code>
	 */
	public static boolean equals(String str1, String str2)
	{
		return str1 == null ? str2 == null : str1.equals(str2);
	}
  
  /**
	 * <p>
	 * Convert a <code>String</code> to an <code>int</code>, returning
	 * <code>zero</code> if the conversion fails.
	 * </p>
	 * 
	 * @param str
	 *            the string to convert
	 * @return the int represented by the string, or <code>zero</code> if
	 *         conversion fails
	 */
	public static int stringToInt(String str) {
		return stringToInt(str, 0);
	}

	/**
	 * <p>
	 * Convert a <code>String</code> to an <code>int</code>, returning a default
	 * value if the conversion fails.
	 * </p>
	 * 
	 * @param str
	 *            the string to convert
	 * @param defaultValue
	 *            the default value
	 * @return the int represented by the string, or the default if conversion
	 *         fails
	 */
	public static int stringToInt(String str, int defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}
	public static double stringToDouble(String str) {
		return stringToDouble(str, 0);
	}
	public static double stringToDouble(String str, int defaultValue) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}
	
	public static float stringToFloat(String str) {
		return stringToFloat(str, 0);
	}
	public static float stringToFloat(String str, int defaultValue) {
		try {
			return Float.parseFloat(str);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}
	
	/**
	 * 十六进制字符串转整形，用于处理颜色十六进制字符串
	 * @param oxStr
	 * @return
	 */
	public static int oxStringToInt(String oxStr){
        String nOxStr = oxStr;
        if(oxStr.contains("0x"))
            nOxStr = oxStr.replaceAll("0x", "");
        else if(oxStr.contains("0X"))
            nOxStr = oxStr.replaceAll("0X", "");
        else if(oxStr.contains("#"))
            nOxStr = oxStr.replaceAll("#", "");
        
       long longInt =  Long.valueOf(nOxStr, 16);
      
       return (int)longInt;
    }
	/**
	 * 将浮点数格式化成百分比
	 * 
	 * @param dValue
	 * @return
	 */
	public static String formatPrecent(double dValue)
	{
		java.text.NumberFormat percentFormat = java.text.NumberFormat
		        .getPercentInstance();
		percentFormat.setMaximumFractionDigits(2); // 最大小数位数
		percentFormat.setMinimumFractionDigits(2); // 最小小数位数
		// percentFormat.setMaximumIntegerDigits(2);// 最大整数位数
		// percentFormat.setMinimumIntegerDigits(2);// 最小整数位数
		return percentFormat.format(dValue);// 自动转换成百分比显示..
	}

	/**
	 * 提供精确的减法运算。
	 * @return
	 */
	public static String ssubString(String v1, String v2)
	{
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2).toString();
	}

	/**
	 * 提供精确的加法运算。
	 * @return
	 */
	public static String saddString(String v1, String v2)
	{
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).toString();
	}

	/**
	 * 提供精确的加法运算。
	 * @return
	 */
	public static double saddDouble(String v1)
	{// , String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		// BigDecimal b2 = new BigDecimal(v2);
		// return b1.add(b2).doubleValue();
		return b1.doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div(String v1, String v2)
	{
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2).doubleValue();
	}
	
	/**
	 * 提供（相对）精确的乘法法运算。
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return 两个参数的积
	 */
	public static double mul(String v1, String v2)
	{
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}
	/**
	 * 提供（相对）精确的乘法法运算。
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return 两个参数的积
	 */
	public static String multoString(String v1, String v2)
	{
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).toString();
	}
	/**
	 * 去掉后面的0
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		  if (str.indexOf(".") != -1 && str.charAt(str.length() - 1) == '0') {
			  return trim(str.substring(0, str.length() - 1));
		  } else {
			  return str.charAt(str.length() - 1) == '.' ? str.substring(0, str.length() - 1) : str;
		  }
	}
	
	
	public static int compare(String v1, String v2)
	{
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.compareTo(b2);
	}
	/**
	 * 换算单位
	 * @param v1
	 * @return
	 */
	public static String Monad(String v1)
	{
		int length = 0;
		String str = "0.00";
		String v2 = v1.substring(0, v1.indexOf("."));
		length = v2.length();
		
		if (length > 8) {
			str = new DecimalFormat("##0.00").format(StringUtils.stringToFloat(v2)/100000000) + "亿";
		} else if (length > 5) {
			str = new DecimalFormat("##0.00").format(StringUtils.stringToFloat(v2)/10000) + "万";
		}
		return str;
	}

	/**
	 * 数组是否相等
	 * 
	 * @param s1
	 * @param s2
	 * @return true相等 false不相等
	 */
	public static boolean isArrayEqual(String[] s1, String[] s2)
	{
		if (s1 == null && s2 == null)
			return true;
		else if ((s1 == null && s2 != null) || (s1 != null && s2 == null))
			return false;

		if (s1.length != s2.length)
			return false;
		else
			for (int i = 0; i < s1.length; i++)
			{
				if (!s1[i].equals(s2[i]))
					return false;
			}
		return true;
	}

	/**
	 * 校验Tag Alias 只能是数字,英文字母和中文
	 * @param s
	 * @return
	 */
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }
    
    /**
	 * 去除空格、回车、换行
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		  Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		  Matcher m = p.matcher(str);
		  String after = m.replaceAll("");
		  return after;
	}

	/**
	 * 获取String数据, 如果为null或空返回"";
	 * @param str
	 * @return
	 */
	public static String optString(String str) {
		if (TextUtils.isEmpty(str)){
			return "";
		}
		return str;
	}

	/**
	 * 将中文空格替换为英文空格:
	 * @param str
	 * @return
	 */
	public static String replaceCNBlankToEN(String str) {
		Pattern p = Pattern.compile("  ");
		Matcher m = p.matcher(str);
		String after = m.replaceAll(" ");
		return after;
	}

	/**
	 * 将字符文本中指定文本全部替换为目标内容
	 * @param source		操作数据源
	 * @param replaced		被替换文本
	 * @param target		替换内容
	 * @return
	 */
	public static String replaceString(String source, String replaced, String target) {
		Pattern p = Pattern.compile(replaced);
		Matcher m = p.matcher(source);
		String after = m.replaceAll(target);
		return after;
	}

	/**
	 * 判断是否为 JSON 格式
	 * @param response
	 * @return
     */
	public static boolean isJsonObject(String response) {
		try {
			BaseJSONObject jsonObject = new BaseJSONObject(response);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断是否为 JSON 格式
	 * @param response
	 * @return
	 */
	public static boolean isJsonArray(String response) {
		try {
			BaseJSONArray jsonArray = new BaseJSONArray(response);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}

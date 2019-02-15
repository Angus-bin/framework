/**
 * 
 */
package com.romaway.commons.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author duminghui
 * 
 */
public class DateUtils
{
	public static final SimpleDateFormat SDF_YYYY_MM_DD = new SimpleDateFormat(
	        "yyyy-MM-dd");
	private static final SimpleDateFormat SDF_HH_MM_SS_SSS = new SimpleDateFormat(
	        "HH:mm:ss:SSS");
	private static final SimpleDateFormat SDF_HH_MM_SS = new SimpleDateFormat(
	        "HH:mm:ss");
	private static final SimpleDateFormat SDF_MMDD = new SimpleDateFormat(
	        "MMdd");
	private static final SimpleDateFormat SDF_YYYYMM = new SimpleDateFormat(
	        "yyyyMM");
	private static final SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat(
	        "yyyyMMdd");
	private static final SimpleDateFormat SDF_YYYYMMDDHHMMSS = new SimpleDateFormat(
	        "yyyyMMddHHmmss");
	private static final SimpleDateFormat SDF_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat SDF_YYYY_MM_DD_HH_MM = new SimpleDateFormat(
			"yyyy-MM-dd  HH:mm");
	private static final SimpleDateFormat SDF_YYYY_MM_DD_HH_MM_new = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
  private static final SimpleDateFormat SDF_YYYYMMDDHHMM = new SimpleDateFormat(
            "yyyyMMddHHmm");
	/**
	 * 信息列表返回的时间格式
	 */
	private static final SimpleDateFormat SDF_YYYYMMDD_HH_MM_SS = new SimpleDateFormat(
	        "yyyyMMdd HH:mm:ss");
	private static final SimpleDateFormat SDF_YYYY_MM_DD_HH_MM_SS_SSS = new SimpleDateFormat(
	        "yyyy-MM-dd HH:mm:ss:SSS");
	private static final SimpleDateFormat SDF_HHMM = new SimpleDateFormat(
	        "HHmm");

	public static String format_HHMM(Date date)
	{
		return format(SDF_HHMM, date);
	}

	public static String format_YYYYMMDDHHMMSS(Date date)
	{
		return format(SDF_YYYYMMDDHHMMSS, date);
	}
	public static String format_YYYY_MM_DD_HH_MM(Date date)
	{
		return format(SDF_YYYY_MM_DD_HH_MM, date);
	}
	public static String format_YYYY_MM_DD_HH_MM_new(Date date)
	{
		return format(SDF_YYYY_MM_DD_HH_MM_new, date);
	}
	//YYYY_MM_DD HH：MM：SS
	public static String format_YYYY_MM_DD_HH_MM_SS(Date date)
	{
		return format(SDF_YYYY_MM_DD_HH_MM_SS, date);
	}
  
	public static String format_YYYYMMDDHHMM(Date date)
    {
        return format(SDF_YYYYMMDDHHMM, date);
    }

	public static String format_YYYYMMDD(Date date)
	{
		return format(SDF_YYYYMMDD, date);
	}

	public static String format_YYYYMM(Date date)
	{
		return format(SDF_YYYYMM, date);
	}

	public static String format_MMDD(Date date)
	{
		return format(SDF_MMDD, date);
	}

	public static String format_HH_MM_SS_SSS(Date date)
	{
		return format(SDF_HH_MM_SS_SSS, date);
	}

	public static String format_HH_MM_SS(Date date)
	{
		return format(SDF_HH_MM_SS, date);
	}

	public static String format_YYYY_MM_DD(Date date)
	{
		return format(SDF_YYYY_MM_DD, date);
	}

	/**
	 * 格式化日期显示
	 * @param date 原日期字符串，格式必须如20140524这样的8位数字
	 * @return
	 */
	public static String format_YYYY_MM_DD(String date)
    {
	    if (StringUtils.isEmpty(date)){
	        return "";
	    } 
	    if (date.length()!=8){
	        return date;
	    }
	    
//	    String tmpY = date.substring(0,4);
//	    String tmpM = date.substring(4,6);
//	    String tmpD = date.substring(6);
	    return String.format("%s-%s-%s", date.substring(0, 4),date.substring(4, 6),date.substring(6));
	   
    }
	
	/**
     * 格式化时间显示
     * @param date 原日期字符串，格式必须如100101这样的6位数字
     * @return
     */
    public static String format_HH_MM_SS(String date)
    {
        if (StringUtils.isEmpty(date)){
            return "";
        } 
        
        int len = date.length();
        if (len!=6 && len!=5){
            return date;
        }
        
//      String tmpY = date.substring(0,4);
//      String tmpM = date.substring(4,6);
//      String tmpD = date.substring(6);
        return String.format("%s:%s:%s", date.substring(0, len-4),date.substring(len-4, len-2),date.substring(len-2));
       
    }
	
	/**
     * 格式化日期显示
     * @param date 原日期字符串，格式必须如201405这样的6位数字
     * @return
     */
	public static String format_YYYY_MM(String date){
	    if (StringUtils.isEmpty(date)){
            return "";
        } 
        if (date.length()!=6){
            return date;
        }
        
//      String tmpY = date.substring(0,4);
//      String tmpM = date.substring(4,6);
//      String tmpD = date.substring(6);
        return String.format("%s年%s月", date.substring(0, 4),date.substring(4));
	}
	public static String format_YYYYMMDD_HH_MM_SS(Date date)
	{
		return format(SDF_YYYYMMDD_HH_MM_SS, date);
	}

	public static String fromat_YYYY_MM_DD_HH_MM_SS_SSS(Date date)
	{
		return format(SDF_YYYY_MM_DD_HH_MM_SS_SSS, date);
	}
	
	public static Date parse_YYYYMMDDHHMMSS(String source)
    {
        return parse(SDF_YYYYMMDDHHMMSS, source);
    }

	/**
	 * 格式化日期
	 * 
	 * @param sdf
	 * @param date
	 * @return
	 */
	public static String format(SimpleDateFormat sdf, Date date)
	{
		if (date == null)
		{
			return null;
		} else
		{
			return sdf.format(date);
		}
	}

	public static Date parse_YYYYMMDD_HH_MM_SS(String source)
	{
		return parse(SDF_YYYYMMDD_HH_MM_SS, source);
	}

	public static Date parse_YYYYMMDD(String source)
	{
		return parse(SDF_YYYYMMDD, source);
	}
	
	
//	public static Date pare_YYYY_MM_DD(String source){
//	    return parse(SDF_YYYY_MM_DD,source);
//	}

	public static Date parse_HHMM(String source)
	{
		return parse(SDF_HHMM, source);
	}

	public static Date parse_HHMMSS(String s)
	{
		return parse(SDF_HH_MM_SS, s);
	}
  
	

	/**
	 * 解析日期
	 * 
	 * @param sdf
	 * @param source
	 * @return
	 */
	public static Date parse(SimpleDateFormat sdf, String source)
	{
		if (StringUtils.isEmpty(source))
		{
			return null;
		} else
		{
			try
			{
				return sdf.parse(source);
			} catch (ParseException e)
			{
				return null;
			}
		}
	}


	/**
     * 返回当前时间
     * 
     * @return
     */
    public static long getTime() {
        Calendar c = Calendar.getInstance();
        return c.getTimeInMillis();
    }
    
    public static String getNextDay(){
        Calendar c = Calendar.getInstance();  
        Date date = new Date(); 
        
        c.setTime(date);  
        c.add(Calendar.HOUR, 24);
       
       
        return format_YYYY_MM_DD(c.getTime());
    }

    
    /**
     * 根据主机的默认 TimeZone，来获得指定形式的时间字符串。
     * 
     * @param dateFormat
     * @return 返回日期字符串，形式和formcat一致。
     */
    public static String getCurrentDateString(String dateFormat) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getDefault());

        return sdf.format(cal.getTime());
    }

}

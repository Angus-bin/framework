/**
 * 
 */
package com.romaway.commons.log;

/**
 * @author duminghui<br>
 * 2011.8.21 祝丰华，加入日志输出模式开关DEBUG, 若为false,则关闭日志输出（只允许输入ERROR级别的日志），true:则打开日志输出。
 * 2014.11.26 万籁唤 直接修改为使用类
 */
public class Logger
{
    /**
     * 判断是否是debug模式，若否，则不输入除ERROR日志之外的任何日志
     */
    protected static boolean DEBUG = false;
    
    private static String format(String level, String tag, String msg)
	{
		return String.format("[%s][%s]%s", level, tag, msg);
	}
    
    /**
     * 设置日志输出模式.建议在整个系统初始化中，根据配置文件来设置值
     * @param value true,打开日志输出，false:关闭日志输出（只输入ERROR级别的日志）
     */
    public static void setDebugMode(boolean value){
    	DEBUG = value;
    }

    /**
     * 获取调试模式表示
     * @return
     */
    public static boolean getDebugMode(){
    	return DEBUG;
    }
     
	public static void i(String tag, String msg)
	{
		if (DEBUG){
			System.out.println(format("INFO", tag, msg));
		}
	}

	public static void i(String tag, String msg, Throwable tr)
	{
		if (DEBUG){
			i(tag, msg);
			tr.printStackTrace(System.out);
		}
	}

	public static void d(String tag, String msg)
	{
		if (DEBUG){
			System.out.println(format("DEBUG", tag, msg));
		}
	}

	public static void d(String tag, String msg, Throwable tr)
	{
		if (DEBUG){
			d(tag, msg);
			tr.printStackTrace(System.out);
		}
	}

	public static void v(String tag, String msg)
	{
		if (DEBUG){
			System.out.println(format("VERBOS", tag, msg));
		}
	}

	public static void v(String tag, String msg, Throwable tr)
	{
		if (DEBUG){
			v(tag, msg);
			tr.printStackTrace(System.out);
		}
	}

	public static void e(String tag, String msg)
	{
		System.out.println(format("ERROR", tag, msg));
	}

	public static void e(String tag, String msg, Throwable tr)
	{
		e(tag, msg);
		tr.printStackTrace(System.err);
	}

	public static void w(String tag, String msg)
	{
		if (DEBUG){
			System.out.println(format("WARN", tag, msg));
		}
	}

	public static void w(String tag, String msg, Throwable tr)
	{
		w(tag, msg);
		tr.printStackTrace(System.out);
	}
}

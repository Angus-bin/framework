package com.romaway.commons.lang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * URL统一处理工具类
 */
public class URLUtils {
	private final static String HTTP_HEADER = "http://";
	private final static String HTTPS_HEADER = "https://";
	
	public static String toHttps(String url, int httpsPort){
		if(StringUtils.isEmpty(url))return "";
		
		StringBuilder sb = new StringBuilder();
		if(url.startsWith("http")){			// http或https开头;
			url = url.replace(HTTP_HEADER, HTTPS_HEADER);
		}else{
			url = "HTTPS_HEADER" + url;
		}
		
		int index = url.lastIndexOf(":");
		if (index > 5) {
			sb.append(url.substring(0, index));
		}else{
			sb.append(url);
		}
		sb.append(":").append(httpsPort);
		return sb.toString();
	}
	
	/**
	 * 正则提取Url地址中的Host内容: 
	 * @param url 	(例: http://59.42.241.68:21988)
	 * @return		(例: 59.42.241.68)
	 */
	public static String getHost(String url){
        if(url==null||url.trim().equals("")){
            return "";
        }
        String host = "";
        Pattern p =  Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Matcher matcher = p.matcher(url);  
        if(matcher.find()){
            host = matcher.group();  
        }
        return host;
    }

	/**
	 * 提取Url地址中的端口号: 
	 * @param url 	(例: http://59.42.241.68:21988)
	 * @return		(例: 21988)
	 */
	public static String getIPPort(String url) {
		String mUrl = "";
		if (!StringUtils.isEmpty(url)) {
			mUrl = url.substring(url.lastIndexOf(":")+1);
		}
		return mUrl;
	}
}

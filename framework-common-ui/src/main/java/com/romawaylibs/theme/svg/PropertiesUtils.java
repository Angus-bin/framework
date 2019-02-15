package com.romawaylibs.theme.svg;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Properties操作工具类;
 */
public class PropertiesUtils {

	private static Properties props = new Properties();
	
	/** 根据key读取value */
	public static String readValue(InputStream fileStream, String key) {
		try {
			props.load(fileStream);
			return props.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/** 根据keys读取values */
	public static String[] readValues(InputStream fileStream, String[] keys) {
		try {
			String[] values = new String[keys.length];
			props.load(fileStream);
			
			String key = null;
			for (int i = 0; i < keys.length; i++) {
				if((key = keys[i]) != null){
					values[i] = props.getProperty(key);
				}
			}
			return values;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 读取properties的全部信息
	public static Map<String, String> readProperties(InputStream fileStream) {
		Map<String, String> propertyValues = null;
		try {
			propertyValues = new HashMap<String, String>();
			props.load(fileStream);
			
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String value = props.getProperty(key);
				propertyValues.put(key, value);
			}
			return propertyValues;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 写入properties信息
	public static void writeProperties(String filePath, String parameterName, String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating " + parameterName + " value error");
		}
	}
}

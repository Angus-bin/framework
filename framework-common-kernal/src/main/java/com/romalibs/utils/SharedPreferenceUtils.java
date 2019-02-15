/**
 * 
 */
package com.romalibs.utils;

import java.util.ArrayList;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.romaway.common.android.base.OriginalContext;

/**
 * @author duminghui
 * 
 */
public class SharedPreferenceUtils
{
	public static final String DATA_USER = "DATA_USER";
	public static final String DATA_CONFIG = "kds_configs";	

	@SuppressWarnings("unchecked")
	public static <T> T getPreference(String pName, String key, T defaultValue)
	{
		SharedPreferences sp = OriginalContext.getContext()
		        .getSharedPreferences(pName, 0);
		if (sp.contains(key))
		{
			if (defaultValue instanceof String)
			{
				return (T) sp.getString(key, (String) defaultValue);
			} else if (defaultValue instanceof Long)
			{
				return (T) Long.valueOf(sp.getLong(key, (Long) defaultValue));
			} else if (defaultValue instanceof Integer)
			{
				return (T) Integer.valueOf(sp.getInt(key,
				        (Integer) defaultValue));
			} else if (defaultValue instanceof Float)
			{
				return (T) Float
				        .valueOf(sp.getFloat(key, (Float) defaultValue));
			} else if (defaultValue instanceof Boolean)
			{
				return (T) Boolean.valueOf(sp.getBoolean(key,
				        (Boolean) defaultValue));
			} else
			{
				return defaultValue;
			}
		} else
		{
			return defaultValue;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getPreference(String pName, String key, Class<T> cls)
	{
		if (cls.equals(String.class))
		{
			return (T) getPreference(pName, key, "");
		} else if (cls.equals(Long.class))
		{
			return (T) getPreference(pName, key, 0L);
		} else if (cls.equals(Integer.class))
		{
			return (T) getPreference(pName, key, 0);
		} else if (cls.equals(Float.class))
		{
			return (T) getPreference(pName, key, 0.0f);
		} else if (cls.equals(Boolean.class))
		{
			return (T) getPreference(pName, key, false);
		}
		return null;
	}

	public static <T> void setPreference(String pName, String key, T value)
	{
		SharedPreferences sp = OriginalContext.getContext()
		        .getSharedPreferences(pName, 0);
		Editor editor = sp.edit();
		if (value instanceof Boolean)
		{
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Integer)
		{
			editor.putInt(key, (Integer) value);
		} else if (value instanceof String)
		{
			editor.putString(key, (String) value);
		} else if (value instanceof Long)
		{
			editor.putLong(key, (Long) value);
		} else if (value instanceof Float)
		{
			editor.putFloat(key, (Float) value);
		}
		editor.commit();
	}

	public static void removePreference(String pName, String key)
	{
		SharedPreferences sp = OriginalContext.getContext()
		        .getSharedPreferences(pName, 0);
		Editor editor = sp.edit();
		editor.remove(key);
		editor.commit();
	}
	
	public static Set<String> getAll(String pName){
		SharedPreferences sp = OriginalContext.getContext()
		        .getSharedPreferences(pName, 0);
		return sp.getAll().keySet();
	}
	
	@SuppressLint("NewApi")
	public static Set<String> getPreference(String pName, String key)
	{
		SharedPreferences sp = OriginalContext.getContext()
		        .getSharedPreferences(pName, 0);	
		return sp.getStringSet(key, null);
	}
	public static void clearDates(String pName)
	{
		SharedPreferences sp = OriginalContext.getContext()
				.getSharedPreferences(pName, 0);	
		sp.edit().clear().commit();
	}
	
	public static void removedates(String pName,ArrayList<String> removeTimes)
	{
		SharedPreferences sp = OriginalContext.getContext()
				.getSharedPreferences(pName, 0);
		Editor editor = sp.edit();
		for(int i=0;i<removeTimes.size();i++){
			editor.remove(removeTimes.get(i));
		}
		editor.commit();
	}
	
	@SuppressLint("NewApi")
	public static void setPreference(String pName, String key, Set<String> set)
	{
		SharedPreferences sp = OriginalContext.getContext()
		        .getSharedPreferences(pName, 0);
		Editor editor = sp.edit();
		editor.putStringSet(key, set);
		editor.commit();
	}
}

/**
 * 
 */
package com.romaway.commons.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.commons.lang.StringUtils;


/**
 *JSON帮助类 
 *@author zhuft
 *@version 1.0
 *2013-10-12
 */
public class JSONUtils {
	/**
     * get Long from jsonObject
     * 
     * @param jsonObject
     * @param key
     * @param defaultValue
     */
    public static Long getLong(JSONObject jsonObject, String key, Long defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }
        try {
            return jsonObject.getLong(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * get Long from jsonData
     * 
     * @param jsonData
     * @param key
     * @param defaultValue
     */
    public static Long getLong(String jsonData, String key, Long defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getLong(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getLong(JSONObject, String, Long)
     */
    public static long getLong(JSONObject jsonObject, String key, long defaultValue) {
        return getLong(jsonObject, key, (Long)defaultValue);
    }

    /**
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getLong(String, String, Long)
     */
    public static long getLong(String jsonData, String key, long defaultValue) {
        return getLong(jsonData, key, (Long)defaultValue);
    }

    /**
     * get Int from jsonObject
     * 
     * @param jsonObject
     * @param key
     * @param defaultValue
     */
    public static Integer getInt(JSONObject jsonObject, String key, Integer defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * get Int from jsonData
     * 
     * @param jsonData
     * @param key
     * @param defaultValue
     */
    public static Integer getInt(String jsonData, String key, Integer defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getInt(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getInt(JSONObject, String, Integer)
     */
    public static int getInt(JSONObject jsonObject, String key, int defaultValue) {
        return getInt(jsonObject, key, (Integer)defaultValue);
    }

    /**
     * @param jsonData
     * @param key
     * @param defaultValue
     * @see JSONUtils#getInt(String, String, Integer)
     */
    public static int getInt(String jsonData, String key, int defaultValue) {
        return getInt(jsonData, key, (Integer)defaultValue);
    }

    /**
     * get Double from jsonObject
     * 
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if key is null or empty, return defaultValue</li>
     * <li>if {@link JSONObject#getDouble(String)} exception, return defaultValue</li>
     * <li>return {@link JSONObject#getDouble(String)}</li>
     * </ul>
     */
    public static Double getDouble(JSONObject jsonObject, String key, Double defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getDouble(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * get Double from jsonData
     * 
     * @param jsonData
     * @param key
     * @param defaultValue
     * if jsonObject is null, return defaultValue
     * if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue
     */
    public static Double getDouble(String jsonData, String key, Double defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getDouble(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getDouble(JSONObject, String, Double)
     */
    public static double getDouble(JSONObject jsonObject, String key, double defaultValue) {
        return getDouble(jsonObject, key, (Double)defaultValue);
    }

    /**
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getDouble(String, String, Double)
     */
    public static double getDouble(String jsonData, String key, double defaultValue) {
        return getDouble(jsonData, key, (Double)defaultValue);
    }

    /**
     * get String from jsonObject
     * 
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if key is null or empty, return defaultValue</li>
     * <li>if {@link JSONObject#getString(String)} exception, return defaultValue</li>
     * <li>return {@link JSONObject#getString(String)}</li>
     * </ul>
     */
    public static String getString(JSONObject jsonObject, String key, String defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * get String from jsonData
     * 
     * @param jsonData
     * @param key
     * @param defaultValue
     */
    public static String getString(String jsonData, String key, String defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getString(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * get String array from jsonObject
     * 
     * @param jsonObject
     * @param key
     * @param defaultValue
     */
    public static String[] getStringArray(JSONObject jsonObject, String key, String[] defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            JSONArray statusArray = jsonObject.getJSONArray(key);
            if (statusArray != null) {
                String[] value = new String[statusArray.length()];
                for (int i = 0; i < statusArray.length(); i++) {
                    value[i] = statusArray.getString(i);
                }
                return value;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
        return defaultValue;
    }

    /**
     * get String array from jsonData
     * 
     * @param jsonData
     * @param key
     * @param defaultValue
     * if jsonObject is null, return defaultValue
     * if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue
     */
    public static String[] getStringArray(String jsonData, String key, String[] defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getStringArray(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * get JSONObject from jsonObject
     * 
     * @param jsonObject<em><em></em></em>
     * @param key
     * @param defaultValue
     */
    public static JSONObject getJSONObject(JSONObject jsonObject, String key, JSONObject defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * get JSONObject from jsonData
     * 
     * @param jsonData
     * @param key
     * @param defaultValue
     */
    public static JSONObject getJSONObject(String jsonData, String key, JSONObject defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONObject(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * get JSONArray from jsonObject
     * 
     * @param jsonObject
     * @param key
     * @param defaultValue
     */
    public static JSONArray getJSONArray(JSONObject jsonObject, String key, JSONArray defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getJSONArray(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * get JSONArray from jsonData
     * 
     * @param jsonData
     * @param key
     * @param defaultValue
     * if jsonObject is null, return defaultValue
     * if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue
     */
    public static JSONArray getJSONArray(String jsonData, String key, JSONArray defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONArray(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * get Boolean from jsonObject
     * 
     * @param jsonObject
     * @param key
     * @param defaultValue
     */
    public static boolean getBoolean(JSONObject jsonObject, String key, Boolean defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getBoolean(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * get Boolean from jsonData
     * 
     * @param jsonData
     * @param key
     * @param defaultValue
     */
    public static boolean getBoolean(String jsonData, String key, Boolean defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getBoolean(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 将json格式的字符串解析成Map对象
     * json格式：{"name":"admin","retries":"3fff","testname"
     * :"ddd","testretries":"fffffffff"}
     */
    public static HashMap<String, String> toHashMap(Object object)
    {
        HashMap<String, String> data = new HashMap<String, String>();
        // 将json字符串转换成jsonObject
        JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(StringUtils.replaceBlank(object.toString()));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(jsonObject == null)
			return data;
		
        Iterator it = jsonObject.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext())
        {
            String key = String.valueOf(it.next());
            String value = null;
			try {
				value = (String) jsonObject.get(key);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(value != null)
				data.put(key, value);
        }
        return data;
    }
    
    /**
     * map转化成json数据结构
     * @param arrayKey json组的key
     * @param map
     * @return
     */
    public static String toJson(String arrayKey, Map<String, String> map){
    	StringBuilder jsonBuilder = new StringBuilder(); 
	    String header = "{\"" + arrayKey+"\":[{";
	    String end = "}]}";
	    
	    jsonBuilder.append(header);
	    int count = 0;
	    for (String key : map.keySet()) {
	    	count ++;
	    	String value = map.get(key);

	    	jsonBuilder.append("\""+key+"\"");
	    	jsonBuilder.append(":");
	    	jsonBuilder.append("\""+value+"\"");
	    	if(count < map.size())
	    		jsonBuilder.append(",");
	        System.out.println("Key = " + key + ", Value = " + value);

	    }
	    jsonBuilder.append(end);
	    
	    return jsonBuilder.toString();
    }
}

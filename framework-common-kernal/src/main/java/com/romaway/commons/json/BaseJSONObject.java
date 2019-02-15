/**
 * 
 */
package com.romaway.commons.json;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.commons.log.Logger;

/**
 * 继承 ,主要是为了不在代码里面写N多try catch
 * 
 * @author zhuft
 * @version 1.0 2012-1-4
 */
public class BaseJSONObject extends JSONObject {

    public BaseJSONObject() {
        super();
    }

    /**
     * @param request
     * @throws JSONException
     */
    public BaseJSONObject(String request) throws JSONException {
        super(request);
    }

    @Override
    public JSONObject accumulate(String key, Object value) {
        try {
            return super.accumulate(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object get(String key) {
        try {
            return super.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean getBoolean(String key) {
        // TODO Auto-generated method stub
        try {
            return super.getBoolean(key);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public double getDouble(String key) {
        // TODO Auto-generated method stub
        try {
            return super.getDouble(key);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getInt(String key) {
        // TODO Auto-generated method stub
        try {
            return super.getInt(key);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public BaseJSONArray getJSONArray(String key) {
        // TODO Auto-generated method stub
        try {
            return new BaseJSONArray(super.get(key).toString());
            // return super.getJSONArray(get);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject getJSONObject(String key) {
        // TODO Auto-generated method stub
        try {
            return super.getJSONObject(key);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getLong(String key) {
        // TODO Auto-generated method stub
        try {
            return super.getLong(key);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getString(String key) {
        // TODO Auto-generated method stub
        try {
            return super.getString(key);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Logger.w("System.err",String.format("json解析key错误:key=%s", key));
            //e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean has(String key) {
        // TODO Auto-generated method stub
        return super.has(key);
    }

    @Override
    public boolean isNull(String key) {
        // TODO Auto-generated method stub
        return super.isNull(key);
    }

//    @Override
//    public Iterator<?> keys() {
//        // TODO Auto-generated method stub
//        return super.keys();
//    }

    @Override
    public Iterator<String> keys() {
        return super.keys();
    }

    @Override
    public int length() {
        // TODO Auto-generated method stub
        return super.length();
    }

    @Override
    public JSONArray names() {
        // TODO Auto-generated method stub
        return super.names();
    }

    @Override
    public Object opt(String key) {
        // TODO Auto-generated method stub
        return super.opt(key);
    }

    @Override
    public boolean optBoolean(String key) {
        // TODO Auto-generated method stub
        return super.optBoolean(key);
    }

    @Override
    public boolean optBoolean(String key, boolean defaultValue) {
        // TODO Auto-generated method stub
        return super.optBoolean(key, defaultValue);
    }

    @Override
    public double optDouble(String key) {
        // TODO Auto-generated method stub
        return super.optDouble(key);
    }

    @Override
    public double optDouble(String key, double defaultValue) {
        // TODO Auto-generated method stub
        return super.optDouble(key, defaultValue);
    }

    @Override
    public int optInt(String key) {
        // TODO Auto-generated method stub
        return super.optInt(key);
    }

    @Override
    public int optInt(String key, int defaultValue) {
        // TODO Auto-generated method stub
        return super.optInt(key, defaultValue);
    }

    @Override
    public JSONArray optJSONArray(String key) {
        // TODO Auto-generated method stub
        return super.optJSONArray(key);
    }

    public BaseJSONArray optBaseJSONArray(String key) {
        JSONArray temp = super.optJSONArray(key);
        try {
            return new BaseJSONArray(temp.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return new BaseJSONArray();
    }

    @Override
    public JSONObject optJSONObject(String key) {
        // TODO Auto-generated method stub
        return super.optJSONObject(key);
    }

    public BaseJSONObject optBaseJSONObject(String key) {
        JSONObject temp = super.optJSONObject(key);
        try {
            return new BaseJSONObject(temp.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new BaseJSONObject();
    }

    @Override
    public long optLong(String key) {
        // TODO Auto-generated method stub
        return super.optLong(key);
    }

    @Override
    public long optLong(String key, long defaultValue) {
        // TODO Auto-generated method stub
        return super.optLong(key, defaultValue);
    }

    @Override
    public String optString(String key) {
        // TODO Auto-generated method stub
        return super.optString(key);
    }

    @Override
    public String optString(String key, String defaultValue) {
        // TODO Auto-generated method stub
        return super.optString(key, defaultValue);
    }

    @Override
    public JSONObject put(String key, boolean value) {
        // TODO Auto-generated method stub
        try {
            return super.put(key, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject put(String key, double value) {
        // TODO Auto-generated method stub
        try {
            return super.put(key, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject put(String key, int value) {
        // TODO Auto-generated method stub
        try {
            return super.put(key, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject put(String key, long value) {
        // TODO Auto-generated method stub
        try {
            return super.put(key, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject put(String key, Object value) {
        // TODO Auto-generated method stub
        try {
            return super.put(key, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject putOpt(String key, Object value) {
        // TODO Auto-generated method stub
        try {
            return super.putOpt(key, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object remove(String key) {
        // TODO Auto-generated method stub
        return super.remove(key);
    }

    @Override
    public JSONArray toJSONArray(JSONArray names) {
        // TODO Auto-generated method stub
        try {
            return super.toJSONArray(names);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return names;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

    @Override
    public String toString(int indentFactor) {
        // TODO Auto-generated method stub
        try {
            return super.toString(indentFactor);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}

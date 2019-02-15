/**
 * 
 */
package com.romaway.commons.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * comments
 * 
 * @author zhuft
 * @version 1.0 2012-1-4
 */
public class BaseJSONArray extends JSONArray {

    public BaseJSONArray() {
        super();
    }

    /**
     * @param postRequest
     * @throws JSONException
     */
    public BaseJSONArray(String postRequest) throws JSONException {
        super(postRequest);
    }

    @Override
    public boolean equals(Object object) {
        // TODO Auto-generated method stub
        return super.equals(object);
    }

    @Override
    public Object get(int index) {
        // TODO Auto-generated method stub
        try {
            return super.get(index);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public boolean getBoolean(int index) {
        // TODO Auto-generated method stub
        try {
            return super.getBoolean(index);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public double getDouble(int index) {
        // TODO Auto-generated method stub
        try {
            return super.getDouble(index);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int getInt(int index) {
        // TODO Auto-generated method stub
        try {
            return super.getInt(index);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public BaseJSONArray getJSONArray(int index) {
        // TODO Auto-generated method stub
        try {
            BaseJSONArray jsonArray = new BaseJSONArray(super.getJSONArray(index).toString());
            return jsonArray;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BaseJSONObject getJSONObject(int index) {
        // TODO Auto-generated method stub
        try {
            BaseJSONObject basejson = new BaseJSONObject(super.getJSONObject(index).toString());
            return basejson;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getLong(int index) {
        // TODO Auto-generated method stub
        try {
            return super.getLong(index);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public String getString(int index) {
        // TODO Auto-generated method stub
        try {
            return super.getString(index);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isNull(int index) {
        // TODO Auto-generated method stub
        return super.isNull(index);
    }

    @Override
    public String join(String separator) {
        // TODO Auto-generated method stub
        try {
            return super.join(separator);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return separator;
    }

    @Override
    public int length() {
        // TODO Auto-generated method stub
        return super.length();
    }

    @Override
    public Object opt(int index) {
        // TODO Auto-generated method stub
        return super.opt(index);
    }

    @Override
    public boolean optBoolean(int index) {
        // TODO Auto-generated method stub
        return super.optBoolean(index);
    }

    @Override
    public boolean optBoolean(int index, boolean defaultValue) {
        // TODO Auto-generated method stub
        return super.optBoolean(index, defaultValue);
    }

    @Override
    public double optDouble(int index) {
        // TODO Auto-generated method stub
        return super.optDouble(index);
    }

    @Override
    public double optDouble(int index, double defaultValue) {
        // TODO Auto-generated method stub
        return super.optDouble(index, defaultValue);
    }

    @Override
    public int optInt(int index) {
        // TODO Auto-generated method stub
        return super.optInt(index);
    }

    @Override
    public int optInt(int index, int defaultValue) {
        // TODO Auto-generated method stub
        return super.optInt(index, defaultValue);
    }

    @Override
    public JSONArray optJSONArray(int index) {
        // TODO Auto-generated method stub
        return super.optJSONArray(index);
    }

    @Override
    public JSONObject optJSONObject(int index) {
        // TODO Auto-generated method stub
        return super.optJSONObject(index);
    }

    @Override
    public long optLong(int index) {
        // TODO Auto-generated method stub
        return super.optLong(index);
    }

    @Override
    public long optLong(int index, long defaultValue) {
        // TODO Auto-generated method stub
        return super.optLong(index, defaultValue);
    }

    @Override
    public String optString(int index) {
        // TODO Auto-generated method stub
        return super.optString(index);
    }

    @Override
    public String optString(int index, String defaultValue) {
        // TODO Auto-generated method stub
        return super.optString(index, defaultValue);
    }

    @Override
    public JSONArray put(boolean value) {
        // TODO Auto-generated method stub
        return super.put(value);
    }

    @Override
    public JSONArray put(double value) {
        // TODO Auto-generated method stub
        try {
            return super.put(value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONArray put(int value) {
        // TODO Auto-generated method stub
        return super.put(value);
    }

    @Override
    public JSONArray put(long value) {
        // TODO Auto-generated method stub
        return super.put(value);
    }

    @Override
    public JSONArray put(Object value) {
        // TODO Auto-generated method stub
        return super.put(value);
    }

    @Override
    public JSONArray put(int index, boolean value) {
        // TODO Auto-generated method stub
        try {
            return super.put(index, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONArray put(int index, double value) {
        // TODO Auto-generated method stub
        try {
            return super.put(index, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONArray put(int index, int value) {
        // TODO Auto-generated method stub
        try {
            return super.put(index, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONArray put(int index, long value) {
        // TODO Auto-generated method stub
        try {
            return super.put(index, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONArray put(int index, Object value) {
        // TODO Auto-generated method stub
        try {
            return super.put(index, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject toJSONObject(JSONArray names) {
        // TODO Auto-generated method stub
        try {
            return super.toJSONObject(names);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
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

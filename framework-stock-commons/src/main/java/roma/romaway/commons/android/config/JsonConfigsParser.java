package roma.romaway.commons.android.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 配制文件解析器主要是解析json格式配制
 * @author 万籁唤
 * @version v1.1 添加json解析对象缓存，因为每一次new一个JSONObject对象比较耗时  2015-11-14
 */
public class JsonConfigsParser {
	
	/***/
	private static Map<String, JSONObject> cacheJSONObjectMap = new HashMap<String, JSONObject>();
	
    /**
     * 解析json数据格式的配制方法
     * @param jsonConfigStr
     * @param panel
     * @param key
     * @return 每一个map就是一个控件对象，List组为一个面板的配制信息
     */
    public static List<Map<String, String>> getJsonConfigInfo(String jsonConfigStr, String panel, String[] key){
        
        //获取配制文件版本时间
        //String version = getConfigVersion(jsonConfigStr);
        //获取下载SVG格式图片的网络地址
        String downloadUrl = getSvgDownloadUrl(jsonConfigStr); 
        List<Map<String, String>> list = null;
        try { 
            list = new ArrayList<Map<String, String>>();
            
            //缓存机制，不会每次都会去new 因为比较耗时间
            JSONObject jsonObject = null;
            if(!cacheJSONObjectMap.containsKey(jsonConfigStr)){
            	jsonObject = new JSONObject(jsonConfigStr);
            	cacheJSONObjectMap.put(jsonConfigStr, jsonObject);
            }else
            	jsonObject = cacheJSONObjectMap.get(jsonConfigStr);
            
            if(jsonObject == null)
            	return null;
            
            JSONArray jsonObjs = jsonObject.optJSONArray(panel);

            if(jsonObjs != null && jsonObjs.length() > 0) {
                for (int i = 0; i < jsonObjs.length(); i++) {
                    JSONObject jsonObj = jsonObjs.getJSONObject(i);
                    Map<String, String> map = new HashMap<String, String>();
                    String value = null;

                    for (int k = 0; k < key.length; k++) {
                        try {
                            if (jsonObj.has(key[k])) {
                                value = jsonObj.getString(key[k]);
                                map.put(key[k], value);
                            } else {
                                map.put(key[k], "");
                            }
                        } catch (Exception e) {
                            System.out.println("Jsons parse error !");
                            e.printStackTrace();
                            value = null;
                            break;
                        }
                    }
                    // map.put("version", version);//版本时间
                    map.put("downloadUrl", downloadUrl);//下载地址
                    if (value != null)
                        list.add(map);
                }
            }
        } catch (JSONException e) {
            System.out.println("Jsons parse error !"); 
            //e.printStackTrace(); 
        }
            return list;
    }
    
    /**
     * 解析最简单的json格式数据
     * @param jsonConfig
     * @param key
     * @return
     */
    public static String getSimpleJson(String jsonConfig, String key){
        String jsonObjsVersion = null;
        try { 
        	JSONObject mJSONObject = new JSONObject(jsonConfig);
                jsonObjsVersion = mJSONObject.getString(key);
        } catch (JSONException e) { 
            System.out.println("Jsons parse error !"); 
            //e.printStackTrace(); 
        }
        
        return jsonObjsVersion;
    }
    
    /**
     * 获取json 配制文件的版本号
     * @param jsonConfig
     * @return
     */
    public static String getConfigVersion(String jsonConfig){
        String jsonObjsVersion = null;
        try { 
        	//缓存机制，不会每次都会去new 因为比较耗时间
            JSONObject jsonObject = null;
            if(!cacheJSONObjectMap.containsKey(jsonConfig)){
            	jsonObject = new JSONObject(jsonConfig);
            	cacheJSONObjectMap.put(jsonConfig, jsonObject);
            }else
            	jsonObject = cacheJSONObjectMap.get(jsonConfig);
            
            if(jsonObject == null)
            	return null;
            
             jsonObjsVersion = jsonObject.getString("version");
        } catch (JSONException e) { 
            System.out.println("Jsons parse error !"); 
            e.printStackTrace(); 
        }
        
        return jsonObjsVersion;
    }
    
    /**
     * 获取json 配制文件SVG格式图片的下载地址
     * @param jsonConfig
     * @return
     */
    public static String getSvgDownloadUrl(String jsonConfig){
        String jsonObjsUrl = null;
        try { 
        	//缓存机制，不会每次都会去new 因为比较耗时间
            JSONObject jsonObject = null;
            if(!cacheJSONObjectMap.containsKey(jsonConfig)){
            	jsonObject = new JSONObject(jsonConfig);
            	cacheJSONObjectMap.put(jsonConfig, jsonObject);
            }else
            	jsonObject = cacheJSONObjectMap.get(jsonConfig);
            
            if(jsonObject == null)
            	return null;
            
        	jsonObjsUrl = jsonObject.getString("downloadUrl");
        } catch (JSONException e) { 
            System.out.println("Jsons parse error !"); 
            //e.printStackTrace(); 
        }
        
        return jsonObjsUrl;
    }
}

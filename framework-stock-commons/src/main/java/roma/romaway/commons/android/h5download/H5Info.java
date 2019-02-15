package roma.romaway.commons.android.h5download;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.romaway.android.phone.config.Configs;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.commons.android.fileutil.FileSystem;

/**
 * 
 * @Description:获取H5模块信息
 * @author lichuan
 * @date 2015年8月13日    上午11:02:51 
 * @version
 */
public class H5Info {
	
	/*public static H5Info singletion = new H5Info();

	private H5Info() {
	}

	public static H5Info getInstance() {
		return singletion;
	}*/
	/**
	 * 
	 * @date   2015年8月13日    上午11:41:28 
	 * @version    
	 * @Description: 取得正在使用中的H5模块
	 * @author lichuan
	 * @param @return    
	 * @return String
	 */
	public static String getCurrVersion(Context context){
		
		 String version = "";
		 //如果是引用内置h5地址，就取内置h5的版本号
		if(Configs.jiaoyiURL.contains("file:///android_asset"))
		{
			version = getAssetsCurrVersion(context);
		}
		else
		{
			//取下发更新的h5版本号
			version =  SharedPreferenceUtils.getPreference(SharedPreferenceUtils.DATA_CONFIG,"JIAO_YI_UPDATE_VERSION", "");
                    
		}
		
		return version;
	}
	
	/**
	 * 
	 * @date   2015年8月13日    下午1:11:25 
	 * @version    
	 * @Description:内置H5模块的版本号
	 * @author lichuan
	 * @param @return    
	 * @return String
	 */
   public static String getAssetsCurrVersion(Context context){
		
	   String version = "";
	   String json = FileSystem.getFromAssets(context, "kds519/config.txt");
       
	   if(json != null)
	   {
		  
		   try {
			
			JSONObject  object = new JSONObject(json);
			version = object.optString("version");
			
			} catch (JSONException e) {
				e.printStackTrace();
			}
		  
	   }
	   
		return version;
	}
	
}

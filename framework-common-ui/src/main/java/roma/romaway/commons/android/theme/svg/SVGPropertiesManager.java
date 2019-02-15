package roma.romaway.commons.android.theme.svg;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * SVG Properties管理类
 * 根据(当前)皮肤类型读取对应Properties配置文件, 获取指定SVG图参数的颜色值;
 * @deprecated
 */
public class SVGPropertiesManager {
	
	private static HashMap<String, Map<String, String>> mPropertiesMaps = new HashMap<String, Map<String, String>>();
	
	/**
	 * 指定皮肤配置文件中, 读取指定SVG图的颜色样式;
	 * @param skinType			指定皮肤类型;
	 * @param svgFileName		SVG图文件名;
	 * @param svgFieldNames	SVG图可/需换色字段列表;
	 * @return	SVG图可/需换色字段及对应值的映射
	 */
	public static Map<String, String> getProperties(String skinType, String svgFileName, String[] svgFieldNames) {
		String[] propertyKey = new String[svgFieldNames.length];
		for (int i = 0; i < svgFieldNames.length; i++) {
			propertyKey[i]  = svgFileName+"."+svgFieldNames[i];
		}
		return getProperties(skinType, svgFieldNames, propertyKey);
	}
	
	/**
	 * 指定皮肤配置文件中, 读取指定SVG图的颜色样式;
	 * @param skinType			指定皮肤类型;
	 * @param svgFieldNames
	 * @param propertyKey		皮肤配置properties文件中换色样式字段键名;
	 * @return	SVG图可/需换色字段及对应值的映射;
	 */
	public static Map<String, String> getProperties(String skinType, String[] svgFieldNames, String[] propertyKey){
		Map<String, String> respPropertys = new HashMap<String, String>();
		
		Map<String, String> cachePropertys = mPropertiesMaps.get(skinType);
		if(cachePropertys == null) cachePropertys= new HashMap<String, String>();
		
		InputStream fileStream = null;
		try{
			String value = null;
			for (int i = 0; i < propertyKey.length; i++) {
				value = cachePropertys.get(propertyKey[i]);
				
				if(value != null){
					respPropertys.put(svgFieldNames[i], value);
				}else{
					if(fileStream == null)
						fileStream = SVGManager.getConfigStreamAsSkinType();
					
					value = PropertiesUtils.readValue(fileStream, propertyKey[i]);
					respPropertys.put(svgFieldNames[i], value);
					cachePropertys.put(propertyKey[i], value);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(fileStream != null)
					fileStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		return respPropertys;
	}

	void setProperty(String address, String name, String value) {
		synchronized (mPropertiesMaps) {
			Map<String, String> propVal = mPropertiesMaps.get(address);
			if (propVal != null) {
				propVal.put(name, value);
				mPropertiesMaps.put(address, propVal);
			} else {
				// Log.e(TAG, "setRemoteDeviceProperty for a device not in cache:" + address);
			}
		}
	}

	boolean isInCache(String skinType) {
		synchronized (mPropertiesMaps) {
			return (mPropertiesMaps.get(skinType) != null);
		}
	}

	boolean isEmpty() {
		synchronized (mPropertiesMaps) {
			return mPropertiesMaps.isEmpty();
		}
	}

	Set<String> keySet() {
		synchronized (mPropertiesMaps) {
			return mPropertiesMaps.keySet();
		}
	}
}

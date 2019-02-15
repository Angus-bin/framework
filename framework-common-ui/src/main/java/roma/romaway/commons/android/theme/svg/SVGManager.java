package roma.romaway.commons.android.theme.svg;

import android.content.Context;
import android.util.Log;

import com.romaway.commons.android.fileutil.FileSystem;
import com.romaway.commons.lang.StringUtils;
import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SvgRes1;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import roma.romaway.commons.android.theme.SkinManager;

/**
 * SVG Manager:
 * 1、读取SVG图的存储路径: assent存储位置、SD卡存储位置、系统存储位置;
 * 2、读取对应皮肤配置的换色参数;
 * @deprecated
 */
public class SVGManager {

    /** 换肤/皮肤使用SVG ICON文件存储相对路径 */
	public final static String SVG_ICON_NAME = "skinIconFolder";
    private final static String SVG_ICON_PATH = "themeFolder/" + SVG_ICON_NAME + "/";
    
    /**
     * 获取存储下来的SVG文件字符串/内容
     * @param context
     * @param svgFileName   SVG文件名;
     * @return
     */
    public static String getSVGParseContentAsStoragePath(Context context, String svgFileName){
    	String config = "";
    	if(SkinManager.iconLoadPathType == SkinManager.SAVE_TYPE_ASSETS)//本地存储位置
    		config = FileSystem.getFromAssets(context, SVG_ICON_PATH + svgFileName +".svg");
    	
    	else if(SkinManager.iconLoadPathType == SkinManager.SAVE_TYPE_SDCARD)//SDK存储位置
    		config = ""; //"file://"+FileSystem.getCacheRootDir(context, "").getPath()+ url;
    	
    	else if(SkinManager.iconLoadPathType == SkinManager.SAVE_TYPE_SYSTEM_DATA_FOLDER)//内部文件系统存储位置
    		config = ""; //FileSystem.readFromFile(context, ConfigsManager.getConfigFile(context, SVG_ICON_PATH, svgFileName).getPath());
    	return config;
    }
    
	// 根据当前皮肤类型读取对应位置properties配置文件;
	public static InputStream getConfigStreamAsSkinType() {
		// return context.getResources().getAssets().open(fileName);
		return SVGManager.class.getResourceAsStream("/assets/themeFolder/"+SkinManager.getCurSkinType()+"/SVGConfig.properties");
	}
	
	/**
	 * 获取SVGParserRenderer对象:
	 * @param context
	 * @param svgFileName
	 * @return	指定存储目录下、当前皮肤样式的SVG图对象;
	 */
    public static SVGParserRenderer getSVGParserRenderer(Context context, String svgFileName){
		try {
			// 读取配置路径下SVG图的字符串/内容;
			String svgContent = getSVGParseContentAsStoragePath(context, svgFileName);
			// 解析带Css风格颜色为正常的SVG格式,并根据皮肤类型设置对应颜色:
			svgContent = parseCssStyleToSvg(SkinManager.getCurSkinType(), svgFileName, svgContent);
			// 根据SVGContent字符串内容创建SVGParserRenderer对象;
			return new SVGParserRenderer(context, svgContent);
		}catch (Exception e){
			Log.e("SVGManager", "解析获取SVG图标失败: "+ e.getMessage());
			return new SVGParserRenderer(context, "");
		}
    }
    
	/**
	 * 获取SVGParserRenderer对象:
	 * @param context
	 * @param svgFileName	assent下对应的SVG文件
	 * @param svgResId		drawable里的SVG资源文件(备用)
	 * @return	指定存储目录下、当前皮肤样式的SVG图对象;
	 */
    public static SVGParserRenderer getSVGParserRenderer(Context context, String svgFileName, int svgResId){
		// 读取配置路径下SVG图的字符串/内容;
    	String svgContent = getSVGParseContentAsStoragePath(context, svgFileName);
    	if(StringUtils.isEmpty(svgContent)){
    		svgContent = SvgRes1.getSvgString(context, svgResId);
    	}
    	// 解析带Css风格颜色为正常的SVG格式,并根据皮肤类型设置对应颜色:
    	svgContent = parseCssStyleToSvg(SkinManager.getCurSkinType(), svgFileName, svgContent);
    	// 根据SVGContent字符串内容创建SVGParserRenderer对象;
    	return new SVGParserRenderer(context, svgContent);
    }
	
    /**
     * 解析带Css风格颜色为正常的SVG格式,并根据皮肤类型设置对应颜色;
     * @param 	svgContent
     * @return	新svgContent
     */
    public static String parseCssStyleToSvg(String skinType, String svgFileName, String svgContent){
    	int startIndex =   svgContent.indexOf("<style");
    	int endIndex = svgContent.indexOf("</style>") + "</style>".length();
    	
    	String styleStr = null;
    	//解析CSS style 将颜色替换
    	if(startIndex >=0 && endIndex >= 0){
    		styleStr = svgContent.substring(startIndex, endIndex);
    		if(styleStr != null && !"".equals(styleStr)){
    			// 解析得到可换色键值对:
    			Map<String, String> styleFieldMap = obtainStyleMap(styleStr);
    			
    			Set<String> keySet = styleFieldMap.keySet();
    			String[] svgFieldNames = keySet.toArray(new String[keySet.size()]);
    			
    			if(svgFieldNames != null && svgFieldNames.length > 0){
    				// 是否存在对应可替换颜色:
    				Map<String, String> properties = SVGPropertiesManager.getProperties(skinType, svgFileName, svgFieldNames);
    				for (String svgFieldName : svgFieldNames) {
    					String svgFieldColor = properties.get(svgFieldName);
    					if(!StringUtils.isEmpty(svgFieldColor)){
    						styleFieldMap.put(svgFieldName, svgFieldColor);
    					}
    					
		    			if(svgContent.contains("class=\""+svgFieldName+"\""))
		    				svgContent = svgContent.replace("class=\""+svgFieldName+"\"", "fill=\""+styleFieldMap.get(svgFieldName)+"\"");
					}
    			}
    		}
    		// 将原Style代码置为"";
    		svgContent = svgContent.replace(styleStr, "");
    	}
    	return svgContent;
    }
    
    /**
	 * 解析CSS风格, 获得Style样式中的可换色键值映射;
	 * @param styleStr
		<style type="text/css">
		<![CDATA[
			.strokeColor{fill:#FFFFFF;}
			.fillColor{fill:#FFFFFF;}
		]]>
		</style>
	 * @return
	 */
    private static Map<String, String> obtainStyleMap(String styleStr){
		int startIndex = styleStr.indexOf(".");
		int lastStartIndex = styleStr.lastIndexOf(".");
		int endIndex = styleStr.lastIndexOf(";}")+";}".length();
		
		String colorStr;
		if(startIndex == lastStartIndex){
			colorStr = styleStr.substring(startIndex+".".length(), endIndex);
		}else{
			colorStr = styleStr.substring(startIndex, endIndex);
		}
		
		Map<String, String> styleMap = new HashMap<String, String>();
		
		String[] fillColorArr = colorStr.split(";");
		if(fillColorArr != null){
			for(int i = 0; i < fillColorArr.length; i++){
				if(fillColorArr[i].contains("fill:")){
					String[] nameColorArr = fillColorArr[i].split("fill:");
					if(nameColorArr != null){
						int nameStartIndex = nameColorArr[0].indexOf(".")+".".length();
						String svgFieldName = nameColorArr[0].substring(nameStartIndex, nameColorArr[0].length()-1);
						String svgFieldColor = nameColorArr[1];
						styleMap.put(svgFieldName, svgFieldColor);
					}
				}
			}
		}
		return styleMap;
    }
}

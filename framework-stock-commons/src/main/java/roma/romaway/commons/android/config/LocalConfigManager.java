package roma.romaway.commons.android.config;

import android.content.Context;
import android.util.Log;

import com.romaway.commons.android.fileutil.FileSystem;
import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SvgRes1;

public class LocalConfigManager {

	private Context mContext;
	public static final int SAVE_TYPE_ASSETS = 0;
    public static final int SAVE_TYPE_SYSTEM_DATA_FOLDER = 1;
    public static final int SAVE_TYPE_SDCARD = 2;
    private static int iconLoadPathType = SAVE_TYPE_ASSETS;//默认是assets目录
    
    
    
    public LocalConfigManager(Context context){
    	mContext = context;
    }
    
	 /**
     * 获取对应主题的SVG Drawable
     * @param context
     * @param saveType 存储类型，也即配制是存储在哪个目录下
     * @return
     */
    public SVGParserRenderer getSVGParserRenderer(String iconName){
        String config = "";
        if(iconLoadPathType == SAVE_TYPE_ASSETS)//本地存储位置
            config = FileSystem.getFromAssets(mContext, "panelConfigFolder/"+iconName);
        
        else if(iconLoadPathType == SAVE_TYPE_SDCARD)//SDK存储位置
            config = "";//"file://"+FileSystem.getCacheRootDir(context, "").getPath()+ url;	
        
        else if(iconLoadPathType == SAVE_TYPE_SYSTEM_DATA_FOLDER)//内部文件系统存储位置
            config = "";//FileSystem.readFromFile(context, 
                    //getConfigFile(context, "panelConfigFolder", svgPath).getPath());
        
        return SvgRes1.getSVGParserRenderer(mContext, config);
    }

	/**
	 * 获取存储下来的SVG Drawable
	 * @param context
	 * @param saveType 存储类型，也即配制是存储在哪个目录下
	 * @return
	 */
	public SVGParserRenderer getSVGParserRenderer(Context context, String svgPath, String fillColor){
		String config = "";
		try {
			if(iconLoadPathType == SAVE_TYPE_ASSETS)//本地存储位置
				config = FileSystem.getFromAssets(context, "panelConfigFolder/"+svgPath);

			else if(iconLoadPathType == SAVE_TYPE_SDCARD)//SDK存储位置
				config = "";//"file://"+FileSystem.getCacheRootDir(context, "").getPath()+ url;

			else if(iconLoadPathType == SAVE_TYPE_SYSTEM_DATA_FOLDER)//内部文件系统存储位置
				config = "";
		}catch (Exception e){
			Log.e("ConfigsManager", "读取SVG图标失败: "+ e.getMessage());
		}
		return SvgRes1.getSVGParserRenderer(context, config, fillColor);
	}
    
	public String getConfig(){
   	 String config = "";
        if(iconLoadPathType == SAVE_TYPE_ASSETS)//本地存储位置
            config = FileSystem.getFromAssets(mContext, "panelConfigFolder/local_config.json");
        
        else if(iconLoadPathType == SAVE_TYPE_SDCARD)//SDK存储位置
            config = "";//"file://"+FileSystem.getCacheRootDir(context, "").getPath()+ url;	
        
        else if(iconLoadPathType == SAVE_TYPE_SYSTEM_DATA_FOLDER)//内部文件系统存储位置
            config = "";//FileSystem.readFromFile(context, 
                    //getConfigFile(context, "panelConfigFolder", svgPath).getPath());
        
        return config;
   }
}

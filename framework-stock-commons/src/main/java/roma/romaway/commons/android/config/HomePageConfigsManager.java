package roma.romaway.commons.android.config;

import java.util.List;
import java.util.Map;

import com.romaway.commons.log.Logger;

import android.content.Context;


public class HomePageConfigsManager extends ConfigsManager{

    private Context mContext;
    private static HomePageConfigsManager mConfigsManager = null;
    
    public static ConfigsManager getInstance(){
        return mConfigsManager;
    }

    public static void newInstance(Context context, ConfigInfo configInfo){
        mConfigsManager = new HomePageConfigsManager(context, configInfo);
    }
    
    public HomePageConfigsManager(Context context, ConfigInfo configInfo) {
        super(context, configInfo);
        // TODO Auto-generated constructor stub
        mContext = context;
    }
  
    @Override
    public boolean checkMisssingIconAndDownload(ConfigsDownloader configsDownloader, String config){
        Logger.i(logTag, "开始更新：检测没有的图片，并开始下载...");
        
        List<Map<String, String>> bottomMapList = 
                JsonConfigsParser.getJsonConfigInfo(config, "ROMA_Common_Tabbar",
              new String[]{"functionCode","simpleName", 
                        "traditionalName","iconUrlNor","iconUrlSel"});
                
        List<Map<String, String>> shortcutMapList = 
                JsonConfigsParser.getJsonConfigInfo(config, "ROMA_HomePage_ShortcutMenu",
              new String[]{"functionCode","simpleName", 
                        "traditionalName","iconUrlNor","iconUrlSel"});
        
        //设置最大的图片下载个数
        setMaxDownloadIconCount((bottomMapList.size() + shortcutMapList.size()) * 2);
        
        for(Map<String, String> map : bottomMapList){
            
            calculateMaxIconCount(map);
            
            //下载正常的图片
            downloadForSvgIcon(map, configsDownloader, "iconUrlNor");
            //下载选中的图片
            downloadForSvgIcon(map, configsDownloader, "iconUrlSel");
        }
        
        for(Map<String, String> map : shortcutMapList){
            
            calculateMaxIconCount(map);
            
            //下载正常的图片
            downloadForSvgIcon(map, configsDownloader, "iconUrlNor");
            //下载选中的图片
            downloadForSvgIcon(map, configsDownloader, "iconUrlSel");
        }
        
        return super.checkMisssingIconAndDownload(configsDownloader, config);
    }
    
}

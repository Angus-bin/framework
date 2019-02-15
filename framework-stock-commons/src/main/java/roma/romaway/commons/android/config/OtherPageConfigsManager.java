package roma.romaway.commons.android.config;

import java.util.List;
import java.util.Map;

import roma.romaway.commons.android.config.ConfigInfo;
import roma.romaway.commons.android.config.ConfigsDownloader;
import roma.romaway.commons.android.config.ConfigsManager;
import roma.romaway.commons.android.config.JsonConfigsParser;

import com.romaway.commons.log.Logger;

import android.content.Context;


public class OtherPageConfigsManager extends ConfigsManager{

    private Context mContext;
    
    private static OtherPageConfigsManager mConfigsManager = null;
    
    public static ConfigsManager getInstance(){
        return mConfigsManager;
    }

    public static void newInstance(Context context, ConfigInfo configInfo){
        mConfigsManager = new OtherPageConfigsManager(context, configInfo);
    }
    
    public OtherPageConfigsManager(Context context, ConfigInfo configInfo) {
        super(context, configInfo);
        // TODO Auto-generated constructor stub
        mContext = context;
    }
  
    @Override
    public boolean checkMisssingIconAndDownload(ConfigsDownloader configsDownloader, String config){
        Logger.i(logTag, "开始更新：检测没有的图片，并开始下载...");
        
        int maxCount = 0;
        
        List<Map<String, String>> bottomMapList = 
                JsonConfigsParser.getJsonConfigInfo(config, "ROMA_Common_Tabbar",
              new String[]{"functionCode","simpleName", 
                        "traditionalName","iconUrlNor","iconUrlSel"});
                
        List<Map<String, String>> shortcutMapList = 
                JsonConfigsParser.getJsonConfigInfo(config, "ROMA_HomePage_ShortcutMenu",
              new String[]{"functionCode","simpleName", 
                        "traditionalName","iconUrlNor","iconUrlSel"});
        maxCount += (bottomMapList.size() + shortcutMapList.size()) * 2;
        
        //我模块图片的下载
        List<Map<String, String>> ROMA_Me_FunctionList =
                JsonConfigsParser.getJsonConfigInfo(config, "ROMA_Me_FunctionList",
              new String[]{"functionCode"});
       
        for(Map<String, String> map : ROMA_Me_FunctionList){
            
            String fun_key = map.get("functionCode");
            
            List<Map<String, String>> ROMA_Me_FunctionListItem =
                    JsonConfigsParser.getJsonConfigInfo(config, fun_key,
                  new String[]{/*"functionCode","simpleName", 
                            "traditionalName",*/"iconUrlNor","iconUrlSel"});
            maxCount += ROMA_Me_FunctionListItem.size() * 2;
        }
        //初始化背景图片下载
        List<Map<String, String>> ROMA_Init_backgroundImage =
                JsonConfigsParser.getJsonConfigInfo(config, "ROMA_Init_backgroundImage",
              new String[]{"iconUrlNor","iconUrlSel"});
        maxCount += ROMA_Init_backgroundImage.size() * 2;
        
        //操作指引背景图片下载
        List<Map<String, String>> ROMA_Oper_Guide =
                JsonConfigsParser.getJsonConfigInfo(config, "ROMA_Oper_Guide",
              new String[]{"iconUrlNor","iconUrlSel"});
        maxCount += ROMA_Oper_Guide.size() * 2;
        
        //设置最大的图片下载个数
        setMaxDownloadIconCount(maxCount);
        
        //下载底部栏的图片
        for(Map<String, String> map : bottomMapList){
            
            calculateMaxIconCount(map);
            
            //下载正常的图片
            downloadForSvgIcon(map, configsDownloader, "iconUrlNor");
            //下载选中的图片
            downloadForSvgIcon(map, configsDownloader, "iconUrlSel");
        }
        //下载首页快捷按钮图标
        for(Map<String, String> map : shortcutMapList){
            
            calculateMaxIconCount(map);
            
            //下载正常的图片
            downloadForSvgIcon(map, configsDownloader, "iconUrlNor");
            //下载选中的图片
            downloadForSvgIcon(map, configsDownloader, "iconUrlSel");
        }
        
        //初始化图片下载
        for(Map<String, String> map : ROMA_Init_backgroundImage){
        	 calculateMaxIconCount(map);
             //下载正常的图片
             downloadForSvgIcon(map, configsDownloader, "iconUrlNor");
             //下载选中的图片
             downloadForSvgIcon(map, configsDownloader, "iconUrlSel");
        }
        
        //下载操作指引图片
        for(Map<String, String> map : ROMA_Oper_Guide){
       	    calculateMaxIconCount(map);
            //下载正常的图片
            downloadForSvgIcon(map, configsDownloader, "iconUrlNor");
            //下载选中的图片
            downloadForSvgIcon(map, configsDownloader, "iconUrlSel");
       }
        
        //下载我主界面的图片文件
        for(Map<String, String> FunctionListMap : ROMA_Me_FunctionList){
            
            String fun_key = FunctionListMap.get("functionCode");
            
            List<Map<String, String>> ROMA_Me_FunctionListItem =
                    JsonConfigsParser.getJsonConfigInfo(config, fun_key,
                  new String[]{"iconUrlNor","iconUrlSel"});
            for(Map<String, String> map : ROMA_Me_FunctionListItem){
                calculateMaxIconCount(map);
                //下载正常的图片
                downloadForSvgIcon(map, configsDownloader, "iconUrlNor");
                //下载选中的图片
                downloadForSvgIcon(map, configsDownloader, "iconUrlSel");
                
               // Logger.d(logTag, "开始更新： 下载配置路径:"+map.get("downloadUrl"));
            }
        }
        
        return super.checkMisssingIconAndDownload(configsDownloader, config);
    }
    
}

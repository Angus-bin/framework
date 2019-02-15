package roma.romaway.commons.android.config;

import java.util.List;
import java.util.Map;

public class ConfigsUtil {
    
    /**
     * 读取主页模块的底部菜单的索引值
     * @param funkey
     * @return
     */
    public static int getMainBottomConfigIndex(String funkey){
        if(OtherPageConfigsManager/*HomePageConfigsManager*/.getInstance().getConfig() != null && 
                !/*HomePageConfigsManager*/OtherPageConfigsManager.getInstance().getConfig().equals("")){
            List<Map<String, String>> ROMA_Common_Tabbar =
                    JsonConfigsParser.getJsonConfigInfo(
                            /*HomePageConfigsManager*/OtherPageConfigsManager.getInstance().getConfig(), 
                            "ROMA_Common_Tabbar",
                  new String[]{"functionCode"});
            
            int index = 0;
            for(Map<String, String> map : ROMA_Common_Tabbar){
                if(map.get("functionCode").equals(funkey)){
                    return index;
                }
                index++;
            }
        }
        return -1;
    }
    
    /**
     * 读取行情模块的配制文件
     * @param funkey
     * @return
     */
    public static int getHqConfigIndex(String funkey){
        if(OtherPageConfigsManager.getInstance().getConfig() != null && 
                !OtherPageConfigsManager.getInstance().getConfig().equals("")){
            List<Map<String, String>> ROMA_HangQingHome_FunctionList =
                    JsonConfigsParser.getJsonConfigInfo(
                    OtherPageConfigsManager.getInstance().getConfig(),
                    "ROMA_HangQingHome_FunctionList",
                  new String[]{"functionCode"});
            int index = 0;
            for(Map<String, String> map : ROMA_HangQingHome_FunctionList){
                if(map.get("functionCode").equals(funkey)){
                    return index;
                }
                index++;
            }
        }
        return 0;
    }
}

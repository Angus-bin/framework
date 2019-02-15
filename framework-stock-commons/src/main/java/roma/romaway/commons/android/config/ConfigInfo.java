package roma.romaway.commons.android.config;

public class ConfigInfo {

    /**
     * 配置文件下载地址
     */
    public String downloadUrl;
    
    /**
     * 文件存储根目录名
     */
    public String saveFolderName;
    
    /**
     * 配置文件名字
     */
    public String configFileName;
    
    /**
     * 临时配置文件名字
     */
    public String tempConfigFileName;
    
    /**
     * 面板KEY，可通过该KEY获取配置属性
     */
    public String panelKey;
    
    /**
     * 面板属性key
     */
    public String[] attributeKey;
    
    
    public void setDownloadUrl(String downloadUrl){
        this.downloadUrl = downloadUrl;
    }
    
    public String getDownloadUrl(){
        return downloadUrl;
    }
    
    public void setSaveFolderName(String saveFolderName){
        this.saveFolderName = saveFolderName;
    }
    
    public String getSaveFolderName(){
        return saveFolderName;
    }
    
    
    public void setConfigFileName(String configFileName){
        this.configFileName = configFileName;
    }
    
    public String getConfigFileName(){
        return configFileName;
    }
    
    public void setTempConfigFileName(String tempConfigFileName){
        this.tempConfigFileName = tempConfigFileName;
    }
    
    public String getTempConfigFileName(){
        return tempConfigFileName;
    }
    
    public void setPanelKey(String panelKey){
        this.panelKey = panelKey;
    }
    
    public String getPanelKey(){
        return panelKey;
    }
    
    public void setattributeKey(String[] attributeKey){
        this.attributeKey = attributeKey;
    }
    
    public String[] getAttributeKey(){
        return attributeKey;
    }
}

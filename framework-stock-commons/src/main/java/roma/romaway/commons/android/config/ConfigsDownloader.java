package roma.romaway.commons.android.config;

import java.io.File;
import com.romaway.commons.log.Logger;
import android.content.Context;

/**
 * 负责配制文件的下载
 * @author 万籁唤
 *
 */
public class ConfigsDownloader {
    
    /**
     * 开始下载首页的配制文件
     * @param context
     */
    public void startDownloadConfigFileForHomepage(Context context,
            ConfigsManager configsManager,
            OnDownloadCompleteListener onDownloadCompleteListener){
      //下载配置文件
        File file = configsManager.getConfigFile(context, 
                configsManager.mConfigInfo.saveFolderName, 
                configsManager.mConfigInfo.tempConfigFileName);
        
        //Logger.d("downloadConfigFile", "exists:"+file.exists()+",filepatth:"+file.getPath()); 
        
        DownloadConfigFileThread downloadConfigFileThread = 
                new DownloadConfigFileThread(
                        context,
                        configsManager.mConfigInfo.downloadUrl, 
                        file, 
                onDownloadCompleteListener);
        
         if(!file.isDirectory() && !file.exists()) {//考虑已经存在同样名字的文件或者目录，
             //Logger.d("downloadConfigFile", "startDownloadForSvgIcon exists:"+file.exists());
             downloadConfigFileThread.start();//开始下载
         }else{
        	 Logger.i("快捷按钮配置文件", "警告：该文件存在相同文件名，不进行下载更新，请知晓！");
             downloadConfigFileThread.onDownloadComplete();//已经存在也视为完成
		 }
    }
    
   /**
    * 开始下载SVG图片
    * @param context
    * @param svgDownloadUrl  下载SVG文件的网络路径
    * @param svgFilePath 保存下下来的SVG文件名
    */
    public void startDownloadForSvgIcon(Context context,
            ConfigsManager configsManager,
            String svgDownloadUrl, String svgFilePath,
            OnDownloadCompleteListener onDownloadCompleteListener){
        
      //下载配置文件
        String parentFolder = configsManager.mConfigInfo.saveFolderName+"/";
        String fileName = "";
        
        if(svgFilePath != null && !svgFilePath.equals("")){
            String[] saveFileDir = svgFilePath.split("/");
            for(int i = 0; i < saveFileDir.length; i++){
                if(saveFileDir[i].contains(".")){
                    fileName = saveFileDir[i];
                    continue;
                }
                    
                parentFolder += saveFileDir[i];
            }
        }else{
            return;//说明不是需要下载的文件
        }
        
        File file = configsManager.getConfigFile(context, parentFolder, fileName);
        
//        Logger.d("downloadConfigFile", "exists:"+file.exists()+
//                ",file.isDirectory():"+file.isDirectory()+
//                ",filepatth:"+file.getPath());
        
        DownloadConfigFileThread downloadConfigFileThread = 
                new DownloadConfigFileThread(context, svgDownloadUrl, file, 
                onDownloadCompleteListener);
        
         if(!file.isDirectory() && !file.exists()) {//考虑已经存在同样名字的文件或者目录，
             //Logger.d("downloadConfigFile", "startDownloadForSvgIcon exists:"+file.exists());
             downloadConfigFileThread.start();//开始下载
             
         }else{
	     Logger.i("快捷按钮配置文件", "警告：该文件存在相同文件名，不进行下载更新，请知晓！");
             downloadConfigFileThread.onDownloadComplete();//已经存在也视为完成
	 }
        
    }
   
    public interface OnDownloadCompleteListener{  
        public void onDownloadComplete();
        public void onDownloadError();
    }
    
    
}

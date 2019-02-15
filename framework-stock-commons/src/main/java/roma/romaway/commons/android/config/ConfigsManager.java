package roma.romaway.commons.android.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roma.romaway.commons.android.config.ConfigsDownloader.OnDownloadCompleteListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.romaway.android.phone.R;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.net.serverinfo.ServerInfoMgr;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.commons.android.fileutil.FileSystem;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SvgRes1;

/**
 * 负责获取配制文件
 * @author 万籁唤
 *
 */
public class ConfigsManager {

    public ConfigInfo mConfigInfo;
    
    private Context mContext;
    private String mCurrentConfigVersion;
    private String mServerConfigVersion;
    public String logTag;
    private Handler handler = new Handler();
    
    public String mConfigContent;
    
    public static final String DATA_IS_ONLINE = "DATA_IS_ONLINE";
    
    public ConfigsManager(Context context, ConfigInfo configInfo) {
        // TODO Auto-generated constructor stub
        mContext = context;
        mConfigInfo = configInfo;
        logTag = "快捷按钮配置文件:" + mConfigInfo.configFileName;
        
        initConfig();
    }

    
    public ConfigInfo getConfigInfo(){
        return mConfigInfo;
    }
    
    public static final int SAVE_TYPE_ASSETS = 0;
    public static final int SAVE_TYPE_SYSTEM_DATA_FOLDER = 1;
    public static final int SAVE_TYPE_SDCARD = 2;
    private int savePathType = SAVE_TYPE_SYSTEM_DATA_FOLDER;//默认是内部存储路径
    
    /**
     * 图片加载路径类型
     */
    public int iconLoadPathType = savePathType;//默认是内部存储路径 
    
    /**
     * 设置图片加载路径类型 
     * @param type
     */
    public void setIconLoadPathType(int type){
        iconLoadPathType = type;
    }
    public int getIconLoadPathType(){
        return iconLoadPathType;
    }
    
    public int getSavePathType(){
        return savePathType;
    }
    
    /**
     * 用于初始化配置文件信息
     */
    public void initConfig(){
        String currentVersion = "0";
        File homepageConfigFile = getConfigFile(mContext, 
                mConfigInfo.saveFolderName, 
                mConfigInfo.configFileName);
        
        String config = null;
        
        //判断配制文件是否存在
        if(!homepageConfigFile.exists()){//不存在就直接先默认采用Assets默认内置的配制
            
            Logger.i(logTag, "界面展示：配置文件暂时不存在！并且采用的是Assets目录下默认的配置！");
            config = configFileToString(
                    mContext,
                    ConfigsManager.SAVE_TYPE_ASSETS,
                    mConfigInfo.configFileName);
            setIconLoadPathType(ConfigsManager.SAVE_TYPE_ASSETS);  
            
           // currentVersion = "0";//设置当前版本时间
        
        }else{//配制文件存在
            Logger.i(logTag, "界面展示：配置文件是存在的！当前采用的正是该配置文件！");
            
            config = configFileToString(
                    mContext,
                    savePathType,
                    mConfigInfo.configFileName);
            //去\r\n 空格
            config = StringUtils.replaceBlank(config);
            setIconLoadPathType(savePathType);
        }
        
        //获取当前正在使用的配置文件版本号
        currentVersion = JsonConfigsParser.getConfigVersion(config);
        
        mCurrentConfigVersion = StringUtils.isEmpty(currentVersion) ? "0" : currentVersion;
        mConfigContent = config;
    }
    
    /**
     * 获取Config
     */
    public String getConfig(){
        return mConfigContent;
    }
    
    /**
     * 获取当前版本号
     * @return
     */
    public String getCurrentConfigVersion(){
        return mCurrentConfigVersion;
    }
    
    /**
     * 检测
     * @param currentVersion
     * @param serverConfigVersion
     */
    public void checkConfigUpdate(String currentVersion, String serverConfigVersion){
        
        mServerConfigVersion = serverConfigVersion == null? mCurrentConfigVersion : serverConfigVersion;
        
        Logger.i(logTag, "界面展示：服务端版本："+mServerConfigVersion+",当前版本："+mCurrentConfigVersion);
        
	   //[需求]：保存本次启动相对于上次启动是否有配置文件更新
       String oldVersion =  SharedPreferenceUtils.getPreference(RomaSysConfig.pName, "oldConfigVersion", "");
       if(!StringUtils.isEmpty(oldVersion) && oldVersion.compareTo(mCurrentConfigVersion) < 0){
    	   RomaSysConfig.hasUpdateConfig = true;
       }
        //保存老版本（上一次启动的）的配置版本号
        SharedPreferenceUtils.setPreference(RomaSysConfig.pName,
				"oldConfigVersion", mCurrentConfigVersion);
        
        if(mServerConfigVersion == null && Logger.getDebugMode())
            Toast.makeText(mContext, "中台没有配置配制文件版本时间!", 500).show();
        
      //比较配制文件中的版本和认证下发的版本，比认证的小，说明有更新
        if(mCurrentConfigVersion.compareTo(mServerConfigVersion) < 0){
            Logger.i(logTag, "开始更新：当前展示的配置文件版本比较旧，正在更新...");
          //处理更新下载配制信息
            updateHomepageConfig();  
            
          //设置更新标志
            SharedPreferenceUtils.setPreference(RomaSysConfig.pName,
    				"hasUpdateConfig", true);
            
        }else{
            Logger.i(logTag, "开始更新：检测到配置文件已经是最新的了，无需更新！");
            
            if(mCurrentConfigVersion.compareTo(mServerConfigVersion) > 0 && Logger.getDebugMode()){
            	
            	Toast.makeText(mContext, "[中台警告]：当前可配置功能版本号 不应大于 认证下发的版本号！", Toast.LENGTH_LONG).show();
            }
            //没有更新时
            //正常情况这里都是满足SVG全部存在的，但以防万一有丢失了的，可以做如下错误纠正处理。但有个缺点是必须重新启动app丢失的图标才可以展示出来
            /*【待处理】循环检索配制文件中的SVG是否都存在？
                                                            如果全部存在则不做任何处理， 等待下一次启动app时采用; 如果不存在则要开始下载SVG图片了*/
        }
    }
    
    
    /**
     * 处理下载配制信息的方法
     */
    private void updateHomepageConfig(){
      //下载的临时文件
        File homepageConfigTempFile = getConfigFile(mContext, 
                mConfigInfo.saveFolderName, 
                mConfigInfo.tempConfigFileName);
        
        if(!homepageConfigTempFile.exists()){//下载的临时文件也不存在，开始下载
            Logger.i(logTag, "开始更新：下载后会保存在临时配置文件中！");
            
            downloadForHomepageConfig();//开始下载首页配制相关文件和SVG图片
            
        }else{//如果存在上次下载的临时文件
            
            String tempConfig = configFileToString(mContext,
                    savePathType,
                    mConfigInfo.tempConfigFileName);
            
            //比较下载临时文件中的版本和认证下发的版本
            if(JsonConfigsParser.getConfigVersion(tempConfig) != null &&
                    JsonConfigsParser.getConfigVersion(tempConfig).compareTo(mServerConfigVersion) == 0){
                Logger.i(logTag, "开始更新：下载的临时文件是存在的！并且版本是最新的！");
               
                //检查是否有丢失的图片并下载
                checkMisssingIconAndDownload(mPanelConfigsDownloader, tempConfig);
               
            }else{//不相等时，说明上次下载的临时文件已经过期了，应先删除，再重新下载
                Logger.i(logTag, "开始更新：下载的临时文件是存在的！但版本号已经过时了，并删除重新开始更新！");
                homepageConfigTempFile.delete();//删除临时的下载文件
                downloadForHomepageConfig();//开始下载首页配制相关文件和SVG图片
            }
        }
    }

    ConfigsDownloader mPanelConfigsDownloader = new ConfigsDownloader();
    /**
     * 下载首页配制文件以及SVG图片
     */
    private void downloadForHomepageConfig(){
        
        Logger.i(logTag, "开始更新：配置文件下载地址："+mConfigInfo.downloadUrl);
        
        mPanelConfigsDownloader.startDownloadConfigFileForHomepage(mContext, 
                this,
                new OnDownloadCompleteListener(){
                    @Override
                    public void onDownloadComplete() {
                        // TODO Auto-generated method stub
                        Logger.i(logTag, "开始更新：临时配置文件下载成功！开始图片下载准备...");
                        String tempConfig = configFileToString(mContext,
                                savePathType,
                                mConfigInfo.tempConfigFileName);
                        checkMisssingIconAndDownload(mPanelConfigsDownloader, tempConfig);
                    }
                    @Override
                    public void onDownloadError() {
                        // add by zhengms 2015/12/4 下载失败延时下载,避免环境配置或网络异常时过于频繁请求:
                        Logger.i(logTag, "开始更新：临时配制文件下载失败！15s后重新下载...");
						if(handler != null){
							handler.postDelayed(new Runnable(){    
								public void run() {    
//									String ss = mConfigInfo.downloadUrl;
//									ServerInfoMgr.getInstance().changeServer(currentServerInfo)
									Logger.e("","-----------------------mConfigInfo.downloadUrl="+mConfigInfo.downloadUrl);
									List<ServerInfo> list = ServerInfoMgr.getInstance().getServerInfos(204);
									changeServer(mConfigInfo.downloadUrl,list);
									downloadForHomepageConfig();
								}    
							}, 15000);
						}
                    }
        });  
    }
    /**
	 * 切换服务器,当切换成功后，返回true,否则，返回false;
	 * 
	 * @param currentServerInfo
	 */
	public final boolean changeServer(String url ,List<ServerInfo> mapServers) {
		if (!StringUtils.isEmpty(url) && url.contains("https")) {
			url = url.replace("https", "http");
		}
		int currIndex = url.lastIndexOf(":");
		if (currIndex > 5) {
			url = url.substring(0, currIndex);
		}
		if (mapServers == null || mapServers.isEmpty()) {
			return false;
		}
		List<ServerInfo> lstServerInfos = mapServers;
		if (lstServerInfos == null || lstServerInfos.size() <= 1) {
			return false; // 若只有1个服务器，则返回
		}
		boolean hasFound = false;
		int idx = 0;
		ServerInfo tmpServerInfo = null ;
		boolean useFirst = false;
		for (ServerInfo s : lstServerInfos) {
			if (idx == 0) {
				tmpServerInfo = s;
				useFirst = true;
			}
			if (hasFound) {
				setDefaultServerInfo(s);
				return true;
			}
			String tempUrl = s.getUrl();
			int index = tempUrl.lastIndexOf(":");
			if (index > 5) {
				tempUrl = tempUrl.substring(0, index);
			}
			if (tempUrl.equalsIgnoreCase(url)) {
				hasFound = true; // 服务器相同
			}
			idx++;
		}

		// 若是最后1个服务器，则使用第1条。
		if (useFirst) {
			setDefaultServerInfo(tmpServerInfo);
		}
		return useFirst;
	}
    private void setDefaultServerInfo(ServerInfo s) {
    	if(s == null){
    		return;
    	}
    	//++[需求]添加统一认证版本控制 wanlh 2015/12/08
		String softtype = SysConfigs.SOFT_TYPE +"/";
		switch(Res.getInteger(R.integer.system_server_version)){
		case 1:
			softtype = "";
			break;
		case 2:
			softtype = SysConfigs.SOFT_TYPE +"/";
			break;
		}
		//--[需求]添加统一认证版本控制 wanlh 2015/12/08
		
        //[需求] 添加软件类型 用于初始化协议入参 wanlh 2105/11/30
		// 初始化其它配置文件信息
		String otherSub = "/api/config/app/ui/otherpage/online/"
				+ softtype
				+ SysConfigs.APPID;
		if (ConfigsManager.isOnline()) {
			otherSub = "/api/config/app/ui/otherpage/online/"
					+ softtype
					+ SysConfigs.APPID;
		} else {
			otherSub = "/api/config/app/ui/otherpage/beta/"
					+ softtype
					+ SysConfigs.APPID;
		}
		Logger.e("","----------------------------new URL="+s.getUrl()+otherSub);
    	mConfigInfo.downloadUrl = s.getUrl() + otherSub;
	}


	/**
     * 检查缺失的配制图片，并下载
     * @param configsDownloader
     */
    public boolean checkMisssingIconAndDownload(ConfigsDownloader configsDownloader, String config){
        
        return false;
    }
    
    /**
     * 计算出最大需要下载图片的张数
     * @param map
     */
    protected void calculateMaxIconCount(Map<String, String> map){
        
        String norstr = map.get("iconUrlNor");
        if(norstr == null || norstr.equals(""))
            maxDownloadIconCount--;
        String selstr = map.get("iconUrlSel");
        if(selstr == null || selstr.equals(""))
            maxDownloadIconCount--;
    }
    
    /**
     * 提交可配置文件生效
     */
    private synchronized void commit(){
        Logger.i(logTag, "更新完成：全部的配制信息和图片文件下载完成，并删除临时配制文件！下次启动生效!");
        File homepageConfigFile = getConfigFile(mContext, 
                mConfigInfo.saveFolderName, 
                mConfigInfo.configFileName);
        if(homepageConfigFile.exists()){
            homepageConfigFile.delete();
        }
        //临时文件重命名为配制文件
        File homepageConfigTempFile = getConfigFile(mContext, 
                mConfigInfo.saveFolderName, 
                mConfigInfo.tempConfigFileName);
        homepageConfigTempFile.renameTo(homepageConfigFile);
    }
    
    /**
     * 计数当前配制文件中有最多需要下载图片的个数
     */
    private int maxDownloadIconCount = 0;
    /**
     * 计数已经下载成功的图片个数
     */
    private int completeDownloadIconCount = 0;
    
    /**
     * 设置最大的图片下载个数
     * @param maxCount
     */
    public void setMaxDownloadIconCount(int maxCount){
        maxDownloadIconCount = maxCount;
    }
    /**
     * 获取最多需要下载的图片张数
     * @return
     */
    public int getMaxDownloadIconCount(){
        return maxDownloadIconCount;
    }
    
    private Map<String, Integer> downloadCountMap = new HashMap<String, Integer>();
    /*
     * 下载Svg 图片配制文件
     */
    protected void downloadForSvgIcon(final Map<String, String> map, 
            final ConfigsDownloader configsDownloader,
            final String iconKey){
        String filePath = map.get(iconKey);
        final String saveFilePath = filePath;
        ServerInfo serverInfo = ServerInfoMgr.getInstance().getDefaultServerInfo(ProtocolConstant.SERVER_FW_AUTH);
        final String svgDownloadUrl = (serverInfo == null ? "" : serverInfo.getAddress() + map.get("downloadUrl")+filePath);
       
        /*++ [优化] 优化图标下载失败修改为最多只下载3次*/
        Integer downloadCount = downloadCountMap.get(svgDownloadUrl);
        if(downloadCount == null)
        	downloadCount = 0;
        if(downloadCount < 3){
        	downloadCount++;
        	downloadCountMap.put(svgDownloadUrl, downloadCount);
        }else{
        	return;
        }
        /*-- [优化] 优化图标下载失败修改为最多只下载3次*/
        
        if(svgDownloadUrl.split("http").length > 2){
        	if(Logger.getDebugMode())
        		Toast.makeText(mContext, "中台配置文件有问题！配置了错误路径["+map.get("downloadUrl")+"]应去掉IP部分!", 500).show();
        } 
        
        Logger.i(logTag, "图片下载路径:"+svgDownloadUrl);
        
        configsDownloader.startDownloadForSvgIcon(mContext,
                this,
                svgDownloadUrl, saveFilePath,
                new OnDownloadCompleteListener(){

                    @Override
                    public void onDownloadComplete() {
                        // TODO Auto-generated method stub
                        //判断是否全部下载完成
                        synchronized(this){
                                //Logger.d(logTag, "更新完成：maxDownloadIconCount:"+maxDownloadIconCount+",completeDownloadIconCount:"+completeDownloadIconCount);
                                if(++completeDownloadIconCount == maxDownloadIconCount){
                                    
                                    commit();
                                    Logger.i(logTag, "更新完成：共更新"+maxDownloadIconCount+"张配置图片！");
                                    
                                    maxDownloadIconCount = 0;
                                    completeDownloadIconCount = 0;
                                }
                            }
                    }

                    @Override
                    public void onDownloadError() {
                        // TODO Auto-generated method stub
                    	
                    	if(!svgDownloadUrl.contains("ueditor")){
                    		Logger.i(logTag, "下载失败，图片下载路径错误！停止重新下载！"+svgDownloadUrl);
                    		if(Logger.getDebugMode())
                    			Toast.makeText(mContext, "[debug]可配置图片下载路径错误!"+svgDownloadUrl, Toast.LENGTH_LONG).show();
                    		return;
                    	}
                    	
                    	Logger.i(logTag, "下载失败，重新开始下载！ onDownloadError");
                        //下载失败会重新下载
                        downloadForSvgIcon(map, 
                                configsDownloader,
                                iconKey);
                    }
        });
    }
    
    /**
     * 获取配制文件中的配制字符串信息
     * @param context
     * @param fromPath 从某种路径类型
     * @param fileName 获取配制信息的文件名
     * @return
     */
    public String configFileToString(Context context, int fromPath, String fileName){
        String config = "";
        
        if(fromPath == SAVE_TYPE_ASSETS)//本地存储位置
            config = FileSystem.getFromAssets(context, mConfigInfo.saveFolderName+"/"+fileName);
        
        else if(fromPath == SAVE_TYPE_SDCARD)//SDK存储位置
            config = "";//"file://"+FileSystem.getCacheRootDir(context, "").getPath()+ url;
        
        else if(fromPath == SAVE_TYPE_SYSTEM_DATA_FOLDER)//内部文件系统存储位置
            config = FileSystem.readFromFile(context, 
                    getConfigFile(context, mConfigInfo.saveFolderName, fileName).getPath());
        
        return config;
    }
    
    /**
     * 获取存储下来的SVG Drawable
     * @param context
     * @param saveType 存储类型，也即配制是存储在哪个目录下
     * @return
     */
    public SVGParserRenderer getSVGParserRenderer(Context context, String svgPath){
        String config = "";
        try {
            if(iconLoadPathType == SAVE_TYPE_ASSETS)//本地存储位置
                config = FileSystem.getFromAssets(context, "panelConfigFolder/"+svgPath);

            else if(iconLoadPathType == SAVE_TYPE_SDCARD)//SDK存储位置
                config = "";//"file://"+FileSystem.getCacheRootDir(context, "").getPath()+ url;

            else if(iconLoadPathType == SAVE_TYPE_SYSTEM_DATA_FOLDER)//内部文件系统存储位置
                config = FileSystem.readFromFile(context,
                        getConfigFile(context, "panelConfigFolder", svgPath).getPath());
        }catch (Exception e){
            Log.e("ConfigsManager", "读取SVG图标失败: "+ e.getMessage());
        }
        return SvgRes1.getSVGParserRenderer(context, config);//new SVGParserRenderer(context, config);
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
                config = FileSystem.readFromFile(context,
                        getConfigFile(context, "panelConfigFolder", svgPath).getPath());
        }catch (Exception e){
            Log.e("ConfigsManager", "读取SVG图标失败: "+ e.getMessage());
        }
        return SvgRes1.getSVGParserRenderer(context, config, fillColor);
    }

    /**
     * 获取存储下来的SVG Drawable内容
     * @param context
     * @param saveType 存储类型，也即配制是存储在哪个目录下
     * @return
     */
    public String getSvgDrawableContent(Context context, String svgPath){
        String config = "";
        if(iconLoadPathType == SAVE_TYPE_ASSETS)//本地存储位置
            config = FileSystem.getFromAssets(context, "panelConfigFolder/"+svgPath);
        
        else if(iconLoadPathType == SAVE_TYPE_SDCARD)//SDK存储位置
            config = "";//"file://"+FileSystem.getCacheRootDir(context, "").getPath()+ url;
        
        else if(iconLoadPathType == SAVE_TYPE_SYSTEM_DATA_FOLDER)//内部文件系统存储位置
            config = FileSystem.readFromFile(context, 
                    getConfigFile(context, "panelConfigFolder", svgPath).getPath());
        
        return config;
    }
    
    public File getConfigFile(Context context, String configFolder, String configFile){
        if(iconLoadPathType == SAVE_TYPE_SDCARD){//SDK存储位置
            return new File(FileSystem.getCacheRootDir(context, configFolder),
                    configFile);
        }else
            return new File(FileSystem.getDataCacheRootDir(context, configFolder),
                configFile);
    }
    
    public Bitmap getBitmap(Context context, String svgPath){
    	Bitmap bm = null;
    	if(iconLoadPathType == SAVE_TYPE_ASSETS){//本地存储位置
    		InputStream input = null;
    		try {
				input = context.getAssets().open("panelConfigFolder/" + svgPath);
				bm = BitmapFactory.decodeStream(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(input != null){
					try {
						input.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
    	} else if(iconLoadPathType == SAVE_TYPE_SYSTEM_DATA_FOLDER){//内部文件系统存储位置
    		String path = context.getFilesDir() + "/panelConfigFolder/" + svgPath;
    		bm = FileSystem.parsePngFile(path);
    	}
    	
    	return bm;
    }

	public static boolean isOnline() {
		return SharedPreferenceUtils.getPreference(SharedPreferenceUtils.DATA_CONFIG, ConfigsManager.DATA_IS_ONLINE, true);
	}

	/**
	 * 设置是否是正式配置地址
	 * @param isOnline
	 */
	public static void setOnline(boolean isOnline) {
		SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG, ConfigsManager.DATA_IS_ONLINE, isOnline);
	}
    
    
}

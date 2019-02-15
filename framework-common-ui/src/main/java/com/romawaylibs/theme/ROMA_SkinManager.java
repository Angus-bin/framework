package com.romawaylibs.theme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.romalibs.utils.Logger;
import com.romalibs.utils.gson.GsonHelper;
import com.romaway.commons.file.FileSystem;
import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SvgRes1;
import com.romawaylibs.picasso.PicassoHelper;
import com.romawaylibs.theme.svg.SVGManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 皮肤设置管理器
 * @author wanlh
 * @version 1.0
 */
public class ROMA_SkinManager {
	
	private static SharedPreferences mSharedPreferences;
	private static final String SKINCONFIG = "SkinConfig";
	private static final String CURSKINTYPEKEY = "curSkinTypeKeyStr";
	private static final String SDPATH = Environment.getExternalStorageDirectory().getPath();
	/** 券商默认皮肤设置 */
	private static String mDefaultSkinKeyStr = "BLACK_SKIN";

	//全局缓存主题
	private static Map<String, String> skinMap;

	private static Context mContext;
	/**新版本标记*/
	private static boolean newVersion = false;
	/**
	 * 添加皮肤更换监听器接口
	 * @author wanlh
	 *
	 */
	public interface OnSkinChangeListener{
		/**
		 * 皮肤改变监听回调
		 * @param skinTypeKey  改变后的皮肤类型
		 */
		public void onSkinChanged(String skinTypeKey);
	}
	/**
	 * 添加皮肤设置监听器
	 * @param onSkinChangeListener
	 */
	public static void setOnSkinChangeListener(OnSkinChangeListener onSkinChangeListener){
		mOnSkinChangeListenerList.add(onSkinChangeListener);
	}

	/**
	 * 注册皮肤切换监听器
	 * @param onSkinChangeListener
     */
	public static void registerSkinListener(OnSkinChangeListener onSkinChangeListener){
		mOnSkinChangeListenerList.add(onSkinChangeListener);
	}

	/**
	 * 反注册皮肤切换监听器
	 * @param onSkinChangeListener
	 */
	public static void unRregisterSkinListener(OnSkinChangeListener onSkinChangeListener){
		removeOnSkinChangeListener(onSkinChangeListener);
	}

	/**
	 * 移除皮肤设置监听器
	 */
	public static void removeOnSkinChangeListener(OnSkinChangeListener onSkinChangeListener){
		mOnSkinChangeListenerList.remove(onSkinChangeListener);
	}
	private static List<OnSkinChangeListener> mOnSkinChangeListenerList = new ArrayList<OnSkinChangeListener>();
	
	/**
	 * 初始化皮肤，一般用作程序第一次执行时调用
	 * @param context
	 */
	public static void initSkin(Context context) {
		initSkin(context, null);
	}

	/**
	 * 初始化皮肤，一般用作程序第一次执行时调用
	 * @param context
	 */
	public static void initSkin(Context context, String defaultSkinKeyStr) {
		if (!TextUtils.isEmpty(defaultSkinKeyStr))
			mDefaultSkinKeyStr = defaultSkinKeyStr;
		mContext = context;
		mSharedPreferences = context.getSharedPreferences(SKINCONFIG, 0);
		getThemeList(context);
		notifyUpdated(context);
	}

	/**
	 * 新版本的初始化主题
	 */
	public static void initSkin(Context context, String defaultSkinKeyStr,boolean flag) {
		if (!TextUtils.isEmpty(defaultSkinKeyStr))
			mDefaultSkinKeyStr = defaultSkinKeyStr;
		mContext = context;
		newVersion = flag;
		mSharedPreferences = context.getSharedPreferences(SKINCONFIG, 0);
		notifyUpdated();
	}

	/**
	 * 获取颜色
	 * @param colorName
	 * @deprecated
	 * @return
	 */
	public static int getColor(String colorName){
		int color = 0;
		if(skinMap == null)
			return color;
		
		try{
			color = Color.parseColor(skinMap.get(colorName));
		}catch(Exception e){
			
		}
		return color;
	}
	
	/**
	 * 获取皮肤颜色
	 * @param colorName 颜色 key
	 * @param defaultColor 默认的颜色值
	 * @return 返回颜色值
	 */
	public static int getColor(String colorName, int defaultColor){
		int color = 0;
		if(skinMap == null)
			return defaultColor;
		
		try{
			color = Color.parseColor(skinMap.get(colorName));
		}catch(Exception e){
			color = defaultColor;
		}
		
		return color;
	}

	/**
	 * 获取皮肤颜色
	 * @param colorName 颜色 key
	 * @param defaultColor 默认的颜色值
	 * @return 返回颜色值
	 */
	public static String getColor(String colorName, String defaultColor){
		String color;
		if(skinMap == null)
			return defaultColor;

		try{
			color = skinMap.get(colorName);
		}catch(Exception e){
			color = defaultColor;
		}
		return color;
	}
	
	/**
	 * 返回图片的本地路径，找不到就返回默认的图片资源ID
	 * @param drawableName Assets目录下的图片名称
	 * @param defIconResId 本地res/drawable目录下图片资源ID
	 * @return 返回String类型的Assets目录下icon路径，或者返回默认的int型drawable 资源ID
	 */
	@SuppressLint("NewApi")
	public static Drawable getDrawable(Context context, String drawableName, int defIconResId) {
		String curTheme = getCurSkinType();
	    //当没有主题的处理
	    if(TextUtils.isEmpty(curTheme))
	    	return context.getResources().getDrawable(defIconResId);
		
	    StringBuffer sb = new StringBuffer();
	    sb.append("themeFolder/");
	    sb.append(curTheme);
	    sb.append("/drawable/");
	    sb.append(drawableName);
	    sb.append(".png");
		String filePath = sb.toString();
		
		Drawable drawable = null;
		Bitmap bm = null;
		try {
			InputStream input = context.getResources().getAssets().open(filePath);
			 bm = BitmapFactory.decodeStream(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(bm == null) {// 说明是资源默认的id
			drawable = context.getResources().getDrawable(defIconResId);
		}else {
			drawable = new BitmapDrawable(context.getResources(), bm);
		}
		
		return drawable;
	}
	
	/**
	 * 设置当前选择的皮肤类型值
	 */
	public static void setCurSkinType(String skinTypeKey){
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(CURSKINTYPEKEY, skinTypeKey);
		editor.commit(); 
	}
	
	/**
	 * 获取当前皮肤
	 * @return
	 */
	public static String getCurSkinType(){
		if(mSharedPreferences != null){
			return mSharedPreferences.getString(CURSKINTYPEKEY, mDefaultSkinKeyStr);
		}
		return mDefaultSkinKeyStr;
	}
		
	/**
	 * 通知主体皮肤更新，与 setCurSkinType 配合调用
	 * @param context
	 * @return 返回主题  map 集
	 */
	public static Map<String, String> notifyUpdated(Context context){
		final String curTheme = getCurSkinType();

	    String colorJson = FileSystem.getFromAssets(context, "themeFolder/"+curTheme+"/color");
		if(!TextUtils.isEmpty(colorJson))
	    	skinMap = GsonHelper.dataMapFromJson(colorJson);

	    //开始通知及时更新皮肤
	    Handler handler = new Handler();
  		for (final OnSkinChangeListener onSkinChangeListener : mOnSkinChangeListenerList) {
  			handler.post(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					onSkinChangeListener.onSkinChanged(curTheme);//通知皮肤被改变
				}
  			});
  			
  		}
		return skinMap;
	}
	
	/**
	 * @deprecated
	 * 更新当前皮肤,一般与 setCurSkinType 配合调用
	 * @return
	 */
	public static Map<String, String> updateCurSkin(Context context){
	    final String curTheme = getCurSkinType();

	    String colorJson = FileSystem.getFromAssets(context, "themeFolder/"+curTheme+"/color");
	    skinMap = GsonHelper.dataMapFromJson(colorJson);

	  //开始通知及时更新皮肤
	    Handler handler = new Handler();
  		for (final OnSkinChangeListener onSkinChangeListener : mOnSkinChangeListenerList) {
  			handler.post(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					onSkinChangeListener.onSkinChanged(curTheme);//通知皮肤被改变
				}
  			});
  			
  		}
		return skinMap;
	}

	/**
	 * 通知更新皮肤，与 setCurSkinType 配合调用
	 * @return 返回主题  map 集
	 */
	public static Map<String, String> notifyUpdated(){
		if (newVersion) {
			final String curTheme = getCurSkinType();

			String colorJson = FileSystem.getFromAssets(mContext, "themeFolder/" + curTheme + "/theme");
			if (!TextUtils.isEmpty(colorJson)) {
				Map<String, Object> themeMap = GsonHelper.dataMapFromJson(colorJson);
				skinMap = (Map<String, String>) themeMap.get("color");
			} else {
				File file = new File(SDPATH + "/download/kdsTheme/" + curTheme + "/theme.json");
				if (file.exists()) {
					StringBuilder sb = new StringBuilder();
					try {
						FileInputStream fis = new FileInputStream(file);
						int c;
						while ((c = fis.read()) != -1) {
							sb.append((char) c);
						}
						fis.close();

						Map<String, Object> themeMap = GsonHelper.dataMapFromJson(sb.toString());
						skinMap = (Map<String, String>) themeMap.get("color");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			//开始通知及时更新皮肤
			Handler handler = new Handler();
			for (final OnSkinChangeListener onSkinChangeListener : mOnSkinChangeListenerList) {
				handler.post(new Runnable() {

					@Override
					public void run() {
						onSkinChangeListener.onSkinChanged(curTheme);//通知皮肤被改变
					}
				});

			}
		}else{
			notifyUpdated(mContext);
		}
		return skinMap;
	}

	public static final int SAVE_TYPE_ASSETS = 0;
    public static final int SAVE_TYPE_SYSTEM_DATA_FOLDER = 1;
    public static final int SAVE_TYPE_SDCARD = 2;
    public static int iconLoadPathType = SAVE_TYPE_ASSETS;//默认是assets目录
	/**
     * 获取对应主题的SVG Drawable
     * @param context
     * @return
     */
    public static SVGParserRenderer getSVGParserRenderer(Context context, String iconName){
        String config = "";
        if(iconLoadPathType == SAVE_TYPE_ASSETS)//本地存储位置
            config = FileSystem.getFromAssets(context, "themeFolder/"+getCurSkinType()+"/icon/"+iconName+".svg");
        
        else if(iconLoadPathType == SAVE_TYPE_SDCARD)//SDK存储位置
            config = "";//"file://"+FileSystem.getCacheRootDir(context, "").getPath()+ url;	
        
        else if(iconLoadPathType == SAVE_TYPE_SYSTEM_DATA_FOLDER)//内部文件系统存储位置
            config = "";//FileSystem.readFromFile(context, 
                    //getConfigFile(context, "panelConfigFolder", svgPath).getPath());
        
        return SvgRes1.getSVGParserRenderer(context, config);
    }

	/**
	 * 获取对应主题的信息
	 * @param info 信息的类型
	 * @return 信息
	 */
	public static String getInfo(String info){

		String colorJson = FileSystem.getFromAssets(mContext, "themeFolder/" + getCurSkinType() + "/theme");

		if (!TextUtils.isEmpty(colorJson)){
			Map<String, Object> themeMap = GsonHelper.dataMapFromJson(colorJson);
			Map<String, Object> infoMap = (Map<String, Object>) themeMap.get("info");
			return (String) infoMap.get(info);
		}

		return "";
	}

	/**
	 * 获取对应主题的普通图片资源
	 * @return 图片的路径
	 */
	public static String getNormalImageName(String name) {

		String path = "themeFolder/" + getCurSkinType() + "/image/";
		String colorJson = FileSystem.getFromAssets(mContext, "themeFolder/" + getCurSkinType() + "/theme");

		if (!TextUtils.isEmpty(colorJson)){
			Map<String, Object> themeMap = GsonHelper.dataMapFromJson(colorJson);
			Map<String, Object> imageMap = (Map<String, Object>) themeMap.get("image");
			Map<String, String> url = (Map<String, String>) imageMap.get(name);
			return path + url.get("iconUrlNor");
		}else{
			File file = new File(SDPATH + "/download/kdsTheme/" + getCurSkinType() + "/theme.json");
			if (file.exists()) {
				StringBuilder sb = new StringBuilder();
				try {
					FileInputStream fis = new FileInputStream(file);
					int c;
					while ((c = fis.read()) != -1) {
						sb.append((char) c);
					}
					fis.close();

					Map<String, Object> themeMap = GsonHelper.dataMapFromJson(sb.toString());
					Map<String, Object> imageMap = (Map<String, Object>) themeMap.get("image");
					Map<String, String> url = (Map<String, String>) imageMap.get(name);
					return path + url.get("iconUrlNor");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

	/**
	 * 获取对应主题的选中图片资源
	 * @param name 图片的名称
	 * @return 图片的路径
	 */
	public static String getSelectImageName(String name) {

		String path = "themeFolder/" + getCurSkinType() + "/image/";
		String colorJson = FileSystem.getFromAssets(mContext, "themeFolder/" + getCurSkinType() + "/theme");

		if (!TextUtils.isEmpty(colorJson)){
			Map<String, Object> themeMap = GsonHelper.dataMapFromJson(colorJson);
			Map<String, Object> imageMap = (Map<String, Object>) themeMap.get("image");
			Map<String, String> url = (Map<String, String>) imageMap.get(name);
			return path + url.get("iconUrlSel");
		}else{
			File file = new File(SDPATH + "/download/kdsTheme/" + getCurSkinType() + "/theme.json");
			if (file.exists()) {
				StringBuilder sb = new StringBuilder();
				try {
					FileInputStream fis = new FileInputStream(file);
					int c;
					while ((c = fis.read()) != -1) {
						sb.append((char) c);
					}
					fis.close();

					Map<String, Object> themeMap = GsonHelper.dataMapFromJson(sb.toString());
					Map<String, Object> imageMap = (Map<String, Object>) themeMap.get("image");
					Map<String, String> url = (Map<String, String>) imageMap.get(name);
					return path + url.get("iconUrlSel");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

    /**
     * 获取主题列表
     * @param context
     * @return
     */
    private static List<ThemeInfo> getThemeList(Context context){
    	List<ThemeInfo> themeList = new ArrayList<ThemeInfo>();
    	try {
    		//获取主题的主目录，该主目录是和config中的key是一致的
			String[] themeArray = context.getResources().getAssets().list("themeFolder");
			for(int i = 0; i < themeArray.length; i++){
				
				String config = FileSystem.getFromAssets(context, "themeFolder/"+themeArray[i]+"/config");
				//ThemeInfo ti = new ThemeInfo();
				ThemeInfo ti = GsonHelper.objectFromJson(config, 
						new TypeToken<ThemeInfo>(){}.getType());
				
//				ti.version = JsonConfigsParser.getSimpleJson(config, "version");
//				ti.key = JsonConfigsParser.getSimpleJson(config, "SkinKey");
//				ti.name = JsonConfigsParser.getSimpleJson(config, "SkinName");
				
				if(TextUtils.isEmpty(ti.SkinKey) || !themeArray[i].equals(ti.SkinKey)){
					Logger.d("主题换肤", themeArray[i]+"主题为非法主题！");
				}else if(SVGManager.SVG_ICON_NAME.equalsIgnoreCase(ti.SkinKey)){
					Logger.d("主题换肤", "正常获取到皮肤公共图标目录");
				}else{
					themeList.add(ti);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return themeList;
    }
    
    /**
     * @deprecated
     * @param context
     * @return
     */
    public static String getJiaoYiSkin(Context context){
    	String skin = FileSystem.getFromAssets(context, "themeFolder/"+getCurSkinType()+"/skin_css");
    	if (TextUtils.isEmpty(skin)) {
			return "-1";
		}
    	return skin;
    }
    
    /**
     * 获取 Assets/themeFolder/getCurSkinType 目录下的主题皮肤文件
     * @param context
     * @param skinFile 皮肤主体文件名
     * @return
     */
    public static String getSkinFile(Context context, String skinFile){
    	String skin = FileSystem.getFromAssets(context, "themeFolder/"+getCurSkinType()+"/skinFile");
    	if (TextUtils.isEmpty(skin)) {
			return "-1";
		}
    	return skin;
    }

	/** Android系统下的 asset 绝对路径 */
	public final static String ASSET_DIR = "file:///android_asset/";
	/**
	 * 加载assets目录当前皮肤下指定名称文件:
	 * @param context
	 * @param imageView				加载的ImageView对象
	 * @param drawableName			加载assets目录当前皮肤下指定名称文件名称
	 * @param defIconResId			加载失败时默认显示图片
     */
	public static void loadImageFile(Context context, ImageView imageView, String drawableName, int defIconResId){
		StringBuffer sb = new StringBuffer();
		sb.append(ASSET_DIR)
				.append("themeFolder/")
				.append(ROMA_SkinManager.getCurSkinType())
				.append("/drawable/")
				.append(drawableName);
		String filePath = sb.toString();
		PicassoHelper.load(context, imageView, filePath, defIconResId);
	}
}

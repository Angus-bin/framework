package roma.romaway.commons.android.theme;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import roma.romaway.commons.android.theme.svg.SVGManager;

//import com.google.gson.reflect.TypeToken;
//import com.romalibs.utils.gson.GsonHelper;
import com.romaway.commons.android.fileutil.FileSystem;
import com.romaway.commons.json.JSONUtils;
import com.romaway.commons.log.Logger;
import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SvgRes1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * 皮肤设置管理器
 * @author wanlh
 * @deprecated
 */
public class SkinManager {
	//----------------皮肤分类常量------------------
	/** 白色皮肤*/
	public final static String SKIN_WHITE = "WHITE_SKIN";
	/** 黑色皮肤*/
	public final static String SKIN_BLACK = "BLACK_SKIN";
	/** 橙色皮肤*/
	public final static String SKIN_ORANGE = "ORANGE_SKIN";
	/** 红色皮肤*/
	public final static String SKIN_RED = "RED_SKIN";
	//----------------皮肤分类常量------------------

	private static SkinManager mSkinConfigManager;
	private static SharedPreferences mSharedPreferences;
	public static final String SKINCONFIG = "SkinConfig";
	public static final String CURSKINTYPEKEY = "curSkinTypeKeyStr";
	private Context mContext;
	//全局缓存主题
	private static Map<String, String> skinMap;
	/** 券商默认皮肤类型:  */
	private static String mDefaultSkinType = SKIN_BLACK;

	/**
	 * 添加皮肤更换监听器
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
	 * 移除皮肤设置监听器
	 * @param onSkinChangeListener
	 */
	public static void removeOnSkinChangeListener(OnSkinChangeListener onSkinChangeListener){
		mOnSkinChangeListenerList.remove(onSkinChangeListener);
	}
	private static List<OnSkinChangeListener> mOnSkinChangeListenerList = new ArrayList<OnSkinChangeListener>();

	private SkinManager(Context context) {
		this(context, mDefaultSkinType);
	}

	private SkinManager(Context context, String defaultSkinType) {
		mContext = context;
		mDefaultSkinType = defaultSkinType;
		mSharedPreferences = context.getSharedPreferences(SKINCONFIG, 0);
		getThemeList(context);
		updateCurSkin();
	}

	public static void instance(Context context, String defaultSkinType){
		if(mSkinConfigManager == null)
			mSkinConfigManager = new SkinManager(context, defaultSkinType);
	}

	public synchronized static SkinManager getInstance(Context context){
		if(mSkinConfigManager == null){
			mSkinConfigManager = new SkinManager(context);
		}
		return mSkinConfigManager;
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
	 * 获取颜色
	 * @param colorName
	 * @return
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

//	    String header = "file:///android_asset/";
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
	 * 设置存储当前选择的皮肤类型值
	 * @param skinTypeKey
	 */
	public void setCurSkinType(String skinTypeKey){
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(CURSKINTYPEKEY, skinTypeKey);
		editor.commit();
	}

	/**
	 * 获取当前存储的皮肤
	 * @return
	 */
	public static String getCurSkinType(){
		if(mSharedPreferences != null){
			return mSharedPreferences.getString(CURSKINTYPEKEY, mDefaultSkinType);
		}
		return null;
	}

	/**
	 * 更新当前皮肤
	 * @return
	 */
	public Map<String, String> updateCurSkin(){
		String curTheme = getCurSkinType();
		//当没有主题的处理
		if(TextUtils.isEmpty(curTheme)){
			//设置默认的皮肤
			//curTheme = Res.getString(R.string.kconfigs_defaultSkin);
			setCurSkinType(curTheme);
		}

		String colorJson = FileSystem.getFromAssets(mContext, "themeFolder/"+curTheme+"/color");
		// skinMap = GsonHelper.dataMapFromJson(colorJson);
		skinMap = JSONUtils.toHashMap(colorJson);

		//开始通知及时更新皮肤
		for (OnSkinChangeListener onSkinChangeListener : mOnSkinChangeListenerList) {
			onSkinChangeListener.onSkinChanged(curTheme);//通知皮肤被改变
		}
		return skinMap;
	}

	public SharedPreferences getSkinConfigPreferences(){
		return mSharedPreferences;
	}

	public static final int SAVE_TYPE_ASSETS = 0;
	public static final int SAVE_TYPE_SYSTEM_DATA_FOLDER = 1;
	public static final int SAVE_TYPE_SDCARD = 2;
	public static int iconLoadPathType = SAVE_TYPE_ASSETS;//默认是assets目录
	/**
	 * 获取对应主题的SVG Drawable
	 * @param context
	 * @param iconName
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
	 * 获取主题列表
	 * @param context
	 * @return
	 */
	public static List<ThemeInfo> getThemeList(Context context){
		List<ThemeInfo> themeList = new ArrayList<ThemeInfo>();
		try {
			//获取主题的主目录，该主目录是和config中的key是一致的
			String[] themeArray = context.getResources().getAssets().list("themeFolder");
			for(int i = 0; i < themeArray.length; i++){

				String config = FileSystem.getFromAssets(context, "themeFolder/"+themeArray[i]+"/config");
				ThemeInfo ti = new ThemeInfo();
//				ThemeInfo ti = GsonHelper.objectFromJson(config,
//						new TypeToken<ThemeInfo>(){}.getType());

				ti.version = JsonConfigsParser.getSimpleJson(config, "version");
				ti.SkinKey = JsonConfigsParser.getSimpleJson(config, "SkinKey");
				ti.SkinName = JsonConfigsParser.getSimpleJson(config, "SkinName");

				if(TextUtils.isEmpty(ti.SkinKey) || !themeArray[i].equals(ti.SkinKey)){
					Logger.d("主题换肤", themeArray[i] + "主题为非法主题！");
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

	public static String getJiaoYiSkin(Context context){
		String skin = FileSystem.getFromAssets(context, "themeFolder/"+getCurSkinType()+"/skin_css");
		if (TextUtils.isEmpty(skin)) {
			return "-1";
		}
		return skin;
	}
}

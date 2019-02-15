package com.romaway.android.phone.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.android.phone.R;
import com.romaway.commons.log.Logger;

/**
 * 语言设置帮助类
 * 
 * @author chenjp
 * 
 */
public class LanguageUtils {

	private static final String DATA_LANGUAGE_SETTING = "DATA_LANGUAGE_SETTING";
	private static final String CURRENT_LANGUAGE = "CURRENT_LANGUAGE";
	private Context mContext;
	private Configuration config;// 语言设置
	private DisplayMetrics dm;
	private static List<LanguageChangeListener> list = new ArrayList<LanguageUtils.LanguageChangeListener>();
	
	private static LanguageUtils mLanguageUtils = new LanguageUtils();
	public LanguageUtils(Context context) {
		mContext = context;
		config = mContext.getResources().getConfiguration();
		dm = mContext.getResources().getDisplayMetrics();
	}
	private LanguageUtils(){
		
	}
	public static LanguageUtils getInstance(){
		if(null!=mLanguageUtils){
			return mLanguageUtils;
		}else {
			return mLanguageUtils = new LanguageUtils();
		}
	}
	public void initLanguage(){
		String language = SharedPreferenceUtils.getPreference(
				DATA_LANGUAGE_SETTING, CURRENT_LANGUAGE, "");
		if (language != null) {
			if (language.equals("CN")) {
				config.locale =  Locale.SIMPLIFIED_CHINESE;
			} else if (language.equals("TW")) {
				config.locale =  Locale.TRADITIONAL_CHINESE;
			} else {
				config.locale =  Locale.getDefault();
			}
		}else {
			config.locale =Locale.getDefault();
		}
		mContext.getResources().updateConfiguration(config, dm);
	}
		
	/**
	 * 获取当前语言
	 * @return
	 */
	public static Locale getCurLanguage() {
		String language = SharedPreferenceUtils.getPreference(
				DATA_LANGUAGE_SETTING, CURRENT_LANGUAGE, "");
		if (language != null) {
			if (language.equals("CN")) {
				return Locale.SIMPLIFIED_CHINESE;
			} else if (language.equals("TW")) {
				return Locale.TRADITIONAL_CHINESE;
			} else {
				return Locale.getDefault();
			}
		} else {
			return Locale.getDefault();
		}
	}

	/**
	 * 
	 * @param locale
	 *            Locale.SIMPLIFIED_CHINESE--简体中文
	 *            Locale.TRADITIONAL_CHINESE--繁体中文
	 */
	public void setLanguage(Locale locale) {
		config.locale = locale;
		mContext.getResources().updateConfiguration(config, dm);
		Logger.d("TAG","-----------------语言改变---"+ Res.getString(R.string.roma_init_phone_check_title));
		saveSetting(locale);
		for(int i=0;i<list.size();i++){
			list.get(i).onLanguageChange();
		}
	}
	
	/**
	 * 保存设置
	 * 
	 * @param locale
	 */
	private void saveSetting(Locale locale) {
		if (locale == Locale.SIMPLIFIED_CHINESE) {// 简体中文
			SharedPreferenceUtils.setPreference(DATA_LANGUAGE_SETTING,
					CURRENT_LANGUAGE, "CN");
		} else if (locale == Locale.TRADITIONAL_CHINESE) {// 繁体中文
			SharedPreferenceUtils.setPreference(DATA_LANGUAGE_SETTING,
					CURRENT_LANGUAGE, "TW");
		}
	}
	public void addLanguageChangeListener(LanguageChangeListener listener){
			if(listener!=null){
				list.add(listener);
			}
	}
	public void removeLanguageChangeListener(LanguageChangeListener listener){
		if(listener!=null){
			list.remove(listener);
		}
	}
	public interface LanguageChangeListener{
		void onLanguageChange();
	}
}

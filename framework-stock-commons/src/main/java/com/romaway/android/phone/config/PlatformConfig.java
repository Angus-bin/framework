package com.romaway.android.phone.config;

import roma.romaway.commons.android.theme.SkinManager;

import com.romaway.common.android.base.Res;
import com.romaway.android.phone.R;

/**
 * 平台标记
 * 
 * @author lh
 * 
 */
public class PlatformConfig {

	/** 东莞证券 */
	public static final int DONG_GUAN = 1;

	/** 国元证券 */
	public static final int GUO_YUAN = 2;

	/**
	 * 获取当前的平台编号
	 * 
	 * @return
	 */
	public static int getCurrentPlatform(){
		return Res.getInteger(R.integer.platform_version);
	}

	/**
	 * 当前是否是指定参数的平台
	 * 
	 * @param platform
	 *            平台编号
	 * @return
	 */
	public static boolean isAppointedPlatform(int platform){
		return platform == getCurrentPlatform();
	}

	/** 是否显示K线菜单移动滑块*/
	public static boolean isShowIndicator(){
		return getCurrentPlatform() == GUO_YUAN && SkinManager.getCurSkinType().equals(SkinManager.SKIN_ORANGE);
	}
	/** 针对橙色皮肤Title字体 */
	public static boolean isSkinType(){
		return SkinManager.getCurSkinType().equals(SkinManager.SKIN_ORANGE);
	}

}

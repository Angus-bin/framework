package com.romalibs.init;

//import com.iflytek.cloud.SpeechUtility;
import com.romalibs.utils.Res;
import com.romawaylibs.theme.ROMA_SkinManager;

import android.content.Context;

/**
 * @deprecated
 * 过时初始化类，最新初始化类 CommonUiInit.java
 */
public class RomaLibsInit {

	public static void init(Context context){
		Res.setContext(context);
		// 注册科大讯飞语音合成SDK 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误
		//SpeechUtility.createUtility(context, "appid=" + "5716f793");

		// 初始化默认主题
		ROMA_SkinManager.initSkin(context);
	}
}

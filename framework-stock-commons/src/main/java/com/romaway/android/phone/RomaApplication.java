/**
 * 
 */
package com.romaway.android.phone;

import java.io.File;

import roma.romaway.commons.android.config.ConfigInfo;
import roma.romaway.commons.android.config.ConfigsManager;
import roma.romaway.commons.android.config.OtherPageConfigsManager;
import roma.romaway.commons.android.theme.SkinManager;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.view.Theme;
import com.romaway.android.phone.config.KConfigs;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.android.phone.utils.AuthServerInfoUtils;
import com.romaway.android.phone.utils.CommonUtils;
import com.romaway.android.phone.utils.DataCleanManager;
import com.romaway.android.phone.utils.LanguageUtils;
import com.romaway.android.phone.view.KLineTheme;
import com.romaway.android.phone.view.MinuteViewTheme;
import com.romaway.android.phone.viewcontrol.UserLoginControl;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.common.android.netstatus.content.NetStatusBroadcastReceiver;
import com.romaway.common.net.serverinfo.ServerInfoMgr;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.service.StockServices;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.CrashHandler;
import com.romaway.commons.log.Logger;
import com.romawaylibs.theme.ROMA_SkinManager;

/**
 * @author duminghui
 * 
 */
public class RomaApplication extends Application {

	private BroadcastReceiver connectionBroadcastReceiver = new NetStatusBroadcastReceiver();

	@Override
	public void onCreate() {
		super.onCreate();

		//初始化网络框架
		StockServices.init(this, true);
		
		Res.setContext(this);

		// 初始化debug模式
		initDebugMode(this);

		// 设置覆盖安装标志
		setCoverInstallFlag();
		
		// 老版本升级到新版本的处理
		oldVerToNewVersion(this);

		// 从配置文件中初始化认证地址
		AuthServerInfoUtils.getServerInfoFromConfig();
		
		//语言初始化
		LanguageUtils languageUtils = new LanguageUtils(this);
		languageUtils.initLanguage();
		// 初始化皮肤主题
		SkinManager.instance(this, Res.getString(R.string.kconfigs_defaultSkin));
		// 初始化新皮肤主题设置
		ROMA_SkinManager.initSkin(this, Res.getString(R.string.kconfigs_defaultSkin));
		// 强制设置皮肤主题为黑色皮肤:
//		int forceSettingCount = SharedPreferenceUtils.getPreference("SkinConfig","FORCE_SETTING_NIGHT_SKIN", 0);
//		if(forceSettingCount == 0){
//			ROMA_SkinManager.setCurSkinType(Res.getString(R.string.kconfigs_defaultSkin));
//			SharedPreferenceUtils.setPreference("SkinConfig", "FORCE_SETTING_NIGHT_SKIN", ++forceSettingCount);
//		}

		Theme.init();
		SysConfigs.init(this);
		KLineTheme.initKLTheme();
		MinuteViewTheme.initTheme();
		KConfigs.init();
		
		//初始化可配置文件
		initPanelConfig();

		IntentFilter intentFilter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(connectionBroadcastReceiver, intentFilter);
//		LogcatHelper.getInstance(this).start();//开始logcat日志保存

		// 第三方统计初始化:
		// RomaAgentMgr.init(this, Res.getString(R.string.umeng_app_key), Res.getString(R.string.umeng_channel_Id));
		// RomaAgentMgr.openActivityDurationTrack(false);
        // RomaAgentMgr.setDebugMode(Logger.getDebugMode());
		// RomaAgentMgr.setCatchUncaughtExceptions(Logger.getDebugMode());
	}

	@SuppressLint("NewApi")
	private void initDebugMode(Context context) {
		int flags = context.getApplicationInfo().flags;

		boolean isDebugMode = ((flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);

		if (isDebugMode) {
			System.out.println("[启用版本类型]：debug版本");
			Logger.setDebugMode(true);
			SharedPreferenceUtils.setPreference(
					SharedPreferenceUtils.DATA_CONFIG, "APP_DEBUG_MODE", true);
			// 添加获取Android签名, 会打印至Log日志, 请前往Copy:
			CommonUtils.getSignKey(this, this.getPackageName());
		} else {
			if (SharedPreferenceUtils.getPreference(
					SharedPreferenceUtils.DATA_CONFIG, "APP_DEBUG_MODE", false)) {
				System.out.println("[启用版本类型]：debug版本");
				Logger.setDebugMode(true);
			} else {
				System.out.println("[启用版本类型]：上线版本");
				Logger.setDebugMode(false);
			}
		}
		// 保存崩溃日志
		CrashHandler.getInstance().init(context.getApplicationContext(),
				Res.getString(R.string.app_name),
				Res.getString(R.string.kds_inner_version_show),
				Res.getString(R.string.cpid), Res.getString(R.string.appid),
				Res.getString(R.string.config_apptype));
	}

	/**
	 * 设置启动相对上一次是覆盖安装的标志
	 */
	private void setCoverInstallFlag(){
		String currentInnerVersionNumber = RomaSysConfig.getClientInnerVersion();
		String innerVersionNumber = SharedPreferenceUtils.getPreference(
				UserLoginControl.pName, UserLoginControl.keyInnerVersionNumber, "0");
		//上一次保存的内部版本号比当前安装的内部版本号 要小 说明是覆盖安装了
		if(innerVersionNumber.compareTo(currentInnerVersionNumber) < 0)
			RomaSysConfig.coverInstallFlag = true;
		else
			RomaSysConfig.coverInstallFlag = false;
	}
	
	private void oldVerToNewVersion(Context context) {
		
		boolean firstAppFlag = SharedPreferenceUtils.getPreference(
				UserLoginControl.pName, UserLoginControl.keyFirstAppFlagName, true);
		Logger.d("老版升级新版", "是否是老版升级新版---- firstAppFlag:" + firstAppFlag);
		
		if(firstAppFlag){
			String user;
			String passwd;
			String ptjyAcount;
			String[] ptjyAcouontList;
			String rzrqAcount;
			String[] rzrqAcountList;
			
			//世纪老版的手机号的获取,如果user不为空，说明是老版本，如果为空，说明可以直接覆盖或者是高版本覆盖低版本，直接走if方法
			user = SharedPreferenceUtils.getPreference("mf_system_data", "sys_key_phone_num","");
			if("".equals(user)){							
				//高版本覆盖低版本
				user = SharedPreferenceUtils.getPreference(
						UserLoginControl.pName, UserLoginControl.keyName, "");
				passwd = SharedPreferenceUtils.getPreference(
						UserLoginControl.pName, UserLoginControl.keyPwd, "");
				
				rzrqAcount = UserAccount.getLatistRZRQAccount();
				rzrqAcountList = UserAccount.get_rzrq_user_login_accounts();
				ptjyAcount = UserAccount.getLatistAccount();
				ptjyAcouontList = UserAccount.get_user_login_accounts();
				Logger.d("oldVerToNewVersion", "===rzrqAcount:" + rzrqAcount + "   ptjyAcount:" + ptjyAcount);
				
				query(context);
			}else{//目前是针对世纪的	
				//新版覆盖老板
				//世纪老版的手机号，普通账号，信用账号的获取
				//String user = SharedPreferenceUtils.getPreference("mf_system_data", "PREF_SYS_KEY_LOGINED_PHONE","");
				passwd = SharedPreferenceUtils.getPreference("mf_system_data", "sys_key_phone_password","");
				ptjyAcount = UserAccount.get_old_shiji_LatistAccount();
				ptjyAcouontList = UserAccount.get_old_shiji_user_login_accounts();
				rzrqAcount = UserAccount.get_old_shiji_LatistRZRQAccount();
				rzrqAcountList = UserAccount.get_rzrq_old_shiji_user_login_accounts();
				
				Logger.d("oldVerToNewVersion", "user:" + user + "   passwd:" 
				+ passwd+ "   ptjyAcount:" + ptjyAcount+ "   rzrqAcount:" + rzrqAcount);
				//获取自选股
				String UserStockCodes=SharedPreferenceUtils.getPreference("mf_user_data", "user_key_mycodes","999999,399001");
				RomaSysConfig.oldVersionUserStockCode=TextUtils.split(UserStockCodes, ",");
			}
			
			DataCleanManager.cleanInternalCache(context);
			DataCleanManager.cleanDatabases(context);
			DataCleanManager.cleanSharedPreference(context);
			
			SharedPreferenceUtils.setPreference(
					UserLoginControl.pName, UserLoginControl.keyFirstAppFlagName, false);
			if(!StringUtils.isEmpty(user)){
				RomaUserAccount.setOldUsername(user);
				RomaUserAccount.setOldUserPwd(passwd);
			}
			if (!StringUtils.isEmpty(rzrqAcount) || (rzrqAcountList != null && rzrqAcountList.length > 0) ) {
				RomaUserAccount.setOldRZRQAcount(rzrqAcount);
				RomaUserAccount.setOldRZRQAcountList(rzrqAcountList);
			}
			if (!StringUtils.isEmpty(ptjyAcount) || (ptjyAcouontList != null && ptjyAcouontList.length > 0)) {
				RomaUserAccount.setOldPTJYAcount(ptjyAcount);
				RomaUserAccount.setOldPTJYAcountList(ptjyAcouontList);
			}
		}
	}
	
	private void query(Context context) {

		final String dbName = "userstock_data";// 数据库名称
		final String SELECT_DATA = "select stockcode,market_id from " + dbName;// 查询方式

		final String filePath = "/data/data/" + context.getPackageName()
				+ "/databases/" + dbName;// 数据库全路径
		File jhPath = new File(filePath);
		Logger.d("老版升级新版", "数据库路径：" + filePath + ",是否存在数据库：" + jhPath.exists());
		// 查看数据库文件是否存在
		if (!jhPath.exists())
			return;
		// 存在则直接返回打开的数据库
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(jhPath, null);

		String[][] s = null;
		Cursor cursor = db.rawQuery(SELECT_DATA, null);

		try {
			if (cursor.getCount() > 0) {
				s = new String[cursor.getCount()][2];
				RomaSysConfig.oldVersionUserStockCode = new String[cursor
						.getCount()];
				for (int i = 0; cursor.moveToNext(); i++) {
					s[i][0] = cursor.getString(0);// stockCode
					s[i][1] = cursor.getString(1);// marketId

					RomaSysConfig.oldVersionUserStockCode[i] = s[i][0];

					Logger.d("老版升级新版", "获取数据库数据：" + s[i][0]);
				}
			}
		} finally {
			cursor.close();
		}
	}
	
	/**
	 * 初始化可配置文件
	 */
	private void initPanelConfig(){
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
				ConfigInfo otherConfigInfo = new ConfigInfo();
				otherConfigInfo.downloadUrl = ServerInfoMgr.getInstance()
						.getDefaultServerInfo(ProtocolConstant.SERVER_FW_AUTH).getUrl()
						+ otherSub;
				otherConfigInfo.saveFolderName = "panelConfigFolder";
				otherConfigInfo.configFileName = "otherpage.json";
				otherConfigInfo.tempConfigFileName = "otherpage_temp.json";
				
				//如果是覆盖安装则删除之前旧的配置文件
				if(RomaSysConfig.coverInstallFlag){//删除动作一定要在初始化之前,因为在初始化的时候会确定采用哪个配置文件
					DataCleanManager.cleanFiles(this, otherConfigInfo.saveFolderName);
					DataCleanManager.cleanFiles(this, otherConfigInfo.saveFolderName+"/ueditor");
				}
				
				OtherPageConfigsManager.newInstance(this, otherConfigInfo);
	}
	
	/**
	 * 获取签名文件
	 */
	public void getSignature() {
		String pkgname = this.getPackageName();
		boolean isEmpty = TextUtils.isEmpty(pkgname);
		if (isEmpty) {
			Toast.makeText(this, "应用程序的包名不能为空！", Toast.LENGTH_SHORT);
		} else {
			try {
				/** 通过包管理器获得指定包名包含签名的包信息 **/
				PackageInfo packageInfo = getPackageManager().getPackageInfo(
						pkgname, PackageManager.GET_SIGNATURES);
				/******* 通过返回的包信息获得签名数组 *******/
				Signature[] signatures = packageInfo.signatures;

				StringBuilder builder = new StringBuilder();

				/******* 循环遍历签名数组拼接应用签名 *******/
				for (Signature signature : signatures) {
					builder.append(signature.toCharsString());
				}
				/************** 得到应用签名 **************/
				String signature = builder.toString();
				Logger.d("tag", "getSignature:" + signature);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}

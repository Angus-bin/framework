/**
 * 
 */
package com.romaway.android.phone.utils;

import android.text.TextUtils;

import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.android.phone.R;
import com.romaway.android.phone.SuperUserAdminActivity;
import com.romaway.android.phone.config.SysConfigs;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.net.serverinfo.ServerInfoMgr;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.commons.lang.StringUtils;

/**
 * 初始化服务器操作类 读取认证地址时，先根据配置文件判断是否使用测试认证地址，若有配置，则使用测试认证地址
 */
public class AuthServerInfoUtils {

	public static final void getServerInfoFromConfig() {

		// 使用测试认证地址
		String tmpUrl = Res.getString(R.string.roma_test_serverurl);
		if (!StringUtils.isEmpty(tmpUrl)) {
			ServerInfoMgr.getInstance().setIP(ProtocolConstant.SERVER_FW_AUTH,
					new ServerInfo(tmpUrl, ProtocolConstant.SERVER_FW_AUTH, "测试地址", tmpUrl, false, Res.getInteger(R.integer.roma_test_https_port)));
			return;
		}

		// 使用自定义修改认证地址:
		String tmpAuthUrl = SharedPreferenceUtils.getPreference(SharedPreferenceUtils.DATA_CONFIG, SuperUserAdminActivity.TEST_AUTH_ADDRESS, "");
		String tmpAuthUrlPort = SharedPreferenceUtils.getPreference(SharedPreferenceUtils.DATA_CONFIG, SuperUserAdminActivity.TEST_AUTH_ADDRESS_PORT, "");
		if (!TextUtils.isEmpty(tmpAuthUrl) && !TextUtils.isEmpty(tmpAuthUrlPort)) {
			ServerInfoMgr.getInstance().setIP(ProtocolConstant.SERVER_FW_AUTH,
					new ServerInfo(tmpAuthUrl, ProtocolConstant.SERVER_FW_AUTH, "自定义测试地址", tmpAuthUrl, false,
							Integer.parseInt(tmpAuthUrlPort)));
			// [Bug]修复修改自定义认证地址后第一次重启登录交易使用旧交易地址;
			JYStatusUtil.isChangePTJYUrl = true;
			JYStatusUtil.isChangeRZRQUrl = true;

			//[兼容需求]再次重启, 由Application触发执行至此, 那么已销毁过进程(兼容集成消息推送功能, 退出App时不销毁进程的情况)
			SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG, "AuthAddress_changed_killProcess", false);
			return;
		}

		String serverNames = SharedPreferenceUtils.getPreference(
				SysConfigs.DB_CONFIG_NAME, SysConfigs.KEY_LOGINSERVER_NAME, "");
		String serverAddrs = SharedPreferenceUtils.getPreference(
				SysConfigs.DB_CONFIG_NAME, SysConfigs.KEY_LOGINSERVER_ADDRESS,
				"");
		String serverHttsPort = SharedPreferenceUtils.getPreference(SysConfigs.DB_CONFIG_NAME, SysConfigs.KEY_LOGINSERVER_HTTPS_PORT, "");

		clear();

		if (StringUtils.isEmpty(serverAddrs)) {
			addToServerInfos(Res.getStringArray(R.array.defaultservernames),
					Res.getStringArray(R.array.defaultserveraddress), Res.getIngegerArray(R.array.defaulthttpsport));
		} else {
			addToServerInfos(serverNames.split(","), serverAddrs.split(","), serverHttsPort.split(","));

		}

	}

	public static final void clear() {
		ServerInfoMgr.getInstance().clearDefaultServerInfo(ProtocolConstant.SERVER_FW_AUTH);
	}

	/**
	 * 添加认证服务器到内存中
	 * 
	 * @param serverNams
	 *            服务器名称数组
	 * @param urls
	 *            地址数组
	 */
	private static final void addToServerInfos(String[] serverNams,
			String[] urls, String[] httpsPort) {
		for (int i = 0; i < urls.length; i++) {
			ServerInfo serverInfo = new ServerInfo(serverNams[i], ProtocolConstant.SERVER_FW_AUTH,
					serverNams[i], urls[i], false, Integer.parseInt(httpsPort[i]));
			ServerInfoMgr.getInstance().addServerInfo(serverInfo);
		}
	}
	private static final void addToServerInfos(String[] serverNams,
			String[] urls, int[] httpsPort) {
		for (int i = 0; i < urls.length; i++) {
			ServerInfo serverInfo = new ServerInfo(serverNams[i], ProtocolConstant.SERVER_FW_AUTH,
					serverNams[i], urls[i], false, httpsPort[i]);
			ServerInfoMgr.getInstance().addServerInfo(serverInfo);
		}
	}
}

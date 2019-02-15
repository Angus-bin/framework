package com.romaway.android.phone.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.romaway.android.phone.RomaUserAccount;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.android.phone.BundleKeyValue;
import com.romaway.android.phone.R;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.net.serverinfo.ServerInfoMgr;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.dl.InitProtocol;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.romawaylibs.theme.ROMA_SkinManager;

public class RomaSysConfig {
	
	/**
	 * 覆盖安装标志
	 */
	public static boolean coverInstallFlag = false;
	
	/**行情刷新事时间，毫秒*/
	public static int hqRefreshTimes;
	
	/**交易登录超时间 秒*/
	public static int jyOutTime = 10;

	public static boolean isChecked;

	
	/**
	 * 相对上次启动是否有可配置更新
	 */
	public static boolean hasUpdateConfig = false;
	
	public static final String pName = "user_data";
	public static final String chengJiaoHuiBaoName = "chengJiaoHuiBaoName_data";
	public static final String dingYueTingName = "dingYueTingName_data";

	/*推送*/
	public static String PUSH_TYPE="push_type";
	public static String PUSH_WARNING="预警提醒";
	public static String PUSH_NOTICE="系统公告";
	public static String yuJingName;
	public static String systemNoticeName;
	public static final String NAME_NT_YD_INFO_IDS = "name_nt_info_counts_ids";
	public static final String NAME_YJ_YD_INFO_IDS = "name_yj_info_counts_ids";
	public static final String NAME_TS_ALIAS= "name_ts_alias";
	public static final String NAME_YJ_IS_FIRST_PUSH_SETTING= "is_first_push_setting";
	public static final String NAME_YJ_IS_USER_PUSH= "is_user_push";
	public static final String NAME_YJ_IS_USER_NODISTURB= "isUserNoDisturb";
	public static final String KDS_MESSAGE_CENTER="kds_message_center";
	public static final String KDS_MESSAGE_TITLE="kds_message_title";
	public static final String KDS_MESSAGE_TIME="kds_message_time";
	public static final String KDS_MESSAGE_CONTENT="kds_message_content";
	/**
	 * 手机号码同步：1
	 */
	public static int USER_STOCK_SYN_PHONENUM = 1;
	/**
	 * 资金账号同步：2
	 */
	public static int USER_STOCK_ZJZH = 2;
	/**
	 * 是否是节假日
	 */
	public static boolean isHoliday = false;
	/**
	 * 自选股同步类型 1：‘手机号码';2: '资金账号/客户号'
	 */
	public static int userStockSyncType = 1;
	/**
	 * 自选股同步所属APP的ID
	 */
	public static int userStockAppsID;
	// 升级信息
	/**
	 * 客户端名称
	 */
	public static String upgradeClientName_release;
	/**
	 * 客户端代码 Iphone：66063; Android: 66065
	 */
	public static String upgradeClientCode_release;
	/**
	 * 升级方式 1：自动; 2:手动
	 */
	public static String upgradeCategory_release;
	/**
	 * 升级模式 1：提示升级; 2:强制升级; 3:不升级
	 */
	public static String upgradeMode_release;
	/**
	 * 下载地址
	 */
	public static String upgradeDownloadUrl_release;
	/**
	 * 版本号
	 */
	public static String upgradeVersion_release;
	/**
	 * 升级提示信息
	 */
	public static String upgradeMsg_release;
	//测试版升级信息
	/**
	 * 客户端名称
	 */
	public static String upgradeClientName_beta;
	/**
	 * 客户端代码 Iphone：66063; Android: 66065
	 */
	public static String upgradeClientCode_beta;
	/**
	 * 升级方式 1：自动; 2:手动
	 */
	public static String upgradeCategory_beta;
	/**
	 * 升级模式 1：提示升级; 2:强制升级; 3:不升级
	 */
	public static String upgradeMode_beta;
	/**
	 * 下载地址
	 */
	public static String upgradeDownloadUrl_beta;
	/**
	 * 版本号
	 */
	public static String upgradeVersion_beta;
	/**
	 * 升级提示信息
	 */
	public static String upgradeMsg_beta;
	// 公告信息
	public static int noticeCount;
	/**
	 * 公告内容
	 */
	public static String[] noticeContent;
	/**
	 * 优先级别 1:高, 2:中, 3:低
	 */
	public static String[] noticePriority;
	/**
	 * 有效时间
	 */
	public static String[] noticeValidTime;
	/**
	 * 公告类型 A:紧急公告, B:上交所公告, C:深交所公告, D:其它公告
	 */
	public static String[] noticeType;
	/**
	 * 公告标题
	 */
	public static String[] noticeTitle;
	/**
	 * 券商ID
	 */
	public static String[] noticeCPID;

	// 默认自选股
	/**
	 * 股票代码
	 */
	public static String[] defaultStockCode;
	/**
	 * 所属APP的ID
	 */
	public static String[] defaultStockAppsID;
	/**
	 * 商品代码
	 */
	public static String[] defaultMarketCode;
	/**
	 * 分组名称
	 */
	public static String[] defaultGroup;
	// 客户端版本号
	/**
	 * 测试版版本号
	 */
	public static String betaVersionNo;
	/**
	 * 正式版版本号
	 */
	public static String onlineVersionNo;
	/**
	 * 速率负载比
	 */
	public static String sitePrioritySelectScale = "0.4";
	/**
	 * 最小负负载
	 */
	public static int minSiteLoad = 0;
	/**
	 * 最大负载
	 */
	public static int maxSiteLoad = 90;
	/**
	 * 计算比较的站点个数
	 */
	public static int maxEnableStation = 3;
	/**
	 * 服务热线
	 */
	public static String serviceHotline = "";
	/**
	 * 微信账号
	 */
	public static String wechatCode;

	/**
	 * h5 版本号
	 */

	public static String h5_versionNum_release;

	/**
	 * h5 版本文件名
	 */

	public static String h5_fileName_release;

	/**
	 * h5 版本文件大小 kb
	 */

	public static int h5_fileSize_release;

	/**
	 * h5 下载地址
	 */

	public static String h5_downloadAddr_release;
	
	/**
	 * h5 下载地址 h5 升级方式app检测版本更新：1、提示升级；2、强制升级; 3、静默升级;
	 */

	public static int h5_upgradeCategory_release = 1;
	
	
	/**
	 * h5 下载地址
	 */

	public static int h5_pushCategory_release;
	
	/**
	 * h5 下载地址
	 */

	public static String h5_upgradeMsg_release = "";
	
	/**
	 * h5 版本号 --测试版
	 */

	public static String h5_versionNum_beta;

	/**
	 * h5 版本文件名--测试版
	 */

	public static String h5_fileName_beta;

	/**
	 * h5 版本文件大小 kb--测试版
	 */

	public static int h5_fileSize_beta;

	/**
	 * h5 下载地址--测试版	
	 */

	public static String h5_downloadAddr_beta;

	/**
	 * h5 下载地址 h5 升级方式app检测版本更新：1、提示升级；2、强制升级
	 */

	public static int h5_upgradeCategory_beta = 1;
	
	
	/**
	 * h5 下载地址
	 */

	public static int h5_pushCategory_beta;
	
	/**
	 * h5 下载地址
	 */

	public static String h5_upgradeMsg_beta = "";
	
	/**
	 * h5 状态,2为测试，3为正式
	 */

	public static int h5_pulishStatus;

	/** 老版本的自选股代码 */
	public static String[] oldVersionUserStockCode;
	
	/**
	 * 广告数量
	 */
	public static int advtCount;
	
	/**
	 * 广告标题
	 */
	public static String[] advtTitle;
	/**
	 * 广告位置
	 */
	public static String[] advtPosition;
	/**
	 * 广告类型
	 */
	public static int[] advtType;
	/**
	 * 广告文字内容
	 */
	public static String[] advtContent;
	/**
	 * 广告图片地址
	 */
	public static String[] advtPicUrl;
	/**
	 * 广告图片地址
	 */
	public static String[] advtPicUrl_home;
	/**
	 * 交易广告图片地址
	 */
	public static String[] advtPicUrl_trade;
	/**
	 * 交易广告图片链接
	 */
	public static String[] advtLinked_trade;
	/**
	 * 广告链接
	 */
	public static String[] advtLinked;
	/**
	 * 广告备注信息
	 */
	public static String[] advtMemo;
	
	/**
	 * 广告是否需要原生提供title
	 */
	public static String[] advSrcTitleVisibility;

	/**
	 * 标记广告的登录方式
	 */
	public static String[] advWebViewLoginFlag;
	/**
	 * 广告结束时间
	 */
	public static String[] advEndTime;
	/**
	 *  广告开始时间
	 */
	public static String[] advStartTime;
	
	/**
	 * 自定义参数值
	 */
	public static String[] resp_paramsValue;
	
	/**
	 * 自定义参数名
	 */
	public static String[] resp_paramsName;
	
	/**
	 * 自定义参数ID
	 */
	public static int[] resp_paramsId;
	
	/**
	 * 营业部信息
	 */
	public static String resp_deptinfos;
	
	/**
	 * 保存资金账号密码在内存中，内存中的该数据是经过RSA加密过的
	 */
	public static String onInterfaceA_loginPassword;
	/**
	 * 登录后的账号
	 */
	public static String onInterfaceA_loginAccount;
	
	/**
	 * H5的升级
	 * 1：提示升级 2：强制升级 3: 静默升级
	 */
	public static int h5_upgradeCategory = 2;
	
	/**
	 * H5的升级内容
	 */
	public static String h5_upgradeMsg="";

	/**
	 * 推送消息第一条ID
	 */
	public static String push_oneMsgTime = "";

	/**
	 * 消息未读数
	 */
	public static int push_NoReadMsgs;

	/**
	 * 微信返回码
	 */
	public static String WXcode;

	public static boolean isBookGoldStock;

	public static boolean isBookLimitUpStock;

	public static boolean isBookwenjian;

	public static String deviceType;

	public static String appVersion;

	public static String upDataUrl;

	public static String jpgpc_id;

	public static String ztdj_id;

	public static String menu_json;

	public static boolean isBookGoldStock() {
		return SharedPreferenceUtils.getPreference("isBookGoldStock", "isBookGoldStock", false);
	}

	public static void setIsBookGoldStock(boolean isBookGoldStock) {
		SharedPreferenceUtils.setPreference("isBookGoldStock", "isBookGoldStock", isBookGoldStock);
	}

	public static boolean isBookAIStock() {
		return SharedPreferenceUtils.getPreference("isBookAIStock", "isBookAIStock", false);
	}

	public static void setIsBookAIStock(boolean isBookAIStock) {
		SharedPreferenceUtils.setPreference("isBookAIStock", "isBookAIStock", isBookAIStock);
	}

	public static boolean isBookLimitUpStock() {
		return SharedPreferenceUtils.getPreference("isBookLimitUpStock", "isBookLimitUpStock", false);
	}

	public static void setIsBookLimitUpStock(boolean isBookLimitUpStock) {
		SharedPreferenceUtils.setPreference("isBookLimitUpStock", "isBookLimitUpStock", isBookLimitUpStock);
	}

	public static boolean isBookwenjian() {
		return SharedPreferenceUtils.getPreference("isBookwenjian", "isBookwenjian", false);
	}

	public static void setIsBookwenjian(boolean isBookwenjian) {
		SharedPreferenceUtils.setPreference("isBookwenjian", "isBookwenjian", isBookwenjian);
	}

	public static String getPush_oneMsgTime() {
		return SharedPreferenceUtils.getPreference("push_oneMsgTime", "push_oneMsgTime", "");
	}

	public static void setPush_oneMsgTime(String push_oneMsgTime) {
		SharedPreferenceUtils.setPreference("push_oneMsgTime", "push_oneMsgTime", push_oneMsgTime);
	}

	public static String getWXcode() {
		return SharedPreferenceUtils.getPreference("WXcode", "WXcode", "");
	}

	public static void setWXcode(String WXcode) {
		SharedPreferenceUtils.setPreference("WXcode", "WXcode", WXcode);
	}

	public static String getAccessToken() {
		return SharedPreferenceUtils.getPreference("AccessToken", "AccessToken", "");
	}

	public static void setAccessToken(String AccessToken) {
		SharedPreferenceUtils.setPreference("AccessToken", "AccessToken", AccessToken);
	}

	public static String getExpiresIn() {
		return SharedPreferenceUtils.getPreference("ExpiresIn", "ExpiresIn", "");
	}

	public static void setExpiresIn(String ExpiresIn) {
		SharedPreferenceUtils.setPreference("ExpiresIn", "ExpiresIn", ExpiresIn);
	}

	public static String getRefreshToken() {
		return SharedPreferenceUtils.getPreference("RefreshToken", "RefreshToken", "");
	}

	public static void setRefreshToken(String RefreshToken) {
		SharedPreferenceUtils.setPreference("RefreshToken", "RefreshToken", RefreshToken);
	}

	public static String getOpenId() {
		return SharedPreferenceUtils.getPreference("OpenId", "OpenId", "");
	}

	public static void setOpenId(String OpenId) {
		SharedPreferenceUtils.setPreference("OpenId", "OpenId", OpenId);
	}

	public static String getScope() {
		return SharedPreferenceUtils.getPreference("Scope", "Scope", "");
	}

	public static void setScope(String Scope) {
		SharedPreferenceUtils.setPreference("Scope", "Scope", Scope);
	}

	public static String getUnionId() {
		return SharedPreferenceUtils.getPreference("UnionId", "UnionId", "");
	}

	public static void setUnionId(String UnionId) {
		SharedPreferenceUtils.setPreference("UnionId", "UnionId", UnionId);
	}

	public static void setUserImageUrl(String imgUrl) {
		SharedPreferenceUtils.setPreference("imgUrl", "imgUrl", imgUrl);
	}

	public static String getUserImageUrl() {
		return SharedPreferenceUtils.getPreference("imgUrl", "imgUrl", "");
	}

	public static void setWxImageUrl(String imgUrl) {
		SharedPreferenceUtils.setPreference("WximgUrl", "imgUrl", imgUrl);
	}

	public static String getWxImageUrl() {
		return SharedPreferenceUtils.getPreference("WximgUrl", "imgUrl", "");
	}

	public static void setXSImageUrl(String imgUrl) {
		SharedPreferenceUtils.setPreference("XSImageUrl", "imgUrl", imgUrl);
	}

	public static String getXSImageUrl() {
		return SharedPreferenceUtils.getPreference("XSImageUrl", "imgUrl", "");
	}

	public static void setShouldToBindPhone(boolean ToBindPhone) {
		SharedPreferenceUtils.setPreference("ToBindPhone", "ToBindPhone", ToBindPhone);
	}

	public static boolean getShouldToBindPhone() {
		return SharedPreferenceUtils.getPreference("ToBindPhone", "ToBindPhone", false);
	}

	public static void setShareUserID(String userId) {
		SharedPreferenceUtils.setPreference("ShareUserID", "userId", userId);
	}

	public static String getShareUserID() {
		return SharedPreferenceUtils.getPreference("ShareUserID", "userId", "");
	}

	public static void setLoginActivity(boolean isLogin) {
		SharedPreferenceUtils.setPreference("isLogin", "isLogin", isLogin);
	}

	public static boolean getLoginActivity() {
		return SharedPreferenceUtils.getPreference("isLogin", "isLogin", false);
	}

	public static void setHistorySum(String sum) {
		SharedPreferenceUtils.setPreference("history", "sum", sum);
	}

	public static String getHistorySum() {
		return SharedPreferenceUtils.getPreference("history", "sum", "");
	}

	public static void setHistoryDataMonth(String dataMonth) {
		SharedPreferenceUtils.setPreference("history", "dataMonth", dataMonth);
	}

	public static String getHistoryDataMonth() {
		return SharedPreferenceUtils.getPreference("history", "dataMonth", "");
	}

	public static void setStartTime(String startTime) {
		SharedPreferenceUtils.setPreference("Time", "startTime", startTime);
	}

	public static String getStartTime() {
		return SharedPreferenceUtils.getPreference("Time", "startTime", "");
	}

	public static void setEndTime(String endTime) {
		SharedPreferenceUtils.setPreference("Time", "endTime", endTime);
	}

	public static String getEndTime() {
		return SharedPreferenceUtils.getPreference("Time", "endTime", "");
	}

	/**
	 * 保存String数据集
	 * 
	 */
	public static void setStringSet(String dateFile,Set<String> set,String setKey){
		SharedPreferenceUtils.setPreference(dateFile, setKey, set);;
	}
	/**
	 *获取String数据集
	 * 
	 */
	public static Set<String>  GetStringSet(String dateFile,String setKey){
		return SharedPreferenceUtils.getPreference(dateFile, setKey);
	}

	/**
	 *获取广告路径
	 * @param name
	 */
	public static String  getIninAdv_url(String name){
		return SharedPreferenceUtils.getPreference(pName, name, "");
	}
	/**
	 *获取广告开始时间
	 * @param name
	 */
	public static String  getIninAdv_starttime(String name){
		return SharedPreferenceUtils.getPreference(pName, name, "");
	}
	/**
	 *获取广告结束时间
	 * @param name
	 */
	public static String  getIninAdv_endtime(String name){
		return SharedPreferenceUtils.getPreference(pName, name, "");
	}
	/**
	 *清空数据
	 * @param dateType
	 */
	public static void clearDates(String dateType){
		SharedPreferenceUtils.clearDates(dateType);
	}
	
	/**
	 *不同用户名不同存储路径，针对预警推送
	 */
	public static void setWarningDatas(){
		yuJingName="yuJingName_data"+ RomaUserAccount.getUsername();
		systemNoticeName="systemNoticeName_data"+ RomaUserAccount.getUsername();
	}

	public static void initData(InitProtocol ptl) {
		//不同用户名不同存储路径，针对预警推送
		setWarningDatas();

		userStockSyncType = ptl.resp_sync_type;
		userStockAppsID = ptl.resp_sync_apps_id;
		// 保存是否是节假日

		if (!StringUtils.isEmpty(ptl.resp_is_holiday))
			isHoliday = ptl.resp_is_holiday.equals("Y") ? true : false;

		SysConfigs.setHoliday(isHoliday);
		initRefreshTime();
	}

	public static void initRefreshTime(){
		String refreshTimeName = Res.getString(R.string.pName_refresh_time);
		String hq_refresh_key = Res.getString(R.string.key_hq_refresh_time);
		String jy_outtime_key = Res.getString(R.string.key_jy_outtime);

		hqRefreshTimes = SharedPreferenceUtils
				.getPreference(refreshTimeName, hq_refresh_key,
						StringUtils.stringToInt(
								RomaSysConfig.getParamsValue("hqDefaultRefreshTimes"),
								Res.getInteger(R.integer.hqDefaultRefreshTimes)));
		jyOutTime = SharedPreferenceUtils.getPreference(refreshTimeName, jy_outtime_key,
				Res.getInteger(R.integer.jyDefaultOutTimes));
	}
	
	public static int getJyOutTime() {
		return jyOutTime * 60 * 1000;
	}

	/** 设置行情刷新时间，并保存数据 */
	public static void setHqRefreshTimes(int time) {
		hqRefreshTimes = time;
		SharedPreferenceUtils.setPreference(
				Res.getString(R.string.pName_refresh_time), 
				Res.getString(R.string.key_hq_refresh_time), null);
		SharedPreferenceUtils.setPreference(
				Res.getString(R.string.pName_refresh_time), 
				Res.getString(R.string.key_hq_refresh_time),
				hqRefreshTimes);
	}

	/** 设置交易超时时间，并保存数据 */
	public static void setJyOutTimes(int time) {
		jyOutTime = time;
		SharedPreferenceUtils.setPreference(
				Res.getString(R.string.pName_refresh_time), 
				Res.getString(R.string.key_jy_outtime), null);
		SharedPreferenceUtils.setPreference(
				Res.getString(R.string.pName_refresh_time), 
				Res.getString(R.string.key_jy_outtime), jyOutTime);
	}

    public static boolean getIsChecked() {
        isChecked = SharedPreferenceUtils.getPreference(Res.getString(R.string.key_sc_principal_stock)
                , Res.getString(R.string.key_sc_stock), true);
        return isChecked;
    }

	/** 是否已开启夜间模式 */
	public static boolean isOpenNightMode() {
		return "NIGHT_MODE".equalsIgnoreCase(ROMA_SkinManager.getCurSkinType());
	}

    /** 设置收藏委托股票是否打开，并保存数据 */
	public static void setSCStock(boolean checked) {
		isChecked = checked;
		SharedPreferenceUtils.setPreference(
				Res.getString(R.string.key_sc_principal_stock),
				Res.getString(R.string.key_sc_stock), null);
		SharedPreferenceUtils.setPreference(
				Res.getString(R.string.key_sc_principal_stock),
				Res.getString(R.string.key_sc_stock), isChecked);
	}

	
	/**
	 * 是否是节假日
	 * 
	 * @return
	 */
	public static boolean isHoliday() {
		return isHoliday;
	}

	/**
	 * 获取自选股同步类型
	 * 
	 * @return
	 */
	public static int getUserStockSyncType() {
		return userStockSyncType;
	}

	/**
	 * 获取自选股同步所属APP的ID
	 * 
	 * @return
	 */
	public static int getUserStockAppsID() {
		return userStockAppsID;
	}

	/*
	 * 获取客户端版本号
	 */
	public static String getClientVersion(Context context) {
		String version = Res.getString(R.string.roma_app_version);
		return version;
	}
	
	/*
	 * 获取客户端内部版本号
	 */
	public static String getClientInnerVersion() {
		String version = "1";
		return version;
	}

	/**
	 * 获取站点速率负载比
	 * 
	 * @return
	 */
	public static String getSitePrioritySelectScale() {
		return SharedPreferenceUtils.getPreference(pName, "speed_load_rate",
				sitePrioritySelectScale);
	}

	/**
	 * 获取最小站点负载
	 * 
	 * @return
	 */
	public static int getMinSiteLoad() {
		return SharedPreferenceUtils.getPreference(pName,
				"station_load_range_min", 0);
	}

	/**
	 * 获取最大站点负载
	 * 
	 * @return
	 */
	public static int getMaxSiteLoad() {
		return SharedPreferenceUtils.getPreference(pName, "station_load_range",
				90);
	}

	/**
	 * 获取比较的站点个数
	 * 
	 * @return
	 */
	public static int getMaxEnableStation() {
		return SharedPreferenceUtils.getPreference(pName, "station_count", 3);
	}

	private static Map<String, JSONObject> cacheJSONObjectMap = new HashMap<String, JSONObject>();
   public static List<Map<String, String>> getInitJsonInfo(String panel, String[] key){
	   String jsonConfigStr = InitProtocol.respJsonContent;
	   if(StringUtils.isEmpty(jsonConfigStr))
        	return null;
        
        //获取配制文件版本时间
        //String version = getConfigVersion(jsonConfigStr);
        List<Map<String, String>> list = null;
        try { 
            list = new ArrayList<Map<String, String>>();
            
            //缓存机制，不会每次都会去new 因为比较耗时间
            JSONObject jsonObject = null;
            if(!cacheJSONObjectMap.containsKey(jsonConfigStr)){
            	jsonObject = new JSONObject(jsonConfigStr);
            	cacheJSONObjectMap.put(jsonConfigStr, jsonObject);
            }else
            	jsonObject = cacheJSONObjectMap.get(jsonConfigStr);
            
            if(jsonObject == null)
            	return null;
            
            JSONArray jsonObjs = jsonObject.getJSONArray(panel); 
            
            for(int i = 0; i < jsonObjs.length(); i++){ 
                JSONObject jsonObj = jsonObjs.getJSONObject(i);
                Map<String, String> map = new HashMap<String, String>();
                String value = null;
                
                for(int k = 0; k < key.length; k++){
					try {
						if (jsonObj.has(key[k])) {
							value = jsonObj.getString(key[k]);
							map.put(key[k], value);
						} else {
							map.put(key[k], "");
						}
					} catch (Exception e) { 
                        System.out.println("Jsons parse error !");   
                        e.printStackTrace(); 
                        value = null;
                        break;
                    }
                }
                if(value != null)
                    list.add(map);
            } 
            
        } catch (JSONException e) { 
            System.out.println("Jsons parse error !"); 
            //e.printStackTrace(); 
        }
            return list;
    }

	/**
	 * 获取参数配置值
	 * @return
	 */
	public static String getParamsValue(String paramsName){
		if(RomaSysConfig.resp_paramsName != null){
			for(int i = 0; i < RomaSysConfig.resp_paramsName.length; i++){
				if(RomaSysConfig.resp_paramsName[i].equals(paramsName))
					return RomaSysConfig.resp_paramsValue[i];
			}
		}
		return null;
	}
	
	/**
	 * 获取参数配置值,带默认值的
	 * @return
	 */
	public static String getParamsValue(String paramsName,
			String defaultValue){
		if(RomaSysConfig.resp_paramsName != null){
			for(int i = 0; i < RomaSysConfig.resp_paramsName.length; i++){
				if(RomaSysConfig.resp_paramsName[i].equals(paramsName))
					return RomaSysConfig.resp_paramsValue[i];
			}
		}
		return defaultValue;
	}
	
	/**
	 * 获取参数配置ID
	 * @return
	 */
	public static int getParamsId(String paramsName){
		if(RomaSysConfig.resp_paramsName != null){
			for(int i = 0; i < RomaSysConfig.resp_paramsName.length; i++){
				if(RomaSysConfig.resp_paramsName[i].equals(paramsName))
					return RomaSysConfig.resp_paramsId[i];
			}
		}
		return -1;
	}
	/**
	 * 获取推送消息未读数
	 * @return
	 */
	public static int getUnreadInfos(){
		int unreadInfos=0;
		//获取预警未读数
		Set<String> idsSet= RomaSysConfig.GetStringSet(RomaSysConfig.yuJingName, RomaSysConfig.NAME_YJ_YD_INFO_IDS);
		if (idsSet != null && idsSet.size() > 0) {
			Iterator<String> it = idsSet.iterator();

			boolean isRead=false;
			while (it.hasNext()) {
				String id = it.next();
				isRead= SharedPreferenceUtils.getPreference(RomaSysConfig.yuJingName, id, false);
				if(isRead){
					//已读
				}else{//未读
					unreadInfos++;
				}
			}
		}
		//获取公告未读数
		Set<String> systemNoticeNameSet= RomaSysConfig.GetStringSet(RomaSysConfig.systemNoticeName, RomaSysConfig.NAME_NT_YD_INFO_IDS);
		if (systemNoticeNameSet != null && systemNoticeNameSet.size() > 0) {
			Iterator<String> it = systemNoticeNameSet.iterator();

			boolean isRead=false;
			while (it.hasNext()) {
				String id = it.next();
				isRead= SharedPreferenceUtils.getPreference(RomaSysConfig.systemNoticeName, id, false);
				if(isRead){
					//已读
				}else{//未读
					unreadInfos++;
				}
			}
		}
		//没有返回0
		return unreadInfos>0?unreadInfos:0;
	}

	public static String getDeviceType() {
		return SharedPreferenceUtils.getPreference("deviceType", "deviceType", "");
	}

	public static void setDeviceType(String deviceType) {
		SharedPreferenceUtils.setPreference("deviceType", "deviceType", deviceType);
	}

	public static String getAppVersion() {
		return SharedPreferenceUtils.getPreference("appVersion", "appVersion", "");
	}

	public static void setAppVersion(String appVersion) {
		SharedPreferenceUtils.setPreference("appVersion", "appVersion", appVersion);
	}

	public static String getUpDataUrl() {
		return SharedPreferenceUtils.getPreference("upDataUrl", "upDataUrl", "");
	}

	public static void setUpDataUrl(String upDataUrl) {
		SharedPreferenceUtils.setPreference("upDataUrl", "upDataUrl", upDataUrl);
	}

	public static String getJpgpcId() {
		return SharedPreferenceUtils.getPreference("jpgpc_id", "jpgpc_id", "");
	}

	public static void setJpgpcId(String jpgpc_id) {
		SharedPreferenceUtils.setPreference("jpgpc_id", "jpgpc_id", jpgpc_id);
	}

	public static String getZtdjId() {
		return SharedPreferenceUtils.getPreference("ztdj_id", "ztdj_id", "");
	}

	public static void setZtdjId(String ztdj_id) {
		SharedPreferenceUtils.setPreference("ztdj_id", "ztdj_id", ztdj_id);
	}

	public static String getAIId() {
		return SharedPreferenceUtils.getPreference("ai_id", "ai_id", "63");
	}

	public static void setAIId(String ai_id) {
		SharedPreferenceUtils.setPreference("ai_id", "ai_id", ai_id);
	}

	public static String getMenuJson() {
		return SharedPreferenceUtils.getPreference("menu_json", "menu_json", "");
	}

	public static void setMenuJson(String menu_json) {
		SharedPreferenceUtils.setPreference("menu_json", "menu_json", menu_json);
	}

	public static String getGoldBook() {
		return SharedPreferenceUtils.getPreference("gold_book", "gold_book", "0");
	}

	public static void setGoldBook(String gold_book) {
		SharedPreferenceUtils.setPreference("gold_book", "gold_book", gold_book);
	}

	public static String getAIStockBook() {
		return SharedPreferenceUtils.getPreference("ai_stock_book", "ai_stock_book", "0");
	}

	public static void setAIStockBook(String ai_stock_book) {
		SharedPreferenceUtils.setPreference("ai_stock_book", "ai_stock_book", ai_stock_book);
	}

	public static String getLimitUpBook() {
		return SharedPreferenceUtils.getPreference("limit_up_book", "limit_up_book", "0");
	}

	public static void setLimitUpBook(String limit_up_book) {
		SharedPreferenceUtils.setPreference("limit_up_book", "limit_up_book", limit_up_book);
	}

	public static String getTgSpeciaListBook() {
		return SharedPreferenceUtils.getPreference("tg_specialist_book", "tg_specialist_book", "0");
	}

	public static void setTgSpeciaListBook(String tg_specialist_book) {
		SharedPreferenceUtils.setPreference("tg_specialist_book", "tg_specialist_book", tg_specialist_book);
	}

	public static boolean IsAllowPush() {
		return SharedPreferenceUtils.getPreference("allow_push", "allow_push", false);
	}

	public static void setAllowPush(boolean isAllow) {
		SharedPreferenceUtils.setPreference("allow_push", "allow_push", isAllow);
	}

	public static boolean IsAllowGoldPush() {
		return SharedPreferenceUtils.getPreference("gold_allow_push", "gold_allow_push", false);
	}

	public static void setAllowGoldPush(boolean isAllow) {
		SharedPreferenceUtils.setPreference("gold_allow_push", "gold_allow_push", isAllow);
	}

	public static boolean IsAllowLimitPush() {
		return SharedPreferenceUtils.getPreference("limit_allow_push", "limit_allow_push", false);
	}

	public static void setAllowLimitPush(boolean isAllow) {
		SharedPreferenceUtils.setPreference("limit_allow_push", "limit_allow_push", isAllow);
	}

	public static boolean IsAllowQushiPush() {
		return SharedPreferenceUtils.getPreference("qushi_allow_push", "qushi_allow_push", false);
	}

	public static void setAllowQushiPush(boolean isAllow) {
		SharedPreferenceUtils.setPreference("qushi_allow_push", "qushi_allow_push", isAllow);
	}

	public static boolean IsAllowArticlePush() {
		return SharedPreferenceUtils.getPreference("article_allow_push", "article_allow_push", false);
	}

	public static void setAllowArticlePush(boolean isAllow) {
		SharedPreferenceUtils.setPreference("article_allow_push", "article_allow_push", isAllow);
	}

	public static void setIp(String Ip) {
		SharedPreferenceUtils.setPreference("Ip", "Ip", Ip);
	}

	public static String getIp() {
		return SharedPreferenceUtils.getPreference("Ip", "Ip", "");
	}

	/**
	 * 0:没有，1:未读，2:已读
	 * @return
     */
	public static String getMsgReadFalg(String index) {
		return SharedPreferenceUtils.getPreference("readflag", "readflag_" + index, "0");
	}

	public static void setMsgReadFlag(String index, String readflag) {
		SharedPreferenceUtils.setPreference("readflag", "readflag_" + index, readflag);
	}

	public static String getQswyArray() {
		return SharedPreferenceUtils.getPreference("qswy_str", "qswy_str", "专家选股,智能选股");
	}

	public static void setQswyArray(String qswy_str) {
		SharedPreferenceUtils.setPreference("qswy_str", "qswy_str", qswy_str);
	}

	public static String getInviteCode() {
		return SharedPreferenceUtils.getPreference("inviteCode", "inviteCode", "");
	}

	public static void setInviteCode(String inviteCode) {
		SharedPreferenceUtils.setPreference("inviteCode", "inviteCode", inviteCode);
	}

	public static String getProductID() {
		return SharedPreferenceUtils.getPreference("productID", "productID", "");
	}

	public static void setProductID(String productID) {
		SharedPreferenceUtils.setPreference("productID", "productID", productID);
	}

	public static boolean getTime() {
		return SharedPreferenceUtils.getPreference("time", "time", false);
	}

	public static void setTime(boolean time) {
		SharedPreferenceUtils.setPreference("time", "time", time);
	}

	public static String getLessDays() {
		return SharedPreferenceUtils.getPreference("lessDays", "lessDays", "");
	}

	public static void setLessDays(String lessDays) {
		SharedPreferenceUtils.setPreference("lessDays", "lessDays", lessDays);
	}

	public static String getWechatCode() {
		return SharedPreferenceUtils.getPreference("wechatCode", "wechatCode", "");
	}

	public static void setWechatCode(String wechatCode) {
		SharedPreferenceUtils.setPreference("wechatCode", "wechatCode", wechatCode);
	}

	public static String getSpReadImage() {
		return SharedPreferenceUtils.getPreference("spread_img", "spread_img", "");
	}

	public static void setSpReadImage(String ImageUrl) {
		SharedPreferenceUtils.setPreference("spread_img", "spread_img", ImageUrl);
	}

	public static String getSpReadID() {
		return SharedPreferenceUtils.getPreference("spread_id", "spread_id", "");
	}

	public static void setSpReadID(String id) {
		SharedPreferenceUtils.setPreference("spread_id", "spread_id", id);
	}

	public static String getSpReadUrl() {
		return SharedPreferenceUtils.getPreference("spread_url", "spread_url", "");
	}

	public static void setSpReadUrl(String url) {
		SharedPreferenceUtils.setPreference("spread_url", "spread_url", url);
	}

	public static String getUserRefresh() {
		return SharedPreferenceUtils.getPreference("is_refresh", "is_refresh", "0");
	}

	public static void setUserRefresh(String is_refresh) {
		SharedPreferenceUtils.setPreference("is_refresh", "is_refresh", is_refresh);
	}

	public static boolean getGoldHelper() {
		return SharedPreferenceUtils.getPreference("GoldHelper", "GoldHelper", false);
	}

	public static void setGoldHelper() {
		SharedPreferenceUtils.setPreference("GoldHelper", "GoldHelper", true);
	}

	public static boolean getLimitHelper() {
		return SharedPreferenceUtils.getPreference("LimitHelper", "LimitHelper", false);
	}

	public static void setLimitHelper() {
		SharedPreferenceUtils.setPreference("LimitHelper", "LimitHelper", true);
	}

	public static boolean getSpecialHelper() {
		return SharedPreferenceUtils.getPreference("SpecialHelper", "SpecialHelper", false);
	}

	public static void setSpecialHelper() {
		SharedPreferenceUtils.setPreference("SpecialHelper", "SpecialHelper", true);
	}

	public static boolean getAIHelper() {
		return SharedPreferenceUtils.getPreference("AIHelper", "AIHelper", false);
	}

	public static void setAIHelper() {
		SharedPreferenceUtils.setPreference("AIHelper", "AIHelper", true);
	}

	public static String getGuideContent() {
		return SharedPreferenceUtils.getPreference("content", "content", "");
	}

	public static void setGuideContent(String content) {
		SharedPreferenceUtils.setPreference("guide_content", "guide_content", content);
	}

	public static String getNewsArray() {
		return SharedPreferenceUtils.getPreference("news_array", "news_array", "内参,视频,每日涨停");
	}

	public static void setNewsArray(String news_array) {
		SharedPreferenceUtils.setPreference("news_array", "news_array", news_array);
	}

	public static String getNewsKey() {
		return SharedPreferenceUtils.getPreference("news_key", "news_key", "1,113,125");
	}

	public static void setNewsKey(String news_key) {
		SharedPreferenceUtils.setPreference("news_key", "news_key", news_key);
	}

}

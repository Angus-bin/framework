package com.romaway.common.protocol.dl;

import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.net.serverinfo.ServerInfoMgr;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

import java.util.List;
import java.util.Map;

public class InitProtocol extends AProtocol{
	
	/**
	 * 初始化协议返回的json内容
	 */
	public static String respJsonContent;

	protected static Map<String,List<Map<String, String>>> datasMap;

	/**
	 * 用户手机号
	 */
	public String req_phoneNum;
	/**
	 * 设备号
	 */
	public String req_deviceID;
	/**
	 * 券商ID
	 */
	public String req_cpid;
	/**
	 * 用户ID
	 */
	public String req_userID;
	/**
	 * 登陆认证令牌
	 */
	public String req_signToken;
	/**
	 * 软件类型
	 */
	public String req_appType;
	/**
	 * 客户端app版本号
	 */
	public String req_appVersion;
	
	//返回数据
	/**
	 * 用户权限等级
	 */
	public String resp_user_lvl;
	/**
	 * 验证类型
	 */
	public String resp_auth_type;
	//body返回内容
	//站点列表-station
	/**
	 * 站点个数
	 */
	public int resp_station_count;
	/**
	 * 站点IP
	 */
	public String[] resp_station_ip;
	/**
	 * 站点名称
	 */
	public String[] resp_station_name;
	/**
	 * 站点端口
	 */
	public String[] resp_station_port;
	/**
	 * https端口号
	 */
	public String[] resp_station_https_port;
	/**
	 * 站点可用模块列表
	 * 201：交易，202：行情，203：资讯，204：认证
	 */
	public String[][] resp_station_moduleList;
	//站点配置信息
	/**
	 * 需计算比较的站点个数
	 */
	public int resp_stationCount;
	/**
	 * 站点负载范围
	 */
	public int resp_station_load_range_min;
	public int resp_station_load_range;
	/**
	 * 速率负载比
	 */
	public double resp_speed_load_rate;
	//客户端版本号-versionNum
	/**
	 * 最新推荐
	 */
	public String resp_newest_recommend;
	/**
	 * 测试版版本号
	 */
	public String resp_beta_versionNo;
	/**
	 * 正式版版本号
	 */
	public String resp_online_versionno;
	
	//默认自选股列表
	public int resp_stock_count;
	/**
	 * 股票代码
	 */
	public String[] resp_stock_code;
	/**
	 * 所属的APP的ID
	 */
	public String[] resp_stock_apps_id;
	/**
	 * 商品代码
	 */
	public String[] resp_stock_market_code;
	/**
	 * 组名
	 * 默认为“自选”
	 */
	public String[] resp_group_name;
	//公告-notice
	public int resp_notice_count;
	/**
	 * 公告内容
	 */
	public String[] resp_notice_content;
	/**
	 * 优先级别
	 * 1:高, 2:中, 3:低
	 */
	public String[] resp_notice_priority;
	/**
	 * 有效时间截止时间
	 */
	public String[] resp_notice_valid_time;
	/**
	 * 公告类型
	 * A:紧急公告, B:上交所公告, C:深交所公告, D:其它公告
	 */
	public String[] resp_notice_type;
	/**
	 * 公告标题
	 */
	public String[] resp_notice_title;
	/**
	 * 券商ID
	 */
	public String[] resp_notice_cpid;
	//升级信息
	public int resp_upgrade_count;
	/**
	 * 客户端名称
	 */
	public String[] resp_upgrade_ctName;
	/**
	 * 客户端代码
	 * Iphone：66063; Android: 66065
	 */
	public String[] resp_upgrade_ctCode;
	/**
	 * 升级方式 
	 * 1：自动; 2:手动
	 */
	public String[] resp_upgrade_category;
	/**
	 * 升级模式
	 * 1：提示升级; 2:强制升级; 3:不升级
	 */
	public String[] resp_upgrade_mode;
	/**
	 * 下载地址
	 */
	public String[] resp_upgrade_download_url;
	/**
	 * 版本号
	 */
	public String[] resp_upgrade_version;
	/**
	 * 升级提示信息
	 */
	public String[] resp_upgrade_msg;
	/**
	 * 升级模式 release/beta
	 */
	public String[] resp_upgrade_type;
	/*
	//站点信息
	*//**
	 * 站点权重
	 *//*
	public int[] resp_station_weight;
	*//**
	 * 站点负载
	 * 如果为负数则表示负载无法计算
	 *//*
	public int[] resp_station_load;
	*//**
	 * 站点IP
	 *//*
	public String[] resp_stationIp;
	*//**
	 * 站点端口
	 *//*
	public String[] resp_stationPort;
	*/
	//自选股同步信息
	/**
	 * 同步类型
	 * 1：‘手机号码';2: '资金账号/客户号'
	 */
	public int resp_sync_type;
	/**
	 * 所属的APP的ID
	 */
	public int resp_sync_apps_id;
	/**
	 * 是否是节假日
	 * "Y":是;"N":否
	 */
	public String resp_is_holiday;
	/**
	 * 服务热线
	 */
	public String resp_service_hot_line;
	/**
	 * 微信号
	 */
	public String resp_service_wechat_code;
	/**
	 * 服务号
	 */
	public String resp_serviceServiceNo;

	/**
	 * h5 版本号
	 */
	
	public String resp_versionNum[];
	
	/**
	 * h5 版本文件名
	 */
	
	public String resp_fileName[];

	/**
	 * h5 版本文件大小 b
	 */
	
	public int resp_fileSize[];
	
	/**
	 * h5 下载地址
	 */
	
	public String resp_downloadAddr[];
	
	/**
	 * h5 状态,beta为测试，release为正式
	 */
	
	public String resp_h5_upgrade_type[];
	
	/**
	 * h5 升级方式app检测版本更新：1、提示升级；2、强制升级
	 */
	public int resp_h5_upgradeCategory[];
	/**
	 * h5 升级方式，推送提示app有新版本：1、不推送升级；2、推送升级
	 */
	public int resp_h5_pushCategory[];
	/**
	 * h5 更新的修改内容：
	 */
	public String resp_h5_upgradeMsg[];
	/**
	 * 新广告对象
	 */
	public InitNewAdvInfo resp_initNewAdvInfo=new InitNewAdvInfo();
	/**
	 * 新广告对象V3(支持快捷菜单Key)
	 */
	public InitNewAdvInfo resp_initNewAdvInfoV3 = new InitNewAdvInfo();
	/**
	 * 广告数量
	 */
	public int resp_advtCount;
	
	/**
	 * 广告标题
	 */
	public String[] resp_advtTitle;
	/**
	 * 广告位置
	 */
	public String[] resp_advtPosition;
	/**
	 * 广告类型
	 */
	public int[] resp_advtType;
	/**
	 * 广告文字内容
	 */
	public String[] resp_advtContent;
	/**
	 * 广告图片地址
	 */
	public String[] resp_advtPicUrl;
	/**
	 * 广告链接
	 */
	public String[] resp_advtLinked;
	/**
	 * 广告备注信息
	 */
	public String[] resp_advtMemo;
	
	/**
	 * 广告展示是否需要原生Title
	 */
	public String[] resp_advSrcTitleVisibility;
	
	/**
	 * 广告展示跳转的登录方式 0:无需任何登录注册；1：仅仅手机号码注册； 大于等于2：必须要手机号码和资金账号登录
	 */
	public String[] resp_advWebViewLoginFlag;
	
	/**
	 * 广告结束时间
	 */
	public String[] resp_endtime;
	/**
	 * 广告开始时间
	 */
	public String[] resp_starttime;
	
	/**
	 * 返回的自定义参数值
	 */
	public String[] resp_paramsValue;
	
	/**
	 * 返回的自定义参数名
	 */
	public String[] resp_paramsName;
	
	/**
	 * 返回的自定义参数ID
	 */
	public int[] resp_paramsId;
	
	/**
	 * 营业部信息，json格式
	 */
	public String resp_deptinfos;

	/**
	 * 获取数据数据集数据
	 * @param arrayNameKey
	 * @return
     */
	public static List<Map<String,String>> getConfigArrayInfo(String arrayNameKey){
		if(datasMap == null)
			return null;

		return datasMap.get(arrayNameKey);
	}

	/**
	 * 获取数据数据集数据,当只有一项数据时直接返回 map 数据
	 * @param arrayNameKey
	 * @return
	 */
	public static Map<String,String> getConfigDataInfo(String arrayNameKey){
		if(datasMap == null)
			return null;

		List<Map<String,String>> listmap = datasMap.get(arrayNameKey);
		if(listmap.size() > 0)
			return listmap.get(0);

		return null;
	}


	/**
	 * 根据参数名获取响应的值
	 * @param paramsName 参数名
	 * @param defValue 默认值
	 * @return
     */
	public static String getConfigParamsValue(String paramsName, String defValue){
		List<Map<String,String>> maplist = getConfigArrayInfo("params");
		for (int i = 0; i < maplist.size(); i++) {
			Map<String,String> info = maplist.get(i);
			if(paramsName.equals(info.get("param_name")))
				return info.get("param_value");
		}

		return defValue;
	}

	/**
	 * 
	 * @param flag
	 */
	public InitProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/system/init/";
	}

}

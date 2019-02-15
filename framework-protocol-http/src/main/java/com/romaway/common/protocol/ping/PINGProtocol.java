package com.romaway.common.protocol.ping;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

public class PINGProtocol extends AProtocol {
	public final static short ping = 1;
	
	/**
	 * 端口号
	 */
	public String resp_station_port;
	/**
	 * 站点名称
	 */
	public String resp_station_name;
	/**
	 * 站点IP
	 */
	public String resp_station_ip;
	/**
	 * 站点权重
	 */
	public String resp_station_weight;
	/**
	 * 站点负载
	 */
	public String resp_station_load;
	/**
	 * https端口号
	 */
	public String resp_station_https_port;
	/**
	 * 站点可用模块列表
	 * 201：交易，202：行情，203：资讯，204：认证
	 */
	public String[] resp_station_moduleList;
	/**
	 * 站点所属运营商
	 * 电信 DX
	 * 移动 YD
	 * 联通 LT
	 * 内网 NW
	 * 没结果 NULL
	 */
	public String resp_station_carrieroperator;
	/**
	 * 当前IP所属运营商
	 */
	public String resp_curr_ip_operator;

	/**
	 * 
	 * @param flag
	 */
	public PINGProtocol(String flag) {
		super(flag, false);
		subFunUrl = "/api/system/sitestatus/";
		isJson = true;
	}

}

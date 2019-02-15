package com.romaway.common.protocol.wo;

import org.json.JSONArray;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

public class WoPersonalCenterSelectProtocol extends AProtocol{
	/**
	 * 资金账号
	 */
	public String req_bacc;
	/**
	 * 设备ID
	 */
	public String req_deviceId;
	/**
	 * 手机号码
	 */
	public String req_phoneNum;

	//返回数据
	/**
	 * 客户编号
	 */
	public String resp_clientId;
	/**
	 * 客户姓名
	 */
	public String resp_clientName;
	/**
	 * 营业部名称
	 */
	public String resp_branchNo;
	/**
	 * 服务人员编号
	 */
	public String resp_managerId;
	/**
	 * 服务人员姓名
	 */
	public String resp_managerName;
	/**
	 * 服务人员类型
	 */
	public String resp_managerType;
	/**
	 * 服务人员电话
	 */
	public String resp_managerTel;

	public WoPersonalCenterSelectProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "/api/auth/personal/info/select/";
	}

}

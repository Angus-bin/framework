package com.romaway.common.protocol.dl;

import com.romaway.common.protocol.AProtocol;

/**
 * 升级状态信息
 * @author chenjp
 *
 */
public class UpgradeStateProtocol extends AProtocol{

	/**
	 * 升级标识
	 *  0 代表 不进行升级 1 代表 升级 h5，不升级 app 2 代表 升级 app，不升级 h5 3 代表 升级 h5 和 app
	 */
	public String resp_flag;
	/**
	 * 升级信息来源
	 * hdsj
	 * release
	 */
	public String resp_type;
	
	public UpgradeStateProtocol(String flag){
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/system/hdsj/flag/";
	}
}

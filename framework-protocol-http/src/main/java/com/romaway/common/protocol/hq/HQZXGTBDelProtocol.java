package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
/**
 * 增加自选股
 * @author dell
 *
 */
public class HQZXGTBDelProtocol extends AProtocol{

	/**
	 * 删除自选股列表
	 */
	public String req_favors;
	/**
	 * 自选股组名
	 */
	public String req_group;
	
	//返回数据

	public HQZXGTBDelProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "/api/system/favor/del/";
	}
}

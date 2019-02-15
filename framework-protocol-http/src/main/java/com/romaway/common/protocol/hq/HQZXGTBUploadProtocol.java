package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;
/**
 * 上传自选股
 * @author dell
 *
 */
public class HQZXGTBUploadProtocol extends AProtocol{

	/**
	 * 上传自选股列表
	 */
	public String req_favors;
	/**
	 * 自选组名
	 */
	public String req_group;
	
	//返回数据

	public HQZXGTBUploadProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "/api/system/favor/update/";
	}

}

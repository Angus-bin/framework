package com.romaway.common.protocol.hq.zxjt;

import com.romaway.common.protocol.AProtocol;

import java.util.List;

/**
 * 云同步上传自选股
 * @author Edward
 *
 */
public class HQZXGCloudSysncUploadProtocol extends AProtocol{

	/**
	 * 资金账号，非空(最长 20 位)
	 */
	public String req_userId;
	/**
	 * 默认资金账号，填空
	 */
	public String req_userCategory;
	/**
	 * 手机端填: TY-MOBILE
	 */
	public String req_clientName;
	/**
	 * 填实际版本号，非空(最长 10 位)
	 */
	public String req_clientVersion;

	public Portfolio req_portfolio;

	//返回数据

	public HQZXGCloudSysncUploadProtocol(String flag) {
		super(flag, true);
		isJson = true;
		subFunUrl = "/api/system/favor/upload/v1.0/";
	}
}

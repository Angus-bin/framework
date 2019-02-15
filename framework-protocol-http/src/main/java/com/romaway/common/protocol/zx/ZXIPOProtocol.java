package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * 首页新股提醒
 * @author wusq
 *
 */
public class ZXIPOProtocol extends AProtocol{
	
	/**
	 * 响应的新股
	 */
	public String resp_ipo;

	public ZXIPOProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/news/ipo/";
	}

}

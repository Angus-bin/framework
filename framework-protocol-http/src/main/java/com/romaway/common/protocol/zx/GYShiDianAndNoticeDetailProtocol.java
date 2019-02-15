package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * 国元视点和最新公告协议
 * @author wusq
 *
 */
public class GYShiDianAndNoticeDetailProtocol extends AProtocol{
	/**
	 * 1-国元试点 2-公告
	 */
	public String req_typeCode;
	/**
	 * 待拉取详情的id
	 */
	public String req_id;

	//返回数据
	/**
	 * 信息详情
	 */
	public String resp_sms;


	public GYShiDianAndNoticeDetailProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl="/api/msg-service/get_notice_view_id";
	}

}

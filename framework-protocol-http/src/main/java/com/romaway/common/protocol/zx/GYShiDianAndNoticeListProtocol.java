package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * 国元视点和最新公告协议
 * @author wusq
 *
 */
public class GYShiDianAndNoticeListProtocol extends AProtocol{
	/**
	 * 1-国元试点 2-公告
	 */
	public String req_typeCode;
	/**
	 * 是否首页三条的标识， 1--是   0--否
	 */
	public String req_begin;
	/**
	 * 拉取的消息真实记录
	 */
	public String req_start;
	/**
	 * 拉取的条数
	 */
	public String req_num;
	//返回数据
	/**
	 * 信息列表
	 */
	public String resp_smsArray;


	public GYShiDianAndNoticeListProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl="/api/msg-service/get_notice_view_list";
	}

}

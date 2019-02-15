package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocol;

/**
 * F10 主营构成
 * @author chenjp
 * @version 2015年7月14日 下午2:02:34
 */
public class ZXF10ZYGCProtocol extends AProtocol{
	
	/**
	 * 返回完整json数据
	 */
	public String resp_news;
	/**
	 * 返回数据个数
	 */
	public int resp_count;
	/**
	 * 业务名称
	 */
	public String[] resp_ywmc;
	/**
	 * 业务收入
	 */
	public String[] resp_ywsr;

	/**
	 * F10 主营构成
	 * @param flag
	 * @param autoRefreshStatus
	 */
	public ZXF10ZYGCProtocol(String flag, boolean autoRefreshStatus) {
		super(flag, autoRefreshStatus);
		isJson = true;
		this.subFunUrl = "/api/news/f10/zygc-";
	}

}

package com.romaway.common.protocol.xt;

import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;

/**
 * 系统服务器之文本信息查询协议
 * @author dumh
 *
 */
public class XTTxtInfoProtocol extends AProtocol
{
	/**
	 * 协议子功能号
	 */
	public final static short XT_XXCX = 25;
	
	//请求
	/**
	 * 资源key
	 */
	public String req_sResourceKey;
	
	/**
	 * 文本偏移
	 */
	public int req_offset;
	
	/**
	 * 需要的文字数量
	 */
	public int req_count;
	
	/**
	 * 券商cpid
	 */
	public String req_cpid;
	
	
	//响应
	/**
	 * 整个文本长度
	 */
	public int resp_count;
	
	/**
	 * 文本内容
	 */
	public String resp_content;
	
	public XTTxtInfoProtocol(String flag, int cmdVersion)
    {
	    super(flag, ProtocolConstant.MF_XT, XT_XXCX, cmdVersion, true, false);
	    // TODO Auto-generated constructor stub
    }

	
	/**
	 * 设置请求资源key
	 * @param req_sResourceKey
	 */
	public void setResourceKey(String req_sResourceKey)
    {
    	this.req_sResourceKey = req_sResourceKey;
    }

	
	/**
	 * 设置请求文本偏移量
	 * @param req_offset
	 */
	public void setOffset(int req_offset)
    {
    	this.req_offset = req_offset;
    }

	
	/**
	 * 设置请求的文本字数
	 * @param req_count
	 */
	public void setCount(int req_count)
    {
    	this.req_count = req_count;
    }

	
	/**
	 * 
	 * @param req_cpid
	 */
	public void setCPID(String req_cpid)
    {
    	this.req_cpid = req_cpid;
    }

	/**
	 * 获取响应下发的字数
	 * @return
	 */
	public int getCount()
    {
    	return resp_count;
    }

	
	/**
	 * 获取响应的文笔内容
	 * @return
	 */
	public String getContent()
    {
    	return resp_content;
    }

	
	
}

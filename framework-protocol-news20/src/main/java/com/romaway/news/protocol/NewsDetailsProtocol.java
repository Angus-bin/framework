package com.romaway.news.protocol;

import com.romalibs.utils.Res;
import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.conn.ConnInfo;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.net.sender.NetMsgSenderProxy;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.common.utils.DES3;
import com.romaway.common.utils.RomaSysConfig;
import com.romaway.commons.lang.StringUtils;

/**
 * 资讯详情协议
 * @author  万籁唤
 * 创建时间：2016年3月24日 下午2:47:15
 * @version 1.0 
 */
@SuppressWarnings("rawtypes")
public class NewsDetailsProtocol  extends AProtocol {
	
	/**
	 * 资讯栏目
	 */
	public String req_funType;
	
	/**
	 * 入参：资讯ID
	 */
	public String req_newsId;
	
	/**
	 * 缓存的数据库名称
	 */
	public String req_cacheDbName;
	
	
	//version 1.0
	/**
	 * 资讯时间
	 */
	public String resp_time;
	/**
	 * 资讯标题
	 */
	public String resp_title;

	/**
	 * 资讯来源
	 */
	public String resp_source;
	
	//version 2.0
	
	/**
	 * 资讯ID，唯一ID标识
	 */
	public String resp_newsId;
	
	/**
	 * PDF地址
	 */
	public String resp_pdfUrl;
	
	/**
	 * 专题类型，如为空即不是专题，只有不为空时才是专题
	 */
	public String req_topic;
	
	/**
	 * 资讯类型：0：正常类型；1：专题；2：独家; 3:推广
	 */
	public String resp_newsType;
	
	/**
	 * 分享URL
	 */
	public String resp_shareUrl;
	
	/**
	 * 资讯2.0的详情内容，最终保存的是html中body部分的字符串,该内容可直接作为html body部分
	 */
	public String resp_body;
	
	/**
	 * 相关股票json串内容，需要传递给H5
	 */
	public String resp_contsCode;
	
	/**
	 * 临时保存html文件的缓存路径
	 */
	public String htmlCachePath;
	
	public String resp_videoImageUrl;
	
	public String resp_videoImageSavePath;
	public String resp_videoUrl;
	
	public String resp_videoWidth;
	
	public String resp_videoHeight;
	public String resp_digest;
	/**
	 * 
	 * @param flag
	 */
	@SuppressWarnings("unchecked")
	public NewsDetailsProtocol(String flag) {
		super(flag, false);
		isJson = true;
//		subFunUrl = "/api/news20/detail/?";
		subFunUrl = "api/getNewsMsg?";
	}

	/**
	 *
	 * @param cacheDbName
	 * @param funType
	 * @param newsId
	 * @param listener
     */
	public void req(String cacheDbName, String funType, String newsId, INetReceiveListener listener){
		req_funType = funType;
		req_newsId = newsId;
		req_cacheDbName = cacheDbName;
		subFunUrl = "/api/news20/detail/?";
		StringBuilder builder = new StringBuilder();
		builder.append(subFunUrl);
		
		//资讯newsId
		builder.append("id=");
		builder.append(req_newsId);
		
		//资讯类别
		builder.append("&type="); 
//		if(req_funType.contains("S"))// "S"类的比较特殊，数据请求时部分栏目，统一使用"S"，比如自选资讯协议
//			builder.append("S");
//		else
			builder.append(req_funType); 
		String url = builder.toString();
		
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_NEWS2, url);
		ServerInfo temp = new ServerInfo("get_stock_news_detail", ProtocolConstant.SERVER_FW_NEWS2, "get_stock_news_detail", "http://hldj.ehlzq.com.cn:31800", false, 31900);
		temp.setSubFunUrl(url);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(getFlag(), EMsgLevel.normal, this, connInfo, false, listener);
		
		NetMsgSenderProxy.getInstance().send(msg);
	}

	public void req(String cacheDbName, String newsId, String grab, String no, String type, String code, INetReceiveListener listener){
		req_funType = "S1";
		req_newsId = newsId;
		req_cacheDbName = cacheDbName;
		isLoadCache = false;

		StringBuilder builder = new StringBuilder();
		builder.append(subFunUrl);

		//资讯newsId
		builder.append("id=");
		builder.append(req_newsId);

		//资讯类别
		builder.append("&grab=");
		builder.append(grab);
		builder.append("&no=");
		builder.append(no);
		builder.append("&type=");
		builder.append(type);
		builder.append("&code=");
		builder.append(code);
		String url = builder.toString();

		String ip = "http://api.romawaysz.com/";
//		if (!StringUtils.isEmpty(RomaSysConfig.getIp())) {
//			ip = RomaSysConfig.getIp();
//		} else {
//			ip = Res.getString(R.string.NetWork_IP);
//		}

		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_NEWS2, url);
		ServerInfo temp = new ServerInfo("get_stock_news_detail", ProtocolConstant.SERVER_FW_NEWS2, "get_stock_news_detail", ip, false, 9801);
		temp.setSubFunUrl(url);
		connInfo.setServerInfo(temp);
		NetMsg msg = new NetMsg(getFlag(), EMsgLevel.normal, this, connInfo, false, listener);
		msg.sendByGet = true;
		NetMsgSenderProxy.getInstance().send(msg);
	}

}

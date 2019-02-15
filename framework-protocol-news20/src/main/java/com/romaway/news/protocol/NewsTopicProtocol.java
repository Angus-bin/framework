package com.romaway.news.protocol;

import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.conn.ConnInfo;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.net.sender.NetMsgSenderProxy;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.news.protocol.info.Item_newsTopic;

import java.util.List;
import java.util.Map;

/** 
 * 资讯详情协议
 * @author  万籁唤
 *  创建时间：2016年3月24日 下午2:47:15
 * @version 1.0 
 */
@SuppressWarnings("rawtypes")
public class NewsTopicProtocol  extends AProtocol {
	/**
	 * 资讯栏目,这个在专题当中是用不上的，这里仅仅是代表上一个要闻等界面选项的funtype
	 */
	public String req_funType;
	
	/**
	 * 入参：资讯ID，这个在专题当中是用不上的，这里仅仅是代表上一个要闻等界面选项的req_newsId
	 */
	public String req_newsId;
	
	/**
	 * 资讯栏目
	 */
	public String req_topic;
	/**
	 * 顶部banner
	 */
	public String resp_banner; 
	/**
	 * 顶部的文字详情部分
	 */
	public String resp_guidance;
	
	/**
	 * 分组的个数
	 */
	public int resp_sectionCount;
	
	/**
	 * 返回的数据
	 */
	public  List<Map<String,Item_newsTopic>> resp_topicDatas;
	
	/**
	 * 
	 * @param flag
	 */
	@SuppressWarnings("unchecked")
	public NewsTopicProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/news20/topic?";
		
	}

	/**
	 *
	 * @param funType
	 * @param newsId
	 * @param topic
	 * @param listener
     */
	public void req(String funType, String newsId, String topic, INetReceiveListener listener){
		// 用来缓存
		req_funType = funType;
		req_newsId = newsId;
		// 用于数据请求入参
		req_topic = topic;
		
		StringBuilder builder = new StringBuilder();
		builder.append(subFunUrl);
		
		//资讯类别
		builder.append("topic=");
		builder.append(topic);
		String url = builder.toString();
		
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(
				ProtocolConstant.SERVER_FW_NEWS2, url);
		NetMsg msg = new NetMsg(getFlag(), EMsgLevel.normal, this, connInfo, false, listener);
		
		NetMsgSenderProxy.getInstance().send(msg);
	}
}

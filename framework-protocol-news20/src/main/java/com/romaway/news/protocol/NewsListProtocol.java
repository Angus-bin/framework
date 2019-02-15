package com.romaway.news.protocol;

import android.text.TextUtils;

import com.romalibs.utils.Logger;
import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.conn.ConnInfo;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.net.sender.NetMsgSenderProxy;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.news.protocol.info.Item_newsListItemData;
import com.romaway.news.protocol.info.NewsListGroupContiner;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯列表首页带轮播广告协议，在请求列表首页时使用
 * @author 洪荣斌  2017-05-25
 *
 * PS: 资讯 7*24小时 增加 A股/国际 分类, 接口更新及数据库缓存读取逻辑扩展思路: 	(zhengms, 2016-10-18)
 * 	1. 缓存数据库表添加 消息类型 msgType/msgtype 字段(ALTER TABLE 表名 ADD COLUMN 列名 数据类型, 注意要升级数据库版本号及版本更新);
 *	2. 缓存管理器类要读取(下拉刷新/上拉加载)时添加该字段判断, 保证界面数据不错乱;
 *	3. 网络加载完成后会更新至缓存数据库(之前可能的判定是: 根据funType以及newsId判断是否已存在, 已存在则不添加), 修正为:
 *		a. 判断请求是否包含msgtype 字段, 不包含走原来逻辑, 包含走增加了msgtype字段的判断;
 *		b. 包含msgtype字段判断为不存在时(此处要考虑两种情况),  再次判断仅根据funType以及newsId判断, 不存在则直接添加, 存在则代表可能是覆盖安装, 要根据funType以及newsId更新数据库条目数据;
 */
@SuppressWarnings("rawtypes")
public class NewsListProtocol extends AProtocol {
	
	/**
	 * 是否进行分组，比如滚动时间轴的有分组功能
	 */
	public boolean isMultiGroup = false;
	
	/**
	 * 0:下拉刷新请求；1：上拉加载更多请求
	 */
	public int direction = 0;
	
	/**资讯类型*/
	public String req_funType; 
	
	/**股票代码和市场代码*/
	public String req_stockCodeMarketid;
	
	/**最后一条的newsId若指定此参数，则返回ID比since_id大的资讯（即比since_id时间晚的资讯），默认为0*/
	public String req_sinceId;
	
	/**若指定此参数，则返回ID小于或等于max_id的资讯，默认为0。*/
	public String req_maxId;
	
	/**单页返回的记录条数，最大不超过100，默认为10。*/
	public int req_count;

	/** 7*24小时 A股/国际数据类型 */
	public String req_msgType;

	/**
	 * 保存的临时列表数据，用于保存缓存时用
	 */
	protected ArrayList<Item_newsListItemData> toCacheNewsList = null;
	
	/**返回的列表数据,一直保存在内存中*/
	public ArrayList<Item_newsListItemData> resp_newsDataList = new ArrayList<Item_newsListItemData>();
	
	/**
	 * 数据返回集合
	 */
	public List<NewsListGroupContiner> resp_NewsGroupList;
	
	@SuppressWarnings("unchecked")
	public NewsListProtocol(String flag) {
		super(flag, false);
		isJson = true;
		subFunUrl = "/api/news20/list?";
	}
	
	/**
	 * 清理内存
	 */
	@SuppressWarnings("unchecked")
	public void clearMemory() {
		resp_newsDataList.clear();
		isHasMemory = false;
	}
	
	/**
	 * 数据请求接口
	 * @param funType
	 * @param maxId
	 * @param sinceId
	 * @param stockCodeMarketid
	 * @param reqCount
	 * @param direction 数据请求方向，0：下拉刷新；1：上拉加载更多
	 * @param isLoadCache 是否需要读取缓存数据
	 * @param listener
	 */
	@SuppressWarnings("unchecked")
	public void req(
			String funType, 
			String maxId,
			String sinceId, 
			String stockCodeMarketid, 
			int reqCount,
			int direction,
			boolean isLoadCache,
			INetReceiveListener listener){
		
		this.direction = direction;
		this.isLoadCache = isLoadCache;
		isMultiGroup = false;
		reqNewsList(funType, maxId, sinceId, stockCodeMarketid, null, reqCount, listener);
	}

	/**
	 * 请求数据后进行分组
	 * @param funType
	 * @param maxId
	 * @param sinceId
	 * @param stockCodeMarketid
	 * @param reqCount
	 * @param direction 数据请求方向，0：下拉刷新；1：上拉加载更多
	 * @param isLoadCache 是否需要读取缓存数据
	 * @param listener
	 */
	@SuppressWarnings("unchecked")
	public void reqMultiGroup(  
			String funType, 
			String maxId,
			String sinceId,
			String stockCodeMarketid,
			String msgType,
			int reqCount,
			int direction,
			boolean isLoadCache,
			INetReceiveListener listener) {
		
		this.direction = direction;
		this.isLoadCache = isLoadCache;
		isMultiGroup = true;
		
		reqNewsList(funType, maxId, sinceId, stockCodeMarketid, msgType, reqCount, listener);
	}

	/**
	 * 请求数据
	 * @param funType 资讯栏目类别
	 * @param maxId 请求比加载更新的数据
	 * @param sinceId 请求加载更多时比sinceId更旧的数据
	 * @param stockCodeMarketid 股票代码市场ID 如：6004462
	 * @param reqCount 请求返回的数据个数
	 * @param listener 
	 */
	private void reqNewsList(String funType, String maxId, String sinceId, String stockCodeMarketid, String msgType, int reqCount, INetReceiveListener listener){
		
		if(TextUtils.isEmpty(funType)) {
			Logger.d("资讯列表协议", "请求列表数据- funType："+funType+", maxId: "+maxId+",sinceId:"+sinceId+",stockCodeMarketid:"+stockCodeMarketid);
			return ;
		}
		if(req_count < 0)
			req_count = 0;
		
		req_funType = funType;
		req_maxId = maxId;
		req_sinceId = sinceId;
		req_stockCodeMarketid = stockCodeMarketid;
		req_count = reqCount;
		req_msgType = msgType;

		StringBuilder builder = new StringBuilder();
		builder.append(subFunUrl);
		//资讯类别
		builder.append("req_funType=");
		builder.append(req_funType);
		
		//资讯maxId
		if(!TextUtils.isEmpty(req_maxId)){
			builder.append("&req_maxId=");
			builder.append(req_maxId);
		}
				
		//资讯sinceId
		if(!TextUtils.isEmpty(req_sinceId)){
			builder.append("&req_sinceId=");
			builder.append(req_sinceId);
		}
		
		//资讯股票代码 市场ID
		if(!TextUtils.isEmpty(req_stockCodeMarketid)){
			builder.append("&req_stockCode=");
			builder.append(req_stockCodeMarketid);
		}

		//资讯msgtype: A股/国际 数据类型
		if(!TextUtils.isEmpty(req_msgType)){
			builder.append("&msgtype=");
			builder.append(req_msgType);
		}

		//资讯请求的个数
		builder.append("&req_count=");  
		builder.append(req_count);
		String url = builder.toString();

		Logger.d("tag", "reqLoadMoreData url :"+url);
		
		ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_NEWS2, url);
		NetMsg msg = new NetMsg(getFlag(), EMsgLevel.normal, this, connInfo, false, listener);
		NetMsgSenderProxy.getInstance().send(msg);
	}
}

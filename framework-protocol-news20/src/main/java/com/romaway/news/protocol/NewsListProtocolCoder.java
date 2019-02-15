package com.romaway.news.protocol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.romalibs.utils.Logger;
import com.romalibs.utils.gson.GsonHelper;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.news.protocol.info.Item_newsListItemData;
import com.romaway.news.protocol.util.MultiGroup;
import com.romaway.news.sql.NewsCacheDao;
import com.romaway.news.sql.SqlContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 资讯轮播广告协议Coder
 * @author 万籁唤  2016-03-22
 *
 */
public class NewsListProtocolCoder extends AProtocolCoder<NewsListProtocol> {

	private NewsCacheDao mNewsCacheDao;
	 
	private MultiGroup mGroup;
	
	/**
	 * 网络请求是否已满，0x00未满， 0x01已满
	 */
	private int netLoadFull = 0x00;
	/**
	 * 本地缓存加载是否已满，0x00未满， 0x01已满
	 */
	private int cacheLoadFull = 0x00;
	
	private Context context;
	
	public NewsListProtocolCoder(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		mNewsCacheDao = new NewsCacheDao(context, 
				SqlContent.CACHE_LIST_DB_NAME);
		// 分组逻辑处理
		mGroup = new MultiGroup();
	}
	
	@Override
	protected synchronized byte[] encode(NewsListProtocol protocol) {
		// TODO Auto-generated method stub
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@SuppressLint("UseSparseArrays")
	@SuppressWarnings("unchecked")
	@Override
	protected void decode(NewsListProtocol protocol) throws ProtocolParserException {
		// TODO Auto-generated method stub
		printfDecode(0, protocol);
	}
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void saveCache(String key, NewsListProtocol protocol)
			throws ProtocolParserException {
		// TODO Auto-generated method stub
		super.saveCache(key, protocol);

		System.out.println("1-saveCache ========== "+protocol.getFlag()/*System.currentTimeMillis()*/);
		if(protocol.toCacheNewsList == null)
			return;

	    @SuppressWarnings("unchecked")
		ArrayList<Item_newsListItemData> tempnewsList1 =
			(ArrayList<Item_newsListItemData>) protocol.toCacheNewsList.clone();
		//数据列表缓存
	    if(tempnewsList1 != null){
	    	for(int i = 0; i < tempnewsList1.size(); i++) {
	    		Item_newsListItemData cacheNewsItem = tempnewsList1.get(i);

	    		String stockCode = null;//不带下划线的股票代码和市场ID
				if(!TextUtils.isEmpty(cacheNewsItem.code))
					stockCode = cacheNewsItem.code.replace("_", "");
				Logger.d("tag", "资讯缓存：newsId = "+cacheNewsItem.newsId);

				if (TextUtils.isEmpty(protocol.req_msgType)) {
					//重复就不添加
					if (mNewsCacheDao.isExistFunTypeNewsId(
							protocol.req_funType,
							cacheNewsItem.newsId))
						continue;

					String jsonValue = GsonHelper.objectToJson(cacheNewsItem);
					Logger.d("tag", "stockCodestockCodestockCode: " + stockCode);
					mNewsCacheDao.insert(
							protocol.req_funType
							, cacheNewsItem.newsId
							, stockCode
							, jsonValue
							, "" + System.currentTimeMillis());
				}else{
					String jsonValue = GsonHelper.objectToJson(cacheNewsItem);
					Logger.d("tag", "stockCodestockCodestockCode: " + stockCode);

					//重复就不添加
					if (mNewsCacheDao.isExistFunTypeNewsId(
							protocol.req_funType,
							cacheNewsItem.newsId,
							protocol.req_msgType))
						continue;
					// 仅funType, newsId重复, 则代表为覆盖安装等有旧缓存数据存在, 即更新缓存库条目内容;
					else if (mNewsCacheDao.isExistFunTypeNewsId(
							protocol.req_funType,
							cacheNewsItem.newsId)){
						mNewsCacheDao.updateItemData(
								protocol.req_funType
								, cacheNewsItem.newsId
								, protocol.req_msgType
								, stockCode
								, jsonValue
								, "" + System.currentTimeMillis());
					}else{
						mNewsCacheDao.insert(
								protocol.req_funType
								, cacheNewsItem.newsId
								, stockCode
								, jsonValue
								, "" + System.currentTimeMillis());
					}
				}

			}
	    }
		System.out.println("2-saveCache ========== "+protocol.getFlag()/*System.currentTimeMillis()*/);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected NewsListProtocol readCache(String key,
			NewsListProtocol protocol) throws ProtocolParserException { 
		// TODO Auto-generated method stub
		
		printfDecode(1, protocol);
		
		return protocol;
	}
	
	private static Object printfLock = new Object();
	/**
	 * 输出数据到内存中
	 * @param type 类型：0：网络数据；否则：缓存数据
	 * @param protocol
	 * @return
	 */
	private NewsListProtocol printfDecode(int type, NewsListProtocol protocol) {
		synchronized(printfLock) {
			if(type == 0){
				printfNetDecode(protocol);
			}else {
				return printfCacheDecode(protocol);
			}
			return protocol;
		}
	}
	
	private void printfNetDecode(NewsListProtocol protocol) {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();
		
		try {
			JSONObject json = new JSONObject(result);
			if (json.has("news")) {//资讯列表数据
				JSONArray newsJSONArray = json.getJSONArray("news");
				int count = newsJSONArray.length();
				protocol.toCacheNewsList = new ArrayList<Item_newsListItemData>();
				
				for (int i = 0; i < count; i++) {
					Item_newsListItemData item_NewsListItemData = new Item_newsListItemData();
					JSONObject jSONObject = newsJSONArray.getJSONObject(i);
					
					if(jSONObject.has("funType"))
						item_NewsListItemData.funType = jSONObject.getString("funType");
					
					if(jSONObject.has("newsId"))
						item_NewsListItemData.newsId = jSONObject.getString("newsId");
					
					if(jSONObject.has("important"))
						item_NewsListItemData.important = jSONObject.getString("important");
					if(jSONObject.has("code"))
						item_NewsListItemData.code = jSONObject.getString("code");
					if(jSONObject.has("name"))
						item_NewsListItemData.name = jSONObject.getString("name");
					if(jSONObject.has("time")) {
						item_NewsListItemData.time = jSONObject.getString("time");
						item_NewsListItemData.timeShow = 
								MultiGroup.getCustomFormat(item_NewsListItemData.time);// 处理时间为当天的就仅仅显示时间而不显示日期
					}
					if(jSONObject.has("title"))
						item_NewsListItemData.title = jSONObject.getString("title");
					if(jSONObject.has("digest"))
						item_NewsListItemData.digest = jSONObject.getString("digest");
					if(jSONObject.has("imgType"))
						item_NewsListItemData.imgType = jSONObject.getString("imgType");
					if(jSONObject.has("layout"))
						item_NewsListItemData.layout = jSONObject.getString("layout");
					if(jSONObject.has("source"))
						item_NewsListItemData.source = jSONObject.getString("source");
					if(jSONObject.has("newsType"))
						item_NewsListItemData.newsType = jSONObject.getString("newsType");
					if(jSONObject.has("imgsrc1"))
						item_NewsListItemData.imgsrc1 = jSONObject.getString("imgsrc1");
					if(jSONObject.has("imgsrc2"))
						item_NewsListItemData.imgsrc2 = jSONObject.getString("imgsrc2");
					if(jSONObject.has("imgsrc3"))
						item_NewsListItemData.imgsrc3 = jSONObject.getString("imgsrc3");
					if(jSONObject.has("topic"))
						item_NewsListItemData.topic = jSONObject.getString("topic");
					if(jSONObject.has("descrip"))
						item_NewsListItemData.descrip = jSONObject.getString("descrip");
					if(jSONObject.has("active")){
						item_NewsListItemData.active = jSONObject.getString("active");
						if("1".equals(item_NewsListItemData.active)){
							mNewsCacheDao.deleteForNewsId(item_NewsListItemData.funType, item_NewsListItemData.newsId);
							continue;
						}
					}
					
					//已读未读标记
					item_NewsListItemData.readFlag = 
							mNewsCacheDao.selectReadFlag(protocol.req_funType,
									item_NewsListItemData.newsId);
					protocol.toCacheNewsList.add(item_NewsListItemData);
				}

				// 输出到内存中
				net2Datas(protocol);
				
				//加载满标志
				netLoadFull = 0x00;
				if(protocol.isHasMemory && count == 0){
					netLoadFull = 0x01;
					cacheLoadFull = protocol.isLoadCache? 0x00 : 0x01;
					protocol.isDataLoadFull = 
							(netLoadFull & cacheLoadFull) > 0? true : false;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private NewsListProtocol printfCacheDecode(NewsListProtocol protocol) {
		//		if(true)
		//		return null;
			String[][] result = null; 
			System.out.println("1-readCache =========="+protocol.getFlag()); 
			
			if(protocol.direction == 0) {//说明是下拉刷新或者是点击了刷新按钮
				//读取db缓存
				if(protocol.req_funType.equals("S4")){
					result = mNewsCacheDao.selectMultipleFromCodeMart(
						protocol.req_funType
						, protocol.req_stockCodeMarketid);
				}else
					result = mNewsCacheDao.selectPullRefreshData(
							protocol.req_funType, 
							protocol.req_stockCodeMarketid,
							protocol.req_msgType,
							protocol.req_count);
			}else if(protocol.direction == 1){//否则说明是上拉加载更多的请求
				
				Logger.d("tag", "2-loadMoreDatas ptl.req_sinceId = "+protocol.req_sinceId);
				
				if(protocol.req_funType.equals("S4")) {
					result = mNewsCacheDao.selectMultipleFromCodeMart(
						protocol.req_funType
						, protocol.req_stockCodeMarketid);
				}else {
					result = mNewsCacheDao.selectLoadMoreData( 
							protocol.req_funType, 
							protocol.req_stockCodeMarketid,
							protocol.req_msgType,
							protocol.req_sinceId, 
							protocol.req_count);
				}
			}
			
			// 输出到内存中
			cache2Datas(protocol, result);  
			
			//加载满标志
			cacheLoadFull = 0x00;//protocol.isLoadCache? 0x00 : 0x01;
			if(result == null || result.length == 0){
				if(protocol.isHasMemory){
					cacheLoadFull = 0x01;
					protocol.isDataLoadFull = 
							(netLoadFull & cacheLoadFull) > 0? true : false;
				}
			}
			System.out.println("2-readCache =========="+protocol.getFlag()); 
			
			if(result == null && 
					!protocol.isDataLoadFull) {
				return null;
			}
			
			return protocol;
		}
	
	/**
	 * 
	 * @param list 
	 * @param newsId
	 * @return 返回list中是否包含newsId
	 */
	private boolean isHasNewsId(ArrayList<Item_newsListItemData> list, String newsId) {
		boolean isHas = false;
		@SuppressWarnings("unchecked")
		ArrayList<Item_newsListItemData> resp_newsDataList1 = 
				(ArrayList<Item_newsListItemData>) list.clone();
		for(Item_newsListItemData tempItem : resp_newsDataList1) {
			
			if(newsId.equals(tempItem.newsId)){
				isHas = true;
				break;
			}
		}
		
		return isHas;
	}
	
	/**
	 * 缓存输出到内存中
	 * @param protocol
	 * @param result
	 */
	private void cache2Datas(NewsListProtocol protocol, String[][] result) {
		toDatas(1, protocol, result);
	}
	
	/**
	 * 网络数据输出到内存中
	 * @param protocol
	 */
	private void net2Datas(NewsListProtocol protocol) {
		toDatas(0, protocol, null);
	}
	
	private void toDatas(int type, NewsListProtocol protocol, String[][] result) {
		if(type == 0) {
			// 清理内存,下拉刷新
			if(protocol.isHasMemory && 
					protocol.direction == 0 && 
						protocol.toCacheNewsList.size() > 0) {
				protocol.clearMemory();
			}
			
			// 设置内存
			@SuppressWarnings("unchecked")
			ArrayList<Item_newsListItemData> tempnewsList1 = 
					(ArrayList<Item_newsListItemData>) protocol.toCacheNewsList.clone();
			for(Item_newsListItemData item : tempnewsList1) {
				if(!isHasNewsId(protocol.resp_newsDataList, item.newsId))
					protocol.resp_newsDataList.add(item);
			}  
			
		}else if(type == 1) {
			//读取数据列表缓存数据
			for(int i =0; result!=null && i < result.length; i++){  
				for(int j = 0; j < result[i].length;j++){
					if(j == 1){
					    //资讯列表
						if(result[i] == null || result[i][j] == null)
							continue;
						Item_newsListItemData item = GsonHelper.objectFromJson(result[i][1], 
								new TypeToken<Item_newsListItemData>(){}.getType());
						
						if(!isHasNewsId(protocol.resp_newsDataList, item.newsId)) {
							item.readFlag = result[i][2];					
							item.timeShow = MultiGroup.getCustomFormat(item.time);
							protocol.resp_newsDataList.add(item);
						}
					}
				}
			}
		}
		
		// 分组
		protocol.resp_NewsGroupList = 
			mGroup.toGroup(
					protocol.resp_newsDataList
					, protocol.isMultiGroup);
		// 是否有内存的标示
		protocol.isHasMemory = 
				(protocol.resp_newsDataList.size() > 0)? true : false;
	}
}

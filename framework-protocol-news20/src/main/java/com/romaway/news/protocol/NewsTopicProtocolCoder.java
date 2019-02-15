package com.romaway.news.protocol;

import android.content.Context;
import android.text.TextUtils;

import com.romalibs.utils.Logger;
import com.romalibs.utils.gson.GsonHelper;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.news.protocol.info.Item_newsTopic;
import com.romaway.news.protocol.util.MultiGroup;
import com.romaway.news.sql.NewsCacheDao;
import com.romaway.news.sql.SqlContent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** 
 * 资讯详情协议
 * @author  万籁唤
 * 创建时间：2016年3月24日 下午2:47:15
 * @version 1.0 
 */
public class NewsTopicProtocolCoder extends AProtocolCoder<NewsTopicProtocol> {

	private Context context;
	private NewsCacheDao mTopicNewsCacheDao;
	public NewsTopicProtocolCoder(Context context){
		this.context = context;
		mTopicNewsCacheDao = new NewsCacheDao(context, SqlContent.CACHE_TOPIC_DB_NAME);
	}
	
	@Override
	protected byte[] encode(NewsTopicProtocol protocol) {
		// TODO Auto-generated method stub
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(NewsTopicProtocol protocol)
			throws ProtocolParserException {
		// TODO Auto-generated method stub
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();
		
		// json数据转换成协议数据
		jsonToProtoocol(result, protocol);
	}
	
	private NewsTopicProtocol jsonToProtoocol(String jsonData, final NewsTopicProtocol protocol){
		
		Logger.d("tag", "1-jsonToProtoocoljsonToProtoocoljsonToProtoocol");
		protocol.isHasMemory = false;
		if(protocol.resp_topicDatas != null && protocol.resp_topicDatas.size() > 0) {
			protocol.isHasMemory = true;
			return protocol;
		}
		
		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			
			if (jsonObject.has("news")) {//资讯列表数据
				JSONObject newsJSONArray = jsonObject.getJSONObject("news");
				
				if (newsJSONArray.has("header")) {
					JSONArray topicHeader = newsJSONArray.getJSONArray("header");
					int len = topicHeader.length();
					for(int i = 0; i < len; i++){
						JSONObject topicArr = topicHeader.getJSONObject(i);
						if(topicArr.has("banner"))
							protocol.resp_banner = topicArr.getString("banner");
						if(topicArr.has("guidance"))
							protocol.resp_guidance = topicArr.getString("guidance");
					}
				}
				
				if (newsJSONArray.has("sections")) {
					JSONArray topicSections = newsJSONArray.getJSONArray("sections");
					int len = topicSections.length();
					protocol.resp_sectionCount = len;
					for(int i = 0; i < len; i++){
						JSONObject topicSectionsObject  = topicSections.getJSONObject(i);
						if(topicSectionsObject.has("section")) {
							Map<String, Item_newsTopic> maps = new HashMap<String, Item_newsTopic>();
							if(topicSectionsObject.has("list")) {
								JSONArray topicList = topicSectionsObject.getJSONArray("list");
								int len1 = topicList.length();
								for(int j = 0; j < len1; j++){
	//								JSONObject jSONObject = topicList.getJSONObject(j);
	//								String topicItem = jSONObject.toString();
									Item_newsTopic item = new Item_newsTopic();
									if(topicSectionsObject.has("section"))
										item.select = topicSectionsObject.getString("section");
									
									JSONObject selectArr = topicList.getJSONObject(j);
									if(selectArr.has("imgsrc1"))
										item.imgsrc1 = selectArr.getString("imgsrc1");
									if(selectArr.has("title"))
										item.title = selectArr.getString("title");
									if(selectArr.has("time")) {
										item.time = selectArr.getString("time");
												MultiGroup.getCustomFormat(selectArr.getString("time"));
										item.timeShow = MultiGroup.getCustomFormat(item.time);
									}
									if(selectArr.has("digest"))
										item.digest = selectArr.getString("digest");
									if(selectArr.has("layout"))
										item.layout = selectArr.getString("layout");
									if(selectArr.has("newsId"))
										item.newsId = selectArr.getString("newsId");
									if(selectArr.has("newsType"))
										item.newsType = selectArr.getString("newsType");
									if(selectArr.has("source"))
										item.source = selectArr.getString("source");
									if(selectArr.has("topic"))
										item.topic = selectArr.getString("topic");
									
									//已读未读标记
									item.readFlag = 
											mTopicNewsCacheDao.selectReadFlag(item.topic,
													item.newsId);
									
									maps.put("topic"+j, item);
								}
							}
							
							if(protocol.resp_topicDatas == null)
								protocol.resp_topicDatas = new ArrayList<Map<String,Item_newsTopic>>();
							protocol.resp_topicDatas.add(maps);
						}
					}
				}
			}
			
			// 设置内存
			protocol.isHasMemory = (protocol.resp_topicDatas != null && 
					protocol.resp_topicDatas.size() > 0)? true : false;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		Logger.d("tag", "2-jsonToProtoocoljsonToProtoocoljsonToProtoocol");
		return protocol;
	}
	
	@Override
	protected void saveCache(String key, NewsTopicProtocol protocol)
			throws ProtocolParserException {
		// TODO Auto-generated method stub
		super.saveCache(key, protocol);
		
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();
		Logger.d("tag", "NewsDetailsProtocolCoder saveCache result = "+result);
		
		//++ 调试之用
		//result = FileSystem.getFromAssets(context, 
		//		"QuanShang/zixun2_0/newsDetails.json");
		
		//-- 调试之用
		if(!TextUtils.isEmpty(result)) {
			if(!TextUtils.isEmpty(protocol.req_topic) &&
					!mTopicNewsCacheDao.isExistFunTypeNewsId(protocol.req_funType, 
					protocol.req_newsId))
				// 保存整个专题详情列表总数据
				mTopicNewsCacheDao.insert1(protocol.req_funType, 
					protocol.req_newsId, result, ""+System.currentTimeMillis(), "true");
		}
		
		// 保存专题的选项数据
		if(protocol.resp_topicDatas != null) {
			// gIndex 组的选项
			for(int gIndex = 0; gIndex < protocol.resp_topicDatas.size(); gIndex++) {
				Map<String,Item_newsTopic> maps = protocol.resp_topicDatas.get(gIndex);
				for (String key1 : maps.keySet()) {
					Item_newsTopic value = maps.get(key1);
					if(!mTopicNewsCacheDao.isExistFunTypeNewsId(value.topic, 
							value.newsId)) {
						
						String jsonValue = GsonHelper.objectToJson(value);
						// 保存专题的选项数据
						mTopicNewsCacheDao.insert(
								value.topic, 
								value.newsId, 
								jsonValue, 
								""+System.currentTimeMillis());
					}
				}
			}
		}
		
	}
	
	@Override
	protected NewsTopicProtocol readCache(String key,
			NewsTopicProtocol protocol) throws ProtocolParserException {
		// TODO Auto-generated method stub
		
		//json数据转换成协议数据
		String json = null;
		if(!TextUtils.isEmpty(protocol.req_topic))// 专题缓存
			json = mTopicNewsCacheDao.selectDetails(protocol.req_funType, protocol.req_newsId);
		
		if(TextUtils.isEmpty(json))
			return null;
		
		jsonToProtoocol(json, protocol);
		return protocol;
	}

}

package com.romaway.news.protocol;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.romalibs.utils.Logger;
import com.romalibs.utils.gson.GsonHelper;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.lang.StringUtils;
import com.romaway.news.protocol.info.Item_newsListItemData;
import com.romaway.news.protocol.util.MultiGroup;
import com.romaway.news.sql.NewsCacheDao;
import com.romaway.news.sql.SqlContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dell on 2016/8/17.
 */
public class NewsTZRLProtocolCoder extends AProtocolCoder<NewsTZRLProtocol> {

    private final Context context;
    private NewsCacheDao mNewsCacheDao;
    
    public NewsTZRLProtocolCoder(Context context){
        this.context = context;
        mNewsCacheDao = new NewsCacheDao(context,
                SqlContent.CACHE_LIST_DB_NAME);
    }

    @Override
    protected byte[] encode(NewsTZRLProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(NewsTZRLProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null?new byte[0]:protocol.getReceiveData());
        String result = r.getString();
        protocol.toCacheElseNewsList = new ArrayList<Item_newsListItemData>();
        protocol.toCacheTodayNewsList = new ArrayList<Item_newsListItemData>();
        try {
            JSONObject data = new JSONObject(result);
            if(data.has("todayNews")){
                JSONArray array = new JSONArray(data.get("todayNews").toString());
                for(int i=0; i<array.length(); i++){
                    Item_newsListItemData bean = new Item_newsListItemData();
                    JSONObject object = (JSONObject) array.get(i);
                    if(object.has("date")){
                        bean.time = object.getString("date");
                    }
                    if(object.has("id")){
                        bean.newsId = object.getString("id");
                    }
                    if(object.has("stock")){
                        bean.code = object.getString("stock");
                    }
                    if(object.has("title")){
                        bean.title = object.getString("title");
                    }
                    if(object.has("field")){
                        bean.descrip = object.getString("field");
                    }
                    bean.imgsrc1= "1";          //作为标识是否为当日要闻
                    protocol.toCacheTodayNewsList.add(bean);
                }
            }
            if(data.has("elseNews")){
                JSONArray array = new JSONArray(data.get("elseNews").toString());
                for(int i=0; i<array.length(); i++){
                    Item_newsListItemData bean = new Item_newsListItemData();
                    JSONObject object = (JSONObject) array.get(i);
                    if(object.has("date")){
                        bean.time = object.getString("date");
                    }
                    if(object.has("id")){
                        bean.newsId = object.getString("id");
                    }
                    if(object.has("stock")){
                        bean.code = object.getString("stock");
                    }
                    if(object.has("title")){
                        bean.title = object.getString("title");
                    }
                    if(object.has("field")){
                        bean.descrip = object.getString("field");
                    }

                    bean.imgsrc1= "0";
                    protocol.toCacheElseNewsList.add(bean);
                }
            }
            toDatas(0, protocol, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void toDatas(int type, NewsTZRLProtocol protocol, String[][] result) {
        if(type == 0) {
            // 清理内存,下拉刷新
            if(protocol.isHasMemory &&
                    protocol.direction == 0 &&
                    protocol.toCacheElseNewsList.size() > 0 || protocol.toCacheTodayNewsList.size() > 0) {
                protocol.clearMemory();
            }

            // 设置内存
            @SuppressWarnings("unchecked")
            ArrayList<Item_newsListItemData> tempnewsList1 =
                    (ArrayList<Item_newsListItemData>) protocol.toCacheElseNewsList.clone();
            for(Item_newsListItemData item : tempnewsList1) {
                if(!isHasNewsId(protocol.resp_else_list, item.newsId))
                    protocol.resp_else_list.add(item);
            }
            ArrayList<Item_newsListItemData> tempnewsList2 =
                    (ArrayList<Item_newsListItemData>) protocol.toCacheTodayNewsList.clone();
            for(Item_newsListItemData item : tempnewsList2) {
                if(!isHasNewsId(protocol.resp_today_list, item.newsId))
                    protocol.resp_today_list.add(item);
            }
        }else if(type == 1) { }

/*        // 分组
        protocol.resp_NewsGroupList = new ArrayList<NewsListGroupContiner>();
        NewsListGroupContiner c = new NewsListGroupContiner();
        c.setGroupList(protocol.resp_today_list);
        c.setGroupName("当日重大事件");
        protocol.resp_NewsGroupList.add(new NewsListGroupContiner().setGroupList());*/
        // 是否有内存的标示
        protocol.isHasMemory =
                (protocol.toCacheElseNewsList.size() > 0 || protocol.toCacheTodayNewsList.size() > 0 )? true : false;
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
    @Override
    protected void saveCache(String key, NewsTZRLProtocol protocol) throws ProtocolParserException {
        // TODO Auto-generated method stub
        super.saveCache(key, protocol);

        System.out.println("1-saveCache ========== "+protocol.getFlag()/*System.currentTimeMillis()*/);
        if(protocol.toCacheElseNewsList == null || protocol.toCacheTodayNewsList == null)
            return;

        @SuppressWarnings("unchecked")
        ArrayList<Item_newsListItemData> tempnewsList1 =
                (ArrayList<Item_newsListItemData>) protocol.toCacheTodayNewsList.clone();
        //数据列表缓存
        if(tempnewsList1 != null){
            for(int i = 0; i < tempnewsList1.size(); i++) {
                Item_newsListItemData cacheNewsItem = tempnewsList1.get(i);

                String stockCode = null;//不带下划线的股票代码和市场ID
                if(!TextUtils.isEmpty(cacheNewsItem.code))
                    stockCode = cacheNewsItem.code.replace("_", "");
                Logger.d("tag", "资讯缓存：newsId = " + cacheNewsItem.newsId);

                //重复就不添加
              /*  if(mNewsCacheDao.isExistFunTypeNewsId(
                        protocol.req_funType,
                        cacheNewsItem.newsId))
                    continue;

                String jsonValue = GsonHelper.objectToJson(cacheNewsItem);
                Logger.d("tag", "stockCodestockCodestockCode: "+stockCode);
                mNewsCacheDao.insert(
                        protocol.req_funType
                        , cacheNewsItem.newsId
                        , stockCode
                        , jsonValue
                        , ""+System.currentTimeMillis());*/
                if(!this.mNewsCacheDao.isExistFunTypeNewsId("G4", cacheNewsItem.newsId)) {
                    String jsonValue = GsonHelper.objectToJson(cacheNewsItem);
                    Logger.d("tag", "stockCodestockCodestockCode: " + stockCode);
                    this.mNewsCacheDao.insert("G4", cacheNewsItem.newsId, stockCode, jsonValue, "" + System.currentTimeMillis());
                }else {
                    String jsonValue = GsonHelper.objectToJson(cacheNewsItem);
                    mNewsCacheDao.updateItemData("G4", cacheNewsItem.newsId, jsonValue);
                }
            }
        }

        ArrayList<Item_newsListItemData> tempnewsList2 =
                (ArrayList<Item_newsListItemData>) protocol.toCacheElseNewsList.clone();
        //数据列表缓存
        if(tempnewsList2 != null){
            for(int i = 0; i < tempnewsList2.size(); i++) {
                Item_newsListItemData cacheNewsItem = tempnewsList2.get(i);

                String stockCode = null;//不带下划线的股票代码和市场ID
                if(!TextUtils.isEmpty(cacheNewsItem.code))
                    stockCode = cacheNewsItem.code.replace("_", "");
                Logger.d("tag", "资讯缓存：newsId = " + cacheNewsItem.newsId);

                //重复就不添加
               /* if(mNewsCacheDao.isExistFunTypeNewsId(
                        protocol.req_funType,
                        cacheNewsItem.newsId))
                    continue;

                String jsonValue = GsonHelper.objectToJson(cacheNewsItem);
                Logger.d("tag", "stockCodestockCodestockCode: "+stockCode);
                mNewsCacheDao.insert(
                        protocol.req_funType
                        , cacheNewsItem.newsId
                        , stockCode
                        , jsonValue
                        , "" + System.currentTimeMillis());*/
                if(!this.mNewsCacheDao.isExistFunTypeNewsId("G4", cacheNewsItem.newsId)) {
                    String jsonValue = GsonHelper.objectToJson(cacheNewsItem);
                    Logger.d("tag", "stockCodestockCodestockCode: " + stockCode);
                    this.mNewsCacheDao.insert("G4", cacheNewsItem.newsId, stockCode, jsonValue, "" + System.currentTimeMillis());
                }else {
                    String jsonValue = GsonHelper.objectToJson(cacheNewsItem);
                    mNewsCacheDao.updateItemData("G4", cacheNewsItem.newsId, jsonValue);
                }
            }

        }
        System.out.println("2-saveCache ========== "+protocol.getFlag()/*System.currentTimeMillis()*/);
    }

    @Override
    protected NewsTZRLProtocol readCache(String key, NewsTZRLProtocol protocol) throws ProtocolParserException {
        return printfCacheDecode(protocol);
    }

    private NewsTZRLProtocol printfCacheDecode(NewsTZRLProtocol protocol) {
        //		if(true)
        //		return nul  l;
        String[][] result = null;
        System.out.println("1-readCache =========="+protocol.getFlag());

        if(protocol.direction == 0) {//说明是下拉刷新或者是点击了刷新按钮
            //读取db缓存
                result = mNewsCacheDao.selectPullRefreshDataForUptime(
                        protocol.req_funType,
                        "",
                        200);
        }else if(protocol.direction == 1){
        }

        // 输出到内存中
        cache2Datas(protocol, result);


        if(result == null &&
                !protocol.isDataLoadFull) {
            return null;
        }

        return protocol;
    }
    private void cache2Datas(NewsTZRLProtocol protocol, String[][] result){

        //读取数据列表缓存数据
        if(result != null){
            for(int i = result.length - 1; result!=null && i >=0 ; i--){
                        //资讯列表
                        if(result[i] == null || result[i][1] == null)
                            continue;
                        Item_newsListItemData item = GsonHelper.objectFromJson(result[i][1],
                                new TypeToken<Item_newsListItemData>(){}.getType());

                        if(!isHasNewsId(protocol.resp_else_list, item.newsId) && !StringUtils.isEmpty(item.imgsrc1) && item.imgsrc1.equals("0")) {
                            //item.readFlag = result[i][2];
                            item.timeShow = MultiGroup.getCustomFormat(item.time);
                            protocol.resp_else_list.add(item);
                        }
                        if(!isHasNewsId(protocol.resp_today_list, item.newsId) && !StringUtils.isEmpty(item.imgsrc1) && item.imgsrc1.equals("1")) {
                           // item.readFlag = result[i][2];
                            item.timeShow = MultiGroup.getCustomFormat(item.time);
                            protocol.resp_today_list.add(item);
                        }
                    }
            }
    }
    }

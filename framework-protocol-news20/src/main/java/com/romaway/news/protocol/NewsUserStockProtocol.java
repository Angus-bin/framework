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
 * Created by dell on 2016/7/15.
 */
public class NewsUserStockProtocol extends AProtocol {
    public String req_funType;
    public String req_sinceId;
    public int req_count;
    public String req_stockCode;
    /**
     * 是否进行分组，比如滚动时间轴的有分组功能
     */
    public boolean isMultiGroup = false;

    /**
     * 0:下拉刷新请求；1：上拉加载更多请求
     */
    public int direction = 0;
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


    public NewsUserStockProtocol(String flag) {
        super(flag,false);
        isJson = true;
        subFunUrl = "/api/news20/select";
    }


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
        reqNewsList(funType, maxId, sinceId, stockCodeMarketid, reqCount, listener);
    }

    /**
     * 请求数据
     * @param funType 资讯栏目类别
     * @param maxId 请求比加载更新的数据
     * @param sinceId 请求加载更多时比sinceId更旧的数据
     * @param stockCode 股票代码市场ID 如：6004462
     * @param reqCount 请求返回的数据个数
     * @param listener
     */
    private void reqNewsList(String funType, String maxId, String sinceId, String stockCode, int reqCount, INetReceiveListener listener){

        if(TextUtils.isEmpty(funType)) {
            Logger.d("资讯列表协议", "请求列表数据- funType：" + funType + ", maxId: " + maxId + ",sinceId:" + sinceId + ",stockCode:" + stockCode);
            return ;
        }
        if(req_count < 0)
            req_count = 0;

        this.req_funType = funType;
       // req_maxId = maxId;
        this.req_sinceId = sinceId;
       // req_stockCodeMarketid = stockCodeMarketid;
        this.req_count = reqCount;

        this.req_stockCode = stockCode;
//        StringBuilder builder = new StringBuilder();
//        builder.append(subFunUrl);
//        //资讯类别
//        builder.append("req_funType=");
//        builder.append(req_funType);


        //资讯sinceId
//        if(!TextUtils.isEmpty(req_sinceId)){
//            builder.append("&req_sinceId=");
//            builder.append(req_sinceId);
//        }
//
//
//        //资讯请求的个数
//        builder.append("&req_count=");
//        builder.append(req_count);
//        String url = builder.toString();

        Logger.d("tag", "reqLoadMoreData url :"+ subFunUrl);

        ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(ProtocolConstant.SERVER_FW_NEWS2, subFunUrl);
        NetMsg msg = new NetMsg(getFlag(), EMsgLevel.normal, this, connInfo, false, listener);
        msg.sendByGet = false;
        NetMsgSenderProxy.getInstance().send(msg);
    }

    /**
     * 清理内存
     */
    @SuppressWarnings("unchecked")
    public void clearMemory() {
        resp_newsDataList.clear();
        isHasMemory = false;
    }
}

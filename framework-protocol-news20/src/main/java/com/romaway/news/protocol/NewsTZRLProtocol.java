package com.romaway.news.protocol;


import com.romaway.common.net.EMsgLevel;
import com.romaway.common.net.conn.ConnInfo;
import com.romaway.common.net.receiver.INetReceiveListener;
import com.romaway.common.net.sender.NetMsgSenderProxy;
import com.romaway.common.protocol.AProtocol;
import com.romaway.common.protocol.service.NetMsg;
import com.romaway.news.protocol.info.Item_newsListItemData;
import com.romaway.news.protocol.info.NewsListGroupContiner;

import java.util.ArrayList;

/**
 * Created by dell on 2016/8/17.
 * 投资日历协议类
 */
public class NewsTZRLProtocol extends AProtocol {
    public String req_id;
    public String req_type;
    public static final String req_funType = "G4";
    public int direction;

    public ArrayList<NewsListGroupContiner> resp_NewsGroupList = new ArrayList<NewsListGroupContiner>();
    public ArrayList<Item_newsListItemData> resp_today_list = new ArrayList<Item_newsListItemData>();
    public ArrayList<Item_newsListItemData> resp_else_list = new ArrayList<Item_newsListItemData>();
    public ArrayList<Item_newsListItemData> toCacheTodayNewsList = new ArrayList<Item_newsListItemData>();
    public ArrayList<Item_newsListItemData> toCacheElseNewsList = new ArrayList<Item_newsListItemData>();


    public NewsTZRLProtocol(String flag) {
        super(flag,false);
        this.isJson = true;
        this.subFunUrl = "/api/news20/detail?";
    }
    public void req(String req_id, String req_type, INetReceiveListener listener) {
        this.req_id = req_id;
        this.req_type = req_type;
        StringBuilder builder = new StringBuilder();
        builder.append(this.subFunUrl);
        builder.append("id=");
        builder.append(this.req_id);
        builder.append("&type=");
        builder.append(this.req_type);
        String url = builder.toString();
        ConnInfo connInfo = ConnInfo.newConnectionInfoSockePost(205, url);
        NetMsg msg = new NetMsg(this.getFlag(), EMsgLevel.normal, this, connInfo, false, listener);
        NetMsgSenderProxy.getInstance().send(msg);
    }

    public void clearMemory() {
        resp_today_list.clear();
        resp_else_list.clear();
        isHasMemory = false;
    }
}

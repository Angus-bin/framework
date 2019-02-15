package com.romaway.common.protocol.zx;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockNewsListEntity;
import com.romaway.common.protocol.tougu.entity.GroupInfoEntity;
import com.romaway.common.protocol.tougu.entity.NewsStockEntity;
import com.romaway.common.protocol.zx.entity.NewsDataListEntity;
import com.romaway.common.protocol.zx.entity.NewsTopBarListEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/3.
 */
public class NewsDataListProtocolCoder extends AProtocolCoder<NewsDataListProtocol> {
    @Override
    protected byte[] encode(NewsDataListProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(NewsDataListProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("NewsDataListProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)){
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errorCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errorMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("item")) {
                    if ("128".equals(protocol.req_sort_id)) { // 自选
                        BaseJSONArray jsonArray = new BaseJSONArray(jsonObject.getString("item"));
                        ArrayList<StockNewsListEntity> list = new ArrayList<StockNewsListEntity>();
                        int count = jsonArray.length();
                        for (int i = 0; i < count; i++) {
                            BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            StockNewsListEntity entity = new StockNewsListEntity();
                            if (jsonObject1.has("id")) {
                                entity.setId(jsonObject1.getString("id"));
                            }
                            if (jsonObject1.has("source")) {
                                entity.setSource(jsonObject1.getString("source"));
                            }
                            if (jsonObject1.has("title")) {
                                entity.setTitle(jsonObject1.getString("title"));
                            }
                            if (jsonObject1.has("time")) {
                                entity.setTime(jsonObject1.getString("time"));
                            }
                            if (jsonObject1.has("no")) {
                                entity.setNo(jsonObject1.getString("no"));
                            }
                            if (jsonObject1.has("grab")) {
                                entity.setGrab(jsonObject1.getString("grab"));
                            }
                            if (jsonObject1.has("type")) {
                                entity.setType(jsonObject1.getString("type"));
                            }
                            if (jsonObject1.has("C_SNo")) {
                                entity.setC_SNo(jsonObject1.getString("C_SNo"));
                            }
                            if (jsonObject1.has("S_Name")) {
                                entity.setS_Name(jsonObject1.getString("S_Name"));
                            }
                            list.add(entity);
                        }
                        protocol.setList_stock(list);
                    } else {
                        BaseJSONArray jsonArray = new BaseJSONArray(jsonObject.getString("item"));
                        ArrayList<GroupInfoEntity> list = new ArrayList<GroupInfoEntity>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            GroupInfoEntity entity = new GroupInfoEntity();
                            if (jsonObject1.has("prosandcons")) {
                                entity.setProsandcons(jsonObject1.getString("prosandcons"));
                            }
                            if (jsonObject1.has("newsname")) {
                                entity.setNewsname(jsonObject1.getString("newsname"));
                            }
                            if (jsonObject1.has("newscomment")) {
                                entity.setNewscomment(jsonObject1.getString("newscomment"));
                            }
                            if (jsonObject1.has("newsimage")) {
                                entity.setNewsimage(jsonObject1.getString("newsimage"));
                            }
                            if (jsonObject1.has("publishtime")) {
                                entity.setPublishtime(jsonObject1.getString("publishtime"));
                            }
                            if (jsonObject1.has("readcounts")) {
                                entity.setReadcounts(jsonObject1.getString("readcounts"));
                            }
                            if (jsonObject1.has("weburl")) {
                                entity.setWeburl(jsonObject1.getString("weburl"));
                            }
                            if (jsonObject1.has("newsid")) {
                                entity.setNewsid(jsonObject1.getString("newsid"));
                            }
                            if (jsonObject1.has("heatlevel")) {
                                entity.setHeatlevel(jsonObject1.getString("heatlevel"));
                            }
                            if (jsonObject1.has("goodnum")) {
                                entity.setGoodnum(jsonObject1.getString("goodnum"));
                            }
                            if (jsonObject1.has("isgood")) {
                                entity.setIsgood(jsonObject1.getString("isgood"));
                            }
                            if (jsonObject1.has("share")) {
                                entity.setStock(jsonObject1.getString("share"));
                            }
                            if (jsonObject1.has("isread")) {
                                entity.setIsread(jsonObject1.getString("isread"));
                            }
                            list.add(entity);
                        }
                        protocol.setList(list);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

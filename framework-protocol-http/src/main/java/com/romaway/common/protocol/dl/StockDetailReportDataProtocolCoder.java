package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockReportEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2018/3/1.
 */
public class StockDetailReportDataProtocolCoder extends AProtocolCoder<StockDetailReportDataProtocol> {
    @Override
    protected byte[] encode(StockDetailReportDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailReportDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailReportDataProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errorCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errorMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("data")) {
                    BaseJSONArray jsonArray = new BaseJSONArray(jsonObject.getString("data"));
                    ArrayList<StockReportEntity> list = new ArrayList<StockReportEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StockReportEntity entity = new StockReportEntity();
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
                        if (jsonObject1.has("issue")) {
                            entity.setIssue(jsonObject1.getString("issue"));
                        }
                        if (jsonObject1.has("grade")) {
                            entity.setGrade(jsonObject1.getString("grade"));
                        }
                        if (jsonObject1.has("type")) {
                            entity.setType(jsonObject1.getString("type"));
                        }
                        list.add(entity);
                    }
                    protocol.setList(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

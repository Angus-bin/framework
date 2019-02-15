package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.HoldStockListEntity;
import com.romaway.common.utils.DES3;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2017/6/7.
 */
public class HoldStockProtocolCoder extends AProtocolCoder<HoldStockProtocol> {
    @Override
    protected byte[] encode(HoldStockProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(HoldStockProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HoldStockProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)){
                BaseJSONObject jsonObject = new BaseJSONObject(result);
                if (jsonObject.has("error")) {
                    protocol.resp_errorCode = jsonObject.getString("error");
                }
                if (jsonObject.has("msg")) {
                    protocol.resp_errorMsg = jsonObject.getString("msg");
                }
                if (jsonObject.has("xy")) {
                    String str = jsonObject.getString("xy").toUpperCase();
                    DES3.setIv(jsonObject.getString("iv"));
                    Logger.d("HoldStockProtocolCoder", "decode >>> result body = " + DES3.decode(DES3.decodeHex(str)));
                    BaseJSONArray jsonArray = new BaseJSONArray(DES3.decode(DES3.decodeHex(str)));
                    ArrayList<HoldStockListEntity> list = new ArrayList<HoldStockListEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        HoldStockListEntity item = new HoldStockListEntity();
                        if (jsonObject1.has("shareid")) {
                            item.setShareid(jsonObject1.getString("shareid"));
                        }
                        if (jsonObject1.has("sharename")) {
                            item.setSharename(jsonObject1.getString("sharename"));
                        }
                        if (jsonObject1.has("sharenum")) {
                            item.setSharenum(jsonObject1.getString("sharenum"));
                        }
                        if (jsonObject1.has("dealprice")) {
                            item.setDealprice(jsonObject1.getString("dealprice"));
                        }
                        if (jsonObject1.has("nowprice")) {
                            item.setNowprice(jsonObject1.getString("nowprice"));
                        }
                        if (jsonObject1.has("earningsratetoday")) {
                            item.setEarningsratetoday(jsonObject1.getString("earningsratetoday"));
                        }
                        if (jsonObject1.has("earningsrateall")) {
                            item.setEarningsrateall(jsonObject1.getString("earningsrateall"));
                        }
                        list.add(item);
                    }
                    protocol.setList(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

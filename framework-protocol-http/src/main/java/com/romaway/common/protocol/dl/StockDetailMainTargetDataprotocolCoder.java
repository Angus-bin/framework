package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.entity.StockMainTargetEntity;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import java.util.ArrayList;

/**
 * Created by hongrb on 2018/3/5.
 */
public class StockDetailMainTargetDataprotocolCoder extends AProtocolCoder<StockDetailMainTargetDataprotocol> {
    @Override
    protected byte[] encode(StockDetailMainTargetDataprotocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailMainTargetDataprotocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailGsgkDataProtocolCoder", "decode >>> result body = " + result);
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
                    ArrayList<StockMainTargetEntity> list = new ArrayList<StockMainTargetEntity>();
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        BaseJSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        StockMainTargetEntity entity = new StockMainTargetEntity();
                        if (jsonObject1.has("M_No")) {
                            entity.setM_No(jsonObject1.getString("M_No"));
                        }
                        if (jsonObject1.has("M_SNo")) {
                            entity.setM_SNo(jsonObject1.getString("M_SNo"));
                        }
                        if (jsonObject1.has("M_Create")) {
                            entity.setM_Create(jsonObject1.getString("M_Create"));
                        }
                        if (jsonObject1.has("M_Jbmgsy")) {
                            entity.setM_Jbmgsy(jsonObject1.getString("M_Jbmgsy"));
                        }
                        if (jsonObject1.has("M_Mgjzc")) {
                            entity.setM_Mgjzc(jsonObject1.getString("M_Mgjzc"));
                        }
                        if (jsonObject1.has("M_Mggjj")) {
                            entity.setM_Mggjj(jsonObject1.getString("M_Mggjj"));
                        }
                        if (jsonObject1.has("M_Mgwfplr")) {
                            entity.setM_Mgwfplr(jsonObject1.getString("M_Mgwfplr"));
                        }
                        if (jsonObject1.has("M_Mgjyxjl")) {
                            entity.setM_Mgjyxjl(jsonObject1.getString("M_Mgjyxjl"));
                        }
                        if (jsonObject1.has("M_Yyzsr")) {
                            entity.setM_Yyzsr(jsonObject1.getString("M_Yyzsr"));
                        }
                        if (jsonObject1.has("M_Mlr")) {
                            entity.setM_Mlr(jsonObject1.getString("M_Mlr"));
                        }
                        if (jsonObject1.has("M_Gsjlr")) {
                            entity.setM_Gsjlr(jsonObject1.getString("M_Gsjlr"));
                        }
                        if (jsonObject1.has("M_Kfjlr")) {
                            entity.setM_Kfjlr(jsonObject1.getString("M_Kfjlr"));
                        }
                        if (jsonObject1.has("M_Yyzsrtbzz")) {
                            entity.setM_Yyzsrtbzz(jsonObject1.getString("M_Yyzsrtbzz"));
                        }
                        if (jsonObject1.has("M_Gsjlrtbzz")) {
                            entity.setM_Gsjlrtbzz(jsonObject1.getString("M_Gsjlrtbzz"));
                        }
                        if (jsonObject1.has("M_Kfjlrtbzz")) {
                            entity.setM_Kfjlrtbzz(jsonObject1.getString("M_Kfjlrtbzz"));
                        }
                        if (jsonObject1.has("M_Yyzsrgdhbzz")) {
                            entity.setM_Yyzsrgdhbzz(jsonObject1.getString("M_Yyzsrgdhbzz"));
                        }
                        if (jsonObject1.has("M_Gsjlrgdhbzz")) {
                            entity.setM_Gsjlrgdhbzz(jsonObject1.getString("M_Gsjlrgdhbzz"));
                        }
                        if (jsonObject1.has("M_Kfjlrgdhbzz")) {
                            entity.setM_Kfjlrgdhbzz(jsonObject1.getString("M_Kfjlrgdhbzz"));
                        }
                        if (jsonObject1.has("M_Mll")) {
                            entity.setM_Mll(jsonObject1.getString("M_Mll"));
                        }
                        if (jsonObject1.has("M_Jll")) {
                            entity.setM_Jll(jsonObject1.getString("M_Jll"));
                        }
                        if (jsonObject1.has("M_Grab")) {
                            entity.setM_Grab(jsonObject1.getString("M_Grab"));
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

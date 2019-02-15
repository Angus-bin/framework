package com.romaway.common.protocol.hq;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.dl.StockDetailGsgkDataProtocol;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.NumberUtils;
import com.romaway.commons.log.Logger;

/**
 * Created by Administrator on 2018/5/24.
 */
public class HQNewCodeListProtocolCoder2 extends AProtocolCoder<HQNewCodeListProtocol2> {
    @Override
    protected byte[] encode(HQNewCodeListProtocol2 protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(HQNewCodeListProtocol2 protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("HQNewCodeListProtocolCoder2", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
                BaseJSONArray jsonArray = new BaseJSONArray(result);
                Logger.d("HQNewCodeListProtocolCoder2", "jsonArray = " + jsonArray.length());
                protocol.resp_pszAddCode_s = new String[jsonArray.length()];
                protocol.resp_pszName_s = new String[jsonArray.length()];
                protocol.resp_pszPYCode_s = new String[jsonArray.length()];
                protocol.resp_wMarketID_s = new int[jsonArray.length()];
                protocol.resp_wCodeType_s = new int[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    BaseJSONArray jsonArray1 = jsonArray.getJSONArray(i);
                    String codeStr = jsonArray1.toString().substring(1, jsonArray1.toString().length() - 1);
//                    Logger.d("HQNewCodeListProtocolCoder2", "jsonArray1.toString() = " + jsonArray1.toString());
                    String[] codes = codeStr.split(",");
//                    Logger.d("HQNewCodeListProtocolCoder2", "codes[0] = " + codes[0]);
                    if ("1".equals(codes[0])) {
                        codes[0] = "2";
                    } else if ("2".equals(codes[0])) {
                        codes[0] = "1";
                    }
                    protocol.resp_wMarketID_s[i] = NumberUtils.toInt(codes[0]);
                    protocol.resp_pszAddCode_s[i] = codes[3].replaceAll("\"", "");
                    protocol.resp_pszName_s[i] = codes[2].replaceAll("\"", "");
                    protocol.resp_pszPYCode_s[i] = codes[4].replaceAll("\"", "");
                    protocol.resp_wCodeType_s[i] = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.romaway.common.protocol.dl;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONArray;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

/**
 * Created by hongrb on 2018/3/5.
 */
public class StockDetailGsgkDataProtocolCoder extends AProtocolCoder<StockDetailGsgkDataProtocol> {
    @Override
    protected byte[] encode(StockDetailGsgkDataProtocol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(StockDetailGsgkDataProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("StockDetailGsgkDataProtocolCoder", "decode >>> result body = " + result);
        try {
            if (!TextUtils.isEmpty(result)) {
//                BaseJSONObject jsonObject = new BaseJSONObject(result);
//                if (jsonObject.has("error")) {
//                    protocol.resp_errorCode = jsonObject.getString("error");
//                }
//                if (jsonObject.has("msg")) {
//                    protocol.resp_errorMsg = jsonObject.getString("msg");
//                }

                BaseJSONArray jsonArray = new BaseJSONArray(result);
                BaseJSONObject jsonObject = jsonArray.getJSONObject(0);
                if (jsonObject.has("fxxg")) {
                    BaseJSONObject jsonObject1 = new BaseJSONObject(jsonObject.getString("fxxg"));
                    Logger.d("StockDetailGsgkDataProtocolCoder", "jsonObject1 = " + jsonObject1.toString());
                    if (jsonObject1.has("ssrq")) {
                        protocol.resp_ssrq = jsonObject1.getString("ssrq");
                    }
                    if (jsonObject1.has("fxl")) {
                        protocol.resp_fxl = jsonObject1.getString("fxl");
                    }
                    if (jsonObject1.has("fxsyl")) {
                        protocol.resp_fxsyl = jsonObject1.getString("fxsyl");
                    }
                    if (jsonObject1.has("mgfxj")) {
                        protocol.resp_mgfxj = jsonObject1.getString("mgfxj");
                    }
                    if (jsonObject1.has("mjzjje")) {
                        protocol.resp_mjzjje = jsonObject1.getString("mjzjje");
                    }
                    if (jsonObject1.has("wxpszql")) {
                        protocol.resp_wxpszql = jsonObject1.getString("wxpszql");
                    }
                    if (jsonObject1.has("clrq")) {
                        protocol.resp_clrq = jsonObject1.getString("clrq");
                    }
                }
                if (jsonObject.has("jbzl")) {
                    BaseJSONObject jsonObject1 = new BaseJSONObject(jsonObject.getString("jbzl"));
                    if (jsonObject1.has("gsmc")) {
                        protocol.resp_gsmc = jsonObject1.getString("gsmc");
                    }
                    if (jsonObject1.has("cym")) {
                        protocol.resp_cym = jsonObject1.getString("cym");
                    }
                    if (jsonObject1.has("qy")) {
                        protocol.resp_qy = jsonObject1.getString("qy");
                    }
                    if (jsonObject1.has("sszjhhy")) {
                        protocol.resp_sszjhhy = jsonObject1.getString("sszjhhy");
                    }
                    if (jsonObject1.has("dsz")) {
                        protocol.resp_dsz = jsonObject1.getString("dsz");
                    }
                    if (jsonObject1.has("frdb")) {
                        protocol.resp_frdb = jsonObject1.getString("frdb");
                    }
                    if (jsonObject1.has("zjl")) {
                        protocol.resp_zjl = jsonObject1.getString("zjl");
                    }
                    if (jsonObject1.has("dm")) {
                        protocol.resp_dm = jsonObject1.getString("dm");
                    }
                    if (jsonObject1.has("zczb")) {
                        protocol.resp_zczb = jsonObject1.getString("zczb");
                    }
                    if (jsonObject1.has("gyrs")) {
                        protocol.resp_gyrs = jsonObject1.getString("gyrs");
                    }
                    if (jsonObject1.has("glryrs")) {
                        protocol.resp_glryrs = jsonObject1.getString("glryrs");
                    }
                    if (jsonObject1.has("lxdh")) {
                        protocol.resp_lxdh = jsonObject1.getString("lxdh");
                    }
                    if (jsonObject1.has("dzxx")) {
                        protocol.resp_dzxx = jsonObject1.getString("dzxx");
                    }
                    if (jsonObject1.has("gswz")) {
                        protocol.resp_gswz = jsonObject1.getString("gswz");
                    }
                    if (jsonObject1.has("bgdz")) {
                        protocol.resp_bgdz = jsonObject1.getString("bgdz");
                    }
                    if (jsonObject1.has("zcdz")) {
                        protocol.resp_zcdz = jsonObject1.getString("zcdz");
                    }
                    if (jsonObject1.has("gsjj")) {
                        protocol.resp_gsjj = jsonObject1.getString("gsjj");
                    }
                    if (jsonObject1.has("jyfw")) {
                        protocol.resp_jyfw = jsonObject1.getString("jyfw");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

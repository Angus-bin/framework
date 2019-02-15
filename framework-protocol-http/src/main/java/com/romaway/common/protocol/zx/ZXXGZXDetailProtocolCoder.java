package com.romaway.common.protocol.zx;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dell on 2016/11/14.
 */
public class ZXXGZXDetailProtocolCoder extends AProtocolCoder<ZXXGZXDetailProtocol> {
    @Override
    protected byte[] encode(ZXXGZXDetailProtocol protocol) {
        RequestCoder coder = new RequestCoder();
        return coder.getData();
    }

    @Override
    protected void decode(ZXXGZXDetailProtocol protocol) throws ProtocolParserException {
        ResponseDecoder decoder = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = decoder.getString();
        try {
            JSONObject object = new JSONObject(result);
            if(object.has("dgsgsz")){
                protocol.resp_dgsgsz = object.getString("dgsgsz");
            }
            if(object.has("dqjg")){
                protocol.resp_dqjg = object.getString("dqjg");
            }
            if(object.has("fxjg")){
                protocol.resp_fxjg = object.getString("fxjg");
            }
            if(object.has("fxlx")){
                protocol.resp_fxlx = object.getString("fxlx");
            }
            if(object.has("fxrq")){
                protocol.resp_fxrq = object.getString("fxrq");
            }
            if(object.has("fxzl")){
                protocol.resp_fxzl = object.getString("fxzl");
            }
            if(object.has("gpdm")){
                protocol.resp_gpdm = object.getString("gpdm");
            }
            if(object.has("gpmc")){
                protocol.resp_gpmc = object.getString("gpmc");
            }
            if(object.has("gsjj")){
                protocol.resp_gsjj = object.getString("gsjj");
            }
            if(object.has("id")){
                protocol.resp_id = object.getString("id");
            }
            if(object.has("jksl")){
                protocol.resp_jksl = object.getString("jksl");
            }
            if(object.has("jysdm")){
                protocol.resp_jysdm = object.getString("jysdm");
            }
            if(object.has("sgdm")){
                protocol.resp_sgdm = object.getString("sgdm");
            }
            if(object.has("sgdw")){
                protocol.resp_sgdw = object.getString("sgdw");
            }
            if(object.has("sgjg")){
                protocol.resp_sgjg = object.getString("sgjg");
            }
            if(object.has("sgrq")){
                protocol.resp_sgrq = object.getString("sgrq");
            }
            if(object.has("sgsx")){
                protocol.resp_sgsx = object.getString("sgsx");
            }
            if(object.has("dgsgsz")){
                protocol.resp_dgsgsz = object.getString("dgsgsz");
            }
            if(object.has("sgzjsx")){
                protocol.resp_sgzjsx = object.getString("sgzjsx");
            }
            if(object.has("sshy")){
                protocol.resp_sshy = object.getString("sshy");
            }
            if(object.has("ssrq")){
                protocol.resp_ssrq = object.getString("ssrq");
            }
            if(object.has("sssrzdf")){
                protocol.resp_sssrzdf = object.getString("sssrzdf");
            }
            if(object.has("sszjzdf")){
                protocol.resp_sszjzdf = object.getString("sszjzdf");
            }
            if(object.has("syl")){
                protocol.resp_syl = object.getString("syl");
            }
            if(object.has("wszql")){
                protocol.resp_wszql = object.getString("wszql");
            }
            if(object.has("zgzt")){
                protocol.resp_xgzt = object.getString("zgzt");
            }
            if(object.has("zqrq")){
                protocol.resp_zqrq = object.getString("zqrq");
            }
            if(object.has("sgxx")){
                protocol.resp_sgxx = object.getString("sgxx");
            }
            if(object.has("wsfxsl")){
                protocol.resp_wsfxsl = object.getString("wsfxsl");
            }
            if(object.has("zqqk")){
                protocol.resp_zqqk = object.getString("zqqk");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

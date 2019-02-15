package com.romaway.common.protocol.zx;

import com.romalibs.utils.Logger;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dell on 2016/11/14.
 */
public class ZXXGZXListProcotolCoder extends AProtocolCoder<ZXXGZXListProcotol> {
    @Override
    protected byte[] encode(ZXXGZXListProcotol protocol) {
        RequestCoder reqCoder = new RequestCoder();
        return reqCoder.getData();
    }

    @Override
    protected void decode(ZXXGZXListProcotol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();
        try {
            JSONObject object = new JSONObject(result);
            JSONArray array = object.getJSONArray("news");
            
            if(array != null){
                int length = array.length();
                protocol.resp_count = length;
                protocol.resp_dqjg = new String[length];
                protocol.resp_fxjg = new String[length];
                protocol.resp_fxrq = new String[length];
                protocol.resp_fxzl = new String[length];
                protocol.resp_gpdm = new String[length];
                protocol.resp_id = new String[length];
                protocol.resp_jysdm = new String[length];
                protocol.resp_sgdm = new String[length];
                protocol.resp_sgdw = new String[length];
                protocol.resp_sgrq = new String[length];
                protocol.resp_sgsx = new String[length];
                protocol.resp_ssrq = new String[length];
                protocol.resp_syl = new String[length];
                protocol.resp_wszql = new String[length];
                protocol.resp_xgzt = new String[length];
                protocol.resp_zqrq = new String[length];
                protocol.resp_gpmc = new String[length];
                for(int i=0; i<length; i++){
                    object = array.getJSONObject(i);
                    if(object.has("dqjg")) {
                        protocol.resp_dqjg[i] = object.getString("dqjg");
                    }
                    if(object.has("fxjg")){
                        protocol.resp_fxjg[i] = object.getString("fxjg");
                    }
                    if(object.has("fxrq")){
                        protocol.resp_fxrq[i] = object.getString("fxrq");
                    }
                    if(object.has("fxzl")){
                        protocol.resp_fxzl[i] = object.getString("fxzl");
                    }
                    if(object.has("gpdm")){
                        protocol.resp_gpdm[i] = object.getString("gpdm");
                    }
                    if(object.has("gpmc")){
                        protocol.resp_gpmc[i] = object.getString("gpmc");
                    }
                    if(object.has("id")){
                        protocol.resp_id[i] = object.getString("id");
                    }
                    if(object.has("jysdm")){
                        protocol.resp_jysdm[i] = object.getString("jysdm");
                    }
                    if(object.has("sgdm")){
                        protocol.resp_sgdm[i] = object.getString("sgdm");
                    }
                    if(object.has("sgdw")){
                        protocol.resp_sgdw[i] = object.getString("sgdw");
                    }
                    if(object.has("sgrq")){
                        protocol.resp_sgrq[i] = object.getString("sgrq");
                    }
                    if(object.has("sgsx")){
                        protocol.resp_sgsx[i] = object.getString("sgsx");
                    }
                    if(object.has("ssrq")){
                        protocol.resp_ssrq[i] = object.getString("ssrq");
                    }
                    if(object.has("syl")){
                        protocol.resp_syl[i] = object.getString("syl");
                    }
                    if(object.has("wszql")){
                        protocol.resp_wszql[i] = object.getString("wszql");
                    }
                    if(object.has("xgzt")){
                        protocol.resp_xgzt[i] = object.getString("xgzt");
                    }
                    if(object.has("zqrq")) {
                        protocol.resp_zqrq[i] = object.getString("zqrq");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Logger.d("zgzx", "json解析异常");
        }}
    }


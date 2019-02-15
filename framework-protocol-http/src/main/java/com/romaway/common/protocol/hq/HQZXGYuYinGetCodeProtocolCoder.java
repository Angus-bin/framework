package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by faith on 2016/7/27.
 */
public class HQZXGYuYinGetCodeProtocolCoder extends AProtocolCoder<HQZXGYuYinGetCodeProtocol> {

    @Override
    protected byte[] encode(HQZXGYuYinGetCodeProtocol ptl) {
        BaseJSONObject json = new BaseJSONObject();
        json.put("device_id",ptl.req_deviceID);
        json.put("phone_num", ptl.req_phoneNum);

        Logger.d("NetMsgEncodeDecode", "encode >>> json.toString() = "+json.toString());

        byte[] result = new byte[1024];
        try {
            result = json.toString().getBytes(HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return result;
    }

    @Override
    protected void decode(HQZXGYuYinGetCodeProtocol ptl) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());
        String result = r.getString();

        if(StringUtils.isEmpty(result)){
            return;
        }
        Logger.d("NetMsgEncodeDecode", "decode >>> result = "+result);

        try {
            JSONObject json = new JSONObject(result);
            ptl.serverErrCode = json.getInt("errCode");
            ptl.serverMsg = json.getString("errMsg");
            if (ptl.serverErrCode == 0 && json.has("sec_code")) {
                ptl.resp_secCode = json.getString("sec_code");
            }
        } catch (JSONException e) {
            e.printStackTrace();
//            ptl.serverErrCode = -1;
//            ptl.serverMsg = "网络请求失败";
        }
    }
}

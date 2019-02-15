package com.romaway.common.protocol.tougu;

import android.text.TextUtils;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.json.BaseJSONObject;
import com.romaway.commons.log.Logger;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by hrb on 2016/8/25.
 */
public class GroupNameSensitiveProtocolCoder extends AProtocolCoder<GroupNameSensitiveProtocol> {
    @Override
    protected byte[] encode(GroupNameSensitiveProtocol protocol) {
        BaseJSONObject jsonObject = new BaseJSONObject();
        jsonObject.put("name", protocol.req_groupName);

        Logger.d("GroupNameSensitiveProtocolCoder",
                "encode >>> json.toString() = " + jsonObject.toString());

        byte[] result = new byte[1024];
        try {
            result = jsonObject.toString().getBytes(HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void decode(GroupNameSensitiveProtocol protocol) throws ProtocolParserException {
        ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
        String result = r.getString();

        Logger.d("GroupNameSensitiveProtocolCoder", "decode >>> result body = " + result);

        try {
            if (!TextUtils.isEmpty(result)) {
                JSONObject jsonObject = new JSONObject(result);
                protocol.resp_errCode = jsonObject.optString("errCode");
                protocol.resp_errMsg = jsonObject.optString("errMsg");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

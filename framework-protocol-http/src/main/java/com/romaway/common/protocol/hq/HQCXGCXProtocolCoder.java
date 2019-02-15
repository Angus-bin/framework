package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.hq.HQCXGCXProtocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dell on 2016/11/15.
 */
public class HQCXGCXProtocolCoder extends AProtocolCoder<HQCXGCXProtocol> {
    @Override
    protected byte[] encode(HQCXGCXProtocol protocol) {
        RequestCoder coder = new RequestCoder();
        return coder.getData();
    }

    @Override
    protected void decode(HQCXGCXProtocol protocol) throws ProtocolParserException {
        ResponseDecoder decoder = new ResponseDecoder(protocol.getReceiveData());
        String result = decoder.getString();
        try {
            JSONObject object = new JSONObject(result);
            JSONArray array = object.getJSONArray("cxg_list");
            if (array != null){
                int length = array.length();
                protocol.resp_count = length;
                protocol.resp_date = new String[length];
                protocol.resp_first_day_change_percent = new String[length];
                protocol.resp_id = new String[length];
                protocol.resp_price = new String[length];
                protocol.resp_stock_code = new String[length];
                protocol.resp_stock_market = new String[length];
                protocol.resp_stock_name = new String[length];
                protocol.resp_to_this_day_change_percent = new String[length];
                for (int i=0; i<length; i++){
                    object = (JSONObject) array.get(i);
                    protocol.resp_date[i] = object.getString("date");
                    protocol.resp_first_day_change_percent[i] = object.getString("first_day_change_percent");
                    protocol.resp_id[i] = object.getString("id");
                    protocol.resp_price[i] = object.getString("price");
                    protocol.resp_stock_code[i] = object.getString("stock_code");
                    protocol.resp_stock_market[i] = object.getString("stock_market");
                    protocol.resp_stock_name[i] = object.getString("stock_name");
                    protocol.resp_to_this_day_change_percent[i] = object.getString("to_this_day_change_percent");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

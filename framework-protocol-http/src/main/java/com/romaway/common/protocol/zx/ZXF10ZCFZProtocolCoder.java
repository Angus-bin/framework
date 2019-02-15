package com.romaway.common.protocol.zx;

import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

/**
 * 资产负债表
 * @author chenjp
 *
 */
public class ZXF10ZCFZProtocolCoder extends AProtocolCoder<ZXF10ZCFZProtocol>{

	@Override
	protected byte[] encode(ZXF10ZCFZProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(ZXF10ZCFZProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ZXF10ZCFZProtocolCoder", "decode >>> result body = " + result);
		
		try {
			JSONObject zcfz = new JSONObject(result);
			if (zcfz.has("news")) {
				protocol.resp_news = result;
				JSONObject news = zcfz.getJSONObject("news");
				//流动资产
				protocol.resp_FA01 = news.getString("FA01");
				protocol.resp_FA02 = news.getString("FA02");
				protocol.resp_FA03 = news.getString("FA03");
				protocol.resp_FA04 = news.getString("FA04");
				protocol.resp_FA05 = news.getString("FA05");
				protocol.resp_FA06 = news.getString("FA06");
				protocol.resp_FA07 = news.getString("FA07");
				protocol.resp_FA08 = news.getString("FA08");
				protocol.resp_FA09 = news.getString("FA09");
				protocol.resp_FA10 = news.getString("FA10");
				protocol.resp_FA11 = news.getString("FA11");
				//非流动资产
				protocol.resp_FB01 = news.getString("FB01");
				protocol.resp_FB02 = news.getString("FB02");
				protocol.resp_FB03 = news.getString("FB03");
				protocol.resp_FB04 = news.getString("FB04");
				protocol.resp_FB05 = news.getString("FB05");
				protocol.resp_FB06 = news.getString("FB06");
				protocol.resp_FB07 = news.getString("FB07");
				protocol.resp_FB08 = news.getString("FB08");
				protocol.resp_FB09 = news.getString("FB09");
				protocol.resp_FB10 = news.getString("FB10");
				protocol.resp_FB11 = news.getString("FB11");
				protocol.resp_FB12 = news.getString("FB12");
				protocol.resp_FB13 = news.getString("FB13");
				protocol.resp_FB14 = news.getString("FB14");
				protocol.resp_FB15 = news.getString("FB15");
				protocol.resp_FB16 = news.getString("FB16");
				protocol.resp_FB17 = news.getString("FB17");
				protocol.resp_FB18 = news.getString("FB18");
				//资产总计
				protocol.resp_FC01 = news.getString("FC01");
				//流动负债
				protocol.resp_FD01 = news.getString("FD01");
				protocol.resp_FD02 = news.getString("FD02");
				protocol.resp_FD03 = news.getString("FD03");
				protocol.resp_FD04 = news.getString("FD04");
				protocol.resp_FD05 = news.getString("FD05");
				protocol.resp_FD06 = news.getString("FD06");
				protocol.resp_FD07 = news.getString("FD07");
				protocol.resp_FD08 = news.getString("FD08");
				protocol.resp_FD09 = news.getString("FD09");
				protocol.resp_FD10 = news.getString("FD10");
				protocol.resp_FD11 = news.getString("FD11");
				protocol.resp_FD12 = news.getString("FD12");
				protocol.resp_FD13 = news.getString("FD13");
				protocol.resp_FD14 = news.getString("FD14");
				//非流动负债
				protocol.resp_FE01 = news.getString("FE01");
				protocol.resp_FE02 = news.getString("FE02");
				protocol.resp_FE03 = news.getString("FE03");
				protocol.resp_FE04 = news.getString("FE04");
				protocol.resp_FE05 = news.getString("FE05");
				protocol.resp_FE06 = news.getString("FE06");
				protocol.resp_FE07 = news.getString("FE07");
				//负债合计
				protocol.resp_FF01 = news.getString("FF01");
				//所有者权益（或股东权益
				protocol.resp_FG01 = news.getString("FG01");
				protocol.resp_FG02 = news.getString("FG02");
				protocol.resp_FG03 = news.getString("FG03");
				protocol.resp_FG04 = news.getString("FG04");
				protocol.resp_FG05 = news.getString("FG05");
				protocol.resp_FG06 = news.getString("FG06");
				protocol.resp_FG07 = news.getString("FG07");
				protocol.resp_FG08 = news.getString("FG08");
				protocol.resp_FG09 = news.getString("FG09");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

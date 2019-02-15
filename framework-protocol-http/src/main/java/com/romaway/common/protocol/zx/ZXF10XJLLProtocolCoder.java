package com.romaway.common.protocol.zx;

import org.json.JSONException;
import org.json.JSONObject;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.commons.log.Logger;

/**
 * 现金流量表
 * @author chenjp
 *
 */
public class ZXF10XJLLProtocolCoder extends AProtocolCoder<ZXF10XJLLProtocol>{

	@Override
	protected byte[] encode(ZXF10XJLLProtocol protocol) {
		RequestCoder reqCoder = new RequestCoder();
		return reqCoder.getData();
	}

	@Override
	protected void decode(ZXF10XJLLProtocol protocol)
			throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(protocol.getReceiveData() == null ? new byte[0] : protocol.getReceiveData());
		String result = r.getString();

		Logger.d("ZXF10XJLLProtocolCoder", "decode >>> result body = " + result);
		try {
			JSONObject xjll = new JSONObject(result);
			if (xjll.has("news")) {
				protocol.resp_news = result;
				JSONObject news = xjll.getJSONObject("news");
				//一、经营活动产生的现金流量
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
				//二、投资活动产生的现金流量
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
				//三、筹资活动产生的现金流量
				protocol.resp_FC01 = news.getString("FC01");
				protocol.resp_FC02 = news.getString("FC02");
				protocol.resp_FC03 = news.getString("FC03");
				protocol.resp_FC04 = news.getString("FC04");
				protocol.resp_FC05 = news.getString("FC05");
				protocol.resp_FC06 = news.getString("FC06");
				protocol.resp_FC07 = news.getString("FC07");
				protocol.resp_FC08 = news.getString("FC08");
				protocol.resp_FC09 = news.getString("FC09");
				//四、现金及现金等价物
				protocol.resp_FD01 = news.getString("FD01");
				protocol.resp_FD02 = news.getString("FD02");
				protocol.resp_FD03 = news.getString("FD03");
				//将净利润调节为经营活动的现金流量
				//1．将净利润调节为经营活动现金流量
				protocol.resp_FE01 = news.getString("FE01");
				protocol.resp_FE02 = news.getString("FE02");
				protocol.resp_FE03 = news.getString("FE03");
				protocol.resp_FE04 = news.getString("FE04");
				protocol.resp_FE05 = news.getString("FE05");
				protocol.resp_FE06 = news.getString("FE06");
				protocol.resp_FE07 = news.getString("FE07");
				protocol.resp_FE08 = news.getString("FE08");
				protocol.resp_FE09 = news.getString("FE09");
				protocol.resp_FE10 = news.getString("FE10");
				protocol.resp_FE11 = news.getString("FE11");
				protocol.resp_FE12 = news.getString("FE12");
				protocol.resp_FE13 = news.getString("FE13");
				protocol.resp_FE14 = news.getString("FE14");
				protocol.resp_FE15 = news.getString("FE15");
				protocol.resp_FE16 = news.getString("FE16");
				protocol.resp_FE17 = news.getString("FE17");
				//2．不涉及现金收支的投资和筹资活动
				protocol.resp_FE18 = news.getString("FE18");
				protocol.resp_FE19 = news.getString("FE19");
				protocol.resp_FE20 = news.getString("FE20");
				//3．现金及现金等价物净变动情况
				protocol.resp_FE21 = news.getString("FE21");
				protocol.resp_FE22 = news.getString("FE22");
				protocol.resp_FE23 = news.getString("FE23");
				protocol.resp_FE24 = news.getString("FE24");
				protocol.resp_FE25 = news.getString("FE25");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

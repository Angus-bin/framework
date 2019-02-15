package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

/**
 * 获取支持的行情类型数据协议
 * 
 * @author xueyan
 * 
 */
public class HQDATAProtocolCoder extends AProtocolCoder<HQDATAProtocol>
{

	@Override
	protected byte[] encode(HQDATAProtocol datahq)
	{
		RequestCoder reqCoder = new RequestCoder();
		reqCoder.addShort(datahq.req_wMarketID);// 1
		reqCoder.addShort(datahq.req_wType);// 2
		reqCoder.addByte(datahq.req_bAll);// 3

		return reqCoder.getData();
	}

	@Override
	protected void decode(HQDATAProtocol hqdata) throws ProtocolParserException
	{
		ResponseDecoder r = new ResponseDecoder(hqdata.getReceiveData());
		short wCount = r.getShort();
		hqdata.resp_wCount = wCount;
		hqdata.resp_wMarketID_s = new short[wCount];
		hqdata.resp_wType_s = new short[wCount];
		hqdata.resp_pszCode_s = new String[wCount];
		hqdata.resp_pszName_s = new String[wCount];
		for (int index = 0; index < wCount; index++)
		{
			hqdata.resp_wMarketID_s[index] = r.getShort();
			hqdata.resp_wType_s[index] = r.getShort();
			hqdata.resp_pszCode_s[index] = r.getString();
			hqdata.resp_pszName_s[index] = r.getUnicodeString();
		}

	}

}

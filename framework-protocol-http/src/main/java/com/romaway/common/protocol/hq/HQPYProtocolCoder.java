package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

/**
 * 股票匹配协议
 * 
 * @author xueyan
 * 
 */
public class HQPYProtocolCoder extends AProtocolCoder<HQPYProtocol>
{

	@Override
	protected byte[] encode(HQPYProtocol hqpy)
	{
		RequestCoder reqCoder = new RequestCoder();
		reqCoder.addShort(hqpy.req_wType);// 1
		reqCoder.addString(hqpy.req_pszPattern,
		        ProtocolConstant.MAX_NAME_LENGTH * 2);// 2
		reqCoder.addShort(hqpy.req_wMarketID);// 3

		return reqCoder.getData();
	}

	@Override
	protected void decode(HQPYProtocol hqpy) throws ProtocolParserException
	{
		ResponseDecoder r = new ResponseDecoder(hqpy.getReceiveData());
		hqpy.resp_wMarketID = r.getShort();
		hqpy.resp_wType = r.getShort();
		hqpy.resp_pszCode = r.getString(ProtocolConstant.MAX_CODE_LENGTH);
		hqpy.resp_pszPYCode = r.getString(ProtocolConstant.MAX_CODE_LENGTH);
		hqpy.resp_pszName = r
		        .getUnicodeString(ProtocolConstant.MAX_NAME_LENGTH);
		short wCount = r.getShort();
		hqpy.resp_wCount = wCount;
		hqpy.resp_pData_s = new String[wCount];
		for (int index = 0; index < wCount; index++)
		{
			hqpy.resp_pData_s[index] = r.getUnicodeString();
		}

	}

}

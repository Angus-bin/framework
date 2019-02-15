package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

/**
 * @author qinyn
 * 
 *         行情分笔协议
 */
public class HQFBProtocolCoder extends AProtocolCoder<HQFBProtocol>
{

	@Override
	protected byte[] encode(HQFBProtocol hqfb)
	{
		RequestCoder reqCoder = new RequestCoder();
		reqCoder.addString(hqfb.req_pszCode, ProtocolConstant.MAX_CODE_LENGTH);
		reqCoder.addByte(hqfb.req_nType);
		reqCoder.addInt32(hqfb.req_nNum);
		reqCoder.addInt32(hqfb.req_nTimeS);
		reqCoder.addInt32(hqfb.req_nTimeE);
		reqCoder.addShort(hqfb.req_wMarketID);
		return reqCoder.getData();
	}

	@Override
	protected void decode(HQFBProtocol hqfb) throws ProtocolParserException
	{
		ResponseDecoder r = new ResponseDecoder(hqfb.getReceiveData());
		hqfb.resp_wMarketID = r.getShort();
		hqfb.resp_wType = r.getShort();
		hqfb.resp_nDate = r.getInt();
		hqfb.resp_pszCode = r.getString(ProtocolConstant.MAX_CODE_LENGTH);
		hqfb.resp_pszName = r
		        .getUnicodeString(ProtocolConstant.MAX_NAME_LENGTH);
		hqfb.resp_nZrsp = r.getInt();

		short wCount = r.getShort();
		// TODO 在這裡若hqfb.resp_wCount = r.getShort()会出现272异常，需要写成hqfb.resp_wCount
		// = wCount
		// hqfb.resp_wCount = r.getShort();
		hqfb.resp_wCount = wCount;
		hqfb.resp_nTime_s = new int[wCount];
		hqfb.resp_bCjlb_s = new byte[wCount];
		hqfb.resp_nZjcj_s = new int[wCount];
		hqfb.resp_nCjss_s = new int[wCount];
		hqfb.resp_nCjje_s = new int[wCount];
		for (int index = 0; index < wCount; index++)
		{
			hqfb.resp_nTime_s[index] = r.getInt();
			hqfb.resp_bCjlb_s[index] = r.getByte();
			hqfb.resp_nZjcj_s[index] = r.getInt();
			hqfb.resp_nCjss_s[index] = r.getInt();
			hqfb.resp_nCjje_s[index] = r.getInt();
		}

		int cmdVersion = hqfb.getCmdServerVersion();
		if (cmdVersion >= 1)
		{
			hqfb.resp_sLinkFlag = r.getString();
		}

	}

}

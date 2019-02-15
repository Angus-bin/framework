package com.romaway.common.protocol.xt;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

public class YJDZCXProtocolCoder extends AProtocolCoder<YJDZCXProtocol>
{

	@Override
	protected byte[] encode(YJDZCXProtocol ptl)
	{
		RequestCoder reqCoder = new RequestCoder();

		reqCoder.addString(ptl.req_sCPID, false);
		reqCoder.addString(ptl.req_sIdentifierType, false);
		reqCoder.addString(ptl.req_sIdentifier, false);
		reqCoder.addInt32(ptl.req_nOrderId);
		reqCoder.addInt32(ptl.req_nServiceId);

		if (ptl.req_sParam1 != null)
		{
			reqCoder.addString(ptl.req_sParam1, false);
		} else
		{
			reqCoder.addString(null, false);
		}
		if (ptl.req_sParam2 != null)
		{
			reqCoder.addString(ptl.req_sParam2, false);
		} else
		{
			reqCoder.addString(null, false);
		}
		if (ptl.req_sParam3 != null)
		{
			reqCoder.addString(ptl.req_sParam3, false);
		} else
		{
			reqCoder.addString(null, false);
		}
		if (ptl.req_sParam4 != null)
		{
			reqCoder.addString(ptl.req_sParam4, false);
		} else
		{
			reqCoder.addString(null, false);
		}
		if (ptl.req_sParam5 != null)
		{
			reqCoder.addString(ptl.req_sParam5, false);
		} else
		{
			reqCoder.addString(null, false);
		}
		return reqCoder.getData();
	}

	@Override
	protected void decode(YJDZCXProtocol ptl) throws ProtocolParserException
	{
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());
		int cmdVersion = ptl.getCmdVersion();
		short count = r.getShort();
		if (count <= 0)
			return;
		ptl.resp_wCount = count;

		ptl.resp_nOrderId_s = new int[count];
		ptl.resp_nServiceId_s = new int[count];
		ptl.resp_sParam1_s = new String[count];
		ptl.resp_sParam2_s = new String[count];
		ptl.resp_sParam3_s = new String[count];
		ptl.resp_sParam4_s = new String[count];
		ptl.resp_sParam5_s = new String[count];
		ptl.resp_wChannel_s = new short[count];
		ptl.resp_wsStockName_s = new String[count];
		ptl.resp_wMarketID_s = new short[count];

		for (int i = 0; i < count; i++)
		{
			ptl.resp_nOrderId_s[i] = r.getInt();
			ptl.resp_nServiceId_s[i] = r.getInt();
			ptl.resp_sParam1_s[i] = r.getString();
			ptl.resp_sParam2_s[i] = r.getString();
			ptl.resp_sParam3_s[i] = r.getString();
			ptl.resp_sParam4_s[i] = r.getString();
			ptl.resp_sParam5_s[i] = r.getString();
			if (cmdVersion >= 1)
				ptl.resp_wChannel_s[i] = r.getShort();
			if (cmdVersion >= 2)
				ptl.resp_wsStockName_s[i] = r.getUnicodeString();
			if (cmdVersion >= 3)
				ptl.resp_wMarketID_s[i] = r.getShort();

		}
	}

}

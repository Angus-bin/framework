package com.romaway.common.protocol.xt;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

public class YJDZProtocolCoder extends AProtocolCoder<YJDZProtocol>
{

	@Override
	protected byte[] encode(YJDZProtocol ptl)
	{
		int cmdVersion = ptl.getCmdVersion();
		RequestCoder reqCoder = new RequestCoder();
		reqCoder.addString(ptl.req_sCPID, false);
		reqCoder.addString(ptl.req_sIdentifierType, false);
		reqCoder.addString(ptl.req_sIdentifier, false);

		reqCoder.addShort(ptl.req_wNum);
		for (int i = 0; i < ptl.req_wNum; i++)
		{
			reqCoder.addInt32(ptl.req_nOrderId_s[i]);
			reqCoder.addShort(ptl.req_wOrderType_s[i]);
			reqCoder.addInt32(ptl.req_nServiceId_s[i]);
			if (ptl.req_sParam1_s != null)
				reqCoder.addString(ptl.req_sParam1_s[i], false);
			else
			{
				reqCoder.addString(null, false);
			}
			if (ptl.req_sParam2_s != null)
				reqCoder.addString(ptl.req_sParam2_s[i], false);
			else
			{
				reqCoder.addString(null, false);
			}
			if (ptl.req_sParam3_s != null)
				reqCoder.addString(ptl.req_sParam3_s[i], false);
			else
			{
				reqCoder.addString(null, false);
			}
			if (ptl.req_sParam4_s != null)
				reqCoder.addString(ptl.req_sParam4_s[i], false);
			else
			{
				reqCoder.addString(null, false);
			}
			if (ptl.req_sParam5_s != null)
				reqCoder.addString(ptl.req_sParam5_s[i], false);
			else
			{
				reqCoder.addString(null, false);
			}
			if (cmdVersion >= 1)
			{
				reqCoder.addShort(ptl.req_wChannel_s[i]);
			}
			if (cmdVersion >= 2)
			{
				reqCoder.addShort(ptl.req_wMarketID_s[i]);
			}

		}
		return reqCoder.getData();
	}

	@Override
	protected void decode(YJDZProtocol ptl) throws ProtocolParserException
	{
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());

		short count = r.getShort();
		if (count <= 0)
			return;
		ptl.resp_wCount = count;

		ptl.resp_nOrderId_s = new int[count];
		ptl.resp_sRetNo_s = new short[count];
		ptl.resp_sRetInfo_s = new String[count];

		for (int i = 0; i < count; i++)
		{
			ptl.resp_nOrderId_s[i] = r.getInt();
			ptl.resp_sRetNo_s[i] = r.getShort();
			ptl.resp_sRetInfo_s[i] = r.getUnicodeString();
		}
	}

}

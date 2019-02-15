package com.romaway.common.protocol.xt;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

public class YJXXCXProtocolCoder extends AProtocolCoder<YJXXCXProtocol>
{

	@Override
	protected byte[] encode(YJXXCXProtocol ptl)
	{
		RequestCoder reqCoder = new RequestCoder();
		reqCoder.addString(ptl.req_sCPID, false);
		reqCoder.addString(ptl.req_sIdentifierType, false);
		reqCoder.addString(ptl.req_sIdentifier, false);
		reqCoder.addInt32(ptl.req_nServiceId);

		return reqCoder.getData();
	}

	@Override
	protected void decode(YJXXCXProtocol ptl) throws ProtocolParserException
	{
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());
		short count = r.getShort();
		if (count <= 0)
			return;
		ptl.resp_wCount = count;

		ptl.resp_nServiceId_s = new int[count];
		ptl.resp_sTime_s = new String[count];
		ptl.resp_wsSource_s = new String[count];
		ptl.resp_wsMsg_s = new String[count];

		for (int i = 0; i < count; i++)
		{
			ptl.resp_nServiceId_s[i] = r.getInt();
			ptl.resp_sTime_s[i] = r.getString();
			ptl.resp_wsSource_s[i] = r.getUnicodeString();
			ptl.resp_wsMsg_s[i] = r.getUnicodeString();
		}
	}

}

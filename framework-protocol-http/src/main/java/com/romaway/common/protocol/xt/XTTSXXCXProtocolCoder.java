package com.romaway.common.protocol.xt;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

public class XTTSXXCXProtocolCoder extends AProtocolCoder<XTTSXXCXProtocol>
{

	@Override
	protected byte[] encode(XTTSXXCXProtocol ptl)
	{
		RequestCoder reqCoder = new RequestCoder();
		int version = ptl.getCmdVersion();

		reqCoder.addString(ptl.req_sCPID, false);
		reqCoder.addString(ptl.req_sIdentifierType, false);
		reqCoder.addString(ptl.req_sIdentifier, false);
		reqCoder.addInt32(ptl.req_dwServiceId);
		reqCoder.addByte(ptl.req_bSort);
		reqCoder.addShort(ptl.req_wFrom);
		reqCoder.addShort(ptl.req_wCount);
		if (version >= 2)
		{
			reqCoder.addByte(ptl.req_nType);
		}
		return reqCoder.getData();
	}

	@Override
	protected void decode(XTTSXXCXProtocol ptl) throws ProtocolParserException
	{
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());
		short count = r.getShort();
		ptl.resp_wNum = count;
		//int cmdVersoin = ptl.getCmdServerVersion();
		int cmdVersoin = 4;
		if (count > 0)
		{
			ptl.resp_dwService_id_s = new int[count];
			ptl.resp_sTime_s = new String[count];
			ptl.resp_wsSource_s = new String[count];
			ptl.resp_wsMsg_s = new String[count];
			ptl.resp_wChannel_s = new short[count];
			ptl.resp_sStockCode_s = new String[count];
			ptl.resp_wsStockName_s = new String[count];
			ptl.resp_sMsgID_s = new String[count];
			ptl.resp_bReadStatus_s = new byte[count];
			ptl.resp_wMarketID_s = new short[count];

			for (int i = 0; i < count; i++)
			{
				ptl.resp_dwService_id_s[i] = r.getInt();
				ptl.resp_sTime_s[i] = r.getString();
				ptl.resp_wsSource_s[i] = r.getUnicodeString();
				ptl.resp_wsMsg_s[i] = r.getUnicodeString();
				ptl.resp_wChannel_s[i] = r.getShort();
				if (cmdVersoin >= 1)
				{
					ptl.resp_sStockCode_s[i] = r.getString();
					ptl.resp_wsStockName_s[i] = r.getUnicodeString();
				}
				if (cmdVersoin >= 3)
				{
					ptl.resp_sMsgID_s[i] = r.getString();
					ptl.resp_bReadStatus_s[i] = r.getByte();
				}
				if (cmdVersoin >= 4)
				{
					ptl.resp_wMarketID_s[i] = r.getShort();
				}
			}
		}
		ptl.resp_wNum_all = r.getShort();

	}
}

package com.romaway.common.protocol.xt;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

public class XTBBCXProtocolCoder extends AProtocolCoder<XTBBCXProtocol>
{

	@Override
	protected byte[] encode(XTBBCXProtocol ptl)
	{
		RequestCoder reqCoder = new RequestCoder();
		reqCoder.addString(ptl.req_sKHDBB, false);
		reqCoder.addString(ptl.req_sCSPP, true);
		reqCoder.addString(ptl.req_sZDLX, false);
		reqCoder.addString(ptl.req_sRJID, false);
		reqCoder.addString(ptl.req_sRJLX, false);
		return reqCoder.getData();
	}

	@Override
	protected void decode(XTBBCXProtocol ptl) throws ProtocolParserException
	{
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());
		// ptl.resp_sKHDBB = r.getString();//加了就解析错误
		ptl.resp_bJR = r.getByte();
		ptl.resp_sZXKHDBB = r.getString();
		ptl.resp_sXZDZ = r.getString();
		ptl.resp_sGXSM = r.getUnicodeString();
	}

}

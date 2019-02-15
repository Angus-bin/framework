package com.romaway.common.protocol.xt;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

/**
 * 系统值文本信息查询
 * @author dumh
 *
 */
public class XTTxtInfoProtocolCoder extends AProtocolCoder<XTTxtInfoProtocol>
{

	@Override
    protected byte[] encode(XTTxtInfoProtocol ptl)
    {
	    // TODO Auto-generated method stub
		RequestCoder reqCoder = new RequestCoder();
	
		reqCoder.addString(ptl.req_sResourceKey, false);
		reqCoder.addInt32(ptl.req_offset);
		reqCoder.addInt32(ptl.req_count);	
		reqCoder.addString(ptl.req_cpid, false);
		return reqCoder.getData();
    }

	@Override
    protected void decode(XTTxtInfoProtocol ptl)
            throws ProtocolParserException
    {
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());
		ptl.resp_count = r.getInt();
		ptl.resp_content = r.getUnicodeString();
    }

}

package com.romaway.common.protocol.xt;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
/**
 * 获取券商信息
 * @author XY
 *
 */
public class XTQSLBProtocolCoder extends AProtocolCoder<XTQSLBProtocol>
{

	@Override
	protected byte[] encode(XTQSLBProtocol ptl)
	{
		RequestCoder reqCoder = new RequestCoder();
		reqCoder.addString(ptl.req_sUserMblPhone, false);
		reqCoder.addString(ptl.req_sCPID, false);
		return reqCoder.getData();
	}

	@Override
	protected void decode(XTQSLBProtocol ptl) throws ProtocolParserException
	{
		ResponseDecoder r = new ResponseDecoder(ptl.getReceiveData());
		short wCount1 = r.getShort();
		ptl.resp_wCount1 = wCount1;
		ptl.resp_sCPID_s = new String[wCount1];
		ptl.resp_sBrokerName_s = new String[wCount1];
		ptl.resp_sBrokerIP_s = new String[wCount1];
		ptl.resp_sBrokerPort_s = new String[wCount1];
		for (int i = 0; i < wCount1; i++)
		{

			ptl.resp_sCPID_s[i] = r.getString();
			ptl.resp_sBrokerName_s[i] = r.getUnicodeString();
			ptl.resp_sBrokerIP_s[i] = r.getString();
			ptl.resp_sBrokerPort_s[i] = r.getString();
		}

		short wCount2 = r.getShort();
		ptl.resp_wCount2 = wCount2;
		ptl.resp_sCPIDS_s = new String[wCount2];
		ptl.resp_sDeptId_s = new String[wCount2];
		ptl.resp_sDeptName_s = new String[wCount2];
		ptl.resp_sDeptShor_s = new String[wCount2];
		ptl.resp_sDeptCode_s = new String[wCount2];
		for (int i = 0; i < wCount2; i++)
		{

			ptl.resp_sCPIDS_s[i] = r.getString();
			ptl.resp_sDeptId_s[i] = r.getString();
			ptl.resp_sDeptName_s[i] = r.getUnicodeString();
			ptl.resp_sDeptShor_s[i] = r.getUnicodeString();
			ptl.resp_sDeptCode_s[i] = r.getString();
		}
	}

}

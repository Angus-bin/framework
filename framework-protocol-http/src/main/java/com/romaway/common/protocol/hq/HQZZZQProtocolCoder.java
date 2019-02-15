package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

/**
 * @author qinyn
 * 
 *         行情中证债券排序协议
 */
public class HQZZZQProtocolCoder extends AProtocolCoder<HQZZZQProtocol>
{

	@Override
	protected byte[] encode(HQZZZQProtocol hqpx)
	{
		RequestCoder reqCoder = new RequestCoder();
		reqCoder.addShort(hqpx.req_wMarketID);// 1
		reqCoder.addShort(hqpx.req_wType);// 2
		reqCoder.addByte(hqpx.req_bSort);// 3
		reqCoder.addByte(hqpx.req_bDirect);// 4
		reqCoder.addShort(hqpx.req_wFrom);// 5
		reqCoder.addShort(hqpx.req_wCount);// 6

		int cmdVersion = hqpx.getCmdServerVersion();
		
		return reqCoder.getData();
	}

	@Override
	protected void decode(HQZZZQProtocol ptl) throws ProtocolParserException
	{
		ResponseDecoder r 			= new ResponseDecoder(ptl.getReceiveData());
		ptl.resp_wTotolCount 		= r.getShort();
		short wCount 				= r.getShort();
		ptl.resp_wCount 			= wCount;
		
		int cmdVersion 				= ptl.getCmdServerVersion();
		
		ptl.resp_wMarketID_s 		= new short[wCount];
		ptl.resp_wType_s 			= new short[wCount];
		ptl.resp_pszName_s 			= new String[wCount];		
		ptl.resp_sDate 				= new String[wCount];		
		ptl.resp_sHSDM 				= new String[wCount];
		ptl.resp_sSSDM 				= new String[wCount];
		ptl.resp_sYHJDM 			= new String[wCount];
		ptl.resp_nGZ 				= new String[wCount];
		ptl.resp_nJSSYL 			= new String[wCount];
		ptl.resp_nXZJQ 				= new String[wCount];
		ptl.resp_nTX 				= new String[wCount];
		ptl.resp_nYJLX 				= new String[wCount];
		ptl.resp_sReserved 			= new String[wCount];

		for (int index = 0; index < wCount; index++)
		{
			ptl.resp_wMarketID_s[index] 	= r.getShort();
			ptl.resp_wType_s[index] 		= r.getShort();
	
			ptl.resp_pszName_s[index] 		= r.getUnicodeString(ProtocolConstant.MAX_NAME_LENGTH);
			// (hqpx.req_wFrom + 1 + index) + "." +
			// r.getUnicodeString(ProtocolConstant.MAX_NAME_LENGTH);
			ptl.resp_sDate[index] 			= r.getString();
			
			ptl.resp_sHSDM[index] 			= r.getString();
			ptl.resp_sSSDM[index] 			= r.getString();
			ptl.resp_sYHJDM[index] 			= r.getString();
			ptl.resp_nGZ[index] 			= r.getString();
			ptl.resp_nJSSYL[index] 			= r.getString();
			ptl.resp_nXZJQ[index] 			= r.getString();
			ptl.resp_nTX[index] 			= r.getString();
			ptl.resp_nYJLX[index] 			= r.getString();
			ptl.resp_sReserved[index] 		= r.getString();

		}

		

	}

}

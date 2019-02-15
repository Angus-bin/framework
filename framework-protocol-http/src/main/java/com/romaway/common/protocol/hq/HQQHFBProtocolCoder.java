package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

/**
 * @author qinyn
 * 
 *         行情期货分笔协议
 */
public class HQQHFBProtocolCoder extends AProtocolCoder<HQQHFBProtocol>
{

	@Override
	protected byte[] encode(HQQHFBProtocol hqqhfb)
	{
		RequestCoder reqCoder = new RequestCoder();
		reqCoder.addString(hqqhfb.req_pszCode, ProtocolConstant.MAX_CODE_LENGTH);// 1
		reqCoder.addByte(hqqhfb.req_nType);// 2
		reqCoder.addInt32(hqqhfb.req_nNum);// 3
		reqCoder.addInt32(hqqhfb.req_nTimeS);// 4
		reqCoder.addInt32(hqqhfb.req_nTimeE);// 5
		reqCoder.addShort(hqqhfb.req_wMarketID);// 6
		return reqCoder.getData();
	}

	@Override
	protected void decode(HQQHFBProtocol hqqhfb) throws ProtocolParserException
	{
		ResponseDecoder r = new ResponseDecoder(hqqhfb.getReceiveData());
		hqqhfb.resp_wMarketID = r.getShort();
		hqqhfb.resp_wType = r.getShort();
		hqqhfb.resp_nDate = r.getInt();
		hqqhfb.resp_pszCode = r.getString(ProtocolConstant.MAX_CODE_LENGTH);
		hqqhfb.resp_pszName = r
		        .getUnicodeString(ProtocolConstant.MAX_NAME_LENGTH);
		hqqhfb.resp_nZrsp = r.getInt();
		hqqhfb.resp_nZhs = r.getInt();
		hqqhfb.resp_nBp = r.getInt();
		hqqhfb.resp_nSp = r.getInt();
		hqqhfb.resp_nCcl = r.getInt();
		hqqhfb.resp_nCc = r.getInt();
		hqqhfb.resp_nCjss = r.getInt();

		short wCount = r.getShort();
		hqqhfb.resp_wCount = r.getShort();
		hqqhfb.resp_nTime_s = new int[wCount];
		hqqhfb.resp_bCjlb_s = new byte[wCount];
		hqqhfb.resp_nZjcj_s = new int[wCount];
		hqqhfb.resp_nCjss_s = new int[wCount];
		hqqhfb.resp_nCjje_s = new int[wCount];
		hqqhfb.resp_nCc_s = new int[wCount];
		hqqhfb.resp_bCjxz_s = new byte[wCount];
		for (int index = 0; index < wCount; index++)
		{
			hqqhfb.resp_nTime_s[index] = r.getInt();
			hqqhfb.resp_bCjlb_s[index] = r.getByte();
			hqqhfb.resp_nZjcj_s[index] = r.getInt();
			hqqhfb.resp_nCjss_s[index] = r.getInt();
			hqqhfb.resp_nCjje_s[index] = r.getInt();
			hqqhfb.resp_nCc_s[index] = r.getInt();
			hqqhfb.resp_bCjxz_s[index] = r.getByte();
		}
	}

}

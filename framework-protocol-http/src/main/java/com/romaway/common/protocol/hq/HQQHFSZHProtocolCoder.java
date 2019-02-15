package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

/**
 * 分时期货综合协议解码
 * 
 * @author qinyn
 * 
 */
public class HQQHFSZHProtocolCoder extends AProtocolCoder<HQQHFSZHProtocol>
{

	@Override
	protected byte[] encode(HQQHFSZHProtocol hqqhfszh)
	{
		RequestCoder reqCoder = new RequestCoder();
		reqCoder.addShort(hqqhfszh.req_wMarketID);
		reqCoder.addString(hqqhfszh.req_sPszCode,
		        ProtocolConstant.MAX_CODE_LENGTH);
		reqCoder.addInt32(hqqhfszh.req_dwFSDate);
		reqCoder.addByte(hqqhfszh.req_wFSFreq);
		reqCoder.addInt32(hqqhfszh.req_dwFSTime);
		return reqCoder.getData();
	}

	@Override
	protected void decode(HQQHFSZHProtocol hqqhfszh)
	        throws ProtocolParserException
	{
		ResponseDecoder r = new ResponseDecoder(hqqhfszh.getReceiveData());

		short wCount = 0;
		hqqhfszh.resp_wMarketID = r.getShort();
		hqqhfszh.resp_wType = r.getShort();
		hqqhfszh.resp_pszCode = r.getString(ProtocolConstant.MAX_CODE_LENGTH);
		hqqhfszh.resp_pszName = r
		        .getUnicodeString(ProtocolConstant.MAX_NAME_LENGTH);
		hqqhfszh.resp_dwDate = r.getInt();

		hqqhfszh.resp_wCYDZS = r.getShort();
		hqqhfszh.resp_nZrsp = r.getInt();

		hqqhfszh.resp_nZhsj = r.getInt();

		hqqhfszh.resp_nJrkp = r.getInt();
		hqqhfszh.resp_nZgcj = r.getInt();
		hqqhfszh.resp_nZdcj = r.getInt();

		hqqhfszh.resp_nZjcj = r.getInt();
		hqqhfszh.resp_nMaxVol = r.getInt();
		hqqhfszh.resp_nHsj = r.getInt();

		hqqhfszh.resp_nZd = r.getInt();
		hqqhfszh.resp_nZdf = r.getInt();
		hqqhfszh.resp_nZf = r.getInt();
		hqqhfszh.resp_nCjss = r.getInt();
		hqqhfszh.resp_nCjje = r.getInt();

		hqqhfszh.resp_nCjjj = r.getInt();
		hqqhfszh.resp_nCcl = r.getInt();
		hqqhfszh.resp_nCc = r.getInt();

		hqqhfszh.resp_nWb = r.getInt();
		hqqhfszh.resp_nWc = r.getInt();
		hqqhfszh.resp_nLb = r.getInt();
		hqqhfszh.resp_nBuyp = r.getInt();
		hqqhfszh.resp_nSelp = r.getInt();

		hqqhfszh.resp_nLimUp = r.getInt();
		hqqhfszh.resp_nLimDown = r.getInt();
		hqqhfszh.resp_sLinkFlag = r.getString();
		hqqhfszh.resp_bSuspended = r.getByte();

		wCount = r.getShort();
		hqqhfszh.resp_wMMFADataCount = wCount;
		if (wCount > 0)
		{
			hqqhfszh.resp_nBjg_s = new int[wCount];
			hqqhfszh.resp_nBsl_s = new int[wCount];
			hqqhfszh.resp_nSjg_s = new int[wCount];
			hqqhfszh.resp_nSsl_s = new int[wCount];
		}
		for (int i = 0; i < wCount; i++)
		{
			hqqhfszh.resp_nBjg_s[i] = r.getInt();
			hqqhfszh.resp_nBsl_s[i] = r.getInt();
			hqqhfszh.resp_nSjg_s[i] = r.getInt();
			hqqhfszh.resp_nSsl_s[i] = r.getInt();
		}

		wCount = r.getShort();
		hqqhfszh.resp_wZSDataCount = wCount;
		if (wCount > 0)
		{
			hqqhfszh.resp_sZSPszCode_s = new String[wCount];
			hqqhfszh.resp_wsZSPszName_s = new String[wCount];
			hqqhfszh.resp_nZSXj_s = new int[wCount];
			hqqhfszh.resp_nZSZdf_s = new int[wCount];
			hqqhfszh.resp_nZSZrsp_s = new int[wCount];
		}
		for (int i = 0; i < wCount; i++)
		{
			hqqhfszh.resp_sZSPszCode_s[i] = r.getString();
			hqqhfszh.resp_wsZSPszName_s[i] = r.getUnicodeString();//
			hqqhfszh.resp_nZSXj_s[i] = r.getInt();
			hqqhfszh.resp_nZSZdf_s[i] = r.getInt();
			hqqhfszh.resp_nZSZrsp_s[i] = r.getInt();
		}

		wCount = r.getShort();
		hqqhfszh.resp_wFBDataCount = wCount;
		if (wCount > 0)
		{
			hqqhfszh.resp_dwFBTime_s = new int[wCount];
			hqqhfszh.resp_bFBCjlb_s = new byte[wCount];
			hqqhfszh.resp_nFBZjcj_s = new int[wCount];
			hqqhfszh.resp_nFBCjss_s = new int[wCount];
			hqqhfszh.resp_nFBCc_s = new int[wCount];
			hqqhfszh.resp_nFBCjxj_s = new byte[wCount];
		}
		for (int i = 0; i < wCount; i++)
		{
			hqqhfszh.resp_dwFBTime_s[i] = r.getInt();
			hqqhfszh.resp_bFBCjlb_s[i] = r.getByte();
			hqqhfszh.resp_nFBZjcj_s[i] = r.getInt();
			hqqhfszh.resp_nFBCjss_s[i] = r.getInt();
			hqqhfszh.resp_nFBCc_s[i] = r.getInt();
			hqqhfszh.resp_nFBCjxj_s[i] = r.getByte();
		}

		wCount = r.getShort();
		hqqhfszh.resp_wFSDataCount = wCount;
		if (wCount > 0)
		{
			hqqhfszh.resp_dwTime_s = new int[wCount];
			hqqhfszh.resp_nZjcj_s = new int[wCount];
			hqqhfszh.resp_nZdf_s = new int[wCount];
			hqqhfszh.resp_nCjss_s = new int[wCount];
			hqqhfszh.resp_nCjje_s = new int[wCount];
			hqqhfszh.resp_nCjjj_s = new int[wCount];
			hqqhfszh.resp_sXxgg_s = new String[wCount];
			hqqhfszh.resp_nCcl_s = new int[wCount];
			hqqhfszh.resp_nLb_s = new int[wCount];
		}// end if

		for (int index = 0; index < wCount; index++)
		{
			hqqhfszh.resp_dwTime_s[index] = r.getInt();
			hqqhfszh.resp_nZjcj_s[index] = r.getInt();
			hqqhfszh.resp_nZdf_s[index] = r.getInt();
			hqqhfszh.resp_nCjss_s[index] = r.getInt();
			hqqhfszh.resp_nCjje_s[index] = r.getInt();
			hqqhfszh.resp_nCjjj_s[index] = r.getInt();
			hqqhfszh.resp_sXxgg_s[index] = r.getUnicodeString();
			hqqhfszh.resp_nCcl_s[index] = r.getInt();
			hqqhfszh.resp_nLb_s[index] = r.getInt();

		}// end for
		
	}

}

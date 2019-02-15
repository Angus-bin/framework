/**
 * 
 */
package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

/**
 * @author qinyn 行情分时协议
 */
public class HQFSProtocolCoder extends AProtocolCoder<HQFSProtocol>
{

	@Override
	protected byte[] encode(HQFSProtocol hqfs)
	{
		RequestCoder reqCoder = new RequestCoder();
		reqCoder.addString(hqfs.req_pszCode, ProtocolConstant.MAX_CODE_LENGTH);// 1
		reqCoder.addInt32(hqfs.req_nDate);// 2
		reqCoder.addByte(hqfs.req_bFreq);// 3
		reqCoder.addInt32(hqfs.req_nTime);// 4
		reqCoder.addShort(hqfs.req_wMarketID);// 5

		return reqCoder.getData();
	}

	@Override
	protected void decode(HQFSProtocol hqfs) throws ProtocolParserException
	{
		ResponseDecoder r = new ResponseDecoder(hqfs.getReceiveData());
		hqfs.resp_wMarketID = r.getShort();
		hqfs.resp_wType = r.getShort();
		hqfs.resp_nDate = r.getInt();
		hqfs.resp_pszCode = r.getString(ProtocolConstant.MAX_CODE_LENGTH);
		hqfs.resp_pszName = r
		        .getUnicodeString(ProtocolConstant.MAX_NAME_LENGTH);
		hqfs.resp_nZrsp = r.getInt();
		hqfs.resp_nZhsj = r.getInt();
		hqfs.resp_nJrkp = r.getInt();
		hqfs.resp_nZgcj = r.getInt();
		hqfs.resp_nZdcj = r.getInt();
		hqfs.resp_nZjcj = r.getInt();
		hqfs.resp_nCjss = r.getInt();
		hqfs.resp_nCjje = r.getInt();
		hqfs.resp_nMaxVol = r.getInt();
		hqfs.resp_nHsj = r.getInt();

		int cmdVersion = hqfs.getCmdServerVersion();
		if (cmdVersion >= 1) // 增加换手率和市盈率
		{
			hqfs.resp_nHsl = r.getInt();
			hqfs.resp_nSyl = r.getInt();
			short wCount = r.getShort();
			hqfs.resp_wCount = wCount;
			if (wCount > 0)
			{
				hqfs.resp_nTime_s = new int[wCount];
				hqfs.resp_nZjcj_s = new int[wCount];
				hqfs.resp_nZdf_s = new int[wCount];
				hqfs.resp_nCjss_s = new int[wCount];
				hqfs.resp_nCjje_s = new int[wCount];
				hqfs.resp_nCjjj_s = new int[wCount];
				hqfs.resp_sXxgg_s = new String[wCount];
				hqfs.resp_nCcl_s = new int[wCount];
				hqfs.resp_nLb_s = new int[wCount];
			}
			for (int index = 0; index < wCount; index++)
			{
				hqfs.resp_nTime_s[index] = r.getInt();
				hqfs.resp_nZjcj_s[index] = r.getInt();
				hqfs.resp_nZdf_s[index] = r.getInt();
				hqfs.resp_nCjss_s[index] = r.getInt();
				hqfs.resp_nCjje_s[index] = r.getInt();
				hqfs.resp_nCjjj_s[index] = r.getInt();
				hqfs.resp_sXxgg_s[index] = r.getUnicodeString();
				if (cmdVersion >= 2)
				{
					hqfs.resp_nCcl_s[index] = r.getInt();
				}
				if (cmdVersion >= 5)
				{
					hqfs.resp_nLb_s[index] = r.getInt();
				}
			}

		}

		if (cmdVersion >= 3)
		{
			hqfs.resp_nWb = r.getInt();
			hqfs.resp_nWc = r.getInt();
			hqfs.resp_nLb = r.getInt();
			hqfs.resp_nZf = r.getInt();
			hqfs.resp_nBuyp = r.getInt();
			hqfs.resp_nSelp = r.getInt();
			hqfs.resp_nLimUp = r.getInt();
			hqfs.resp_nLimDown = r.getInt();
			hqfs.resp_nBjg1 = r.getInt();
			hqfs.resp_nBss1 = r.getInt();
			hqfs.resp_nBjg2 = r.getInt();
			hqfs.resp_nBss2 = r.getInt();

			hqfs.resp_nBjg3 = r.getInt();
			hqfs.resp_nBss3 = r.getInt();
			hqfs.resp_nBjg4 = r.getInt();
			hqfs.resp_nBss4 = r.getInt();
			hqfs.resp_nBjg5 = r.getInt();
			hqfs.resp_nBss5 = r.getInt();
			hqfs.resp_nSjg1 = r.getInt();
			hqfs.resp_nSss1 = r.getInt();
			hqfs.resp_nSjg2 = r.getInt();
			hqfs.resp_nSss2 = r.getInt();
			hqfs.resp_nSjg3 = r.getInt();
			hqfs.resp_nSss3 = r.getInt();
			hqfs.resp_nSjg4 = r.getInt();
			hqfs.resp_nSss4 = r.getInt();
			hqfs.resp_nSjg5 = r.getInt();
			hqfs.resp_nSss5 = r.getInt();
			hqfs.resp_bSuspended = r.getByte();
		}
		if (cmdVersion >= 4)
		{
			hqfs.resp_nJZC = r.getInt();
			hqfs.resp_nGB = r.getInt();
			hqfs.resp_nLTGB = r.getInt();
			hqfs.resp_nSY = r.getInt();
			hqfs.resp_sLinkFlag = r.getString();
			hqfs.resp_sBKCode = r.getString();
			hqfs.resp_sBKName = r.getUnicodeString();
			hqfs.resp_nBKZf = r.getInt();
		}

	}

}

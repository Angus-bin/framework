package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.util.KFloat;

/**
 * 期货K线综合协议
 * 
 * @author xueyan
 * 
 */
public class HQQHKXZHProtocolCoder extends AProtocolCoder<HQQHKXZHProtocol>
{

	@Override
	protected byte[] encode(HQQHKXZHProtocol kxhq)
	{
		RequestCoder reqCoder = new RequestCoder();
		reqCoder.addShort(kxhq.req_wMarketID);
		reqCoder.addString(kxhq.req_sPszCode, ProtocolConstant.MAX_CODE_LENGTH);
		reqCoder.addInt32(kxhq.req_dwKXDate);
		reqCoder.addShort(kxhq.req_wKXType);
		reqCoder.addShort(kxhq.req_wKXCount);
		return reqCoder.getData();
	}

	@Override
	protected void decode(HQQHKXZHProtocol hqkx) throws ProtocolParserException
	{

		ResponseDecoder r = new ResponseDecoder(hqkx.getReceiveData());
		short wCount = 0;
		hqkx.resp_wMarketID = r.getShort();
		hqkx.resp_wType = r.getShort();
		hqkx.resp_sPszCode = r.getString(ProtocolConstant.MAX_CODE_LENGTH);
		hqkx.resp_wsPszName = r
		        .getUnicodeString(ProtocolConstant.MAX_NAME_LENGTH);
		hqkx.resp_nZrsp = r.getInt();
		hqkx.resp_nZhsj = r.getInt();
		hqkx.resp_nJrkp = r.getInt();
		hqkx.resp_nZgcj = r.getInt();
		hqkx.resp_nZdcj = r.getInt();
		hqkx.resp_nZjcj = r.getInt();
		hqkx.resp_nMaxVol = r.getInt();
		hqkx.resp_nHsj = r.getInt();
		hqkx.resp_nZd = r.getInt();
		hqkx.resp_nZdf = r.getInt();
		hqkx.resp_nZf = r.getInt();
		hqkx.resp_nCjss = r.getInt();
		hqkx.resp_nCjje = r.getInt();
		hqkx.resp_nCjjj = r.getInt();
		hqkx.resp_nCcl = r.getInt();
		hqkx.resp_nCc = r.getInt();
		
		hqkx.resp_nWb = r.getInt();
		hqkx.resp_nWc = r.getInt();
		hqkx.resp_nLb = r.getInt();
		hqkx.resp_nBuyp = r.getInt();
		hqkx.resp_nSelp = r.getInt();
		hqkx.resp_nLimUp = r.getInt();
		hqkx.resp_nLimDown = r.getInt();
		
		hqkx.resp_sLinkFlag = r.getString();
		hqkx.resp_bSuspended = r.getByte();
		hqkx.resp_nMaxPrice = r.getInt();
		hqkx.resp_nMinPrice = r.getInt();
        wCount = r.getShort();
        hqkx.resp_wMMFADataCount = wCount;
        if (wCount > 0)
        {
        	hqkx.resp_nBjg_s = new int[wCount];
        	hqkx.resp_nBsl_s = new int[wCount];
            hqkx.resp_nSjg_s = new int[wCount];
            hqkx.resp_nSsl_s = new int[wCount];
        }
        for (int i = 0; i < wCount; i++)
        {
        	hqkx.resp_nBjg_s[i] = r.getInt();
        	hqkx.resp_nBsl_s[i] = r.getInt();
        	hqkx.resp_nSjg_s[i] = r.getInt();
        	hqkx.resp_nSsl_s[i] = r.getInt();
        }
        
        wCount = r.getShort();
        hqkx.resp_wZSDataCount = wCount;
        if (wCount > 0)
        {
        	hqkx.resp_sZSPszCode_s = new String[wCount];
        	hqkx.resp_wsZSPszName_s = new String[wCount];
        	hqkx.resp_nZSXj_s = new int[wCount];
        	hqkx.resp_nZSZdf_s = new int[wCount];
        	hqkx.resp_nZSZs_s = new int[wCount];
        }
        for (int i = 0; i < wCount; i++)
        {
        	hqkx.resp_sZSPszCode_s[i] = r.getString();
        	hqkx.resp_wsZSPszName_s[i] = r.getUnicodeString();//
        	hqkx.resp_nZSXj_s[i] = r.getInt();
        	hqkx.resp_nZSZdf_s[i] = r.getInt();
        	hqkx.resp_nZSZs_s[i] = r.getInt();
        }

        wCount = r.getShort();
        hqkx.resp_wFBDataCount = wCount;
        if (wCount > 0)
        {
        	hqkx.resp_dwFBTime_s = new int[wCount];
        	hqkx.resp_bFBCjlb_s = new byte[wCount];
        	hqkx.resp_nFBZjcj_s = new int[wCount];
        	hqkx.resp_nFBCjss_s = new int[wCount];
        	hqkx.resp_nFBCc_s = new int[wCount];
        	hqkx.resp_nFBbCjxz_s = new byte[wCount];
        }
        for (int i = 0; i < wCount; i++)
        {
        	hqkx.resp_dwFBTime_s[i] = r.getInt();
            hqkx.resp_bFBCjlb_s[i] = r.getByte();
            hqkx.resp_nFBZjcj_s[i] = r.getInt();
            hqkx.resp_nFBCjss_s[i] = r.getInt();
            hqkx.resp_nFBCc_s[i] = r.getInt();
            hqkx.resp_nFBbCjxz_s[i] = r.getByte();
        }

        wCount = r.getShort();
        hqkx.resp_wKXDataCount = wCount;
        if (wCount > 0)
        {
        	hqkx.resp_dwTime_s = new int[wCount];
        	hqkx.resp_nYClose_s = new int[wCount];
        	hqkx.resp_nOpen_s = new int[wCount];
        	hqkx.resp_nZgcj_s = new int[wCount];
        	hqkx.resp_nZdcj_s = new int[wCount];

        	hqkx.resp_nClose_s = new int[wCount];
        	hqkx.resp_nZdf_s = new int[wCount];
        	hqkx.resp_nCjje_s = new int[wCount];
        	hqkx.resp_nCjss_s = new int[wCount];
        	hqkx.resp_nCcl_s = new int[wCount];

        	hqkx.resp_nHsl_s = new int[wCount];
        	hqkx.resp_nSyl_s = new int[wCount];
        	hqkx.resp_nMA1_s = new int[wCount];
            hqkx.resp_nMA2_s = new int[wCount];
            hqkx.resp_nMA3_s = new int[wCount];

            hqkx.resp_nTech1_s = new int[wCount];
            hqkx.resp_nTech2_s = new int[wCount];
            hqkx.resp_nTech3_s = new int[wCount];
            hqkx.resp_wsXxgg_s = new String[wCount];
            hqkx.resp_nHsj_s = new int[wCount];

            hqkx.resp_bFlag_s = new byte[wCount];
            hqkx.resp_nZd_s = new int[wCount];
        }
        for (int i = 0; i < wCount; i++)
        {
        	hqkx.resp_dwTime_s[i] = r.getInt();
        	hqkx.resp_nYClose_s[i] = r.getInt();
        	hqkx.resp_nOpen_s[i] = r.getInt();
        	hqkx.resp_nZgcj_s[i] = r.getInt();
        	hqkx.resp_nZdcj_s[i] = r.getInt();

        	hqkx.resp_nClose_s[i] = r.getInt();
        	hqkx.resp_nZdf_s[i] = r.getInt();
        	hqkx.resp_nCjje_s[i] = r.getInt();
        	hqkx.resp_nCjss_s[i] = r.getInt();
        	hqkx.resp_nCcl_s[i] = r.getInt();

        	hqkx.resp_nHsl_s[i] = r.getInt();
        	hqkx.resp_nSyl_s[i] = r.getInt();
        	hqkx.resp_nMA1_s[i] = r.getInt();
        	hqkx.resp_nMA2_s[i] = r.getInt();
        	hqkx.resp_nMA3_s[i] = r.getInt();

        	hqkx.resp_nTech1_s[i] = r.getInt();
        	hqkx.resp_nTech2_s[i] = r.getInt();
        	hqkx.resp_nTech3_s[i] = r.getInt();
        	hqkx.resp_wsXxgg_s[i] = r.getUnicodeString();
        	hqkx.resp_nHsj_s[i] = r.getInt();

        	hqkx.resp_bFlag_s[i] = r.getByte();
        	hqkx.resp_nZd_s[i] = r.getInt();
        }

       

    }// end decode
}

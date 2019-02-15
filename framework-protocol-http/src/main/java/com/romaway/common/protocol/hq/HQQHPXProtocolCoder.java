package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

/**
 * @author qinyn
 * 
 *         期货排序协议
 */
public class HQQHPXProtocolCoder extends AProtocolCoder<HQQHPXProtocol>
{

	@Override
	protected byte[] encode(HQQHPXProtocol hqqhpx)
	{
		RequestCoder reqCoder = new RequestCoder();
		reqCoder.addShort(hqqhpx.req_wMarketID);// 1
		reqCoder.addShort(hqqhpx.req_wType);// 2
		reqCoder.addByte(hqqhpx.req_bSort);// 3
		reqCoder.addByte(hqqhpx.req_bDirect);// 4
		reqCoder.addShort(hqqhpx.req_wFrom);// 5
		reqCoder.addShort(hqqhpx.req_wCount);// 6
		return reqCoder.getData();
	}

	@Override
	protected void decode(HQQHPXProtocol hqqhpx) throws ProtocolParserException
	{
		ResponseDecoder r = new ResponseDecoder(hqqhpx.getReceiveData());

		short wCount = r.getShort();
		hqqhpx.resp_wCount = wCount;
		hqqhpx.resp_wMarketID_s = new short[wCount];
		hqqhpx.resp_wType_s = new short[wCount];
		hqqhpx.resp_pszCode_s = new String[wCount];
		hqqhpx.resp_pszName_s = new String[wCount];
		hqqhpx.resp_nZrsp_s = new int[wCount];
		hqqhpx.resp_nZhsj_s = new int[wCount];
		hqqhpx.resp_nJrkp_s = new int[wCount];
		hqqhpx.resp_nZgcj_s = new int[wCount];
		hqqhpx.resp_nZdcj_s = new int[wCount];
		hqqhpx.resp_nZjcj_s = new int[wCount];
		hqqhpx.resp_nCjss_s = new int[wCount];
		hqqhpx.resp_nCjje_s = new int[wCount];
		hqqhpx.resp_nJj_s = new int[wCount];
		hqqhpx.resp_nCcl_s = new int[wCount];
		hqqhpx.resp_nCc_s = new int[wCount];
		hqqhpx.resp_nXl_s = new int[wCount];
		hqqhpx.resp_nHsj_s = new int[wCount];
		hqqhpx.resp_nBjg1_s = new int[wCount];
		hqqhpx.resp_nSjg1_s = new int[wCount];
		hqqhpx.resp_nZd_s = new int[wCount];
		hqqhpx.resp_nZdf_s = new int[wCount];
		hqqhpx.resp_nZf_s = new int[wCount];
		hqqhpx.resp_nZl_s = new int[wCount];

		hqqhpx.resp_nWb_s = new int[wCount];
		hqqhpx.resp_nLb_s = new int[wCount];
		hqqhpx.resp_n5Min_s = new int[wCount];
		hqqhpx.resp_bSuspended_s = new byte[wCount];

		hqqhpx.resp_nHsl_s = new int[wCount];
		hqqhpx.resp_nSyl_s = new int[wCount];
		hqqhpx.resp_nBP_s = new int[wCount];
		hqqhpx.resp_nSP_s = new int[wCount];

		for (int index = 0; index < wCount; index++)
		{
			// 1
			hqqhpx.resp_wMarketID_s[index] = r.getShort();
			// 2
			hqqhpx.resp_wType_s[index] = r.getShort();
			// 3
			hqqhpx.resp_pszCode_s[index] = r
			        .getString(ProtocolConstant.MAX_CODE_LENGTH);
			// 4
			hqqhpx.resp_pszName_s[index] = r
			        .getUnicodeString(ProtocolConstant.MAX_NAME_LENGTH);
			// 5
			hqqhpx.resp_nZrsp_s[index] = r.getInt();
			// 6
			hqqhpx.resp_nZhsj_s[index] = r.getInt();
			// 7
			hqqhpx.resp_nJrkp_s[index] = r.getInt();
			// 8
			hqqhpx.resp_nZgcj_s[index] = r.getInt();
			// 9
			hqqhpx.resp_nZdcj_s[index] = r.getInt();
			// 10
			hqqhpx.resp_nZjcj_s[index] = r.getInt();
			// 11
			hqqhpx.resp_nCjss_s[index] = r.getInt();
			// 12
			hqqhpx.resp_nCjje_s[index] = r.getInt();
			// 13
			hqqhpx.resp_nJj_s[index] = r.getInt();
			// 14
			hqqhpx.resp_nCcl_s[index] = r.getInt();
			// 15
			hqqhpx.resp_nCc_s[index] = r.getInt();
			// 16
			hqqhpx.resp_nXl_s[index] = r.getInt();
			// 17
			hqqhpx.resp_nHsj_s[index] = r.getInt();
			// 18
			hqqhpx.resp_nBjg1_s[index] = r.getInt();
			// 19
			hqqhpx.resp_nSjg1_s[index] = r.getInt();
			// 20
			hqqhpx.resp_nZd_s[index] = r.getInt();
			// 21
			hqqhpx.resp_nZdf_s[index] = r.getInt();
			// 22
			hqqhpx.resp_nZf_s[index] = r.getInt();
			// 23
			hqqhpx.resp_nZl_s[index] = r.getInt();
			// 24
			hqqhpx.resp_nWb_s[index] = r.getInt();
			// 25
			hqqhpx.resp_nLb_s[index] = r.getInt();
			// 26
			hqqhpx.resp_n5Min_s[index] = r.getInt();
			// 27
			hqqhpx.resp_bSuspended_s[index] = r.getByte();
			// 28
			hqqhpx.resp_nHsl_s[index] = r.getInt();
			// 29
			hqqhpx.resp_nSyl_s[index] = r.getInt();
			// 30
			hqqhpx.resp_nBP_s[index] = r.getInt();
			// 31
			hqqhpx.resp_nSP_s[index] = r.getInt();
		}

	}

}

package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;

/**
 * 关联股票行情请求协议
 * 
 * @author xueyan
 * 
 */
public class HQLINKProtocolCoder extends AProtocolCoder<HQLINKProtocol>
{

	@Override
	protected byte[] encode(HQLINKProtocol hql)
	{
		RequestCoder requestCoder = new RequestCoder();
		requestCoder.addString(hql.req_pszCode, false);// 1
		requestCoder.addString(hql.req_marketCode, false);// 2

		return requestCoder.getData();
	}

	@Override
	protected void decode(HQLINKProtocol hql) throws ProtocolParserException
	{
		ResponseDecoder r = new ResponseDecoder(hql.getReceiveData());
		hql.resp_wCount = r.getShort();
		hql.resp_wMarketId_s = new short[hql.resp_wCount];
		hql.resp_wType_s = new short[hql.resp_wCount];
		hql.resp_pszCode_s = new String[hql.resp_wCount];
		hql.resp_pszName_s = new String[hql.resp_wCount];
		hql.resp_nZrsp_s = new int[hql.resp_wCount];
		hql.resp_nJrkp_s = new int[hql.resp_wCount];
		hql.resp_nZgcj_s = new int[hql.resp_wCount];
		hql.resp_nZdcj_s = new int[hql.resp_wCount];
		hql.resp_nZjcj_s = new int[hql.resp_wCount];
		hql.resp_nCjss_s = new int[hql.resp_wCount];
		hql.resp_nCjje_s = new int[hql.resp_wCount];
		hql.resp_nZd_s = new int[hql.resp_wCount];
		hql.resp_bSuspended_s = new byte[hql.resp_wCount];
		hql.resp_sCodeType_s = new String[hql.resp_wCount];

		for (int index = 0; index < hql.resp_wCount; index++)
		{
			hql.resp_wMarketId_s[index] = r.getShort();
			hql.resp_wType_s[index] = r.getShort();
			hql.resp_pszCode_s[index] = r.getString();
			hql.resp_pszName_s[index] = r
			        .getUnicodeString(ProtocolConstant.MAX_NAME_LENGTH);
			hql.resp_nZrsp_s[index] = r.getInt();
			hql.resp_nJrkp_s[index] = r.getInt();
			hql.resp_nZgcj_s[index] = r.getInt();
			hql.resp_nZdcj_s[index] = r.getInt();
			hql.resp_nZjcj_s[index] = r.getInt();
			hql.resp_nCjss_s[index] = r.getInt();
			hql.resp_nCjje_s[index] = r.getInt();
			hql.resp_nZd_s[index] = r.getInt();
			hql.resp_bSuspended_s[index] = r.getByte();
			hql.resp_sCodeType_s[index] = r.getUnicodeString();

		}

	}

}

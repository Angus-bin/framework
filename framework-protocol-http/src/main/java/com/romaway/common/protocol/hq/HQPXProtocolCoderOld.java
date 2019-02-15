package com.romaway.common.protocol.hq;

import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.coder.RequestCoder;
import com.romaway.common.protocol.coder.ResponseDecoder;
import com.romaway.common.protocol.hq.HQPXProtocolOld;

public class HQPXProtocolCoderOld extends AProtocolCoder<HQPXProtocolOld> {

	@Override
	protected byte[] encode(HQPXProtocolOld hqpx) {
		RequestCoder reqCoder = new RequestCoder();
		reqCoder.addShort(hqpx.req_wMarketID);// 1
		reqCoder.addShort(hqpx.req_wType);// 2
		reqCoder.addByte(hqpx.req_bSort);// 3
		reqCoder.addByte(hqpx.req_bDirect);// 4
		reqCoder.addShort(hqpx.req_wFrom);// 5
		reqCoder.addShort(hqpx.req_wCount);// 6

		int cmdVersion = hqpx.getCmdServerVersion();
		if (cmdVersion >= 3) {
			reqCoder.addString(hqpx.req_pszBKCode, false);// 7
		}
		return reqCoder.getData();
	}

	@Override
	protected void decode(HQPXProtocolOld hqpx) throws ProtocolParserException {
		ResponseDecoder r = new ResponseDecoder(hqpx.getReceiveData());

		short wCount = r.getShort();
		hqpx.resp_wCount = wCount;
		int cmdVersion = hqpx.getCmdServerVersion();
		hqpx.resp_wMarketID_s = new short[wCount];
		hqpx.resp_wType_s = new short[wCount];
		hqpx.resp_pszCode_s = new String[wCount];
		hqpx.resp_pszName_s = new String[wCount];
		hqpx.resp_nZrsp_s = new int[wCount];

		hqpx.resp_nZhsj_s = new int[wCount];
		hqpx.resp_nJrkp_s = new int[wCount];
		hqpx.resp_nZgcj_s = new int[wCount];
		hqpx.resp_nZdcj_s = new int[wCount];
		hqpx.resp_nZjcj_s = new int[wCount];

		hqpx.resp_nCjss_s = new int[wCount];
		hqpx.resp_nCjje_s = new int[wCount];
		hqpx.resp_nCcl_s = new int[wCount];
		hqpx.resp_nHsj_s = new int[wCount];
		hqpx.resp_nBjg1_s = new int[wCount];

		hqpx.resp_nSjg1_s = new int[wCount];
		hqpx.resp_nZd_s = new int[wCount];
		hqpx.resp_nZdf_s = new int[wCount];
		hqpx.resp_nZf_s = new int[wCount];
		hqpx.resp_nZl_s = new int[wCount];

		hqpx.resp_nWb_s = new int[wCount];
		hqpx.resp_nLb_s = new int[wCount];
		hqpx.resp_n5Min_s = new int[wCount];
		hqpx.resp_bSuspended_s = new byte[wCount];

		// dwVersion>=1,增加换手率、市盈率
		hqpx.resp_nHsl_s = new int[wCount];
		hqpx.resp_nSyl_s = new int[wCount];
		hqpx.resp_nReserved_s = new int[wCount];

		// dwVersion>=2,增加买盘、卖盘
		hqpx.resp_nBP_s = new int[wCount];
		hqpx.resp_nSP_s = new int[wCount];

		// dwVersion>=3,增加是否关联股票
		hqpx.resp_sLinkFlag_s = new String[wCount];

		// dwVersion>=4,增加样本数量、样本均价、平均股本、总市值、占比、指数级别标识
		hqpx.resp_dwsampleNum_s = new int[wCount];
		hqpx.resp_nsampleAvgPrice_s = new int[wCount];
		hqpx.resp_navgStock_s = new int[wCount];
		hqpx.resp_nmarketValue_s = new int[wCount];
		hqpx.resp_nzb_s = new int[wCount];
		hqpx.resp_slevelFlag_s = new String[wCount];

		for (int index = 0; index < wCount; index++) {
			hqpx.resp_wMarketID_s[index] = r.getShort();
			hqpx.resp_wType_s[index] = r.getShort();
			hqpx.resp_pszCode_s[index] = r
					.getString(ProtocolConstant.MAX_CODE_LENGTH);
			hqpx.resp_pszName_s[index] = r
					.getUnicodeString(ProtocolConstant.MAX_NAME_LENGTH);
			// (hqpx.req_wFrom + 1 + index) + "." +
			// r.getUnicodeString(ProtocolConstant.MAX_NAME_LENGTH);
			hqpx.resp_nZrsp_s[index] = r.getInt();

			hqpx.resp_nZhsj_s[index] = r.getInt();
			hqpx.resp_nJrkp_s[index] = r.getInt();
			hqpx.resp_nZgcj_s[index] = r.getInt();
			hqpx.resp_nZdcj_s[index] = r.getInt();
			hqpx.resp_nZjcj_s[index] = r.getInt();

			hqpx.resp_nCjss_s[index] = r.getInt();
			hqpx.resp_nCjje_s[index] = r.getInt();
			hqpx.resp_nCcl_s[index] = r.getInt();
			hqpx.resp_nHsj_s[index] = r.getInt();
			hqpx.resp_nBjg1_s[index] = r.getInt();

			hqpx.resp_nSjg1_s[index] = r.getInt();
			hqpx.resp_nZd_s[index] = r.getInt();
			hqpx.resp_nZdf_s[index] = r.getInt();
			hqpx.resp_nZf_s[index] = r.getInt();
			hqpx.resp_nZl_s[index] = r.getInt();

			hqpx.resp_nWb_s[index] = r.getInt();
			hqpx.resp_nLb_s[index] = r.getInt();
			hqpx.resp_n5Min_s[index] = r.getInt();
			hqpx.resp_bSuspended_s[index] = r.getByte();

			if (cmdVersion >= 1) {
				hqpx.resp_nHsl_s[index] = r.getInt();
				hqpx.resp_nSyl_s[index] = r.getInt();
				hqpx.resp_nReserved_s[index] = r.getInt();
			}
			if (cmdVersion >= 2) {
				hqpx.resp_nBP_s[index] = r.getInt();
				hqpx.resp_nSP_s[index] = r.getInt();

			}
			if (cmdVersion >= 3) {
				hqpx.resp_sLinkFlag_s[index] = r.getString();
			}
			if (cmdVersion >= 4) {
				hqpx.resp_dwsampleNum_s[index] = r.getInt();
				hqpx.resp_nsampleAvgPrice_s[index] = r.getInt();
				hqpx.resp_navgStock_s[index] = r.getInt();
				hqpx.resp_nmarketValue_s[index] = r.getInt();
				hqpx.resp_nzb_s[index] = r.getInt();
				hqpx.resp_slevelFlag_s[index] = r.getString();
			}

		}

		if (cmdVersion >= 5) {
			hqpx.resp_totalCount = r.getShort();
		}

	}
}

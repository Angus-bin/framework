package com.romaway.common.protocol.hq;

import com.google.protobuf.InvalidProtocolBufferException;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.R;
import com.romaway.common.protocol.util.ULong;

import java.util.List;

import protobuf.Protobuf;
import protobuf.Protobuf.multiStocks_rep;
import protobuf.Protobuf.multi_selectedStocks_req;
import protobuf.Protobuf.rank_option;
import protobuf.Protobuf.selectedStocks_rep;
import protobuf.Protobuf.selectedStocks_req;
import protobuf.Protobuf.stock_details_data;

import com.google.protobuf.InvalidProtocolBufferException;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.util.ULongUtils;
import com.romaway.commons.log.Logger;

/**
 * @author qinyn
 *  自选行情协议
 */
public class HQZXProtocolCoder extends AProtocolCoder<HQZXProtocol>
{

	@Override
	protected byte[] encode(HQZXProtocol hqzx) {
		//通用排序控制参数
		rank_option.Builder rank_optionbuild = rank_option.newBuilder();
		rank_optionbuild.setWType(0);
		rank_optionbuild.setBSort(hqzx.req_bSort);
		if (hqzx.req_bDirect == 0) {
			rank_optionbuild.setBDirect(false);
		} else {
			rank_optionbuild.setBDirect(true);
		}
		rank_optionbuild.setWFrom(hqzx.req_wFrom);
		rank_optionbuild.setWCount(hqzx.req_wCount);

		// 暂底层请求时添加上 涨停价,跌停价,板块id,板块名称:
		rank_optionbuild.setFieldsBitMap(ULongUtils.merge(ULong.valueOf(hqzx.req_bitmap),
				ULong.valueOf(ULongUtils.getWholeBitMap(OriginalContext.getContext().
						getResources().getIntArray(R.array.hq_tougug_data_protocol_bitmap)))).longValue());

		//自选请求
		selectedStocks_req.Builder selectedStocks_reqbuild = selectedStocks_req.newBuilder();
		selectedStocks_reqbuild.setPszCodes(hqzx.req_pszCodes);
		selectedStocks_reqbuild.setMarketList(hqzx.req_marketList);
		selectedStocks_reqbuild.setOptions(rank_optionbuild.build());

		multi_selectedStocks_req.Builder multi_selectedStocks_reqbuild = multi_selectedStocks_req.newBuilder();
		multi_selectedStocks_reqbuild.addReqs(selectedStocks_reqbuild.build());
		multi_selectedStocks_req mmulti_selectedStocks_req = multi_selectedStocks_reqbuild.build();

		return mmulti_selectedStocks_req.toByteArray();
	}
	
	@Override
	protected void decode(HQZXProtocol hqzx) throws ProtocolParserException
	{		
		if (hqzx.getReceiveData() != null) {
         	Protobuf.multi_selectedStocks_rep rep;
			try {
				rep = Protobuf.multi_selectedStocks_rep.parseFrom(hqzx.getReceiveData());
				for(int i=0;i<rep.getRepsCount();i++){
					selectedStocks_rep mselectedStocks_rep=rep.getReps(i);
					multiStocks_rep mmultiStocks_rep=mselectedStocks_rep.getRep();
					rank_option rank_option=mmultiStocks_rep.getOptions();
					hqzx.resp_wFrom=rank_option.getWFrom();					
	         		List<stock_details_data> array=mmultiStocks_rep.getDataArrList();
	         		hqzx.resp_wCount=array.size();
	         		hqzx.resp_wMarketID_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_wType_s = new short[hqzx.resp_wCount];
	        		hqzx.resp_pszCode_s = new String[hqzx.resp_wCount];
	        		hqzx.resp_pszName_s = new String[hqzx.resp_wCount];
	        		hqzx.resp_pszMark_s = new String[hqzx.resp_wCount];
	        
	        		hqzx.resp_nZrsp_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nZhsj_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nJrkp_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nZgcj_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nZdcj_s = new int[hqzx.resp_wCount];
	        
	        		hqzx.resp_nZjcj_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nCjss_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nCjje_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nCcl_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nHsj_s = new int[hqzx.resp_wCount];
	        
	        		hqzx.resp_nLimUp_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nLimDown_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nBjg1_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nBss1_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nBjg2_s = new int[hqzx.resp_wCount];
	        		
	        		hqzx.resp_nBss2_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nBjg3_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nBss3_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nBjg4_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nBss4_s = new int[hqzx.resp_wCount];
	        		
	        		hqzx.resp_nBjg5_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nBss5_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nSjg1_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nSss1_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nSjg2_s = new int[hqzx.resp_wCount];
	        		
	        		hqzx.resp_nSss2_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nSjg3_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nSss3_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nSjg4_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nSss4_s = new int[hqzx.resp_wCount];
	        		
	        		hqzx.resp_nSjg5_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nSss5_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nZd_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nZdf_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nZl_s = new int[hqzx.resp_wCount];
	        		
	        		hqzx.resp_nWb_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nLb_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_n5Min_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nCjjj_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_bSuspended_s = new byte[hqzx.resp_wCount];
	        		
	        		hqzx.resp_nHsl_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nSyl_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nBP_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_nSP_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_sLinkFlag_s = new String[hqzx.resp_wCount];
	        		
	        		hqzx.resp_nZZZQValPrice = new int[hqzx.resp_wCount];
	        		hqzx.resp_sZZZQValPrice = new String[hqzx.resp_wCount];
	        		hqzx.resp_pszBKCode_s = new int[hqzx.resp_wCount];
	        		hqzx.resp_pszBKName_s = new String[hqzx.resp_wCount];
	        		hqzx.resp_nBKzdf_s = new int[hqzx.resp_wCount];
	         		for(int index = 0; index < hqzx.resp_wCount; index++){
	         			stock_details_data mstock_details_data=array.get(index);
	         			hqzx.resp_wMarketID_s[index] = mstock_details_data.getMarketID();
	        			hqzx.resp_wType_s[index] =(short) mstock_details_data.getWType();
	        			hqzx.resp_pszCode_s[index] = mstock_details_data.getStockCode();
	        			hqzx.resp_pszName_s[index] = mstock_details_data.getStockName();
	        			hqzx.resp_pszMark_s[index] = mstock_details_data.getPszMark();

						hqzx.resp_nZrsp_s[index] = mstock_details_data.getNZrsp();
	        			hqzx.resp_nZhsj_s[index] = 0;
	        			hqzx.resp_nJrkp_s[index] = mstock_details_data.getNJrkp();
	        			hqzx.resp_nZgcj_s[index] = mstock_details_data.getNZgcj();
	        			hqzx.resp_nZdcj_s[index] = mstock_details_data.getNZdcj();
	        
	        			hqzx.resp_nZjcj_s[index] = mstock_details_data.getNZjcj();
	        			hqzx.resp_nCjss_s[index] = mstock_details_data.getNCjss();
	        			hqzx.resp_nCjje_s[index] = mstock_details_data.getNCjje();
	        			hqzx.resp_nCcl_s[index] = mstock_details_data.getTotalLongPosition();
	        			hqzx.resp_nHsj_s[index] = 0;
	        
	        			hqzx.resp_nLimUp_s[index] = mstock_details_data.getNLimUp();
	        			hqzx.resp_nLimDown_s[index] = mstock_details_data.getNLimDown();
	        			hqzx.resp_nBjg1_s[index] = mstock_details_data.getNBjg1();
	        			hqzx.resp_nBss1_s[index] = mstock_details_data.getNBsl1();
	        			hqzx.resp_nBjg2_s[index] = 0;
	        			
	        			hqzx.resp_nBss2_s[index] = 0;
	        			hqzx.resp_nBjg3_s[index] = 0;
	        			hqzx.resp_nBss3_s[index] = 0;
	        			hqzx.resp_nBjg4_s[index] = 0;
	        			hqzx.resp_nBss4_s[index] = 0;
	        			
	        			hqzx.resp_nBjg5_s[index] = 0;
	        			hqzx.resp_nBss5_s[index] = 0;
	        			hqzx.resp_nSjg1_s[index] = mstock_details_data.getNSjg1();
	        			hqzx.resp_nSss1_s[index] = mstock_details_data.getNSsl1();
	        			hqzx.resp_nSjg2_s[index] = 0;
	        			
	        			hqzx.resp_nSss2_s[index] = 0;
	        			hqzx.resp_nSjg3_s[index] = 0;
	        			hqzx.resp_nSss3_s[index] = 0;
	        			hqzx.resp_nSjg4_s[index] = 0;
	        			hqzx.resp_nSss4_s[index] = 0;
	        			
	        			hqzx.resp_nSjg5_s[index] = 0;
	        			hqzx.resp_nSss5_s[index] = 0;
	        			hqzx.resp_nZd_s[index] = mstock_details_data.getNZd();
	        			hqzx.resp_nZdf_s[index] = mstock_details_data.getNZdf();
	        			hqzx.resp_nZl_s[index] = 0;
	        			
	        			hqzx.resp_nWb_s[index] = 0;
	        			hqzx.resp_nLb_s[index] = mstock_details_data.getNLb();
	        			hqzx.resp_n5Min_s[index] = mstock_details_data.getN5Min();
	        			hqzx.resp_nCjjj_s[index] = 0;
	        			if(mstock_details_data.getBSuspended()){
	        				hqzx.resp_bSuspended_s[index] = 1;
	        			}else{
	        				hqzx.resp_bSuspended_s[index] = 0;
	        			}
	        			
	        			hqzx.resp_nHsl_s[index] = mstock_details_data.getNHsl();
	        			hqzx.resp_nSyl_s[index] = mstock_details_data.getNSyl();
	        			hqzx.resp_nBP_s[index] = 0;
	        			hqzx.resp_nSP_s[index] = 0;
	        			hqzx.resp_sLinkFlag_s[index] = "";
	        			
	        			hqzx.resp_nZZZQValPrice[index] = 0;
	        			hqzx.resp_sZZZQValPrice[index] = "";
	        			hqzx.resp_pszBKCode_s[index] = mstock_details_data.getBlockID();
	        			hqzx.resp_pszBKName_s[index] = mstock_details_data.getBlockName();
	        			hqzx.resp_nBKzdf_s[index] = 0;
	         		}
	         	}
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}         	
         }
	}
}

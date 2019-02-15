package com.romaway.common.protocol.hq;

import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.R;
import com.romaway.common.protocol.util.ULongUtils;

import java.io.IOException;
import java.util.List;

import protobuf.Protobuf;
import protobuf.Protobuf.multiStocks_rep;
import protobuf.Protobuf.multi_stockRank_req;
import protobuf.Protobuf.rank_option;
import protobuf.Protobuf.stockRank_rep;
import protobuf.Protobuf.stockRank_req;
import protobuf.Protobuf.stock_details_data;

/**
 * @author qinyn
 * 
 *         行情排序协议
 */
public class HQPXProtocolCoder extends AProtocolCoder<HQPXProtocol> {

	@Override
	protected byte[] encode(HQPXProtocol hqpx) {
		multi_stockRank_req.Builder multi_stockRank_reqbuild=multi_stockRank_req.newBuilder();
		if(hqpx.is_array_req){
			for (int i=0; i<hqpx.req_wMarketID_array.length; i++){
				//通用排序控制参数
				rank_option.Builder rank_optionbuild=rank_option.newBuilder();
				rank_optionbuild.setWType(hqpx.req_wType_array[i]);
				rank_optionbuild.setBSort(hqpx.req_bSort_array[i]);
				if(hqpx.req_bDirect_array[i]==0){
					rank_optionbuild.setBDirect(false);
				}else{
					rank_optionbuild.setBDirect(true);
				}
				rank_optionbuild.setWFrom(hqpx.req_wFrom_array[i]);
				rank_optionbuild.setWCount(hqpx.req_wCount_array[i]);

				long whole;
				if (hqpx.req_fieldsBitMap > 0){
					whole = hqpx.req_fieldsBitMap;
				}else{
					whole = getMarketFieldBitmap(hqpx.req_wMarketID_array[i]);
				}
				rank_optionbuild.setFieldsBitMap(whole);
				//板块请求
				stockRank_req.Builder stockRank_reqbuild=stockRank_req.newBuilder();
				stockRank_reqbuild.setOptions(rank_optionbuild.build());
				stockRank_reqbuild.setWMarketID(hqpx.req_wMarketID_array[i]);
				stockRank_reqbuild.setSBKCode(hqpx.req_pszBKCode);
				multi_stockRank_reqbuild.addReqs(stockRank_reqbuild.build());
			}
		}else if(hqpx.req_autoRefreshArray!=null){
			int reqCount=0;
			for(int j=0;j<hqpx.req_autoRefreshArray.length;j++){
				if(hqpx.req_autoRefreshArray[j]>0){
					//通用排序控制参数
					rank_option.Builder rank_optionbuild=rank_option.newBuilder();
					rank_optionbuild.setWType(hqpx.req_wType);
					rank_optionbuild.setBSort(hqpx.req_bSort_new[j+2]);
					if(hqpx.req_bDirect_new[j+2]==0){
						rank_optionbuild.setBDirect(false);
					}else{
						rank_optionbuild.setBDirect(true);
					}
					rank_optionbuild.setWFrom(hqpx.req_wFrom);
					rank_optionbuild.setWCount(hqpx.req_wCount);

					if((hqpx.req_autoRefreshArray[j]==2||hqpx.req_autoRefreshArray[j]==3)){
						// 涨跌幅: 需获取昨日收盘价;
						rank_optionbuild.setFieldsBitMap(ULongUtils.getWholeBitMap(OriginalContext.getContext().getResources().getIntArray(R.array.hq_stocklist_zdf_protocol_bitmap)));
					}else{
						// 其他: 昨日收盘, 量比, 5分钟涨速, 振幅, 换手率
						rank_optionbuild.setFieldsBitMap(ULongUtils.getWholeBitMap(OriginalContext.getContext().getResources().getIntArray(R.array.hq_stocklist_other_protocol_bitmap)));
					}

					//板块请求
					stockRank_req.Builder stockRank_reqbuild=stockRank_req.newBuilder();
					stockRank_reqbuild.setOptions(rank_optionbuild.build());
					stockRank_reqbuild.setWMarketID(hqpx.req_wMarketID);
					stockRank_reqbuild.setSBKCode(hqpx.req_pszBKCode);
					multi_stockRank_reqbuild.addReqs(stockRank_reqbuild.build());
				}
			}
		}else{
			//通用排序控制参数
	    	rank_option.Builder rank_optionbuild=rank_option.newBuilder();
	    	rank_optionbuild.setWType(hqpx.req_wType);
	    	rank_optionbuild.setBSort(hqpx.req_bSort);
	    	if(hqpx.req_bDirect==0){
	    		rank_optionbuild.setBDirect(false);
	    	}else{
	    		rank_optionbuild.setBDirect(true);
	    	}  	
	    	rank_optionbuild.setWFrom(hqpx.req_wFrom);
	    	rank_optionbuild.setWCount(hqpx.req_wCount);

			long whole;
			if (hqpx.req_fieldsBitMap > 0){
				whole = hqpx.req_fieldsBitMap;
			}else{
				whole = getMarketFieldBitmapDetailList(hqpx.req_wMarketID);
			}
			rank_optionbuild.setFieldsBitMap(whole);
	    	//板块请求
	    	stockRank_req.Builder stockRank_reqbuild=stockRank_req.newBuilder();
	    	stockRank_reqbuild.setOptions(rank_optionbuild.build());
	    	stockRank_reqbuild.setWMarketID(hqpx.req_wMarketID);
	    	stockRank_reqbuild.setSBKCode(hqpx.req_pszBKCode);
	    	multi_stockRank_reqbuild.addReqs(stockRank_reqbuild.build());
		}
		
	
    	multi_stockRank_req mmulti_stockRank_req=multi_stockRank_reqbuild.build();

		return mmulti_stockRank_req.toByteArray();
	}

	/**
	 * 根据市场Id获取该市场排行榜列表需要请求数据的fieldBitmap值
	 * @param marketId
	 * @return
     */
	private long getMarketFieldBitmap(short marketId) {
		long whole;
		switch (marketId) {
            // 考虑沪深排行榜列表, 沪深默认获取所有的其他:
            case ProtocolConstant.SE_SZ:
            case ProtocolConstant.SE_SH:
            case ProtocolConstant.SE_SS:
                // 其他: 昨日收盘, 量比, 5分钟涨速, 振幅, 换手率
				whole = ULongUtils.getWholeBitMap(
						OriginalContext.getContext().getResources().getIntArray(R.array.hq_stocklist_other_protocol_bitmap));
                break;
            case ProtocolConstant.SE_SGT:
            case ProtocolConstant.SE_HGT:
            case ProtocolConstant.SE_GG:
            case ProtocolConstant.SE_BH:
            default:
                // [Bug]港股不支持请求量比字段, 改为只请求涨跌幅及昨日收盘价;
                whole = ULongUtils.getWholeBitMap(
						OriginalContext.getContext().getResources().getIntArray(R.array.hq_stocklist_zdf_protocol_bitmap));
                break;
        }
		return whole;
	}

	/**
	 * 根据市场Id获取该市场排行榜列表需要请求数据的fieldBitmap值
	 * @param marketId
	 * @return
	 */
	private long getMarketFieldBitmapDetailList(short marketId) {
		long whole;
		switch (marketId) {
			case ProtocolConstant.SE_SGT:
			case ProtocolConstant.SE_HGT:
			case ProtocolConstant.SE_GG:
			case ProtocolConstant.SE_BH:
				// [Bug]港股不支持请求量比字段, 改为只请求涨跌幅及昨日收盘价;
				whole = ULongUtils.getWholeBitMap(
						OriginalContext.getContext().getResources().getIntArray(R.array.hq_hangqing_list_protocol_bitmap));
				break;
			// 考虑沪深排行榜列表, 沪深默认获取所有的其他:
			case ProtocolConstant.SE_SZ:
			case ProtocolConstant.SE_SH:
			case ProtocolConstant.SE_SS:
			default:
				// 其他: 昨日收盘, 量比, 5分钟涨速, 振幅, 换手率
				whole = ULongUtils.getWholeBitMap(
						OriginalContext.getContext().getResources().getIntArray(R.array.hq_hushen_more_list_protocol_bitmap));
				break;
		}
		return whole;
	}

	@Override
	protected void decode(HQPXProtocol hqpx) throws ProtocolParserException {	
		 if (hqpx.getReceiveData() != null) {
         	Protobuf.multi_stockRank_rep rep;
			try {
				rep = Protobuf.multi_stockRank_rep.parseFrom(hqpx.getReceiveData());	
				if(hqpx.is_array_req){
					int repsCount = rep.getRepsCount();
					initArray(repsCount, hqpx);
					for(int i=0;i<repsCount; i++){
						stockRank_rep mstockRank_rep=rep.getReps(i);
						multiStocks_rep mmultiStocks_rep=mstockRank_rep.getRep();
						rank_option mrank_option=mmultiStocks_rep.getOptions();
						hqpx.resp_wFrom_array[i] = mrank_option.getWFrom();
						hqpx.resp_bSort_array[i] = mrank_option.getBSort();
						if(mrank_option.getBDirect()){
							hqpx.resp_bDirect_array[i] =1;
						}else{
							hqpx.resp_bDirect_array[i] =0;
						}

						hqpx.resp_wTotalCount_array[i] = mrank_option.getWTotalCount();
						List<stock_details_data> array=mmultiStocks_rep.getDataArrList();
						int wCount = array.size();
						hqpx.resp_wCount_array[i] = wCount;
						if(wCount>0){
							hqpx.resp_wMarketID_s_array[i] = new int[wCount];
							hqpx.resp_wType_s_array[i] = new short[wCount];
							hqpx.resp_wType_fix_array[i] = new short[wCount];
							hqpx.resp_pszCode_s_array[i] = new String[wCount];
							hqpx.resp_pszName_s_array[i] = new String[wCount];
							hqpx.resp_pszMark_s_array[i] = new String[wCount];
							hqpx.resp_nZrsp_s_array[i] = new int[wCount];
							hqpx.resp_nZhsj_s_array[i] = new int[wCount];
							hqpx.resp_nJrkp_s_array[i] = new int[wCount];
							hqpx.resp_nZgcj_s_array[i] = new int[wCount];

							hqpx.resp_nZdcj_s_array[i] = new int[wCount];
							hqpx.resp_nZjcj_s_array[i] = new int[wCount];
							hqpx.resp_nCjss_s_array[i] = new int[wCount];
							hqpx.resp_nCjje_s_array[i] = new int[wCount];
							hqpx.resp_nCcl_s_array[i] = new int[wCount];

							hqpx.resp_nHsj_s_array[i] = new int[wCount];
							hqpx.resp_nBjg1_s_array[i] = new int[wCount];
							hqpx.resp_nBsl1_s_array[i] = new int[wCount];
							hqpx.resp_nSjg1_s_array[i] = new int[wCount];
							hqpx.resp_nSsl1_s_array[i] = new int[wCount];

							hqpx.resp_nZd_s_array[i] = new int[wCount];
							hqpx.resp_nZdf_s_array[i] = new int[wCount];
							hqpx.resp_nZf_s_array[i] = new int[wCount];
							hqpx.resp_nZl_s_array[i] = new int[wCount];
							hqpx.resp_nWb_s_array[i] = new int[wCount];

							hqpx.resp_nLb_s_array[i] = new int[wCount];
							hqpx.resp_n5Min_s_array[i] = new int[wCount];
							hqpx.resp_bSuspended_s_array[i] = new byte[wCount];
							hqpx.resp_nHsl_s_array[i] = new int[wCount];
							hqpx.resp_nSyl_s_array[i] = new int[wCount];

							hqpx.resp_nReserved_s_array[i] = new int[wCount];
							hqpx.resp_nBP_s_array[i] = new int[wCount];
							hqpx.resp_nSP_s_array[i] = new int[wCount];
							hqpx.resp_sLinkFlag_s_array[i] = new String[wCount];
							hqpx.resp_dwsampleNum_s_array[i] = new int[wCount];

							hqpx.resp_nsampleAvgPrice_s_array[i] = new int[wCount];
							hqpx.resp_navgStock_s_array[i] = new int[wCount];
							hqpx.resp_nmarketValue_s_array[i] = new int[wCount];
							hqpx.resp_nzb_s_array[i] = new int[wCount];
							hqpx.resp_slevelFlag_s_array[i] = new String[wCount];

							hqpx.resp_pszBKCode_s_array[i] = new String[wCount];
							hqpx.resp_pszBKName_s_array[i] = new String[wCount];
							hqpx.resp_nBKzdf_s_array[i] = new int[wCount];
							for(int index = 0; index < wCount; index++){
								stock_details_data mstock_details_data=array.get(index);
								hqpx.resp_wMarketID_s_array[i][index] =mstock_details_data.getMarketID();
								hqpx.resp_wType_s_array[i][index] = (short) mstock_details_data.getWType();
								hqpx.resp_wType_fix_array[i][index] = (short) mstock_details_data.getWType();
								hqpx.resp_pszCode_s_array[i][index] =mstock_details_data.getStockCode();
								hqpx.resp_pszName_s_array[i][index] =mstock_details_data.getStockName();

								hqpx.resp_pszMark_s_array[i][index] =mstock_details_data.getPszMark();
								hqpx.resp_nZrsp_s_array[i][index] = mstock_details_data.getNZrsp();
								hqpx.resp_nZhsj_s_array[i][index] = 0;
								hqpx.resp_nJrkp_s_array[i][index] = mstock_details_data.getNJrkp();
								hqpx.resp_nZgcj_s_array[i][index] = mstock_details_data.getNZgcj();

								hqpx.resp_nZdcj_s_array[i][index] = mstock_details_data.getNZdcj();
								hqpx.resp_nZjcj_s_array[i][index] = mstock_details_data.getNZjcj();
								hqpx.resp_nCjss_s_array[i][index] = mstock_details_data.getNCjss();
								hqpx.resp_nCjje_s_array[i][index] = mstock_details_data.getNCjje();
								hqpx.resp_nCcl_s_array[i][index] = mstock_details_data.getTotalLongPosition();

								hqpx.resp_nHsj_s_array[i][index] = 0;
								hqpx.resp_nBjg1_s_array[i][index] = mstock_details_data.getNBjg1();
								hqpx.resp_nSjg1_s_array[i][index] = mstock_details_data.getNSjg1();
								hqpx.resp_nBsl1_s_array[i][index] = mstock_details_data.getNBsl1();
								hqpx.resp_nSsl1_s_array[i][index]=mstock_details_data.getNSsl1();

								hqpx.resp_nZd_s_array[i][index] = mstock_details_data.getNZd();
								hqpx.resp_nZdf_s_array[i][index] = mstock_details_data.getNZdf();
								hqpx.resp_nZf_s_array[i][index] = mstock_details_data.getNZf();
								hqpx.resp_nZl_s_array[i][index] = 0;
								hqpx.resp_nWb_s_array[i][index] = 0;

								hqpx.resp_nLb_s_array[i][index] = mstock_details_data.getNLb();
								hqpx.resp_n5Min_s_array[i][index] = mstock_details_data.getN5Min();
								if(mstock_details_data.getBSuspended()){
									hqpx.resp_bSuspended_s_array[i][index] = 1;
								}else{
									hqpx.resp_bSuspended_s_array[i][index] = 0;
								}
								hqpx.resp_nHsl_s_array[i][index] = mstock_details_data.getNHsl();
								hqpx.resp_nSyl_s_array[i][index] = mstock_details_data.getNSyl();

								hqpx.resp_nReserved_s_array[i][index]=0;
								hqpx.resp_nBP_s_array[i][index] = 0;
								hqpx.resp_nSP_s_array[i][index] = 0;
								hqpx.resp_sLinkFlag_s_array[i][index] = "";
								hqpx.resp_dwsampleNum_s_array[i][index] = 0;

								hqpx.resp_nsampleAvgPrice_s_array[i][index]=0;
								hqpx.resp_navgStock_s_array[i][index] = 0;
								hqpx.resp_nmarketValue_s_array[i][index] = mstock_details_data.getNZSZ();
								hqpx.resp_nzb_s_array[i][index] = 0;
								hqpx.resp_slevelFlag_s_array[i][index] ="";

								hqpx.resp_pszBKCode_s_array[i][index] = "";
								hqpx.resp_pszBKName_s_array[i][index] =mstock_details_data.getStockName();
								hqpx.resp_nBKzdf_s_array[i][index] =0;

							}
						}

					}
				}else if(hqpx.req_autoRefreshArray!=null){
					int repsCount=rep.getRepsCount();
					hqpx.resp_requestCount_new=repsCount;
					if(repsCount>0){
						hqpx.resp_wFrom_new=new int[repsCount];
						hqpx.resp_bSort_new=new int[repsCount];
						hqpx.resp_bDirect_new=new byte[repsCount];
						hqpx.resp_wTotalCount_new=new int[repsCount];
						hqpx.resp_wCount_new=new int[repsCount];
						hqpx.resp_wMarketID_s_new = new int[repsCount][];
	         			hqpx.resp_wType_s_new = new short[repsCount][];
	         			hqpx.resp_wType_fix_new = new short[repsCount][];
	         			hqpx.resp_pszCode_s_new = new String[repsCount][];
	         			hqpx.resp_pszName_s_new = new String[repsCount][];
	         			
	         			hqpx.resp_pszMark_s_new = new String[repsCount][];
	         			hqpx.resp_nZrsp_s_new = new int[repsCount][];
	         			hqpx.resp_nZhsj_s_new = new int[repsCount][];
	         			hqpx.resp_nJrkp_s_new = new int[repsCount][];
	         			hqpx.resp_nZgcj_s_new = new int[repsCount][];
	         			
	         			hqpx.resp_nZdcj_s_new = new int[repsCount][];
	         			hqpx.resp_nZjcj_s_new = new int[repsCount][];
	         			hqpx.resp_nCjss_s_new = new int[repsCount][];
	         			hqpx.resp_nCjje_s_new = new int[repsCount][];
	         			hqpx.resp_nCcl_s_new = new int[repsCount][];
	         			
	         			hqpx.resp_nHsj_s_new = new int[repsCount][];
	         			hqpx.resp_nBjg1_s_new = new int[repsCount][];
	         			hqpx.resp_nBsl1_s_new = new int[repsCount][];
	         			hqpx.resp_nSjg1_s_new = new int[repsCount][];
	         			hqpx.resp_nSsl1_s_new = new int[repsCount][];
	         			
	         			hqpx.resp_nZd_s_new = new int[repsCount][];
	         			hqpx.resp_nZdf_s_new = new int[repsCount][];
	         			hqpx.resp_nZf_s_new = new int[repsCount][];
	         			hqpx.resp_nZl_s_new = new int[repsCount][];
	         			hqpx.resp_nWb_s_new = new int[repsCount][];
	         			
	         			hqpx.resp_nLb_s_new = new int[repsCount][];
	         			hqpx.resp_n5Min_s_new = new int[repsCount][];
	         			hqpx.resp_bSuspended_s_new = new byte[repsCount][];
	         			hqpx.resp_nHsl_s_new = new int[repsCount][];
	         			hqpx.resp_nSyl_s_new = new int[repsCount][];
	         			
	         			hqpx.resp_nReserved_s_new = new int[repsCount][];
	         			hqpx.resp_nBP_s_new = new int[repsCount][];
	         			hqpx.resp_nSP_s_new = new int[repsCount][];
	         			hqpx.resp_sLinkFlag_s_new = new String[repsCount][];
	         			hqpx.resp_dwsampleNum_s_new = new int[repsCount][];
	         			
	         			hqpx.resp_nsampleAvgPrice_s_new = new int[repsCount][];
	         			hqpx.resp_navgStock_s_new = new int[repsCount][];
	         			hqpx.resp_nmarketValue_s_new = new int[repsCount][];
	         			hqpx.resp_nzb_s_new = new int[repsCount][];
	         			hqpx.resp_slevelFlag_s_new = new String[repsCount][];
	         			
	         			hqpx.resp_pszBKCode_s_new = new String[repsCount][];
	         			hqpx.resp_pszBKName_s_new = new String[repsCount][];
	         			hqpx.resp_nBKzdf_s_new = new int[repsCount][];
					}
					for(int i=0;i<repsCount;i++){
		         		stockRank_rep mstockRank_rep=rep.getReps(i);
		         		multiStocks_rep mmultiStocks_rep=mstockRank_rep.getRep();
		         		rank_option mrank_option=mmultiStocks_rep.getOptions();
		         		hqpx.resp_wFrom_new[i] = mrank_option.getWFrom();
		        		hqpx.resp_bSort_new[i] = mrank_option.getBSort();
		        		if(mrank_option.getBDirect()){
		        			hqpx.resp_bDirect_new[i] =1;
		            	}else{
		            		hqpx.resp_bDirect_new[i] =0;
		            	}  
		  	
		        		hqpx.resp_wTotalCount_new[i] = mrank_option.getWTotalCount();
		         		List<stock_details_data> array=mmultiStocks_rep.getDataArrList();
		         		int wCount = array.size();
		        		hqpx.resp_wCount_new[i] = wCount;
		         		if(wCount>0){
							hqpx.resp_wMarketID_s = new int[wCount];
		         			hqpx.resp_wType_s = new short[wCount];
		         			hqpx.resp_wType_fix = new short[wCount];
		         			hqpx.resp_pszCode_s = new String[wCount];
		         			hqpx.resp_pszName_s = new String[wCount];
		         			
		         			hqpx.resp_pszMark_s = new String[wCount];
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
		         			hqpx.resp_nBsl1_s = new int[wCount];
		         			hqpx.resp_nSjg1_s = new int[wCount];
		         			hqpx.resp_nSsl1_s = new int[wCount];
		         			
		         			hqpx.resp_nZd_s = new int[wCount];
		         			hqpx.resp_nZdf_s = new int[wCount];
		         			hqpx.resp_nZf_s = new int[wCount];
		         			hqpx.resp_nZl_s = new int[wCount];
		         			hqpx.resp_nWb_s = new int[wCount];
		         			
		         			hqpx.resp_nLb_s = new int[wCount];
		         			hqpx.resp_n5Min_s = new int[wCount];
		         			hqpx.resp_bSuspended_s = new byte[wCount];
		         			hqpx.resp_nHsl_s = new int[wCount];
		         			hqpx.resp_nSyl_s = new int[wCount];
		         			
		         			hqpx.resp_nReserved_s = new int[wCount];
		         			hqpx.resp_nBP_s = new int[wCount];
		         			hqpx.resp_nSP_s = new int[wCount];
		         			hqpx.resp_sLinkFlag_s = new String[wCount];
		         			hqpx.resp_dwsampleNum_s = new int[wCount];
		         			
		         			hqpx.resp_nsampleAvgPrice_s = new int[wCount];
		         			hqpx.resp_navgStock_s = new int[wCount];
		         			hqpx.resp_nmarketValue_s = new int[wCount];
		         			hqpx.resp_nzb_s = new int[wCount];
		         			hqpx.resp_slevelFlag_s = new String[wCount];
		         			
		         			hqpx.resp_pszBKCode_s = new String[wCount];
		         			hqpx.resp_pszBKName_s = new String[wCount];
		         			hqpx.resp_nBKzdf_s = new int[wCount];
		         			for(int index = 0; index < wCount; index++){
		         				stock_details_data mstock_details_data=array.get(index);
		         				hqpx.resp_wMarketID_s[index] =mstock_details_data.getMarketID();
		         				hqpx.resp_wType_s[index] = (short) mstock_details_data.getWType();
		         				hqpx.resp_wType_fix[index] = (short) mstock_details_data.getWType();
		         				hqpx.resp_pszCode_s[index] =mstock_details_data.getStockCode();
		         				hqpx.resp_pszName_s[index] =mstock_details_data.getStockName();
		         				
		         				hqpx.resp_pszMark_s[index] =mstock_details_data.getPszMark();
		         				hqpx.resp_nZrsp_s[index] = mstock_details_data.getNZrsp();
		         				hqpx.resp_nZhsj_s[index] = 0;
		         				hqpx.resp_nJrkp_s[index] = mstock_details_data.getNJrkp();
		         				hqpx.resp_nZgcj_s[index] = mstock_details_data.getNZgcj();
		         				
		         				hqpx.resp_nZdcj_s[index] = mstock_details_data.getNZdcj();
		         				hqpx.resp_nZjcj_s[index] = mstock_details_data.getNZjcj();
		         				hqpx.resp_nCjss_s[index] = mstock_details_data.getNCjss();
		         				hqpx.resp_nCjje_s[index] = mstock_details_data.getNCjje();
		         				hqpx.resp_nCcl_s[index] = mstock_details_data.getTotalLongPosition();
		         				
		         				hqpx.resp_nHsj_s[index] = 0;
		         				hqpx.resp_nBjg1_s[index] = mstock_details_data.getNBjg1();
		         				hqpx.resp_nSjg1_s[index] = mstock_details_data.getNSjg1();
		         				hqpx.resp_nBsl1_s[index] = mstock_details_data.getNBsl1();
		         				hqpx.resp_nSsl1_s[index]=mstock_details_data.getNSsl1();
		         				
		         				hqpx.resp_nZd_s[index] = mstock_details_data.getNZd();
		         				hqpx.resp_nZdf_s[index] = mstock_details_data.getNZdf();
		         				hqpx.resp_nZf_s[index] = mstock_details_data.getNZf();
		         				hqpx.resp_nZl_s[index] = 0;
		         				hqpx.resp_nWb_s[index] = 0;
		         				
		         				hqpx.resp_nLb_s[index] = mstock_details_data.getNLb();
		         				hqpx.resp_n5Min_s[index] = mstock_details_data.getN5Min();
		         				if(mstock_details_data.getBSuspended()){
		         					hqpx.resp_bSuspended_s[index] = 1;
			        			}else{
			        				hqpx.resp_bSuspended_s[index] = 0;
			        			}
		         				hqpx.resp_nHsl_s[index] = mstock_details_data.getNHsl();
		         				hqpx.resp_nSyl_s[index] = mstock_details_data.getNSyl();
		         				
		         				hqpx.resp_nReserved_s[index]=0;
		         				hqpx.resp_nBP_s[index] = 0;
		         				hqpx.resp_nSP_s[index] = 0;
		         				hqpx.resp_sLinkFlag_s[index] = "";
		         				hqpx.resp_dwsampleNum_s[index] = 0;
		         				
		         				hqpx.resp_nsampleAvgPrice_s[index]=0;
		         				hqpx.resp_navgStock_s[index] = 0;
		         				hqpx.resp_nmarketValue_s[index] = mstock_details_data.getNZSZ();
		         				hqpx.resp_nzb_s[index] = 0;
		         				hqpx.resp_slevelFlag_s[index] ="";
		         				
		         				hqpx.resp_pszBKCode_s[index] = "";
		         				hqpx.resp_pszBKName_s[index] =mstock_details_data.getStockName();
		         				hqpx.resp_nBKzdf_s[index] =0;
		         				
		         			}
	
							hqpx.resp_wMarketID_s_new[i] = hqpx.resp_wMarketID_s;
		         			hqpx.resp_wType_s_new[i] =hqpx.resp_wType_s;
		         			hqpx.resp_wType_fix_new[i] = hqpx.resp_wType_fix;
		         			hqpx.resp_pszCode_s_new[i] = hqpx.resp_pszCode_s;
		         			hqpx.resp_pszName_s_new[i] = hqpx.resp_pszName_s;
		         			
		         			hqpx.resp_pszMark_s_new[i] = hqpx.resp_pszMark_s;
		         			hqpx.resp_nZrsp_s_new[i] =hqpx.resp_nZrsp_s;
		         			hqpx.resp_nZhsj_s_new[i] = hqpx.resp_nZhsj_s;
		         			hqpx.resp_nJrkp_s_new[i] =hqpx.resp_nJrkp_s;
		         			hqpx.resp_nZgcj_s_new[i] = hqpx.resp_nZgcj_s;
		         			
		         			hqpx.resp_nZdcj_s_new[i] =hqpx.resp_nZdcj_s;
		         			hqpx.resp_nZjcj_s_new[i] = hqpx.resp_nZjcj_s;
		         			hqpx.resp_nCjss_s_new[i] = hqpx.resp_nCjss_s;
		         			hqpx.resp_nCjje_s_new[i] = hqpx.resp_nCjje_s;
		         			hqpx.resp_nCcl_s_new[i] =hqpx.resp_nCcl_s;
		         			
		         			hqpx.resp_nHsj_s_new[i] = hqpx.resp_nHsj_s;
		         			hqpx.resp_nBjg1_s_new[i] = hqpx.resp_nBjg1_s;
		         			hqpx.resp_nBsl1_s_new[i] = hqpx.resp_nBsl1_s;
		         			hqpx.resp_nSjg1_s_new[i] = hqpx.resp_nSjg1_s;
		         			hqpx.resp_nSsl1_s_new[i] = hqpx.resp_nSsl1_s;
		         			
		         			hqpx.resp_nZd_s_new[i] = hqpx.resp_nZd_s;
		         			hqpx.resp_nZdf_s_new[i] = hqpx.resp_nZdf_s;
		         			hqpx.resp_nZf_s_new[i] = hqpx.resp_nZf_s;
		         			hqpx.resp_nZl_s_new[i] = hqpx.resp_nZl_s;
		         			hqpx.resp_nWb_s_new[i] = hqpx.resp_nWb_s;
		         			
		         			hqpx.resp_nLb_s_new[i] = hqpx.resp_nLb_s;
		         			hqpx.resp_n5Min_s_new[i] = hqpx.resp_n5Min_s;
		         			hqpx.resp_bSuspended_s_new[i] = hqpx.resp_bSuspended_s;
		         			hqpx.resp_nHsl_s_new[i] = hqpx.resp_nHsl_s;
		         			hqpx.resp_nSyl_s_new[i] =hqpx.resp_nSyl_s;
		         			
		         			hqpx.resp_nReserved_s_new[i] = hqpx.resp_nReserved_s;
		         			hqpx.resp_nBP_s_new[i] = hqpx.resp_nBP_s;
		         			hqpx.resp_nSP_s_new[i] = hqpx.resp_nSP_s;
		         			hqpx.resp_sLinkFlag_s_new[i] = hqpx.resp_sLinkFlag_s;
		         			hqpx.resp_dwsampleNum_s_new[i] = hqpx.resp_dwsampleNum_s;
		         			
		         			hqpx.resp_nsampleAvgPrice_s_new[i] = hqpx.resp_nsampleAvgPrice_s;
		         			hqpx.resp_navgStock_s_new[i] =hqpx.resp_navgStock_s;
		         			hqpx.resp_nmarketValue_s_new[i] = hqpx.resp_nmarketValue_s;
		         			hqpx.resp_nzb_s_new[i] = hqpx.resp_nzb_s;
		         			hqpx.resp_slevelFlag_s_new[i] = hqpx.resp_slevelFlag_s;
		         			
		         			hqpx.resp_pszBKCode_s_new[i] =hqpx.resp_pszBKCode_s;
		         			hqpx.resp_pszBKName_s_new[i] = hqpx.resp_pszBKName_s;
		         			hqpx.resp_nBKzdf_s_new[i] =hqpx.resp_nBKzdf_s;
		         			
						}
		         		
		         	}
				}else{
					for(int i=0;i<rep.getRepsCount();i++){
		         		stockRank_rep mstockRank_rep=rep.getReps(i);
		         		multiStocks_rep mmultiStocks_rep=mstockRank_rep.getRep();
		         		rank_option mrank_option=mmultiStocks_rep.getOptions();
		         		hqpx.resp_wFrom = mrank_option.getWFrom();
		        		hqpx.resp_bSort = mrank_option.getBSort();
		        		if(mrank_option.getBDirect()){
		        			hqpx.resp_bDirect =1;
		            	}else{
		            		hqpx.resp_bDirect =0;
		            	}  
		  	
		        		hqpx.resp_wTotalCount = mrank_option.getWTotalCount();
		         		List<stock_details_data> array=mmultiStocks_rep.getDataArrList();
		         		int wCount = array.size();
		        		hqpx.resp_wCount = wCount;
		         		if(wCount>0){
							hqpx.resp_wMarketID_s = new int[wCount];
		         			hqpx.resp_wType_s = new short[wCount];
		         			hqpx.resp_wType_fix = new short[wCount];
		         			hqpx.resp_pszCode_s = new String[wCount];
		         			hqpx.resp_pszName_s = new String[wCount];
		         			
		         			hqpx.resp_pszMark_s = new String[wCount];
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
		         			hqpx.resp_nBsl1_s = new int[wCount];
		         			hqpx.resp_nSjg1_s = new int[wCount];
		         			hqpx.resp_nSsl1_s = new int[wCount];
		         			
		         			hqpx.resp_nZd_s = new int[wCount];
		         			hqpx.resp_nZdf_s = new int[wCount];
		         			hqpx.resp_nZf_s = new int[wCount];
		         			hqpx.resp_nZl_s = new int[wCount];
		         			hqpx.resp_nWb_s = new int[wCount];
		         			
		         			hqpx.resp_nLb_s = new int[wCount];
		         			hqpx.resp_n5Min_s = new int[wCount];
		         			hqpx.resp_bSuspended_s = new byte[wCount];
		         			hqpx.resp_nHsl_s = new int[wCount];
		         			hqpx.resp_nSyl_s = new int[wCount];
		         			
		         			hqpx.resp_nReserved_s = new int[wCount];
		         			hqpx.resp_nBP_s = new int[wCount];
		         			hqpx.resp_nSP_s = new int[wCount];
		         			hqpx.resp_sLinkFlag_s = new String[wCount];
		         			hqpx.resp_dwsampleNum_s = new int[wCount];
		         			
		         			hqpx.resp_nsampleAvgPrice_s = new int[wCount];
		         			hqpx.resp_navgStock_s = new int[wCount];
		         			hqpx.resp_nmarketValue_s = new int[wCount];
		         			hqpx.resp_nzb_s = new int[wCount];
		         			hqpx.resp_slevelFlag_s = new String[wCount];
		         			
		         			hqpx.resp_pszBKCode_s = new String[wCount];
		         			hqpx.resp_pszBKName_s = new String[wCount];
		         			hqpx.resp_nBKzdf_s = new int[wCount];
		         			for(int index = 0; index < wCount; index++){
		         				stock_details_data mstock_details_data=array.get(index);
		         				hqpx.resp_wMarketID_s[index] =mstock_details_data.getMarketID();
		         				hqpx.resp_wType_s[index] = (short) mstock_details_data.getWType();
		         				hqpx.resp_wType_fix[index] = (short) mstock_details_data.getWType();
		         				hqpx.resp_pszCode_s[index] =mstock_details_data.getStockCode();
		         				hqpx.resp_pszName_s[index] =mstock_details_data.getStockName();
		         				
		         				hqpx.resp_pszMark_s[index] =mstock_details_data.getPszMark();
		         				hqpx.resp_nZrsp_s[index] = mstock_details_data.getNZrsp();
		         				hqpx.resp_nZhsj_s[index] = 0;
		         				hqpx.resp_nJrkp_s[index] = mstock_details_data.getNJrkp();
		         				hqpx.resp_nZgcj_s[index] = mstock_details_data.getNZgcj();
		         				
		         				hqpx.resp_nZdcj_s[index] = mstock_details_data.getNZdcj();
		         				hqpx.resp_nZjcj_s[index] = mstock_details_data.getNZjcj();
		         				hqpx.resp_nCjss_s[index] = mstock_details_data.getNCjss();
		         				hqpx.resp_nCjje_s[index] = mstock_details_data.getNCjje();
		         				hqpx.resp_nCcl_s[index] = mstock_details_data.getTotalLongPosition();
		         				
		         				hqpx.resp_nHsj_s[index] = 0;
		         				hqpx.resp_nBjg1_s[index] = mstock_details_data.getNBjg1();
		         				hqpx.resp_nSjg1_s[index] = mstock_details_data.getNSjg1();
		         				hqpx.resp_nBsl1_s[index] = mstock_details_data.getNBsl1();
		         				hqpx.resp_nSsl1_s[index]=mstock_details_data.getNSsl1();
		         				
		         				hqpx.resp_nZd_s[index] = mstock_details_data.getNZd();
		         				hqpx.resp_nZdf_s[index] = mstock_details_data.getNZdf();
		         				hqpx.resp_nZf_s[index] = mstock_details_data.getNZf();
		         				hqpx.resp_nZl_s[index] = 0;
		         				hqpx.resp_nWb_s[index] = 0;
		         				
		         				hqpx.resp_nLb_s[index] = mstock_details_data.getNLb();
		         				hqpx.resp_n5Min_s[index] = mstock_details_data.getN5Min();
		         				if(mstock_details_data.getBSuspended()){
		         					hqpx.resp_bSuspended_s[index] = 1;
			        			}else{
			        				hqpx.resp_bSuspended_s[index] = 0;
			        			}
		         				hqpx.resp_nHsl_s[index] = mstock_details_data.getNHsl();
		         				hqpx.resp_nSyl_s[index] = mstock_details_data.getNSyl();
		         				
		         				hqpx.resp_nReserved_s[index]=0;
		         				hqpx.resp_nBP_s[index] = 0;
		         				hqpx.resp_nSP_s[index] = 0;
		         				hqpx.resp_sLinkFlag_s[index] = "";
		         				hqpx.resp_dwsampleNum_s[index] = 0;
		         				
		         				hqpx.resp_nsampleAvgPrice_s[index]=0;
		         				hqpx.resp_navgStock_s[index] = 0;
		         				hqpx.resp_nmarketValue_s[index] = mstock_details_data.getNZSZ();
		         				hqpx.resp_nzb_s[index] = 0;
		         				hqpx.resp_slevelFlag_s[index] ="";
		         				
		         				hqpx.resp_pszBKCode_s[index] = "";
		         				hqpx.resp_pszBKName_s[index] =mstock_details_data.getStockName();
		         				hqpx.resp_nBKzdf_s[index] =0;
		         				
		         			}
						}
		         		
		         	}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
         
         }


	}
	private void initArray(int repsCount, HQPXProtocol hqpx){
		hqpx.resp_wFrom_array = new int[repsCount];
		hqpx.resp_bSort_array = new int[repsCount];
		hqpx.resp_bDirect_array = new byte[repsCount];
		hqpx.resp_wTotalCount_array = new int[repsCount];
		hqpx.resp_wCount_array = new int[repsCount];
		hqpx.resp_wMarketID_s_array = new int[repsCount][];
		hqpx.resp_wType_s_array = new short[repsCount][];
		hqpx.resp_wType_fix_array = new short[repsCount][];
		hqpx.resp_pszCode_s_array = new String[repsCount][];
		hqpx.resp_pszName_s_array = new String[repsCount][];
		hqpx.resp_pszMark_s_array = new String[repsCount][];
		hqpx.resp_nZrsp_s_array = new int[repsCount][];
		hqpx.resp_nZhsj_s_array = new int[repsCount][];
		hqpx.resp_nJrkp_s_array = new int[repsCount][];
		hqpx.resp_nZgcj_s_array = new int[repsCount][];
		hqpx.resp_nZdcj_s_array = new int[repsCount][];
		hqpx.resp_nZjcj_s_array = new int[repsCount][];
		hqpx.resp_nCjss_s_array = new int[repsCount][];
		hqpx.resp_nCjje_s_array = new int[repsCount][];
		hqpx.resp_nCcl_s_array = new int[repsCount][];
		hqpx.resp_nHsj_s_array = new int[repsCount][];
		hqpx.resp_nBjg1_s_array = new int[repsCount][];
		hqpx.resp_nBsl1_s_array = new int[repsCount][];
		hqpx.resp_nSjg1_s_array = new int[repsCount][];
		hqpx.resp_nSsl1_s_array = new int[repsCount][];
		hqpx.resp_nZd_s_array = new int[repsCount][];
		hqpx.resp_nZdf_s_array = new int[repsCount][];
		hqpx.resp_nZf_s_array = new int[repsCount][];
		hqpx.resp_nZl_s_array = new int[repsCount][];
		hqpx.resp_nWb_s_array = new int[repsCount][];
		hqpx.resp_nLb_s_array = new int[repsCount][];
		hqpx.resp_n5Min_s_array = new int[repsCount][];
		hqpx.resp_bSuspended_s_array = new byte[repsCount][];
		hqpx.resp_nHsl_s_array = new int[repsCount][];
		hqpx.resp_nSyl_s_array = new int[repsCount][];
		hqpx.resp_nReserved_s_array = new int[repsCount][];
		hqpx.resp_nBP_s_array = new int[repsCount][];
		hqpx.resp_nSP_s_array = new int[repsCount][];
		hqpx.resp_sLinkFlag_s_array = new String[repsCount][];
		hqpx.resp_dwsampleNum_s_array = new int[repsCount][];
		hqpx.resp_nsampleAvgPrice_s_array = new int[repsCount][];
		hqpx.resp_navgStock_s_array = new int[repsCount][];
		hqpx.resp_nmarketValue_s_array = new int[repsCount][];
		hqpx.resp_nzb_s_array = new int[repsCount][];
		hqpx.resp_slevelFlag_s_array = new String[repsCount][];
		hqpx.resp_pszBKCode_s_array = new String[repsCount][];
		hqpx.resp_pszBKName_s_array = new String[repsCount][];
		hqpx.resp_nBKzdf_s_array = new int[repsCount][];
	}
}

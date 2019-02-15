package com.romaway.common.protocol.hq;

import com.google.protobuf.InvalidProtocolBufferException;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.R;
import com.romaway.common.protocol.util.KFloat;
import com.romaway.common.protocol.util.ULongUtils;

import java.util.List;

import protobuf.Protobuf;
import protobuf.Protobuf.multi_stock_united_req;
import protobuf.Protobuf.stock_details_data;
import protobuf.Protobuf.stock_timeDivision_data;
import protobuf.Protobuf.stock_timeDivision_rep;
import protobuf.Protobuf.stock_timeDivision_req;
import protobuf.Protobuf.stock_tradeClassify_data;
import protobuf.Protobuf.stock_tradeClassify_data_repeated;
import protobuf.Protobuf.stock_tradeDetail_data;
import protobuf.Protobuf.stock_tradeDetail_rep;
import protobuf.Protobuf.stock_tradeDetail_req;
import protobuf.Protobuf.stock_united_rep;
import protobuf.Protobuf.stock_united_req;

/**
 * 分时综合协议解码
 * 
 * @author qinyn
 * 
 */
public class HQFSZHProtocolCoder extends AProtocolCoder<HQFSZHProtocol>
{

	@Override
	protected byte[] encode(HQFSZHProtocol hqfszh)
	{
		//分时请求
		stock_timeDivision_req.Builder stock_timeDivision_reqbuild=stock_timeDivision_req.newBuilder();
		stock_timeDivision_reqbuild.setDwFSDate(hqfszh.req_dwFSDate);
		stock_timeDivision_reqbuild.setDwFSTime(hqfszh.req_dwFSTime);
		//分笔请求
		stock_tradeDetail_req.Builder stock_tradeDetail_reqbuild=stock_tradeDetail_req.newBuilder();
		stock_tradeDetail_reqbuild.setCount(20);
		//个股综合
		stock_united_req.Builder stock_united_reqqbuild=stock_united_req.newBuilder();
		stock_united_reqqbuild.setWMarketID(hqfszh.req_wMarketID);
		stock_united_reqqbuild.setSPszCode(hqfszh.req_sPszCode);
		stock_united_reqqbuild.setWType(0);

		long whole = ULongUtils.getWholeBitMap(hqfszh.req_fieldsRes);

		stock_united_reqqbuild.setFieldsBitMap(whole);
		stock_united_reqqbuild.setTimeDivisionReq(stock_timeDivision_reqbuild.build());
		stock_united_reqqbuild.setTradeDetailReq(stock_tradeDetail_reqbuild.build());


		multi_stock_united_req.Builder multi_stock_united_reqbuild=multi_stock_united_req.newBuilder();
		multi_stock_united_reqbuild.addReqs(stock_united_reqqbuild.build());

		return multi_stock_united_reqbuild.build().toByteArray();
	}
	
	@Override
	protected void decode(HQFSZHProtocol hqfszh) throws ProtocolParserException
	{
		if (hqfszh.getReceiveData() != null) {
         	Protobuf.multi_stock_united_rep rep;
			try {
				rep = Protobuf.multi_stock_united_rep.parseFrom(hqfszh.getReceiveData());
				for(int i=0;i<rep.getRepsCount();i++){
					stock_united_rep mstock_united_rep=rep.getReps(i);
					stock_details_data mstock_details_data=mstock_united_rep.getDetailsData();
					stock_tradeDetail_rep tradeDetail_data=mstock_united_rep.getTradeDetailData();
					stock_timeDivision_rep timeDivision_rep=mstock_united_rep.getTimeDivisionData();
					short wCount = 0;
					hqfszh.resp_wMarketID =(short) mstock_details_data.getMarketID();
					hqfszh.resp_wType = (short) mstock_details_data.getWType();
					hqfszh.resp_pszCode =mstock_details_data.getStockCode();
					hqfszh.resp_pszName =mstock_details_data.getStockName();
					hqfszh.resp_pszMark=mstock_details_data.getPszMark();
					hqfszh.resp_wStockStatus = (short) mstock_details_data.getWStockStatus();

					hqfszh.resp_dwDate = mstock_details_data.getNRqSj();
					hqfszh.resp_wCYDZS = (short) mstock_details_data.getWCYDZS();
					hqfszh.resp_nZrsp = mstock_details_data.getNZrsp();
					hqfszh.resp_nJrkp = mstock_details_data.getNJrkp();
					hqfszh.resp_nZgcj = mstock_details_data.getNZgcj();

					hqfszh.resp_nZdcj = mstock_details_data.getNZdcj();
					hqfszh.resp_nZjcj = mstock_details_data.getNZjcj();
					hqfszh.resp_dwLastTime = mstock_details_data.getNRqSj();
					hqfszh.resp_nMaxVol = timeDivision_rep.getNMaxVol();
					hqfszh.resp_nHsj =0;

					hqfszh.resp_nZd = mstock_details_data.getNZd();
					hqfszh.resp_nZdf = mstock_details_data.getNZdf();
					hqfszh.resp_nZf = mstock_details_data.getNZf();
					hqfszh.resp_nCjss =mstock_details_data.getNCjss();
					hqfszh.resp_nCjje = mstock_details_data.getNCjje();

					hqfszh.resp_wZjs =(short) mstock_details_data.getWZjs();
					hqfszh.resp_wDjs = (short) mstock_details_data.getWDjs();
					hqfszh.resp_sBKCode = mstock_details_data.getBlockID();
					hqfszh.resp_wsBKName =mstock_details_data.getBlockName();
					hqfszh.resp_nBKZF = mstock_details_data.getBlockZDF();

					hqfszh.resp_nWb = 0;
					hqfszh.resp_nWc = 0;
					hqfszh.resp_nLb = mstock_details_data.getNLb();
					hqfszh.resp_nBuyp =tradeDetail_data.getNBuyp();
					hqfszh.resp_nSelp = tradeDetail_data.getNSelp();

					hqfszh.resp_nLimUp = mstock_details_data.getNLimUp();
					hqfszh.resp_nLimDown = mstock_details_data.getNLimDown();
					hqfszh.resp_sLinkFlag = "";
					if(mstock_details_data.getBSuspended()){
						hqfszh.resp_bSuspended = 1;
        			}else{
        				hqfszh.resp_bSuspended = 0;
        			}

					//买卖档位
					stock_tradeClassify_data_repeated mstock_tradeClassify_data_repeated=
							mstock_details_data.getTradeClassifyDataArr();
					List<stock_tradeClassify_data> Datas=mstock_tradeClassify_data_repeated.getTradeClassifyDataList();
					hqfszh.resp_wMMFADataCount = Datas.size();
					if(hqfszh.resp_wMMFADataCount>0){
						hqfszh.resp_nBjg_s = new int[hqfszh.resp_wMMFADataCount];
						hqfszh.resp_nBsl_s = new int[hqfszh.resp_wMMFADataCount];
						hqfszh.resp_nSjg_s = new int[hqfszh.resp_wMMFADataCount];
						hqfszh.resp_nSsl_s = new int[hqfszh.resp_wMMFADataCount];
					}
					for(int j=0;j<hqfszh.resp_wMMFADataCount;j++){
						stock_tradeClassify_data tradeClassify_data=mstock_tradeClassify_data_repeated.getTradeClassifyData(j);
						hqfszh.resp_nBjg_s[j] = tradeClassify_data.getNBjg();
						hqfszh.resp_nBsl_s[j] = tradeClassify_data.getNBsl();
						hqfszh.resp_nSjg_s[j] = tradeClassify_data.getNSjg();
						hqfszh.resp_nSsl_s[j] = tradeClassify_data.getNSsl();

					}

			   //此段协议没有，需要调试看看看需不需要
					hqfszh.resp_wZSDataCount = 0;
					if (wCount > 0)
					{
						hqfszh.resp_sZSPszCode_s = new String[wCount];
						hqfszh.resp_wsZSPszName_s = new String[wCount];
						hqfszh.resp_nZSXj_s = new int[wCount];
						hqfszh.resp_nZSZdf_s = new int[wCount];
						hqfszh.resp_nZSZrsp_s = new int[wCount];
					}
					for (int k = 0; k< wCount; k++)
					{
						hqfszh.resp_sZSPszCode_s[i] = "";
						hqfszh.resp_wsZSPszName_s[i] = "";//
						hqfszh.resp_nZSXj_s[i] = 0;
						hqfszh.resp_nZSZdf_s[i] = 0;
						hqfszh.resp_nZSZrsp_s[i] = 0;
					}

					//分笔					
					List<stock_tradeDetail_data> stock_tradeDetail_datas= tradeDetail_data.getDataArrList();
					hqfszh.resp_wFBDataCount = stock_tradeDetail_datas.size();
					if (hqfszh.resp_wFBDataCount > 0)
					{
						hqfszh.resp_dwFBTime_s = new int[hqfszh.resp_wFBDataCount];
						hqfszh.resp_bFBCjlb_s = new byte[hqfszh.resp_wFBDataCount];
						hqfszh.resp_nFBZjcj_s = new int[hqfszh.resp_wFBDataCount];
						hqfszh.resp_nFBCjss_s = new int[hqfszh.resp_wFBDataCount];
						for (int l = 0; l < hqfszh.resp_wFBDataCount; l++)
						{
							stock_tradeDetail_data mstock_tradeDetail_data=stock_tradeDetail_datas.get(l);
							hqfszh.resp_dwFBTime_s[l] =mstock_tradeDetail_data.getDwTime();
							hqfszh.resp_bFBCjlb_s[l] =(byte) mstock_tradeDetail_data.getBCjlb().charAt(0);
							hqfszh.resp_nFBZjcj_s[l] =mstock_tradeDetail_data.getNZjcj();
							hqfszh.resp_nFBCjss_s[l] = mstock_tradeDetail_data.getNCjss();
						}
					}


					//分时					
					List<stock_timeDivision_data> stock_timeDivision_datas= timeDivision_rep.getDataArrList();
					hqfszh.resp_wFSDataCount = (short) stock_timeDivision_datas.size();
					if (hqfszh.resp_wFSDataCount > 0)
					{
						hqfszh.resp_dwTime_s = new int[hqfszh.resp_wFSDataCount];
						hqfszh.resp_nZjcj_s = new int[hqfszh.resp_wFSDataCount];
						hqfszh.resp_nZdf_s = new int[hqfszh.resp_wFSDataCount];
						hqfszh.resp_nCjss_s = new int[hqfszh.resp_wFSDataCount];
						hqfszh.resp_nCjje_s = new int[hqfszh.resp_wFSDataCount];
						hqfszh.resp_nCjjj_s = new int[hqfszh.resp_wFSDataCount];
						hqfszh.resp_sXxgg_s = new short[hqfszh.resp_wFSDataCount];
						hqfszh.resp_nCcl_s = new int[hqfszh.resp_wFSDataCount];
						hqfszh.resp_nLb_s = new int[hqfszh.resp_wFSDataCount];
						hqfszh.resp_zNum_s = new int[hqfszh.resp_wFSDataCount];
						hqfszh.resp_dNum_s = new int[hqfszh.resp_wFSDataCount];
						for (int m = 0; m < hqfszh.resp_wFSDataCount; m++)
						{
							stock_timeDivision_data mstock_timeDivision_data=stock_timeDivision_datas.get(m);
							hqfszh.resp_dwTime_s[m] = mstock_timeDivision_data.getNTime();
							hqfszh.resp_nZjcj_s[m] = mstock_timeDivision_data.getNZjcj();
							hqfszh.resp_nZdf_s[m] = mstock_timeDivision_data.getNZdf();
							hqfszh.resp_nCjss_s[m] = mstock_timeDivision_data.getNCjss();
							hqfszh.resp_nCjje_s[m] = mstock_timeDivision_data.getNCjje();
							hqfszh.resp_nCjjj_s[m] = mstock_timeDivision_data.getNCjjj();
							hqfszh.resp_sXxgg_s[m] =0;
							hqfszh.resp_nCcl_s[m] = 0;
							hqfszh.resp_nLb_s[m] = 0;
							hqfszh.resp_zNum_s[m] =0;
							hqfszh.resp_dNum_s[m] = 0;
						}
					}

					hqfszh.resp_nSY = 0;
					hqfszh.resp_nSYKC = 0;
					hqfszh.resp_nJZC = 0;
					hqfszh.resp_nJZCSYL = 0;
					hqfszh.resp_nZBGJJ = 0;

					hqfszh.resp_nWFPLY = 0;
					hqfszh.resp_nXJLL = 0;
					hqfszh.resp_nJLY = 0;
					hqfszh.resp_nGDQY = 0;
					hqfszh.resp_iXL = 0;
					hqfszh.resp_iZSZ = mstock_details_data.getNZSZ();
					hqfszh.resp_iJJ = 0;
					hqfszh.resp_iJQJJ = 0;
					hqfszh.resp_iPP = mstock_details_data.getWPjs();
					hqfszh.resp_iHSL = mstock_details_data.getNHsl();
					hqfszh.resp_iSYL = mstock_details_data.getNSyl();
					hqfszh.resp_iLTP = mstock_details_data.getNLTP();

					hqfszh.resp_sZZZQValPrice ="";
					hqfszh.resp_sbRZBD = '0';
					hqfszh.resp_sbRQBD = '0';

					hqfszh.resp_buy_1_price = mstock_details_data.getNBjg1();
					hqfszh.resp_sell_1_price = mstock_details_data.getNSjg1();
					hqfszh.resp_exercise_price = mstock_details_data.getExercisePrice();
					hqfszh.resp_buy_1_volume = mstock_details_data.getNBsl1();
					hqfszh.resp_sell_1_volume = mstock_details_data.getNSsl1();
					hqfszh.resp_total_long_position = mstock_details_data.getTotalLongPosition();
					hqfszh.resp_high_price = mstock_details_data.getNZgcj();
					hqfszh.resp_low_price = mstock_details_data.getNZdcj();
					hqfszh.resp_cang_ca = mstock_details_data.getCangCha();
					hqfszh.resp_contract_multiplier_unit = mstock_details_data.getContractMultiplierUnit();
					hqfszh.resp_surplus_days = mstock_details_data.getSurplusDays();

					// 沪港通盘中熔断及盘后集合竞价: hgt_cv_data
					if(mstock_details_data.hasHcd()){
						KFloat kFloat = new KFloat();
						Protobuf.hgt_cv_data hgt_data = mstock_details_data.getHcd();
						hqfszh.resp_startTime = hgt_data.getStartTime()+"";
						hqfszh.resp_endTime = hgt_data.getEndTime()+"";
						hqfszh.resp_direction = hgt_data.getDirection();
						if (hgt_data.hasVcmRefPrice())
							hqfszh.resp_vcm_refPrice = kFloat.init(hgt_data.getVcmRefPrice()).toString();
						if (hgt_data.hasVcmLowPrice())
							hqfszh.resp_vcm_lowPrice = kFloat.init(hgt_data.getVcmLowPrice()).toString();
						if (hgt_data.hasVcmUpPrice())
							hqfszh.resp_vcm_upPrice = kFloat.init(hgt_data.getVcmUpPrice()).toString();
						if (hgt_data.hasCasRefPrice())
							hqfszh.resp_cas_refPrice = kFloat.init(hgt_data.getCasRefPrice()).toString();
						if (hgt_data.hasCasLowPrice())
							hqfszh.resp_cas_lowPrice = kFloat.init(hgt_data.getCasLowPrice()).toString();
						if (hgt_data.hasCasUpPrice())
							hqfszh.resp_cas_upPrice = kFloat.init(hgt_data.getCasUpPrice()).toString();
						if (hgt_data.hasQty())
							hqfszh.resp_qty = kFloat.init(hgt_data.getQty()).toString();
					}
					if(mstock_details_data.hasGzTypes()){
						KFloat kFloat = new KFloat();
						Protobuf.gz_type_group gz_type_group = mstock_details_data.getGzTypes();
						hqfszh.resp_gqzr_type = gz_type_group.getGqzrType();
						hqfszh.resp_gzfc_type = gz_type_group.getGzfcType();
						hqfszh.resp_lwts_type = gz_type_group.getLwtsType();
					}
	         	}
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}
         }
	}

}

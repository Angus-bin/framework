package com.romaway.common.protocol.hq;

import com.google.protobuf.InvalidProtocolBufferException;
import com.romaway.common.android.base.OriginalContext;
import com.romaway.common.protocol.AProtocolCoder;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.common.protocol.ProtocolParserException;
import com.romaway.common.protocol.R;
import com.romaway.common.protocol.util.KFloat;
import com.romaway.common.protocol.util.ULongUtils;

import java.util.List;

import protobuf.Protobuf;
import protobuf.Protobuf.multi_stock_united_req;
import protobuf.Protobuf.stock_details_data;
import protobuf.Protobuf.stock_kline_data;
import protobuf.Protobuf.stock_kline_rep;
import protobuf.Protobuf.stock_kline_req;
import protobuf.Protobuf.stock_tradeDetail_rep;
import protobuf.Protobuf.stock_tradeDetail_req;
import protobuf.Protobuf.stock_united_rep;
import protobuf.Protobuf.stock_united_req;

/**
 * K线带指标请求协议
 * 
 * @author xueyan
 * 
 */
public class HQKXProtocolCoder extends AProtocolCoder<HQKXProtocol> {

	@Override
	protected byte[] encode(HQKXProtocol kxhq) {

		//k线请求
		stock_kline_req.Builder stock_kline_reqqbuild=stock_kline_req.newBuilder();
		stock_kline_reqqbuild.setWKXType(kxhq.req_wkxType);
		stock_kline_reqqbuild.setDwKXDate(kxhq.req_nDate);
		stock_kline_reqqbuild.setDwKXTime(kxhq.req_nTime);
		stock_kline_reqqbuild.setWKXCount(kxhq.req_wCount);
		stock_kline_reqqbuild.setWFQType(kxhq.req_wFQType);
		//分笔请求
		stock_tradeDetail_req.Builder stock_tradeDetail_reqbuild=stock_tradeDetail_req.newBuilder();
		stock_tradeDetail_reqbuild.setCount(1);
		//个股综合
		stock_united_req.Builder stock_united_reqqbuild=stock_united_req.newBuilder();
		stock_united_reqqbuild.setWMarketID(kxhq.req_wMarketID);
		stock_united_reqqbuild.setSPszCode(kxhq.req_pszCode);
		stock_united_reqqbuild.setWType(0);

        long whole = ULongUtils.getWholeBitMap(kxhq.req_fieldsRes);

		stock_united_reqqbuild.setFieldsBitMap(whole);
		stock_united_reqqbuild.setKlineReq(stock_kline_reqqbuild.build());
		stock_united_reqqbuild.setTradeDetailReq(stock_tradeDetail_reqbuild.build());
		    	
		multi_stock_united_req.Builder multi_stock_united_reqbuild=multi_stock_united_req.newBuilder();
		multi_stock_united_reqbuild.addReqs(stock_united_reqqbuild.build());
		return multi_stock_united_reqbuild.build().toByteArray();
	}
	
	@Override
	protected void decode(HQKXProtocol hqkx) throws ProtocolParserException {
		
		if (hqkx.getReceiveData() != null) {
         	Protobuf.multi_stock_united_rep rep;
			try {
				rep = Protobuf.multi_stock_united_rep.parseFrom(hqkx.getReceiveData());
				for(int i=0;i<rep.getRepsCount();i++){
					stock_united_rep mstock_united_rep=rep.getReps(i);
	         		stock_details_data mstock_details_data=mstock_united_rep.getDetailsData();
	         		stock_tradeDetail_rep tradeDetail_data=mstock_united_rep.getTradeDetailData();
	         		stock_kline_rep  mstock_kline_rep=mstock_united_rep.getKlineData();
	         		hqkx.resp_wMarketID =(short) mstock_details_data.getMarketID();
					hqkx.resp_wType = (short) mstock_details_data.getWType();
					hqkx.resp_wKXType = mstock_kline_rep.getWKXType();
                    hqkx.resp_needFQ = mstock_kline_rep.getNeedFQ();
					hqkx.resp_pszCode =mstock_details_data.getStockCode();
					hqkx.resp_pszMark=mstock_details_data.getPszMark();
					hqkx.resp_pszName =mstock_details_data.getStockName();
					hqkx.resp_wStockStatus = mstock_details_data.getWStockStatus();
					
					hqkx.resp_nZrsp = mstock_details_data.getNZrsp();
					hqkx.resp_nJrkp = mstock_details_data.getNJrkp();
					hqkx.resp_nZgcj = mstock_details_data.getNZgcj();										
					hqkx.resp_nZdcj = mstock_details_data.getNZdcj();
					hqkx.resp_nZjcj = mstock_details_data.getNZjcj();
					
					hqkx.resp_nZd = mstock_details_data.getNZd();
					hqkx.resp_nZdf = mstock_details_data.getNZdf();
					hqkx.resp_nZf = mstock_details_data.getNZf();
					hqkx.resp_nCjss = mstock_details_data.getNCjss();
					hqkx.resp_nCjje = mstock_details_data.getNCjje();
					hqkx.resp_wZjs = mstock_details_data.getWZjs();
			
					hqkx.resp_wDjs = mstock_details_data.getWDjs();
					hqkx.resp_wPjs = mstock_details_data.getWPjs();
					hqkx.resp_nBuyp = tradeDetail_data.getNBuyp();
					hqkx.resp_nSelp = tradeDetail_data.getNSelp();
					hqkx.resp_sHSL =mstock_details_data.getNHsl();
					
					hqkx.resp_sSYL = mstock_details_data.getNSyl();
					hqkx.resp_sLTP = mstock_details_data.getNLTP();
					hqkx.resp_sZSZ = mstock_details_data.getNZSZ();
					hqkx.resp_dwDateTime = mstock_details_data.getNRqSj();
					if(mstock_details_data.getBSuspended()){
						hqkx.resp_bSuspended = 1;
        			}else{
        				hqkx.resp_bSuspended = 0;
        			}
				//k线数据集	
					List<stock_kline_data> stock_kline_datas=mstock_kline_rep.getKlineDataArrList();					
					int wCount =stock_kline_datas.size();
					hqkx.resp_wKXDataCount = wCount;					
					hqkx.resp_dwDate_s = new int[wCount];
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
			
					hqkx.resp_nMA1_s = new int[wCount];
					hqkx.resp_nMA2_s = new int[wCount];
					hqkx.resp_nMA3_s = new int[wCount];
					hqkx.resp_nTech1_s = new int[wCount];
					hqkx.resp_nTech2_s = new int[wCount];
			
					hqkx.resp_nTech3_s = new int[wCount];
					hqkx.resp_nZd_s = new int[wCount];
					for (int index = wCount-1; index >= 0; index--) {
						stock_kline_data mstock_kline_data=stock_kline_datas.get(index);
						hqkx.resp_dwDate_s[wCount-1-index] = mstock_kline_data.getNDate();
						hqkx.resp_dwTime_s[wCount-1-index] = mstock_kline_data.getNTime();
						hqkx.resp_nYClose_s[wCount-1-index] = mstock_kline_data.getNYClose();
						hqkx.resp_nOpen_s[wCount-1-index] = mstock_kline_data.getNOpen();
						hqkx.resp_nZgcj_s[wCount-1-index] = mstock_kline_data.getNZgcj();
						hqkx.resp_nZdcj_s[wCount-1-index] = mstock_kline_data.getNZdcj();
			
						hqkx.resp_nClose_s[wCount-1-index] = mstock_kline_data.getNClose();
						hqkx.resp_nZdf_s[wCount-1-index] = mstock_kline_data.getNZdf();
						hqkx.resp_nCjje_s[wCount-1-index] = mstock_kline_data.getNCjje();
						hqkx.resp_nCjss_s[wCount-1-index] = mstock_kline_data.getNCjss();
						hqkx.resp_nCcl_s[wCount-1-index] = mstock_kline_data.getNCcl();
						List<Integer> mas=mstock_kline_data.getNMAList();
						List<Integer> Techs=mstock_kline_data.getNTechList();
						for(int j=0;j<mas.size();j++){
							if(j==0){
								hqkx.resp_nMA1_s[wCount-1-index] = mas.get(j);
							}else if(j==1){
								hqkx.resp_nMA2_s[wCount-1-index] = mas.get(j);
							}else if(j==2){
								hqkx.resp_nMA3_s[wCount-1-index] = mas.get(j);
							}					
						}
						for(int k=0;k<Techs.size();k++){
							if(k==0){
								hqkx.resp_nTech1_s[wCount-1-index] = Techs.get(k);
							}else if(k==1){
								hqkx.resp_nTech2_s[wCount-1-index] = Techs.get(k);
							}else if(k==2){
								hqkx.resp_nTech3_s[wCount-1-index] = Techs.get(k);
							}					
						}												
						hqkx.resp_nZd_s[wCount-1-index] = mstock_kline_data.getNZd();
					}
					hqkx.resp_bRZBD = '0';
					hqkx.resp_bRQBD = '0';
					
					hqkx.resp_buy_1_price = mstock_details_data.getNBjg1();
					hqkx.resp_sell_1_price = mstock_details_data.getNSjg1();
					hqkx.resp_exercise_price = mstock_details_data.getExercisePrice();
					hqkx.resp_buy_1_volume = mstock_details_data.getNBsl1();
					hqkx.resp_sell_1_volume = mstock_details_data.getNSsl1();
					hqkx.resp_total_long_position = mstock_details_data.getTotalLongPosition();
					hqkx.resp_cang_ca = mstock_details_data.getCangCha();
					hqkx.resp_contract_multiplier_unit = mstock_details_data.getContractMultiplierUnit();
					hqkx.resp_surplus_days = mstock_details_data.getSurplusDays();
                    hqkx.resp_high_price = mstock_details_data.getNZgcj();
                    hqkx.resp_low_price = mstock_details_data.getNZdcj();

					// 沪港通盘中熔断及盘后集合竞价: hgt_cv_data
					if(mstock_details_data.hasHcd()){
						KFloat kFloat = new KFloat();
						Protobuf.hgt_cv_data hgt_data = mstock_details_data.getHcd();
						hqkx.resp_startTime = hgt_data.getStartTime()+"";
						hqkx.resp_endTime = hgt_data.getEndTime()+"";
						hqkx.resp_direction = hgt_data.getDirection();
						if (hgt_data.hasVcmRefPrice())
							hqkx.resp_vcm_refPrice = kFloat.init(hgt_data.getVcmRefPrice()).toString();
						if (hgt_data.hasVcmLowPrice())
							hqkx.resp_vcm_lowPrice = kFloat.init(hgt_data.getVcmLowPrice()).toString();
						if (hgt_data.hasVcmUpPrice())
							hqkx.resp_vcm_upPrice = kFloat.init(hgt_data.getVcmUpPrice()).toString();
						if (hgt_data.hasCasRefPrice())
							hqkx.resp_cas_refPrice = kFloat.init(hgt_data.getCasRefPrice()).toString();
						if (hgt_data.hasCasLowPrice())
							hqkx.resp_cas_lowPrice = kFloat.init(hgt_data.getCasLowPrice()).toString();
						if (hgt_data.hasCasUpPrice())
							hqkx.resp_cas_upPrice = kFloat.init(hgt_data.getCasUpPrice()).toString();
						if (hgt_data.hasQty())
							hqkx.resp_qty = kFloat.init(hgt_data.getQty()).toString();
					}
					if(mstock_details_data.hasGzTypes()){
						KFloat kFloat = new KFloat();
						Protobuf.gz_type_group gz_type_group = mstock_details_data.getGzTypes();
						hqkx.resp_gqzr_type = gz_type_group.getGqzrType();
						hqkx.resp_gzfc_type = gz_type_group.getGzfcType();
						hqkx.resp_lwts_type = gz_type_group.getLwtsType();
					}
	         	}
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}         	
         }	
	}

}
